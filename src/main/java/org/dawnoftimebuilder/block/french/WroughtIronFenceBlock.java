package org.dawnoftimebuilder.block.french;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.templates.PlateBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

public class WroughtIronFenceBlock extends PlateBlock {

    private static final BooleanProperty UP = BlockStateProperties.UP;
    private static final VoxelShape[] SHAPES_UP = DoTBBlockUtils.GenerateHorizontalShapes(makeShapes(true));
    private static final VoxelShape[] SHAPES_FULL = DoTBBlockUtils.GenerateHorizontalShapes(makeShapes(false));

    public WroughtIronFenceBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(PlateBlock.FACING, Direction.NORTH)
                .setValue(PlateBlock.SHAPE, StairsShape.STRAIGHT)
                .setValue(UP, true)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        int index = (state.getValue(FACING).get2DDataValue() + 2) % 4;
        index *= 3;
        switch (state.getValue(SHAPE)) {
            default:
            case OUTER_LEFT:
                break;
            case OUTER_RIGHT:
                index += 3;
                break;
            case STRAIGHT:
                index += 1;
                break;
            case INNER_LEFT:
                index += 2;
                break;
            case INNER_RIGHT:
                index += 5;
                break;
        }
        index %= 12;
        return state.getValue(UP) ? SHAPES_UP[index] : SHAPES_FULL[index];
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * 0 : NW Outer <p/>
     * 1 : N Default <p/>
     * 2 : NW Inner <p/>
     * 3 : NE Outer <p/>
     * 4 : N Default <p/>
     * 5 : NE Inner <p/>
     * 6 : SE Outer <p/>
     * 7 : S Default <p/>
     * 8 : SE Inner <p/>
     * 9 : SW Outer <p/>
     * 10 : S Default <p/>
     * 11 : SW Inner <p/>
     */
    private static VoxelShape[] makeShapes(boolean up) {
        int size_flat = up ? 8 : 16;
        int size_corner = up ? 10 : 16;
        VoxelShape vs_north_flat = Block.box(0.0D, 0.0D, 0.5D, 16.0D, size_flat, 2.5D);
        VoxelShape vs_east_flat = Block.box(13.5D, 0.0D, 0.0D, 15.5D, size_flat, 16.0D);
        VoxelShape vs_south_flat = Block.box(0.0D, 0.0D, 13.5D, 16.0D, size_flat, 15.5D);
        VoxelShape vs_west_flat = Block.box(0.5D, 0.0D, 0.0D, 2.5D, size_flat, 16.0D);
        VoxelShape vs_nw_corner = Block.box(0.0D, 0.0D, 0.0D, 3.0D, size_corner, 3.0D);
        VoxelShape vs_ne_corner = Block.box(13.0D, 0.0D, 0.0D, 16.0D, size_corner, 3.0D);
        VoxelShape vs_se_corner = Block.box(13.0D, 0.0D, 13.0D, 16.0D, size_corner, 16.0D);
        VoxelShape vs_sw_corner = Block.box(0.0D, 0.0D, 13.0D, 3.0D, size_corner, 16.0D);
        return new VoxelShape[]{
                vs_nw_corner,
                vs_north_flat,
                VoxelShapes.or(vs_north_flat, vs_west_flat, vs_nw_corner),
                vs_ne_corner,
                vs_east_flat,
                VoxelShapes.or(vs_east_flat, vs_north_flat, vs_ne_corner),
                vs_se_corner,
                vs_south_flat,
                VoxelShapes.or(vs_south_flat, vs_east_flat, vs_se_corner),
                vs_sw_corner,
                vs_west_flat,
                VoxelShapes.or(vs_west_flat, vs_south_flat, vs_sw_corner),
        };
    }

    @Override
    public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(UP);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return super.getStateForPlacement(context).setValue(UP, !context.getLevel().getBlockState(context.getClickedPos().above()).getBlock().is(this));
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(facing == Direction.UP){
            stateIn = stateIn.setValue(UP, !facingState.getBlock().is(this));
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }
}
