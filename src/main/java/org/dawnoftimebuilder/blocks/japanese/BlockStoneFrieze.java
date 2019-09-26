package org.dawnoftimebuilder.blocks.japanese;

import org.dawnoftimebuilder.blocks.general.DoTBBlockPlate;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockStoneFrieze extends DoTBBlockPlate {
	
    public BlockStoneFrieze() {
    	super("stone_frieze", Material.ROCK, 1.5F, SoundType.STONE);
    }
    
	@Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
		return BlockFaceShape.UNDEFINED;
    }
}
