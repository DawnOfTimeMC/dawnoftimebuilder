package org.dawnoftimebuilder;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.blocks.DoTBBlocksRegistry;
import org.dawnoftimebuilder.crafts.DoTBRecipesRegistry;
import org.dawnoftimebuilder.items.DoTBItemsRegistry;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class DoTBEvents {
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		DoTBBlocksRegistry.registerBlocks(event);
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		DoTBBlocksRegistry.registerBlockItems(event);
		DoTBItemsRegistry.registerItems(event);
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event){
		DoTBRecipesRegistry.registerRecipes(event);
	}
}
