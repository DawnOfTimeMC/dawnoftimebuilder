package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.material.Material;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class BlockDoTB extends Block {

	public BlockDoTB(String name, Properties properties) {
		super(properties);
		this.setRegistryName(MOD_ID, name);
	}

	public BlockDoTB(String name, Material materialIn, float hardness, float resistance) {
		this(name, Properties.create(materialIn).hardnessAndResistance(hardness, resistance));
	}

	public Block setBurnable() {
		FireBlock fireblock = (FireBlock) Blocks.FIRE;
		fireblock.setFireInfo(this, 5, 20);
		return this;
	}
}
