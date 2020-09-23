package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;

public class SlabBlockDoTB extends SlabBlock {

	public SlabBlockDoTB(Properties properties) {
		super(properties);
	}

	public SlabBlockDoTB(Material materialIn, float hardness, float resistance) {
		this(Properties.create(materialIn).hardnessAndResistance(hardness, resistance));
	}

	public SlabBlockDoTB(Block block) {
		this(Properties.from(block));
	}

	public Block setBurnable() {
		FireBlock fireblock = (FireBlock) Blocks.FIRE;
		fireblock.setFireInfo(this, 5, 20);
		return this;
	}
}
