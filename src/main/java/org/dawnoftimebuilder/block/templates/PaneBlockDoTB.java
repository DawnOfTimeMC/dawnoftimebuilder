package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import static org.dawnoftimebuilder.registries.DoTBBlocksRegistry.PAPER_DOOR;

public class PaneBlockDoTB extends PaneBlock {

	private final BlockRenderLayer renderLayer;

	public PaneBlockDoTB(Material materialIn, float hardness, float resistance, BlockRenderLayer renderLayer) {
		this(BlockDoTB.Properties.create(materialIn).hardnessAndResistance(hardness, resistance), renderLayer);
	}

	public PaneBlockDoTB(Material materialIn, float hardness, float resistance) {
		this(BlockDoTB.Properties.create(materialIn).hardnessAndResistance(hardness, resistance), BlockRenderLayer.SOLID);
	}

	public PaneBlockDoTB(Properties properties, BlockRenderLayer renderLayer) {
        super(properties);
		this.renderLayer = renderLayer;
    }

	public Block setBurnable() {
		FireBlock fireblock = (FireBlock) Blocks.FIRE;
		fireblock.setFireInfo(this, 5, 20);
		return this;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		//Must be overridden to allow connection to paper_door
		IBlockReader world = context.getWorld();
		BlockPos pos = context.getPos();
		IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
		BlockPos posNorth = pos.north();
		BlockPos posSouth = pos.south();
		BlockPos posWest = pos.west();
		BlockPos posEast = pos.east();
		BlockState stateNorth = world.getBlockState(posNorth);
		BlockState stateSouth = world.getBlockState(posSouth);
		BlockState stateWest = world.getBlockState(posWest);
		BlockState stateEast = world.getBlockState(posEast);
		return this.getDefaultState()
				.with(NORTH, this.canAttachPane(stateNorth, stateNorth.func_224755_d(world, posNorth, Direction.SOUTH)))
				.with(SOUTH, this.canAttachPane(stateSouth, stateSouth.func_224755_d(world, posSouth, Direction.NORTH)))
				.with(WEST, this.canAttachPane(stateWest, stateWest.func_224755_d(world, posWest, Direction.EAST)))
				.with(EAST, this.canAttachPane(stateEast, stateEast.func_224755_d(world, posEast, Direction.WEST)))
				.with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		//Must be overridden to allow connection to paper_door
		if (stateIn.get(WATERLOGGED)) worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		return facing.getAxis().isHorizontal() ? stateIn.with(FACING_TO_PROPERTY_MAP.get(facing), this.canAttachPane(facingState, facingState.func_224755_d(worldIn, facingPos, facing.getOpposite()))) : stateIn;
	}

	public boolean canAttachPane(BlockState adjacentState, boolean hasSolidSide) {
		Block block = adjacentState.getBlock();
		return (!cannotAttach(block) && hasSolidSide) || block instanceof PaneBlock || block == PAPER_DOOR;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return this.renderLayer;
	}
}
