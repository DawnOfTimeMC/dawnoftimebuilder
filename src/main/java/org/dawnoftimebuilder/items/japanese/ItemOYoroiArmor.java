package org.dawnoftimebuilder.items.japanese;

import net.minecraft.client.model.ModelBiped;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.inventory.EntityEquipmentSlot;
import org.dawnoftimebuilder.client.renderer.model.ModelOYoroiArmor;
import org.dawnoftimebuilder.items.general.DoTBItemCustomArmor;

import static org.dawnoftimebuilder.enums.EArmorMaterial.OYOROI;

public class ItemOYoroiArmor extends DoTBItemCustomArmor {

	public ItemOYoroiArmor(EntityEquipmentSlot equipmentSlotIn) {
		super("o_yoroi_armor", OYOROI.getArmorMaterial(), equipmentSlotIn);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped createModel(){
		return new ModelOYoroiArmor(0.0F, this.armorType);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped createSlimModel() {
		return new ModelOYoroiArmor(0.0F, this.armorType);
	}
}