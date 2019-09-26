package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.*;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class DoTBBlockPath extends DoTBBlock {
	private static final PropertyBool FULL = PropertyBool.create("full");

	private static final AxisAlignedBB PATH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

	public DoTBBlockPath(String name) {
		super(name, Material.GRASS, 0.5F, SoundType.GROUND);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FULL, Boolean.FALSE));
		this.setLightOpacity(255);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(FULL, this.isFull(worldIn, pos));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	private boolean isFull(IBlockAccess worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos.up()).getBlock();
		return !(block instanceof BlockAir || block instanceof BlockLeaves || block instanceof BlockBush);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return this.getActualState(state, source, pos).getValue(FULL) ? FULL_BLOCK_AABB : PATH_AABB;
	}

	/**
	 * Get the MapColor for this Block and the given BlockState
	 */
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return MapColor.GRAY;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state){
		return state.getValue(FULL);
	}

	@Override
	public boolean isFullCube(IBlockState state){
		return state.getValue(FULL);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FULL);
	}
}