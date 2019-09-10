package org.dawnoftimebuilder.blocks.vanilla;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public abstract class SlabWrapperBlock extends BlockSlab {

    private String name;

    @Deprecated
    public SlabWrapperBlock(String name, Material material, boolean addToTab) {
        super(material);

        this.name = name;
        this.setRegistryName(MOD_ID, name);
        this.setTranslationKey(MOD_ID + "." + name);

        if(addToTab){
            this.setCreativeTab(DOTB_TAB);
        }
    }

}
