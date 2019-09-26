package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBBlockStairs extends BlockStairs {

    public DoTBBlockStairs(String name, IBlockState state, float hardness, SoundType sound){
        super(state);

        this.setRegistryName(MOD_ID, name);
        this.setTranslationKey(MOD_ID + "." + name);
        this.setCreativeTab(DOTB_TAB);

        this.useNeighborBrightness = true;

        this.setHardness(hardness);
        this.setSoundType(sound);
    }

	public DoTBBlockStairs(String name, Block block, int meta, float hardness, SoundType sound) {
        this(name, block.getBlockState().getValidStates().get(meta), hardness, sound);
	}

    public DoTBBlockStairs(String name, Block block, float hardness, SoundType sound) {
        this(name, block.getDefaultState(), hardness, sound);
    }

    public Block setBurnable() {
        Blocks.FIRE.setFireInfo(this, 5, 20);
        return this;
    }
}
