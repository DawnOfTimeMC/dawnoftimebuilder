package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.block.IBlockClimbingPlant;
import org.dawnoftime.dawnoftime.block.IBlockPillar;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.dawnoftime.dawnoftime.util.Utils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static org.dawnoftime.dawnoftime.util.Utils.TOOLTIP_BEAM;
import static org.dawnoftime.dawnoftime.util.Utils.TOOLTIP_CLIMBING_PLANT;
import static org.dawnoftime.dawnoftime.util.VoxelShapes.BEAM_SHAPES;

public class BeamBlock extends WaterloggedBlock implements IBlockPillar, IBlockClimbingPlant {
    public static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;
    public static final BooleanProperty AXIS_X = BlockStatePropertiesAA.AXIS_X;
    public static final BooleanProperty AXIS_Y = BlockStatePropertiesAA.AXIS_Y;
    public static final BooleanProperty AXIS_Z = BlockStatePropertiesAA.AXIS_Z;
    public static final EnumProperty<BlockStatePropertiesAA.ClimbingPlant> CLIMBING_PLANT = BlockStatePropertiesAA.CLIMBING_PLANT;
    private static final IntegerProperty AGE = BlockStatePropertiesAA.AGE_0_6;
    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;

    public BeamBlock(Properties properties, VoxelShape[] shapes) {
        super(properties, shapes);
        this.registerDefaultState(this.defaultBlockState().setValue(BOTTOM, false).setValue(AXIS_Y, false).setValue(AXIS_X, false).setValue(AXIS_Z, false).setValue(CLIMBING_PLANT, BlockStatePropertiesAA.ClimbingPlant.NONE).setValue(AGE, 0).setValue(WATERLOGGED, false).setValue(PERSISTENT, false));
    }

    public BeamBlock(Properties properties){
        this(properties, BEAM_SHAPES);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BOTTOM, AXIS_Y, AXIS_X, AXIS_Z, CLIMBING_PLANT, AGE, PERSISTENT);
    }

    @Override
    public int getShapeIndex(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        if(state.getValue(AXIS_Y)) {
            int index = 3;
            if(state.getValue(BOTTOM))
                index += 1;
            if(state.getValue(AXIS_X))
                index += 2;
            if(state.getValue(AXIS_Z))
                index += 4;
            return index;
        } else {
            int index = state.getValue(AXIS_Z) ? 1 : 0;
            return state.getValue(AXIS_X) ? index * 2 : index;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        if(state.getBlock() != this) {
            state = super.getStateForPlacement(context);
        }
        switch (context.getClickedFace().getAxis()) {
            default -> {
                BlockState stateUnder = context.getLevel().getBlockState(context.getClickedPos().below());
                state = state.setValue(AXIS_Y, true);
                return state.setValue(BOTTOM, isBeamBottom(state, stateUnder));
            }
            case X -> {
                return state.setValue(AXIS_X, true);
            }
            case Z -> {
                return state.setValue(AXIS_Z, true);
            }
        }
    }

    @Override
    public boolean canBeReplaced(@NotNull BlockState state, BlockPlaceContext useContext) {
        ItemStack itemstack = useContext.getItemInHand();
        if(useContext.getPlayer() != null && useContext.getPlayer().isCrouching())
            return false;
        if(itemstack.getItem() == this.asItem()) {
            if(useContext.replacingClickedOnBlock()) {
                return switch (useContext.getClickedFace().getAxis()) {
                    case X -> !state.getValue(AXIS_X);
                    case Y -> !state.getValue(AXIS_Y);
                    case Z -> !state.getValue(AXIS_Z);
                };
            }
        }
        return false;
    }

    @Override
    public void spawnAfterBreak(BlockState state, ServerLevel worldIn, BlockPos pos, ItemStack stack, boolean p_222953_) {
        super.spawnAfterBreak(state, worldIn, pos, stack, p_222953_);
        // Be careful, climbing plants are not dropping from block's loot_table, but from their own loot_table
        this.dropPlant(state, worldIn, pos, stack, p_222953_);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(CLIMBING_PLANT).hasNoPlant();
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        this.tickPlant(state, worldIn, pos, random);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        // If the block is not PERSISTENT, we change it to persistent state to prevent plant growth.
        if(!state.getValue(PERSISTENT)) {
            if(Utils.useLighter(worldIn, pos, player, handIn)) {
                Random rand = new Random();
                for(int i = 0; i < 5; i++) {
                    worldIn.addAlwaysVisibleParticle(ParticleTypes.SMOKE, (double) pos.getX() + rand.nextDouble(), (double) pos.getY() + 0.5D + rand.nextDouble() / 2, (double) pos.getZ() + rand.nextDouble(), 0.0D, 0.07D, 0.0D);
                }
                worldIn.setBlock(pos, state.setValue(PERSISTENT, true), 10);
                return InteractionResult.SUCCESS;
            }
        }
        // If the player is in creative or if he right-clicked the most bottom block, we try to put plant on it.
        BlockState stateUnder = worldIn.getBlockState(pos.below());
        if((this.isBeamBottom(state, stateUnder) && this.canSustainClimbingPlant(stateUnder)) || player.isCreative()) {
            if(this.tryPlacingPlant(state, worldIn, pos, player, handIn)) {
                return InteractionResult.SUCCESS;
            }
        }
        // If there is a plant that can be harvested, we harvest it.
        if(this.harvestPlant(state, worldIn, pos, player, handIn) == InteractionResult.SUCCESS) {
            return InteractionResult.SUCCESS;
        }
        if(player.isCrouching()) {
            if(state.getValue(CLIMBING_PLANT).hasNoPlant()) {
                // If there is no plant and the player is snicking, we switch on/off the bottom.
                if(this.isBeamBottom(state, stateUnder)) {
                    this.placePlant(state.setValue(BOTTOM, !state.getValue(BOTTOM)), worldIn, pos, 10);
                    return InteractionResult.SUCCESS;
                }
            } else {
                // If there is a plant and the player is snicking, we remove the plant.
                this.placePlant(this.removePlant(state, worldIn, pos, ItemStack.EMPTY), worldIn, pos, 10);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Nonnull
    @Override
    public BlockStatePropertiesAA.PillarConnection getBlockPillarConnectionAbove(BlockState state) {
        return state.getValue(AXIS_Y) ? BlockStatePropertiesAA.PillarConnection.TEN_PX : BlockStatePropertiesAA.PillarConnection.NOTHING;
    }

    // TODO: Implement workaround for this.
//    @Override
//    public boolean isLadder(BlockState state, LevelReader world, BlockPos pos, LivingEntity entity) {
//        return !state.getValue(CLIMBING_PLANT).hasNoPlant();
//    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        Utils.addTooltip(tooltip, TOOLTIP_BEAM, TOOLTIP_CLIMBING_PLANT);
    }

    @Override
    public boolean canHavePlant(BlockState state) {
        return IBlockClimbingPlant.super.canHavePlant(state) && !state.getValue(BOTTOM);
    }

    /**
     * Checks if the BlockState of the block under require this block to have a bottom.
     *
     * @param state      is the state of this block.
     * @param stateUnder is the state of the block below.
     *
     * @return True if this block is the bottom of the beam pillar.
     */
    public boolean isBeamBottom(BlockState state, BlockState stateUnder) {
        if(state.getValue(AXIS_Y)) {
            if(stateUnder.getBlock() instanceof BeamBlock) {
                return !stateUnder.getValue(AXIS_Y);
            } else {
                return !(stateUnder.getBlock() instanceof IBlockClimbingPlant);
            }
        } else {
            return true;
        }
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        boolean x = state.getValue(AXIS_X);
        boolean z = state.getValue(AXIS_Z);

        return switch (rotation) {
            case CLOCKWISE_90, COUNTERCLOCKWISE_90 -> state.setValue(AXIS_X, z).setValue(AXIS_Z, x);
            default -> state;
        };
    }
}
