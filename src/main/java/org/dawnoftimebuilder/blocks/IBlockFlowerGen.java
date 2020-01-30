package org.dawnoftimebuilder.blocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.List;
import java.util.Random;

public interface IBlockFlowerGen {

	/**
	 * @return A list of Biome's names where this flower can spawn
	 */
	List<String> getAcceptedBiomes();

	void spawnInWorld(World world, BlockPos pos, Random rand);

	/**
	 * @return The radius around the position of a patch where flower will spawn
	 */
	int getPatchSize();

	/**
	 * @return  The opposite of the probability this flower has to spawn : 1 / PatchChance.
	 */
	int getPatchChance();

	/**
	 * @return The concentration of patches in the world
	 */
	int getPatchQuantity();

	/**
	 * @return The amount of flower that a patch will contain.
	 * This value will change randomly between patches from 50% to 150%
	 */
	int getPatchDensity();
}
