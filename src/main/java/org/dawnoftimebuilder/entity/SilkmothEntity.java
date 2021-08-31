package org.dawnoftimebuilder.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.AmbientEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.templates.DoubleGrowingBushBlock;
import org.dawnoftimebuilder.util.DoTBConfig;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static org.dawnoftimebuilder.registry.DoTBBlocksRegistry.MULBERRY;
import static org.dawnoftimebuilder.registry.DoTBEntitiesRegistry.SILKMOTH_ENTITY;

public class SilkmothEntity extends AmbientEntity {

	private static final DataParameter<BlockPos> ROTATION_POS = EntityDataManager.createKey(SilkmothEntity.class, DataSerializers.BLOCK_POS);
	private static final DataParameter<Boolean> CLOCKWISE = EntityDataManager.createKey(SilkmothEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Float> DISTANCE = EntityDataManager.createKey(SilkmothEntity.class, DataSerializers.FLOAT);

	public SilkmothEntity(World worldIn) {
		super(SILKMOTH_ENTITY, worldIn);
	}

	@Nullable
	public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		this.dataManager.set(ROTATION_POS, new BlockPos(this));
		this.dataManager.set(CLOCKWISE, this.rand.nextBoolean());
		this.dataManager.set(DISTANCE, this.getNewRotationDistance());
		return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	public static void createEntity(World world) {
		if(!world.isRemote()) {
			world.addEntity(new SilkmothEntity(world));
		}
	}

	private float getNewRotationDistance(){
		return 0.5F + DoTBConfig.SILKMOTH_ROTATION_MAX_RANGE.get() * this.rand.nextFloat();
	}

	@Override
	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(DoTBConfig.SILKMOTH_HEALTH.get());
	}

	@Override
	public void tick() {
		super.tick();
		this.setMotion(this.getMotion());
	}

	protected void updateAITasks() {
		super.updateAITasks();

		if(this.ticksExisted >= 24000){
			//The silkmoth dies from oldness.
			if(!this.hasCustomName() && DoTBConfig.SILKMOTH_MUST_DIE.get()) this.attackEntityFrom(DamageSource.STARVE, 20.0F);
		}

		if(this.rand.nextInt(DoTBConfig.SILKMOTH_ROTATION_CHANGE.get()) == 0){
			//Randomly changes the rotation pos.
			this.changeRotationPos();
		}

		BlockPos pos = this.dataManager.get(ROTATION_POS);
		double distance = this.dataManager.get(DISTANCE);
		double x = this.posX - (pos.getX() + 0.5D);
		double z = this.posZ - (pos.getZ() + 0.5D);

		double alpha;
		if(z == 0) alpha = (x > 0) ? 0 : Math.PI;
		else{
			alpha = Math.atan(z / x);
			if(x > 0) alpha += Math.PI;
		}
		double d = Math.sqrt(x * x + z * z);
		d = - distance * 2 / (d + distance) + 1;
		alpha += (this.dataManager.get(CLOCKWISE) ? d - 1 : 1 - d) * Math.PI / 2;

		Vec3d motionVector = this.getMotion();
		this.setMotion(motionVector.x * 0.5D + Math.cos(alpha) * 0.15D, Math.sin(this.ticksExisted / 20.0D) * 0.05D, motionVector.z * 0.5D + Math.sin(alpha) * 0.15D);

		this.rotationYaw = (float) MathHelper.wrapDegrees(180.0D * alpha / Math.PI - 90.0D);
	}

	private void changeRotationPos(){
		int horizontalRange = 5;
		int verticalRange = 2;

		int x = (int) this.world.getDayTime() % 23999;
		boolean isNight = x > 12000 && x < 23000;
		x = (int) Math.floor(this.posX) - horizontalRange;
		int y = (int) Math.floor(this.posY) - verticalRange;
		int z = (int) Math.floor(this.posZ) - horizontalRange;

		List<BlockPos> listMulberry = new ArrayList<>();
		List<BlockPos> listLight = new ArrayList<>();
		BlockState state;

		for(int searchX = 0; searchX < 2 * horizontalRange + 1; searchX++){
			for(int searchZ = 0; searchZ < 2 * horizontalRange + 1; searchZ++){
				for(int searchY = 0; searchY < 2 * verticalRange + 1; searchY++){

					BlockPos pos = new BlockPos(x + searchX, y + searchY, z + searchZ);
					state = this.world.getBlockState(pos);

					if(state.getBlock() == MULBERRY){
						if(!((DoubleGrowingBushBlock) state.getBlock()).isBottomCrop(state)) listMulberry.add(pos);
					}else if(isNight){
						if(state.getLightValue(this.world, pos) >= 14) listLight.add(pos);
					}

				}
			}
		}

		if(!listLight.isEmpty()){
			this.dataManager.set(ROTATION_POS, listLight.get(rand.nextInt(listLight.size())));
		}else if(!listMulberry.isEmpty()){
			this.dataManager.set(ROTATION_POS, listMulberry.get(rand.nextInt(listMulberry.size())));
		}else this.dataManager.set(ROTATION_POS, new BlockPos(x + this.rand.nextInt(2 * horizontalRange + 1), y + this.rand.nextInt(2 * verticalRange + 1), z + this.rand.nextInt(2 * horizontalRange + 1)));

		this.dataManager.set(DISTANCE, 0.5F + 2 * this.rand.nextFloat());
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return sizeIn.height / 2.0F;
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
	public boolean canBePushed() {
		return false;
	}

	@Override
	protected void collideWithEntity(Entity entityIn) {
	}

	@Override
	protected void collideWithNearbyEntities() {
	}

	@Override
	protected float getSoundVolume() {
		return 0.2F;
	}

	@Override
	protected float getSoundPitch() {
		return super.getSoundPitch() * 0.95F;
	}

	@Nullable
	@Override
	public SoundEvent getAmbientSound() {
		return this.rand.nextInt(4) != 0 ? null : SoundEvents.ENTITY_BAT_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_BAT_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_BAT_DEATH;
	}

	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(ROTATION_POS, BlockPos.ZERO);
		this.dataManager.register(CLOCKWISE, true);
		this.dataManager.register(DISTANCE, 1.5F);

	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.dataManager.set(ROTATION_POS, new BlockPos(compound.getInt("RotationX"), compound.getInt("RotationY"), compound.getInt("RotationZ")));
		this.dataManager.set(CLOCKWISE, compound.getBoolean("RotationClockwise"));
		this.dataManager.set(DISTANCE, compound.getFloat("RotationDistance"));
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putInt("RotationX", this.dataManager.get(ROTATION_POS).getX());
		compound.putInt("RotationY", this.dataManager.get(ROTATION_POS).getY());
		compound.putInt("RotationZ", this.dataManager.get(ROTATION_POS).getZ());
		compound.putBoolean("RotationClockwise", this.dataManager.get(CLOCKWISE));
		compound.putFloat("RotationDistance", this.dataManager.get(DISTANCE));
	}
}