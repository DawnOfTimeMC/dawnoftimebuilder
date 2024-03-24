package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.util.DoTBUtils;

public class ReliefBlock extends SidedPlaneConnectibleBlock{
    private static final VoxelShape[] SHAPES = DoTBUtils.GenerateHorizontalShapes(makeShapes());
    public ReliefBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        int index = 16 * state.getValue(FACING).get2DDataValue();
        index += 4 * state.getValue(VERTICAL_CONNECTION).getIndex();
        index += state.getValue(HORIZONTAL_CONNECTION).getIndex();
        return SHAPES[index];
    }

    /**
     * @return Stores VoxelShape for South face with index : <p/>
     * 0 : NONE & NONE <p/>
     * 1 : NONE & RIGHT -> horizontal <p/>
     * 2 : NONE & LEFT -> horizontal <p/>
     * 3 : NONE & BOTH -> horizontal <p/>
     * 4 : UNDER & NONE -> vertical <p/>
     * 5 : UNDER & RIGHT <p/>
     * 6 : UNDER & LEFT <p/>
     * 7 : UNDER & BOTH <p/>
     * 8 : ABOVE & NONE -> vertical <p/>
     * 9 : ABOVE & RIGHT <p/>
     * 10 : ABOVE & LEFT <p/>
     * 11 : ABOVE & BOTH <p/>
     * 12 : BOTH & NONE -> vertical <p/>
     * 13 : BOTH & RIGHT <p/>
     * 14 : BOTH & LEFT <p/>
     * 15 : BOTH & BOTH <p/>
     */
    private static VoxelShape[] makeShapes() {
        VoxelShape vs_center = Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 4.0D);
        VoxelShape vs_under = Block.box(4.0D, 0.0D, 0.0D, 12.0D, 4.0D, 4.0D);
        VoxelShape vs_above = Block.box(4.0D, 12.0D, 0.0D, 12.0D, 16.0D, 4.0D);
        VoxelShape vs_left = Block.box(0.0D, 4.0D, 0.0D, 4.0D, 12.0D, 4.0D);
        VoxelShape vs_right = Block.box(12.0D, 4.0D, 0.0D, 16.0D, 12.0D, 4.0D);
        VoxelShape vs_vertical = Shapes.or(vs_center, vs_under, vs_above);
        VoxelShape vs_horizontal = Shapes.or(vs_center, vs_left, vs_right);
        return new VoxelShape[] {
                vs_center,
                vs_horizontal,
                vs_horizontal,
                vs_horizontal,
                vs_vertical,
                Shapes.or(vs_center, vs_under, vs_right),
                Shapes.or(vs_center, vs_under, vs_left),
                Shapes.or(vs_horizontal, vs_under),
                vs_vertical,
                Shapes.or(vs_center, vs_above, vs_right),
                Shapes.or(vs_center, vs_above, vs_left),
                Shapes.or(vs_horizontal, vs_above),
                vs_vertical,
                Shapes.or(vs_vertical, vs_right),
                Shapes.or(vs_vertical, vs_left),
                Shapes.or(vs_vertical, vs_horizontal)
        };
    }
}
