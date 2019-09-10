package org.dawnoftimebuilder.items.japanese;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.inventory.EntityEquipmentSlot;
import org.dawnoftimebuilder.client.renderer.model.ModelOYoroiArmor;
import org.dawnoftimebuilder.items.global.DoTBItemCustomArmor;

public class ItemOYoroiArmor extends DoTBItemCustomArmor {

	public ItemOYoroiArmor(EntityEquipmentSlot equipmentSlotIn) {
		super("o_yoroi_armor", ArmorMaterial.GOLD, equipmentSlotIn);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void createModel(){
		this.model = new ModelOYoroiArmor(0.0F, this.armorType);
	}
}