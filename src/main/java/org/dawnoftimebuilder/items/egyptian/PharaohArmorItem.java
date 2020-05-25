package org.dawnoftimebuilder.items.egyptian;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.client.renderer.armor.PharaohArmorModel;
import org.dawnoftimebuilder.items.templates.CustomArmorItem;

import static org.dawnoftimebuilder.utils.DoTBMaterials.ArmorMaterial.PHARAOH;

public class PharaohArmorItem extends CustomArmorItem {

	public PharaohArmorItem(EquipmentSlotType slot) {
		super("pharaoh_armor", PHARAOH, slot);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public BipedModel<?> createModel(LivingEntity entityLiving) {
		return new PharaohArmorModel<>(this.slot, true);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public BipedModel<?> createSlimModel(LivingEntity entityLiving) {
		return new PharaohArmorModel<>(this.slot, false);
	}
}