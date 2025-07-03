package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.util.Utils;

public class CenteredDoorBlock extends DoorBlockDoT {
    //VoxelShapes are slightly larger than paper_walls so that it can easily be right-clicked when opened.
    private static final VoxelShape[] VS_NORTH = Utils.generateHorizontalShapes(new VoxelShape[] { Block.box(0.0D, 0.0D, 6.99D, 16.0D, 16.0D, 8.01D) });
    private static final VoxelShape[] VS_NORTH_OPEN = Utils.generateHorizontalShapes(new VoxelShape[] { Block.box(-12.0D, 0.0D, 6.99D, 4.0D, 16.0D, 8.01) });

    public CenteredDoorBlock(Properties properties, BlockSetType blockSetType) {
        super(properties, blockSetType);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return state.getValue(OPEN) ? VS_NORTH_OPEN[state.getValue(HINGE) == DoorHingeSide.LEFT ? state.getValue(FACING).getOpposite().get2DDataValue() : state.getValue(FACING).get2DDataValue()] : VS_NORTH[state.getValue(FACING).get2DDataValue()];
    }
}
