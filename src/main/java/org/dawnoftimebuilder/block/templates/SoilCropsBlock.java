package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
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
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.PlantType;
import org.dawnoftimebuilder.block.IBlockCustomItem;
import org.dawnoftimebuilder.item.templates.SoilSeedsItem;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static org.dawnoftimebuilder.util.DoTBBlockUtils.TOOLTIP_CROP;

public class SoilCropsBlock extends CropsBlock implements IBlockCustomItem {

	private final SoilSeedsItem seed;
	private final String seedName;
	private final PlantType plantType;
	public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;

	public SoilCropsBlock(String seedName, PlantType plantType, Food food){
		super(BlockDoTB.Properties.of(Material.VEGETABLE).noCollission().randomTicks().strength(0.0F).sound(SoundType.CROP));
		this.plantType = plantType;
		this.seedName = seedName;
		this.seed = new SoilSeedsItem(this, food);
		this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(),0).setValue(PERSISTENT, false));
	}

	public SoilCropsBlock(String seedName, PlantType plantType){
		this(seedName, plantType, null);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(PERSISTENT);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		if(state.getValue(PERSISTENT)) return;
		super.tick(state, worldIn, pos, random);
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, IBlockReader world, BlockPos pos) {
		if(this.getPlantType(world, pos).equals(PlantType.DESERT)){
			return this.getBlock() == Blocks.SAND || this.getBlock() == Blocks.TERRACOTTA || this.getBlock() instanceof GlazedTerracottaBlock;
		} else if (this.getPlantType(world, pos).equals(PlantType.NETHER)) {
			return this.getBlock() == Blocks.SOUL_SAND;
		} else if (this.getPlantType(world, pos).equals(PlantType.CROP)) {
			return state.is(Blocks.FARMLAND);
		} else if (this.getPlantType(world, pos).equals(PlantType.CAVE)) {
			return state.isFaceSturdy(world, pos, Direction.UP);
		} else if (this.getPlantType(world, pos).equals(PlantType.PLAINS)) {
			return this.getBlock() == Blocks.GRASS_BLOCK || net.minecraftforge.common.Tags.Blocks.DIRT.contains(this) || this.getBlock() == Blocks.FARMLAND;
		} else if (this.getPlantType(world, pos).equals(PlantType.WATER)) {
			return state.getMaterial() == net.minecraft.block.material.Material.WATER;
		} else if (this.getPlantType(world, pos).equals(PlantType.BEACH)) {
			boolean isBeach = state.is(Blocks.GRASS_BLOCK) || net.minecraftforge.common.Tags.Blocks.DIRT.contains(this) || state.is(Blocks.SAND) || state.is(Blocks.RED_SAND);
			boolean hasWater = false;
			for (Direction face : Direction.Plane.HORIZONTAL) {
				BlockState blockState = world.getBlockState(pos.relative(face));
				FluidState fluidState = world.getFluidState(pos.relative(face));
				hasWater = blockState.is(Blocks.FROSTED_ICE);
				hasWater |= fluidState.is(FluidTags.WATER);
				if (hasWater)
					break; //No point continuing.
			}
			return isBeach && hasWater;
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
			if(DoTBBlockUtils.useLighter(worldIn, pos, player, handIn)){
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

	public void setPlantWithAge(BlockState currentState, World worldIn, BlockPos pos, int newAge){
		worldIn.setBlock(pos, currentState.setValue(this.getAgeProperty(), newAge), 10);
	}

	@Override
	public PlantType getPlantType(IBlockReader world, BlockPos pos) {
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
	protected IItemProvider getBaseSeedId() {
		return this.seed;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		DoTBBlockUtils.addTooltip(tooltip, TOOLTIP_CROP);
	}
}
