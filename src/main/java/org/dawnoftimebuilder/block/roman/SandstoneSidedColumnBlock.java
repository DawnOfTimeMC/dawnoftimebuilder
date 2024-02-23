package org.dawnoftimebuilder.block.roman;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.SidedColumnConnectibleBlock;
import org.dawnoftimebuilder.util.DoTBUtils;

public class SandstoneSidedColumnBlock extends SidedColumnConnectibleBlock {
    private static final VoxelShape[] SHAPES = DoTBUtils.GenerateHorizontalShapes(makeShapes());

    public SandstoneSidedColumnBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        int index = state.getValue(VERTICAL_CONNECTION).getIndex();
        return SHAPES[index + state.getValue(FACING).get2DDataValue() * 4];
    }

    /**
     * @return Stores VoxelShape for "South" with index : <p/>
     * 0 : S Lone <p/>
     * 1 : S Under <p/>
     * 2 : S Above <p/>
     * 3 : S Both <p/>
     */
    private static VoxelShape[] makeShapes() {
        VoxelShape vs_column = Block.box(4.0D, 0.0D, 0.0D, 12.0D, 16.0D, 4.0D);
        return new VoxelShape[] {
                vs_column,
                Shapes.or(
                        Block.box(4.0D, 0.0D, 0.0D, 12.0D, 8.0D, 4.0D),
                        Block.box(2.0D, 8.0D, 0.0D, 14.0D, 12.0D, 6.0D),
                        Block.box(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 8.0D)),
                Shapes.or(
                        Block.box(2.0D, 0.0D, 0.0D, 14.0D, 8.0D, 6.0D),
                        Block.box(4.0D, 8.0D, 0.0D, 12.0D, 16.0D, 4.0D)),
                vs_column
        };
    }
}
