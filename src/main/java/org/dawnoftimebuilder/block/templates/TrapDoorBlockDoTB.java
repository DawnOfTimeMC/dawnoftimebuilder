package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class TrapDoorBlockDoTB extends TrapDoorBlock {

	public TrapDoorBlockDoTB(String name, Properties properties) {
		super(properties);
		this.setRegistryName(MOD_ID, name);
	}

	public TrapDoorBlockDoTB(String name, Material materialIn, float hardness, float resistance) {
		this(name, Block.Properties.create(materialIn).hardnessAndResistance(hardness, resistance));
	}

	public Block setBurnable() {
		FireBlock fireblock = (FireBlock) Blocks.FIRE;
		fireblock.setFireInfo(this, 5, 20);
		return this;
	}
}
