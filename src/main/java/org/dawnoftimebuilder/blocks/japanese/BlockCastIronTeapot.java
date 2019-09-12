package org.dawnoftimebuilder.blocks.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.blocks.IBlockMeta;
import org.dawnoftimebuilder.blocks.IBlockSpecialDisplay;
import org.dawnoftimebuilder.blocks.global.DoTBBlock;
import org.dawnoftimebuilder.enums.IEnumMetaVariants;
import org.dawnoftimebuilder.items.japanese.ItemCastIronTeapot;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;

public class BlockCastIronTeapot extends DoTBBlock implements IBlockMeta, IBlockSpecialDisplay, IBlockCustomItem {

    private static final AxisAlignedBB AABB = new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.4D, 0.7D);
    public static final PropertyEnum<BlockCastIronTeapot.EnumType> VARIANT = PropertyEnum.create("variant", BlockCastIronTeapot.EnumType.class);

    public BlockCastIronTeapot() {
    	super("cast_iron_teapot", Material.IRON, 0.7F, SoundType.METAL);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.GREY));
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
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

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        ItemStack stack = super.getPickBlock(state, target, world, pos, player);
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setBoolean("completed", false);
        tagCompound.setInteger("style", 0);
        stack.setTagCompound(tagCompound);
        return stack;
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (EnumType type : EnumType.values()) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger("style", type.getMetadata());
            compound.setBoolean("completed", false);
            ItemStack stack = new ItemStack(this, 1, type.getMetadata());
            stack.setTagCompound(compound);
            items.add(stack);
        }
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing facing) {
        return BlockFaceShape.UNDEFINED;
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

    @Override
    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, BlockCastIronTeapot.EnumType.byMetadata(meta));
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
    	return this.getTranslationKey() + "_" + BlockCastIronTeapot.EnumType.byMetadata(meta).getName();
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
    public float getDisplayScale(int meta) {
        return 1.0F;
    }

    @Override
    public Item getCustomItemBlock() {
        return new ItemCastIronTeapot()
                .setRegistryName(this.getRegistryName())
                .setTranslationKey(this.getTranslationKey());
    }

    public enum EnumType implements IEnumMetaVariants {
    	GREY(0, "grey"),
        GOLDEN(1, "green"),
    	RED(2, "decorated");

        private static final BlockCastIronTeapot.EnumType[] META_LOOKUP = new BlockCastIronTeapot.EnumType[values().length];
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
        public static BlockCastIronTeapot.EnumType byMetadata(int meta){
            if (meta < 0 || meta >= META_LOOKUP.length) meta = 0;
            return META_LOOKUP[meta];
        }

        static {
            for (BlockCastIronTeapot.EnumType type : values()) {
                META_LOOKUP[type.getMetadata()] = type;
            }
        }
    }
}