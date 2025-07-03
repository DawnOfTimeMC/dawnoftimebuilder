package org.dawnoftime.dawnoftime.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.dawnoftime.dawnoftime.block.IBlockChain;
import org.dawnoftime.dawnoftime.block.templates.BlockDoT;
import org.dawnoftime.dawnoftime.platform.Services;
import org.dawnoftime.dawnoftime.registry.DoTBItemsRegistry;
import org.dawnoftime.dawnoftime.util.Utils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

import static org.dawnoftime.dawnoftime.util.VoxelShapes.STICK_BUNDLE_SHAPES;

public class StickBundleBlock extends BlockDoT implements IBlockChain {
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    private static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public StickBundleBlock(Properties properties) {
        super(properties.pushReaction(PushReaction.DESTROY), STICK_BUNDLE_SHAPES);
        this.registerDefaultState(this.defaultBlockState().setValue(AGE, 0).setValue(HALF, Half.TOP));
    }

    @Override
    public int getShapeIndex(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return state.getValue(HALF) == Half.BOTTOM ? 1 : 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, AGE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if(!world.getBlockState(pos.below()).canBeReplaced(context) || !canSurvive(this.defaultBlockState(), world, pos))
            return null;
        return super.getStateForPlacement(context);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        worldIn.setBlock(pos.below(), state.setValue(HALF, Half.BOTTOM), 10);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(facing.getAxis().isHorizontal())
            return stateIn;
        if(facing == Direction.UP && stateIn.getValue(HALF) == Half.BOTTOM) {
            if(facingState.getBlock() == this) {
                if(facingState.getValue(HALF) == Half.TOP) {
                    return stateIn;
                }
            }
            return Blocks.AIR.defaultBlockState();
        }
        if(facing == Direction.DOWN && stateIn.getValue(HALF) == Half.TOP) {
            if(facingState.getBlock() == this) {
                if(facingState.getValue(HALF) == Half.BOTTOM) {
                    return stateIn;
                }
            }
            return Blocks.AIR.defaultBlockState();
        }
        return stateIn;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        pos = pos.above();
        BlockState stateUp = worldIn.getBlockState(pos);
        return state.getValue(HALF) == Half.BOTTOM || canSupportCenter(worldIn, pos, Direction.DOWN) || IBlockChain.canBeChained(stateUp, true);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if(!worldIn.isClientSide()) {
            //The StickBundle is empty, we try to put worms on it.
            if(state.getValue(AGE) == 0) {
                ItemStack itemstack = player.getItemInHand(handIn);
                if(itemstack.getItem() == DoTBItemsRegistry.INSTANCE.SILK_WORMS.get() && !itemstack.isEmpty()) {
                    itemstack.shrink(1);
                    worldIn.setBlock(pos, state.setValue(AGE, 1), 10);
                    if(state.getValue(HALF) == Half.TOP) {
                        worldIn.setBlock(pos.below(), this.defaultBlockState().setValue(HALF, Half.BOTTOM).setValue(AGE, 1), 10);
                    } else {
                        worldIn.setBlock(pos.above(), this.defaultBlockState().setValue(HALF, Half.TOP).setValue(AGE, 1), 10);
                    }
                    return InteractionResult.SUCCESS;
                }
            }

            //The StickBundle has fully grown worms, it's time to harvest !
            if(state.getValue(AGE) == 3) {
                List<ItemStack> drops = Utils.getLootList((ServerLevel) worldIn, state, player.getItemInHand(handIn), Objects.requireNonNull(this.builtInRegistryHolder().key()).location().getPath() + "_harvest");
                Utils.dropLootFromList(worldIn, pos, drops, 1.0F);
                worldIn.setBlock(pos, state.setValue(AGE, 0), 10);
                worldIn.playSound(null, pos, SoundEvents.GRASS_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                if(state.getValue(HALF) == Half.TOP) {
                    worldIn.setBlock(pos.below(), this.defaultBlockState().setValue(HALF, Half.BOTTOM).setValue(AGE, 0), 10);
                } else {
                    worldIn.setBlock(pos.above(), this.defaultBlockState().setValue(HALF, Half.TOP).setValue(AGE, 0), 10);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) > 0 && state.getValue(AGE) < 3 && state.getValue(HALF) == Half.TOP;
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        int growth = state.getValue(AGE);
        if(growth > 0 && growth < 3) {
            if(random.nextInt(Services.PLATFORM.getConfig().stickBundleGrowthChance) == 0) {
                worldIn.setBlock(pos, worldIn.getBlockState(pos).setValue(AGE, growth + 1), 10);
                worldIn.setBlock(pos.below(), worldIn.getBlockState(pos.below()).setValue(AGE, growth + 1), 10);
            }
        }
    }

    @Override
    public boolean canConnectToChainUnder(BlockState state) {
        return false;
    }

    @Override
    public void playerWillDestroy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        // Prevents item from dropping in creative by removing the part that gives the item with a setBlock.
        if (!level.isClientSide() && player.isCreative()) {
            if (state.getValue(HALF) == Half.TOP) {
                BlockPos adjacentPos = pos.below();
                BlockState adjacentState = level.getBlockState(adjacentPos);
                if (adjacentState.is(this) && adjacentState.getValue(HALF) == Half.BOTTOM) {
                    level.setBlock(adjacentPos, Blocks.AIR.defaultBlockState(), 35);
                    // Event that plays the "break block" sound.
                    level.levelEvent(player, 2001, adjacentPos, Block.getId(state));
                }
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }
}