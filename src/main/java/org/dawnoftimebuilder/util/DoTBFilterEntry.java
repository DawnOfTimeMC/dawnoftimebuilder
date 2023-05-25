package org.dawnoftimebuilder.util;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;

import java.util.ArrayList;
import java.util.List;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;
public class DoTBFilterEntry {

    private ResourceLocation tag;
    private String translationKey;
    private ItemStack icon;
    private boolean enabled = false;
    private List<Item> items = new ArrayList<>();

    public DoTBFilterEntry(ResourceLocation tag, ItemStack icon) {
        this.tag = tag;
        this.translationKey = String.format("gui" + ".%s.%s", tag.getNamespace(), tag.getPath().replace("/", "."));
        this.icon = icon;
    }

    public ResourceLocation getTag() {
        return tag;
    }

    public ItemStack getIcon() {
        return this.icon;
    }

    public String getName() {
        return I18n.get(this.translationKey);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void add(Item item) {
        this.items.add(item);
    }

    void add(Block block) {
        this.items.add(block.asItem());
    }

    public void clear() {
        this.items.clear();
    }

    public List<Item> getItems() {
        return this.items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DoTBFilterEntry that = (DoTBFilterEntry) o;
        return this.tag.equals(that.tag);
    }

    @Override
    public int hashCode() {
        return this.tag.hashCode();
    }
}
