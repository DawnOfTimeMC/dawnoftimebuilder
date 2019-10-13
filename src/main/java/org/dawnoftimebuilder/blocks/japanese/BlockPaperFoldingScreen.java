package org.dawnoftimebuilder.blocks.japanese;

import com.google.common.collect.Lists;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.dawnoftimebuilder.blocks.general.DoTBBlockColumn;
import org.dawnoftimebuilder.enums.EnumsBlock;

import java.util.List;

public class BlockPaperFoldingScreen extends DoTBBlockColumn {

	private static final PropertyBool ROTATED = PropertyBool.create("rotated");

	public BlockPaperFoldingScreen() {
		super("paper_folding_screen", Material.CLOTH);
		this.setDefaultState(this.getDefaultState().withProperty(ROTATED, false).withProperty(VERTICAL_CONNECTION, EnumsBlock.EnumVerticalConnection.NONE));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, ROTATED, VERTICAL_CONNECTION);
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(ROTATED, isRotated(pos));
	}

	private boolean isRotated(BlockPos pos){
		return ((pos.getX() + pos.getZ()) % 2) == 0;
	}

	@Override
	public List<AxisAlignedBB> getCollisionBoxList(IBlockState state) {
		List<AxisAlignedBB> list = Lists.newArrayList();
		list.add(FULL_BLOCK_AABB);
		return list;
	}

	@Override
	public int getMetaFromState(IBlockState state){
		return state.getValue(ROTATED) ? 1 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta){
		return this.getDefaultState().withProperty(ROTATED, meta == 1);
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing side){
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public boolean isSameColumn(IBlockAccess worldIn, BlockPos pos){
		return worldIn.getBlockState(pos).getBlock() instanceof BlockPaperFoldingScreen;
	}

	@Override
	public EnumsBlock.EnumVerticalConnection getShape(IBlockAccess worldIn, BlockPos pos){
		if(!(worldIn.getBlockState(pos).getBlock() instanceof BlockPaperFoldingScreen)) return EnumsBlock.EnumVerticalConnection.NONE;
		boolean rotated = worldIn.getBlockState(pos).getValue(ROTATED);
		if(isSameColumn(worldIn, pos.up())) {
			if(worldIn.getBlockState(pos.up()).getValue(ROTATED) == rotated){

				if (isSameColumn(worldIn, pos.down())) {
					if(worldIn.getBlockState(pos.down()).getValue(ROTATED) == rotated){
						return EnumsBlock.EnumVerticalConnection.BOTH;
					}
				}
				return EnumsBlock.EnumVerticalConnection.ABOVE;
			}
		}

		if (isSameColumn(worldIn, pos.down())) {
			if(worldIn.getBlockState(pos.down()).getValue(ROTATED) == rotated){
				return EnumsBlock.EnumVerticalConnection.UNDER;
			}
		}
		return EnumsBlock.EnumVerticalConnection.NONE;
	}
}
