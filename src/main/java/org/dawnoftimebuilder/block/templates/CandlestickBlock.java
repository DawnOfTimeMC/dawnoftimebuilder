package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nonnull;
import java.util.Random;

public class CandlestickBlock extends CandleLampBlock {

    private static final VoxelShape VS_BOTTOM = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 15.0D, 11.0D);
    private static final VoxelShape[] VS_SIDE = DoTBBlockUtils.GenerateHorizontalShapes(new VoxelShape[]{Block.box(4.0D, 1.0D, 0.0D, 14.0D, 15.0D, 14.0D)});
    public static final DirectionProperty FACING = BlockStateProperties.FACING_HOPPER;
    private static final BooleanProperty LIT = BlockStateProperties.LIT;

    public CandlestickBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED,false).setValue(FACING, Direction.DOWN).setValue(LIT, false));
    }

    @Nonnull
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction facing = context.getClickedFace();
        return super.getStateForPlacement(context).setValue(FACING, facing == Direction.UP ? Direction.DOWN : facing);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        this.animateLitCandle(stateIn, worldIn, pos, 0.5D, 1.0D, 0.5D);
    }

    @Override
    public int getLitLightValue() {
        return 10;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction facing = state.getValue(FACING);
        return facing == Direction.DOWN ? VS_BOTTOM : VS_SIDE[facing.get2DDataValue()];
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
