package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.blockentity.DryerBlockEntity;
import org.dawnoftime.dawnoftime.registry.DoTBBlockEntitiesRegistry;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static net.minecraft.world.Containers.dropItemStack;

public class DryerBlock extends WaterloggedBlock implements EntityBlock {
    //TODO Add redstone compatibility : ie emit redstone when dried
    public static final IntegerProperty SIZE = BlockStatePropertiesAA.SIZE_0_2;

    public DryerBlock(final Properties properties, VoxelShape[] shapes) {
        super(properties, shapes);
        this.registerDefaultState(this.defaultBlockState().setValue(DryerBlock.SIZE, 0));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, @NotNull BlockEntityType<T> entityType) {
        return !pLevel.isClientSide() ? (pLevel1, pPos, pState1, entity) -> ((DryerBlockEntity)entity).tick() : null;
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(DryerBlock.SIZE);
    }

    @Override
    public int getShapeIndex(final @NotNull BlockState state, final @NotNull BlockGetter worldIn, final @NotNull BlockPos pos, final @NotNull CollisionContext context) {
        return state.getValue(DryerBlock.SIZE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        final BlockPos pos = context.getClickedPos();
        final BlockState state = context.getLevel().getBlockState(pos);
        if(state.getBlock() == this) {
            return state.setValue(DryerBlock.SIZE, context.getLevel().getBlockState(pos.above()).getBlock() == this ? 2 : 1);
        }
        return super.getStateForPlacement(context);
    }

    @Override
    public boolean canBeReplaced(final BlockState state, final BlockPlaceContext useContext) {
        final ItemStack itemstack = useContext.getItemInHand();
        if(state.getValue(DryerBlock.SIZE) == 0 && itemstack.getItem() == this.asItem()) {
            return useContext.replacingClickedOnBlock();
        }
        return false;
    }

    @Override
    public void onRemove(final BlockState oldState, final Level worldIn, final BlockPos pos, final BlockState newState, final boolean isMoving) {
        if(oldState.getBlock() != newState.getBlock()) {
            final BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if(tileEntity instanceof DryerBlockEntity dryerBlockEntity) {
                dryerBlockEntity.itemHandler.removeAllItems().forEach(h -> {
                    dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), h);
                    dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), h);
                });
            }
        }
        super.onRemove(oldState, worldIn, pos, newState, isMoving);
    }

    @Override
    public boolean canSurvive(final BlockState state, final LevelReader worldIn, BlockPos pos) {
        pos = pos.below();
        final BlockState stateDown = worldIn.getBlockState(pos);
        if(stateDown.getBlock() == this) {
            return stateDown.getValue(DryerBlock.SIZE) != 0;
        }
        return Block.canSupportRigidBlock(worldIn, pos);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState stateIn, final @NotNull Direction facing, final @NotNull BlockState facingState, final @NotNull LevelAccessor worldIn, final @NotNull BlockPos currentPos, final @NotNull BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        if(facing == Direction.DOWN && !this.canSurvive(stateIn, worldIn, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        if(facing == Direction.UP) {
            return stateIn.setValue(DryerBlock.SIZE, stateIn.getValue(DryerBlock.SIZE) != 0 && facingState.getBlock() == this ? 2 : 1);
        }
        return stateIn;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return DoTBBlockEntitiesRegistry.INSTANCE.DRYER.get().create(pPos, pState);
    }

    @Override
    public InteractionResult use(final BlockState state, final Level worldIn, final BlockPos pos, final Player player, final InteractionHand handIn, final BlockHitResult hit) {
        if(!worldIn.isClientSide() && handIn == InteractionHand.MAIN_HAND && worldIn.getBlockEntity(pos) instanceof DryerBlockEntity dryerEntity) {
            if(player.isCrouching()) {
                return dryerEntity.dropOneItem(worldIn, pos);
            }
            ItemStack handStack = player.getItemInHand(handIn);
            return dryerEntity.tryInsertItemStack(handStack, state.getValue(DryerBlock.SIZE) == 0, worldIn, pos, player);
        }
        return InteractionResult.FAIL;
    }
}
