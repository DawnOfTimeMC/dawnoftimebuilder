package org.dawnoftime.dawnoftime.block.german;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.dawnoftime.dawnoftime.block.roman.MarbleStatueBlock;
import org.dawnoftime.dawnoftime.block.templates.HorizontalBlockAA;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;

public class WaxedOakCanopyBedWoodBlock extends HorizontalBlockAA {
    public static final IntegerProperty MULTIBLOCK_X = BlockStatePropertiesAA.MULTIBLOCK_3X;
    public static final IntegerProperty MULTIBLOCK_Y = BlockStatePropertiesAA.MULTIBLOCK_2Y;
    public static final IntegerProperty MULTIBLOCK_Z = BlockStatePropertiesAA.MULTIBLOCK_3Z;
    public WaxedOakCanopyBedWoodBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(MULTIBLOCK_X, 0).setValue(MULTIBLOCK_Y, 0).setValue(MULTIBLOCK_Z, 0));
    }

    public static BlockState getMultiblockState(BlockState defaultState, int x, int y, int z){
        return defaultState.setValue(MULTIBLOCK_X, x).setValue(MULTIBLOCK_Y, y).setValue(MULTIBLOCK_Z, z);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(MULTIBLOCK_X, MULTIBLOCK_Y, MULTIBLOCK_Z);
    }

    @Override
    public void playerWillDestroy(final Level worldIn, final BlockPos blockPosIn, final BlockState blockStateIn, final Player playerEntityIn) {
        // Breaks the bed center to trigger the destruction of the multiblock, with the correct tag to drop the item or not.
        // TODO Below
        if(!worldIn.isClientSide() && playerEntityIn.isCreative()) {
            BlockPos blockPos;
            int multiblockValue = blockStateIn.getValue(MarbleStatueBlock.MULTIBLOCK);
            if(multiblockValue > 0) {
                blockPos = blockPosIn.below(multiblockValue);
                final BlockState blockState = worldIn.getBlockState(blockPos);
                worldIn.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 35);
                // Event that plays the "break block" sound.
                worldIn.levelEvent(playerEntityIn, 2001, blockPos, Block.getId(blockState));
            }
        }
        super.playerWillDestroy(worldIn, blockPosIn, blockStateIn, playerEntityIn);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        // TODO Below : check if center exists.
        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }
}
