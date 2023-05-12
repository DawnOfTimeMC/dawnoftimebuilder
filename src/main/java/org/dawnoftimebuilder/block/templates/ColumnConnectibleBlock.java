package org.dawnoftimebuilder.block.templates;

import java.util.List;

import javax.annotation.Nullable;

import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public abstract class ColumnConnectibleBlock extends WaterloggedBlock {

	public static final EnumProperty<DoTBBlockStateProperties.VerticalConnection> VERTICAL_CONNECTION = DoTBBlockStateProperties.VERTICAL_CONNECTION;

	public ColumnConnectibleBlock(final Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(ColumnConnectibleBlock.VERTICAL_CONNECTION, DoTBBlockStateProperties.VerticalConnection.NONE));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ColumnConnectibleBlock.VERTICAL_CONNECTION);
	}

	@Override
	public abstract VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context);

	@Override
	public BlockState updateShape(BlockState stateIn, final Direction facing, final BlockState facingState, final IWorld worldIn, final BlockPos currentPos, final BlockPos facingPos) {
		stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		return facing.getAxis().isVertical() ? stateIn.setValue(ColumnConnectibleBlock.VERTICAL_CONNECTION, this.getColumnState(worldIn, currentPos, stateIn)) : stateIn;
	}

	public DoTBBlockStateProperties.VerticalConnection getColumnState(final IWorld worldIn, final BlockPos pos, final BlockState stateIn) {
		if (this.isConnectible(worldIn, pos.above(), stateIn)) {
			return this.isConnectible(worldIn, pos.below(), stateIn) ? DoTBBlockStateProperties.VerticalConnection.BOTH : DoTBBlockStateProperties.VerticalConnection.ABOVE;
		}
		return this.isConnectible(worldIn, pos.below(), stateIn) ? DoTBBlockStateProperties.VerticalConnection.UNDER : DoTBBlockStateProperties.VerticalConnection.NONE;
	}

	public boolean isConnectible(final IWorld worldIn, final BlockPos pos, final BlockState stateIn) {
		return worldIn.getBlockState(pos).getBlock() == this;
	}

	@Override
	public ActionResultType use(final BlockState state, final World worldIn, final BlockPos pos, final PlayerEntity player, final Hand handIn, final BlockRayTraceResult hit) {
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
				return ActionResultType.SUCCESS;
			}
		}
		else if (!heldItemStack.isEmpty() && heldItemStack.getItem() == this.asItem()) {
			//We put a ColumnBlock on top of the column
			final BlockPos topPos = this.getHighestColumnPos(worldIn, pos).above();
			if (topPos.getY() <= DoTBUtils.HIGHEST_Y) {
				if (!worldIn.isClientSide() && worldIn.getBlockState(topPos).isAir(worldIn, topPos)) {
					worldIn.setBlock(topPos, state, 11);
					if (!player.isCreative()) {
						heldItemStack.shrink(1);
					}
				}
				return ActionResultType.SUCCESS;
			}
		}
		return super.use(state, worldIn, pos, player, handIn, hit);
	}

	private BlockPos getHighestColumnPos(final World worldIn, final BlockPos pos) {
		int yOffset;
		for (yOffset = 0; yOffset + pos.getY() <= DoTBUtils.HIGHEST_Y; yOffset++) {
			if (worldIn.getBlockState(pos.above(yOffset)).getBlock() != this) {
				break;
			}
		}
		return pos.above(yOffset - 1);
	}

	@Override
	public void appendHoverText(final ItemStack stack, @Nullable final IBlockReader worldIn, final List<ITextComponent> tooltip, final ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		DoTBUtils.addTooltip(tooltip, DoTBUtils.TOOLTIP_COLUMN);
	}
}