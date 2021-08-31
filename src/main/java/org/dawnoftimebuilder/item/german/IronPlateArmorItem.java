package org.dawnoftimebuilder.item.german;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.client.model.armor.IronPlateArmorModel;
import org.dawnoftimebuilder.item.templates.CustomArmorItem;

import static org.dawnoftimebuilder.util.DoTBMaterials.ArmorMaterial.IRON_PLATE;

public class IronPlateArmorItem extends CustomArmorItem {

	public IronPlateArmorItem(EquipmentSlotType slot) {
		super("iron_plate_armor", IRON_PLATE, slot);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public BipedModel<?> createModel(LivingEntity entityLiving) {
		return new IronPlateArmorModel<>(this.slot, true);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public BipedModel<?> createSlimModel(LivingEntity entityLiving) {
		return new IronPlateArmorModel<>(this.slot, false);
	}
}