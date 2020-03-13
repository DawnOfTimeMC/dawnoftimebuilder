package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBBlockPane extends PaneBlock {

	private BlockRenderLayer renderLayer;

	public DoTBBlockPane(String name, Material materialIn, float hardness, float resistance, BlockRenderLayer renderLayer) {
		this(name, Block.Properties.create(materialIn).hardnessAndResistance(hardness, resistance), renderLayer);
	}

	public DoTBBlockPane(String name, Material materialIn, float hardness, float resistance) {
		this(name, Block.Properties.create(materialIn).hardnessAndResistance(hardness, resistance), BlockRenderLayer.SOLID);
	}

	public DoTBBlockPane(String name, Properties properties, BlockRenderLayer renderLayer) {
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
