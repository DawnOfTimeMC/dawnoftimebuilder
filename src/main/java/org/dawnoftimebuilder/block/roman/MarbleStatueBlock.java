package org.dawnoftimebuilder.block.roman;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import javax.annotation.Nullable;

public class MarbleStatueBlock extends WaterloggedBlock {

    private static final VoxelShape VS = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape VS_TOP = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty MULTIBLOCK = DoTBBlockStateProperties.MULTIBLOCK_0_2;

    public MarbleStatueBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(MULTIBLOCK, 0).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, MULTIBLOCK);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.getValue(MULTIBLOCK) == 2 ? VS_TOP : VS;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos pos = context.getClickedPos();
        World level = context.getLevel();
        if(!level.getBlockState(pos.above()).canBeReplaced(context)
                || !level.getBlockState(pos.above(2)).canBeReplaced(context)){
            return null;
        }
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        BlockPos abovePos = pos.above();
        worldIn.setBlock(abovePos, state.setValue(MULTIBLOCK, 1).setValue(WATERLOGGED, worldIn.getFluidState(abovePos).getType() == Fluids.WATER), 10);
        abovePos = abovePos.above();
        worldIn.setBlock(abovePos, state.setValue(MULTIBLOCK, 2).setValue(WATERLOGGED, worldIn.getFluidState(abovePos).getType() == Fluids.WATER), 10);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(facing.getAxis().isHorizontal()) return stateIn;
        int multipart = stateIn.getValue(MULTIBLOCK);

        if(facing == Direction.UP && multipart < 2) {
            if(facingState.getBlock() == this){
                if(facingState.getValue(FACING) == stateIn.getValue(FACING) && facingState.getValue(MULTIBLOCK) == multipart + 1){
                    return stateIn;
                }
            }
        }

        if(facing == Direction.DOWN && multipart > 0) {
            if(facingState.getBlock() == this){
                if(facingState.getValue(FACING) == stateIn.getValue(FACING) && facingState.getValue(MULTIBLOCK) == multipart - 1){
                    return stateIn;
                }
            }
        }

        return Blocks.AIR.defaultBlockState();
    }
}
