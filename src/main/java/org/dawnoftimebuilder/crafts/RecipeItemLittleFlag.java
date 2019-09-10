package org.dawnoftimebuilder.crafts;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import org.dawnoftimebuilder.items.japanese.ItemLittleFlag;

public class RecipeItemLittleFlag extends RecipeBase{
	
	RecipeItemLittleFlag(){
		this.setRegistryName("little_flag");
	}

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting inv, World worldIn){
        ItemStack itemstack = ItemStack.EMPTY;
        List<ItemStack> list = Lists.newArrayList();

        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemstack1 = inv.getStackInSlot(i);

            if (!itemstack1.isEmpty()) {
                if (itemstack1.getItem() instanceof ItemLittleFlag) itemstack = itemstack1;
                else{
                    if (itemstack1.getItem() != Items.DYE) return false;
                    list.add(itemstack1);
                }
            }
        }

        return !itemstack.isEmpty() && !list.isEmpty();
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting inv){
        ItemStack itemstack = ItemStack.EMPTY;
        int[] aint = new int[3];
        int j = 0;
        ItemLittleFlag itemarmor = null;

        for (int k = 0; k < inv.getSizeInventory(); ++k) {
            ItemStack itemstack1 = inv.getStackInSlot(k);

            if (!itemstack1.isEmpty()) {
                if (itemstack1.getItem() instanceof ItemLittleFlag) {
                    itemarmor = (ItemLittleFlag)itemstack1.getItem();

                    if (!itemstack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }

                    itemstack = itemstack1.copy();
                    itemstack.setCount(1);


                    int l = itemarmor.getItemColor(itemstack);
                    float f = (float)(l >> 16 & 255) / 255.0F;
                    float f1 = (float)(l >> 8 & 255) / 255.0F;
                    float f2 = (float)(l & 255) / 255.0F;
                    aint[0] = (int)((float)aint[0] + f * 255.0F);
                    aint[1] = (int)((float)aint[1] + f1 * 255.0F);
                    aint[2] = (int)((float)aint[2] + f2 * 255.0F);
                    ++j;
                } else {
                    if (itemstack1.getItem() != Items.DYE) return ItemStack.EMPTY;

                    float[] afloat = EnumDyeColor.byDyeDamage(itemstack1.getMetadata()).getColorComponentValues();
                    int l1 = (int)(afloat[0] * 255.0F);
                    int i2 = (int)(afloat[1] * 255.0F);
                    int j2 = (int)(afloat[2] * 255.0F);
                    aint[0] += l1;
                    aint[1] += i2;
                    aint[2] += j2;
                    ++j;
                }
            }
        }

        if (itemarmor == null) return ItemStack.EMPTY;
        else {
            int i1 = aint[0] / j;
            int j1 = aint[1] / j;
            int k1 = aint[2] / j;
            int k2 = (i1 << 8) + j1;
            k2 = (k2 << 8) + k1;
            itemarmor.setColor(itemstack, k2);
            return itemstack;
        }
    }

    public ItemStack getRecipeOutput()
    {
        return ItemStack.EMPTY;
    }

    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            nonnulllist.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
        }

        return nonnulllist;
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    public boolean canFit(int width, int height)
    {
        return width * height >= 2;
    }

}