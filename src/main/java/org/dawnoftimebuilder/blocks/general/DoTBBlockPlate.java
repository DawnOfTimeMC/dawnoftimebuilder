package org.dawnoftimebuilder.blocks.general;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.SoundType;
import org.dawnoftimebuilder.DoTBUtils;
import com.google.common.collect.Lists;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.dawnoftimebuilder.enums.EnumsBlock;

public class DoTBBlockPlate extends DoTBBlock {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	private static final PropertyEnum<EnumsBlock.EnumStairsShape> SHAPE = PropertyEnum.create("shape", EnumsBlock.EnumStairsShape.class);
	private static final AxisAlignedBB AABB_HLF_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
	private static final AxisAlignedBB AABB_QTR_NW = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 0.5D);

	public DoTBBlockPlate(String name, Material materialIn, float hardness, SoundType sound) {
		super(name, materialIn, hardness, sound);

		this.useNeighborBrightness = true;
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(SHAPE, EnumsBlock.EnumStairsShape.STRAIGHT));
		this.setLightOpacity(255);
	}

	public DoTBBlockPlate(String name) {
		this(name, Material.ROCK, 1.5F, SoundType.STONE);
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, SHAPE);
	}

	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
		if (!isActualState) state = this.getActualState(state, worldIn, pos);
		for (AxisAlignedBB axisalignedbb : getCollisionBoxList(state)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, axisalignedbb);
		}
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return DoTBUtils.getMainAABB(getCollisionBoxList(this.getActualState(state, source, pos)));
	}

	private static List<AxisAlignedBB> getCollisionBoxList(IBlockState state) {
		List<AxisAlignedBB> list = Lists.newArrayList();
		EnumFacing facing = state.getValue(FACING);
		EnumsBlock.EnumStairsShape shape = state.getValue(SHAPE);
		switch (shape) {
			default:
			case STRAIGHT:
				list.add(DoTBUtils.getRotatedAABB(AABB_HLF_NORTH, facing));
				break;

			case OUTER_LEFT:
				list.add(DoTBUtils.getRotatedAABB(AABB_QTR_NW, facing));
				break;

			case OUTER_RIGHT:
				list.add(DoTBUtils.getRotatedAABB(AABB_QTR_NW, facing.rotateY()));
				break;

			case INNER_LEFT:
				list.add(DoTBUtils.getRotatedAABB(AABB_HLF_NORTH, facing));
				list.add(DoTBUtils.getRotatedAABB(AABB_QTR_NW, facing.rotateYCCW()));
				break;

			case INNER_RIGHT:
				list.add(DoTBUtils.getRotatedAABB(AABB_HLF_NORTH, facing));
				list.add(DoTBUtils.getRotatedAABB(AABB_QTR_NW, facing.getOpposite()));
				break;
		}
		return list;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing facing) {
		state = this.getActualState(state, worldIn, pos);

		EnumsBlock.EnumStairsShape shape = state.getValue(SHAPE);

		if (shape != EnumsBlock.EnumStairsShape.OUTER_LEFT && shape != EnumsBlock.EnumStairsShape.OUTER_RIGHT) {
			EnumFacing enumfacing = state.getValue(FACING);
			if(facing.getAxis() == EnumFacing.Axis.Y) return BlockFaceShape.UNDEFINED;
			switch (shape) {
				case INNER_RIGHT:
					return enumfacing != facing && enumfacing != facing.rotateYCCW() ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
				case INNER_LEFT:
					return enumfacing != facing && enumfacing != facing.rotateY() ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
				case STRAIGHT:
					return enumfacing == facing ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
				default:
					return BlockFaceShape.UNDEFINED;
			}
		} else return BlockFaceShape.UNDEFINED;
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies properties not visible in the
	 * metadata, such as fence connections.
	 */
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(SHAPE, getVerticalSlabShape(state, worldIn, pos));
	}

	private static EnumsBlock.EnumStairsShape getVerticalSlabShape(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		EnumFacing facing = state.getValue(FACING);
		DoTBBlockPlate block = (DoTBBlockPlate) state.getBlock();

		IBlockState stateAdjacent = worldIn.getBlockState(pos.offset(facing));
		if (stateAdjacent.getBlock() instanceof DoTBBlockPlate) {
			EnumFacing facingAdjacent = stateAdjacent.getValue(FACING);

			if (facingAdjacent.getAxis() != state.getValue(FACING).getAxis() && block.isNotConnectible(state, worldIn, pos, facingAdjacent.getOpposite())) {
				if (facingAdjacent == facing.rotateYCCW()) return EnumsBlock.EnumStairsShape.OUTER_LEFT;
				else return EnumsBlock.EnumStairsShape.OUTER_RIGHT;
			}
		}

		IBlockState stateOpposite = worldIn.getBlockState(pos.offset(facing.getOpposite()));
		if (stateOpposite.getBlock() instanceof DoTBBlockPlate) {
			EnumFacing facingOpposite = stateOpposite.getValue(FACING);

			if (facingOpposite.getAxis() != state.getValue(FACING).getAxis() && block.isNotConnectible(state, worldIn, pos, facingOpposite)) {
				if (facingOpposite == facing.rotateYCCW()) return EnumsBlock.EnumStairsShape.INNER_LEFT;
				else return EnumsBlock.EnumStairsShape.INNER_RIGHT;
			}
		}

		return EnumsBlock.EnumStairsShape.STRAIGHT;
	}

	private boolean isNotConnectible(IBlockState state, IBlockAccess worldIn, BlockPos pos, EnumFacing direction) {
		IBlockState stateAdjacent = worldIn.getBlockState(pos.offset(direction));
		return !isConnectibleWithDoTPlate(stateAdjacent) || stateAdjacent.getValue(FACING) != state.getValue(FACING);
	}

	private static boolean isConnectibleWithDoTPlate(IBlockState state) {
		return state.getBlock() instanceof DoTBBlockPlate;
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()).withProperty(SHAPE, EnumsBlock.EnumStairsShape.STRAIGHT);
	}

	@Override
	public int getMetaFromState(IBlockState state){
		return state.getValue(FACING).getHorizontalIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta){
		return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
	}

	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state){
		return false;
	}

	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		EnumFacing enumfacing = state.getValue(FACING);
		EnumsBlock.EnumStairsShape shape = state.getValue(SHAPE);

		switch (mirrorIn) {
			case LEFT_RIGHT:
				if (enumfacing.getAxis() == EnumFacing.Axis.Z) {
					switch (shape) {
						case OUTER_LEFT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumsBlock.EnumStairsShape.OUTER_RIGHT);
						case OUTER_RIGHT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumsBlock.EnumStairsShape.OUTER_LEFT);
						case INNER_RIGHT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumsBlock.EnumStairsShape.INNER_LEFT);
						case INNER_LEFT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumsBlock.EnumStairsShape.INNER_RIGHT);
						default:
							return state.withRotation(Rotation.CLOCKWISE_180);
					}
				}
				break;

			case FRONT_BACK:
				if (enumfacing.getAxis() == EnumFacing.Axis.X) {
					switch (shape) {
						case OUTER_LEFT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumsBlock.EnumStairsShape.OUTER_RIGHT);
						case OUTER_RIGHT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumsBlock.EnumStairsShape.OUTER_LEFT);
						case INNER_RIGHT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumsBlock.EnumStairsShape.INNER_RIGHT);
						case INNER_LEFT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumsBlock.EnumStairsShape.INNER_LEFT);
						case STRAIGHT:
							return state.withRotation(Rotation.CLOCKWISE_180);
					}
				}
		}
		return state;
	}
}