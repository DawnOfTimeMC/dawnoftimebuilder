package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class ConnectedVerticalSidedPlanBlock extends ConnectedVerticalSidedBlock {
    public static final EnumProperty<BlockStatePropertiesAA.HorizontalConnection> HORIZONTAL_CONNECTION = BlockStatePropertiesAA.HORIZONTAL_CONNECTION;

    public ConnectedVerticalSidedPlanBlock(Properties properties, VoxelShape[] shapes) {
        super(properties, shapes);
        this.registerDefaultState(this.defaultBlockState().setValue(HORIZONTAL_CONNECTION, BlockStatePropertiesAA.HorizontalConnection.NONE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HORIZONTAL_CONNECTION);
    }

    @Override
    public int getShapeIndex(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        int index = 16 * state.getValue(FACING).get2DDataValue();
        index += 4 * state.getValue(VERTICAL_CONNECTION).getIndex();
        index += state.getValue(HORIZONTAL_CONNECTION).getIndex();
        return index;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public @NotNull BlockState updateShape(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        return (facing.getAxis() == stateIn.getValue(FACING).getClockWise().getAxis()) ? stateIn.setValue(HORIZONTAL_CONNECTION, this.getLineState(worldIn, currentPos, stateIn)) : stateIn;
    }

    public BlockStatePropertiesAA.HorizontalConnection getLineState(LevelAccessor worldIn, BlockPos pos, BlockState stateIn) {
        Direction direction = stateIn.getValue(FACING).getClockWise();
        if(isConnectible(stateIn, worldIn, pos.relative(direction, -1), direction)) {
            return (isConnectible(stateIn, worldIn, pos.relative(direction), direction)) ? BlockStatePropertiesAA.HorizontalConnection.BOTH : BlockStatePropertiesAA.HorizontalConnection.LEFT;
        } else {
            return (isConnectible(stateIn, worldIn, pos.relative(direction), direction)) ? BlockStatePropertiesAA.HorizontalConnection.RIGHT : BlockStatePropertiesAA.HorizontalConnection.NONE;
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
    }
}