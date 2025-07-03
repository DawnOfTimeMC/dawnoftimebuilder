package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public class PaneBlockDoT extends IronBarsBlock {
    public PaneBlockDoT(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelReader world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Fluid fluid = context.getLevel().getFluidState(context.getClickedPos()).getType();
        BlockPos posNorth = pos.north();
        BlockPos posSouth = pos.south();
        BlockPos posWest = pos.west();
        BlockPos posEast = pos.east();
        return this.defaultBlockState()
                .setValue(NORTH, this.canAttachPane(world, posNorth, Direction.SOUTH, world.getBlockState(posNorth)))
                .setValue(SOUTH, this.canAttachPane(world, posSouth, Direction.NORTH, world.getBlockState(posSouth)))
                .setValue(WEST, this.canAttachPane(world, posWest,  Direction.EAST, world.getBlockState(posWest)))
                .setValue(EAST, this.canAttachPane(world, posEast,  Direction.WEST, world.getBlockState(posEast)))
                .setValue(WATERLOGGED, fluid == Fluids.WATER);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        // Override was required because IronBarsBlock#attachsTo() is final (???) and I need to allow connection to CenteredDoors.
        if(stateIn.getValue(WATERLOGGED))
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        return facing.getAxis().isHorizontal() ? stateIn.setValue(PROPERTY_BY_DIRECTION.get(facing), this.canAttachPane(worldIn, facingPos, facing.getOpposite(), facingState)) : stateIn;
    }

    public boolean canAttachPane(LevelReader level, BlockPos pos, Direction dir, BlockState adjacentState) {
        Block block = adjacentState.getBlock();
        if(block instanceof IronBarsBlock || adjacentState.is(BlockTags.WALLS)){
            return true;
        }else if(block instanceof CenteredDoorBlock){
            return adjacentState.getValue(DoorBlock.FACING).getAxis() != dir.getAxis();
        }else{
            return !isExceptionForConnection(adjacentState) && adjacentState.isFaceSturdy(level, pos, dir);
        }
    }
}
