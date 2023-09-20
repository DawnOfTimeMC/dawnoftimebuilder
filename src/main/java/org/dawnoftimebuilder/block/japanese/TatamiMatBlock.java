package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.block.Blocks.SPRUCE_PLANKS;
import static org.dawnoftimebuilder.registry.DoTBBlocksRegistry.TATAMI_FLOOR;
import static org.dawnoftimebuilder.util.DoTBUtils.COVERED_BLOCKS;

public class TatamiMatBlock extends WaterloggedBlock {

    private static final VoxelShape VS = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    private static final VoxelShape[] SHAPES = DoTBUtils.GenerateHorizontalShapes(makeShapes());
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty ROLLED = DoTBBlockStateProperties.ROLLED;
    public static final IntegerProperty STACK = DoTBBlockStateProperties.STACK;

    public TatamiMatBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ROLLED, false).setValue(HALF, Half.TOP).setValue(STACK, 1));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, HALF, ROLLED, STACK);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if(!state.getValue(ROLLED)) return VS;
        return SHAPES[state.getValue(STACK) - 1 + state.getValue(FACING).get2DDataValue() * 3];
    }

    /**
     * @return Stores VoxelShape for "South" with index : <p/>
     * 0 : S Rolled <p/>
     * 1 : S Rolled stack 2 <p/>
     * 2 : S Rolled stack 3 <p/>
     */
    private static VoxelShape[] makeShapes() {
        return new VoxelShape[]{
                Block.box(0.0D, 0.0D, 8.5D, 16.0D, 7.0D, 15.5D),
                Block.box(0.0D, 0.0D, 0.5D, 16.0D, 7.0D, 15.5D),
                Block.box(0.0D, 0.0D, 0.5D, 16.0D, 14.0D, 15.5D)
        };
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState oldState = world.getBlockState(pos);
        if(oldState.getBlock() == this){
            int stack = oldState.getValue(STACK);
            if(oldState.getValue(ROLLED) && stack < 3)
                return oldState.setValue(STACK, stack + 1);
        }
        Direction direction = context.getHorizontalDirection();
        if(world.getBlockState(pos.relative(direction)).canBeReplaced(context))
            return super.getStateForPlacement(context)
                    .setValue(ROLLED, world.getBlockState(pos.below()).getBlock().is(COVERED_BLOCKS) || world.getBlockState(pos.below().relative(direction)).getBlock().is(COVERED_BLOCKS))
                    .setValue(FACING, direction);
        return null;
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if(state.getValue(HALF) == Half.BOTTOM){
            BlockState topState = worldIn.getBlockState(pos.relative(state.getValue(FACING).getOpposite()));
            if(topState.getBlock() == this){
                if(topState.getValue(HALF) == Half.TOP && topState.getValue(FACING) == state.getValue(FACING))
                    return !worldIn.isEmptyBlock(pos.below());
            }
        }else return !worldIn.isEmptyBlock(pos.below());
        return false;
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        BlockState newState = this.tryMergingWithSprucePlanks(state, worldIn, pos);
        if(newState.getBlock() == Blocks.AIR)
            worldIn.setBlock(pos, newState, 10);
        else if(!newState.getValue(ROLLED))
            worldIn.setBlock(pos.relative(newState.getValue(FACING)), newState.setValue(HALF, Half.BOTTOM), 3);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        if(facing.getAxis().isVertical()){
            return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : this.tryMergingWithSprucePlanks(stateIn, worldIn, currentPos);
        }else{
            boolean mustDisappear = false;
            if(stateIn.getValue(ROLLED))
                return stateIn;
            Direction direction = stateIn.getValue(FACING);
            if(stateIn.getValue(HALF) == Half.TOP){
                if(direction == facing){ //If block is TOP, then BOTTOM half is toward "direction"
                    if(facingState.getBlock() == this){ //Update comes from the other half
                        if(facingState.getValue(FACING) != facing || facingState.getValue(HALF) != Half.BOTTOM || facingState.getValue(ROLLED))
                            mustDisappear = true;
                    }else
                        mustDisappear = true;
                }
            }else if(direction == facing.getOpposite()){ //If block is BOTTOM, then Top half is toward the opposite of "direction"
                if(facingState.getBlock() == this){ //Update comes from the other half
                    if(facingState.getValue(FACING) != direction || facingState.getValue(HALF) != Half.TOP || facingState.getValue(ROLLED))
                        mustDisappear = true;
                }else
                    mustDisappear = true;
            }
            if(mustDisappear){
                stateIn = Blocks.AIR.defaultBlockState();
                worldIn.setBlock(currentPos, stateIn, 2); //Avoid the breaking particles
            }
        }
        return stateIn;
    }

    private BlockState tryMergingWithSprucePlanks(BlockState state, IWorld worldIn, BlockPos pos){
        if(state.getValue(ROLLED)) return state;
        Direction facing = state.getValue(FACING);
        Block blockDown = worldIn.getBlockState(pos.below()).getBlock();
        Block blockDownAdjacent = worldIn.getBlockState(pos.relative(facing).below()).getBlock();
        if(blockDown == SPRUCE_PLANKS && blockDownAdjacent == SPRUCE_PLANKS){
            worldIn.setBlock(pos.below(), TATAMI_FLOOR.get().defaultBlockState().setValue(TatamiFloorBlock.FACING, facing).setValue(TatamiFloorBlock.HALF, state.getValue(HALF)), 10);
            worldIn.setBlock(pos.relative(facing).below(), TATAMI_FLOOR.get().defaultBlockState().setValue(TatamiFloorBlock.FACING, facing).setValue(TatamiFloorBlock.HALF, state.getValue(HALF) == Half.TOP ? Half.BOTTOM : Half.TOP), 10);
            return Blocks.AIR.defaultBlockState();
        }
        return state;
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(player.isCrouching()){
            int stack = state.getValue(STACK);
            boolean isRolled = state.getValue(ROLLED);
            if(isRolled && stack == 1) {
                if (worldIn.getBlockState(pos.below()).getBlock().is(COVERED_BLOCKS))
                    return ActionResultType.PASS;
            }
            if(state.getValue(STACK) > 1){
                state = state.setValue(STACK, stack - 1);
                InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this.asItem()));
            }else{
                state = state.setValue(ROLLED, !isRolled);
                Direction facing = state.getValue(FACING);
                if(isRolled){
                    if(!worldIn.isEmptyBlock(pos.relative(facing)) //Check if the position for the Bottom half is free
                            || worldIn.isEmptyBlock(pos.relative(facing).below()) //Check if this position can't support tatami
                            || worldIn.getBlockState(pos.below().relative(facing)).getBlock().is(COVERED_BLOCKS)) //Check if this position is not supported by a covered block
                        return ActionResultType.PASS;
                    worldIn.setBlock(pos.relative(facing), state.setValue(HALF, Half.BOTTOM), 2);
                }else if(state.getValue(HALF) == Half.BOTTOM){
                    state = state.setValue(HALF, Half.TOP).setValue(FACING, facing.getOpposite());
                }
            }
            state = this.updateShape(state, Direction.DOWN, worldIn.getBlockState(pos.below()), worldIn, pos, pos.below());
            worldIn.setBlock(pos, state, 2);
            worldIn.playSound(player, pos, this.soundType.getPlaceSound(), SoundCategory.BLOCKS, (this.soundType.getVolume() + 1.0F) / 2.0F, this.soundType.getPitch() * 0.8F);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockItemUseContext useContext) {
        ItemStack itemstack = useContext.getItemInHand();
        if(itemstack.getItem() == this.asItem()) {
            if(!state.getValue(ROLLED) || state.getValue(STACK) == 3)
                return false;
            return useContext.replacingClickedOnBlock();
        }
        return false;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return rotate(state, Rotation.CLOCKWISE_180);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        DoTBUtils.addTooltip(tooltip, this);
    }
}