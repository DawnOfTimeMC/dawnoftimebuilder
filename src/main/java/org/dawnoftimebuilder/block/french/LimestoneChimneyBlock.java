package org.dawnoftimebuilder.block.french;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.block.templates.ColumnConnectibleBlock;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;

import java.util.Random;

public class LimestoneChimneyBlock extends ColumnConnectibleBlock {
	public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
	private static final VoxelShape[] SHAPES = makeShapes();

	public LimestoneChimneyBlock() {
		super(Material.ROCK, 1.5F, 6.0F);
		this.setDefaultState(this.getStateContainer().getBaseState().with(HORIZONTAL_AXIS, Direction.Axis.X));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(HORIZONTAL_AXIS);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if(state.get(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.NONE) return SHAPES[0];
		return SHAPES[state.get(VERTICAL_CONNECTION).getIndex() - 1];
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState state = super.getStateForPlacement(context);
		return state.with(HORIZONTAL_AXIS, (context.getPlacementHorizontalFacing().getAxis() == Direction.Axis.X)? Direction.Axis.Z : Direction.Axis.X);
	}

	/**
	 * @return Stores VoxelShape with index : <p/>
	 * 0 : None or Under <p/>
	 * 1 : Above
	 * 2 : Both
	 */
	private static VoxelShape[] makeShapes() {
		return new VoxelShape[]{
				VoxelShapes.or(
						makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D),
						makeCuboidShape(4.0D, 8.0D, 4.0D, 12.0D, 16.0D, 12.0D)
				),
				VoxelShapes.or(
						makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
						makeCuboidShape(2.0D, 8.0D, 2.0D, 14.0D, 16.0D, 14.0D)
				),
				makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D)
		};
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if(stateIn.get(WATERLOGGED)) return;
		if(stateIn.get(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.UNDER || stateIn.get(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.NONE) {
			worldIn.addOptionalParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, true, (double) pos.getX() + rand.nextDouble() * 0.5D + 0.25D, (double) pos.getY() + rand.nextDouble() * 0.5D + 0.3D, (double) pos.getZ() + rand.nextDouble() * 0.5D + 0.25D, 0.0D, 0.07D, 0.0D);
			worldIn.addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, (double) pos.getX() + rand.nextDouble() * 0.5D + 0.25D, (double) pos.getY() + rand.nextDouble() * 0.5D + 0.3D, (double) pos.getZ() + rand.nextDouble() * 0.5D + 0.25D, 0.0D, 0.04D, 0.0D);
		}
	}
}
