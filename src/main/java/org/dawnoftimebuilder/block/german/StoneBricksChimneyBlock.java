package org.dawnoftimebuilder.block.german;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.particles.ParticleTypes;
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

public class StoneBricksChimneyBlock extends ColumnConnectibleBlock {
	private static final VoxelShape[] SHAPES = makeShapes();

	public StoneBricksChimneyBlock(Material materialIn, float hardness, float resistance, SoundType soundType) {
		super(Properties.of(materialIn).strength(hardness, resistance).sound(soundType));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if(state.get(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.NONE) return SHAPES[0];
		return SHAPES[state.get(VERTICAL_CONNECTION).getIndex() - 1];
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
						Block.box(1.0D, 8.0D, 1.0D, 15.0D, 11.0D, 15.0D)
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
		if(stateIn.get(WATERLOGGED)) return;
		if(stateIn.get(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.UNDER || stateIn.get(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.NONE) {
			worldIn.addOptionalParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, true, (double) pos.getX() + rand.nextDouble() * 0.5D + 0.25D, (double) pos.getY() + rand.nextDouble() * 0.5D + 0.3D, (double) pos.getZ() + rand.nextDouble() * 0.5D + 0.25D, 0.0D, 0.07D, 0.0D);
			worldIn.addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, (double) pos.getX() + rand.nextDouble() * 0.5D + 0.25D, (double) pos.getY() + rand.nextDouble() * 0.5D + 0.3D, (double) pos.getZ() + rand.nextDouble() * 0.5D + 0.25D, 0.0D, 0.04D, 0.0D);
		}
	}
}
