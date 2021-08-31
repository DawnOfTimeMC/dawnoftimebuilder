package org.dawnoftimebuilder.registry;

import net.minecraft.client.renderer.model.ModelResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class DoTBSpecialModelRegistry {

	public static final List<ModelResourceLocation> SPECIAL_MODELS = new ArrayList<>();

	//public static final ModelResourceLocation DISPLAYER_CONTAINER = reg("displayer", (IContainerFactory<DisplayerContainer>) (windowId, playerInventory, data) -> new DisplayerContainer(windowId, playerInventory, DawnOfTimeBuilder.PROXY.getClientWorld(), data.readBlockPos()));
/*
	private static <T extends Container> ContainerType<T> reg(String name, ContainerType.IFactory<T> factory) {
		ContainerType<T> type = new ContainerType<>(factory);
		type.setRegistryName(MOD_ID, name);
		SPECIAL_MODELS.add(type);
		return type;
	}

 */
}
