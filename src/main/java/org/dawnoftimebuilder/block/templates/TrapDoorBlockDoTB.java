package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;

public class TrapDoorBlockDoTB extends TrapDoorBlock {

	public TrapDoorBlockDoTB(Properties properties) {
		super(properties);
	}

	public TrapDoorBlockDoTB(Material materialIn, float hardness, float resistance) {
		this(Block.Properties.create(materialIn).hardnessAndResistance(hardness, resistance));
	}

	public Block setBurnable() {
		FireBlock fireblock = (FireBlock) Blocks.FIRE;
		fireblock.setFireInfo(this, 5, 20);
		return this;
	}
}
