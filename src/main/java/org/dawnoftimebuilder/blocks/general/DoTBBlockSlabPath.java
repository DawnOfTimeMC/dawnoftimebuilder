package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class DoTBBlockSlabPath extends DoTBBlockSlab {

	private static final PropertyBool FULL = PropertyBool.create("full");
	private static final AxisAlignedBB AABB_BOTTOM_PATH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.4375D, 1.0D);
	private static final AxisAlignedBB AABB_TOP_PATH = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 0.9375D, 1.0D);
	private static final AxisAlignedBB AABB_FULL_PATH = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 0.9375D, 1.0D);

	public DoTBBlockSlabPath(String name) {
		super(name, Material.GRASS, 0.5F, SoundType.GROUND);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SLAB, EnumSlab.BOTTOM).withProperty(FULL, false));
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SLAB, FULL);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(FULL, this.isFull(worldIn, pos));
	}

	private boolean isFull(IBlockAccess worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos.up()).getBlock();
		return !(block instanceof BlockAir || block instanceof BlockLeaves || block instanceof BlockBush);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		boolean full = this.getActualState(state, worldIn, pos).getValue(FULL);
		switch (state.getValue(SLAB)) {
			default:
			case BOTTOM:
				return full ? AABB_BOTTOM : AABB_BOTTOM_PATH;
			case TOP:
				return full ? AABB_TOP : AABB_TOP_PATH;
			case DOUBLE:
				return full ? FULL_BLOCK_AABB : AABB_FULL_PATH;
		}
	}
}
