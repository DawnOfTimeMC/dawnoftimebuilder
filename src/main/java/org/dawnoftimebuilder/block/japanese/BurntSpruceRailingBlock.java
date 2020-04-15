package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import org.dawnoftimebuilder.block.builders.FenceBlockDoTB;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties.FencePillar;

public class BurntSpruceRailingBlock extends FenceBlockDoTB {

	private static final EnumProperty<DoTBBlockStateProperties.FencePillar> FENCE_PILLAR = DoTBBlockStateProperties.FENCE_PILLAR;

	public BurntSpruceRailingBlock() {
		super("burnt_spruce_railing", Material.WOOD, 2.0F, 2.0F);
		this.setBurnable();
		this.setDefaultState(this.getStateContainer().getBaseState().with(EAST, false).with(NORTH, false).with(FENCE_PILLAR, FencePillar.NONE).with(SOUTH, false).with(WEST, false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(FENCE_PILLAR);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState state = super.getStateForPlacement(context);
		if(state == null) return this.getDefaultState();
		return state.with(FENCE_PILLAR, (context.getNearestLookingDirection().getAxis().isVertical()) ? FencePillar.PILLAR_BIG : FencePillar.NONE);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		if(this.hasNoPillar(stateIn)) return stateIn;
		else{
			if((stateIn.get(EAST) || stateIn.get(WEST)) && (stateIn.get(NORTH) || stateIn.get(SOUTH))){
				if(!worldIn.isAirBlock(currentPos.up())) return stateIn.with(FENCE_PILLAR, FencePillar.PILLAR_BIG);
				else return stateIn.with(FENCE_PILLAR, FencePillar.CAP_PILLAR_BIG);
			}else return stateIn.with(FENCE_PILLAR, FencePillar.PILLAR_SMALL);
		}
	}

    private boolean hasNoPillar(BlockState state){
		return state.get(FENCE_PILLAR) == FencePillar.NONE;
	}

	public FencePillar getShape(BlockState state){
		return state.get(FENCE_PILLAR);
	}
}
