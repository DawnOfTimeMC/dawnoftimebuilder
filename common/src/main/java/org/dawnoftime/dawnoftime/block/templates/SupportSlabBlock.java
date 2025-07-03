package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.block.IBlockPillar;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static org.dawnoftime.dawnoftime.util.VoxelShapes.SUPPORT_SLAB_SHAPES;

public class SupportSlabBlock extends WaterloggedBlock {
    private static final EnumProperty<BlockStatePropertiesAA.PillarConnection> PILLAR_CONNECTION = BlockStatePropertiesAA.PILLAR_CONNECTION;

    public SupportSlabBlock(Properties properties) {
        super(properties, SUPPORT_SLAB_SHAPES);
        this.registerDefaultState(this.defaultBlockState().setValue(PILLAR_CONNECTION, BlockStatePropertiesAA.PillarConnection.NOTHING));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PILLAR_CONNECTION);
    }

    @Override
    public int getShapeIndex(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return switch (state.getValue(PILLAR_CONNECTION)) {
            case FOUR_PX -> 1;
            case EIGHT_PX -> 2;
            case TEN_PX -> 3;
            default -> 0;
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(PILLAR_CONNECTION, IBlockPillar.getPillarConnectionAbove(context.getLevel(), context.getClickedPos().below()));
    }

    @Override
    public @NotNull BlockState updateShape(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        return facing == Direction.DOWN ? stateIn.setValue(PILLAR_CONNECTION, IBlockPillar.getPillarConnectionAbove(worldIn, currentPos.below())) : stateIn;
    }
}