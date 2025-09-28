package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.block.IBlockPillar;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.dawnoftime.dawnoftime.util.Utils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.List;

import static org.dawnoftime.dawnoftime.util.Utils.TOOLTIP_BEAM;
import static org.dawnoftime.dawnoftime.util.VoxelShapes.PERGOLA_SHAPES;

public class PergolaBlock extends WaterloggedBlock implements IBlockPillar {
    public static final BooleanProperty AXIS_X = BlockStatePropertiesAA.AXIS_X;
    public static final BooleanProperty AXIS_Y = BlockStatePropertiesAA.AXIS_Y;
    public static final BooleanProperty AXIS_Z = BlockStatePropertiesAA.AXIS_Z;

    public PergolaBlock(Properties properties, VoxelShape[] shapes) {
        super(properties, shapes);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS_Y, false).setValue(AXIS_X, false).setValue(AXIS_Z, false).setValue(WATERLOGGED, false));
    }

    public PergolaBlock(Properties properties){
        this(properties, PERGOLA_SHAPES);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AXIS_Y, AXIS_X, AXIS_Z);
    }

    @Override
    public int getShapeIndex(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        if(state.getValue(AXIS_Y)) {
            int index = 3;
            if(state.getValue(AXIS_X))
                index += 1;
            if(state.getValue(AXIS_Z))
                index += 2;
            return index;
        } else {
            int index = state.getValue(AXIS_Z) ? 1 : 0;
            return state.getValue(AXIS_X) ? index * 2 : index;
        }
    }

    @Nonnull
    @Override
    public BlockStatePropertiesAA.PillarConnection getBlockPillarConnectionAbove(BlockState state) {
        return state.getValue(AXIS_Y) ? BlockStatePropertiesAA.PillarConnection.SIX_PX : BlockStatePropertiesAA.PillarConnection.NOTHING;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        if(state.getBlock() != this) {
            state = super.getStateForPlacement(context);
            if (state == null) {
                return null;
            }
        }
        return switch (context.getClickedFace().getAxis()) {
            case X -> state.setValue(AXIS_X, true);
            case Z -> state.setValue(AXIS_Z, true);
            default -> state.setValue(AXIS_Y, true);
        };
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
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        boolean x = state.getValue(AXIS_X);
        boolean z = state.getValue(AXIS_Z);

        return switch (rotation) {
            case CLOCKWISE_90, COUNTERCLOCKWISE_90 -> state.setValue(AXIS_X, z).setValue(AXIS_Z, x);
            default -> state;
        };
    }
}
