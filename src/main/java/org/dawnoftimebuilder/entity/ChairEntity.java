package org.dawnoftimebuilder.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkHooks;
import org.dawnoftimebuilder.registry.DoTBEntitiesRegistry;

import java.util.List;

public class ChairEntity extends Entity {

	private BlockPos pos;

	public ChairEntity(final Level Level) {
		super(DoTBEntitiesRegistry.CHAIR_ENTITY.get(), Level);
		this.noPhysics = true;
	}

	private ChairEntity(final Level Level, final BlockPos pos, final float pixelsXOffset, final float pixelsYOffset, final float pixelsZOffset) {
		this(Level);
		this.pos = pos;
		//Strangely, the default position (with 0 vertical offset) is 3 pixels above the floor.
		this.setPos(pos.getX() + pixelsXOffset / 16.0D, pos.getY() + (pixelsYOffset - 3.0D) / 16.0D, pos.getZ() + pixelsZOffset / 16.0D);
	}

	public static InteractionResult createEntity(final Level Level, final BlockPos pos, final Player player, final float pixelsXOffset, final float pixelsYOffset, final float pixelsZOffset) {
		if (!Level.isClientSide()) {
			final List<ChairEntity> seats = Level.getEntitiesOfClass(ChairEntity.class, new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0D, pos.getY() + 1.0D, pos.getZ() + 1.0D));
			if (seats.isEmpty()) {
				final ChairEntity seat = new ChairEntity(Level, pos, pixelsXOffset, pixelsYOffset, pixelsZOffset);
				Level.addFreshEntity(seat);
				if (player.startRiding(seat, false)) {
					return InteractionResult.SUCCESS;
				}
			}

			return InteractionResult.PASS;
		}
		return InteractionResult.SUCCESS;
	}

	public static InteractionResult createEntity(final Level Level, final BlockPos pos, final Player player, final float pixelsYOffset) {
		return ChairEntity.createEntity(Level, pos, player, 8.0F, pixelsYOffset, 8.0F);
	}

	@Override
	protected void defineSynchedData() {

	}

	@Override
	public void tick() {
		super.tick();
		if (this.pos == null) {
			this.pos = this.blockPosition();
		}
		if (!this.level.isClientSide() && (this.getPassengers().isEmpty() || this.level.isEmptyBlock(this.pos))) {
			this.remove(RemovalReason.KILLED);
			this.level.updateNeighbourForOutputSignal(this.blockPosition(), this.level.getBlockState(this.blockPosition()).getBlock());
		}
	}

	@Override
	protected void readAdditionalSaveData(final CompoundTag p_70037_1_) {

	}

	@Override
	protected void addAdditionalSaveData(final CompoundTag p_213281_1_) {

	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public double getPassengersRidingOffset() {
		return 0.0D;
	}

	@Override
	protected boolean canRide(final Entity entity) {
		return true;
	}
}
