package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

import static org.dawnoftimebuilder.registry.DoTBBlockEntitiesRegistry.DISPLAYER_TE;

public abstract class MultiblockTableBlock extends DisplayerBlock {
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    public MultiblockTableBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(NORTH, false).setValue(WATERLOGGED, Boolean.FALSE).setValue(LIT, false).setValue(HALF, Half.BOTTOM));
    }

    /**
     * @param top   if this state is Top, false otherwise.
     * @param index is a value that depends on the state : <p/>
     *              0 : Full connection <p/>
     *              1 : S <p/>
     *              2 : W <p/>
     *              3 : S-W + Pillar SW <p/>
     *              4 : N <p/>
     *              5 : S + N <p/>
     *              6 : W-N + Pillar WN <p/>
     *              7 : S-W-N + Pillar SW-WN <p/>
     *              8 : E <p/>
     *              9 : S-E + Pillar ES <p/>
     *              10 : W-E <p/>
     *              11 : S-W-E + Pillar SW-ES <p/>
     *              12 : N-E + Pillar NE <p/>
     *              13 : S-N-E + Pillar NE-ES <p/>
     *              14 : W-N-E + Pillar WN-NE <p/>
     *              15 : S-W-N-E + Pillar SW-WN-NE-ES <p/>
     *
     * @return the corresponding shape
     */
    public abstract VoxelShape getShapeByIndex(int index, boolean top);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        int index = state.getValue(SOUTH) ? 0 : 1;
        index += state.getValue(WEST) ? 0 : 2;
        index += state.getValue(NORTH) ? 0 : 4;
        index += state.getValue(EAST) ? 0 : 8;
        return this.getShapeByIndex(index, state.getValue(HALF) == Half.TOP);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if(!context.getLevel().getBlockState(context.getClickedPos().above()).canBeReplaced(context))
            return null;
        BlockState state = super.getStateForPlacement(context);
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        return state
                .setValue(NORTH, world.getBlockState(pos.north()).getBlock().equals(this))
                .setValue(EAST, world.getBlockState(pos.east()).getBlock().equals(this))
                .setValue(SOUTH, world.getBlockState(pos.south()).getBlock().equals(this))
                .setValue(WEST, world.getBlockState(pos.west()).getBlock().equals(this));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH, EAST, SOUTH, WEST, HALF);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level world, BlockPos pos, Player playerEntity, InteractionHand hand, BlockHitResult rayTraceResult) {
        return blockState.getValue(HALF) == Half.TOP ? super.use(blockState, world, pos, playerEntity, hand, rayTraceResult) : InteractionResult.PASS;
    }

    @Override
    public @org.jetbrains.annotations.Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return pState.getValue(HALF) == Half.TOP ? DISPLAYER_TE.get().create(pPos, pState) : null;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        worldIn.setBlock(pos.above(), state.setValue(HALF, Half.TOP), 10);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(facing.getAxis().isHorizontal()) {
            BlockState state = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
            boolean canConnect = facingState.getBlock().equals(this);
            switch(facing) {
                default:
                case NORTH:
                    return state.setValue(NORTH, canConnect);
                case WEST:
                    return state.setValue(WEST, canConnect);
                case SOUTH:
                    return state.setValue(SOUTH, canConnect);
                case EAST:
                    return state.setValue(EAST, canConnect);
            }
        } else {
            Direction halfDirection = (stateIn.getValue(HALF) == Half.TOP) ? Direction.DOWN : Direction.UP;
            if(facing == halfDirection) {
                if(facingState.getBlock() != this)
                    return Blocks.AIR.defaultBlockState();
            }
            return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        if(state.getValue(HALF) == Half.TOP) {
            BlockState bottomState = worldIn.getBlockState(pos.below());
            if(bottomState.getBlock() == this) {
                return bottomState.getValue(HALF) == Half.BOTTOM;
            }
        } else
            return true;
        return false;
    }
}