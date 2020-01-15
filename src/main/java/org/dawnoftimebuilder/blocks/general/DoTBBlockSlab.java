package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.dawnoftimebuilder.items.general.DoTBItemSlab;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;

public class DoTBBlockSlab extends DoTBBlock implements IBlockCustomItem {

	protected static final PropertyEnum<EnumSlab> SLAB = PropertyEnum.create("slab", EnumSlab.class);

	static final AxisAlignedBB AABB_BOTTOM = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
	static final AxisAlignedBB AABB_TOP = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);

	public DoTBBlockSlab(String name, Material materialIn, float hardness, SoundType sound) {
		super(name, materialIn, hardness, sound);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SLAB, EnumSlab.BOTTOM));
		this.setLightOpacity(255);
		this.useNeighborBrightness = true;
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SLAB);
	}

	/**
	 * Called by ItemBlocks just before a blocks is actually set in the world, to allow for adjustments to the
	 * IBlockstate
	 */
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		IBlockState state = super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(SLAB, EnumSlab.BOTTOM);
		return facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double)hitY <= 0.5D) ? state : state.withProperty(SLAB, EnumSlab.TOP);
	}

	@Override
	protected boolean canSilkHarvest()
	{
		return false;
	}

	@Override
	public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		drops.add(new ItemStack(Item.getItemFromBlock(this), 1));
		if(state.getValue(SLAB) == EnumSlab.DOUBLE) drops.add(new ItemStack(Item.getItemFromBlock(this), 1));
	}

	@Override
	public int getMetaFromState(IBlockState state){
		switch (state.getValue(SLAB)) {
			default:
			case BOTTOM:
				return 0;
			case TOP:
				return 1;
			case DOUBLE:
				return 2;
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		switch (meta) {
			default:
			case 0:
				return this.getDefaultState().withProperty(SLAB, EnumSlab.BOTTOM);
			case 1:
				return this.getDefaultState().withProperty(SLAB, EnumSlab.TOP);
			case 2:
				return this.getDefaultState().withProperty(SLAB, EnumSlab.DOUBLE);
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		switch (state.getValue(SLAB)) {
			default:
			case BOTTOM:
				return AABB_BOTTOM;
			case TOP:
				return AABB_TOP;
			case DOUBLE:
				return FULL_BLOCK_AABB;
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return state.getValue(SLAB) == EnumSlab.DOUBLE;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return state.getValue(SLAB) == EnumSlab.DOUBLE;
	}

	@Override
	public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
		return this.getBlockFaceShape(world, state, pos, side) == BlockFaceShape.SOLID;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing side) {
		if(state.getValue(SLAB) == EnumSlab.DOUBLE) return BlockFaceShape.SOLID;
		if(side == EnumFacing.DOWN && state.getValue(SLAB) == EnumSlab.BOTTOM) return BlockFaceShape.SOLID;
		if(side == EnumFacing.UP && state.getValue(SLAB) == EnumSlab.TOP) return BlockFaceShape.SOLID;
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public Item getCustomItemBlock() {
		return new DoTBItemSlab(this);
	}

	public EnumSlab getSlabState(IBlockState state){
		return state.getValue(SLAB);
	}

	public IBlockState getDoubleSlabDefaultState(){
		return this.getDefaultState().withProperty(SLAB, EnumSlab.DOUBLE);
	}

	public enum EnumSlab implements IStringSerializable {
		BOTTOM("bottom"),
		TOP("top"),
		DOUBLE("double");

		private final String name;

		EnumSlab(String name){
			this.name = name;
		}

		public String toString(){
			return this.name;
		}

		public String getName(){
			return this.name;
		}
	}
}
