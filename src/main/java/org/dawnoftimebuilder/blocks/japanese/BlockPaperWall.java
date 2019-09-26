package org.dawnoftimebuilder.blocks.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.blocks.general.DoTBBlockPane;

public class BlockPaperWall extends DoTBBlockPane {
	
    private static final PropertyBool BOTTOM = PropertyBool.create("wallbottom");

	public BlockPaperWall(String name) {
		super(name, Material.CLOTH);
		this.setBurnable();
	}

	@Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH, BOTTOM);
    }
    
	@Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return super.getActualState(state, worldIn, pos).withProperty(BOTTOM, this.isBottom(worldIn, pos));
    }
    
	private boolean isBottom(IBlockAccess worldIn, BlockPos pos){
		
		Block blockBottom = worldIn.getBlockState(pos.down()).getBlock();
		Block blockUp = worldIn.getBlockState(pos.up()).getBlock();
		
		return !(blockBottom instanceof BlockPaperWall) && !(blockBottom instanceof BlockFloweryPaperWall) && (blockUp instanceof BlockPaperWall || blockUp instanceof BlockFloweryPaperWall);
	}
	
    @SideOnly(Side.CLIENT)
	@Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}
