package org.dawnoftimebuilder.registries;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.dawnoftimebuilder.items.DoTBItems;

public class DoTBRecipesRegistry {

	public static void init(){
		addSmeltingRecipe(DoTBItems.grey_clay_tile, DoTBItems.grey_tile, 1.0F);
	}

	private static void addSmeltingRecipe(Item before, Item after, float experience){
		GameRegistry.addSmelting(before, new ItemStack(after), experience);
	}
}
