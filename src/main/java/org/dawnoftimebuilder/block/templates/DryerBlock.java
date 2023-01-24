package org.dawnoftimebuilder.block.templates;

import javax.annotation.Nullable;

import org.dawnoftimebuilder.registry.DoTBTileEntitiesRegistry;
import org.dawnoftimebuilder.tileentity.DryerTileEntity;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
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
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

public class DryerBlock extends WaterloggedBlock {

	//TODO Add redstone compatibility : ie emit redstone when dried
	public static final IntegerProperty	SIZE		= DoTBBlockStateProperties.SIZE_0_2;
	public static final VoxelShape		VS_SIMPLE	= Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
	public static final VoxelShape		VS_DOUBLE	= Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

	public DryerBlock(final Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(DryerBlock.SIZE, 0));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(DryerBlock.SIZE);
	}

	@Override
	public VoxelShape getShape(final BlockState state, final IBlockReader worldIn, final BlockPos pos, final ISelectionContext context) {
		switch (state.getValue(DryerBlock.SIZE)) {
			default:
			case 0:
				return DryerBlock.VS_SIMPLE;
			case 1:
				return DryerBlock.VS_DOUBLE;
			case 2:
				return VoxelShapes.block();
		}
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(final BlockItemUseContext context) {
		final BlockPos		pos		= context.getClickedPos();
		final BlockState	state	= context.getLevel().getBlockState(pos);
		if (state.getBlock() == this) {
			return state.setValue(DryerBlock.SIZE, context.getLevel().getBlockState(pos.above()).getBlock() == this ? 2 : 1);
		}
		return super.getStateForPlacement(context);
	}

	@Override
	public boolean canBeReplaced(final BlockState state, final BlockItemUseContext useContext) {
		final ItemStack itemstack = useContext.getItemInHand();
		if (state.getValue(DryerBlock.SIZE) == 0 && itemstack.getItem() == this.asItem()) {
			return useContext.replacingClickedOnBlock();
		}
		return false;
	}

	@Override
	public void onRemove(final BlockState oldState, final World worldIn, final BlockPos pos, final BlockState newState, final boolean isMoving) {
		if (oldState.getBlock() != newState.getBlock()) {
			final TileEntity tileEntity = worldIn.getBlockEntity(pos);
			if (tileEntity instanceof DryerTileEntity) {
				tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
					InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), h.getStackInSlot(0));
					InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), h.getStackInSlot(1));
				});
			}
		}
		super.onRemove(oldState, worldIn, pos, newState, isMoving);
	}

	@Override
	public boolean canSurvive(final BlockState state, final IWorldReader worldIn, BlockPos pos) {
		pos = pos.below();
		final BlockState stateDown = worldIn.getBlockState(pos);
		if (stateDown.getBlock() == this) {
			return stateDown.getValue(DryerBlock.SIZE) != 0;
		}
		return Block.canSupportRigidBlock(worldIn, pos);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, final Direction facing, final BlockState facingState, final IWorld worldIn, final BlockPos currentPos, final BlockPos facingPos) {
		stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		if (facing == Direction.DOWN && !this.canSurvive(stateIn, worldIn, currentPos)) {
			return Blocks.AIR.defaultBlockState();
		}
		if (facing == Direction.UP && facingState.getBlock() == this) {
			System.out.println(stateIn.getBlock() + " : " + facingState.getBlock());
			return stateIn.setValue(DryerBlock.SIZE, stateIn.getValue(DryerBlock.SIZE) != 0 && facingState.getBlock() == this ? 2 : 1);
		}
		return stateIn;
	}

	@Override
	public boolean hasTileEntity(final BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(final BlockState state, final IBlockReader world) {
		return DoTBTileEntitiesRegistry.DRYER_TE.get().create();
	}

	@Override
	public ActionResultType use(final BlockState state, final World worldIn, final BlockPos pos, final PlayerEntity player, final Hand handIn, final BlockRayTraceResult hit) {
		if (!worldIn.isClientSide() && handIn == Hand.MAIN_HAND && worldIn.getBlockEntity(pos) instanceof DryerTileEntity) {
			final DryerTileEntity tileEntity = (DryerTileEntity) worldIn.getBlockEntity(pos);
			if (tileEntity == null) {
				return ActionResultType.PASS;
			}

			if (player.isCrouching()) {
				return tileEntity.dropOneItem(worldIn, pos);
			}
			return tileEntity.tryInsertItemStack(player.getItemInHand(handIn), state.getValue(DryerBlock.SIZE) == 0, worldIn, pos, player);
		}
		return ActionResultType.FAIL;
	}
}
