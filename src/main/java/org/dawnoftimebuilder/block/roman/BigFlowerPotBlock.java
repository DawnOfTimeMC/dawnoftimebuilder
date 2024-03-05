package org.dawnoftimebuilder.block.roman;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;

public class BigFlowerPotBlock extends WaterloggedBlock {
    private static final VoxelShape VS = Shapes.or(
            Block.box(-0.5F, 12.0F, -0.5F, 16.5F, 17.0F, 16.5F),
            Block.box(0.5F, 0.0F, 0.5F, 15.5F, 12.0F, 15.5F));

    public BigFlowerPotBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return VS;
    }
}
