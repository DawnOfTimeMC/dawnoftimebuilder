package org.dawnoftime.dawnoftime.item.templates;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.dawnoftime.dawnoftime.block.templates.FlowerPotBlockDoT;
import org.dawnoftime.dawnoftime.block.templates.SoilCropsBlock;
import org.dawnoftime.dawnoftime.item.IHasFlowerPot;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nullable;

public class SoilSeedsItem extends BlockItem implements IHasFlowerPot {
    private FlowerPotBlockDoT potBlock;

    public <T extends SoilCropsBlock> SoilSeedsItem(T crops, @Nullable FoodProperties food) {
        super(crops, food != null ? new Properties().food(food) : new Properties());
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        var stack = context.getItemInHand();
        Level world = context.getLevel();
        if (!world.isClientSide() && this.getPotBlock() != null) {
            BlockPos pos = context.getClickedPos();
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof FlowerPotBlock pot && ((FlowerPotBlock) pot.defaultBlockState().getBlock()).getContent() == Blocks.AIR) {
                Player player = context.getPlayer();
                if (player == null || !player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                world.setBlock(pos, this.getPotBlock().getRandomState(), 2);
                return InteractionResult.SUCCESS;
            }
        }
        return super.useOn(context);
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        if (context.getClickedFace() != Direction.UP)
            return InteractionResult.FAIL;

        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if (!this.getBlock().canSurvive(this.getBlock().defaultBlockState(), world, pos))
            return InteractionResult.FAIL;
        if (!world.getBlockState(pos).canBeReplaced(context))
            return InteractionResult.FAIL;
        BlockPlaceContext blockitemusecontext = this.updatePlacementContext(context);
        if (blockitemusecontext == null) {
            return InteractionResult.FAIL;
        } else {
            BlockState madeState = this.getPlacementState(blockitemusecontext);
            if (madeState == null) {
                return InteractionResult.FAIL;
            } else if (!world.setBlock(pos, madeState, 11)) {
                return InteractionResult.FAIL;
            } else {
                Player playerentity = blockitemusecontext.getPlayer();
                ItemStack itemstack = blockitemusecontext.getItemInHand();
                BlockState newState = world.getBlockState(pos);
                Block block = newState.getBlock();
                if (block == madeState.getBlock()) {
                    this.updateCustomBlockEntityTag(pos, world, playerentity, itemstack, newState);
                    block.setPlacedBy(world, pos, newState, playerentity, itemstack);
                    if (playerentity instanceof ServerPlayer) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) playerentity, pos, itemstack);
                    }
                }
                SoundType soundtype = newState.getSoundType();
                world.playSound(playerentity, pos, this.getPlaceSound(newState), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                itemstack.shrink(1);
                return InteractionResult.SUCCESS;
            }
        }
    }

    @Override
    public FlowerPotBlockDoT getPotBlock() {
        return this.potBlock;
    }

    @Override
    public void setPotBlock(FlowerPotBlockDoT pot) {
        this.potBlock = pot;
    }
}