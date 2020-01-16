package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dawnoftimebuilder.DawnOfTimeBuilder;
import org.dawnoftimebuilder.blocks.IBlockDisplayer;
import org.dawnoftimebuilder.client.gui.DoTGuiHandler;
import org.dawnoftimebuilder.tileentity.DoTBTileEntityDisplayer;

public abstract class DoTBBlockDisplayer extends DoTBBlockTileEntity implements IBlockDisplayer {

	public DoTBBlockDisplayer(String name, Material materialIn, float hardness, SoundType sound) {
		this(name, materialIn);
		this.setHardness(hardness);
		this.setSoundType(sound);
	}

	public DoTBBlockDisplayer(String name, Material materialIn) {
		super(name, materialIn);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new DoTBTileEntityDisplayer();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(!worldIn.isRemote){
			playerIn.openGui(DawnOfTimeBuilder.instance, DoTGuiHandler.DISPLAYER_GUI, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
		DoTBTileEntityDisplayer tileEntity = (DoTBTileEntityDisplayer) worldIn.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(worldIn, pos, tileEntity);
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
		if(stack.hasDisplayName()){
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if(tileEntity instanceof TileEntityDispenser) ((TileEntityDispenser)tileEntity).setCustomName(stack.getDisplayName());
		}
	}
}
