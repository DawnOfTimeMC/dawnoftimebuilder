package org.dawnoftimebuilder.registry;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.dawnoftimebuilder.container.DisplayerMenu;

import java.util.function.Supplier;

public abstract class DoTBMenuTypesRegistry {
	public static DoTBMenuTypesRegistry INSTANCE;

	public final Supplier<MenuType<DisplayerMenu>> DISPLAYER = register("displayer", DisplayerMenu::new, DisplayerMenu.DisplayerMenuData.CODEC);

	public abstract <T extends AbstractContainerMenu, D> Supplier<MenuType<T>> register(String name, MenuTypeFactory<T, D> factory, StreamCodec<? super RegistryFriendlyByteBuf, D> packetCodec);

	@FunctionalInterface
	public interface MenuTypeFactory<T extends AbstractContainerMenu, D> {
		T create(int syncId, Inventory inventory, D data);
	}
}
