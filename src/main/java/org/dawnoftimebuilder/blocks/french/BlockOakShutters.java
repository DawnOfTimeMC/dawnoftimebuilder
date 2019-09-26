package org.dawnoftimebuilder.blocks.french;

import java.util.Random;

import org.dawnoftimebuilder.DoTBUtils;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockPlanks;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.dawnoftimebuilder.blocks.general.DoTBBlock;
import org.dawnoftimebuilder.items.DoTBItems;
import org.dawnoftimebuilder.items.french.ItemOakShutters;

public class BlockOakShutters extends DoTBBlock implements IBlockCustomItem {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<BlockOakShutters.EnumOpenPosition> OPEN = PropertyEnum.create("open", BlockOakShutters.EnumOpenPosition.class);
    public static final PropertyEnum<BlockOakShutters.EnumHingePosition> HINGE = PropertyEnum.create("hinge", BlockOakShutters.EnumHingePosition.class);
    public static final PropertyBool POWERED = PropertyBool.create("powered");
    public static final PropertyEnum<BlockOakShutters.EnumDoorHalf> HALF = PropertyEnum.create("half", BlockOakShutters.EnumDoorHalf.class);
    private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.8125D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB NORTH_FULL_RIGHT_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.8125D, 0.1875D, 1.0D, 1.0D);
    private static final AxisAlignedBB NORTH_FULL_LEFT_AABB = new AxisAlignedBB(0.8125D, 0.0D, 0.8125D, 1.0D, 1.0D, 1.0D);

    public BlockOakShutters() {
        super("oak_shutters", Material.WOOD);
        this.useNeighborBrightness = true;
        this.setBurnable();
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(OPEN, BlockOakShutters.EnumOpenPosition.CLOSED).withProperty(HINGE, BlockOakShutters.EnumHingePosition.LEFT).withProperty(HALF, BlockOakShutters.EnumDoorHalf.LOWER));
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF, FACING, OPEN, HINGE, POWERED);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        state = state.getActualState(source, pos);
        boolean hinge = state.getValue(HINGE) == BlockOakShutters.EnumHingePosition.RIGHT;
        if(state.getValue(OPEN) == BlockOakShutters.EnumOpenPosition.FULL) {
        	return DoTBUtils.getRotatedAABB(hinge ? NORTH_FULL_RIGHT_AABB : NORTH_FULL_LEFT_AABB, state.getValue(FACING));
        } else return DoTBUtils.getRotatedAABB(NORTH_AABB, state.getValue(OPEN) == BlockOakShutters.EnumOpenPosition.CLOSED ? state.getValue(FACING) : (hinge ? state.getValue(FACING).rotateY() : state.getValue(FACING).rotateYCCW()));
    }

    @Override
    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
        return (state.getActualState(world, pos).getValue(OPEN) == EnumOpenPosition.CLOSED) ? 255 : 0;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    /**
     * Determines if an entity can path through this blocks
     */
    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos).getValue(OPEN) != BlockOakShutters.EnumOpenPosition.CLOSED;
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    private int getCloseSound() {
        return 1012;
    }

    private int getOpenSound() {
        return 1006;
    }

    /**
     * Get the MapColor for this Block and the given BlockState
     */
    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return BlockPlanks.EnumType.OAK.getMapColor();
    }
    
    public static BlockOakShutters.EnumOpenPosition getOpenPosition(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    	pos = pos.offset((state.getValue(HINGE) == BlockOakShutters.EnumHingePosition.LEFT) ? state.getValue(FACING).rotateY() : state.getValue(FACING).rotateYCCW());
    	boolean bot = (!(worldIn.getBlockState(pos).getBlock() instanceof BlockSmallOakShutters) && !(worldIn.getBlockState(pos).getBlock() instanceof BlockOakShutters)) && worldIn.getBlockState(pos).getCollisionBoundingBox(worldIn, pos) == Block.NULL_AABB;
    	pos = pos.up();
    	boolean top = (!(worldIn.getBlockState(pos).getBlock() instanceof BlockSmallOakShutters) && !(worldIn.getBlockState(pos).getBlock() instanceof BlockOakShutters)) && worldIn.getBlockState(pos).getCollisionBoundingBox(worldIn, pos) == Block.NULL_AABB;
    	return (top && bot) ? BlockOakShutters.EnumOpenPosition.FULL : BlockOakShutters.EnumOpenPosition.HALF;
    }
    
    /**
     * Called when the blocks is right clicked by a player.
     */
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        BlockPos blockpos = state.getValue(HALF) == BlockOakShutters.EnumDoorHalf.LOWER ? pos : pos.down();
        IBlockState iblockstate = pos.equals(blockpos) ? state : worldIn.getBlockState(blockpos);

        if (iblockstate.getBlock() instanceof BlockOakShutters) {
            state = (iblockstate.getValue(OPEN) != BlockOakShutters.EnumOpenPosition.CLOSED) ? iblockstate.withProperty(OPEN, BlockOakShutters.EnumOpenPosition.CLOSED) : iblockstate.withProperty(OPEN, getOpenPosition(iblockstate, worldIn, blockpos));
            worldIn.setBlockState(blockpos, state, 10);
            worldIn.markBlockRangeForRenderUpdate(blockpos, pos);
            worldIn.playEvent(playerIn, (iblockstate.getValue(OPEN) == BlockOakShutters.EnumOpenPosition.CLOSED) ? this.getCloseSound() : this.getOpenSound(), pos, 0);
            return true;
        } else return false;
    }

    /**
     * Called when a neighboring blocks was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * blocks, etc.
     */
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        BlockPos posUp;
        IBlockState stateUp;
        state = state.getActualState(worldIn, pos);
    	if(state.getValue(HALF) == BlockOakShutters.EnumDoorHalf.UPPER) {
    		posUp = pos;
            stateUp = state;
            pos = pos.down();
    		state = worldIn.getBlockState(pos).getActualState(worldIn, pos);
            if (state.getBlock() != this) {
            	worldIn.setBlockToAir(pos);
            }
            else{
            	if (state != stateUp.withProperty(HALF, BlockOakShutters.EnumDoorHalf.LOWER)) {
            		worldIn.setBlockState(posUp, state.withProperty(HALF, BlockOakShutters.EnumDoorHalf.UPPER), 10);
            	}
            	if (fromPos != pos) state.neighborChanged(worldIn, pos, blockIn, fromPos);
            }
        }else{
            posUp = pos.up();
            stateUp = worldIn.getBlockState(posUp).getActualState(worldIn, posUp);
        	boolean mustDrop = false;
            if (!(stateUp.getBlock() instanceof BlockOakShutters)) {
                worldIn.setBlockToAir(pos);
                mustDrop = true;
            }

            EnumFacing direction = state.getValue(FACING).getOpposite();
            if (!canSupportShutters(worldIn, pos.offset(direction).offset(state.getValue(HINGE) == BlockOakShutters.EnumHingePosition.LEFT ? direction.rotateYCCW() : direction.rotateY()), direction, state.getValue(HINGE))) {
            	worldIn.setBlockToAir(pos);
                mustDrop = true;
                if (stateUp.getBlock() instanceof BlockOakShutters) worldIn.setBlockToAir(posUp);
            }

            if (mustDrop) {
                if (!worldIn.isRemote) this.dropBlockAsItem(worldIn, pos, state, 0);
            } else {
                boolean isPowered = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(posUp);
                if (blockIn != this && (isPowered || blockIn.getDefaultState().canProvidePower()) && isPowered != stateUp.getValue(POWERED)) {
                	worldIn.setBlockState(posUp, stateUp.withProperty(POWERED, isPowered), 2);
                    if (isPowered != (state.getValue(OPEN) != BlockOakShutters.EnumOpenPosition.CLOSED)) {
                        if(isPowered) state = state.withProperty(OPEN, getOpenPosition(stateUp, worldIn, pos));
                        else state = state.withProperty(OPEN, BlockOakShutters.EnumOpenPosition.CLOSED);
                        worldIn.setBlockState(pos, state, 2);
                        worldIn.markBlockRangeForRenderUpdate(pos, pos);
                        worldIn.playEvent(null, isPowered ? this.getOpenSound() : this.getCloseSound(), pos, 0);
                    }else if(state.getValue(OPEN) != BlockOakShutters.EnumOpenPosition.CLOSED) {
                    	if(state.getValue(OPEN) != getOpenPosition(stateUp, worldIn, pos)) worldIn.setBlockState(pos, state.withProperty(OPEN, getOpenPosition(stateUp, worldIn, pos)), 2);
                    }
                }else if(state.getValue(OPEN) != BlockOakShutters.EnumOpenPosition.CLOSED) {
                	if(state.getValue(OPEN) != getOpenPosition(stateUp, worldIn, pos)) worldIn.setBlockState(pos, state.withProperty(OPEN, getOpenPosition(stateUp, worldIn, pos)), 2);
                }
            }
        }
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(HALF) == BlockOakShutters.EnumDoorHalf.UPPER ? Items.AIR : this.getItem();
    }
    
    public static boolean canSupportShutters(World worldIn, BlockPos pos, EnumFacing direction, BlockOakShutters.EnumHingePosition hinge) {
    	boolean bot = worldIn.getBlockState(pos).getBlockFaceShape(worldIn, pos, direction) == BlockFaceShape.SOLID || worldIn.getBlockState(pos).getBlockFaceShape(worldIn, pos, (hinge == BlockOakShutters.EnumHingePosition.LEFT) ? direction.rotateYCCW() : direction.rotateY()) == BlockFaceShape.SOLID;
    	pos = pos.up();
    	boolean top = worldIn.getBlockState(pos).getBlockFaceShape(worldIn, pos, direction) == BlockFaceShape.SOLID || worldIn.getBlockState(pos).getBlockFaceShape(worldIn, pos, (hinge == BlockOakShutters.EnumHingePosition.LEFT) ? direction.rotateYCCW() : direction.rotateY()) == BlockFaceShape.SOLID;
    	return bot && top;
    }

    @Override
    public EnumPushReaction getPushReaction(IBlockState state) {
        return EnumPushReaction.DESTROY;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(this.getItem());
    }

    private Item getItem() {
    	return DoTBItems.oak_shutters;
    }
    
    /**
     * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually
     * collect this blocks
     */
    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        BlockPos blockpos = pos.down();
        BlockPos blockpos1 = pos.up();

        if (player.capabilities.isCreativeMode && state.getValue(HALF) == BlockOakShutters.EnumDoorHalf.UPPER && worldIn.getBlockState(blockpos).getBlock() == this) {
            worldIn.setBlockToAir(blockpos);
        }

        if (state.getValue(HALF) == BlockOakShutters.EnumDoorHalf.LOWER && worldIn.getBlockState(blockpos1).getBlock() == this) {
            if (player.capabilities.isCreativeMode) worldIn.setBlockToAir(pos);
            worldIn.setBlockToAir(blockpos1);
        }
    }

    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        if (state.getValue(HALF) == BlockOakShutters.EnumDoorHalf.LOWER) {
            IBlockState stateUp = worldIn.getBlockState(pos.up());
            if (stateUp.getBlock() == this) state = state.withProperty(HINGE, stateUp.getValue(HINGE)).withProperty(POWERED, stateUp.getValue(POWERED));
            if (state.getValue(OPEN) != BlockOakShutters.EnumOpenPosition.CLOSED) state = state.withProperty(OPEN, getOpenPosition(state, worldIn, pos));
        } else {
            IBlockState stateDown = worldIn.getBlockState(pos.down());
            if (stateDown.getBlock() == this) state = state.withProperty(FACING, stateDown.getValue(FACING)).withProperty(OPEN, stateDown.getValue(OPEN));
            if (state.getValue(OPEN) != BlockOakShutters.EnumOpenPosition.CLOSED) state = state.withProperty(OPEN, getOpenPosition(state, worldIn, pos.down()));
        }
        return state;
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.getValue(HALF) != BlockOakShutters.EnumDoorHalf.LOWER ? state : state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return mirrorIn == Mirror.NONE ? state : state.withRotation(mirrorIn.toRotation(state.getValue(FACING))).cycleProperty(HINGE);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return (meta & 8) > 0 ? this.getDefaultState().withProperty(HALF, BlockOakShutters.EnumDoorHalf.UPPER).withProperty(HINGE, (meta & 1) > 0 ? BlockOakShutters.EnumHingePosition.RIGHT : BlockOakShutters.EnumHingePosition.LEFT).withProperty(POWERED, (meta & 2) > 0) : this.getDefaultState().withProperty(HALF, BlockOakShutters.EnumDoorHalf.LOWER).withProperty(FACING, EnumFacing.byHorizontalIndex(meta & 3).rotateYCCW()).withProperty(OPEN, ((meta & 4) > 0) ? BlockOakShutters.EnumOpenPosition.HALF : BlockOakShutters.EnumOpenPosition.CLOSED);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        if (state.getValue(HALF) == BlockOakShutters.EnumDoorHalf.UPPER) {
        	i = i | 8;
            if (state.getValue(HINGE) == BlockOakShutters.EnumHingePosition.RIGHT) i |= 1;
            if (state.getValue(POWERED)) i |= 2;
        } else {
            i = i | (state.getValue(FACING)).rotateY().getHorizontalIndex();
            if (state.getValue(OPEN) != BlockOakShutters.EnumOpenPosition.CLOSED) i |= 4;
        }
        return i;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public Item getCustomItemBlock() {
        return new ItemOakShutters()
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

    public enum EnumOpenPosition implements IStringSerializable {
        CLOSED,
        HALF,
        FULL;

        public String toString()
        {
            return this.getName();
        }

        public String getName()
        {
            return this == CLOSED ? "closed" : (this == HALF ? "half" : "full");
        }
    }
}
