package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import org.dawnoftimebuilder.entity.ChairEntity;

import javax.annotation.Nonnull;

public class ChairBlock extends WaterloggedBlock{

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public final float pixelsYOffset;

    public ChairBlock(Properties properties, float pixelsYOffset) {
        super(properties);
        this.pixelsYOffset = pixelsYOffset;
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED,false));
    }

    public ChairBlock(Material materialIn, float hardness, float resistance, float pixelsYOffset) {
        this(Properties.of(materialIn).strength(hardness, resistance), pixelsYOffset);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    @Nonnull
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        return ChairEntity.createEntity(worldIn, pos, player, this.pixelsYOffset);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return this.rotate(state, Rotation.CLOCKWISE_180);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}
