package org.dawnoftimebuilder.item.templates;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.templates.SoilCropsBlock;

import javax.annotation.Nullable;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;

public class SoilSeedsItem extends BlockItem {
    private final SoilCropsBlock crops;

    public SoilSeedsItem(SoilCropsBlock crops, @Nullable Food food) {
        super(crops, food != null ? new Item.Properties().tab(DOTB_TAB).food(food) : new Item.Properties().tab(DOTB_TAB));
        this.crops = crops;
    }

    @Override
    public ActionResultType place(BlockItemUseContext context) {
        if(context.getClickedFace() != Direction.UP) return ActionResultType.FAIL;

        World world = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if(!this.crops.canSurvive(this.crops.defaultBlockState(), world, pos)) return ActionResultType.FAIL;
        if(!world.getBlockState(pos).canBeReplaced(context)) return ActionResultType.FAIL;
        BlockItemUseContext blockitemusecontext = this.updatePlacementContext(context);
        if (blockitemusecontext == null) {
            return ActionResultType.FAIL;
        } else {
            BlockState madeState = this.getPlacementState(blockitemusecontext);
            if (madeState == null) {
                return ActionResultType.FAIL;
            } else if (!world.setBlock(pos, madeState, 11)) {
                return ActionResultType.FAIL;
            } else {
                PlayerEntity playerentity = blockitemusecontext.getPlayer();
                ItemStack itemstack = blockitemusecontext.getItemInHand();
                BlockState newState = world.getBlockState(pos);
                Block block = newState.getBlock();
                if (block == madeState.getBlock()) {
                    this.updateCustomBlockEntityTag(pos, world, playerentity, itemstack, newState);
                    block.setPlacedBy(world, pos, newState, playerentity, itemstack);
                    if (playerentity instanceof ServerPlayerEntity) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)playerentity, pos, itemstack);
                    }
                }
                SoundType soundtype = newState.getSoundType(world, pos, context.getPlayer());
                world.playSound(playerentity, pos, this.getPlaceSound(newState, world, pos, context.getPlayer()), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                itemstack.shrink(1);
                return ActionResultType.SUCCESS;
            }
        }
    }
}