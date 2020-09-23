package org.dawnoftimebuilder.block.german;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;
import org.dawnoftimebuilder.utils.DoTBBlockUtils;

import javax.annotation.Nonnull;

import static net.minecraft.util.Direction.NORTH;
import static org.dawnoftimebuilder.utils.DoTBBlockStateProperties.HorizontalConnection.NONE;

public class StoneBricksMachicolationBlock extends WaterloggedBlock {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final EnumProperty<DoTBBlockStateProperties.HorizontalConnection> HORIZONTAL_CONNECTION = DoTBBlockStateProperties.HORIZONTAL_CONNECTION;
	private static final VoxelShape[] SHAPES = DoTBBlockUtils.GenerateHorizontalShapes(makeShapes());

	public StoneBricksMachicolationBlock() {
		super(Material.ROCK, 1.5F, 6.0F);
		this.setDefaultState(this.getDefaultState().with(FACING, NORTH).with(HORIZONTAL_CONNECTION, NONE).with(WATERLOGGED, false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(FACING, HORIZONTAL_CONNECTION);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		int index = state.get(HORIZONTAL_CONNECTION).getIndex();
		return SHAPES[index + state.get(FACING).getHorizontalIndex() * 4];
	}

	/**
	 * @return Stores VoxelShape for "South" with index : <p/>
	 * 0 : S None <p/>
	 * 1 : S Left <p/>
	 * 2 : S Right <p/>
	 * 3 : S Both <p/>
	 */
	private static VoxelShape[] makeShapes() {
		VoxelShape floorVS = makeCuboidShape(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 8.0D);
		return new VoxelShape[]{
				VoxelShapes.or(
						floorVS,
						makeCuboidShape(0.0D, 8.0D, 0.0D, 6.0D, 16.0D, 16.0D),
						makeCuboidShape(10.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D),
						makeCuboidShape(0.0D, 4.0D, 5.0D, 6.0D, 8.0D, 16.0D),
						makeCuboidShape(10.0D, 4.0D, 5.0D, 16.0D, 8.0D, 16.0D),
						makeCuboidShape(0.0D, 0.0D, 11.0D, 6.0D, 4.0D, 16.0D),
						makeCuboidShape(10.0D, 0.0D, 11.0D, 16.0D, 4.0D, 16.0D)
				),
				VoxelShapes.or(
						floorVS,
						makeCuboidShape(0.0D, 8.0D, 0.0D, 6.0D, 16.0D, 16.0D),
						makeCuboidShape(13.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D),
						makeCuboidShape(0.0D, 4.0D, 5.0D, 6.0D, 8.0D, 16.0D),
						makeCuboidShape(13.0D, 4.0D, 5.0D, 16.0D, 8.0D, 16.0D),
						makeCuboidShape(0.0D, 0.0D, 11.0D, 6.0D, 4.0D, 16.0D),
						makeCuboidShape(13.0D, 0.0D, 11.0D, 16.0D, 4.0D, 16.0D)
				),
				VoxelShapes.or(
						floorVS,
						makeCuboidShape(0.0D, 8.0D, 0.0D, 3.0D, 16.0D, 16.0D),
						makeCuboidShape(10.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D),
						makeCuboidShape(0.0D, 4.0D, 5.0D, 3.0D, 8.0D, 16.0D),
						makeCuboidShape(10.0D, 4.0D, 5.0D, 16.0D, 8.0D, 16.0D),
						makeCuboidShape(0.0D, 0.0D, 11.0D, 3.0D, 4.0D, 16.0D),
						makeCuboidShape(10.0D, 0.0D, 11.0D, 16.0D, 4.0D, 16.0D)
				),
				VoxelShapes.or(
						floorVS,
						makeCuboidShape(0.0D, 8.0D, 0.0D, 3.0D, 16.0D, 16.0D),
						makeCuboidShape(13.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D),
						makeCuboidShape(0.0D, 4.0D, 5.0D, 3.0D, 8.0D, 16.0D),
						makeCuboidShape(13.0D, 4.0D, 5.0D, 16.0D, 8.0D, 16.0D),
						makeCuboidShape(0.0D, 0.0D, 11.0D, 3.0D, 4.0D, 16.0D),
						makeCuboidShape(13.0D, 0.0D, 11.0D, 16.0D, 4.0D, 16.0D)
				)
		};
	}

	@Nonnull
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState state = super.getStateForPlacement(context).with(FACING, context.getPlacementHorizontalFacing());
		return state.with(HORIZONTAL_CONNECTION, this.getLineState(context.getWorld(), context.getPos(), state));
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		return (facing.getAxis() == stateIn.get(FACING).rotateY().getAxis()) ? stateIn.with(HORIZONTAL_CONNECTION, this.getLineState(worldIn, currentPos, stateIn)) : stateIn;
	}

	private DoTBBlockStateProperties.HorizontalConnection getLineState(IWorld worldIn, BlockPos pos, BlockState stateIn){
		Direction direction = stateIn.get(FACING).rotateY();
		if(isConnectible(worldIn, pos.offset(direction, -1), stateIn)){
			return (isConnectible(worldIn, pos.offset(direction), stateIn)) ? DoTBBlockStateProperties.HorizontalConnection.BOTH : DoTBBlockStateProperties.HorizontalConnection.LEFT;
		}else{
			return (isConnectible(worldIn, pos.offset(direction), stateIn)) ? DoTBBlockStateProperties.HorizontalConnection.RIGHT : NONE;
		}
	}

	private boolean isConnectible(IWorld worldIn, BlockPos offset, BlockState stateIn) {
		BlockState state = worldIn.getBlockState(offset);
		if(state.getBlock() == this){
			return state.get(FACING) == stateIn.get(FACING);
		}
		return false;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot){
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return this.rotate(state, Rotation.CLOCKWISE_180);
	}
}
