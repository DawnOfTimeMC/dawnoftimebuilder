package org.dawnoftimebuilder.item.templates;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.dawnoftimebuilder.client.model.armor.CustomArmorModel;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;

import net.minecraft.block.DispenserBlock;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.enchantment.IArmorVanishable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class CustomArmorItem extends ArmorItem implements IArmorVanishable {

	// ARMOR ITEM CLASS COPY STATIC VARIABLES
	private static final UUID[] ARMOR_MODIFIER_UUID_PER_SLOT = new UUID[] {
			UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
			UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
			UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
			UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150") };
	public static final IDispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
		protected ItemStack execute(IBlockSource blockSource, ItemStack stack) {
			return dispenseArmor(blockSource, stack) ? stack : super.execute(blockSource, stack);
		}
	};

	// ARMOR ITEM CLASS STATIC METHODS

	public static boolean dispenseArmor(IBlockSource blockSource, ItemStack stack) {
		BlockPos blockpos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
		List<LivingEntity> list = blockSource.getLevel().getEntitiesOfClass(LivingEntity.class,
				new AxisAlignedBB(blockpos),
				EntityPredicates.NO_SPECTATORS.and(new EntityPredicates.ArmoredMob(stack)));
		if (list.isEmpty()) {
			return false;
		} else {
			LivingEntity livingentity = list.get(0);
			EquipmentSlotType equipmentslottype = MobEntity.getEquipmentSlotForItem(stack);
			ItemStack itemstack = stack.split(1);
			livingentity.setItemSlot(equipmentslottype, itemstack);
			if (livingentity instanceof MobEntity) {
				((MobEntity) livingentity).setDropChance(equipmentslottype, 2.0F);
				((MobEntity) livingentity).setPersistenceRequired();
			}

			return true;
		}
	}

	@Override
	public int getMaxDamage(ItemStack stackIn) {
		return material.getDurabilityForSlot(slot);
	}

	// CUSTOM ARMOR ITEM VARIABLES

	public final String setName;
	public final String pieceName;
	public CustomArmorModel<LivingEntity> model = null;
	public CustomArmorModel<LivingEntity> slimModel = null;

	// ARMOR ITEM CLASS COPY VARIABLES

	protected final EquipmentSlotType slot;
	protected final IArmorMaterial material;
	private Multimap<Attribute, AttributeModifier> defaultModifiers;

	public CustomArmorItem(String setNameIn, String pieceNameIn, IArmorMaterial materialIn, EquipmentSlotType slotIn) {
		super(materialIn, slotIn, new Item.Properties().stacksTo(1).tab(DOTB_TAB)
				.defaultDurability(materialIn.getDurabilityForSlot(slotIn)));
		this.setName = setNameIn;
		this.material = materialIn;
		this.pieceName = pieceNameIn;
		this.slot = slotIn;
		DispenserBlock.registerBehavior(this, DISPENSE_ITEM_BEHAVIOR);
		updateAttributes(); // default attributes value
	}

	public final void updateAttributes() {
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		UUID uuid = ARMOR_MODIFIER_UUID_PER_SLOT[slot.getIndex()];
		builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", this.getDefense(), AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", this.getToughness(), AttributeModifier.Operation.ADDITION));

		if (this.material.getKnockbackResistance() > 0) {
			builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", material.getKnockbackResistance(), AttributeModifier.Operation.ADDITION));
		}

		this.defaultModifiers = builder.build();
	}

	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	@OnlyIn(Dist.CLIENT)
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
		if (!itemStack.isEmpty()) {
			if (itemStack.getItem() instanceof CustomArmorItem) {
				if (entityLiving instanceof AbstractClientPlayerEntity) {
					if ("slim".equals(((AbstractClientPlayerEntity) entityLiving).getModelName())) {
						if (this.slimModel == null) {
							this.slimModel = this.createSlimModel(entityLiving);
						}
						this.slimModel.young = _default.young;
						this.slimModel.crouching = _default.crouching;
						this.slimModel.riding = _default.riding;
						this.slimModel.rightArmPose = _default.rightArmPose;
						this.slimModel.leftArmPose = _default.leftArmPose;
						this.slimModel.setupAnim(entityLiving, (float) entityLiving.tickCount);

						return (A) this.slimModel;
					}
				}

				if (this.model == null) {
					this.model = this.createModel(entityLiving);
				}
				this.model.young = _default.young;
				this.model.crouching = _default.crouching;
				this.model.riding = _default.riding;
				this.model.rightArmPose = _default.rightArmPose;
				this.model.leftArmPose = _default.leftArmPose;
				this.model.setupAnim(entityLiving, (float) entityLiving.tickCount);

				return (A) this.model;
			}
		}
		return null;
	}

	@Nullable
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		if (entity instanceof AbstractClientPlayerEntity) {
			if ("slim".equals(((AbstractClientPlayerEntity) entity).getModelName())) {
				return MOD_ID + ":textures/models/armor/" + this.setName + "_slim.png";
			}
		}
		return MOD_ID + ":textures/models/armor/" + this.setName + ".png";
	}

	@OnlyIn(Dist.CLIENT)
	public CustomArmorModel<LivingEntity> createModel(LivingEntity entityLiving) {
		return this.getModelFactory().create(this.slot, true, entityLiving.getScale());
	}

	@OnlyIn(Dist.CLIENT)
	public CustomArmorModel<LivingEntity> createSlimModel(LivingEntity entityLiving) {
		return this.getModelFactory().create(this.slot, false, entityLiving.getScale());
	}

	@OnlyIn(Dist.CLIENT)
	public abstract ArmorModelFactory getModelFactory();

	@FunctionalInterface
	public interface ArmorModelFactory {
		CustomArmorModel<LivingEntity> create(EquipmentSlotType slot, boolean isSteve, float scale);
	}

	// ARMOR ITEM CLASS COPY METHODS

	@Override
	public EquipmentSlotType getEquipmentSlot(ItemStack stackIn) {
		if (stackIn.getItem() instanceof CustomArmorItem)
			return slot;

		return null;
	}

	public EquipmentSlotType getSlot() {
		return this.slot;
	}

	public int getEnchantmentValue() {
		return this.material.getEnchantmentValue();
	}

	public IArmorMaterial getMaterial() {
		return this.material;
	}

	public boolean isValidRepairItem(ItemStack p_82789_1_, ItemStack p_82789_2_) {
		return this.material.getRepairIngredient().test(p_82789_2_) || super.isValidRepairItem(p_82789_1_, p_82789_2_);
	}

	public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
		ItemStack itemstack = p_77659_2_.getItemInHand(p_77659_3_);
		EquipmentSlotType equipmentslottype = MobEntity.getEquipmentSlotForItem(itemstack);
		ItemStack itemstack1 = p_77659_2_.getItemBySlot(equipmentslottype);
		if (itemstack1.isEmpty()) {
			p_77659_2_.setItemSlot(equipmentslottype, itemstack.copy());
			itemstack.setCount(0);
			return ActionResult.sidedSuccess(itemstack, p_77659_1_.isClientSide());
		} else {
			return ActionResult.fail(itemstack);
		}
	}

	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType p_111205_1_) {
		return p_111205_1_ == this.slot ? this.defaultModifiers : super.getDefaultAttributeModifiers(p_111205_1_);
	}

	public int getDefense() {
		return material.getDefenseForSlot(slot);
	}

	public float getToughness() {
		return material.getToughness();
	}
}
