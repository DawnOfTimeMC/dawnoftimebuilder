package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraftforge.common.PlantType;

import javax.annotation.Nullable;

public class DoubleCropsBlock extends SoilCropsBlock {

	public int growingAge;
	public VoxelShape[] SHAPES;
	public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
	
	public DoubleCropsBlock(String name, String seedName, PlantType plantType, int growingAge) {
		super(name, seedName, plantType);
		this.growingAge = growingAge;
		this.SHAPES = this.makeShapes();
		this.setDefaultState(this.getDefaultState().with(HALF, Half.BOTTOM));
	}

	public DoubleCropsBlock(String name, PlantType plantType, int growingAge){
		this(name, name, plantType, growingAge);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		int index = state.get(AGE);
		if(index >= this.growingAge) index = (state.get(HALF) == Half.BOTTOM) ? this.growingAge : index + 1;
		return SHAPES[index];
	}

	/**
	 * @return Stores VoxelShape with index : <p/>
	 * 0 : stage 0 bottom 4px <p/>
	 * 1 : stage 1 bottom 8px <p/>
	 * 2 : stage 2 bottom 12px <p/>
	 * 3 : stage 3 bottom 16px <p/>
	 * 4 : stage 4-5-6-7 bottom 16px <p/>
	 * 4 : stage 4 top 4px <p/>
	 * 4 : stage 5 top 8px <p/>
	 * 4 : stage 6 top 12px <p/>
	 * 4 : stage 7 top 16px <p/>
	 */
	public VoxelShape[] makeShapes() {
		return new VoxelShape[]{
				Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
				Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
				Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
				Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
				Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
				Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
				Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
				Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
				Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
		};
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HALF, AGE);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		Half half = stateIn.get(HALF);
		if (facing.getAxis() == Direction.Axis.Y && half == Half.BOTTOM == (facing == Direction.UP)) {
			if(facingState.getBlock() == this && facingState.get(HALF) != half){
				return half == Half.BOTTOM ? stateIn : stateIn.with(AGE, facingState.get(AGE));
			}else if(half == Half.BOTTOM && stateIn.get(AGE) < this.growingAge) return stateIn;
			return Blocks.AIR.getDefaultState();
		}else{
			return half == Half.BOTTOM && facing == Direction.DOWN && this.isValidGround(stateIn, worldIn, currentPos.down()) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		}
	}

	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
		super.harvestBlock(worldIn, player, pos, Blocks.AIR.getDefaultState(), te, stack);
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		Half half = state.get(HALF);
		BlockPos blockpos = (half == Half.BOTTOM) ? pos.up() : pos.down();
		BlockState blockstate = worldIn.getBlockState(blockpos);
		if (blockstate.getBlock() == this) {
			worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
			worldIn.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
			ItemStack itemstack = player.getHeldItemMainhand();
			if (!worldIn.isRemote && !player.isCreative()) {
				Block.spawnDrops(state, worldIn, pos, null, player, itemstack);
				Block.spawnDrops(blockstate, worldIn, blockpos, null, player, itemstack);
			}
		}

		super.onBlockHarvested(worldIn, pos, state, player);
	}
	
	@Override
    public void grow(World worldIn, BlockPos pos, BlockState state) {
		if(this.isBottomCrop(worldIn, pos)){
	        int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
	        int j = this.getMaxAge();
	        if (i > j) i = j;
	        this.tryGrow(worldIn, pos, state, i);
		}else{
			BlockPos bottomCrop = this.getBottomCrop(worldIn, pos);
			this.grow(worldIn, bottomCrop, worldIn.getBlockState(bottomCrop));
		}
    }

	private void tryGrow(World worldIn, BlockPos pos, BlockState state, int i){
		if(this.isBottomCrop(worldIn, pos)){
			boolean canGrow = true;
	    	if(i >= this.getAgeReachingTopBlock()){
	        	canGrow = false;
	        	BlockPos topPos = pos.up();
	        	BlockState topCrop = worldIn.getBlockState(topPos);
	        	
	        	if(topCrop.getBlock() == this){
	        		worldIn.setBlockState(topPos, this.setAge(topCrop.with(HALF, Half.TOP), i), 2);
	        		canGrow = true;
	        	}else if(worldIn.isAirBlock(topPos)){
	        		worldIn.setBlockState(topPos, this.setAge(this.getDefaultState().with(HALF, Half.TOP), i), 2);
	        		canGrow = true;
	        	}
	        }
	        
	        if(canGrow){
	        	worldIn.setBlockState(pos, this.setAge(state.with(HALF, Half.BOTTOM), i), 2);
	        }
		}
	}
    
	public BlockState setAge(BlockState state, int age){
		return state.with(AGE, age);
	}
    
	public boolean isBottomCrop(IWorld world, BlockPos pos){
		return pos.equals(getBottomCrop(world, pos));
	}
	
	private BlockPos getBottomCrop(IWorld world, BlockPos pos){
		BlockPos bottom = pos;
		while(world.getBlockState(bottom.offset(Direction.DOWN)).getBlock() == this){
			bottom = bottom.offset(Direction.DOWN);
		}
		return bottom;
		
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
