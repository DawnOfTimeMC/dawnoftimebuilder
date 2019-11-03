package org.dawnoftimebuilder.entities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
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

	private static final DataParameter<BlockPos> SYNC_ROTATION_POS = EntityDataManager.createKey(EntitySilkmoth.class, DataSerializers.BLOCK_POS);
	private static final DataParameter<Float> SYNC_ROTATION_DIST = EntityDataManager.createKey(EntitySilkmoth.class, DataSerializers.FLOAT);
	private static final DataParameter<Boolean> SYNC_CLOCKWISE = EntityDataManager.createKey(EntitySilkmoth.class, DataSerializers.BOOLEAN);

	public EntitySilkmoth(World worldIn) {
		super(worldIn);

		this.setSize(0.3F, 0.3F);
		this.moveForward = 0.5F;
		dataManager.set(SYNC_CLOCKWISE, this.rand.nextBoolean());
		dataManager.set(SYNC_ROTATION_DIST, 0.5F + 2.0F * this.rand.nextFloat());
	}

	@Override
	protected void entityInit(){
		super.entityInit();
		dataManager.register(SYNC_ROTATION_POS, new BlockPos(0,0,0));
		dataManager.register(SYNC_ROTATION_DIST, 1.0F);
		dataManager.register(SYNC_CLOCKWISE, true);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		dataManager.set(SYNC_ROTATION_POS, new BlockPos(compound.getInteger("SilkmothRotationX"), compound.getInteger("SilkmothRotationY"), compound.getInteger("SilkmothRotationZ")));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		BlockPos pos = dataManager.get(SYNC_ROTATION_POS);
		compound.setInteger("SilkmothRotationX", pos.getX());
		compound.setInteger("SilkmothRotationY", pos.getY());
		compound.setInteger("SilkmothRotationZ", pos.getZ());
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(!world.isRemote){

			BlockPos pos = dataManager.get(SYNC_ROTATION_POS);

			if(this.ticksExisted < 10){
				if(pos.getX() == 0  && pos.getY() == 0 && pos.getZ() == 0){
					dataManager.set(SYNC_ROTATION_POS, new BlockPos(this.posX, this.posY, this.posZ));
				}
			}

			if(this.ticksExisted >= 24000){
				if(!this.hasCustomName()) this.setDead();
			}

			if(this.rand.nextInt(1000) == 0) this.changeRotationPos();

			double x = this.posX - (pos.getX() + 0.5D);
			double z = this.posZ - (pos.getZ() + 0.5D);
			double alpha;

			if(z == 0) alpha = (x > 0) ? 0 : Math.PI;
			else{
				alpha = Math.atan(z / x);
				if(x > 0) alpha += Math.PI;
			}

			float rotationDist = dataManager.get(SYNC_ROTATION_DIST);
			double d = Math.sqrt(x * x + z * z);
			d = - rotationDist * 2 / (d + rotationDist) + 1;

			alpha += (dataManager.get(SYNC_CLOCKWISE) ? d - 1 : 1 - d) * Math.PI / 2;

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
			dataManager.set(SYNC_ROTATION_POS, listLight.get(rand.nextInt(listLight.size())));
		}else if(!listMulberry.isEmpty()){
			dataManager.set(SYNC_ROTATION_POS, listMulberry.get(rand.nextInt(listMulberry.size())));
		}else dataManager.set(SYNC_ROTATION_POS, new BlockPos(x + this.rand.nextInt(11), y + this.rand.nextInt(3), z + this.rand.nextInt(11)));

		dataManager.set(SYNC_ROTATION_DIST, 0.5F + 2.0F * this.rand.nextFloat());
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

	@Override
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
		return true;
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
