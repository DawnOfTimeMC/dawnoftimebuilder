package org.dawnoftimebuilder.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.block.templates.BushBlockDoT;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;

public class MapleSaplingBlock extends BushBlockDoT implements BonemealableBlock {
    public final static boolean isValidForPlacement(final LevelAccessor worldIn, final BlockPos bottomCenterIn, final boolean isSaplingCallIn) {
        final BlockPos floorCenter = bottomCenterIn.below();
        BlockState state = worldIn.getBlockState(floorCenter);

        if(!state.is(BlockTags.DIRT)) {
            return false;
        }

        state = worldIn.getBlockState(bottomCenterIn);

        if(!state.canBeReplaced() || !isSaplingCallIn) {
            return true;
        }

        for(int x = -1; x <= 1; x++) {
            for(int y = 0; y <= 1; y++) {
                for(int z = -1; z <= 1; z++) {
                    state = worldIn.getBlockState(bottomCenterIn.offset(x, y + 1, z));

                    if(state != null && !(state.getBlock() instanceof AirBlock)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static void placeFinalTreeIfPossible(final LevelAccessor worldIn, final BlockPos centerPosIn) {
        if(MapleSaplingBlock.isValidForPlacement(worldIn, centerPosIn, true)) {
            final Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(worldIn.getRandom());
            worldIn.setBlock(centerPosIn, DoTBBlocksRegistry.MAPLE_RED_TRUNK.get().defaultBlockState().setValue(MapleTrunkBlock.FACING, direction), 10);

            for(int x = -1; x <= 1; x++) {
                for(int y = 0; y <= 1; y++) {
                    for(int z = -1; z <= 1; z++) {
                        final BlockPos newBlockPosition = new BlockPos(centerPosIn.getX() + x, centerPosIn.getY() + y + 1, centerPosIn.getZ() + z);

                        worldIn.setBlock(newBlockPosition, DoTBBlocksRegistry.MAPLE_RED_LEAVES.get().defaultBlockState().setValue(MapleTrunkBlock.FACING, direction).setValue(MapleLeavesBlock.MULTIBLOCK_X, x + 1).setValue(MapleLeavesBlock.MULTIBLOCK_Y, y).setValue(MapleLeavesBlock.MULTIBLOCK_Z, z + 1), 10);
                    }
                }
            }
        }
    }

    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);

    public MapleSaplingBlock(final Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(MapleSaplingBlock.STAGE, 0));
    }

    @Override
    public InteractionResult use(final BlockState p_225533_1_In, final Level p_225533_2_In, final BlockPos p_225533_3_In, final Player p_225533_4_In, final InteractionHand p_225533_5_In, final BlockHitResult p_225533_6_In) {
        final Item mainItem = p_225533_4_In.getMainHandItem().getItem();
        if(mainItem instanceof FlintAndSteelItem) {
            if(!p_225533_2_In.isClientSide) {
                p_225533_2_In.setBlock(p_225533_3_In, DoTBBlocksRegistry.PAUSED_MAPLE_RED_SAPLING.get().defaultBlockState(), 35);
                p_225533_2_In.levelEvent(p_225533_4_In, 2001, p_225533_3_In, Block.getId(p_225533_1_In));
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    @Override
    public VoxelShape getShape(final BlockState p_220053_1_In, final BlockGetter p_220053_2_In, final BlockPos p_220053_3_In, final CollisionContext p_220053_4_In) {
        return MapleSaplingBlock.SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(MapleSaplingBlock.STAGE);
    }

    /**
     * Growable code
     */
    @Override
    public boolean isRandomlyTicking(final BlockState blockstateIn) {
        return true;
    }

    @Override
    public void tick(final BlockState p_225542_1_, final ServerLevel p_225542_2_, final BlockPos p_225542_3_, final RandomSource p_225542_4_) {
        if(p_225542_2_.getMaxLocalRawBrightness(p_225542_3_.above()) >= 9 && p_225542_4_.nextInt(7) == 0) {
            if(!p_225542_2_.isAreaLoaded(p_225542_3_, 1)) {
                return; // Forge: prevent loading unloaded chunks when checking neighbor's light
            }
            this.advanceTree(p_225542_2_, p_225542_3_, p_225542_1_, p_225542_4_);
        }
    }

    public void advanceTree(final ServerLevel p_226942_1_, final BlockPos p_226942_2_, final BlockState p_226942_3_, final RandomSource p_226942_4_) {
        if(p_226942_3_.getValue(MapleSaplingBlock.STAGE) == 0) {
            p_226942_1_.setBlock(p_226942_2_, p_226942_3_.cycle(MapleSaplingBlock.STAGE), 4);
        } else if(MapleSaplingBlock.isValidForPlacement(p_226942_1_, p_226942_2_, true)) {
            MapleSaplingBlock.placeFinalTreeIfPossible(p_226942_1_, p_226942_2_);
        }
    }

    @Override
    public boolean isValidBonemealTarget(final LevelReader p_176473_1_, final BlockPos p_176473_2_, final BlockState p_176473_3_, final boolean p_176473_4_) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(final Level p_180670_1_, final RandomSource p_180670_2_, final BlockPos p_180670_3_, final BlockState p_180670_4_) {
        return p_180670_1_.random.nextFloat() < 0.45D;
    }

    @Override
    public void performBonemeal(final ServerLevel p_225535_1_, final RandomSource p_225535_2_, final BlockPos p_225535_3_, final BlockState p_225535_4_) {
        this.advanceTree(p_225535_1_, p_225535_3_, p_225535_4_, p_225535_2_);
    }

    /**
     * Lights methods
     */
    @Override
    public boolean useShapeForLightOcclusion(final BlockState p_220074_1_In) {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getShadeBrightness(final BlockState p_220080_1_, final BlockGetter p_220080_2_, final BlockPos p_220080_3_) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(final BlockState p_200123_1_, final BlockGetter p_200123_2_, final BlockPos p_200123_3_) {
        return true;
    }
}
