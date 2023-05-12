package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.PlantType;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBUtils;

import java.util.List;

import static net.minecraftforge.common.Tags.Items.SHEARS;

public class GrowingBushBlock extends SoilCropsBlock {

	public final VoxelShape[] SHAPES;
	public final int cutAge;
	private static final IntegerProperty AGE = BlockStateProperties.AGE_5;
	private static final BooleanProperty CUT = DoTBBlockStateProperties.CUT;

	public GrowingBushBlock(String seedName, PlantType plantType, int cutAge) {
		this(seedName, plantType, cutAge, null);
	}

	public GrowingBushBlock(String seedName, PlantType plantType, int cutAge, Food food){
		super(seedName, plantType, food);
		this.cutAge = cutAge;
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(CUT, false).setValue(PERSISTENT, false));
		this.SHAPES = this.makeShapes();
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<net.minecraft.block.Block, BlockState> builder) {
		builder.add(AGE, PERSISTENT, CUT);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		int index = state.getValue(AGE);
		return state.getValue(CUT) ? SHAPES[5] : SHAPES[index];
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return (state.getValue(AGE) == 0) ? VoxelShapes.empty() : super.getShape(state, worldIn, pos, context);
	}

	/**
	 * @return Stores VoxelShape with index : <p/>
	 * 0 : Stage 0 <p/>
	 * 1 : Stage 1 <p/>
	 * 2 : Stage 2 <p/>
	 * 3 : Stage 3 <p/>
	 * 4 : Stage 4 <p/>
	 * 5 : Stage 5 or cut <p/>
	 */
	public VoxelShape[] makeShapes() {
		return new VoxelShape[]{
				Block.box(5.0D, 0.0D, 5.0D, 11.0D, 5.5D, 11.0D),
				Block.box(4.0D, 0.0D, 4.0D, 12.0D, 9.0D, 12.0D),
				Block.box(3.0D, 0.0D, 3.0D, 13.0D, 10.5D, 13.0D),
				Block.box(2.0D, 0.0D, 2.0D, 14.0D, 11.0D, 14.0D),
				Block.box(1.5D, 0.0D, 1.5D, 14.5D, 12.0D, 14.5D),
				Block.box(1.0D, 0.0D, 1.0D, 15.0D, 12.0D, 15.0D),
		};
	}

	@Override
	public IntegerProperty getAgeProperty() {
		return AGE;
	}

	public BooleanProperty getCutProperty(){
		return CUT;
	}

	@Override
	public int getMaxAge() {
		return 5;
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockRayTraceResult ray) {
		if(super.use(state, worldIn, pos, playerIn, hand, ray) == ActionResultType.SUCCESS) return ActionResultType.SUCCESS;
		if(this.isMaxAge(state) && !playerIn.isCreative()){
			if (!worldIn.isClientSide()) {
				ItemStack itemStackHand = playerIn.getItemInHand(hand);
				boolean holdShears = itemStackHand.getItem().is(SHEARS);
				if(holdShears) itemStackHand.hurtAndBreak(1, playerIn, (p) -> p.broadcastBreakEvent(hand));

				ResourceLocation resourceLocation = this.getRegistryName();
				if(resourceLocation != null) {
					this.harvestWithoutBreaking(state, worldIn, pos, itemStackHand, resourceLocation.getPath(), holdShears ? 1.5F : 1.0F);
					return ActionResultType.SUCCESS;
				}
			}
		}
		return ActionResultType.PASS;
	}

	public void harvestWithoutBreaking(BlockState state, World worldIn, BlockPos pos, ItemStack itemStackHand, String blockName, float dropMultiplier){
		List<ItemStack> drops = DoTBUtils.getLootList((ServerWorld)worldIn, state, itemStackHand, blockName);
		DoTBUtils.dropLootFromList(worldIn, pos, drops, dropMultiplier);

		worldIn.playSound(null, pos, SoundEvents.GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
		worldIn.setBlock(pos, state.setValue(AGE, this.cutAge).setValue(CUT, true), 2);
	}
}
