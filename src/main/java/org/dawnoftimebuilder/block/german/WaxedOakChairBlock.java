package org.dawnoftimebuilder.block.german;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.DoubleChairBlock;
import org.dawnoftimebuilder.util.DoTBUtils;

public class WaxedOakChairBlock extends DoubleChairBlock {
    private static final VoxelShape[] VS = DoTBUtils.GenerateHorizontalShapes(new VoxelShape[] {
            Shapes.or(
                    Block.box(2.0D, 0.0D, 2.0D, 14.0D, 11.0D, 14.0D),
                    Block.box(2.5D, 11.0D, 3.0D, 13.5D, 16.0D, 5.0D)),
            Block.box(2.5D, 0.0D, 3.0D, 13.5D, 10.0D, 5.0D) });

    public WaxedOakChairBlock(Properties properties, float pixelsYOffset) {
        super(properties, pixelsYOffset);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        int index = (state.getValue(HALF) == Half.TOP) ? 1 : 0;
        return VS[state.getValue(FACING).get2DDataValue() * 2 + index];
    }
}
