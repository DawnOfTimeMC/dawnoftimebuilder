package org.dawnoftimebuilder.item.roman;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.client.model.armor.CenturionArmorModel;
import org.dawnoftimebuilder.client.model.armor.CustomArmorModel;
import org.dawnoftimebuilder.item.templates.CustomArmorItem;

import static org.dawnoftimebuilder.util.DoTBMaterials.ArmorMaterial.IRON_PLATE;

public class CenturionArmorItem extends CustomArmorItem {

	public CenturionArmorItem(EquipmentSlotType slot) {
		super("centurion_armor", IRON_PLATE, slot);
	} //TODO Decide what we are gonna do with the config of armor (I put IRON_PLATE for centurion).

	@OnlyIn(Dist.CLIENT)
	@Override
	public CustomArmorModel<LivingEntity> createModel(LivingEntity entityLiving) {
		return new CenturionArmorModel<>(this.slot, true, entityLiving.getScale());
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public CustomArmorModel<LivingEntity> createSlimModel(LivingEntity entityLiving) {
		return new CenturionArmorModel<>(this.slot, false, entityLiving.getScale());
	}
}