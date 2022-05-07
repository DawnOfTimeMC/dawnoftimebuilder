package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import javax.annotation.Nullable;

public class ShutterBlock extends SmallShutterBlock {

    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    public ShutterBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(HALF, Half.BOTTOM));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HALF);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getLevel();
        Direction direction = context.getHorizontalDirection();
        BlockPos pos = context.getClickedPos();
        if(!world.getBlockState(pos.above()).canBeReplaced(context))
            return null;
        int x = direction.getStepX();
        int z = direction.getStepZ();
        double onX = context.getClickLocation().x - pos.getX();
        double onZ = context.getClickLocation().z - pos.getZ();
        boolean hingeLeft = (x >= 0 || onZ >= 0.5D) && (x <= 0 || onZ <= 0.5D) && (z >= 0 || onX <= 0.5D) && (z <= 0 || onX >= 0.5D);
        return super.getStateForPlacement(context).setValue(HINGE, hingeLeft ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT).setValue(FACING, direction).setValue(POWERED, world.hasNeighborSignal(pos)).setValue(HALF, Half.BOTTOM);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if(state.getValue(HALF) == Half.TOP){
            BlockState bottomState = worldIn.getBlockState(pos.below());
            if(bottomState.getBlock() == this){
                return bottomState.getValue(HALF) == Half.BOTTOM
                        && bottomState.getValue(FACING) == state.getValue(FACING)
                        && bottomState.getValue(HINGE) == state.getValue(HINGE);
            }
        }else return true;
        return false;
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack itemStack) {
        worldIn.setBlock(pos.above(), state.setValue(HALF, Half.TOP), 10);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        Direction halfDirection = (stateIn.getValue(HALF) == Half.TOP) ? Direction.DOWN : Direction.UP;
        if(facing == halfDirection){
            if(facingState.getBlock() != this)
                return Blocks.AIR.defaultBlockState();
            if(facingState.getValue(HALF) == stateIn.getValue(HALF)
                    || facingState.getValue(FACING) != stateIn.getValue(FACING)
                    || facingState.getValue(HINGE) != stateIn.getValue(HINGE))
                return Blocks.AIR.defaultBlockState();
            stateIn = stateIn.setValue(OPEN_POSITION, facingState.getValue(OPEN_POSITION));
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    protected DoTBBlockStateProperties.OpenPosition getOpenState(BlockState stateIn, IWorld worldIn, BlockPos pos) {
        BlockPos secondPos = pos.relative(stateIn.getValue(HALF) == Half.TOP ? Direction.DOWN : Direction.UP);
        if (!worldIn.getBlockState(secondPos).getCollisionShape(worldIn, pos).isEmpty() || !worldIn.getBlockState(pos).getCollisionShape(worldIn, pos).isEmpty())
            return DoTBBlockStateProperties.OpenPosition.HALF;
        return DoTBBlockStateProperties.OpenPosition.FULL;
    }
}
