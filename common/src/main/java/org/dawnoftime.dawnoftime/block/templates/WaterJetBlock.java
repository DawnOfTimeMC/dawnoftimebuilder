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
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA.VerticalLimitedConnection;

public class WaterJetBlock extends BlockDoT {
    public WaterJetBlock(final Properties propertiesIn) {
        super(propertiesIn);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.UP, false).setValue(BlockStateProperties.DOWN, false).setValue(BlockStatePropertiesAA.NORTH_STATE, VerticalLimitedConnection.NONE).setValue(BlockStatePropertiesAA.EAST_STATE, VerticalLimitedConnection.NONE).setValue(BlockStatePropertiesAA.SOUTH_STATE, VerticalLimitedConnection.NONE)
                .setValue(BlockStatePropertiesAA.WEST_STATE, VerticalLimitedConnection.NONE).setValue(BlockStateProperties.POWERED, false).setValue(BlockStatePropertiesAA.ACTIVATED, false));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(
                BlockStateProperties.UP,
                BlockStateProperties.DOWN,
                BlockStatePropertiesAA.NORTH_STATE,
                BlockStatePropertiesAA.EAST_STATE,
                BlockStatePropertiesAA.SOUTH_STATE,
                BlockStatePropertiesAA.WEST_STATE,
                BlockStateProperties.POWERED,
                BlockStatePropertiesAA.ACTIVATED);
    }

    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        if(state.getBlock() != this) {
            state = super.getStateForPlacement(context);
        }
        if(state == null) {
            return this.defaultBlockState().setValue(BlockStateProperties.DOWN, true);
        }
        VerticalLimitedConnection clickedFace = (context.getClickLocation().y - context.getClickedPos().getY() <= 0.5D) ? VerticalLimitedConnection.BOTTOM : VerticalLimitedConnection.TOP;
        return switch (context.getClickedFace()) {
            default -> state.setValue(BlockStateProperties.DOWN, true);
            case DOWN -> state.setValue(BlockStateProperties.UP, true);
            case SOUTH -> state.setValue(BlockStatePropertiesAA.NORTH_STATE, clickedFace);
            case WEST -> state.setValue(BlockStatePropertiesAA.EAST_STATE, clickedFace);
            case NORTH -> state.setValue(BlockStatePropertiesAA.SOUTH_STATE, clickedFace);
            case EAST -> state.setValue(BlockStatePropertiesAA.WEST_STATE, clickedFace);
        };
    }

    @Override
    public boolean canBeReplaced(final BlockState state, final BlockPlaceContext useContext) {
        final ItemStack itemstack = useContext.getItemInHand();
        if(useContext.getPlayer() != null && useContext.getPlayer().isCrouching()) {
            return false;
        }
        if(itemstack.getItem() == this.asItem()) {
            final Direction newDirection = useContext.getClickedFace();
            return switch (newDirection) {
                default -> !state.getValue(BlockStateProperties.UP);
                case DOWN -> !state.getValue(BlockStateProperties.DOWN);
                case SOUTH -> VerticalLimitedConnection.NONE.equals(state.getValue(BlockStatePropertiesAA.NORTH_STATE));
                case WEST -> VerticalLimitedConnection.NONE.equals(state.getValue(BlockStatePropertiesAA.EAST_STATE));
                case NORTH -> VerticalLimitedConnection.NONE.equals(state.getValue(BlockStatePropertiesAA.SOUTH_STATE));
                case EAST -> VerticalLimitedConnection.NONE.equals(state.getValue(BlockStatePropertiesAA.WEST_STATE));
            };
        }
        return false;
    }

    @Override
    public InteractionResult use(BlockState blockStateIn, final Level worldIn, final BlockPos blockPosIn, final Player playerEntityIn, final InteractionHand handIn, final BlockHitResult blockRaytraceResultIn) {

        final ItemStack mainHandItemStack = playerEntityIn.getMainHandItem();
        if(!mainHandItemStack.isEmpty() && mainHandItemStack.getItem() == this.asItem()) {
            return InteractionResult.PASS;
        }
        blockStateIn = blockStateIn.setValue(BlockStatePropertiesAA.ACTIVATED, !blockStateIn.getValue(BlockStatePropertiesAA.ACTIVATED));
        worldIn.setBlock(blockPosIn, blockStateIn, 10);

        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, final Direction directionIn, final BlockState facingStateIn, final LevelAccessor worldIn, final BlockPos currentPosIn, final BlockPos facingPosIn) {
        switch (directionIn) {
            case NORTH -> {
                if (!stateIn.getValue(BlockStatePropertiesAA.NORTH_STATE).equals(VerticalLimitedConnection.NONE) && facingStateIn.getBlock() instanceof PoolBlock) {
                    int level = facingStateIn.getValue(BlockStatePropertiesAA.LEVEL);
                    stateIn = stateIn.setValue(BlockStatePropertiesAA.ACTIVATED, level >= ((PoolBlock) facingStateIn.getBlock()).faucetLevel);
                }
            }
            case SOUTH -> {
                if (!stateIn.getValue(BlockStatePropertiesAA.SOUTH_STATE).equals(VerticalLimitedConnection.NONE) && facingStateIn.getBlock() instanceof PoolBlock) {
                    int level = facingStateIn.getValue(BlockStatePropertiesAA.LEVEL);
                    stateIn = stateIn.setValue(BlockStatePropertiesAA.ACTIVATED, level >= ((PoolBlock) facingStateIn.getBlock()).faucetLevel);
                }
            }
            case EAST -> {
                if (!stateIn.getValue(BlockStatePropertiesAA.EAST_STATE).equals(VerticalLimitedConnection.NONE) && facingStateIn.getBlock() instanceof PoolBlock) {
                    int level = facingStateIn.getValue(BlockStatePropertiesAA.LEVEL);
                    stateIn = stateIn.setValue(BlockStatePropertiesAA.ACTIVATED, level >= ((PoolBlock) facingStateIn.getBlock()).faucetLevel);
                }
            }
            case WEST -> {
                if (!stateIn.getValue(BlockStatePropertiesAA.WEST_STATE).equals(VerticalLimitedConnection.NONE) && facingStateIn.getBlock() instanceof PoolBlock) {
                    int level = facingStateIn.getValue(BlockStatePropertiesAA.LEVEL);
                    stateIn = stateIn.setValue(BlockStatePropertiesAA.ACTIVATED, level >= ((PoolBlock) facingStateIn.getBlock()).faucetLevel);
                }
            }
            case UP -> {
                if (stateIn.getValue(BlockStateProperties.UP) && facingStateIn.getBlock() instanceof PoolBlock) {
                    System.out.println("A !");
                    int level = facingStateIn.getValue(BlockStatePropertiesAA.LEVEL);
                    stateIn = stateIn.setValue(BlockStatePropertiesAA.ACTIVATED, level > 0);
                }
            }
            case DOWN -> {
                if (facingStateIn.getBlock() instanceof PoolBlock) {
                    int level = facingStateIn.getValue(BlockStatePropertiesAA.LEVEL);
                    stateIn = stateIn.setValue(BlockStatePropertiesAA.ACTIVATED, level >= ((PoolBlock) facingStateIn.getBlock()).maxLevel - 1);
                }
            }
        }

        return stateIn;
    }
}