package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
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

	public TatamiFloorBlock(Material materialIn, float hardness, float resistance, SoundType soundType) {
		super(Properties.of(materialIn).strength(hardness, resistance).sound(soundType));
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
		Direction directionOtherHalf = (stateIn.get(HALF) == Half.TOP) ? stateIn.get(FACING) : stateIn.get(FACING).getOpposite();
		if(facing == Direction.UP) {
			BlockState stateAbove = worldIn.getBlockState(facingPos);
			if (hasSolidSide(stateAbove, worldIn, facingPos, Direction.DOWN) && stateAbove.isSolid()) {
				spawnAsEntity(worldIn.getLevel(), currentPos, new ItemStack(TATAMI_MAT.asItem(), 1));
				worldIn.setBlock(currentPos.relative(directionOtherHalf), SPRUCE_PLANKS.defaultBlockState(), 10);
				return SPRUCE_PLANKS.defaultBlockState();
			}
		}
		if(facing == directionOtherHalf){
			if(facingState.getBlock() != this)
				return Blocks.AIR.defaultBlockState();
			else if(facingState.get(FACING) != stateIn.get(FACING) || facingState.get(HALF) == stateIn.get(HALF)) return Blocks.AIR.defaultBlockState();
		}
		return stateIn;
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(!worldIn.isClientSide){
			if(player.isCrouching()){
				boolean isTop = state.get(HALF) == Half.TOP;
				BlockPos otherPos = (isTop) ? pos.relative(state.get(FACING)) : pos.relative(state.get(FACING).getOpposite());
				if(isTop)//Check if the blocks above each part are AIR
					if(!worldIn.isAirBlock(pos.above()))
						return false;
					else if(!worldIn.isAirBlock(otherPos.above()))
						return false;
				worldIn.setBlock(pos, SPRUCE_PLANKS.defaultBlockState(), 2);
				worldIn.setBlock(otherPos, SPRUCE_PLANKS.defaultBlockState(), 2);
				worldIn.setBlock((isTop) ? pos.above() : otherPos.above(), TATAMI_MAT.defaultBlockState().setValue(TatamiMatBlock.HALF, Half.TOP).setValue(TatamiMatBlock.FACING, state.get(FACING)).setValue(TatamiMatBlock.ROLLED, true), 2);
				return true;
			}
		}
		return false;
	}

	@Override
	public PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.DESTROY;
	}

	@Override
	public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
		super.onPlayerDestroy(worldIn, pos, state);
		BlockPos otherPos = (state.get(HALF) == Half.TOP) ? pos.relative(state.get(FACING)) : pos.relative(state.get(FACING).getOpposite());
		worldIn.setBlock(pos, SPRUCE_PLANKS.defaultBlockState(), 10);
		worldIn.setBlock(otherPos, SPRUCE_PLANKS.defaultBlockState(), 10);
	}

	@Override
	public boolean isSolid(BlockState state) {
		return true;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return rotate(state, Rotation.CLOCKWISE_180);
	}
}