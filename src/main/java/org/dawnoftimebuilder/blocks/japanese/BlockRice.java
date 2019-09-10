package org.dawnoftimebuilder.blocks.japanese;

import java.util.Random;

import net.minecraft.util.NonNullList;
import net.minecraft.world.IBlockAccess;
import org.dawnoftimebuilder.blocks.global.DoTBBlockSoilCrops;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import static org.dawnoftimebuilder.items.DoTBItems.rice;

public class BlockRice extends DoTBBlockSoilCrops {

	public BlockRice() {
		super("rice", Blocks.WATER);
	}
	
    protected Item getSeed()
    {
        return rice;
    }

    protected Item getCrop()
    {
        return rice;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = new Random();
        int bonus = this.isMaxAge(state) ? (rand.nextInt(2) + 1) : 0;

        drops.add(new ItemStack(this.getCrop(), 1 + bonus));
    }
}
