package org.dawnoftimebuilder.block.general;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import org.dawnoftimebuilder.block.IBlockPillar;
import org.dawnoftimebuilder.block.builders.ColumnConnectibleBlock;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;

public class IronChainBlock extends ColumnConnectibleBlock implements IBlockPillar {

	private static final VoxelShape VS = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

	public IronChainBlock() {
		super("iron_chain", Material.IRON, 5.0F, 6.0F);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VS;
	}

	@Override
	public DoTBBlockStateProperties.VerticalConnection getColumnState(IWorld worldIn, BlockPos pos, BlockState stateIn){
		if(isConnectible(worldIn, pos.up(), stateIn)){
			return (canConnectToBottom(worldIn, pos.down(), stateIn)) ? DoTBBlockStateProperties.VerticalConnection.BOTH : DoTBBlockStateProperties.VerticalConnection.ABOVE;
		}else{
			return (canConnectToBottom(worldIn, pos.down(), stateIn)) ? DoTBBlockStateProperties.VerticalConnection.UNDER : DoTBBlockStateProperties.VerticalConnection.NONE;
		}
	}

	private boolean canConnectToBottom(IWorld worldIn, BlockPos pos, BlockState stateIn){
		Block block = worldIn.getBlockState(pos).getBlock();
		//TODO active this
		/*
		if(block instanceof BlockStoneLantern)
			if(worldIn.getBlockState(pos).get(BlockStoneLantern.FACING) == Direction.DOWN)
				return true;
		if(block instanceof BlockStickBundle) return true;
		*/
		return isConnectible(worldIn, pos, stateIn);
	}

    @Override
    public boolean isConnectible(IWorld worldIn, BlockPos pos, BlockState state){
        return worldIn.getBlockState(pos).getBlock() instanceof IronChainBlock;
    }

    @Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public DoTBBlockStateProperties.PillarConnection getBlockPillarConnection(BlockState state) {
		return DoTBBlockStateProperties.PillarConnection.FOUR_PX;
	}
}
