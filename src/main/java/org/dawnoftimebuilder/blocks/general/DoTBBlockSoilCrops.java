package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraftforge.common.PlantType;
import org.dawnoftimebuilder.items.general.DoTBItemSoilSeeds;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;

import java.util.List;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBBlockSoilCrops extends CropsBlock implements IBlockCustomItem {

	private DoTBItemSoilSeeds seed;
	private PlantType plantType;

	public DoTBBlockSoilCrops(String name, String seedName, PlantType plantType){
		super(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.CROP));
		this.plantType = plantType;
		this.setRegistryName(MOD_ID, name);
		this.seed = new DoTBItemSoilSeeds(this, seedName);
	}

	public DoTBBlockSoilCrops(String name, PlantType plantType){
		this(name, name, plantType);
	}

	@Override
	public boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
		BlockState stateGround = worldIn.getBlockState(pos);
		Block blockUnder = stateGround.getBlock();
		switch (this.plantType) {
			case Desert: return blockUnder == Blocks.SAND || blockUnder == Blocks.TERRACOTTA || blockUnder instanceof GlazedTerracottaBlock;
			case Nether: return blockUnder == Blocks.SOUL_SAND;
			case Crop:   return blockUnder == Blocks.FARMLAND;
			case Cave:   return Block.hasSolidSide(stateGround, worldIn, pos, Direction.UP);
			case Plains: return blockUnder == Blocks.GRASS_BLOCK || Block.isDirt(blockUnder) || blockUnder == Blocks.FARMLAND;
			case Water:
				stateGround = worldIn.getBlockState(pos.up());
				return stateGround.getMaterial() == Material.WATER && (blockUnder == Blocks.GRASS_BLOCK || Block.isDirt(blockUnder) || blockUnder == Blocks.FARMLAND || blockUnder == Blocks.GRAVEL);
			case Beach:
				boolean isBeach = blockUnder == Blocks.GRASS_BLOCK || Block.isDirt(blockUnder) || blockUnder == Blocks.SAND;
				boolean hasWater = (worldIn.getBlockState(pos.east()).getMaterial() == Material.WATER ||
						worldIn.getBlockState(pos.west()).getMaterial() == Material.WATER ||
						worldIn.getBlockState(pos.north()).getMaterial() == Material.WATER ||
						worldIn.getBlockState(pos.south()).getMaterial() == Material.WATER);
				return isBeach && hasWater;
		}
		return false;
	}

	public PlantType getPlantType(){
		return this.plantType;
	}

	@Override
	public Item getCustomItemBlock() {
		return this.seed;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		return super.getDrops(state, builder);
	}
}
