package org.dawnoftimebuilder.block.french;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.SidedColumnConnectibleBlock;
import org.dawnoftimebuilder.util.DoTBUtils;

public class LimestoneSidedColumnBlock extends SidedColumnConnectibleBlock {
    private static final VoxelShape[] SHAPES = DoTBUtils.GenerateHorizontalShapes(makeShapes());

    public LimestoneSidedColumnBlock(Properties properties) {
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
        VoxelShape vs_column = Shapes.or(
                Block.box(1.5D, 0.0D, 0.0D, 14.5D, 16.0D, 3.0D),
                Block.box(5.5D, 0.0D, 3.0D, 10.5D, 16.0D, 6.0D));
        return new VoxelShape[] {
                vs_column,
                Shapes.or(
                        Block.box(1.5D, 0.0D, 0.0D, 14.5D, 9.0D, 3.0D),
                        Block.box(5.5D, 0.0D, 3.0D, 10.5D, 9.0D, 6.0D),
                        Block.box(0.5D, 9.0D, 0.0D, 15.5D, 14.0D, 4.0D),
                        Block.box(4.5D, 9.0D, 4.0D, 11.5D, 14.0D, 7.0D),
                        Block.box(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 5.0D),
                        Block.box(3.5D, 14.0D, 5.0D, 12.5D, 16.0D, 8.0D)),
                Shapes.or(
                        Block.box(1.5D, 4.0D, 0.0D, 14.5D, 16.0D, 3.0D),
                        Block.box(5.5D, 4.0D, 3.0D, 10.5D, 16.0D, 6.0D),
                        Block.box(1.0D, 5.0D, 0.0D, 15.0D, 6.0D, 3.5D),
                        Block.box(5.0D, 5.0D, 3.5D, 11.0D, 6.0D, 6.5D),
                        Block.box(0.5D, 0.0D, 0.0D, 15.5D, 4.0D, 4.0D),
                        Block.box(4.5D, 0.0D, 4.0D, 11.5D, 4.0D, 7.0D)),
                vs_column
        };
    }
}
