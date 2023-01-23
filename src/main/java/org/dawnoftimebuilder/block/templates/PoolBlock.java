package org.dawnoftimebuilder.block.templates;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.EmptyFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import static net.minecraft.state.properties.BlockStateProperties.*;
import static org.dawnoftimebuilder.util.DoTBBlockStateProperties.HAS_PILLAR;

/**
 * @author seyro
 */
public class PoolBlock extends WaterloggedBlock {

    private static final VoxelShape[] SHAPES = makeShapes();

    public PoolBlock(final Properties propertiesIn) {
        super(propertiesIn);
        this.registerDefaultState(this.defaultBlockState().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(HAS_PILLAR, false).setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(final BlockState state, final IBlockReader worldIn, final BlockPos pos, final ISelectionContext context) {
        int index = 0;
        if(state.getValue(NORTH)){
            index += 1;
        }
        if(state.getValue(EAST)){
            index += 2;
        }
        if(state.getValue(SOUTH)){
            index += 4;
        }
        if(state.getValue(WEST)){
            index += 8;
        }
        if(state.getValue(HAS_PILLAR)){
            index += 16;
        }
        return SHAPES[index];
    }

    private static VoxelShape[] makeShapes() {
        VoxelShape vs_floor = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
        VoxelShape vs_north = Block.box(0.0D, 2.0D, 0.0D, 16.0D, 16.0D, 2.0D);
        VoxelShape vs_east = Block.box(14.0D, 2.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vs_south = Block.box(0.0D, 2.0D, 14.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vs_west = Block.box(0.0D, 2.0D, 0.0D, 2.0D, 16.0D, 16.0D);
        VoxelShape vs_pillar = Block.box(4.0D, 2.0D, 4.0D, 12.0D, 16.0D, 12.0D);
        VoxelShape[] shapes = new VoxelShape[32];
        for (int i = 0; i < 32; i++){
            VoxelShape temp = vs_floor;
            if((i & 1) == 0){ // Check first bit : 0 -> North true
                temp = VoxelShapes.or(temp, vs_north);
            }
            if(((i >> 1) & 1) == 0){ // Check second bit : 0 -> East true
                temp = VoxelShapes.or(temp, vs_east);
            }
            if(((i >> 2) & 1) == 0){ // Check third bit : 0 -> South true
                temp = VoxelShapes.or(temp, vs_south);
            }
            if(((i >> 3) & 1) == 0){ // Check fourth bit : 0 -> West true
                temp = VoxelShapes.or(temp, vs_west);
            }
            if(((i >> 4) & 1) == 1){ // Check fifth bit : 1 -> Pillar true
                temp = VoxelShapes.or(temp, vs_pillar);
            }
            shapes[i] = temp;
        }
        return shapes;
    }

    @Override
    protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH).add(EAST).add(BlockStateProperties.SOUTH).add(BlockStateProperties.WEST).add(HAS_PILLAR);
    }

    private final static Map<BlockPos, BlockState> REMOVE_WATER_MAP = new HashMap<>();

    /**
     * Seynax : binary method to remove water on all associated pool
     */
    public static boolean removeWater(final Map<BlockPos, BlockState> testedPositionsIn, BlockState blockStateIn, final BlockPos blockPosIn, final World worldIn, final float prohibitedXIn, final float prohibitedZIn) {

        boolean success = blockStateIn.getValue(BlockStateProperties.WATERLOGGED);
		blockStateIn = blockStateIn.setValue(BlockStateProperties.WATERLOGGED, false);
        worldIn.setBlock(blockPosIn, blockStateIn, 10);

        if (prohibitedXIn != 1) {
            final BlockPos pos = blockPosIn.offset(1, 0, 0);
            if (!testedPositionsIn.containsKey(pos)) {
                final BlockState state = worldIn.getBlockState(pos);
                if (state.getBlock() instanceof PoolBlock) {
                    testedPositionsIn.put(pos, state);
                    if (PoolBlock.removeWater(testedPositionsIn, state, pos, worldIn, 1, 0)) {
                        success = true;
                    }
                }
            }
        }
        if (prohibitedXIn != -1) {
            final BlockPos pos = blockPosIn.offset(-1, 0, 0);
            if (!testedPositionsIn.containsKey(pos)) {
                final BlockState state = worldIn.getBlockState(pos);
                if (state.getBlock() instanceof PoolBlock) {
                    testedPositionsIn.put(pos, state);
                    if (PoolBlock.removeWater(testedPositionsIn, state, pos, worldIn, -1, 0)) {
                        success = true;
                    }
                }
            }
        }

        if (prohibitedZIn != 1) {
            final BlockPos pos = blockPosIn.offset(0, 0, 1);
            if (!testedPositionsIn.containsKey(pos)) {
                final BlockState state = worldIn.getBlockState(pos);
                if (state.getBlock() instanceof PoolBlock) {
                    testedPositionsIn.put(pos, state);
                    if (PoolBlock.removeWater(testedPositionsIn, state, pos, worldIn, 0, 1)) {
                        success = true;
                    }
                }
            }
        }
        if (prohibitedZIn != -1) {
            final BlockPos pos = blockPosIn.offset(0, 0, -1);
            if (!testedPositionsIn.containsKey(pos)) {
                final BlockState state = worldIn.getBlockState(pos);
                if (state.getBlock() instanceof PoolBlock) {
                    testedPositionsIn.put(pos, state);
                    if (PoolBlock.removeWater(testedPositionsIn, state, pos, worldIn, 0, -1)) {
                        success = true;
                    }
                }
            }
        }

        return success;
    }

    @Override
    public ActionResultType use(BlockState blockStateIn, final World worldIn, final BlockPos blockPosIn, final PlayerEntity playerEntityIn, final Hand handIn, final BlockRayTraceResult blockRayTraceResultIn) {
        if (playerEntityIn.isCrouching()) {
            /*
             * Seynax : allows the player to remove the contents of all stuck basins, with a single click with an empty bucket while sneaking
             */
            final ItemStack itemStack = playerEntityIn.getMainHandItem();
            if (itemStack == null || !(itemStack.getItem() instanceof BucketItem)) {
                blockStateIn = blockStateIn.setValue(HAS_PILLAR, !blockStateIn.getValue(HAS_PILLAR));

                worldIn.setBlock(blockPosIn, blockStateIn, 10);

                return ActionResultType.SUCCESS;
            }
            if (((BucketItem) itemStack.getItem()).getFluid() instanceof EmptyFluid) {
                PoolBlock.REMOVE_WATER_MAP.clear();
                if (PoolBlock.removeWater(PoolBlock.REMOVE_WATER_MAP, blockStateIn, blockPosIn, worldIn, 0, 0)) {
                    return ActionResultType.SUCCESS;
                }
            }
        }

        return ActionResultType.PASS;
    }

    /*
     * Seynax : allows the player to prevent the appearance of a pole in the basin below, under sneak conditions
     */
    @Override
    public void setPlacedBy(final World worldIn, final BlockPos blockPosIn, final BlockState blockStateIn, final LivingEntity entityIn, final ItemStack itemStackIn) {
        if (entityIn instanceof PlayerEntity && !entityIn.isShiftKeyDown()) {
            super.setPlacedBy(worldIn, blockPosIn, blockStateIn, entityIn, itemStackIn);

            final BlockPos blockPos = blockPosIn.below();
            BlockState blockState = worldIn.getBlockState(blockPos);
            if (blockState.getBlock() instanceof PoolBlock) {
                blockState = blockState.setValue(HAS_PILLAR, true);
                worldIn.setBlock(blockPos, blockState, 10);
            }
        }

    }

    @Override
    public BlockState updateShape(BlockState stateIn, final Direction directionIn, final BlockState facingStateIn, final IWorld worldIn, final BlockPos currentPosIn, final BlockPos facingPosIn) {
		final boolean hasPoolInSide = facingStateIn.getBlock() instanceof PoolBlock;

        switch (directionIn) {
            case NORTH:
                stateIn = stateIn.setValue(NORTH, hasPoolInSide);

                if (hasPoolInSide && facingStateIn.getValue(BlockStateProperties.WATERLOGGED)) {
                    stateIn = stateIn.setValue(BlockStateProperties.WATERLOGGED, true);
                }

                break;
            case EAST:
                stateIn = stateIn.setValue(EAST, hasPoolInSide);

                if (hasPoolInSide && facingStateIn.getValue(BlockStateProperties.WATERLOGGED)) {
                    stateIn = stateIn.setValue(BlockStateProperties.WATERLOGGED, true);
                }

                break;
            case SOUTH:
                stateIn = stateIn.setValue(BlockStateProperties.SOUTH, hasPoolInSide);

                if (hasPoolInSide && facingStateIn.getValue(BlockStateProperties.WATERLOGGED)) {
                    stateIn = stateIn.setValue(BlockStateProperties.WATERLOGGED, true);
                }

                break;
            case WEST:
                stateIn = stateIn.setValue(BlockStateProperties.WEST, hasPoolInSide);

                if (hasPoolInSide && facingStateIn.getValue(BlockStateProperties.WATERLOGGED)) {
                    stateIn = stateIn.setValue(BlockStateProperties.WATERLOGGED, true);
                }
                break;
            default:
                break;
        }

        return super.updateShape(stateIn, directionIn, facingStateIn, worldIn, currentPosIn, facingPosIn);
    }
}