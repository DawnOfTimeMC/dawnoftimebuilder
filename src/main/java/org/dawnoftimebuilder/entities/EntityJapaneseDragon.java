package org.dawnoftimebuilder.entities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.dawnoftimebuilder.registries.DoTBEntitiesRegistry;

import javax.annotation.Nullable;
import java.util.Random;

import static net.minecraft.block.Block.NULL_AABB;

public class EntityJapaneseDragon extends EntityCreature {

	private static final DataParameter<Float> SYNC_SIZE = EntityDataManager.createKey(EntityJapaneseDragon.class, DataSerializers.FLOAT);
	private double speed;

	public EntityJapaneseDragon(World worldIn) {
		super(worldIn);

		float dragonSize = (float) this.rand.nextGaussian();
		if(dragonSize < 0) dragonSize = -1 / (dragonSize - 1);
		else dragonSize = (float) (1.0D + Math.min(dragonSize * 0.5D, 15.0D));

		dataManager.set(SYNC_SIZE, dragonSize);
		this.speed = 0.4D + 0.2D * this.getDragonSize();
		this.setSize(1.5F * dragonSize, 1.5F * dragonSize);
		this.experienceValue = (int) Math.ceil(100 * dragonSize);
		this.moveHelper = new JapaneseDragonMoveHelper(this);
	}

	@Override
	protected void entityInit(){
		super.entityInit();
		dataManager.register(SYNC_SIZE, 1.0F);
	}

	@Override
	protected void initEntityAI(){
		this.tasks.addTask(1, new EntityAISwimming(this));
		//this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(5, new EntityJapaneseDragon.AIWanderFly(this));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 20.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
	}

	public float getDragonSize(){
		return dataManager.get(SYNC_SIZE);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		float size = compound.getFloat("JapaneseDragonSize");
		dataManager.set(SYNC_SIZE, (size > 0) ? size : 1.0F);
		this.speed = 0.8D + 0.2D * size;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setFloat("JapaneseDragonSize", this.getDragonSize());
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(!world.isRemote){
			/*
			if(z == 0) alpha = (x > 0) ? 0 : Math.PI;
			else{
				alpha = Math.atan(z / x);
				if(x > 0) alpha += Math.PI;
			}

			this.motionX = this.motionX * 0.5D + Math.cos(alpha) * 0.15D;
			this.motionY = Math.sin(this.ticksExisted / 20.0D) * 0.05D;
			this.motionZ = this.motionZ * 0.5D + Math.sin(alpha) * 0.15D;
			*/
		}
	}

	private void changeRotationPos(){
		/*
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

		this.distance = 0.5D + 2 * this.rand.nextDouble();
		*/
	}

	@Override
	public void fall(float distance, float damageMultiplier){}

	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos){}

	@Override
	public boolean hasNoGravity() {
		return true;
	}

	@Nullable
	@Override
	protected ResourceLocation getLootTable(){
		return DoTBEntitiesRegistry.LT_SILKMOTH;
	}

	@Override
	protected void applyEntityAttributes(){
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D * this.getDragonSize());
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D + 2.0D * this.getDragonSize());
	}

	@Override
	protected float getSoundVolume() {
		return this.getDragonSize();
	}

	@Override
	protected float getSoundPitch() {
		return super.getSoundPitch() * this.getDragonSize();
	}

	@Nullable
	@Override
	public SoundEvent getAmbientSound() {
		return this.rand.nextInt(4) != 0 ? null : SoundEvents.ENTITY_ENDERDRAGON_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_ENDERDRAGON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_ENDERDRAGON_DEATH;
	}

	static class AIWanderFly extends EntityAIBase{
		private final EntityJapaneseDragon parentEntity;

		AIWanderFly(EntityJapaneseDragon dragon){
			this.parentEntity = dragon;
			this.setMutexBits(1);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute(){
			if (this.parentEntity.getIdleTime() >= 100){
				return false;
			}

			if (this.parentEntity.getRNG().nextInt(60) != 0){
				return false;
			}

			EntityMoveHelper entitymovehelper = this.parentEntity.getMoveHelper();

			if (!entitymovehelper.isUpdating()) return true;
			else{
				double distanceX = entitymovehelper.getX() - this.parentEntity.posX;
				double distanceY = entitymovehelper.getY() - this.parentEntity.posY;
				double distanceZ = entitymovehelper.getZ() - this.parentEntity.posZ;
				double diagonalSquare = distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ;
				return diagonalSquare < 1.0D;
			}
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		public boolean shouldContinueExecuting()
		{
			return false;
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void startExecuting(){
			Random random = this.parentEntity.getRNG();
			double newX = this.parentEntity.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double newZ = this.parentEntity.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);

			World world = this.parentEntity.world;
			BlockPos pos;
			for(double newY = this.parentEntity.posY - 16.0D; newY <= this.parentEntity.posY + 16.0D; newY++){
				pos = new BlockPos(newX, newY, newZ);
				if(world.getBlockState(pos).getCollisionBoundingBox(world, pos) == NULL_AABB && !world.getBlockState(pos).getMaterial().isLiquid()){
					this.parentEntity.getMoveHelper().setMoveTo(newX, newY + 1.0 + this.parentEntity.getDragonSize(), newZ, this.parentEntity.speed);
					return;
				}
			}
			this.parentEntity.getMoveHelper().setMoveTo(newX, this.parentEntity.posY, newZ, this.parentEntity.speed);
		}
	}

	static class JapaneseDragonMoveHelper extends EntityMoveHelper {
		private final EntityJapaneseDragon parentEntity;

		JapaneseDragonMoveHelper(EntityJapaneseDragon dragon){
			super(dragon);
			this.parentEntity = dragon;
		}

		public void onUpdateMoveHelper(){
			if(this.action == EntityMoveHelper.Action.MOVE_TO){

				double distanceX = this.posX - this.parentEntity.posX;
				double distanceY = this.posY - this.parentEntity.posY;
				double distanceZ = this.posZ - this.parentEntity.posZ;
				double distance = distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ;

				if (distance < 1.0D) {
					this.action = EntityMoveHelper.Action.WAIT;
					this.entity.setMoveForward(0.0F);
					return;
				}

				float rotation = (float)(MathHelper.atan2(distanceZ, distanceX) * (180D / Math.PI)) - 90.0F;
				this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, rotation, 90.0F);
				this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
				this.parentEntity.motionY = (distanceY / distance) * this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();

				if (!this.isNotColliding(this.posX, this.posY, this.posZ, (double) MathHelper.sqrt(distance))) {
					this.action = EntityMoveHelper.Action.WAIT;
				}
			}
		}

		/**
		 * Checks if entity bounding box is not colliding with terrain
		 */
		private boolean isNotColliding(double x, double y, double z, double distance){
			double d0 = (x - this.parentEntity.posX) / distance;
			double d1 = (y - this.parentEntity.posY) / distance;
			double d2 = (z - this.parentEntity.posZ) / distance;
			AxisAlignedBB axisalignedbb = this.parentEntity.getEntityBoundingBox();

			for (int i = 1; (double)i < distance; ++i){
				axisalignedbb = axisalignedbb.offset(d0, d1, d2);

				if (!this.parentEntity.world.getCollisionBoxes(this.parentEntity, axisalignedbb).isEmpty()){
					return false;
				}
			}
			return true;
		}
	}
}
