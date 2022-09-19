package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;

import javax.annotation.Nullable;

public class HorizontalAxisBlockDoTB extends BlockDoTB {

    private static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;

    public HorizontalAxisBlockDoTB(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(HORIZONTAL_AXIS, Direction.Axis.X));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<net.minecraft.block.Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HORIZONTAL_AXIS);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction.Axis clicked = context.getClickedFace().getAxis();
        if(clicked.isHorizontal()){
            return this.defaultBlockState().setValue(HORIZONTAL_AXIS, clicked);
        }
        return this.defaultBlockState().setValue(HORIZONTAL_AXIS, context.getHorizontalDirection().getAxis());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        if(rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90){
            return state.setValue(HORIZONTAL_AXIS, (state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X) ? Direction.Axis.Z : Direction.Axis.X);
        }
        return super.rotate(state, rot);
    }
}
