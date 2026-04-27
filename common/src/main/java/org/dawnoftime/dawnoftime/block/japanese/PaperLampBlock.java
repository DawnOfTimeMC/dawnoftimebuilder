package org.dawnoftime.dawnoftime.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.dawnoftime.dawnoftime.block.IBlockSpecialDisplay;
import org.dawnoftime.dawnoftime.block.templates.ConnectedVerticalBlock;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;

import static org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA.VerticalConnection;
import static org.dawnoftime.dawnoftime.util.VoxelShapes.PAPER_LAMP_SHAPES;

public class PaperLampBlock extends ConnectedVerticalBlock implements IBlockSpecialDisplay {

    public PaperLampBlock(Properties properties) {
        super(properties, PAPER_LAMP_SHAPES);
    }

    @Override
    public int getShapeIndex(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        VerticalConnection connection = state.getValue(VERTICAL_CONNECTION);
        return connection == VerticalConnection.ABOVE || connection == VerticalConnection.BOTH ? 0 : 1;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Item.TooltipContext context,
            @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.dawnoftimebuilder.column_label"));
        tooltip.add(Component.translatable("tooltip.dawnoftimebuilder.column"));
    }

    @Override
    public boolean emitsLight() {
        return true;
    }

    @Override
    public float getDisplayScale() {
        return 0.6F;
    }
}
