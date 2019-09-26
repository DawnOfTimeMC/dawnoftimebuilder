package org.dawnoftimebuilder.blocks.japanese;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;
import org.dawnoftimebuilder.blocks.general.DoTBBlock;
import org.dawnoftimebuilder.enums.EnumsBlock;
import org.dawnoftimebuilder.items.japanese.ItemSpruceRoofSupport;

public class BlockSpruceRoofSupport extends DoTBBlock implements IBlockCustomItem {

	private static final AxisAlignedBB AABB_HALF = new AxisAlignedBB(0.0D, 0.6875D, 0.0D, 1.0D, 1.0D, 1.0D);

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	private static final PropertyEnum<EnumsBlock.EnumStairsShape> SHAPE = PropertyEnum.create("shape", EnumsBlock.EnumStairsShape.class);

	public BlockSpruceRoofSupport(String name) {
		super(name, Material.WOOD, 1.0F, SoundType.WOOD);

		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(SHAPE, EnumsBlock.EnumStairsShape.STRAIGHT));
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, SHAPE);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB_HALF;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing facing) {
		return BlockFaceShape.UNDEFINED;
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies properties not visible in the
	 * metadata, such as fence connections.
	 */
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(SHAPE, this.getShape(state, worldIn, pos));
	}

	private EnumsBlock.EnumStairsShape getShape(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		EnumFacing facing = state.getValue(FACING);
		BlockSpruceRoofSupport block = (BlockSpruceRoofSupport) state.getBlock();

		IBlockState stateAdjacent = worldIn.getBlockState(pos.offset(facing));
		if (stateAdjacent.getBlock() == this) {
			EnumFacing facingAdjacent = stateAdjacent.getValue(FACING);

			if (facingAdjacent.getAxis() != state.getValue(FACING).getAxis() && block.isNotConnectible(state, worldIn, pos, facingAdjacent.getOpposite())) {
				if (facingAdjacent == facing.rotateYCCW()) return EnumsBlock.EnumStairsShape.OUTER_LEFT;
				else return EnumsBlock.EnumStairsShape.OUTER_RIGHT;
			}
		}

		IBlockState stateOpposite = worldIn.getBlockState(pos.offset(facing.getOpposite()));
		if (stateOpposite.getBlock() == this) {
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
		if(stateAdjacent.getBlock() != this) return true;
		return stateAdjacent.getValue(FACING) != state.getValue(FACING);
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
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

	@Override
	public Item getCustomItemBlock() {
		return new ItemSpruceRoofSupport();
	}
}
