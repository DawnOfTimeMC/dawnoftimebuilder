package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.PlantType;
import org.dawnoftimebuilder.item.templates.SoilSeedsItem;
import org.dawnoftimebuilder.block.IBlockCustomItem;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.List;
import java.util.Random;

import static net.minecraft.block.Blocks.AIR;
import static net.minecraftforge.common.Tags.Blocks.SAND;
import static org.dawnoftimebuilder.util.DoTBBlockUtils.TOOLTIP_CROP;

public class SoilCropsBlock extends CropsBlock implements IBlockCustomItem {

	private final SoilSeedsItem seed;
	private final String seedName;
	private final PlantType plantType;
	public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;

	public SoilCropsBlock(String seedName, PlantType plantType, Food food){
		super(BlockDoTB.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.CROP));
		this.plantType = plantType;
		this.seedName = seedName;
		this.seed = new SoilSeedsItem(this, food);
		this.setDefaultState(this.stateContainer.getBaseState().with(this.getAgeProperty(),0).with(PERSISTENT, false));
	}

	public SoilCropsBlock(String seedName, PlantType plantType){
		this(seedName, plantType, null);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(PERSISTENT);
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

	@Override
	public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
		if(state.get(PERSISTENT)) return;
		super.tick(state, worldIn, pos, random);
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(state.get(PERSISTENT)){
			if(player.isCreative()){
				int age = this.getAge(state);
				if(player.isSneaking()){
					if(age > 0){
						this.setPlantWithAge(state, worldIn, pos, age - 1);
						return true;
					}
				}else{
					if(age < this.getMaxAge()) {
						this.setPlantWithAge(state, worldIn, pos, age + 1);
						return true;
					}
				}
			}
		}else{
			if(DoTBBlockUtils.useLighter(worldIn, pos, player, handIn)){
				Random rand = new Random();
				for(int i = 0; i < 5; i++){
					worldIn.addOptionalParticle(ParticleTypes.SMOKE, (double)pos.getX() + rand.nextDouble(), (double)pos.getY() + 0.5D + rand.nextDouble() / 2, (double)pos.getZ() + rand.nextDouble(), 0.0D, 0.07D, 0.0D);
				}
				worldIn.setBlockState(pos, state.with(PERSISTENT, true), 10);
				return true;
			}
		}
		return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
	}

	public void setPlantWithAge(BlockState currentState, World worldIn, BlockPos pos, int newAge){
		worldIn.setBlockState(pos, currentState.with(this.getAgeProperty(), newAge), 10);
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

	@Override
	public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		DoTBBlockUtils.addTooltip(tooltip, TOOLTIP_CROP);
	}
}
