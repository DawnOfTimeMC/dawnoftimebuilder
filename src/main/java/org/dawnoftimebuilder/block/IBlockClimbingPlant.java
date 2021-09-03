package org.dawnoftimebuilder.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockUtils;
import org.dawnoftimebuilder.util.DoTBConfig;

import java.util.List;
import java.util.Random;

import static net.minecraft.state.properties.BlockStateProperties.PERSISTENT;
import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;
import static org.dawnoftimebuilder.util.DoTBBlockStateProperties.AGE_0_6;
import static org.dawnoftimebuilder.util.DoTBBlockStateProperties.CLIMBING_PLANT;

public interface IBlockClimbingPlant {

	/**
	 * Tries to increase the AGE of the Climbing Plant.
	 * Else, tries to spread on an adjacent Block.
	 * @param stateIn Current State of the Block.
	 * @param worldIn World of the Block.
	 * @param pos Position of the Block.
	 * @param random Used to determine if the Block must grow.
	 */
	default void tickPlant(BlockState stateIn, World worldIn, BlockPos pos, Random random){
		if (!worldIn.isRemote()) {
			if (stateIn.get(CLIMBING_PLANT).hasNoPlant() || stateIn.get(PERSISTENT)) return;
			if (!worldIn.isAreaLoaded(pos, 2)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light

			if (worldIn.getLightSubtracted(pos, 0) >= 8) {
				int age = stateIn.get(AGE_0_6);
				if (ForgeHooks.onCropsGrowPre(worldIn, pos, stateIn, random.nextInt(DoTBConfig.CLIMBING_PLANT_GROWTH_CHANCE.get()) == 0)) {//Probability "can grow"
					if(age < 2){
						worldIn.setBlockState(pos, stateIn.with(AGE_0_6, age + 1), 2);
						ForgeHooks.onCropsGrowPost(worldIn, pos, stateIn);
						return;
					}else{
						if(stateIn.get(CLIMBING_PLANT).canGrow(worldIn, age)){
							worldIn.setBlockState(pos, stateIn.with(AGE_0_6, 2 + ((age - 1) % 5)), 2);
							ForgeHooks.onCropsGrowPost(worldIn, pos, stateIn);
							return;
						}
					}
				}
				if(age < 2 || random.nextInt(DoTBConfig.CLIMBING_PLANT_SPREAD_CHANCE.get()) != 0) return;//Probability "can spread"
				BlockPos[] positions = new BlockPos[]{
						pos.north(),
						pos.east(),
						pos.south(),
						pos.west(),
						pos.up()
				};
				int index = random.nextInt(5);//Probability "chose the adjacent block to grow on"
				BlockState newState = worldIn.getBlockState(positions[index]);
				if(newState.getBlock() instanceof IBlockClimbingPlant){
					IBlockClimbingPlant newBlock = (IBlockClimbingPlant) newState.getBlock();
					if(newBlock.canHavePlant(newState) && newState.get(CLIMBING_PLANT).hasNoPlant()){
						worldIn.setBlockState(positions[index], newState.with(CLIMBING_PLANT, stateIn.get(CLIMBING_PLANT)));
					}
				}
			}
		}
	}

	/**
	 * When a Player right-clicks the Block with a Climbing Plant seed, it tries to set the corresponding Climbing Plant on the Block.
	 * @param stateIn Current State of the Block.
	 * @param worldIn World of the Block.
	 * @param pos Position of the Block.
	 * @param player Player that right-clicked the Block.
	 * @param handIn Active hand.
	 * @return True if a Climbing Plant was successfully put on the Block.
	 */
	default boolean tryPlacingPlant(BlockState stateIn, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn){
		if(player.isSneaking()) return false;
		ItemStack heldItemStack = player.getHeldItem(handIn);
		if(this.canHavePlant(stateIn) && stateIn.get(CLIMBING_PLANT).hasNoPlant()){
			DoTBBlockStateProperties.ClimbingPlant plant = DoTBBlockStateProperties.ClimbingPlant.getFromItem(heldItemStack.getItem());
			if(!plant.hasNoPlant()){
				stateIn = stateIn.with(CLIMBING_PLANT, plant);
				if (!player.isCreative()) {
					heldItemStack.shrink(1);
				}
				worldIn.setBlockState(pos, stateIn, 10);
				return true;
			}
		}
		return false;
	}

	/**
	 * If the player is in Creative, he can control plant's age with right-click and shift-right-click
	 * Else, if the Climbing Plant is older than AGE 2 and has a loot_table, it drops its loots.
	 * Else, if the player is sneaking, the plant is removed and its loots dropped.
	 * @param stateIn Current State of the Block.
	 * @param worldIn World of the Block.
	 * @param pos Position of the Block.
	 * @param player Player that right-clicked the Block.
	 * @param handIn Active hand.
	 * @return True if the Climbing Plant was modified.
	 */
	default boolean harvestPlant(BlockState stateIn, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn){
		if(player.isCreative() && stateIn.get(PERSISTENT) && !stateIn.get(CLIMBING_PLANT).hasNoPlant()){
			if(player.isSneaking()){
				if(stateIn.get(AGE_0_6) > 0){
					worldIn.setBlockState(pos, stateIn.with(AGE_0_6, stateIn.get(AGE_0_6) - 1), 10);
				}else{
					worldIn.setBlockState(pos, stateIn.with(CLIMBING_PLANT, DoTBBlockStateProperties.ClimbingPlant.NONE).with(AGE_0_6, 0), 10);
				}
				return true;
			}else{
				if(stateIn.get(AGE_0_6) < stateIn.get(CLIMBING_PLANT).maxAge()){
					worldIn.setBlockState(pos, stateIn.with(AGE_0_6, stateIn.get(AGE_0_6) + 1), 10);
					return true;
				}
				return false;
			}
		}else{
			if(stateIn.get(AGE_0_6) > 2){
				if(this.dropPlant(stateIn, worldIn, pos, player.getHeldItem(handIn))){
					stateIn = stateIn.with(AGE_0_6, 2);
					worldIn.setBlockState(pos, stateIn, 10);
					worldIn.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
					return true;
				}
			}
			if(player.isSneaking()){
				return tryRemovingPlant(stateIn, worldIn, pos, player.getHeldItem(handIn));
			}
			return false;
		}
	}

	/**
	 * If the state in input contains a Climbing Plant, it removes it and drops its loots.
	 * @param stateIn Current State of the Block.
	 * @param worldIn World of the Block.
	 * @param pos Position of the Block.
	 * @param heldItemStack Item in active hand to apply tool conditions in the LootTable.
	 * @return True if a Climbing Plant was removed.
	 */
	default boolean tryRemovingPlant(BlockState stateIn, World worldIn, BlockPos pos, ItemStack heldItemStack){
		if(!stateIn.get(CLIMBING_PLANT).hasNoPlant()){
			stateIn = this.removePlant(stateIn, worldIn, pos, heldItemStack);
			worldIn.setBlockState(pos, stateIn, 10);
			return true;
		}
		return false;
	}

	/**
	 * Removes the Climbing Plant, drop its LootTable, and play breaking sound.
	 * @param stateIn Current State of the Block.
	 * @param worldIn World of the Block.
	 * @param pos Position of the Block.
	 * @param heldItemStack Item in active hand to apply tool conditions in the LootTable.
	 * @return The State in input with no Climbing Plant, and AGE back to 0.
	 */
	default BlockState removePlant(BlockState stateIn, World worldIn, BlockPos pos, ItemStack heldItemStack){
		this.dropPlant(stateIn, worldIn, pos, heldItemStack);
		worldIn.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
		stateIn = stateIn.with(CLIMBING_PLANT, DoTBBlockStateProperties.ClimbingPlant.NONE).with(AGE_0_6, 0);
		return stateIn;
	}

	/**
	 * Drops one by one each item contained in the LootTable of the Climbing Plant at the corresponding age.
	 * For example, if the Block contains a plant called "plant" at age "3", it will drop each Item in the LootTable named "plant_3" in Blocks folder.
	 * @param stateIn Current State of the Block.
	 * @param worldIn World of the Block.
	 * @param pos Position of the Block.
	 * @param heldItemStack Item in active hand to apply tool conditions.
	 * @return True if some loot is dropped. False if there were no loot_table found or item dropped.
	 */
	default boolean dropPlant(BlockState stateIn, World worldIn, BlockPos pos, ItemStack heldItemStack){
		if(worldIn.isRemote()) return false;
		DoTBBlockStateProperties.ClimbingPlant plant = stateIn.get(CLIMBING_PLANT);
		if(plant.hasNoPlant()) return false;
		List<ItemStack> drops = DoTBBlockUtils.getLootList((ServerWorld)worldIn, stateIn, pos, heldItemStack, plant.getName() + "_" + stateIn.get(AGE_0_6));
		return DoTBBlockUtils.dropLootFromList(worldIn, pos, drops, 1.0F);
	}

	/**
	 * @param state Current state of the Block.
	 * @return True if the state of the Block allows Climbing Plants.
	 */
	default boolean canHavePlant(BlockState state) {
		if(state.getBlock() instanceof IWaterLoggable){
			return !state.get(WATERLOGGED);
		}
		return true;
	}
}
