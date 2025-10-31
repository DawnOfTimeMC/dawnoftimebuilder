package org.dawnoftime.dawnoftime.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.dawnoftime.dawnoftime.block.templates.BlockDoT;
import org.dawnoftime.dawnoftime.registry.DoTBBlocksRegistry;
import org.dawnoftime.dawnoftime.util.VoxelShapes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SmallTatamiFloorBlock extends BlockDoT {
    public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;

    public SmallTatamiFloorBlock(Properties properties) {
        super(properties, VoxelShapes.SMALL_TATAMI_FLOOR_SHAPES);
        this.registerDefaultState(this.defaultBlockState().setValue(HORIZONTAL_AXIS, Direction.Axis.X));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HORIZONTAL_AXIS);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext contextIn) {
        final Level level = contextIn.getLevel();
        final BlockPos pos = contextIn.getClickedPos();
        final BlockState currentState = level.getBlockState(pos);
        return currentState.is(this) ? currentState : this.defaultBlockState();
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState state, Level worldIn, BlockPos pos, Player player, BlockHitResult hit) {
        if(player.isCrouching()) {
            if(worldIn.isEmptyBlock(pos.above())) {
                worldIn.setBlock(pos.above(), DoTBBlocksRegistry.INSTANCE.SMALL_TATAMI_MAT.get().defaultBlockState().setValue(SmallTatamiMatBlock.ROLLED, true).setValue(SmallTatamiMatBlock.HORIZONTAL_AXIS, state.getValue(HORIZONTAL_AXIS)), 10);
                worldIn.setBlock(pos, Blocks.SPRUCE_PLANKS.defaultBlockState(), 10);
                worldIn.playSound(player, pos.above(), this.soundType.getPlaceSound(), SoundSource.BLOCKS, (this.soundType.getVolume() + 1.0F) / 2.0F, this.soundType.getPitch() * 0.8F);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(HORIZONTAL_AXIS, state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X);
    }

    @Override
    public @NotNull BlockState mirror(@NotNull BlockState state, Mirror mirrorIn) {
        return switch (mirrorIn) {
            case LEFT_RIGHT ->
                    state.setValue(HORIZONTAL_AXIS, state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X);
            case FRONT_BACK ->
                    state.setValue(HORIZONTAL_AXIS, state.getValue(HORIZONTAL_AXIS) == Direction.Axis.Z ? Direction.Axis.X : Direction.Axis.Z);
            default -> state;
        };
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel world, BlockPos pos, boolean movedByPiston) {
        world.setBlock(pos, Blocks.SPRUCE_PLANKS.defaultBlockState(), 10);
        Containers.dropItemStack(world, pos.getX(), pos.getY() + 1, pos.getZ(),
                new ItemStack(DoTBBlocksRegistry.INSTANCE.SMALL_TATAMI_MAT.get().asItem(), 1));
    }
}
