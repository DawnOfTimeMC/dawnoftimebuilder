package org.dawnoftimebuilder.block.templates;

import javax.annotation.Nonnull;

import org.dawnoftimebuilder.entity.ChairEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class ChairBlock extends WaterloggedBlock {

	public static final DirectionProperty	FACING	= BlockStateProperties.HORIZONTAL_FACING;
	public final float						pixelsYOffset;

	public ChairBlock(final Properties properties, final float pixelsYOffset) {
		super(properties);
		this.pixelsYOffset = pixelsYOffset;
		this.registerDefaultState(this.defaultBlockState().setValue(ChairBlock.FACING, Direction.NORTH));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ChairBlock.FACING);
	}

	@Override
	@Nonnull
	public BlockState getStateForPlacement(final BlockItemUseContext context) {
		return super.getStateForPlacement(context).setValue(ChairBlock.FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public ActionResultType use(final BlockState state, final World worldIn, final BlockPos pos, final PlayerEntity player, final Hand handIn, final BlockRayTraceResult hit) {
		return ChairEntity.createEntity(worldIn, pos, player, this.pixelsYOffset);
	}

	@Override
	public BlockState rotate(final BlockState state, final Rotation rot) {
		return state.setValue(ChairBlock.FACING, rot.rotate(state.getValue(ChairBlock.FACING)));
	}

	@Override
	public BlockState mirror(final BlockState state, final Mirror mirrorIn) {
		return this.rotate(state, Rotation.CLOCKWISE_180);
	}
}
