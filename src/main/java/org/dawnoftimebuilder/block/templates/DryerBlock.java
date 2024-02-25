package org.dawnoftimebuilder.block.templates;

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
import org.dawnoftimebuilder.blockentity.DryerBlockEntity;
import org.dawnoftimebuilder.registry.DoTBBlockEntitiesRegistry;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import javax.annotation.Nullable;

import static net.minecraft.world.Containers.dropItemStack;
import static net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER;

public class DryerBlock extends WaterloggedBlock implements EntityBlock {
    //TODO Add redstone compatibility : ie emit redstone when dried
    public static final IntegerProperty SIZE = DoTBBlockStateProperties.SIZE_0_2;
    public static final VoxelShape VS_SIMPLE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
    public static final VoxelShape VS_DOUBLE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    public DryerBlock(final Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(DryerBlock.SIZE, 0));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return !pLevel.isClientSide() ? (pLevel1, pPos, pState1, pBlockEntity) -> ((DryerBlockEntity)pBlockEntity).tick() : null;
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(DryerBlock.SIZE);
    }

    @Override
    public VoxelShape getShape(final BlockState state, final BlockGetter worldIn, final BlockPos pos, final CollisionContext context) {
        switch(state.getValue(DryerBlock.SIZE)) {
            default:
            case 0:
                return DryerBlock.VS_SIMPLE;
            case 1:
                return DryerBlock.VS_DOUBLE;
            case 2:
                return Shapes.block();
        }
    }

    @Override
    @Nullable
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
            if(tileEntity instanceof DryerBlockEntity) {
                tileEntity.getCapability(ITEM_HANDLER).ifPresent(h -> {
                    dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), h.getStackInSlot(0));
                    dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), h.getStackInSlot(1));
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
    public BlockState updateShape(BlockState stateIn, final Direction facing, final BlockState facingState, final LevelAccessor worldIn, final BlockPos currentPos, final BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        if(facing == Direction.DOWN && !this.canSurvive(stateIn, worldIn, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        if(facing == Direction.UP && facingState.getBlock() == this) {
            return stateIn.setValue(DryerBlock.SIZE, stateIn.getValue(DryerBlock.SIZE) != 0 && facingState.getBlock() == this ? 2 : 1);
        }
        return stateIn;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return DoTBBlockEntitiesRegistry.DRYER.get().create(pPos, pState);
    }

    @Override
    public InteractionResult use(final BlockState state, final Level worldIn, final BlockPos pos, final Player player, final InteractionHand handIn, final BlockHitResult hit) {
        if(!worldIn.isClientSide() && handIn == InteractionHand.MAIN_HAND && worldIn.getBlockEntity(pos) instanceof DryerBlockEntity) {
            final DryerBlockEntity tileEntity = (DryerBlockEntity) worldIn.getBlockEntity(pos);
            if(tileEntity == null) {
                return InteractionResult.PASS;
            }

            if(player.isCrouching()) {
                return tileEntity.dropOneItem(worldIn, pos);
            }
            ItemStack handStack = player.getItemInHand(handIn);
            return tileEntity.tryInsertItemStack(handStack, state.getValue(DryerBlock.SIZE) == 0, worldIn, pos, player);
        }
        return InteractionResult.FAIL;
    }
}
