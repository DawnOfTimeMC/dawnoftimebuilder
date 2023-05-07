package org.dawnoftimebuilder.client.gui.filters;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.dawnoftimebuilder.DawnOfTimeBuilder;
import org.dawnoftimebuilder.util.DoTBFilterEntry;
import org.dawnoftimebuilder.util.filters.TabHelper;
import org.lwjgl.glfw.GLFW;

import java.util.*;
import java.util.stream.Collectors;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class CreativeInventoryFilters {

    private static final ResourceLocation ICONS = new ResourceLocation(MOD_ID, "textures/gui/icons.png");
    private static final Map<ItemGroup, Integer> scrollMap = new HashMap<>();
    private boolean updatedFilters;
    private final List<TagButton> buttons = new ArrayList<>();
    private final Map<ItemGroup, DoTBFilterEntry> miscFilterMap = new HashMap<>();
    private IconButton btnScrollUp, btnScrollDown;
    private DoTBFilterEntry selectedEntry;
    public boolean noFilters;

    @SubscribeEvent
    public void onPlayerLogout(ClientPlayerNetworkEvent.LoggedOutEvent event) {
        this.updatedFilters = false;
    }

    @SubscribeEvent
    public void onScreenInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof CreativeScreen) {
            if (!this.updatedFilters) {
                this.updateFilters();
                this.updatedFilters = true;
            }

            int guiCenterX = ((CreativeScreen) event.getGui()).getGuiLeft();
            int guiCenterY = ((CreativeScreen) event.getGui()).getGuiTop();

            event.addWidget(this.btnScrollUp = new IconButton(new TranslationTextComponent("gui.button.filters.scroll_filters_up"),
                    guiCenterX - 22, guiCenterY - 12, 16, 16, 0, 0, 0, ICONS, button -> scrollUp()));

            event.addWidget(this.btnScrollDown = new IconButton(new TranslationTextComponent("gui.button.filters.scroll_filters_down"),
                    guiCenterX - 22, guiCenterY + 127, 16, 16, 16, 0, 0, ICONS, button -> scrollDown()));

            this.hideButtons();

            CreativeScreen screen = (CreativeScreen) event.getGui();
            this.updateTagButtons(screen);

            if (TabHelper.getHasFilters(event)) {
                this.showButtons();
                this.updateItems(screen);
            }
        }
    }

    @SubscribeEvent
    public void onScreenClick(GuiScreenEvent.MouseClickedEvent.Pre event) {
        if (event.getButton() != GLFW.GLFW_MOUSE_BUTTON_LEFT) return;
        if (event.getGui() instanceof CreativeScreen) {
            for (TagButton button : this.buttons) {
                if (button.isMouseOver(event.getMouseX(), event.getMouseY())) {
                    if (button.mouseReleased(event.getMouseX(), event.getMouseY(), event.getButton())) {
                        DoTBFilterEntry entry = button.getFilter();
                        if (selectedEntry != null && entry != selectedEntry) {
                            selectedEntry.setEnabled(false);
                            for (TagButton oldButton : this.buttons) {
                                if (oldButton.getFilter() == selectedEntry) {
                                    oldButton.updateState();
                                    break;
                                }
                            }
                        }
                        entry.setEnabled(!entry.isEnabled());
                        button.updateState();
                        updateItems(((CreativeScreen) event.getGui()));

                        selectedEntry = entry;
                        return;
                    }
                }
            }
        }
    }

    public void onCreativeTabChange(CreativeScreen screen, ItemGroup group) {
        if (TabHelper.getHasFilters(group)) {
            noFilters = true;
            this.updateItems(screen);
        } else {
            noFilters = false;
        }
        Screen creativeScreen = Minecraft.getInstance().screen;
        if (creativeScreen instanceof CreativeScreen) {
            screen = (CreativeScreen) creativeScreen;
            this.updateTagButtons(screen);
        }
    }

    @SubscribeEvent
    public void onScreenDrawPre(GuiScreenEvent.DrawScreenEvent.Pre event) {
        if (event.getGui() instanceof CreativeScreen) {
            if (!TabHelper.getHasFilters(event)) {
                CreativeScreen screen = (CreativeScreen) event.getGui();
                this.updateItems(screen);
            }
        }
    }

    @SubscribeEvent
    public void onScreenDrawBackground(GuiContainerEvent.DrawBackground event) {
        if (event.getGuiContainer() instanceof CreativeScreen) {
            CreativeScreen screen = (CreativeScreen) event.getGuiContainer();
            ItemGroup group = TabHelper.getGroup(screen.getSelectedTab());
            if (DawnOfTimeBuilder.get().hasFilters(group)) {
                this.buttons.forEach(TagButton::renderButton);
            }
        }
    }

    @SubscribeEvent
    public void onScreenDrawPost(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (event.getGui() instanceof CreativeScreen) {
            CreativeScreen screen = (CreativeScreen) event.getGui();
            ItemGroup group = TabHelper.getGroup(screen.getSelectedTab());

            if (DawnOfTimeBuilder.get().hasFilters(group)) {
                this.buttons.forEach(button ->
                {
                    if (button.isMouseOver(event.getMouseX(), event.getMouseY())) {
                        screenRenderToolTip(screen, button.getFilter().getName(), event.getMouseX(), event.getMouseY());
                    }
                });

            }
        }
    }

    private void screenRenderToolTip(CreativeScreen screen, String name, int mouseX, int mouseY) {
        screen.renderTooltip(new MatrixStack(), new StringTextComponent(name), mouseX, mouseY);
    }

    @SubscribeEvent
    public void onMouseScroll(GuiScreenEvent.MouseScrollEvent.Pre event) {
        if (event.getGui() instanceof CreativeScreen) {
            CreativeScreen creativeScreen = (CreativeScreen) event.getGui();
            int guiLeft = creativeScreen.getGuiLeft();
            int guiTop = creativeScreen.getGuiTop();
            int startX = guiLeft - 32;
            int startY = guiTop + 10;
            //noinspection UnnecessaryLocalVariable
            int endX = guiLeft;
            int endY = startY + 28 * 4 + 3;
            if (event.getMouseX() >= startX && event.getMouseX() < endX && event.getMouseY() >= startY && event.getMouseY() < endY) {
                if (event.getScrollDelta() > 0) {
                    this.scrollUp();
                } else {
                    this.scrollDown();
                }
                event.setCanceled(true);
            }
        }
    }

    private void updateTagButtons(CreativeScreen screen) {
        if (!this.updatedFilters)
            return;

        this.buttons.clear();
        ItemGroup group = TabHelper.getGroup(screen.getSelectedTab());
        if (DawnOfTimeBuilder.get().hasFilters(group)) {
            List<DoTBFilterEntry> entries = this.getFilters(group);
            int scroll = scrollMap.computeIfAbsent(group, group1 -> 0);
            for (int i = scroll; i < scroll + 4 && i < entries.size(); i++) {
                TagButton button = new TagButton(screen.getGuiLeft() - 28, screen.getGuiTop() + 29 * (i - scroll) + 10, entries.get(i), button1 -> {
                    TagButton tagButton = (TagButton) button1;
                    if (!tagButton.isSelected()) {
                        tagButton.setSelected(true);
                        Screen screen1 = Minecraft.getInstance().screen;
                        if (screen1 instanceof CreativeScreen) {
                            if (tagButton.isSelected()) {
                                this.updateItems(screen);
                            }
                        }
                    }
                });
                this.buttons.add(button);
            }


            this.btnScrollUp.setEnabled(scroll > 0);
            this.btnScrollDown.setEnabled(scroll <= entries.size() - 4 - 1);
            this.buttons.forEach(button -> button.active = (scrollMap.size() < getFilters(group).size()));
            this.showButtons();


        } else {
            this.hideButtons();
        }
    }

    private void updateItems(CreativeScreen screen) {
        CreativeScreen.CreativeContainer container = screen.getMenu();
        List<Item> filteredItems = new ArrayList<>();
        ItemGroup group = TabHelper.getGroup(screen.getSelectedTab());
        if (group != null) {
            if (DawnOfTimeBuilder.get().hasFilters(group)) {
                List<DoTBFilterEntry> entries = DawnOfTimeBuilder.get().getFilters(group);
                if (entries != null) {
                    boolean filterEnabled = false;
                    for (DoTBFilterEntry filter : this.getFilters(group)) {
                        if (filter.isEnabled()) {
                            filteredItems.addAll(filter.getItems());
                            filterEnabled = true;
                        }
                    }
                    if (!filterEnabled) {
                        for (DoTBFilterEntry filter : this.getFilters(group)) {
                            filteredItems.addAll(filter.getItems());
                        }
                    }

                    container.items.clear();

                    filteredItems.forEach(item -> item.fillItemCategory(group, container.items));
                    container.items.sort(Comparator.comparing(o -> Item.getId(o.getItem())));
                    container.scrollTo(0);
                }
            }
        }
    }


    private void updateFilters() {
        for (ItemGroup group : DawnOfTimeBuilder.get().getGroups()) {
            List<DoTBFilterEntry> entries = DawnOfTimeBuilder.get().getFilters(group);
            entries.forEach(DoTBFilterEntry::clear);

            Set<Item> removed = new HashSet<>();
            List<Item> items = ForgeRegistries.ITEMS.getValues().stream()
                    .filter(item -> item.getItemCategory() == group || item == Items.ENCHANTED_BOOK)
                    .collect(Collectors.toList());
            items.forEach(item ->
            {
                for (ResourceLocation location : item.getTags()) {
                    for (DoTBFilterEntry filter : entries) {
                        if (location.equals(filter.getTag())) {
                            filter.add(item);
                            removed.add(item);
                        }
                    }
                }
            });
            items.removeAll(removed);

            if (group.getEnchantmentCategories().length == 0) {
                items.remove(Items.ENCHANTED_BOOK);
            }

            if (!items.isEmpty()) {
                DoTBFilterEntry entry = new DoTBFilterEntry(new ResourceLocation("miscellaneous"), new ItemStack(Blocks.BARRIER));
                items.forEach(entry::add);
                this.miscFilterMap.put(group, entry);
            }
        }
    }

    private List<DoTBFilterEntry> getFilters(ItemGroup group) {
        if (DawnOfTimeBuilder.get().hasFilters(group)) {
            List<DoTBFilterEntry> filters = new ArrayList<>(DawnOfTimeBuilder.get().getFilters(group));
            if (this.miscFilterMap.containsKey(group)) {
                filters.add(this.miscFilterMap.get(group));
            }
            return filters;
        }
        return Collections.emptyList();
    }

    private void showButtons() {
        this.btnScrollUp.setActive(true);
        this.btnScrollDown.setActive(true);
        this.buttons.forEach(button -> button.setActive(true));
    }

    private void hideButtons() {
        this.btnScrollUp.setActive(false);
        this.btnScrollDown.setActive(false);
        this.buttons.forEach(button -> button.setActive(false));
    }

    private void scrollUp() {
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof CreativeScreen) {
            CreativeScreen creativeScreen = (CreativeScreen) screen;
            ItemGroup group = TabHelper.getGroup(creativeScreen.getSelectedTab());
            int scroll = scrollMap.computeIfAbsent(group, group1 -> 0);
            if (scroll > 0) {
                scrollMap.put(group, scroll - 1);
                this.updateTagButtons(creativeScreen);
            }
        }
    }

    private void scrollDown() {
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof CreativeScreen) {
            CreativeScreen creativeScreen = (CreativeScreen) screen;
            ItemGroup group = TabHelper.getGroup(creativeScreen.getSelectedTab());
            List<DoTBFilterEntry> entries = this.getFilters(group);
            int scroll = scrollMap.computeIfAbsent(group, group1 -> 0);
            if (scroll <= entries.size() - 4 - 1) {
                scrollMap.put(group, scroll + 1);
                this.updateTagButtons(creativeScreen);
            }
        }
    }
}
