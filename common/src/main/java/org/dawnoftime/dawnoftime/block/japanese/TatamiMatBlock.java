package org.dawnoftime.dawnoftime.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.dawnoftime.dawnoftime.block.templates.WaterloggedBlock;
import org.dawnoftime.dawnoftime.registry.DoTBBlocksRegistry;
import org.dawnoftime.dawnoftime.registry.DoTBTags;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.dawnoftime.dawnoftime.util.Utils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.world.level.block.Blocks.SPRUCE_PLANKS;
import static org.dawnoftime.dawnoftime.util.VoxelShapes.TATAMI_MAT_SHAPES;

public class TatamiMatBlock extends WaterloggedBlock {
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty ROLLED = BlockStatePropertiesAA.ROLLED;
    public static final IntegerProperty STACK = BlockStatePropertiesAA.STACK;

    public TatamiMatBlock(Properties properties) {
        super(properties.pushReaction(PushReaction.DESTROY), TATAMI_MAT_SHAPES);
        this.registerDefaultState(this.defaultBlockState().setValue(ROLLED, false).setValue(HALF, Half.TOP).setValue(STACK, 1));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, HALF, ROLLED, STACK);
    }

    @Override
    public int getShapeIndex(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return state.getValue(ROLLED) ? state.getValue(STACK) - 1 + state.getValue(FACING).get2DDataValue() * 3 : 12;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState oldState = world.getBlockState(pos);
        if(oldState.getBlock() == this) {
            int stack = oldState.getValue(STACK);
            if(oldState.getValue(ROLLED) && stack < 3)
                return oldState.setValue(STACK, stack + 1);
        }
        Direction direction = context.getHorizontalDirection();
        if(world.getBlockState(pos.relative(direction)).canBeReplaced(context))
            return super.getStateForPlacement(context)
                    .setValue(ROLLED, world.getBlockState(pos.below()).is(DoTBTags.INSTANCE.COVERED_BLOCKS) || world.getBlockState(pos.below().relative(direction)).is(DoTBTags.INSTANCE.COVERED_BLOCKS))
                    .setValue(FACING, direction);
        return null;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        if(state.getValue(HALF) == Half.BOTTOM) {
            BlockState topState = worldIn.getBlockState(pos.relative(state.getValue(FACING).getOpposite()));
            if(topState.getBlock() == this) {
                if(topState.getValue(HALF) == Half.TOP && topState.getValue(FACING) == state.getValue(FACING))
                    return !worldIn.isEmptyBlock(pos.below());
            }
        } else
            return !worldIn.isEmptyBlock(pos.below());
        return false;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        BlockState newState = this.tryMergingWithSprucePlanks(state, worldIn, pos);
        if(newState.getBlock() == Blocks.AIR)
            worldIn.setBlock(pos, newState, 10);
        else if(!newState.getValue(ROLLED))
            worldIn.setBlock(pos.relative(newState.getValue(FACING)), newState.setValue(HALF, Half.BOTTOM), 3);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        if(facing.getAxis().isVertical()) {
            return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : this.tryMergingWithSprucePlanks(stateIn, worldIn, currentPos);
        } else {
            boolean mustDisappear = false;
            if(stateIn.getValue(ROLLED))
                return stateIn;
            Direction direction = stateIn.getValue(FACING);
            if(stateIn.getValue(HALF) == Half.TOP) {
                if(direction == facing) { //If block is TOP, then BOTTOM half is toward "direction"
                    if(facingState.getBlock() == this) { //Update comes from the other half
                        if(facingState.getValue(FACING) != facing || facingState.getValue(HALF) != Half.BOTTOM || facingState.getValue(ROLLED))
                            mustDisappear = true;
                    } else
                        mustDisappear = true;
                }
            } else if(direction == facing.getOpposite()) { //If block is BOTTOM, then Top half is toward the opposite of "direction"
                if(facingState.getBlock() == this) { //Update comes from the other half
                    if(facingState.getValue(FACING) != direction || facingState.getValue(HALF) != Half.TOP || facingState.getValue(ROLLED))
                        mustDisappear = true;
                } else
                    mustDisappear = true;
            }
            if(mustDisappear) {
                stateIn = Blocks.AIR.defaultBlockState();
                worldIn.setBlock(currentPos, stateIn, 2); //Avoid the breaking particles
            }
        }
        return stateIn;
    }

    private BlockState tryMergingWithSprucePlanks(BlockState state, LevelAccessor worldIn, BlockPos pos) {
        if(state.getValue(ROLLED))
            return state;
        Direction facing = state.getValue(FACING);
        Block blockDown = worldIn.getBlockState(pos.below()).getBlock();
        Block blockDownAdjacent = worldIn.getBlockState(pos.relative(facing).below()).getBlock();
        if(blockDown == SPRUCE_PLANKS && blockDownAdjacent == SPRUCE_PLANKS) {
            worldIn.setBlock(pos.below(), DoTBBlocksRegistry.INSTANCE.TATAMI_FLOOR.get().defaultBlockState().setValue(TatamiFloorBlock.FACING, facing).setValue(TatamiFloorBlock.HALF, state.getValue(HALF)), 10);
            worldIn.setBlock(pos.relative(facing).below(), DoTBBlocksRegistry.INSTANCE.TATAMI_FLOOR.get().defaultBlockState().setValue(TatamiFloorBlock.FACING, facing).setValue(TatamiFloorBlock.HALF, state.getValue(HALF) == Half.TOP ? Half.BOTTOM : Half.TOP), 10);
            return Blocks.AIR.defaultBlockState();
        }
        return state;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level worldIn, BlockPos pos, Player player, BlockHitResult hit) {
        if(player.isCrouching()) {
            int stack = state.getValue(STACK);
            boolean isRolled = state.getValue(ROLLED);
            if(isRolled && stack == 1) {
                if(worldIn.getBlockState(pos.below()).is(DoTBTags.INSTANCE.COVERED_BLOCKS))
                    return InteractionResult.PASS;
            }
            if(state.getValue(STACK) > 1) {
                state = state.setValue(STACK, stack - 1);
                Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this.asItem()));
            } else {
                state = state.setValue(ROLLED, !isRolled);
                Direction facing = state.getValue(FACING);
                if(isRolled) {
                    if(!worldIn.isEmptyBlock(pos.relative(facing)) //Check if the position for the Bottom half is free
                            || worldIn.isEmptyBlock(pos.relative(facing).below()) //Check if this position can't support tatami
                            || worldIn.getBlockState(pos.below().relative(facing)).is(DoTBTags.INSTANCE.COVERED_BLOCKS)) //Check if this position is not supported by a covered block
                        return InteractionResult.PASS;
                    worldIn.setBlock(pos.relative(facing), state.setValue(HALF, Half.BOTTOM), 2);
                } else if(state.getValue(HALF) == Half.BOTTOM) {
                    state = state.setValue(HALF, Half.TOP).setValue(FACING, facing.getOpposite());
                }
            }
            state = this.updateShape(state, Direction.DOWN, worldIn.getBlockState(pos.below()), worldIn, pos, pos.below());
            worldIn.setBlock(pos, state, 2);
            worldIn.playSound(player, pos, this.soundType.getPlaceSound(), SoundSource.BLOCKS, (this.soundType.getVolume() + 1.0F) / 2.0F, this.soundType.getPitch() * 0.8F);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        ItemStack itemstack = useContext.getItemInHand();
        if(itemstack.getItem() == this.asItem()) {
            if(!state.getValue(ROLLED) || state.getValue(STACK) == 3)
                return false;
            return useContext.replacingClickedOnBlock();
        }
        return false;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        switch (mirrorIn) {
            case LEFT_RIGHT:
                return state.setValue(FACING, state.getValue(FACING).getOpposite()).setValue(HALF, (state.getValue(HALF) == Half.TOP) ? Half.BOTTOM : Half.TOP);
            case FRONT_BACK:
                return state.setValue(FACING, state.getValue(FACING).getOpposite());
            default:
                return super.mirror(state, mirrorIn);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        Utils.addTooltip(tooltipComponents, this);
    }
}