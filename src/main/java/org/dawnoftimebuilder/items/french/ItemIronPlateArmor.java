package org.dawnoftimebuilder.items.french;

import com.google.common.collect.Multimap;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.client.renderer.model.ModelIronPlateArmor;
import org.dawnoftimebuilder.items.general.DoTBItemCustomArmor;

import static org.dawnoftimebuilder.enums.EArmorMaterial.IRONPLATE;

public class ItemIronPlateArmor extends DoTBItemCustomArmor {
	public ItemIronPlateArmor(EntityEquipmentSlot equipmentSlotIn) {
		super("iron_plate_armor", IRONPLATE.getArmorMaterial(), equipmentSlotIn);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped createModel(){
		return new ModelIronPlateArmor(0.0F, this.armorType, true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped createSlimModel() {
		return new ModelIronPlateArmor(0.0F, this.armorType, false);
	}

	@Override
	public Multimap<String, AttributeModifier> getFullSetAttributeModifiers() {
		Multimap<String, AttributeModifier> multimap = super.getFullSetAttributeModifiers();
		multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier("Armor modifier", -0.03D, 0).setSaved(false));
		multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier("Armor modifier", 8.0D, 0).setSaved(false));
		return multimap;
	}
}
