package org.dawnoftimebuilder.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.IBlockSpecialDisplay;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;

import javax.annotation.Nonnull;

public class PaperLanternBlock extends WaterloggedBlock implements IBlockSpecialDisplay {
    private static final VoxelShape VS = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 15.0D, 12.0D);

    public PaperLanternBlock(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return VS;
    }

    @Override
    public boolean emitsLight() {
        return true;
    }
}
