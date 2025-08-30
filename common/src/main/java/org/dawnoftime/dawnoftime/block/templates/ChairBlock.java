package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.entity.ChairEntity;

public class ChairBlock extends WaterloggedHorizontalBlock {
    public final float pixelsYOffset;

    public ChairBlock(final Properties properties, final float pixelsYOffset, VoxelShape[] shapes) {
        super(properties, shapes);
        this.pixelsYOffset = pixelsYOffset;
    }

    @Override
    public InteractionResult use(final BlockState state, final Level worldIn, final BlockPos pos, final Player player, final InteractionHand handIn, final BlockHitResult hit) {
        return ChairEntity.createEntity(worldIn, pos, player, state.getValue(FACING), this.pixelsYOffset);
    }
}
