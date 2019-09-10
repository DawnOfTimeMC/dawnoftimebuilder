package org.dawnoftimebuilder.blocks.japanese;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.dawnoftimebuilder.blocks.global.DoTBBlockPlate;

import javax.annotation.Nullable;
import java.util.List;

public class BlockSpruceRoofSupport extends DoTBBlockPlate {

	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.6875D, 0.0D, 1.0D, 1.0D, 1.0D);

	public BlockSpruceRoofSupport() {
		super("spruce_roof_support", Material.WOOD, 1.0F, SoundType.WOOD);

		this.canConnectWithDoTPlate = false;
		this.useNeighborBrightness = true;
		this.setLightOpacity(0);
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing facing) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public boolean isNotConnectible(IBlockState state, IBlockAccess worldIn, BlockPos pos, EnumFacing direction) {
		IBlockState stateAdjacent = worldIn.getBlockState(pos.offset(direction));
		return !(stateAdjacent.getBlock() instanceof BlockSpruceRoofSupport) || stateAdjacent.getValue(FACING) != state.getValue(FACING);
	}
}
