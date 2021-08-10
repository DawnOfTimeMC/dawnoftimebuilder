package org.dawnoftimebuilder.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class PacketHandler {
/*
	public static SimpleChannel HANDLER;

	private static final String PROTOCOL_VERSION = "1";
	private static int nextId = 0;

	public static void init() {
		HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, "network"), () -> PROTOCOL_VERSION, s -> true, s -> true);
		reg(DisplayerMessage.class, new DisplayerMessage());
	}

	private static <T> void reg(Class<T> messageClass, IMessage<T> message) {
		HANDLER.registerMessage(nextId++, messageClass, message::encode, message::decode, message::handle);
	}

	public static <T> void send(PacketDistributor.PacketTarget target, IMessage<T> message){
		HANDLER.send(target, message);
	}*/
}