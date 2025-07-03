package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.EmptyFluid;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.block.general.WaterTrickleBlock;
import org.dawnoftime.dawnoftime.mixin.impl.BucketItemAccessor;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.dawnoftime.dawnoftime.util.Utils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PoolBlock extends BlockDoT {

    public final int maxLevel;
    public final int faucetLevel;

    public PoolBlock(final Properties propertiesIn, final int maxLevelIn, final int faucetLevelIn, VoxelShape[] shapes) {
        super(propertiesIn.lightLevel((state) -> 1), shapes);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.NORTH, false).setValue(BlockStateProperties.EAST, false).setValue(BlockStateProperties.SOUTH, false).setValue(BlockStateProperties.WEST, false).setValue(BlockStatePropertiesAA.HAS_PILLAR, false).setValue(BlockStatePropertiesAA.LEVEL, 0));
        this.maxLevel = maxLevelIn;
        this.faucetLevel = faucetLevelIn;
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.NORTH).add(BlockStateProperties.EAST).add(BlockStateProperties.SOUTH).add(BlockStateProperties.WEST).add(BlockStatePropertiesAA.HAS_PILLAR).add(BlockStatePropertiesAA.LEVEL);
    }

    @Override
    public int getShapeIndex(final @NotNull BlockState state, final @NotNull BlockGetter worldIn, final @NotNull BlockPos pos, final @NotNull CollisionContext context) {
        int index = 0;
        if(state.getValue(BlockStateProperties.NORTH)) {
            index += 1;
        }
        if(state.getValue(BlockStateProperties.EAST)) {
            index += 2;
        }
        if(state.getValue(BlockStateProperties.SOUTH)) {
            index += 4;
        }
        if(state.getValue(BlockStateProperties.WEST)) {
            index += 8;
        }
        if(state.getValue(BlockStatePropertiesAA.HAS_PILLAR)) {
            index += 16;
        }
        return index;
    }

    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext contextIn) {
        /*
         * Determined sides AND level by the highest level of surrounding adjacent pools. If none is found or the highest level is equal to 0, checks if a tap or a trickle of water exists above this pool or one of the adjacent
         * pools around, if yes, applies the level to 1 and launches a tick schedule otherwise the level is 0.
         */
        final PoolLevelAndSides levelAndSides = PoolBlock.levelOfPoolAround(contextIn.getClickedPos(), contextIn.getLevel());
        BlockState state = this.defaultBlockState().setValue(BlockStateProperties.NORTH, levelAndSides.south).setValue(BlockStateProperties.SOUTH, levelAndSides.north).setValue(BlockStateProperties.EAST, levelAndSides.right).setValue(BlockStateProperties.WEST, levelAndSides.left);

        int level = levelAndSides.level;
        if(level <= 0 && EnumActivatorState.ENABLED.equals(PoolBlock.hasOnePoolActivatorAround(contextIn.getClickedPos(), contextIn.getLevel()))) {
            level = 1;
            if(!contextIn.getLevel().isClientSide()) {
                contextIn.getLevel().scheduleTick(contextIn.getClickedPos(), this, 5);
            }
        }

        state = state.setValue(BlockStatePropertiesAA.LEVEL, level);


        return state;
    }

    @Override
    public InteractionResult use(BlockState blockStateIn, final Level worldIn, final BlockPos blockPosIn, final Player playerEntityIn, final InteractionHand handIn, final BlockHitResult blockRayTraceResultIn) {
        final ItemStack itemStack = playerEntityIn.getMainHandItem();
        if(!playerEntityIn.isCrouching()) {
            final int lastLevel = blockStateIn.getValue(BlockStatePropertiesAA.LEVEL);
            int nextLevel = lastLevel;
            boolean tryToChangeLevel = false;
            ItemStack newItemStack = null;

            if(itemStack.getItem() instanceof BucketItem) {
                if(((BucketItemAccessor) itemStack.getItem()).getContent() instanceof WaterFluid) {
                    nextLevel = this.maxLevel;

                    if(!playerEntityIn.isCreative()) {
                        newItemStack = new ItemStack(Items.BUCKET);
                    }
                } else if(((BucketItemAccessor) itemStack.getItem()).getContent() instanceof EmptyFluid) {
                    nextLevel = 0;

                    if(!playerEntityIn.isCreative()) {
                        newItemStack = new ItemStack(Items.WATER_BUCKET);
                    }
                }
                tryToChangeLevel = true;
            } else if(itemStack.getItem() instanceof PotionItem) {
                final Potion potion = PotionUtils.getPotion(itemStack);

                if(potion.getEffects().isEmpty() && nextLevel + 1 < this.maxLevel) {
                    nextLevel++;

                    if(!playerEntityIn.isCreative()) {
                        newItemStack = new ItemStack(Items.GLASS_BOTTLE);
                    }
                }
                tryToChangeLevel = true;
            } else if(itemStack.getItem() instanceof BottleItem) {
                final Potion potion = PotionUtils.getPotion(itemStack);

                if(potion.getEffects().isEmpty() && nextLevel - 1 >= 0) {
                    nextLevel--;

                    if(!playerEntityIn.isCreative()) {
                        newItemStack = Items.POTION.getDefaultInstance();
                    }
                }
                tryToChangeLevel = true;
            }

            if(tryToChangeLevel) {
                if(nextLevel == lastLevel) {
                    return InteractionResult.CONSUME;
                }
                if(newItemStack != null) {
                    itemStack.shrink(1);
                    playerEntityIn.getInventory().add(newItemStack);
                }

                blockStateIn = blockStateIn.setValue(BlockStatePropertiesAA.LEVEL, nextLevel);
                worldIn.setBlock(blockPosIn, blockStateIn, 10);

                if(nextLevel == 0) {
                    PoolBlock.removeWaterAround(blockStateIn, blockPosIn, worldIn);
                }

                return InteractionResult.SUCCESS;
            }
            if(itemStack.isEmpty()) {
                blockStateIn = blockStateIn.setValue(BlockStatePropertiesAA.HAS_PILLAR, !blockStateIn.getValue(BlockStatePropertiesAA.HAS_PILLAR));
                worldIn.setBlock(blockPosIn, blockStateIn, 10);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    /*
     * Seynax : allows the player to prevent the appearance of a pole in the basin below, under sneak conditions
     */
    @Override
    public void setPlacedBy(final Level worldIn, final BlockPos blockPosIn, final BlockState blockStateIn, final LivingEntity entityIn, final ItemStack itemStackIn) {
        if(entityIn instanceof Player && !entityIn.isShiftKeyDown()) {
            super.setPlacedBy(worldIn, blockPosIn, blockStateIn, entityIn, itemStackIn);

            final BlockPos blockPos = blockPosIn.below();
            BlockState blockState = worldIn.getBlockState(blockPos);
            if(blockState.getBlock() == this) {
                blockState = blockState.setValue(BlockStatePropertiesAA.HAS_PILLAR, true);
                worldIn.setBlock(blockPos, blockState, 10);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void tick(BlockState blockStateIn, final ServerLevel serverWorldIn, final BlockPos blockPosIn, final RandomSource randomIn) {
        super.tick(blockStateIn, serverWorldIn, blockPosIn, randomIn);

        final boolean increase = EnumActivatorState.ENABLED.equals(PoolBlock.hasOnePoolActivatorAround(blockPosIn, serverWorldIn));

        if(increase) {
            final int level = blockStateIn.getValue(BlockStatePropertiesAA.LEVEL);

            if(level < this.maxLevel) {
                blockStateIn = blockStateIn.setValue(BlockStatePropertiesAA.LEVEL, level + 1);
                serverWorldIn.setBlock(blockPosIn, blockStateIn, 10);

                if(level + 1 < this.maxLevel) {
                    serverWorldIn.scheduleTick(blockPosIn, this, 5);
                }
            }
        } else {
            int level = blockStateIn.getValue(BlockStatePropertiesAA.LEVEL);

            if(level - 1 >= 0) {
                level--;
                blockStateIn = blockStateIn.setValue(BlockStatePropertiesAA.LEVEL, level);
                serverWorldIn.setBlock(blockPosIn, blockStateIn, 10);

                if(level - 1 >= 0) {
                    serverWorldIn.scheduleTick(blockPosIn, this, 5);
                }
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState stateIn, final Direction directionIn, final BlockState facingStateIn, final LevelAccessor worldIn, final BlockPos currentPosIn, final BlockPos facingPosIn) {
        if(directionIn.getAxis().isHorizontal()) {
            final boolean hasPoolInSide = facingStateIn.getBlock() == this;
            if(hasPoolInSide && facingStateIn.getValue(BlockStatePropertiesAA.LEVEL) >= 0) {
                stateIn = stateIn.setValue(BlockStatePropertiesAA.LEVEL, facingStateIn.getValue(BlockStatePropertiesAA.LEVEL));
            }
            switch(directionIn) {
                case NORTH:
                    stateIn = stateIn.setValue(BlockStateProperties.NORTH, hasPoolInSide);
                    break;
                case EAST:
                    stateIn = stateIn.setValue(BlockStateProperties.EAST, hasPoolInSide);
                    break;
                case SOUTH:
                    stateIn = stateIn.setValue(BlockStateProperties.SOUTH, hasPoolInSide);
                    break;
                case WEST:
                    stateIn = stateIn.setValue(BlockStateProperties.WEST, hasPoolInSide);
                    break;
                default:
                    break;
            }
        }

        int level = stateIn.getValue(BlockStatePropertiesAA.LEVEL);

        if(facingPosIn.getY() == currentPosIn.getY() + 1) {
            final int lastLevel = level;
            final EnumActivatorState state = PoolBlock.hasOnePoolActivatorAround(currentPosIn, worldIn);

            if(EnumActivatorState.ENABLED.equals(state)) {
                if(level < this.maxLevel) {
                    level++;
                }
            } else if(EnumActivatorState.DISABLED.equals(state) && level - 1 >= 0) {
                level--;
            }

            stateIn = stateIn.setValue(BlockStatePropertiesAA.LEVEL, level);

            if(!worldIn.isClientSide() && lastLevel != level) {
                (worldIn).scheduleTick(currentPosIn, this, 5);
            }
        }

        return stateIn;
    }

    @Override
    public void appendHoverText(final ItemStack stack, @Nullable final BlockGetter worldIn, final List<Component> tooltip, final TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        Utils.addTooltip(tooltip, Utils.TOOLTIP_ADD_COLUMN);
    }

    @Override
    public int getLightBlock(final BlockState p_200011_1_In, final BlockGetter p_200011_2_In, final BlockPos p_200011_3_In) {
        return 1;
    }

    @Override
    public boolean useShapeForLightOcclusion(final BlockState p_220074_1_In) {
        return false;
    }

    @Override
    public boolean propagatesSkylightDown(final BlockState p_200123_1_In, final BlockGetter p_200123_2_In, final BlockPos p_200123_3_In) {
        return true;
    }

    /**
     * Method used to execute removeWaterAround(final Map<BlockPos, BlockState> testedPositionsIn, BlockState blockStateIn, final BlockPos blockPosIn, final World worldIn, final float prohibitedXIn, final float
     * prohibitedZIn)
     */
    public static boolean removeWaterAround(final BlockState blockStateIn, final BlockPos blockPosIn, final Level worldIn) {
        return PoolBlock.removeWaterAround(new LinkedHashMap<>(), blockStateIn, blockPosIn, worldIn, 0.0f, 0.0f);
    }

    /**
     * Seynax : binary method to remove water on all associated pool
     */
    private static boolean removeWaterAround(final Map<BlockPos, BlockState> testedPositionsIn, BlockState blockStateIn, final BlockPos blockPosIn, final Level worldIn, final float prohibitedXIn, final float prohibitedZIn) {
        boolean success = blockStateIn.getValue(BlockStatePropertiesAA.LEVEL) > 0;
        blockStateIn = blockStateIn.setValue(BlockStatePropertiesAA.LEVEL, 0);
        worldIn.setBlock(blockPosIn, blockStateIn, 10);
        if(prohibitedXIn != 1 && PoolBlock.removeWaterAroundOffset(testedPositionsIn, blockPosIn, worldIn, 1, 0)) {
            success = true;
        }
        if(prohibitedXIn != -1 && PoolBlock.removeWaterAroundOffset(testedPositionsIn, blockPosIn, worldIn, -1, 0)) {
            success = true;
        }
        if(prohibitedZIn != 1 && PoolBlock.removeWaterAroundOffset(testedPositionsIn, blockPosIn, worldIn, 0, 1)) {
            success = true;
        }
        if(prohibitedZIn != -1 && PoolBlock.removeWaterAroundOffset(testedPositionsIn, blockPosIn, worldIn, 0, -1)) {
            success = true;
        }
        return success;
    }

    /**
     * Used by removeWaterAround(final Map<BlockPos, BlockState> testedPositionsIn, BlockState blockStateIn, final BlockPos blockPosIn, final World worldIn, final float prohibitedXIn, final float prohibitedZIn)
     *
     * @param testedPositionsIn
     * @param blockPosIn
     * @param worldIn
     * @param x
     * @param z
     *
     * @return
     */
    private static boolean removeWaterAroundOffset(final Map<BlockPos, BlockState> testedPositionsIn, final BlockPos blockPosIn, final Level worldIn, final int x, final int z) {
        final BlockPos pos = blockPosIn.offset(x, 0, z);
        if(!testedPositionsIn.containsKey(pos)) {
            BlockState state = worldIn.getBlockState(pos);
            if(state.getBlock() instanceof PoolBlock) {
                testedPositionsIn.put(pos, state);
                return PoolBlock.removeWaterAround(testedPositionsIn, state, pos, worldIn, x, z);
            }
            if(state.getBlock() instanceof FaucetBlock) {
                state = state.setValue(BlockStatePropertiesAA.ACTIVATED, false);
                worldIn.setBlock(pos, state, 10);
            }
        }
        return false;
    }

    /**
     * Method used to execute hasOneActivatedFaucetOrJet(final Map<BlockPos, BlockState> testedPositionsIn, final BlockPos blockPosIn, final LevelAccessor worldIn, final float prohibitedXIn, final float prohibitedZIn)
     *
     * @param blockPosIn
     * @param worldIn
     */
    public static EnumActivatorState hasOnePoolActivatorAround(final BlockPos blockPosIn, final LevelAccessor worldIn) {
        return PoolBlock.hasOnePoolActivatorAround(new LinkedHashMap<>(), blockPosIn, worldIn, 0.0f, 0.0f);
    }

    /**
     * You can use hasOnePoolActivatorAround(final BlockPos blockPosIn, final LevelAccessor worldIn) Seynax : binary method to remove water on all associated pool
     */
    private static EnumActivatorState hasOnePoolActivatorAround(final Map<BlockPos, BlockState> testedPositionsIn, final BlockPos blockPosIn, final LevelAccessor worldIn, final float prohibitedXIn, final float prohibitedZIn) {
        if(testedPositionsIn.containsKey(blockPosIn)) {
            return EnumActivatorState.NO;
        }
        final BlockState state = worldIn.getBlockState(blockPosIn.above());
        testedPositionsIn.put(blockPosIn, state);

        if(state.getBlock() instanceof WaterTrickleBlock || state.getBlock() instanceof FaucetBlock && state.getValue(BlockStatePropertiesAA.ACTIVATED)) {
            return EnumActivatorState.ENABLED;
        }

        if(prohibitedXIn != 1 && EnumActivatorState.ENABLED.equals(PoolBlock.hasOnePoolActivatorAroundOffset(testedPositionsIn, worldIn, blockPosIn, 1, 0)) || prohibitedXIn != -1 && EnumActivatorState.ENABLED.equals(PoolBlock.hasOnePoolActivatorAroundOffset(testedPositionsIn, worldIn, blockPosIn, -1, 0)) || prohibitedZIn != 1 && EnumActivatorState.ENABLED.equals(PoolBlock.hasOnePoolActivatorAroundOffset(testedPositionsIn, worldIn, blockPosIn, 0, 1)) || prohibitedZIn != -1 && EnumActivatorState.ENABLED.equals(PoolBlock.hasOnePoolActivatorAroundOffset(testedPositionsIn, worldIn, blockPosIn, 0, -1))) {
            return EnumActivatorState.ENABLED;
        }

        return EnumActivatorState.NO;
    }

    public static EnumActivatorState hasOnePoolActivatorAroundOffset(final Map<BlockPos, BlockState> testedPositionsIn, final LevelAccessor worldIn, final BlockPos baseBlockPosIn, final int xOffsetIn, final int zOffsetIn) {
        final BlockPos offsetBlockPos = baseBlockPosIn.offset(xOffsetIn, 0, zOffsetIn);
        final BlockState offsetState = worldIn.getBlockState(offsetBlockPos);

        if(offsetState.getBlock() instanceof PoolBlock) {
            return PoolBlock.hasOnePoolActivatorAround(testedPositionsIn, offsetBlockPos, worldIn, -xOffsetIn, -zOffsetIn);
        }

        return EnumActivatorState.NO;
    }

    private static PoolLevelAndSides levelOfPoolAround(final BlockPos blockPosIn, final LevelAccessor worldIn) {
        return PoolBlock.levelOfPoolAround(new LinkedHashMap<>(), new PoolLevelAndSides(), blockPosIn, worldIn, 0.0f, 0.0f);
    }

    private static PoolLevelAndSides levelOfPoolAround(final Map<BlockPos, BlockState> testedPositionsIn, final PoolLevelAndSides poolLevelAndSidesIn, final BlockPos blockPosIn, final LevelAccessor worldIn, final float prohibitedXIn, final float prohibitedZIn) {
        final boolean center = prohibitedXIn == 0 && prohibitedZIn == 0;

        if(prohibitedXIn != 1) {
            final int level = PoolBlock.poolLevelAroundOffset(testedPositionsIn, poolLevelAndSidesIn, blockPosIn, worldIn, 1, 0);
            if(level > 0 && center) {
                poolLevelAndSidesIn.right = true;
            }
            if(level > poolLevelAndSidesIn.level) {
                poolLevelAndSidesIn.level = level;
            }
        }

        if(prohibitedXIn != -1) {
            final int level = PoolBlock.poolLevelAroundOffset(testedPositionsIn, poolLevelAndSidesIn, blockPosIn, worldIn, -1, 0);
            if(level > 0 && center) {
                poolLevelAndSidesIn.left = true;
            }
            if(level > poolLevelAndSidesIn.level) {
                poolLevelAndSidesIn.level = level;
            }
        }

        if(prohibitedZIn != 1) {
            final int level = PoolBlock.poolLevelAroundOffset(testedPositionsIn, poolLevelAndSidesIn, blockPosIn, worldIn, 0, 1);
            if(level > 0 && center) {
                poolLevelAndSidesIn.north = true;
            }
            if(level > poolLevelAndSidesIn.level) {
                poolLevelAndSidesIn.level = level;
            }
        }

        if(prohibitedZIn != -1) {
            final int level = PoolBlock.poolLevelAroundOffset(testedPositionsIn, poolLevelAndSidesIn, blockPosIn, worldIn, 0, -1);
            if(level > 0 && center) {
                poolLevelAndSidesIn.south = true;
            }

            if(level > poolLevelAndSidesIn.level) {
                poolLevelAndSidesIn.level = level;
            }
        }

        return poolLevelAndSidesIn;
    }

    /**
     * Used by hasOnePoolActivatorAround(final BlockPos blockPosIn, final LevelAccessor worldIn)
     *
     * @param testedPositionsIn
     * @param baseBlockPosIn
     * @param worldIn
     * @param xOffsetIn
     * @param zOffsetIn
     *
     * @return
     */
    private static int poolLevelAroundOffset(final Map<BlockPos, BlockState> testedPositionsIn, final PoolLevelAndSides poolLevelAndSidesIn, final BlockPos baseBlockPosIn, final LevelAccessor worldIn, final int xOffsetIn, final int zOffsetIn) {
        final BlockPos pos = baseBlockPosIn.offset(xOffsetIn, 0, zOffsetIn);
        if(testedPositionsIn.containsKey(pos)) {
            return -1;
        }
        final BlockState state = worldIn.getBlockState(pos);
        testedPositionsIn.put(pos, state);

        if(state.getBlock() instanceof PoolBlock) {
            PoolBlock.levelOfPoolAround(testedPositionsIn, poolLevelAndSidesIn, pos, worldIn, -xOffsetIn, -zOffsetIn);
            final int currentLevel = state.getValue(BlockStatePropertiesAA.LEVEL);

            return Math.max(currentLevel, poolLevelAndSidesIn.level);
        }

        return -1;
    }

    public static final class PoolLevelAndSides {
        public boolean left;
        public boolean right;
        public boolean north;
        public boolean south;
        public int level;

        public PoolLevelAndSides() {
            this.level = 0;
        }
    }

    public enum EnumActivatorState {
        NO, DISABLED, ENABLED,
    }
}