package org.dawnoftimebuilder.block.persian;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.ColumnConnectibleBlock;
import org.jetbrains.annotations.NotNull;

import static org.dawnoftimebuilder.util.DoTBUtils.clickedOnLeftHalf;

public class MoraqMosaicColumnBlock extends ColumnConnectibleBlock {
    private static final VoxelShape VS_LONE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape VS_BOT = Shapes.or(
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D),
            Block.box(2.5D, 6.0D, 2.5D, 13.5D, 16.0D, 13.5D)
    );
    private static final VoxelShape VS_MID = Block.box(2.5D, 0.0D, 2.5D, 13.5D, 16.0D, 13.5D);
    private static final VoxelShape VS_TOP = Shapes.or(
            Block.box(1.0D, 14.0D, 1.0D, 15.0D, 16.0D, 15.0D),
            Block.box(2.5D, 0.0D, 2.5D, 13.5D, 14.0D, 13.5D)
    );
    public MoraqMosaicColumnBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.INVERTED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(VERTICAL_CONNECTION)) {
            default -> VS_TOP;
            case BOTH -> VS_MID;
            case NONE -> VS_LONE;
            case ABOVE -> VS_BOT;
        };
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.INVERTED);
    }

    @NotNull
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state.setValue(BlockStateProperties.INVERTED, clickedOnLeftHalf(context.getClickedPos(), context.getHorizontalDirection(), context.getClickLocation()));
    }
}
