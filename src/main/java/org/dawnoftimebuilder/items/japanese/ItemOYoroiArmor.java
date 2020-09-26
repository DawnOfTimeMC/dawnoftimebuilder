package org.dawnoftimebuilder.items.japanese;

import com.google.common.collect.Multimap;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.inventory.EntityEquipmentSlot;
import org.dawnoftimebuilder.client.renderer.model.ModelOYoroiArmor;
import org.dawnoftimebuilder.items.general.DoTBItemCustomArmor;

import static org.dawnoftimebuilder.enums.EArmorMaterial.OYOROI;

public class ItemOYoroiArmor extends DoTBItemCustomArmor {

	public ItemOYoroiArmor(EntityEquipmentSlot equipmentSlotIn) {
		super("o_yoroi_armor", OYOROI.getArmorMaterial(), equipmentSlotIn);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped createModel(){
		return new ModelOYoroiArmor(0.0F, this.armorType, true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped createSlimModel() {
		return new ModelOYoroiArmor(0.0F, this.armorType, false);
	}

	@Override
	public Multimap<String, AttributeModifier> getFullSetAttributeModifiers() {
		Multimap<String, AttributeModifier> multimap = super.getFullSetAttributeModifiers();
		multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier("Armor modifier", 4.0D, 0));
		multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier("Armor modifier", 1.5D, 0));
		return multimap;
	}

	@Override
	public void applyFullSetEffects(EntityLivingBase entity) {
		super.applyFullSetEffects(entity);
		entity.stepHeight += 0.5F;
	}

	@Override
	public void removeFullSetEffects(EntityLivingBase entity) {
		super.removeFullSetEffects(entity);
		entity.stepHeight -= 0.5F;
	}
}