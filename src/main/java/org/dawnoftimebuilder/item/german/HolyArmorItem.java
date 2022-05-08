package org.dawnoftimebuilder.item.german;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.client.model.armor.CustomArmorModel;
import org.dawnoftimebuilder.client.model.armor.HolyArmorModel;
import org.dawnoftimebuilder.item.templates.CustomArmorItem;

import static org.dawnoftimebuilder.util.DoTBMaterials.ArmorMaterial.HOLY;

public class HolyArmorItem extends CustomArmorItem {

	public HolyArmorItem(EquipmentSlotType slot) {
		super("holy_armor", HOLY, slot);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public CustomArmorModel<LivingEntity> createModel(LivingEntity entityLiving) {
		return new HolyArmorModel<>(this.slot, true, entityLiving.getScale());
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public CustomArmorModel<LivingEntity> createSlimModel(LivingEntity entityLiving) {
		return new HolyArmorModel<>(this.slot, false, entityLiving.getScale());
	}
}