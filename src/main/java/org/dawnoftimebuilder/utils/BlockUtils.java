package org.dawnoftimebuilder.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class BlockUtils {

	/** Fill a table with VS rotated in each horizontal directions in order :<p/>
	 * south - west - north - east
	 *
	 * @param shapes contains the VoxelShapes oriented toward south
	 * @return a table filled with the previous VS and new ones rotated in each 3 horizontal directions
	 */
	public static VoxelShape[] GenerateHorizontalShapes(VoxelShape[] shapes){
		VoxelShape[] newShape = {VoxelShapes.empty()};
		VoxelShape[] newShapes = new VoxelShape[shapes.length * 4];
		int i = 0;
		for(VoxelShape shape : shapes){
			newShapes[i] = shape;
			i++;
		}
		for(int rotation = 1; rotation < 4; rotation++){
			int j = 0;
			for(VoxelShape shape : shapes){
				shape.forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> newShape[0] = VoxelShapes.or(newShape[0], VoxelShapes.create(1-maxZ, minY, minX, 1-minZ, maxY, maxX)));
				shapes[j] = newShape[0];
				newShapes[i] = newShape[0];
				newShape[0] = VoxelShapes.empty();
				i++;
				j++;
			}
		}
		return newShapes;
	}

	public enum DoTBTags {

		;

		private final ResourceLocation resource;

		DoTBTags(String tagID) {
			this.resource = new ResourceLocation(MOD_ID, tagID);
		}

		public boolean contains(Item item) {
			return ItemTags.getCollection().getOrCreate(this.resource).contains(item);
		}

		public boolean contains(Block block) {
			return BlockTags.getCollection().getOrCreate(this.resource).contains(block);
		}
	}
}
