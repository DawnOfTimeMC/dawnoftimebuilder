package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.block.general.WaterSourceTrickleBlock;
import org.dawnoftime.dawnoftime.block.general.WaterTrickleBlock;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.dawnoftime.dawnoftime.util.VoxelShapes;
import org.jetbrains.annotations.NotNull;

public class FaucetBlock extends WaterSourceTrickleBlock {
    public FaucetBlock(final Properties propertiesIn) {
        super(propertiesIn);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStatePropertiesAA.ACTIVATED, false));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        // NOTE: The 4 directional boolean properties (N/E/S/W) must be part of the state container.
        // If WaterSourceTrickleBlock doesn't add them, add them here:
        // builder.add(BlockStateProperties.NORTH, BlockStateProperties.EAST,
        //             BlockStateProperties.SOUTH, BlockStateProperties.WEST);
        builder.add(BlockStatePropertiesAA.ACTIVATED);
    }

    /* ------------------------------------------------------------
     *  Placement fixes (root cause of 'must stand in front'):
     *
     *  - Allow self-replacement: placing the same block into itself should update its state
     *    instead of shifting placement to the adjacent block.
     *  - Pick the side based on exact local click (Option B).
     * ------------------------------------------------------------ */

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
        // Allow stacking onto the same block when holding this item (like candles/pickles/slabs).
        // This prevents vanilla from offsetting placement to the adjacent block.
        return !ctx.isSecondaryUseActive() && ctx.getItemInHand().is(this.asItem());
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        final Level level = ctx.getLevel();
        final BlockPos pos = ctx.getClickedPos();
        final BlockState current = level.getBlockState(pos);

        // When placing onto an existing FaucetBlock (thanks to canBeReplaced),
        // keep the current state and just toggle/add the correct side.
        BlockState out = current.is(this) ? current : this.defaultBlockState();

        Direction target = pickHorizontalByLocalHit(pos, ctx.getClickLocation());
        return out.setValue(prop(target), true);
    }

    /** Map direction → boolean property carried by this block's state (N/E/S/W). */
    private static BooleanProperty prop(Direction d) {
        return switch (d) {
            case NORTH -> BlockStateProperties.NORTH;
            case EAST  -> BlockStateProperties.EAST;
            case SOUTH -> BlockStateProperties.SOUTH;
            case WEST  -> BlockStateProperties.WEST;
            default    -> BlockStateProperties.NORTH; // shouldn't happen for vertical directions
        };
    }

    /** Option B: choose side by local click position relative to block center. */
    private static Direction pickHorizontalByLocalHit(BlockPos pos, Vec3 worldHit) {
        double localX = worldHit.x - pos.getX() - 0.5;
        double localZ = worldHit.z - pos.getZ() - 0.5;
        if (Math.abs(localX) > Math.abs(localZ)) {
            return (localX > 0) ? Direction.EAST : Direction.WEST;
        } else {
            return (localZ > 0) ? Direction.SOUTH : Direction.NORTH;
        }
    }

    /* ------------------------------------------------------------
     *  Water trickle passthrough (unchanged, just left here):
     * ------------------------------------------------------------ */

    @Override
    public boolean[] getWaterTrickleOutPut(BlockState currentState) {
        if (!currentState.getValue(BlockStatePropertiesAA.ACTIVATED)) {
            return new boolean[]{
                    currentState.getValue(BlockStatePropertiesAA.NORTH_TRICKLE),
                    currentState.getValue(BlockStatePropertiesAA.EAST_TRICKLE),
                    currentState.getValue(BlockStatePropertiesAA.SOUTH_TRICKLE),
                    currentState.getValue(BlockStatePropertiesAA.WEST_TRICKLE),
                    currentState.getValue(BlockStatePropertiesAA.CENTER_TRICKLE)
            };
        }
        return super.getWaterTrickleOutPut(currentState);
    }

    /* ------------------------------------------------------------
     *  Activation toggle (left as-is):
     * ------------------------------------------------------------ */

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.isClientSide) {
            world.playSound(player, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, 0.6F);
            world.playSound(player, pos, SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, 0.3F, 1.0F);
        }

        boolean activated = !state.getValue(BlockStatePropertiesAA.ACTIVATED);
        state = state.setValue(BlockStatePropertiesAA.ACTIVATED, activated);

        if (activated) state = state.setValue(BlockStateProperties.UNSTABLE, true);
        world.setBlock(pos, state, 10);
        return InteractionResult.SUCCESS;
    }

    /* ------------------------------------------------------------
     *  Neighbor updates (kept intact):
     * ------------------------------------------------------------ */

    @Override
    public BlockState updateShape(BlockState stateIn, Direction directionIn, BlockState facingStateIn,
                                  LevelAccessor worldIn, BlockPos currentPosIn, BlockPos facingPosIn) {
        BlockState state = super.updateShape(stateIn, directionIn, facingStateIn, worldIn, currentPosIn, facingPosIn);
        boolean lastActivation = state.getValue(BlockStatePropertiesAA.ACTIVATED);

        switch (directionIn) {
            case NORTH -> {
                if (state.getValue(BlockStateProperties.NORTH) && facingStateIn.getBlock() instanceof PoolBlock pb) {
                    int level = facingStateIn.getValue(BlockStatePropertiesAA.LEVEL);
                    state = state.setValue(BlockStatePropertiesAA.ACTIVATED, level >= pb.faucetLevel);
                }
            }
            case SOUTH -> {
                if (state.getValue(BlockStateProperties.SOUTH) && facingStateIn.getBlock() instanceof PoolBlock pb) {
                    int level = facingStateIn.getValue(BlockStatePropertiesAA.LEVEL);
                    state = state.setValue(BlockStatePropertiesAA.ACTIVATED, level >= pb.faucetLevel);
                }
            }
            case EAST -> {
                if (state.getValue(BlockStateProperties.EAST) && facingStateIn.getBlock() instanceof PoolBlock pb) {
                    int level = facingStateIn.getValue(BlockStatePropertiesAA.LEVEL);
                    state = state.setValue(BlockStatePropertiesAA.ACTIVATED, level >= pb.faucetLevel);
                }
            }
            case WEST -> {
                if (state.getValue(BlockStateProperties.WEST) && facingStateIn.getBlock() instanceof PoolBlock pb) {
                    int level = facingStateIn.getValue(BlockStatePropertiesAA.LEVEL);
                    state = state.setValue(BlockStatePropertiesAA.ACTIVATED, level >= pb.faucetLevel);
                }
            }
            case DOWN -> {
                if (state.getValue(BlockStatePropertiesAA.ACTIVATED)) {
                    if (facingStateIn.getBlock() instanceof WaterTrickleBlock) {
                        state = state.setValue(BlockStateProperties.UNSTABLE, false);
                    } else {
                        state = state.setValue(BlockStateProperties.UNSTABLE, true);
                    }
                }
            }
        }

        if (worldIn.isClientSide() && state.getValue(BlockStatePropertiesAA.ACTIVATED) && !lastActivation) {
            worldIn.playSound(null, currentPosIn, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, 0.6F);
            worldIn.playSound(null, currentPosIn, SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, 0.3F, 1.0F);
        }

        if (!worldIn.isClientSide() && state.getValue(BlockStatePropertiesAA.ACTIVATED) != lastActivation) {
            worldIn.scheduleTick(currentPosIn, this, 5);
        }

        return state;
    }

    /* ------------------------------------------------------------
     *  Shapes (precomposed + index):
     * ------------------------------------------------------------ */

    @Override
    public int getShapeIndex(@NotNull BlockState state, @NotNull BlockGetter worldIn,
                             @NotNull BlockPos pos, @NotNull CollisionContext context) {
        int index = 0;
        if (state.getValue(BlockStateProperties.SOUTH)) index += 1;
        if (state.getValue(BlockStateProperties.WEST))  index += 2;
        if (state.getValue(BlockStateProperties.NORTH)) index += 4;
        if (state.getValue(BlockStateProperties.EAST))  index += 8;
        if (index > 14) index = 0; // 15 -> 0 (all sides)
        return index;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        boolean s = state.getValue(BlockStateProperties.SOUTH);
        boolean w = state.getValue(BlockStateProperties.WEST);
        boolean n = state.getValue(BlockStateProperties.NORTH);
        boolean e = state.getValue(BlockStateProperties.EAST);

        if (!s && !w && !n && !e) {
            return Shapes.block(); // no sides at all
        }
        int idx = getShapeIndex(state, world, pos, context);
        return VoxelShapes.FAUCET_FOUR_SIDES[idx];
    }

    /* ------------------------------------------------------------
     *  Rotation / Mirror (kept):
     * ------------------------------------------------------------ */

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        switch (rot) {
            case CLOCKWISE_90 -> {
                return state.setValue(BlockStateProperties.NORTH, state.getValue(BlockStateProperties.WEST))
                        .setValue(BlockStateProperties.EAST,  state.getValue(BlockStateProperties.NORTH))
                        .setValue(BlockStateProperties.SOUTH, state.getValue(BlockStateProperties.EAST))
                        .setValue(BlockStateProperties.WEST,  state.getValue(BlockStateProperties.SOUTH));
            }
            case COUNTERCLOCKWISE_90 -> {
                return state.setValue(BlockStateProperties.NORTH, state.getValue(BlockStateProperties.EAST))
                        .setValue(BlockStateProperties.EAST,  state.getValue(BlockStateProperties.SOUTH))
                        .setValue(BlockStateProperties.SOUTH, state.getValue(BlockStateProperties.WEST))
                        .setValue(BlockStateProperties.WEST,  state.getValue(BlockStateProperties.NORTH));
            }
            case CLOCKWISE_180 -> {
                return state.setValue(BlockStateProperties.NORTH, state.getValue(BlockStateProperties.SOUTH))
                        .setValue(BlockStateProperties.EAST,  state.getValue(BlockStateProperties.WEST))
                        .setValue(BlockStateProperties.SOUTH, state.getValue(BlockStateProperties.NORTH))
                        .setValue(BlockStateProperties.WEST,  state.getValue(BlockStateProperties.EAST));
            }
            default -> {
                return state;
            }
        }
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        switch (mirrorIn) {
            case LEFT_RIGHT -> {
                return state.setValue(BlockStateProperties.NORTH, state.getValue(BlockStateProperties.SOUTH))
                        .setValue(BlockStateProperties.EAST,  state.getValue(BlockStateProperties.WEST))
                        .setValue(BlockStateProperties.SOUTH, state.getValue(BlockStateProperties.NORTH))
                        .setValue(BlockStateProperties.WEST,  state.getValue(BlockStateProperties.EAST));
            }
            case FRONT_BACK -> {
                // No-op mirror in this mapping; keep as-is.
                return state;
            }
            default -> {
                return state;
            }
        }
    }
}
