package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import javax.annotation.Nullable;

public class DoubleCropsBlock extends SoilCropsBlock {
    private final int growingAge;
    public final VoxelShape[] SHAPES;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    public DoubleCropsBlock(PlantType plantType, int growingAge) {
        super(plantType);
        this.growingAge = growingAge;
        this.SHAPES = this.makeShapes();
        this.registerDefaultState(this.defaultBlockState().setValue(HALF, Half.BOTTOM).setValue(this.getAgeProperty(), 0).setValue(PERSISTENT, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        int index = state.getValue(AGE);
        if(index >= this.getAgeReachingTopBlock())
            index = (state.getValue(HALF) == Half.BOTTOM) ? this.getAgeReachingTopBlock() : index + 1;
        return SHAPES[index];
    }

    public VoxelShape[] makeShapes() {
        return new VoxelShape[] {
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HALF);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(this.isBottomCrop(stateIn)) {
            if(facing == Direction.UP) {
                if(facingState.getBlock() == this && !this.isBottomCrop(facingState)) {
                    return stateIn.setValue(PERSISTENT, facingState.getValue(PERSISTENT));
                } else
                    return (stateIn.getValue(AGE) < this.getAgeReachingTopBlock()) ? stateIn : this.getRemovedState(stateIn);
            }
        } else {
            if(facing == Direction.DOWN) {
                if(facingState.getBlock() == this && this.isBottomCrop(facingState)) {
                    return stateIn.setValue(AGE, facingState.getValue(AGE)).setValue(PERSISTENT, facingState.getValue(PERSISTENT));
                } else
                    return this.getRemovedState(stateIn);
            }
        }

        return stateIn;
    }

    @Override
    public void setPlantWithAge(BlockState currentState, LevelAccessor worldIn, BlockPos pos, int newAge) {
        if(currentState.getValue(HALF) == Half.TOP)
            pos = pos.below();
        if(newAge >= this.getAgeReachingTopBlock()) {
            BlockPos posUp = pos.above();
            if(worldIn.getBlockState(posUp).getBlock() == this || worldIn.isEmptyBlock(posUp)) {
                worldIn.setBlock(posUp, currentState.setValue(this.getAgeProperty(), newAge).setValue(HALF, Half.TOP), 10);
            }
        }
        if(newAge < this.getAgeReachingTopBlock() && this.getAge(currentState) == this.getAgeReachingTopBlock()) {
            worldIn.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 10);
        }
        worldIn.setBlock(pos, currentState.setValue(this.getAgeProperty(), newAge).setValue(HALF, Half.BOTTOM), 10);
    }

    @Override
    public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
        super.playerDestroy(worldIn, player, pos, Blocks.AIR.defaultBlockState(), te, stack);
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        Half half = state.getValue(HALF);
        BlockPos otherPos = (half == Half.BOTTOM) ? pos.above() : pos.below();
        BlockState otherState = worldIn.getBlockState(otherPos);
        if(otherState.getBlock() == this) {
            worldIn.setBlock(otherPos, this.getRemovedState(otherState), 35);
            worldIn.levelEvent(player, 2001, otherPos, Block.getId(otherState));
            ItemStack itemstack = player.getMainHandItem();
            if(!worldIn.isClientSide() && !player.isCreative()) {
                Block.dropResources(state, worldIn, pos, null, player, itemstack);
                Block.dropResources(otherState, worldIn, otherPos, null, player, itemstack);
            }
        }

        super.playerWillDestroy(worldIn, pos, state, player);
    }

    public BlockState getRemovedState(BlockState state){
        return Blocks.AIR.defaultBlockState();
    }

    // Only called with Bonemeal
    @Override
    public void growCrops(Level worldIn, BlockPos pos, BlockState state) {
        if(this.isBottomCrop(state)) {
            int newAge = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
            if(newAge > this.getMaxAge())
                newAge = this.getMaxAge();
            if(newAge >= this.getAgeReachingTopBlock()) {
                BlockPos topPos = pos.above();
                if(worldIn.getBlockState(topPos).getBlock() == this || worldIn.isEmptyBlock(topPos)) {
                    state = this.getStateForAge(newAge);
                    worldIn.setBlock(pos, state, 2);
                    worldIn.setBlock(topPos, this.getTopState(state), 2);
                }
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        if(this.isBottomCrop(state)) {
            if(!worldIn.isLoaded(pos) || state.getValue(PERSISTENT))
                return; // Forge: prevent loading unloaded chunks when checking neighbor's light
            if(worldIn.getRawBrightness(pos, 0) >= 9) {
                int i = this.getAge(state);
                if(i < this.getMaxAge()) {
                    float f = getGrowthSpeed(this, worldIn, pos);
                    BlockPos topPos = pos.above();
                    if(worldIn.getBlockState(topPos).getBlock() == this || worldIn.isEmptyBlock(topPos)) {
                        if(random.nextInt((int) (25.0F / f) + 1) == 0) {
                            state = this.getStateForAge(i + 1);
                            worldIn.setBlock(pos, state, 2);
                            if(i + 1 >= this.getAgeReachingTopBlock())
                                worldIn.setBlock(topPos, this.getTopState(state), 2);
                        }
                    }
                }
            }
        }
    }

    public boolean isBottomCrop(BlockState state) {
        if(state.getBlock() instanceof DoubleCropsBlock) {
            return state.getValue(HALF) == Half.BOTTOM;
        }
        return false;
    }

    /**
     * @param bottomState State of the bottom part of the double crop.
     *
     * @return State of the Top part of the crop in input.
     */
    public BlockState getTopState(BlockState bottomState) {
        return bottomState.setValue(HALF, Half.TOP);
    }

    public int getAgeReachingTopBlock() {
        return this.growingAge;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        if(state.getValue(HALF) == Half.TOP)
            return true;
        return super.canSurvive(state, worldIn, pos);
    }
}
