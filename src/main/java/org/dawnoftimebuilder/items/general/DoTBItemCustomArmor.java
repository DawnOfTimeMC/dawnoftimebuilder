package org.dawnoftimebuilder.items.general;

import net.minecraft.client.entity.AbstractClientPlayer;
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
	public ModelBiped slimModel = null;

	@SideOnly(Side.CLIENT)
	public abstract ModelBiped createModel();

	@SideOnly(Side.CLIENT)
	public abstract ModelBiped createSlimModel();

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
		if(this.model == null) this.model = createModel();
		if(this.slimModel == null) this.slimModel = createSlimModel();
		if(!itemStack.isEmpty()) {
			if(itemStack.getItem() instanceof ItemArmor) {
				ModelBiped armorModel;
				if(entityLiving instanceof AbstractClientPlayer){
					if("slim".equals(((AbstractClientPlayer) entityLiving).getSkinType())){
						armorModel = this.slimModel;
						armorModel.setModelAttributes(_default);
						return armorModel;
					}
				}
				armorModel = this.model;
				armorModel.setModelAttributes(_default);
				return armorModel;
			}
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if(entity instanceof AbstractClientPlayer){
			AbstractClientPlayer clientPlayer = (AbstractClientPlayer) entity;
			if ("slim".equals(clientPlayer.getSkinType())) {
				return MOD_ID + ":textures/models/armor/" + this.set + "_slim.png";
			}
		}
		return MOD_ID + ":textures/models/armor/" + this.set + ".png";
	}
}