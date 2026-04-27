package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.dawnoftime.dawnoftime.block.IFlammable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RotatedPillarBlockDoT extends RotatedPillarBlock implements IFlammable {
    private int fireSpreadSpeed = 0;
    private int fireDestructionSpeed = 0;
    private final String[] tooltipKeys;

    public RotatedPillarBlockDoT(Properties properties, String... tooltipKeys) {
        super(properties);
        this.tooltipKeys = tooltipKeys != null ? tooltipKeys : new String[0];
    }

    public RotatedPillarBlockDoT(Properties properties) {
        this(properties, new String[0]);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Item.TooltipContext context,
            @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        for (String key : tooltipKeys) {
            tooltip.add(Component.translatable(key));
        }
    }

    /**
     * Set Encouragement to 5 and Flammability to 20
     *
     * @return this
     */
    public Block setBurnable() {
        return setBurnable(5, 20);
    }

    /**
     * Set burning parameters (default 5 / 20)
     *
     * @param fireSpreadSpeed      Increases the probability to catch fire
     * @param fireDestructionSpeed Decreases burning duration
     *
     * @return this
     */
    public Block setBurnable(int fireSpreadSpeed, int fireDestructionSpeed) {
        this.fireSpreadSpeed = fireSpreadSpeed;
        this.fireDestructionSpeed = fireDestructionSpeed;
        return this;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) ? 0 : this.fireSpreadSpeed;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) ? 0 : this.fireDestructionSpeed;
    }
}
