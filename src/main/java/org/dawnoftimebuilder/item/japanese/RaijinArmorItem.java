package org.dawnoftimebuilder.item.japanese;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.client.model.armor.CustomArmorModel;
import org.dawnoftimebuilder.client.model.armor.RaijinArmorModel;
import org.dawnoftimebuilder.item.templates.CustomArmorItem;

import static org.dawnoftimebuilder.util.DoTBMaterials.ArmorMaterial.RAIJIN;

public class RaijinArmorItem extends CustomArmorItem {

	public RaijinArmorItem(EquipmentSlotType slot) {
		super("raijin_armor", RAIJIN, slot);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public CustomArmorModel<?> createModel(LivingEntity entityLiving) {
		return new RaijinArmorModel<>(this.slot, true);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public CustomArmorModel<?> createSlimModel(LivingEntity entityLiving) {
		return new RaijinArmorModel<>(this.slot, false);
	}
}