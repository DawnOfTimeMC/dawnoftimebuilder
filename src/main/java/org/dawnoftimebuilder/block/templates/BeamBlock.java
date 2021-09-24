package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.IBlockClimbingPlant;
import org.dawnoftimebuilder.block.IBlockPillar;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static org.dawnoftimebuilder.util.DoTBBlockUtils.TOOLTIP_CLIMBING_PLANT;

public class BeamBlock extends WaterloggedBlock implements IBlockPillar, IBlockClimbingPlant {

	public static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;
	public static final BooleanProperty AXIS_X = DoTBBlockStateProperties.AXIS_X;
	public static final BooleanProperty AXIS_Y = DoTBBlockStateProperties.AXIS_Y;
	public static final BooleanProperty AXIS_Z = DoTBBlockStateProperties.AXIS_Z;
	public static final EnumProperty<DoTBBlockStateProperties.ClimbingPlant> CLIMBING_PLANT = DoTBBlockStateProperties.CLIMBING_PLANT;
	private static final IntegerProperty AGE = DoTBBlockStateProperties.AGE_0_6;
	public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
	private static final VoxelShape[] SHAPES = makeShapes();

	public BeamBlock(Material materialIn, float hardness, float resistance, SoundType soundType) {
		super(materialIn, hardness, resistance, soundType);
		this.setDefaultState(this.getStateContainer().getBaseState().with(BOTTOM, false).with(AXIS_Y, false).with(AXIS_X, false).with(AXIS_Z, false).with(CLIMBING_PLANT, DoTBBlockStateProperties.ClimbingPlant.NONE).with(AGE, 0).with(WATERLOGGED, false).with(PERSISTENT, false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(BOTTOM, AXIS_Y, AXIS_X, AXIS_Z, CLIMBING_PLANT, AGE, PERSISTENT);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPES[getShapeIndex(state)];
	}

	protected int getShapeIndex(BlockState state){
		if (state.get(AXIS_Y)){
			int index = 3;
			if (state.get(BOTTOM)) index += 1;
			if (state.get(AXIS_X)) index += 2;
			if (state.get(AXIS_Z)) index += 4;
			return index;
		}else{
			int index = state.get(AXIS_Z) ? 1 : 0;
			return state.get(AXIS_X) ? index * 2 : index;
		}
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
		VoxelShape vs_axis_x = net.minecraft.block.Block.Block.box(0.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);
		VoxelShape vs_axis_z = net.minecraft.block.Block.Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D);
		VoxelShape vs_axis_x_z = VoxelShapes.or(vs_axis_x, vs_axis_z);
		VoxelShape vs_axis_y = net.minecraft.block.Block.Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
		VoxelShape vs_axis_y_bottom = VoxelShapes.or(vs_axis_y, net.minecraft.block.Block.Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D));
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
		BlockState state = context.getLevel().getBlockState(context.getPos());
		if (state.getBlock() != this)
			state = super.getStateForPlacement(context);
		switch (context.getFace().getAxis()) {
			case X:
				state = state.with(AXIS_X, true);
				break;
			case Y:
				state = state.with(AXIS_Y, true);
				break;
			case Z:
				state = state.with(AXIS_Z, true);
				break;
		}
		return this.getCurrentState(state, context.getLevel(), context.getPos());
	}

	public BlockState getCurrentState(BlockState stateIn, IWorld worldIn, BlockPos pos){
		return stateIn.get(AXIS_Y) ? stateIn.with(BOTTOM, !canConnectUnder(worldIn.getBlockState(pos.down()))) : stateIn;
	}

	@Override
	public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
		ItemStack itemstack = useContext.getItem();
		if(useContext.isPlacerSneaking()) return false;
		if (itemstack.getItem() == this.asItem()) {
			if (useContext.replacingClickedOnBlock()) {
				switch (useContext.getFace().getAxis()) {
					case X:
						return !state.get(AXIS_X);
					case Y:
						return !state.get(AXIS_Y);
					case Z:
						return !state.get(AXIS_Z);
				}
			}
		}
		return false;
	}

	@Override
	public void spawnAdditionalDrops(BlockState state, World worldIn, BlockPos pos, ItemStack stack) {
		super.spawnAdditionalDrops(state, worldIn, pos, stack);
		//Be careful, climbing plants are not dropping from block's loot_table, but from their own loot_table
		this.dropPlant(state, worldIn, pos, stack);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		stateIn = this.getCurrentState(stateIn, worldIn, currentPos);
		if(!this.canHavePlant(stateIn) && !stateIn.get(CLIMBING_PLANT).hasNoPlant()){
			return this.removePlant(stateIn, worldIn.getLevel(), currentPos, ItemStack.EMPTY);
		}
		return stateIn;
	}

	public boolean canConnectUnder(BlockState state) {
		if (state.getBlock() instanceof BeamBlock)
			return state.get(AXIS_Y);
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
		if(!state.get(PERSISTENT)){
			if(DoTBBlockUtils.useLighter(worldIn, pos, player, handIn)){
				Random rand = new Random();
				for(int i = 0; i < 5; i++){
					worldIn.addOptionalParticle(ParticleTypes.SMOKE, (double)pos.getX() + rand.nextDouble(), (double)pos.getY() + 0.5D + rand.nextDouble() / 2, (double)pos.getZ() + rand.nextDouble(), 0.0D, 0.07D, 0.0D);
				}
				worldIn.setBlockState(pos, state.with(PERSISTENT, true), 10);
				return true;
			}
		}
		if(player.isCreative()){
			if(this.tryPlacingPlant(state, worldIn, pos, player, handIn)) return true;
		}
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
	public DoTBBlockStateProperties.PillarConnection getBlockPillarConnectionAbove(BlockState state) {
		return state.get(AXIS_Y) ? DoTBBlockStateProperties.PillarConnection.TEN_PX : DoTBBlockStateProperties.PillarConnection.NOTHING;
	}

	@Override
	public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity) {
		return !state.get(CLIMBING_PLANT).hasNoPlant();
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		DoTBBlockUtils.addTooltip(tooltip, TOOLTIP_CLIMBING_PLANT);
	}
}
