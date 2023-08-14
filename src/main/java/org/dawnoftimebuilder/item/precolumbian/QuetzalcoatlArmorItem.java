package org.dawnoftimebuilder.item.precolumbian;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.client.model.armor.CustomArmorModel;
import org.dawnoftimebuilder.client.model.armor.QuetzalcoatlModel;
import org.dawnoftimebuilder.item.templates.CustomArmorItem;

import static org.dawnoftimebuilder.util.DoTBMaterials.ArmorMaterial.RAIJIN;

public class QuetzalcoatlArmorItem extends CustomArmorItem {

	public QuetzalcoatlArmorItem(EquipmentSlotType slot) {
		super("quetzalcoatl_armor", RAIJIN, slot);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public CustomArmorModel<LivingEntity> createModel(LivingEntity entityLiving) {
		return new QuetzalcoatlModel<>(this.slot, true, entityLiving.getScale());
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public CustomArmorModel<LivingEntity> createSlimModel(LivingEntity entityLiving) {
		return new QuetzalcoatlModel<>(this.slot, false, entityLiving.getScale());
	}
}