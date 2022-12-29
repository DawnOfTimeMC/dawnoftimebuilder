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
import net.minecraft.block.IGrowable;
import net.minecraft.block.SaplingBlock;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class MapleSaplingBlock extends BushBlockDoT implements IBlockGeneration, ICustomBlockItem, IGrowable
{
    public MapleSaplingBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState());
        SaplingBlock a;
    }

    /**
     * Cypress code like
     */
    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        return canSupportCenter(worldIn, pos.below(), Direction.UP) || worldIn.getBlockState(pos.below()).getBlock() == this;
    }

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

    /**@Override
    public boolean isRandomlyTicking(BlockState blockstateIn)
    {
    	return true;
    }**/

	@Override
    public void randomTick(BlockState p_225542_1_, ServerWorld p_225542_2_, BlockPos p_225542_3_, Random p_225542_4_)
    {
    	super.randomTick(p_225542_1_, p_225542_2_, p_225542_3_, p_225542_4_);

        if (p_225542_2_.getMaxLocalRawBrightness(p_225542_3_.above()) >= 9 && p_225542_4_.nextInt(14) == 0)
        {
	        if (!p_225542_2_.isAreaLoaded(p_225542_3_, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
	           this.advanceTree(p_225542_2_, p_225542_3_, p_225542_1_, p_225542_4_);
        }
    }

     public void advanceTree(ServerWorld p_226942_1_, BlockPos p_226942_2_, BlockState p_226942_3_, Random p_226942_4_)
     {
    	 if(MapleTrunkBlock.isValidForPlacement(p_226942_1_, p_226942_2_, true))
    	 {
    		 p_226942_1_.setBlock(p_226942_2_, DoTBBlocksRegistry.MAPLE_RED_TRUNK.get().defaultBlockState(), 4); 
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
}
