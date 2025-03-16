package org.dawnoftimebuilder.block.roman;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.ChairBlock;
import org.dawnoftimebuilder.entity.ChairEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class BirchCouchBlock extends ChairBlock {

    public BirchCouchBlock(Properties properties, float pixelsYOffset, VoxelShape[] shapes) {
        super(properties.pushReaction(PushReaction.DESTROY), pixelsYOffset, shapes);
        this.defaultBlockState().setValue(BlockStateProperties.PERSISTENT, true);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.PERSISTENT);
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState state, Level worldIn, BlockPos pos, Player player, BlockHitResult hit) {
        float x = 8.0F;
        float z = 8.0F;
        switch (state.getValue(FACING)) {
            case SOUTH -> z = 14.0F;
            case WEST -> x = 2.0F;
            case EAST -> x = 14.0F;
            default -> z = 2.0F;
        }

        return ChairEntity.createEntity(worldIn, pos, player, player.getDirection().getOpposite(), x, this.pixelsYOffset, z);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection();
        if (context.getLevel().getBlockState(context.getClickedPos().relative(direction)).canBeReplaced(context))
            return super.getStateForPlacement(context).setValue(FACING, direction);
        return null;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        Direction currentFacing = state.getValue(FACING);
        worldIn.setBlock(pos.relative(currentFacing), state.setValue(FACING, currentFacing.getOpposite()).setValue(BlockStateProperties.PERSISTENT, false), 3);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        Direction blockFacing = stateIn.getValue(FACING);
        if (facing == blockFacing) {
            if (facingState.getBlock() == this) {
                if (facingState.getValue(FACING).getOpposite() == blockFacing)
                    return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
            }
            return Blocks.AIR.defaultBlockState();
        } else
            return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public @NotNull BlockState playerWillDestroy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        // Prevents item from dropping in creative by removing the part that gives the item with a setBlock.
        if (!level.isClientSide() && player.isCreative()) {
            if (!state.getValue(BlockStateProperties.PERSISTENT)) {
                Direction dir = state.getValue(FACING);
                BlockPos adjacentPos = pos.relative(dir);
                BlockState adjacentState = level.getBlockState(adjacentPos);
                if (adjacentState.is(this) && adjacentState.getValue(FACING) == dir.getOpposite()) {
                    level.setBlock(adjacentPos, Blocks.AIR.defaultBlockState(), 35);
                    // Event that plays the "break block" sound.
                    level.levelEvent(player, 2001, adjacentPos, Block.getId(state));
                }
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }
}
