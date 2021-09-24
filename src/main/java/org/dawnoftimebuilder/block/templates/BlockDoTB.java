package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockDoTB extends Block {

	private int fireSpreadSpeed = 0;
	private int fireDestructionSpeed = 0;

	public BlockDoTB(Properties properties) {
		super(properties);
	}

	public BlockDoTB(Material materialIn, float hardness, float resistance, SoundType soundType) {
		this(Properties.of(materialIn).strength(hardness, resistance).sound(soundType));
	}

	/**
	 * Set Encouragement to 5 and Flammability to 20
	 * @return this
	 */
	public Block setBurnable() {
		return setBurnable(5, 20);
	}

	/**
	 * Set burning parameters (default 5 / 20)
	 * @param fireSpreadSpeed Increases the probability to catch fire
	 * @param fireDestructionSpeed Decreases burning duration
	 * @return this
	 */
	public Block setBurnable(int fireSpreadSpeed, int fireDestructionSpeed) {
		this.fireSpreadSpeed = fireSpreadSpeed;
		this.fireDestructionSpeed = fireDestructionSpeed;
		return this;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) ? 0 : this.fireSpreadSpeed;
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) ? 0 : this.fireDestructionSpeed;
	}
}
