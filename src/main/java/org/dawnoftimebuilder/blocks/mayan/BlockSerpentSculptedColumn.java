package org.dawnoftimebuilder.blocks.mayan;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.BlockHorizontal;
import org.dawnoftimebuilder.DoTBUtils;
import org.dawnoftimebuilder.blocks.general.DoTBBlock;

import com.google.common.collect.Lists;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSerpentSculptedColumn extends DoTBBlock {
	
	private static final PropertyInteger PILLAR = PropertyInteger.create("pillar", 0, 3);
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
    private static final AxisAlignedBB HEAD_NORTH_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.0D, 0.75D, 0.6875D, 0.5625D);
    private static final AxisAlignedBB TAIL_NORTH_AABB = new AxisAlignedBB(0.3125D, 0.0D, 0.625D, 0.6875D, 1.0D, 1.0D);
    private static final AxisAlignedBB SMALL_TAIL_NORTH_AABB = new AxisAlignedBB(0.3125D, 0.0D, 0.625D, 0.6875D, 0.375D, 1.0D);
    private static final AxisAlignedBB END_TAIL_NORTH_AABB = new AxisAlignedBB(0.3125D, 0.625D, 0.0625D, 0.6875D, 1.0D, 0.625D);
    
	public BlockSerpentSculptedColumn() {
		super("serpent_sculpted_column", Material.ROCK, 1.5F, SoundType.STONE);
	    this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, FACING, PILLAR);
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
        switch (state.getValue(PILLAR)) {
        	default:
            case 0:
            	list.add(DoTBUtils.getRotatedAABB(TAIL_NORTH_AABB, facing));
            	list.add(DoTBUtils.getRotatedAABB(END_TAIL_NORTH_AABB, facing));
                break;
                
            case 1:
            	list.add(DoTBUtils.getRotatedAABB(TAIL_NORTH_AABB, facing));
                break;
            case 3:
            	list.add(DoTBUtils.getRotatedAABB(HEAD_NORTH_AABB, facing));
            	list.add(DoTBUtils.getRotatedAABB(SMALL_TAIL_NORTH_AABB, facing));
                break;
            
            case 2:
            	list.add(DoTBUtils.getRotatedAABB(TAIL_NORTH_AABB, facing));
            	list.add(DoTBUtils.getRotatedAABB(HEAD_NORTH_AABB, facing));
        }
        return list;
    }
    
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
	    return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
    
	@Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos){
    	return state.withProperty(PILLAR, getTileIndex(state, worldIn, pos));
    }
    
	private int getTileIndex(IBlockState state, IBlockAccess worldIn, BlockPos pos){
		IBlockState up = worldIn.getBlockState(pos.up());
		IBlockState down = worldIn.getBlockState(pos.down());
		EnumFacing facing = state.getValue(FACING);
		boolean blockUp = (up.getBlock() instanceof BlockSerpentSculptedColumn) && up.getValue(FACING) == facing;
		boolean blockDown = (down.getBlock() instanceof BlockSerpentSculptedColumn) && down.getValue(FACING) == facing;
		
		return (blockUp && blockDown) ? 1 : (!blockUp && !blockDown) ? 3 : blockDown ? 0 : 2;
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

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getRenderLayer()
	{
	    return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
		return false;
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
	public IBlockState withRotation(IBlockState state, Rotation rot) {
	    return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
	    return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}
	
	@Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
        return BlockFaceShape.UNDEFINED;
    }
}
