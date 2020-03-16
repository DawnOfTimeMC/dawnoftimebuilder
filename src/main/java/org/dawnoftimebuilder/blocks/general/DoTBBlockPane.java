package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBBlockPane extends BlockPane {

	public DoTBBlockPane(String name, Material materialIn, float hardness, SoundType sound) {
		this(name, materialIn, hardness);
		this.setSoundType(sound);
	}

	public DoTBBlockPane(String name, Material materialIn){
		this(name, materialIn, 2.0F);
	}

    public DoTBBlockPane(String name, Material materialIn, float hardness) {
        super(materialIn, true);
		this.setHardness(hardness);
		this.setRegistryName(MOD_ID, name);
		this.setTranslationKey(MOD_ID + "." + name);
		this.setCreativeTab(DOTB_TAB);
    }

	public Block setBurnable() {
		Blocks.FIRE.setFireInfo(this, 5, 20);
		return this;
	}
}
