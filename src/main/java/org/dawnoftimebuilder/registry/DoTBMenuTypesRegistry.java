package org.dawnoftimebuilder.registry;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.dawnoftimebuilder.container.DisplayerMenu;

import java.util.function.Supplier;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBMenuTypesRegistry {
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MOD_ID);

	public static final RegistryObject<MenuType<DisplayerMenu>> DISPLAYER = reg("displayer", () -> IForgeMenuType.create(DisplayerMenu::new));

	private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> reg(String name, Supplier<MenuType<T>> menuType) {
		return MENU_TYPES.register(name, menuType);
	}

	public static void register(IEventBus eventBus) {
		MENU_TYPES.register(eventBus);
	}
}
