package org.dawnoftimebuilder.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.NoItemBlock;

import static net.minecraft.world.level.block.Blocks.SPRUCE_PLANKS;
import static org.dawnoftimebuilder.registry.DoTBBlocksRegistry.TATAMI_MAT;

public class TatamiFloorBlock extends NoItemBlock {
    private static final VoxelShape VS = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 17.0D, 16.0D);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    public TatamiFloorBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return VS;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        Direction directionOtherHalf = (stateIn.getValue(HALF) == Half.TOP) ? stateIn.getValue(FACING) : stateIn.getValue(FACING).getOpposite();
        if(facing == Direction.UP && worldIn instanceof Level) {
            BlockState stateAbove = worldIn.getBlockState(facingPos);
            if(isFaceFull(stateAbove.getShape(worldIn, facingPos), Direction.DOWN) && stateAbove.canOcclude()) {
                Containers.dropItemStack((Level) worldIn, currentPos.getX(), currentPos.getY(), currentPos.getZ(), new ItemStack(TATAMI_MAT.get().asItem()));
                new ItemStack(TATAMI_MAT.get().asItem());
                worldIn.setBlock(currentPos.relative(directionOtherHalf), SPRUCE_PLANKS.defaultBlockState(), 10);
                return SPRUCE_PLANKS.defaultBlockState();
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
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if(!worldIn.isClientSide) {
            if(player.isCrouching()) {
                boolean isTop = state.getValue(HALF) == Half.TOP;
                BlockPos otherPos = (isTop) ? pos.relative(state.getValue(FACING)) : pos.relative(state.getValue(FACING).getOpposite());
                if(isTop)//Check if the blocks above each part are AIR
                    if(!worldIn.isEmptyBlock(pos.above()))
                        return InteractionResult.PASS;
                    else if(!worldIn.isEmptyBlock(otherPos.above()))
                        return InteractionResult.PASS;
                worldIn.setBlock(pos, SPRUCE_PLANKS.defaultBlockState(), 2);
                worldIn.setBlock(otherPos, SPRUCE_PLANKS.defaultBlockState(), 2);
                worldIn.setBlock((isTop) ? pos.above() : otherPos.above(), TATAMI_MAT.get().defaultBlockState().setValue(TatamiMatBlock.HALF, Half.TOP).setValue(TatamiMatBlock.FACING, state.getValue(FACING)).setValue(TatamiMatBlock.ROLLED, true), 2);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(worldIn, pos, state, player);
        BlockPos otherPos = (state.getValue(HALF) == Half.TOP) ? pos.relative(state.getValue(FACING)) : pos.relative(state.getValue(FACING).getOpposite());
        worldIn.setBlock(pos, SPRUCE_PLANKS.defaultBlockState(), 10);
        worldIn.setBlock(otherPos, SPRUCE_PLANKS.defaultBlockState(), 10);
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