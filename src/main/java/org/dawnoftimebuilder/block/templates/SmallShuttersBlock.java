package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

public class SmallShuttersBlock extends WaterloggedBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final EnumProperty<DoTBBlockStateProperties.OpenPosition> OPEN_POSITION = DoTBBlockStateProperties.OPEN_POSITION;
    public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;
    private static final VoxelShape[] SHAPES = DoTBBlockUtils.GenerateHorizontalShapes(makeShapes());

    public SmallShuttersBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(OPEN_POSITION, DoTBBlockStateProperties.OpenPosition.CLOSED).setValue(HINGE, DoorHingeSide.LEFT).setValue(POWERED, false));
    }

    public SmallShuttersBlock(Material materialIn, float hardness, float resistance, SoundType soundType) {
        this(Properties.of(materialIn).strength(hardness, resistance).sound(soundType));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, HINGE, OPEN_POSITION, POWERED);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        int index = 0;
        int horizontalIndex = 0;
        boolean hinge = (state.getValue(HINGE) == DoorHingeSide.RIGHT);
        switch (state.getValue(OPEN_POSITION)) {
            case FULL:
                index = hinge ? 1 : 2;
            case CLOSED:
                horizontalIndex = state.getValue(FACING).get2DDataValue();
                break;
            case HALF:
                if(hinge)
                    horizontalIndex = state.getValue(FACING).getClockWise().get2DDataValue();
                else
                    horizontalIndex = state.getValue(FACING).getCounterClockWise().get2DDataValue();
                break;

        }
        return SHAPES[index + horizontalIndex * 3];
    }

    /**
     * @return Stores VoxelShape for "South" with index : <p/>
     * 0 : S Closed <p/>
     * 1 : S Fully opened to the right <p/>
     * 2 : S Fully opened to the left
     */
    private static VoxelShape[] makeShapes() {
        return new VoxelShape[]{
                Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D),
                Block.box(-13.0D, 0.0D, 13.0D, 3.0D, 16.0D, 16.0D),
                Block.box(13.0D, 0.0D, 13.0D, 29.0D, 16.0D, 16.0D)
        };
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getLevel();
        Direction direction = context.getHorizontalDirection();
        BlockPos pos = context.getClickedPos();
        int x = direction.getStepX();
        int z = direction.getStepZ();
        double onX = context.getClickLocation().x - pos.getX();
        double onZ = context.getClickLocation().z - pos.getZ();
        boolean hingeLeft = (x >= 0 || onZ >= 0.5D) && (x <= 0 || onZ <= 0.5D) && (z >= 0 || onX <= 0.5D) && (z <= 0 || onX >= 0.5D);
        boolean powered = world.hasNeighborSignal(pos) || world.hasNeighborSignal(pos.above());
        BlockState madeState = super.getStateForPlacement(context).setValue(HINGE, hingeLeft ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT).setValue(FACING, direction).setValue(POWERED, powered);
        return madeState.setValue(OPEN_POSITION, powered ? this.getOpenState(madeState, world, pos) : DoTBBlockStateProperties.OpenPosition.CLOSED);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        Direction direction = stateIn.getValue(FACING);
        Direction hingeDirection = (stateIn.getValue(HINGE) == DoorHingeSide.LEFT) ? direction.getCounterClockWise() : direction.getClockWise();
        if(facing == hingeDirection) {
            stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
            return stateIn.getValue(OPEN_POSITION) == DoTBBlockStateProperties.OpenPosition.CLOSED ? stateIn : stateIn.setValue(OPEN_POSITION, getOpenState(stateIn, worldIn, facingPos));
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(state.getValue(OPEN_POSITION).isOpen())
            state = state.setValue(OPEN_POSITION, DoTBBlockStateProperties.OpenPosition.CLOSED);
        else{
            Direction hingeDirection = (state.getValue(HINGE) == DoorHingeSide.LEFT) ? state.getValue(FACING).getCounterClockWise() : state.getValue(FACING).getClockWise();
            state = state.setValue(OPEN_POSITION, getOpenState(state, worldIn, pos.relative(hingeDirection)));
        }
        worldIn.setBlock(pos, state, 10);
        worldIn.levelEvent(player, state.getValue(OPEN_POSITION).isOpen() ? this.getOpenSound() : this.getCloseSound(), pos, 0);
        if (state.getValue(WATERLOGGED)) worldIn.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        return ActionResultType.SUCCESS;
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        boolean isPowered = worldIn.hasNeighborSignal(pos);
        if(blockIn != this && isPowered != state.getValue(POWERED)) {
            if (isPowered != state.getValue(OPEN_POSITION).isOpen()) {
                this.playSound(worldIn, pos, isPowered);
            }
            if(isPowered){
                Direction hingeDirection = (state.getValue(HINGE) == DoorHingeSide.LEFT) ? state.getValue(FACING).getCounterClockWise() : state.getValue(FACING).getClockWise();
                state = state.setValue(OPEN_POSITION, getOpenState(state, worldIn, pos.relative(hingeDirection)));
            }else
                state = state.setValue(OPEN_POSITION, DoTBBlockStateProperties.OpenPosition.CLOSED);
            worldIn.setBlock(pos, state.setValue(POWERED, isPowered), 2);
        }
    }

    protected DoTBBlockStateProperties.OpenPosition getOpenState(BlockState stateIn, IWorld worldIn, BlockPos pos){
        return worldIn.getBlockState(pos).getCollisionShape(worldIn, pos).isEmpty() ? DoTBBlockStateProperties.OpenPosition.FULL : DoTBBlockStateProperties.OpenPosition.HALF;
    }

    private void playSound(World worldIn, BlockPos pos, boolean isOpening) {
        worldIn.levelEvent(null, isOpening ? this.getOpenSound() : this.getCloseSound(), pos, 0);
    }

    private int getCloseSound() {
        return 1012;
    }

    private int getOpenSound() {
        return 1006;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        switch (mirrorIn) {
            default:
            case NONE:
                return state;
            case FRONT_BACK:
                state = rotate(state, Rotation.CLOCKWISE_180);
            case LEFT_RIGHT:
                return state.setValue(HINGE, state.getValue(HINGE) == DoorHingeSide.RIGHT ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT);
        }
    }
}