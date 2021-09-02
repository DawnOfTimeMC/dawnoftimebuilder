package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.PlantType;
import org.dawnoftimebuilder.item.templates.SoilSeedsItem;
import org.dawnoftimebuilder.block.IBlockCustomItem;
import org.dawnoftimebuilder.util.DoTBFoods;

import javax.annotation.Nonnull;

import static net.minecraft.block.Blocks.AIR;
import static net.minecraftforge.common.Tags.Blocks.SAND;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;

public class SoilCropsBlock extends CropsBlock implements IBlockCustomItem {

	private final SoilSeedsItem seed;
	private final String seedName;
	private final PlantType plantType;

	public SoilCropsBlock(String seedName, PlantType plantType, Food food){
		super(BlockDoTB.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.CROP));
		this.plantType = plantType;
		this.seedName = seedName;
		this.seed = new SoilSeedsItem(this, food);
	}

	public SoilCropsBlock(String seedName, PlantType plantType){
		this(seedName, plantType, null);
	}

	@Override
	public boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
		BlockState stateGround = worldIn.getBlockState(pos);
		Block blockUnder = stateGround.getBlock();
		switch (this.plantType) {
			case Desert: return blockUnder.isIn(SAND) || blockUnder == Blocks.TERRACOTTA || blockUnder instanceof GlazedTerracottaBlock;
			case Nether: return blockUnder == Blocks.SOUL_SAND;
			case Crop:   return blockUnder == Blocks.FARMLAND;
			case Cave:   return BlockDoTB.hasSolidSide(stateGround, worldIn, pos, Direction.UP);
			case Plains: return blockUnder == Blocks.GRASS_BLOCK || BlockDoTB.isDirt(blockUnder) || blockUnder == Blocks.FARMLAND;
			case Water:
				return worldIn.getFluidState(pos.up()).getFluid() == Fluids.WATER && worldIn.getBlockState(pos.up(2)).getBlock() == AIR && (blockUnder == Blocks.GRASS_BLOCK || BlockDoTB.isDirt(blockUnder) || blockUnder == Blocks.FARMLAND || blockUnder == Blocks.GRAVEL);
			case Beach:
				boolean isBeach = blockUnder == Blocks.GRASS_BLOCK || BlockDoTB.isDirt(blockUnder) || blockUnder.isIn(SAND);
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

	@Nonnull
	@Override
	public String getCustomItemName() {
		return this.seedName;
	}

	@Override
	protected IItemProvider getSeedsItem() {
		return this.seed;
	}
}
