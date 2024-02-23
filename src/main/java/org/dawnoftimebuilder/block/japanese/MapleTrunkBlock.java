package org.dawnoftimebuilder.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.block.ICustomBlockItem;
import org.dawnoftimebuilder.block.templates.BlockDoTB;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;

public class MapleTrunkBlock extends BlockDoTB implements ICustomBlockItem {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public MapleTrunkBlock(final Properties properties) {
        super(properties);

        this.registerDefaultState(this.defaultBlockState().setValue(MapleTrunkBlock.FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(MapleTrunkBlock.FACING);
    }

    @Override
    public void playerWillDestroy(final Level worldIn, final BlockPos blockPosIn, final BlockState blockStateIn, final Player playerEntityIn) {
        if(!worldIn.isClientSide) {
            if(playerEntityIn.isCreative()) {
                final BlockPos trunkBlockPos = new BlockPos(blockPosIn.getX(), blockPosIn.getY(), blockPosIn.getZ());
                final BlockState state = worldIn.getBlockState(trunkBlockPos);
                worldIn.setBlock(trunkBlockPos, Blocks.AIR.defaultBlockState(), 35);
                worldIn.levelEvent(playerEntityIn, 2001, blockPosIn, Block.getId(state));
            }

            for(int x = -1; x <= 1; x++) {
                for(int y = 1; y <= 2; y++) {
                    for(int z = -1; z <= 1; z++) {
                        final BlockPos baseBlock = new BlockPos(blockPosIn.getX() + x, blockPosIn.getY() + y, blockPosIn.getZ() + z);
                        final BlockState state = worldIn.getBlockState(baseBlock);
                        worldIn.setBlock(baseBlock, Blocks.AIR.defaultBlockState(), 35);
                        worldIn.levelEvent(playerEntityIn, 2001, blockPosIn, Block.getId(state));
                    }
                }
            }
        }

        super.playerWillDestroy(worldIn, blockPosIn, blockStateIn, playerEntityIn);
    }

    @Override
    public VoxelShape getShape(final BlockState state, final BlockGetter worldIn, final BlockPos pos, final CollisionContext context) {
        return Shapes.block();
    }

    @Override
    public VoxelShape getBlockSupportShape(final BlockState p_230335_1_, final BlockGetter p_230335_2_, final BlockPos p_230335_3_) {
        return Shapes.empty();
    }

    @Override
    public BlockState updateShape(final BlockState stateIn, final Direction facing, final BlockState facingState, final LevelAccessor worldIn, final BlockPos currentPos, final BlockPos facingPos) {
        if(Direction.DOWN.equals(facing)) {
            final BlockState state = worldIn.getBlockState(currentPos.below());
            if(!BlockTags.DIRT.equals(state.getBlock())) {
                return Blocks.AIR.defaultBlockState();
            }
        } else if(Direction.UP.equals(facing)) {
            final BlockState state = worldIn.getBlockState(currentPos.above());
            if(!(state.getBlock() instanceof MapleLeavesBlock)) {
                return Blocks.AIR.defaultBlockState();
            }

            final Direction currentFacing = stateIn.getValue(MapleTrunkBlock.FACING);
            if(currentFacing == null || !currentFacing.equals(state.getValue(MapleTrunkBlock.FACING))) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return stateIn;
    }

    @Override
    public PushReaction getPistonPushReaction(final BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public Item getCustomBlockItem() {
        return null;
    }

    @Override
    public ItemStack getCloneItemStack(final BlockState stateIn, final HitResult targetIn, final BlockGetter worldIn, final BlockPos posIn, final Player playerIn) {
        return new ItemStack(DoTBBlocksRegistry.MAPLE_RED_SAPLING.get().asItem());
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