package org.dawnoftimebuilder.items.japanese;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.client.renderer.model.ModelJapaneseLightArmor;
import org.dawnoftimebuilder.items.global.DoTBItemCustomArmor;

public class ItemJapaneseLightArmor extends DoTBItemCustomArmor {

	public ItemJapaneseLightArmor(EntityEquipmentSlot equipmentSlotIn) {
		super("japanese_light_armor", ArmorMaterial.LEATHER, equipmentSlotIn);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void createModel(){
		this.model = new ModelJapaneseLightArmor(0.0F, this.armorType);
	}
}