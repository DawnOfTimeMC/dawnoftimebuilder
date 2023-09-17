package org.dawnoftimebuilder.block.templates;

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
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties.VerticalLimitedConnection;

public class WaterJetBlock extends BlockDoTB {

    public WaterJetBlock(final Properties propertiesIn) {
        super(propertiesIn);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.UP, false).setValue(BlockStateProperties.DOWN, false).setValue(DoTBBlockStateProperties.NORTH_STATE, DoTBBlockStateProperties.VerticalLimitedConnection.NONE).setValue(DoTBBlockStateProperties.EAST_STATE, DoTBBlockStateProperties.VerticalLimitedConnection.NONE).setValue(DoTBBlockStateProperties.SOUTH_STATE, DoTBBlockStateProperties.VerticalLimitedConnection.NONE)
                .setValue(DoTBBlockStateProperties.WEST_STATE, DoTBBlockStateProperties.VerticalLimitedConnection.NONE).setValue(BlockStateProperties.POWERED, false).setValue(DoTBBlockStateProperties.ACTIVATED, false));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(
                BlockStateProperties.UP,
                BlockStateProperties.DOWN,
                DoTBBlockStateProperties.NORTH_STATE,
                DoTBBlockStateProperties.EAST_STATE,
                DoTBBlockStateProperties.SOUTH_STATE,
                DoTBBlockStateProperties.WEST_STATE,
                BlockStateProperties.POWERED,
                DoTBBlockStateProperties.ACTIVATED);
    }

    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        if (state.getBlock() != this) {
            state = super.getStateForPlacement(context);
        }
        if (state == null) {
            return this.defaultBlockState().setValue(BlockStateProperties.DOWN, true);
        }
        DoTBBlockStateProperties.VerticalLimitedConnection clickedFace = (context.getClickLocation().y - context.getClickedPos().getY() <= 0.5D) ? DoTBBlockStateProperties.VerticalLimitedConnection.BOTTOM : DoTBBlockStateProperties.VerticalLimitedConnection.TOP;
        switch (context.getClickedFace()) {
            default:
            case UP:
                return state.setValue(BlockStateProperties.DOWN, true);
            case DOWN:
                return state.setValue(BlockStateProperties.UP, true);
            case SOUTH:
                return state.setValue(DoTBBlockStateProperties.NORTH_STATE, clickedFace);
            case WEST:
                return state.setValue(DoTBBlockStateProperties.EAST_STATE, clickedFace);
            case NORTH:
                return state.setValue(DoTBBlockStateProperties.SOUTH_STATE, clickedFace);
            case EAST:
                return state.setValue(DoTBBlockStateProperties.WEST_STATE, clickedFace);
        }
    }

    @Override
    public boolean canBeReplaced(final BlockState state, final BlockPlaceContext useContext) {
        final ItemStack itemstack = useContext.getItemInHand();
        if (useContext.getPlayer() != null && useContext.getPlayer().isCrouching()) {
            return false;
        }
        if (itemstack.getItem() == this.asItem()) {
            final Direction newDirection = useContext.getClickedFace();
            switch (newDirection) {
                default:
                case UP:
                    return !state.getValue(BlockStateProperties.UP);
                case DOWN:
                    return !state.getValue(BlockStateProperties.DOWN);
                case SOUTH:
                    return DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(state.getValue(DoTBBlockStateProperties.NORTH_STATE));
                case WEST:
                    return DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(state.getValue(DoTBBlockStateProperties.EAST_STATE));
                case NORTH:
                    return DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(state.getValue(DoTBBlockStateProperties.SOUTH_STATE));
                case EAST:
                    return DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(state.getValue(DoTBBlockStateProperties.WEST_STATE));
            }
        }
        return false;
    }

    @Override
    public InteractionResult use(BlockState blockStateIn, final Level worldIn, final BlockPos blockPosIn, final Player playerEntityIn, final InteractionHand handIn, final BlockHitResult blockRaytraceResultIn) {

        final ItemStack mainHandItemStack = playerEntityIn.getMainHandItem();
        if (!mainHandItemStack.isEmpty() && mainHandItemStack.getItem() == this.asItem()) {
            return InteractionResult.PASS;
        }
        blockStateIn = blockStateIn.setValue(DoTBBlockStateProperties.ACTIVATED, !blockStateIn.getValue(DoTBBlockStateProperties.ACTIVATED));
        worldIn.setBlock(blockPosIn, blockStateIn, 10);

        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, final Direction directionIn, final BlockState facingStateIn, final LevelAccessor worldIn, final BlockPos currentPosIn, final BlockPos facingPosIn) {
        switch (directionIn) {
            case NORTH:
                if (!stateIn.getValue(DoTBBlockStateProperties.NORTH_STATE).equals(VerticalLimitedConnection.NONE) && facingStateIn.getBlock() instanceof BasePoolBlock) {
                    int level = facingStateIn.getValue(DoTBBlockStateProperties.LEVEL);
                    stateIn = stateIn.setValue(DoTBBlockStateProperties.ACTIVATED, level >= ((BasePoolBlock) facingStateIn.getBlock()).faucetLevel);
                }
                break;
            case SOUTH:
                if (!stateIn.getValue(DoTBBlockStateProperties.SOUTH_STATE).equals(VerticalLimitedConnection.NONE) && facingStateIn.getBlock() instanceof BasePoolBlock) {
                    int level = facingStateIn.getValue(DoTBBlockStateProperties.LEVEL);
                    stateIn = stateIn.setValue(DoTBBlockStateProperties.ACTIVATED, level >= ((BasePoolBlock) facingStateIn.getBlock()).faucetLevel);
                }
                break;
            case EAST:
                if (!stateIn.getValue(DoTBBlockStateProperties.EAST_STATE).equals(VerticalLimitedConnection.NONE) && facingStateIn.getBlock() instanceof BasePoolBlock) {
                    int level = facingStateIn.getValue(DoTBBlockStateProperties.LEVEL);
                    stateIn = stateIn.setValue(DoTBBlockStateProperties.ACTIVATED, level >= ((BasePoolBlock) facingStateIn.getBlock()).faucetLevel);
                }
                break;
            case WEST:
                if (!stateIn.getValue(DoTBBlockStateProperties.WEST_STATE).equals(VerticalLimitedConnection.NONE) && facingStateIn.getBlock() instanceof BasePoolBlock) {
                    int level = facingStateIn.getValue(DoTBBlockStateProperties.LEVEL);
                    stateIn = stateIn.setValue(DoTBBlockStateProperties.ACTIVATED, level >= ((BasePoolBlock) facingStateIn.getBlock()).faucetLevel);
                }
                break;
            case UP:
                if (stateIn.getValue(BlockStateProperties.UP) && facingStateIn.getBlock() instanceof BasePoolBlock) {
                    System.out.println("A !");
                    int level = facingStateIn.getValue(DoTBBlockStateProperties.LEVEL);
                    stateIn = stateIn.setValue(DoTBBlockStateProperties.ACTIVATED, level > 0);
                }
                break;
            case DOWN:
                if (facingStateIn.getBlock() instanceof BasePoolBlock) {
                    int level = facingStateIn.getValue(DoTBBlockStateProperties.LEVEL);
                    stateIn = stateIn.setValue(DoTBBlockStateProperties.ACTIVATED, level >= ((BasePoolBlock) facingStateIn.getBlock()).maxLevel - 1);
                }
                break;
        }

        return stateIn;
    }
}