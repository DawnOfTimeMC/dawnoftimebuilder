package org.dawnoftimebuilder.block.french;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
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
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.builders.BlockDoTB;

public class BlockLatticeOakWindow extends BlockDoTB {

	public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
	private static final BooleanProperty UP = BlockStateProperties.UP;
	private static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;
    private static final VoxelShape X_VS = makeCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
    private static final VoxelShape Z_VS = makeCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
	
	public BlockLatticeOakWindow() {
		super("lattice_oak_window", Material.GLASS, 1.0F, 1.0F);
	    this.setBurnable();
	    this.setDefaultState(this.getStateContainer().getBaseState().with(HORIZONTAL_AXIS, Direction.Axis.X).with(UP, false).with(ATTACHED, false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_AXIS, UP, ATTACHED);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return state.get(HORIZONTAL_AXIS) == Direction.Axis.X ? X_VS : Z_VS;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		World worldIn = context.getWorld();
		BlockPos pos = context.getPos();
		BlockState state = this.getDefaultState().with(HORIZONTAL_AXIS, context.getPlacementHorizontalFacing().getAxis());
		return state.with(UP, canConnectVertical(worldIn, pos)).with(ATTACHED, canConnectHorizontal(state, worldIn, pos));
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, net.minecraft.block.Block blockIn, BlockPos fromPos, boolean isMoving){
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
		boolean changeTOP = canConnectVertical(worldIn, pos);
		boolean changeSIDE = canConnectHorizontal(state, worldIn, pos);
		BlockState newState = state;
		if(changeTOP != state.get(UP)) newState = newState.with(UP, changeTOP);
		if(changeSIDE != state.get(ATTACHED)) newState = newState.with(ATTACHED, changeSIDE);
        if(changeTOP != state.get(UP) || changeSIDE != state.get(ATTACHED)) worldIn.setBlockState(pos, newState, 10);
    }
	
    private boolean canConnectVertical(World worldIn, BlockPos pos) {
    	if(worldIn.getBlockState(pos.down()).getBlock() instanceof BlockLatticeOakWindow){
			return !(worldIn.getBlockState(pos.up()).getBlock() instanceof BlockLatticeOakWindow);
    	}
    	return false;
    }
    
    private boolean canConnectHorizontal(BlockState state, World worldIn, BlockPos pos) {
    	pos = pos.offset((state.get(HORIZONTAL_AXIS) == Direction.Axis.X) ? Direction.NORTH : Direction.WEST);
    	if(worldIn.getBlockState(pos).getBlock() instanceof BlockLatticeOakWindow){
			return worldIn.getBlockState(pos).get(HORIZONTAL_AXIS) == state.get(HORIZONTAL_AXIS);
    	}
    	return false;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		Direction.Axis axis = state.get(HORIZONTAL_AXIS);
		return (rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90) ? state.with(HORIZONTAL_AXIS, (axis == Direction.Axis.X) ? Direction.Axis.Z : Direction.Axis.X) : state;
	}
}
