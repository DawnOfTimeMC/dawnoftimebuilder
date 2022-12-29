package org.dawnoftimebuilder.block.japanese;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;

import java.util.Random;

import javax.annotation.Nullable;

import org.dawnoftimebuilder.block.IBlockGeneration;
import org.dawnoftimebuilder.block.ICustomBlockItem;
import org.dawnoftimebuilder.block.templates.BushBlockDoT;
import org.dawnoftimebuilder.item.templates.PotAndBlockItem;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;

public class MapleSaplingBlock extends BushBlockDoT implements IBlockGeneration, ICustomBlockItem, IGrowable
{
    public final static boolean isValidForPlacement(World worldIn, BlockPos bottomCenterIn, boolean isSaplingCallIn)
    {
    	BlockPos floorCenter = bottomCenterIn.below();
    	BlockState state = worldIn.getBlockState(floorCenter);

    	if(!Tags.Blocks.DIRT.contains(state.getBlock()))
    		return false;

    	state = worldIn.getBlockState(bottomCenterIn);

    	System.out.println("A ");
    	if(!state.getMaterial().isReplaceable() || !isSaplingCallIn)
    		return true;

    	System.out.println("B ");

        for(int x = -1; x <= 1; x ++)
        {
            for(int y = 0; y <= 1; y ++)
            {
                for(int z = -1; z <= 1; z ++)
                {
                	state = worldIn.getBlockState(bottomCenterIn.offset(x, y + 1, z));

                	System.out.println(state);
                	if(state == null || !state.getMaterial().isReplaceable())
                	{
                		return false;
                	}
                }
            }
        }

        return true;
    }
	
    public MapleSaplingBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState());
    }

    /**
     * Cypress code like
     */

	@Override
	public void generateOnPos(IWorld world, BlockPos pos, BlockState state, Random random)
	{
	}
    
    @Nullable
    @Override
    public Item getCustomBlockItem()
    {
        return new PotAndBlockItem(this, new Item.Properties().tab(DOTB_TAB));
    }
    
    /**
     * Sapling code like
     */
     // TODO getShape of MapleSaplingBlock
     /**public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
     }**/

    @Override
    public boolean isRandomlyTicking(BlockState blockstateIn)
    {
    	return true;
    }

	@Override
    public void tick(BlockState p_225542_1_, ServerWorld p_225542_2_, BlockPos p_225542_3_, Random p_225542_4_)
    {
    	//super.tick(p_225542_1_, p_225542_2_, p_225542_3_, p_225542_4_);

        if (p_225542_2_.getMaxLocalRawBrightness(p_225542_3_.above()) >= 9 && p_225542_4_.nextInt(100) == 0)
        {
	        if (!p_225542_2_.isAreaLoaded(p_225542_3_, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
	           this.advanceTree(p_225542_2_, p_225542_3_, p_225542_1_, p_225542_4_);
        }
    }

     public void advanceTree(ServerWorld p_226942_1_, BlockPos p_226942_2_, BlockState p_226942_3_, Random p_226942_4_)
     {
    	 if(isValidForPlacement(p_226942_1_, p_226942_2_, true))
    	 {
    		 Direction direction =  Direction.Plane.HORIZONTAL.getRandomDirection(p_226942_1_.random);
    		 p_226942_1_.setBlock(p_226942_2_, DoTBBlocksRegistry.MAPLE_RED_TRUNK.get().defaultBlockState()
    	        		.setValue(MapleTrunkBlock.FACING, direction), 10);
    	    	
    	        for(int x = -1; x <= 1; x ++)
    	        {
    	            for(int y = 0; y <= 1; y ++)
    	            {
    	                for(int z = -1; z <= 1; z ++)
    	                {
    	                	BlockPos newBlockPosition = new BlockPos(p_226942_2_.getX() + x, p_226942_2_.getY() + y+1, p_226942_2_.getZ() + z);

    	                	p_226942_1_.setBlock(newBlockPosition, DoTBBlocksRegistry.MAPLE_RED_LEAVES.get().defaultBlockState()
    	                    		.setValue(MapleTrunkBlock.FACING, direction)
    	                    		.setValue(MapleLeavesBlock.MULTIBLOCK_X, x+1)
    	                    		.setValue(MapleLeavesBlock.MULTIBLOCK_Y, y)
    	                    		.setValue(MapleLeavesBlock.MULTIBLOCK_Z, z+1), 10);
    	                }
    	            }
    	        }
    	 }
     }

     @Override
     public boolean isValidBonemealTarget(IBlockReader p_176473_1_, BlockPos p_176473_2_, BlockState p_176473_3_, boolean p_176473_4_)
     {
        return true;
     }

     @Override
     public boolean isBonemealSuccess(World p_180670_1_, Random p_180670_2_, BlockPos p_180670_3_, BlockState p_180670_4_)
     {    	 
        return (double)p_180670_1_.random.nextFloat() < 0.45D;
     }

     @Override
     public void performBonemeal(ServerWorld p_225535_1_, Random p_225535_2_, BlockPos p_225535_3_, BlockState p_225535_4_)
     {
        this.advanceTree(p_225535_1_, p_225535_3_, p_225535_4_, p_225535_2_);
     }

     /**
      * Glass code like
      */
     
     @OnlyIn(Dist.CLIENT)
     public float getShadeBrightness(BlockState p_220080_1_, IBlockReader p_220080_2_, BlockPos p_220080_3_) {
        return 1.0F;
     }

     public boolean propagatesSkylightDown(BlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_) {
        return true;
     }
}
