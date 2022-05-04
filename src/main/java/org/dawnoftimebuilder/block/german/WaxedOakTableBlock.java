package org.dawnoftimebuilder.block.german;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.templates.DisplayerBlock;

import javax.annotation.Nonnull;

public class WaxedOakTableBlock extends DisplayerBlock {

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;

    public WaxedOakTableBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(NORTH, false).setValue(WATERLOGGED, Boolean.FALSE).setValue(LIT, false));
    }

    @Nonnull
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = super.getStateForPlacement(context);
        World world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        return state
                .setValue(NORTH, world.getBlockState(pos.north()).getBlock().equals(this))
                .setValue(EAST, world.getBlockState(pos.east()).getBlock().equals(this))
                .setValue(SOUTH, world.getBlockState(pos.south()).getBlock().equals(this))
                .setValue(WEST, world.getBlockState(pos.west()).getBlock().equals(this));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<net.minecraft.block.Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public double getDisplayerX(BlockState state) {
        return 0;
    }

    @Override
    public double getDisplayerY(BlockState state) {
        return 0;
    }

    @Override
    public double getDisplayerZ(BlockState state) {
        return 0;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(facing.getAxis().isHorizontal()){
            BlockState state = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
            boolean canConnect = facingState.getBlock().equals(this);
            switch(facing){
                default:
                case NORTH:
                    return state.setValue(NORTH, canConnect);
                case WEST:
                    return state.setValue(WEST, canConnect);
                case SOUTH:
                    return state.setValue(SOUTH, canConnect);
                case EAST:
                    return state.setValue(EAST, canConnect);
            }
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }
}