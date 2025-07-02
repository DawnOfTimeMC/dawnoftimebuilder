package org.dawnoftime.dawnoftime.block.templates;

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
import org.dawnoftime.dawnoftime.registry.DoTBBlockEntitiesRegistry;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import net.minecraft.world.phys.shapes.Shapes;
public class ConnectedHorizontalPlanDoubleTableBlock extends DisplayerBlock {
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    public ConnectedHorizontalPlanDoubleTableBlock(Properties properties, VoxelShape[] shapes) {
        super(properties, shapes);
        this.registerDefaultState(this.defaultBlockState().setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(NORTH, false).setValue(WATERLOGGED, Boolean.FALSE).setValue(LIT, false).setValue(HALF, Half.BOTTOM));
    }

    @Override
    public int getShapeIndex(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        int index = state.getValue(SOUTH) ? 0 : 1;
        index += state.getValue(WEST) ? 0 : 2;
        index += state.getValue(NORTH) ? 0 : 4;
        index += state.getValue(EAST) ? 0 : 8;
        return state.getValue(HALF) == Half.TOP ? index + 1 : index;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if(!context.getLevel().getBlockState(context.getClickedPos().above()).canBeReplaced(context)) {
            return null;
        }
        BlockState state = super.getStateForPlacement(context);
        if(state == null){
            return null;
        }
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
        return pState.getValue(HALF) == Half.TOP ? DoTBBlockEntitiesRegistry.INSTANCE.DISPLAYER.get().create(pPos, pState) : null;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        worldIn.setBlock(pos.above(), state.setValue(HALF, Half.TOP), 10);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        if(facing.getAxis().isHorizontal()) {
            BlockState state = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
            boolean canConnect = facingState.getBlock().equals(this);
            return switch (facing) {
                default -> state.setValue(NORTH, canConnect);
                case WEST -> state.setValue(WEST, canConnect);
                case SOUTH -> state.setValue(SOUTH, canConnect);
                case EAST -> state.setValue(EAST, canConnect);
            };
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

    @Override
    public void playerWillDestroy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        // Prevents item from dropping in creative by removing the part that gives the item with a setBlock.
        if (!level.isClientSide() && player.isCreative()) {
            if (state.getValue(HALF) == Half.TOP) {
                BlockPos adjacentPos = pos.below();
                BlockState adjacentState = level.getBlockState(adjacentPos);
                if (adjacentState.is(this) && adjacentState.getValue(HALF) == Half.BOTTOM) {
                    level.setBlock(adjacentPos, Blocks.AIR.defaultBlockState(), 35);
                    // Event that plays the "break block" sound.
                    level.levelEvent(player, 2001, adjacentPos, Block.getId(state));
                }
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public double getDisplayerX(BlockState state) {
        return 0.1875D;
    }

    @Override
    public double getDisplayerY(BlockState state) {
        return 0.125D;
    }

    @Override
    public double getDisplayerZ(BlockState state) {
        return 0.1875D;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(HALF) == Half.TOP) {
            return Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);
        }
        return Shapes.block();
    }


}