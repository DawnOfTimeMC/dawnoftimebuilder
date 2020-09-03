package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.PlantType;
import org.dawnoftimebuilder.items.templates.SoilSeedsItem;
import org.dawnoftimebuilder.block.IBlockCustomItem;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class SoilCropsBlock extends CropsBlock implements IBlockCustomItem {

	private final SoilSeedsItem seed;
	private final PlantType plantType;

	public SoilCropsBlock(String name, String seedName, PlantType plantType){
		super(BlockDoTB.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.CROP));
		this.plantType = plantType;
		this.setRegistryName(MOD_ID, name);
		this.seed = new SoilSeedsItem(this, seedName);
	}

	public SoilCropsBlock(String name, PlantType plantType){
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
			case Cave:   return BlockDoTB.hasSolidSide(stateGround, worldIn, pos, Direction.UP);
			case Plains: return blockUnder == Blocks.GRASS_BLOCK || BlockDoTB.isDirt(blockUnder) || blockUnder == Blocks.FARMLAND;
			case Water:
				return worldIn.getFluidState(pos.up()).getFluid() == Fluids.WATER && (blockUnder == Blocks.GRASS_BLOCK || BlockDoTB.isDirt(blockUnder) || blockUnder == Blocks.FARMLAND || blockUnder == Blocks.GRAVEL);
			case Beach:
				boolean isBeach = blockUnder == Blocks.GRASS_BLOCK || BlockDoTB.isDirt(blockUnder) || blockUnder == Blocks.SAND;
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
}
