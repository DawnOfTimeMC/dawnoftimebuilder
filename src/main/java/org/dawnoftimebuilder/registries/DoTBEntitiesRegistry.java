package org.dawnoftimebuilder.registries;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.dawnoftimebuilder.entities.EntityJapaneseDragon;
import org.dawnoftimebuilder.entities.EntitySilkmoth;
import org.dawnoftimebuilder.tileentity.*;

import java.awt.*;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.instance;

public class DoTBEntitiesRegistry {

	private static int idInt;

	public static final ResourceLocation LT_SILKMOTH = LootTableList.register(new ResourceLocation(MOD_ID, "entities/silkmoth"));

	public static void init(){
		idInt = 0;
		registerTileEntity(EntitySilkmoth.class, "silkmoth", 40, new Color(219, 219, 216), new Color(248,248,243));
		registerTileEntity(EntityJapaneseDragon.class, "japanese_dragon", 150, new Color(120,7,7), new Color(240, 8, 8));
	}

		private static void registerTileEntity(Class<? extends Entity> entityClass, String id, int range, Color eggPrimary, Color eggSecondary){
			EntityRegistry.registerModEntity(new ResourceLocation(MOD_ID, id), entityClass, MOD_ID + "." + id, idInt, instance, range, 1, true, eggPrimary.getRGB(), eggSecondary.getRGB());
		idInt++;
	}
}
