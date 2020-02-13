package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import org.dawnoftimebuilder.enums.DoTBBlockStateProperties;

public class IronChainBlock extends DoTBBlockColumn {

	private static final VoxelShape VS = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

	public IronChainBlock() {
		super("iron_chain", Material.WOOD, 5.0F, 6.0F);
	}

	@Override
	public VoxelShape getShapeFromIndex(int index) {
		return VS;
	}

	@Override
	public DoTBBlockStateProperties.VerticalConnection getColumnState(IWorld worldIn, BlockPos pos){
		if(isSameColumn(worldIn, pos.up())){
			return (canConnectToBottom(worldIn, pos.down())) ? DoTBBlockStateProperties.VerticalConnection.BOTH : DoTBBlockStateProperties.VerticalConnection.ABOVE;
		}else{
			return (canConnectToBottom(worldIn, pos.down())) ? DoTBBlockStateProperties.VerticalConnection.UNDER : DoTBBlockStateProperties.VerticalConnection.NONE;
		}
	}

	private boolean canConnectToBottom(IWorld worldIn, BlockPos pos){
		Block block = worldIn.getBlockState(pos).getBlock();
		//TODO active this
		/*
		if(block instanceof BlockStoneLantern)
			if(worldIn.getBlockState(pos).get(BlockStoneLantern.FACING) == Direction.DOWN)
				return true;
		if(block instanceof BlockStickBundle) return true;
		*/
		return isSameColumn(worldIn, pos);
	}

    @Override
    public boolean isSameColumn(IWorld worldIn, BlockPos pos){
        return worldIn.getBlockState(pos).getBlock() instanceof IronChainBlock;
    }

    @Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

}
