package org.dawnoftime.dawnoftime.block.roman;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.dawnoftime.dawnoftime.block.templates.WaterloggedBlock;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nullable;
import static org.dawnoftime.dawnoftime.util.VoxelShapes.MARBLE_STATUE_SHAPES;

public class MarbleStatueBlock extends WaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty MULTIBLOCK = BlockStatePropertiesAA.MULTIBLOCK_0_2;

    public MarbleStatueBlock(final Properties properties) {
        super(properties.pushReaction(PushReaction.DESTROY), MARBLE_STATUE_SHAPES);
        this.registerDefaultState(this.defaultBlockState().setValue(MarbleStatueBlock.FACING, Direction.NORTH).setValue(MarbleStatueBlock.MULTIBLOCK, 0).setValue(WaterloggedBlock.WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(MarbleStatueBlock.FACING, MarbleStatueBlock.MULTIBLOCK);
    }

    @Override
    public void playerWillDestroy(final Level worldIn, final BlockPos blockPosIn, final BlockState blockStateIn, final Player playerEntityIn) {
        // Prevents item from dropping in creative by removing the part that gives the item with a setBlock.
        if (!worldIn.isClientSide() && playerEntityIn.isCreative()) {
            BlockPos blockPos;
            int multiblockValue = blockStateIn.getValue(MarbleStatueBlock.MULTIBLOCK);
            if (multiblockValue > 0) {
                blockPos = blockPosIn.below(multiblockValue);
                final BlockState blockState = worldIn.getBlockState(blockPos);
                worldIn.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 35);
                // Event that plays the "break block" sound.
                worldIn.levelEvent(playerEntityIn, 2001, blockPos, Block.getId(blockState));
            }
        }
        super.playerWillDestroy(worldIn, blockPosIn, blockStateIn, playerEntityIn);
    }

    @Override
    public int getShapeIndex(final @NotNull BlockState state, final @NotNull BlockGetter worldIn, final @NotNull BlockPos pos, final @NotNull CollisionContext context) {
        return state.getValue(MarbleStatueBlock.MULTIBLOCK) == 2 ? 1 : 0;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        final BlockPos pos = context.getClickedPos();
        final Level level = context.getLevel();
        if (!level.getBlockState(pos.above()).canBeReplaced(context) || !level.getBlockState(pos.above(2)).canBeReplaced(context)) {
            return null;
        }
        return super.getStateForPlacement(context).setValue(MarbleStatueBlock.FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public void setPlacedBy(final Level worldIn, final BlockPos pos, final BlockState state, final LivingEntity placer, final ItemStack stack) {
        BlockPos abovePos = pos.above();
        worldIn.setBlock(abovePos, state.setValue(MarbleStatueBlock.MULTIBLOCK, 1).setValue(WaterloggedBlock.WATERLOGGED, worldIn.getFluidState(abovePos).getType() == Fluids.WATER), 10);
        abovePos = abovePos.above();
        worldIn.setBlock(abovePos, state.setValue(MarbleStatueBlock.MULTIBLOCK, 2).setValue(WaterloggedBlock.WATERLOGGED, worldIn.getFluidState(abovePos).getType() == Fluids.WATER), 10);
    }

    @Override
    public @NotNull BlockState updateShape(final BlockState stateIn, final @NotNull Direction facing, final @NotNull BlockState facingState, final @NotNull LevelAccessor worldIn, final @NotNull BlockPos currentPos, final @NotNull BlockPos facingPos) {
        if (facing.getAxis().isHorizontal()) {
            return stateIn;
        }
        final int multipart = stateIn.getValue(MarbleStatueBlock.MULTIBLOCK);

        if (facing == Direction.UP && multipart < 2 && facingState.getBlock() == this) {
            if (facingState.getValue(MarbleStatueBlock.FACING) == stateIn.getValue(MarbleStatueBlock.FACING) && facingState.getValue(MarbleStatueBlock.MULTIBLOCK) == multipart + 1) {
                return stateIn;
            }
        }

        if (facing == Direction.DOWN && multipart > 0 && facingState.getBlock() == this) {
            if (facingState.getValue(MarbleStatueBlock.FACING) == stateIn.getValue(MarbleStatueBlock.FACING) && facingState.getValue(MarbleStatueBlock.MULTIBLOCK) == multipart - 1) {
                return stateIn;
            }
        }

        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public @NotNull BlockState rotate(@NotNull BlockState state, @NotNull Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(@NotNull BlockState state, Mirror mirror) {
        return this.rotate(state, mirror.getRotation(state.getValue(FACING)));
    }
}
