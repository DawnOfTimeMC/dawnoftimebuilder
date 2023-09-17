package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

/**
 * @author seyro
 */
public class PoolBlock extends BasePoolBlock {
    private static final VoxelShape[] SHAPES = PoolBlock.makeShapes();

    public PoolBlock(final Properties propertiesIn) {
        super(propertiesIn, 16, 14);
    }

    @Override
    public VoxelShape getShape(final BlockState state, final BlockGetter worldIn, final BlockPos pos, final CollisionContext context) {
        int index = 0;
        if (state.getValue(BlockStateProperties.NORTH)) {
            index += 1;
        }
        if (state.getValue(BlockStateProperties.EAST)) {
            index += 2;
        }
        if (state.getValue(BlockStateProperties.SOUTH)) {
            index += 4;
        }
        if (state.getValue(BlockStateProperties.WEST)) {
            index += 8;
        }
        if (state.getValue(DoTBBlockStateProperties.HAS_PILLAR)) {
            index += 16;
        }
        return PoolBlock.SHAPES[index];
    }

    private static VoxelShape[] makeShapes() {
        final VoxelShape vs_floor = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
        final VoxelShape vs_north = Block.box(0.0D, 2.0D, 0.0D, 16.0D, 16.0D, 2.0D);
        final VoxelShape vs_east = Block.box(14.0D, 2.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        final VoxelShape vs_south = Block.box(0.0D, 2.0D, 14.0D, 16.0D, 16.0D, 16.0D);
        final VoxelShape vs_west = Block.box(0.0D, 2.0D, 0.0D, 2.0D, 16.0D, 16.0D);
        final VoxelShape vs_pillar = Block.box(4.0D, 2.0D, 4.0D, 12.0D, 16.0D, 12.0D);
        final VoxelShape[] shapes = new VoxelShape[32];
        for (int i = 0; i < 32; i++) {
            VoxelShape temp = vs_floor;
            if ((i & 1) == 0) { // Check first bit : 0 -> North true
                temp = Shapes.or(temp, vs_north);
            }
            if ((i >> 1 & 1) == 0) { // Check second bit : 0 -> East true
                temp = Shapes.or(temp, vs_east);
            }
            if ((i >> 2 & 1) == 0) { // Check third bit : 0 -> South true
                temp = Shapes.or(temp, vs_south);
            }
            if ((i >> 3 & 1) == 0) { // Check fourth bit : 0 -> West true
                temp = Shapes.or(temp, vs_west);
            }
            if ((i >> 4 & 1) == 1) { // Check fifth bit : 1 -> Pillar true
                temp = Shapes.or(temp, vs_pillar);
            }
            shapes[i] = temp;
        }
        return shapes;
    }
}