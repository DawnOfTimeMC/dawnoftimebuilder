package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nonnull;

public class CandlestickBlock extends CandleLampBlock {
    private static final VoxelShape VS_BOTTOM = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 15.0D, 11.0D);
    private static final VoxelShape[] VS_SIDE = DoTBUtils.GenerateHorizontalShapes(new VoxelShape[] { Block.box(4.0D, 1.0D, 0.0D, 12.0D, 15.0D, 14.0D) });
    public static final DirectionProperty FACING = BlockStateProperties.FACING_HOPPER;
    private static final BooleanProperty LIT = BlockStateProperties.LIT;

    public CandlestickBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(FACING, Direction.DOWN).setValue(LIT, false));
    }

    @Nonnull
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getClickedFace();
        return super.getStateForPlacement(context).setValue(FACING, facing == Direction.UP ? Direction.DOWN : facing);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        this.animateLitCandle(stateIn, worldIn, pos, 0.5D, 1.0D, 0.5D);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
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
