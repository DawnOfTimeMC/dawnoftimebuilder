package org.dawnoftimebuilder.item.german;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.client.model.armor.CustomArmorModel;
import org.dawnoftimebuilder.client.model.armor.IronPlateArmorModel;
import org.dawnoftimebuilder.item.templates.CustomArmorItem;

import static org.dawnoftimebuilder.util.DoTBMaterials.ArmorMaterial.IRON_PLATE;

public class IronPlateArmorItem extends CustomArmorItem {

	public IronPlateArmorItem(EquipmentSlotType slot) {
		super("iron_plate_armor", IRON_PLATE, slot);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public CustomArmorModel<LivingEntity> createModel(LivingEntity entityLiving) {
		return new IronPlateArmorModel<>(this.slot, true, entityLiving.getScale());
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public CustomArmorModel<LivingEntity> createSlimModel(LivingEntity entityLiving) {
		return new IronPlateArmorModel<>(this.slot, false, entityLiving.getScale());
	}
}