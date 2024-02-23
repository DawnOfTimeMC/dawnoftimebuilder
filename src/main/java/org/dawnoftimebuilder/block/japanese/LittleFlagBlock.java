package org.dawnoftimebuilder.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.PaneBlockDoTB;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

public class LittleFlagBlock extends PaneBlockDoTB {
    public static final BooleanProperty AXIS_Y = DoTBBlockStateProperties.AXIS_Y;
    private final VoxelShape[] VS_PILLAR = this.makePillarShapes(this.shapeByIndex);

    public LittleFlagBlock(DyeColor dyeColor, Properties properties) {
        super(dyeColor, properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS_Y, true).setValue(NORTH, true).setValue(WEST, true).setValue(SOUTH, true).setValue(EAST, true).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AXIS_Y);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState newState = super.getStateForPlacement(context);
        if(newState == null)
            newState = this.defaultBlockState();
        if(this.hasNoConnection(newState))
            newState = this.defaultBlockState().setValue(WATERLOGGED, newState.getValue(WATERLOGGED));
        return newState.setValue(AXIS_Y, context.getClickedFace().getAxis().isVertical());
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(stateIn.getValue(WATERLOGGED))
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        if(facing.getAxis().isHorizontal()) {
            if(this.hasAllConnections(stateIn)) {
                //We must check connections on all sides
                BlockState northState = worldIn.getBlockState(currentPos.north());
                BlockState westState = worldIn.getBlockState(currentPos.west());
                BlockState southState = worldIn.getBlockState(currentPos.south());
                BlockState eastState = worldIn.getBlockState(currentPos.east());
                return stateIn
                        .setValue(NORTH, this.canAttachPane(northState, northState.isFaceSturdy(worldIn, currentPos.north(), Direction.SOUTH)))
                        .setValue(WEST, this.canAttachPane(westState, westState.isFaceSturdy(worldIn, currentPos.west(), Direction.EAST)))
                        .setValue(SOUTH, this.canAttachPane(southState, southState.isFaceSturdy(worldIn, currentPos.south(), Direction.NORTH)))
                        .setValue(EAST, this.canAttachPane(eastState, eastState.isFaceSturdy(worldIn, currentPos.east(), Direction.WEST)));
            } else {
                stateIn = stateIn.setValue(PROPERTY_BY_DIRECTION.get(facing), this.canAttachPane(facingState, facingState.isFaceSturdy(worldIn, facingPos, facing.getOpposite())));
                if(this.hasNoConnection(stateIn))
                    return stateIn.setValue(NORTH, true).setValue(WEST, true).setValue(SOUTH, true).setValue(EAST, true);
            }
        }
        return stateIn;
    }

    private boolean hasNoConnection(BlockState state) {
        return !state.getValue(NORTH) && !state.getValue(WEST) && !state.getValue(SOUTH) && !state.getValue(EAST);
    }

    private boolean hasAllConnections(BlockState state) {
        return state.getValue(NORTH) && state.getValue(WEST) && state.getValue(SOUTH) && state.getValue(EAST);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if(state.getValue(AXIS_Y))
            return VS_PILLAR[this.getAABBIndex(state)];
        return super.getShape(state, worldIn, pos, context);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    /**
     * @return a copy of FourWayBlock shapes merge with a center pillar of 4*4 pixels
     */
    private VoxelShape[] makePillarShapes(VoxelShape[] shapes) {
        VoxelShape[] shapesPillar = new VoxelShape[16];
        VoxelShape vsPillar = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
        for(int index = 0; index < 16; index++) {
            shapesPillar[index] = Shapes.or(
                    shapes[index],
                    vsPillar);
        }
        return shapesPillar;
    }
}
