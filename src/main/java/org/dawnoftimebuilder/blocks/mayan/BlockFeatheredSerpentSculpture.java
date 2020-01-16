package org.dawnoftimebuilder.blocks.mayan;

import net.minecraft.block.BlockHorizontal;
import org.dawnoftimebuilder.DoTBUtils;
import org.dawnoftimebuilder.blocks.general.DoTBBlock;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
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

public class BlockFeatheredSerpentSculpture extends DoTBBlock {
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
    private static final AxisAlignedBB FEATHERED_SERPENT_SCULPTURE_NORTH_AABB = new AxisAlignedBB(0.25D, 0.25D, 0.125D, 0.75D, 0.75D, 1.0D);
	
	public BlockFeatheredSerpentSculpture() {
		super("feathered_serpent_sculpture", Material.ROCK, 1.5F, SoundType.STONE);
	    this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	return DoTBUtils.getRotatedAABB(FEATHERED_SERPENT_SCULPTURE_NORTH_AABB, state.getValue(FACING));
    }
    
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
	    return canPlaceBlock(worldIn, pos, facing) ? this.getDefaultState().withProperty(FACING, facing) : this.getDefaultState();
	}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
	    return canPlaceBlock(worldIn, pos, side);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
	    for (EnumFacing enumfacing : FACING.getAllowedValues()) {
	        if (canPlaceBlock(worldIn, pos, enumfacing)) return true;
	    }
	    return false;
	}

	private boolean canPlaceBlock(World worldIn, BlockPos pos, EnumFacing direction) {
	    BlockPos blockpos = pos.offset(direction.getOpposite());
	    IBlockState iblockstate = worldIn.getBlockState(blockpos);
	    BlockFaceShape shape = iblockstate.getBlockFaceShape(worldIn, blockpos, direction);
	    Block block = iblockstate.getBlock();
	    
	    if (direction != EnumFacing.UP && direction != EnumFacing.DOWN) {
	    	return !isExceptBlockForAttachWithPiston(block) && shape == BlockFaceShape.SOLID;
        } else return false;
	}
	
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.checkForDrop(worldIn, pos, state);
    }
    
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos){
		if (this.checkForDrop(worldIn, pos, state)) {
			EnumFacing facing = state.getValue(FACING);
			if (worldIn.getBlockState(pos.offset(facing.getOpposite())).getBlockFaceShape(worldIn, pos.offset(facing), facing) != BlockFaceShape.SOLID) {
				this.dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
			}
		}
    }

    private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
        if (state.getBlock() == this && this.canPlaceBlock(worldIn, pos, state.getValue(FACING))) return true;
        else {
            if (worldIn.getBlockState(pos).getBlock() == this){
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
            }
            return false;
        }
    }
	
	@Override
	protected BlockStateContainer createBlockState()
	{
	    return new BlockStateContainer(this, FACING);
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
