package org.dawnoftime.dawnoftime.mixin.impl;

import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(StateDefinition.Builder.class)
public interface StateDefinitionBuilderAccessor<O, S extends StateHolder<O, S>> {
    @Accessor("properties")
    Map<String, Property<?>> getProperties();
}
