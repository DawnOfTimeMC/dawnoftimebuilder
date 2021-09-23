package org.dawnoftimebuilder.client.gui.creative;

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

import java.util.*;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class CreativeInventoryEvents {

	public static final ResourceLocation CREATIVE_ICONS = new ResourceLocation(MOD_ID, "textures/gui/creative_icons.png");
	private List<CategoryButton> buttons;
	private Button btnScrollUp;
	private Button btnScrollDown;
	private int guiCenterX = 0;
	private int guiCenterY = 0;
	public static int selectedCategoryID = 0;
	public static int page = 0;
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

			event.addWidget(this.btnScrollUp = new GroupButton(this.guiCenterX - 22, this.guiCenterY - 22, StringTextComponent.EMPTY, button -> {
				if(page > 0){
					page--;
					this.updateCategoryButtons();
				}
			}, CREATIVE_ICONS, 0, 56));

			event.addWidget(this.btnScrollDown = new GroupButton(this.guiCenterX - 22, this.guiCenterY + 120, StringTextComponent.EMPTY, button -> {
				if(page < MAX_PAGE){
					page++;
					this.updateCategoryButtons();
				}
			}, CREATIVE_ICONS, 16, 56));

			for(int i = 0; i < 4; i++){
				this.buttons.add(new CategoryButton(this.guiCenterX - 27, this.guiCenterY + 30 * i, i, button -> {
					CategoryButton categoryButton = (CategoryButton) button;
					if(!categoryButton.isSelected()){
						buttons.get(selectedCategoryID % 4).setSelected(false);
						categoryButton.setSelected(true);
						selectedCategoryID = categoryButton.getCategoryID();
						Screen screen = Minecraft.getInstance().screen;
						if(screen instanceof CreativeScreen) {
							this.updateItems((CreativeScreen) screen);
						}
					}
				}));
			}
			this.buttons.forEach(event::addWidget);
			this.updateCategoryButtons();

			CreativeScreen screen = (CreativeScreen) event.getGui();
			if(screen.getSelectedTab() == DOTB_TAB.getId()) {
				this.btnScrollUp.visible = true;
				this.btnScrollDown.visible = true;
				tabDoTBSelected = true;
				this.updateCategoryButtons();
				this.buttons.forEach(button -> button.visible = true);
				this.buttons.get(selectedCategoryID % 4).setSelected(true);
				this.updateItems(screen);
			}else{
				this.btnScrollUp.visible = false;
				this.btnScrollDown.visible = false;
				this.buttons.forEach(button -> button.visible = false);
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
			
			if(screen.getSelectedTab() == DOTB_TAB.getId()){
				tabDoTBSelected = true;
				this.btnScrollUp.visible = true;
				this.btnScrollDown.visible = true;
				this.buttons.forEach(button -> button.visible = true);

				// Render tooltips after so it renders above buttons
				this.buttons.forEach(button -> {
					if(button.isMouseOver(event.getMouseX(), event.getMouseY())){
						screen.renderTooltip(event.getMatrixStack(), CreativeInventoryCategories.values()[button.getCategoryID()].getTranslation(), event.getMouseX(), event.getMouseY());
					}
				});

			}else if(tabDoTBSelected) {
				this.btnScrollUp.visible = false;
				this.btnScrollDown.visible = false;
				this.buttons.forEach(button -> button.visible = false);
				tabDoTBSelected = false;
			}
		}
	}

	private void updateCategoryButtons(){
		this.btnScrollUp.active = (page > 0);
		this.btnScrollDown.active = (page < MAX_PAGE);
		this.buttons.forEach(button -> button.active = (button.getCategoryID() < CreativeInventoryCategories.values().length));
		this.buttons.get(selectedCategoryID % 4).setSelected(selectedCategoryID - page * 4 >= 0 && selectedCategoryID - page * 4 < 4);
	}

	private void updateItems(CreativeScreen screen){
		CreativeScreen.CreativeContainer container = screen.getMenu();
		container.items.clear();
		CreativeInventoryCategories.values()[selectedCategoryID].getItems().forEach(item -> container.items.add(new ItemStack(item)));
		container.scrollTo(0);
	}
}