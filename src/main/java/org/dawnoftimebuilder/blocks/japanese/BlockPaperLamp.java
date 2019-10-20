package org.dawnoftimebuilder.blocks.japanese;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.dawnoftimebuilder.blocks.IBlockSpecialDisplay;
import org.dawnoftimebuilder.blocks.general.DoTBBlockColumn;

import java.util.List;

public class BlockPaperLamp extends DoTBBlockColumn implements IBlockSpecialDisplay {

    private static final AxisAlignedBB BOT_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
	private static final AxisAlignedBB TOP_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.8125D, 0.75D);

	public BlockPaperLamp() {
		super("paper_lamp", Material.CLOTH);
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return 14;
	}

	public List<AxisAlignedBB> getCollisionBoxList(IBlockState state) {
		List<AxisAlignedBB> list = Lists.newArrayList();

		switch (state.getValue(VERTICAL_CONNECTION)) {
			default:
			case NONE:
			case UNDER:
				list.add(TOP_AABB);
				break;

			case BOTH:
			case ABOVE:
				list.add(BOT_AABB);
		}
		return list;
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch (this.getActualState(state, source, pos).getValue(VERTICAL_CONNECTION)) {
			default:
			case UNDER:
			case NONE:
				return TOP_AABB;
			case BOTH:
			case ABOVE:
				return BOT_AABB;
		}
	}

    @Override
    public boolean isSameColumn(IBlockAccess worldIn, BlockPos pos){
		return worldIn.getBlockState(pos).getBlock() instanceof BlockPaperLamp;
    }

	@Override
	public float getDisplayScale(int meta) {
		return 0.5f;
	}
}
