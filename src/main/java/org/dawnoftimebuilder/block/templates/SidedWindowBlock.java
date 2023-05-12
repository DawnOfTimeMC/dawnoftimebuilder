package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties.SidedWindow;
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nullable;
import java.util.List;

import static org.dawnoftimebuilder.util.DoTBUtils.TOOLTIP_SIDED_WINDOW;

public class SidedWindowBlock extends BlockDoTB {

	public static final EnumProperty<DoTBBlockStateProperties.SidedWindow> SIDED_WINDOW = DoTBBlockStateProperties.SIDED_WINDOW;
	private static final BooleanProperty UP = BlockStateProperties.UP;
	private static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;
	private static final VoxelShape NORTH_VS = Block.box(0.0D, 0.0D, 2.0D, 16.0D, 16.0D, 6.0D);
	private static final VoxelShape EAST_VS = Block.box(10.0D, 0.0D, 0.0D, 14.0D, 16.0D, 16.0D);
	private static final VoxelShape SOUTH_VS = Block.box(0.0D, 0.0D, 10.0D, 16.0D, 16.0D, 14.0D);
	private static final VoxelShape WEST_VS = Block.box(2.0D, 0.0D, 0.0D, 6.0D, 16.0D, 16.0D);
    private static final VoxelShape X_VS = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
    private static final VoxelShape Z_VS = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);

	public SidedWindowBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(SIDED_WINDOW, SidedWindow.AXIS_X).setValue(UP, false).setValue(ATTACHED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(SIDED_WINDOW, UP, ATTACHED);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		switch(state.getValue(SIDED_WINDOW)){
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
		World worldIn = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState state = this.defaultBlockState().setValue(SIDED_WINDOW, SidedWindow.getSide(context.getHorizontalDirection(), context.getPlayer() != null && context.getPlayer().isCrouching()));
		return state.setValue(UP, canConnectVertical(state, worldIn, pos)).setValue(ATTACHED, canConnectHorizontal(state, worldIn, pos));
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, net.minecraft.block.Block blockIn, BlockPos fromPos, boolean isMoving){
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
		boolean changeTOP = canConnectVertical(state, worldIn, pos);
		boolean changeSIDE = canConnectHorizontal(state, worldIn, pos);
		BlockState newState = state;
		if(changeTOP != state.getValue(UP)) newState = newState.setValue(UP, changeTOP);
		if(changeSIDE != state.getValue(ATTACHED)) newState = newState.setValue(ATTACHED, changeSIDE);
        if(changeTOP != state.getValue(UP) || changeSIDE != state.getValue(ATTACHED)) worldIn.setBlock(pos, newState, 10);
    }
	
    private boolean canConnectVertical(BlockState state, World worldIn, BlockPos pos) {
    	if(isSameWindowAndSide(state, worldIn, pos.below())){
			return !isSameWindowAndSide(state, worldIn, pos.above());
    	}
    	return false;
    }
    
    private boolean canConnectHorizontal(BlockState state, World worldIn, BlockPos pos) {
    	return isSameWindowAndSide(state, worldIn, pos.relative(state.getValue(SIDED_WINDOW).getOffset()));
    }

    private boolean isSameWindowAndSide(BlockState state, World worldIn, BlockPos pos){
		BlockState otherState = worldIn.getBlockState(pos);
		if(otherState.getBlock() == this){
			return otherState.getValue(SIDED_WINDOW) == state.getValue(SIDED_WINDOW);
		}else return false;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot){
		SidedWindow side = state.getValue(SIDED_WINDOW);
		if(side == SidedWindow.AXIS_X || side == SidedWindow.AXIS_Z){
			return (rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90) ? state.setValue(SIDED_WINDOW, (side == SidedWindow.AXIS_X) ? SidedWindow.AXIS_Z : SidedWindow.AXIS_X) : state;
		}else{
			return state.setValue(SIDED_WINDOW, SidedWindow.getSide(rot.rotate(side.getDirection()), false));
		}
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		SidedWindow side = state.getValue(SIDED_WINDOW);
		if(side == SidedWindow.AXIS_X || side == SidedWindow.AXIS_Z) return state;
		return rotate(state, Rotation.CLOCKWISE_180);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		DoTBUtils.addTooltip(tooltip, TOOLTIP_SIDED_WINDOW);
	}
}
