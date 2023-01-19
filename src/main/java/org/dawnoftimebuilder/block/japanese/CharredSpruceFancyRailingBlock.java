package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.templates.PaneBlockDoTB;

public class CharredSpruceFancyRailingBlock extends PaneBlockDoTB {

    private static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    private static final VoxelShape[] VS_HANGING = makeHangingShapes();

    public CharredSpruceFancyRailingBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(WATERLOGGED, false).setValue(HANGING, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.getValue(HANGING) ? VS_HANGING[this.getAABBIndex(state)] : super.getShape(state, worldIn, pos, context);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.getValue(HANGING) ? VS_HANGING[this.getAABBIndex(state)] : super.getCollisionShape(state, worldIn, pos, context);
    }

    private static VoxelShape[] makeHangingShapes() {
        VoxelShape voxelShape = Block.box(7.0D, 6.0D, 7.0D, 9.0D, 16.0D, 9.0D);
        VoxelShape voxelShape1 = Block.box(7.0D, 6.0D, 0.0D, 9.0D, 16.0D, 9.0D);
        VoxelShape voxelShape2 = Block.box(7.0D, 6.0D, 7.0D, 9.0D, 16.0D, 16.0D);
        VoxelShape voxelShape3 = Block.box(0.0D, 6.0D, 7.0D, 9.0D, 16.0D, 9.0D);
        VoxelShape voxelShape4 = Block.box(7.0D, 6.0D, 7.0D, 16.0D, 16.0D, 9.0D);
        VoxelShape voxelShape5 = VoxelShapes.or(voxelShape1, voxelShape4);
        VoxelShape voxelShape6 = VoxelShapes.or(voxelShape2, voxelShape3);
        VoxelShape[] allVoxelShape = new VoxelShape[]{VoxelShapes.empty(), voxelShape2, voxelShape3, voxelShape6, voxelShape1, VoxelShapes.or(voxelShape2, voxelShape1), VoxelShapes.or(voxelShape3, voxelShape1), VoxelShapes.or(voxelShape6, voxelShape1), voxelShape4, VoxelShapes.or(voxelShape2, voxelShape4), VoxelShapes.or(voxelShape3, voxelShape4), VoxelShapes.or(voxelShape6, voxelShape4), voxelShape5, VoxelShapes.or(voxelShape2, voxelShape5), VoxelShapes.or(voxelShape3, voxelShape5), VoxelShapes.or(voxelShape6, voxelShape5)};

        for(int i = 0; i < 16; ++i) {
            allVoxelShape[i] = VoxelShapes.or(voxelShape, allVoxelShape[i]);
        }

        return allVoxelShape;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction clickedFace = context.getClickedFace();
        BlockPos pos = context.getClickedPos();
        BlockState state = super.getStateForPlacement(context);
        if(state == null) state = this.defaultBlockState();
        return state.setValue(HANGING, clickedFace == Direction.DOWN || (clickedFace != Direction.UP && context.getClickLocation().y - pos.getY() > 0.5D));
    }

    @Override
    public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HANGING);
    }
}
