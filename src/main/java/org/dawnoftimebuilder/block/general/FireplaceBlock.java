package org.dawnoftimebuilder.block.general;

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
import net.minecraft.world.level.*;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties.HorizontalConnection;
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nullable;
import java.util.List;

public class FireplaceBlock extends WaterloggedBlock {
    public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final EnumProperty<DoTBBlockStateProperties.HorizontalConnection> HORIZONTAL_CONNECTION = DoTBBlockStateProperties.HORIZONTAL_CONNECTION;
    private static final VoxelShape ON_X_SHAPE = Block.box(0.0D, 0.0D, 2.0D, 16.0D, 14.0D, 14.0D);
    private static final VoxelShape OFF_X_SHAPE = Block.box(0.0D, 0.0D, 2.0D, 16.0D, 5.0D, 14.0D);
    private static final VoxelShape ON_Z_SHAPE = Block.box(2.0D, 0.0D, 0.0D, 14.0D, 14.0D, 16.0D);
    private static final VoxelShape OFF_Z_SHAPE = Block.box(2.0D, 0.0D, 0.0D, 14.0D, 5.0D, 16.0D);

    public FireplaceBlock(final Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FireplaceBlock.LIT, false).setValue(FireplaceBlock.HORIZONTAL_AXIS, Direction.Axis.X).setValue(FireplaceBlock.HORIZONTAL_CONNECTION, HorizontalConnection.NONE));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FireplaceBlock.HORIZONTAL_AXIS, FireplaceBlock.LIT, FireplaceBlock.HORIZONTAL_CONNECTION);
    }

    @Override
    public VoxelShape getCollisionShape(final BlockState state, final BlockGetter worldIn, final BlockPos pos, final CollisionContext context) {
        return state.getValue(FireplaceBlock.HORIZONTAL_AXIS) == Direction.Axis.X ? FireplaceBlock.OFF_X_SHAPE : FireplaceBlock.OFF_Z_SHAPE;
    }

    @Override
    public VoxelShape getShape(final BlockState state, final BlockGetter worldIn, final BlockPos pos, final CollisionContext context) {
        if(state.getValue(FireplaceBlock.HORIZONTAL_AXIS) == Direction.Axis.X) {
            return state.getValue(FireplaceBlock.LIT) ? FireplaceBlock.ON_X_SHAPE : FireplaceBlock.OFF_X_SHAPE;
        }
        return state.getValue(FireplaceBlock.LIT) ? FireplaceBlock.ON_Z_SHAPE : FireplaceBlock.OFF_Z_SHAPE;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, final Direction facing, final BlockState facingState, final LevelAccessor worldIn, final BlockPos currentPos, final BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(final BlockState state, final LevelReader worldIn, final BlockPos pos) {
        return Block.canSupportCenter(worldIn, pos.below(), Direction.UP);
    }

    @Override
    public InteractionResult use(final BlockState state, final Level worldIn, final BlockPos pos, final Player player, final InteractionHand handIn, final BlockHitResult hit) {
        return DoTBUtils.changeBlockLitStateWithItemOrCreativePlayer(state, worldIn, pos, player, handIn) >= 0 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public void onProjectileHit(final Level worldIn, final BlockState state, final BlockHitResult hit, final Projectile projectile) {
        int activation = -1;

        if(!state.getValue(WaterloggedBlock.WATERLOGGED) && !state.getValue(FireplaceBlock.LIT) && (projectile instanceof AbstractArrow && projectile.isOnFire() || projectile instanceof Fireball)) {
            activation = 1;
        } else if(state.getValue(FireplaceBlock.LIT) && (projectile instanceof Snowball || projectile instanceof ThrownPotion && PotionUtils.getPotion(((ThrownPotion) projectile).getItem()).getEffects().size() <= 0)) {
            activation = 0;
        }

        if(activation >= 0) {

            final BlockPos pos = hit.getBlockPos();
            final boolean isActivated = activation == 1;

            if(!worldIn.isClientSide()) {
                worldIn.setBlock(pos, state.setValue(FireplaceBlock.LIT, isActivated), 10);
                worldIn.playSound(null, pos, isActivated ? SoundEvents.FIRE_AMBIENT : SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else if(!isActivated && worldIn.isClientSide()) {
                for(int i = 0; i < worldIn.random.nextInt(1) + 1; ++i) {
                    worldIn.addParticle(ParticleTypes.CLOUD, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, worldIn.random.nextFloat() / 4.0F, 2.5E-5D, worldIn.random.nextFloat() / 4.0F);
                }
            }
        }
    }

    @Override
    public void entityInside(final BlockState state, final Level world, final BlockPos pos, final Entity entityIn) {
        if(!entityIn.fireImmune() && state.getValue(FireplaceBlock.LIT) && entityIn instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entityIn)) {
            entityIn.hurt(entityIn.damageSources().inFire(), 1.0F);
        }
        super.entityInside(state, world, pos, entityIn);
    }

    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        final BlockState state = super.getStateForPlacement(context);
        final Direction.Axis axis = context.getHorizontalDirection().getAxis() == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
        return state.setValue(FireplaceBlock.HORIZONTAL_AXIS, axis).setValue(FireplaceBlock.HORIZONTAL_CONNECTION, this.getHorizontalShape(context.getLevel(), context.getClickedPos(), axis));
    }

    @Override
    public void neighborChanged(BlockState state, final Level worldIn, final BlockPos pos, final Block blockIn, final BlockPos fromPos, final boolean isMoving) {
        //TODO Debug : it seems the block is not updated when an adjacent fireplace is lit.
        if(pos.getY() == fromPos.getY()) {
            final Direction.Axis axis = state.getValue(FireplaceBlock.HORIZONTAL_AXIS);
            if(axis == Direction.Axis.X) {
                if(pos.getX() == fromPos.getX()) {
                    return;
                }
            } else if(pos.getZ() == fromPos.getZ()) {
                return;
            }

            state = state.setValue(FireplaceBlock.HORIZONTAL_CONNECTION, this.getHorizontalShape(worldIn, pos, axis));

            final BlockState newState = worldIn.getBlockState(fromPos);
            if(newState.getBlock() instanceof FireplaceBlock && newState.getValue(FireplaceBlock.HORIZONTAL_AXIS) == axis && newState.getValue(FireplaceBlock.LIT) != state.getValue(FireplaceBlock.LIT)) {
                if(newState.getValue(FireplaceBlock.LIT) && state.getValue(WaterloggedBlock.WATERLOGGED)) {
                    return;
                }
                worldIn.setBlock(pos, state.setValue(FireplaceBlock.LIT, newState.getValue(FireplaceBlock.LIT)), 10);
                final BlockPos newPos = axis == Direction.Axis.X ? pos.relative(Direction.EAST, pos.getX() - fromPos.getX()) : pos.relative(Direction.SOUTH, pos.getZ() - fromPos.getZ());
                worldIn.getBlockState(newPos).neighborChanged(worldIn, newPos, this, pos, false);
                return;
            }
            worldIn.setBlock(pos, state, 10);
        }
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }

    private DoTBBlockStateProperties.HorizontalConnection getHorizontalShape(final Level worldIn, final BlockPos pos, final Direction.Axis axis) {

        final BlockState left = worldIn.getBlockState(pos.relative(axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH, 1));
        final BlockState right = worldIn.getBlockState(pos.relative(axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH, -1));

        boolean blockLeft = left.getBlock() instanceof FireplaceBlock;
        if(blockLeft) {
            blockLeft = left.getValue(FireplaceBlock.HORIZONTAL_AXIS) == axis;
        }

        boolean blockRight = right.getBlock() instanceof FireplaceBlock;
        if(blockRight) {
            blockRight = right.getValue(FireplaceBlock.HORIZONTAL_AXIS) == axis;
        }

        if(blockLeft) {
            return blockRight ? DoTBBlockStateProperties.HorizontalConnection.BOTH : DoTBBlockStateProperties.HorizontalConnection.LEFT;
        }
        return blockRight ? DoTBBlockStateProperties.HorizontalConnection.RIGHT : DoTBBlockStateProperties.HorizontalConnection.NONE;
    }

    @Override
    public boolean placeLiquid(final LevelAccessor world, final BlockPos pos, final BlockState state, final FluidState fluid) {
        if(state.getValue(BlockStateProperties.WATERLOGGED) || fluid.getType() != Fluids.WATER) {
            return false;
        }
        if(state.getValue(FireplaceBlock.LIT)) {
            world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        world.setBlock(pos, state.setValue(WaterloggedBlock.WATERLOGGED, true).setValue(FireplaceBlock.LIT, false), 10);
        world.scheduleTick(pos, fluid.getType(), fluid.getType().getTickDelay(world));
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(final BlockState stateIn, final Level worldIn, final BlockPos pos, final RandomSource rand) {
        if(stateIn.getValue(FireplaceBlock.LIT)) {
            if(rand.nextInt(10) == 0) {
                worldIn.playLocalSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
            }

            if(rand.nextInt(10) == 0) {
                for(int i = 0; i < rand.nextInt(1) + 1; ++i) {
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
    public void appendHoverText(final ItemStack stack, @Nullable final BlockGetter worldIn, final List<Component> tooltip, final TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        DoTBUtils.addTooltip(tooltip, DoTBUtils.TOOLTIP_FIREPLACE);
    }
}