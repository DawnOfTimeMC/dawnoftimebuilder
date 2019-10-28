package org.dawnoftimebuilder.blocks.compatibility;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.dawnoftimebuilder.blocks.DoTBBlocks;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;
import org.dawnoftimebuilder.blocks.IBlockMeta;
import org.dawnoftimebuilder.enums.IEnumMetaVariants;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public abstract class BlockTilesSlab extends BlockSlab implements IBlockMeta, IBlockCustomItem {
    private static final PropertyBool SEAMLESS = PropertyBool.create("seamless");
    public static final PropertyEnum<BlockTilesSlab.EnumType> VARIANT = PropertyEnum.create("variant", BlockTilesSlab.EnumType.class);

    public BlockTilesSlab(String name) {
        super(Material.GROUND);

        this.setRegistryName(MOD_ID, name);
        this.setTranslationKey(MOD_ID + "." + name);
        this.useNeighborBrightness = true;
        IBlockState iblockstate = this.blockState.getBaseState();

        if (this.isDouble()) iblockstate = iblockstate.withProperty(SEAMLESS, Boolean.FALSE);
        else iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);

        this.setDefaultState(iblockstate.withProperty(VARIANT, BlockTilesSlab.EnumType.SPRUCE_FOUNDATION));

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
                return DoTBBlocks.spruce_foundation_slab.getStateFromMeta(newMeta);
            case 1 :
                return DoTBBlocks.grey_roof_tiles_slab.getStateFromMeta(newMeta);
            case 2 :
                return DoTBBlocks.flat_roof_tiles_slab.getStateFromMeta(newMeta);
			case 3 :
				return DoTBBlocks.thatch_wheat_slab.getStateFromMeta(newMeta);
			case 4 :
				return DoTBBlocks.plastered_stone_slab.getStateFromMeta(newMeta);
            case 5 :
                return DoTBBlocks.limestone_brick_slab.getStateFromMeta(newMeta);
            case 6 :
                return DoTBBlocks.red_plastered_stone_slab.getStateFromMeta(newMeta);
			case 7 :
				return DoTBBlocks.thatch_bamboo_slab.getStateFromMeta(newMeta);
            default :
                return Blocks.AIR.getStateFromMeta(newMeta);
        }
    }

	@Override
	public String getTranslationKey(int meta) {
		return super.getTranslationKey() + "." + BlockTilesSlab.EnumType.byMetadata(meta).getName();
	}

    @Override
    public IProperty<?> getVariantProperty()
    {
        return VARIANT;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack){
        return BlockTilesSlab.EnumType.byMetadata(stack.getMetadata() & 7);
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        for (BlockTilesSlab.EnumType blockstoneslab$enumtype : BlockTilesSlab.EnumType.values())
        {

        	items.add(new ItemStack(this, 1, blockstoneslab$enumtype.getMetadata()));

        }
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, BlockTilesSlab.EnumType.byMetadata(meta & 7));
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
        i = i | (state.getValue(VARIANT)).getMetadata();

        if (this.isDouble()) {
            if (state.getValue(SEAMLESS)) {
                i |= 8;
            }
        }else if (state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
            i |= 8;
        }

        return i;
    }

    protected BlockStateContainer createBlockState() {
        return this.isDouble() ? new BlockStateContainer(this, SEAMLESS, VARIANT) : new BlockStateContainer(this, HALF, VARIANT);
    }

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
    public int damageDropped(IBlockState state) {
        return (state.getValue(VARIANT)).getMetadata();
    }

    /**
     * Get the MapColor for this Block and the given BlockState
     */
    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return (state.getValue(VARIANT)).getMapColor();
    }

    @Override
    @Deprecated
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos){
        return blockState.getValue(VARIANT).hardness;

    }

    /**
     * Get a material of block
     */
    @Deprecated
    @Override
    public Material getMaterial(IBlockState state)
    {
        return state.getValue(VARIANT).material;
    }

    /**
     * Sensitive version of getSoundType
     * @param state The state
     * @param world The world
     * @param pos The position. Note that the world may not necessarily have {@code state} here!
     * @param entity The entity that is breaking/stepping on/placing/hitting/falling on this block, or null if no entity is in this context
     * @return A SoundType to use
     */
    @Override
    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        return state.getValue(VARIANT).sound;
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
        SPRUCE_FOUNDATION(0, Material.WOOD, SoundType.WOOD, 1.0F, MapColor.WOOD, "spruce_foundation"),
        GREY_ROOF_TILES(1, Material.ROCK, SoundType.STONE, 2.0F, MapColor.STONE, "grey_roof_tiles"),
        FLAT_ROOF_TILES(2, Material.ROCK, SoundType.STONE, 2.0F, MapColor.ORANGE_STAINED_HARDENED_CLAY, "flat_roof_tiles"),
        THATCH_WHEAT(3, Material.CLOTH, SoundType.CLOTH, 0.3F, MapColor.SAND , "thatch_wheat"),
        PLASTERED_STONE(4, Material.ROCK, SoundType.STONE, 2.0F, MapColor.QUARTZ , "plastered_stone"),
        LIMESTONE_BRICK(5, Material.ROCK, SoundType.STONE, 2.0F, MapColor.STONE, "limestone_brick"),
        RED_PLASTERED_STONE(6, Material.ROCK, SoundType.STONE, 2.0F, MapColor.RED_STAINED_HARDENED_CLAY, "red_plastered_stone"),
        THATCH_BAMBOO(7, Material.CLOTH, SoundType.CLOTH, 0.3F, MapColor.SAND , "thatch_bamboo");

        private static final BlockTilesSlab.EnumType[] META_LOOKUP = new BlockTilesSlab.EnumType[values().length];
        private final int meta;
        private final MapColor color;
        private final SoundType sound;
        private final Material material;
        private final float hardness;
        private final String name;

        EnumType(int meta, Material material, SoundType sound, float hardness, MapColor color, String name) {
        	this.color = color;
            this.meta = meta;
            this.name = name;
            this.material = material;
            this.sound = sound;
            this.hardness = hardness;
        }

        public int getMetadata()
        {
            return this.meta;
        }

        public MapColor getMapColor(){
            return this.color;
        }


        public static BlockTilesSlab.EnumType byMetadata(int meta) {
            if (meta < 0 || meta >= META_LOOKUP.length) meta = 0;
            return META_LOOKUP[meta];
        }

        public String getName()
        {
            return this.name;
        }

        static {
            for (BlockTilesSlab.EnumType type : values()) {
                META_LOOKUP[type.getMetadata()] = type;
            }
        }
    }
}