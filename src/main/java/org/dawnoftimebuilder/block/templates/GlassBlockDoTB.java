package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

public class GlassBlockDoTB extends GlassBlock {
    public GlassBlockDoTB(Material materialIn, float hardness, float resistance, SoundType soundType) {
        super(Block.Properties.of(materialIn).strength(hardness, resistance).sound(soundType));
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}