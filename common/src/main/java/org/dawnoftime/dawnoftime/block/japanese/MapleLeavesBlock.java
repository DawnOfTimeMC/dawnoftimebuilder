package org.dawnoftime.dawnoftime.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.block.templates.BlockDoT;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.jetbrains.annotations.NotNull;

public class MapleLeavesBlock extends BlockDoT {
    public static final IntegerProperty MULTIBLOCK_X = BlockStatePropertiesAA.MULTIBLOCK_3X;
    public static final IntegerProperty MULTIBLOCK_Y = BlockStatePropertiesAA.MULTIBLOCK_2Y;
    public static final IntegerProperty MULTIBLOCK_Z = BlockStatePropertiesAA.MULTIBLOCK_3Z;

    public MapleLeavesBlock(final Properties properties) {
        super(properties.pushReaction(PushReaction.DESTROY));
        this.registerDefaultState(this.defaultBlockState().setValue(MapleTrunkBlock.FACING, Direction.NORTH).setValue(MapleLeavesBlock.MULTIBLOCK_X, 0).setValue(MapleLeavesBlock.MULTIBLOCK_Y, 0).setValue(MapleLeavesBlock.MULTIBLOCK_Z, 0));
    }

    @Override
    public void playerWillDestroy(final Level worldIn, final BlockPos blockPosIn, final BlockState blockStateIn, final Player playerEntityIn) {
        if(!worldIn.isClientSide) {
            final float currentX = -blockStateIn.getValue(MapleLeavesBlock.MULTIBLOCK_X);
            final float currentY = -blockStateIn.getValue(MapleLeavesBlock.MULTIBLOCK_Y);
            final float currentZ = -blockStateIn.getValue(MapleLeavesBlock.MULTIBLOCK_Z);
            for(int x = 0; x <= 2; x++) {
                for(int y = 0; y <= 1; y++) {
                    for(int z = 0; z <= 2; z++) {
                        final BlockPos baseBlockPos = new BlockPos((int) (blockPosIn.getX() + x + currentX), (int) (blockPosIn.getY() + y + currentY), (int) (blockPosIn.getZ() + z + currentZ));
                        final BlockState state = worldIn.getBlockState(baseBlockPos);
                        worldIn.setBlock(baseBlockPos, Blocks.AIR.defaultBlockState(), 35);
                        worldIn.levelEvent(playerEntityIn, 2001, blockPosIn, Block.getId(state));
                    }
                }
            }

            if(!playerEntityIn.isCreative()) {
                final BlockPos trunkBlockPos = new BlockPos((int) (blockPosIn.getX() + currentX + 1), (int) (blockPosIn.getY() + currentY - 1), (int) (blockPosIn.getZ() + currentZ + 1));
                worldIn.destroyBlock(trunkBlockPos, true);
            } else {
                final BlockPos trunkBlockPos = new BlockPos((int) (blockPosIn.getX() + currentX + 1), (int) (blockPosIn.getY() + currentY - 1), (int) (blockPosIn.getZ() + currentZ + 1));
                final BlockState state = worldIn.getBlockState(trunkBlockPos);
                worldIn.setBlock(trunkBlockPos, Blocks.AIR.defaultBlockState(), 35);
                worldIn.levelEvent(playerEntityIn, 2001, blockPosIn, Block.getId(state));
            }
        }

        super.playerWillDestroy(worldIn, blockPosIn, blockStateIn, playerEntityIn);
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(MapleTrunkBlock.FACING, MapleLeavesBlock.MULTIBLOCK_X, MapleLeavesBlock.MULTIBLOCK_Y, MapleLeavesBlock.MULTIBLOCK_Z);
    }

    @Override
    public BlockState updateShape(final BlockState stateIn, final Direction facing, final BlockState facingState, final LevelAccessor worldIn, final BlockPos currentPos, final BlockPos facingPos) {
        final Direction currentFacing = stateIn.getValue(MapleTrunkBlock.FACING);
        final float multiblockX = stateIn.getValue(MapleLeavesBlock.MULTIBLOCK_X);
        final float multiblockY = stateIn.getValue(MapleLeavesBlock.MULTIBLOCK_Y);
        final float multiblockZ = stateIn.getValue(MapleLeavesBlock.MULTIBLOCK_Z);

        if(Direction.DOWN.equals(facing)) {
            if(multiblockX == 1 && multiblockY == 0 && multiblockZ == 1) {
                final BlockState state = worldIn.getBlockState(currentPos.offset(0, -1, 0));

                if(!(state.getBlock() instanceof MapleTrunkBlock) || currentFacing == null || !currentFacing.equals(state.getValue(MapleTrunkBlock.FACING))) {
                    return Blocks.AIR.defaultBlockState();
                }
            } else if(multiblockY == 1) {
                final BlockState state = worldIn.getBlockState(currentPos.offset(0, -1, 0));

                if(!(state.getBlock() instanceof MapleLeavesBlock) || currentFacing == null || !currentFacing.equals(state.getValue(MapleTrunkBlock.FACING))) {
                    return Blocks.AIR.defaultBlockState();
                }
            }
        } else if(Direction.UP.equals(facing) && multiblockY == 0) {
            final BlockState state = worldIn.getBlockState(currentPos.offset(0, 1, 0));

            if(!(state.getBlock() instanceof MapleLeavesBlock) || currentFacing == null || !currentFacing.equals(state.getValue(MapleTrunkBlock.FACING))) {
                return Blocks.AIR.defaultBlockState();
            }
        } else if(Direction.WEST.equals(facing) && multiblockX > 0) {
            final BlockState state = worldIn.getBlockState(currentPos.offset(-1, 0, 0));

            if(!(state.getBlock() instanceof MapleLeavesBlock) || currentFacing == null || !currentFacing.equals(state.getValue(MapleTrunkBlock.FACING))) {
                return Blocks.AIR.defaultBlockState();
            }
        } else if(Direction.EAST.equals(facing)) {
            if(multiblockX < 2) {
                final BlockState state = worldIn.getBlockState(currentPos.offset(1, 0, 0));

                if(!(state.getBlock() instanceof MapleLeavesBlock) || currentFacing == null || !currentFacing.equals(state.getValue(MapleTrunkBlock.FACING))) {
                    return Blocks.AIR.defaultBlockState();
                }
            }
        } else if(Direction.NORTH.equals(facing)) {
            if(multiblockZ > 0) {
                final BlockState state = worldIn.getBlockState(currentPos.offset(0, 0, -1));

                if(!(state.getBlock() instanceof MapleLeavesBlock) || currentFacing == null || !currentFacing.equals(state.getValue(MapleTrunkBlock.FACING))) {
                    return Blocks.AIR.defaultBlockState();
                }
            }
        } else if(Direction.SOUTH.equals(facing) && multiblockZ < 2) {
            final BlockState state = worldIn.getBlockState(currentPos.offset(0, 0, 1));

            if(!(state.getBlock() instanceof MapleLeavesBlock) || currentFacing == null || !currentFacing.equals(state.getValue(MapleTrunkBlock.FACING))) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return stateIn;
    }

//    @Override
//    public ItemStack getCloneItemStack(final BlockState stateIn, final HitResult targetIn, final BlockGetter worldIn, final BlockPos posIn, final Player playerIn) {
//        return new ItemStack(DoTBBlocksRegistry.MAPLE_RED_SAPLING.get().asItem());
//    }

    @Override
    public VoxelShape getBlockSupportShape(final BlockState p_230335_1_, final BlockGetter p_230335_2_, final BlockPos p_230335_3_) {
        return Shapes.empty();
    }

    /**
     * Light corrections methods
     */
    private static final VoxelShape VS = Block.box(0.1D, 0.1D, 0.1D, 15.9D, 15.9D, 15.9D);

    @Override
    public @NotNull VoxelShape getShape(final @NotNull BlockState state, final @NotNull BlockGetter worldIn, final @NotNull BlockPos pos, final @NotNull CollisionContext context) {
        return MapleLeavesBlock.VS;
    }

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
}
