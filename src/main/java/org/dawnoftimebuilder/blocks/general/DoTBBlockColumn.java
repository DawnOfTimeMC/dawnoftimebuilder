package org.dawnoftimebuilder.blocks.general;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.util.EnumFacing;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.dawnoftimebuilder.DoTBUtils;
import org.dawnoftimebuilder.enums.EnumsBlock;

public abstract class DoTBBlockColumn extends DoTBBlock {

	protected static final PropertyEnum<EnumsBlock.EnumVerticalConnection> VERTICAL_CONNECTION = PropertyEnum.create("vertical_connection", EnumsBlock.EnumVerticalConnection.class);

	public DoTBBlockColumn(String name, Material materialIn, float hardness, SoundType sound) {
		super(name, materialIn, hardness, sound);
	}

	public DoTBBlockColumn(String name, Material materialIn) {
		super(name, materialIn);
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
		if (!p_185477_7_) state = this.getActualState(state, worldIn, pos);
		for (AxisAlignedBB axisalignedbb : getCollisionBoxList(state)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, axisalignedbb);
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return DoTBUtils.getMainAABB(getCollisionBoxList(this.getActualState(state, source, pos)));
	}

	public List<AxisAlignedBB> getCollisionBoxList(IBlockState state) {
		return null;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos){
		return state.withProperty(VERTICAL_CONNECTION, this.getShape(worldIn, pos));
	}

	public EnumsBlock.EnumVerticalConnection getShape(IBlockAccess worldIn, BlockPos pos){
		if(isSameColumn(worldIn, pos.up())){
			return (isSameColumn(worldIn, pos.down())) ? EnumsBlock.EnumVerticalConnection.BOTH : EnumsBlock.EnumVerticalConnection.ABOVE;
		}else{
			return (isSameColumn(worldIn, pos.down())) ? EnumsBlock.EnumVerticalConnection.UNDER : EnumsBlock.EnumVerticalConnection.NONE;
		}
	}

	public boolean isSameColumn(IBlockAccess worldIn, BlockPos pos){
		return worldIn.getBlockState(pos).getBlock() instanceof DoTBBlockColumn;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, VERTICAL_CONNECTION);
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
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing side){
		return side.getAxis().isVertical() ? BlockFaceShape.CENTER_BIG : BlockFaceShape.UNDEFINED;
	}
}