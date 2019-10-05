package org.dawnoftimebuilder.blocks.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.blocks.general.BlockIronChain;
import org.dawnoftimebuilder.blocks.general.DoTBBlockTileEntity;
import org.dawnoftimebuilder.tileentity.DoTBTileEntityStove;

import java.util.Random;

//TODO When needed, a parent class DoTBBlockStove will need to be created and the NBT functionality will have to be added to the container (currently the cast_iron_teapot)
public class BlockIrori extends DoTBBlockTileEntity {

	private static final AxisAlignedBB IRORI_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
	private static PropertyBool BURNING = PropertyBool.create("burning");
	private static PropertyBool HOOK = PropertyBool.create("hook");

	public BlockIrori() {
		super("irori", Material.SAND);
		this.setDefaultState(this.getDefaultState().withProperty(BURNING, false).withProperty(HOOK, false));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand){
		if(stateIn.getValue(BURNING)) {
			if (rand.nextInt(24) == 0) {
				worldIn.playSound((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
			}
			for (int i = 0; i < 2; ++i) {
				double x = (double) pos.getX() + rand.nextDouble() * 0.5D + 0.25D;
				double y = (double) pos.getY() + rand.nextDouble() * 0.5D + 0.6D;
				double z = (double) pos.getZ() + rand.nextDouble() * 0.5D + 0.25D;
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack itemstack = playerIn.getHeldItem(hand);
		Item item = itemstack.getItem();
		DoTBTileEntityStove te = (DoTBTileEntityStove) worldIn.getTileEntity(pos);

		if(!state.getValue(BURNING)) {
			if(!itemstack.isEmpty() && (item == Items.FLINT_AND_STEEL || item == Item.getItemFromBlock(Blocks.TORCH))){
				worldIn.setBlockState(pos, state.withProperty(BURNING, true), 10);
				worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
				if (itemstack.getItem() == Items.FLINT_AND_STEEL) itemstack.damageItem(1, playerIn);
				else if (!playerIn.capabilities.isCreativeMode) itemstack.shrink(1);
				return true;
			}
		}

		if (te != null) {
			if (!te.isEmpty()) {
				spawnAsEntity(worldIn, pos, te.getItemStack());
				te.removeItemStack();
				worldIn.notifyBlockUpdate(pos, state, state, 2);
				return true;
			} else {
				if (item instanceof ItemBlock && !itemstack.isEmpty() && state.getValue(HOOK)) {
					if (((ItemBlock) item).getBlock() instanceof BlockCastIronTeapot) {
						te.putItemStack(item, itemstack.getMetadata());
						worldIn.notifyBlockUpdate(pos, state, state, 2);
						if (!playerIn.capabilities.isCreativeMode) itemstack.shrink(1);
						return true;
					}
				}
			}
		}

		if(state.getValue(BURNING)) {
			worldIn.setBlockState(pos, state.withProperty(BURNING, false), 3);
			worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
			return true;
		}

		return false;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos){
		boolean needHook = worldIn.getBlockState(pos.up()).getBlock() instanceof BlockIronChain;
		if(state.getValue(HOOK) != needHook) worldIn.setBlockState(pos, state.withProperty(HOOK, needHook), 3);

		if(!needHook){
			DoTBTileEntityStove te = (DoTBTileEntityStove) worldIn.getTileEntity(pos);

			if (te != null) {
				if (!te.isEmpty()) {
					spawnAsEntity(worldIn, pos, te.getItemStack());
					te.removeItemStack();
				}
			}
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return IRORI_AABB;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
		return FULL_BLOCK_AABB;
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos){
		return state.getValue(BURNING) ? 14 : 0;
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(HOOK, worldIn.getBlockState(pos.up()).getBlock() instanceof BlockIronChain);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState();
		if(meta > 1){
			state = state.withProperty(HOOK, true);
			meta -= 2;
		}
		return state.withProperty(BURNING, meta == 1);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = state.getValue(BURNING) ? 1 : 0;
		if(state.getValue(HOOK)) meta += 2;
		return meta;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BURNING, HOOK);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new DoTBTileEntityStove();
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
		return BlockFaceShape.UNDEFINED;
	}
}
