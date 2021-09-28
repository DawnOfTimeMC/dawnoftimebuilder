package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nullable;

import java.util.List;

import static net.minecraft.block.Blocks.SPRUCE_PLANKS;
import static org.dawnoftimebuilder.registry.DoTBBlocksRegistry.TATAMI_FLOOR;
import static org.dawnoftimebuilder.util.DoTBBlockUtils.COVERED_BLOCKS;

public class TatamiMatBlock extends WaterloggedBlock {

    private static final VoxelShape VS = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    private static final VoxelShape[] SHAPES = DoTBBlockUtils.GenerateHorizontalShapes(makeShapes());
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty ROLLED = DoTBBlockStateProperties.ROLLED;
    public static final IntegerProperty STACK = DoTBBlockStateProperties.STACK;

    public TatamiMatBlock(Material materialIn, float hardness, float resistance, SoundType soundType) {
        super(Properties.of(materialIn).strength(hardness, resistance).sound(soundType));
        this.registerDefaultState(this.stateContainer.getBaseState().setValue(WATERLOGGED, false).setValue(ROLLED, false).setValue(HALF, Half.TOP).setValue(STACK, 1));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, HALF, ROLLED, STACK);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if(!state.get(ROLLED)) return VS;
        return SHAPES[state.get(STACK) - 1 + state.get(FACING).get2DDataValue() * 3];
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
            int stack = oldState.get(STACK);
            if(oldState.get(ROLLED) && stack < 3)
                return oldState.setValue(STACK, stack + 1);
        }
        Direction direction = context.getHorizontalDirection();
        if(world.getBlockState(pos.relative(direction)).isReplaceable(context))
            return super.getStateForPlacement(context)
                    .setValue(ROLLED, world.getBlockState(pos.below()).getBlock().isIn(COVERED_BLOCKS) || world.getBlockState(pos.below().relative(direction)).getBlock().isIn(COVERED_BLOCKS))
                    .setValue(FACING, direction);
        return null;
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if(state.get(HALF) == Half.BOTTOM){
            BlockState topState = worldIn.getBlockState(pos.relative(state.get(FACING).getOpposite()));
            if(topState.getBlock() == this){
                if(topState.get(HALF) == Half.TOP && topState.get(FACING) == state.get(FACING))
                    return !worldIn.isEmptyBlock(pos.below());
            }
        }else return !worldIn.isEmptyBlock(pos.below());
        return false;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        BlockState newState = this.tryMergingWithSprucePlanks(state, worldIn, pos);
        if(newState.getBlock() == Blocks.AIR)
            worldIn.setBlock(pos, newState);
        else if(!newState.get(ROLLED))
            worldIn.setBlock(pos.relative(newState.get(FACING)), newState.setValue(HALF, Half.BOTTOM), 3);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        if(facing.getAxis().isVertical()){
            return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : this.tryMergingWithSprucePlanks(stateIn, worldIn.getLevel(), currentPos);
        }else{
            boolean mustDisappear = false;
            if(stateIn.get(ROLLED))
                return stateIn;
            Direction direction = stateIn.get(FACING);
            if(stateIn.get(HALF) == Half.TOP){
                if(direction == facing){ //If block is TOP, then BOTTOM half is toward "direction"
                    if(facingState.getBlock() == this){ //Update comes from the other half
                        if(facingState.get(FACING) != facing || facingState.get(HALF) != Half.BOTTOM || facingState.get(ROLLED))
                            mustDisappear = true;
                    }else
                        mustDisappear = true;
                }
            }else if(direction == facing.getOpposite()){ //If block is BOTTOM, then Top half is toward the opposite of "direction"
                if(facingState.getBlock() == this){ //Update comes from the other half
                    if(facingState.get(FACING) != direction || facingState.get(HALF) != Half.TOP || facingState.get(ROLLED))
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

    private BlockState tryMergingWithSprucePlanks(BlockState state, World worldIn, BlockPos pos){
        if(state.get(ROLLED)) return state;
        Direction facing = state.get(FACING);
        Block blockDown = worldIn.getBlockState(pos.below()).getBlock();
        Block blockDownAdjacent = worldIn.getBlockState(pos.relative(facing).below()).getBlock();
        if(blockDown == SPRUCE_PLANKS && blockDownAdjacent == SPRUCE_PLANKS){
            worldIn.setBlock(pos.below(), TATAMI_FLOOR.defaultBlockState().setValue(TatamiFloorBlock.FACING, facing).setValue(TatamiFloorBlock.HALF, state.get(HALF)));
            worldIn.setBlock(pos.relative(facing).below(), TATAMI_FLOOR.defaultBlockState().setValue(TatamiFloorBlock.FACING, facing).setValue(TatamiFloorBlock.HALF, state.get(HALF) == Half.TOP ? Half.BOTTOM : Half.TOP));
            return Blocks.AIR.defaultBlockState();
        }
        return state;
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(player.isCrouching()){
            int stack = state.get(STACK);
            boolean isRolled = state.get(ROLLED);
            if(isRolled && stack == 1) {
                if (worldIn.getBlockState(pos.below()).getBlock().isIn(COVERED_BLOCKS))
                    return false;
            }
            if(state.get(STACK) > 1){
                state = state.setValue(STACK, stack - 1);
                spawnAsEntity(worldIn.getLevel(), pos, new ItemStack(this.asItem(), 1));
            }else{
                state = state.setValue(ROLLED, !isRolled);
                Direction facing = state.get(FACING);
                if(isRolled){
                    if(!worldIn.isEmptyBlock(pos.relative(facing)) //Check if the position for the Bottom half is free
                            || worldIn.isEmptyBlock(pos.relative(facing).below()) //Check if this position can't support tatami
                            || worldIn.getBlockState(pos.below().relative(facing)).getBlock().isIn(COVERED_BLOCKS)) //Check if this position is not supported by a covered block
                        return false;
                    worldIn.setBlock(pos.relative(facing), state.setValue(HALF, Half.BOTTOM), 2);
                }else if(state.get(HALF) == Half.BOTTOM){
                    state = state.setValue(HALF, Half.TOP).setValue(FACING, facing.getOpposite());
                }
            }
            state = this.updateShape(state, Direction.DOWN, worldIn.getBlockState(pos.below()), worldIn, pos, pos.below());
            worldIn.setBlock(pos, state, 2);
            worldIn.playSound(player, pos, this.soundType.getPlaceSound(), SoundCategory.BLOCKS, (this.soundType.getVolume() + 1.0F) / 2.0F, this.soundType.getPitch() * 0.8F);
            return true;
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
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return rotate(state, Rotation.CLOCKWISE_180);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        DoTBBlockUtils.addTooltip(tooltip, this);
    }
}