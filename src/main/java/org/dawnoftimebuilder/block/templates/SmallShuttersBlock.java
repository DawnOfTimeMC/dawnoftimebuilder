package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
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
        this.setDefaultState(this.getStateContainer().getBaseState().with(OPEN_POSITION, DoTBBlockStateProperties.OpenPosition.CLOSED).with(HINGE, DoorHingeSide.LEFT).with(POWERED, false));
    }

    public SmallShuttersBlock(Material materialIn, float hardness, float resistance, SoundType soundType) {
        this(Properties.of(materialIn).strength(hardness, resistance).sound(soundType));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(FACING, HINGE, OPEN_POSITION, POWERED);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        int index = 0;
        int horizontalIndex = 0;
        boolean hinge = (state.get(HINGE) == DoorHingeSide.RIGHT);
        switch (state.get(OPEN_POSITION)) {
            case FULL:
                index = hinge ? 1 : 2;
            case CLOSED:
                horizontalIndex = state.get(FACING).getHorizontalIndex();
                break;
            case HALF:
                if(hinge)
                    horizontalIndex = state.get(FACING).rotateY().getHorizontalIndex();
                else
                    horizontalIndex = state.get(FACING).rotateYCCW().getHorizontalIndex();
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
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getLevel();
        Direction direction = context.getPlacementHorizontalFacing();
        BlockPos pos = context.getPos();
        int x = direction.getXOffset();
        int z = direction.getZOffset();
        double onX = context.getHitVec().x - pos.getX();
        double onZ = context.getHitVec().z - pos.getZ();
        boolean hingeLeft = (x >= 0 || onZ >= 0.5D) && (x <= 0 || onZ <= 0.5D) && (z >= 0 || onX <= 0.5D) && (z <= 0 || onX >= 0.5D);
        Direction hingeDirection = hingeLeft ? direction.rotateYCCW() : direction.rotateY();
        if(!canSupportShutters(world, pos, direction, hingeDirection)) hingeLeft = !hingeLeft;
        return super.getStateForPlacement(context).with(HINGE, hingeLeft ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT).with(FACING, direction).with(POWERED, world.isBlockPowered(pos));
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        Direction direction = state.get(FACING);
        Direction hingeDirection = (state.get(HINGE) == DoorHingeSide.LEFT) ? direction.rotateYCCW() : direction.rotateY();
        return canSupportShutters(worldIn, pos, direction, hingeDirection);
    }

    protected static boolean canSupportShutters(IWorldReader worldIn, BlockPos shutterPos, Direction direction, Direction hingeDirection) {
        BlockPos pos = shutterPos.offset(direction).offset(hingeDirection);
        return hasSolidSide(worldIn.getBlockState(pos), worldIn, pos, direction.getOpposite()) || hasSolidSide(worldIn.getBlockState(pos), worldIn, pos, hingeDirection.getOpposite());
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        Direction direction = stateIn.get(FACING);
        Direction hingeDirection = (stateIn.get(HINGE) == DoorHingeSide.LEFT) ? direction.rotateYCCW() : direction.rotateY();
        if(!canSupportShutters(worldIn, currentPos, direction, hingeDirection))
            return Blocks.AIR.defaultBlockState();
        if(facing == hingeDirection) {
            stateIn = super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
            return stateIn.get(OPEN_POSITION) == DoTBBlockStateProperties.OpenPosition.CLOSED ? stateIn : stateIn.with(OPEN_POSITION, getOpenState(stateIn, worldIn.getLevel(), facingPos));
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(state.get(OPEN_POSITION).isOpen())
            state = state.with(OPEN_POSITION, DoTBBlockStateProperties.OpenPosition.CLOSED);
        else{
            Direction hingeDirection = (state.get(HINGE) == DoorHingeSide.LEFT) ? state.get(FACING).rotateYCCW() : state.get(FACING).rotateY();
            state = state.with(OPEN_POSITION, getOpenState(state, worldIn, pos.offset(hingeDirection)));
        }
        worldIn.setBlockState(pos, state, 10);
        worldIn.playEvent(player, state.get(OPEN_POSITION).isOpen() ? this.getOpenSound() : this.getCloseSound(), pos, 0);
        if (state.get(WATERLOGGED)) worldIn.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        return true;
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        boolean isPowered = worldIn.isBlockPowered(pos);
        if(blockIn != this && isPowered != state.get(POWERED)) {
            if (isPowered != state.get(OPEN_POSITION).isOpen()) {
                this.playSound(worldIn, pos, isPowered);
            }
            if(isPowered){
                Direction hingeDirection = (state.get(HINGE) == DoorHingeSide.LEFT) ? state.get(FACING).rotateYCCW() : state.get(FACING).rotateY();
                state = state.with(OPEN_POSITION, getOpenState(state, worldIn, pos.offset(hingeDirection)));
            }else
                state = state.with(OPEN_POSITION, DoTBBlockStateProperties.OpenPosition.CLOSED);
            worldIn.setBlockState(pos, state.with(POWERED, isPowered), 2);
        }
    }

    protected DoTBBlockStateProperties.OpenPosition getOpenState(BlockState stateIn, World worldIn, BlockPos pos){
        return worldIn.getBlockState(pos).getCollisionShape(worldIn, pos).isEmpty() ? DoTBBlockStateProperties.OpenPosition.FULL : DoTBBlockStateProperties.OpenPosition.HALF;
    }

    private void playSound(World worldIn, BlockPos pos, boolean isOpening) {
        worldIn.playEvent(null, isOpening ? this.getOpenSound() : this.getCloseSound(), pos, 0);
    }

    private int getCloseSound() {
        return 1012;
    }

    private int getOpenSound() {
        return 1006;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
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
                return state.with(HINGE, state.get(HINGE) == DoorHingeSide.RIGHT ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT);
        }
    }
}