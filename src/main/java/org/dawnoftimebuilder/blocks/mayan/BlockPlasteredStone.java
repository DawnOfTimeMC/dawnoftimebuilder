package org.dawnoftimebuilder.blocks.mayan;

import org.dawnoftimebuilder.blocks.IBlockMeta;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.dawnoftimebuilder.blocks.general.DoTBBlock;
import org.dawnoftimebuilder.enums.IEnumMetaVariants;

public class BlockPlasteredStone extends DoTBBlock implements IBlockMeta {
	
    private static final PropertyEnum<BlockPlasteredStone.EnumType> VARIANT = PropertyEnum.create("variant", BlockPlasteredStone.EnumType.class);
	
    public BlockPlasteredStone() {
    	super("plastered_stone", Material.ROCK, 1.5F, SoundType.STONE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockPlasteredStone.EnumType.PLASTERED_STONE));
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
        for (BlockPlasteredStone.EnumType type : BlockPlasteredStone.EnumType.values()) {
            items.add(new ItemStack(this, 1, type.getMetadata()));
        }
    }
    
    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, BlockPlasteredStone.EnumType.byMetadata(meta));
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
    	return this.getTranslationKey() + "_" + BlockPlasteredStone.EnumType.byMetadata(meta).getName();
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
    	PLASTERED_STONE(0, "plastered_stone"),
    	RED_PLASTERED_STONE(1, "red_plastered_stone"),
    	RED_ORNAMENTED_PLASTERED_STONE(2, "red_ornamented_plastered_stone");

        private static final BlockPlasteredStone.EnumType[] META_LOOKUP = new BlockPlasteredStone.EnumType[values().length];
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
        public static BlockPlasteredStone.EnumType byMetadata(int meta) {
            if (meta < 0 || meta >= META_LOOKUP.length) meta = 0;
            return META_LOOKUP[meta];
        }
        
        static {
            for (BlockPlasteredStone.EnumType blockPlasteredStone$enumtype : values()) {
                META_LOOKUP[blockPlasteredStone$enumtype.getMetadata()] = blockPlasteredStone$enumtype;
            }
        }
    }
}