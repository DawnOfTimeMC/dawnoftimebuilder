package org.dawnoftime.dawnoftime.block.japanese;

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


import org.dawnoftime.dawnoftime.block.templates.BushBlockDoT;
import org.dawnoftime.dawnoftime.registry.DoTBBlocksRegistry;

import static org.dawnoftime.dawnoftime.util.VoxelShapes.SAPLING_SHAPES;

public class MapleSaplingBlock extends BushBlockDoT implements BonemealableBlock {

    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;

    public MapleSaplingBlock(final Properties properties) {
        super(properties, SAPLING_SHAPES);
        this.registerDefaultState(this.stateDefinition.any().setValue(MapleSaplingBlock.STAGE, 0));
    }

    @Override
    public InteractionResult use(final BlockState p_225533_1_In, final Level p_225533_2_In, final BlockPos p_225533_3_In, final Player p_225533_4_In, final InteractionHand p_225533_5_In, final BlockHitResult p_225533_6_In) {
        final Item mainItem = p_225533_4_In.getMainHandItem().getItem();
        if(mainItem instanceof FlintAndSteelItem) {
            if(!p_225533_2_In.isClientSide) {
                p_225533_2_In.setBlock(p_225533_3_In, DoTBBlocksRegistry.INSTANCE.PAUSED_MAPLE_RED_SAPLING.get().defaultBlockState(), 35);
                p_225533_2_In.levelEvent(p_225533_4_In, 2001, p_225533_3_In, Block.getId(p_225533_1_In));
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
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
    public void tick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource source) {
        if(level.getMaxLocalRawBrightness(pos.above()) >= 9 && source.nextInt(7) == 0) {
            if(!level.isLoaded(pos)) {
                return; // Forge: prevent loading unloaded chunks when checking neighbor's light
            }
            this.advanceTree(level, pos, state);
        }
    }

    public void advanceTree(final ServerLevel level, final BlockPos pos, final BlockState state) {
        if(state.getValue(MapleSaplingBlock.STAGE) == 0) {
            level.setBlock(pos, state.cycle(MapleSaplingBlock.STAGE), 4);
        } else {
            MapleSaplingBlock.placeFinalTreeIfPossible(level, pos);
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
    public void performBonemeal(final ServerLevel level, final RandomSource source, final BlockPos pos, final BlockState state) {
        this.advanceTree(level, pos, state);
    }

    /**
     * Lights methods
     */
    @Override
    public boolean useShapeForLightOcclusion(final BlockState p_220074_1_In) {
        return false;
    }

    @Override
    
    public float getShadeBrightness(final BlockState p_220080_1_, final BlockGetter p_220080_2_, final BlockPos p_220080_3_) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(final BlockState p_200123_1_, final BlockGetter p_200123_2_, final BlockPos p_200123_3_) {
        return true;
    }

    @Override
    public boolean generateOnPos(WorldGenLevel world, BlockPos pos, BlockState state, RandomSource random) {
        return MapleSaplingBlock.placeFinalTreeIfPossible(world, pos);
    }
    public static boolean isValidForPlacement(final LevelAccessor worldIn, final BlockPos bottomCenterIn) {
        final BlockPos floorCenter = bottomCenterIn.below();
        BlockState state = worldIn.getBlockState(floorCenter);
        if(!state.is(BlockTags.DIRT)) {
            return false;
        }
        for(int x = -1; x <= 1; x++) {
            for(int y = 0; y <= 1; y++) {
                for(int z = -1; z <= 1; z++) {
                    state = worldIn.getBlockState(bottomCenterIn.offset(x, y + 1, z));
                    if(!(state.getBlock() instanceof AirBlock)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean placeFinalTreeIfPossible(final LevelAccessor worldIn, final BlockPos centerPosIn) {
        if(MapleSaplingBlock.isValidForPlacement(worldIn, centerPosIn)) {
            final Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(worldIn.getRandom());
            worldIn.setBlock(centerPosIn, DoTBBlocksRegistry.INSTANCE.MAPLE_RED_TRUNK.get().defaultBlockState().setValue(MapleTrunkBlock.FACING, direction), 10);

            for(int x = -1; x <= 1; x++) {
                for(int y = 0; y <= 1; y++) {
                    for(int z = -1; z <= 1; z++) {
                        final BlockPos newBlockPosition = new BlockPos(centerPosIn.getX() + x, centerPosIn.getY() + y + 1, centerPosIn.getZ() + z);

                        worldIn.setBlock(newBlockPosition, DoTBBlocksRegistry.INSTANCE.MAPLE_RED_LEAVES.get().defaultBlockState().setValue(MapleTrunkBlock.FACING, direction).setValue(MapleLeavesBlock.MULTIBLOCK_X, x + 1).setValue(MapleLeavesBlock.MULTIBLOCK_Y, y).setValue(MapleLeavesBlock.MULTIBLOCK_Z, z + 1), 10);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
