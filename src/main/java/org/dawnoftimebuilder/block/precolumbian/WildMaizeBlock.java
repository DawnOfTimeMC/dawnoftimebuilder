package org.dawnoftimebuilder.block.precolumbian;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.templates.WildPlantBlock;

import javax.annotation.Nullable;

public class WildMaizeBlock extends WildPlantBlock {

    private static final VoxelShape VS = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
	public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    public WildMaizeBlock(Properties properties) {
		super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(HALF, Half.BOTTOM));
    }

    @Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		Vec3d vec3d = state.getOffset(worldIn, pos);
		return VS.withOffset(vec3d.x, vec3d.y, vec3d.z);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HALF);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
    	if(state.get(HALF) == Half.TOP) return true;
		return super.canSurvive(state, worldIn, pos);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		if(!context.getLevel().getBlockState(context.getClickedPos().above()).isReplaceable(context)) return null;
		return super.getStateForPlacement(context);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		worldIn.setBlock(pos.above(), state.setValue(HALF, Half.TOP), 10);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if(facing.getAxis().isHorizontal()) return stateIn;
		if(facing == Direction.UP && stateIn.get(HALF) == Half.BOTTOM) {
			if(facingState.getBlock() == this){
				if(facingState.get(HALF) == Half.TOP){
					return stateIn;
				}
			}
			return Blocks.AIR.defaultBlockState();
		}
		if(facing == Direction.DOWN) {
			if(stateIn.get(HALF) == Half.TOP){
				if(facingState.getBlock() == this){
					if(facingState.get(HALF) == Half.BOTTOM){
						return stateIn;
					}
				}
			}else if(canSurvive(stateIn, worldIn, currentPos)) return stateIn;
			return Blocks.AIR.defaultBlockState();
		}
    	return stateIn;
	}

    public BlockState getDefaultTopState() {
    	return this.defaultBlockState().setValue(HALF, Half.TOP);
    }
}