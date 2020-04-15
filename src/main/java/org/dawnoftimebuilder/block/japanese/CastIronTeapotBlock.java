package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.IBlockCustomItem;
import org.dawnoftimebuilder.block.IBlockSpecialDisplay;
import org.dawnoftimebuilder.block.builders.WaterloggedBlock;

public class CastIronTeapotBlock extends WaterloggedBlock implements IBlockSpecialDisplay {

	private static final VoxelShape VS = makeCuboidShape(4.8D, 0.0D, 4.8D, 11.2D, 6.4D, 11.2D);
	private final float renderYOffset;

	public CastIronTeapotBlock(String name, float renderYOffset) {
		super(name, Material.IRON, 0.8F, 0.8F);
		this.renderYOffset = renderYOffset;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VS;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	public float getRenderYOffset() {
		return this.renderYOffset;
	}
}