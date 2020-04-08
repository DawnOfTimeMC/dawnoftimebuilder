package org.dawnoftimebuilder.block.builders;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class WallBlockDoTB extends WallBlock {

    public WallBlockDoTB(String name, Properties properties) {
        super(properties);
        this.setRegistryName(MOD_ID, name);
    }

    public WallBlockDoTB(String name, Material materialIn, float hardness, float resistance) {
        this(name, BlockDoTB.Properties.create(materialIn).hardnessAndResistance(hardness, resistance));
    }

    public Block setBurnable() {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFireInfo(this, 5, 20);
        return this;
    }
}