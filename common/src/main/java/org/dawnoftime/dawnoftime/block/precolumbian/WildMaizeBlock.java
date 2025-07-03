package org.dawnoftime.dawnoftime.block.precolumbian;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.block.IBlockGeneration;
import org.dawnoftime.dawnoftime.block.templates.WildPlantBlock;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class WildMaizeBlock extends WildPlantBlock implements IBlockGeneration {
    private static final VoxelShape VS = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    public WildMaizeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(HALF, Half.BOTTOM));
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Vec3 vec3d = state.getOffset(worldIn, pos);
        return VS.move(vec3d.x, vec3d.y, vec3d.z);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        if(state.getValue(HALF) == Half.TOP)
            return true;
        return super.canSurvive(state, worldIn, pos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if(!context.getLevel().getBlockState(context.getClickedPos().above()).canBeReplaced(context))
            return null;
        return super.getStateForPlacement(context);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        worldIn.setBlock(pos.above(), state.setValue(HALF, Half.TOP), 10);
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
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(facing.getAxis().isHorizontal())
            return stateIn;

        if(facing == Direction.UP && stateIn.getValue(HALF) == Half.BOTTOM) {
            if(facingState.getBlock() == this) {
                if(facingState.getValue(HALF) == Half.TOP) {
                    return stateIn;
                }
            }
            return Blocks.AIR.defaultBlockState();
        }

        if(facing == Direction.DOWN) {
            if(stateIn.getValue(HALF) == Half.TOP) {
                if(facingState.getBlock() == this) {
                    if(facingState.getValue(HALF) == Half.BOTTOM) {
                        return stateIn;
                    }
                }
            } else if(canSurvive(stateIn, worldIn, currentPos))
                return stateIn;
            return Blocks.AIR.defaultBlockState();
        }

        return stateIn;
    }

    @Override
    public boolean generateOnPos(WorldGenLevel world, BlockPos pos, BlockState state, RandomSource random) {
        if(!world.getBlockState(pos.above()).isAir())
            return false;
        world.setBlock(pos, state, 2);
        world.setBlock(pos.above(), state.setValue(HALF, Half.TOP), 2);
        return true;
    }
}