package org.dawnoftime.dawnoftime.mixin.impl.client;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.dawnoftime.dawnoftime.client.gui.creative.CreativeInventoryCategories;
import org.dawnoftime.dawnoftime.client.gui.elements.buttons.CategoryButton;
import org.dawnoftime.dawnoftime.client.gui.elements.buttons.GroupButton;
import org.dawnoftime.dawnoftime.client.gui.elements.buttons.SocialsButton;
import org.dawnoftime.dawnoftime.mixin.api.CreativeScreen;
import org.dawnoftime.dawnoftime.registry.DoTBCreativeModeTabsRegistry;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

import static org.dawnoftime.dawnoftime.DoTBCommon.CREATIVE_ICONS;

@SuppressWarnings("unused")
@Debug(print = true)
@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeInventoryMixin extends EffectRenderingInventoryScreen<CreativeModeInventoryScreen.ItemPickerMenu> implements CreativeScreen {
    @Shadow public abstract boolean mouseScrolled(double p_98527_, double p_98528_, double p_98529_);

    @Unique
    private List<CategoryButton> dOTBuilder$buttons;
    @Unique
    private Button dOTBuilder$btnScrollUp;
    @Unique
    private Button dOTBuilder$btnScrollDown;
    @Unique
    private Button dOTBuilder$discord;
    @Unique
    private Button dOTBuilder$curse;
    @Unique
    private Button dOTBuilder$patreon;
    @Unique
    private Button dOTBuilder$github;
    @Unique
    private static int dOTBuilder$selectedCategoryID = 0;
    @Unique
    private static int dOTBuilder$page = 0;
    @Unique
    private boolean dOTBuilder$tabDoTBSelected;
    @Unique
    private final int MAX_PAGE = (int) Math.floor((double) (CreativeInventoryCategories.values().length - 1) / 4);

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

        this.addRenderableWidget(this.dOTBuilder$discord = new SocialsButton(this.leftPos + 200, this.topPos, "discord", button -> dOTBuilder$openLink("https://discord.gg/cteCdn9Hnf")));
        this.dOTBuilder$discord.setTooltip(Tooltip.create(Component.literal("Discord")));
        this.addRenderableWidget(this.dOTBuilder$curse = new SocialsButton(this.leftPos + 200, this.topPos + 35, "curse", button -> dOTBuilder$openLink("https://www.curseforge.com/minecraft/mc-mods/dawn-of-time")));
        this.dOTBuilder$curse.setTooltip(Tooltip.create(Component.literal("Curse Forge")));
        this.addRenderableWidget(this.dOTBuilder$patreon = new SocialsButton(this.leftPos + 200, this.topPos + 70, "patreon", button -> dOTBuilder$openLink("https://www.patreon.com/dawnoftimemod")));
        this.dOTBuilder$patreon.setTooltip(Tooltip.create(Component.literal("Patreon")));
        this.addRenderableWidget(this.dOTBuilder$github = new SocialsButton(this.leftPos + 200, this.topPos + 105, "github", button -> dOTBuilder$openLink("https://github.com/PierreChag/dawnoftimebuilder")));
        this.dOTBuilder$github.setTooltip(Tooltip.create(Component.literal("Github")));

        for(int i = 0; i < 4; i++) {
            this.dOTBuilder$buttons.add(new CategoryButton(this.leftPos - 27, this.topPos + 30 * i, i, button -> {
                CategoryButton categoryButton = (CategoryButton) button;
                if(!categoryButton.isSelected()) {
                    dOTBuilder$buttons.get(dOTBuilder$selectedCategoryID % 4).setSelected(false);
                    categoryButton.setSelected(true);
                    dOTBuilder$selectedCategoryID = categoryButton.getCategoryID();
                    Screen screen1 = Minecraft.getInstance().screen;
                    if(screen1 instanceof CreativeModeInventoryScreen) {
                        this.dOTBuilder$updateItems((CreativeModeInventoryScreen) screen1);
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
        //            dOTBuilder$updateItems((CreativeModeInventoryScreen) (Object) this);

        if (!dOTBuilder$hasSetItemsYet && this.dOTBuilder$tabDoTBSelected) {
            dOTBuilder$updateItems((CreativeModeInventoryScreen) (Object) this);
            dOTBuilder$hasSetItemsYet = true;
        } else if (!this.dOTBuilder$tabDoTBSelected) {
            dOTBuilder$hasSetItemsYet = false;
        }

        dOTBuilder$toggleButtons(this.dOTBuilder$tabDoTBSelected);
    }

    @Unique
    private void dOTBuilder$toggleButtons(boolean val) {
        this.dOTBuilder$btnScrollUp.visible = val;
        this.dOTBuilder$btnScrollDown.visible =  val;
        this.dOTBuilder$discord.visible = val;
        this.dOTBuilder$curse.visible = val;
        this.dOTBuilder$patreon.visible = val;
        this.dOTBuilder$github.visible = val;
        this.dOTBuilder$buttons.forEach(button -> button.visible = val);
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
            Tooltip tt = button.getTooltipForCategory();
            if (tt != null) {
                button.setTooltip(tt);
            }
        });
        this.dOTBuilder$buttons.get(dOTBuilder$selectedCategoryID % 4).setSelected(dOTBuilder$selectedCategoryID - dOTBuilder$page * 4 >= 0 && dOTBuilder$selectedCategoryID - dOTBuilder$page * 4 < 4);
    }

    @Unique
    private void dOTBuilder$updateItems(CreativeModeInventoryScreen screen) {
        this.mouseScrolled(0, 0, Float.MAX_VALUE);
        CreativeModeInventoryScreen.ItemPickerMenu container = screen.getMenu();
        container.items.clear();
        CreativeInventoryCategories.values()[dOTBuilder$selectedCategoryID].getItems().forEach(item -> container.items.add(new ItemStack(item)));
        container.scrollTo(0);
    }

    @Unique
    private void dOTBuilder$openLink(String link) {
        Util.getPlatform().openUri(link);
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