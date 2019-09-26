package org.dawnoftimebuilder.blocks.french;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.dawnoftimebuilder.DoTBUtils;
import org.dawnoftimebuilder.blocks.general.DoTBBlock;

public class BlockSmallOakShutters extends DoTBBlock {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static final PropertyEnum<BlockOakShutters.EnumOpenPosition> OPEN = PropertyEnum.create("open", BlockOakShutters.EnumOpenPosition.class);
    private static final PropertyEnum<BlockOakShutters.EnumHingePosition> HINGE = PropertyEnum.create("hinge", BlockOakShutters.EnumHingePosition.class);
    private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.8125D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB NORTH_FULL_RIGHT_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.8125D, 0.1875D, 1.0D, 1.0D);
    private static final AxisAlignedBB NORTH_FULL_LEFT_AABB = new AxisAlignedBB(0.8125D, 0.0D, 0.8125D, 1.0D, 1.0D, 1.0D);

    public BlockSmallOakShutters() {
        super("small_oak_shutters", Material.WOOD);
        this.useNeighborBrightness = true;
        this.setBurnable();
        this.setLightOpacity(255);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(OPEN, BlockOakShutters.EnumOpenPosition.CLOSED).withProperty(HINGE, BlockOakShutters.EnumHingePosition.LEFT));
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        state = state.getActualState(source, pos);
        boolean hinge = state.getValue(HINGE) == BlockOakShutters.EnumHingePosition.RIGHT;
        if(state.getValue(OPEN) == BlockOakShutters.EnumOpenPosition.FULL) {
        	return DoTBUtils.getRotatedAABB(hinge ? NORTH_FULL_RIGHT_AABB : NORTH_FULL_LEFT_AABB, state.getValue(FACING));
        } else return DoTBUtils.getRotatedAABB(NORTH_AABB, state.getValue(OPEN) == BlockOakShutters.EnumOpenPosition.CLOSED ? state.getValue(FACING) : (hinge ? state.getValue(FACING).rotateY() : state.getValue(FACING).rotateYCCW()));
    }

    @Override
    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
        return (state.getActualState(world, pos).getValue(OPEN) == BlockOakShutters.EnumOpenPosition.CLOSED) ? 255 : 0;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    /**
     * Determines if an entity can path through this blocks
     */
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return getMetaFromState(worldIn.getBlockState(pos)) < 8;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    private int getCloseSound() {
        return 1012;
    }

    private int getOpenSound() {
        return 1006;
    }

    @Override
    public EnumPushReaction getPushReaction(IBlockState state) {
        return EnumPushReaction.DESTROY;
    }
    
    /**
     * Get the MapColor for this Block and the given BlockState
     */
    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return BlockPlanks.EnumType.OAK.getMapColor();
    }

    private static BlockOakShutters.EnumOpenPosition getOpenPosition(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    	pos = pos.offset((state.getValue(HINGE) == BlockOakShutters.EnumHingePosition.LEFT) ? state.getValue(FACING).rotateY() : state.getValue(FACING).rotateYCCW());
    	boolean bot = (!(worldIn.getBlockState(pos).getBlock() instanceof BlockSmallOakShutters) && !(worldIn.getBlockState(pos).getBlock() instanceof BlockOakShutters)) && worldIn.getBlockState(pos).getCollisionBoundingBox(worldIn, pos) == Block.NULL_AABB;
    	return bot ? BlockOakShutters.EnumOpenPosition.FULL : BlockOakShutters.EnumOpenPosition.HALF;
    }
    
    /**
     * Called when the blocks is right clicked by a player.
     */
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (state.getBlock() instanceof BlockSmallOakShutters) {
            state = (state.getValue(OPEN) != BlockOakShutters.EnumOpenPosition.CLOSED) ? state.withProperty(OPEN, BlockOakShutters.EnumOpenPosition.CLOSED) : state.withProperty(OPEN, getOpenPosition(state, worldIn, pos));
            worldIn.setBlockState(pos, state, 10);
            worldIn.playEvent(playerIn, (state.getValue(OPEN) == BlockOakShutters.EnumOpenPosition.CLOSED) ? this.getCloseSound() : this.getOpenSound(), pos, 0);
            return true;
        } else return false;
    }

    /**
     * Called when a neighboring blocks was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * blocks, etc.
     */
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        EnumFacing direction = state.getValue(FACING).getOpposite();
        if (!canSupportShutters(worldIn, pos.offset(direction).offset(state.getValue(HINGE) == BlockOakShutters.EnumHingePosition.LEFT ? direction.rotateYCCW() : direction.rotateY()), direction, state.getValue(HINGE))) {
            worldIn.setBlockToAir(pos);
        	if (!worldIn.isRemote) this.dropBlockAsItem(worldIn, pos, state, 0);
        }
        if(state.getValue(OPEN) != BlockOakShutters.EnumOpenPosition.CLOSED) worldIn.setBlockState(pos, state.withProperty(OPEN, getOpenPosition(state, worldIn, pos)), 2);
    }
    
    private static boolean canSupportShutters(World worldIn, BlockPos pos, EnumFacing direction, BlockOakShutters.EnumHingePosition hinge) {
       	return worldIn.getBlockState(pos).getBlockFaceShape(worldIn, pos, direction) == BlockFaceShape.SOLID || worldIn.getBlockState(pos).getBlockFaceShape(worldIn, pos, (hinge == BlockOakShutters.EnumHingePosition.LEFT) ? direction.rotateYCCW() : direction.rotateY()) == BlockFaceShape.SOLID;
    }


	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		int i = facing.getXOffset();
        int j = facing.getZOffset();
		BlockOakShutters.EnumHingePosition hinge = canPlaceBlock(worldIn, pos, facing.getOpposite(), i < 0 && hitZ < 0.5F || i > 0 && hitZ > 0.5F || j < 0 && hitX > 0.5F || j > 0 && hitX < 0.5F);
		return hinge != null ? this.getDefaultState().withProperty(FACING, facing).withProperty(HINGE, hinge) : this.getDefaultState();
	}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
	    return canPlaceBlock(worldIn, pos, side.getOpposite(), false) != null;
	}
	
	private BlockOakShutters.EnumHingePosition canPlaceBlock(World worldIn, BlockPos pos, EnumFacing direction, boolean leftHinge) {
	    if (direction.getAxis() != Axis.Y) {
        	boolean canSupportLeft = canSupportShutters(worldIn, pos.offset(direction).offset(direction.rotateYCCW()), direction, BlockOakShutters.EnumHingePosition.LEFT);
        	if(canSupportShutters(worldIn, pos.offset(direction).offset(direction.rotateY()), direction, BlockOakShutters.EnumHingePosition.RIGHT)) {
            	if(canSupportLeft) return leftHinge ? BlockOakShutters.EnumHingePosition.LEFT : BlockOakShutters.EnumHingePosition.RIGHT;
            	else return BlockOakShutters.EnumHingePosition.RIGHT;
            }else if(canSupportLeft) return BlockOakShutters.EnumHingePosition.LEFT;
        }
		return null;
	}
    
    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return (state.getValue(OPEN) != BlockOakShutters.EnumOpenPosition.CLOSED) ? state.withProperty(OPEN, getOpenPosition(state, worldIn, pos)) : state;
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
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(OPEN, ((meta & 8) > 0) ? BlockOakShutters.EnumOpenPosition.HALF : BlockOakShutters.EnumOpenPosition.CLOSED).withProperty(HINGE, (meta & 4) > 0 ? BlockOakShutters.EnumHingePosition.RIGHT : BlockOakShutters.EnumHingePosition.LEFT).withProperty(FACING, EnumFacing.byHorizontalIndex(meta & 3).rotateYCCW());
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        if (state.getValue(OPEN) != BlockOakShutters.EnumOpenPosition.CLOSED) i |= 8;
        if (state.getValue(HINGE) == BlockOakShutters.EnumHingePosition.RIGHT) i |= 4;
        i |= state.getValue(FACING).rotateY().getHorizontalIndex();
        return i;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, HINGE, OPEN);
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
        return BlockFaceShape.UNDEFINED;
    }
}