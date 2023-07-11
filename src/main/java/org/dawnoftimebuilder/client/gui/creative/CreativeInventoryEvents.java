package org.dawnoftimebuilder.client.gui.creative;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.Util;
import org.dawnoftimebuilder.DawnOfTimeBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class CreativeInventoryEvents {

    public static final ResourceLocation CREATIVE_ICONS = new ResourceLocation(DawnOfTimeBuilder.MOD_ID,
            "textures/gui/creative_icons.png");
    public static final ResourceLocation SOCIAL_ICONS = new ResourceLocation(DawnOfTimeBuilder.MOD_ID,
            "textures/gui/social_icons.png");

    private List<CategoryButton> buttons;
    private Button btnScrollUp;
    private Button btnScrollDown;
    private Button discord;
    private Button curse;
    private Button patreon;
    private Button github;
    private int guiCenterX = 0;
    private int guiCenterY = 0;
    public static int selectedCategoryID = 0;
    public static int page = 0;
    private static boolean tabDoTBSelected;
    private final int MAX_PAGE = (int) Math
            .floor((double) (CreativeInventoryCategories.values().length - 1) / 4);

    /**
     * Called at each opening of a GUI.
     */
    @SubscribeEvent
    public void onScreenInit(final GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof CreativeScreen) {

            CreativeInventoryEvents.tabDoTBSelected = false;
            this.guiCenterX = ((CreativeScreen) event.getGui()).getGuiLeft();
            this.guiCenterY = ((CreativeScreen) event.getGui()).getGuiTop();
            this.buttons = new ArrayList<>();
/*
            event.addWidget(this.btnScrollUp = new GroupButton(this.guiCenterX - 22, this.guiCenterY - 22,
                    StringTextComponent.EMPTY, button -> {
                if (CreativeInventoryEvents.page > 0) {
                    CreativeInventoryEvents.page--;
                    this.updateCategoryButtons();
                }
            }, CreativeInventoryEvents.CREATIVE_ICONS, 0, 56));

            event.addWidget(this.btnScrollDown = new GroupButton(this.guiCenterX - 22, this.guiCenterY + 120,
                    StringTextComponent.EMPTY, button -> {
                if (CreativeInventoryEvents.page < this.MAX_PAGE) {
                    CreativeInventoryEvents.page++;
                    this.updateCategoryButtons();
                }
            }, CreativeInventoryEvents.CREATIVE_ICONS, 16, 56));
*/
            event.addWidget(this.discord = new SocialsButton(this.guiCenterX + 200, this.guiCenterY, "discord", button -> openLink("https://discord.gg/cteCdn9Hnf")));
            event.addWidget(this.curse = new SocialsButton(this.guiCenterX + 200, this.guiCenterY + 35, "curse", button -> openLink("https://www.curseforge.com/minecraft/mc-mods/dawn-of-time")));
            event.addWidget(this.patreon = new SocialsButton(this.guiCenterX + 200, this.guiCenterY + 70, "patreon", button -> openLink("https://www.patreon.com/dawnoftimemod")));
            event.addWidget(this.github = new SocialsButton(this.guiCenterX + 200, this.guiCenterY + 105, "github", button -> openLink("https://github.com/PierreChag/dawnoftimebuilder")));

            for (int i = 0; i < 4; i++) {
                this.buttons.add(new CategoryButton(this.guiCenterX - 27, this.guiCenterY + 30 * i, i, button -> {
                    final CategoryButton categoryButton = (CategoryButton) button;
                    if (!categoryButton.isSelected()) {
                        this.buttons.get(CreativeInventoryEvents.selectedCategoryID % 4).setSelected(false);
                        categoryButton.setSelected(true);
                        CreativeInventoryEvents.selectedCategoryID = categoryButton.getCategoryID();
                        @SuppressWarnings("resource") final Screen screen = Minecraft.getInstance().screen;
                        if (screen instanceof CreativeScreen) {
                            this.updateItems((CreativeScreen) screen);
                        }
                    }
                }));
            }
            this.buttons.forEach(event::addWidget);
            this.updateCategoryButtons();

            final CreativeScreen screen = (CreativeScreen) event.getGui();
            if (screen.getSelectedTab() == DawnOfTimeBuilder.DOTB_TAB.getId()) {
                this.btnScrollUp.visible = true;
                this.btnScrollDown.visible = true;
                this.discord.visible = true;
                this.curse.visible = true;
                this.patreon.visible = true;
                this.github.visible = true;
                CreativeInventoryEvents.tabDoTBSelected = true;
                this.updateCategoryButtons();
                this.buttons.forEach(button -> button.visible = true);
                this.buttons.get(CreativeInventoryEvents.selectedCategoryID % 4).setSelected(true);
                this.updateItems(screen);
            } else {
                this.btnScrollUp.visible = false;
                this.btnScrollDown.visible = false;
                this.discord.visible = false;
                this.curse.visible = false;
                this.patreon.visible = false;
                this.github.visible = false;
                this.buttons.forEach(button -> button.visible = false);
            }
        }
    }

    /**
     * Called at each tick, after onScreenDrawPre, when a GUI is open
     */
    @SubscribeEvent
    public void onScreenDrawPost(final GuiScreenEvent.DrawScreenEvent.Post event) {
        if (event.getGui() instanceof CreativeScreen) {
            final CreativeScreen screen = (CreativeScreen) event.getGui();
            this.guiCenterX = screen.getGuiLeft();
            this.guiCenterY = screen.getGuiTop();

            if (screen.getSelectedTab() == DawnOfTimeBuilder.DOTB_TAB.getId()) {
                CreativeInventoryEvents.tabDoTBSelected = true;
                this.btnScrollUp.visible = true;
                this.btnScrollDown.visible = true;
                this.discord.visible = true;
                this.curse.visible = true;
                this.patreon.visible = true;
                this.github.visible = true;
                this.buttons.forEach(button -> button.visible = true);

                // Render tooltips after so it renders above buttons
                this.buttons.forEach(button -> {
                    if (button.isMouseOver(event.getMouseX(), event.getMouseY())) {
                        screen.renderTooltip(event.getMatrixStack(),
                                CreativeInventoryCategories.values()[button.getCategoryID()].getTranslation(),
                                event.getMouseX(), event.getMouseY());
                    }
                });

            } else if (CreativeInventoryEvents.tabDoTBSelected) {
                this.btnScrollUp.visible = false;
                this.btnScrollDown.visible = false;
                this.discord.visible = false;
                this.curse.visible = false;
                this.patreon.visible = false;
                this.github.visible = false;
                this.buttons.forEach(button -> button.visible = false);
                CreativeInventoryEvents.tabDoTBSelected = false;
            }
        }
    }

    private void updateCategoryButtons() {
        this.btnScrollUp.active = CreativeInventoryEvents.page > 0;
        this.btnScrollDown.active = CreativeInventoryEvents.page < this.MAX_PAGE;
        this.buttons.forEach(
                button -> button.active = button.getCategoryID() < CreativeInventoryCategories.values().length);
        this.buttons.get(CreativeInventoryEvents.selectedCategoryID % 4)
                .setSelected(CreativeInventoryEvents.selectedCategoryID - CreativeInventoryEvents.page * 4 >= 0
                        && CreativeInventoryEvents.selectedCategoryID - CreativeInventoryEvents.page * 4 < 4);
    }

    private void updateItems(final CreativeScreen screen) {
        final CreativeScreen.CreativeContainer container = screen.getMenu();
        container.items.clear();
        CreativeInventoryCategories.values()[CreativeInventoryEvents.selectedCategoryID].getItems()
                .forEach(item -> container.items.add(new ItemStack(item)));
        container.scrollTo(0);
    }

    private void openLink(String link) {
        Util.getPlatform().openUri(link);
    }
}