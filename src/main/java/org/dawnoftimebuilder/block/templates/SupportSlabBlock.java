package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.IBlockPillar;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

public class SupportSlabBlock extends WaterloggedBlock {
    private static final VoxelShape VS = Block.box(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape VS_FOUR_PX = Shapes.or(VS, Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D));
    private static final VoxelShape VS_EIGHT_PX = Shapes.or(VS, Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D));
    private static final VoxelShape VS_TEN_PX = Shapes.or(VS, Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D));
    private static final EnumProperty<DoTBBlockStateProperties.PillarConnection> PILLAR_CONNECTION = DoTBBlockStateProperties.PILLAR_CONNECTION;

    public SupportSlabBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(PILLAR_CONNECTION, DoTBBlockStateProperties.PillarConnection.NOTHING));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PILLAR_CONNECTION);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch(state.getValue(PILLAR_CONNECTION)) {
            case FOUR_PX:
                return VS_FOUR_PX;
            case EIGHT_PX:
                return VS_EIGHT_PX;
            case TEN_PX:
                return VS_TEN_PX;
            default:
                return VS;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(PILLAR_CONNECTION, IBlockPillar.getPillarConnectionAbove(context.getLevel(), context.getClickedPos().below()));
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        return facing == Direction.DOWN ? stateIn.setValue(PILLAR_CONNECTION, IBlockPillar.getPillarConnectionAbove(worldIn, currentPos.below())) : stateIn;
    }
}