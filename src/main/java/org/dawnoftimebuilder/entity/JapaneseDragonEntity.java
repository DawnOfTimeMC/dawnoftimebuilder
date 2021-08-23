package org.dawnoftimebuilder.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.dawnoftimebuilder.utils.DoTBConfig;

import javax.annotation.Nullable;

import java.util.Random;

import static org.dawnoftimebuilder.registries.DoTBEntitiesRegistry.JAPANESE_DRAGON_ENTITY;

public class JapaneseDragonEntity extends CreatureEntity {

	private static final DataParameter<Float> SIZE = EntityDataManager.createKey(JapaneseDragonEntity.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> ANGLE = EntityDataManager.createKey(JapaneseDragonEntity.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> ANIM_DURATION = EntityDataManager.createKey(JapaneseDragonEntity.class, DataSerializers.FLOAT);
	private float previousTickAge = 0.0F;
	private float animationLoop = 0.0F;

	public JapaneseDragonEntity(World worldIn) {
		super(JAPANESE_DRAGON_ENTITY, worldIn);
	}

	@Nullable
	public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		float size = (float) this.rand.nextGaussian();
		if(size < 0) size = -1 / (size - 1);
		else size = (float) (1.0D + Math.min(size * 0.5D, 15.0D));

		this.setDragonSize(size);
		this.experienceValue = (int) Math.ceil(100 * size);
		this.setAnimDuration(10.0F);
		return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	@Override
	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(DoTBConfig.JAPANESE_DRAGON_HEALTH.get() * this.getDragonSize());
		this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(DoTBConfig.JAPANESE_DRAGON_ATTACK.get() + 2.0D * this.getDragonSize());
	}

	@Override
	public void tick() {
		super.tick();
		this.idleTime++;
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return sizeIn.height * 0.6F;
	}

	@Override
	protected boolean canTriggerWalking(){
		return false;
	}

	@Override
	public void fall(float distance, float damageMultiplier){}

	@Override
	protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {}

	@Override
	public boolean doesEntityNotTriggerPressurePlate() {
		return true;
	}

	@Override
	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	protected float getSoundVolume() {
		return this.getDragonSize();
	}

	@Override
	protected float getSoundPitch() {
		return super.getSoundPitch() * this.getDragonSize();
	}

	@Override
	public boolean hasNoGravity() {
		return true;
	}

	@Nullable
	@Override
	public SoundEvent getAmbientSound(){
		return this.rand.nextInt(4) != 0 ? null : SoundEvents.ENTITY_ENDER_DRAGON_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_ENDER_DRAGON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_ENDER_DRAGON_GROWL;
	}

	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(SIZE, 1.0F);
		this.dataManager.register(ANGLE, 0.0F);
		this.dataManager.register(ANIM_DURATION, 50.0F);
	}

	private void setDragonSize(float size){
		this.dataManager.set(SIZE, size);
	}

	public float getDragonSize(){
		return this.dataManager.get(SIZE);
	}

	private float getSpeed(){
		return 0.4F + this.getDragonSize();
	}

	private void setHeadTargetAngle(float angle){
		this.dataManager.set(ANGLE, angle / 2 + this.getHeadTargetAngle() / 2);
	}

	public float getHeadTargetAngle(){
		return this.dataManager.get(ANGLE);
	}

	/**
	 * @param factor is multiplied with 5 + dragon's size. A value of ten give an average speed of animation
	 */
	private void setAnimDuration(float factor){
		this.dataManager.set(ANIM_DURATION, factor * (5.0F + this.getDragonSize()));
	}

	private float getAnimDuration(){
		return this.dataManager.get(ANIM_DURATION);
	}

	public float getAnimationLoop(float tickAge) {
		float f = (tickAge - this.previousTickAge) / this.getAnimDuration();
		this.previousTickAge = tickAge;
		this.animationLoop = (this.animationLoop + f) % 2.0F;
		return this.animationLoop;
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		float size = compound.getFloat("JapaneseDragonSize");
		if(size > 0.0F){
			this.setDragonSize(size);
			this.setAnimDuration(10.0F);
			this.experienceValue = (int) Math.ceil(100 * size);
		}
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		compound.putFloat("JapaneseDragonSize", this.getDragonSize());
		super.writeAdditional(compound);
	}

	@Override
	protected void registerGoals() {
		//this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		this.goalSelector.addGoal(3, new FlyUpGoal(this));
		this.goalSelector.addGoal(3, new FlyDownGoal(this));
		this.goalSelector.addGoal(4, new LongFlyGoal(this));
		this.goalSelector.addGoal(5, new WanderFlyGoal(this));
		this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
	}

	public static class FlyUpGoal extends Goal {
		protected final JapaneseDragonEntity dragon;

		public FlyUpGoal(JapaneseDragonEntity entityIn) {
			this.dragon = entityIn;
		}

		@Override
		public boolean shouldExecute() {
			if(this.dragon.getIdleTime() < 100) return false;
			if(this.dragon.posY > 100.0D) return false;
			if(this.dragon.getRNG().nextInt(200) != 0) return false;
			BlockPos pos = new BlockPos(this.dragon.posX, this.dragon.posY, this.dragon.posZ);
			return this.dragon.world.canBlockSeeSky(pos);
		}

		@Override
		public boolean shouldContinueExecuting(){
			return false;
		}

		@Override
		public void startExecuting(){
			this.dragon.setIdleTime(0);
			this.dragon.getMoveHelper().setMoveTo(this.dragon.posX, 100.0D + 30.0D * this.dragon.getRNG().nextDouble(), this.dragon.posZ, this.dragon.getSpeed() * 1.2D);
			this.dragon.setAnimDuration(8.0F);
		}
	}

	static class FlyDownGoal extends Goal {
		private final JapaneseDragonEntity dragon;
		private int goalY = 100;

		FlyDownGoal(JapaneseDragonEntity dragon){
			this.dragon = dragon;
		}

		@Override
		public boolean shouldExecute(){
			if(this.dragon.posY < 100.0D) return false;
			if(this.dragon.getRNG().nextInt(100) != 0) return false;

			BlockPos pos = new BlockPos(this.dragon.posX, 90.0D, this.dragon.posZ);
			World world = this.dragon.world;
			if(world.getBlockState(pos).getCollisionShape(world, pos) == VoxelShapes.empty() && !world.getBlockState(pos).getMaterial().isLiquid()) return false;
			int newY = world.getChunk(new BlockPos(this.dragon.posX, this.dragon.posY, this.dragon.posZ)).getTopFilledSegment();
			for(int y = newY + 16; y >= newY; y--){
				pos = new BlockPos(this.dragon.posX, y, this.dragon.posZ);
				if(world.getBlockState(pos).getCollisionShape(world, pos) != VoxelShapes.empty() || world.getBlockState(pos).getMaterial().isLiquid()){
					break;
				}
			}
			if(newY + 11 >= this.dragon.posY) return false;
			this.goalY = newY + 1;
			return true;
		}

		@Override
		public boolean shouldContinueExecuting(){
			return false;
		}

		@Override
		public void startExecuting(){
			this.dragon.setIdleTime(0);
			this.dragon.getMoveHelper().setMoveTo(this.dragon.posX, this.goalY + this.dragon.getDragonSize(), this.dragon.posZ, this.dragon.getSpeed() * 1.2D);
			this.dragon.setAnimDuration(8.0F);
		}
	}

	static class WanderFlyGoal extends Goal {
		private final JapaneseDragonEntity dragon;

		WanderFlyGoal(JapaneseDragonEntity dragon){
			this.dragon = dragon;
		}

		@Override
		public boolean shouldExecute(){
			if(this.dragon.posY > 100.0D) return false;
			if(this.dragon.getRNG().nextInt(50) != 0) return false;

			MovementController controller = this.dragon.getMoveHelper();
			if (!controller.isUpdating()) return true;
			else{
				double distanceX = controller.getX() - this.dragon.posX;
				double distanceY = controller.getY() - this.dragon.posY;
				double distanceZ = controller.getZ() - this.dragon.posZ;
				double diagonalSquare = distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ;
				return diagonalSquare < 1.0D;
			}
		}

		@Override
		public boolean shouldContinueExecuting(){
			return false;
		}

		@Override
		public void startExecuting(){
			Random random = this.dragon.getRNG();
			double newX = this.dragon.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double newZ = this.dragon.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);

			World world = this.dragon.world;
			BlockPos pos;
			this.dragon.setAnimDuration(10.0F);
			for(double newY = this.dragon.posY - 16.0D; newY <= this.dragon.posY + 16.0D; newY++){
				pos = new BlockPos(newX, newY, newZ);
				if(world.getBlockState(pos).getCollisionShape(world, pos) == VoxelShapes.empty() && !world.getBlockState(pos).getMaterial().isLiquid()){
					this.dragon.getMoveHelper().setMoveTo(newX, newY + this.dragon.getDragonSize(), newZ, this.dragon.getSpeed());
					return;
				}
			}
			this.dragon.getMoveHelper().setMoveTo(newX, this.dragon.posY, newZ, this.dragon.getSpeed());
		}
	}

	static class LongFlyGoal extends Goal {
		private final JapaneseDragonEntity dragon;

		LongFlyGoal(JapaneseDragonEntity dragon){
			this.dragon = dragon;
		}

		@Override
		public boolean shouldExecute(){
			if(this.dragon.posY < 100.0D) return false;
			BlockPos pos = new BlockPos(this.dragon.posX, this.dragon.posY, this.dragon.posZ);
			return this.dragon.world.canBlockSeeSky(pos);
		}

		@Override
		public boolean shouldContinueExecuting(){
			return false;
		}

		@Override
		public void startExecuting(){
			this.dragon.setIdleTime(0);
			Random random = this.dragon.getRNG();
			double newX = this.dragon.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 500.0F);
			double newZ = this.dragon.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 500.0F);

			this.dragon.getMoveHelper().setMoveTo(newX, this.dragon.posY, newZ, this.dragon.getSpeed() * 2.5F);
			this.dragon.setAnimDuration(5.0F);
		}
	}
	/*
	static class JapaneseDragonMoveHelper extends EntityMoveHelper {
		private final JapaneseDragonEntity parentEntity;

		JapaneseDragonMoveHelper(JapaneseDragonEntity dragon){
			super(dragon);
			this.parentEntity = dragon;
		}

		@Override
		public void onUpdateMoveHelper(){
			if(this.action == EntityMoveHelper.Action.MOVE_TO){

				double distanceX = this.posX - this.parentEntity.posX;
				double distanceY = this.posY - this.parentEntity.posY;
				double distanceZ = this.posZ - this.parentEntity.posZ;
				double distance = distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ;
				if (distance < 1.0D) {
					this.stopMovement();
					return;
				}
				distance = MathHelper.sqrt(distance);

				float rotation = (float)(MathHelper.atan2(distanceZ, distanceX) * (180D / Math.PI)) - 90.0F;
				this.parentEntity.rotationYaw = this.limitAngle(this.parentEntity.rotationYaw, rotation, 90.0F);

				this.parentEntity.motionX = 0.2F * MathHelper.clamp(distanceX / distance, -1.0F, 1.0F) * this.speed * this.parentEntity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
				this.parentEntity.motionY = 0.2F * MathHelper.clamp(distanceY / distance, -1.0F, 1.0F) * this.speed * this.parentEntity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
				this.parentEntity.motionZ = 0.2F * MathHelper.clamp(distanceZ / distance, -1.0F, 1.0F) * this.speed * this.parentEntity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();

				double d = Math.asin(MathHelper.clamp(distanceY/ distance, -1.0D, 1.0D));
				d = d * (1.0F - 1.0F / ((distance + 1.0F) / 2));
				this.parentEntity.setHeadTargetAngle((float) d);
				if (!this.isNotColliding(this.posX, this.posY, this.posZ, distance)) {
					this.stopMovement();
				}
			}
		}

		private void stopMovement(){
			this.action = EntityMoveHelper.Action.WAIT;
			this.parentEntity.setMoveForward(0.0F);
			this.parentEntity.setMoveVertical(0.0F);
			this.parentEntity.setAnimDuration(14.0F);
		}

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
	 */
}
