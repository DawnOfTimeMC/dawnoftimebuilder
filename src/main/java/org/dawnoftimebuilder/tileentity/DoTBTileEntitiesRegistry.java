package org.dawnoftimebuilder.tileentity;

import net.minecraft.util.ResourceLocation;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBTileEntitiesRegistry {

	public static void init(){
		registerTileEntity(DoTBTileEntityDryer.class, "dryer");
		registerTileEntity(DoTBTileEntityDisplayer.class, "displayer");
		registerTileEntity(TileEntityLittleFlag.class, "little_flag");
		registerTileEntity(DoTBTileEntityBed.class, "bed");
	}

	private static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id){
		GameRegistry.registerTileEntity(tileEntityClass, new ResourceLocation(MOD_ID, id + "_te"));
	}
}
