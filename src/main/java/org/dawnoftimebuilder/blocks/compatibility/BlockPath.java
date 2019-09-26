package org.dawnoftimebuilder.blocks.compatibility;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.blocks.DoTBBlocks;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;
import org.dawnoftimebuilder.blocks.IBlockMeta;
import org.dawnoftimebuilder.blocks.general.DoTBBlock;
import org.dawnoftimebuilder.enums.IEnumMetaVariants;

import java.util.Random;

public class BlockPath extends DoTBBlock implements IBlockMeta, IBlockCustomItem {
	public static final PropertyEnum<BlockPath.EnumType> VARIANT = PropertyEnum.create("variant", BlockPath.EnumType.class);
	private static final PropertyBool FULL = PropertyBool.create("full");

	private static final AxisAlignedBB GRASS_PATH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

	/** This block is a block that will be replaced automatically with the new blocks (different ID and meta) and will be removed in the futures updates.
	*
	 */
	public BlockPath() {
		super("path", Material.GRASS, 0.5F, SoundType.GROUND);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockPath.EnumType.GRAVEL).withProperty(FULL, Boolean.FALSE));
		this.setLightOpacity(255);

		this.setTickRandomly(true);
	}

	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
		this.replaceBlock(worldIn, pos, this.getMetaFromState(state));
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		this.replaceBlock(worldIn, pos, this.getMetaFromState(state));
	}

	private void replaceBlock(World worldIn, BlockPos pos, int currentMeta){
		worldIn.setBlockState(pos, getNewState(currentMeta));
	}

	private IBlockState getNewState(int currentMeta){
		switch(currentMeta){
			case 0 :
				return DoTBBlocks.path_gravel.getDefaultState();
			case 1 :
				return DoTBBlocks.path_stepping_stones.getDefaultState();
			case 2 :
				return DoTBBlocks.path_cobbled.getDefaultState();
			case 4 :
				return DoTBBlocks.path_ochre_tiles.getDefaultState();
			case 5 :
				return DoTBBlocks.path_dirt.getDefaultState();
			default :
				return Blocks.AIR.getDefaultState();
		}
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos){
		return state.withProperty(FULL, this.isFull(worldIn, pos));
	}

	private boolean isFull(IBlockAccess worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos.up()).getBlock();
		return !(block instanceof BlockAir || block instanceof BlockLeaves || block instanceof BlockBush);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return GRASS_PATH_AABB;
	}
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items){
		for (BlockPath.EnumType type : BlockPath.EnumType.values()){
			items.add(new ItemStack(this, 1, type.getMetadata()));
		}
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta){
		return this.getDefaultState().withProperty(VARIANT, BlockPath.EnumType.byMetadata(meta));
	}

	/**
	 * Get the MapColor for this Block and the given BlockState
	 */
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return MapColor.GRAY;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(VARIANT).getMetadata();
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@SuppressWarnings("deprecation")
	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		switch (side) {
			case UP:
				return true;
			case NORTH:
			case SOUTH:
			case WEST:
			case EAST:
				IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
				Block block = iblockstate.getBlock();
				return !iblockstate.isOpaqueCube() && block != Blocks.FARMLAND && block != Blocks.GRASS_PATH;
			default:
				return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
		}
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
		return p_193383_4_ == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}

	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side){
		return base_state.getValue(FULL) || (side != EnumFacing.UP);
	}


	@Override
	protected BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, VARIANT, FULL);
	}

	@Override
	public String getTranslationKey(int meta) {
		return this.getTranslationKey() + "_" + BlockPath.EnumType.byMetadata(meta).getName();
	}

	@Override
	public Block getBlock() {
		return this;
	}

	@Override
	public IEnumMetaVariants[] getVariants() {
		return EnumType.values();
	}

	@Override
	public Item getCustomItemBlock() {
		return null;
	}

	public enum EnumType implements IEnumMetaVariants{
		GRAVEL(0, "gravel"),
		STEPPING_STONES(1, "stepping_stones"),
		COBBLED(2, "cobbled"),
		SANDSTONE(3, "sandstone"),
		OCHRE_TILES(4, "ochre_tiles"),
		DIRT(5, "dirt");

		private static final BlockPath.EnumType[] META_LOOKUP = new BlockPath.EnumType[values().length];
		private final int meta;
		private final String name;

		EnumType(int metaIn, String nameIn){
			this.meta = metaIn;
			this.name = nameIn;
		}

		public int getMetadata(){
			return this.meta;
		}

		public static BlockPath.EnumType byMetadata(int meta){
			if (meta < 0 || meta >= META_LOOKUP.length) meta = 0;
			return META_LOOKUP[meta];
		}

		@Override
		public String getName(){
			return this.name;
		}

		static{
			for (BlockPath.EnumType type : values()){
				META_LOOKUP[type.getMetadata()] = type;
			}
		}
	}
}