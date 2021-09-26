package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.PlantType;

import javax.annotation.Nullable;
import java.util.Random;

public class DoubleGrowingBushBlock extends GrowingBushBlock {

	public final int growingAge;
	public final VoxelShape[] TOP_SHAPES;
	public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

	public DoubleGrowingBushBlock(String seedName, PlantType plantType, int cutAge, int growingAge) {
		this(seedName, plantType, growingAge, cutAge, null);
	}

	public DoubleGrowingBushBlock(String seedName, PlantType plantType, int cutAge, int growingAge, Food food) {
		super(seedName, plantType, cutAge, food);
		this.growingAge = growingAge;
		this.TOP_SHAPES = this.makeTopShapes();
		this.setDefaultState(this.defaultBlockState().with(HALF, Half.BOTTOM). with(this.getAgeProperty(), 0).with(this.getCutProperty(), false).with(PERSISTENT, false));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if(state.get(HALF) == Half.BOTTOM){
			return SHAPES[Math.min(this.getAge(state), SHAPES.length - 1)];
		}else return TOP_SHAPES[Math.min(state.get(this.getCutProperty()) ? this.getMaxAge() : this.getAge(state), TOP_SHAPES.length - 1)];
	}

	/**
	 * @return Stores default VoxelShape (growingAge 2) with index : <p/>
	 * 0 : stage 0 bottom 6px <p/>
	 * 1 : stage 1 bottom 16px <p/>
	 * 2 : stage 2 bottom 16px <p/>
	 * 3 : stage 3+ bottom 16px <p/>
	 */
	@Override
	public VoxelShape[] makeShapes() {
		return new VoxelShape[]{
				Block.Block.box(5.5D, 0.0D, 5.5D, 10.5D, 6.0D, 10.5D),
				Block.Block.box(4.5D, 0.0D, 4.5D, 11.5D, 16.0D, 11.5D),
				VoxelShapes.or(
						Block.Block.box(6.0D, 0.0D, 6.0D, 10.0D, 12.0D, 10.0D),
						Block.Block.box(4.0D, 12.0D, 4.0D, 12.0D, 16.0D, 12.0D)),
				Block.Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D),
		};
	}

	/**
	 * @return Stores default VoxelShape for Top half with index : <p/>
	 * 0 : stage 0 top null <p/>
	 * 1 : stage 1 top null <p/>
	 * 2 : stage 2 top 5px <p/>
	 * 3 : stage 3 top 10px <p/>
	 * 4 : stage 4+ OR Cut top 15px <p/>
	 */
	public VoxelShape[] makeTopShapes() {
		return new VoxelShape[]{
				null,
				null,
				Block.Block.box(4.0D, 0.0D, 4.0D, 12.0D, 5.0D, 12.0D),
				Block.Block.box(1.0D, 0.0D, 1.0D, 15.0D, 10.0D, 15.0D),
				Block.Block.box(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D),
		};
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(HALF);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		Half half = stateIn.get(HALF);
		if (facing.getAxis() == Direction.Axis.Y && half == Half.BOTTOM == (facing == Direction.UP)) {
			if(facingState.getBlock() == this && facingState.get(HALF) != half){
				stateIn = stateIn.with(PERSISTENT, facingState.get(PERSISTENT));
				return half == Half.BOTTOM ? stateIn : stateIn.with(this.getAgeProperty(), this.getAge(facingState));
			}else if(half == Half.BOTTOM && this.getAge(stateIn) < this.growingAge) return stateIn;
			return Blocks.AIR.defaultBlockState();
		}else{
			return half == Half.BOTTOM && facing == Direction.DOWN && this.isValidGround(stateIn, worldIn, currentPos.down()) ? Blocks.AIR.defaultBlockState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		}
	}

	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
		super.harvestBlock(worldIn, player, pos, Blocks.AIR.defaultBlockState(), te, stack);
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		Half half = state.get(HALF);
		BlockPos blockpos = (half == Half.BOTTOM) ? pos.above() : pos.down();
		BlockState blockstate = worldIn.getBlockState(blockpos);
		if (blockstate.getBlock() == this) {
			worldIn.setBlockState(blockpos, Blocks.AIR.defaultBlockState(), 35);
			worldIn.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
			ItemStack itemstack = player.getItemInHandMainhand();
			if (!worldIn.isClientSide && !player.isCreative()) {
				Block.spawnDrops(state, worldIn, pos, null, player, itemstack);
				Block.spawnDrops(blockstate, worldIn, blockpos, null, player, itemstack);
			}
		}

		super.onBlockHarvested(worldIn, pos, state, player);
	}

	@Override
	public void harvestWithoutBreaking(BlockState state, World worldIn, BlockPos pos, ItemStack itemStackHand, String blockName, float dropMultiplier) {
		boolean isTop = state.get(HALF) == Half.TOP;
		worldIn.setBlockState(isTop ? pos.down() : pos.above(), state.with(this.getAgeProperty(), this.cutAge).with(this.getCutProperty(), true).with(HALF, isTop ? Half.BOTTOM : Half.TOP));
		super.harvestWithoutBreaking(state, worldIn, pos, itemStackHand, blockName, dropMultiplier);
	}

	// Only called with Bonemeal
	@Override
    public void grow(World worldIn, BlockPos pos, BlockState state) {
		if(this.isBottomCrop(state)){
	        int newAge = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
	        if (newAge > this.getMaxAge()) newAge = this.getMaxAge();
			if(newAge >= this.getAgeReachingTopBlock()){
				BlockPos topPos = pos.above();
				if(worldIn.getBlockState(topPos).getBlock() == this || worldIn.isAirBlock(topPos)){
					state = this.withAge(newAge);
					worldIn.setBlockState(pos, state, 2);
					worldIn.setBlockState(topPos, this.getTopState(state), 2);
				}
			}
		}
    }

	@Override
	public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
		if(this.isBottomCrop(state)) {
			if (!worldIn.isAreaLoaded(pos, 1) || state.get(PERSISTENT))
				return; // Forge: prevent loading unloaded chunks when checking neighbor's light
			if (worldIn.getRawBrightness(pos, 0) >= 9) {
				int i = this.getAge(state);
				if (i < this.getMaxAge()) {
					float f = getGrowthChance(this, worldIn, pos);
					BlockPos topPos = pos.above();
					if (worldIn.getBlockState(topPos).getBlock() == this || worldIn.isAirBlock(topPos)) {
						if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0)) {
							state = this.withAge(i + 1);
							worldIn.setBlockState(pos, state, 2);
							if (i + 1 >= this.growingAge)
								worldIn.setBlockState(topPos, this.getTopState(state), 2);
							ForgeHooks.onCropsGrowPost(worldIn, pos, state);
						}
					}

				}
			}
		}
	}

	public boolean isBottomCrop(BlockState state){
		if(state.getBlock() instanceof DoubleGrowingBushBlock){
			return state.get(HALF) == Half.BOTTOM;
		}
		return false;
	}

	/**
	 * @param bottomState State of the bottom part of the double crop.
	 * @return State of the Top part of the crop in input.
	 */
	public BlockState getTopState(BlockState bottomState){
		return bottomState.with(HALF, Half.TOP);
	}

	@Override
	public PushReaction getPushReaction(BlockState state) {
		return PushReaction.DESTROY;
	}

	public int getAgeReachingTopBlock(){
		return this.growingAge;
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		if(state.get(HALF) == Half.TOP){
			BlockState stateUnder = worldIn.getBlockState(pos.down());
			if(stateUnder.getBlock() == this) return stateUnder.get(HALF) == Half.BOTTOM;
		}
		return super.isValidPosition(state, worldIn, pos);
	}

	@Override
	public void setPlantWithAge(BlockState currentState, World worldIn, BlockPos pos, int newAge) {
		if(currentState.get(HALF) == Half.TOP) pos = pos.down();
		if(newAge >= this.getAgeReachingTopBlock()){
			BlockPos posUp = pos.above();
			if(worldIn.getBlockState(posUp).getBlock() == this || worldIn.isAirBlock(posUp)){
				worldIn.setBlockState(posUp, currentState.with(this.getAgeProperty(), newAge).with(HALF, Half.TOP), 10);
			}
		}
		if(newAge < this.getAgeReachingTopBlock() && this.getAge(currentState) == this.getAgeReachingTopBlock()){
			worldIn.setBlockState(pos.above(), Blocks.AIR.defaultBlockState(), 10);
		}
		worldIn.setBlockState(pos, currentState.with(this.getAgeProperty(), newAge).with(HALF, Half.BOTTOM), 8);
	}
}
