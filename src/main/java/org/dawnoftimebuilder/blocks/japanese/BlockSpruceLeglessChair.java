package org.dawnoftimebuilder.blocks.japanese;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.DoTBUtils;
import org.dawnoftimebuilder.blocks.general.DoTBBlockTileEntity;
import org.dawnoftimebuilder.entities.EntitySeat;

import javax.annotation.Nullable;
import java.util.List;

public class BlockSpruceLeglessChair extends DoTBBlockTileEntity {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	private static final AxisAlignedBB BOTTOM_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.0D, 0.875D, 0.1875D, 1.0D);
	private static final AxisAlignedBB BACK_AABB = new AxisAlignedBB(0.125D, 0.25D, 0.75D, 0.875D, 0.6875D, 1.0D);

	public BlockSpruceLeglessChair() {
		super("spruce_legless_chair", Material.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
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

	private static List<AxisAlignedBB> getCollisionBoxList(IBlockState state) {
		List<AxisAlignedBB> list = Lists.newArrayList();
		list.add(DoTBUtils.getRotatedAABB(BOTTOM_AABB, state.getValue(FACING)));
		list.add(DoTBUtils.getRotatedAABB(BACK_AABB, state.getValue(FACING)));
		return list;
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
	    return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
	    return false;
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
	    return BlockRenderLayer.SOLID;
	}

	@Override
	public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
		return false;
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
	public IBlockState withRotation(IBlockState state, Rotation rot) {
	    return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
	    return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}
	
	@Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
        return BlockFaceShape.UNDEFINED;
    }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return null;
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
		return isSomeoneSitting(worldIn, pos.getX(), pos.getY(), pos.getZ()) ? 1 : 0;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!playerIn.isSneaking()) {
			if(sitOnChair(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, 0.0D, state.getValue(FACING), -0.1D)) {
				worldIn.updateComparatorOutputLevel(pos, this);
				return true;
			}
		}
		return false;
	}

	public static boolean sitOnChair(World world, double x, double y, double z, EntityPlayer player, double yOffset, EnumFacing facing, double rotationOffset) {
		if(!world.isRemote && !player.isSneaking() && !checkForExistingSeat(world, x, y, z, player)) {
			EntitySeat seat = new EntitySeat(world, x, y, z, yOffset, facing, rotationOffset);
			world.spawnEntity(seat);
			player.startRiding(seat);
		}
		return true;
	}

	private static boolean checkForExistingSeat(World world, double x, double y, double z, EntityPlayer player) {
		if(!world.isRemote) {
			List<EntitySeat> seats = world.getEntitiesWithinAABB(EntitySeat.class, new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).grow(1D));
			for(EntitySeat seat : seats){
				if(seat.blockPosX == x && seat.blockPosY == y && seat.blockPosZ == z){
					if(!seat.isBeingRidden()) player.startRiding(seat);
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isSomeoneSitting(World world, double x, double y, double z){
		if(!world.isRemote){
			List<EntitySeat> seats = world.getEntitiesWithinAABB(EntitySeat.class, new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).grow(1D));
			for(EntitySeat seat : seats){
				if(seat.blockPosX == x && seat.blockPosY == y && seat.blockPosZ == z) return seat.isBeingRidden();
			}
		}
		return false;
	}
}
