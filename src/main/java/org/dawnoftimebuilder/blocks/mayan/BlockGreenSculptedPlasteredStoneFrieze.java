package org.dawnoftimebuilder.blocks.mayan;

import java.util.List;

import javax.annotation.Nullable;

import org.dawnoftimebuilder.DoTBUtils;
import org.dawnoftimebuilder.blocks.general.DoTBBlock;
import org.dawnoftimebuilder.enums.EnumsBlock;

import com.google.common.collect.Lists;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGreenSculptedPlasteredStoneFrieze extends DoTBBlock {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static final PropertyEnum<EnumsBlock.EnumStairsShape> SHAPE = PropertyEnum.create("shape", EnumsBlock.EnumStairsShape.class);
    private static final AxisAlignedBB AABB_QTR_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 0.5D);
    private static final AxisAlignedBB AABB_OCT_NW = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 0.25D, 0.5D);
    private static final AxisAlignedBB AABB_SPIKE_NORTH = new AxisAlignedBB(0.28125D, 0.25D, 0.125D, 0.71875D, 1.0D, 0.25D);
    private static final AxisAlignedBB AABB_SPIKE_NW = new AxisAlignedBB(0.0D, 0.25D, 0.0D, 0.375D, 1.0D, 0.375D);
    
    public BlockGreenSculptedPlasteredStoneFrieze() {
    	super("green_sculpted_plastered_stone_frieze", Material.ROCK, 1.5F, SoundType.STONE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(SHAPE, EnumsBlock.EnumStairsShape.STRAIGHT));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, SHAPE);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        if (!p_185477_7_) state = this.getActualState(state, worldIn, pos);
        for (AxisAlignedBB axisalignedbb : getCollisionBoxList(state)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, axisalignedbb);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	return DoTBUtils.getMainAABB(getCollisionBoxList(this.getActualState(state, source, pos)));
    }

    private static List<AxisAlignedBB> getCollisionBoxList(IBlockState state) {
        List<AxisAlignedBB> list = Lists.newArrayList();
        EnumFacing facing = state.getValue(FACING);
        EnumsBlock.EnumStairsShape shape = state.getValue(SHAPE);
        switch (shape) {
        	default:
            case STRAIGHT:
            	list.add(DoTBUtils.getRotatedAABB(AABB_QTR_NORTH, facing));
            	list.add(DoTBUtils.getRotatedAABB(AABB_SPIKE_NORTH, facing));
            	break;
                
            case OUTER_LEFT:
            	list.add(DoTBUtils.getRotatedAABB(AABB_OCT_NW, facing));
            	list.add(DoTBUtils.getRotatedAABB(AABB_SPIKE_NW, facing));
                break;
                
            case OUTER_RIGHT:
            	list.add(DoTBUtils.getRotatedAABB(AABB_OCT_NW, facing.rotateY()));
            	list.add(DoTBUtils.getRotatedAABB(AABB_SPIKE_NW, facing.rotateY()));
                break;
            
            case INNER_LEFT:
            	list.add(DoTBUtils.getRotatedAABB(AABB_QTR_NORTH, facing));
            	list.add(DoTBUtils.getRotatedAABB(AABB_OCT_NW, facing.rotateYCCW()));
            	list.add(DoTBUtils.getRotatedAABB(AABB_SPIKE_NORTH, facing));
            	list.add(DoTBUtils.getRotatedAABB(AABB_SPIKE_NORTH, facing.rotateYCCW()));
                break;
                
            case INNER_RIGHT:
            	list.add(DoTBUtils.getRotatedAABB(AABB_QTR_NORTH, facing));
            	list.add(DoTBUtils.getRotatedAABB(AABB_OCT_NW, facing.getOpposite()));
            	list.add(DoTBUtils.getRotatedAABB(AABB_SPIKE_NORTH, facing));
            	list.add(DoTBUtils.getRotatedAABB(AABB_SPIKE_NORTH, facing.rotateY()));
            	break;
        }
        return list;
    }
    
	@Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
        return BlockFaceShape.UNDEFINED;
    }
    
    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    	return state.withProperty(SHAPE, getGreenStoneFriezeShape(state, worldIn, pos));
    }
    
    private static EnumsBlock.EnumStairsShape getGreenStoneFriezeShape(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        EnumFacing enumfacing = state.getValue(FACING);
        IBlockState stateAdjacent = worldIn.getBlockState(pos.offset(enumfacing));

        if (isBlockGreenStoneFrieze(stateAdjacent)) {
            EnumFacing facing = stateAdjacent.getValue(FACING);
            if (facing.getAxis() != (state.getValue(FACING)).getAxis() && isDifferentGreenStoneFrieze(state, worldIn, pos, facing.getOpposite())) {
                if (facing == enumfacing.rotateYCCW()) return EnumsBlock.EnumStairsShape.OUTER_LEFT;
                return EnumsBlock.EnumStairsShape.OUTER_RIGHT;
            }
        }

        stateAdjacent = worldIn.getBlockState(pos.offset(enumfacing.getOpposite()));

        if (isBlockGreenStoneFrieze(stateAdjacent)) {
            EnumFacing enumfacing2 = stateAdjacent.getValue(FACING);
            if (enumfacing2.getAxis() != (state.getValue(FACING)).getAxis() && isDifferentGreenStoneFrieze(state, worldIn, pos, enumfacing2)) {
                if (enumfacing2 == enumfacing.rotateYCCW()) return EnumsBlock.EnumStairsShape.INNER_LEFT;
                return EnumsBlock.EnumStairsShape.INNER_RIGHT;
            }
        }
        
        return EnumsBlock.EnumStairsShape.STRAIGHT;
    }
    
    private static boolean isDifferentGreenStoneFrieze(IBlockState p_185704_0_, IBlockAccess p_185704_1_, BlockPos p_185704_2_, EnumFacing p_185704_3_) {
        IBlockState iblockstate = p_185704_1_.getBlockState(p_185704_2_.offset(p_185704_3_));
        return !isBlockGreenStoneFrieze(iblockstate) || iblockstate.getValue(FACING) != p_185704_0_.getValue(FACING);
    }
    
    private static boolean isBlockGreenStoneFrieze(IBlockState state) {
        return state.getBlock() instanceof BlockGreenSculptedPlasteredStoneFrieze;
    }
    
	@Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()).withProperty(SHAPE, EnumsBlock.EnumStairsShape.STRAIGHT);
    }
    
	@Override
	public int getMetaFromState(IBlockState state){
		return state.getValue(FACING).getHorizontalIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta){
		return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
	}
    
	@Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

	@Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        EnumFacing enumfacing = state.getValue(FACING);
        EnumsBlock.EnumStairsShape shape = state.getValue(SHAPE);

        switch (mirrorIn) {
            case LEFT_RIGHT:
                if (enumfacing.getAxis() == EnumFacing.Axis.Z) {
                    switch (shape) {
                        case OUTER_LEFT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumsBlock.EnumStairsShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumsBlock.EnumStairsShape.OUTER_LEFT);
                        case INNER_RIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumsBlock.EnumStairsShape.INNER_LEFT);
                        case INNER_LEFT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumsBlock.EnumStairsShape.INNER_RIGHT);
                        default:
                            return state.withRotation(Rotation.CLOCKWISE_180);
                    }
                }
                break;
                
            case FRONT_BACK:
                if (enumfacing.getAxis() == EnumFacing.Axis.X) {
                    switch (shape) {
                        case OUTER_LEFT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumsBlock.EnumStairsShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumsBlock.EnumStairsShape.OUTER_LEFT);
                        case INNER_RIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumsBlock.EnumStairsShape.INNER_RIGHT);
                        case INNER_LEFT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumsBlock.EnumStairsShape.INNER_LEFT);
                        case STRAIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180);
                    }
                }
        }

        return state;
    }
}