/**
 *
 */
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.block.general.FireplaceBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties.VerticalConnection;
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Seynax
 */
public class BaseChimneyDoTB extends ColumnConnectibleBlock {
    private static final VoxelShape[] SHAPES = BaseChimneyDoTB.makeShapes();
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public BaseChimneyDoTB(final Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.LIT, true));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.LIT);
    }

    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        final BlockState state = super.getStateForPlacement(context);
        final Level world = context.getLevel();
        BlockPos belowBlockPos = context.getClickedPos().below();
        BlockState blockState = world.getBlockState(belowBlockPos);

        if(blockState == null) {
            return state;
        }

        if(blockState.getBlock() instanceof BaseChimneyDoTB) {
            return state.setValue(BlockStateProperties.LIT, blockState.getValue(BlockStateProperties.LIT));
        }

        if(blockState.getBlock() instanceof MultiblockFireplaceBlock) {
            BlockState foundBlockState = blockState;
            belowBlockPos = belowBlockPos.below();
            while((foundBlockState = world.getBlockState(belowBlockPos)) != null && foundBlockState.getBlock() instanceof MultiblockFireplaceBlock) {
                blockState = foundBlockState;
                belowBlockPos = belowBlockPos.below();
            }
            return state.setValue(BlockStateProperties.LIT, blockState.getValue(BlockStateProperties.LIT));
        }
        return state;
    }

    @Override
    public InteractionResult use(final BlockState blockStateIn, final Level worldIn, final BlockPos blockPosIn, final Player player, final InteractionHand handIn, final BlockHitResult hit) {
        if(super.use(blockStateIn, worldIn, blockPosIn, player, handIn, hit).equals(InteractionResult.SUCCESS))
            return InteractionResult.SUCCESS;

        final int activation = DoTBUtils.changeBlockLitStateWithItemOrCreativePlayer(blockStateIn, worldIn, blockPosIn, player, handIn);
        if(activation >= 0) {
            final boolean isActivated = activation == 1;

            BaseChimneyDoTB.updateAllChimneyConductParts(isActivated, blockStateIn, blockPosIn, worldIn);
            BaseChimneyDoTB.updateFireplace(isActivated, blockPosIn, worldIn);

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void onProjectileHit(final Level worldIn, final BlockState state, final BlockHitResult hit, final Projectile projectile) {

        int activation = -1;

        if(!state.getValue(WaterloggedBlock.WATERLOGGED) && !state.getValue(FireplaceBlock.LIT) && (projectile instanceof Arrow && ((Arrow) projectile).isOnFire() || projectile instanceof Fireball)) {
            activation = 1;
        } else if(state.getValue(FireplaceBlock.LIT) && (projectile instanceof Snowball || projectile instanceof ThrowableProjectile && PotionUtils.getPotion(((ThrowableItemProjectile) projectile).getItem()).getEffects().size() <= 0)) {
            activation = 0;
        }

        if(activation >= 0) {
            final BlockPos pos = hit.getBlockPos();
            final boolean isActivated = activation == 1;

            if(!worldIn.isClientSide()) {
                BaseChimneyDoTB.updateAllChimneyConductParts(isActivated, state, pos, worldIn);
                worldIn.playSound(null, pos, isActivated ? SoundEvents.FIRE_AMBIENT : SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else if(!isActivated && worldIn.isClientSide()) {
                for(int i = 0; i < worldIn.random.nextInt(1) + 1; ++i) {
                    worldIn.addParticle(ParticleTypes.CLOUD, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, worldIn.random.nextFloat() / 4.0F, 2.5E-5D, worldIn.random.nextFloat() / 4.0F);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(final BlockState stateIn, final Level worldIn, final BlockPos pos, final RandomSource rand) {
        if(stateIn.getValue(WaterloggedBlock.WATERLOGGED) || !stateIn.getValue(BlockStateProperties.LIT)) {
            return;
        }
        if(stateIn.getValue(ColumnConnectibleBlock.VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.UNDER || stateIn.getValue(ColumnConnectibleBlock.VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.NONE) {
            worldIn.addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, true, pos.getX() + rand.nextDouble() * 0.5D + 0.25D, pos.getY() + rand.nextDouble() * 0.5D + 0.3D, pos.getZ() + rand.nextDouble() * 0.5D + 0.25D, 0.0D, 0.07D, 0.0D);
            worldIn.addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, pos.getX() + rand.nextDouble() * 0.5D + 0.25D, pos.getY() + rand.nextDouble() * 0.5D + 0.3D, pos.getZ() + rand.nextDouble() * 0.5D + 0.25D, 0.0D, 0.04D, 0.0D);
        }
    }

    @Override
    public VoxelShape getShape(final BlockState state, final BlockGetter worldIn, final BlockPos pos, final CollisionContext context) {
        if(state.getValue(ColumnConnectibleBlock.VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.NONE) {
            return BaseChimneyDoTB.SHAPES[0];
        }
        return BaseChimneyDoTB.SHAPES[state.getValue(ColumnConnectibleBlock.VERTICAL_CONNECTION).getIndex() - 1];
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * 0 : None or Under <p/>
     * 1 : Above
     * 2 : Both
     */
    private static VoxelShape[] makeShapes() {
        return new VoxelShape[] {
                Shapes.or(
                        Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D),
                        Block.box(1.0D, 8.0D, 1.0D, 15.0D, 11.0D, 15.0D)
                ),
                Shapes.or(
                        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
                        Block.box(2.0D, 8.0D, 2.0D, 14.0D, 16.0D, 14.0D)
                ),
                Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D)
        };
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn,
                                  BlockPos currentPos, BlockPos facingPos) {
        if(facingState.getBlock() instanceof BaseChimneyDoTB || facingState.getBlock() instanceof MultiblockFireplaceBlock) {
            stateIn.setValue(LIT, facingState.getValue(LIT));
        }

        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public void appendHoverText(final ItemStack stack, @Nullable final BlockGetter worldIn, final List<Component> tooltip, final TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        DoTBUtils.addTooltip(tooltip, DoTBUtils.TOOLTIP_FIREPLACE);
    }

    public static void updateAllChimneyConductParts(final boolean isActivatedIn, BlockState stateIn, final BlockPos blockPosIn, final Level worldIn) {
        stateIn = stateIn.setValue(BlockStateProperties.LIT, isActivatedIn);
        worldIn.setBlock(blockPosIn, stateIn, 10);
        BaseChimneyDoTB.updateIsActivatedInAllPartsOfBottom(isActivatedIn, stateIn, blockPosIn, worldIn);
        BaseChimneyDoTB.updateIsActivatedInAllPartsOfTop(isActivatedIn, stateIn, blockPosIn, worldIn);
    }

    public static void updateIsActivatedInAllPartsOfBottom(final boolean isActivatedIn, final BlockState stateIn, final BlockPos blockPosIn, final Level worldIn) {
        BlockState blockState = null;
        BlockPos blockPos = blockPosIn;
        while((blockState = worldIn.getBlockState(blockPos = blockPos.below())) != null && blockState.getBlock() instanceof BaseChimneyDoTB) {
            blockState = blockState.setValue(BlockStateProperties.LIT, isActivatedIn);
            worldIn.setBlock(blockPos, blockState, 10);
        }
    }

    public static void updateIsActivatedInAllPartsOfTop(final boolean isActivatedIn, final BlockState stateIn, final BlockPos blockPosIn, final Level worldIn) {
        BlockState blockState = null;
        BlockPos blockPos = blockPosIn;
        while((blockState = worldIn.getBlockState(blockPos = blockPos.above())) != null && blockState.getBlock() instanceof BaseChimneyDoTB) {
            blockState = blockState.setValue(BlockStateProperties.LIT, isActivatedIn);
            worldIn.setBlock(blockPos, blockState, 10);
        }
    }

    public static void updateFireplace(final boolean isActivatedIn, final BlockPos blockPosIn, final Level worldIn) {
        BlockState blockState = null;
        BlockPos blockPos = blockPosIn;
        while((blockState = worldIn.getBlockState(blockPos = blockPos.below())) != null && blockState.getBlock() instanceof BaseChimneyDoTB) {
        }

        while((blockState = worldIn.getBlockState(blockPos = blockPos.below())) != null && blockState.getBlock() instanceof MultiblockFireplaceBlock) {
            final VerticalConnection verticalConnection = blockState.getValue(ColumnConnectibleBlock.VERTICAL_CONNECTION);
            if(verticalConnection != null && VerticalConnection.ABOVE.equals(verticalConnection)) {
                blockState = blockState.setValue(BlockStateProperties.LIT, isActivatedIn);
                worldIn.setBlock(blockPos, blockState, 10);
                final Direction direction = blockState.getValue(SidedColumnConnectibleBlock.FACING);
                worldIn.getBlockState(blockPos.relative(direction.getCounterClockWise())).neighborChanged(worldIn, blockPos.relative(direction.getCounterClockWise()), blockState.getBlock(), blockPos, false);
                worldIn.getBlockState(blockPos.relative(direction.getClockWise())).neighborChanged(worldIn, blockPos.relative(direction.getClockWise()), blockState.getBlock(), blockPos, false);
            }
        }
    }
}