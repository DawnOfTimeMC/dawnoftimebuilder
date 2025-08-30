package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.*;
import org.jetbrains.annotations.NotNull;

public class DoorBlockDoT extends DoorBlock {
    public DoorBlockDoT(Properties properties, BlockSetType blockSetType) {
        super(properties, blockSetType);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        Direction dirOtherDoor = (stateIn.getValue(HINGE) == DoorHingeSide.LEFT) ? stateIn.getValue(FACING).getClockWise() : stateIn.getValue(FACING).getCounterClockWise();
        if(facing == dirOtherDoor) {
            if(facingState.getBlock() instanceof DoorBlock) {
                if(stateIn.getValue(HINGE) != facingState.getValue(HINGE))
                    return stateIn.setValue(OPEN, facingState.getValue(OPEN));
            }
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }
}
