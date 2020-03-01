package org.dawnoftimebuilder.entities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IEntityLivingData;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import org.dawnoftimebuilder.registries.DoTBEntitiesRegistry;

import javax.annotation.Nullable;
import java.util.Random;

import static net.minecraft.block.Block.NULL_AABB;

public class EntityJapaneseDragon extends EntityCreature {

	private static final DataParameter<Float> SIZE = EntityDataManager.createKey(EntityJapaneseDragon.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> ANGLE = EntityDataManager.createKey(EntityJapaneseDragon.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> ANIM_DURATION = EntityDataManager.createKey(EntityJapaneseDragon.class, DataSerializers.FLOAT);
	private float previousTickAge = 0.0F;
	private float animationLoop = 0.0F;

	public EntityJapaneseDragon(World worldIn) {
		super(worldIn);
		this.moveHelper = new JapaneseDragonMoveHelper(this);
	}

	@Override
	public float getEyeHeight() {
		return this.height * 0.6F;
	}

	@Nullable
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData data) {

		float size = (float) this.rand.nextGaussian();
		if(size < 0) size = -1 / (size - 1);
		else size = (float) (1.0D + Math.min(size * 0.5D, 15.0D));

		this.setDragonSize(size);
		this.experienceValue = (int) Math.ceil(100 * size);
		this.setAnimDuration(10.0F);

		return super.onInitialSpawn(difficulty, data);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
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
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		float size = compound.getFloat("JapaneseDragonSize");
		if(size > 0.0F){
			this.setDragonSize(size);
			this.setAnimDuration(10.0F);
			this.experienceValue = (int) Math.ceil(100 * size);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		compound.setFloat("JapaneseDragonSize", this.getDragonSize());
		super.writeEntityToNBT(compound);
	}

	@Override
	protected void initEntityAI(){
		//this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(3, new AIVerticalFlyUp(this));
		this.tasks.addTask(3, new AIVerticalFlyDown(this));
		this.tasks.addTask(4, new AILongFly(this));
		this.tasks.addTask(5, new AIWanderFly(this));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 20.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		//I know it's overkill, but I have no idea what method to call to set the size on client side AFTER readEntityFromNBT was called...
		if(this.ticksExisted < 5){
			float size = this.getDragonSize();
			this.setSize(size* 1.1F, size * 1.1F);
		}
	}

	@Override
	public void fall(float distance, float damageMultiplier){}

	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos){}

	@Override
	public boolean hasNoGravity() {
		return true;
	}

	@Override
	protected boolean canDespawn() {
		return false;
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
		return SoundEvents.ENTITY_ENDERDRAGON_GROWL;
	}

	static class AIVerticalFlyUp extends EntityAIBase{
		private final EntityJapaneseDragon parentEntity;

		AIVerticalFlyUp(EntityJapaneseDragon dragon){
			this.parentEntity = dragon;
			this.setMutexBits(1);
		}

		@Override
		public boolean shouldExecute(){
			if(this.parentEntity.getLeashed()) return false;
			if(this.parentEntity.getIdleTime() >= 100) return false;
			BlockPos pos = new BlockPos(this.parentEntity.posX, this.parentEntity.posY, this.parentEntity.posZ);
			if(!this.parentEntity.world.canSeeSky(pos)) return false;
			if(this.parentEntity.getRNG().nextInt(300) != 0) return false;
			if(this.parentEntity.getMoveHelper().action == EntityMoveHelper.Action.WAIT && this.parentEntity.getRNG().nextInt(1000) == 0) return true;
			return this.parentEntity.posY < 100.0D;
		}

		@Override
		public boolean shouldContinueExecuting(){
			return false;
		}

		@Override
		public void startExecuting(){
			this.parentEntity.getMoveHelper().setMoveTo(this.parentEntity.posX, 100.0D + 30.0D * this.parentEntity.getRNG().nextDouble(), this.parentEntity.posZ, this.parentEntity.getSpeed() * 1.2D);
			this.parentEntity.setAnimDuration(8.0F);
		}
	}

	static class AIVerticalFlyDown extends EntityAIBase{
		private final EntityJapaneseDragon parentEntity;
		private int goalY = 100;

		AIVerticalFlyDown(EntityJapaneseDragon dragon){
			this.parentEntity = dragon;
			this.setMutexBits(1);
		}

		@Override
		public boolean shouldExecute(){
			if(this.parentEntity.getLeashed()) return false;
			if(this.parentEntity.posY < 100.0D) return false;
			if(this.parentEntity.getRNG().nextInt(100) != 0) return false;

			BlockPos pos = new BlockPos(this.parentEntity.posX, 90.0D, this.parentEntity.posZ);
			World world = this.parentEntity.world;
			if(world.getBlockState(pos).getCollisionBoundingBox(world, pos) == NULL_AABB && !world.getBlockState(pos).getMaterial().isLiquid()) return false;
			int newY = world.getChunk(new BlockPos(this.parentEntity.posX, this.parentEntity.posY, this.parentEntity.posZ)).getTopFilledSegment();
			for(int y = newY + 16; y >= newY; y--){
				pos = new BlockPos(this.parentEntity.posX, y, this.parentEntity.posZ);
				if(world.getBlockState(pos).getCollisionBoundingBox(world, pos) != NULL_AABB || world.getBlockState(pos).getMaterial().isLiquid()){
					break;
				}
			}
			if(newY + 11 >= this.parentEntity.posY) return false;
			this.goalY = newY + 1;
			return true;
		}

		@Override
		public boolean shouldContinueExecuting(){
			return false;
		}

		@Override
		public void startExecuting(){
			this.parentEntity.getMoveHelper().setMoveTo(this.parentEntity.posX, this.goalY + this.parentEntity.getDragonSize(), this.parentEntity.posZ, this.parentEntity.getSpeed() * 1.2D);
			this.parentEntity.setAnimDuration(8.0F);
		}
	}

	static class AIWanderFly extends EntityAIBase{
		private final EntityJapaneseDragon parentEntity;

		AIWanderFly(EntityJapaneseDragon dragon){
			this.parentEntity = dragon;
			this.setMutexBits(1);
		}

		@Override
		public boolean shouldExecute(){
			if(this.parentEntity.getLeashed()) return false;
			if (this.parentEntity.getIdleTime() >= 100) return false;
			if (this.parentEntity.getRNG().nextInt(60) != 0) return false;

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

		@Override
		public boolean shouldContinueExecuting(){
			return false;
		}

		@Override
		public void startExecuting(){
			Random random = this.parentEntity.getRNG();
			double newX = this.parentEntity.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double newZ = this.parentEntity.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);

			World world = this.parentEntity.world;
			BlockPos pos;
			for(double newY = this.parentEntity.posY - 16.0D; newY <= this.parentEntity.posY + 16.0D; newY++){
				pos = new BlockPos(newX, newY, newZ);
				if(world.getBlockState(pos).getCollisionBoundingBox(world, pos) == NULL_AABB && !world.getBlockState(pos).getMaterial().isLiquid()){
					if(world.rayTraceBlocks(new Vec3d(this.parentEntity.posX, this.parentEntity.posY, this.parentEntity.posZ), new Vec3d(newX, newY + this.parentEntity.getDragonSize(), newZ), true, true, false) == null){
						this.parentEntity.getMoveHelper().setMoveTo(newX, newY + this.parentEntity.getDragonSize(), newZ, this.parentEntity.getSpeed());
						return;
					}
				}
			}
			this.parentEntity.getMoveHelper().setMoveTo(newX, this.parentEntity.posY, newZ, this.parentEntity.getSpeed());
			this.parentEntity.setAnimDuration(10.0F);
		}
	}

	static class AILongFly extends EntityAIBase{
		private final EntityJapaneseDragon parentEntity;

		AILongFly(EntityJapaneseDragon dragon){
			this.parentEntity = dragon;
			this.setMutexBits(1);
		}

		@Override
		public boolean shouldExecute(){
			if(this.parentEntity.getLeashed()) return false;
			if(this.parentEntity.posY < 100.0D) return false;
			if(this.parentEntity.getMoveHelper().action != EntityMoveHelper.Action.WAIT) return false;

			BlockPos pos = new BlockPos(this.parentEntity.posX, this.parentEntity.posY, this.parentEntity.posZ);
			return this.parentEntity.world.canSeeSky(pos);
		}

		@Override
		public boolean shouldContinueExecuting(){
			return false;
		}

		@Override
		public void startExecuting(){
			Random random = this.parentEntity.getRNG();
			double newX = this.parentEntity.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 500.0F);
			double newZ = this.parentEntity.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 500.0F);

			this.parentEntity.getMoveHelper().setMoveTo(newX, this.parentEntity.posY, newZ, this.parentEntity.getSpeed() * 2.5F);
			this.parentEntity.setAnimDuration(5.0F);
		}
	}

	static class JapaneseDragonMoveHelper extends EntityMoveHelper {
		private final EntityJapaneseDragon parentEntity;

		JapaneseDragonMoveHelper(EntityJapaneseDragon dragon){
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
}
