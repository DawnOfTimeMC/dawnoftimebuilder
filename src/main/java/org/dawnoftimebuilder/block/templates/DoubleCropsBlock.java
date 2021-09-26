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
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.PlantType;

import javax.annotation.Nullable;
import java.util.Random;

public class DoubleCropsBlock extends SoilCropsBlock {

	private final int growingAge;
	public final VoxelShape[] SHAPES;
	public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

	public DoubleCropsBlock(String seedName, PlantType plantType, int growingAge) {
		this(seedName, plantType, growingAge, null);
	}

	public DoubleCropsBlock(String seedName, PlantType plantType, int growingAge, Food food) {
		super(seedName, plantType, food);
		this.growingAge = growingAge;
		this.SHAPES = this.makeShapes();
		this.setDefaultState(this.defaultBlockState().with(HALF, Half.BOTTOM).with(this.getAgeProperty(), 0).with(PERSISTENT, false));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		int index = state.get(AGE);
		if(index >= this.getAgeReachingTopBlock()) index = (state.get(HALF) == Half.BOTTOM) ? this.getAgeReachingTopBlock() : index + 1;
		return SHAPES[index];
	}

	/**
	 * @return Stores VoxelShape with index : <p/>
	 * 0 : stage 0 bottom 4px <p/>
	 * 1 : stage 1 bottom 8px <p/>
	 * 2 : stage 2 bottom 12px <p/>
	 * 3 : stage 3 bottom 16px <p/>
	 * 4 : stage 4-5-6-7 bottom 16px <p/>
	 * 5 : stage 4 top 4px <p/>
	 * 6 : stage 5 top 8px <p/>
	 * 7 : stage 6 top 12px <p/>
	 * 8 : stage 7 top 16px <p/>
	 */
	public VoxelShape[] makeShapes() {
		return new VoxelShape[]{
				Block.Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
				Block.Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
				Block.Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
				Block.Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
				Block.Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
				Block.Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
				Block.Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
				Block.Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
				Block.Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
		};
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(HALF);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if(facing.getAxis() == Direction.Axis.Y){
			boolean isBottom = stateIn.get(HALF) == Half.BOTTOM;
			if(isBottom == (facing == Direction.UP)) {
				if(facingState.getBlock() == this && facingState.get(HALF) != stateIn.get(HALF)){
					stateIn = stateIn.with(PERSISTENT, facingState.get(PERSISTENT));
					return isBottom ? stateIn : stateIn.with(AGE, facingState.get(AGE));
				}else if(isBottom && stateIn.get(AGE) < this.getAgeReachingTopBlock()) return stateIn;
				return Blocks.AIR.defaultBlockState();
			}else{
				return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
			}
		}
		return stateIn;
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
							if (i + 1 >= this.getAgeReachingTopBlock())
								worldIn.setBlockState(topPos, this.getTopState(state), 2);
							ForgeHooks.onCropsGrowPost(worldIn, pos, state);
						}
					}

				}
			}
		}
	}

	public boolean isBottomCrop(BlockState state){
		if(state.getBlock() instanceof DoubleCropsBlock){
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
}
