package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;

public class DoorBlockDoT extends DoorBlock {
    public DoorBlockDoT(Properties properties, BlockSetType blockSetType) {
        super(blockSetType, properties);
    }

    @Override
    protected BlockState updateShape(BlockState stateIn, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction facing, BlockPos neighborPos, BlockState facingState, RandomSource random) {
        Direction dirOtherDoor = (stateIn.getValue(HINGE) == DoorHingeSide.LEFT) ? stateIn.getValue(FACING).getClockWise() : stateIn.getValue(FACING).getCounterClockWise();
        if(facing == dirOtherDoor) {
            if(facingState.getBlock() instanceof DoorBlock) {
                if(stateIn.getValue(HINGE) != facingState.getValue(HINGE))
                    return stateIn.setValue(OPEN, facingState.getValue(OPEN));
            }
        }
        return super.updateShape(stateIn, level, scheduledTickAccess, pos, facing, neighborPos, facingState, random);
    }
}
