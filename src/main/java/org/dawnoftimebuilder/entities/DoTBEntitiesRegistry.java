package org.dawnoftimebuilder.entities;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.dawnoftimebuilder.tileentity.*;

import java.awt.*;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.instance;

public class DoTBEntitiesRegistry {

	private static int idInt;

	public static final ResourceLocation LT_SILKMOTH = LootTableList.register(new ResourceLocation(MOD_ID, "entities/silkmoth"));

	public static void init(){
		idInt = 0;
		registerTileEntity(EntitySilkmoth.class, "silkmoth");
	}

	private static void registerTileEntity(Class<? extends Entity> entityClass, String id){
		EntityRegistry.registerModEntity(new ResourceLocation(MOD_ID, id), entityClass, id, idInt, instance, 40, 1, true, new Color(219, 219, 216).getRGB(), new Color(248,248,243).getRGB());
		idInt++;
	}
}
