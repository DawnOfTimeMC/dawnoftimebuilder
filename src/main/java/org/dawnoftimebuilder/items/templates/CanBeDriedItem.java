package org.dawnoftimebuilder.items.templates;

import net.minecraft.item.Item;
import org.dawnoftimebuilder.items.IItemCanBeDried;

public class CanBeDriedItem extends ItemDoTB implements IItemCanBeDried {
    private final int undriedItemQuantity;
    private final int dryingTime;
    private final Item driedItem;
    private final int driedItemQuantity;

    public CanBeDriedItem(String name, int undriedItemQuantity, int dryingTime, Item driedItem, int driedItemQuantity) {
        super(name);
        this.undriedItemQuantity = undriedItemQuantity;
        this.dryingTime = dryingTime;
        this.driedItem = driedItem;
        this.driedItemQuantity = driedItemQuantity;
    }

    @Override
    public Item getItem() {
        return this;
    }

    @Override
    public int getItemQuantity() {
        return this.undriedItemQuantity;
    }

    @Override
    public int getDryingTime() {
        return this.dryingTime;
    }

    @Override
    public Item getDriedItem() {
        return this.driedItem;
    }

    @Override
    public int getDriedItemQuantity() {
        return this.driedItemQuantity;
    }
}
