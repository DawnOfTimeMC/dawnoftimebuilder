package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBBlockRotatedPillar extends BlockRotatedPillar {

	public DoTBBlockRotatedPillar(String name, Material materialIn, float hardness, SoundType sound) {
		this(name, materialIn);

		this.setHardness(hardness);
		this.setSoundType(sound);
	}

	public DoTBBlockRotatedPillar(String name, Material materialIn) {
		super(materialIn);

		this.setRegistryName(MOD_ID, name);
		this.setTranslationKey(MOD_ID + "." + name);

		this.setCreativeTab(DOTB_TAB);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.Y));
	}

	public Block setBurnable() {
		Blocks.FIRE.setFireInfo(this, 5, 20);
		return this;
	}
}
