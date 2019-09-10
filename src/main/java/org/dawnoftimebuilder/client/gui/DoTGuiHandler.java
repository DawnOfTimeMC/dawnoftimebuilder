package org.dawnoftimebuilder.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import org.dawnoftimebuilder.inventory.ContainerDisplayer;
import org.dawnoftimebuilder.tileentity.DoTBTileEntityDisplayer;

public class DoTGuiHandler implements IGuiHandler {

	public static final int DISPLAYER_GUI = 0;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID){
			case DISPLAYER_GUI:
				return new ContainerDisplayer(player.inventory, (DoTBTileEntityDisplayer)world.getTileEntity(new BlockPos(x, y, z)), player);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID){
			case DISPLAYER_GUI:
				return new GuiDisplayer(player.inventory, (DoTBTileEntityDisplayer)world.getTileEntity(new BlockPos(x, y, z)), player);
		}
		
		return null;
	}

}
