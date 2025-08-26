package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.blockentity.DisplayerBlockEntity;
import org.dawnoftime.dawnoftime.registry.DoTBBlockEntitiesRegistry;
import org.jetbrains.annotations.NotNull;

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
    public @NotNull InteractionResult use(@NotNull BlockState blockState, Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (hit.getDirection() != Direction.UP) {
            return InteractionResult.PASS;
        }
        if (world.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        Vec3 hitVec = hit.getLocation();
        double localX = hitVec.x - pos.getX();
        double localZ = hitVec.z - pos.getZ();
        int gridX = (int) (localX * 3);
        int gridZ = (int) (localZ * 3);
        gridX = Mth.clamp(gridX, 0, 2);
        gridZ = Mth.clamp(gridZ, 0, 2);
        int slot = gridZ * 3 + gridX;

        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof DisplayerBlockEntity displayer)) {
            return InteractionResult.PASS;
        }

        ItemStack held = player.getItemInHand(hand);
        ItemStack slotItem = displayer.getItem(slot);
        if (!held.isEmpty()) {
            if (slotItem.isEmpty()) {
                displayer.setItem(slot, held);
            } else if (held.getItem() == slotItem.getItem() && held.getCount() < held.getMaxStackSize()) {
                held.setCount(held.getCount() + 1);
                displayer.setItem(slot, ItemStack.EMPTY);
            }
        } else if (!slotItem.isEmpty()) {
            player.setItemInHand(hand, slotItem);
            displayer.setItem(slot, ItemStack.EMPTY);
        }
        boolean lit = displayer.isLit();
        if (lit != blockState.getValue(LIT)) {
            world.setBlock(pos, blockState.setValue(LIT, lit), 10);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState oldState, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if(oldState.getBlock() != newState.getBlock()) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if(tileEntity instanceof DisplayerBlockEntity displayerEntity) {
                displayerEntity.removeAllItems().forEach(itemStack -> dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemStack));
            }
        }
        super.onRemove(oldState, worldIn, pos, newState, isMoving);
    }

    public abstract double getDisplayerX(BlockState state);

    public abstract double getDisplayerY(BlockState state);

    public abstract double getDisplayerZ(BlockState state);
}