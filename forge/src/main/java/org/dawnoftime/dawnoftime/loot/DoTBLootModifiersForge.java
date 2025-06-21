package org.dawnoftime.dawnoftime.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.dawnoftime.dawnoftime.platform.Services;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class DoTBLootModifiersForge extends LootModifier {

    public static final Supplier<Codec<DoTBLootModifiersForge>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(instance ->
                    codecStart(instance)
                            .and(BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(m -> m.item))
                            .apply(instance, DoTBLootModifiersForge::new)
            )
    );

    private final Item item;

    public DoTBLootModifiersForge(LootItemCondition[] conditionsIn, Item item) {
        super(conditionsIn);
        this.item = item;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, @NotNull LootContext lootContext) {
        for (LootItemCondition condition : this.conditions)
            if (!condition.test(lootContext))
                return generatedLoot;

        if (Services.PLATFORM.getConfig().generateChestLoot && LootTablesToModify.SHOULD_ADD_MAP.getOrDefault(item, false))
            generatedLoot.add(new ItemStack(this.item));

        return generatedLoot;
    }

    @Override
    public @NotNull Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
