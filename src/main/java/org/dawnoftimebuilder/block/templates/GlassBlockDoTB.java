package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class GlassBlockDoTB extends GlassBlock {
    public GlassBlockDoTB(String name, float hardness, float resistance) {
        super(Block.Properties.create(Material.GLASS).hardnessAndResistance(hardness, resistance).sound(SoundType.GLASS));
        this.setRegistryName(MOD_ID, name);
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}