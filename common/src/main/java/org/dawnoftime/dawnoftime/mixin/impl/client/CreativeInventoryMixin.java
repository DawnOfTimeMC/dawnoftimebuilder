package org.dawnoftime.dawnoftime.mixin.impl.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.dawnoftime.dawnoftime.client.gui.creative.CreativeInventoryCategories;
import org.dawnoftime.dawnoftime.client.gui.elements.buttons.CategoryButton;
import org.dawnoftime.dawnoftime.client.gui.elements.buttons.GroupButton;
import org.dawnoftime.dawnoftime.client.gui.elements.buttons.PlaylistButton;
import org.dawnoftime.dawnoftime.client.gui.elements.buttons.SubTabButton;
import org.dawnoftime.dawnoftime.client.patreon.ClientPatronState;
import org.dawnoftime.dawnoftime.client.patreon.PatreonGateHelper;
import org.dawnoftime.dawnoftime.mixin.api.CreativeScreen;
import org.dawnoftime.dawnoftime.registry.DoTBCreativeModeTabsRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.dawnoftime.dawnoftime.DoTBCommon.CREATIVE_ICONS;
import static org.dawnoftime.dawnoftime.DoTBCommon.MOD_ID;

@SuppressWarnings("unused")
@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeInventoryMixin extends EffectRenderingInventoryScreen<CreativeModeInventoryScreen.ItemPickerMenu> implements CreativeScreen {
    @Unique
    private static final ResourceLocation TAB_PATREON = new ResourceLocation(MOD_ID, "textures/gui/tab_patreon.png");
    @Unique
    private static final String PATREON_URL = "https://www.patreon.com/dawnoftimemod";
    @Unique
    private static final String DISCORD_URL = "https://discord.gg/TfSM3qjPUt";
    @Unique
    private static final ResourceLocation SOCIAL_PATREON = new ResourceLocation(MOD_ID, "textures/gui/patreon.png");
    @Unique
    private static final ResourceLocation SOCIAL_DISCORD = new ResourceLocation(MOD_ID, "textures/gui/discord.png");
    @Shadow public abstract boolean mouseScrolled(double p_98527_, double p_98528_, double p_98529_);

    @Unique
    private List<CategoryButton> dOTBuilder$buttons;
    @Unique
    private Button dOTBuilder$btnScrollUp;
    @Unique
    private Button dOTBuilder$btnScrollDown;
    @Unique
    private Button dOT$youtubePlaylist;
    @Unique
    private List<SubTabButton> dOTBuilder$subTabButtons;
    @Unique
    private List<PlaylistButton> dOTBuilder$socialButtons;
    @Unique
    private static int dOTBuilder$selectedCategoryID = 0;
    @Unique
    private static int dOTBuilder$selectedSubTabID = 0;
    @Unique
    private static int dOTBuilder$page = 0;
    @Unique
    private boolean dOTBuilder$tabDoTBSelected;
    @Unique
    private final int MAX_PAGE = (int) Math.floor((double) (CreativeInventoryCategories.values().length - 1) / 4);
    @Unique
    private Set<Item> dOTBuilder$lockedPatreonItems = Set.of();

    protected CreativeInventoryMixin(CreativeModeInventoryScreen.ItemPickerMenu $$0, Inventory $$1, Component $$2) {
        super($$0, $$1, $$2);
    }

    @Override
    public int dOTBuilder$getPage() {
        return dOTBuilder$page;
    }

    @Inject(method = "init", at = @At(value = "TAIL"))
    public void dawnoftimebuilder$init(CallbackInfo ci) {
        this.dOTBuilder$buttons = new ArrayList<>();

        this.addRenderableWidget(this.dOTBuilder$btnScrollUp = new GroupButton(this.leftPos - 22, this.topPos - 22, Component.empty(), button -> {
            if(dOTBuilder$page > 0) {
                dOTBuilder$page--;
                this.dOTBuilder$updateCategoryButtons();
            }
        }, CREATIVE_ICONS, 0, 56));

        this.addRenderableWidget(this.dOTBuilder$btnScrollDown = new GroupButton(this.leftPos - 22, this.topPos + 120, Component.empty(), button -> {
            if(dOTBuilder$page < MAX_PAGE) {
                dOTBuilder$page++;
                this.dOTBuilder$updateCategoryButtons();
            }
        }, CREATIVE_ICONS, 16, 56));

        this.addRenderableWidget(this.dOT$youtubePlaylist = new PlaylistButton(this.leftPos + 156, this.topPos + 4, button -> dOTBuilder$openLink(CreativeInventoryCategories.values()[dOTBuilder$selectedCategoryID].getYoutubePlaylist())));

        this.dOTBuilder$socialButtons = new ArrayList<>();
        PlaylistButton btnPatreon = new PlaylistButton(this.leftPos + 142, this.topPos + 4,
            button -> dOTBuilder$openLink(PATREON_URL), SOCIAL_PATREON);
        btnPatreon.setTooltip(Tooltip.create(Component.translatable("tooltip." + MOD_ID + ".patreon_link")));
        this.dOTBuilder$socialButtons.add(btnPatreon);
        this.addRenderableWidget(btnPatreon);

        PlaylistButton btnDiscord = new PlaylistButton(this.leftPos + 156, this.topPos + 4,
            button -> dOTBuilder$openLink(DISCORD_URL), SOCIAL_DISCORD);
        btnDiscord.setTooltip(Tooltip.create(Component.translatable("tooltip." + MOD_ID + ".discord_link")));
        this.dOTBuilder$socialButtons.add(btnDiscord);
        this.addRenderableWidget(btnDiscord);

        this.dOTBuilder$subTabButtons = new ArrayList<>();
        this.dOTBuilder$buildSubTabButtons((CreativeModeInventoryScreen) (Object) this);

        for(int i = 0; i < 4; i++) {
            this.dOTBuilder$buttons.add(new CategoryButton(this.leftPos - 27, this.topPos + 30 * i, i, button -> {
                CategoryButton categoryButton = (CategoryButton) button;
                if(!categoryButton.isSelected()) {
                    dOTBuilder$buttons.get(dOTBuilder$selectedCategoryID % 4).setSelected(false);
                    categoryButton.setSelected(true);
                    dOTBuilder$selectedCategoryID = categoryButton.getCategoryID();
                    dOTBuilder$selectedSubTabID = 0;
                    Screen screen1 = Minecraft.getInstance().screen;
                    if(screen1 instanceof CreativeModeInventoryScreen screen2) {
                        this.dOTBuilder$buildSubTabButtons(screen2);
                        this.dOTBuilder$updateItems(screen2);
                    }
                }
            }, this));
        }

        for (CategoryButton dOTBuilder$button : this.dOTBuilder$buttons) {
            addRenderableWidget(dOTBuilder$button);
        }
        this.dOTBuilder$updateCategoryButtons();

        if(this.dOTBuilder$tabDoTBSelected) {
            this.dOTBuilder$updateItems((CreativeModeInventoryScreen) (Object) this);
            dOTBuilder$toggleButtons(true);
            this.dOTBuilder$buttons.get(dOTBuilder$selectedCategoryID % 4).setSelected(true);
        } else {
            dOTBuilder$toggleButtons(false);
        }
    }

    @Unique
    private static boolean dOTBuilder$hasSetItemsYet = false;
    @Inject(method = "render", at = @At(value = "HEAD"))
    public void dawnoftimebuilder$render(GuiGraphics $$0, int $$1, int $$2, float $$3, CallbackInfo ci) {
        if (!dOTBuilder$hasSetItemsYet && this.dOTBuilder$tabDoTBSelected) {
            dOTBuilder$updateItems((CreativeModeInventoryScreen) (Object) this);
            dOTBuilder$hasSetItemsYet = true;
        } else if (!this.dOTBuilder$tabDoTBSelected) {
            dOTBuilder$hasSetItemsYet = false;
        }

        dOTBuilder$toggleButtons(this.dOTBuilder$tabDoTBSelected);
    }

    @Inject(method = "render", at = @At(value = "TAIL"))
    public void dawnoftimebuilder$renderPatreonGating(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (!dOTBuilder$tabDoTBSelected || dOTBuilder$lockedPatreonItems.isEmpty()) return;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        for (Slot slot : this.menu.slots) {
            if (!slot.hasItem()) continue;
            if (!dOTBuilder$lockedPatreonItems.contains(slot.getItem().getItem())) continue;
            int slotX = this.leftPos + slot.x;
            int slotY = this.topPos + slot.y;
            guiGraphics.fill(slotX, slotY, slotX + 16, slotY + 16, 0x80111111);
        }
        RenderSystem.disableBlend();
    }

    @Inject(method = "renderBg", at = @At(value = "TAIL"))
    public void dawnoftimebuilder$renderPatreonOverlay(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci) {
        if (this.dOTBuilder$tabDoTBSelected
                && CreativeInventoryCategories.values()[dOTBuilder$selectedCategoryID] == CreativeInventoryCategories.PATREON) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            guiGraphics.blit(TAB_PATREON, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
            RenderSystem.disableBlend();
        }
    }

    @Inject(method = "slotClicked", at = @At(value = "HEAD"), cancellable = true)
    public void dawnoftimebuilder$blockLockedSlotClicked(Slot slot, int slotId, int mouseButton, ClickType clickType, CallbackInfo ci) {
        if (!dOTBuilder$tabDoTBSelected || dOTBuilder$lockedPatreonItems.isEmpty()) return;
        if (slot == null || !slot.hasItem()) return;
        if (dOTBuilder$lockedPatreonItems.contains(slot.getItem().getItem())) {
            ci.cancel();
        }
    }

    @Unique
    private void dOTBuilder$toggleButtons(boolean val) {
        this.dOTBuilder$btnScrollUp.visible = val;
        this.dOTBuilder$btnScrollDown.visible = val;
        boolean isPatreon = CreativeInventoryCategories.values()[dOTBuilder$selectedCategoryID] == CreativeInventoryCategories.PATREON;
        if (!isPatreon) {
            boolean hasPlaylist = CreativeInventoryCategories.values()[dOTBuilder$selectedCategoryID].getYoutubePlaylist() != null;
            this.dOT$youtubePlaylist.visible = val && hasPlaylist;
            if (hasPlaylist) {
                String key = "tooltip." + MOD_ID + ".youtube_" + CreativeInventoryCategories.values()[dOTBuilder$selectedCategoryID].getName();
                this.dOT$youtubePlaylist.setTooltip(Tooltip.create(Component.translatable(key)));
            }
        } else {
            this.dOT$youtubePlaylist.visible = false;
        }
        this.dOTBuilder$buttons.forEach(button -> button.visible = val);
        boolean hasSubTabs = CreativeInventoryCategories.values()[dOTBuilder$selectedCategoryID].hasSubTabs();
        this.dOTBuilder$subTabButtons.forEach(button -> button.visible = val && hasSubTabs);
        this.dOTBuilder$socialButtons.forEach(button -> button.visible = val && isPatreon);
    }

    @Inject(method = "selectTab", at = @At(value = "HEAD"), cancellable = false)
    public void dawnoftimebuilder$selectTab(CreativeModeTab $$0, CallbackInfo ci) {
        dOTBuilder$tabDoTBSelected = $$0 == DoTBCreativeModeTabsRegistry.INSTANCE.DOT_TAB.get();
    }

    @Inject(method = "mouseScrolled", at = @At(value = "HEAD"), cancellable = true)
    public void dawnoftimebuilder$mouseScrolled(double mouseX, double mouseY, double delta, CallbackInfoReturnable<Boolean> cir) {
        int guiLeft = this.leftPos;
        int guiTop = this.topPos;
        int startX = guiLeft - 32;
        int startY = guiTop + 10;
        int endY = startY + 28 * 4 + 3;
        if(mouseX >= startX && mouseX < guiLeft && mouseY >= startY && mouseY < endY) {
            if(delta > 0) {
                this.dOTBuilder$scrollUp();
            } else {
                this.dOTBuilder$scrollDown();
            }
            cir.setReturnValue(true);
        }
    }

    @Unique
    private void dOTBuilder$updateCategoryButtons() {
        this.dOTBuilder$btnScrollUp.active = (dOTBuilder$page > 0);
        this.dOTBuilder$btnScrollDown.active = (dOTBuilder$page < MAX_PAGE);
        this.dOTBuilder$buttons.forEach(button -> {
            button.active = (button.getCategoryID() < CreativeInventoryCategories.values().length);
            if (button.active) {
                Tooltip tt = button.getTooltipForCategory();
                if (tt != null) {
                    button.setTooltip(tt);
                }
            } else {
                button.setTooltip(null);
            }
        });
        this.dOTBuilder$buttons.get(dOTBuilder$selectedCategoryID % 4).setSelected(dOTBuilder$selectedCategoryID - dOTBuilder$page * 4 >= 0 && dOTBuilder$selectedCategoryID - dOTBuilder$page * 4 < 4);
    }

    @Unique
    private void dOTBuilder$updateItems(CreativeModeInventoryScreen screen) {
        this.mouseScrolled(0, 0, Float.MAX_VALUE);
        CreativeModeInventoryScreen.ItemPickerMenu container = screen.getMenu();
        container.items.clear();
        CreativeInventoryCategories.values()[dOTBuilder$selectedCategoryID].getSubTabItems(dOTBuilder$selectedSubTabID).forEach(item -> container.items.add(new ItemStack(item)));
        container.scrollTo(0);

        if (CreativeInventoryCategories.values()[dOTBuilder$selectedCategoryID] == CreativeInventoryCategories.PATREON) {
            dOTBuilder$lockedPatreonItems = PatreonGateHelper.computeLockedItems(
                CreativeInventoryCategories.PATREON.getItems(),
                ClientPatronState.playerTier
            );
        } else {
            dOTBuilder$lockedPatreonItems = Set.of();
        }
    }

    @Unique
    private void dOTBuilder$buildSubTabButtons(CreativeModeInventoryScreen screen) {
        this.dOTBuilder$subTabButtons.forEach(btn -> this.removeWidget(btn));
        this.dOTBuilder$subTabButtons.clear();

        CreativeInventoryCategories category = CreativeInventoryCategories.values()[dOTBuilder$selectedCategoryID];
        List<CreativeInventoryCategories.SubTab> subTabs = category.getSubTabs();
        if (subTabs.isEmpty()) return;

        int count = subTabs.size();
        int y = this.topPos + 4;

        for (int i = 0; i < count; i++) {
            final int subTabIndex = i;
            int x = this.leftPos + 156 - (count - i) * 14;
            CreativeInventoryCategories.SubTab subTab = subTabs.get(i);
            SubTabButton btn = new SubTabButton(
                    x, y,
                    subTab.textureOn(),
                    subTab.textureOff(),
                    subTab.getTooltip(),
                    button -> {
                        dOTBuilder$subTabButtons.forEach(b -> b.setSelected(false));
                        ((SubTabButton) button).setSelected(true);
                        dOTBuilder$selectedSubTabID = subTabIndex;
                        this.dOTBuilder$updateItems(screen);
                    }
            );
            btn.setSelected(i == dOTBuilder$selectedSubTabID);
            this.dOTBuilder$subTabButtons.add(btn);
            this.addRenderableWidget(btn);
        }
    }

    @Unique
    private void dOTBuilder$openLink(String link) {
        if (link != null) {
            Util.getPlatform().openUri(link);
        }
    }

    @Unique
    private void dOTBuilder$scrollUp() {
        if(dOTBuilder$page > 0) {
            dOTBuilder$page--;
            this.dOTBuilder$updateCategoryButtons();
        }
    }

    @Unique
    private void dOTBuilder$scrollDown() {
        if(dOTBuilder$page < MAX_PAGE) {
            dOTBuilder$page++;
            this.dOTBuilder$updateCategoryButtons();
        }
    }
}
