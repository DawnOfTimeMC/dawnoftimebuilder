package org.dawnoftimebuilder.block.german;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.SidedWindowBlock;

public class LatticeStoneBricksWindowBlock extends SidedWindowBlock {
    private static final VoxelShape NORTH_VS = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 6.0D);
    private static final VoxelShape EAST_VS = Block.box(10.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape SOUTH_VS = Block.box(0.0D, 0.0D, 10.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape WEST_VS = Block.box(0.0D, 0.0D, 0.0D, 6.0D, 16.0D, 16.0D);
    private static final VoxelShape X_VS = Block.box(5.0D, 0.0D, 0.0D, 11.0D, 16.0D, 16.0D);
    private static final VoxelShape Z_VS = Block.box(0.0D, 0.0D, 5.0D, 16.0D, 16.0D, 11.0D);

    public LatticeStoneBricksWindowBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch(state.getValue(SIDED_WINDOW)) {
            default:
            case NORTH:
                return NORTH_VS;
            case EAST:
                return EAST_VS;
            case SOUTH:
                return SOUTH_VS;
            case WEST:
                return WEST_VS;
            case AXIS_X:
                return X_VS;
            case AXIS_Z:
                return Z_VS;
        }
    }
}
