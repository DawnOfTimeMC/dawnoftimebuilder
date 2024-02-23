package org.dawnoftimebuilder.block.general;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.block.templates.BasePoolBlock;
import org.dawnoftimebuilder.block.templates.BlockDoTB;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties.WaterTrickleEnd;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.InteractionHand.MAIN_HAND;

public abstract class WaterTrickleBlock extends BlockDoTB {
    public WaterTrickleBlock(final Properties propertiesIn) {
        super(propertiesIn);
        this.registerDefaultState(this.defaultBlockState().setValue(DoTBBlockStateProperties.NORTH_TRICKLE, false).setValue(DoTBBlockStateProperties.EAST_TRICKLE, false).setValue(DoTBBlockStateProperties.SOUTH_TRICKLE, false)
                .setValue(DoTBBlockStateProperties.WEST_TRICKLE, false).setValue(DoTBBlockStateProperties.CENTER_TRICKLE, false).setValue(BlockStateProperties.UNSTABLE, true)
                .setValue(DoTBBlockStateProperties.WATER_TRICKLE_END, WaterTrickleEnd.FADE));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(DoTBBlockStateProperties.NORTH_TRICKLE, DoTBBlockStateProperties.EAST_TRICKLE, DoTBBlockStateProperties.SOUTH_TRICKLE, DoTBBlockStateProperties.WEST_TRICKLE, DoTBBlockStateProperties.CENTER_TRICKLE,
                BlockStateProperties.UNSTABLE, DoTBBlockStateProperties.WATER_TRICKLE_END);
    }

    @Override
    public void setPlacedBy(final Level pLevel, final BlockPos pPos, final BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);

        if(!pLevel.isClientSide() && pState.getValue(BlockStateProperties.UNSTABLE)) {
            pLevel.scheduleTick(pPos, this, 5);
        }
    }

    @Override
    public BlockState updateShape(final BlockState stateIn, final Direction directionIn, final BlockState facingStateIn, final LevelAccessor worldIn, final BlockPos currentPosIn, final BlockPos facingPosIn) {
        // If a block above or under changed, the water trickle is set UNSTABLE. Unstable water trickles are updated on the next randomTick.
        if(directionIn == Direction.UP && facingStateIn.getBlock() instanceof WaterTrickleBlock) {
            if(!worldIn.isClientSide()) {
                (worldIn).scheduleTick(currentPosIn, this, 5);
            }
            return stateIn.setValue(BlockStateProperties.UNSTABLE, true);
        }
        if(directionIn == Direction.DOWN && worldIn instanceof Level) {
            if(!worldIn.isClientSide()) {
                (worldIn).scheduleTick(currentPosIn, this, 5);
            }
            return stateIn.setValue(DoTBBlockStateProperties.WATER_TRICKLE_END, this.getWaterTrickleEnd((Level) worldIn, facingPosIn, facingStateIn)).setValue(BlockStateProperties.UNSTABLE, true);
        }
        return stateIn;
    }

    /**
     * Creates an array used to get the list of the water trickles produced by this block.
     *
     * @param currentState BlockState of the WaterTrickle.
     *
     * @return A boolean array that contains 5 booleans {north, east, south, west center}. Each true value correspond to a water trickle at the corresponding position.
     */
    public boolean[] getWaterTrickleOutPut(final BlockState currentState) {
        return new boolean[] {
                currentState.getValue(DoTBBlockStateProperties.NORTH_TRICKLE), currentState.getValue(DoTBBlockStateProperties.EAST_TRICKLE), currentState.getValue(DoTBBlockStateProperties.SOUTH_TRICKLE),
                currentState.getValue(DoTBBlockStateProperties.WEST_TRICKLE), currentState.getValue(DoTBBlockStateProperties.CENTER_TRICKLE) };
    }

    /**
     * Synchronize the water trickles with the water trickles produced by the block above.
     *
     * @param currentState Actual state of the block to synchronize.
     * @param aboveState   BlockState of the block above.
     *
     * @return An updated BlockState with the updated trickles. Can be AIR blockstate if the block should disappear.
     */
    public BlockState inheritWaterTrickles(final BlockState currentState, final BlockState aboveState) {
        final boolean[] trickles = ((WaterTrickleBlock) aboveState.getBlock()).getWaterTrickleOutPut(aboveState);
        final BooleanProperty[] properties = {
                DoTBBlockStateProperties.NORTH_TRICKLE, DoTBBlockStateProperties.EAST_TRICKLE, DoTBBlockStateProperties.SOUTH_TRICKLE, DoTBBlockStateProperties.WEST_TRICKLE, DoTBBlockStateProperties.CENTER_TRICKLE };
        int i = 0;
        BlockState updatedState = currentState;
        for(final BooleanProperty property : properties) {
            if(currentState.getValue(property) != trickles[i]) {
                updatedState = updatedState.setValue(property, trickles[i]).setValue(BlockStateProperties.UNSTABLE, true);
            }
            i++;
        }
        return updatedState;
    }

    @Override
    public void tick(BlockState state, final ServerLevel world, final BlockPos pos, final RandomSource rand) {
        super.tick(state, world, pos, rand);
        // We consider that this Water Trickle won't be unstable by the end of this random tick.
        state = state.setValue(BlockStateProperties.UNSTABLE, false);
        final BlockPos bottomPos = pos.below();
        BlockState bottomState = world.getBlockState(bottomPos);
        final BlockPos abovePos = pos.above();
        final BlockState aboveState = world.getBlockState(abovePos);

        // First we check if the block under has trickles. If yes, we check if there is some difference, and make it unstable if yes.
        if(bottomState.getBlock() instanceof WaterTrickleBlock) {
            final BlockState updatedState = this.inheritWaterTrickles(bottomState, state);
            if(updatedState.getValue(BlockStateProperties.UNSTABLE)) {
                world.setBlock(bottomPos, bottomState.setValue(BlockStateProperties.UNSTABLE, true), 10);
            }
            // If the block under is not a water trickle and can be replaced and is not liquid, we put an unstable Flowing Water Trickle.
        } else if(bottomState.canBeReplaced(this.generateContext(world, bottomPos)) && world.getFluidState(bottomPos).getType().equals(Fluids.EMPTY)) {
            bottomState = this.createFlowingTrickle(bottomState, this.getWaterTrickleOutPut(state), world, bottomPos);
            // If the block can contain fluid, we fill it with water.
        } else if(bottomState.getBlock() instanceof LiquidBlockContainer && ((LiquidBlockContainer) bottomState.getBlock()).canPlaceLiquid(world, bottomPos, bottomState, Fluids.WATER)) {
            ((LiquidBlockContainer) bottomState.getBlock()).placeLiquid(world, bottomPos, bottomState, Fluids.WATER.getSource(false));
        }

        final BlockState updatedState = this.updateWaterTrickle(world, state, bottomPos, bottomState, aboveState);
        world.setBlock(pos, updatedState, 10);
    }

    public BlockState updateWaterTrickle(final Level world, BlockState currentState, final BlockPos bottomPos, final BlockState bottomState, final BlockState aboveState) {
        // We update the water trickle end, now that the bottom block has been updated.
        final WaterTrickleEnd lowerEnd = this.getWaterTrickleEnd(world, bottomPos, bottomState);
        currentState = currentState.setValue(DoTBBlockStateProperties.WATER_TRICKLE_END, lowerEnd);

        // Finally, we synchronize this water trickle with the block above. If it changes, this block stays unstable.
        if(aboveState.getBlock() instanceof WaterTrickleBlock) {
            currentState = this.inheritWaterTrickles(currentState, aboveState);
        } else {
            currentState = this.inheritWaterTrickles(currentState, this.defaultBlockState());
        }

        return currentState;
    }

    /**
     * Generates a BlockState of Water Trickle based on the current block, and set it in the world if needed.
     *
     * @param currentState    BlockState of the current block. Will be returned by the function.
     * @param trickles        Array of booleans that correspond to each of the 5 trickles.
     * @param world           Level in which the Water Trickle must be set.
     * @param waterTricklePos BlockPos where the Trickle must be placed.
     *
     * @return The new BlockState of the block (either a trickle or the old state).
     */
    public BlockState createFlowingTrickle(final BlockState currentState, final boolean[] trickles, final Level world, final BlockPos waterTricklePos) {
        final BooleanProperty[] properties = {
                DoTBBlockStateProperties.NORTH_TRICKLE, DoTBBlockStateProperties.EAST_TRICKLE, DoTBBlockStateProperties.SOUTH_TRICKLE, DoTBBlockStateProperties.WEST_TRICKLE, DoTBBlockStateProperties.CENTER_TRICKLE };
        int i = 0;
        int numberOfTrickle = 0;
        BlockState waterTrickleState = DoTBBlocksRegistry.WATER_FLOWING_TRICKLE.get().defaultBlockState();
        // Creates the BlockState of the trickle based on the source block.
        for(final BooleanProperty property : properties) {
            waterTrickleState = waterTrickleState.setValue(property, trickles[i]);
            if(trickles[i]) {
                numberOfTrickle++;
            }
            i++;
        }
        // If the number of trickle is still null, then we don't create anything.
        if(numberOfTrickle > 0) {
            world.setBlock(waterTricklePos, waterTrickleState, 10);
            return waterTrickleState;
        }
        return currentState;
    }

    @Override
    public void animateTick(final BlockState state, final Level worldIn, final BlockPos pos, final RandomSource rand) {
        super.animateTick(state, worldIn, pos, rand);
        final boolean[] trickles = this.getWaterTrickleOutPut(state);
        if(state.getValue(DoTBBlockStateProperties.WATER_TRICKLE_END) == WaterTrickleEnd.SPLASH) {
            this.spawnFullParticles(worldIn, pos, trickles[0], rand, 0.5D, 0.4D);
            this.spawnFullParticles(worldIn, pos, trickles[1], rand, 0.6D, 0.5D);
            this.spawnFullParticles(worldIn, pos, trickles[2], rand, 0.5D, 0.6D);
            this.spawnFullParticles(worldIn, pos, trickles[3], rand, 0.5D, 0.6D);
            this.spawnFullParticles(worldIn, pos, trickles[4], rand, 0.5D, 0.5D);

            return;
        }

        final BlockState belowState = worldIn.getBlockState(pos.below());

        if(belowState.getBlock() instanceof BasePoolBlock && belowState.getValue(DoTBBlockStateProperties.LEVEL) > ((BasePoolBlock) belowState.getBlock()).faucetLevel) {
            this.spawnLimitedParticles(worldIn, pos, trickles[0], rand, 0.5D, 0.4D);
            this.spawnLimitedParticles(worldIn, pos, trickles[1], rand, 0.6D, 0.5D);
            this.spawnLimitedParticles(worldIn, pos, trickles[2], rand, 0.5D, 0.6D);
            this.spawnLimitedParticles(worldIn, pos, trickles[3], rand, 0.5D, 0.6D);
            this.spawnLimitedParticles(worldIn, pos, trickles[4], rand, 0.5D, 0.5D);
        }
    }

    private void spawnLimitedParticles(final Level worldIn, final BlockPos pos, final boolean isOn, final RandomSource rand, final double xOffset, final double zOffset) {
        if(isOn) {
            double offset = 0.75D;
            worldIn.addParticle(ParticleTypes.BUBBLE_POP, true, pos.getX() + xOffset + (rand.nextDouble() * offset - offset / 2.0D), pos.getY() + 0.1D, pos.getZ() + zOffset + (rand.nextDouble() * offset - offset / 2.0D), 0.0125D, 0.075D,
                    0.0125D);

            offset = 0.60D;
            worldIn.addParticle(ParticleTypes.CLOUD, true, pos.getX() + xOffset + (rand.nextDouble() * offset - offset / 2.0D), pos.getY() + 0.0D, pos.getZ() + zOffset + (rand.nextDouble() * offset - offset / 2.0D), 0.0005D, 0.010D,
                    0.0005D);
        }
    }

    private void spawnFullParticles(final Level worldIn, final BlockPos pos, final boolean isOn, final RandomSource rand, final double xOffset, final double zOffset) {
        if(isOn) {
            double offset;
            for(int i = 0; i < 4; i++) {
                offset = 0.75D;
                worldIn.addParticle(ParticleTypes.BUBBLE_POP, true, pos.getX() + xOffset + (rand.nextDouble() * offset - offset / 2.0D), pos.getY() + 0.1D, pos.getZ() + zOffset + (rand.nextDouble() * offset - offset / 2.0D), 0.0125D,
                        0.075D, 0.0125D);

                offset = 0.60D;
                worldIn.addParticle(ParticleTypes.CLOUD, true, pos.getX() + xOffset + (rand.nextDouble() * offset - offset / 2.0D), pos.getY() + 0.0D, pos.getZ() + zOffset + (rand.nextDouble() * offset - offset / 2.0D), 0.0005D, 0.010D,
                        0.0005D);
            }
        }
    }

    protected WaterTrickleEnd getWaterTrickleEnd(final Level level, final BlockPos bottomPos, final BlockState bottomState) {
        if(bottomState.getBlock() instanceof WaterTrickleBlock) {
            return WaterTrickleEnd.STRAIGHT;
        }
        // If the face under the water trickle is full or if there is a fluid, there is a splash effect.
        if(!level.getFluidState(bottomPos).getType().equals(Fluids.EMPTY)) {
            return WaterTrickleEnd.SPLASH;
        }
        return WaterTrickleEnd.FADE;
    }

    private BlockPlaceContext generateContext(final Level level, final BlockPos fromPos) {
        final Vec3 vec = new Vec3(fromPos.getX() + 0.5D, fromPos.getY(), fromPos.getZ() + 0.5D);
        return new BlockPlaceContext(level, null, MAIN_HAND, ItemStack.EMPTY, new BlockHitResult(vec, Direction.DOWN, fromPos, false));
    }

    protected static BooleanProperty getPropertyFromDirection(final Direction facing) {
        switch(facing) {
            default:
                return DoTBBlockStateProperties.CENTER;
            case NORTH:
                return BlockStateProperties.NORTH;
            case SOUTH:
                return BlockStateProperties.SOUTH;
            case WEST:
                return BlockStateProperties.WEST;
            case EAST:
                return BlockStateProperties.EAST;
        }
    }

    @Override
    public PushReaction getPistonPushReaction(final BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }

    @Override
    public int getLightBlock(final BlockState p_200011_1_In, final BlockGetter p_200011_2_In, final BlockPos p_200011_3_In) {
        return 1;
    }

    @Override
    public boolean useShapeForLightOcclusion(final BlockState p_220074_1_In) {
        return false;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
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