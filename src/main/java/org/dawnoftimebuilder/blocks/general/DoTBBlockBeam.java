package org.dawnoftimebuilder.blocks.general;

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
import org.dawnoftimebuilder.DoTBUtils;
import org.dawnoftimebuilder.enums.EnumsBlock;

import javax.annotation.Nullable;
import java.util.List;

public class DoTBBlockBeam extends DoTBBlock {

	private static final AxisAlignedBB AABB_AXIS_X = new AxisAlignedBB(0.0D, 0.25D, 0.25D, 1.0D, 0.75D, 0.75D);
	private static final AxisAlignedBB AABB_AXIS_Y = new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 1.0D, 0.8125D);
	private static final AxisAlignedBB AABB_AXIS_Z = new AxisAlignedBB(0.25D, 0.25D, 0.0D, 0.75D, 0.75D, 1.0D);
	private static final AxisAlignedBB AABB_BOTTOM = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.25D, 0.875D);

	private static final PropertyBool BOTTOM = PropertyBool.create("bottom");
    static final PropertyEnum<EnumFacing.Axis> MAIN_AXIS = PropertyEnum.create("main_axis", EnumFacing.Axis.class);
	private static final PropertyBool SUBAXIS_X = PropertyBool.create("subaxis_x");
	private static final PropertyBool SUBAXIS_Z = PropertyBool.create("subaxis_z");

	public DoTBBlockBeam(String name, Material materialIn, float hardness, SoundType sound) {
		super(name, materialIn, hardness, sound);
		this.setDefaultState(this.blockState.getBaseState().withProperty(BOTTOM, false).withProperty(MAIN_AXIS, EnumFacing.Axis.Y).withProperty(SUBAXIS_X, false).withProperty(SUBAXIS_Z, false));
	}
	
	@Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BOTTOM, MAIN_AXIS, SUBAXIS_X, SUBAXIS_Z);
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
    			list.add(AABB_AXIS_X);
    			break;
    		case Y:
    		default:
    			list.add(AABB_AXIS_Y);
    			break;
    		case Z:
    			list.add(AABB_AXIS_Z);
    			break;
        }
		if (state.getValue(BOTTOM)) list.add(AABB_BOTTOM);
        if (state.getValue(SUBAXIS_X)) list.add(AABB_AXIS_X);
        if (state.getValue(SUBAXIS_Z)) list.add(AABB_AXIS_Z);
        return list;
    }
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
	    return this.getDefaultState().withProperty(MAIN_AXIS, facing.getAxis());
	}
    
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing side){
		if(side.getAxis().isVertical() && state.getValue(MAIN_AXIS).isVertical()) return BlockFaceShape.CENTER_BIG;
		if(side.getAxis() == EnumFacing.Axis.X){
			if(state.getValue(MAIN_AXIS) == EnumFacing.Axis.X || state.getValue(SUBAXIS_X)) return BlockFaceShape.CENTER_BIG;
		}
		if(side.getAxis() == EnumFacing.Axis.Z){
			if(state.getValue(MAIN_AXIS) == EnumFacing.Axis.Z || state.getValue(SUBAXIS_Z)) return BlockFaceShape.CENTER_BIG;
		}
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(MAIN_AXIS).isVertical();
	}

    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    	if(state.getValue(MAIN_AXIS).isHorizontal()) return state;
    	else{
			boolean bottom = false;
			boolean subAxisX = false;
			boolean subAxisZ = false;
			if(canConnect(worldIn, pos, EnumFacing.EAST) || canConnect(worldIn, pos, EnumFacing.WEST)) subAxisX = true;
			if(canConnect(worldIn, pos, EnumFacing.NORTH) || canConnect(worldIn, pos, EnumFacing.SOUTH)) subAxisZ = true;
			if(!isConnectibleBeam(worldIn.getBlockState(pos.down()), EnumFacing.DOWN)) bottom = true;
			return state.withProperty(BOTTOM, bottom).withProperty(SUBAXIS_X, subAxisX).withProperty(SUBAXIS_Z, subAxisZ);
		}
    }

    private boolean canConnect(IBlockAccess world, BlockPos pos, EnumFacing direction){
    	IBlockState state = world.getBlockState(pos.offset(direction));
    	return isConnectibleBeam(state, direction) || isConnectibleSupportBeam(state, direction) || isConnectibleBeam(state, direction) || isConnectibleSupportBeam(state, direction);
	}

	private boolean isConnectibleBeam(IBlockState state, EnumFacing direction){
    	if(state.getBlock() instanceof DoTBBlockBeam) return state.getValue(MAIN_AXIS) == direction.getAxis() || state.getValue(MAIN_AXIS) == EnumFacing.Axis.Y;
    	else return false;
	}

	private boolean isConnectibleSupportBeam(IBlockState state, EnumFacing direction){
    	if(state.getBlock() instanceof DoTBBlockSupportBeam) return state.getValue(DoTBBlockSupportBeam.AXIS) == EnumsBlock.EnumHorizontalAxis.getHorizontalAxis(direction);
		else return false;
	}

	@Override
    public int getMetaFromState(IBlockState state) {
    	switch (state.getValue(MAIN_AXIS)) {
			default:
    		case Y:
				return 0;
			case X:
				return 1;
			case Z:
				return 2;
		}
    }
	
    @Override
    public IBlockState getStateFromMeta(int meta) {
		switch (meta) {
			default:
				return this.getDefaultState();
			case 1:
				return this.getDefaultState().withProperty(MAIN_AXIS, EnumFacing.Axis.X);
			case 2:
				return this.getDefaultState().withProperty(MAIN_AXIS, EnumFacing.Axis.Z);
		}
    }
	
	@Override
    public boolean isFullCube(IBlockState state){
        return false;
    }
	
	@Override
    public boolean isOpaqueCube(IBlockState state){
        return false;
    }
}
