package org.dawnoftimebuilder.items.japanese;

import com.google.common.collect.Multimap;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.client.renderer.model.ModelJapaneseLightArmor;
import org.dawnoftimebuilder.items.general.DoTBItemCustomArmor;

import static org.dawnoftimebuilder.enums.EArmorMaterial.JAPANESE_LIGHT_ARMOR;

public class ItemJapaneseLightArmor extends DoTBItemCustomArmor {

	public ItemJapaneseLightArmor(EntityEquipmentSlot equipmentSlotIn) {
		super("japanese_light_armor", JAPANESE_LIGHT_ARMOR.getArmorMaterial(), equipmentSlotIn);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped createModel(){
		return new ModelJapaneseLightArmor(0.0F, this.armorType, true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped createSlimModel() {
		return new ModelJapaneseLightArmor(0.0F, this.armorType, false);
	}

	@Override
	public Multimap<String, AttributeModifier> getFullSetAttributeModifiers() {
		Multimap<String, AttributeModifier> multimap = super.getFullSetAttributeModifiers();
		multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier("Armor modifier", 0.1D, 0).setSaved(false));
		return multimap;
	}
}