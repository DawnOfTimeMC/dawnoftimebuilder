package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;

public class RotatedPillarBlockDoTB extends RotatedPillarBlock {

	public RotatedPillarBlockDoTB(Properties properties) {
		super(properties);
	}

	public RotatedPillarBlockDoTB(Material materialIn, float hardness, float resistance) {
		this(Properties.create(materialIn).hardnessAndResistance(hardness, resistance));
	}

	public Block setBurnable() {
		FireBlock fireblock = (FireBlock) Blocks.FIRE;
		fireblock.setFireInfo(this, 5, 20);
		return this;
	}
}
