package org.dawnoftimebuilder.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import org.dawnoftimebuilder.block.templates.BlockAA;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;
import org.dawnoftimebuilder.util.VoxelShapes;

public class TatamiFloorBlock extends BlockAA {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    public TatamiFloorBlock(Properties properties) {
        super(properties.pushReaction(PushReaction.DESTROY), VoxelShapes.TATAMI_FLOOR_SHAPES);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, FACING);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        Direction directionOtherHalf = (stateIn.getValue(HALF) == Half.TOP) ? stateIn.getValue(FACING) : stateIn.getValue(FACING).getOpposite();
        if(facing == Direction.UP && worldIn instanceof Level) {
            BlockState stateAbove = worldIn.getBlockState(facingPos);
            if(isFaceFull(stateAbove.getShape(worldIn, facingPos), Direction.DOWN) && stateAbove.canOcclude()) {
                Containers.dropItemStack((Level) worldIn, currentPos.getX(), currentPos.getY(), currentPos.getZ(), new ItemStack(DoTBBlocksRegistry.INSTANCE.TATAMI_MAT.get().asItem()));
                new ItemStack(DoTBBlocksRegistry.INSTANCE.TATAMI_MAT.get().asItem());
                worldIn.setBlock(currentPos.relative(directionOtherHalf), Blocks.SPRUCE_PLANKS.defaultBlockState(), 10);
                return Blocks.SPRUCE_PLANKS.defaultBlockState();
            }
        }
        if(facing == directionOtherHalf) {
            if(facingState.getBlock() != this)
                return Blocks.AIR.defaultBlockState();
            else if(facingState.getValue(FACING) != stateIn.getValue(FACING) || facingState.getValue(HALF) == stateIn.getValue(HALF))
                return Blocks.AIR.defaultBlockState();
        }
        return stateIn;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level worldIn, BlockPos pos, Player player, BlockHitResult hit) {
        if(!worldIn.isClientSide) {
            if(player.isCrouching()) {
                boolean isTop = state.getValue(HALF) == Half.TOP;
                BlockPos otherPos = (isTop) ? pos.relative(state.getValue(FACING)) : pos.relative(state.getValue(FACING).getOpposite());
                if(isTop)//Check if the blocks above each part are AIR
                    if(!worldIn.isEmptyBlock(pos.above()))
                        return InteractionResult.PASS;
                    else if(!worldIn.isEmptyBlock(otherPos.above()))
                        return InteractionResult.PASS;
                worldIn.setBlock(pos, Blocks.SPRUCE_PLANKS.defaultBlockState(), 2);
                worldIn.setBlock(otherPos, Blocks.SPRUCE_PLANKS.defaultBlockState(), 2);
                worldIn.setBlock((isTop) ? pos.above() : otherPos.above(), DoTBBlocksRegistry.INSTANCE.TATAMI_MAT.get().defaultBlockState().setValue(TatamiMatBlock.HALF, Half.TOP).setValue(TatamiMatBlock.FACING, state.getValue(FACING)).setValue(TatamiMatBlock.ROLLED, true), 2);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public BlockState playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(worldIn, pos, state, player);
        BlockPos otherPos = (state.getValue(HALF) == Half.TOP) ? pos.relative(state.getValue(FACING)) : pos.relative(state.getValue(FACING).getOpposite());
        worldIn.setBlock(pos, Blocks.SPRUCE_PLANKS.defaultBlockState(), 10);
        worldIn.setBlock(otherPos, Blocks.SPRUCE_PLANKS.defaultBlockState(), 10);
        return state;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return rotate(state, Rotation.CLOCKWISE_180);
    }
}