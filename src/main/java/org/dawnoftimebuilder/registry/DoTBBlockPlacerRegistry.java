package org.dawnoftimebuilder.registry;

/*import com.mojang.serialization.Codec;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dawnoftimebuilder.generation.DoTBBlockPlacer;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBBlockPlacerRegistry {
    public static final DeferredRegister<BlockPlacerType<?>> PLACER_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_PLACER_TYPES, MOD_ID);

    public static final RegistryObject<BlockPlacerType<DoTBBlockPlacer>> DOTB_BLOCK_PLACER = reg("dotb_block_placer", DoTBBlockPlacer.CODEC);

    private static <P extends BlockPlacer> RegistryObject<BlockPlacerType<P>> reg(String name, Codec<P> codec) {
        return PLACER_TYPES.register(name, () -> new BlockPlacerType<>(codec));
    }
}*/