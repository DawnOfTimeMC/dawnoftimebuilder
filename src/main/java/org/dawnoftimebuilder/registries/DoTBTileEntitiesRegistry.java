package org.dawnoftimebuilder.registries;

import net.minecraft.util.ResourceLocation;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.dawnoftimebuilder.tileentity.*;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBTileEntitiesRegistry {

	public static void init(){
		registerTileEntity(DoTBTileEntityBed.class, "bed");
		registerTileEntity(DoTBTileEntityDryer.class, "dryer");
		registerTileEntity(DoTBTileEntityDisplayer.class, "displayer");
		registerTileEntity(DoTBTileEntityStove.class, "stove");
		registerTileEntity(TileEntityLittleFlag.class, "little_flag");
	}

	private static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id){
		GameRegistry.registerTileEntity(tileEntityClass, new ResourceLocation(MOD_ID, id + "_te"));
	}
}
