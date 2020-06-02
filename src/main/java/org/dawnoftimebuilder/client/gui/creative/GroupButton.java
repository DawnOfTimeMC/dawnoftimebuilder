package org.dawnoftimebuilder.client.gui.creative;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GroupButton extends Button{
	private final ResourceLocation iconResource;
	private final int iconU;
	private final int iconV;

	public GroupButton(int x, int y, String message, IPressable pressable, ResourceLocation iconResource, int iconU, int iconV){
		super(x, y, 20, 20, message, pressable);
		this.iconResource = iconResource;
		this.iconU = iconU;
		this.iconV = iconV;
	}

	@Override
	public void renderButton(int mouseX, int mouseY, float partialTicks){
		Minecraft.getInstance().getTextureManager().bindTexture(WIDGETS_LOCATION);
		GlStateManager.disableLighting();
		GlStateManager.color3f(1F, 1F, 1F);
		GlStateManager.enableBlend();
		int offset = this.getYImage(this.isHovered());
		this.blit(this.x, this.y, 0, 46 + offset * 20, this.width / 2, this.height);
		this.blit(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + offset * 20, this.width / 2, this.height);
		if(!this.active) GlStateManager.color4f(0.5F, 0.5F, 0.5F, 1.0F);
		Minecraft.getInstance().getTextureManager().bindTexture(this.iconResource);
		this.blit(this.x + 2, this.y + 2, this.iconU, this.iconV, 16, 16);
	}
}
