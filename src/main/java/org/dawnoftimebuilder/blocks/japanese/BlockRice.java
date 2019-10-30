package org.dawnoftimebuilder.blocks.japanese;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.NonNullList;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.dawnoftimebuilder.blocks.IBlockFlowerGen;
import org.dawnoftimebuilder.blocks.general.DoTBBlockSoilCrops;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import static org.dawnoftimebuilder.items.DoTBItems.rice;

public class BlockRice extends DoTBBlockSoilCrops implements IBlockFlowerGen {

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

    @Override
    public List<String> getAcceptedBiomes() {
        return Arrays.asList(
                "swampland",
                "river"
        );
    }

    @Override
    public void spawnInWorld(World world, BlockPos pos, Random rand) {
        if(world.getBlockState(pos.down()).getBlock() == Blocks.WATER){
            Block block = world.getBlockState(pos.down(2)).getBlock();
            if(block == Blocks.DIRT || block == Blocks.CLAY || block == Blocks.GRAVEL){
                world.setBlockState(pos, this.withAge(4 + rand.nextInt(4)), 2);
            }
        }
    }

    @Override
    public int getPatchSize() {
        return 3;
    }

    @Override
    public int getPatchChance() {
        return 16;
    }

    @Override
    public int getPatchQuantity() {
        return 2;
    }

    @Override
    public int getPatchDensity() {
        return 14;
    }
}
