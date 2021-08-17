package org.dawnoftimebuilder.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

import static org.dawnoftimebuilder.registries.DoTBEntitiesRegistry.CHAIR_ENTITY;

public class ChairEntity extends Entity {

    private BlockPos pos;

    public ChairEntity(World world){
        super(CHAIR_ENTITY, world);
        this.noClip = true;
    }

    private ChairEntity(World world, BlockPos pos) {
        this(world);
        this.pos = pos;
        this.setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
    }

    public static boolean createEntity(World world, BlockPos pos, PlayerEntity player) {
        if(!world.isRemote()) {
            List<ChairEntity> seats = world.getEntitiesWithinAABB(ChairEntity.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0D, pos.getY() + 1.0D, pos.getZ() + 1.0D));
            if(seats.isEmpty()) {
                ChairEntity seat = new ChairEntity(world, pos);
                world.addEntity(seat);
                player.startRiding(seat, false);
                return true;
            }
        }
        return false;
    }

    @Override
    public void tick(){
        super.tick();
        if(pos == null){
            pos = this.getPosition();
        }
        if(!this.world.isRemote()) {
            if(this.getPassengers().isEmpty() || this.world.isAirBlock(this.pos)) {
                this.remove();
                this.world.updateComparatorOutputLevel(getPosition(), this.world.getBlockState(getPosition()).getBlock());
            }
        }
    }

    public BlockPos getPos(){
        return this.pos;
    }

    @Override
    protected void registerData() {

    }

    @Override
    protected void readAdditional(CompoundNBT compound) {

    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
    }

    @Override
    public double getMountedYOffset(){
        return 0.0D;
    }

    @Override
    protected boolean canBeRidden(Entity entity) {
        return true;
    }

    @Override
    public IPacket<?> createSpawnPacket(){
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
