package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dawnoftimebuilder.items.general.DoTBItemSoilSeeds;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBBlockSoilCrops extends BlockCrops implements IBlockCustomItem {

	private Block soil;
	private String seedName;

	public DoTBBlockSoilCrops(String name, Block soilBlock, String seedName){
		super();
		
		this.soil = soilBlock;
		this.seedName = seedName;
		this.setRegistryName(MOD_ID, name);
		this.setTranslationKey(MOD_ID + "." + name);

		this.setCreativeTab(DOTB_TAB);
	}

	public DoTBBlockSoilCrops(String name, Block soilBlock){
		this(name, soilBlock, name);
	}

	public Block getSoilBlock(){
		return this.soil;
	}

	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state){
		return (worldIn.getLight(pos) >= 8 || worldIn.canSeeSky(pos)) && this.isValidSoil(worldIn, pos);
	}
	
	private boolean isValidSoil(World world, BlockPos pos){
		IBlockState soil = world.getBlockState(pos.down());
		
		if(this.soil == Blocks.WATER){
			return soil.getMaterial() == Material.WATER;
		}else{
			return soil.getBlock() == this.soil;
		}
	}

	protected String getSeedName() {
		return seedName;
	}

	@Override
	public Item getCustomItemBlock() {
		return new DoTBItemSoilSeeds(this)
				.setRegistryName(MOD_ID, this.getSeedName())
				.setTranslationKey(MOD_ID + "." + this.getSeedName());
	}
}
