package org.dawnoftime.dawnoftime.client.patreon;

import net.minecraft.world.item.Item;
import org.dawnoftime.dawnoftime.client.gui.creative.CreativeInventoryCategories;
import org.dawnoftime.dawnoftime.registry.DoTBItemsRegistry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PatreonGateHelper {

    private static Item[] cachedTokens = null;

    private static Item[] getTokens() {
        if (cachedTokens == null) {
            cachedTokens = new Item[]{
                null,
                DoTBItemsRegistry.INSTANCE.PATREON_TIER_1.get(),
                DoTBItemsRegistry.INSTANCE.PATREON_TIER_2.get(),
                DoTBItemsRegistry.INSTANCE.PATREON_TIER_3.get(),
                DoTBItemsRegistry.INSTANCE.PATREON_TIER_4.get(),
                DoTBItemsRegistry.INSTANCE.PATREON_TIER_5.get(),
                DoTBItemsRegistry.INSTANCE.PATREON_TIER_6.get()
            };
        }
        return cachedTokens;
    }

    // Returns 1-6 if item is a tier token, 0 otherwise
    static int getTokenTier(Item item) {
        Item[] tokens = getTokens();
        for (int i = 1; i <= 6; i++) {
            if (item == tokens[i]) return i;
        }
        return 0;
    }

    public static Set<Item> computeLockedItems(List<Item> patreonItems, int playerTier) {
        Set<Item> locked = new HashSet<>();
        int currentTier = 0;
        for (Item item : patreonItems) {
            int t = getTokenTier(item);
            if (t > 0) currentTier = t;
            if (currentTier > playerTier) locked.add(item);
        }
        return locked;
    }

    // Returns the tier (1-6) the given item belongs to in the patreon list, 0 if not found
    public static int getItemTier(Item item) {
        List<Item> items = CreativeInventoryCategories.PATREON.getItems();
        int currentTier = 0;
        for (Item i : items) {
            int t = getTokenTier(i);
            if (t > 0) currentTier = t;
            if (i == item) return currentTier;
        }
        return 0;
    }
}
