package org.dawnoftimebuilder.blocks.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;
import org.dawnoftimebuilder.blocks.general.DoTBBlock;
import org.dawnoftimebuilder.enums.EnumsBlock;
import org.dawnoftimebuilder.items.japanese.ItemStickBundle;

import java.util.Objects;
import java.util.Random;

import static org.dawnoftimebuilder.items.DoTBItems.*;

public class BlockStickBundle extends DoTBBlock implements IBlockCustomItem {

    private static final AxisAlignedBB AABB_TOP = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
    private static final AxisAlignedBB AABB_BOTTOM = new AxisAlignedBB(0.25D, 0.25D, 0.25D, 0.75D, 1.0D, 0.75D);
    public static final PropertyEnum<EnumsBlock.EnumHalf> HALF = PropertyEnum.create("half", EnumsBlock.EnumHalf.class);
	private static final PropertyInteger GROWTH = PropertyInteger.create("growth", 0, 3);

    public BlockStickBundle() {
        super("stick_bundle", Material.WOOD);
        this.setBurnable();
        this.setDefaultState(this.blockState.getBaseState().withProperty(GROWTH, 0).withProperty(HALF, EnumsBlock.EnumHalf.BOTTOM));
    	this.setTickRandomly(true);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	return (state.getValue(HALF) == EnumsBlock.EnumHalf.TOP) ? AABB_TOP : AABB_BOTTOM;
    }

	@Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, GROWTH, HALF);
    }

	@Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.BROWN;
    }

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    	if(state.getValue(HALF) == EnumsBlock.EnumHalf.TOP) drops.add(new ItemStack(stick_bundle, 1));
    	switch(state.getValue(GROWTH)){
			case 4:
				drops.add(new ItemStack(silk_cocoons, 1));
				break;
			case 3:
			case 2:
			case 1:
				drops.add(new ItemStack(silk_worms, 1));
				break;
			default:
		}
	}

	/**
	 * Called when the blocks is right clicked by a player.
	 */
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(state.getValue(GROWTH) == 3){
			spawnAsEntity(worldIn, pos, new ItemStack(silk_cocoons, 1));
			worldIn.setBlockState(pos, state.withProperty(GROWTH, 0));
			if(state.getValue(HALF) == EnumsBlock.EnumHalf.TOP){
				worldIn.setBlockState(pos.down(), this.getDefaultState().withProperty(HALF, EnumsBlock.EnumHalf.BOTTOM).withProperty(GROWTH, 0));
			}else{
				worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(HALF, EnumsBlock.EnumHalf.TOP).withProperty(GROWTH, 0));
			}
			return true;
		}

		if(!worldIn.isRemote && !worldIn.restoringBlockSnapshots){
			if(state.getValue(GROWTH) == 0){
				ItemStack itemstack = playerIn.getHeldItem(hand);
				if(itemstack.getItem() == silk_worms && !itemstack.isEmpty()){
					itemstack.shrink(1);
					worldIn.setBlockState(pos, state.withProperty(GROWTH, 1));
					if(state.getValue(HALF) == EnumsBlock.EnumHalf.TOP){
						worldIn.setBlockState(pos.down(), this.getDefaultState().withProperty(HALF, EnumsBlock.EnumHalf.BOTTOM).withProperty(GROWTH, 1));
					}else{
						worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(HALF, EnumsBlock.EnumHalf.TOP).withProperty(GROWTH, 1));
					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if(state.getValue(HALF) == EnumsBlock.EnumHalf.TOP){
			int growth = state.getValue(GROWTH);
			if (growth > 0 && growth < 3) {
				if(rand.nextInt(5) == 0) {
					worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(GROWTH,growth + 1));
					worldIn.setBlockState(pos.down(), worldIn.getBlockState(pos.down()).withProperty(GROWTH,growth + 1));
				}
			}
		}
	}

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos){
        return super.canPlaceBlockAt(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos.down());
    }

    /**
     * Called when a neighboring blocks was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * blocks, etc.
     */
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if(state.getValue(HALF) == EnumsBlock.EnumHalf.TOP) {
            if(worldIn.getBlockState(pos.down()).getBlock() != this){
				this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
            }else if(worldIn.getBlockState(pos.down()).getValue(HALF) != EnumsBlock.EnumHalf.BOTTOM){
				this.dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
			}
        }else{
			if(worldIn.getBlockState(pos.up()).getBlock() != this){
				this.dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
			}else if(worldIn.getBlockState(pos.up()).getValue(HALF) != EnumsBlock.EnumHalf.TOP){
				this.dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
			}
		}
    }

    @Override
    public EnumPushReaction getPushReaction(IBlockState state) {
        return EnumPushReaction.DESTROY;
    }

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing facing) {
		return (state.getValue(HALF) == EnumsBlock.EnumHalf.TOP && facing == EnumFacing.UP) ? BlockFaceShape.MIDDLE_POLE : BlockFaceShape.UNDEFINED;
	}

	@Override
    public IBlockState getStateFromMeta(int meta) {
    	IBlockState state = this.getDefaultState();
    	if(meta > 3){
    		state = state.withProperty(HALF, EnumsBlock.EnumHalf.TOP);
    		meta -= 4;
		}
        return state.withProperty(GROWTH, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
    	int meta = (state.getValue(HALF) == EnumsBlock.EnumHalf.BOTTOM) ? 0 : 4;
        return meta + state.getValue(GROWTH);
    }
	
	@Override
    public boolean isFullCube(IBlockState state){
        return false;
    }

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

    @Override
    public Item getCustomItemBlock() {
        return new ItemStickBundle()
                .setRegistryName(Objects.requireNonNull(this.getRegistryName()))
                .setTranslationKey(this.getTranslationKey());
    }
}