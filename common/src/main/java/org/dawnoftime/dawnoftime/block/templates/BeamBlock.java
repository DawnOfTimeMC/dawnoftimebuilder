package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.block.IBlockPillar;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import static org.dawnoftime.dawnoftime.util.VoxelShapes.BEAM_SHAPES;

public class BeamBlock extends PergolaBlock {
    public static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;

    public BeamBlock(Properties properties, VoxelShape[] shapes) {
        super(properties, shapes);
        this.registerDefaultState(this.defaultBlockState().setValue(BOTTOM, false).setValue(AXIS_Y, false).setValue(AXIS_X, false).setValue(AXIS_Z, false).setValue(WATERLOGGED, false));
    }

    public BeamBlock(Properties properties){
        this(properties, BEAM_SHAPES);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BOTTOM);
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
            }
        }
        return true;
    }

    @Override
    public int getShapeIndex(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        int index = super.getShapeIndex(state, worldIn, pos, context);
        if(state.getValue(AXIS_Y) && state.getValue(BOTTOM)) {
            index += 4;
        }
        return index;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        if (state == null) {
            return null;
        }
        if (state.getValue(AXIS_Y)) {
            BlockState stateUnder = context.getLevel().getBlockState(context.getClickedPos().below());
            return state.setValue(BOTTOM, isBeamBottom(state, stateUnder));
        }
        return state;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, Player player, @NotNull InteractionHand handIn, @NotNull BlockHitResult hit) {
        if(player.isCrouching()) {
            worldIn.setBlock(pos, state.setValue(BOTTOM, !state.getValue(BOTTOM)), 10);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Nonnull
    @Override
    public BlockStatePropertiesAA.PillarConnection getBlockPillarConnectionAbove(BlockState state) {
        return state.getValue(AXIS_Y) ? BlockStatePropertiesAA.PillarConnection.TEN_PX : BlockStatePropertiesAA.PillarConnection.NOTHING;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("tooltip.dawnoftimebuilder.beam"));
    }
}
