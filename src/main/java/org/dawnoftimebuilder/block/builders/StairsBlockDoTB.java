package org.dawnoftimebuilder.block.builders;

import net.minecraft.block.*;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class StairsBlockDoTB extends StairsBlock {

    public StairsBlockDoTB(String name, Block block){
        super(block.getDefaultState(), Block.Properties.from(block));

        this.setRegistryName(MOD_ID, name);
    }

    public Block setBurnable() {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFireInfo(this, 5, 20);
        return this;
    }
}
