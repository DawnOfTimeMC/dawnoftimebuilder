package org.dawnoftimebuilder;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.items.general.DoTBItemCustomArmor;
import org.dawnoftimebuilder.registries.DoTBBlocksRegistry;
import org.dawnoftimebuilder.crafts.DoTBRecipesRegistry;
import org.dawnoftimebuilder.registries.DoTBItemsRegistry;

import java.util.Objects;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class DoTBEvents {
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event){
		DoTBBlocksRegistry.registerBlocks(event);
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event){
		DoTBBlocksRegistry.initItemBlocks();
		DoTBItemsRegistry.registerItems(event);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event){
		DoTBItemsRegistry.registerItemsModels();
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event){
		DoTBRecipesRegistry.registerRecipes(event);
	}

	@SubscribeEvent
	public void onMissingBlockMapping(RegistryEvent.MissingMappings<Block> event){
		for(RegistryEvent.MissingMappings.Mapping<Block> mapping : event.getAllMappings()){
			ResourceLocation resource = mapping.key;
			if(resource != null && Objects.equals(resource.getNamespace(), "dawnoftime")){
				String name = resource.getPath();
				if(Objects.equals(name, "commelina_flower")) name = "commelina";
				if(Objects.equals(name, "tatami_floor")) name = "small_tatami_floor";
				Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MOD_ID, name));
				if(block != null){
					mapping.remap(block);
				}
			}
		}
	}

	@SubscribeEvent
	public void onMissingItemMapping(RegistryEvent.MissingMappings<Item> event){
		for(RegistryEvent.MissingMappings.Mapping<Item> mapping : event.getAllMappings()){
			ResourceLocation resource = mapping.key;
			if(resource != null && Objects.equals(resource.getNamespace(), "dawnoftime")){
				String name = resource.getPath();
				Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(MOD_ID, name));
				if(item != null){
					mapping.remap(item);
				}
			}
		}
	}
/*
	@SubscribeEvent
	public void onEquipmentChange(LivingEquipmentChangeEvent event){
		if(event.getSlot() == EntityEquipmentSlot.MAINHAND || event.getSlot() == EntityEquipmentSlot.OFFHAND) return;
		EntityLivingBase entity = event.getEntityLiving();
		Item itemFrom = event.getFrom().getItem();
		Item itemTo = event.getTo().getItem();
		DoTBItemCustomArmor itemArmor;

		if(itemFrom instanceof DoTBItemCustomArmor){
			itemArmor = (DoTBItemCustomArmor) itemFrom;
			if(itemArmor.entityWearsFullSet(entity)){
				//Entity had full set : let's remove the its effects
				itemArmor.removeFullSetEffects(entity);
			}
		}

		if(itemTo instanceof DoTBItemCustomArmor){
			itemArmor = (DoTBItemCustomArmor) itemTo;
			if(itemArmor.entityWearsFullSet(entity)){
				//Entity has now full set : let's add the its effects
				itemArmor.applyFullSetEffects(entity);
			}
		}
	}
*/
}
