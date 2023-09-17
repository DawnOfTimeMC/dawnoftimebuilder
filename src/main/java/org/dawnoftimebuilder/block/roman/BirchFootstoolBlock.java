package org.dawnoftimebuilder.block.roman;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.ChairBlock;

import javax.annotation.Nonnull;

public class BirchFootstoolBlock extends ChairBlock {

    private static final VoxelShape X_AXIS_VS = Shapes.or(
            Block.box(4.0F, 0.0F, 2.0F, 12.0F, 3.0F, 14.0F),
            Block.box(2.0F, 3.0F, 0.0F, 14.0F, 9.0F, 16.0F));
    private static final VoxelShape Z_AXIS_VS = Shapes.or(
            Block.box(2.0F, 0.0F, 4.0F, 14.0F, 3.0F, 12.0F),
            Block.box(0.0F, 3.0F, 2.0F, 16.0F, 9.0F, 14.0F));

    public BirchFootstoolBlock(Properties properties, float pixelsYOffset) {
        super(properties, pixelsYOffset);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return (state.getValue(FACING).getAxis() == Direction.Axis.X) ? X_AXIS_VS : Z_AXIS_VS;
    }
}
