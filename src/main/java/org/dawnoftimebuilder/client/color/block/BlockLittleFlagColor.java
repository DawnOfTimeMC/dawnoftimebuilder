package org.dawnoftimebuilder.client.color.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.dawnoftimebuilder.tileentity.TileEntityLittleFlag;

public class BlockLittleFlagColor implements IBlockColor{

	@Override
	public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
		
		TileEntityLittleFlag flag = (TileEntityLittleFlag) worldIn.getTileEntity(pos);
		
		if(flag != null){
			return flag.getItemColor();
		}else{
			return 0xFFFFFF;
		}
		
	}

}
