package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
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
import net.minecraft.world.World;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nonnull;
import java.util.Random;

public class PergolaBlock extends BeamBlock {

	private static final VoxelShape[] SHAPES = makeShapes();

	public PergolaBlock(Properties properties) {
		super(properties);
	}

	/**
	 * @return Stores VoxelShape with index : <p/>
	 * 0 : Axis X <p/>
	 * 1 : Axis Z <p/>
	 * 2 : Axis X + Z <p/>
	 * 3 : Axis Y <p/>
	 * 4 : Axis Y + Bottom <p/>
	 * 5 : Axis Y + X <p/>
	 * 6 : Axis Y + X + Bottom <p/>
	 * 7 : Axis Y + Z <p/>
	 * 8 : Axis Y + Z + Bottom <p/>
	 * 9 : Axis Y + X + Z <p/>
	 * 10 : Axis Y + X + Z + Bottom
	 */
	private static VoxelShape[] makeShapes() {
		VoxelShape vs_axis_x = Block.box(0.0D, 5.0D, 6.0D, 16.0D, 11.0D, 10.0D);
		VoxelShape vs_axis_z = Block.box(6.0D, 5.0D, 0.0D, 10.0D, 11.0D, 16.0D);
		VoxelShape vs_axis_x_z = VoxelShapes.or(vs_axis_x, vs_axis_z);
		VoxelShape vs_axis_y = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
		VoxelShape vs_axis_y_bottom = VoxelShapes.or(vs_axis_y, Block.box(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D));
		return new VoxelShape[]{
				vs_axis_x,
				vs_axis_z,
				vs_axis_x_z,
				vs_axis_y,
				vs_axis_y_bottom,
				VoxelShapes.or(vs_axis_y, vs_axis_x),
				VoxelShapes.or(vs_axis_y_bottom, vs_axis_x),
				VoxelShapes.or(vs_axis_y, vs_axis_z),
				VoxelShapes.or(vs_axis_y_bottom, vs_axis_z),
				VoxelShapes.or(vs_axis_y, vs_axis_x_z),
				VoxelShapes.or(vs_axis_y_bottom, vs_axis_x_z)
		};
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPES[getShapeIndex(state)];
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(!state.getValue(PERSISTENT)){
			if(DoTBUtils.useLighter(worldIn, pos, player, handIn)){
				Random rand = new Random();
				for(int i = 0; i < 5; i++){
					worldIn.addAlwaysVisibleParticle(ParticleTypes.SMOKE, (double)pos.getX() + rand.nextDouble(), (double)pos.getY() + 0.5D + rand.nextDouble() / 2, (double)pos.getZ() + rand.nextDouble(), 0.0D, 0.07D, 0.0D);
				}
				worldIn.setBlock(pos, state.setValue(PERSISTENT, true), 10);
				return ActionResultType.SUCCESS;
			}
		}
		if(player.isCreative()){
			if(this.tryPlacingPlant(state, worldIn, pos, player, handIn)){
				return ActionResultType.SUCCESS;
			}
		}
		if(this.harvestPlant(state, worldIn, pos, player, handIn) == ActionResultType.SUCCESS){
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		if(facing == Direction.DOWN){
			stateIn.setValue(BOTTOM, canNotConnectUnder(facingState));
		}
		return stateIn;
	}

	@Override
	public boolean canHavePlant(BlockState state) {
		return !state.getValue(WATERLOGGED);
	}

	@Nonnull
	@Override
	public DoTBBlockStateProperties.PillarConnection getBlockPillarConnectionAbove(BlockState state) {
		return state.getValue(AXIS_Y) ? DoTBBlockStateProperties.PillarConnection.SIX_PX : DoTBBlockStateProperties.PillarConnection.NOTHING;
	}
}
