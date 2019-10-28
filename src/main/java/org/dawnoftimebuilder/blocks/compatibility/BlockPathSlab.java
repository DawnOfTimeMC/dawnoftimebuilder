package org.dawnoftimebuilder.blocks.compatibility;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.dawnoftimebuilder.blocks.DoTBBlocks;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;
import org.dawnoftimebuilder.blocks.IBlockMeta;
import org.dawnoftimebuilder.enums.IEnumMetaVariants;

import java.util.Random;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public abstract class BlockPathSlab extends BlockSlab implements IBlockMeta, IBlockCustomItem {
	private static final PropertyBool SEAMLESS = PropertyBool.create("seamless");
	public static final PropertyEnum<BlockPathSlab.EnumType> VARIANT = PropertyEnum.create("variant", BlockPathSlab.EnumType.class);
	private static final PropertyBool FULL = PropertyBool.create("full");

	BlockPathSlab(String name) {
		super(Material.GROUND);

		this.setRegistryName(MOD_ID, name);
		this.setTranslationKey(MOD_ID + "." + name);
		this.useNeighborBrightness = true;
		IBlockState iblockstate = this.blockState.getBaseState();

		if (this.isDouble()) iblockstate = iblockstate.withProperty(SEAMLESS, Boolean.FALSE);
		else iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);

		this.setDefaultState(iblockstate.withProperty(VARIANT, BlockPathSlab.EnumType.GRAVEL).withProperty(FULL, Boolean.FALSE));

		this.setSoundType(SoundType.GROUND);
		this.setHardness(0.5F);
		this.setTickRandomly(true);
	}

	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
		worldIn.setBlockState(pos, getNewState(this.getMetaFromState(state)));
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		worldIn.setBlockState(pos, getNewState(this.getMetaFromState(state)));
	}

	private IBlockState getNewState(int currentMeta){
		int newMeta = this.isDouble() ? 2 : (currentMeta >= 8) ? 1 : 0;
		if(currentMeta >= 8) currentMeta -= 8;
		switch(currentMeta){
			case 0 :
				return DoTBBlocks.path_gravel_slab.getStateFromMeta(newMeta);
			case 1 :
				return DoTBBlocks.path_stepping_stones_slab.getStateFromMeta(newMeta);
			case 2 :
				return DoTBBlocks.path_cobbled_slab.getStateFromMeta(newMeta);
			case 4 :
				return DoTBBlocks.path_ochre_tiles_slab.getStateFromMeta(newMeta);
			case 5 :
				return DoTBBlocks.path_dirt_slab.getStateFromMeta(newMeta);
			default :
				return Blocks.AIR.getStateFromMeta(newMeta);
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public String getTranslationKey(int meta) {
		return super.getTranslationKey() + "." + BlockPathSlab.EnumType.byMetadata(meta).getName();
	}

	@Override
	public IProperty<?> getVariantProperty() {
		return VARIANT;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack){
		return BlockPathSlab.EnumType.byMetadata(stack.getMetadata() & 7);
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (BlockPathSlab.EnumType type : BlockPathSlab.EnumType.values()) {
			items.add(new ItemStack(this, 1, type.getMetadata()));
		}
	}


	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, BlockPathSlab.EnumType.byMetadata(meta & 7));
		if (this.isDouble()) iblockstate = iblockstate.withProperty(SEAMLESS, (meta & 8) != 0);
		else iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
		return iblockstate;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | state.getValue(VARIANT).getMetadata();
		if (this.isDouble()) {
			if (state.getValue(SEAMLESS)) i |= 8;
		}
		else if (state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) i |= 8;
		return i;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
		if (((BlockSlab)p_193383_2_.getBlock()).isDouble()) return BlockFaceShape.SOLID;
		else if (p_193383_4_ == EnumFacing.UP && p_193383_2_.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) return BlockFaceShape.SOLID;
		else return p_193383_4_ == EnumFacing.DOWN && p_193383_2_.getValue(HALF) == BlockSlab.EnumBlockHalf.BOTTOM ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}

	protected BlockStateContainer createBlockState() {
		return this.isDouble() ? new BlockStateContainer(this, SEAMLESS, VARIANT, FULL) : new BlockStateContainer(this, HALF, VARIANT, FULL);
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
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		boolean full = state.getValue(FULL);
		boolean isDouble = this.isDouble();

		if(isDouble){
			if(full && face != EnumFacing.UP) return false;
			else if(face == EnumFacing.DOWN) return false;
		}else{

			BlockSlab.EnumBlockHalf half = state.getValue(HALF);
			if(half == BlockSlab.EnumBlockHalf.BOTTOM){
				if(face == EnumFacing.DOWN) return false;
			}else{
				if(face == EnumFacing.UP && full) return false;
			}
		}
		return false;
	}

	@Override
	public int damageDropped(IBlockState state) {
		return (state.getValue(VARIANT)).getMetadata();
	}

	@Override
	public IEnumMetaVariants[] getVariants() {
		return EnumType.values();
	}

	@Override
	public Item getCustomItemBlock() {
		return null;
	}

	public enum EnumType implements IEnumMetaVariants {
		GRAVEL(0, "gravel"),
		STEPPING_STONES(1,  "stepping_stones"),
		COBBLED(2, "cobbled"),
		SANDSTONE(3, "sandstone"),
		OCHRE_TILES(4, "ochre_tiles"),
		DIRT(5, "dirt");

		private static final BlockPathSlab.EnumType[] META_LOOKUP = new BlockPathSlab.EnumType[values().length];
		private final int meta;
		private final String name;

		EnumType(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}

		public int getMetadata()
		{
			return this.meta;
		}

		public static BlockPathSlab.EnumType byMetadata(int meta) {
			if (meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}
			return META_LOOKUP[meta];
		}

		public String getName()
		{
			return this.name;
		}


		static {
			for (BlockPathSlab.EnumType type : values()) {
				META_LOOKUP[type.getMetadata()] = type;
			}
		}
	}
}