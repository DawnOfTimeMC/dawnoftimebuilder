package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBUtils;

public class SmallShutterBlock extends WaterloggedBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final EnumProperty<DoTBBlockStateProperties.OpenPosition> OPEN_POSITION = DoTBBlockStateProperties.OPEN_POSITION;
    public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;
    private static final VoxelShape[] SHAPES = DoTBUtils.GenerateHorizontalShapes(SmallShutterBlock.makeShapes());

    public SmallShutterBlock(final Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.defaultBlockState().setValue(SmallShutterBlock.OPEN_POSITION, DoTBBlockStateProperties.OpenPosition.CLOSED).setValue(SmallShutterBlock.HINGE, DoorHingeSide.LEFT).setValue(SmallShutterBlock.POWERED, false));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SmallShutterBlock.FACING, SmallShutterBlock.HINGE, SmallShutterBlock.OPEN_POSITION, SmallShutterBlock.POWERED);
    }

    @Override
    public VoxelShape getShape(final BlockState state, final BlockGetter worldIn, final BlockPos pos, final CollisionContext context) {
        int index = 0;
        int horizontalIndex = 0;
        final boolean hinge = state.getValue(SmallShutterBlock.HINGE) == DoorHingeSide.RIGHT;
        switch (state.getValue(SmallShutterBlock.OPEN_POSITION)) {
            case FULL:
                index = hinge ? 1 : 2;
            case CLOSED:
                horizontalIndex = state.getValue(SmallShutterBlock.FACING).get2DDataValue();
                break;
            case HALF:
                if (hinge) {
                    horizontalIndex = state.getValue(SmallShutterBlock.FACING).getClockWise().get2DDataValue();
                } else {
                    horizontalIndex = state.getValue(SmallShutterBlock.FACING).getCounterClockWise().get2DDataValue();
                }
                break;

        }
        return SmallShutterBlock.SHAPES[index + horizontalIndex * 3];
    }

    /**
     * @return Stores VoxelShape for "South" with index :
     * <p/>
     * 0 : S Closed
     * <p/>
     * 1 : S Fully opened to the right
     * <p/>
     * 2 : S Fully opened to the left
     */
    private static VoxelShape[] makeShapes() {
        return new VoxelShape[]{Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D), Block.box(-13.0D, 0.0D, 13.0D, 3.0D, 16.0D, 16.0D), Block.box(13.0D, 0.0D, 13.0D, 29.0D, 16.0D, 16.0D)};
    }

    @Override
    public PushReaction getPistonPushReaction(final BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        final Level world = context.getLevel();
        final Direction direction = context.getHorizontalDirection();
        final BlockPos pos = context.getClickedPos();
        final int x = direction.getStepX();
        final int z = direction.getStepZ();
        final double onX = context.getClickLocation().x - pos.getX();
        final double onZ = context.getClickLocation().z - pos.getZ();
        final boolean hingeLeft = (x >= 0 || onZ >= 0.5D) && (x <= 0 || onZ <= 0.5D) && (z >= 0 || onX <= 0.5D) && (z <= 0 || onX >= 0.5D);
        final boolean powered = world.hasNeighborSignal(pos) || world.hasNeighborSignal(pos.above());
        final BlockState madeState = super.getStateForPlacement(context).setValue(SmallShutterBlock.HINGE, hingeLeft ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT).setValue(SmallShutterBlock.FACING, direction)
                .setValue(SmallShutterBlock.POWERED, powered);
        return madeState.setValue(SmallShutterBlock.OPEN_POSITION, powered ? this.getOpenState(madeState, world, pos) : DoTBBlockStateProperties.OpenPosition.CLOSED);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, final Direction facing, final BlockState facingState, final LevelAccessor worldIn, final BlockPos currentPos, final BlockPos facingPos) {
        final Direction direction = stateIn.getValue(SmallShutterBlock.FACING);
        final Direction hingeDirection = stateIn.getValue(SmallShutterBlock.HINGE) == DoorHingeSide.LEFT ? direction.getCounterClockWise() : direction.getClockWise();
        if (facing == hingeDirection) {
            stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
            return stateIn.getValue(SmallShutterBlock.OPEN_POSITION) == DoTBBlockStateProperties.OpenPosition.CLOSED ? stateIn : stateIn.setValue(SmallShutterBlock.OPEN_POSITION, this.getOpenState(stateIn, worldIn, facingPos));
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public InteractionResult use(BlockState state, final Level worldIn, final BlockPos pos, final Player player, final InteractionHand handIn, final BlockHitResult hit) {
        if (state.getValue(SmallShutterBlock.OPEN_POSITION).isOpen()) {
            state = state.setValue(SmallShutterBlock.OPEN_POSITION, DoTBBlockStateProperties.OpenPosition.CLOSED);
        } else {
            final Direction hingeDirection = state.getValue(SmallShutterBlock.HINGE) == DoorHingeSide.LEFT ? state.getValue(SmallShutterBlock.FACING).getCounterClockWise() : state.getValue(SmallShutterBlock.FACING).getClockWise();
            state = state.setValue(SmallShutterBlock.OPEN_POSITION, this.getOpenState(state, worldIn, pos.relative(hingeDirection)));
        }
        worldIn.setBlock(pos, state, 10);
        worldIn.levelEvent(player, state.getValue(SmallShutterBlock.OPEN_POSITION).isOpen() ? this.getOpenSound() : this.getCloseSound(), pos, 0);
        if (state.getValue(WaterloggedBlock.WATERLOGGED)) {
            worldIn.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void neighborChanged(BlockState state, final Level worldIn, final BlockPos pos, final Block blockIn, final BlockPos fromPos, final boolean isMoving) {
        final boolean isPowered = worldIn.hasNeighborSignal(pos);
        if (blockIn != this && isPowered != state.getValue(SmallShutterBlock.POWERED)) {
            if (isPowered != state.getValue(SmallShutterBlock.OPEN_POSITION).isOpen()) {
                this.playSound(worldIn, pos, isPowered);
            }
            if (isPowered) {
                final Direction hingeDirection = state.getValue(SmallShutterBlock.HINGE) == DoorHingeSide.LEFT ? state.getValue(SmallShutterBlock.FACING).getCounterClockWise() : state.getValue(SmallShutterBlock.FACING).getClockWise();
                state = state.setValue(SmallShutterBlock.OPEN_POSITION, this.getOpenState(state, worldIn, pos.relative(hingeDirection)));
            } else {
                state = state.setValue(SmallShutterBlock.OPEN_POSITION, DoTBBlockStateProperties.OpenPosition.CLOSED);
            }
            worldIn.setBlock(pos, state.setValue(SmallShutterBlock.POWERED, isPowered), 2);
        }
    }

    protected DoTBBlockStateProperties.OpenPosition getOpenState(final BlockState stateIn, final LevelAccessor worldIn, final BlockPos pos) {
        return worldIn.getBlockState(pos).getCollisionShape(worldIn, pos).isEmpty() ? DoTBBlockStateProperties.OpenPosition.FULL : DoTBBlockStateProperties.OpenPosition.HALF;
    }

    private void playSound(final Level worldIn, final BlockPos pos, final boolean isOpening) {
        worldIn.levelEvent(null, isOpening ? this.getOpenSound() : this.getCloseSound(), pos, 0);
    }

    private int getCloseSound() {
        return 1012;
    }

    private int getOpenSound() {
        return 1006;
    }

    @Override
    public BlockState rotate(final BlockState state, final Rotation rot) {
        return state.setValue(SmallShutterBlock.FACING, rot.rotate(state.getValue(SmallShutterBlock.FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, final Mirror mirrorIn) {
        switch (mirrorIn) {
            default:
            case NONE:
                return state;
            case FRONT_BACK:
                state = this.rotate(state, Rotation.CLOCKWISE_180);
            case LEFT_RIGHT:
                return state.setValue(SmallShutterBlock.HINGE, state.getValue(SmallShutterBlock.HINGE) == DoorHingeSide.RIGHT ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT);
        }
    }

    /**
     * Light corrections methods
     */
    @Override
    public int getLightBlock(final BlockState p_200011_1_In, final BlockGetter p_200011_2_In, final BlockPos p_200011_3_In) {
        return 1;
    }

    @Override
    public boolean useShapeForLightOcclusion(final BlockState p_220074_1_In) {
        return false;
    }

    @Override
    public int getLightEmission(final BlockState stateIn, final BlockGetter worldIn, final BlockPos posIn) {
        return 1;
    }

    @Override
    public VoxelShape getOcclusionShape(final BlockState p_196247_1_In, final BlockGetter p_196247_2_In, final BlockPos p_196247_3_In) {
        return Shapes.empty();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getShadeBrightness(final BlockState p_220080_1_, final BlockGetter p_220080_2_, final BlockPos p_220080_3_) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(final BlockState p_200123_1_In, final BlockGetter p_200123_2_In, final BlockPos p_200123_3_In) {
        return true;
    }
}