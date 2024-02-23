package org.dawnoftimebuilder.block.german;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.PlateBlock;
import org.dawnoftimebuilder.util.DoTBUtils;

public class WaxedOakBalusterBlock extends PlateBlock {
    private static final VoxelShape[] SHAPES = DoTBUtils.GenerateHorizontalShapes(makeShapes());

    public WaxedOakBalusterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        int index = (state.getValue(FACING).get2DDataValue() + 2) % 4;
        index *= 3;
        switch(state.getValue(SHAPE)) {
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
        return SHAPES[index];
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
    private static VoxelShape[] makeShapes() {
        VoxelShape vs_north_flat = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
        VoxelShape vs_east_flat = Block.box(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vs_south_flat = Block.box(0.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vs_west_flat = Block.box(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 16.0D);
        VoxelShape vs_nw_corner = Block.box(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 4.0D);
        VoxelShape vs_ne_corner = Block.box(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
        VoxelShape vs_se_corner = Block.box(12.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vs_sw_corner = Block.box(0.0D, 0.0D, 12.0D, 4.0D, 16.0D, 16.0D);
        return new VoxelShape[] {
                vs_nw_corner,
                vs_north_flat,
                Shapes.or(vs_north_flat, vs_west_flat),
                vs_ne_corner,
                vs_east_flat,
                Shapes.or(vs_east_flat, vs_north_flat),
                vs_se_corner,
                vs_south_flat,
                Shapes.or(vs_south_flat, vs_east_flat),
                vs_sw_corner,
                vs_west_flat,
                Shapes.or(vs_west_flat, vs_south_flat),
        };
    }
}
