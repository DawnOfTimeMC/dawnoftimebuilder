package org.dawnoftime.dawnoftime.block.general;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.block.templates.ConnectedVerticalBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA.*;
import static org.dawnoftime.dawnoftime.util.Utils.isShapeIncludedInShape;
import static org.dawnoftime.dawnoftime.util.VoxelShapes.*;

public class IronColumnBlock extends ConnectedVerticalBlock {
    public IronColumnBlock(Properties properties) {
        super(properties, IRON_COLUMN_SHAPES);
        this.registerDefaultState(this.defaultBlockState().setValue(SMALL_TOP, true).setValue(AXIS_Y, false).setValue(AXIS_X, false).setValue(AXIS_Z, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SMALL_TOP, AXIS_X, AXIS_Y, AXIS_Z);
    }

    @Override
    public int getShapeIndex(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        // 0: axis_x, 1: axis_z, 2: axis_xz, 3-8: axis_y, 9-14: axis_xy, 15-20: axis_yz, 21-27: axis_xyz
        if (!state.getValue(AXIS_Y)) {
            return state.getValue(AXIS_Z) ? (state.getValue(AXIS_X) ? 2 : 1) : 0;
        }
        int i = switch (state.getValue(ConnectedVerticalBlock.VERTICAL_CONNECTION)) {
            case NONE -> state.getValue(SMALL_TOP) ? 3 : 4;
            case UNDER -> state.getValue(SMALL_TOP) ? 5 : 6;
            case ABOVE -> 7;
            case BOTH -> 8;
        };
        if (state.getValue(AXIS_X)) {
            i += 6;
        }
        if (state.getValue(AXIS_Z)) {
            i += 12;
        }
        return i;
    }

    @Override
    public boolean canBeReplaced(@NotNull BlockState state, BlockPlaceContext useContext) {
        ItemStack itemstack = useContext.getItemInHand();
        if (useContext.getPlayer() != null && useContext.getPlayer().isCrouching())
            return false;
        if (itemstack.getItem() == this.asItem()) {
            if (useContext.replacingClickedOnBlock()) {
                return switch (useContext.getClickedFace().getAxis()) {
                    case X -> !state.getValue(AXIS_X);
                    case Y -> !state.getValue(AXIS_Y);
                    case Z -> !state.getValue(AXIS_Z);
                };
            }
        }
        return false;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockPos abovePos = pos.above();
        BlockState state = context.getLevel().getBlockState(pos);
        if (state.getBlock() != this) {
            state = super.getStateForPlacement(context);
        }
        if (state != null) {
            switch (context.getClickedFace().getAxis()) {
                case X -> {
                    return state.setValue(AXIS_X, true);
                }
                case Y -> {
                    BlockState aboveState = level.getBlockState(abovePos);
                    state = state.setValue(AXIS_Y, true).setValue(ConnectedVerticalBlock.VERTICAL_CONNECTION, this.getColumnState(level, pos, state));
                    if (!aboveState.is(this)) {
                        VoxelShape aboveShape = aboveState.getShape(level, abovePos, CollisionContext.empty());
                        state = state.setValue(SMALL_TOP, isShapeIncludedInShape(aboveShape, SHAPE_DOWN_16x16, SHAPE_DOWN_4x4));
                    }
                }
                case Z -> {
                    return state.setValue(AXIS_Z, true);
                }
            }
        }
        return state;
    }

    @Override
    public @NotNull BlockState updateShape(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        if (!worldIn.isClientSide()) {
            if (facing == Direction.UP && stateIn.getValue(AXIS_Y)) {
                if (!facingState.is(this)) {
                    VoxelShape aboveShape = facingState.getShape(worldIn, facingPos, CollisionContext.empty());
                    return stateIn.setValue(SMALL_TOP, isShapeIncludedInShape(aboveShape, SHAPE_DOWN_16x16, SHAPE_DOWN_4x4));
                } else {
                    return stateIn.setValue(SMALL_TOP, true);
                }
            }
        }
        return stateIn;
    }

    @Override
    public boolean isConnectible(BlockState stateIn, LevelAccessor worldIn, BlockPos pos, Direction faceToConnect) {
        BlockState adjState = worldIn.getBlockState(pos);
        return adjState.getBlock() == this && adjState.getValue(AXIS_Y);
    }


    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {}

    @Override
    public InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHit) {
        return InteractionResult.PASS;
    }
}
