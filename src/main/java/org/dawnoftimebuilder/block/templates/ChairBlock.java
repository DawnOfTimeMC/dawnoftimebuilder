package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.dawnoftimebuilder.entity.ChairEntity;

import javax.annotation.Nonnull;

public class ChairBlock extends WaterloggedBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public final float pixelsYOffset;

    public ChairBlock(final Properties properties, final float pixelsYOffset) {
        super(properties);
        this.pixelsYOffset = pixelsYOffset;
        this.registerDefaultState(this.defaultBlockState().setValue(ChairBlock.FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ChairBlock.FACING);
    }

    @Override
    @Nonnull
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(ChairBlock.FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public InteractionResult use(final BlockState state, final Level worldIn, final BlockPos pos, final Player player, final InteractionHand handIn, final BlockHitResult hit) {
        return ChairEntity.createEntity(worldIn, pos, player, this.pixelsYOffset);
    }

    @Override
    public BlockState rotate(final BlockState state, final Rotation rot) {
        return state.setValue(ChairBlock.FACING, rot.rotate(state.getValue(ChairBlock.FACING)));
    }

    @Override
    public BlockState mirror(final BlockState state, final Mirror mirrorIn) {
        return this.rotate(state, Rotation.CLOCKWISE_180);
    }
}
