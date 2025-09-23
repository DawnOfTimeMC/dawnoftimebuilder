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
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.dawnoftime.dawnoftime.block.general.FireplaceBlock;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA.HorizontalConnection;
import org.dawnoftime.dawnoftime.util.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static org.dawnoftime.dawnoftime.util.VoxelShapes.MULTIBLOCK_FIREPLACE_SHAPES;

public class ConnectedVerticalSidedPlanFireplaceBlock extends ConnectedVerticalSidedPlanBlock {

    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public ConnectedVerticalSidedPlanFireplaceBlock(final Properties properties) {
        super(properties, MULTIBLOCK_FIREPLACE_SHAPES);
        this.registerDefaultState(this.defaultBlockState().setValue(ConnectedVerticalSidedPlanFireplaceBlock.LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ConnectedVerticalSidedPlanFireplaceBlock.LIT);
    }

    @Override
    public int getShapeIndex(final @NotNull BlockState state, final @NotNull BlockGetter worldIn, final @NotNull BlockPos pos, final @NotNull CollisionContext context) {
        int index = state.getValue(ConnectedVerticalBlock.VERTICAL_CONNECTION) == BlockStatePropertiesAA.VerticalConnection.NONE ? 0 : 1;
        return index + state.getValue(ConnectedVerticalSidedBlock.FACING).get2DDataValue() * 2;
    }

    @Override
    public InteractionResult useWithoutItem(final BlockState stateIn, final Level worldIn, final BlockPos pos, final Player player, final BlockHitResult hit) {
        if(player.getMainHandItem() != null && player.getMainHandItem().getItem() instanceof BlockItem &&
                ((BlockItem) player.getMainHandItem().getItem()).getBlock() instanceof ConnectedVerticalSidedPlanFireplaceBlock) {
            return InteractionResult.PASS;
        }
        if(stateIn.getValue(ConnectedVerticalBlock.VERTICAL_CONNECTION) != BlockStatePropertiesAA.VerticalConnection.BOTH && stateIn.getValue(ConnectedVerticalBlock.VERTICAL_CONNECTION) != BlockStatePropertiesAA.VerticalConnection.UNDER) {
            final int activation = Utils.changeBlockLitStateWithItemOrCreativePlayer(stateIn, worldIn, pos, player, player.getUsedItemHand());
            if(activation >= 0) {
                final Direction direction = stateIn.getValue(ConnectedVerticalSidedBlock.FACING);
                worldIn.getBlockState(pos.relative(direction.getCounterClockWise())).handleNeighborChanged(worldIn, pos.relative(direction.getCounterClockWise()), this, pos, false);
                worldIn.getBlockState(pos.relative(direction.getClockWise())).handleNeighborChanged(worldIn, pos.relative(direction.getClockWise()), stateIn.getBlock(), pos, false);

                ConnectedVerticalSidedPlanFireplaceBlock.updateChimneys(activation == 1, stateIn, pos, worldIn);

                return InteractionResult.SUCCESS;
            }
            return InteractionResult.FAIL;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void onProjectileHit(final Level worldIn, BlockState state, final BlockHitResult hit, final Projectile projectile) {

        int activation = -1;

        if(!state.getValue(WaterloggedBlock.WATERLOGGED) && !state.getValue(FireplaceBlock.LIT)) {
            if(projectile instanceof AbstractArrow) {
                activation = 1;
            }
        } else if(state.getValue(FireplaceBlock.LIT) && (projectile instanceof Snowball || projectile instanceof ThrowableProjectile && Utils.getPotionByName(Utils.getItemKeyAsString(((ThrowableItemProjectile) projectile).getItem().getItem())).getEffects().size() <= 0)) {
            activation = 0;
        }

        if(activation >= 0) {
            final BlockPos pos = hit.getBlockPos();
            final boolean isActivated = activation == 1;

            if(!worldIn.isClientSide()) {
                state = state.setValue(FireplaceBlock.LIT, isActivated);
                worldIn.setBlock(pos, state, 10);
                worldIn.playSound(null, pos, isActivated ? SoundEvents.FIRE_AMBIENT : SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else if(!isActivated && worldIn.isClientSide()) {
                for(int i = 0; i < worldIn.random.nextInt(1) + 1; ++i) {
                    worldIn.addParticle(ParticleTypes.CLOUD, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, worldIn.random.nextFloat() / 4.0F, 2.5E-5D, worldIn.random.nextFloat() / 4.0F);
                }
            }

            ConnectedVerticalSidedPlanFireplaceBlock.updateChimneys(isActivated, state, pos, worldIn);

            final Direction direction = state.getValue(ConnectedVerticalSidedBlock.FACING);
            worldIn.getBlockState(pos.relative(direction.getClockWise())).handleNeighborChanged(worldIn, pos.relative(direction.getClockWise()), this, pos, false);
            worldIn.getBlockState(pos.relative(direction.getCounterClockWise())).handleNeighborChanged(worldIn, pos.relative(direction.getCounterClockWise()), this, pos, false);
        }
    }

    @Override
    public void neighborChanged(final BlockState state, final Level worldIn, final BlockPos pos, final Block blockIn, final BlockPos fromPos, final boolean isMoving) {
        if(state.getValue(ConnectedVerticalBlock.VERTICAL_CONNECTION) != BlockStatePropertiesAA.VerticalConnection.BOTH && state.getValue(ConnectedVerticalBlock.VERTICAL_CONNECTION) != BlockStatePropertiesAA.VerticalConnection.UNDER) {
            final BlockState newState = worldIn.getBlockState(fromPos);
            if(newState.getBlock() == this) {
                final Direction facing = state.getValue(ConnectedVerticalSidedBlock.FACING);
                if(newState.getValue(ConnectedVerticalSidedBlock.FACING) == facing) {
                    if((facing.equals(Direction.NORTH) || facing.equals(Direction.SOUTH)) && fromPos.getZ() != pos.getZ())
                        return;
                    if((facing.equals(Direction.EAST) || facing.equals(Direction.WEST)) && fromPos.getX() != pos.getX())
                        return;

                    final boolean burning = newState.getValue(ConnectedVerticalSidedPlanFireplaceBlock.LIT);
                    if(burning != state.getValue(ConnectedVerticalSidedPlanFireplaceBlock.LIT)) {
                        if(newState.getValue(ConnectedVerticalSidedPlanFireplaceBlock.LIT) && state.getValue(WaterloggedBlock.WATERLOGGED)) {
                            return;
                        }
                        worldIn.setBlock(pos, state.setValue(ConnectedVerticalSidedPlanFireplaceBlock.LIT, burning), 10);
                        final BlockPos newPos = pos.relative(facing.getClockWise()).equals(fromPos) ? pos.relative(facing.getCounterClockWise()) : pos.relative(facing.getClockWise());
                        worldIn.getBlockState(newPos).handleNeighborChanged(worldIn, newPos, this, pos, false);
                    }
                }
            }
        }
    }

    public static void updateChimneys(final boolean isActivatedIn, final BlockState blockStateIn, final BlockPos posIn, final Level worldIn) {
        BlockPos pos = posIn;
        BlockState blockState = null;
        while((blockState = worldIn.getBlockState(pos)) != null && blockState.getBlock() instanceof ConnectedVerticalSidedPlanFireplaceBlock) {
            pos = pos.above();
        }
        pos = pos.below();
        // UP
        ConnectedVerticalSidedPlanFireplaceBlock.updateChimneyConductsAtSide(isActivatedIn, pos.above(), worldIn);
        // Sides
        ConnectedVerticalSidedPlanFireplaceBlock.updateRightChimneys(isActivatedIn, blockStateIn, pos, worldIn);
        ConnectedVerticalSidedPlanFireplaceBlock.updateLeftChimneys(isActivatedIn, blockStateIn, pos, worldIn);
    }

    public static void updateLeftChimneys(final boolean isActivatedIn, final BlockState blockStateIn, final BlockPos posIn, final Level worldIn) {
        ConnectedVerticalSidedPlanFireplaceBlock.updateChimneyConductsAtSide(isActivatedIn, posIn.above(), worldIn);
        final Direction facing = blockStateIn.getValue(ConnectedVerticalSidedBlock.FACING);
        final HorizontalConnection horizontalConnection = blockStateIn.getValue(ConnectedVerticalSidedPlanBlock.HORIZONTAL_CONNECTION);
        if((HorizontalConnection.RIGHT.equals(horizontalConnection) || HorizontalConnection.BOTH.equals(horizontalConnection))) {

            BlockPos rightBlockPos = null;
            if(Direction.NORTH.equals(facing) || Direction.SOUTH.equals(facing)) {
                rightBlockPos = posIn.offset(-1, 0, 0);
            } else if(Direction.EAST.equals(facing) || Direction.WEST.equals(facing)) {
                rightBlockPos = posIn.offset(0, 0, -1);
            }

            final BlockState leftBlockState = worldIn.getBlockState(rightBlockPos);

            if(leftBlockState.getBlock() instanceof ConnectedVerticalSidedPlanFireplaceBlock) {
                ConnectedVerticalSidedPlanFireplaceBlock.updateLeftChimneys(isActivatedIn, blockStateIn, rightBlockPos, worldIn);
            }
        }
    }

    public static void updateRightChimneys(final boolean isActivatedIn, final BlockState blockStateIn, final BlockPos posIn, final Level worldIn) {
        ConnectedVerticalSidedPlanFireplaceBlock.updateChimneyConductsAtSide(isActivatedIn, posIn.above(), worldIn);

        final Direction facing = blockStateIn.getValue(ConnectedVerticalSidedBlock.FACING);
        final HorizontalConnection horizontalConnection = blockStateIn.getValue(ConnectedVerticalSidedPlanBlock.HORIZONTAL_CONNECTION);
        if((HorizontalConnection.LEFT.equals(horizontalConnection) || HorizontalConnection.BOTH.equals(horizontalConnection))) {

            BlockPos rightBlockPos = null;
            if(Direction.NORTH.equals(facing) || Direction.SOUTH.equals(facing)) {
                rightBlockPos = posIn.offset(1, 0, 0);
            } else if(Direction.EAST.equals(facing) || Direction.WEST.equals(facing)) {
                rightBlockPos = posIn.offset(0, 0, 1);
            }

            final BlockState rightBlockState = worldIn.getBlockState(rightBlockPos);

            if(rightBlockState.getBlock() instanceof ConnectedVerticalSidedPlanFireplaceBlock) {
                ConnectedVerticalSidedPlanFireplaceBlock.updateRightChimneys(isActivatedIn, blockStateIn, rightBlockPos, worldIn);
            }
        }
    }

    public static void updateChimneyConductsAtSide(final boolean isActivatedIn, final BlockPos blockPosIn, final Level worldIn) {
        final BlockState blockState = worldIn.getBlockState(blockPosIn);
        if(blockState.getBlock() instanceof ChimneyBlockDoT) {
            ChimneyBlockDoT.updateAllChimneyConductParts(isActivatedIn, blockState, blockPosIn, worldIn);
        }
    }

    @Override
    public boolean placeLiquid(final LevelAccessor world, final BlockPos pos, final BlockState state, final FluidState fluid) {
        if(state.getValue(BlockStateProperties.WATERLOGGED) || fluid.getType() != Fluids.WATER) {
            return false;
        }
        if(state.getValue(ConnectedVerticalSidedPlanFireplaceBlock.LIT)) {
            world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        world.setBlock(pos, state.setValue(WaterloggedBlock.WATERLOGGED, true).setValue(ConnectedVerticalSidedPlanFireplaceBlock.LIT, false), 10);
        world.scheduleTick(pos, fluid.getType(), fluid.getType().getTickDelay(world));
        return true;
    }

    
    @Override
    public void animateTick(final BlockState stateIn, final Level worldIn, final BlockPos pos, final RandomSource rand) {
        if(stateIn.getValue(ConnectedVerticalBlock.VERTICAL_CONNECTION) != BlockStatePropertiesAA.VerticalConnection.BOTH && stateIn.getValue(ConnectedVerticalBlock.VERTICAL_CONNECTION) != BlockStatePropertiesAA.VerticalConnection.UNDER && stateIn.getValue(ConnectedVerticalSidedPlanFireplaceBlock.LIT)) {
            if(rand.nextInt(24) == 0) {
                worldIn.playLocalSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
            }
            for(int i = 0; i < 3; ++i) {
                worldIn.addParticle(ParticleTypes.SMOKE, pos.getX() + rand.nextDouble() * 0.5F + 0.25F, pos.getY() + rand.nextDouble() * 0.5F + 0.6F, pos.getZ() + rand.nextDouble() * 0.5F + 0.25F, 0.0F, 0.0F, 0.0F);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        Utils.addTooltip(tooltipComponents, Utils.TOOLTIP_FIREPLACE);
    }
}