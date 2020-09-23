package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

public class PaneBlockDoTB extends PaneBlock {

	private final BlockRenderLayer renderLayer;

	public PaneBlockDoTB(Material materialIn, float hardness, float resistance, BlockRenderLayer renderLayer) {
		this(BlockDoTB.Properties.create(materialIn).hardnessAndResistance(hardness, resistance), renderLayer);
	}

	public PaneBlockDoTB(Material materialIn, float hardness, float resistance) {
		this(BlockDoTB.Properties.create(materialIn).hardnessAndResistance(hardness, resistance), BlockRenderLayer.SOLID);
	}

	public PaneBlockDoTB(Properties properties, BlockRenderLayer renderLayer) {
        super(properties);
		this.renderLayer = renderLayer;
    }

	public Block setBurnable() {
		FireBlock fireblock = (FireBlock) Blocks.FIRE;
		fireblock.setFireInfo(this, 5, 20);
		return this;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return this.renderLayer;
	}
}
