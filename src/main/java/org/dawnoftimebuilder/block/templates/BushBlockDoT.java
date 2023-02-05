package org.dawnoftimebuilder.block.templates;

import java.util.Random;

import org.dawnoftimebuilder.block.IBlockGeneration;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.Tags;

public class BushBlockDoT extends BushBlock implements IBlockGeneration {

	private int	fireSpreadSpeed			= 0;
	private int	fireDestructionSpeed	= 0;

	public BushBlockDoT(final Properties properties) {
		super(properties);
	}

	/**
	 * Set Encouragement to 5 and Flammability to 20
	 * @return this
	 */
	public Block setBurnable() {
		return this.setBurnable(5, 20);
	}

	/**
	 * Set burning parameters (default 5 / 20)
	 * @param fireSpreadSpeed Increases the probability to catch fire
	 * @param fireDestructionSpeed Decreases burning duration
	 * @return this
	 */
	public Block setBurnable(final int fireSpreadSpeed, final int fireDestructionSpeed) {
		this.fireSpreadSpeed		= fireSpreadSpeed;
		this.fireDestructionSpeed	= fireDestructionSpeed;
		return this;
	}

	@Override
	public int getFireSpreadSpeed(final BlockState state, final IBlockReader world, final BlockPos pos, final Direction face) {
		return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) ? 0 : this.fireSpreadSpeed;
	}

	@Override
	public int getFlammability(final BlockState state, final IBlockReader world, final BlockPos pos, final Direction face) {
		return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) ? 0 : this.fireDestructionSpeed;
	}

	@Override
	public void generateOnPos(final IWorld worldIn, final BlockPos posIn, final BlockState stateIn, final Random randomIn) {
		final BlockState groundState = worldIn.getBlockState(posIn.below());

		if (!Tags.Blocks.DIRT.contains(groundState.getBlock())) {
			return;
		}
		worldIn.setBlock(posIn, stateIn, 2);
	}
}
