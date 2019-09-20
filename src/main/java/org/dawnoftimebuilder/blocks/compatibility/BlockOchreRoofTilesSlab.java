package org.dawnoftimebuilder.blocks.compatibility;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.dawnoftimebuilder.blocks.DoTBBlocks;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public abstract class BlockOchreRoofTilesSlab extends BlockSlab implements IBlockCustomItem {
	private static final PropertyBool SANDSTONE = PropertyBool.create("sandstone");
	public static final PropertyEnum<BlockOchreRoofTilesType> VARIANT = PropertyEnum.<BlockOchreRoofTilesType>create("variant", BlockOchreRoofTilesType.class);

	public BlockOchreRoofTilesSlab(String name) {
		super(Material.ROCK);

		this.setRegistryName(MOD_ID, name);
		this.setTranslationKey(MOD_ID + "." + name);
		this.useNeighborBrightness = true;

		IBlockState iblockstate = this.blockState.getBaseState();
		if (this.isDouble()) iblockstate = iblockstate.withProperty(SANDSTONE, false);
		else iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
		this.setDefaultState(iblockstate);

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
		int newMeta = this.isDouble() ? 2 : (currentMeta >= 8) ? 1 : 0;
		return DoTBBlocks.ochre_roof_tiles_slab.getStateFromMeta(newMeta);
	}

	@Override
	public String getTranslationKey(int meta){
		return this.getTranslationKey();
	}

	@Override
	public IProperty<?> getVariantProperty(){
		return VARIANT;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack)
	{
		return BlockOchreRoofTilesType.NORMAL;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState();

		if (this.isDouble()) state = state.withProperty(SANDSTONE, (meta & 8) != 0);
		else state = state.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
		return state;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;

		if (this.isDouble()) {
			if (state.getValue(SANDSTONE)) i |= 8;
		} else if (state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) i |= 8;

		return i;
	}

	protected BlockStateContainer createBlockState() {
		return this.isDouble() ? new BlockStateContainer(this, SANDSTONE, VARIANT) : new BlockStateContainer(this, HALF, VARIANT);
	}

	/**
	 * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
	 * returns the metadata of the dropped item based on the old metadata of the block.
	 */
	public int damageDropped(IBlockState state)
	{
		return 0;
	}

	/**
	 * Get the MapColor for this Block and the given BlockState
	 */
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return MapColor.RED_STAINED_HARDENED_CLAY;
	}

	@Override
	public Item getCustomItemBlock(){
		return null;
	}


	public enum BlockOchreRoofTilesType implements IStringSerializable{
		NORMAL("normal");

		private String typeName;

		BlockOchreRoofTilesType(String typeName) {
			this.typeName = typeName;
		}

		@Override
		public String getName() {
			return this.typeName;
		}
	}
}