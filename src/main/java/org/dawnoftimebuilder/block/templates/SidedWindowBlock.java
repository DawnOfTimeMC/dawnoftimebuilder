package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties.SidedWindow;

public class SidedWindowBlock extends BlockDoTB {

	public static final EnumProperty<DoTBBlockStateProperties.SidedWindow> SIDED_WINDOW = DoTBBlockStateProperties.SIDED_WINDOW;
	private static final BooleanProperty UP = BlockStateProperties.UP;
	private static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;
	private static final VoxelShape NORTH_VS = makeCuboidShape(0.0D, 0.0D, 2.0D, 16.0D, 16.0D, 6.0D);
	private static final VoxelShape EAST_VS = makeCuboidShape(10.0D, 0.0D, 0.0D, 14.0D, 16.0D, 16.0D);
	private static final VoxelShape SOUTH_VS = makeCuboidShape(0.0D, 0.0D, 10.0D, 16.0D, 16.0D, 14.0D);
	private static final VoxelShape WEST_VS = makeCuboidShape(2.0D, 0.0D, 0.0D, 6.0D, 16.0D, 16.0D);
    private static final VoxelShape X_VS = makeCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
    private static final VoxelShape Z_VS = makeCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);

	public SidedWindowBlock(String name, Properties properties) {
		super(name, properties);
		this.setBurnable();
		this.setDefaultState(this.getStateContainer().getBaseState().with(SIDED_WINDOW, SidedWindow.AXIS_X).with(UP, false).with(ATTACHED, false));
	}

	public SidedWindowBlock(String name, Material materialIn, float hardness, float resistance) {
		this(name, Properties.create(materialIn).hardnessAndResistance(hardness, resistance));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(SIDED_WINDOW, UP, ATTACHED);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		switch(state.get(SIDED_WINDOW)){
			default:
			case NORTH:
				return NORTH_VS;
			case EAST:
				return EAST_VS;
			case SOUTH:
				return SOUTH_VS;
			case WEST:
				return WEST_VS;
			case AXIS_X:
				return X_VS;
			case AXIS_Z:
				return Z_VS;
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		World worldIn = context.getWorld();
		BlockPos pos = context.getPos();
		BlockState state = this.getDefaultState().with(SIDED_WINDOW, SidedWindow.getSide(context.getPlacementHorizontalFacing(), context.isPlacerSneaking()));
		return state.with(UP, canConnectVertical(state, worldIn, pos)).with(ATTACHED, canConnectHorizontal(state, worldIn, pos));
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, net.minecraft.block.Block blockIn, BlockPos fromPos, boolean isMoving){
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
		boolean changeTOP = canConnectVertical(state, worldIn, pos);
		boolean changeSIDE = canConnectHorizontal(state, worldIn, pos);
		BlockState newState = state;
		if(changeTOP != state.get(UP)) newState = newState.with(UP, changeTOP);
		if(changeSIDE != state.get(ATTACHED)) newState = newState.with(ATTACHED, changeSIDE);
        if(changeTOP != state.get(UP) || changeSIDE != state.get(ATTACHED)) worldIn.setBlockState(pos, newState, 10);
    }
	
    private boolean canConnectVertical(BlockState state, World worldIn, BlockPos pos) {
    	if(isSameWindowAndSide(state, worldIn, pos.down())){
			return !isSameWindowAndSide(state, worldIn, pos.up());
    	}
    	return false;
    }
    
    private boolean canConnectHorizontal(BlockState state, World worldIn, BlockPos pos) {
    	return isSameWindowAndSide(state, worldIn, pos.offset(state.get(SIDED_WINDOW).getOffset()));
    }

    private boolean isSameWindowAndSide(BlockState state, World worldIn, BlockPos pos){
		BlockState otherState = worldIn.getBlockState(pos);
		if(otherState.getBlock() == this){
			return otherState.get(SIDED_WINDOW) == state.get(SIDED_WINDOW);
		}else return false;
	}

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

	@Override
	public boolean isSolid(BlockState state) {
		return true;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot){
		SidedWindow side = state.get(SIDED_WINDOW);
		if(side == SidedWindow.AXIS_X || side == SidedWindow.AXIS_Z){
			return (rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90) ? state.with(SIDED_WINDOW, (side == SidedWindow.AXIS_X) ? SidedWindow.AXIS_Z : SidedWindow.AXIS_X) : state;
		}else{
			return state.with(SIDED_WINDOW, SidedWindow.getSide(rot.rotate(side.getDirection()), false));
		}
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		SidedWindow side = state.get(SIDED_WINDOW);
		if(side == SidedWindow.AXIS_X || side == SidedWindow.AXIS_Z) return state;
		return rotate(state, Rotation.CLOCKWISE_180);
	}
}
