package org.dawnoftimebuilder.block.templates;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import java.util.Map;

public class CappedWallBlock extends Block implements IWaterLoggable {

    public static final EnumProperty<WallHeight> PILLAR = DoTBBlockStateProperties.PILLAR_WALL;
    public static final EnumProperty<WallHeight> EAST_WALL = BlockStateProperties.EAST_WALL;
    public static final EnumProperty<WallHeight> NORTH_WALL = BlockStateProperties.NORTH_WALL;
    public static final EnumProperty<WallHeight> SOUTH_WALL = BlockStateProperties.SOUTH_WALL;
    public static final EnumProperty<WallHeight> WEST_WALL = BlockStateProperties.WEST_WALL;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private final Map<BlockState, VoxelShape> shapeByIndex;
    private final Map<BlockState, VoxelShape> collisionShapeByIndex;
    private static final VoxelShape POST_TEST = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
    private static final VoxelShape NORTH_TEST = Block.box(7.0D, 0.0D, 0.0D, 9.0D, 16.0D, 9.0D);
    private static final VoxelShape SOUTH_TEST = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 16.0D);
    private static final VoxelShape WEST_TEST = Block.box(0.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
    private static final VoxelShape EAST_TEST = Block.box(7.0D, 0.0D, 7.0D, 16.0D, 16.0D, 9.0D);

    public CappedWallBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PILLAR, WallHeight.LOW).setValue(NORTH_WALL, WallHeight.NONE).setValue(EAST_WALL, WallHeight.NONE).setValue(SOUTH_WALL, WallHeight.NONE).setValue(WEST_WALL, WallHeight.NONE).setValue(WATERLOGGED, false));
        this.shapeByIndex = this.makeShapes(16.0F, 14.0F);
        this.collisionShapeByIndex = this.makeShapes(24.0F, 24.0F);
    }

    private static VoxelShape applyWallShape(VoxelShape mainShape, WallHeight height, VoxelShape newShape, VoxelShape p_235631_3_) {
        if (height == WallHeight.TALL) {
            return VoxelShapes.or(mainShape, p_235631_3_);
        } else {
            return height == WallHeight.LOW ? VoxelShapes.or(mainShape, newShape) : mainShape;
        }
    }

    private Map<BlockState, VoxelShape> makeShapes(float sizePillarVertical, float sizeWallVertical) {
        float sizePillarStart = 4.0F;
        float sizePillarEnd = 12.0F;
        float sizeWallStart = 5.0F;
        float sizeWallEnd = 11.0F;
        VoxelShape voxelshape = Block.box(sizePillarStart,0.0D, sizePillarStart, sizePillarEnd, sizePillarVertical, sizePillarEnd);
        VoxelShape voxelshape1 = Block.box(sizeWallStart, 0.0D,0.0D, sizeWallEnd, sizeWallVertical, sizeWallEnd);
        VoxelShape voxelshape2 = Block.box(sizeWallStart, 0.0D, sizeWallStart, sizeWallEnd, sizeWallVertical, 16.0D);
        VoxelShape voxelshape3 = Block.box(0.0D, 0.0D, sizeWallStart, sizeWallEnd, sizeWallVertical, sizeWallEnd);
        VoxelShape voxelshape4 = Block.box(sizeWallStart, 0.0D, sizeWallStart, 16.0D, sizeWallVertical, sizeWallEnd);
        VoxelShape voxelshape5 = Block.box(sizeWallStart, 0.0D, 0.0D, sizeWallEnd, sizePillarVertical, sizeWallEnd);
        VoxelShape voxelshape6 = Block.box(sizeWallStart, 0.0D, sizeWallStart, sizeWallEnd, sizePillarVertical, 16.0D);
        VoxelShape voxelshape7 = Block.box(0.0D, 0.0D, sizeWallStart, sizeWallEnd, sizePillarVertical, sizeWallEnd);
        VoxelShape voxelshape8 = Block.box(sizeWallStart, 0.0D, sizeWallStart, 16.0D, sizePillarVertical, sizeWallEnd);
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        for(WallHeight pillarStates : PILLAR.getPossibleValues()) {
            for(WallHeight eastState : EAST_WALL.getPossibleValues()) {
                for(WallHeight northState : NORTH_WALL.getPossibleValues()) {
                    for(WallHeight westState : WEST_WALL.getPossibleValues()) {
                        for(WallHeight southState : SOUTH_WALL.getPossibleValues()) {
                            VoxelShape mainShape = VoxelShapes.empty();
                            mainShape = applyWallShape(mainShape, eastState, voxelshape4, voxelshape8);
                            mainShape = applyWallShape(mainShape, westState, voxelshape3, voxelshape7);
                            mainShape = applyWallShape(mainShape, northState, voxelshape1, voxelshape5);
                            mainShape = applyWallShape(mainShape, southState, voxelshape2, voxelshape6);
                            if (pillarStates != WallHeight.NONE) {
                                mainShape = VoxelShapes.or(mainShape, voxelshape);
                            }

                            BlockState blockstate = this.defaultBlockState().setValue(PILLAR, pillarStates).setValue(EAST_WALL, eastState).setValue(WEST_WALL, westState).setValue(NORTH_WALL, northState).setValue(SOUTH_WALL, southState);
                            builder.put(blockstate.setValue(WATERLOGGED, false), mainShape);
                            builder.put(blockstate.setValue(WATERLOGGED, true), mainShape);
                        }
                    }
                }
            }
        }
        return builder.build();
    }

    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return this.shapeByIndex.get(state);
    }

    public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return this.collisionShapeByIndex.get(state);
    }

    public boolean isPathfindable(BlockState state, IBlockReader world, BlockPos pos, PathType pathType) {
        return false;
    }

    private boolean connectsTo(BlockState state, boolean p_220113_2_, Direction direction) {
        Block block = state.getBlock();
        boolean flag = block instanceof FenceGateBlockDoTB && FenceGateBlockDoTB.connectsToDirection(state, direction);
        return state.is(BlockTags.WALLS) || !isExceptionForConnection(block) && p_220113_2_ || block instanceof PaneBlock || flag;
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IWorldReader world = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        BlockPos posNorth = clickedPos.north();
        BlockPos posEast = clickedPos.east();
        BlockPos posSouth = clickedPos.south();
        BlockPos posWest = clickedPos.west();
        BlockPos posAbove = clickedPos.above();
        BlockState blockstate = world.getBlockState(posNorth);
        BlockState blockstate1 = world.getBlockState(posEast);
        BlockState blockstate2 = world.getBlockState(posSouth);
        BlockState blockstate3 = world.getBlockState(posWest);
        BlockState blockstate4 = world.getBlockState(posAbove);
        boolean connectsSouth = this.connectsTo(blockstate, blockstate.isFaceSturdy(world, posNorth, Direction.SOUTH), Direction.SOUTH);
        boolean connectsWest = this.connectsTo(blockstate1, blockstate1.isFaceSturdy(world, posEast, Direction.WEST), Direction.WEST);
        boolean connectsNorth = this.connectsTo(blockstate2, blockstate2.isFaceSturdy(world, posSouth, Direction.NORTH), Direction.NORTH);
        boolean connectsEast = this.connectsTo(blockstate3, blockstate3.isFaceSturdy(world, posWest, Direction.EAST), Direction.EAST);
        BlockState state = this.defaultBlockState().setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
        return this.updateShape(world, state, posAbove, blockstate4, connectsSouth, connectsWest, connectsNorth, connectsEast);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState p_196271_3_, IWorld world, BlockPos pos, BlockPos p_196271_6_) {
        if (state.getValue(WATERLOGGED)) {
            world.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        if (direction == Direction.DOWN) {
            return super.updateShape(state, direction, p_196271_3_, world, pos, p_196271_6_);
        } else {
            return direction == Direction.UP ? this.topUpdate(world, state, p_196271_6_, p_196271_3_) : this.sideUpdate(world, pos, state, p_196271_6_, p_196271_3_, direction);
        }
    }

    private static boolean isConnected(BlockState p_235629_0_, Property<WallHeight> p_235629_1_) {
        return p_235629_0_.getValue(p_235629_1_) != WallHeight.NONE;
    }

    private static boolean isCovered(VoxelShape p_235632_0_, VoxelShape p_235632_1_) {
        return !VoxelShapes.joinIsNotEmpty(p_235632_1_, p_235632_0_, IBooleanFunction.ONLY_FIRST);
    }

    private BlockState topUpdate(IWorldReader p_235625_1_, BlockState p_235625_2_, BlockPos p_235625_3_, BlockState p_235625_4_) {
        boolean flag = isConnected(p_235625_2_, NORTH_WALL);
        boolean flag1 = isConnected(p_235625_2_, EAST_WALL);
        boolean flag2 = isConnected(p_235625_2_, SOUTH_WALL);
        boolean flag3 = isConnected(p_235625_2_, WEST_WALL);
        return this.updateShape(p_235625_1_, p_235625_2_, p_235625_3_, p_235625_4_, flag, flag1, flag2, flag3);
    }

    private BlockState sideUpdate(IWorldReader p_235627_1_, BlockPos p_235627_2_, BlockState p_235627_3_, BlockPos p_235627_4_, BlockState p_235627_5_, Direction p_235627_6_) {
        Direction direction = p_235627_6_.getOpposite();
        boolean flag = p_235627_6_ == Direction.NORTH ? this.connectsTo(p_235627_5_, p_235627_5_.isFaceSturdy(p_235627_1_, p_235627_4_, direction), direction) : isConnected(p_235627_3_, NORTH_WALL);
        boolean flag1 = p_235627_6_ == Direction.EAST ? this.connectsTo(p_235627_5_, p_235627_5_.isFaceSturdy(p_235627_1_, p_235627_4_, direction), direction) : isConnected(p_235627_3_, EAST_WALL);
        boolean flag2 = p_235627_6_ == Direction.SOUTH ? this.connectsTo(p_235627_5_, p_235627_5_.isFaceSturdy(p_235627_1_, p_235627_4_, direction), direction) : isConnected(p_235627_3_, SOUTH_WALL);
        boolean flag3 = p_235627_6_ == Direction.WEST ? this.connectsTo(p_235627_5_, p_235627_5_.isFaceSturdy(p_235627_1_, p_235627_4_, direction), direction) : isConnected(p_235627_3_, WEST_WALL);
        BlockPos blockpos = p_235627_2_.above();
        BlockState blockstate = p_235627_1_.getBlockState(blockpos);
        return this.updateShape(p_235627_1_, p_235627_3_, blockpos, blockstate, flag, flag1, flag2, flag3);
    }

    private BlockState updateShape(IWorldReader world, BlockState state, BlockPos pos, BlockState stateTop, boolean p_235626_5_, boolean p_235626_6_, boolean p_235626_7_, boolean p_235626_8_) {
        VoxelShape voxelshape = stateTop.getCollisionShape(world, pos).getFaceShape(Direction.DOWN);
        BlockState blockstate = this.updateSides(state, p_235626_5_, p_235626_6_, p_235626_7_, p_235626_8_, voxelshape);
        return blockstate.setValue(PILLAR, this.shouldRaisePost(blockstate, stateTop, voxelshape));
    }

    private WallHeight shouldRaisePost(BlockState state, BlockState stateTop, VoxelShape shape) {
        boolean flag = stateTop.getBlock() instanceof WallBlock && stateTop.getValue(WallBlock.UP);
        if (flag) {
            return this.getPillarState(stateTop);
        } else {
            WallHeight wallheight = state.getValue(NORTH_WALL);
            WallHeight wallheight1 = state.getValue(SOUTH_WALL);
            WallHeight wallheight2 = state.getValue(EAST_WALL);
            WallHeight wallheight3 = state.getValue(WEST_WALL);
            boolean flag1 = wallheight1 == WallHeight.NONE;
            boolean flag2 = wallheight3 == WallHeight.NONE;
            boolean flag3 = wallheight2 == WallHeight.NONE;
            boolean flag4 = wallheight == WallHeight.NONE;
            boolean flag5 = flag4 && flag1 && flag2 && flag3 || flag4 != flag1 || flag2 != flag3;
            if (flag5) {
                return this.getPillarState(stateTop);
            } else {
                boolean flag6 = wallheight == WallHeight.TALL && wallheight1 == WallHeight.TALL || wallheight2 == WallHeight.TALL && wallheight3 == WallHeight.TALL;
                if (flag6) {
                    return WallHeight.NONE;
                } else {
                    return stateTop.getBlock().is(BlockTags.WALL_POST_OVERRIDE) || isCovered(shape, POST_TEST) ? this.getPillarState(stateTop) : WallHeight.NONE;
                }
            }
        }
    }

    private WallHeight getPillarState(BlockState stateTop){
        return stateTop.is(BlockTags.WALLS) ? WallHeight.LOW : WallHeight.TALL;
    }

    private BlockState updateSides(BlockState state, boolean p_235630_2_, boolean p_235630_3_, boolean p_235630_4_, boolean p_235630_5_, VoxelShape p_235630_6_) {
        return state.setValue(NORTH_WALL, this.makeWallState(p_235630_2_, p_235630_6_, NORTH_TEST)).setValue(EAST_WALL, this.makeWallState(p_235630_3_, p_235630_6_, EAST_TEST)).setValue(SOUTH_WALL, this.makeWallState(p_235630_4_, p_235630_6_, SOUTH_TEST)).setValue(WEST_WALL, this.makeWallState(p_235630_5_, p_235630_6_, WEST_TEST));
    }

    private WallHeight makeWallState(boolean p_235633_1_, VoxelShape shape, VoxelShape p_235633_3_) {
        if (p_235633_1_) {
            return isCovered(shape, p_235633_3_) ? WallHeight.TALL : WallHeight.LOW;
        } else {
            return WallHeight.NONE;
        }
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader world, BlockPos pos) {
        return !state.getValue(WATERLOGGED);
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> container) {
        container.add(PILLAR, NORTH_WALL, EAST_WALL, WEST_WALL, SOUTH_WALL, WATERLOGGED);
    }

    public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
        switch(p_185499_2_) {
            case CLOCKWISE_180:
                return p_185499_1_.setValue(NORTH_WALL, p_185499_1_.getValue(SOUTH_WALL)).setValue(EAST_WALL, p_185499_1_.getValue(WEST_WALL)).setValue(SOUTH_WALL, p_185499_1_.getValue(NORTH_WALL)).setValue(WEST_WALL, p_185499_1_.getValue(EAST_WALL));
            case COUNTERCLOCKWISE_90:
                return p_185499_1_.setValue(NORTH_WALL, p_185499_1_.getValue(EAST_WALL)).setValue(EAST_WALL, p_185499_1_.getValue(SOUTH_WALL)).setValue(SOUTH_WALL, p_185499_1_.getValue(WEST_WALL)).setValue(WEST_WALL, p_185499_1_.getValue(NORTH_WALL));
            case CLOCKWISE_90:
                return p_185499_1_.setValue(NORTH_WALL, p_185499_1_.getValue(WEST_WALL)).setValue(EAST_WALL, p_185499_1_.getValue(NORTH_WALL)).setValue(SOUTH_WALL, p_185499_1_.getValue(EAST_WALL)).setValue(WEST_WALL, p_185499_1_.getValue(SOUTH_WALL));
            default:
                return p_185499_1_;
        }
    }

    public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
        switch(p_185471_2_) {
            case LEFT_RIGHT:
                return p_185471_1_.setValue(NORTH_WALL, p_185471_1_.getValue(SOUTH_WALL)).setValue(SOUTH_WALL, p_185471_1_.getValue(NORTH_WALL));
            case FRONT_BACK:
                return p_185471_1_.setValue(EAST_WALL, p_185471_1_.getValue(WEST_WALL)).setValue(WEST_WALL, p_185471_1_.getValue(EAST_WALL));
            default:
                return super.mirror(p_185471_1_, p_185471_2_);
        }
    }
}
