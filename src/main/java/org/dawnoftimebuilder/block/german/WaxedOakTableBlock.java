package org.dawnoftimebuilder.block.german;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import org.dawnoftimebuilder.block.templates.MultiblockTableBlock;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

public class WaxedOakTableBlock extends MultiblockTableBlock {

    private static final VoxelShape[] BOT_SHAPES = makeShapes();
    private static final VoxelShape TOP_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);

    public WaxedOakTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShapeByIndex(int index, boolean top) {
        return top ? TOP_SHAPE : BOT_SHAPES[index];
    }
    private static VoxelShape[] makeShapes() {
        // South - West - North - East:
        VoxelShape[] vs_side = DoTBBlockUtils.GenerateHorizontalShapes(new VoxelShape[]{VoxelShapes.or(Block.box(1.0D, 1.0D, 13.5D, 15.0D, 3.0D, 14.5D), Block.box(1.0D, 12.0D, 13.5D, 15.0D, 16.0D, 14.5D))});
        // SW - NW - NE - SE:
        VoxelShape[] vs_pillar = DoTBBlockUtils.GenerateHorizontalShapes(new VoxelShape[]{Block.box(0.5D, 0.0D, 12.5D, 3.5D, 16.0D, 15.5D)});
        // W - N - E - S
        VoxelShape[] vs_pillar_left = DoTBBlockUtils.GenerateHorizontalShapes(new VoxelShape[]{Block.box(0.0D, 0.0D, 13.0D, 1.0D, 16.0D, 15.0D)});
        // S - W - N - E
        VoxelShape[] vs_pillar_right = DoTBBlockUtils.GenerateHorizontalShapes(new VoxelShape[]{Block.box(1.0D, 0.0D, 15.0D, 3.0D, 16.0D, 16.0D)});
        return new VoxelShape[]{
                VoxelShapes.empty(),
                VoxelShapes.or(vs_side[0], vs_pillar_left[0], vs_pillar_right[3]),
                VoxelShapes.or(vs_side[1], vs_pillar_left[1], vs_pillar_right[0]),
                VoxelShapes.or(vs_side[0], vs_side[1], vs_pillar[0], vs_pillar_left[1], vs_pillar_right[3]),
                VoxelShapes.or(vs_side[2], vs_pillar_left[2], vs_pillar_right[1]),
                VoxelShapes.or(vs_side[0], vs_side[2], vs_pillar_left[0], vs_pillar_right[3], vs_pillar_left[2], vs_pillar_right[1]),
                VoxelShapes.or(vs_side[1], vs_side[2], vs_pillar[1], vs_pillar_left[2], vs_pillar_right[0]),
                VoxelShapes.or(vs_side[0], vs_side[1], vs_side[2], vs_pillar[0], vs_pillar[1], vs_pillar_left[2], vs_pillar_right[3]),
                VoxelShapes.or(vs_side[3], vs_pillar_left[3], vs_pillar_right[2]),
                VoxelShapes.or(vs_side[0], vs_side[3], vs_pillar[3], vs_pillar_left[0], vs_pillar_right[2]),
                VoxelShapes.or(vs_side[1], vs_side[3], vs_pillar_left[1], vs_pillar_right[0], vs_pillar_left[3], vs_pillar_right[2]),
                VoxelShapes.or(vs_side[0], vs_side[1], vs_side[3], vs_pillar[0], vs_pillar[3], vs_pillar_left[1], vs_pillar_right[2]),
                VoxelShapes.or(vs_side[2], vs_side[3], vs_pillar[2], vs_pillar_right[1], vs_pillar_left[3]),
                VoxelShapes.or(vs_side[0], vs_side[2], vs_side[3], vs_pillar[2], vs_pillar[3], vs_pillar_left[0], vs_pillar_right[1]),
                VoxelShapes.or(vs_side[1],vs_side[2], vs_side[3], vs_pillar[1], vs_pillar[2], vs_pillar_left[3], vs_pillar_right[0]),
                VoxelShapes.or(vs_side[0], vs_side[1], vs_side[2], vs_side[3], vs_pillar[0], vs_pillar[1], vs_pillar[2], vs_pillar[3]),
        };
    }

    @Override
    public double getDisplayerX(BlockState state){
        return 0.1875D;
    }

    @Override
    public double getDisplayerY(BlockState state){
        return 0.125D;
    }

    @Override
    public double getDisplayerZ(BlockState state){
        return 0.1875D;
    }
}