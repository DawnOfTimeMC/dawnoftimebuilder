package org.dawnoftimebuilder.blocks.general;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.blocks.japanese.BlockStickBundle;
import org.dawnoftimebuilder.blocks.japanese.BlockStoneLantern;
import org.dawnoftimebuilder.enums.EnumsBlock;

import java.util.List;

public class BlockIronChain extends DoTBBlockColumn {

    private static final AxisAlignedBB AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);

	public BlockIronChain() {
		super("iron_chain", Material.IRON, 2.0F, SoundType.METAL);
	}

	@Override
    public List<AxisAlignedBB> getCollisionBoxList(IBlockState state) {
        List<AxisAlignedBB> list = Lists.newArrayList();
		list.add(AABB);
        return list;
    }

    @Override
	public EnumsBlock.EnumVerticalConnection getShape(IBlockAccess worldIn, BlockPos pos){
		if(isSameColumn(worldIn, pos.up())){
			return (canConnectToBottom(worldIn, pos.down())) ? EnumsBlock.EnumVerticalConnection.BOTH : EnumsBlock.EnumVerticalConnection.ABOVE;
		}else{
			return (canConnectToBottom(worldIn, pos.down())) ? EnumsBlock.EnumVerticalConnection.UNDER : EnumsBlock.EnumVerticalConnection.NONE;
		}
	}

	private boolean canConnectToBottom(IBlockAccess worldIn, BlockPos pos){
		Block block = worldIn.getBlockState(pos).getBlock();
		if(block instanceof BlockStoneLantern)
			if(worldIn.getBlockState(pos).getValue(BlockStoneLantern.FACING) == EnumFacing.DOWN)
				return true;
		if(block instanceof BlockStickBundle) return true;
		return isSameColumn(worldIn, pos);
	}

    @Override
    public boolean isSameColumn(IBlockAccess worldIn, BlockPos pos){
        return worldIn.getBlockState(pos).getBlock() instanceof BlockIronChain;
    }

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
}
