package org.dawnoftimebuilder.blocks.compatibility;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.dawnoftimebuilder.blocks.DoTBBlocks;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;
import org.dawnoftimebuilder.blocks.IBlockMeta;
import org.dawnoftimebuilder.blocks.general.DoTBBlock;
import org.dawnoftimebuilder.enums.IEnumMetaVariants;

import java.util.Random;

public class BlockThatch extends DoTBBlock implements IBlockMeta, IBlockCustomItem {

	public static final PropertyEnum<BlockThatch.EnumType> VARIANT = PropertyEnum.<BlockThatch.EnumType>create("variant", BlockThatch.EnumType.class);

	public BlockThatch() {
		super("thatch", Material.CLOTH);

		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockThatch.EnumType.WHEAT));
		this.setBurnable();
		this.setHardness(0.3F);

		this.setSoundType(SoundType.CLOTH);
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
		if(currentMeta == 0) return DoTBBlocks.thatch_wheat.getDefaultState();
		else return DoTBBlocks.thatch_bamboo.getDefaultState();
	}

	/**
	 * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
	 * returns the metadata of the dropped item based on the old metadata of the block.
	 */
	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(VARIANT).getMetadata();
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (BlockThatch.EnumType type : BlockThatch.EnumType.values()){
			items.add(new ItemStack(this, 1, type.getMetadata()));
		}
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, BlockThatch.EnumType.byMetadata(meta));
	}

	/**
	 * Get the MapColor for this Block and the given BlockState
	 */
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return MapColor.BROWN;
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
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, VARIANT);
	}

	@Override
	public String getTranslationKey(int meta){
		return this.getTranslationKey() + "_" + BlockThatch.EnumType.byMetadata(meta).getName();
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

	public static enum EnumType implements IEnumMetaVariants {
		WHEAT(0, "wheat"),
		BAMBOO(1, "bamboo");

		private static final BlockThatch.EnumType[] META_LOOKUP = new BlockThatch.EnumType[values().length];
		private final int meta;
		private final String name;

		EnumType(int metaIn, String nameIn) {
			this.meta = metaIn;
			this.name = nameIn;
		}

		public int getMetadata()
		{
			return this.meta;
		}

		@Override
		public String getName()
		{
			return this.name;
		}

		/**
		 * Returns an EnumType for the BlockState from a metadata value.
		 */
		public static BlockThatch.EnumType byMetadata(int meta) {
			if (meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}
			return META_LOOKUP[meta];
		}

		static {
			for (BlockThatch.EnumType blockThatch$enumtype : values()) {
				META_LOOKUP[blockThatch$enumtype.getMetadata()] = blockThatch$enumtype;
			}
		}
	}
}