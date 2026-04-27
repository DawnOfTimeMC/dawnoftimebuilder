package org.dawnoftime.dawnoftime.block.roman;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.dawnoftime.dawnoftime.block.IBlockPillar;
import org.dawnoftime.dawnoftime.block.templates.ConnectedVerticalBlock;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nonnull;

import java.util.List;
import static org.dawnoftime.dawnoftime.util.VoxelShapes.SANDSTONE_COLUMN_SHAPES;

public class SandstoneColumnBlock extends ConnectedVerticalBlock implements IBlockPillar {
    public SandstoneColumnBlock(Properties properties) {
        super(properties, SANDSTONE_COLUMN_SHAPES);
    }

    @Override
    public int getShapeIndex(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return switch (state.getValue(VERTICAL_CONNECTION)) {
            case ABOVE -> 1;
            case UNDER -> 2;
            default -> 0;
        };
    }

    @Nonnull
    @Override
    public BlockStatePropertiesAA.PillarConnection getBlockPillarConnectionAbove(BlockState state) {
        return BlockStatePropertiesAA.PillarConnection.EIGHT_PX;
    }

    @Override
    public boolean isConnectible(BlockState stateIn, LevelAccessor worldIn, BlockPos pos, Direction faceToConnect) {
        BlockState testedState = worldIn.getBlockState(pos);
        if (faceToConnect == Direction.DOWN && IBlockPillar.getPillarConnectionUnder(worldIn, pos) == BlockStatePropertiesAA.PillarConnection.EIGHT_PX) {
            return true;
        }
        if (faceToConnect == Direction.UP && IBlockPillar.getPillarConnectionAbove(worldIn, pos) == BlockStatePropertiesAA.PillarConnection.EIGHT_PX) {
            return true;
        }
        return testedState.getBlock() == this;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Item.TooltipContext context,
            @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.dawnoftimebuilder.column_label"));
        tooltip.add(Component.translatable("tooltip.dawnoftimebuilder.column"));
    }
}
