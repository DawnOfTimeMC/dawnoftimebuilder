package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class PaneBlockDoTB extends StainedGlassPaneBlock {
    public PaneBlockDoTB(DyeColor dyeColor, Properties properties) {
        super(dyeColor, properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        //Must be overridden to allow connection to paper_door
        LevelReader world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Fluid fluid = context.getLevel().getFluidState(context.getClickedPos()).getType();
        BlockPos posNorth = pos.north();
        BlockPos posSouth = pos.south();
        BlockPos posWest = pos.west();
        BlockPos posEast = pos.east();
        BlockState stateNorth = world.getBlockState(posNorth);
        BlockState stateSouth = world.getBlockState(posSouth);
        BlockState stateWest = world.getBlockState(posWest);
        BlockState stateEast = world.getBlockState(posEast);
        return this.defaultBlockState()
                .setValue(NORTH, this.canAttachPane(stateNorth, stateNorth.isFaceSturdy(world, posNorth, Direction.SOUTH)))
                .setValue(SOUTH, this.canAttachPane(stateSouth, stateSouth.isFaceSturdy(world, posSouth, Direction.NORTH)))
                .setValue(WEST, this.canAttachPane(stateWest, stateWest.isFaceSturdy(world, posWest, Direction.EAST)))
                .setValue(EAST, this.canAttachPane(stateEast, stateEast.isFaceSturdy(world, posEast, Direction.WEST)))
                .setValue(WATERLOGGED, fluid == Fluids.WATER);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        //Must be overridden to allow connection to paper_door
        if(stateIn.getValue(WATERLOGGED))
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        return facing.getAxis().isHorizontal() ? stateIn.setValue(PROPERTY_BY_DIRECTION.get(facing), this.canAttachPane(facingState, facingState.isFaceSturdy(worldIn, facingPos, facing.getOpposite()))) : stateIn;
    }

    public boolean canAttachPane(BlockState adjacentState, boolean hasSolidSide) {
        Block block = adjacentState.getBlock();
        return (!isExceptionForConnection(adjacentState) && hasSolidSide) || block instanceof StainedGlassPaneBlock /*|| block == PAPER_DOOR.get()*/;
    }
}
