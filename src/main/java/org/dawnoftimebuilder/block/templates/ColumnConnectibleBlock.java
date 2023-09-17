package org.dawnoftimebuilder.block.templates;

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
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ColumnConnectibleBlock extends WaterloggedBlock {

	public static final EnumProperty<DoTBBlockStateProperties.VerticalConnection> VERTICAL_CONNECTION = DoTBBlockStateProperties.VERTICAL_CONNECTION;

	public ColumnConnectibleBlock(final Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(ColumnConnectibleBlock.VERTICAL_CONNECTION, DoTBBlockStateProperties.VerticalConnection.NONE));
	}

	@Override
	protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ColumnConnectibleBlock.VERTICAL_CONNECTION);
	}

	@Override
	public abstract VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context);

	@Override
	public BlockState updateShape(BlockState stateIn, final Direction facing, final BlockState facingState, final LevelAccessor worldIn, final BlockPos currentPos, final BlockPos facingPos) {
		stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		return facing.getAxis().isVertical() ? stateIn.setValue(ColumnConnectibleBlock.VERTICAL_CONNECTION, this.getColumnState(worldIn, currentPos, stateIn)) : stateIn;
	}

	public DoTBBlockStateProperties.VerticalConnection getColumnState(final LevelAccessor worldIn, final BlockPos pos, final BlockState stateIn) {
		if (this.isConnectible(stateIn, worldIn, pos.above(), Direction.DOWN)) {
			return this.isConnectible(stateIn, worldIn, pos.below(), Direction.UP) ? DoTBBlockStateProperties.VerticalConnection.BOTH : DoTBBlockStateProperties.VerticalConnection.ABOVE;
		}
		return this.isConnectible(stateIn, worldIn, pos.below(), Direction.UP) ? DoTBBlockStateProperties.VerticalConnection.UNDER : DoTBBlockStateProperties.VerticalConnection.NONE;
	}

	public boolean isConnectible(final BlockState stateIn, final LevelAccessor worldIn, final BlockPos pos, final Direction faceToConnect) {
		return worldIn.getBlockState(pos).getBlock() == this;
	}

	@Override
	public InteractionResult use(final BlockState state, final Level worldIn, final BlockPos pos, final Player player, final InteractionHand handIn, final BlockHitResult hit) {
		final ItemStack heldItemStack = player.getItemInHand(handIn);
		if (player.isCrouching()) {
			//We remove the highest ColumnBlock
			if (state.getValue(ColumnConnectibleBlock.VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.NONE) {
				return super.use(state, worldIn, pos, player, handIn, hit);
			}
			final BlockPos topPos = this.getHighestColumnPos(worldIn, pos);
			if (topPos != pos) {
				if (!worldIn.isClientSide()) {
					worldIn.setBlock(topPos, Blocks.AIR.defaultBlockState(), 35);
					if (!player.isCreative()) {
						Block.dropResources(state, worldIn, pos, null, player, heldItemStack);
					}
				}
				return InteractionResult.SUCCESS;
			}
		}
		else if (!heldItemStack.isEmpty() && heldItemStack.getItem() == this.asItem()) {
			//We put a ColumnBlock on top of the column
			final BlockPos topPos = this.getHighestColumnPos(worldIn, pos).above();
			if (topPos.getY() <= DoTBUtils.HIGHEST_Y) {
				if (!worldIn.isClientSide() && worldIn.getBlockState(topPos).isAir()) {
					worldIn.setBlock(topPos, state, 11);
					if (!player.isCreative()) {
						heldItemStack.shrink(1);
					}
				}
				return InteractionResult.SUCCESS;
			}
		}
		return super.use(state, worldIn, pos, player, handIn, hit);
	}

	private BlockPos getHighestColumnPos(final Level worldIn, final BlockPos pos) {
		int yOffset;
		for (yOffset = 0; yOffset + pos.getY() <= DoTBUtils.HIGHEST_Y; yOffset++) {
			if (worldIn.getBlockState(pos.above(yOffset)).getBlock() != this) {
				break;
			}
		}
		return pos.above(yOffset - 1);
	}

	@Override
	public void appendHoverText(final ItemStack stack, @Nullable final BlockGetter worldIn, final List<Component> tooltip, final TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		DoTBUtils.addTooltip(tooltip, DoTBUtils.TOOLTIP_COLUMN);
	}
}