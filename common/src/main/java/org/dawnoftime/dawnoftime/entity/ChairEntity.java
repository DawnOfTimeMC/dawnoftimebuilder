package org.dawnoftime.dawnoftime.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.dawnoftime.dawnoftime.registry.DoTBEntitiesRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChairEntity extends Entity {
    private BlockPos pos;

    public ChairEntity(final Level level) {
        super(DoTBEntitiesRegistry.INSTANCE.CHAIR_ENTITY.get(), level);
        this.noPhysics = true;
    }

    private ChairEntity(final Level level, final BlockPos pos, Direction direction, final float pixelsXOffset, final float pixelsYOffset, final float pixelsZOffset) {
        this(level);
        this.pos = pos;
        this.setYRot(direction.toYRot());
        //Strangely, the default position (with 0 vertical offset) is 3 pixels above the floor.
        this.setPos(pos.getX() + pixelsXOffset / 16.0D, pos.getY() + (pixelsYOffset - 3.0D) / 16.0D, pos.getZ() + pixelsZOffset / 16.0D);
    }

    public static InteractionResult createEntity(final Level level, final BlockPos pos, final Player player, Direction direction, float pixelsXOffset, final float pixelsYOffset, final float pixelsZOffset) {
        if(!level.isClientSide()) {
            final List<ChairEntity> seats = level.getEntitiesOfClass(ChairEntity.class, new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0D, pos.getY() + 1.0D, pos.getZ() + 1.0D));
            if(seats.isEmpty()) {
                final ChairEntity seat = new ChairEntity(level, pos, direction, pixelsXOffset, pixelsYOffset, pixelsZOffset);
                level.addFreshEntity(seat);
                if(player.startRiding(seat, false)) {
                    return InteractionResult.SUCCESS;
                }
            }

            return InteractionResult.PASS;
        }
        return InteractionResult.SUCCESS;
    }

    public static InteractionResult createEntity(final Level level, final BlockPos pos, final Player player, Direction direction, final float pixelsYOffset) {
        return ChairEntity.createEntity(level, pos, player, direction, 8.0F, pixelsYOffset, 8.0F);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {}

    @Override
    public void tick() {
        super.tick();
        if(this.pos == null) {
            this.pos = this.blockPosition();
        }
        if(!this.level().isClientSide() && (this.getPassengers().isEmpty() || this.level().isEmptyBlock(this.pos))) {
            this.remove(RemovalReason.KILLED);
            this.level().updateNeighbourForOutputSignal(this.blockPosition(), this.level().getBlockState(this.blockPosition()).getBlock());
        }
    }

    @Override
    public boolean hurtServer(@NotNull ServerLevel serverLevel, @NotNull DamageSource damageSource, float v) {return false;}

    @Override
    protected void readAdditionalSaveData(@NotNull ValueInput valueInput) {}

    @Override
    protected void addAdditionalSaveData(@NotNull ValueOutput valueOutput) {}

    protected void clampRotation(Entity player) {
        player.setYBodyRot(this.getYRot());
        float f = Mth.wrapDegrees(player.getYRot() - this.getYRot());
        float f1 = Mth.clamp(f, -105.0F, 105.0F);
        player.yRotO += f1 - f;
        player.setYRot(player.getYRot() + f1 - f);
        player.setYHeadRot(player.getYRot());
    }

    @Override
    public void onPassengerTurned(Entity pEntityToUpdate) {
        this.clampRotation(pEntityToUpdate);
    }

    @Override
    protected boolean canRide(final Entity entity) {
        return true;
    }

    @Override
    public @NotNull Vec3 getDismountLocationForPassenger(@NotNull LivingEntity passenger) {
        Direction dir = this.getDirection();
        Direction dirCW = dir.getClockWise();
        Direction dirCCW = dir.getCounterClockWise();
        // We try to dismount in front of the chair, or on the sides.
        final int[][] candidatePositions = new int[][]{
                {dir.getStepX(), dir.getStepZ()},
                {dirCW.getStepX(), dirCW.getStepZ()},
                {dirCCW.getStepX(), dirCCW.getStepZ()}};
        BlockPos pos = this.blockPosition();
        BlockPos.MutableBlockPos posMutable = new BlockPos.MutableBlockPos();
        for(Pose pose : passenger.getDismountPoses()) {
            AABB aabb = passenger.getLocalBoundsForPose(pose);
            for(int[] candidatePos : candidatePositions) {
                posMutable.set(pos.getX() + candidatePos[0], pos.getY(), pos.getZ() + candidatePos[1]);
                double posY = this.level().getBlockFloorHeight(posMutable);
                if (DismountHelper.isBlockFloorValid(posY)) {
                    Vec3 vec3 = Vec3.upFromBottomCenterOf(posMutable, posY);
                    if (DismountHelper.canDismountTo(this.level(), passenger, aabb.move(vec3))) {
                        passenger.setPose(pose);
                        return vec3;
                    }
                }
            }
        }
        return new Vec3(this.getX(), this.getY() + 1, this.getZ());
    }
}
