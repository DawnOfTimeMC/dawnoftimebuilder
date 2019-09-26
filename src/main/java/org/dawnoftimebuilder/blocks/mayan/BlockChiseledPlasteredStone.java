package org.dawnoftimebuilder.blocks.mayan;

import org.dawnoftimebuilder.blocks.IBlockMeta;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.dawnoftimebuilder.blocks.general.DoTBBlock;
import org.dawnoftimebuilder.enums.IEnumMetaVariants;

public class BlockChiseledPlasteredStone extends DoTBBlock implements IBlockMeta {

    private static final PropertyEnum<BlockChiseledPlasteredStone.EnumType> VARIANT = PropertyEnum.create("variant", BlockChiseledPlasteredStone.EnumType.class);
    
    public BlockChiseledPlasteredStone() {
    	super("chiseled_plastered_stone", Material.ROCK, 1.5F, SoundType.STONE);

        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockChiseledPlasteredStone.EnumType.CHISELED_PLASTERED_STONE));
    }
    
    /**
     * Gets the metadata of the item this Block can drop. This method is called when the blocks gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the blocks.
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
        for (BlockChiseledPlasteredStone.EnumType type : BlockChiseledPlasteredStone.EnumType.values())
        {
            items.add(new ItemStack(this, 1, type.getMetadata()));
        }
    }
    
    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, BlockChiseledPlasteredStone.EnumType.byMetadata(meta));
    }
    
    /**
     * Get the MapColor for this Block and the given BlockState
     */
    @Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return MapColor.QUARTZ;
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
    	return this.getTranslationKey() + "_" + BlockChiseledPlasteredStone.EnumType.byMetadata(meta).getName();
    }
    
	@Override
	public Block getBlock() {
		return this;
	}

    @Override
    public IEnumMetaVariants[] getVariants(){
        return EnumType.values();
    }

    public enum EnumType implements IEnumMetaVariants {
    	CHISELED_PLASTERED_STONE(0, "chiseled_plastered_stone"),
    	ORNAMENTED_CHISELED_PLASTERED_STONE(1, "ornamented_chiseled_plastered_stone"),
    	GREEN_CHISELED_PLASTERED_STONE(2, "green_chiseled_plastered_stone"),
    	GREEN_ORNAMENTED_CHISELED_PLASTERED_STONE(3, "green_ornamented_chiseled_plastered_stone"),
    	RED_CHISELED_PLASTERED_STONE(4, "red_chiseled_plastered_stone"),
    	RED_ORNAMENTED_CHISELED_PLASTERED_STONE(5, "red_ornamented_chiseled_plastered_stone");

        private static final BlockChiseledPlasteredStone.EnumType[] META_LOOKUP = new BlockChiseledPlasteredStone.EnumType[values().length];
        private final int meta;
        private final String name;

        EnumType(int metaIn, String nameIn) {
            this.meta = metaIn;
            this.name = nameIn;
        }

        /**
         * Returns the EnumType's metadata value.
         */
        @Override
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
        public static BlockChiseledPlasteredStone.EnumType byMetadata(int meta) {
            if (meta < 0 || meta >= META_LOOKUP.length) meta = 0;
            return META_LOOKUP[meta];
        }
        
        static {
            for (BlockChiseledPlasteredStone.EnumType type : values()) {
                META_LOOKUP[type.getMetadata()] = type;
            }
        }
    }
}
