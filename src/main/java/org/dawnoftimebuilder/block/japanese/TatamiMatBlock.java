package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;
import org.dawnoftimebuilder.utils.DoTBBlockUtils;

import javax.annotation.Nullable;

import static net.minecraft.block.Blocks.SPRUCE_PLANKS;
import static org.dawnoftimebuilder.registries.DoTBBlocksRegistry.TATAMI_FLOOR;
import static org.dawnoftimebuilder.utils.DoTBBlockUtils.DoTBTags.COVERED_BLOCKS;

public class TatamiMatBlock extends WaterloggedBlock {

    private static final VoxelShape VS = makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    private static final VoxelShape[] SHAPES = DoTBBlockUtils.GenerateHorizontalShapes(makeShapes());
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty ROLLED = DoTBBlockStateProperties.ROLLED;
    public static final IntegerProperty STACK = DoTBBlockStateProperties.STACK;

    public TatamiMatBlock() {
        super("tatami_mat", Material.CARPET, 0.6F, 0.6F);
        this.setBurnable();
        this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, false).with(ROLLED, false).with(HALF, Half.TOP).with(STACK, 1));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(FACING, HALF, ROLLED, STACK);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if(!state.get(ROLLED)) return VS;
        return SHAPES[state.get(STACK) - 1 + state.get(FACING).getHorizontalIndex() * 3];
    }

    /**
     * @return Stores VoxelShape for "South" with index : <p/>
     * 0 : S Rolled <p/>
     * 1 : S Rolled stack 2 <p/>
     * 2 : S Rolled stack 3 <p/>
     */
    private static VoxelShape[] makeShapes() {
        return new VoxelShape[]{
                makeCuboidShape(0.0D, 0.0D, 8.5D, 16.0D, 7.0D, 15.5D),
                makeCuboidShape(0.0D, 0.0D, 0.5D, 16.0D, 7.0D, 15.5D),
                makeCuboidShape(0.0D, 0.0D, 0.5D, 16.0D, 14.0D, 15.5D)
        };
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState oldState = world.getBlockState(pos);
        if(oldState.getBlock() == this){
            int stack = oldState.get(STACK);
            if(oldState.get(ROLLED) && stack < 3)
                return oldState.with(STACK, stack + 1);
        }
        Direction direction = context.getPlacementHorizontalFacing();
        if(world.getBlockState(pos.offset(direction)).isReplaceable(context))
            return super.getStateForPlacement(context)
                    .with(ROLLED, COVERED_BLOCKS.contains(world.getBlockState(pos.down()).getBlock()) || COVERED_BLOCKS.contains(world.getBlockState(pos.down().offset(direction)).getBlock()))
                    .with(FACING, direction);
        return null;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if(state.get(HALF) == Half.BOTTOM){
            BlockState topState = worldIn.getBlockState(pos.offset(state.get(FACING).getOpposite()));
            if(topState.getBlock() == this){
                if(topState.get(HALF) == Half.TOP && topState.get(FACING) == state.get(FACING))
                    return !worldIn.isAirBlock(pos.down());
            }
        }else return !worldIn.isAirBlock(pos.down());
        return false;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        BlockState newState = this.tryMergingWithSprucePlanks(state, worldIn, pos);
        if(newState.getBlock() == Blocks.AIR)
            worldIn.setBlockState(pos, newState);
        else if(!newState.get(ROLLED))
            worldIn.setBlockState(pos.offset(newState.get(FACING)), newState.with(HALF, Half.BOTTOM), 3);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        stateIn = super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        if(facing.getAxis().isVertical()){
            return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : this.tryMergingWithSprucePlanks(stateIn, worldIn.getWorld(), currentPos);
        }else{
            Direction direction = stateIn.get(FACING);
            if(stateIn.get(HALF) == Half.TOP){
                if(!stateIn.get(ROLLED) && direction == facing){
                    if(facingState.getBlock() == this){
                        if(facingState.get(FACING) != facing || facingState.get(HALF) != Half.BOTTOM)
                            return Blocks.AIR.getDefaultState();
                    }else
                        return Blocks.AIR.getDefaultState();
                }
            }else if(direction == facing.getOpposite()){
                if(facingState.getBlock() == this){
                    if(facingState.get(FACING) != direction || facingState.get(HALF) != Half.TOP)
                        return Blocks.AIR.getDefaultState();
                }else
                    return Blocks.AIR.getDefaultState();
            }
        }
        return stateIn;
    }

    private BlockState tryMergingWithSprucePlanks(BlockState state, World worldIn, BlockPos pos){
        if(state.get(ROLLED)) return state;
        Direction facing = state.get(FACING);
        Block blockDown = worldIn.getBlockState(pos.down()).getBlock();
        Block blockDownAdjacent = worldIn.getBlockState(pos.offset(facing).down()).getBlock();
        if(blockDown == SPRUCE_PLANKS && blockDownAdjacent == SPRUCE_PLANKS){
            worldIn.setBlockState(pos.down(), TATAMI_FLOOR.getDefaultState().with(TatamiFloorBlock.FACING, facing).with(TatamiFloorBlock.HALF, state.get(HALF)));
            worldIn.setBlockState(pos.offset(facing).down(), TATAMI_FLOOR.getDefaultState().with(TatamiFloorBlock.FACING, facing).with(TatamiFloorBlock.HALF, state.get(HALF) == Half.TOP ? Half.BOTTOM : Half.TOP));
            return Blocks.AIR.getDefaultState();
        }
        return state;
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isRemote){
            if(player.isSneaking()){
                int stack = state.get(STACK);
                boolean isRolled = state.get(ROLLED);
                if(isRolled && stack == 1) {
                    if (COVERED_BLOCKS.contains(worldIn.getBlockState(pos.down()).getBlock()))
                        return false;
                }
                if(state.get(STACK) > 1){
                    state = state.with(STACK, stack - 1);
                    spawnAsEntity(worldIn.getWorld(), pos, new ItemStack(this.asItem(), 1));
                }else{
                    state = state.with(ROLLED, !isRolled);
                    Direction facing = state.get(FACING);
                    if(isRolled){
                        if(!worldIn.isAirBlock(pos.offset(facing)) //Check if the position for the Bottom half is free
                                || worldIn.isAirBlock(pos.offset(facing).down()) //Check if this position can't support tatami
                                || COVERED_BLOCKS.contains(worldIn.getBlockState(pos.down().offset(facing)).getBlock())) //Check if this position is not supported by a covered block
                            return false;
                        worldIn.setBlockState(pos.offset(facing), state.with(HALF, Half.BOTTOM), 8);
                    }else{
                        boolean isTop = state.get(HALF) == Half.TOP;
                        if(isTop){
                            worldIn.setBlockState(pos.offset(facing), Blocks.AIR.getDefaultState(), 8);
                        }else{
                            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 8);
                            pos = pos.offset(facing.getOpposite());
                            state = state.with(HALF, Half.TOP);
                        }
                    }
                }
                state = this.updatePostPlacement(state, Direction.DOWN, worldIn.getBlockState(pos.down()), worldIn, pos, pos.down());
                worldIn.setBlockState(pos, state, 8);
                return true;
            }
        }
        return false;
    }

    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        ItemStack itemstack = useContext.getItem();
        if(itemstack.getItem() == this.asItem()) {
            if(!state.get(ROLLED) || state.get(STACK) == 3)
                return false;
            return useContext.replacingClickedOnBlock();
        }
        return false;
    }

    @Override
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, Blocks.AIR.getDefaultState(), te, stack);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockPos blockpos = (state.get(HALF) == Half.TOP) ? pos.offset(state.get(FACING)) : pos.offset(state.get(FACING).getOpposite());
        BlockState otherState = worldIn.getBlockState(blockpos);
        if(otherState.getBlock() == this && otherState.get(HALF) != state.get(HALF)) {
            worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
            worldIn.playEvent(player, 2001, blockpos, Block.getStateId(otherState));
            ItemStack itemstack = player.getHeldItemMainhand();
            if(!worldIn.isRemote && !player.isCreative()) {
                Block.spawnDrops(state, worldIn, pos, null, player, itemstack);
                Block.spawnDrops(otherState, worldIn, blockpos, null, player, itemstack);
            }
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return rotate(state, Rotation.CLOCKWISE_180);
    }
}