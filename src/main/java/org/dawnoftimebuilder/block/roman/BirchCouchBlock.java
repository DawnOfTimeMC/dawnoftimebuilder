package org.dawnoftimebuilder.block.roman;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.ChairBlock;
import org.dawnoftimebuilder.entity.ChairEntity;
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nullable;

public class BirchCouchBlock extends ChairBlock {

    private static final VoxelShape[] SHAPES = DoTBUtils.GenerateHorizontalShapes(new VoxelShape[]{
            Shapes.or(
                    Block.box(1.0D, 0.0D, 2.0D, 15.0D, 8.0D, 6.0D),
                    Block.box(0.0D, 8.0D, 0.0D, 16.0D, 13.0D, 16.0D),
                    Block.box(0.0D, 13.0D, 0.0D, 16.0D, 19.0D, 8.0D)
            )});

    public BirchCouchBlock(Properties properties, float pixelsYOffset) {
        super(properties, pixelsYOffset);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(FACING).get2DDataValue()];
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
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
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection();
        if(context.getLevel().getBlockState(context.getClickedPos().relative(direction)).canBeReplaced(context)) return super.getStateForPlacement(context).setValue(FACING, direction);
        return null;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        Direction currentFacing = state.getValue(FACING);
        worldIn.setBlock(pos.relative(currentFacing), state.setValue(FACING, currentFacing.getOpposite()), 3);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
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
    public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
        super.playerDestroy(worldIn, player, pos, Blocks.AIR.defaultBlockState(), te, stack);
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
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
