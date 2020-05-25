package org.dawnoftimebuilder.items.japanese;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.client.renderer.armor.RaijinArmorModel;
import org.dawnoftimebuilder.items.templates.CustomArmorItem;

import static org.dawnoftimebuilder.utils.DoTBMaterials.ArmorMaterial.RAIJIN;

public class RaijinArmorItem extends CustomArmorItem {

	public RaijinArmorItem(EquipmentSlotType slot) {
		super("raijin_armor", RAIJIN, slot);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public BipedModel<?> createModel(LivingEntity entityLiving) {
		return new RaijinArmorModel<>(this.slot, true);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public BipedModel<?> createSlimModel(LivingEntity entityLiving) {
		return new RaijinArmorModel<>(this.slot, false);
	}
}