package org.dawnoftimebuilder.blocks.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.dawnoftimebuilder.blocks.general.DoTBBlockPane;

public class BlockFloweryPaperWall extends DoTBBlockPane {

	private static final PropertyInteger TILE = PropertyInteger.create("tile", 0, 3);
	
	public BlockFloweryPaperWall() {
		super("flowery_paper_wall", Material.CLOTH);
		this.setBurnable();
	}
	
	@Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos){
    	return super.getActualState(state, worldIn, pos).withProperty(TILE, getTileIndex(worldIn, pos));
    }
	
	private int getTileIndex(IBlockAccess worldIn, BlockPos pos){
		
		Block up = worldIn.getBlockState(pos.up()).getBlock();
		Block down = worldIn.getBlockState(pos.down()).getBlock();
		
		boolean blockUp = up instanceof BlockFloweryPaperWall || up instanceof BlockPaperWall;
		boolean blockDown = down instanceof BlockFloweryPaperWall || down instanceof BlockPaperWall;
		
		return (blockUp && blockDown) ? 1 : (!blockUp && ! blockDown) ? 3 : blockDown ? 0 : 2;
	}
	
	@Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH, TILE);
    }
}
