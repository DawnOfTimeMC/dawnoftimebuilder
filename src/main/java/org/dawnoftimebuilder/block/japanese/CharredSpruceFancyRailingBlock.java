package org.dawnoftimebuilder.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.PaneBlockDoTB;

public class CharredSpruceFancyRailingBlock extends PaneBlockDoTB {

    private static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    private static final VoxelShape[] VS_HANGING = makeHangingShapes();

    public CharredSpruceFancyRailingBlock(DyeColor dyeColor, Properties properties) {
        super(dyeColor, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(WATERLOGGED, false).setValue(HANGING, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return state.getValue(HANGING) ? VS_HANGING[this.getAABBIndex(state)] : super.getShape(state, worldIn, pos, context);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return state.getValue(HANGING) ? VS_HANGING[this.getAABBIndex(state)] : super.getCollisionShape(state, worldIn, pos, context);
    }

    private static VoxelShape[] makeHangingShapes() {
        VoxelShape voxelShape = Block.box(7.0D, 6.0D, 7.0D, 9.0D, 16.0D, 9.0D);
        VoxelShape voxelShape1 = Block.box(7.0D, 6.0D, 0.0D, 9.0D, 16.0D, 9.0D);
        VoxelShape voxelShape2 = Block.box(7.0D, 6.0D, 7.0D, 9.0D, 16.0D, 16.0D);
        VoxelShape voxelShape3 = Block.box(0.0D, 6.0D, 7.0D, 9.0D, 16.0D, 9.0D);
        VoxelShape voxelShape4 = Block.box(7.0D, 6.0D, 7.0D, 16.0D, 16.0D, 9.0D);
        VoxelShape voxelShape5 = Shapes.or(voxelShape1, voxelShape4);
        VoxelShape voxelShape6 = Shapes.or(voxelShape2, voxelShape3);
        VoxelShape[] allVoxelShape = new VoxelShape[]{Shapes.empty(), voxelShape2, voxelShape3, voxelShape6, voxelShape1, Shapes.or(voxelShape2, voxelShape1), Shapes.or(voxelShape3, voxelShape1), Shapes.or(voxelShape6, voxelShape1), voxelShape4, Shapes.or(voxelShape2, voxelShape4), Shapes.or(voxelShape3, voxelShape4), Shapes.or(voxelShape6, voxelShape4), voxelShape5, Shapes.or(voxelShape2, voxelShape5), Shapes.or(voxelShape3, voxelShape5), Shapes.or(voxelShape6, voxelShape5)};

        for(int i = 0; i < 16; ++i) {
            allVoxelShape[i] = Shapes.or(voxelShape, allVoxelShape[i]);
        }

        return allVoxelShape;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction clickedFace = context.getClickedFace();
        BlockPos pos = context.getClickedPos();
        BlockState state = super.getStateForPlacement(context);
        if(state == null) state = this.defaultBlockState();
        return state.setValue(HANGING, clickedFace == Direction.DOWN || (clickedFace != Direction.UP && context.getClickLocation().y - pos.getY() > 0.5D));
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HANGING);
    }
}
