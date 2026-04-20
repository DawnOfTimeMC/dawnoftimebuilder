package org.dawnoftime.dawnoftime.block.general;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;


import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.block.templates.WaterloggedBlock;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA.HorizontalConnection;
import org.dawnoftime.dawnoftime.util.Utils;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nullable;
import java.util.List;
import static org.dawnoftime.dawnoftime.util.VoxelShapes.FIREPLACE_SHAPES;

public class FireplaceBlock extends WaterloggedBlock {
    public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final EnumProperty<HorizontalConnection> HORIZONTAL_CONNECTION = BlockStatePropertiesAA.HORIZONTAL_CONNECTION;

    public FireplaceBlock(final Properties properties) {
        super(properties, FIREPLACE_SHAPES);
        this.registerDefaultState(this.defaultBlockState().setValue(FireplaceBlock.LIT, false).setValue(FireplaceBlock.HORIZONTAL_AXIS, Direction.Axis.X).setValue(FireplaceBlock.HORIZONTAL_CONNECTION, HorizontalConnection.NONE));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FireplaceBlock.HORIZONTAL_AXIS, FireplaceBlock.LIT, FireplaceBlock.HORIZONTAL_CONNECTION);
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(final BlockState state, final BlockGetter worldIn, final BlockPos pos, final CollisionContext context) {
        return this.getShape(state.getValue(FireplaceBlock.HORIZONTAL_AXIS) == Direction.Axis.X ? 1 : 3);
    }

    @Override
    public int getShapeIndex(final @NotNull BlockState state, final @NotNull BlockGetter worldIn, final @NotNull BlockPos pos, final @NotNull CollisionContext context) {
        if (state.getValue(FireplaceBlock.HORIZONTAL_AXIS) == Direction.Axis.X) {
            return state.getValue(FireplaceBlock.LIT) ? 0 : 1;
        }
        return state.getValue(FireplaceBlock.LIT) ? 2 : 3;
    }

    @Override
    public @NotNull BlockState updateShape(BlockState stateIn, final @NotNull Direction facing, final @NotNull BlockState facingState, final @NotNull LevelAccessor worldIn, final @NotNull BlockPos currentPos, final @NotNull BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        if (stateIn.getValue(WATERLOGGED)) {
            stateIn = stateIn.setValue(LIT, false);
        } else {
            if (facing.getAxis() == stateIn.getValue(HORIZONTAL_AXIS) && facingState.getBlock() == this) {
                if (!facingState.getValue(WATERLOGGED)) {
                    stateIn = stateIn.setValue(LIT, facingState.getValue(LIT));
                }
            }
        }
        return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : stateIn;
    }

    @Override
    public boolean canSurvive(final BlockState state, final LevelReader worldIn, final BlockPos pos) {
        return Block.canSupportCenter(worldIn, pos.below(), Direction.UP);
    }

    @Override
    public InteractionResult use(final BlockState state, final Level worldIn, final BlockPos pos, final Player player, final InteractionHand handIn, final BlockHitResult hit) {
        return Utils.changeBlockLitStateWithItemOrCreativePlayer(state, worldIn, pos, player, handIn) >= 0 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public void onProjectileHit(final Level worldIn, final BlockState state, final BlockHitResult hit, final Projectile projectile) {
        int activation = -1;

        if (!state.getValue(WaterloggedBlock.WATERLOGGED) && !state.getValue(FireplaceBlock.LIT) && (projectile instanceof AbstractArrow && projectile.isOnFire() || projectile instanceof Fireball)) {
            activation = 1;
        } else if (state.getValue(FireplaceBlock.LIT) && (projectile instanceof Snowball || projectile instanceof ThrownPotion && PotionUtils.getPotion(((ThrownPotion) projectile).getItem()).getEffects().size() <= 0)) {
            activation = 0;
        }

        if (activation >= 0) {

            final BlockPos pos = hit.getBlockPos();
            final boolean isActivated = activation == 1;

            if (!worldIn.isClientSide()) {
                worldIn.setBlock(pos, state.setValue(FireplaceBlock.LIT, isActivated), 10);
                worldIn.playSound(null, pos, isActivated ? SoundEvents.FIRE_AMBIENT : SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else if (!isActivated && worldIn.isClientSide()) {
                for (int i = 0; i < worldIn.random.nextInt(1) + 1; ++i) {
                    worldIn.addParticle(ParticleTypes.CLOUD, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, worldIn.random.nextFloat() / 4.0F, 2.5E-5D, worldIn.random.nextFloat() / 4.0F);
                }
            }
        }
    }

    @Override
    public void entityInside(final BlockState state, final Level world, final BlockPos pos, final Entity entityIn) {
        if (!entityIn.fireImmune() && state.getValue(FireplaceBlock.LIT) && entityIn instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entityIn)) {
            entityIn.hurt(entityIn.damageSources().inFire(), 1.0F);
        }
        super.entityInside(state, world, pos, entityIn);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        final BlockState state = super.getStateForPlacement(context);
        final Direction.Axis axis = context.getHorizontalDirection().getAxis() == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
        return state.setValue(FireplaceBlock.HORIZONTAL_AXIS, axis).setValue(FireplaceBlock.HORIZONTAL_CONNECTION, this.getHorizontalShape(context.getLevel(), context.getClickedPos(), axis));
    }


    private HorizontalConnection getHorizontalShape(final Level worldIn, final BlockPos pos, final Direction.Axis axis) {

        final BlockState left = worldIn.getBlockState(pos.relative(axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH, 1));
        final BlockState right = worldIn.getBlockState(pos.relative(axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH, -1));

        boolean blockLeft = left.getBlock() instanceof FireplaceBlock;
        if (blockLeft) {
            blockLeft = left.getValue(FireplaceBlock.HORIZONTAL_AXIS) == axis;
        }

        boolean blockRight = right.getBlock() instanceof FireplaceBlock;
        if (blockRight) {
            blockRight = right.getValue(FireplaceBlock.HORIZONTAL_AXIS) == axis;
        }

        if (blockLeft) {
            return blockRight ? HorizontalConnection.BOTH : HorizontalConnection.LEFT;
        }
        return blockRight ? HorizontalConnection.RIGHT : HorizontalConnection.NONE;
    }

    @Override
    public boolean placeLiquid(final LevelAccessor world, final BlockPos pos, final BlockState state, final FluidState fluid) {
        if (state.getValue(BlockStateProperties.WATERLOGGED) || fluid.getType() != Fluids.WATER) {
            return false;
        }
        if (state.getValue(FireplaceBlock.LIT)) {
            world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        world.setBlock(pos, state.setValue(WaterloggedBlock.WATERLOGGED, true).setValue(FireplaceBlock.LIT, false), 10);
        world.scheduleTick(pos, fluid.getType(), fluid.getType().getTickDelay(world));
        return true;
    }


    @Override
    public void animateTick(final BlockState stateIn, final Level worldIn, final BlockPos pos, final RandomSource rand) {
        if (stateIn.getValue(FireplaceBlock.LIT)) {
            if (rand.nextInt(10) == 0) {
                worldIn.playLocalSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
            }

            if (rand.nextInt(10) == 0) {
                for (int i = 0; i < rand.nextInt(1) + 1; ++i) {
                    worldIn.addParticle(ParticleTypes.LAVA, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, rand.nextFloat() / 4.0F, 2.5E-5D, rand.nextFloat() / 4.0F);
                }
            }
            worldIn.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.getX() + 0.5D + rand.nextDouble() / 3.0D * (rand.nextBoolean() ? 1 : -1), pos.getY() + 0.4D, pos.getZ() + 0.5D + rand.nextDouble() / 3.0D * (rand.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
        }
    }

    @Override
    public BlockState rotate(final BlockState state, final Rotation rot) {
        final Direction.Axis axis = state.getValue(FireplaceBlock.HORIZONTAL_AXIS);
        return rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90 ? state.setValue(FireplaceBlock.HORIZONTAL_AXIS, axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X) : state;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("tooltip.dawnoftimebuilder.fireplace"));
    }

}