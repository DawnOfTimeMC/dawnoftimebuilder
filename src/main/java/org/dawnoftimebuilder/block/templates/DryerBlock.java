package org.dawnoftimebuilder.block.templates;

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
import org.dawnoftimebuilder.tileentity.DryerTileEntity;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import javax.annotation.Nullable;

import static org.dawnoftimebuilder.registry.DoTBTileEntitiesRegistry.DRYER_TE;

public class DryerBlock extends WaterloggedBlock {

	//TODO Add redstone compatibility : ie emit redstone when dried
	public static final IntegerProperty SIZE = DoTBBlockStateProperties.SIZE_0_2;
	public static final VoxelShape VS_SIMPLE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
	public static final VoxelShape VS_DOUBLE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

	public DryerBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(SIZE, 0));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(SIZE);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch(state.getValue(SIZE)) {
			default:
			case 0:
				return VS_SIMPLE;
			case 1:
				return VS_DOUBLE;
			case 2 :
				return VoxelShapes.block();
		}
	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos pos = context.getClickedPos();
		BlockState state = context.getLevel().getBlockState(pos);
		if (state.getBlock() == this) {
			return state.setValue(SIZE, (context.getLevel().getBlockState(pos.above()).getBlock() == this) ? 2 : 1);
		}
		return super.getStateForPlacement(context);
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockItemUseContext useContext) {
		ItemStack itemstack = useContext.getItemInHand();
		if(state.getValue(SIZE) == 0 && itemstack.getItem() == this.asItem()) {
			return useContext.replacingClickedOnBlock();
		}
		return false;
	}

	@Override
	public void onRemove(BlockState oldState, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (oldState.getBlock() != newState.getBlock()) {
			TileEntity tileEntity = worldIn.getBlockEntity(pos);
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
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		pos = pos.below();
		BlockState stateDown = worldIn.getBlockState(pos);
		if(stateDown.getBlock() == this){
			return stateDown.getValue(SIZE) != 0;
		}
		return canSupportRigidBlock(worldIn, pos);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		if(facing == Direction.DOWN){
			if(!canSurvive(stateIn,worldIn,currentPos)) return Blocks.AIR.defaultBlockState();
		}
		if(facing == Direction.UP){
			return stateIn.setValue(SIZE, (stateIn.getValue(SIZE) != 0 && facingState.getBlock() == this) ? 2 : 1);
		}
		return stateIn;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world){
		return DRYER_TE.get().create();
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(!worldIn.isClientSide() && handIn == Hand.MAIN_HAND) {
			if(worldIn.getBlockEntity(pos) instanceof DryerTileEntity) {
				DryerTileEntity tileEntity = (DryerTileEntity) worldIn.getBlockEntity(pos);
				if(tileEntity == null) return ActionResultType.PASS;

				if(player.isCrouching()) return tileEntity.dropOneItem(worldIn, pos);

				else {
					return tileEntity.tryInsertItemStack(player.getItemInHand(handIn), state.getValue(SIZE) == 0, worldIn, pos, player);
				}
			}
		}
		return ActionResultType.FAIL;
	}
}
