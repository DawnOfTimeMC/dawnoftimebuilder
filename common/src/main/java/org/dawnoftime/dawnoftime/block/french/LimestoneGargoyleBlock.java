package org.dawnoftime.dawnoftime.block.french;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.dawnoftime.dawnoftime.block.templates.WaterloggedBlock;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.dawnoftime.dawnoftime.util.Utils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;

import static org.dawnoftime.dawnoftime.util.VoxelShapes.LIMESTONE_GARGOYLE_SHAPES;

public class LimestoneGargoyleBlock extends WaterloggedBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
    private static final IntegerProperty HUMIDITY = BlockStatePropertiesAA.HUMIDITY_0_8;

    public LimestoneGargoyleBlock(Properties properties) {
        super(properties, LIMESTONE_GARGOYLE_SHAPES);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(HUMIDITY, 0).setValue(PERSISTENT, false));
    }

    private int getMaxHumidity() {
        return 8;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, HUMIDITY, PERSISTENT);
    }

    @Override
    public int getShapeIndex(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return state.getValue(FACING).get2DDataValue();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level worldIn, BlockPos pos, Player player, BlockHitResult hit) {
        if (state.getValue(PERSISTENT)) {
            if (player.isCreative()) {
                int humidity = state.getValue(HUMIDITY);
                if (player.isCrouching()) {
                    if (humidity > 0) {
                        worldIn.setBlock(pos, state.setValue(HUMIDITY, humidity - 1), 10);
                        return InteractionResult.SUCCESS;
                    }
                } else {
                    if (humidity < this.getMaxHumidity()) {
                        worldIn.setBlock(pos, state.setValue(HUMIDITY, humidity + 1), 10);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        } else {
            if (Utils.useLighter(worldIn, pos, player, player.getUsedItemHand())) {
                Random rand = new Random();
                for (int i = 0; i < 5; i++) {
                    worldIn.addAlwaysVisibleParticle(ParticleTypes.SMOKE, (double) pos.getX() +
                                    rand.nextDouble(), (double) pos.getY() + 0.5D + rand.nextDouble() / 2,
                            (double) pos.getZ() + rand.nextDouble(), 0.0D, 0.07D, 0.0D);
                }
                worldIn.setBlock(pos, state.setValue(PERSISTENT, true), 10);
                return InteractionResult.SUCCESS;
            }
        }
        return super.useWithoutItem(state, worldIn, pos, player, hit);
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, @NotNull Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    public void animateTick(final BlockState state, final Level world, final BlockPos pos, final RandomSource rand) {
        int humidity = state.getValue(HUMIDITY);
        if (humidity > 0) {
            if (humidity == this.getMaxHumidity()) {
                Direction facing = state.getValue(FACING);
                double x = pos.getX() + (8.0D + facing.getStepX() * 6.0D) / 16.0D;
                double y = pos.getY() + 9.5D / 16.0D;
                double z = pos.getZ() + (8.0D + facing.getStepZ() * 6.0D) / 16.0D;
                world.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0.0D, 0.0D, 0.0D);
                world.addParticle(ParticleTypes.DRIPPING_WATER, x, y - 0.0625D, z, 0.0D, 0.0D, 0.0D);
            } else if (rand.nextInt(this.getMaxHumidity() - humidity + 1) == 0) {
                Direction facing = state.getValue(FACING);
                double x = pos.getX() + (8.0D + facing.getStepX() * 6.0D) / 16.0D;
                double y = pos.getY() + 9.5D / 16.0D;
                double z = pos.getZ() + (8.0D + facing.getStepZ() * 6.0D) / 16.0D;
                world.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource rand) {
        int humidity = state.getValue(HUMIDITY);

        if (state.getValue(PERSISTENT)) {
            super.randomTick(state, world, pos, rand);
            return;
        }

        if (world.isRaining() && world.canSeeSky(pos)) {
            if (state.getValue(HUMIDITY) < this.getMaxHumidity()) {
                world.setBlock(pos, state.setValue(HUMIDITY, this.getMaxHumidity()), 2);
            }
        } else {
            if (rand.nextInt(5) == 0 && humidity > 0) {
                world.setBlock(pos, state.setValue(HUMIDITY, humidity - 1), 2);
            }
        }

        super.randomTick(state, world, pos, rand);
    }

    @Override
    public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluid) {
        if (!state.getValue(WATERLOGGED) && fluid.getType() == Fluids.WATER) {
            world.setBlock(pos, state.setValue(WATERLOGGED, true).setValue(HUMIDITY, 0), 2);
            world.scheduleTick(pos, fluid.getType(), fluid.getType().getTickDelay(world));
            return true;
        } else {
            return false;
        }
    }

//    @Override
//    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
//        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
//        Utils.addTooltip(tooltipComponents, this);
//    }
}
