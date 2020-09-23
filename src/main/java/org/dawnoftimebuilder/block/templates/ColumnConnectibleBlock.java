package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;

import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;

public abstract class ColumnConnectibleBlock extends WaterloggedBlock {

	public static final EnumProperty<DoTBBlockStateProperties.VerticalConnection> VERTICAL_CONNECTION = DoTBBlockStateProperties.VERTICAL_CONNECTION;

	public ColumnConnectibleBlock(Material materialIn, float hardness, float resistance) {
		this(Block.Properties.create(materialIn).hardnessAndResistance(hardness, resistance));
	}

	public ColumnConnectibleBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.getStateContainer().getBaseState().with(VERTICAL_CONNECTION, DoTBBlockStateProperties.VerticalConnection.NONE));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(VERTICAL_CONNECTION);
	}

	@Override
	public abstract VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context);

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		return (facing.getAxis().isVertical()) ? stateIn.with(VERTICAL_CONNECTION, this.getColumnState(worldIn, currentPos, stateIn)) : stateIn;
	}

	public DoTBBlockStateProperties.VerticalConnection getColumnState(IWorld worldIn, BlockPos pos, BlockState stateIn){
		if(isConnectible(worldIn, pos.up(), stateIn)){
			return (isConnectible(worldIn, pos.down(), stateIn)) ? DoTBBlockStateProperties.VerticalConnection.BOTH : DoTBBlockStateProperties.VerticalConnection.ABOVE;
		}else{
			return (isConnectible(worldIn, pos.down(), stateIn)) ? DoTBBlockStateProperties.VerticalConnection.UNDER : DoTBBlockStateProperties.VerticalConnection.NONE;
		}
	}

	public boolean isConnectible(IWorld worldIn, BlockPos pos, BlockState stateIn){
		ResourceLocation rs = worldIn.getBlockState(pos).getBlock().getRegistryName();
		if(rs == null) return false;
		return rs.equals(stateIn.getBlock().getRegistryName());
	}
}