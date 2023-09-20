package org.dawnoftimebuilder.block.roman;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
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
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nullable;

public class BirchCouchBlock extends ChairBlock {

    private static final VoxelShape[] SHAPES = DoTBUtils.GenerateHorizontalShapes(new VoxelShape[]{
            VoxelShapes.or(
                    Block.box(1.0D, 0.0D, 2.0D, 15.0D, 8.0D, 6.0D),
                    Block.box(0.0D, 8.0D, 0.0D, 16.0D, 13.0D, 16.0D),
                    Block.box(0.0D, 13.0D, 0.0D, 16.0D, 19.0D, 8.0D)
            )});

    public BirchCouchBlock(Properties properties, float pixelsYOffset) {
        super(properties, pixelsYOffset);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.getValue(FACING).get2DDataValue()];
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        float x = 8.0F;
        float z = 8.0F;
        switch (state.getValue(FACING)) {
            default:
            case NORTH:
                z = 2.0F;
                break;
            case SOUTH:
                z = 14.0F;
                break;
            case WEST:
                x = 2.0F;
                break;
            case EAST:
                x = 14.0F;
                break;
        }
        return ChairEntity.createEntity(worldIn, pos, player, x, this.pixelsYOffset, z);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getHorizontalDirection();
        if(context.getLevel().getBlockState(context.getClickedPos().relative(direction)).canBeReplaced(context)) return super.getStateForPlacement(context).setValue(FACING, direction);
        return null;
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        Direction currentFacing = state.getValue(FACING);
        worldIn.setBlock(pos.relative(currentFacing), state.setValue(FACING, currentFacing.getOpposite()), 3);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        Direction blockFacing = stateIn.getValue(FACING);
        if(facing == blockFacing){
            if(facingState.getBlock() == this){
                if(facingState.getValue(FACING).getOpposite() == blockFacing) return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
            }
            return Blocks.AIR.defaultBlockState();
        }else return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }


    @Override
    public void playerDestroy(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.playerDestroy(worldIn, player, pos, Blocks.AIR.defaultBlockState(), te, stack);
    }

    @Override
    public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockPos otherPos = pos.relative(state.getValue(FACING));
        BlockState otherState = worldIn.getBlockState(otherPos);
        if(otherState.getBlock() == this) {
            worldIn.setBlock(otherPos, Blocks.AIR.defaultBlockState(), 35);
            worldIn.levelEvent(player, 2001, otherPos, Block.getId(otherState));
            ItemStack itemstack = player.getMainHandItem();
            if(!worldIn.isClientSide() && !player.isCreative()) {
                //Only one of the 2 blocks drops since there is no way to make a difference between halves in loot_tables
                Block.dropResources(state, worldIn, pos, null, player, itemstack);
            }
        }
        super.playerWillDestroy(worldIn, pos, state, player);
    }
}
