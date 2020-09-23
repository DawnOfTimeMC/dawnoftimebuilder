package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;

public class FenceBlockDoTB extends FenceBlock {

    public FenceBlockDoTB(Properties properties) {
        super(properties);
    }

    public FenceBlockDoTB(Material materialIn, float hardness, float resistance) {
        this(BlockDoTB.Properties.create(materialIn).hardnessAndResistance(hardness, resistance));
    }

    public Block setBurnable() {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFireInfo(this, 5, 20);
        return this;
    }
}
