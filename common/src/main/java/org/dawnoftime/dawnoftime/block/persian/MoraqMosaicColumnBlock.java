package org.dawnoftime.dawnoftime.block.persian;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import org.dawnoftime.dawnoftime.block.templates.ConnectedVerticalBlock;
import org.dawnoftime.dawnoftime.util.Utils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static org.dawnoftime.dawnoftime.util.Utils.clickedOnLeftHalf;
import static org.dawnoftime.dawnoftime.util.VoxelShapes.MORAQ_MOSAIC_COLUMN_SHAPES;

public class MoraqMosaicColumnBlock extends ConnectedVerticalBlock {

    public MoraqMosaicColumnBlock(Properties properties) {
        super(properties, MORAQ_MOSAIC_COLUMN_SHAPES);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.INVERTED, false));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.INVERTED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state.setValue(BlockStateProperties.INVERTED, clickedOnLeftHalf(context.getClickedPos(), context.getHorizontalDirection(), context.getClickLocation()));
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level worldIn, BlockPos pos, Player player, BlockHitResult hit) {
        state = state.setValue(BlockStateProperties.INVERTED, clickedOnLeftHalf(pos, hit.getDirection(), hit.getLocation()));
        return super.useWithoutItem(state, worldIn, pos, player, hit);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Utils.addTooltip(tooltipComponents, this, Utils.TOOLTIP_COLUMN);
    }
}
