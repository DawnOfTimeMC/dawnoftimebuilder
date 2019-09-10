package org.dawnoftimebuilder.items.japanese;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.inventory.EntityEquipmentSlot;
import org.dawnoftimebuilder.client.renderer.model.ModelRaijinArmor;
import org.dawnoftimebuilder.items.global.DoTBItemCustomArmor;

public class ItemRaijinArmor extends DoTBItemCustomArmor {

	public ItemRaijinArmor(EntityEquipmentSlot equipmentSlotIn) {
		super("raijin_armor", ArmorMaterial.GOLD, equipmentSlotIn);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void createModel(){
		this.model = new ModelRaijinArmor(0.0F, this.armorType);
	}
}
