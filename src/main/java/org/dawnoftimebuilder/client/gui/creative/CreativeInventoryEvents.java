package org.dawnoftimebuilder.client.gui.creative;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

import java.util.*;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

//Thank you, MrCrayFish, for your code! That was a great help!
@OnlyIn(Dist.CLIENT)
public class CreativeInventoryEvents {

	public static final ResourceLocation CREATIVE_ICONS = new ResourceLocation(MOD_ID, "textures/gui/creative_icons.png");
	private List<CategoryButton> buttons;
	private Button btnScrollUp;
	private Button btnScrollDown;
	private int guiCenterX = 0;
	private int guiCenterY = 0;
	public static int selectedButtonID = 0;
	private static int page = 0;
	private static boolean tabDoTBSelected;
	private final int MAX_PAGE = (int) Math.floor((double) (CreativeInventoryCategories.values().length - 1)/ 4);

	/**
	 * Called at each opening of a GUI.
	 */
	@SubscribeEvent
	public void onScreenInit(GuiScreenEvent.InitGuiEvent.Post event){
		if(event.getGui() instanceof CreativeScreen){

			tabDoTBSelected = false;
			this.guiCenterX = ((CreativeScreen) event.getGui()).getGuiLeft();
			this.guiCenterY = ((CreativeScreen) event.getGui()).getGuiTop();
			this.buttons = new ArrayList<>();

			event.addWidget(this.btnScrollUp = new GroupButton(this.guiCenterX - 22, this.guiCenterY - 22, "", button -> {
				if(page > 0){
					page--;
					this.updateCategoryButtons();
				}
			}, CREATIVE_ICONS, 0, 56));

			event.addWidget(this.btnScrollDown = new GroupButton(this.guiCenterX - 22, this.guiCenterY + 120, "", button -> {
				if(page < MAX_PAGE){
					page++;
					this.updateCategoryButtons();
				}
			}, CREATIVE_ICONS, 16, 56));

			this.btnScrollUp.visible = false;
			this.btnScrollDown.visible = false;

			this.updateCategoryButtons();

			CreativeScreen screen = (CreativeScreen) event.getGui();
			if(screen.getSelectedTabIndex() == DOTB_TAB.getIndex()) {
				this.btnScrollUp.visible = true;
				this.btnScrollDown.visible = true;
				tabDoTBSelected = true;
				this.buttons.forEach(button -> button.visible = true);
				this.buttons.get(selectedButtonID).setSelected(true);
				this.updateItems(screen);
			}
		}
	}

	@SubscribeEvent
	public void onScreenClick(GuiScreenEvent.MouseClickedEvent.Pre event){
		if(event.getButton() != GLFW.GLFW_MOUSE_BUTTON_LEFT)
			return;

		if(event.getGui() instanceof CreativeScreen){
			for(CategoryButton button : this.buttons){
				if(button.isMouseOver(event.getMouseX(), event.getMouseY())){
					if(button.mouseClicked(event.getMouseX(), event.getMouseY(), event.getButton())){
						if(!button.isSelected()){
							buttons.get(selectedButtonID).setSelected(false);
							button.setSelected(true);
							selectedButtonID = buttons.indexOf(button);
							Screen screen = Minecraft.getInstance().currentScreen;
							if(screen instanceof CreativeScreen) {
								this.updateItems((CreativeScreen) screen);
							}
						}
						return;
					}
				}
			}
		}
	}

	/**
	 * Called at each tick, after onScreenDrawPre, when a GUI is open
	 */
	@SubscribeEvent
	public void onScreenDrawPost(GuiScreenEvent.DrawScreenEvent.Post event){
		if(event.getGui() instanceof CreativeScreen){
			CreativeScreen screen = (CreativeScreen) event.getGui();
			this.guiCenterX = screen.getGuiLeft();
			this.guiCenterY = screen.getGuiTop();
			
			if(screen.getSelectedTabIndex() == DOTB_TAB.getIndex()){
				tabDoTBSelected = true;
				this.btnScrollUp.visible = true;
				this.btnScrollDown.visible = true;
				this.buttons.forEach(button -> button.visible = true);

				// Render tooltips after so it renders above buttons
				this.buttons.forEach(button -> {
					if(button.isMouseOver(event.getMouseX(), event.getMouseY())){
						screen.renderTooltip(button.getCategory().getTranslation(), event.getMouseX(), event.getMouseY());
					}
				});

				// Render buttons
				this.buttons.forEach(button -> button.render(event.getMouseX(), event.getMouseY(), event.getRenderPartialTicks()));

			}else if(tabDoTBSelected) {
				this.btnScrollUp.visible = false;
				this.btnScrollDown.visible = false;
				this.buttons.forEach(button -> button.visible = false);
				tabDoTBSelected = false;
			}
		}
	}

	private void updateCategoryButtons(){
		this.buttons.clear();
		for(int i = page * 4; i < (page + 1) * 4 && i < CreativeInventoryCategories.values().length; i++) {
			CategoryButton categoryButton = new CategoryButton(this.guiCenterX - 27, this.guiCenterY + 30 * (i % 4), CreativeInventoryCategories.values()[i], button -> {});
			this.buttons.add(categoryButton);
		}
		this.btnScrollUp.active = (page > 0);
		this.btnScrollDown.active = (page < MAX_PAGE);
	}

	private void updateItems(CreativeScreen screen){
		CreativeScreen.CreativeContainer container = screen.getContainer();
		container.itemList.clear();
		CreativeInventoryCategories.values()[selectedButtonID + 4 * page].getItems().forEach(item -> container.itemList.add(new ItemStack(item)));
		container.scrollTo(0);
	}
}