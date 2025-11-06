package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA.VerticalLimitedConnection;

public class WaterJetBlock extends BlockDoT {
    public WaterJetBlock(Properties propertiesIn) {
        super(propertiesIn);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(BlockStateProperties.UP, false)
                .setValue(BlockStateProperties.DOWN, false)
                .setValue(BlockStatePropertiesAA.NORTH_STATE, VerticalLimitedConnection.NONE)
                .setValue(BlockStatePropertiesAA.SOUTH_STATE, VerticalLimitedConnection.NONE)
                .setValue(BlockStatePropertiesAA.EAST_STATE,  VerticalLimitedConnection.NONE)
                .setValue(BlockStatePropertiesAA.WEST_STATE,  VerticalLimitedConnection.NONE)
                .setValue(BlockStateProperties.POWERED, false)
                .setValue(BlockStatePropertiesAA.ACTIVATED, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> b) {
        super.createBlockStateDefinition(b);
        b.add(
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

    /* ------------------------------------------------------------
     * Placement UX:
     *  - Allow self-replacement so we stack into the SAME block.
     *  - Choose TOP/BOTTOM by local Y of the click within the cube.
     *  - Map clicked face to the opposite-state properties (your convention kept).
     * ------------------------------------------------------------ */

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
        // Block can absorb another WaterJet from the same item when not crouching
        if (ctx.isSecondaryUseActive()) return false;
        return ctx.getItemInHand().is(this.asItem());
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos pos = ctx.getClickedPos();
        BlockState existing = ctx.getLevel().getBlockState(pos);
        boolean same = existing.getBlock() == this;
        BlockState base = same ? existing : this.defaultBlockState();

        VerticalLimitedConnection vertical = resolveVerticalConnection(ctx.getClickLocation(), pos);
        return addConnection(base, ctx.getClickedFace(), vertical);
    }

    private static VerticalLimitedConnection resolveVerticalConnection(Vec3 hitLocation, BlockPos blockPos) {
        // local Y in [0..1): bottom half -> BOTTOM, top half -> TOP
        double localY = hitLocation.y - blockPos.getY();
        return localY <= 0.5D ? VerticalLimitedConnection.BOTTOM : VerticalLimitedConnection.TOP;
    }

    /** Apply one connection on the clicked face with its vertical detail (no more 'opposite' mapping). */
    private static BlockState addConnection(BlockState state, Direction face, VerticalLimitedConnection vertical) {
        return switch (face) {
            case DOWN  -> state.setValue(BlockStateProperties.UP, true);  // place on the bottom of this cube
            case UP    -> state.setValue(BlockStateProperties.DOWN, true);    // place on the top of this cube
            case SOUTH -> state.setValue(BlockStatePropertiesAA.NORTH_STATE, vertical);
            case NORTH -> state.setValue(BlockStatePropertiesAA.SOUTH_STATE, vertical);
            case WEST  -> state.setValue(BlockStatePropertiesAA.EAST_STATE,  vertical);
            case EAST  -> state.setValue(BlockStatePropertiesAA.WEST_STATE,  vertical);
        };
    }

    /* ------------------------------------------------------------
     * Interaction: kept minimal (toggle "activated" like Faucet).
     * ------------------------------------------------------------ */

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos,
                                 Player player, BlockHitResult hit) {
        if (world.isClientSide()) {
            world.playSound(player, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, 0.6F);
            world.playSound(player, pos, SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, 0.3F, 1.0F);
        }

        boolean activated = !state.getValue(BlockStatePropertiesAA.ACTIVATED);
        state = state.setValue(BlockStatePropertiesAA.ACTIVATED, activated);
        world.setBlock(pos, state, 10);
        return InteractionResult.SUCCESS;
    }

    /* ------------------------------------------------------------
     * Shapes: O(1) lookup into the 324 precomputed variants.
     * ------------------------------------------------------------ */

    // WaterJetBlock.java (anciens imports conservés)
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        // Encode order is SOUTH, WEST, NORTH, EAST.
        // Because addConnection() stores 'opposite', we READ the opposite properties:
        //  SOUTH -> NORTH_STATE
        //  WEST  -> EAST_STATE
        //  NORTH -> SOUTH_STATE
        //  EAST  -> WEST_STATE
        int idx = org.dawnoftime.dawnoftime.util.VoxelShapesBuilder.encodeWaterJetIndex(
                state.getValue(BlockStateProperties.UP),
                state.getValue(BlockStateProperties.DOWN),
                state.getValue(BlockStatePropertiesAA.SOUTH_STATE), // SOUTH face stored here
                state.getValue(BlockStatePropertiesAA.WEST_STATE),  // WEST  face stored here
                state.getValue(BlockStatePropertiesAA.NORTH_STATE), // NORTH face stored here
                state.getValue(BlockStatePropertiesAA.EAST_STATE)   // EAST  face stored here
        );
        return org.dawnoftime.dawnoftime.util.VoxelShapes.WATERJET_SHAPES[idx];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        // Ensure collision matches the outline (important on some versions/loaders)
        return getShape(state, world, pos, context);
    }

    /* ------------------------------------------------------------
     * Rotation / Mirror: remap the four VLC properties accordingly.
     * ------------------------------------------------------------ */

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        VerticalLimitedConnection n = state.getValue(BlockStatePropertiesAA.NORTH_STATE);
        VerticalLimitedConnection e = state.getValue(BlockStatePropertiesAA.EAST_STATE);
        VerticalLimitedConnection s = state.getValue(BlockStatePropertiesAA.SOUTH_STATE);
        VerticalLimitedConnection w = state.getValue(BlockStatePropertiesAA.WEST_STATE);

        return switch (rot) {
            case CLOCKWISE_90      -> state.setValue(BlockStatePropertiesAA.NORTH_STATE, w)
                    .setValue(BlockStatePropertiesAA.EAST_STATE,  n)
                    .setValue(BlockStatePropertiesAA.SOUTH_STATE, e)
                    .setValue(BlockStatePropertiesAA.WEST_STATE,  s);
            case CLOCKWISE_180     -> state.setValue(BlockStatePropertiesAA.NORTH_STATE, s)
                    .setValue(BlockStatePropertiesAA.EAST_STATE,  w)
                    .setValue(BlockStatePropertiesAA.SOUTH_STATE, n)
                    .setValue(BlockStatePropertiesAA.WEST_STATE,  e);
            case COUNTERCLOCKWISE_90 -> state.setValue(BlockStatePropertiesAA.NORTH_STATE, e)
                    .setValue(BlockStatePropertiesAA.EAST_STATE,  s)
                    .setValue(BlockStatePropertiesAA.SOUTH_STATE, w)
                    .setValue(BlockStatePropertiesAA.WEST_STATE,  n);
            default -> state;
        };
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return switch (mirror) {
            case LEFT_RIGHT -> state
                    .setValue(BlockStatePropertiesAA.NORTH_STATE, state.getValue(BlockStatePropertiesAA.SOUTH_STATE))
                    .setValue(BlockStatePropertiesAA.SOUTH_STATE, state.getValue(BlockStatePropertiesAA.NORTH_STATE));
            case FRONT_BACK -> state
                    .setValue(BlockStatePropertiesAA.EAST_STATE, state.getValue(BlockStatePropertiesAA.WEST_STATE))
                    .setValue(BlockStatePropertiesAA.WEST_STATE, state.getValue(BlockStatePropertiesAA.EAST_STATE));
            default -> state;
        };
    }
}
