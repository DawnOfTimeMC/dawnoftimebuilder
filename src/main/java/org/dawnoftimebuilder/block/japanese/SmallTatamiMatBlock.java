package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
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
import org.dawnoftimebuilder.block.IBlockChain;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.block.Blocks.SPRUCE_PLANKS;
import static org.dawnoftimebuilder.registry.DoTBBlocksRegistry.SMALL_TATAMI_FLOOR;
import static org.dawnoftimebuilder.util.DoTBBlockUtils.COVERED_BLOCKS;

public class SmallTatamiMatBlock extends WaterloggedBlock implements IBlockChain {

    private static final VoxelShape VS = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    private static final VoxelShape X_ROLLED_VS = Block.box(5.5D, 0.0D, 0.0D, 10.5D, 5.0D, 16.0D);
    private static final VoxelShape X_ROLLED_VS_2 = Block.box(1.5D, 0.0D, 0.0D, 14.5D, 5.0D, 16.0D);
    private static final VoxelShape X_ROLLED_VS_3 = Block.box(1.5D, 0.0D, 0.0D, 14.5D, 10.0D, 16.0D);
    private static final VoxelShape Z_ROLLED_VS = Block.box(0.0D, 0.0D, 5.5D, 16.0D, 5.0D, 10.5D);
    private static final VoxelShape Z_ROLLED_VS_2 = Block.box(0.0D, 0.0D, 1.5D, 16.0D, 5.0D, 14.5D);
    private static final VoxelShape Z_ROLLED_VS_3 = Block.box(0.0D, 0.0D, 1.5D, 16.0D, 10.0D, 14.5D);
    private static final VoxelShape ATTACHED_VS = Block.box(5.5D, 0.0D, 5.5D, 10.5D, 16.0D, 10.5D);
    public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;
    public static final BooleanProperty ROLLED = DoTBBlockStateProperties.ROLLED;
    public static final IntegerProperty STACK = DoTBBlockStateProperties.STACK;

    public SmallTatamiMatBlock(Material materialIn, float hardness, float resistance, SoundType soundType) {
        super(Properties.of(materialIn).strength(hardness, resistance).sound(soundType));
        this.registerDefaultState(this.stateContainer.getBaseState().setValue(ROLLED, false).setValue(ATTACHED, false).setValue(STACK, 1).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ATTACHED, HORIZONTAL_AXIS, ROLLED, STACK);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if(state.getValue(ATTACHED)) return ATTACHED_VS;
        if(state.getValue(ROLLED)){
            boolean isAxisX = state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X;
            switch (state.getValue(STACK)){
                default:
                case 1:
                    return isAxisX ? X_ROLLED_VS : Z_ROLLED_VS;
                case 2:
                    return isAxisX ? X_ROLLED_VS_2 : Z_ROLLED_VS_2;
                case 3:
                    return isAxisX ? X_ROLLED_VS_3 : Z_ROLLED_VS_3;
            }
        }
        return VS;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState oldState = world.getBlockState(pos);
        if(oldState.getBlock() == this){
            int stack = oldState.getValue(STACK);
            if(oldState.getValue(ROLLED) && !oldState.getValue(ATTACHED) && stack < 3){
                return oldState.setValue(STACK, stack + 1);
            }
        }
        return super.getStateForPlacement(context)
                .setValue(ROLLED, world.getBlockState(pos.below()).getBlock().isIn(COVERED_BLOCKS))
                .setValue(HORIZONTAL_AXIS, context.getHorizontalDirection().getAxis());
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if(state.get(ATTACHED)){
            BlockState stateUp = worldIn.getBlockState(pos.above());
            Block blockUp = stateUp.getBlock();
            if(blockUp.isIn(BlockTags.FENCES) || worldIn.getBlockState(pos.below()).getBlock().isIn(BlockTags.FENCES)) return true;
            if(IBlockChain.canBeChained(stateUp, true)) return true;
        }
        return !worldIn.isAirBlock(pos.below());
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        if(facing.getAxis().isVertical()){
            stateIn = stateIn.setValue(ATTACHED, false);
            if(stateIn.get(ROLLED) && stateIn.get(STACK) == 1){
                BlockState stateUp = worldIn.getBlockState(currentPos.above());
                if(stateUp.getBlock().isIn(BlockTags.FENCES)
                        || worldIn.getBlockState(currentPos.below()).getBlock().isIn(BlockTags.FENCES)
                        || IBlockChain.canBeChained(stateUp, true))
                    stateIn = stateIn.setValue(ATTACHED, true);
            }
            return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : this.tryMergingWithSprucePlanks(stateIn, worldIn.getLevel(), currentPos);
        }
        return stateIn;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        BlockState newState = this.tryMergingWithSprucePlanks(state, worldIn, pos);
        if(newState.getBlock() == Blocks.AIR) worldIn.setBlock(pos, newState);
    }

    private BlockState tryMergingWithSprucePlanks(BlockState state, World worldIn, BlockPos pos){
        if(state.get(ROLLED)) return state;
        Block blockDown = worldIn.getBlockState(pos.below()).getBlock();
        if(blockDown == SPRUCE_PLANKS){
            worldIn.setBlock(pos.below(), SMALL_TATAMI_FLOOR.defaultBlockState());
            return Blocks.AIR.defaultBlockState();
        }
        return state.setValue(ATTACHED, false);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(player.isCrouching()){
            int stack = state.get(STACK);
            boolean isRolled = state.get(ROLLED);
            if(isRolled && stack == 1)
                if(worldIn.getBlockState(pos.below()).getBlock().isIn(COVERED_BLOCKS))
                    return false;
            if(state.get(STACK) > 1){
                state = state.setValue(STACK, stack - 1);
                spawnAsEntity(worldIn.getLevel(), pos, new ItemStack(this.asItem(), 1));
            }else
                state = state.setValue(ROLLED, !isRolled);
            state = this.updateShape(state, Direction.DOWN, worldIn.getBlockState(pos.below()), worldIn, pos, pos.below());
            worldIn.setBlock(pos, state, 10);
            worldIn.playSound(player, pos, this.soundType.getPlaceSound(), SoundCategory.BLOCKS, (this.soundType.getVolume() + 1.0F) / 2.0F, this.soundType.getPitch() * 0.8F);

            return true;
        }
        return false;
    }

    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        ItemStack itemstack = useContext.getItem();
        if(itemstack.getItem() == this.asItem()) {
            if(!state.get(ROLLED) || state.get(ATTACHED) || state.get(STACK) == 3) return false;
            return useContext.replacingClickedOnBlock();
        }
        return false;
    }

    @Override
    public boolean canConnectToChainAbove(BlockState state) {
        return state.get(ATTACHED);
    }

    @Override
    public boolean canConnectToChainUnder(BlockState state) {
        return state.get(ATTACHED);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(HORIZONTAL_AXIS, state.get(HORIZONTAL_AXIS) == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        DoTBBlockUtils.addTooltip(tooltip, this);
    }
}