package org.dawnoftime.dawnoftime.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.dawnoftime.dawnoftime.DoTBCommon;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = DoTBCommon.MOD_ID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Server event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        //TODO: Check if it works with event.includeDev(), if not, find other way to do it.
        generator.addProvider(event.includeDev(), new DoTBBlockTagGenerator(packOutput, lookupProvider));
        generator.addProvider(event.includeDev(), new DoTBItemTagGenerator(packOutput, lookupProvider));
    }
}