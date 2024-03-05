package org.dawnoftimebuilder.block.precolumbian;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.SidedColumnConnectibleBlock;
import org.dawnoftimebuilder.util.DoTBUtils;

public class SerpentSculptedColumnBlock extends SidedColumnConnectibleBlock {
    private static final VoxelShape[] SHAPES = DoTBUtils.GenerateHorizontalShapes(makeShapes());

    public SerpentSculptedColumnBlock(Properties properties) {
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
        VoxelShape vs_head = Block.box(4.0D, 0.0D, 6.0D, 12.0D, 9.0D, 16.0D);
        VoxelShape vs_tail = Block.box(5.0D, 0.0D, 0.0D, 11.0D, 16.0D, 6.0D);
        return new VoxelShape[] {
                Shapes.or(vs_head, Block.box(5.0D, 0.0D, 0.0D, 11.0D, 6.0D, 6.0D)),
                Shapes.or(vs_tail, Block.box(5.0D, 10.0D, 6.0D, 11.0D, 16.0D, 15.0D)),
                Shapes.or(vs_head, vs_tail),
                vs_tail
        };
    }
}
