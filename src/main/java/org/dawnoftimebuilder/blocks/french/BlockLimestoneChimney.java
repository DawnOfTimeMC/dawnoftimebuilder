package org.dawnoftimebuilder.blocks.french;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.DoTBUtils;
import org.dawnoftimebuilder.blocks.global.DoTBBlockColumn;
import org.dawnoftimebuilder.enums.EnumsBlock;

import java.util.List;
import java.util.Random;

public class BlockLimestoneChimney extends DoTBBlockColumn {

    private static final AxisAlignedBB BOT_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    private static final AxisAlignedBB BOTCOLUMN_AABB = new AxisAlignedBB(0.125D, 0.5D, 0.125D, 0.875D, 1.0D, 0.875D);
	private static final AxisAlignedBB MIDCOLUMN_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);
    private static final AxisAlignedBB TOP_AABB = new AxisAlignedBB(0.25D, 0.5D, 0.25D, 0.75D, 1.0D, 0.75D);
    private static final AxisAlignedBB TOPCOLUMN_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.5D, 0.875D);

	public BlockLimestoneChimney() {
		super("limestone_chimney", Material.ROCK, 0.8F, SoundType.STONE);
        this.setTickRandomly(true);
	}

	@Override
    public List<AxisAlignedBB> getCollisionBoxList(IBlockState state) {
        List<AxisAlignedBB> list = Lists.newArrayList();

        switch (state.getValue(VERTICAL_CONNECTION)) {
        	default:
            case UNDER:
            	list.add(TOP_AABB);
            	list.add(TOPCOLUMN_AABB);
                break;
                
            case NONE:
            case BOTH:
            	list.add(MIDCOLUMN_AABB);
                break;
            
            case ABOVE:
            	list.add(BOT_AABB);
            	list.add(BOTCOLUMN_AABB);
        }
        return list;
    }

    @Override
    public boolean isSameColumn(Block block){
        return block instanceof BlockLimestoneChimney;
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand){
        EnumsBlock.EnumVerticalConnection connection = this.getActualState(stateIn, worldIn, pos).getValue(VERTICAL_CONNECTION);
        if(connection == EnumsBlock.EnumVerticalConnection.UNDER || connection == EnumsBlock.EnumVerticalConnection.NONE) {
            for (int i = 0; i < 5; ++i) {
                double d0 = (double) pos.getX() + rand.nextDouble() * 0.5D + 0.25D;
                double d1 = (double) pos.getY() + rand.nextDouble() * 0.5D + 0.3D;
                double d2 = (double) pos.getZ() + rand.nextDouble() * 0.5D + 0.25D;
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
