package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class StairsBlockDoTB extends StairsBlock {

    public StairsBlockDoTB(Block block){
        super(block.getDefaultState(), Block.Properties.from(block));
    }

    public Block setBurnable() {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFireInfo(this, 5, 20);
        return this;
    }
}
