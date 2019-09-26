package org.dawnoftimebuilder.blocks.mayan;

import java.util.Random;

import org.dawnoftimebuilder.blocks.general.DoTBBlockSoilCrops;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import static org.dawnoftimebuilder.items.DoTBItems.commelina;

public class BlockCommelina extends DoTBBlockSoilCrops {
	
	public BlockCommelina() {
		super("commelina", Blocks.FARMLAND);
	}

	@Override
    protected Item getSeed() {
        return commelina;
    }

    @Override
    protected Item getCrop() {
        return commelina;
    }

    @Override
    public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, net.minecraft.world.IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    	drops.add(new ItemStack(this.getCrop(), 1));
    	
        Random rand = new Random();
        if(this.isMaxAge(state)){
            int bonus = rand.nextInt(2) + 1;
            for(int i = 0; i < bonus; i++){
                drops.add(new ItemStack(this.getCrop(), 1));
            }
        }
    }
}
