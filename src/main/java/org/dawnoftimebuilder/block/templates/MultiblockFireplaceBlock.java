package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
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
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.block.general.FireplaceBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties.HorizontalConnection;
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nullable;
import java.util.List;

public class MultiblockFireplaceBlock extends SidedPlaneConnectibleBlock {
    public final static void updateChimneys(final boolean isActivatedIn, final BlockState blockStateIn, final BlockPos posIn, final Level worldIn) {
        BlockPos pos = posIn;

        BlockState blockState = null;
        while((blockState = worldIn.getBlockState(pos)) != null && blockState.getBlock() instanceof MultiblockFireplaceBlock) {
            pos = pos.above();
        }
        pos = pos.below();

        // UP

        MultiblockFireplaceBlock.updateChimneyConductsAtSide(isActivatedIn, pos.above(), worldIn);

        // Sides

        MultiblockFireplaceBlock.updateRightChimneys(isActivatedIn, blockStateIn, pos, worldIn);
        MultiblockFireplaceBlock.updateLeftChimneys(isActivatedIn, blockStateIn, pos, worldIn);
    }

    public final static void updateLeftChimneys(final boolean isActivatedIn, final BlockState blockStateIn, final BlockPos posIn, final Level worldIn) {

        MultiblockFireplaceBlock.updateChimneyConductsAtSide(isActivatedIn, posIn.above(), worldIn);

        final Direction facing = blockStateIn.getValue(SidedColumnConnectibleBlock.FACING);
        if(facing != null) {
            final HorizontalConnection horizontalConnection = blockStateIn.getValue(SidedPlaneConnectibleBlock.HORIZONTAL_CONNECTION);
            if(horizontalConnection != null && (HorizontalConnection.RIGHT.equals(horizontalConnection) || HorizontalConnection.BOTH.equals(horizontalConnection))) {

                BlockPos rightBlockPos = null;
                if(Direction.NORTH.equals(facing) || Direction.SOUTH.equals(facing)) {
                    rightBlockPos = posIn.offset(-1, 0, 0);
                } else if(Direction.EAST.equals(facing) || Direction.WEST.equals(facing)) {
                    rightBlockPos = posIn.offset(0, 0, -1);
                }

                final BlockState leftBlockState = worldIn.getBlockState(rightBlockPos);

                if(leftBlockState != null && leftBlockState.getBlock() instanceof MultiblockFireplaceBlock) {
                    MultiblockFireplaceBlock.updateLeftChimneys(isActivatedIn, blockStateIn, rightBlockPos, worldIn);
                }
            }
        }
    }

    public final static void updateRightChimneys(final boolean isActivatedIn, final BlockState blockStateIn, final BlockPos posIn, final Level worldIn) {
        MultiblockFireplaceBlock.updateChimneyConductsAtSide(isActivatedIn, posIn.above(), worldIn);

        final Direction facing = blockStateIn.getValue(SidedColumnConnectibleBlock.FACING);
        if(facing != null) {
            final HorizontalConnection horizontalConnection = blockStateIn.getValue(SidedPlaneConnectibleBlock.HORIZONTAL_CONNECTION);
            if(horizontalConnection != null && (HorizontalConnection.LEFT.equals(horizontalConnection) || HorizontalConnection.BOTH.equals(horizontalConnection))) {

                BlockPos rightBlockPos = null;
                if(Direction.NORTH.equals(facing) || Direction.SOUTH.equals(facing)) {
                    rightBlockPos = posIn.offset(1, 0, 0);
                } else if(Direction.EAST.equals(facing) || Direction.WEST.equals(facing)) {
                    rightBlockPos = posIn.offset(0, 0, 1);
                }

                final BlockState rightBlockState = worldIn.getBlockState(rightBlockPos);

                if(rightBlockState != null && rightBlockState.getBlock() instanceof MultiblockFireplaceBlock) {
                    MultiblockFireplaceBlock.updateRightChimneys(isActivatedIn, blockStateIn, rightBlockPos, worldIn);
                }
            }
        }
    }

    public final static void updateChimneyConductsAtSide(final boolean isActivatedIn, final BlockPos blockPosIn, final Level worldIn) {
        final BlockState blockState = worldIn.getBlockState(blockPosIn);

        if(blockState != null && blockState.getBlock() instanceof BaseChimneyDoTB) {
            BaseChimneyDoTB.updateAllChimneyConductParts(isActivatedIn, blockState, blockPosIn, worldIn);
        }
    }

    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    private static final VoxelShape[] SHAPES = DoTBUtils.GenerateHorizontalShapes(MultiblockFireplaceBlock.makeShapes());

    public MultiblockFireplaceBlock(final Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(MultiblockFireplaceBlock.LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(MultiblockFireplaceBlock.LIT);
    }

    @Override
    public VoxelShape getShape(final BlockState state, final BlockGetter worldIn, final BlockPos pos, final CollisionContext context) {
        final int index = state.getValue(ColumnConnectibleBlock.VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.NONE ? 0 : 1;
        return MultiblockFireplaceBlock.SHAPES[index + state.getValue(SidedColumnConnectibleBlock.FACING).get2DDataValue() * 2];
    }

    /**
     * @return Stores VoxelShape for "South" with index : <p/>
     * 0 : S Small fireplace <p/>
     * 1 : S Big fireplace <p/>
     */
    private static VoxelShape[] makeShapes() {
        return new VoxelShape[] {
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 12.0D)
        };
    }

    @Override
    public InteractionResult use(final BlockState stateIn, final Level worldIn, final BlockPos pos, final Player player, final InteractionHand handIn, final BlockHitResult hit) {
        if(player.getMainHandItem() != null && player.getMainHandItem().getItem() instanceof BlockItem &&
                ((BlockItem) player.getMainHandItem().getItem()).getBlock() instanceof MultiblockFireplaceBlock) {
            return InteractionResult.PASS;
        }
        if(stateIn.getValue(ColumnConnectibleBlock.VERTICAL_CONNECTION) != DoTBBlockStateProperties.VerticalConnection.BOTH && stateIn.getValue(ColumnConnectibleBlock.VERTICAL_CONNECTION) != DoTBBlockStateProperties.VerticalConnection.UNDER) {
            final int activation = DoTBUtils.changeBlockLitStateWithItemOrCreativePlayer(stateIn, worldIn, pos, player, handIn);
            if(activation >= 0) {
                final Direction direction = stateIn.getValue(SidedColumnConnectibleBlock.FACING);
                worldIn.getBlockState(pos.relative(direction.getCounterClockWise())).neighborChanged(worldIn, pos.relative(direction.getCounterClockWise()), this, pos, false);
                worldIn.getBlockState(pos.relative(direction.getClockWise())).neighborChanged(worldIn, pos.relative(direction.getClockWise()), stateIn.getBlock(), pos, false);

                MultiblockFireplaceBlock.updateChimneys(activation == 1, stateIn, pos, worldIn);

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
            if(projectile instanceof AbstractArrow && ((AbstractArrow) projectile).isOnFire()) {
                activation = 1;
            } else if(projectile instanceof AbstractArrow) {
                activation = 1;
            }
        } else if(state.getValue(FireplaceBlock.LIT) && (projectile instanceof Snowball || projectile instanceof ThrowableProjectile && PotionUtils.getPotion(((ThrowableItemProjectile) projectile).getItem()).getEffects().size() <= 0)) {
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

            MultiblockFireplaceBlock.updateChimneys(isActivated, state, pos, worldIn);

            final Direction direction = state.getValue(SidedColumnConnectibleBlock.FACING);
            worldIn.getBlockState(pos.relative(direction.getClockWise())).neighborChanged(worldIn, pos.relative(direction.getClockWise()), this, pos, false);
            worldIn.getBlockState(pos.relative(direction.getCounterClockWise())).neighborChanged(worldIn, pos.relative(direction.getCounterClockWise()), this, pos, false);
        }
    }

    @Override
    public void neighborChanged(final BlockState state, final Level worldIn, final BlockPos pos, final Block blockIn, final BlockPos fromPos, final boolean isMoving) {
        if(state.getValue(ColumnConnectibleBlock.VERTICAL_CONNECTION) != DoTBBlockStateProperties.VerticalConnection.BOTH && state.getValue(ColumnConnectibleBlock.VERTICAL_CONNECTION) != DoTBBlockStateProperties.VerticalConnection.UNDER) {
            final BlockState newState = worldIn.getBlockState(fromPos);
            if(newState.getBlock() == this) {
                final Direction facing = state.getValue(SidedColumnConnectibleBlock.FACING);
                if(newState.getValue(SidedColumnConnectibleBlock.FACING) == facing) {
                    if((facing.equals(Direction.NORTH) || facing.equals(Direction.SOUTH)) && fromPos.getZ() != pos.getZ())
                        return;
                    if((facing.equals(Direction.EAST) || facing.equals(Direction.WEST)) && fromPos.getX() != pos.getX())
                        return;

                    final boolean burning = newState.getValue(MultiblockFireplaceBlock.LIT);
                    if(burning != state.getValue(MultiblockFireplaceBlock.LIT)) {
                        if(newState.getValue(MultiblockFireplaceBlock.LIT) && state.getValue(WaterloggedBlock.WATERLOGGED)) {
                            return;
                        }
                        worldIn.setBlock(pos, state.setValue(MultiblockFireplaceBlock.LIT, burning), 10);
                        final BlockPos newPos = pos.relative(facing.getClockWise()).equals(fromPos) ? pos.relative(facing.getCounterClockWise()) : pos.relative(facing.getClockWise());
                        worldIn.getBlockState(newPos).neighborChanged(worldIn, newPos, this, pos, false);
                    }
                }
            }
        }
    }

    @Override
    public boolean placeLiquid(final LevelAccessor world, final BlockPos pos, final BlockState state, final FluidState fluid) {
        if(state.getValue(BlockStateProperties.WATERLOGGED) || fluid.getType() != Fluids.WATER) {
            return false;
        }
        if(state.getValue(MultiblockFireplaceBlock.LIT)) {
            world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        world.setBlock(pos, state.setValue(WaterloggedBlock.WATERLOGGED, true).setValue(MultiblockFireplaceBlock.LIT, false), 10);
        world.scheduleTick(pos, fluid.getType(), fluid.getType().getTickDelay(world));
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(final BlockState stateIn, final Level worldIn, final BlockPos pos, final RandomSource rand) {
        if(stateIn.getValue(ColumnConnectibleBlock.VERTICAL_CONNECTION) != DoTBBlockStateProperties.VerticalConnection.BOTH && stateIn.getValue(ColumnConnectibleBlock.VERTICAL_CONNECTION) != DoTBBlockStateProperties.VerticalConnection.UNDER && stateIn.getValue(MultiblockFireplaceBlock.LIT)) {
            if(rand.nextInt(24) == 0) {
                worldIn.playLocalSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
            }
            for(int i = 0; i < 3; ++i) {
                worldIn.addParticle(ParticleTypes.SMOKE, pos.getX() + rand.nextDouble() * 0.5F + 0.25F, pos.getY() + rand.nextDouble() * 0.5F + 0.6F, pos.getZ() + rand.nextDouble() * 0.5F + 0.25F, 0.0F, 0.0F, 0.0F);
            }
        }
    }

    @Override
    public void appendHoverText(final ItemStack stack, @Nullable final BlockGetter worldIn, final List<Component> tooltip, final TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        DoTBUtils.addTooltip(tooltip, DoTBUtils.TOOLTIP_FIREPLACE);
    }
}