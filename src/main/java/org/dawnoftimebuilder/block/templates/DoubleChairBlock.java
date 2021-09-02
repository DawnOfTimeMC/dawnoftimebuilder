package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class DoubleChairBlock extends ChairBlock{

    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    public DoubleChairBlock(Properties properties, float offsetY) {
        super(properties, offsetY);
        this.setDefaultState(this.getStateContainer().getBaseState().with(HALF, Half.BOTTOM).with(FACING, Direction.NORTH).with(WATERLOGGED,false));
    }

    public DoubleChairBlock(Material materialIn, float hardness, float resistance, float pixelsYOffset) {
        this(Properties.create(materialIn).hardnessAndResistance(hardness, resistance), pixelsYOffset);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(HALF);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        if(!context.getWorld().getBlockState(context.getPos().up()).isReplaceable(context)) return null;
        return super.getStateForPlacement(context).with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        worldIn.setBlockState(pos.up(), state.with(HALF, Half.TOP), 10);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(state.get(HALF) == Half.TOP) return false;
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        Direction halfDirection = (stateIn.get(HALF) == Half.TOP) ? Direction.DOWN : Direction.UP;
        if(facing == halfDirection){
            if(facingState.getBlock() != this) return Blocks.AIR.getDefaultState();
            if(facingState.get(HALF) == stateIn.get(HALF) || facingState.get(FACING) != stateIn.get(FACING)) return Blocks.AIR.getDefaultState();
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, Blocks.AIR.getDefaultState(), te, stack);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockPos blockpos = (state.get(HALF) == Half.TOP) ? pos.down() : pos.up();
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
}
