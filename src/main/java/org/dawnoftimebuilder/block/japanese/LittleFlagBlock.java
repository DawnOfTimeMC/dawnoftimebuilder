package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import org.dawnoftimebuilder.block.templates.PaneBlockDoTB;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

public class LittleFlagBlock extends PaneBlockDoTB {

    public static final BooleanProperty AXIS_Y = DoTBBlockStateProperties.AXIS_Y;
    private final VoxelShape[] VS_PILLAR = this.makePillarShapes(this.shapeByIndex);

    public LittleFlagBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS_Y, true).setValue(NORTH, true).setValue(WEST, true).setValue(SOUTH, true).setValue(EAST, true).setValue(WATERLOGGED,false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<net.minecraft.block.Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AXIS_Y);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState newState = super.getStateForPlacement(context);
        if(newState == null) newState = this.defaultBlockState();
        if(this.hasNoConnection(newState)) newState = this.defaultBlockState().setValue(WATERLOGGED, newState.getValue(WATERLOGGED));
        return newState.setValue(AXIS_Y, context.getClickedFace().getAxis().isVertical());
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(stateIn.getValue(WATERLOGGED)) worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        if(facing.getAxis().isHorizontal()){
            if(this.hasAllConnections(stateIn)){
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
            }else{
                stateIn = stateIn.setValue(PROPERTY_BY_DIRECTION.get(facing), this.canAttachPane(facingState, facingState.isFaceSturdy(worldIn, facingPos, facing.getOpposite())));
                if(this.hasNoConnection(stateIn)) return stateIn.setValue(NORTH, true).setValue(WEST, true).setValue(SOUTH, true).setValue(EAST, true);
            }
        }
        return stateIn;
    }

    private boolean hasNoConnection(BlockState state){
        return !state.getValue(NORTH) && !state.getValue(WEST) && !state.getValue(SOUTH) && !state.getValue(EAST);
    }

    private boolean hasAllConnections(BlockState state){
        return state.getValue(NORTH) && state.getValue(WEST) && state.getValue(SOUTH) && state.getValue(EAST);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if(state.getValue(AXIS_Y)) return VS_PILLAR[this.getAABBIndex(state)];
        return super.getShape(state, worldIn, pos, context);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    /**
     * @return a copy of FourWayBlock shapes merge with a center pillar of 4*4 pixels
     */
    private VoxelShape[] makePillarShapes(VoxelShape[] shapes) {
        VoxelShape[] shapesPillar = new VoxelShape[16];
        VoxelShape vsPillar = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
        for(int index = 0; index < 16; index++){
            shapesPillar[index] = VoxelShapes.or(
                    shapes[index],
                    vsPillar);
        }
        return shapesPillar;
    }
}
