package org.dawnoftimebuilder.blocks.mayan;

import java.util.List;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.dawnoftimebuilder.blocks.general.DoTBBlockColumn;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;

public class BlockPlasteredStoneColumn extends DoTBBlockColumn {

    private static final AxisAlignedBB LONE_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);
    private static final AxisAlignedBB BOT_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.25D, 0.875D);
    private static final AxisAlignedBB BOT_COLUMN_AABB = new AxisAlignedBB(0.1875D, 0.25D, 0.1875D, 0.8125D, 1.0D, 0.8125D);
	private static final AxisAlignedBB MID_COLUMN_AABB = new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 1.0D, 0.8125D);
    private static final AxisAlignedBB TOP_AABB = new AxisAlignedBB(0.0625D, 0.5D, 0.0625D, 0.9375D, 1.0D, 0.9375D);
    private static final AxisAlignedBB TOP_COLUMN_AABB = new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.5D, 0.8125D);
	
	public BlockPlasteredStoneColumn() {
		super("plastered_stone_column", Material.ROCK, 0.8F, SoundType.STONE);
	}

	@Override
    public List<AxisAlignedBB> getCollisionBoxList(IBlockState state) {
        List<AxisAlignedBB> list = Lists.newArrayList();

        switch (state.getValue(VERTICAL_CONNECTION)) {
        	default:
            case UNDER:
            	list.add(TOP_AABB);
            	list.add(TOP_COLUMN_AABB);
                break;
                
            case BOTH:
            	list.add(MID_COLUMN_AABB);
                break;

            case NONE:
            	list.add(LONE_AABB);
                break;
            
            case ABOVE:
            	list.add(BOT_AABB);
            	list.add(BOT_COLUMN_AABB);
        }
        return list;
    }


    @Override
    public boolean isSameColumn(IBlockAccess worldIn, BlockPos pos){
		return worldIn.getBlockState(pos).getBlock() instanceof BlockPlasteredStoneColumn;
    }
}
