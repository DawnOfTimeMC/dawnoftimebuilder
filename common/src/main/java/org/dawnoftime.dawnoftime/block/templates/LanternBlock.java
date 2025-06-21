package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.block.IBlockChain;
import org.dawnoftime.dawnoftime.block.templates.SpecialDisplayBlock;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static org.dawnoftime.dawnoftime.util.VoxelShapes.STONE_LANTERN_SHAPES;

public class LanternBlock extends SpecialDisplayBlock implements IBlockChain {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public LanternBlock(Properties properties, VoxelShape[] shapes) {
        super(properties.lightLevel((state) -> 15), shapes);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.DOWN));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getClickedFace());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public int getShapeIndex(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Direction facing = state.getValue(FACING);
        return switch (facing){
            default -> facing.get2DDataValue();
            case DOWN -> 4;
            case UP -> 5;
        };
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return rotate(state, Rotation.CLOCKWISE_180);
    }

    @Override
    public boolean canConnectToChainUnder(BlockState state) {
        return false;
    }

    @Override
    public boolean emitsLight() {
        return true;
    }

    @Override
    public float getDisplayScale() {
        return 0.6F;
    }
}
