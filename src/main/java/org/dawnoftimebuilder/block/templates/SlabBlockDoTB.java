package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class SlabBlockDoTB extends SlabBlock {

	public SlabBlockDoTB(String name, Properties properties) {
		super(properties);
		this.setRegistryName(MOD_ID, name);
	}

	public SlabBlockDoTB(String name, Material materialIn, float hardness, float resistance) {
		this(name, Properties.create(materialIn).hardnessAndResistance(hardness, resistance));
	}

	public SlabBlockDoTB(String name, Block block) {
		this(name, Properties.from(block));
	}

	public Block setBurnable() {
		FireBlock fireblock = (FireBlock) Blocks.FIRE;
		fireblock.setFireInfo(this, 5, 20);
		return this;
	}
}
