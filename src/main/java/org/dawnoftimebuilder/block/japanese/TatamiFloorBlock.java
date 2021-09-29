package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.templates.NoItemBlock;

import static net.minecraft.block.Blocks.SPRUCE_PLANKS;
import static org.dawnoftimebuilder.registry.DoTBBlocksRegistry.TATAMI_MAT;

public class TatamiFloorBlock extends NoItemBlock {

	private static final VoxelShape VS = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 17.0D, 16.0D);
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

	public TatamiFloorBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HALF, FACING);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VS;
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		Direction directionOtherHalf = (stateIn.getValue(HALF) == Half.TOP) ? stateIn.getValue(FACING) : stateIn.getValue(FACING).getOpposite();
		if(facing == Direction.UP && worldIn instanceof World) {
			BlockState stateAbove = worldIn.getBlockState(facingPos);
			if (isFaceFull(stateAbove.getShape(worldIn, facingPos), Direction.DOWN) && stateAbove.canOcclude()) {
				InventoryHelper.dropItemStack((World) worldIn, currentPos.getX(), currentPos.getY(), currentPos.getZ(), new ItemStack(TATAMI_MAT.get().asItem()));
				new ItemStack(TATAMI_MAT.get().asItem());
				worldIn.setBlock(currentPos.relative(directionOtherHalf), SPRUCE_PLANKS.defaultBlockState(), 10);
				return SPRUCE_PLANKS.defaultBlockState();
			}
		}
		if(facing == directionOtherHalf){
			if(facingState.getBlock() != this)
				return Blocks.AIR.defaultBlockState();
			else if(facingState.getValue(FACING) != stateIn.getValue(FACING) || facingState.getValue(HALF) == stateIn.getValue(HALF)) return Blocks.AIR.defaultBlockState();
		}
		return stateIn;
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(!worldIn.isClientSide){
			if(player.isCrouching()){
				boolean isTop = state.getValue(HALF) == Half.TOP;
				BlockPos otherPos = (isTop) ? pos.relative(state.getValue(FACING)) : pos.relative(state.getValue(FACING).getOpposite());
				if(isTop)//Check if the blocks above each part are AIR
					if(!worldIn.isEmptyBlock(pos.above()))
						return ActionResultType.PASS;
					else if(!worldIn.isEmptyBlock(otherPos.above()))
						return ActionResultType.PASS;
				worldIn.setBlock(pos, SPRUCE_PLANKS.defaultBlockState(), 2);
				worldIn.setBlock(otherPos, SPRUCE_PLANKS.defaultBlockState(), 2);
				worldIn.setBlock((isTop) ? pos.above() : otherPos.above(), TATAMI_MAT.get().defaultBlockState().setValue(TatamiMatBlock.HALF, Half.TOP).setValue(TatamiMatBlock.FACING, state.getValue(FACING)).setValue(TatamiMatBlock.ROLLED, true), 2);
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.PASS;
	}

	@Override
	public PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.DESTROY;
	}

	@Override
	public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		super.playerWillDestroy(worldIn, pos, state, player);
		BlockPos otherPos = (state.getValue(HALF) == Half.TOP) ? pos.relative(state.getValue(FACING)) : pos.relative(state.getValue(FACING).getOpposite());
		worldIn.setBlock(pos, SPRUCE_PLANKS.defaultBlockState(), 10);
		worldIn.setBlock(otherPos, SPRUCE_PLANKS.defaultBlockState(), 10);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return rotate(state, Rotation.CLOCKWISE_180);
	}
}