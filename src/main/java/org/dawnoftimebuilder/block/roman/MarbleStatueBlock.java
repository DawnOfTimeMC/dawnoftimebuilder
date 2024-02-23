package org.dawnoftimebuilder.block.roman;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import javax.annotation.Nullable;

public class MarbleStatueBlock extends WaterloggedBlock {
    private static final VoxelShape VS = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape VS_TOP = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty MULTIBLOCK = DoTBBlockStateProperties.MULTIBLOCK_0_2;

    public MarbleStatueBlock(final Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(MarbleStatueBlock.FACING, Direction.NORTH).setValue(MarbleStatueBlock.MULTIBLOCK, 0).setValue(WaterloggedBlock.WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(MarbleStatueBlock.FACING, MarbleStatueBlock.MULTIBLOCK);
    }

    @Override
    public void playerWillDestroy(final Level worldIn, final BlockPos blockPosIn, final BlockState blockStateIn, final Player playerEntityIn) {

        if(playerEntityIn.isCreative() && playerEntityIn.isCreative()) {
            BlockPos blockPos = blockPosIn;
            System.out.println(blockStateIn.getValue(MarbleStatueBlock.MULTIBLOCK));
            if(blockStateIn.getValue(MarbleStatueBlock.MULTIBLOCK) > 0) {
                blockPos = blockPosIn.below(blockStateIn.getValue(MarbleStatueBlock.MULTIBLOCK));
            }

            final BlockState blockState = worldIn.getBlockState(blockPos);
            worldIn.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 35);
            worldIn.levelEvent(playerEntityIn, 2001, blockPos, Block.getId(blockState));
        }

        super.playerWillDestroy(worldIn, blockPosIn, blockStateIn, playerEntityIn);
    }

    @Override
    public VoxelShape getShape(final BlockState state, final BlockGetter worldIn, final BlockPos pos, final CollisionContext context) {
        return state.getValue(MarbleStatueBlock.MULTIBLOCK) == 2 ? MarbleStatueBlock.VS_TOP : MarbleStatueBlock.VS;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        final BlockPos pos = context.getClickedPos();
        final Level level = context.getLevel();
        if(!level.getBlockState(pos.above()).canBeReplaced(context) || !level.getBlockState(pos.above(2)).canBeReplaced(context)) {
            return null;
        }
        return super.getStateForPlacement(context).setValue(MarbleStatueBlock.FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public void setPlacedBy(final Level worldIn, final BlockPos pos, final BlockState state, final LivingEntity placer, final ItemStack stack) {
        BlockPos abovePos = pos.above();
        worldIn.setBlock(abovePos, state.setValue(MarbleStatueBlock.MULTIBLOCK, 1).setValue(WaterloggedBlock.WATERLOGGED, worldIn.getFluidState(abovePos).getType() == Fluids.WATER), 10);
        abovePos = abovePos.above();
        worldIn.setBlock(abovePos, state.setValue(MarbleStatueBlock.MULTIBLOCK, 2).setValue(WaterloggedBlock.WATERLOGGED, worldIn.getFluidState(abovePos).getType() == Fluids.WATER), 10);
    }

    @Override
    public BlockState updateShape(final BlockState stateIn, final Direction facing, final BlockState facingState, final LevelAccessor worldIn, final BlockPos currentPos, final BlockPos facingPos) {
        if(facing.getAxis().isHorizontal()) {
            return stateIn;
        }
        final int multipart = stateIn.getValue(MarbleStatueBlock.MULTIBLOCK);

        if(facing == Direction.UP && multipart < 2 && facingState.getBlock() == this) {
            if(facingState.getValue(MarbleStatueBlock.FACING) == stateIn.getValue(MarbleStatueBlock.FACING) && facingState.getValue(MarbleStatueBlock.MULTIBLOCK) == multipart + 1) {
                return stateIn;
            }
        }

        if(facing == Direction.DOWN && multipart > 0 && facingState.getBlock() == this) {
            if(facingState.getValue(MarbleStatueBlock.FACING) == stateIn.getValue(MarbleStatueBlock.FACING) && facingState.getValue(MarbleStatueBlock.MULTIBLOCK) == multipart - 1) {
                return stateIn;
            }
        }

        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public PushReaction getPistonPushReaction(final BlockState state) {
        return PushReaction.DESTROY;
    }
}
