package org.dawnoftimebuilder.block.precolumbian;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.IBlockGeneration;
import org.dawnoftimebuilder.block.templates.WildPlantBlock;

import javax.annotation.Nullable;

public class WildMaizeBlock extends WildPlantBlock implements IBlockGeneration {

    private static final VoxelShape VS = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
	public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    public WildMaizeBlock(Properties properties) {
		super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(HALF, Half.BOTTOM));
    }

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		Vec3 vec3d = state.getOffset(worldIn, pos);
		return VS.move(vec3d.x, vec3d.y, vec3d.z);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(HALF);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
    	if(state.getValue(HALF) == Half.TOP) return true;
		return super.canSurvive(state, worldIn, pos);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		if(!context.getLevel().getBlockState(context.getClickedPos().above()).canBeReplaced(context)) return null;
		return super.getStateForPlacement(context);
	}

	@Override
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		worldIn.setBlock(pos.above(), state.setValue(HALF, Half.TOP), 10);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		if(facing.getAxis().isHorizontal()) return stateIn;

		if(facing == Direction.UP && stateIn.getValue(HALF) == Half.BOTTOM) {
			if(facingState.getBlock() == this){
				if(facingState.getValue(HALF) == Half.TOP){
					return stateIn;
				}
			}
			return Blocks.AIR.defaultBlockState();
		}

		if(facing == Direction.DOWN) {
			if(stateIn.getValue(HALF) == Half.TOP){
				if(facingState.getBlock() == this){
					if(facingState.getValue(HALF) == Half.BOTTOM){
						return stateIn;
					}
				}
			}else if(canSurvive(stateIn, worldIn, currentPos)) return stateIn;
			return Blocks.AIR.defaultBlockState();
		}

    	return stateIn;
	}

	@Override
	public void generateOnPos(LevelAccessor world, BlockPos pos, BlockState state, RandomSource random) {
		if(world.getBlockState(pos.above()).getMaterial() != Material.AIR)
			return;
		world.setBlock(pos, state, 2);
		world.setBlock(pos.above(), state.setValue(HALF, Half.TOP), 2);
	}
}