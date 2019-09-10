package org.dawnoftimebuilder.items.global;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public abstract class DoTBItemCustomArmor extends ItemArmor {

	public final String set;
	public ModelBiped model = null;

	@SideOnly(Side.CLIENT)
	public void createModel(){
		this.model = new ModelBiped();
	}

	public DoTBItemCustomArmor(String set, ArmorMaterial materialIn, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, 0, equipmentSlotIn);
		this.set = set;

		this.setTranslationKey(MOD_ID + "." + set + "_" + equipmentSlotIn.getName());
		this.setRegistryName(MOD_ID, set + "_" + equipmentSlotIn.getName());
		this.setCreativeTab(DOTB_TAB);

		this.setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		if(this.model == null) createModel();
		if(!itemStack.isEmpty()) {
			if(itemStack.getItem() instanceof ItemArmor) {
				ModelBiped armorModel = this.model;
				armorModel.setModelAttributes(_default);
				return armorModel;
			}
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return MOD_ID + ":textures/models/armor/" + this.set + ".png";
	}
}