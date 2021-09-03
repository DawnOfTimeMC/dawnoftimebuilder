package org.dawnoftimebuilder.block.japanese;

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
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

public class CharredSpruceShuttersBlock extends WaterloggedBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    private static final VoxelShape[] SHAPES = DoTBBlockUtils.GenerateHorizontalShapes(new VoxelShape[]{
            makeCuboidShape(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D),
            VoxelShapes.or(
                    makeCuboidShape(0.0D, 12.0D, 12.0D, 16.0D, 16.0D, 16.0D),
                    makeCuboidShape(0.0D, 9.0D, 9.0D, 16.0D, 13.0D, 13.0D),
                    makeCuboidShape(0.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D),
                    makeCuboidShape(0.0D, 3.0D, 3.0D, 16.0D, 7.0D, 7.0D))});

    public CharredSpruceShuttersBlock(Material materialIn, float hardness, float resistance, SoundType soundType) {
        super(Properties.create(materialIn).hardnessAndResistance(hardness, resistance).sound(soundType));
        this.setDefaultState(this.getStateContainer().getBaseState().with(OPEN, false).with(WATERLOGGED, false).with(POWERED, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(FACING, OPEN, POWERED);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        int index = state.get(OPEN) ? 1 : 0;
        return SHAPES[state.get(FACING).getHorizontalIndex() * 2 + index];
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
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        return super.getStateForPlacement(context).with(FACING, context.getPlacementHorizontalFacing()).with(POWERED, world.isBlockPowered(pos));
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return canSupportShutters(worldIn, pos, state.get(FACING));
    }

    public boolean canSupportShutters(IWorldReader worldIn, BlockPos shutterPos, Direction direction) {
        BlockPos pos = shutterPos.offset(direction).up();
        return hasSolidSide(worldIn.getBlockState(pos), worldIn, pos, direction.getOpposite()) || hasSolidSide(worldIn.getBlockState(pos), worldIn, pos, Direction.DOWN);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(!canSupportShutters(worldIn, currentPos, stateIn.get(FACING))) return Blocks.AIR.getDefaultState();
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        state = state.with(OPEN, !state.get(OPEN));
        worldIn.setBlockState(pos, state, 10);
        worldIn.playEvent(player, state.get(OPEN) ? this.getOpenSound() : this.getCloseSound(), pos, 0);
        if (state.get(WATERLOGGED)) worldIn.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        return true;
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        boolean isPowered = worldIn.isBlockPowered(pos);
        if(blockIn != this && isPowered != state.get(POWERED)) {
            if (isPowered != state.get(OPEN)) this.playSound(worldIn, pos, isPowered);
            worldIn.setBlockState(pos, state.with(POWERED, isPowered).with(OPEN, isPowered), 2);
        }
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
        return this.rotate(state, Rotation.CLOCKWISE_180);
    }
}