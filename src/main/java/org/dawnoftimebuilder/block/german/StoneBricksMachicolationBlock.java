package org.dawnoftimebuilder.block.german;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nonnull;

import static net.minecraft.core.Direction.NORTH;
import static org.dawnoftimebuilder.util.DoTBBlockStateProperties.HorizontalConnection.NONE;

public class StoneBricksMachicolationBlock extends WaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<DoTBBlockStateProperties.HorizontalConnection> HORIZONTAL_CONNECTION = DoTBBlockStateProperties.HORIZONTAL_CONNECTION;
    private static final VoxelShape[] SHAPES = DoTBUtils.GenerateHorizontalShapes(makeShapes());

    public StoneBricksMachicolationBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, NORTH).setValue(HORIZONTAL_CONNECTION, NONE).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, HORIZONTAL_CONNECTION);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        int index = state.getValue(HORIZONTAL_CONNECTION).getIndex();
        return SHAPES[index + state.getValue(FACING).get2DDataValue() * 4];
    }

    /**
     * @return Stores VoxelShape for "South" with index : <p/>
     * 0 : S None <p/>
     * 1 : S Left <p/>
     * 2 : S Right <p/>
     * 3 : S Both <p/>
     */
    private static VoxelShape[] makeShapes() {
        VoxelShape floorVS = Block.box(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        return new VoxelShape[] {
                Shapes.or(
                        floorVS,
                        Block.box(0.0D, 8.0D, 0.0D, 6.0D, 16.0D, 16.0D),
                        Block.box(10.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D),
                        Block.box(0.0D, 4.0D, 5.0D, 6.0D, 8.0D, 16.0D),
                        Block.box(10.0D, 4.0D, 5.0D, 16.0D, 8.0D, 16.0D),
                        Block.box(0.0D, 0.0D, 11.0D, 6.0D, 4.0D, 16.0D),
                        Block.box(10.0D, 0.0D, 11.0D, 16.0D, 4.0D, 16.0D)
                ),
                Shapes.or(
                        floorVS,
                        Block.box(0.0D, 8.0D, 0.0D, 6.0D, 16.0D, 16.0D),
                        Block.box(13.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D),
                        Block.box(0.0D, 4.0D, 5.0D, 6.0D, 8.0D, 16.0D),
                        Block.box(13.0D, 4.0D, 5.0D, 16.0D, 8.0D, 16.0D),
                        Block.box(0.0D, 0.0D, 11.0D, 6.0D, 4.0D, 16.0D),
                        Block.box(13.0D, 0.0D, 11.0D, 16.0D, 4.0D, 16.0D)
                ),
                Shapes.or(
                        floorVS,
                        Block.box(0.0D, 8.0D, 0.0D, 3.0D, 16.0D, 16.0D),
                        Block.box(10.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D),
                        Block.box(0.0D, 4.0D, 5.0D, 3.0D, 8.0D, 16.0D),
                        Block.box(10.0D, 4.0D, 5.0D, 16.0D, 8.0D, 16.0D),
                        Block.box(0.0D, 0.0D, 11.0D, 3.0D, 4.0D, 16.0D),
                        Block.box(10.0D, 0.0D, 11.0D, 16.0D, 4.0D, 16.0D)
                ),
                Shapes.or(
                        floorVS,
                        Block.box(0.0D, 8.0D, 0.0D, 3.0D, 16.0D, 16.0D),
                        Block.box(13.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D),
                        Block.box(0.0D, 4.0D, 5.0D, 3.0D, 8.0D, 16.0D),
                        Block.box(13.0D, 4.0D, 5.0D, 16.0D, 8.0D, 16.0D),
                        Block.box(0.0D, 0.0D, 11.0D, 3.0D, 4.0D, 16.0D),
                        Block.box(13.0D, 0.0D, 11.0D, 16.0D, 4.0D, 16.0D)
                )
        };
    }

    @Nonnull
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection());
        return state.setValue(HORIZONTAL_CONNECTION, this.getLineState(context.getLevel(), context.getClickedPos(), state));
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        return (facing.getAxis() == stateIn.getValue(FACING).getClockWise().getAxis()) ? stateIn.setValue(HORIZONTAL_CONNECTION, this.getLineState(worldIn, currentPos, stateIn)) : stateIn;
    }

    private DoTBBlockStateProperties.HorizontalConnection getLineState(LevelAccessor worldIn, BlockPos pos, BlockState stateIn) {
        Direction direction = stateIn.getValue(FACING).getClockWise();
        if(isConnectible(worldIn, pos.relative(direction, -1), stateIn)) {
            return (isConnectible(worldIn, pos.relative(direction), stateIn)) ? DoTBBlockStateProperties.HorizontalConnection.BOTH : DoTBBlockStateProperties.HorizontalConnection.LEFT;
        } else {
            return (isConnectible(worldIn, pos.relative(direction), stateIn)) ? DoTBBlockStateProperties.HorizontalConnection.RIGHT : NONE;
        }
    }

    private boolean isConnectible(LevelAccessor worldIn, BlockPos offset, BlockState stateIn) {
        BlockState state = worldIn.getBlockState(offset);
        if(state.getBlock() == this) {
            return state.getValue(FACING) == stateIn.getValue(FACING);
        }
        return false;
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
