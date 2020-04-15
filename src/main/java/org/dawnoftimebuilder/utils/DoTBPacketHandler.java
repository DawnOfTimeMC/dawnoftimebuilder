package org.dawnoftimebuilder.utils;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBPacketHandler {

	private static final String PROTOCOL_VERSION = "1";
	private static int id = 0;

	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(MOD_ID, "dotb_packets"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);
}
