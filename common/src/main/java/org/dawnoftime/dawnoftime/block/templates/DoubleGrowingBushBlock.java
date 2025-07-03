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
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class DoubleGrowingBushBlock extends GrowingBushBlock {
    public final int growingAge;
    public final VoxelShape[] TOP_SHAPES;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    public DoubleGrowingBushBlock(PlantType plantType, int cutAge, int growingAge) {
        super(plantType, cutAge);
        this.growingAge = growingAge;
        this.TOP_SHAPES = this.makeTopShapes();
        this.registerDefaultState(this.defaultBlockState().setValue(HALF, Half.BOTTOM));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if(state.getValue(HALF) == Half.BOTTOM) {
            return SHAPES[Math.min(this.getAge(state), SHAPES.length - 1)];
        } else
            return TOP_SHAPES[Math.min(state.getValue(this.getCutProperty()) ? this.getMaxAge() : this.getAge(state), TOP_SHAPES.length - 1)];
    }

    /**
     * @return Stores default VoxelShape (growingAge 2) with index : <p/>
     * 0 : stage 0 bottom 6px <p/>
     * 1 : stage 1 bottom 16px <p/>
     * 2 : stage 2 bottom 16px <p/>
     * 3 : stage 3+ bottom 16px <p/>
     */
    @Override
    public VoxelShape[] makeShapes() {
        return new VoxelShape[] {
                Block.box(5.5D, 0.0D, 5.5D, 10.5D, 6.0D, 10.5D),
                Block.box(4.5D, 0.0D, 4.5D, 11.5D, 16.0D, 11.5D),
                Shapes.or(
                        Block.box(6.0D, 0.0D, 6.0D, 10.0D, 12.0D, 10.0D),
                        Block.box(4.0D, 12.0D, 4.0D, 12.0D, 16.0D, 12.0D)),
                Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D),
        };
    }

    /**
     * @return Stores default VoxelShape for Top half with index : <p/>
     * 0 : stage 0 top empty <p/>
     * 1 : stage 1 top empty <p/>
     * 2 : stage 2 top 5px <p/>
     * 3 : stage 3 top 10px <p/>
     * 4 : stage 4+ OR Cut top 15px <p/>
     */
    public VoxelShape[] makeTopShapes() {
        return new VoxelShape[] {
                Shapes.empty(),
                Shapes.empty(),
                Block.box(4.0D, 0.0D, 4.0D, 12.0D, 5.0D, 12.0D),
                Block.box(1.0D, 0.0D, 1.0D, 15.0D, 10.0D, 15.0D),
                Block.box(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D),
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HALF);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        Half half = stateIn.getValue(HALF);
        if(facing.getAxis() == Direction.Axis.Y && half == Half.BOTTOM == (facing == Direction.UP)) {
            if(facingState.getBlock() == this && facingState.getValue(HALF) != half) {
                stateIn = stateIn.setValue(PERSISTENT, facingState.getValue(PERSISTENT));
                return half == Half.BOTTOM ? stateIn : stateIn.setValue(this.getAgeProperty(), this.getAge(facingState));
            } else if(half == Half.BOTTOM && this.getAge(stateIn) < this.growingAge)
                return stateIn;
            return Blocks.AIR.defaultBlockState();
        } else {
            return half == Half.BOTTOM && facing == Direction.DOWN && this.canSurvive(stateIn, worldIn, currentPos.below()) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        }
    }

    @Override
    public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
        super.playerDestroy(worldIn, player, pos, Blocks.AIR.defaultBlockState(), te, stack);
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        Half half = state.getValue(HALF);
        BlockPos otherPos = (half == Half.BOTTOM) ? pos.above() : pos.below();
        BlockState blockstate = worldIn.getBlockState(otherPos);
        if(blockstate.getBlock() == this) {
            worldIn.setBlock(otherPos, Blocks.AIR.defaultBlockState(), 35);
            worldIn.levelEvent(player, 2001, otherPos, Block.getId(blockstate));
            ItemStack itemstack = player.getMainHandItem();
            if(!worldIn.isClientSide() && !player.isCreative()) {
                Block.dropResources(state, worldIn, pos, null, player, itemstack);
                Block.dropResources(blockstate, worldIn, otherPos, null, player, itemstack);
            }
        }

        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public void harvestWithoutBreaking(BlockState state, Level worldIn, BlockPos pos, ItemStack itemStackHand, String blockName, float dropMultiplier) {
        boolean isTop = state.getValue(HALF) == Half.TOP;
        worldIn.setBlock(isTop ? pos.below() : pos.above(), state.setValue(this.getAgeProperty(), this.cutAge).setValue(this.getCutProperty(), true).setValue(HALF, isTop ? Half.BOTTOM : Half.TOP), 2);
        super.harvestWithoutBreaking(state, worldIn, pos, itemStackHand, blockName, dropMultiplier);
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
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
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
                            if(i + 1 >= this.growingAge)
                                worldIn.setBlock(topPos, this.getTopState(state), 2);
                        }
                    }
                }
            }
        }
    }

    public boolean isBottomCrop(BlockState state) {
        if(state.getBlock() instanceof DoubleGrowingBushBlock) {
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
        if(state.getValue(HALF) == Half.TOP) {
            BlockState stateUnder = worldIn.getBlockState(pos.below());
            if(stateUnder.getBlock() == this)
                return stateUnder.getValue(HALF) == Half.BOTTOM;
        }
        return super.canSurvive(state, worldIn, pos);
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
}
