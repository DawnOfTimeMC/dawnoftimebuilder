package org.dawnoftime.dawnoftime.item.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.dawnoftime.dawnoftime.block.templates.FlowerPotBlockDoT;
import org.dawnoftime.dawnoftime.item.IHasFlowerPot;
import javax.annotation.Nullable;

public class PotItem extends ItemDoTB implements IHasFlowerPot {
    private FlowerPotBlockDoT potBlock;

    public PotItem() {
        super();
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var stack = context.getItemInHand();
        Level world = context.getLevel();
        if (!world.isClientSide() && this.getPotBlock() != null) {
            BlockPos pos = context.getClickedPos();
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof FlowerPotBlock pot) {
                if (((FlowerPotBlock) pot.defaultBlockState().getBlock()).getContent() == Blocks.AIR) {
                    Player player = context.getPlayer();
                    if (player == null || !player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                    world.setBlock(pos, this.getPotBlock().getRandomState(), 2);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.useOn(context);
    }

    @Nullable
    @Override
    public FlowerPotBlockDoT getPotBlock() {
        return this.potBlock;
    }

    @Override
    public void setPotBlock(@Nullable FlowerPotBlockDoT pot) {
        this.potBlock = pot;
    }
}
