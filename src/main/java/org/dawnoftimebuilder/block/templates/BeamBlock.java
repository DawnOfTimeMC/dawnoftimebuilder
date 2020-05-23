package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
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
import org.dawnoftimebuilder.block.IBlockClimbingPlant;
import org.dawnoftimebuilder.block.IBlockPillar;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;

import javax.annotation.Nonnull;
import java.util.Random;

public class BeamBlock extends WaterloggedBlock implements IBlockPillar, IBlockClimbingPlant {

	public static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;
	public static final EnumProperty<Direction.Axis> MAIN_AXIS = BlockStateProperties.AXIS;
	private static final BooleanProperty SUBAXIS_X = DoTBBlockStateProperties.SUBAXIS_X;
	private static final BooleanProperty SUBAXIS_Z = DoTBBlockStateProperties.SUBAXIS_Z;
	public static final EnumProperty<DoTBBlockStateProperties.ClimbingPlant> CLIMBING_PLANT = DoTBBlockStateProperties.CLIMBING_PLANT;
	private static final IntegerProperty AGE = DoTBBlockStateProperties.AGE_0_6;
	private static final VoxelShape[] SHAPES = makeShapes();

	public BeamBlock(String name, Material materialIn, float hardness, float resistance) {
		super(name, materialIn, hardness, resistance);
		this.setDefaultState(this.getStateContainer().getBaseState().with(BOTTOM, false).with(MAIN_AXIS, Direction.Axis.Y).with(SUBAXIS_X, false).with(SUBAXIS_Z, false).with(CLIMBING_PLANT, DoTBBlockStateProperties.ClimbingPlant.NONE).with(AGE, 0).with(WATERLOGGED, false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(BOTTOM, MAIN_AXIS, SUBAXIS_X, SUBAXIS_Z, CLIMBING_PLANT, AGE);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPES[getShapeIndex(state)];
	}

	protected int getShapeIndex(BlockState state){
		switch (state.get(MAIN_AXIS)) {
			case Z:
				return state.get(SUBAXIS_X) ? 2 : 1;
			case X:
				return state.get(SUBAXIS_Z) ? 2 : 0;
			default:
				break;
		}
		int index = 3;
		if (state.get(BOTTOM)) index += 1;
		if (state.get(SUBAXIS_X)) index += 2;
		if (state.get(SUBAXIS_Z)) index += 4;
		return index;
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
		VoxelShape vs_axis_x = net.minecraft.block.Block.makeCuboidShape(0.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);
		VoxelShape vs_axis_z = net.minecraft.block.Block.makeCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D);
		VoxelShape vs_axis_x_z = VoxelShapes.or(vs_axis_x, vs_axis_z);
		VoxelShape vs_axis_y = net.minecraft.block.Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
		VoxelShape vs_axis_y_bottom = VoxelShapes.or(vs_axis_y, net.minecraft.block.Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D));
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
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState state = super.getStateForPlacement(context);
		return this.getCurrentState(state.with(MAIN_AXIS, context.getFace().getAxis()), context.getWorld(), context.getPos());
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		stateIn = this.getCurrentState(stateIn, worldIn, currentPos);
		if(!this.canHavePlant(stateIn) && !stateIn.get(CLIMBING_PLANT).hasNoPlant()){
			return this.removePlant(stateIn, worldIn.getWorld(), currentPos, ItemStack.EMPTY);
		}
		return stateIn;
	}

	public BlockState getCurrentState(BlockState stateIn, IWorld worldIn, BlockPos pos){
		boolean subAxisX = false;
		boolean subAxisZ = false;
		if (canConnect(worldIn, pos, Direction.EAST) || canConnect(worldIn, pos, Direction.WEST)) subAxisX = true;
		if (canConnect(worldIn, pos, Direction.NORTH) || canConnect(worldIn, pos, Direction.SOUTH)) subAxisZ = true;
		if (stateIn.get(MAIN_AXIS).isHorizontal()){
			if(stateIn.get(MAIN_AXIS) == Direction.Axis.X) return stateIn.with(SUBAXIS_X, false).with(SUBAXIS_Z, subAxisZ);
			else return stateIn.with(SUBAXIS_X, subAxisX).with(SUBAXIS_Z, false);
		} else {
			boolean bottom = false;
			if (!isConnectibleBeam(worldIn.getBlockState(pos.down()), Direction.DOWN)) bottom = true;
			return stateIn.with(BOTTOM, bottom).with(SUBAXIS_X, subAxisX).with(SUBAXIS_Z, subAxisZ);
		}
	}

	private boolean canConnect(IWorld world, BlockPos pos, Direction direction) {
		BlockState state = world.getBlockState(pos.offset(direction));
		return isConnectibleBeam(state, direction) || isConnectibleSupportBeam(state, direction);
	}

	private boolean isConnectibleBeam(BlockState state, Direction direction) {
		if (state.getBlock() instanceof BeamBlock)
			return state.get(MAIN_AXIS) == direction.getAxis();
		else return false;
	}

	private boolean isConnectibleSupportBeam(BlockState state, Direction direction) {
		if (state.getBlock() instanceof SupportBeamBlock)
			return state.get(SupportBeamBlock.HORIZONTAL_AXIS) == direction.getAxis();
		else return false;
	}

	@Override
	public boolean ticksRandomly(BlockState state) {
		return !state.get(CLIMBING_PLANT).hasNoPlant();
	}

	@Override
	public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
		this.tickPlant(state, worldIn, pos, random);
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
		return this.harvestPlant(state, worldIn, pos, player, handIn);
	}

	@Override
	public boolean canHavePlant(BlockState state) {
		return !state.get(WATERLOGGED) && !state.get(BOTTOM);
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Nonnull
	@Override
	public DoTBBlockStateProperties.PillarConnection getBlockPillarConnection(BlockState state) {
		return (state.get(MAIN_AXIS) == Direction.Axis.Y) ? DoTBBlockStateProperties.PillarConnection.TEN_PX : DoTBBlockStateProperties.PillarConnection.NOTHING;
	}

	@Override
	public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity) {
		return !state.get(CLIMBING_PLANT).hasNoPlant();
	}
}
