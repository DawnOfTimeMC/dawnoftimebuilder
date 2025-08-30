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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
import org.dawnoftime.dawnoftime.DoTBCommon;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA.VerticalLimitedConnection;

import java.util.List;
import java.util.ArrayList;

public class WaterJetBlock extends BlockDoT {

    // Hitbox séparées pour chaque position de robinet
    private static final VoxelShape SHAPE_UP = Block.box(6, 10, 6, 10, 16, 10);
    private static final VoxelShape SHAPE_DOWN = Block.box(6, 0, 6, 10, 6, 10);

    // Hitbox séparées haut/bas pour les faces horizontales
    private static final VoxelShape SHAPE_NORTH_TOP = Block.box(6, 10, 0, 10, 14, 6);
    private static final VoxelShape SHAPE_NORTH_BOTTOM = Block.box(6, 2, 0, 10, 6, 6);
    private static final VoxelShape SHAPE_SOUTH_TOP = Block.box(6, 10, 10, 10, 14, 16);
    private static final VoxelShape SHAPE_SOUTH_BOTTOM = Block.box(6, 2, 10, 10, 6, 16);
    private static final VoxelShape SHAPE_EAST_TOP = Block.box(10, 10, 6, 16, 14, 10);
    private static final VoxelShape SHAPE_EAST_BOTTOM = Block.box(10, 2, 6, 16, 6, 10);
    private static final VoxelShape SHAPE_WEST_TOP = Block.box(0, 10, 6, 6, 14, 10);
    private static final VoxelShape SHAPE_WEST_BOTTOM = Block.box(0, 2, 6, 6, 6, 10);

    public WaterJetBlock(Properties propertiesIn) {
        super(propertiesIn);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(BlockStateProperties.UP, false)
                .setValue(BlockStateProperties.DOWN, false)
                .setValue(BlockStatePropertiesAA.NORTH_STATE, VerticalLimitedConnection.NONE)
                .setValue(BlockStatePropertiesAA.SOUTH_STATE, VerticalLimitedConnection.NONE)
                .setValue(BlockStatePropertiesAA.EAST_STATE, VerticalLimitedConnection.NONE)
                .setValue(BlockStatePropertiesAA.WEST_STATE, VerticalLimitedConnection.NONE)
                .setValue(BlockStateProperties.POWERED, false)
                .setValue(BlockStatePropertiesAA.ACTIVATED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(
                BlockStateProperties.UP,
                BlockStateProperties.DOWN,
                BlockStatePropertiesAA.NORTH_STATE,
                BlockStatePropertiesAA.SOUTH_STATE,
                BlockStatePropertiesAA.EAST_STATE,
                BlockStatePropertiesAA.WEST_STATE,
                BlockStateProperties.POWERED,
                BlockStatePropertiesAA.ACTIVATED
        );
    }

    // Détection précise du robinet cliqué
    private TapInfo getClickedTap(BlockHitResult hit, BlockState state) {
        Vec3 hitVec = hit.getLocation().subtract(Vec3.atLowerCornerOf(hit.getBlockPos()));
        Direction face = hit.getDirection();

        switch (face) {
            case DOWN -> {
                if (state.getValue(BlockStateProperties.UP)) {
                    return new TapInfo(Direction.DOWN, VerticalLimitedConnection.NONE);
                }
            }
            case UP -> {
                if (state.getValue(BlockStateProperties.DOWN)) {
                    return new TapInfo(Direction.UP, VerticalLimitedConnection.NONE);
                }
            }
            case SOUTH -> {
                VerticalLimitedConnection northState = state.getValue(BlockStatePropertiesAA.NORTH_STATE);
                if (northState != VerticalLimitedConnection.NONE) {
                    boolean isTop = hitVec.y > 0.5;
                    if ((isTop && northState == VerticalLimitedConnection.TOP) ||
                            (!isTop && northState == VerticalLimitedConnection.BOTTOM)) {
                        return new TapInfo(Direction.SOUTH, northState);
                    }
                }
            }
            case NORTH -> {
                VerticalLimitedConnection southState = state.getValue(BlockStatePropertiesAA.SOUTH_STATE);
                if (southState != VerticalLimitedConnection.NONE) {
                    boolean isTop = hitVec.y > 0.5;
                    if ((isTop && southState == VerticalLimitedConnection.TOP) ||
                            (!isTop && southState == VerticalLimitedConnection.BOTTOM)) {
                        return new TapInfo(Direction.NORTH, southState);
                    }
                }
            }
            case WEST -> {
                VerticalLimitedConnection eastState = state.getValue(BlockStatePropertiesAA.EAST_STATE);
                if (eastState != VerticalLimitedConnection.NONE) {
                    boolean isTop = hitVec.y > 0.5;
                    if ((isTop && eastState == VerticalLimitedConnection.TOP) ||
                            (!isTop && eastState == VerticalLimitedConnection.BOTTOM)) {
                        return new TapInfo(Direction.WEST, eastState);
                    }
                }
            }
            case EAST -> {
                VerticalLimitedConnection westState = state.getValue(BlockStatePropertiesAA.WEST_STATE);
                if (westState != VerticalLimitedConnection.NONE) {
                    boolean isTop = hitVec.y > 0.5;
                    if ((isTop && westState == VerticalLimitedConnection.TOP) ||
                            (!isTop && westState == VerticalLimitedConnection.BOTTOM)) {
                        return new TapInfo(Direction.EAST, westState);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        List<VoxelShape> shapes = new ArrayList<>();

        if (state.getValue(BlockStateProperties.UP)) shapes.add(SHAPE_UP);
        if (state.getValue(BlockStateProperties.DOWN)) shapes.add(SHAPE_DOWN);

        VerticalLimitedConnection northState = state.getValue(BlockStatePropertiesAA.NORTH_STATE);
        if (northState == VerticalLimitedConnection.TOP) shapes.add(SHAPE_NORTH_TOP);
        if (northState == VerticalLimitedConnection.BOTTOM) shapes.add(SHAPE_NORTH_BOTTOM);

        VerticalLimitedConnection southState = state.getValue(BlockStatePropertiesAA.SOUTH_STATE);
        if (southState == VerticalLimitedConnection.TOP) shapes.add(SHAPE_SOUTH_TOP);
        if (southState == VerticalLimitedConnection.BOTTOM) shapes.add(SHAPE_SOUTH_BOTTOM);

        VerticalLimitedConnection eastState = state.getValue(BlockStatePropertiesAA.EAST_STATE);
        if (eastState == VerticalLimitedConnection.TOP) shapes.add(SHAPE_EAST_TOP);
        if (eastState == VerticalLimitedConnection.BOTTOM) shapes.add(SHAPE_EAST_BOTTOM);

        VerticalLimitedConnection westState = state.getValue(BlockStatePropertiesAA.WEST_STATE);
        if (westState == VerticalLimitedConnection.TOP) shapes.add(SHAPE_WEST_TOP);
        if (westState == VerticalLimitedConnection.BOTTOM) shapes.add(SHAPE_WEST_BOTTOM);

        if (shapes.isEmpty()) return Shapes.block();

        VoxelShape result = shapes.get(0);
        for (int i = 1; i < shapes.size(); i++) {
            result = Shapes.or(result, shapes.get(i));
        }
        return result;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        BlockState existing = context.getLevel().getBlockState(pos);
        boolean same = existing.getBlock() == this;
        BlockState base = same ? existing : this.defaultBlockState();
        VerticalLimitedConnection vertical = resolveVerticalConnection(context.getClickLocation(), pos);
        return addConnection(base, context.getClickedFace(), vertical);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        ItemStack stack = context.getItemInHand();
        if (context.getPlayer() != null && context.getPlayer().isCrouching()) return false;
        if (stack.getItem() != this.asItem()) return false;

        Direction face = context.getClickedFace();
        VerticalLimitedConnection vertical = resolveVerticalConnection(context.getClickLocation(), context.getClickedPos());

        return switch(face) {
            case DOWN -> !state.getValue(BlockStateProperties.UP);
            case UP -> !state.getValue(BlockStateProperties.DOWN);
            case SOUTH -> {
                VerticalLimitedConnection current = state.getValue(BlockStatePropertiesAA.NORTH_STATE);
                yield current == VerticalLimitedConnection.NONE || current != vertical;
            }
            case NORTH -> {
                VerticalLimitedConnection current = state.getValue(BlockStatePropertiesAA.SOUTH_STATE);
                yield current == VerticalLimitedConnection.NONE || current != vertical;
            }
            case WEST -> {
                VerticalLimitedConnection current = state.getValue(BlockStatePropertiesAA.EAST_STATE);
                yield current == VerticalLimitedConnection.NONE || current != vertical;
            }
            case EAST -> {
                VerticalLimitedConnection current = state.getValue(BlockStatePropertiesAA.WEST_STATE);
                yield current == VerticalLimitedConnection.NONE || current != vertical;
            }
        };
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
    public void attack(BlockState state, Level world, BlockPos pos, Player player) {
        if (world.isClientSide) return;

        BlockHitResult hit = (BlockHitResult) player.pick(20, 0, false);
        TapInfo clickedTap = getClickedTap(hit, state);

        if (clickedTap != null) {
            BlockState newState = removeSpecificConnection(state, clickedTap.face, clickedTap.vertical);
            if (newState != state) {
                world.setBlock(pos, newState, 3);
                // Drop l'item du robinet retiré
                popResource(world, pos, new ItemStack(this.asItem()));
            }
        }
    }

    private BlockState addConnection(BlockState state, Direction face, VerticalLimitedConnection vertical) {
        return switch(face) {
            case DOWN -> state.setValue(BlockStateProperties.UP, true);
            case UP -> state.setValue(BlockStateProperties.DOWN, true);
            case SOUTH -> state.setValue(BlockStatePropertiesAA.NORTH_STATE, vertical);
            case NORTH -> state.setValue(BlockStatePropertiesAA.SOUTH_STATE, vertical);
            case WEST -> state.setValue(BlockStatePropertiesAA.EAST_STATE, vertical);
            case EAST -> state.setValue(BlockStatePropertiesAA.WEST_STATE, vertical);
        };
    }

    private BlockState removeSpecificConnection(BlockState state, Direction face, VerticalLimitedConnection vertical) {
        return switch(face) {
            case DOWN -> state.setValue(BlockStateProperties.UP, false);
            case UP -> state.setValue(BlockStateProperties.DOWN, false);
            case SOUTH -> {
                VerticalLimitedConnection current = state.getValue(BlockStatePropertiesAA.NORTH_STATE);
                yield current == vertical ? state.setValue(BlockStatePropertiesAA.NORTH_STATE, VerticalLimitedConnection.NONE) : state;
            }
            case NORTH -> {
                VerticalLimitedConnection current = state.getValue(BlockStatePropertiesAA.SOUTH_STATE);
                yield current == vertical ? state.setValue(BlockStatePropertiesAA.SOUTH_STATE, VerticalLimitedConnection.NONE) : state;
            }
            case WEST -> {
                VerticalLimitedConnection current = state.getValue(BlockStatePropertiesAA.EAST_STATE);
                yield current == vertical ? state.setValue(BlockStatePropertiesAA.EAST_STATE, VerticalLimitedConnection.NONE) : state;
            }
            case EAST -> {
                VerticalLimitedConnection current = state.getValue(BlockStatePropertiesAA.WEST_STATE);
                yield current == vertical ? state.setValue(BlockStatePropertiesAA.WEST_STATE, VerticalLimitedConnection.NONE) : state;
            }
        };
    }

    @Override
    public BlockState updateShape(BlockState state, Direction dir, BlockState neighborState, LevelAccessor world,
                                  BlockPos pos, BlockPos neighborPos) {
        if (!(neighborState.getBlock() instanceof PoolBlock pool)) return state;
        int level = neighborState.getValue(BlockStatePropertiesAA.LEVEL);

        boolean shouldActivate = switch (dir) {
            case NORTH -> state.getValue(BlockStatePropertiesAA.NORTH_STATE) != VerticalLimitedConnection.NONE && level >= pool.faucetLevel;
            case SOUTH -> state.getValue(BlockStatePropertiesAA.SOUTH_STATE) != VerticalLimitedConnection.NONE && level >= pool.faucetLevel;
            case EAST -> state.getValue(BlockStatePropertiesAA.EAST_STATE) != VerticalLimitedConnection.NONE && level >= pool.faucetLevel;
            case WEST -> state.getValue(BlockStatePropertiesAA.WEST_STATE) != VerticalLimitedConnection.NONE && level >= pool.faucetLevel;
            case UP -> state.getValue(BlockStateProperties.UP) && level > 0;
            case DOWN -> level >= pool.maxLevel - 1;
        };

        return shouldActivate ? state.setValue(BlockStatePropertiesAA.ACTIVATED, true) : state;
    }

    private VerticalLimitedConnection resolveVerticalConnection(Vec3 hitLocation, BlockPos blockPos) {
        double relativeY = hitLocation.y - blockPos.getY();
        return relativeY <= 0.5D ? VerticalLimitedConnection.BOTTOM : VerticalLimitedConnection.TOP;
    }

    // Classe helper pour identifier un robinet précis
    private static class TapInfo {
        final Direction face;
        final VerticalLimitedConnection vertical;

        TapInfo(Direction face, VerticalLimitedConnection vertical) {
            this.face = face;
            this.vertical = vertical;
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        // Sauvegarde des valeurs actuelles
        VerticalLimitedConnection north = state.getValue(BlockStatePropertiesAA.NORTH_STATE);
        VerticalLimitedConnection east  = state.getValue(BlockStatePropertiesAA.EAST_STATE);
        VerticalLimitedConnection south = state.getValue(BlockStatePropertiesAA.SOUTH_STATE);
        VerticalLimitedConnection west  = state.getValue(BlockStatePropertiesAA.WEST_STATE);

        // Remappage selon la rotation
        switch (rot) {
            case CLOCKWISE_90:
                return state
                        .setValue(BlockStatePropertiesAA.NORTH_STATE, west)
                        .setValue(BlockStatePropertiesAA.EAST_STATE, north)
                        .setValue(BlockStatePropertiesAA.SOUTH_STATE, east)
                        .setValue(BlockStatePropertiesAA.WEST_STATE, south);
            case CLOCKWISE_180:
                return state
                        .setValue(BlockStatePropertiesAA.NORTH_STATE, south)
                        .setValue(BlockStatePropertiesAA.EAST_STATE, west)
                        .setValue(BlockStatePropertiesAA.SOUTH_STATE, north)
                        .setValue(BlockStatePropertiesAA.WEST_STATE, east);
            case COUNTERCLOCKWISE_90:
                return state
                        .setValue(BlockStatePropertiesAA.NORTH_STATE, east)
                        .setValue(BlockStatePropertiesAA.EAST_STATE, south)
                        .setValue(BlockStatePropertiesAA.SOUTH_STATE, west)
                        .setValue(BlockStatePropertiesAA.WEST_STATE, north);
            default:
                return state;
        }
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        switch (mirror) {
            case LEFT_RIGHT: // inverse N et S
                return state
                        .setValue(BlockStatePropertiesAA.NORTH_STATE, state.getValue(BlockStatePropertiesAA.SOUTH_STATE))
                        .setValue(BlockStatePropertiesAA.SOUTH_STATE, state.getValue(BlockStatePropertiesAA.NORTH_STATE));
            case FRONT_BACK: // inverse E et W
                return state
                        .setValue(BlockStatePropertiesAA.EAST_STATE, state.getValue(BlockStatePropertiesAA.WEST_STATE))
                        .setValue(BlockStatePropertiesAA.WEST_STATE, state.getValue(BlockStatePropertiesAA.EAST_STATE));
            default:
                return state;
        }
    }
}