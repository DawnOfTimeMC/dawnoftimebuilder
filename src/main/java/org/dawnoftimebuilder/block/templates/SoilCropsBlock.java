package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.PlantType;
import org.dawnoftimebuilder.block.IBlockGeneration;
import org.dawnoftimebuilder.block.ICustomBlockItem;
import org.dawnoftimebuilder.item.templates.SoilSeedsItem;
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static org.dawnoftimebuilder.util.DoTBUtils.TOOLTIP_CROP;

public class SoilCropsBlock extends CropBlock implements ICustomBlockItem, IBlockGeneration {

	private final SoilSeedsItem seed;
	private final String seedName;
	private final PlantType plantType;
	public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;

	public SoilCropsBlock(String seedName, PlantType plantType, FoodProperties food){
		super(BlockDoTB.Properties.copy(Blocks.SUNFLOWER).randomTicks().sound(SoundType.CROP));
		this.plantType = plantType;
		this.seedName = seedName;
		this.seed = makeSeed(food);
		this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(),0).setValue(PERSISTENT, false));
	}

	public SoilCropsBlock(String seedName, PlantType plantType){
		this(seedName, plantType, null);
	}

	public SoilSeedsItem makeSeed(FoodProperties food){
		return new SoilSeedsItem(this, food);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(PERSISTENT);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
		if(state.getValue(PERSISTENT)) return;
		if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
		if (worldIn.getRawBrightness(pos, 0) >= 9) {
			int age = this.getAge(state);
			if (age < this.getMaxAge()) {
				float f = getGrowthSpeed(this, worldIn, pos);
				if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int)(25.0F / f) + 1) == 0)) {
					this.setPlantWithAge(state, worldIn, pos, age + 1);
					ForgeHooks.onCropsGrowPost(worldIn, pos, state);
				}
			}
		}
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return this.mayGenerateOn(world, pos.below(), this.getPlantType(world, pos)) && super.canSurvive(state, world, pos);
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter world, BlockPos pos) {
		return this.mayGenerateOn(world, pos, this.getPlantType(world, pos));
	}

	/**
	 * Checks the block at the given position and states if the plant can be generated on.
	 * @param worldIn of the block.
	 * @param pos to be checked (block under the plant).
	 * @param plantType of the plant, which is used to get the whitelisted blocks.
	 * @return true if the plant can be generated on this block, false otherwise.
	 */
	public boolean mayGenerateOn(BlockGetter worldIn, BlockPos pos, PlantType plantType){
		BlockState stateOn = worldIn.getBlockState(pos);
		Block blockOn = stateOn.getBlock();

		if(plantType.equals(PlantType.DESERT)){
			return stateOn.is(Blocks.SAND)
					|| stateOn.is(Blocks.TERRACOTTA)
					|| blockOn instanceof GlazedTerracottaBlock;

		} else if (plantType.equals(PlantType.NETHER)) {
			return stateOn.is(Blocks.SOUL_SAND);

		} else if (plantType.equals(PlantType.CROP)) {
			return stateOn.is(Blocks.FARMLAND);

		} else if (plantType.equals(PlantType.CAVE)) {
			return stateOn.isFaceSturdy(worldIn, pos, Direction.UP);

		} else if (plantType.equals(PlantType.PLAINS)) {
			return stateOn.is(Blocks.GRASS_BLOCK)
					|| stateOn.is(Blocks.DIRT)
					|| stateOn.is(Blocks.FARMLAND);

		} else if (plantType.equals(PlantType.WATER)) {
			return worldIn.getFluidState(pos.above()).getType() == Fluids.WATER
					&& (stateOn.is(Blocks.CLAY)
					|| stateOn.is(Blocks.DIRT)
					|| stateOn.is(Blocks.FARMLAND)
					|| stateOn.is(Blocks.GRAVEL));

		} else if (plantType.equals(PlantType.BEACH)) {
			boolean isBeach = stateOn.is(Blocks.GRASS_BLOCK)
					|| stateOn.is(Blocks.DIRT)
					|| stateOn.is(Blocks.SAND)
					|| stateOn.is(Blocks.RED_SAND);
			if(isBeach){
				boolean hasWater = false;
				for (Direction face : Direction.Plane.HORIZONTAL) {
					BlockState blockState = worldIn.getBlockState(pos.relative(face));
					FluidState fluidState = worldIn.getFluidState(pos.relative(face));
					hasWater = blockState.is(Blocks.FROSTED_ICE);
					hasWater |= fluidState.is(FluidTags.WATER);
					if (hasWater)
						break; //No point continuing.
				}
				return hasWater;
			}
		}
		return false;
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if(state.getValue(PERSISTENT)){
			if(player.isCreative()){
				int age = this.getAge(state);
				if(player.isCrouching()){
					if(age > 0){
						this.setPlantWithAge(state, worldIn, pos, age - 1);
						return InteractionResult.SUCCESS;
					}
				}else{
					if(age < this.getMaxAge()) {
						this.setPlantWithAge(state, worldIn, pos, age + 1);
						return InteractionResult.SUCCESS;
					}
				}
			}
		}else{
			if(DoTBUtils.useLighter(worldIn, pos, player, handIn)){
				Random rand = new Random();
				for(int i = 0; i < 5; i++){
					worldIn.addAlwaysVisibleParticle(ParticleTypes.SMOKE, (double)pos.getX() + rand.nextDouble(), (double)pos.getY() + 0.5D + rand.nextDouble() / 2, (double)pos.getZ() + rand.nextDouble(), 0.0D, 0.07D, 0.0D);
				}
				worldIn.setBlock(pos, state.setValue(PERSISTENT, true), 10);
				return InteractionResult.SUCCESS;
			}
		}
		return super.use(state, worldIn, pos, player, handIn, hit);
	}

	public void setPlantWithAge(BlockState currentState, LevelAccessor worldIn, BlockPos pos, int newAge){
		worldIn.setBlock(pos, currentState.setValue(this.getAgeProperty(), newAge), 10);
	}

	@Override
	public PlantType getPlantType(BlockGetter world, BlockPos pos) {
		return this.plantType;
	}

	@Override
	public Item getCustomBlockItem() {
		return this.seed;
	}

	@Nonnull
	@Override
	public String getCustomItemName() {
		return this.seedName;
	}

	/*@Override
	protected IItemProvider getBaseSeedId() {
		return this.seed;
	}*/

	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		DoTBUtils.addTooltip(tooltip, TOOLTIP_CROP);
	}

	@Override
	public void generateOnPos(LevelAccessor world, BlockPos pos, BlockState state, RandomSource random) {
		this.setPlantWithAge(state, world, pos, random.nextInt(this.getMaxAge() + 1));
	}
}
