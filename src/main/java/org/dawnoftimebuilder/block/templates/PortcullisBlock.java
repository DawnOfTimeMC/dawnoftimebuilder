package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nullable;
import java.util.List;

public class PortcullisBlock extends WaterloggedBlock {
    private static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    private static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    private static final EnumProperty<DoTBBlockStateProperties.VerticalConnection> VERTICAL_CONNECTION = DoTBBlockStateProperties.VERTICAL_CONNECTION;
    private static final VoxelShape VS_AXIS_X = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    private static final VoxelShape VS_AXIS_Z = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    public PortcullisBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(OPEN, false).setValue(POWERED, false).setValue(HORIZONTAL_AXIS, Direction.Axis.X).setValue(VERTICAL_CONNECTION, DoTBBlockStateProperties.VerticalConnection.NONE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(OPEN, POWERED, HORIZONTAL_AXIS, VERTICAL_CONNECTION);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if(state.getValue(OPEN) && state.getValue(VERTICAL_CONNECTION) != DoTBBlockStateProperties.VerticalConnection.NONE && state.getValue(VERTICAL_CONNECTION) != DoTBBlockStateProperties.VerticalConnection.UNDER)
            return Shapes.empty();
        else
            return (state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X) ? VS_AXIS_X : VS_AXIS_Z;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        state = state.setValue(HORIZONTAL_AXIS, (context.getHorizontalDirection().getAxis() == Direction.Axis.X) ? Direction.Axis.Z : Direction.Axis.X);
        return this.getShape(state, context.getLevel(), context.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        return this.getShape(stateIn, worldIn, currentPos);
    }

    private BlockState getShape(BlockState state, LevelAccessor worldIn, BlockPos pos) {
        Direction.Axis axis = state.getValue(HORIZONTAL_AXIS);
        if(hasSameAxis(worldIn.getBlockState(pos.above()), axis)) {
            return state.setValue(VERTICAL_CONNECTION, (hasSameAxis(worldIn.getBlockState(pos.below()), axis)) ? DoTBBlockStateProperties.VerticalConnection.BOTH : DoTBBlockStateProperties.VerticalConnection.ABOVE);
        } else {
            return state.setValue(VERTICAL_CONNECTION, (hasSameAxis(worldIn.getBlockState(pos.below()), axis)) ? DoTBBlockStateProperties.VerticalConnection.UNDER : DoTBBlockStateProperties.VerticalConnection.NONE);
        }
    }

    private boolean hasSameAxis(BlockState state, Direction.Axis axis) {
        if(state.getBlock() instanceof PortcullisBlock) {
            return state.getValue(HORIZONTAL_AXIS) == axis;
        } else
            return false;
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if(state.getValue(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.UNDER) {
            //update coming from top blocks : check the shape of whole portcullis to know if it can be or stay open
            Direction.Axis axis = state.getValue(HORIZONTAL_AXIS);
            boolean isNowPowered = worldIn.hasNeighborSignal(pos);
            if(state.getValue(OPEN)) {
                if(isInSamePlane(pos, fromPos, axis) && isNowPowered)
                    setOpenState(worldIn, pos, axis, true);
            } else {
                if(isNowPowered) {
                    setOpenState(worldIn, pos, axis, true);
                    state = state.setValue(OPEN, true);
                }
            }
            if(!isNowPowered && state.getValue(POWERED)) {
                state = state.setValue(POWERED, false);
                worldIn.setBlock(pos, state, 2);
                if(!hasAnotherPowerSource(worldIn, pos, axis))
                    setOpenState(worldIn, pos, axis, false);
            }
            if(isNowPowered != state.getValue(POWERED)) {
                state = state.setValue(POWERED, isNowPowered);
                worldIn.setBlock(pos, state, 2);
            }
        } else if(state.getValue(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.NONE) {
            if(state.getValue(OPEN))
                worldIn.setBlock(pos, state.setValue(OPEN, false), 2);
        } else {
            //update coming from the rest of the portcullis (and when opening) : send an update task to top blocks
            //NB : VerticalConnection.NONE can't be open
            if(state.getValue(OPEN)) {
                Direction.Axis axis = state.getValue(HORIZONTAL_AXIS);
                if(isInSamePlane(pos, fromPos, axis)) {
                    pos = getTopPortcullisPos(worldIn, pos, axis);
                    worldIn.getBlockState(pos).neighborChanged(worldIn, pos, blockIn, fromPos, isMoving);
                }
            }
        }
    }

    private boolean isInSamePlane(BlockPos pos, BlockPos fromPos, Direction.Axis axis) {
        return (axis == Direction.Axis.X) ? fromPos.getZ() == pos.getZ() : fromPos.getX() == pos.getX();
    }

    private BlockPos getTopPortcullisPos(Level worldIn, BlockPos pos, Direction.Axis axis) {
        BlockState state;
        for(boolean isStillPortcullis = true; isStillPortcullis; pos = pos.above()) {
            state = worldIn.getBlockState(pos);
            if(state.getBlock() instanceof PortcullisBlock) {
                if(!hasSameAxis(state, axis))
                    isStillPortcullis = false;
            } else
                isStillPortcullis = false;
        }
        return pos.below();
    }

    private boolean hasAnotherPowerSource(Level worldIn, BlockPos pos, Direction.Axis axis) {
        boolean isAxisX = axis == Direction.Axis.X;
        Direction direction = (isAxisX) ? Direction.WEST : Direction.NORTH;
        int widthLeft = getPortcullisWidth(worldIn, pos, axis, (isAxisX) ? Direction.EAST : Direction.SOUTH);
        int widthRight = getPortcullisWidth(worldIn, pos, axis, (isAxisX) ? Direction.WEST : Direction.NORTH);
        BlockState state;
        pos = pos.relative(direction, -widthLeft);
        for(int i = 0; i <= (widthLeft + widthRight); i++) {
            state = worldIn.getBlockState(pos.relative(direction, i));
            if(state.getValue(POWERED))
                return true;
        }
        return false;
    }

    private void setOpenState(Level worldIn, BlockPos pos, Direction.Axis axis, boolean openState) {
        boolean isAxisX = axis == Direction.Axis.X;
        Direction direction = (isAxisX) ? Direction.WEST : Direction.NORTH;
        int height = getPortcullisHeight(worldIn, pos, axis);
        int widthLeft = getPortcullisWidth(worldIn, pos, axis, (isAxisX) ? Direction.EAST : Direction.SOUTH);
        int widthRight = getPortcullisWidth(worldIn, pos, axis, (isAxisX) ? Direction.WEST : Direction.NORTH);
        BlockState state;
        if(height > 16 || (widthLeft + widthRight + 1) > 16)
            return;
        if(openState) {
            for(int horizontal = 1; horizontal <= widthLeft; horizontal++) {
                for(int vertical = 0; vertical < height; vertical++) {
                    state = worldIn.getBlockState(pos.relative((isAxisX) ? Direction.EAST : Direction.SOUTH, horizontal).below(vertical));
                    if(state.getBlock() instanceof PortcullisBlock) {
                        if(state.getValue(HORIZONTAL_AXIS) != axis) {
                            openState = false;
                            break;
                        }
                    } else {
                        openState = false;
                        break;
                    }
                }
                if(!openState)
                    break;
            }
            if(openState) {
                for(int horizontal = 1; horizontal <= widthRight; horizontal++) {
                    for(int vertical = 0; vertical < height; vertical++) {
                        state = worldIn.getBlockState(pos.relative(direction, horizontal).below(vertical));
                        if(state.getBlock() instanceof PortcullisBlock) {
                            if(state.getValue(HORIZONTAL_AXIS) != axis) {
                                openState = false;
                                break;
                            }
                        } else {
                            openState = false;
                            break;
                        }
                    }
                    if(!openState)
                        break;
                }
            }
            if(openState) {
                openState = isCorrectBorder(worldIn, pos.relative(direction, -widthLeft).above(), axis, (isAxisX) ? Direction.WEST : Direction.NORTH, widthLeft + widthRight);
                openState = openState && isCorrectBorder(worldIn, pos.relative(direction, -widthLeft).below(height), axis, (isAxisX) ? Direction.WEST : Direction.NORTH, widthLeft + widthRight);
                openState = openState && isCorrectBorder(worldIn, pos.relative(direction, -widthLeft - 1), axis, Direction.DOWN, height - 1);
                openState = openState && isCorrectBorder(worldIn, pos.relative(direction, widthRight + 1), axis, Direction.DOWN, height - 1);
            }
        }
        pos = pos.relative(direction, -widthLeft);
        BlockPos newPos;
        for(int horizontal = 0; horizontal < widthLeft + widthRight + 1; horizontal++) {
            for(int vertical = 0; vertical < height; vertical++) {
                newPos = pos.relative(direction, horizontal).below(vertical);
                state = worldIn.getBlockState(newPos);
                if(state.getBlock() instanceof PortcullisBlock) {
                    if(state.getValue(HORIZONTAL_AXIS) == axis) {
                        worldIn.setBlock(newPos, state.setValue(OPEN, openState), 10);
                        if(horizontal == widthLeft) {
                            if(vertical == 0 && openState)
                                worldIn.levelEvent(null, this.getOpenSound(), newPos, 0);
                            if(vertical == height - 1 && !openState)
                                worldIn.levelEvent(null, this.getCloseSound(), newPos, 0);
                        }
                    }
                }
            }
        }
    }

    private int getPortcullisHeight(Level worldIn, BlockPos pos, Direction.Axis axis) {
        int height = 0;
        BlockState state;
        for(boolean isSamePortcullis = true; isSamePortcullis; height++) {
            pos = pos.below();
            state = worldIn.getBlockState(pos);
            if(state.getBlock() instanceof PortcullisBlock) {
                if(state.getValue(HORIZONTAL_AXIS) != axis)
                    isSamePortcullis = false;
            } else
                isSamePortcullis = false;
        }
        return height;
    }

    private int getPortcullisWidth(Level worldIn, BlockPos pos, Direction.Axis axis, Direction direction) {
        int width = 0;
        BlockState state;
        for(boolean isSamePortcullis = true; isSamePortcullis; width++) {
            pos = pos.relative(direction);
            state = worldIn.getBlockState(pos);
            if(state.getBlock() instanceof PortcullisBlock) {
                if(state.getValue(HORIZONTAL_AXIS) != axis)
                    isSamePortcullis = false;
            } else
                isSamePortcullis = false;
        }
        return (width - 1);
    }

    private boolean isCorrectBorder(Level worldIn, BlockPos pos, Direction.Axis axis, Direction direction, int length) {
        BlockState state;
        for(int i = 0; i <= length; i++) {
            state = worldIn.getBlockState(pos.relative(direction, i));
            if(state.getBlock() instanceof PortcullisBlock) {
                if(state.getValue(HORIZONTAL_AXIS) == axis)
                    return false;
            }
        }
        return true;
    }

    private int getCloseSound() {
        return 1011;
    }

    private int getOpenSound() {
        return 1005;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        if(rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90)
            return state.setValue(HORIZONTAL_AXIS, (state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X) ? Direction.Axis.Z : Direction.Axis.X);
        else
            return super.rotate(state, rot);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        DoTBUtils.addTooltip(tooltip, this);
    }
}