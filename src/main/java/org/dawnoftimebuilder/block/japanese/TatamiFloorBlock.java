package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.IBlockCustomItem;
import org.dawnoftimebuilder.block.templates.BlockDoTB;

import javax.annotation.Nullable;

import static net.minecraft.block.Blocks.SPRUCE_PLANKS;
import static org.dawnoftimebuilder.registries.DoTBBlocksRegistry.*;

public class TatamiFloorBlock extends BlockDoTB implements IBlockCustomItem {

	private VoxelShape VS = makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 17.0D, 16.0D);
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

	public TatamiFloorBlock() {
		super("tatami_floor", Material.WOOD, 2.0F, 2.0F);
		this.setBurnable();
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HALF, FACING);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VS;
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		Direction directionOtherHalf = (stateIn.get(HALF) == Half.TOP) ? stateIn.get(FACING) : stateIn.get(FACING).getOpposite();
		if(facing == Direction.UP) {
			BlockState stateAbove = worldIn.getBlockState(facingPos);
			if (hasSolidSide(stateAbove, worldIn, facingPos, Direction.DOWN) && stateAbove.isSolid()) {
				spawnAsEntity(worldIn.getWorld(), currentPos, new ItemStack(TATAMI_MAT.asItem(), 1));
				worldIn.setBlockState(currentPos.offset(directionOtherHalf), SPRUCE_PLANKS.getDefaultState(), 10);
				return SPRUCE_PLANKS.getDefaultState();
			}
		}
		if(facing == directionOtherHalf){
			if(facingState.getBlock() != this)
				return Blocks.AIR.getDefaultState();
			else if(facingState.get(FACING) != stateIn.get(FACING) || facingState.get(HALF) == stateIn.get(HALF)) return Blocks.AIR.getDefaultState();
		}
		return stateIn;
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(!worldIn.isRemote){
			if(player.isSneaking()){
				boolean isTop = state.get(HALF) == Half.TOP;
				BlockPos otherPos = (isTop) ? pos.offset(state.get(FACING)) : pos.offset(state.get(FACING).getOpposite());
				if(isTop)//Check if the blocks above each part are AIR
					if(!worldIn.isAirBlock(pos.up()))
						return false;
					else if(!worldIn.isAirBlock(otherPos.up()))
						return false;
				worldIn.setBlockState(pos, SPRUCE_PLANKS.getDefaultState(), 2);
				worldIn.setBlockState(otherPos, SPRUCE_PLANKS.getDefaultState(), 2);
				worldIn.setBlockState((isTop) ? pos.up() : otherPos.up(), TATAMI_MAT.getDefaultState().with(TatamiMatBlock.HALF, Half.TOP).with(TatamiMatBlock.FACING, state.get(FACING)).with(TatamiMatBlock.ROLLED, true), 2);
				return true;
			}
		}
		return false;
	}

	@Override
	public PushReaction getPushReaction(BlockState state) {
		return PushReaction.DESTROY;
	}

	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
		super.harvestBlock(worldIn, player, pos, Blocks.AIR.getDefaultState(), te, stack);
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		BlockPos otherPos = (state.get(HALF) == Half.TOP) ? pos.offset(state.get(FACING)) : pos.offset(state.get(FACING).getOpposite());
		BlockState otherState = worldIn.getBlockState(otherPos);
		if (otherState.getBlock() == this && otherState.get(HALF) != state.get(HALF)) {
			worldIn.setBlockState(otherPos, Blocks.AIR.getDefaultState(), 10);
			worldIn.playEvent(player, 2001, otherPos, Block.getStateId(otherState));
			ItemStack itemstack = player.getHeldItemMainhand();
			if (!worldIn.isRemote && !player.isCreative()) {
				Block.spawnDrops(state, worldIn, pos, null, player, itemstack);
				Block.spawnDrops(otherState, worldIn, otherPos, null, player, itemstack);
			}
		}
		super.onBlockHarvested(worldIn, pos, state, player);
	}

	@Nullable
	@Override
	public Item getCustomItemBlock() {
		return null;
	}

	@Override
	public boolean isSolid(BlockState state) {
		return true;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return rotate(state, Rotation.CLOCKWISE_180);
	}
}