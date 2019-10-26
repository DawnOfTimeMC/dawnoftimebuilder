package org.dawnoftimebuilder.blocks.general;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockWall;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.dawnoftimebuilder.DoTBUtils;
import org.dawnoftimebuilder.blocks.japanese.BlockSpruceRailing;
import org.dawnoftimebuilder.enums.EnumsBlock;

import javax.annotation.Nullable;
import java.util.List;

public class DoTBBlockSupportSlab extends DoTBBlock {

    private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.75D, 0.0D, 1.0D, 1.0D, 1.0D);
	private static final AxisAlignedBB AABB_FOUR_PX = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.75D, 0.625D);
	private static final AxisAlignedBB AABB_EIGHT_PX = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.75D, 0.75D);
	private static final AxisAlignedBB AABB_TEN_PX = new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.75D, 0.8125D);

	private static final PropertyEnum<EnumsBlock.EnumUnderConnection> UNDER = PropertyEnum.create("under", EnumsBlock.EnumUnderConnection.class);

	public DoTBBlockSupportSlab(String name, Material materialIn, float hardness, SoundType sound) {
		super(name, materialIn, hardness, sound);
		this.setDefaultState(this.blockState.getBaseState().withProperty(UNDER, EnumsBlock.EnumUnderConnection.NOTHING));
	}

	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
		if (!p_185477_7_) state = this.getActualState(state, worldIn, pos);
		for (AxisAlignedBB axisalignedbb : getCollisionBoxList(state)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, axisalignedbb);
		}
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return DoTBUtils.getMainAABB(getCollisionBoxList(this.getActualState(state, source, pos)));
	}

	public List<AxisAlignedBB> getCollisionBoxList(IBlockState state) {
		List<AxisAlignedBB> list = Lists.newArrayList();
		list.add(AABB);
		switch(state.getValue(UNDER)) {
			case FOUR_PX:
				list.add(AABB_FOUR_PX);
				break;
			case EIGHT_PX:
				list.add(AABB_EIGHT_PX);
				break;
			case TEN_PX:
				list.add(AABB_TEN_PX);
				break;
			default:
		}
		return list;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, UNDER);
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies properties not visible in the
	 * metadata, such as fence connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		Block blockUnder = worldIn.getBlockState(pos.down()).getBlock();
		if(blockUnder instanceof BlockSpruceRailing){
			BlockSpruceRailing block = (BlockSpruceRailing) blockUnder;
			switch(block.getShape(worldIn.getBlockState(pos.down()))) {
				case NONE:
					return state.withProperty(UNDER, EnumsBlock.EnumUnderConnection.NOTHING);
				case PILLAR_SMALL:
					return state.withProperty(UNDER, EnumsBlock.EnumUnderConnection.FOUR_PX);
				default:
					return state.withProperty(UNDER, EnumsBlock.EnumUnderConnection.EIGHT_PX);
			}
		}
		if(blockUnder instanceof BlockFence) return state.withProperty(UNDER, EnumsBlock.EnumUnderConnection.FOUR_PX);
		if(blockUnder instanceof BlockWall || blockUnder instanceof DoTBBlockWall) return state.withProperty(UNDER, EnumsBlock.EnumUnderConnection.EIGHT_PX);
		if(blockUnder instanceof DoTBBlockBeam) if(worldIn.getBlockState(pos.down()).getValue(DoTBBlockBeam.MAIN_AXIS).isVertical()) return state.withProperty(UNDER, EnumsBlock.EnumUnderConnection.TEN_PX);
		return state.withProperty(UNDER, EnumsBlock.EnumUnderConnection.NOTHING);
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
		return side == EnumFacing.UP;
	}

	@Override
	public boolean isTopSolid(IBlockState state) {
		return true;
	}

	@Override
	public int getMetaFromState(IBlockState state){
		return 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta){
		return this.getDefaultState();
	}

	@Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing side) {
		switch(side) {
			case DOWN:
				state = this.getActualState(state, worldIn, pos);
				switch(state.getValue(UNDER)) {
					case FOUR_PX:
						return BlockFaceShape.CENTER;
					case EIGHT_PX:
					case TEN_PX:
						return BlockFaceShape.CENTER_BIG;
					default:
						return BlockFaceShape.UNDEFINED;
				}
			case UP:
				return BlockFaceShape.SOLID;
			default:
				return BlockFaceShape.UNDEFINED;
		}
    }
}