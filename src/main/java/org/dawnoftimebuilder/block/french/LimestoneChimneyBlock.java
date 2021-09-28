package org.dawnoftimebuilder.block.french;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
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
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import java.util.Random;

public class LimestoneChimneyBlock extends ColumnConnectibleBlock {
	public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
	private static final VoxelShape[] SHAPES = makeShapes();

	public LimestoneChimneyBlock(Material materialIn, float hardness, float resistance, SoundType soundType) {
		super(materialIn, hardness, resistance, soundType);
		this.registerDefaultState(this.defaultBlockState().setValue(HORIZONTAL_AXIS, Direction.Axis.X));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HORIZONTAL_AXIS);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if(state.getValue(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.NONE) return SHAPES[0];
		return SHAPES[state.getValue(VERTICAL_CONNECTION).getIndex() - 1];
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState state = super.getStateForPlacement(context);
		return state.setValue(HORIZONTAL_AXIS, (context.getHorizontalDirection().getAxis() == Direction.Axis.X)? Direction.Axis.Z : Direction.Axis.X);
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
						Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D),
						Block.box(4.0D, 8.0D, 4.0D, 12.0D, 16.0D, 12.0D)
				),
				VoxelShapes.or(
						Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
						Block.box(2.0D, 8.0D, 2.0D, 14.0D, 16.0D, 14.0D)
				),
				Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D)
		};
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if(stateIn.getValue(WATERLOGGED)) return;
		if(stateIn.getValue(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.UNDER || stateIn.getValue(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.NONE) {
			worldIn.addOptionalParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, true, (double) pos.getX() + rand.nextDouble() * 0.5D + 0.25D, (double) pos.getY() + rand.nextDouble() * 0.5D + 0.3D, (double) pos.getZ() + rand.nextDouble() * 0.5D + 0.25D, 0.0D, 0.07D, 0.0D);
			worldIn.addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, (double) pos.getX() + rand.nextDouble() * 0.5D + 0.25D, (double) pos.getY() + rand.nextDouble() * 0.5D + 0.3D, (double) pos.getZ() + rand.nextDouble() * 0.5D + 0.25D, 0.0D, 0.04D, 0.0D);
		}
	}
}
