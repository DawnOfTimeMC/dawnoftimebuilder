package org.dawnoftimebuilder.block.general;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.ICustomBlockItem;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import javax.annotation.Nullable;

public class WaterFlowingTrickleBlock extends WaterTrickleBlock implements ICustomBlockItem {
    public WaterFlowingTrickleBlock(Properties propertiesIn) {
        super(propertiesIn);
    }

    @Override
    public BlockState updateWaterTrickle(World world, BlockState currentState, BlockPos bottomPos, BlockState bottomState, BlockState aboveState) {
        currentState = super.updateWaterTrickle(world, currentState, bottomPos, bottomState, aboveState);
        BooleanProperty[] properties = new BooleanProperty[]{
                DoTBBlockStateProperties.NORTH_TRICKLE,
                DoTBBlockStateProperties.EAST_TRICKLE,
                DoTBBlockStateProperties.SOUTH_TRICKLE,
                DoTBBlockStateProperties.WEST_TRICKLE,
                DoTBBlockStateProperties.CENTER_TRICKLE
        };
        // If one of the bool properties is True, it means this flowing water trickle is not empty. It disappears otherwise.
        boolean hasTickle = false;
        for(BooleanProperty prop : properties){
            if(currentState.getValue(prop)){
                hasTickle = true;
                break;
            }
        }
        if(!hasTickle){
            return Blocks.AIR.defaultBlockState();
        }
        // If the block under has a full face, we create a Water Block;
        if(Block.isFaceFull(bottomState.getCollisionShape(world, bottomPos), Direction.UP)){
            return Blocks.WATER.defaultBlockState();
        }
        return currentState;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction directionIn, BlockState facingStateIn, IWorld worldIn, BlockPos currentPosIn, BlockPos facingPosIn) {
        if(directionIn == Direction.UP){
            if(!(facingStateIn.getBlock() instanceof WaterTrickleBlock)){
                return Blocks.AIR.defaultBlockState();
            }
        }
        return super.updateShape(stateIn, directionIn, facingStateIn, worldIn, currentPosIn, facingPosIn);
    }

    @Override
    public boolean canBeReplaced(BlockState p_196253_1_, BlockItemUseContext p_196253_2_) {
        return true;
    }

    @Nullable
    @Override
    public Item getCustomBlockItem() {
        return null;
    }
}
