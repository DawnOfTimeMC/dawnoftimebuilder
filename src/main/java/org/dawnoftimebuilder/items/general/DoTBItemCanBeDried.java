package org.dawnoftimebuilder.items.general;

import net.minecraft.item.Item;
import org.dawnoftimebuilder.items.IItemCanBeDried;

public class DoTBItemCanBeDried extends DoTBItem implements IItemCanBeDried {

    private int undriedItemQuantity;
    private int dryingTime;
    private Item driedItem;
    private int driedItemQuantity;

    public DoTBItemCanBeDried(String name, int undriedItemQuantity, int dryingTime, Item driedItem, int driedItemQuantity) {
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
