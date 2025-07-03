package org.dawnoftime.dawnoftime.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.block.templates.PaneBlockDoT;

import static org.dawnoftime.dawnoftime.util.VoxelShapes.CHARRED_SPRUCE_FANCY_RAILING_SHAPES;

public class CharredSpruceFancyRailingBlock extends PaneBlockDoT {
    private static final BooleanProperty HANGING = BlockStateProperties.HANGING;

    public CharredSpruceFancyRailingBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(WATERLOGGED, false).setValue(HANGING, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return state.getValue(HANGING) ? CHARRED_SPRUCE_FANCY_RAILING_SHAPES[this.getAABBIndex(state)] : super.getShape(state, worldIn, pos, context);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return state.getValue(HANGING) ? CHARRED_SPRUCE_FANCY_RAILING_SHAPES[this.getAABBIndex(state)] : super.getCollisionShape(state, worldIn, pos, context);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction clickedFace = context.getClickedFace();
        BlockPos pos = context.getClickedPos();
        BlockState state = super.getStateForPlacement(context);
        if(state == null)
            state = this.defaultBlockState();
        return state.setValue(HANGING, clickedFace == Direction.DOWN || (clickedFace != Direction.UP && context.getClickLocation().y - pos.getY() > 0.5D));
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HANGING);
    }
}
