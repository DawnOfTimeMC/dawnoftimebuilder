package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.state.properties.Half;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;

import javax.annotation.Nullable;

public class ShuttersBlock extends SmallShuttersBlock {

    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    public ShuttersBlock(Properties properties) {
        super(properties);
    }

    public ShuttersBlock(Material materialIn, float hardness, float resistance) {
        this(Properties.create(materialIn).hardnessAndResistance(hardness, resistance));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(HALF);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getWorld();
        Direction direction = context.getPlacementHorizontalFacing();
        BlockPos pos = context.getPos();
        if(!world.getBlockState(pos.up()).isReplaceable(context))
            return null;
        int x = direction.getXOffset();
        int z = direction.getZOffset();
        double onX = context.getHitVec().x - pos.getX();
        double onZ = context.getHitVec().z - pos.getZ();
        boolean hingeLeft = (x >= 0 || onZ >= 0.5D) && (x <= 0 || onZ <= 0.5D) && (z >= 0 || onX <= 0.5D) && (z <= 0 || onX >= 0.5D);
        Direction hingeDirection = hingeLeft ? direction.rotateYCCW() : direction.rotateY();
        if(!canSupportShutters(world, pos, direction, hingeDirection) || !canSupportShutters(world, pos.up(), direction, hingeDirection)) hingeLeft = !hingeLeft;
        return super.getStateForPlacement(context).with(HINGE, hingeLeft ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT).with(FACING, direction).with(POWERED, world.isBlockPowered(pos)).with(HALF, Half.BOTTOM);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        Direction direction = state.get(FACING);
        Direction hingeDirection = (state.get(HINGE) == DoorHingeSide.LEFT) ? direction.rotateYCCW() : direction.rotateY();
        if(state.get(HALF) == Half.TOP){
            BlockState bottomState = worldIn.getBlockState(pos.down());
            if(bottomState.getBlock() == this){
                if(bottomState.get(HALF) == Half.BOTTOM
                        && bottomState.get(FACING) == state.get(FACING)
                        && bottomState.get(HINGE) == state.get(HINGE))
                    return canSupportShutters(worldIn, pos, direction, hingeDirection);
            }
        }else return canSupportShutters(worldIn, pos, direction, hingeDirection);
        return false;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        worldIn.setBlockState(pos.up(), state.with(HALF, Half.TOP), 10);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        Direction halfDirection = (stateIn.get(HALF) == Half.TOP) ? Direction.DOWN : Direction.UP;
        if(facing == halfDirection){
            if(facingState.getBlock() != this)
                return Blocks.AIR.getDefaultState();
            if(facingState.get(HALF) == stateIn.get(HALF)
                    || facingState.get(FACING) != stateIn.get(FACING)
                    || facingState.get(HINGE) != stateIn.get(HINGE))
                return Blocks.AIR.getDefaultState();
            stateIn = stateIn.with(OPEN_POSITION, facingState.get(OPEN_POSITION));
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    protected DoTBBlockStateProperties.OpenPosition getOpenState(BlockState stateIn, World worldIn, BlockPos pos) {
        BlockPos secondPos = pos.offset(stateIn.get(HALF) == Half.TOP ? Direction.DOWN : Direction.UP);
        if(!worldIn.getBlockState(secondPos).getCollisionShape(worldIn, pos).isEmpty() || !worldIn.getBlockState(pos).getCollisionShape(worldIn, pos).isEmpty())
            return DoTBBlockStateProperties.OpenPosition.HALF;
        return DoTBBlockStateProperties.OpenPosition.FULL;
    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, Blocks.AIR.getDefaultState(), te, stack);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockPos blockpos = (state.get(HALF) == Half.TOP) ? pos.down() : pos.up();
        BlockState otherState = worldIn.getBlockState(blockpos);
        if(otherState.getBlock() == this && otherState.get(HALF) != state.get(HALF)) {
            worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
            worldIn.playEvent(player, 2001, blockpos, Block.getStateId(otherState));
            ItemStack itemstack = player.getHeldItemMainhand();
            if(!worldIn.isRemote && !player.isCreative()) {
                Block.spawnDrops(state, worldIn, pos, null, player, itemstack);
                Block.spawnDrops(otherState, worldIn, blockpos, null, player, itemstack);
            }
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }
}
