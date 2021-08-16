package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
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
import org.dawnoftimebuilder.items.IItemCanBeDried;
import org.dawnoftimebuilder.tileentity.DryerTileEntity;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;

import javax.annotation.Nullable;

import static org.dawnoftimebuilder.registries.DoTBTileEntitiesRegistry.DRYER_TE;

public class DryerBlock extends WaterloggedBlock {

	//TODO It doesn't work, especially the renderer :(
	public static final IntegerProperty SIZE = DoTBBlockStateProperties.SIZE_0_2;
	public static final VoxelShape VS_SIMPLE = makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
	public static final VoxelShape VS_DOUBLE = makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

	public DryerBlock(Material materialIn, float hardness, float resistance) {
		super(materialIn, hardness, resistance);
		this.setDefaultState(this.stateContainer.getBaseState().with(SIZE, 0).with(WATERLOGGED,false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(SIZE);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch(state.get(SIZE)) {
			default:
			case 0:
				return VS_SIMPLE;
			case 1:
				return VS_DOUBLE;
			case 2 :
				return VoxelShapes.fullCube();
		}
	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos pos = context.getPos();
		BlockState state = context.getWorld().getBlockState(pos);
		if (state.getBlock() == this) {
			return state.with(SIZE, (context.getWorld().getBlockState(pos.up()).getBlock() == this) ? 2 : 1);
		}
		return super.getStateForPlacement(context);
	}

	public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
		ItemStack itemstack = useContext.getItem();
		if(state.get(SIZE) == 0 && itemstack.getItem() == this.asItem()) {
			return useContext.replacingClickedOnBlock();
		}
		return false;
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		pos = pos.down();
		BlockState stateDown = worldIn.getBlockState(pos);
		if(stateDown.getBlock() == this){
			return stateDown.get(SIZE) != 0;
		}
		return hasSolidSideOnTop(worldIn, pos);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		if(facing == Direction.DOWN){
			if(!isValidPosition(stateIn,worldIn,currentPos)) return Blocks.AIR.getDefaultState();
		}
		if(facing == Direction.UP){
			return stateIn.with(SIZE, (stateIn.get(SIZE) != 0 && facingState.getBlock() == this) ? 2 : 1);
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
		return DRYER_TE.create();
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(!worldIn.isRemote()) {
			if(worldIn.getTileEntity(pos) instanceof DryerTileEntity) {
				DryerTileEntity tileEntity = (DryerTileEntity) worldIn.getTileEntity(pos);
				if(tileEntity == null) return false;

				if(player.isSneaking()) return tileEntity.dropOneItem(worldIn, pos);

				else {
					ItemStack itemstack = player.getHeldItem(handIn);
					if(itemstack.getItem() instanceof IItemCanBeDried) {
						IItemCanBeDried item = (IItemCanBeDried) itemstack.getItem();
						int quantityNeeded = item.getItemQuantity();
						if(quantityNeeded <= itemstack.getCount()){
							if(tileEntity.putUndriedItem(item, state.get(SIZE) == 0, worldIn, pos)) {
								if(!player.isCreative()) itemstack.shrink(quantityNeeded);
								return true;
							}else return false;
						}
					}
					return tileEntity.dropOneDriedItem(worldIn, pos) >= 0;
				}
			}
		}
		return false;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
}
