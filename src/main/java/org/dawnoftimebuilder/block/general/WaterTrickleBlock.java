package org.dawnoftimebuilder.block.general;

import static net.minecraft.util.Hand.MAIN_HAND;

import java.util.Random;

import org.dawnoftimebuilder.block.templates.BasePoolBlock;
import org.dawnoftimebuilder.block.templates.BlockDoTB;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties.WaterTrickleEnd;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.block.material.PushReaction;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class WaterTrickleBlock extends BlockDoTB {

	public WaterTrickleBlock(final Properties propertiesIn) {
		super(propertiesIn);
		this.registerDefaultState(this.defaultBlockState()
				.setValue(DoTBBlockStateProperties.NORTH_TRICKLE, false)
				.setValue(DoTBBlockStateProperties.EAST_TRICKLE, false)
				.setValue(DoTBBlockStateProperties.SOUTH_TRICKLE, false)
				.setValue(DoTBBlockStateProperties.WEST_TRICKLE, false)
				.setValue(DoTBBlockStateProperties.CENTER_TRICKLE, false)
				.setValue(BlockStateProperties.UNSTABLE, true)
				.setValue(DoTBBlockStateProperties.WATER_TRICKLE_END, WaterTrickleEnd.FADE));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(
				DoTBBlockStateProperties.NORTH_TRICKLE,
				DoTBBlockStateProperties.EAST_TRICKLE,
				DoTBBlockStateProperties.SOUTH_TRICKLE,
				DoTBBlockStateProperties.WEST_TRICKLE,
				DoTBBlockStateProperties.CENTER_TRICKLE,
				BlockStateProperties.UNSTABLE,
				DoTBBlockStateProperties.WATER_TRICKLE_END);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, final Direction directionIn, BlockState facingStateIn, final IWorld worldIn, final BlockPos currentPosIn, final BlockPos facingPosIn) {
		// If a block above or under changed, the water trickle is set UNSTABLE. Unstable water trickles are updated on the next randomTick.
		if(directionIn == Direction.UP){
			if(facingStateIn.getBlock() instanceof WaterTrickleBlock){
				return stateIn.setValue(BlockStateProperties.UNSTABLE, true);
			}
		}
		if(directionIn == Direction.DOWN){
			if(worldIn instanceof World){
				return stateIn
							.setValue(DoTBBlockStateProperties.WATER_TRICKLE_END, this.getWaterTrickleEnd((World) worldIn, facingPosIn, facingStateIn))
							.setValue(BlockStateProperties.UNSTABLE, true);
			}
		}
		return stateIn;
	}

	/**
	 * Creates an array used to get the list of the water trickles produced by this block.
	 * @param currentState BlockState of the WaterTrickle.
	 * @return A boolean array that contains 5 booleans {north, east, south, west center}.
	 * Each true value correspond to a water trickle at the corresponding position.
	 */
	public boolean[] getWaterTrickleOutPut(BlockState currentState){
		return new boolean[]{
				currentState.getValue(DoTBBlockStateProperties.NORTH_TRICKLE),
				currentState.getValue(DoTBBlockStateProperties.EAST_TRICKLE),
				currentState.getValue(DoTBBlockStateProperties.SOUTH_TRICKLE),
				currentState.getValue(DoTBBlockStateProperties.WEST_TRICKLE),
				currentState.getValue(DoTBBlockStateProperties.CENTER_TRICKLE)};
	}

	/**
	 * Synchronize the water trickles with the water trickles produced by the block above.
	 * @param currentState Actual state of the block to synchronize.
	 * @param aboveState BlockState of the block above.
	 * @return An updated BlockState with the updated trickles. Can be AIR blockstate if the block should disappear.
	 */
	public BlockState inheritWaterTrickles(BlockState currentState, BlockState aboveState){
		boolean[] trickles = ((WaterTrickleBlock) aboveState.getBlock()).getWaterTrickleOutPut(aboveState);
		BooleanProperty[] properties = new BooleanProperty[]{
				DoTBBlockStateProperties.NORTH_TRICKLE,
				DoTBBlockStateProperties.EAST_TRICKLE,
				DoTBBlockStateProperties.SOUTH_TRICKLE,
				DoTBBlockStateProperties.WEST_TRICKLE,
				DoTBBlockStateProperties.CENTER_TRICKLE
		};
		int i = 0;
		BlockState updatedState = currentState;
		for(BooleanProperty property : properties){
			if(currentState.getValue(property) != trickles[i]){
				updatedState = updatedState
						.setValue(property, trickles[i])
						.setValue(BlockStateProperties.UNSTABLE, true);
			}
			i++;
		}
		return updatedState;
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return state.getValue(BlockStateProperties.UNSTABLE);
	}

	@Override
	public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
		super.tick(state, world, pos, rand);
		// We consider that this Water Trickle won't be unstable by the end of this random tick.
		state = state.setValue(BlockStateProperties.UNSTABLE, false);
		BlockPos bottomPos = pos.below();
		BlockState bottomState = world.getBlockState(bottomPos);
		BlockPos abovePos = pos.above();
		BlockState aboveState = world.getBlockState(abovePos);

		// First we check if the block under has trickles. If yes, we check if there is some difference, and make it unstable if yes.
		if(bottomState.getBlock() instanceof WaterTrickleBlock){
			BlockState updatedState = this.inheritWaterTrickles(bottomState, state);
			if(updatedState.getValue(BlockStateProperties.UNSTABLE)){
				world.setBlock(bottomPos, bottomState.setValue(BlockStateProperties.UNSTABLE, true), 10);
			}
		// If the block under is not a water trickle and can be replaced and is not liquid, we put an unstable Flowing Water Trickle.
		}else if(bottomState.canBeReplaced(this.generateContext(world, bottomPos)) && world.getFluidState(bottomPos).getType().equals(Fluids.EMPTY)){
			bottomState = this.createFlowingTrickle(bottomState, this.getWaterTrickleOutPut(state), world, bottomPos);
		// If the block can contain fluid, we fill it with water.
		}else if(bottomState.getBlock() instanceof ILiquidContainer && ((ILiquidContainer) bottomState.getBlock()).canPlaceLiquid(world, bottomPos, bottomState, Fluids.WATER)){
			((ILiquidContainer) bottomState.getBlock()).placeLiquid(world, bottomPos, bottomState,Fluids.WATER.getSource(false));
		}

		BlockState updatedState = updateWaterTrickle(world, state, bottomPos, bottomState, aboveState);
		world.setBlock(pos, updatedState, 10);
	}

	public BlockState updateWaterTrickle(World world, BlockState currentState, BlockPos bottomPos, BlockState bottomState, BlockState aboveState){
		// We update the water trickle end, now that the bottom block has been updated.
		WaterTrickleEnd lowerEnd = this.getWaterTrickleEnd(world, bottomPos, bottomState);
		currentState = currentState.setValue(DoTBBlockStateProperties.WATER_TRICKLE_END, lowerEnd);

		// Finally, we synchronize this water trickle with the block above. If it changes, this block stays unstable.
		if(aboveState.getBlock() instanceof WaterTrickleBlock){
			currentState = this.inheritWaterTrickles(currentState, aboveState);
		}else{
			currentState = this.inheritWaterTrickles(currentState, this.defaultBlockState());
		}
		return currentState;
	}

	/**
	 * Generates a BlockState of Water Trickle based on the current block, and set it in the world if needed.
	 * @param currentState BlockState of the current block. Will be returned by the function.
	 * @param trickles Array of booleans that correspond to each of the 5 trickles.
	 * @param world Level in which the Water Trickle must be set.
	 * @param waterTricklePos BlockPos where the Trickle must be placed.
	 * @return The new BlockState of the block (either a trickle or the old state).
	 */
	public BlockState createFlowingTrickle(BlockState currentState, boolean[] trickles, World world, BlockPos waterTricklePos){
		BooleanProperty[] properties = new BooleanProperty[]{
				DoTBBlockStateProperties.NORTH_TRICKLE,
				DoTBBlockStateProperties.EAST_TRICKLE,
				DoTBBlockStateProperties.SOUTH_TRICKLE,
				DoTBBlockStateProperties.WEST_TRICKLE,
				DoTBBlockStateProperties.CENTER_TRICKLE
		};
		int i = 0;
		int numberOfTrickle = 0;
		BlockState waterTrickleState = DoTBBlocksRegistry.WATER_FLOWING_TRICKLE.get().defaultBlockState();
		// Creates the BlockState of the trickle based on the source block.
		for(BooleanProperty property : properties){
			waterTrickleState = waterTrickleState.setValue(property, trickles[i]);
			if(trickles[i]){
				numberOfTrickle++;
			}
			i++;
		}
		// If the number of trickle is still null, then we don't create anything.
		if(numberOfTrickle > 0){
			world.setBlock(waterTricklePos, waterTrickleState, 10);
			return waterTrickleState;
		}
		return currentState;
	}

	@Override
	public void animateTick(BlockState state, World worldIn, BlockPos pos, Random rand) {
		super.animateTick(state, worldIn, pos, rand);
		boolean[] trickles = this.getWaterTrickleOutPut(state);
		if(state.getValue(DoTBBlockStateProperties.WATER_TRICKLE_END) == WaterTrickleEnd.SPLASH)
		{
			this.spawnFullParticles(worldIn, pos, trickles[0], rand, 0.5D, 0.4D);
			this.spawnFullParticles(worldIn, pos, trickles[1], rand, 0.6D, 0.5D);
			this.spawnFullParticles(worldIn, pos, trickles[2], rand, 0.5D, 0.6D);
			this.spawnFullParticles(worldIn, pos, trickles[3], rand, 0.5D, 0.6D);
			this.spawnFullParticles(worldIn, pos, trickles[4], rand, 0.5D, 0.5D);

			return;
		}

		BlockState belowState = worldIn.getBlockState(pos.below());

		if(belowState.getBlock() instanceof BasePoolBlock)
		{
			if(belowState.getValue(DoTBBlockStateProperties.LEVEL) > ((BasePoolBlock) belowState.getBlock()).faucetLevel)
			{
				this.spawnLimitedParticles(worldIn, pos, trickles[0], rand, 0.5D, 0.4D);
				this.spawnLimitedParticles(worldIn, pos, trickles[1], rand, 0.6D, 0.5D);
				this.spawnLimitedParticles(worldIn, pos, trickles[2], rand, 0.5D, 0.6D);
				this.spawnLimitedParticles(worldIn, pos, trickles[3], rand, 0.5D, 0.6D);
				this.spawnLimitedParticles(worldIn, pos, trickles[4], rand, 0.5D, 0.5D);
			}
		}
	}

	private void spawnLimitedParticles(World worldIn, BlockPos pos, boolean isOn, Random rand, double xOffset, double zOffset)
	{
		if(isOn){
			double offset = 0.75D;
			worldIn.addParticle(
					ParticleTypes.BUBBLE_POP,
					true,
					pos.getX() + xOffset + (rand.nextDouble() * offset - offset/2.0D),
					pos.getY() + 0.1D,
					pos.getZ() + zOffset + (rand.nextDouble() * offset - offset/2.0D),
					0.0125D,
					0.075D,
					0.0125D);

			offset = 0.60D;
			worldIn.addParticle(
					ParticleTypes.CLOUD,
					true,
					pos.getX() + xOffset + (rand.nextDouble() * offset - offset/2.0D),
					pos.getY() + 0.0D,
					pos.getZ() + zOffset + (rand.nextDouble() * offset - offset/2.0D),
					0.0005D,
					0.010D,
					0.0005D);
		}
	}

	private void spawnFullParticles(World worldIn, BlockPos pos, boolean isOn, Random rand, double xOffset, double zOffset)
	{
		if(isOn){
			double offset;
			for(int i = 0; i < 4; i ++)
			{
				offset = 0.75D;
				worldIn.addParticle(
						ParticleTypes.BUBBLE_POP,
						true,
						pos.getX() + xOffset + (rand.nextDouble() * offset - offset/2.0D),
						pos.getY() + 0.1D,
						pos.getZ() + zOffset + (rand.nextDouble() * offset - offset/2.0D),
						0.0125D,
						0.075D,
						0.0125D);

				offset = 0.60D;
				worldIn.addParticle(
						ParticleTypes.CLOUD,
						true,
						pos.getX() + xOffset + (rand.nextDouble() * offset - offset/2.0D),
						pos.getY() + 0.0D,
						pos.getZ() + zOffset + (rand.nextDouble() * offset - offset/2.0D),
						0.0005D,
						0.010D,
						0.0005D);
			}
		}
	}

	protected WaterTrickleEnd getWaterTrickleEnd(World level, BlockPos bottomPos, BlockState bottomState){
		if(bottomState.getBlock() instanceof WaterTrickleBlock){
			return WaterTrickleEnd.STRAIGHT;
		}
		// If the face under the water trickle is full or if there is a fluid, there is a splash effect.
		if(!level.getFluidState(bottomPos).getType().equals(Fluids.EMPTY)){
			return WaterTrickleEnd.SPLASH;
		}
		return WaterTrickleEnd.FADE;
	}

	private BlockItemUseContext generateContext(World level, BlockPos fromPos){
		Vector3d vec = new Vector3d(fromPos.getX() + 0.5D, fromPos.getY(), fromPos.getZ() + 0.5D);
		return new BlockItemUseContext(level, null, MAIN_HAND, ItemStack.EMPTY, new BlockRayTraceResult(vec, Direction.DOWN, fromPos, false));
	}

	protected static BooleanProperty getPropertyFromDirection(Direction facing){
		switch(facing){
			default:
				return DoTBBlockStateProperties.CENTER;
			case NORTH:
				return BlockStateProperties.NORTH;
			case SOUTH:
				return BlockStateProperties.SOUTH;
			case WEST:
				return BlockStateProperties.WEST;
			case EAST:
				return BlockStateProperties.EAST;
		}
	}

	@Override
	public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
		return PushReaction.DESTROY;
	}
}