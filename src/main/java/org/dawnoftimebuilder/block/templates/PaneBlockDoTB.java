package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class PaneBlockDoTB extends PaneBlock {

	private final BlockRenderLayer renderLayer;

	public PaneBlockDoTB(String name, Material materialIn, float hardness, float resistance, BlockRenderLayer renderLayer) {
		this(name, BlockDoTB.Properties.create(materialIn).hardnessAndResistance(hardness, resistance), renderLayer);
	}

	public PaneBlockDoTB(String name, Material materialIn, float hardness, float resistance) {
		this(name, BlockDoTB.Properties.create(materialIn).hardnessAndResistance(hardness, resistance), BlockRenderLayer.SOLID);
	}

	public PaneBlockDoTB(String name, Properties properties, BlockRenderLayer renderLayer) {
        super(properties);
		this.renderLayer = renderLayer;
		this.setRegistryName(MOD_ID, name);
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
