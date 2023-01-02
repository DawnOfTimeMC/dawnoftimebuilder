package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

public class PlanterBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    private static final VoxelShape[] SHAPES_TOP = DoTBBlockUtils.GenerateHorizontalShapes(new VoxelShape[]{Block.box(0.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D)});
    private static final VoxelShape[] SHAPES_BOTTOM = DoTBBlockUtils.GenerateHorizontalShapes(new VoxelShape[]{Block.box(0.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D)});

    public PlanterBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(HALF, Half.BOTTOM));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, HALF);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.getValue(HALF) == Half.BOTTOM ? SHAPES_BOTTOM[state.getValue(FACING).get2DDataValue()] : SHAPES_TOP[state.getValue(FACING).get2DDataValue()];
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = super.getStateForPlacement(context);
        Direction direction = context.getClickedFace();
        BlockPos pos = context.getClickedPos();
        if(state != null){
            return state.setValue(FACING, context.getHorizontalDirection()).setValue(HALF, direction != Direction.DOWN && (direction == Direction.UP || !(context.getClickLocation().y - (double)pos.getY() > 0.5D)) ? Half.BOTTOM : Half.TOP);
        }
        return null;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return this.rotate(state, Rotation.CLOCKWISE_180);
    }
}
