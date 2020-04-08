package org.dawnoftimebuilder.block.builders;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class FenceBlockDoTB extends FenceBlock {

    public FenceBlockDoTB(String name, Properties properties) {
        super(properties);
        this.setRegistryName(MOD_ID, name);
    }

    public FenceBlockDoTB(String name, Material materialIn, float hardness, float resistance) {
        this(name, BlockDoTB.Properties.create(materialIn).hardnessAndResistance(hardness, resistance));
    }

    public Block setBurnable() {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFireInfo(this, 5, 20);
        return this;
    }
}
