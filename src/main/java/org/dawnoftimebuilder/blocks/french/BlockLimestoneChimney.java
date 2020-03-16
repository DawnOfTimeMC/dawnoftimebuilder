package org.dawnoftimebuilder.blocks.french;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.blocks.general.DoTBBlockColumn;
import org.dawnoftimebuilder.enums.EnumsBlock;

import java.util.List;
import java.util.Random;

public class BlockLimestoneChimney extends DoTBBlockColumn {

	private static final PropertyEnum<EnumsBlock.EnumHorizontalAxis> AXIS = PropertyEnum.create("axis", EnumsBlock.EnumHorizontalAxis.class);
    private static final AxisAlignedBB BOT_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    private static final AxisAlignedBB BOT_COLUMN_AABB = new AxisAlignedBB(0.125D, 0.5D, 0.125D, 0.875D, 1.0D, 0.875D);
	private static final AxisAlignedBB MID_COLUMN_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);
    private static final AxisAlignedBB TOP_AABB = new AxisAlignedBB(0.25D, 0.5D, 0.25D, 0.75D, 1.0D, 0.75D);
    private static final AxisAlignedBB TOP_COLUMN_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.5D, 0.875D);

	public BlockLimestoneChimney() {
		super("limestone_chimney", Material.ROCK);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumsBlock.EnumHorizontalAxis.AXIS_X).withProperty(VERTICAL_CONNECTION, EnumsBlock.EnumVerticalConnection.NONE));
		this.setTickRandomly(true);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, VERTICAL_CONNECTION, AXIS);
	}


	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(AXIS, (placer.getHorizontalFacing().getAxis() == EnumFacing.Axis.X) ? EnumsBlock.EnumHorizontalAxis.AXIS_Z : EnumsBlock.EnumHorizontalAxis.AXIS_X);
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
                
            case NONE:
            case BOTH:
            	list.add(MID_COLUMN_AABB);
                break;
            
            case ABOVE:
            	list.add(BOT_AABB);
            	list.add(BOT_COLUMN_AABB);
        }
        return list;
    }

    @Override
    public boolean isSameColumn(IBlockAccess worldIn, BlockPos pos){
        return worldIn.getBlockState(pos).getBlock() instanceof BlockLimestoneChimney;
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

	@Override
	public IBlockState getStateFromMeta(int meta){
		return this.getDefaultState().withProperty(AXIS, (meta < 8) ? EnumsBlock.EnumHorizontalAxis.AXIS_X : EnumsBlock.EnumHorizontalAxis.AXIS_Z);
	}

	@Override
	public int getMetaFromState(IBlockState state){
		return (state.getValue(AXIS) == EnumsBlock.EnumHorizontalAxis.AXIS_X) ? 0 : 8;
	}
}
