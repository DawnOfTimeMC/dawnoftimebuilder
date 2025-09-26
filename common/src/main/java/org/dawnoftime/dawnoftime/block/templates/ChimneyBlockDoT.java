package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.block.general.FireplaceBlock;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA.VerticalConnection;
import org.dawnoftime.dawnoftime.util.Utils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class ChimneyBlockDoT extends ConnectedVerticalBlock {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public ChimneyBlockDoT(final Properties properties, VoxelShape[] shapes) {
        super(properties, shapes);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.LIT, true));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.LIT);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        final BlockState state = super.getStateForPlacement(context);
        final Level world = context.getLevel();
        BlockPos belowBlockPos = context.getClickedPos().below();
        BlockState blockState = world.getBlockState(belowBlockPos);

        if(blockState.getBlock() instanceof ChimneyBlockDoT) {
            return state.setValue(BlockStateProperties.LIT, blockState.getValue(BlockStateProperties.LIT));
        }

        if(blockState.getBlock() instanceof ConnectedVerticalSidedPlanFireplaceBlock) {
            BlockState foundBlockState;
            belowBlockPos = belowBlockPos.below();
            while((foundBlockState = world.getBlockState(belowBlockPos)).getBlock() instanceof ConnectedVerticalSidedPlanFireplaceBlock) {
                blockState = foundBlockState;
                belowBlockPos = belowBlockPos.below();
            }
            return state.setValue(BlockStateProperties.LIT, blockState.getValue(BlockStateProperties.LIT));
        }
        return state;
    }

    @Override
    public InteractionResult useWithoutItem(final BlockState blockStateIn, final Level worldIn, final BlockPos blockPosIn, final Player player, final BlockHitResult hit) {
        if(super.useWithoutItem(blockStateIn, worldIn, blockPosIn, player, hit).equals(InteractionResult.SUCCESS))
            return InteractionResult.SUCCESS;

        final int activation = Utils.changeBlockLitStateWithItemOrCreativePlayer(blockStateIn, worldIn, blockPosIn, player, player.getUsedItemHand());
        if(activation >= 0) {
            final boolean isActivated = activation == 1;

            ChimneyBlockDoT.updateAllChimneyConductParts(isActivated, blockStateIn, blockPosIn, worldIn);
            ChimneyBlockDoT.updateFireplace(isActivated, blockPosIn, worldIn);

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void onProjectileHit(final Level worldIn, final BlockState state, final BlockHitResult hit, final Projectile projectile) {

        int activation = -1;

        if(!state.getValue(WaterloggedBlock.WATERLOGGED) && !state.getValue(FireplaceBlock.LIT) && (projectile instanceof Arrow && ((Arrow) projectile).isOnFire() || projectile instanceof Fireball)) {
            activation = 1;
        } else if(state.getValue(FireplaceBlock.LIT) && (projectile instanceof Snowball || projectile instanceof ThrowableProjectile && Utils.getPotionByName(Utils.getItemKeyAsString(((ThrowableItemProjectile) projectile).getItem().getItem())).getEffects().size() <= 0)) {
            activation = 0;
        }

        if(activation >= 0) {
            final BlockPos pos = hit.getBlockPos();
            final boolean isActivated = activation == 1;

            if(!worldIn.isClientSide()) {
                ChimneyBlockDoT.updateAllChimneyConductParts(isActivated, state, pos, worldIn);
                worldIn.playSound(null, pos, isActivated ? SoundEvents.FIRE_AMBIENT : SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else if(!isActivated && worldIn.isClientSide()) {
                for(int i = 0; i < worldIn.random.nextInt(1) + 1; ++i) {
                    worldIn.addParticle(ParticleTypes.CLOUD, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, worldIn.random.nextFloat() / 4.0F, 2.5E-5D, worldIn.random.nextFloat() / 4.0F);
                }
            }
        }
    }

    
    @Override
    public void animateTick(final BlockState stateIn, final Level worldIn, final BlockPos pos, final RandomSource rand) {
        if(stateIn.getValue(WaterloggedBlock.WATERLOGGED) || !stateIn.getValue(BlockStateProperties.LIT)) {
            return;
        }
        if(stateIn.getValue(ConnectedVerticalBlock.VERTICAL_CONNECTION) == VerticalConnection.UNDER || stateIn.getValue(ConnectedVerticalBlock.VERTICAL_CONNECTION) == VerticalConnection.NONE) {
            worldIn.addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, true, pos.getX() + rand.nextDouble() * 0.5D + 0.25D, pos.getY() + rand.nextDouble() * 0.5D + 0.3D, pos.getZ() + rand.nextDouble() * 0.5D + 0.25D, 0.0D, 0.07D, 0.0D);
            worldIn.addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, pos.getX() + rand.nextDouble() * 0.5D + 0.25D, pos.getY() + rand.nextDouble() * 0.5D + 0.3D, pos.getZ() + rand.nextDouble() * 0.5D + 0.25D, 0.0D, 0.04D, 0.0D);
        }
    }

    @Override
    public @NotNull BlockState updateShape(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        if(facingState.getBlock() instanceof ChimneyBlockDoT || facingState.getBlock() instanceof ConnectedVerticalSidedPlanFireplaceBlock) {
            stateIn.setValue(LIT, facingState.getValue(LIT));
        }

        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        Utils.addTooltip(tooltipComponents, Utils.TOOLTIP_FIREPLACE);

    }

    public static void updateAllChimneyConductParts(final boolean isActivatedIn, BlockState stateIn, final BlockPos blockPosIn, final Level worldIn) {
        stateIn = stateIn.setValue(BlockStateProperties.LIT, isActivatedIn);
        worldIn.setBlock(blockPosIn, stateIn, 10);
        ChimneyBlockDoT.updateIsActivatedInAllPartsOfBottom(isActivatedIn, stateIn, blockPosIn, worldIn);
        ChimneyBlockDoT.updateIsActivatedInAllPartsOfTop(isActivatedIn, stateIn, blockPosIn, worldIn);
    }

    public static void updateIsActivatedInAllPartsOfBottom(final boolean isActivatedIn, final BlockState stateIn, final BlockPos blockPosIn, final Level worldIn) {
        BlockState blockState = null;
        BlockPos blockPos = blockPosIn;
        while((blockState = worldIn.getBlockState(blockPos = blockPos.below())) != null && blockState.getBlock() instanceof ChimneyBlockDoT) {
            blockState = blockState.setValue(BlockStateProperties.LIT, isActivatedIn);
            worldIn.setBlock(blockPos, blockState, 10);
        }
    }

    public static void updateIsActivatedInAllPartsOfTop(final boolean isActivatedIn, final BlockState stateIn, final BlockPos blockPosIn, final Level worldIn) {
        BlockState blockState = null;
        BlockPos blockPos = blockPosIn;
        while((blockState = worldIn.getBlockState(blockPos = blockPos.above())) != null && blockState.getBlock() instanceof ChimneyBlockDoT) {
            blockState = blockState.setValue(BlockStateProperties.LIT, isActivatedIn);
            worldIn.setBlock(blockPos, blockState, 10);
        }
    }

    public static void updateFireplace(final boolean isActivatedIn, final BlockPos blockPosIn, final Level worldIn) {
        BlockState blockState = null;
        BlockPos blockPos = blockPosIn;
        while((blockState = worldIn.getBlockState(blockPos = blockPos.below())) != null && blockState.getBlock() instanceof ChimneyBlockDoT) {
        }

        while((blockState = worldIn.getBlockState(blockPos = blockPos.below())) != null && blockState.getBlock() instanceof ConnectedVerticalSidedPlanFireplaceBlock) {
            final VerticalConnection verticalConnection = blockState.getValue(ConnectedVerticalBlock.VERTICAL_CONNECTION);
            if(verticalConnection != null && VerticalConnection.ABOVE.equals(verticalConnection)) {
                blockState = blockState.setValue(BlockStateProperties.LIT, isActivatedIn);
                worldIn.setBlock(blockPos, blockState, 10);
                final Direction direction = blockState.getValue(ConnectedVerticalSidedBlock.FACING);
                worldIn.getBlockState(blockPos.relative(direction.getCounterClockWise())).handleNeighborChanged(worldIn, blockPos.relative(direction.getCounterClockWise()), blockState.getBlock(), blockPos, false);
                worldIn.getBlockState(blockPos.relative(direction.getClockWise())).handleNeighborChanged(worldIn, blockPos.relative(direction.getClockWise()), blockState.getBlock(), blockPos, false);
            }
        }
    }
}