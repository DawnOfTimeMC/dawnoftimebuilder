package org.dawnoftimebuilder.blocks.japanese;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
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
import org.dawnoftimebuilder.DoTBUtils;
import org.dawnoftimebuilder.blocks.IBlockSpecialDisplay;
import org.dawnoftimebuilder.blocks.general.BlockIronChain;
import org.dawnoftimebuilder.blocks.general.DoTBBlock;

public class BlockStoneLantern extends DoTBBlock implements IBlockSpecialDisplay {

	private AxisAlignedBB SIDE_AABB = new AxisAlignedBB(0.125F, 0.0F, 0.125F, 0.875F, 1.0F, 1.0F);
	private AxisAlignedBB VERTICAL_AABB = new AxisAlignedBB(0.125F, 0.0F, 0.125F, 0.875F, 1.0F, 0.875F);
	private static final PropertyBool CHAIN = PropertyBool.create("chain");
	public static final PropertyDirection FACING = PropertyDirection.create("facing");

	public BlockStoneLantern() {
		super("stone_lantern", Material.ROCK, 2.0F, SoundType.STONE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.DOWN).withProperty(CHAIN, false));
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return 14;
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return (this.getActualState(state, source, pos).getValue(FACING).getAxis() == EnumFacing.Axis.Y) ? VERTICAL_AABB : DoTBUtils.getRotatedAABB(SIDE_AABB, state.getValue(FACING));
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies properties not visible in the
	 * metadata, such as fence connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(CHAIN, worldIn.getBlockState(pos.up()).getBlock() instanceof BlockIronChain);
	}

	@Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return canPlaceBlock(worldIn, pos, facing) ? this.getDefaultState().withProperty(FACING, facing) : this.getDefaultState().withProperty(FACING, EnumFacing.DOWN);
    }
    
	@Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
        return canPlaceBlock(worldIn, pos, side);
    }

	@Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        for(EnumFacing enumfacing : EnumFacing.values()) {
            if(canPlaceBlock(worldIn, pos, enumfacing)) return true;
        }

        return false;
    }
    
    private static boolean canPlaceBlock(World worldIn, BlockPos pos, EnumFacing direction){
        BlockPos blockpos = pos.offset(direction.getOpposite());
        IBlockState iblockstate = worldIn.getBlockState(blockpos);
        BlockFaceShape shape = iblockstate.getBlockFaceShape(worldIn, blockpos, direction);

        return shape != BlockFaceShape.UNDEFINED;

    }
    
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing side){
    	
    	if(side == state.getValue(FACING).getOpposite()){
    		return BlockFaceShape.SOLID;
    	}
    	
    	return BlockFaceShape.UNDEFINED;
    	
    }
    
	@Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, CHAIN);
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
    	return state.getValue(FACING).getIndex();
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta){
    	return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta));
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
	public float getDisplayScale(int meta) {
		return 0.5f;
	}
}
