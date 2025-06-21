package org.dawnoftime.dawnoftime.registry;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.dawnoftime.dawnoftime.container.DisplayerMenu;

import java.util.function.Supplier;

public abstract class DoTBMenuTypesRegistry {
	public static DoTBMenuTypesRegistry INSTANCE;

	public final Supplier<MenuType<DisplayerMenu>> DISPLAYER = register("displayer", DisplayerMenu::new);

	public abstract <T extends AbstractContainerMenu> Supplier<MenuType<T>> register(String name, MenuTypeFactory<T> factory);

	@FunctionalInterface
	public interface MenuTypeFactory<T> {
		AbstractContainerMenu create(int windowId, Inventory playerInventory, FriendlyByteBuf additionalData);
	}
}
