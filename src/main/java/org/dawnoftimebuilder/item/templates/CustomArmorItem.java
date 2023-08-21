package org.dawnoftimebuilder.item.templates;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.client.model.armor.CustomArmorModel;

import javax.annotation.Nullable;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class CustomArmorItem extends ArmorItem {

	public final String set;
	private final ArmorModelFactory modelFactory;
	public CustomArmorModel<LivingEntity> model = null;
	public CustomArmorModel<LivingEntity> slimModel = null;

	public CustomArmorItem(String set, IArmorMaterial materialIn, EquipmentSlotType slot, ArmorModelFactory modelFactory) {
		super(materialIn, slot, new Item.Properties().stacksTo(1).tab(DOTB_TAB));
		this.set = set;
		this.modelFactory = modelFactory;
	}

	public String getItemPartName(){
		return this.set + "_" + slot.getName();
	}

	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	@OnlyIn(Dist.CLIENT)
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
		if(!itemStack.isEmpty()) {
			if(itemStack.getItem() instanceof ArmorItem) {

				if(entityLiving instanceof AbstractClientPlayerEntity){
					if("slim".equals(((AbstractClientPlayerEntity) entityLiving).getModelName())){
						if(this.slimModel == null) this.slimModel = this.createSlimModel(entityLiving);
						this.slimModel.young = _default.young;
						this.slimModel.crouching = _default.crouching;
						this.slimModel.riding = _default.riding;
						this.slimModel.rightArmPose = _default.rightArmPose;
						this.slimModel.leftArmPose = _default.leftArmPose;
						this.slimModel.setupAnim(entityLiving, (float)entityLiving.tickCount);

						return (A) this.slimModel;
					}
				}

				if(this.model == null) this.model = this.createModel(entityLiving);
				this.model.young = _default.young;
				this.model.crouching = _default.crouching;
				this.model.riding = _default.riding;
				this.model.rightArmPose = _default.rightArmPose;
				this.model.leftArmPose = _default.leftArmPose;
				this.model.setupAnim(entityLiving, (float)entityLiving.tickCount);

				return (A) this.model;
			}
		}
		return null;
	}

	@Nullable
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		if(entity instanceof AbstractClientPlayerEntity){
			if("slim".equals(((AbstractClientPlayerEntity) entity).getModelName())){
				return MOD_ID + ":textures/models/armor/" + this.set + "_slim.png";
			}
		}
		return MOD_ID + ":textures/models/armor/" + this.set + ".png";
	}

	@OnlyIn(Dist.CLIENT)
	public CustomArmorModel<LivingEntity> createModel(LivingEntity entityLiving){
		return this.modelFactory.create(this.slot, true, entityLiving.getScale());
	}

	@OnlyIn(Dist.CLIENT)
	public CustomArmorModel<LivingEntity> createSlimModel(LivingEntity entityLiving){
		return this.modelFactory.create(this.slot, false, entityLiving.getScale());
	}

	@FunctionalInterface
	public interface ArmorModelFactory {
		CustomArmorModel<LivingEntity> create(EquipmentSlotType slot, boolean isSteve, float scale);
	}
}
