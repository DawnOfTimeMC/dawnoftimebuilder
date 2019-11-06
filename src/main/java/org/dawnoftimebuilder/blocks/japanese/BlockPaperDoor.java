package org.dawnoftimebuilder.blocks.japanese;

import java.util.Random;

import org.dawnoftimebuilder.DoTBUtils;
import org.dawnoftimebuilder.blocks.general.DoTBBlock;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.items.japanese.ItemPaperDoor;

import static org.dawnoftimebuilder.items.DoTBItems.paper_door;

public class BlockPaperDoor extends DoTBBlock implements IBlockCustomItem {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool OPEN = PropertyBool.create("open");
    public static final PropertyEnum<BlockPaperDoor.EnumHingePosition> HINGE = PropertyEnum.create("hinge", BlockPaperDoor.EnumHingePosition.class);
    public static final PropertyBool POWERED = PropertyBool.create("powered");
    public static final PropertyEnum<BlockPaperDoor.EnumDoorHalf> HALF = PropertyEnum.create("half", BlockPaperDoor.EnumDoorHalf.class);
    private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.4374D, 1.0D, 1.0D, 0.5626D);
    private static final AxisAlignedBB NORTH_OPEN_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.4374D, 0.25D, 1.0D, 0.5626D);

    public BlockPaperDoor() {
        super("paper_door", Material.CLOTH);
        this.setBurnable();
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(OPEN, false).withProperty(HINGE, BlockPaperDoor.EnumHingePosition.LEFT).withProperty(POWERED, false).withProperty(HALF, BlockPaperDoor.EnumDoorHalf.LOWER));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        state = state.getActualState(source, pos);
        boolean open = state.getValue(OPEN);
        boolean hinge = state.getValue(HINGE) == BlockPaperDoor.EnumHingePosition.RIGHT;
        return DoTBUtils.getRotatedAABB(open ? NORTH_OPEN_AABB : NORTH_AABB, hinge ? state.getValue(FACING).getOpposite() : state.getValue(FACING));
    }

    /**
     * Gets the localized name of this blocks. Used for the statistics page.
     */
    public String getLocalizedName()
    {
        return I18n.translateToLocal((this.getTranslationKey() + ".name").replaceAll("tile", "item"));
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    /**
     * Determines if an entity can path through this blocks
     */
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return isOpen(combineMetadata(worldIn, pos));
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    private int getCloseSound()
    {
        return 1012;
    }

    private int getOpenSound()
    {
        return 1006;
    }

    /**
     * Get the MapColor for this Block and the given BlockState
     */
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.WHITE_STAINED_HARDENED_CLAY;
    }

    /**
     * Called when the blocks is right clicked by a player.
     */
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        BlockPos blockpos = state.getValue(HALF) == BlockPaperDoor.EnumDoorHalf.LOWER ? pos : pos.down();
        IBlockState iblockstate = pos.equals(blockpos) ? state : worldIn.getBlockState(blockpos);

        if (iblockstate.getBlock() != this) return false;
        else {
            state = iblockstate.cycleProperty(OPEN);
            worldIn.setBlockState(blockpos, state, 10);
            worldIn.markBlockRangeForRenderUpdate(blockpos, pos);
            worldIn.playEvent(playerIn, state.getValue(OPEN) ? this.getOpenSound() : this.getCloseSound(), pos, 0);
            return true;
        }
    }

    /**
     * Called when a neighboring blocks was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * blocks, etc.
     */
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (state.getValue(HALF) == BlockPaperDoor.EnumDoorHalf.UPPER) {
            BlockPos posDown = pos.down();
            IBlockState stateDown = worldIn.getBlockState(posDown);

            if (stateDown.getBlock() != this) worldIn.setBlockToAir(pos);
            else if (blockIn != this) stateDown.neighborChanged(worldIn, posDown, blockIn, fromPos);
        } else {
            boolean mustDrop = false;
            BlockPos posUp = pos.up();
            IBlockState stateUp = worldIn.getBlockState(posUp);

            if (stateUp.getBlock() != this) {
                worldIn.setBlockToAir(pos);
                mustDrop = true;
            }

            if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn,  pos.down(), EnumFacing.UP)) {
                worldIn.setBlockToAir(pos);
                mustDrop = true;
                if (stateUp.getBlock() == this) worldIn.setBlockToAir(posUp);
            }

            if (mustDrop) {
                if (!worldIn.isRemote) this.dropBlockAsItem(worldIn, pos, state, 0);
            } else {
                boolean isPowered = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(posUp);

                if (blockIn != this && (isPowered || blockIn.getDefaultState().canProvidePower()) && isPowered != stateUp.getValue(POWERED)) {
                    worldIn.setBlockState(posUp, stateUp.withProperty(POWERED, isPowered), 2);

                    if (isPowered != state.getValue(OPEN)) {
                        worldIn.setBlockState(pos, state.withProperty(OPEN, isPowered), 2);
                        worldIn.markBlockRangeForRenderUpdate(pos, pos);
                        worldIn.playEvent(null, isPowered ? this.getOpenSound() : this.getCloseSound(), pos, 0);
                    }
                }
            }
        }
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(HALF) == BlockPaperDoor.EnumDoorHalf.UPPER ? Items.AIR : this.getItem();
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        if (pos.getY() >= worldIn.getHeight() - 1) return false;
        else return worldIn.getBlockState(pos.down()).getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID && super.canPlaceBlockAt(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos.up());
    }

    @Override
    public EnumPushReaction getPushReaction(IBlockState state)
    {
        return EnumPushReaction.DESTROY;
    }

    private static int combineMetadata(IBlockAccess worldIn, BlockPos pos) {
        IBlockState state = worldIn.getBlockState(pos);
        int i = state.getBlock().getMetaFromState(state);
        boolean flag = isTop(i);

        IBlockState stateDown = worldIn.getBlockState(pos.down());
        int j = stateDown.getBlock().getMetaFromState(stateDown);
        int k = flag ? j : i;

        IBlockState stateUp = worldIn.getBlockState(pos.up());
        int l = stateUp.getBlock().getMetaFromState(stateUp);
        int i1 = flag ? i : l;

        boolean flag1 = (i1 & 1) != 0;
        boolean flag2 = (i1 & 2) != 0;
        return removeHalfBit(k) | (flag ? 8 : 0) | (flag1 ? 16 : 0) | (flag2 ? 32 : 0);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(this.getItem());
    }

    private Item getItem() {
    	return paper_door;
    }

    /**
     * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually
     * collect this blocks
     */
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        BlockPos posDown = pos.down();
        BlockPos posUp = pos.up();

        if(player.capabilities.isCreativeMode && state.getValue(HALF) == BlockPaperDoor.EnumDoorHalf.UPPER && worldIn.getBlockState(posDown).getBlock() == this)
            worldIn.setBlockToAir(posDown);

        if(state.getValue(HALF) == BlockPaperDoor.EnumDoorHalf.LOWER && worldIn.getBlockState(posUp).getBlock() == this) {
            if(player.capabilities.isCreativeMode) worldIn.setBlockToAir(pos);
            worldIn.setBlockToAir(posUp);
        }
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        if (state.getValue(HALF) == BlockPaperDoor.EnumDoorHalf.LOWER) {
            IBlockState stateUp = worldIn.getBlockState(pos.up());

            if (stateUp.getBlock() == this) state = state.withProperty(HINGE, stateUp.getValue(HINGE)).withProperty(POWERED, stateUp.getValue(POWERED));
        }else{
            IBlockState stateDown = worldIn.getBlockState(pos.down());

            if (stateDown.getBlock() == this) state = state.withProperty(FACING, stateDown.getValue(FACING)).withProperty(OPEN, stateDown.getValue(OPEN));
        }

        return state;
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.getValue(HALF) != BlockPaperDoor.EnumDoorHalf.LOWER ? state : state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return mirrorIn == Mirror.NONE ? state : state.withRotation(mirrorIn.toRotation(state.getValue(FACING))).cycleProperty(HINGE);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta){
        return (meta & 8) > 0
				? this.getDefaultState()
                        .withProperty(HALF, BlockPaperDoor.EnumDoorHalf.UPPER)
                        .withProperty(HINGE, (meta & 1) > 0 ? BlockPaperDoor.EnumHingePosition.RIGHT : BlockPaperDoor.EnumHingePosition.LEFT)
                        .withProperty(POWERED, (meta & 2) > 0)
                : this.getDefaultState()
						.withProperty(HALF, BlockPaperDoor.EnumDoorHalf.LOWER)
						.withProperty(FACING, EnumFacing.byHorizontalIndex(meta & 3).rotateYCCW())
						.withProperty(OPEN, (meta & 4) > 0);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state) {
        int i = 0;

        if (state.getValue(HALF) == BlockPaperDoor.EnumDoorHalf.UPPER) {
            i = i | 8;
            if (state.getValue(HINGE) == BlockPaperDoor.EnumHingePosition.RIGHT) i |= 1;
            if (state.getValue(POWERED)) i |= 2;
        } else {
            i = i | (state.getValue(FACING)).rotateY().getHorizontalIndex();
            if (state.getValue(OPEN)) i |= 4;
        }

        return i;
    }

    private static int removeHalfBit(int meta) {
        return meta & 7;
    }

    private static boolean isOpen(int combinedMeta) {
        return (combinedMeta & 4) != 0;
    }

    private static boolean isTop(int meta) {
        return (meta & 8) != 0;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF, FACING, OPEN, HINGE, POWERED);
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing facing) {
        state = this.getActualState(state, worldIn, pos);
        EnumFacing enumfacing = state.getValue(FACING);
        return facing == enumfacing.rotateY() || facing == enumfacing.rotateYCCW() ? BlockFaceShape.MIDDLE_POLE_THIN : BlockFaceShape.UNDEFINED;
    }

    @Override
    public Item getCustomItemBlock() {
        return new ItemPaperDoor()
                .setRegistryName(this.getRegistryName())
                .setTranslationKey(this.getTranslationKey());
    }

    public enum EnumDoorHalf implements IStringSerializable {
        UPPER,
        LOWER;

        public String toString()
        {
            return this.getName();
        }

        public String getName()
        {
            return this == UPPER ? "upper" : "lower";
        }
    }

    public enum EnumHingePosition implements IStringSerializable {
        LEFT,
        RIGHT;

        public String toString()
        {
            return this.getName();
        }

        public String getName()
        {
            return this == LEFT ? "left" : "right";
        }
    }
}