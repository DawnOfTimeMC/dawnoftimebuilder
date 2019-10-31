package org.dawnoftimebuilder.items.japanese;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.blocks.IBlockMeta;
import org.dawnoftimebuilder.items.general.DoTBItemMetaBlock;

import javax.annotation.Nullable;
import java.util.List;

import static org.dawnoftimebuilder.blocks.DoTBBlocks.cast_iron_teapot;

public class ItemCastIronTeapot extends DoTBItemMetaBlock {

	private static final IItemPropertyGetter STYLE_PREDICATE = new IItemPropertyGetter() {

		@Override
		@SideOnly(Side.CLIENT)
		public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
			if(stack.hasTagCompound() && stack.getTagCompound().hasKey("style")){
				return stack.getTagCompound().getInteger("style");
			}
			return 0;
		}
	};

	public ItemCastIronTeapot(IBlockMeta block) {
		super(block);
		this.setMaxStackSize(1);

		//Change model, based on style
		this.addPropertyOverride(new ResourceLocation("style"), STYLE_PREDICATE);
	}


	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		/*
		ItemStack stack = player.getHeldItem(hand);
		RayTraceResult raytraceresult = this.rayTrace(world, player, true);
		if(raytraceresult == null) return ActionResult.newResult(EnumActionResult.FAIL, stack);
		BlockPos pos = raytraceresult.getBlockPos();
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("completed")) {
			if(stack.getTagCompound().getBoolean("completed")) {
				// Check if we're trying to fill a cup.
			} else {
				// We're empty, need to fill water.
				if(world.getBlockState(pos).getBlock() == Blocks.WATER) {
					stack.setItemDamage(5);
					return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
				}
			}
		}
		*/
		return super.onItemRightClick( world, player, hand);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		/*
		if(stack.getItemDamage() != 0) {
			if (stack.hasTagCompound() && stack.getTagCompound().hasKey("completed") && stack.getTagCompound().getBoolean("completed")) {
				tooltip.add("[Brewed Tea]");
			} else {
				tooltip.add("[Water]");
			}
		} else {
			tooltip.add("[EMPTY]");
		}
		*/
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		/*
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("style")){
			Integer styleType = stack.getTagCompound().getInteger("style");
			String typeName = BlockCastIronTeapot.EnumType.byMetadata(styleType).getName();
			return this.block.getTranslationKey() + "_" + typeName.toLowerCase();
		}
		*/
		return super.getTranslationKey(stack);
	}
}
