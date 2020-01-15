package org.dawnoftimebuilder.items.egyptian;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.client.renderer.model.ModelPharaohArmor;
import org.dawnoftimebuilder.items.general.DoTBItemCustomArmor;

import static org.dawnoftimebuilder.enums.EArmorMaterial.PHARAOH;

public class ItemPharaohArmor extends DoTBItemCustomArmor {
	public ItemPharaohArmor(EntityEquipmentSlot equipmentSlotIn) {
		super("pharaoh_armor", PHARAOH.getArmorMaterial(), equipmentSlotIn);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void createModel(){
		this.model = new ModelPharaohArmor(0.0F, this.armorType);
	}
}
