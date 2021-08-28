package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;

public class PortcullisBlock extends WaterloggedBlock {

	private static final BooleanProperty OPEN =  BlockStateProperties.OPEN;
	private static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	private static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
	private static final EnumProperty<DoTBBlockStateProperties.VerticalConnection> VERTICAL_CONNECTION = DoTBBlockStateProperties.VERTICAL_CONNECTION;

	private static final VoxelShape VS_AXIS_X = net.minecraft.block.Block.makeCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
	private static final VoxelShape VS_AXIS_Z = net.minecraft.block.Block.makeCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

	public PortcullisBlock() {
		super(Properties.from(Blocks.IRON_BARS));
		this.setDefaultState(this.getStateContainer().getBaseState().with(OPEN, false).with(POWERED, false).with(HORIZONTAL_AXIS, Direction.Axis.X).with(VERTICAL_CONNECTION, DoTBBlockStateProperties.VerticalConnection.NONE));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<net.minecraft.block.Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(OPEN, POWERED, HORIZONTAL_AXIS, VERTICAL_CONNECTION);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if(state.get(OPEN) && state.get(VERTICAL_CONNECTION) != DoTBBlockStateProperties.VerticalConnection.NONE && state.get(VERTICAL_CONNECTION) != DoTBBlockStateProperties.VerticalConnection.UNDER)
			return VoxelShapes.empty();
		else return (state.get(HORIZONTAL_AXIS) == Direction.Axis.X) ? VS_AXIS_X : VS_AXIS_Z;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState state = super.getStateForPlacement(context);
		state = state.with(HORIZONTAL_AXIS, (context.getPlacementHorizontalFacing().getAxis() == Direction.Axis.X) ? Direction.Axis.Z : Direction.Axis.X);
		return this.getShape(state, context.getWorld(), context.getPos());
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		return this.getShape(stateIn, worldIn, currentPos);
	}

	private BlockState getShape(BlockState state, IWorld worldIn, BlockPos pos){
		Direction.Axis axis = state.get(HORIZONTAL_AXIS);
		if(hasSameAxis(worldIn.getBlockState(pos.up()), axis)){
			return state.with(VERTICAL_CONNECTION, (hasSameAxis(worldIn.getBlockState(pos.down()), axis)) ? DoTBBlockStateProperties.VerticalConnection.BOTH : DoTBBlockStateProperties.VerticalConnection.ABOVE);
		}else{
			return state.with(VERTICAL_CONNECTION, (hasSameAxis(worldIn.getBlockState(pos.down()), axis)) ? DoTBBlockStateProperties.VerticalConnection.UNDER : DoTBBlockStateProperties.VerticalConnection.NONE);
		}
	}

	private boolean hasSameAxis(BlockState state, Direction.Axis axis){
		if(state.getBlock() instanceof PortcullisBlock){
			return state.get(HORIZONTAL_AXIS) == axis;
		}else return false;
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, net.minecraft.block.Block blockIn, BlockPos fromPos, boolean isMoving) {
		if (state.get(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.UNDER) {
			//update coming from top blocks : check the shape of whole portcullis to know if it can be or stay open
			Direction.Axis axis = state.get(HORIZONTAL_AXIS);
			boolean isNowPowered = worldIn.isBlockPowered(pos);
			if(state.get(OPEN)){
				if(isInSamePlane(pos, fromPos, axis) && isNowPowered) setOpenState(worldIn, pos, axis,true);
			}else{
				if(isNowPowered){
					setOpenState(worldIn, pos, axis,true);
					state = state.with(OPEN, true);
				}
			}
			if(!isNowPowered && state.get(POWERED)){
				state = state.with(POWERED, false);
				worldIn.setBlockState(pos, state, 2);
				if(!hasAnotherPowerSource(worldIn, pos, axis)) setOpenState(worldIn, pos, axis,false);
			}
			if(isNowPowered != state.get(POWERED)) {
				state = state.with(POWERED, isNowPowered);
				worldIn.setBlockState(pos, state, 2);
			}
		} else if(state.get(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.NONE) {
			if(state.get(OPEN)) worldIn.setBlockState(pos, state.with(OPEN, false), 2);
		} else {
			//update coming from the rest of the portcullis (and when opening) : send an update task to top blocks
			//NB : VerticalConnection.NONE can't be open
			if (state.get(OPEN)) {
				Direction.Axis axis = state.get(HORIZONTAL_AXIS);
				if(isInSamePlane(pos, fromPos, axis)){
					pos = getTopPortcullisPos(worldIn, pos, axis);
					worldIn.getBlockState(pos).neighborChanged(worldIn, pos, blockIn, fromPos, isMoving);
				}
			}
		}
	}

	private boolean isInSamePlane(BlockPos pos, BlockPos fromPos, Direction.Axis axis){
		return (axis == Direction.Axis.X) ? fromPos.getZ() == pos.getZ() : fromPos.getX() == pos.getX();
	}

	private BlockPos getTopPortcullisPos(World worldIn, BlockPos pos, Direction.Axis axis){
		BlockState state;
		for (boolean isStillPortcullis = true; isStillPortcullis; pos = pos.up()) {
			state = worldIn.getBlockState(pos);
			if(state.getBlock() instanceof PortcullisBlock){
				if(!hasSameAxis(state, axis)) isStillPortcullis = false;
			}else isStillPortcullis = false;
		}
		return pos.down();
	}

	private boolean hasAnotherPowerSource(World worldIn, BlockPos pos, Direction.Axis axis){
		boolean isAxisX = axis == Direction.Axis.X;
		Direction direction = (isAxisX) ? Direction.WEST : Direction.NORTH;
		int widthLeft = getPortcullisWidth(worldIn, pos, axis, (isAxisX) ? Direction.EAST : Direction.SOUTH);
		int widthRight = getPortcullisWidth(worldIn, pos, axis, (isAxisX) ? Direction.WEST : Direction.NORTH);
		BlockState state;
		pos = pos.offset(direction, -widthLeft);
		for(int i = 0; i <= (widthLeft + widthRight); i++){
			state = worldIn.getBlockState(pos.offset(direction, i));
			if(state.get(POWERED)) return true;
		}
		return false;
	}

	private void setOpenState(World worldIn, BlockPos pos, Direction.Axis axis, boolean openState){
		boolean isAxisX = axis == Direction.Axis.X;
		Direction direction = (isAxisX) ? Direction.WEST : Direction.NORTH;
		int height = getPortcullisHeight(worldIn, pos, axis);
		int widthLeft = getPortcullisWidth(worldIn, pos, axis, (isAxisX) ? Direction.EAST : Direction.SOUTH);
		int widthRight = getPortcullisWidth(worldIn, pos, axis, (isAxisX) ? Direction.WEST : Direction.NORTH);
		BlockState state;
		if(height > 16 || (widthLeft + widthRight + 1) > 16) return;
		if(openState){
			for(int horizontal = 1; horizontal <= widthLeft; horizontal++){
				for(int vertical = 0; vertical < height; vertical++){
					state = worldIn.getBlockState(pos.offset((isAxisX) ? Direction.EAST : Direction.SOUTH, horizontal).down(vertical));
					if(state.getBlock() instanceof PortcullisBlock){
						if(state.get(HORIZONTAL_AXIS) != axis) {
							openState = false;
							break;
						}
					}else{
						openState = false;
						break;
					}
				}
				if(!openState) break;
			}
			if(openState) {
				for (int horizontal = 1; horizontal <= widthRight; horizontal++) {
					for (int vertical = 0; vertical < height; vertical++) {
						state = worldIn.getBlockState(pos.offset(direction, horizontal).down(vertical));
						if (state.getBlock() instanceof PortcullisBlock) {
							if (state.get(HORIZONTAL_AXIS) != axis) {
								openState = false;
								break;
							}
						} else {
							openState = false;
							break;
						}
					}
					if (!openState) break;
				}
			}
			if(openState) {
				openState = isCorrectBorder(worldIn, pos.offset(direction, -widthLeft).up(), axis, (isAxisX) ? Direction.WEST : Direction.NORTH, widthLeft + widthRight);
				openState = openState && isCorrectBorder(worldIn, pos.offset(direction, -widthLeft).down(height), axis, (isAxisX) ? Direction.WEST : Direction.NORTH, widthLeft + widthRight);
				openState = openState && isCorrectBorder(worldIn, pos.offset(direction, -widthLeft - 1), axis, Direction.DOWN, height - 1);
				openState = openState && isCorrectBorder(worldIn, pos.offset(direction, widthRight + 1), axis, Direction.DOWN, height - 1);
			}
		}
		pos = pos.offset(direction, -widthLeft);
		BlockPos newPos;
		for(int horizontal = 0; horizontal < widthLeft + widthRight + 1; horizontal++){
			for(int vertical = 0; vertical < height; vertical++){
				newPos = pos.offset(direction, horizontal).down(vertical);
				state = worldIn.getBlockState(newPos);
				if(state.getBlock() instanceof PortcullisBlock){
					if(state.get(HORIZONTAL_AXIS) == axis) {
						worldIn.setBlockState(newPos, state.with(OPEN, openState), 10);
						if(horizontal == widthLeft){
							if(vertical == 0 && openState) worldIn.playEvent(null, this.getOpenSound(), newPos, 0);
							if(vertical == height - 1 && !openState) worldIn.playEvent(null, this.getCloseSound(), newPos, 0);
						}
					}
				}
			}
		}
	}

	private int getPortcullisHeight(World worldIn, BlockPos pos, Direction.Axis axis){
		int height = 0;
		BlockState state;
		for (boolean isSamePortcullis = true; isSamePortcullis; height++) {
			pos = pos.down();
			state = worldIn.getBlockState(pos);
			if(state.getBlock() instanceof PortcullisBlock){
				if(state.get(HORIZONTAL_AXIS) != axis) isSamePortcullis = false;
			}else isSamePortcullis = false;
		}
		return height;
	}

	private int getPortcullisWidth(World worldIn, BlockPos pos, Direction.Axis axis, Direction direction){
		int width = 0;
		BlockState state;
		for (boolean isSamePortcullis = true; isSamePortcullis; width++) {
			pos = pos.offset(direction);
			state = worldIn.getBlockState(pos);
			if(state.getBlock() instanceof PortcullisBlock){
				if(state.get(HORIZONTAL_AXIS) != axis) isSamePortcullis = false;
			}else isSamePortcullis = false;
		}
		return (width - 1);
	}

	private boolean isCorrectBorder(World worldIn, BlockPos pos, Direction.Axis axis, Direction direction, int length){
		BlockState state;
		for(int i = 0; i <= length; i++){
			state = worldIn.getBlockState(pos.offset(direction, i));
			if(state.getBlock() instanceof PortcullisBlock){
				if(state.get(HORIZONTAL_AXIS) == axis) return false;
			}
		}
		return true;
	}

	private int getCloseSound() {
		return 1011;
	}

	private int getOpenSound()
	{
		return 1005;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		if(rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90) return state.with(HORIZONTAL_AXIS, (state.get(HORIZONTAL_AXIS) == Direction.Axis.X) ? Direction.Axis.Z : Direction.Axis.X);
		else return super.rotate(state, rot);
	}
}