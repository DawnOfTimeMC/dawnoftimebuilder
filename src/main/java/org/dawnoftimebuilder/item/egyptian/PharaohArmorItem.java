package org.dawnoftimebuilder.item.egyptian;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.client.model.armor.CustomArmorModel;
import org.dawnoftimebuilder.client.model.armor.PharaohArmorModel;
import org.dawnoftimebuilder.item.templates.CustomArmorItem;

import static org.dawnoftimebuilder.util.DoTBMaterials.ArmorMaterial.PHARAOH;

public class PharaohArmorItem extends CustomArmorItem {

	public PharaohArmorItem(EquipmentSlotType slot) {
		super("pharaoh_armor", PHARAOH, slot);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public CustomArmorModel<LivingEntity> createModel(LivingEntity entityLiving) {
		return new PharaohArmorModel<>(this.slot, true, entityLiving.getScale());
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public CustomArmorModel<LivingEntity> createSlimModel(LivingEntity entityLiving) {
		return new PharaohArmorModel<>(this.slot, false, entityLiving.getScale());
	}
}