package org.dawnoftime.dawnoftime.block.roman;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.block.IBlockGeneration;
import org.dawnoftime.dawnoftime.block.templates.BlockDoT;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.dawnoftime.dawnoftime.util.Utils;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nullable;
import java.util.List;
import static org.dawnoftime.dawnoftime.util.VoxelShapes.CYPRESS_SHAPES;

public class CypressBlock extends BlockDoT implements IBlockGeneration {
    public static final IntegerProperty SIZE = BlockStatePropertiesAA.SIZE_0_5;

    public CypressBlock(final Properties properties) {
        super(properties, CYPRESS_SHAPES);
        this.registerDefaultState(this.defaultBlockState().setValue(CypressBlock.SIZE, 1));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CypressBlock.SIZE);
    }

    @Override
    public int getShapeIndex(final @NotNull BlockState state, final @NotNull BlockGetter worldIn, final @NotNull BlockPos pos, final @NotNull CollisionContext context) {
        return state.getValue(CypressBlock.SIZE);
    }

    @Override
    public boolean canSurvive(final @NotNull BlockState state, final @NotNull LevelReader worldIn, final BlockPos pos) {
        return Block.canSupportCenter(worldIn, pos.below(), Direction.UP) || worldIn.getBlockState(pos.below()).getBlock() == this;
    }

    @Override
    public @NotNull InteractionResult use(final @NotNull BlockState state, final @NotNull Level worldIn, final @NotNull BlockPos pos, final Player player, final InteractionHand handIn, final BlockHitResult hit) {
        final ItemStack heldItemStack = player.getItemInHand(handIn);
        if (player.isCrouching()) {
            //We remove the highest CypressBlock
            final BlockPos topPos = this.getHighestCypressPos(worldIn, pos);
            if (topPos != pos) {
                if (!worldIn.isClientSide()) {
                    worldIn.setBlock(topPos, Blocks.AIR.defaultBlockState(), 35);
                    if (!player.isCreative()) {
                        Block.dropResources(state, worldIn, pos, null, player, heldItemStack);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        } else if (!heldItemStack.isEmpty() && heldItemStack.getItem() == this.asItem()) {
            //We put a CypressBlock on top of the cypress
            final BlockPos topPos = this.getHighestCypressPos(worldIn, pos).above();
            if (topPos.getY() <= Utils.HIGHEST_Y) {
                if (!worldIn.isClientSide() && worldIn.getBlockState(topPos).isAir()) {
                    worldIn.setBlock(topPos, this.defaultBlockState(), 11);
                    if (!player.isCreative()) {
                        heldItemStack.shrink(1);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    private BlockPos getHighestCypressPos(final Level worldIn, final BlockPos pos) {
        int yOffset;
        for (yOffset = 0; yOffset + pos.getY() <= Utils.HIGHEST_Y; yOffset++) {
            if (worldIn.getBlockState(pos.above(yOffset)).getBlock() != this) {
                break;
            }
        }
        return pos.above(yOffset - 1);
    }

    @Override
    public VoxelShape getBlockSupportShape(final BlockState p_230335_1_, final BlockGetter p_230335_2_, final BlockPos p_230335_3_) {
        return Shapes.empty();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        BlockState adjacentState = context.getLevel().getBlockState(context.getClickedPos().above());
        final int size = adjacentState.getBlock() == this ? Math.min(adjacentState.getValue(CypressBlock.SIZE) + 1, 5) : 1;
        if (size < 3) {
            return this.defaultBlockState().setValue(CypressBlock.SIZE, size);
        }
        adjacentState = context.getLevel().getBlockState(context.getClickedPos().below());
        return this.defaultBlockState().setValue(CypressBlock.SIZE, adjacentState.getBlock() == this ? size : 0);
    }

    @Override
    public BlockState updateShape(final BlockState stateIn, final Direction facing, final BlockState facingState, final LevelAccessor worldIn, final BlockPos currentPos, final BlockPos facingPos) {
        if (!facing.getAxis().isVertical()) {
            return stateIn;
        }
        if (!this.canSurvive(stateIn, worldIn, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        BlockState adjacentState = worldIn.getBlockState(currentPos.above());
        final int size = adjacentState.getBlock() == this
                ? Math.min(adjacentState.getValue(CypressBlock.SIZE) + 1, 5)
                : 1;
        if (size < 3) {
            return this.defaultBlockState().setValue(CypressBlock.SIZE, size);
        }
        adjacentState = worldIn.getBlockState(currentPos.below());
        return this.defaultBlockState().setValue(CypressBlock.SIZE, adjacentState.getBlock() == this ? size : 0);
    }

    @Override
    public void animateTick(final BlockState stateIn, final Level worldIn, final BlockPos pos, final RandomSource rand) {
        if (worldIn.isRainingAt(pos.above()) && rand.nextInt(15) == 1) {
            final BlockPos posDown = pos.below();
            final BlockState stateDown = worldIn.getBlockState(posDown);
            if (!stateDown.canOcclude() || !stateDown.isFaceSturdy(worldIn, posDown, Direction.UP)) {
                final double x = pos.getX() + rand.nextFloat();
                final double y = pos.getY() - 0.05D;
                final double z = pos.getZ() + rand.nextFloat();
                worldIn.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource source) {
        if (state.getValue(SIZE) == 1) {
            BlockPos abovePos = pos.above();
            if (level.getBlockState(pos.below(5)).getBlock() != this) {
                if (level.isEmptyBlock(abovePos)) {
                    if (source.nextInt(16) == 0) {
                        level.setBlock(abovePos, this.defaultBlockState(), 2);
                    }
                }
            }
        }
    }

    @Override
    public void appendHoverText(final ItemStack stack, @Nullable final BlockGetter worldIn, final List<Component> tooltip, final TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        Utils.addTooltip(tooltip, Utils.TOOLTIP_COLUMN);
    }

    @Override
    public boolean generateOnPos(WorldGenLevel world, BlockPos pos, BlockState state, RandomSource random) {
        final BlockState groundState = world.getBlockState(pos.below());

        if (!groundState.is(BlockTags.DIRT)) {
            return false;
        }

        final int maxSize = 2 + random.nextInt(5);
        for (int i = 0; i < maxSize; i++) {
            if (!world.getBlockState(pos.above(i)).isAir()) {
                return false;
            }
        }
        world.setBlock(pos, state.setValue(CypressBlock.SIZE, 0), 2);
        int size = 1;
        for (int i = maxSize; i > 0; i--) {
            world.setBlock(pos.above(i), state.setValue(CypressBlock.SIZE, size), 2);
            if (size < 5) {
                size++;
            }
        }
        return true;
    }
}