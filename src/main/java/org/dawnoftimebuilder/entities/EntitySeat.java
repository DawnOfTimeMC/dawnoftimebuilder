package org.dawnoftimebuilder.entities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.minecraftforge.fml.relauncher.Side.CLIENT;

public class EntitySeat extends Entity {
	public int blockPosX;
	public int blockPosY;
	public int blockPosZ;

	public EntitySeat(World world) {
		super(world);
		this.noClip = true;
		this.height = 0.01F;
		this.width = 0.01F;
	}

	public EntitySeat(World world, double x, double y, double z, double y0ffset, EnumFacing facing, double rotationOffset) {
		this(world);
		this.blockPosX = (int) x;
		this.blockPosY = (int) y;
		this.blockPosZ = (int) z;
		this.rotationYaw = facing.getHorizontalAngle();
		setPositionConsideringRotation(x + 0.5D, y + y0ffset, z + 0.5D, facing, rotationOffset);
	}

	public void setPositionConsideringRotation(double x, double y, double z, EnumFacing facing, double rotationOffset) {
		switch(facing) {
			default:
			case SOUTH:
				z += rotationOffset;
				break;
			case WEST:
				x -= rotationOffset;
				break;
			case NORTH:
				z -= rotationOffset;
				break;
			case EAST:
				x += rotationOffset;
				break;
		}
		setPosition(x, y, z);
	}

	@Override
	@SideOnly(CLIENT)
	public void applyOrientationToEntity(Entity entityToUpdate) {
		entityToUpdate.setRenderYawOffset(this.rotationYaw);
		float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
		float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
		entityToUpdate.prevRotationYaw += f1 - f;
		entityToUpdate.rotationYaw += f1 - f;
		entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
	}

	@Override
	public double getMountedYOffset() {
		return 0.0D;
	}

	@Override
	protected boolean shouldSetPosAfterLoading() {
		return false;
	}

	@Override
	public void onEntityUpdate() {
		if(!this.world.isRemote) {
			if(!this.isBeingRidden() || this.world.isAirBlock(new BlockPos(blockPosX, blockPosY, blockPosZ))) {
				this.setDead();
				world.updateComparatorOutputLevel(getPosition(), world.getBlockState(getPosition()).getBlock());
			}
		}
	}

	@Override
	protected void entityInit() {
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound){
	}
}
