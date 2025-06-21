package org.dawnoftime.dawnoftime.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.dawnoftime.dawnoftime.DoTBConfig;
import org.dawnoftime.dawnoftime.platform.Services;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.dawnoftime.dawnoftime.util.Utils;

import java.util.List;

import static net.minecraft.world.level.block.LeavesBlock.PERSISTENT;
import static org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA.AGE_0_6;
import static org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA.CLIMBING_PLANT;

public interface IBlockClimbingPlant {
    /**
     * Tries to increase the AGE of the Climbing Plant.
     * Else, tries to spread on an adjacent Block.
     *
     * @param stateIn Current State of the Block.
     * @param worldIn World of the Block.
     * @param pos     Position of the Block.
     * @param random  Used to determine if the Block must grow.
     */
    default void tickPlant(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource random) {
        if(!worldIn.isClientSide()) {
            if(stateIn.getValue(CLIMBING_PLANT).hasNoPlant() || stateIn.getValue(PERSISTENT))
                return;
            if(!worldIn.isLoaded(pos))
                return; // Forge: prevent loading unloaded chunks when checking neighbor's light

            if(worldIn.getRawBrightness(pos, 0) >= 8) {
                int age = stateIn.getValue(AGE_0_6);
				if (random.nextInt(Services.PLATFORM.getConfig().climbingPlantGrowthChance) == 0) {//Probability "can grow"
					if(age < 2){
						this.placePlant(stateIn.setValue(AGE_0_6, age + 1), worldIn, pos, 2);
						return;
					}else{
						if(stateIn.getValue(CLIMBING_PLANT).canGrow(worldIn, age)){
							this.placePlant(stateIn.setValue(AGE_0_6, 2 + ((age - 1) % 5)), worldIn, pos, 2);
							return;
						}
					}
				}
                if(age < 2 || random.nextInt(Services.PLATFORM.getConfig().climbingPlantSpreadChance) != 0)
                    return;//Probability "can spread"
                BlockPos[] positions = new BlockPos[] {
                        pos.north(),
                        pos.east(),
                        pos.south(),
                        pos.west(),
                        pos.above()
                };
                int index = random.nextInt(5);//Probability "chose the adjacent block to grow on"
                BlockState newState = worldIn.getBlockState(positions[index]);
                if(newState.getBlock() instanceof IBlockClimbingPlant newBlock) {
                    if(newBlock.canHavePlant(newState) && newState.getValue(CLIMBING_PLANT).hasNoPlant()) {
                        newBlock.placePlant(newState.setValue(CLIMBING_PLANT, stateIn.getValue(CLIMBING_PLANT)), worldIn, positions[index], 2);
                    }
                }
            }
        }
    }

    /**
     * When a Player right-clicks the Block with a Climbing Plant seed, it tries to set the corresponding Climbing Plant on the Block.
     *
     * @param stateIn Current State of the Block.
     * @param worldIn World of the Block.
     * @param pos     Position of the Block.
     * @param player  Player that right-clicked the Block.
     * @param handIn  Active hand.
     *
     * @return True if a Climbing Plant was successfully put on the Block.
     */
    default boolean tryPlacingPlant(BlockState stateIn, Level worldIn, BlockPos pos, Player player, InteractionHand handIn) {
        if(player.isCrouching())
            return false;
        ItemStack heldItemStack = player.getItemInHand(handIn);
        if(this.canHavePlant(stateIn) && stateIn.getValue(CLIMBING_PLANT).hasNoPlant()) {
            BlockStatePropertiesAA.ClimbingPlant plant = BlockStatePropertiesAA.ClimbingPlant.getFromItem(heldItemStack.getItem());
            if(!plant.hasNoPlant()) {
                stateIn = stateIn.setValue(CLIMBING_PLANT, plant);
                if(!player.isCreative()) {
                    heldItemStack.shrink(1);
                }
                this.placePlant(stateIn, worldIn, pos, 10);
                return true;
            }
        }
        return false;
    }

    /**
     * Function used to replace the BlockState with the new one with updated plant.
     *
     * @param state BlockState of the block with updated climbing plant state.
     * @param world World of the block.
     * @param pos   Pos of the block.
     */
    default void placePlant(BlockState state, Level world, BlockPos pos, int option) {
        world.setBlock(pos, state, option);
    }

    /**
     * If the player is in Creative, he can control plant's age with right-click and shift-right-click
     * Else, if the Climbing Plant is older than AGE 2 and has a loot_table, it drops its loots.
     * Else, if the player is sneaking, the plant is removed and its loots dropped.
     *
     * @param stateIn Current State of the Block.
     * @param worldIn World of the Block.
     * @param pos     Position of the Block.
     * @param player  Player that right-clicked the Block.
     * @param handIn  Active hand.
     *
     * @return True if the Climbing Plant was modified.
     */
    default InteractionResult harvestPlant(BlockState stateIn, Level worldIn, BlockPos pos, Player player, InteractionHand handIn) {
        if(player.isCreative() && stateIn.getValue(PERSISTENT) && !stateIn.getValue(CLIMBING_PLANT).hasNoPlant()) {
            if(player.isCrouching()) {
                if(stateIn.getValue(AGE_0_6) > 0) {
                    this.placePlant(stateIn.setValue(AGE_0_6, stateIn.getValue(AGE_0_6) - 1), worldIn, pos, 10);
                } else {
                    this.placePlant(stateIn.setValue(CLIMBING_PLANT, BlockStatePropertiesAA.ClimbingPlant.NONE).setValue(AGE_0_6, 0), worldIn, pos, 10);
                }
                return InteractionResult.SUCCESS;
            } else {
                if(stateIn.getValue(AGE_0_6) < stateIn.getValue(CLIMBING_PLANT).maxAge()) {
                    this.placePlant(stateIn.setValue(AGE_0_6, stateIn.getValue(AGE_0_6) + 1), worldIn, pos, 10);
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.PASS;
            }
        } else {
            if(stateIn.getValue(AGE_0_6) > 2) {
                if(this.dropPlant(stateIn, worldIn, pos, player.getItemInHand(handIn), true)) {
                    stateIn = stateIn.setValue(AGE_0_6, 2);
                    this.placePlant(stateIn, worldIn, pos, 10);
                    worldIn.playSound(null, pos, SoundEvents.GRASS_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }
            }
            if(player.isCrouching()) {
                return tryRemovingPlant(stateIn, worldIn, pos, player.getItemInHand(handIn));
            }
            return InteractionResult.PASS;
        }
    }

    /**
     * If the state in input contains a Climbing Plant, it removes it and drops its loots.
     *
     * @param stateIn       Current State of the Block.
     * @param worldIn       World of the Block.
     * @param pos           Position of the Block.
     * @param heldItemStack Item in active hand to apply tool conditions in the LootTable.
     *
     * @return True if a Climbing Plant was removed.
     */
    default InteractionResult tryRemovingPlant(BlockState stateIn, Level worldIn, BlockPos pos, ItemStack heldItemStack) {
        if(!stateIn.getValue(CLIMBING_PLANT).hasNoPlant()) {
            stateIn = this.removePlant(stateIn, worldIn, pos, heldItemStack);
            this.placePlant(stateIn, worldIn, pos, 10);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    /**
     * Removes the Climbing Plant, drop its LootTable, and play breaking sound.
     *
     * @param stateIn       Current State of the Block.
     * @param worldIn       World of the Block.
     * @param pos           Position of the Block.
     * @param heldItemStack Item in active hand to apply tool conditions in the LootTable.
     *
     * @return The State in input with no Climbing Plant, and AGE back to 0.
     */
    default BlockState removePlant(BlockState stateIn, LevelAccessor worldIn, BlockPos pos, ItemStack heldItemStack) {
        this.dropPlant(stateIn, worldIn, pos, heldItemStack, true);
        worldIn.playSound(null, pos, SoundEvents.GRASS_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
        stateIn = stateIn.setValue(CLIMBING_PLANT, BlockStatePropertiesAA.ClimbingPlant.NONE).setValue(AGE_0_6, 0);
        return stateIn;
    }

    /**
     * Drops one by one each item contained in the LootTable of the Climbing Plant at the corresponding age.
     * For example, if the Block contains a plant called "plant" at age "3", it will drop each Item in the LootTable named "plant_3" in Blocks folder.
     *
     * @param stateIn       Current State of the Block.
     * @param worldIn       World of the Block.
     * @param pos           Position of the Block.
     * @param heldItemStack Item in active hand to apply tool conditions.
     *
     * @return True if some loot is dropped. False if there were no loot_table found or item dropped.
     */
    default boolean dropPlant(BlockState stateIn, LevelAccessor worldIn, BlockPos pos, ItemStack heldItemStack, boolean bool) {
        if(worldIn.isClientSide())
            return false;
        BlockStatePropertiesAA.ClimbingPlant plant = stateIn.getValue(CLIMBING_PLANT);
        if(plant.hasNoPlant())
            return false;
        List<ItemStack> drops = Utils.getLootList((ServerLevel) worldIn, stateIn, heldItemStack, plant.getSerializedName() + "_" + stateIn.getValue(AGE_0_6));
        return Utils.dropLootFromList(worldIn, pos, drops, 1.0F);
    }

    /**
     * States if this block can have a climbing Plant.
     *
     * @param state Current state of the Block.
     *
     * @return True if the state of the Block allows Climbing Plants.
     */
    default boolean canHavePlant(BlockState state) {
        if(state.getBlock() instanceof SimpleWaterloggedBlock) {
            return !state.getValue(BlockStateProperties.WATERLOGGED);
        }
        return true;
    }

    /**
     * States if the block below can sustain a climbing plant growing in the block above it.
     *
     * @param stateUnder BlockState of the block under this block.
     *
     * @return True if the block under can sustain climbing plants (dirt or grass).
     */
    default boolean canSustainClimbingPlant(BlockState stateUnder) {
        Block block = stateUnder.getBlock();
        return block == Blocks.GRASS_BLOCK || stateUnder.is(BlockTags.DIRT);
    }
}
