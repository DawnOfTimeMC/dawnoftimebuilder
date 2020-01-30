package org.dawnoftimebuilder;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.dawnoftimebuilder.registries.DoTBBlocksRegistry;
import org.dawnoftimebuilder.registries.DoTBItemsRegistry;

import java.util.Objects;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MOD_ID)
public class DoTBEvents {

	public static final DoTBEvents INSTANCE = new DoTBEvents();

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event){
		DoTBBlocksRegistry.registerBlocks(event);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event){
		DoTBItemsRegistry.registerItems(event);
	}
/*
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event){
		DoTBItemsRegistry.registerItemsModels();
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event){
		DoTBRecipesRegistry.registerRecipes(event);
	}*/
}
