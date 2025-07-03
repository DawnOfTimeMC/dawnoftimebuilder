package org.dawnoftime.dawnoftime.block.general;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;

public class WaterFlowingTrickleBlock extends WaterTrickleBlock {
    public WaterFlowingTrickleBlock(Properties propertiesIn) {
        super(propertiesIn);
    }

    @Override
    public BlockState updateWaterTrickle(Level world, BlockState currentState, BlockPos bottomPos, BlockState bottomState, BlockState aboveState) {
        currentState = super.updateWaterTrickle(world, currentState, bottomPos, bottomState, aboveState);
        BooleanProperty[] properties = new BooleanProperty[] {
                BlockStatePropertiesAA.NORTH_TRICKLE,
                BlockStatePropertiesAA.EAST_TRICKLE,
                BlockStatePropertiesAA.SOUTH_TRICKLE,
                BlockStatePropertiesAA.WEST_TRICKLE,
                BlockStatePropertiesAA.CENTER_TRICKLE
        };
        // If one of the bool properties is True, it means this flowing water trickle is not empty. It disappears otherwise.
        boolean hasTickle = false;
        for(BooleanProperty prop : properties) {
            if(currentState.getValue(prop)) {
                hasTickle = true;
                break;
            }
        }
        if(!hasTickle) {
            return Blocks.AIR.defaultBlockState();
        }
        // If the block under has a full face, we create a Water Block;
        if(Block.isFaceFull(bottomState.getCollisionShape(world, bottomPos), Direction.UP)) {
            return Blocks.WATER.defaultBlockState();
        }
        return currentState;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction directionIn, BlockState facingStateIn, LevelAccessor worldIn, BlockPos currentPosIn, BlockPos facingPosIn) {
        if(directionIn == Direction.UP) {
            if(!(facingStateIn.getBlock() instanceof WaterTrickleBlock)) {
                return Blocks.AIR.defaultBlockState();
            }
        }
        return super.updateShape(stateIn, directionIn, facingStateIn, worldIn, currentPosIn, facingPosIn);
    }

    @Override
    public boolean canBeReplaced(BlockState p_196253_1_, BlockPlaceContext p_196253_2_) {
        return true;
    }
}
