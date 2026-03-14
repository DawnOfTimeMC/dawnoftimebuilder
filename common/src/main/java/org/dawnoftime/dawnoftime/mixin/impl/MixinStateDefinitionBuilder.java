package org.dawnoftime.dawnoftime.mixin.impl;

import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import org.dawnoftime.dawnoftime.DoTBCommon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(StateDefinition.Builder.class)
public class MixinStateDefinitionBuilder<O, S extends StateHolder<O, S>> {

    // TODO Remove this in 1.21 (mixin that fixes the incompatibility with DramaticDoors)
    @Inject(method = "add", at = @At("HEAD"))
    private void injectCheckDuplicate(Property<?>[] newProperties, CallbackInfoReturnable<StateDefinition.Builder<O, S>> cir) {
        Map<String, Property<?>> properties = ((StateDefinitionBuilderAccessor<?, ?>) this).getProperties();
        for (Property<?> property : newProperties) {
            String propertyName = property.getName();
            if (properties.containsKey(propertyName)) {
                DoTBCommon.LOG.warn("Duplicate property: {}. This property was only added once, but it can lead to several bugs!", propertyName);
                properties.remove(propertyName);
            }
        }
    }
}
