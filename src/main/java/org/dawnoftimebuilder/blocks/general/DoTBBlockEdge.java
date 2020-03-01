package org.dawnoftimebuilder.blocks.general;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.SoundType;
import com.google.common.collect.Lists;

import net.minecraft.block.BlockHorizontal;
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
import org.dawnoftimebuilder.DoTBUtils;
import org.dawnoftimebuilder.enums.EnumsBlock;

public class DoTBBlockEdge extends DoTBBlock {
	
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<EnumsBlock.EnumHalf> HALF = PropertyEnum.create("half", EnumsBlock.EnumHalf.class);
    private static final PropertyEnum<EnumsBlock.EnumStairsShape> SHAPE = PropertyEnum.create("shape", EnumsBlock.EnumStairsShape.class);
    
    private static final AxisAlignedBB AABB_QTR_BOT_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 0.5D);
    private static final AxisAlignedBB AABB_OCT_BOT_NW = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 0.5D, 0.5D);

    public DoTBBlockEdge(String name, Material materialIn, float hardness, SoundType sound) {
    	super(name, materialIn, hardness, sound);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(HALF, EnumsBlock.EnumHalf.BOTTOM).withProperty(SHAPE, EnumsBlock.EnumStairsShape.STRAIGHT));
        this.useNeighborBrightness = true;
    }

    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, FACING, HALF, SHAPE);
    }
    
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        if (!p_185477_7_) state = this.getActualState(state, worldIn, pos);
        for (AxisAlignedBB axisalignedbb : getCollisionBoxList(state)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, axisalignedbb);
        }
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	return DoTBUtils.getMainAABB(getCollisionBoxList(this.getActualState(state, source, pos)));
    }

    private static List<AxisAlignedBB> getCollisionBoxList(IBlockState state) {
        List<AxisAlignedBB> list = Lists.newArrayList();
        EnumFacing facing = state.getValue(FACING);
        EnumsBlock.EnumStairsShape shape = state.getValue(SHAPE);
        double offsetY = (state.getValue(HALF) == EnumsBlock.EnumHalf.BOTTOM) ? 0.0D : 0.5D;
        switch (shape) {
        	default:
            case STRAIGHT:
            	list.add(DoTBUtils.getRotatedAABB(AABB_QTR_BOT_NORTH, facing).offset(0.0D, offsetY, 0.0D));
                break;
                
            case OUTER_LEFT:
            	list.add(DoTBUtils.getRotatedAABB(AABB_OCT_BOT_NW, facing).offset(0.0D, offsetY, 0.0D));
                break;
                
            case OUTER_RIGHT:
            	list.add(DoTBUtils.getRotatedAABB(AABB_OCT_BOT_NW, facing.rotateY()).offset(0.0D, offsetY, 0.0D));
                break;
            
            case INNER_LEFT:
            	list.add(DoTBUtils.getRotatedAABB(AABB_QTR_BOT_NORTH, facing).offset(0.0D, offsetY, 0.0D));
            	list.add(DoTBUtils.getRotatedAABB(AABB_OCT_BOT_NW, facing.rotateYCCW()).offset(0.0D, offsetY, 0.0D));
                break;
                
            case INNER_RIGHT:
            	list.add(DoTBUtils.getRotatedAABB(AABB_QTR_BOT_NORTH, facing).offset(0.0D, offsetY, 0.0D));
            	list.add(DoTBUtils.getRotatedAABB(AABB_OCT_BOT_NW, facing.getOpposite()).offset(0.0D, offsetY, 0.0D));
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
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(SHAPE, getSmallPlateShape(state, worldIn, pos));
    }
	
    private static EnumsBlock.EnumStairsShape getSmallPlateShape(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);
        IBlockState adjacentState = worldIn.getBlockState(pos.offset(facing));
        if (isBlockEdge(adjacentState) && state.getValue(HALF) == adjacentState.getValue(HALF)) {
            EnumFacing adjacentFacing = adjacentState.getValue(FACING);

            if (adjacentFacing.getAxis() != (state.getValue(FACING)).getAxis() && isDifferentBlockEdge(state, worldIn, pos, adjacentFacing.getOpposite())) {
                if (adjacentFacing == facing.rotateYCCW()) return EnumsBlock.EnumStairsShape.OUTER_LEFT;
                return EnumsBlock.EnumStairsShape.OUTER_RIGHT;
            }
        }

        adjacentState = worldIn.getBlockState(pos.offset(facing.getOpposite()));
        if (isBlockEdge(adjacentState) && state.getValue(HALF) == adjacentState.getValue(HALF)){
            EnumFacing adjacentFacing = adjacentState.getValue(FACING);

            if (adjacentFacing.getAxis() != (state.getValue(FACING)).getAxis() && isDifferentBlockEdge(state, worldIn, pos, adjacentFacing)) {
                if (adjacentFacing == facing.rotateYCCW()) return EnumsBlock.EnumStairsShape.INNER_LEFT;
                return EnumsBlock.EnumStairsShape.INNER_RIGHT;
            }
        }

        return EnumsBlock.EnumStairsShape.STRAIGHT;
    }

    private static boolean isDifferentBlockEdge(IBlockState state, IBlockAccess worldIn, BlockPos pos, EnumFacing facing) {
        IBlockState adjacentState = worldIn.getBlockState(pos.offset(facing));
        return !isBlockEdge(adjacentState) || adjacentState.getValue(FACING) != state.getValue(FACING) || adjacentState.getValue(HALF) != state.getValue(HALF);
    }

    private static boolean isBlockEdge(IBlockState state) {
        return state.getBlock() instanceof DoTBBlockEdge;
    }
    
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        IBlockState iblockstate = super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
        iblockstate = iblockstate.withProperty(FACING, placer.getHorizontalFacing()).withProperty(SHAPE, EnumsBlock.EnumStairsShape.STRAIGHT);
        return facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double)hitY <= 0.5D) ? iblockstate.withProperty(HALF, EnumsBlock.EnumHalf.BOTTOM) : iblockstate.withProperty(HALF, EnumsBlock.EnumHalf.TOP);
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        if (state.getValue(HALF) == EnumsBlock.EnumHalf.TOP) i = 4;
        i += state.getValue(FACING).getHorizontalIndex();
        return i;
    }

	@Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState();
        if(meta >= 4){
            iblockstate = iblockstate.withProperty(HALF, EnumsBlock.EnumHalf.TOP);
            meta -= 4;
        }
        return iblockstate.withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
    }

	@Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

	@Override
    public boolean isFullCube(IBlockState state){
        return false;
    }

    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("incomplete-switch")
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        EnumFacing enumfacing = state.getValue(FACING);
        EnumsBlock.EnumStairsShape shape = state.getValue(SHAPE);

        switch (mirrorIn){
            case LEFT_RIGHT:
                if (enumfacing.getAxis() == EnumFacing.Axis.Z){
                    switch (shape){
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
                if (enumfacing.getAxis() == EnumFacing.Axis.X){
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
