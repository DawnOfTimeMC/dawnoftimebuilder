package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PillarPaneBlock extends PaneBlockDoT {
    private static final VoxelShape[] SHAPES_PILLAR = makeShapesPillar(true);
    private static final VoxelShape[] SHAPES_NO_PILLAR = makeShapesPillar(false);

    public PillarPaneBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        boolean pillar = (state.getValue(NORTH) && state.getValue(EAST))
                || (state.getValue(EAST) && state.getValue(SOUTH))
                || (state.getValue(SOUTH) && state.getValue(WEST))
                || (state.getValue(WEST) && state.getValue(NORTH));
        return pillar ? SHAPES_PILLAR[this.getAABBIndex(state)] : SHAPES_NO_PILLAR[this.getAABBIndex(state)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return this.getShape(state, world, pos, context);
    }

    private static VoxelShape[] makeShapesPillar(boolean with_pillar) {
        VoxelShape poll = with_pillar ? Block.box(4.0F, 0.0D, 4.0F, 12.0F, 16.0F, 12.0F) : Block.box(7.0F, 0.0D, 7.0F, 9.0F, 16.0F, 9.0F);
        VoxelShape side_A = Block.box(7.0F, 0.0F, 0.0D, 9.0F, 16.0F, 9.0F);
        VoxelShape side_B = Block.box(7.0F, 0.0F, 7.0F, 9.0F, 16.0F, 16.0D);
        VoxelShape side_C = Block.box(0.0D, 0.0F, 7.0F, 9.0F, 16.0F, 9.0F);
        VoxelShape side_D = Block.box(7.0F, 0.0F, 7.0F, 16.0D, 16.0F, 9.0F);
        VoxelShape angle_AD = Shapes.or(side_A, side_D);
        VoxelShape angle_BC = Shapes.or(side_B, side_C);
        VoxelShape[] all_vs = new VoxelShape[] {
                Shapes.empty(),
                side_B,
                side_C,
                angle_BC,
                side_A,
                Shapes.or(side_B, side_A),
                Shapes.or(side_C, side_A),
                Shapes.or(angle_BC, side_A),
                side_D,
                Shapes.or(side_B, side_D),
                Shapes.or(side_C, side_D),
                Shapes.or(angle_BC, side_D),
                angle_AD,
                Shapes.or(side_B, angle_AD),
                Shapes.or(side_C, angle_AD),
                Shapes.or(angle_BC, angle_AD) };

        for(int i = 0; i < 16; ++i) {
            all_vs[i] = Shapes.or(poll, all_vs[i]);
        }

        return all_vs;
    }
}
