package org.dawnoftime.dawnoftime.block.french;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.block.templates.WaterloggedHorizontalBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StoneBricksArrowslitBlock extends WaterloggedHorizontalBlock {
    public StoneBricksArrowslitBlock(Properties properties, VoxelShape[] shapes) {
        super(properties, shapes);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("tooltip.dawnoftimebuilder.stone_bricks_defense"));
    }
}
