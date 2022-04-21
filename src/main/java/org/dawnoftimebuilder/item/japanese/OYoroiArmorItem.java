package org.dawnoftimebuilder.item.japanese;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.client.model.armor.CustomArmorModel;
import org.dawnoftimebuilder.client.model.armor.OYoroiArmorModel;
import org.dawnoftimebuilder.item.templates.CustomArmorItem;

import static org.dawnoftimebuilder.util.DoTBMaterials.ArmorMaterial.O_YOROI;

public class OYoroiArmorItem extends CustomArmorItem {

	public OYoroiArmorItem(EquipmentSlotType slot) {
		super("o_yoroi_armor", O_YOROI, slot);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public CustomArmorModel<?> createModel(LivingEntity entityLiving) {
		return new OYoroiArmorModel<>(this.slot, true);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public CustomArmorModel<?> createSlimModel(LivingEntity entityLiving) {
		return new OYoroiArmorModel<>(this.slot, false);
	}
}