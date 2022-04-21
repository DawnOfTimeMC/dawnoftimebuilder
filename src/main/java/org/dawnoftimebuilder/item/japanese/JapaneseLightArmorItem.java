package org.dawnoftimebuilder.item.japanese;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.client.model.armor.CustomArmorModel;
import org.dawnoftimebuilder.client.model.armor.JapaneseLightArmorModel;
import org.dawnoftimebuilder.item.templates.CustomArmorItem;

import static org.dawnoftimebuilder.util.DoTBMaterials.ArmorMaterial.JAPANESE_LIGHT;

public class JapaneseLightArmorItem extends CustomArmorItem {

	public JapaneseLightArmorItem(EquipmentSlotType slot) {
		super("japanese_light_armor", JAPANESE_LIGHT, slot);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public CustomArmorModel<?> createModel(LivingEntity entityLiving) {
		return new JapaneseLightArmorModel<>(this.slot, true);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public CustomArmorModel<?> createSlimModel(LivingEntity entityLiving) {
		return new JapaneseLightArmorModel<>(this.slot, false);
	}
}