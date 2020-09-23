package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

public class GlassBlockDoTB extends GlassBlock {
    public GlassBlockDoTB(float hardness, float resistance) {
        super(Block.Properties.create(Material.GLASS).hardnessAndResistance(hardness, resistance).sound(SoundType.GLASS));
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}