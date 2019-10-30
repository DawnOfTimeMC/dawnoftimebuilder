package org.dawnoftimebuilder;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.dawnoftimebuilder.blocks.IBlockFlowerGen;

import java.util.Random;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS;
import static net.minecraftforge.fml.common.eventhandler.Event.Result.ALLOW;
import static net.minecraftforge.fml.common.eventhandler.Event.Result.DEFAULT;
import static org.dawnoftimebuilder.blocks.DoTBBlocks.*;

public class DoTBWorldGen {

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onWorldDecoration(DecorateBiomeEvent.Decorate event) {
		BlockPos pos = (event.getPlacementPos() != null) ? event.getPlacementPos() : event.getChunkPos().getBlock(0, 0, 0);

		if((event.getResult() == ALLOW || event.getResult() == DEFAULT) && event.getType() == FLOWERS) {
			decorateWorldWith(event.getWorld(), pos, rice, event.getRand());
			decorateWorldWith(event.getWorld(), pos, camellia, event.getRand());
			decorateWorldWith(event.getWorld(), pos, mulberry, event.getRand());
		}
	}

	private static void decorateWorldWith(World world, BlockPos pos, IBlockFlowerGen flower, Random rand){
		ResourceLocation resource = world.getBiome(pos).getRegistryName();
		if(resource == null) return;
		if(flower.getAcceptedBiomes().contains(resource.getPath())){
			int dist = flower.getPatchSize();
			for(int i = 0; i < flower.getPatchQuantity(); i++) {
				if(rand.nextInt(flower.getPatchChance()) == 0) {
					int x = pos.getX() + rand.nextInt(16) + 8;
					int z = pos.getZ() + rand.nextInt(16) + 8;

					for(int j = 0; j < Math.ceil(flower.getPatchDensity() * (rand.nextDouble() + 0.5D)); j++) {

						int newX = x + rand.nextInt(dist * 2) - dist;
						int newZ = z + rand.nextInt(dist * 2) - dist;

						BlockPos newPos = getSurfacePos(world, newX, newZ);

						if(newPos.getY() < 127) flower.spawnInWorld(world, newPos, rand);
					}
				}
			}
		}
	}

	/**
	 * @return The BlockPos of the last block of AIR above the roof
	 */
	private static BlockPos getSurfacePos(World world, int x, int z){
		BlockPos pos;
		IBlockState state;
		Chunk chunk = world.getChunk(new BlockPos(x, 0, z));
		for(int y = chunk.getTopFilledSegment() + 16; y >= 0; y--){
			pos = new BlockPos(x, y, z);
			state = chunk.getBlockState(pos);
			if(state.getBlock() != Blocks.AIR && !state.getBlock().isLeaves(state, world, pos) && !state.getBlock().isFoliage(world, pos)){
				return pos.up();
			}
		}
		return new BlockPos(x, 200, z);
	}
}
