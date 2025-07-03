package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.blockentity.DisplayerBlockEntity;
import org.dawnoftime.dawnoftime.platform.Services;
import org.dawnoftime.dawnoftime.registry.DoTBBlockEntitiesRegistry;

import static net.minecraft.world.Containers.dropItemStack;

public abstract class DisplayerBlock extends WaterloggedBlock implements EntityBlock {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    protected DisplayerBlock(Properties properties, VoxelShape[] shapes) {
        super(properties, shapes);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return DoTBBlockEntitiesRegistry.INSTANCE.DISPLAYER.get().create(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level world, BlockPos pos, Player playerEntity, InteractionHand hand, BlockHitResult rayTraceResult) {
        if(!world.isClientSide()) {
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if(tileEntity instanceof MenuProvider provider) {
                Services.PLATFORM.openScreenHandler(playerEntity, provider, (player, buf) -> buf.writeBlockPos(pos));
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState oldState, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if(oldState.getBlock() != newState.getBlock()) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if(tileEntity instanceof DisplayerBlockEntity displayerEntity) {
                displayerEntity.itemHandler.removeAllItems().forEach(itemStack -> dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemStack));
            }
        }
        super.onRemove(oldState, worldIn, pos, newState, isMoving);
    }

    public abstract double getDisplayerX(BlockState state);

    public abstract double getDisplayerY(BlockState state);

    public abstract double getDisplayerZ(BlockState state);
}