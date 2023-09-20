package org.dawnoftimebuilder.item.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.templates.FlowerPotBlockDoTB;
import org.dawnoftimebuilder.item.IHasFlowerPot;

public class PotAndBlockItem extends BlockItem implements IHasFlowerPot {

    private FlowerPotBlockDoTB potBlock;

    public PotAndBlockItem(Block block , Properties properties) {
        super(block, properties);
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        World world = context.getLevel();
        if(!world.isClientSide() && this.getPotBlock() != null){
            BlockPos pos = context.getClickedPos();
            BlockState state = world.getBlockState(pos);
            if(state.getBlock() instanceof FlowerPotBlock){
                FlowerPotBlock pot = (FlowerPotBlock) state.getBlock();
                if(pot.getEmptyPot().getContent() == Blocks.AIR){
                    world.setBlock(pos, this.getPotBlock().getRandomState(), 2);
                    return ActionResultType.SUCCESS;
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
