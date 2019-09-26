package org.dawnoftimebuilder.blocks.japanese;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.blocks.IBlockSpecialDisplay;
import org.dawnoftimebuilder.blocks.general.DoTBBlock;

public class BlockPaperLantern extends DoTBBlock implements IBlockSpecialDisplay {

    private AxisAlignedBB AABB = new AxisAlignedBB(0.25F, 0.0F, 0.25F, 0.75F, 0.9F, 0.75F);

	public BlockPaperLantern() {
		super("paper_lantern", Material.CLOTH);
		this.setBurnable();
	}

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return 10;
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
    
    @Override
    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
    	return false;
    }

	@Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing facing) {
        return facing == EnumFacing.UP ? BlockFaceShape.MIDDLE_POLE_THIN : BlockFaceShape.UNDEFINED;
    }
    
	@Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return !worldIn.getBlockState(pos.offset(EnumFacing.UP)).getBlock().isPassable(worldIn, pos);
    }
	
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    
    @Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
		return AABB;
    }

	@Override
	public float getDisplayScale(int meta) {
		return 0.4f;
	}
}
