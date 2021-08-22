package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
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
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;
import org.dawnoftimebuilder.utils.DoTBBlockUtils;

import java.util.List;

import static org.dawnoftimebuilder.utils.DoTBBlockUtils.DoTBTags.SHEARS;

public class GrowingBushBlock extends SoilCropsBlock {

	public final VoxelShape[] SHAPES;
	public final int cutAge;
	private static final IntegerProperty AGE = BlockStateProperties.AGE_0_5;
	private static final BooleanProperty CUT = DoTBBlockStateProperties.CUT;

	public GrowingBushBlock(String seedName, PlantType plantType, int cutAge){
		super(seedName, plantType);
		this.cutAge = cutAge;
		this.setDefaultState(this.getDefaultState().with(AGE, 0).with(CUT, false));
		this.SHAPES = this.makeShapes();
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<net.minecraft.block.Block, BlockState> builder) {
		builder.add(AGE, CUT);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		int index = state.get(AGE);
		return state.get(CUT) ? SHAPES[5] : SHAPES[index];
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return (state.get(AGE) == 0) ? VoxelShapes.empty() : super.getShape(state, worldIn, pos, context);
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
				Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 5.5D, 11.0D),
				Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 9.0D, 12.0D),
				Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 10.5D, 13.0D),
				Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 11.0D, 14.0D),
				Block.makeCuboidShape(1.5D, 0.0D, 1.5D, 14.5D, 12.0D, 14.5D),
				Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 12.0D, 15.0D),
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
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockRayTraceResult ray) {
		if(this.isMaxAge(state)){
			if (!worldIn.isRemote()) {
				ItemStack itemStackHand = playerIn.getHeldItem(hand);
				boolean holdShears = SHEARS.contains(itemStackHand.getItem());
				if(holdShears) itemStackHand.damageItem(1, playerIn, (p_220287_1_) -> p_220287_1_.sendBreakAnimation(hand));

				ResourceLocation resourceLocation = this.getRegistryName();
				if(resourceLocation != null) {
					this.harvestWithoutBreaking(state, worldIn, pos, itemStackHand, resourceLocation.getPath(), holdShears ? 1.5F : 1.0F);
					return true;
				}else return false;
			}
		}
		return false;
	}

	public void harvestWithoutBreaking(BlockState state, World worldIn, BlockPos pos, ItemStack itemStackHand, String blockName, float dropMultiplier ){
		List<ItemStack> drops = DoTBBlockUtils.getLootList((ServerWorld)worldIn, state, pos, itemStackHand, blockName);
		DoTBBlockUtils.dropLootFromList(worldIn, pos, drops, dropMultiplier);

		worldIn.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
		worldIn.setBlockState(pos, state.with(AGE, this.cutAge).with(CUT, true));
	}
}
