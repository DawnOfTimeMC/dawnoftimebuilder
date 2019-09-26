package org.dawnoftimebuilder.blocks.french;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.blocks.general.DoTBBlockPane;

public class BlockLatticeGlassPane extends DoTBBlockPane {

	public BlockLatticeGlassPane() {
		super("lattice_glass_pane", Material.GLASS);
	}
	
    @SideOnly(Side.CLIENT)
	@Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

}
