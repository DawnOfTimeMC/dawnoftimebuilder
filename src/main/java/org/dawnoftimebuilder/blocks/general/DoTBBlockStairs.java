package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.*;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBBlockStairs extends StairsBlock {

    public DoTBBlockStairs(String name, Block block){
        super(block.getDefaultState(), Block.Properties.from(block));

        this.setRegistryName(MOD_ID, name);
    }

    public Block setBurnable() {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFireInfo(this, 5, 20);
        return this;
    }
}
