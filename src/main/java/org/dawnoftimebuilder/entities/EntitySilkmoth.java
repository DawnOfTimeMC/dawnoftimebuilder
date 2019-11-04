package org.dawnoftimebuilder.entities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.dawnoftimebuilder.blocks.japanese.BlockMulberry;
import org.dawnoftimebuilder.registries.DoTBEntitiesRegistry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class EntitySilkmoth extends EntityAmbientCreature {

	private BlockPos rotationPos = new BlockPos(0,0,0);
	private boolean clockwise;
	private float distance;

	public EntitySilkmoth(World worldIn) {
		super(worldIn);
		this.setSize(0.3F, 0.3F);
		this.moveForward = 0.5F;
		this.clockwise = this.rand.nextBoolean();
		this.distance = 0.5F + 2.0F * this.rand.nextFloat();
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.rotationPos = new BlockPos(compound.getInteger("SilkmothRotationX"), compound.getInteger("SilkmothRotationY"), compound.getInteger("SilkmothRotationZ"));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("SilkmothRotationX", this.rotationPos.getX());
		compound.setInteger("SilkmothRotationY", this.rotationPos.getY());
		compound.setInteger("SilkmothRotationZ", this.rotationPos.getZ());
		super.writeEntityToNBT(compound);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(!world.isRemote){
			if(this.ticksExisted >= 24000){
				if(!this.hasCustomName()) this.setDead();
			}

			if(this.ticksExisted < 5){
				if(this.rotationPos.getX() == 0 && this.rotationPos.getY() == 0 && this.rotationPos.getZ() == 0){
					this.rotationPos = new BlockPos(Math.floor(this.posX), Math.floor(this.posY), Math.floor(this.posZ));
				}
			}else if(this.rand.nextInt(1000) == 0) this.changeRotationPos();

			double x = this.posX - (this.rotationPos.getX() + 0.5D);
			double z = this.posZ - (this.rotationPos.getZ() + 0.5D);
			double alpha;

			if(z == 0) alpha = (x > 0) ? 0 : Math.PI;
			else{
				alpha = Math.atan(z / x);
				if(x > 0) alpha += Math.PI;
			}

			double d = Math.sqrt(x * x + z * z);
			d = - this.distance * 2 / (d + this.distance) + 1;

			alpha += (this.clockwise ? d - 1 : 1 - d) * Math.PI / 2;

			this.motionX = this.motionX * 0.5D + Math.cos(alpha) * 0.15D;
			this.motionY = Math.sin(this.ticksExisted / 20.0D) * 0.05D;
			this.motionZ = this.motionZ * 0.5D + Math.sin(alpha) * 0.15D;

			this.rotationYaw = (float) MathHelper.wrapDegrees(180.0D * alpha / Math.PI - 90.0D);
			this.rotationYawHead = 0.0F;
			this.rotationPitch = (float) -this.motionY * 5 * 90;
		}
	}

	private void changeRotationPos(){
		int x = (int) world.getWorldTime() % 23999;
		boolean isNight = x > 12000 && x < 23000;

		x = (int) this.posX - 5;
		int y = (int) this.posY - 1;
		int z = (int) this.posZ - 5;
		List<BlockPos> listMulberry = new ArrayList<>();
		List<BlockPos> listLight = new ArrayList<>();
		IBlockState state;

		for(int i = 0; i < 11; i++){
			for(int j = 0; j < 11; j++){
				for(int k = 0; k < 3; k++){
					BlockPos pos = new BlockPos(x + i, y + k, z + j);
					state = this.world.getBlockState(pos);
					if(state.getBlock() instanceof BlockMulberry){
						if(!((BlockMulberry) state.getBlock()).isBottomCrop(world, pos)) listMulberry.add(pos);
					}else if(isNight){
						if (state.getLightValue(this.world, pos) >= 14) listLight.add(pos);
					}
				}
			}
		}

		if(!listLight.isEmpty()){
			this.rotationPos = listLight.get(rand.nextInt(listLight.size()));
		}else if(!listMulberry.isEmpty()){
			this.rotationPos = listMulberry.get(rand.nextInt(listMulberry.size()));
		}else this.rotationPos = new BlockPos(x + this.rand.nextInt(11), y + this.rand.nextInt(3), z + this.rand.nextInt(11));

		this.distance = 0.5F + 2 * this.rand.nextFloat();
	}

	@Override
	protected boolean canTriggerWalking(){
		return false;
	}

	@Override
	public void fall(float distance, float damageMultiplier){}

	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos){}

	@Override
	public boolean doesEntityNotTriggerPressurePlate(){
		return true;
	}

	public float getEyeHeight(){
		return this.height / 2.0F;
	}

	@Nullable
	@Override
	protected ResourceLocation getLootTable(){
		return DoTBEntitiesRegistry.LT_SILKMOTH;
	}

	@Override
	public boolean isAIDisabled() {
		return false;
	}

	@Override
	protected void applyEntityAttributes(){
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(3.0D);
	}

	@Override
	protected float getSoundVolume() {
		return 0.1F;
	}

	@Override
	protected float getSoundPitch() {
		return super.getSoundPitch() * 0.95F;
	}

	@Nullable
	@Override
	public SoundEvent getAmbientSound() {
		return this.rand.nextInt(4) != 0 ? null : SoundEvents.ENTITY_RABBIT_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_RABBIT_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_RABBIT_DEATH;
	}
}