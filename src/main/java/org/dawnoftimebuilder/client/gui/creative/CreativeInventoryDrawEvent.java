package org.dawnoftimebuilder.client.gui.creative;

import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;

public class CreativeInventoryDrawEvent {

	private List<GroupButtons> buttons = new ArrayList<>();
	private PageButtons upArrow = null;
	private PageButtons downArrow = null;
	private CreativeInventoryCategories selectedCategory;
	private int page = 0;

	private int getMaxPages() {
		return (int) Math.ceil((double) CreativeInventoryCategories.values().length / 4);
	}

	private int getCurrentPage() {
		return page + 1;
	}

	@SubscribeEvent
	public void onDrawGui(GuiScreenEvent.InitGuiEvent.Post event){
		if(event.getGui() instanceof GuiContainerCreative){
			if(!buttons.isEmpty()){
				event.getButtonList().removeAll(buttons);
				buttons.clear();
			}

			int leftPos = ((GuiContainerCreative) event.getGui()).getGuiLeft();
			int upPos = ((GuiContainerCreative) event.getGui()).getGuiTop();

			for(int i = 0; i < CreativeInventoryCategories.values().length; i++) {
				// init buttons, add to my list
				int tabPosition = i % 4;
				int height = upPos + (tabPosition * 28) + (tabPosition * 2) + 10;
				GroupButtons button = new GroupButtons(44, leftPos - 31, height, 31, 28, CreativeInventoryCategories.values()[i]);
				buttons.add(button);
				event.getButtonList().add(button);
			}

			if (getMaxPages() > 1) {
				upArrow = new PageButtons(44, leftPos - 17, upPos, true);
				downArrow = new PageButtons(44, leftPos - 17, upPos + 130, false);
				event.getButtonList().add(upArrow);
				event.getButtonList().add(downArrow);
			}
		}
	}

	@SubscribeEvent
	public void onDrawGui(GuiScreenEvent.DrawScreenEvent.Post event) {
		if(event.getGui() instanceof GuiContainerCreative) {
			GuiContainerCreative gui = (GuiContainerCreative) event.getGui();
			if(gui.getSelectedTabIndex() == DOTB_TAB.getIndex()) {
				buttons.forEach(button -> button.visible = false);
				int startIndex = 4 * page;
				for(int i = startIndex; i < startIndex + 4; i++){
					if(i <= buttons.size() - 1){
						GroupButtons button = buttons.get(i);
						button.visible = true;
					}
				}
				if(upArrow != null) {
					upArrow.visible = true;
					downArrow.visible = true;
					if(getCurrentPage() == 1) {
						upArrow.visible = false;
					}
					if(getCurrentPage() == getMaxPages()) {
						downArrow.visible = false;
					}
				}
			} else {
				buttons.forEach(button -> button.visible = false);
				if(upArrow != null) {
					upArrow.visible = false;
					downArrow.visible = false;
				}
			}
		}
	}

	@SubscribeEvent
	public void onClick(GuiScreenEvent.ActionPerformedEvent.Post event){
		if(event.getGui() instanceof GuiContainerCreative) {
			if (event.getButton() instanceof GroupButtons) {
				GroupButtons button = (GroupButtons) event.getButton();
				buttons.forEach(GroupButtons::deSelect);
				button.setSelected();
				selectedCategory = button.getCategory();
				updateCreativeItems((GuiContainerCreative) event.getGui());

			} else if (event.getButton() instanceof PageButtons) {
				PageButtons buttons = (PageButtons) event.getButton();
				if (buttons.isUp()) {
					page--;
				} else {
					page++;
				}
			}
		}
	}

	private void updateCreativeItems(GuiContainerCreative containerCreative){
		if(containerCreative.getSelectedTabIndex() == DOTB_TAB.getIndex()){
			GuiContainerCreative.ContainerCreative creativeInv = (GuiContainerCreative.ContainerCreative) containerCreative.inventorySlots;
			creativeInv.itemList.clear();
			if(selectedCategory != null) {
				selectedCategory.getItems().forEach(item -> item.getSubItems(DOTB_TAB, creativeInv.itemList));
			}
			creativeInv.scrollTo(0);
		}
	}
}
