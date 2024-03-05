package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import javax.annotation.Nullable;

public class ShutterBlock extends SmallShutterBlock {
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    public ShutterBlock(final Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ShutterBlock.HALF, Half.BOTTOM));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ShutterBlock.HALF);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        final Level world = context.getLevel();
        final Direction direction = context.getHorizontalDirection();
        final BlockPos pos = context.getClickedPos();
        if(!world.getBlockState(pos.above()).canBeReplaced(context)) {
            return null;
        }
        final int x = direction.getStepX();
        final int z = direction.getStepZ();
        final double onX = context.getClickLocation().x - pos.getX();
        final double onZ = context.getClickLocation().z - pos.getZ();
        final boolean hingeLeft = (x >= 0 || onZ >= 0.5D) && (x <= 0 || onZ <= 0.5D) && (z >= 0 || onX <= 0.5D) && (z <= 0 || onX >= 0.5D);
        return super.getStateForPlacement(context).setValue(SmallShutterBlock.HINGE, hingeLeft ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT).setValue(SmallShutterBlock.FACING, direction)
                .setValue(SmallShutterBlock.POWERED, world.hasNeighborSignal(pos)).setValue(ShutterBlock.HALF, Half.BOTTOM);
    }

    @Override
    public boolean canSurvive(final BlockState state, final LevelReader worldIn, final BlockPos pos) {
        if(state.getValue(ShutterBlock.HALF) != Half.TOP) {
            return true;
        }
        final BlockState bottomState = worldIn.getBlockState(pos.below());
        if(bottomState.getBlock() == this) {
            return bottomState.getValue(ShutterBlock.HALF) == Half.BOTTOM && bottomState.getValue(SmallShutterBlock.FACING) == state.getValue(SmallShutterBlock.FACING)
                    && bottomState.getValue(SmallShutterBlock.HINGE) == state.getValue(SmallShutterBlock.HINGE);
        }
        return false;
    }

    @Override
    public void setPlacedBy(final Level worldIn, final BlockPos pos, final BlockState state, @Nullable final LivingEntity entity, final ItemStack itemStack) {
        worldIn.setBlock(pos.above(), state.setValue(ShutterBlock.HALF, Half.TOP), 10);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, final Direction facing, final BlockState facingState, final LevelAccessor worldIn, final BlockPos currentPos, final BlockPos facingPos) {
        final Direction halfDirection = stateIn.getValue(ShutterBlock.HALF) == Half.TOP ? Direction.DOWN : Direction.UP;
        if(facing == halfDirection) {
            if(facingState.getBlock() != this || facingState.getValue(ShutterBlock.HALF) == stateIn.getValue(ShutterBlock.HALF) || facingState.getValue(SmallShutterBlock.FACING) != stateIn.getValue(SmallShutterBlock.FACING)
                    || facingState.getValue(SmallShutterBlock.HINGE) != stateIn.getValue(SmallShutterBlock.HINGE)) {
                return Blocks.AIR.defaultBlockState();
            }
            stateIn = stateIn.setValue(SmallShutterBlock.OPEN_POSITION, facingState.getValue(SmallShutterBlock.OPEN_POSITION));
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    protected DoTBBlockStateProperties.OpenPosition getOpenState(final BlockState stateIn, final LevelAccessor worldIn, final BlockPos pos) {
        final BlockPos secondPos = pos.relative(stateIn.getValue(ShutterBlock.HALF) == Half.TOP ? Direction.DOWN : Direction.UP);
        if(!worldIn.getBlockState(secondPos).getCollisionShape(worldIn, pos).isEmpty() || !worldIn.getBlockState(pos).getCollisionShape(worldIn, pos).isEmpty()) {
            return DoTBBlockStateProperties.OpenPosition.HALF;
        }
        return DoTBBlockStateProperties.OpenPosition.FULL;
    }
}