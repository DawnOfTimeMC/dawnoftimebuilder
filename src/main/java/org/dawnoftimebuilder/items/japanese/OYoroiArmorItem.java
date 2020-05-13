package org.dawnoftimebuilder.items.japanese;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.client.renderer.armor.OYoroiArmorModel;
import org.dawnoftimebuilder.items.templates.CustomArmorItem;

import static org.dawnoftimebuilder.utils.DoTBMaterials.ArmorMaterial.O_YOROI;

public class OYoroiArmorItem extends CustomArmorItem {

	public OYoroiArmorItem(EquipmentSlotType slot) {
		super("o_yoroi_armor", O_YOROI, slot);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public BipedModel<?> createModel(LivingEntity entityLiving) {
		return new OYoroiArmorModel<>(this.slot, true);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public BipedModel<?> createSlimModel(LivingEntity entityLiving) {
		return new OYoroiArmorModel<>(this.slot, false);
	}
}