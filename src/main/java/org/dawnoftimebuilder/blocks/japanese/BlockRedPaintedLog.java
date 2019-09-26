package org.dawnoftimebuilder.blocks.japanese;

import java.util.List;

import javax.annotation.Nullable;

import org.dawnoftimebuilder.DoTBUtils;
import org.dawnoftimebuilder.blocks.general.DoTBBlock;
import com.google.common.collect.Lists;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedPaintedLog extends DoTBBlock {

	private static final AxisAlignedBB AABB_AXIS_X = new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 0.8125D, 0.625D, 1.0D);
	private static final AxisAlignedBB AABB_AXIS_Y = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
	private static final AxisAlignedBB AABB_AXIS_Z = new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 1.0D, 0.625D, 0.8125D);

    private static final PropertyEnum<EnumFacing.Axis> MAIN_AXIS = PropertyEnum.create("main_axis", EnumFacing.Axis.class);
	private static final PropertyBool SUBAXIS_X = PropertyBool.create("subaxis_x");
	private static final PropertyBool SUBAXIS_Z = PropertyBool.create("subaxis_z");
	
	public BlockRedPaintedLog() {
		super("red_painted_log", Material.WOOD, 2.0F, SoundType.WOOD);
		this.setBurnable();
		this.setDefaultState(this.blockState.getBaseState().withProperty(MAIN_AXIS, EnumFacing.Axis.Y).withProperty(SUBAXIS_X, false).withProperty(SUBAXIS_Z, false));
	}
	
	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, MAIN_AXIS, SUBAXIS_X, SUBAXIS_Z);
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
        switch (state.getValue(MAIN_AXIS)) {
    		case X:
    			list.add(AABB_AXIS_Z);
    			break;
    		case Y:
    		default:
    			list.add(AABB_AXIS_Y);
    			break;
    		case Z:
    			list.add(AABB_AXIS_X);
    			break;
        }
        if (state.getValue(SUBAXIS_X)) list.add(AABB_AXIS_Z);
        if (state.getValue(SUBAXIS_Z)) list.add(AABB_AXIS_X);
        return list;
    }
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
	    return this.getDefaultState().withProperty(MAIN_AXIS, facing.getAxis());
	}
    
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing side){
		return (side == EnumFacing.DOWN && state.getValue(MAIN_AXIS) == EnumFacing.Axis.Y) ? BlockFaceShape.CENTER_BIG : BlockFaceShape.UNDEFINED;
	}
	
    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    	boolean subaxisX = false;
    	boolean subaxisZ = false;

        switch (state.getValue(MAIN_AXIS)) {
    		case X:
    			if(isPaintedLog(worldIn, pos.north()) || isPaintedLog(worldIn, pos.south())) subaxisZ = true;
    			break;
    		case Z:
    			if(isPaintedLog(worldIn, pos.east()) || isPaintedLog(worldIn, pos.west())) subaxisX = true;
                break;
            case Y:
            default:
                if(isPaintedLog(worldIn, pos.east()) || isPaintedLog(worldIn, pos.west())) subaxisX = true;
                if(isPaintedLog(worldIn, pos.north()) || isPaintedLog(worldIn, pos.south())) subaxisZ = true;
                break;
        }
    	return state.withProperty(SUBAXIS_X, subaxisX).withProperty(SUBAXIS_Z, subaxisZ);
    }
	
	private boolean isPaintedLog(IBlockAccess world, BlockPos pos){
		return world.getBlockState(pos).getBlock() instanceof BlockRedPaintedLog;
	}
    
	@Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        EnumFacing.Axis axis = state.getValue(MAIN_AXIS);
        
        if (axis == EnumFacing.Axis.X) i |= 4;
        else if (axis == EnumFacing.Axis.Z) i |= 8;
        
        return i;
    }
	
    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing.Axis axis = EnumFacing.Axis.Y;
        int i = meta & 12;

        if (i == 4) axis = EnumFacing.Axis.X;
        else if (i == 8) axis = EnumFacing.Axis.Z;
        
        return this.getDefaultState().withProperty(MAIN_AXIS, axis);
    }
	
	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
	
	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
}
