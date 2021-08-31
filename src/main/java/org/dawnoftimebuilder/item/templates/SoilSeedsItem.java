package org.dawnoftimebuilder.item.templates;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import org.dawnoftimebuilder.block.templates.SoilCropsBlock;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;

public class SoilSeedsItem extends BlockItem implements IPlantable {
    private final SoilCropsBlock crops;

    public SoilSeedsItem(SoilCropsBlock crops) {
        super(crops, new Item.Properties().group(DOTB_TAB));
        this.crops = crops;
    }

    @Override
    public ActionResultType tryPlace(BlockItemUseContext context) {
        if(context.getFace() != Direction.UP) return ActionResultType.FAIL;

        World world = context.getWorld();
        BlockPos pos = context.getPos();

        if(!this.crops.isValidGround(this.crops.getDefaultState(), world, pos.down())) return ActionResultType.FAIL;
        if(!world.getBlockState(pos).isReplaceable(context)) return ActionResultType.FAIL;
        BlockItemUseContext blockitemusecontext = this.getBlockItemUseContext(context);
        if (blockitemusecontext == null) {
            return ActionResultType.FAIL;
        } else {
            BlockState madeState = this.getStateForPlacement(blockitemusecontext);
            if (madeState == null) {
                return ActionResultType.FAIL;
            } else if (!world.setBlockState(pos, madeState, 11)) {
                return ActionResultType.FAIL;
            } else {
                PlayerEntity playerentity = blockitemusecontext.getPlayer();
                ItemStack itemstack = blockitemusecontext.getItem();
                BlockState newState = world.getBlockState(pos);
                Block block = newState.getBlock();
                if (block == madeState.getBlock()) {
                    this.onBlockPlaced(pos, world, playerentity, itemstack, newState);
                    block.onBlockPlacedBy(world, pos, newState, playerentity, itemstack);
                    if (playerentity instanceof ServerPlayerEntity) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)playerentity, pos, itemstack);
                    }
                }
                SoundType soundtype = newState.getSoundType(world, pos, context.getPlayer());
                world.playSound(playerentity, pos, this.getPlaceSound(newState), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                itemstack.shrink(1);
                return ActionResultType.SUCCESS;
            }
        }
    }
    
    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos){
        return this.crops.getPlantType();
    }

    @Override
    public BlockState getPlant(IBlockReader world, BlockPos pos) {
        return this.crops.getDefaultState();
    }
}