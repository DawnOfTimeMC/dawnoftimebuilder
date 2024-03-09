package org.dawnoftimebuilder.client.gui.creative;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.dawnoftimebuilder.HandlerClient;
import org.dawnoftimebuilder.client.gui.elements.buttons.CategoryButton;
import org.dawnoftimebuilder.client.gui.elements.buttons.GroupButton;
import org.dawnoftimebuilder.client.gui.elements.buttons.SocialsButton;

import java.util.ArrayList;
import java.util.List;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class CreativeInventoryEvents {
    public static final ResourceLocation CREATIVE_ICONS = new ResourceLocation(MOD_ID, "textures/gui/creative_icons.png");
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
    private final int MAX_PAGE = (int) Math.floor((double) (CreativeInventoryCategories.values().length - 1) / 4);

    /**
     * Called at each opening of a GUI.
     */
    @SubscribeEvent
    public void onScreenInit(ScreenEvent.Init.Post event) {
        if(event.getScreen() instanceof CreativeModeInventoryScreen) {

            this.guiCenterX = ((CreativeModeInventoryScreen) event.getScreen()).getGuiLeft();
            this.guiCenterY = ((CreativeModeInventoryScreen) event.getScreen()).getGuiTop();
            this.buttons = new ArrayList<>();

            event.addListener(this.btnScrollUp = new GroupButton(this.guiCenterX - 22, this.guiCenterY - 22, Component.empty(), button -> {
                if(page > 0) {
                    page--;
                    this.updateCategoryButtons();
                }
            }, CREATIVE_ICONS, 0, 56));

            event.addListener(this.btnScrollDown = new GroupButton(this.guiCenterX - 22, this.guiCenterY + 120, Component.empty(), button -> {
                if(page < MAX_PAGE) {
                    page++;
                    this.updateCategoryButtons();
                }
            }, CREATIVE_ICONS, 16, 56));

            event.addListener(this.discord = new SocialsButton(this.guiCenterX + 200, this.guiCenterY, "discord", button -> openLink("https://discord.gg/cteCdn9Hnf")));
            event.addListener(this.curse = new SocialsButton(this.guiCenterX + 200, this.guiCenterY + 35, "curse", button -> openLink("https://www.curseforge.com/minecraft/mc-mods/dawn-of-time")));
            event.addListener(this.patreon = new SocialsButton(this.guiCenterX + 200, this.guiCenterY + 70, "patreon", button -> openLink("https://www.patreon.com/dawnoftimemod")));
            event.addListener(this.github = new SocialsButton(this.guiCenterX + 200, this.guiCenterY + 105, "github", button -> openLink("https://github.com/PierreChag/dawnoftimebuilder")));

            for(int i = 0; i < 4; i++) {
                this.buttons.add(new CategoryButton(this.guiCenterX - 27, this.guiCenterY + 30 * i, i, button -> {
                    CategoryButton categoryButton = (CategoryButton) button;
                    if(!categoryButton.isSelected()) {
                        buttons.get(selectedCategoryID % 4).setSelected(false);
                        categoryButton.setSelected(true);
                        selectedCategoryID = categoryButton.getCategoryID();
                        Screen screen = Minecraft.getInstance().screen;
                        if(screen instanceof CreativeModeInventoryScreen) {
                            this.updateItems((CreativeModeInventoryScreen) screen);
                        }
                    }
                }));
            }

            this.buttons.forEach(event::addListener);
            this.updateCategoryButtons();

            CreativeModeInventoryScreen screen = (CreativeModeInventoryScreen) event.getScreen();
            if(HandlerClient.isDotSelected()) {
                this.updateItems(screen);
                this.btnScrollUp.visible = true;
                this.btnScrollDown.visible = true;
                this.discord.visible = true;
                this.curse.visible = true;
                this.patreon.visible = true;
                this.github.visible = true;
                tabDoTBSelected = true;
                this.buttons.forEach(button -> button.visible = true);
                this.buttons.get(selectedCategoryID % 4).setSelected(true);
            } else {
                this.btnScrollUp.visible = false;
                this.btnScrollDown.visible = false;
                this.discord.visible = false;
                this.curse.visible = false;
                this.patreon.visible = false;
                this.github.visible = false;
                tabDoTBSelected = false;
                this.buttons.forEach(button -> button.visible = false);
            }
        }
    }

    @SubscribeEvent
    public void onScreenDrawPre(ScreenEvent.Render.Pre event) {
        if(event.getScreen() instanceof CreativeModeInventoryScreen screen) {
            if(HandlerClient.isDotSelected()) {
                if(!tabDoTBSelected) {
                    updateItems(screen);
                }
            } else {
                tabDoTBSelected = false;
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
    public void onScreenDrawPost(ScreenEvent.Render.Post event) {
        if(event.getScreen() instanceof CreativeModeInventoryScreen screen) {
            this.guiCenterX = screen.getGuiLeft();
            this.guiCenterY = screen.getGuiTop();

            if(HandlerClient.isDotSelected()) {
                this.btnScrollUp.visible = true;
                this.btnScrollDown.visible = true;
                this.discord.visible = true;
                this.curse.visible = true;
                this.patreon.visible = true;
                this.github.visible = true;
                tabDoTBSelected = true;

                this.buttons.forEach(button -> button.visible = true);

                // Render tooltips after so it renders above buttons
                this.buttons.forEach(button -> {
                    if(button.isMouseOver(event.getMouseX(), event.getMouseY())) {
                        event.getGuiGraphics().renderTooltip(Minecraft.getInstance().font, CreativeInventoryCategories.values()[button.getCategoryID()].getTranslation(), event.getMouseX(), event.getMouseY());
                    }
                });
            } else if(tabDoTBSelected) {
                this.btnScrollUp.visible = false;
                this.btnScrollDown.visible = false;
                this.discord.visible = false;
                this.curse.visible = false;
                this.patreon.visible = false;
                this.github.visible = false;
                tabDoTBSelected = false;

                this.buttons.forEach(button -> button.visible = false);
            }
        }
    }

    @SubscribeEvent
    public void onScreenDrawBackground(ScreenEvent.BackgroundRendered event) {
        if(event.getScreen() instanceof CreativeModeInventoryScreen screen) {
            if(HandlerClient.isDotSelected()) {
                if(!tabDoTBSelected) {
                    updateItems(screen);
                    tabDoTBSelected = true;
                }
            } else {
                tabDoTBSelected = false;
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

    private void updateCategoryButtons() {
        this.btnScrollUp.active = (page > 0);
        this.btnScrollDown.active = (page < MAX_PAGE);
        this.buttons.forEach(button -> button.active = (button.getCategoryID() < CreativeInventoryCategories.values().length));
        this.buttons.get(selectedCategoryID % 4).setSelected(selectedCategoryID - page * 4 >= 0 && selectedCategoryID - page * 4 < 4);
    }

    private void updateItems(CreativeModeInventoryScreen screen) {
        screen.mouseScrolled(0, 0, Float.MAX_VALUE);
        CreativeModeInventoryScreen.ItemPickerMenu container = screen.getMenu();
        container.items.clear();
        CreativeInventoryCategories.values()[selectedCategoryID].getItems().forEach(item -> container.items.add(new ItemStack(item)));
        container.scrollTo(0);
    }

    private void openLink(String link) {
        Util.getPlatform().openUri(link);
    }

    @SubscribeEvent
    public void onMouseScroll(ScreenEvent.MouseScrolled.Pre event) {
        if(event.getScreen() instanceof CreativeModeInventoryScreen screen) {
            int guiLeft = screen.getGuiLeft();
            int guiTop = screen.getGuiTop();
            int startX = guiLeft - 32;
            int startY = guiTop + 10;
            //noinspection UnnecessaryLocalVariable
            int endX = guiLeft;
            int endY = startY + 28 * 4 + 3;
            if(event.getMouseX() >= startX && event.getMouseX() < endX && event.getMouseY() >= startY && event.getMouseY() < endY) {
                if(event.getScrollDelta() > 0) {
                    this.scrollUp();
                } else {
                    this.scrollDown();
                }
                event.setCanceled(true);
            }
        }
    }

    private void scrollUp() {
        Screen screen = Minecraft.getInstance().screen;
        if(screen instanceof CreativeModeInventoryScreen) {
            if(page > 0) {
                page--;
                this.updateCategoryButtons();
            }
        }
    }

    private void scrollDown() {
        Screen screen = Minecraft.getInstance().screen;
        if(screen instanceof CreativeModeInventoryScreen) {
            if(page < MAX_PAGE) {
                page++;
                this.updateCategoryButtons();
            }
        }
    }
}