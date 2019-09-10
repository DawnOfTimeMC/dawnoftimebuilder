package org.dawnoftimebuilder.blocks.global;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBBlockFence extends BlockFence {

    public DoTBBlockFence(String name, Material materialIn, float hardness, SoundType sound) {
        this(name, materialIn);

        this.setHardness(hardness);
        this.setSoundType(sound);
    }

    public DoTBBlockFence(String name, Material materialIn) {
        super(materialIn, materialIn.getMaterialMapColor());

        this.setRegistryName(MOD_ID, name);
        this.setTranslationKey(MOD_ID + "." + name);

        this.setCreativeTab(DOTB_TAB);
    }

    public Block setBurnable() {
        Blocks.FIRE.setFireInfo(this, 5, 20);
        return this;
    }
}
