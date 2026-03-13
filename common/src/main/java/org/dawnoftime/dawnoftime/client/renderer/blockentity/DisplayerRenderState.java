package org.dawnoftime.dawnoftime.client.renderer.blockentity;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

import java.util.ArrayList;
import java.util.List;

public class DisplayerRenderState extends BlockEntityRenderState {
    public final List<ItemStackRenderState> items = new ArrayList<>(9);
}