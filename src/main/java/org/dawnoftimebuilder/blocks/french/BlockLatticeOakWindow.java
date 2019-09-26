package org.dawnoftimebuilder.blocks.french;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.blocks.general.DoTBBlock;

public class BlockLatticeOakWindow extends DoTBBlock {

    private static final PropertyBool AXISX = PropertyBool.create("axisx");
    private static final PropertyBool TOP = PropertyBool.create("top");
    private static final PropertyBool SIDE = PropertyBool.create("side");
    private static final AxisAlignedBB X_AXIS_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D);
    private static final AxisAlignedBB Z_AXIS_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D);
	
	public BlockLatticeOakWindow() {
		super("lattice_oak_window", Material.GLASS, 0.8F, SoundType.GLASS);
	    this.setBurnable();
	    this.setDefaultState(this.blockState.getBaseState().withProperty(AXISX, true).withProperty(TOP, false).withProperty(SIDE, false));
	}

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return state.getValue(AXISX) ? X_AXIS_AABB : Z_AXIS_AABB;
    }
	
	@Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        IBlockState state = this.getDefaultState().withProperty(AXISX, placer.getHorizontalFacing().getAxis() == EnumFacing.Axis.X);
		return state.withProperty(TOP, canConnectVertical(worldIn, pos)).withProperty(SIDE, canConnectHorizontal(state, worldIn, pos));
    }

    /**
     * Called when a neighboring blocks was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * blocks, etc.
     */
	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		boolean changeTOP = canConnectVertical(worldIn, pos);
		boolean changeSIDE = canConnectHorizontal(state, worldIn, pos);
		IBlockState newState = state;
		if(changeTOP != state.getValue(TOP)) newState = newState.withProperty(TOP, changeTOP);
		if(changeSIDE != state.getValue(SIDE)) newState = newState.withProperty(SIDE, changeSIDE);
        if(changeTOP != state.getValue(TOP) || changeSIDE != state.getValue(SIDE)) worldIn.setBlockState(pos, newState, 10);
    }
	
    private boolean canConnectVertical(World worldIn, BlockPos pos) {
    	if(worldIn.getBlockState(pos.down()).getBlock() instanceof BlockLatticeOakWindow){
			return !(worldIn.getBlockState(pos.up()).getBlock() instanceof BlockLatticeOakWindow);
    	}
    	return false;
    }
    
    private boolean canConnectHorizontal(IBlockState state, World worldIn, BlockPos pos) {
    	pos = pos.offset((state.getValue(AXISX)) ? EnumFacing.NORTH : EnumFacing.WEST);
    	if(worldIn.getBlockState(pos).getBlock() instanceof BlockLatticeOakWindow){
			return worldIn.getBlockState(pos).getValue(AXISX) == state.getValue(AXISX);
    	}
    	return false;
    }
    
	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, AXISX, TOP, SIDE);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
	    return false;
	}

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
	
	@Override
	public boolean isFullCube(IBlockState state) {
	    return false;
	}

	@Override
	public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
		return false;
	}

	@Override
	public int getMetaFromState(IBlockState state){
        int i = (state.getValue(AXISX)) ? 1 : 0;
        if (state.getValue(TOP)) i |= 2;
        if (state.getValue(SIDE)) i |= 4;
        return i;
	}

	@Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(AXISX, (meta & 1) > 0).withProperty(TOP, (meta & 2) > 0).withProperty(SIDE, (meta & 4) > 0);
    }

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		boolean axisX = state.getValue(AXISX);
	    return state.withProperty(AXISX, (rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90) != axisX);
	}
	
	@Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState state, BlockPos p_193383_3_, EnumFacing facing) {
		if(facing.getAxis() == EnumFacing.Axis.Y) return BlockFaceShape.UNDEFINED;
		return (state.getValue(AXISX) == (facing.getAxis() == EnumFacing.Axis.X)) ? BlockFaceShape.MIDDLE_POLE_THIN : BlockFaceShape.UNDEFINED;
    }
}
