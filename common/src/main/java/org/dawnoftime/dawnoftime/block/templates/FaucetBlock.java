package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.block.general.WaterSourceTrickleBlock;
import org.dawnoftime.dawnoftime.block.general.WaterTrickleBlock;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FaucetBlock extends WaterSourceTrickleBlock {
    private static final VoxelShape SHAPE_NORTH = Block.box(6, 10, 0, 10, 14, 6);
    private static final VoxelShape SHAPE_SOUTH = Block.box(6, 10, 10, 10, 14, 16);
    private static final VoxelShape SHAPE_EAST = Block.box(10, 10, 6, 16, 14, 10);
    private static final VoxelShape SHAPE_WEST = Block.box(0, 10, 6, 6, 14, 10);

    public FaucetBlock(final Properties propertiesIn) {
        super(propertiesIn);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStatePropertiesAA.ACTIVATED, false));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStatePropertiesAA.ACTIVATED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext contextIn) {
        final Level level = contextIn.getLevel();
        final BlockPos pos = contextIn.getClickedPos();
        final BlockState currentState = level.getBlockState(pos);
        final Direction targetDirection = contextIn.getHorizontalDirection();

        // If the current block is a WaterTrickle, we keep its state to add a new trickle in this block.
        BlockState outState = currentState.is(this) ? currentState : this.defaultBlockState();
        // We add a trickle for the target direction and the end type.
        return outState.setValue(getPropertyFromDirection(targetDirection), true);
    }

    @Override
    public boolean[] getWaterTrickleOutPut(BlockState currentState) {
        if(!currentState.getValue(BlockStatePropertiesAA.ACTIVATED)) {
            return new boolean[] {
                    currentState.getValue(BlockStatePropertiesAA.NORTH_TRICKLE),
                    currentState.getValue(BlockStatePropertiesAA.EAST_TRICKLE),
                    currentState.getValue(BlockStatePropertiesAA.SOUTH_TRICKLE),
                    currentState.getValue(BlockStatePropertiesAA.WEST_TRICKLE),
                    currentState.getValue(BlockStatePropertiesAA.CENTER_TRICKLE) };
        }
        return super.getWaterTrickleOutPut(currentState);
    }

    @Override
    public void attack(BlockState state, Level level, BlockPos pos, Player player) {
        if (level.isClientSide()) return;

        if (player.isShiftKeyDown()) {
            // Shift: casser tout le bloc
            level.destroyBlock(pos, true);
        } else {
            // Détermine quel robinet casser selon la direction regardée
            Direction targetDirection = getTargetedFaucet(state, pos, player);
            if (targetDirection != null) {
                BooleanProperty property = getPropertyFromDirection(targetDirection);
                if (state.getValue(property)) {
                    BlockState newState = state.setValue(property, false);

                    // Vérifie s'il reste des robinets
                    if (!newState.getValue(BlockStateProperties.NORTH) &&
                            !newState.getValue(BlockStateProperties.SOUTH) &&
                            !newState.getValue(BlockStateProperties.EAST) &&
                            !newState.getValue(BlockStateProperties.WEST)) {
                        // Plus de robinets, détruit complètement
                        level.destroyBlock(pos, true);
                    } else {
                        // Met à jour avec le nouvel état
                        level.setBlock(pos, newState, 3);
                        // Drop un item
                        popResource(level, pos, new ItemStack(this.asItem()));
                    }
                }
            }
        }
    }

    private Direction getTargetedFaucet(BlockState state, BlockPos pos, Player player) {
        double playerX = player.getX() - pos.getX() - 0.5;
        double playerZ = player.getZ() - pos.getZ() - 0.5;

        // Détermine la direction basée sur la position du joueur
        if (Math.abs(playerX) > Math.abs(playerZ)) {
            if (playerX > 0 && state.getValue(BlockStateProperties.EAST)) return Direction.EAST;
            if (playerX < 0 && state.getValue(BlockStateProperties.WEST)) return Direction.WEST;
        } else {
            if (playerZ > 0 && state.getValue(BlockStateProperties.SOUTH)) return Direction.SOUTH;
            if (playerZ < 0 && state.getValue(BlockStateProperties.NORTH)) return Direction.NORTH;
        }

        // Fallback: trouve le premier robinet disponible
        if (state.getValue(BlockStateProperties.NORTH)) return Direction.NORTH;
        if (state.getValue(BlockStateProperties.EAST)) return Direction.EAST;
        if (state.getValue(BlockStateProperties.SOUTH)) return Direction.SOUTH;
        if (state.getValue(BlockStateProperties.WEST)) return Direction.WEST;

        return null;
    }

    @Override
    public InteractionResult use(BlockState blockStateIn, Level worldIn, BlockPos blockPosIn, Player playerEntityIn, InteractionHand handIn, BlockHitResult hitIn) {
        if (worldIn.isClientSide) {
            // On joue un son de clic type levier + eau de minecraft vanilla
            worldIn.playSound(playerEntityIn, blockPosIn, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, 0.6F);
            worldIn.playSound(playerEntityIn, blockPosIn, SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, 0.3F, 1.0F);
        }

        final ItemStack mainHandItemStack = playerEntityIn.getMainHandItem();
        if(!mainHandItemStack.isEmpty() && mainHandItemStack.getItem() == this.asItem()) {
            return InteractionResult.PASS;
        }
        boolean activated = !blockStateIn.getValue(BlockStatePropertiesAA.ACTIVATED);
        blockStateIn = blockStateIn.setValue(BlockStatePropertiesAA.ACTIVATED, activated);

        if(activated) {
            blockStateIn = blockStateIn.setValue(BlockStateProperties.UNSTABLE, true);
        }

        worldIn.setBlock(blockPosIn, blockStateIn, 10);

        // Si le joueur n'est pas en créatif, on consomme l'item
        if (!playerEntityIn.isCreative()) {
            ItemStack itemStack = playerEntityIn.getItemInHand(handIn);
            if (itemStack.getCount() > 1) {
                itemStack.shrink(1);
            } else {
                playerEntityIn.setItemInHand(handIn, ItemStack.EMPTY);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction directionIn, BlockState facingStateIn, LevelAccessor worldIn, BlockPos currentPosIn, BlockPos facingPosIn) {
        BlockState state = super.updateShape(stateIn, directionIn, facingStateIn, worldIn, currentPosIn, facingPosIn);
        boolean lastActivation = state.getValue(BlockStatePropertiesAA.ACTIVATED);

        switch (directionIn) {
            case NORTH -> {
                if (state.getValue(BlockStateProperties.NORTH) && facingStateIn.getBlock() instanceof PoolBlock) {
                    int level = facingStateIn.getValue(BlockStatePropertiesAA.LEVEL);
                    state = state.setValue(BlockStatePropertiesAA.ACTIVATED, level >= ((PoolBlock) facingStateIn.getBlock()).faucetLevel);
                }
            }
            case SOUTH -> {
                if (state.getValue(BlockStateProperties.SOUTH) && facingStateIn.getBlock() instanceof PoolBlock) {
                    int level = facingStateIn.getValue(BlockStatePropertiesAA.LEVEL);
                    state = state.setValue(BlockStatePropertiesAA.ACTIVATED, level >= ((PoolBlock) facingStateIn.getBlock()).faucetLevel);
                }
            }
            case EAST -> {
                if (state.getValue(BlockStateProperties.EAST) && facingStateIn.getBlock() instanceof PoolBlock) {
                    int level = facingStateIn.getValue(BlockStatePropertiesAA.LEVEL);
                    state = state.setValue(BlockStatePropertiesAA.ACTIVATED, level >= ((PoolBlock) facingStateIn.getBlock()).faucetLevel);
                }
            }
            case WEST -> {
                if (state.getValue(BlockStateProperties.WEST) && facingStateIn.getBlock() instanceof PoolBlock) {
                    int level = facingStateIn.getValue(BlockStatePropertiesAA.LEVEL);
                    state = state.setValue(BlockStatePropertiesAA.ACTIVATED, level >= ((PoolBlock) facingStateIn.getBlock()).faucetLevel);
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

        // Si passe de non activé à activé et côté client, on execute un son
        if (worldIn.isClientSide() && state.getValue(BlockStatePropertiesAA.ACTIVATED) && !lastActivation) {
            worldIn.playSound(null, currentPosIn, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, 0.6F);
            worldIn.playSound(null, currentPosIn, SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, 0.3F, 1.0F);
        }

        if(!worldIn.isClientSide() && state.getValue(BlockStatePropertiesAA.ACTIVATED) != lastActivation) {
            (worldIn).scheduleTick(currentPosIn, this, 5);
        }

        return state;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        List<VoxelShape> shapes = new ArrayList<>();
        if (state.getValue(BlockStateProperties.NORTH)) shapes.add(SHAPE_NORTH);
        if (state.getValue(BlockStateProperties.SOUTH)) shapes.add(SHAPE_SOUTH);
        if (state.getValue(BlockStateProperties.EAST)) shapes.add(SHAPE_EAST);
        if (state.getValue(BlockStateProperties.WEST)) shapes.add(SHAPE_WEST);

        if (shapes.isEmpty()) return Shapes.block();

        VoxelShape result = shapes.get(0);
        for (int i = 1; i < shapes.size(); i++) {
            result = Shapes.or(result, shapes.get(i));
        }
        return result;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot)
    {
        switch (rot) {
            case CLOCKWISE_90 -> {
                return state.setValue(BlockStateProperties.NORTH, state.getValue(BlockStateProperties.WEST))
                        .setValue(BlockStateProperties.EAST, state.getValue(BlockStateProperties.NORTH))
                        .setValue(BlockStateProperties.SOUTH, state.getValue(BlockStateProperties.EAST))
                        .setValue(BlockStateProperties.WEST, state.getValue(BlockStateProperties.SOUTH));
            }
            case COUNTERCLOCKWISE_90 -> {
                return state.setValue(BlockStateProperties.NORTH, state.getValue(BlockStateProperties.EAST))
                        .setValue(BlockStateProperties.EAST, state.getValue(BlockStateProperties.SOUTH))
                        .setValue(BlockStateProperties.SOUTH, state.getValue(BlockStateProperties.WEST))
                        .setValue(BlockStateProperties.WEST, state.getValue(BlockStateProperties.NORTH));
            }
            case CLOCKWISE_180 -> {
                return state.setValue(BlockStateProperties.NORTH, state.getValue(BlockStateProperties.SOUTH))
                        .setValue(BlockStateProperties.EAST, state.getValue(BlockStateProperties.WEST))
                        .setValue(BlockStateProperties.SOUTH, state.getValue(BlockStateProperties.NORTH))
                        .setValue(BlockStateProperties.WEST, state.getValue(BlockStateProperties.EAST));
            }
            default -> {
                return state;
            }
        }
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn)
    {
        switch (mirrorIn) {
            case LEFT_RIGHT -> {
                return state.setValue(BlockStateProperties.NORTH, state.getValue(BlockStateProperties.SOUTH))
                        .setValue(BlockStateProperties.EAST, state.getValue(BlockStateProperties.WEST))
                        .setValue(BlockStateProperties.SOUTH, state.getValue(BlockStateProperties.NORTH))
                        .setValue(BlockStateProperties.WEST, state.getValue(BlockStateProperties.EAST));
            }
            case FRONT_BACK -> {
                return state.setValue(BlockStateProperties.NORTH, state.getValue(BlockStateProperties.NORTH))
                        .setValue(BlockStateProperties.EAST, state.getValue(BlockStateProperties.EAST))
                        .setValue(BlockStateProperties.SOUTH, state.getValue(BlockStateProperties.SOUTH))
                        .setValue(BlockStateProperties.WEST, state.getValue(BlockStateProperties.WEST));
            }
            default -> {
                return state;
            }
        }
    }
}