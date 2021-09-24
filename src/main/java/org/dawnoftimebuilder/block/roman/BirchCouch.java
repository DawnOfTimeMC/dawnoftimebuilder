package org.dawnoftimebuilder.block.roman;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.templates.ChairBlock;
import org.dawnoftimebuilder.entity.ChairEntity;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nullable;

public class BirchCouch extends ChairBlock {

    private static final VoxelShape[] SHAPES = DoTBBlockUtils.GenerateHorizontalShapes(new VoxelShape[]{
            VoxelShapes.or(
                    Block.box(1.0D, 0.0D, 2.0D, 15.0D, 8.0D, 6.0D),
                    Block.box(0.0D, 8.0D, 0.0D, 16.0D, 13.0D, 16.0D),
                    Block.box(0.0D, 13.0D, 0.0D, 16.0D, 19.0D, 8.0D)
            )});

    public BirchCouch(Material materialIn, float hardness, float resistance, SoundType soundType, float pixelsYOffset) {
        super(Properties.of(materialIn).strength(hardness, resistance).sound(soundType), pixelsYOffset);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.get(FACING).getHorizontalIndex()];
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        float x = 8.0F;
        float z = 8.0F;
        switch (state.get(FACING)) {
            default:
            case NORTH:
                z = 0.0F;
                break;
            case SOUTH:
                z = 16.0F;
                break;
            case WEST:
                x = 0.0F;
                break;
            case EAST:
                x = 16.0F;
                break;
        }
        return ChairEntity.createEntity(worldIn, pos, player, x, this.pixelsYOffset, z);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getPlacementHorizontalFacing();
        if(context.getLevel().getBlockState(context.getPos().offset(direction)).isReplaceable(context)) return super.getStateForPlacement(context).with(FACING, direction);
        return null;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        Direction currentFacing = state.get(FACING);
        worldIn.setBlockState(pos.offset(currentFacing), state.with(FACING, currentFacing.getOpposite()), 3);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        Direction blockFacing = stateIn.get(FACING);
        if(facing == blockFacing){
            if(facingState.getBlock() == this){
                if(facingState.get(FACING).getOpposite() == blockFacing) return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
            }
            return Blocks.AIR.defaultBlockState();
        }else return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }


    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, Blocks.AIR.defaultBlockState(), te, stack);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockPos otherPos = pos.offset(state.get(FACING));
        BlockState otherState = worldIn.getBlockState(otherPos);
        if(otherState.getBlock() == this) {
            worldIn.setBlockState(otherPos, Blocks.AIR.defaultBlockState(), 35);
            worldIn.playEvent(player, 2001, otherPos, Block.getStateId(otherState));
            ItemStack itemstack = player.getHeldItemMainhand();
            if(!worldIn.isRemote() && !player.isCreative()) {
                //Only one of the 2 blocks drops since there is no way to make a difference between halves in loot_tables
                Block.spawnDrops(state, worldIn, pos, null, player, itemstack);
            }
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }
}
