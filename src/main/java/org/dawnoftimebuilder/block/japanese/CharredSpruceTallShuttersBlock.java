package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nullable;

import static org.dawnoftimebuilder.util.DoTBBlockStateProperties.SquareCorners;

public class CharredSpruceTallShuttersBlock extends CharredSpruceShuttersBlock {

    public static final EnumProperty<DoTBBlockStateProperties.SquareCorners> CORNER = DoTBBlockStateProperties.CORNER;
    private static final VoxelShape[] SHAPES = DoTBBlockUtils.GenerateHorizontalShapes(new VoxelShape[]{
            Block.box(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D),
            VoxelShapes.or(
                    Block.box(0.0D, 10.0D, 11.0D, 16.0D, 16.0D, 16.0D),
                    Block.box(0.0D, 5.0D, 9.0D, 16.0D, 10.0D, 14.0D),
                    Block.box(0.0D, 0.0D, 7.0D, 16.0D, 5.0D, 12.0D)),
            VoxelShapes.or(
                    Block.box(0.0D, 11.0D, 5.0D, 16.0D, 16.0D, 10.0D),
                    Block.box(0.0D, 6.0D, 3.0D, 16.0D, 11.0D, 8.0D),
                    Block.box(0.0D, 1.0D, 1.0D, 16.0D, 6.0D, 6.0D))});

    public CharredSpruceTallShuttersBlock(Material materialIn, float hardness, float resistance, SoundType soundType) {
        super(materialIn, hardness, resistance, soundType);
        this.registerDefaultState(this.defaultBlockState().setValue(CORNER, DoTBBlockStateProperties.SquareCorners.TOP_LEFT).setValue(OPEN, false).setValue(WATERLOGGED, false).setValue(POWERED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CORNER);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        int index = state.get(OPEN) ? state.get(CORNER).isTopCorner() ? 1 : 2 : 0;
        return SHAPES[state.get(FACING).get2DDataValue() * 3 + index];
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction facing = context.getHorizontalDirection();

        BlockPos posRight = pos.relative(facing.getClockWise());
        boolean right = world.getBlockState(posRight).isReplaceable(context);
        BlockPos posLeft = pos.relative(facing.getCounterClockWise());
        boolean left = world.getBlockState(posLeft).isReplaceable(context);
        if(!right && !left) return null;

        if(world.getBlockState(pos.above()).isReplaceable(context) && canSupportShutters(world, pos.above(), facing)){
            if(right){
                if(world.getBlockState(posRight.above()).isReplaceable(context) && canSupportShutters(world, posRight.above(), facing)){
                    return super.getStateForPlacement(context).setValue(CORNER, SquareCorners.BOTTOM_LEFT).setValue(FACING, facing).setValue(POWERED, world.isBlockPowered(pos));
                }
            }
            if(left){
                if(world.getBlockState(posLeft.above()).isReplaceable(context) && canSupportShutters(world, posLeft.above(), facing)){
                    return super.getStateForPlacement(context).setValue(CORNER, SquareCorners.BOTTOM_RIGHT).setValue(FACING, facing).setValue(POWERED, world.isBlockPowered(pos));
                }
            }
        }
        //The block from top row of the shutters can't be put above. The top of the shutters must be on this Y.
        // We need to check if the currentPos can support shutters.
        if(canSupportShutters(world, pos, facing)){
            if(world.getBlockState(pos.below()).isReplaceable(context)){
                if(right && canSupportShutters(world, posRight, facing)){
                    if(world.getBlockState(posRight.below()).isReplaceable(context)){
                        return super.getStateForPlacement(context).setValue(CORNER, SquareCorners.TOP_LEFT).setValue(FACING, facing).setValue(POWERED, world.isBlockPowered(pos));
                    }
                }
                if(left && canSupportShutters(world, posLeft, facing)){
                    if(world.getBlockState(posLeft.below()).isReplaceable(context)){
                        return super.getStateForPlacement(context).setValue(CORNER, SquareCorners.TOP_RIGHT).setValue(FACING, facing).setValue(POWERED, world.isBlockPowered(pos));
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if(state.get(CORNER).isTopCorner()){
            return canSupportShutters(worldIn, pos, state.get(FACING));
        }else return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        SquareCorners thisCorner = state.get(CORNER);
        Direction facing = state.get(FACING);
        //Let's set the 3 other corners
        for(SquareCorners corner : SquareCorners.values()){
            if(thisCorner != corner){
                BlockPos cornerPos = pos.above(corner.getVerticalOffset(thisCorner)).relative(facing.getClockWise(), corner.getHorizontalOffset(thisCorner));
                worldIn.setBlock(cornerPos, state.setValue(CORNER, corner).setValue(WATERLOGGED, worldIn.getFluidState(cornerPos).getFluid() == Fluids.WATER), 10);
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        SquareCorners thisCorner = stateIn.get(CORNER);
        if(stateIn.get(WATERLOGGED)) worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        if(!canSupportShutters(worldIn, currentPos, stateIn.get(FACING)) && stateIn.get(CORNER).isTopCorner()) return Blocks.AIR.defaultBlockState();
        Direction currentFacing = stateIn.get(FACING);
        SquareCorners expectedCorner = thisCorner.getAdjacentCorner(facing.getAxis().isVertical());
        if(currentPos.above(expectedCorner.getVerticalOffset(thisCorner)).relative(currentFacing.getClockWise(), expectedCorner.getHorizontalOffset(thisCorner)).equals(facingPos)){
            //The pos of the updated block is supposed to contain a part of the shutter : let's check it
            if(facingState.getBlock() == this){
                if(facingState.get(CORNER) == expectedCorner && facingState.get(FACING) == currentFacing){
                    return stateIn.setValue(OPEN, facingState.get(OPEN));
                }
            }
            return Blocks.AIR.defaultBlockState();
        }
        return stateIn;
    }
}