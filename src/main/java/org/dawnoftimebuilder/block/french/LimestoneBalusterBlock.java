package org.dawnoftimebuilder.block.french;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import org.dawnoftimebuilder.block.templates.PlateBlock;

public class LimestoneBalusterBlock extends PlateBlock {
    public LimestoneBalusterBlock(Material materialIn, float hardness, float resistance, SoundType soundType) {
        super(materialIn, hardness, resistance, soundType);
    }

    @Override
    public boolean isSolid(BlockState state) {
        return false;
    }
}
