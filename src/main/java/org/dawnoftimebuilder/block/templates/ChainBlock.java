package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import org.dawnoftimebuilder.block.IBlockChain;
import org.dawnoftimebuilder.block.IBlockPillar;
import org.dawnoftimebuilder.block.templates.ColumnConnectibleBlock;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;

public class ChainBlock extends ColumnConnectibleBlock implements IBlockPillar, IBlockChain {

	private static final VoxelShape VS = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

	public ChainBlock(Material materialIn, float hardness, float resistance) {
		this(Block.Properties.create(materialIn).hardnessAndResistance(hardness, resistance));
	}

	public ChainBlock(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VS;
	}

	@Override
	public DoTBBlockStateProperties.VerticalConnection getColumnState(IWorld worldIn, BlockPos pos, BlockState stateIn){
		if(isConnectible(worldIn, pos.up(), stateIn, false))
			return (isConnectible(worldIn, pos.down(), stateIn, true)) ? DoTBBlockStateProperties.VerticalConnection.BOTH : DoTBBlockStateProperties.VerticalConnection.ABOVE;
		else
			return (isConnectible(worldIn, pos.down(), stateIn, true)) ? DoTBBlockStateProperties.VerticalConnection.UNDER : DoTBBlockStateProperties.VerticalConnection.NONE;
	}

	private boolean isConnectible(IWorld worldIn, BlockPos pos, BlockState stateIn, boolean bottomOfChain){
		if(isConnectible(worldIn, pos, stateIn)){
			BlockState state = worldIn.getBlockState(pos);
			return ((IBlockChain)state.getBlock()).canConnectToChain(state, bottomOfChain);
		}
		return false;
	}

    @Override
    public boolean isConnectible(IWorld worldIn, BlockPos pos, BlockState state){
        return worldIn.getBlockState(pos).getBlock() instanceof IBlockChain;
    }

    @Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public DoTBBlockStateProperties.PillarConnection getBlockPillarConnection(BlockState state) {
		return DoTBBlockStateProperties.PillarConnection.FOUR_PX;
	}

	@Override
	public boolean canConnectToChain(BlockState state, boolean bottomOfChain) {
		return true;
	}
}
