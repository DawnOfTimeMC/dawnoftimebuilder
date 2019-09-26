package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class DoTBBlockTileEntity extends DoTBBlock implements ITileEntityProvider {

	DoTBBlockTileEntity(String name, Material materialIn, float hardness, SoundType sound){
		super(name, materialIn, hardness, sound);
	}

	public DoTBBlockTileEntity(String name, Material materialIn) {
		super(name, materialIn);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public abstract TileEntity createNewTileEntity(World world, int meta);

	/**
	 * Called server-side after this blocks is replaced with another in Chunk, but before the Tile Entity is updated
	 */
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}
}
