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
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.PlantType;
import org.dawnoftimebuilder.item.templates.SoilSeedsItem;
import org.dawnoftimebuilder.block.IBlockCustomItem;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.List;
import java.util.Random;

import static net.minecraft.block.Blocks.AIR;
import static net.minecraftforge.common.PlantType.*;
import static net.minecraftforge.common.Tags.Blocks.DIRT;
import static net.minecraftforge.common.Tags.Blocks.SAND;
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
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		BlockState stateGround = worldIn.getBlockState(pos);
		Block blockUnder = stateGround.getBlock();
		if (NETHER.equals(this.plantType)) return blockUnder == Blocks.SOUL_SAND;
		if (CAVE.equals(this.plantType)) return BlockDoTB.canSupportCenter( worldIn, pos, Direction.UP);
		if((worldIn.getRawBrightness(pos, 0) < 8 && !worldIn.canSeeSky(pos))) return false;
		if (DESERT.equals(this.plantType)) {
			return blockUnder.is(SAND) || blockUnder == Blocks.TERRACOTTA || blockUnder instanceof GlazedTerracottaBlock;
		} else if (CROP.equals(this.plantType)) {
			return blockUnder == Blocks.FARMLAND;
		} else if (PLAINS.equals(this.plantType)) {
			return blockUnder == Blocks.GRASS_BLOCK || blockUnder.is(DIRT) || blockUnder == Blocks.FARMLAND;
		} else if (WATER.equals(this.plantType)) {
			return worldIn.getFluidState(pos.above()).getFluidState().getType() == Fluids.WATER && worldIn.getBlockState(pos.above(2)).getBlock() == AIR && (blockUnder == Blocks.GRASS_BLOCK || blockUnder.is(DIRT) || blockUnder == Blocks.FARMLAND || blockUnder == Blocks.GRAVEL);
		} else if (BEACH.equals(this.plantType)) {
			boolean isBeach = blockUnder == Blocks.GRASS_BLOCK || blockUnder.is(DIRT) || blockUnder.is(SAND);
			boolean hasWater = (worldIn.getBlockState(pos.east()).getMaterial() == Material.WATER ||
					worldIn.getBlockState(pos.west()).getMaterial() == Material.WATER ||
					worldIn.getBlockState(pos.north()).getMaterial() == Material.WATER ||
					worldIn.getBlockState(pos.south()).getMaterial() == Material.WATER);
			return isBeach && hasWater;
		}
		return false;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		if(state.getValue(PERSISTENT)) return;
		super.tick(state, worldIn, pos, random);
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
	protected IItemProvider getBaseSeedId() {
		return this.seed;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		DoTBBlockUtils.addTooltip(tooltip, TOOLTIP_CROP);
	}
}
