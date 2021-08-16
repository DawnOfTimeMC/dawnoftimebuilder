package org.dawnoftimebuilder.proxy;

import net.minecraft.item.Item;
import net.minecraft.world.World;

public class CommonProxy{
	public void onSetupCommon() {}

	public void onSetupClient() {}

	public World getClientWorld() {
		return null;
	}
}