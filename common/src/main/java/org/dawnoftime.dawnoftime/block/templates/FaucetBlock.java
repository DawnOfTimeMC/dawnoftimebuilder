package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import org.dawnoftime.dawnoftime.block.general.WaterSourceTrickleBlock;
import org.dawnoftime.dawnoftime.block.general.WaterTrickleBlock;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;

public class FaucetBlock extends WaterSourceTrickleBlock {
    public FaucetBlock(final Properties propertiesIn) {
        super(propertiesIn);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStatePropertiesAA.ACTIVATED, false));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStatePropertiesAA.ACTIVATED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext contextIn) {
        final Level level = contextIn.getLevel();
        final BlockPos pos = contextIn.getClickedPos();
        final BlockState currentState = level.getBlockState(pos);
        final Direction targetDirection = contextIn.getHorizontalDirection();

        // If the current block is a WaterTrickle, we keep its state to add a new trickle in this block.
        BlockState outState = currentState.is(this) ? currentState : this.defaultBlockState();
        // We add a trickle for the target direction and the end type.
        return outState.setValue(getPropertyFromDirection(targetDirection), true);
    }

    @Override
    public boolean[] getWaterTrickleOutPut(BlockState currentState) {
        if(!currentState.getValue(BlockStatePropertiesAA.ACTIVATED)) {
            return new boolean[] {
                    currentState.getValue(BlockStatePropertiesAA.NORTH_TRICKLE),
                    currentState.getValue(BlockStatePropertiesAA.EAST_TRICKLE),
                    currentState.getValue(BlockStatePropertiesAA.SOUTH_TRICKLE),
                    currentState.getValue(BlockStatePropertiesAA.WEST_TRICKLE),
                    currentState.getValue(BlockStatePropertiesAA.CENTER_TRICKLE) };
        }
        return super.getWaterTrickleOutPut(currentState);
    }

    @Override
    public InteractionResult use(BlockState blockStateIn, final Level worldIn, final BlockPos blockPosIn, final Player playerEntityIn, final InteractionHand handIn, final BlockHitResult blockRaytraceResultIn) {
        final ItemStack mainHandItemStack = playerEntityIn.getMainHandItem();
        if(!mainHandItemStack.isEmpty() && mainHandItemStack.getItem() == this.asItem()) {
            return InteractionResult.PASS;
        }
        boolean activated = !blockStateIn.getValue(BlockStatePropertiesAA.ACTIVATED);
        blockStateIn = blockStateIn.setValue(BlockStatePropertiesAA.ACTIVATED, activated);

        if(activated) {
            blockStateIn = blockStateIn.setValue(BlockStateProperties.UNSTABLE, true);
        }
        if(!worldIn.isClientSide()) {
            worldIn.scheduleTick(blockPosIn, this, 5);
        }

        worldIn.setBlock(blockPosIn, blockStateIn, 10);

        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction directionIn, BlockState facingStateIn, LevelAccessor worldIn, BlockPos currentPosIn, BlockPos facingPosIn) {
        BlockState state = super.updateShape(stateIn, directionIn, facingStateIn, worldIn, currentPosIn, facingPosIn);
        boolean lastActivation = state.getValue(BlockStatePropertiesAA.ACTIVATED);

        switch (directionIn) {
            case NORTH -> {
                if (state.getValue(BlockStateProperties.NORTH) && facingStateIn.getBlock() instanceof PoolBlock) {
                    int level = facingStateIn.getValue(BlockStatePropertiesAA.LEVEL);
                    state = state.setValue(BlockStatePropertiesAA.ACTIVATED, level >= ((PoolBlock) facingStateIn.getBlock()).faucetLevel);
                }
            }
            case SOUTH -> {
                if (state.getValue(BlockStateProperties.SOUTH) && facingStateIn.getBlock() instanceof PoolBlock) {
                    int level = facingStateIn.getValue(BlockStatePropertiesAA.LEVEL);
                    state = state.setValue(BlockStatePropertiesAA.ACTIVATED, level >= ((PoolBlock) facingStateIn.getBlock()).faucetLevel);
                }
            }
            case EAST -> {
                if (state.getValue(BlockStateProperties.EAST) && facingStateIn.getBlock() instanceof PoolBlock) {
                    int level = facingStateIn.getValue(BlockStatePropertiesAA.LEVEL);
                    state = state.setValue(BlockStatePropertiesAA.ACTIVATED, level >= ((PoolBlock) facingStateIn.getBlock()).faucetLevel);
                }
            }
            case WEST -> {
                if (state.getValue(BlockStateProperties.WEST) && facingStateIn.getBlock() instanceof PoolBlock) {
                    int level = facingStateIn.getValue(BlockStatePropertiesAA.LEVEL);
                    state = state.setValue(BlockStatePropertiesAA.ACTIVATED, level >= ((PoolBlock) facingStateIn.getBlock()).faucetLevel);
                }
            }
            case DOWN -> {
                if (state.getValue(BlockStatePropertiesAA.ACTIVATED)) {
                    if (facingStateIn.getBlock() instanceof WaterTrickleBlock) {
                        state = state.setValue(BlockStateProperties.UNSTABLE, false);
                    } else {
                        state = state.setValue(BlockStateProperties.UNSTABLE, true);
                    }
                }
            }
        }


        if(!worldIn.isClientSide() && state.getValue(BlockStatePropertiesAA.ACTIVATED) != lastActivation) {
            (worldIn).scheduleTick(currentPosIn, this, 5);
        }

        return state;
    }
}