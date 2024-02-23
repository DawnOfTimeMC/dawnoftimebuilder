package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.PlantType;
import org.dawnoftimebuilder.item.templates.SoilSeedsItem;

public class WaterDoubleCropsBlock extends DoubleCropsBlock implements SimpleWaterloggedBlock {
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public WaterDoubleCropsBlock(String seedName, int growingAge) {
        this(seedName, growingAge, null);
    }

    public WaterDoubleCropsBlock(String seedName, int growingAge, FoodProperties food) {
        super(seedName, PlantType.WATER, growingAge, food);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, true));
    }

    @Override
    public SoilSeedsItem makeSeed(FoodProperties food) {
        return new SoilSeedsItem(this, food) {
            @Override
            public boolean hasFlowerPot() {
                return false;
            }
        };
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * 0 : AGE 0 (bot) : 8px <p/>
     * 1 : AGE 1 (bot) : 12px <p/>
     * 2 : AGE 2 (bot) : 16px <p/>
     * 3 : AGE 2 (top) : 1px <p/>
     * 4 : AGE 3 (top) : 5px <p/>
     * 5 : AGE 4 (top) : 8px <p/>
     * 6 : AGE 5 (top) : 11px <p/>
     * 7 : AGE 6 (top) : 14px <p/>
     * 8 : AGE 7 (top) : 16px <p/>
     */
    @Override
    public VoxelShape[] makeShapes() {
        return new VoxelShape[] {
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D),
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D),
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 11.0D, 16.0D),
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        LevelAccessor world = context.getLevel();
        return this.defaultBlockState().setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(stateIn.getValue(WATERLOGGED))
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public BlockState getTopState(BlockState bottomState) {
        return super.getTopState(bottomState).setValue(WATERLOGGED, false);
    }

    @Override
    public void setPlantWithAge(BlockState currentState, LevelAccessor worldIn, BlockPos pos, int newAge) {
        if(currentState.getValue(HALF) == Half.TOP)
            pos = pos.below();
        if(newAge >= this.getAgeReachingTopBlock()) {
            BlockPos posUp = pos.above();
            if(worldIn.getBlockState(posUp).getBlock() == this || worldIn.isEmptyBlock(posUp)) {
                worldIn.setBlock(posUp, currentState.setValue(this.getAgeProperty(), newAge).setValue(HALF, Half.TOP).setValue(WATERLOGGED, false), 10);
            }
        }
        if(newAge < this.getAgeReachingTopBlock() && this.getAge(currentState) == this.getAgeReachingTopBlock()) {
            worldIn.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 10);
        }
        worldIn.setBlock(pos, currentState.setValue(this.getAgeProperty(), newAge).setValue(HALF, Half.BOTTOM).setValue(WATERLOGGED, true), 8);
    }
}
