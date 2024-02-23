package org.dawnoftimebuilder.block.roman;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.IBlockPillar;
import org.dawnoftimebuilder.block.templates.ColumnConnectibleBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import javax.annotation.Nonnull;

public class MarbleColumnBlock extends ColumnConnectibleBlock implements IBlockPillar {
    public static final BooleanProperty AXIS_X = DoTBBlockStateProperties.AXIS_X;
    private static final VoxelShape VS_BOT = Shapes.or(
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
            Block.box(3.0D, 4.0D, 3.0D, 13.0D, 6.0D, 13.0D),
            Block.box(4.0D, 6.0D, 4.0D, 12.0D, 16.0D, 12.0D)
    );
    private static final VoxelShape VS_MID = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    private static final VoxelShape VS_TOP_X = Shapes.or(
            Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D),
            Block.box(2.0D, 10.0D, 1.0D, 14.0D, 14.0D, 15.0D),
            Block.box(2.0D, 14.0D, 2.0D, 14.0D, 16.0D, 14.0D)
    );
    private static final VoxelShape VS_TOP_Y = Shapes.or(
            Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D),
            Block.box(1.0D, 10.0D, 2.0D, 15.0D, 14.0D, 14.0D),
            Block.box(2.0D, 14.0D, 2.0D, 14.0D, 16.0D, 14.0D)
    );

    public MarbleColumnBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS_X, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AXIS_X);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch(state.getValue(VERTICAL_CONNECTION)) {
            case UNDER:
                return state.getValue(AXIS_X) ? VS_TOP_X : VS_TOP_Y;
            default:
            case NONE:
            case BOTH:
                return VS_MID;
            case ABOVE:
                return VS_BOT;
        }
    }

    @Nonnull
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super
                .getStateForPlacement(context)
                .setValue(AXIS_X, context.getNearestLookingDirection().getAxis() == Direction.Axis.X);
    }

    @Nonnull
    @Override
    public DoTBBlockStateProperties.PillarConnection getBlockPillarConnectionAbove(BlockState state) {
        return DoTBBlockStateProperties.PillarConnection.EIGHT_PX;
    }

    @Override
    public boolean isConnectible(BlockState stateIn, LevelAccessor worldIn, BlockPos pos, Direction faceToConnect) {
        BlockState testedState = worldIn.getBlockState(pos);
        if(faceToConnect == Direction.DOWN && IBlockPillar.getPillarConnectionUnder(worldIn, pos) == DoTBBlockStateProperties.PillarConnection.EIGHT_PX) {
            return true;
        }
        if(faceToConnect == Direction.UP && IBlockPillar.getPillarConnectionAbove(worldIn, pos) == DoTBBlockStateProperties.PillarConnection.EIGHT_PX) {
            return true;
        }
        return testedState.getBlock() == this;
    }
}
