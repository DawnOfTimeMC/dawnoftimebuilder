package org.dawnoftimebuilder.block.templates;

import net.minecraft.advancements.criterion.BlockPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import org.dawnoftimebuilder.block.general.WaterSourceTrickleBlock;
import org.dawnoftimebuilder.block.general.WaterTrickleBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties.VerticalLimitedConnection;

/**
 * @author Seynax
 */
public class FaucetBlock extends WaterSourceTrickleBlock {

	public FaucetBlock(final Properties propertiesIn) {
		super(propertiesIn);
		this.registerDefaultState(this.defaultBlockState()
				.setValue(DoTBBlockStateProperties.ACTIVATED, false));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(DoTBBlockStateProperties.ACTIVATED);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext contextIn)
	{
		final World level = contextIn.getLevel();
		final BlockPos pos = contextIn.getClickedPos();
		final BlockState currentState = level.getBlockState(pos);
		final Direction targetDirection = contextIn.getHorizontalDirection();

		// If the current block is a WaterTrickle, we keep its state to add a new trickle in this block.
		BlockState outState = currentState.getBlock().is(this) ? currentState : this.defaultBlockState();
		// We add a trickle for the target direction and the end type.
		return outState.setValue(getPropertyFromDirection(targetDirection), true);
	}

	@Override
	public boolean[] getWaterTrickleOutPut(BlockState currentState) {
		if(!currentState.getValue(DoTBBlockStateProperties.ACTIVATED)){
			return new boolean[]{
					currentState.getValue(DoTBBlockStateProperties.NORTH_TRICKLE),
					currentState.getValue(DoTBBlockStateProperties.EAST_TRICKLE),
					currentState.getValue(DoTBBlockStateProperties.SOUTH_TRICKLE),
					currentState.getValue(DoTBBlockStateProperties.WEST_TRICKLE),
					currentState.getValue(DoTBBlockStateProperties.CENTER_TRICKLE)};
		}
		return super.getWaterTrickleOutPut(currentState);
	}

	@Override
	public ActionResultType use(BlockState blockStateIn, final World worldIn, final BlockPos blockPosIn, final PlayerEntity playerEntityIn, final Hand handIn, final BlockRayTraceResult blockRaytraceResultIn) {
		final ItemStack mainHandItemStack = playerEntityIn.getMainHandItem();
		if (!mainHandItemStack.isEmpty() && mainHandItemStack.getItem() == this.asItem()) {
			return ActionResultType.PASS;
		}
		boolean activated = !blockStateIn.getValue(DoTBBlockStateProperties.ACTIVATED);
		blockStateIn = blockStateIn.setValue(DoTBBlockStateProperties.ACTIVATED, activated);
		if(activated)
		{
			blockStateIn = blockStateIn.setValue(BlockStateProperties.UNSTABLE, true);
		}

		worldIn.setBlock(blockPosIn, blockStateIn, 10);

		return ActionResultType.SUCCESS;
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction directionIn, BlockState facingStateIn, IWorld worldIn,
			BlockPos currentPosIn, BlockPos facingPosIn)
	{
		BlockState state = super.updateShape(stateIn, directionIn, facingStateIn, worldIn, currentPosIn, facingPosIn);
		boolean lastActivation = state.getValue(DoTBBlockStateProperties.ACTIVATED);

		switch(directionIn)
		{
		case NORTH:
			if(state.getValue(BlockStateProperties.NORTH) && facingStateIn.getBlock() instanceof BasePoolBlock)
			{
				int level = facingStateIn.getValue(DoTBBlockStateProperties.LEVEL);
				state = state.setValue(DoTBBlockStateProperties.ACTIVATED, level >= ((BasePoolBlock) facingStateIn.getBlock()).faucetLevel);
			}
			break;
		case SOUTH:
			if(state.getValue(BlockStateProperties.SOUTH) && facingStateIn.getBlock() instanceof BasePoolBlock)
			{
				int level = facingStateIn.getValue(DoTBBlockStateProperties.LEVEL);
				state = state.setValue(DoTBBlockStateProperties.ACTIVATED, level >= ((BasePoolBlock) facingStateIn.getBlock()).faucetLevel);
			}
			break;
		case EAST:
			if(state.getValue(BlockStateProperties.EAST) && facingStateIn.getBlock() instanceof BasePoolBlock)
			{
				int level = facingStateIn.getValue(DoTBBlockStateProperties.LEVEL);
				state = state.setValue(DoTBBlockStateProperties.ACTIVATED, level >= ((BasePoolBlock) facingStateIn.getBlock()).faucetLevel);
			}
			break;
		case WEST:
			if(state.getValue(BlockStateProperties.WEST) && facingStateIn.getBlock() instanceof BasePoolBlock)
			{
				int level = facingStateIn.getValue(DoTBBlockStateProperties.LEVEL);
				state = state.setValue(DoTBBlockStateProperties.ACTIVATED, level >= ((BasePoolBlock) facingStateIn.getBlock()).faucetLevel);
			}
			break;
		case DOWN:
			if(state.getValue(DoTBBlockStateProperties.ACTIVATED))
			{
				if(facingStateIn.getBlock() instanceof WaterTrickleBlock )
				{
					state = state.setValue(BlockStateProperties.UNSTABLE, false);
				}
				else
				{
					state = state.setValue(BlockStateProperties.UNSTABLE, true);
				}
			}
			break;
		}


		if(!worldIn.isClientSide() && state.getValue(DoTBBlockStateProperties.ACTIVATED) != lastActivation)
		{
			((ServerWorld) worldIn).getBlockTicks().scheduleTick(currentPosIn, this, 5);
		}

		return state;
	}
}