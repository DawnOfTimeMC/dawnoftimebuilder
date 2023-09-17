package org.dawnoftimebuilder.client.gui.creative;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.dawnoftimebuilder.client.gui.elements.buttons.CategoryButton;
import org.dawnoftimebuilder.client.gui.elements.buttons.GroupButton;
import org.dawnoftimebuilder.client.gui.elements.buttons.SocialsButton;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class CreativeInventoryEvents {

    public static final ResourceLocation CREATIVE_ICONS = new ResourceLocation(MOD_ID, "textures/gui/creative_icons.png");
    public static final ResourceLocation SOCIAL_ICONS = new ResourceLocation(MOD_ID, "textures/gui/social_icons.png");
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
    public void onScreenInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof CreativeScreen) {

            this.guiCenterX = ((CreativeScreen) event.getGui()).getGuiLeft();
            this.guiCenterY = ((CreativeScreen) event.getGui()).getGuiTop();
            this.buttons = new ArrayList<>();

            event.addWidget(this.btnScrollUp = new GroupButton(this.guiCenterX - 22, this.guiCenterY - 22, StringTextComponent.EMPTY, button -> {
                if (page > 0) {
                    page--;
                    this.updateCategoryButtons();
                }
            }, CREATIVE_ICONS, 0, 56));

            event.addWidget(this.btnScrollDown = new GroupButton(this.guiCenterX - 22, this.guiCenterY + 120, StringTextComponent.EMPTY, button -> {
                if (page < MAX_PAGE) {
                    page++;
                    this.updateCategoryButtons();
                }
            }, CREATIVE_ICONS, 16, 56));

            event.addWidget(this.discord = new SocialsButton(this.guiCenterX + 200, this.guiCenterY, "discord", button -> openLink("https://discord.gg/cteCdn9Hnf")));
            event.addWidget(this.curse = new SocialsButton(this.guiCenterX + 200, this.guiCenterY + 35, "curse", button -> openLink("https://www.curseforge.com/minecraft/mc-mods/dawn-of-time")));
            event.addWidget(this.patreon = new SocialsButton(this.guiCenterX + 200, this.guiCenterY + 70, "patreon", button -> openLink("https://www.patreon.com/dawnoftimemod")));
            event.addWidget(this.github = new SocialsButton(this.guiCenterX + 200, this.guiCenterY + 105, "github", button -> openLink("https://github.com/PierreChag/dawnoftimebuilder")));

            for (int i = 0; i < 4; i++) {
                this.buttons.add(new CategoryButton(this.guiCenterX - 27, this.guiCenterY + 30 * i, i, button -> {
                    CategoryButton categoryButton = (CategoryButton) button;
                    if (!categoryButton.isSelected()) {
                        buttons.get(selectedCategoryID % 4).setSelected(false);
                        categoryButton.setSelected(true);
                        selectedCategoryID = categoryButton.getCategoryID();
                        Screen screen = Minecraft.getInstance().screen;
                        if (screen instanceof CreativeScreen) {
                            this.updateItems((CreativeScreen) screen);
                        }
                    }
                }));
            }

            this.buttons.forEach(event::addWidget);
            this.updateCategoryButtons();

            CreativeScreen screen = (CreativeScreen) event.getGui();
            if (screen.getSelectedTab() == DOTB_TAB.getId()) {
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
    public void onScreenDrawPre(GuiScreenEvent.DrawScreenEvent.Pre event) {
        if (event.getGui() instanceof CreativeScreen) {
            CreativeScreen screen = (CreativeScreen) event.getGui();
            if (screen.getSelectedTab() == DOTB_TAB.getId()) {
                if (!tabDoTBSelected) {
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

    /**
     * Called at each tick, after onScreenDrawPre, when a GUI is open
     */
    @SubscribeEvent
    public void onScreenDrawPost(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (event.getGui() instanceof CreativeScreen) {
            CreativeScreen screen = (CreativeScreen) event.getGui();
            this.guiCenterX = screen.getGuiLeft();
            this.guiCenterY = screen.getGuiTop();

            if (screen.getSelectedTab() == DOTB_TAB.getId()) {
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
                    if (button.isMouseOver(event.getMouseX(), event.getMouseY())) {
                        screen.renderTooltip(event.getMatrixStack(), CreativeInventoryCategories.values()[button.getCategoryID()].getTranslation(), event.getMouseX(), event.getMouseY());
                    }
                });

            } else if (tabDoTBSelected) {
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
    public void onScreenDrawBackground(GuiContainerEvent.DrawBackground event) {
        if (event.getGuiContainer() instanceof CreativeScreen) {
            CreativeScreen screen = (CreativeScreen) event.getGuiContainer();
            if (screen.getSelectedTab() == DOTB_TAB.getId()) {
                if (!tabDoTBSelected) {
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

    private void updateItems(CreativeScreen screen) {
        CreativeScreen.CreativeContainer container = screen.getMenu();
        container.items.clear();
        CreativeInventoryCategories.values()[selectedCategoryID].getItems().forEach(item -> container.items.add(new ItemStack(item)));
        container.scrollTo(0);
    }

    private void openLink(String link) {
        Util.getPlatform().openUri(link);
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

    private void scrollUp() {
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof CreativeScreen) {
            if (page > 0) {
                page--;
                this.updateCategoryButtons();
            }
        }
    }

    private void scrollDown() {
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof CreativeScreen) {
            if (page < MAX_PAGE) {
                page++;
                this.updateCategoryButtons();
            }
        }
    }
}