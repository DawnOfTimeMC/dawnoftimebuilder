package org.dawnoftimebuilder.item.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.dawnoftimebuilder.block.templates.FlowerPotBlockDoTB;
import org.dawnoftimebuilder.item.IHasFlowerPot;

public class PotAndBlockItem extends BlockItem implements IHasFlowerPot {
    private FlowerPotBlockDoTB potBlock;

    public PotAndBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level world = context.getLevel();
        if(!world.isClientSide() && this.getPotBlock() != null) {
            BlockPos pos = context.getClickedPos();
            BlockState state = world.getBlockState(pos);
            if(state.getBlock() instanceof FlowerPotBlock pot) {
                if(pot.getEmptyPot().getContent() == Blocks.AIR) {
                    world.setBlock(pos, this.getPotBlock().getRandomState(), 2);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.onItemUseFirst(stack, context);
    }

    @Override
    public FlowerPotBlockDoTB getPotBlock() {
        return this.potBlock;
    }

    @Override
    public void setPotBlock(FlowerPotBlockDoTB pot) {
        this.potBlock = pot;
    }
}
