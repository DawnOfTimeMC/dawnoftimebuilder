package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.dawnoftime.dawnoftime.util.Utils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class ConnectedVerticalBlock extends WaterloggedBlock {
    public static final EnumProperty<BlockStatePropertiesAA.VerticalConnection> VERTICAL_CONNECTION = BlockStatePropertiesAA.VERTICAL_CONNECTION;

    public ConnectedVerticalBlock(final Properties properties, VoxelShape[] shapes) {
        super(properties, shapes);
        this.registerDefaultState(this.defaultBlockState().setValue(ConnectedVerticalBlock.VERTICAL_CONNECTION, BlockStatePropertiesAA.VerticalConnection.NONE));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ConnectedVerticalBlock.VERTICAL_CONNECTION);
    }

    @Override
    public int getShapeIndex(final @NotNull BlockState state, final @NotNull BlockGetter worldIn, final @NotNull BlockPos pos, final @NotNull CollisionContext context) {
        return state.getValue(ConnectedVerticalBlock.VERTICAL_CONNECTION).getIndex();
    }

    @Override
    public @NotNull BlockState updateShape(BlockState stateIn, final @NotNull Direction facing, final @NotNull BlockState facingState, final @NotNull LevelAccessor worldIn, final @NotNull BlockPos currentPos, final @NotNull BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        return facing.getAxis().isVertical() ? stateIn.setValue(ConnectedVerticalBlock.VERTICAL_CONNECTION, this.getColumnState(worldIn, currentPos, stateIn)) : stateIn;
    }

    public BlockStatePropertiesAA.VerticalConnection getColumnState(final LevelAccessor worldIn, final BlockPos pos, final BlockState stateIn) {
        if(this.isConnectible(stateIn, worldIn, pos.above(), Direction.DOWN)) {
            return this.isConnectible(stateIn, worldIn, pos.below(), Direction.UP) ? BlockStatePropertiesAA.VerticalConnection.BOTH : BlockStatePropertiesAA.VerticalConnection.ABOVE;
        }
        return this.isConnectible(stateIn, worldIn, pos.below(), Direction.UP) ? BlockStatePropertiesAA.VerticalConnection.UNDER : BlockStatePropertiesAA.VerticalConnection.NONE;
    }

    public boolean isConnectible(final BlockState stateIn, final LevelAccessor worldIn, final BlockPos pos, final Direction faceToConnect) {
        return worldIn.getBlockState(pos).getBlock() == this;
    }

    @Override
    public InteractionResult use(final BlockState state, final Level worldIn, final BlockPos pos, final Player player, final InteractionHand handIn, final BlockHitResult hit) {
        final ItemStack heldItemStack = player.getItemInHand(handIn);
        if(player.isCrouching()) {
            //We remove the highest ColumnBlock
            if(state.getValue(ConnectedVerticalBlock.VERTICAL_CONNECTION) == BlockStatePropertiesAA.VerticalConnection.NONE) {
                return super.use(state, worldIn, pos, player, handIn, hit);
            }
            final BlockPos topPos = this.getHighestColumnPos(worldIn, pos);
            if(topPos != pos) {
                if(!worldIn.isClientSide()) {
                    worldIn.setBlock(topPos, Blocks.AIR.defaultBlockState(), 35);
                    if(!player.isCreative()) {
                        Block.dropResources(state, worldIn, pos, null, player, heldItemStack);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        } else if(!heldItemStack.isEmpty() && heldItemStack.getItem() == this.asItem()) {
            //We put a ColumnBlock on top of the column
            final BlockPos topPos = this.getHighestColumnPos(worldIn, pos).above();
            if(topPos.getY() <= Utils.HIGHEST_Y) {
                if(worldIn.getBlockState(topPos).isAir()) {
                    if (!worldIn.isClientSide()) {
                        worldIn.setBlock(topPos, state, 11);
                        if (!player.isCreative()) {
                            heldItemStack.shrink(1);
                        }
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    private BlockPos getHighestColumnPos(final Level worldIn, final BlockPos pos) {
        int yOffset;
        for(yOffset = 0; yOffset + pos.getY() <= Utils.HIGHEST_Y; yOffset++) {
            if(worldIn.getBlockState(pos.above(yOffset)).getBlock() != this) {
                break;
            }
        }
        return pos.above(yOffset - 1);
    }

    @Override
    public void appendHoverText(final ItemStack stack, @Nullable final BlockGetter worldIn, final List<Component> tooltip, final TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        Utils.addTooltip(tooltip, Utils.TOOLTIP_COLUMN);
    }
}