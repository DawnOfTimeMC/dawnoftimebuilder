package org.dawnoftimebuilder.registries;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.network.IContainerFactory;
import org.dawnoftimebuilder.container.DisplayerContainer;
import org.dawnoftimebuilder.tileentity.DisplayerTileEntity;

import java.util.ArrayList;
import java.util.List;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBContainersRegistry {

	public static final List<ContainerType<?>> CONTAINER_TYPES = new ArrayList<>();

	public static final ContainerType<DisplayerContainer> DISPLAYER_CONTAINER = reg("displayer", (IContainerFactory<DisplayerContainer>) (windowId, playerInventory, data) -> {
		DisplayerTileEntity displayer = (DisplayerTileEntity) playerInventory.player.world.getTileEntity(data.readBlockPos());
		return new DisplayerContainer(windowId, playerInventory, displayer);
	});

	private static <T extends Container> ContainerType<T> reg(String name, ContainerType.IFactory<T> factory) {
		ContainerType<T> type = new ContainerType<>(factory);
		type.setRegistryName(MOD_ID, name);
		CONTAINER_TYPES.add(type);
		return type;
	}
}
