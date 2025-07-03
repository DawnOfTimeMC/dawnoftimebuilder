package org.dawnoftime.dawnoftime;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoTBCommon {
	public static final String MOD_ID = "dawnoftimebuilder";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);
	public static final ResourceLocation CREATIVE_ICONS = new ResourceLocation(MOD_ID, "textures/gui/creative_icons.png");

	public static void init() {}
}