package org.dawnoftimebuilder.items.japanese;

import com.google.common.collect.Multimap;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.inventory.EntityEquipmentSlot;
import org.dawnoftimebuilder.client.renderer.model.ModelRaijinArmor;
import org.dawnoftimebuilder.items.general.DoTBItemCustomArmor;

import static org.dawnoftimebuilder.enums.EArmorMaterial.RAIJIN;

public class ItemRaijinArmor extends DoTBItemCustomArmor {
	public ItemRaijinArmor(EntityEquipmentSlot equipmentSlotIn) {
		super("raijin_armor", RAIJIN.getArmorMaterial(), equipmentSlotIn);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped createModel(){
		return new ModelRaijinArmor(0.0F, this.armorType, true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped createSlimModel() {
		return new ModelRaijinArmor(0.0F, this.armorType, false);
	}

	@Override
	public Multimap<String, AttributeModifier> getFullSetAttributeModifiers() {
		Multimap<String, AttributeModifier> multimap = super.getFullSetAttributeModifiers();
		multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier("Armor modifier", 0.5D, 0));
		multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier("Armor modifier", 0.1D, 0));
		multimap.put(SharedMonsterAttributes.FLYING_SPEED.getName(), new AttributeModifier("Armor modifier", 0.2D, 0));
		multimap.put(SharedMonsterAttributes.LUCK.getName(), new AttributeModifier("Armor modifier", 1.0D, 0));
		return multimap;
	}
}
