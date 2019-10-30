package org.dawnoftimebuilder.blocks.general;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.dawnoftimebuilder.enums.EnumsBlock;
import org.dawnoftimebuilder.items.general.DoTBItemDoubleCropsSeed;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;


public abstract class DoTBBlockDoubleCrops extends DoTBBlockSoilCrops {

	public static final PropertyEnum<EnumsBlock.EnumHalf> HALF = PropertyEnum.create("half", EnumsBlock.EnumHalf.class);
	
	public DoTBBlockDoubleCrops(String name, Block soilBlock, String seedName) {
		super(name, soilBlock, seedName);
		this.setDefaultState(this.getDefaultState().withProperty(HALF, EnumsBlock.EnumHalf.BOTTOM));
	}

	public DoTBBlockDoubleCrops(String name, Block soilBlock){
		this(name, soilBlock, name);
	}

	@Override
    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!this.canBlockStay(worldIn, pos, state)) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            this.destroyPlant(pos, worldIn);
        }
    }
	
	@Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state){
		BlockPos bottomCrop = this.getBottomCrop(worldIn, pos);
		
		if(bottomCrop.equals(pos)){
			
			boolean hasTopCrop = worldIn.getBlockState(pos.up()).getBlock() == this;
			
			if(!hasTopCrop && this.getAge(state) >= this.getAgeReachingTopBlock()){
				return false;
			}
			
			return super.canBlockStay(worldIn, pos, state);
		}else{
			return pos.getY() - bottomCrop.getY() == 1;
		}
		
     }
	
	private void destroyPlant(BlockPos pos, World world){
		world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand){
		if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
		if (worldIn.getLightFromNeighbors(pos.up()) >= 9){
            int i = this.getAge(state);

            if (i < this.getMaxAge()){
                float f = getGrowthChance(this, worldIn, pos);
                if(net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((int)(25.0F / f) + 1) == 0)) {
                	this.tryGrow(worldIn, pos, state, i + 1);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
                }
            }
        }
	}
	
	@Override
    public void grow(World worldIn, BlockPos pos, IBlockState state) {

		if(this.isBottomCrop(worldIn, pos)){
	        int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
	        int j = this.getMaxAge();
	        if (i > j) i = j;
	        this.tryGrow(worldIn, pos, state, i);
        
		}else{

			BlockPos bottomCrop = this.getBottomCrop(worldIn, pos);
			this.grow(worldIn, bottomCrop, worldIn.getBlockState(bottomCrop));
		}
    }

	private void tryGrow(World worldIn, BlockPos pos, IBlockState state, int i){
		
		if(this.isBottomCrop(worldIn, pos)){
			boolean canGrow = true;
	    	if(i >= this.getAgeReachingTopBlock()){
	        	canGrow = false;
	        	BlockPos topPos = pos.up();
	        	IBlockState topCrop = worldIn.getBlockState(topPos);
	        	
	        	if(topCrop.getBlock() == this){
	        		worldIn.setBlockState(topPos, this.setAge(topCrop.withProperty(HALF, EnumsBlock.EnumHalf.TOP), i), 2);
	        		canGrow = true;
	        		
	        	}else if(worldIn.isAirBlock(topPos)){
	        		worldIn.setBlockState(topPos, this.setAge(this.getDefaultState().withProperty(HALF, EnumsBlock.EnumHalf.TOP), i), 2);
	        		canGrow = true;
	        	}
	        }
	        
	        if(canGrow){
	        	worldIn.setBlockState(pos, this.setAge(state.withProperty(HALF, EnumsBlock.EnumHalf.BOTTOM), i), 2);
	        }
		}
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		boolean top = state.getValue(HALF) == EnumsBlock.EnumHalf.TOP;
		return new AxisAlignedBB(0.0D, top ? -1.0D : 0.0D, 0.0D, 1.0D, getCropHeightAtAge(state.getValue(AGE)) + (top ? -1.0D : 0.0D), 1.0D);
    }
    
	private double getCropHeightAtAge(int age){
		return (age + 1) * 0.25D;
	}
	
	@Override
    public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
    	this.destroyPlant(pos, worldIn);
    }
    
    @Override
    public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state) {
        this.destroyPlant(pos, worldIn);
    }
    
    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    	if(this.isBottomCrop(world, pos) && state.getValue(HALF) == EnumsBlock.EnumHalf.BOTTOM){
    		this.getDoubleCropsDrops(drops, world, pos, state, fortune);
    	}
    }
    
    public void getDoubleCropsDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    	super.getDrops(drops, world, pos, state, fortune);
    }
    
	public IBlockState setAge(IBlockState state, int age){
		return state.withProperty(AGE, age);
	}
    
	public boolean isBottomCrop(IBlockAccess world, BlockPos pos){
		return pos.equals(getBottomCrop(world, pos));
	}
	
	private BlockPos getBottomCrop(IBlockAccess world, BlockPos pos){
		BlockPos bottom = pos;
		while(world.getBlockState(bottom.offset(EnumFacing.DOWN)).getBlock() == this){
			bottom = bottom.offset(EnumFacing.DOWN);
		}
		return bottom;
		
	}
	
	@Override
    public IBlockState getStateFromMeta(int meta){

		EnumsBlock.EnumHalf half = EnumsBlock.EnumHalf.BOTTOM;
		if((meta & 1) > 0) half = EnumsBlock.EnumHalf.TOP;
		meta >>= 1;
		
        return this.withAge(meta).withProperty(HALF, half);
    }

	@Override
    public int getMetaFromState(IBlockState state) {
		boolean top = state.getValue(HALF) == EnumsBlock.EnumHalf.TOP;
		int meta = this.getAge(state);
		meta <<= 1;
		
		if(top) meta = meta | 1;
		
        return meta;
    }
    
	@Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AGE, HALF);
    }

	@Override
	public Item getCustomItemBlock() {
		return new DoTBItemDoubleCropsSeed(this)
				.setRegistryName(MOD_ID, this.getSeedName())
				.setTranslationKey(MOD_ID + "." + this.getSeedName());
	}

	public abstract int getAgeReachingTopBlock();
}
