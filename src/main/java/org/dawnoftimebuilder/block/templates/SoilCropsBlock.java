package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.Tags;
import org.dawnoftimebuilder.block.ICustomBlockItem;
import org.dawnoftimebuilder.block.IBlockGeneration;
import org.dawnoftimebuilder.item.templates.SoilSeedsItem;
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static org.dawnoftimebuilder.util.DoTBUtils.TOOLTIP_CROP;

public class SoilCropsBlock extends CropsBlock implements ICustomBlockItem, IBlockGeneration {

	private final SoilSeedsItem seed;
	private final String seedName;
	private final PlantType plantType;
	public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;

	public SoilCropsBlock(String seedName, PlantType plantType, Food food){
		super(BlockDoTB.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP));
		this.plantType = plantType;
		this.seedName = seedName;
		this.seed = makeSeed(food);
		this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(),0).setValue(PERSISTENT, false));
	}

	public SoilCropsBlock(String seedName, PlantType plantType){
		this(seedName, plantType, null);
	}

	public SoilSeedsItem makeSeed(Food food){
		return new SoilSeedsItem(this, food);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(PERSISTENT);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
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
	public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
		return this.mayGenerateOn(world, pos.below(), this.getPlantType(world, pos)) && super.canSurvive(state, world, pos);
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, IBlockReader world, BlockPos pos) {
		return this.mayGenerateOn(world, pos, this.getPlantType(world, pos));
	}

	/**
	 * Checks the block at the given position and states if the plant can be generated on.
	 * @param worldIn of the block.
	 * @param pos to be checked (block under the plant).
	 * @param plantType of the plant, which is used to get the whitelisted blocks.
	 * @return true if the plant can be generated on this block, false otherwise.
	 */
	public boolean mayGenerateOn(IBlockReader worldIn, BlockPos pos, PlantType plantType){
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
					|| stateOn.is(Tags.Blocks.DIRT)
					|| stateOn.is(Blocks.FARMLAND);

		} else if (plantType.equals(PlantType.WATER)) {
			return worldIn.getFluidState(pos.above()).getFluidState().getType() == Fluids.WATER
					&& (stateOn.is(Blocks.CLAY)
					|| stateOn.is(Tags.Blocks.DIRT)
					|| stateOn.is(Blocks.FARMLAND)
					|| stateOn.is(Blocks.GRAVEL));

		} else if (plantType.equals(PlantType.BEACH)) {
			boolean isBeach = stateOn.is(Blocks.GRASS_BLOCK)
					|| stateOn.is(Tags.Blocks.DIRT)
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
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(state.getValue(PERSISTENT)){
			if(player.isCreative()){
				int age = this.getAge(state);
				if(player.isCrouching()){
					if(age > 0){
						this.setPlantWithAge(state, worldIn, pos, age - 1);
						return ActionResultType.SUCCESS;
					}
				}else{
					if(age < this.getMaxAge()) {
						this.setPlantWithAge(state, worldIn, pos, age + 1);
						return ActionResultType.SUCCESS;
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
				return ActionResultType.SUCCESS;
			}
		}
		return super.use(state, worldIn, pos, player, handIn, hit);
	}

	public void setPlantWithAge(BlockState currentState, IWorld worldIn, BlockPos pos, int newAge){
		worldIn.setBlock(pos, currentState.setValue(this.getAgeProperty(), newAge), 10);
	}

	@Override
	public PlantType getPlantType(IBlockReader world, BlockPos pos) {
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

	@Override
	protected IItemProvider getBaseSeedId() {
		return this.seed;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		DoTBUtils.addTooltip(tooltip, TOOLTIP_CROP);
	}

	@Override
	public void generateOnPos(IWorld world, BlockPos pos, BlockState state, Random random) {
		this.setPlantWithAge(state, world, pos, random.nextInt(this.getMaxAge() + 1));
	}
}
