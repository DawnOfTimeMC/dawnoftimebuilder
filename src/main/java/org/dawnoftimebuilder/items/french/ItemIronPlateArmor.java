package org.dawnoftimebuilder.items.french;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.client.renderer.model.ModelIronPlateArmor;
import org.dawnoftimebuilder.items.global.DoTBItemCustomArmor;

public class ItemIronPlateArmor extends DoTBItemCustomArmor {
	public ItemIronPlateArmor(EntityEquipmentSlot equipmentSlotIn) {
		super("iron_plate_armor", ArmorMaterial.DIAMOND, equipmentSlotIn);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void createModel(){
		this.model = new ModelIronPlateArmor(0.0F, this.armorType);
	}
}
