package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBBlockFence extends FenceBlock {

    public DoTBBlockFence(String name, Properties properties) {
        super(properties);
        this.setRegistryName(MOD_ID, name);
    }

    public DoTBBlockFence(String name, Material materialIn, float hardness, float resistance) {
        this(name, Block.Properties.create(materialIn).hardnessAndResistance(hardness, resistance));
    }

    public Block setBurnable() {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFireInfo(this, 5, 20);
        return this;
    }
}
