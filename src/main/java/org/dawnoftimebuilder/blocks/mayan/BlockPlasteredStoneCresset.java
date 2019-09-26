package org.dawnoftimebuilder.blocks.mayan;

import java.util.Random;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.blocks.general.DoTBBlock;

public class BlockPlasteredStoneCresset extends DoTBBlock {

    private static final PropertyInteger LIT = PropertyInteger.create("lit", 0, 15);
    
    public BlockPlasteredStoneCresset() {
        super("plastered_stone_cresset", Material.ROCK);
        this.setTickRandomly(true);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.875F, 0.8125F);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LIT);
    }
    
    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        int lit = state.getValue(LIT) - 1;
        if(lit == 2) worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.4F, 1.0F);
        if(lit >= 0) worldIn.setBlockState(pos, state.withProperty(LIT, lit));
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        IBlockState other = world.getBlockState(pos);
        if (other.getBlock() != this) return other.getLightValue(world, pos);
        if (state.getValue(LIT) > 2) return 15;
        else if(state.getValue(LIT) > 0) return 5;
        else return 0;
    }

    /**
     * Called when the blocks is right clicked by a player.
     */
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = playerIn.getHeldItem(hand);
        if (!itemstack.isEmpty() && (itemstack.getItem() == Items.FLINT_AND_STEEL  || itemstack.getItem() == Item.getItemFromBlock(Blocks.TORCH))) {
            
        	worldIn.setBlockState(pos, getDefaultState().withProperty(LIT, 15));

            worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (itemstack.getItem() == Items.FLINT_AND_STEEL) itemstack.damageItem(1, playerIn);
            else if (!playerIn.capabilities.isCreativeMode) itemstack.shrink(1);
            return true;
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand){
        int lit = stateIn.getValue(LIT);
        if (rand.nextInt(24) == 0 && lit > 2) {
            worldIn.playSound((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
        }
        if (lit > 0){
            for (int i = 0; i < 3; ++i) {
                double d0 = (double)pos.getX() + rand.nextDouble() * 0.5D + 0.25D;
                double d1 = (double)pos.getY() + rand.nextDouble() * 0.5D + 0.6D;
                double d2 = (double)pos.getZ() + rand.nextDouble() * 0.5D + 0.25D;
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(LIT, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(LIT);
    }
    
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
	    return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
	    return false;
	}
    
	@Override
	public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
		return false;
	}
    
    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
	
	@Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
        return BlockFaceShape.UNDEFINED;
    }
}