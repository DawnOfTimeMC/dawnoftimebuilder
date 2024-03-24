package org.dawnoftimebuilder.block.persian;

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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.ColumnConnectibleBlock;
import org.dawnoftimebuilder.util.DoTBUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static org.dawnoftimebuilder.util.DoTBUtils.clickedOnLeftHalf;

public class MoraqMosaicColumnBlock extends ColumnConnectibleBlock {
    private static final VoxelShape VS_LONE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape VS_BOT = Shapes.or(
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D),
            Block.box(2.5D, 6.0D, 2.5D, 13.5D, 16.0D, 13.5D)
    );
    private static final VoxelShape VS_MID = Block.box(2.5D, 0.0D, 2.5D, 13.5D, 16.0D, 13.5D);
    private static final VoxelShape VS_TOP = Shapes.or(
            Block.box(2.0D, 14.0D, 2.0D, 14.0D, 16.0D, 14.0D),
            Block.box(2.5D, 0.0D, 2.5D, 13.5D, 14.0D, 13.5D)
    );
    public MoraqMosaicColumnBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.INVERTED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(VERTICAL_CONNECTION)) {
            default -> VS_TOP;
            case BOTH -> VS_MID;
            case NONE -> VS_LONE;
            case ABOVE -> VS_BOT;
        };
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.INVERTED);
    }

    @NotNull
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state.setValue(BlockStateProperties.INVERTED, clickedOnLeftHalf(context.getClickedPos(), context.getHorizontalDirection(), context.getClickLocation()));
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        state = state.setValue(BlockStateProperties.INVERTED, clickedOnLeftHalf(pos, hit.getDirection(), hit.getLocation()));
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        DoTBUtils.addTooltip(tooltip, this, DoTBUtils.TOOLTIP_COLUMN);
    }
}
