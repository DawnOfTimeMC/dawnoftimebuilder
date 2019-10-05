package org.dawnoftimebuilder.blocks.french;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.DoTBUtils;
import org.dawnoftimebuilder.blocks.general.DoTBBlock;
import org.dawnoftimebuilder.enums.EnumsBlock;

import java.util.Random;

public class BlockLimestoneFireplace extends DoTBBlock {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	private static final PropertyEnum<EnumsBlock.EnumVerticalConnection> VERTICAL_CONNECTION = PropertyEnum.create("vertical_connection", EnumsBlock.EnumVerticalConnection.class);
	private static final PropertyEnum<EnumsBlock.EnumHorizontalConnection> HORIZONTAL_CONNECTION = PropertyEnum.create("horizontal_connection", EnumsBlock.EnumHorizontalConnection.class);
	private static final PropertyBool BURNING = PropertyBool.create("burning");
	private static final AxisAlignedBB SMALL_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
	private static final AxisAlignedBB BIG_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);

	public BlockLimestoneFireplace() {
		super("limestone_fireplace", Material.ROCK);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(BURNING,false).withProperty(VERTICAL_CONNECTION, EnumsBlock.EnumVerticalConnection.NONE).withProperty(HORIZONTAL_CONNECTION, EnumsBlock.EnumHorizontalConnection.NONE));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, VERTICAL_CONNECTION, HORIZONTAL_CONNECTION, BURNING);
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return DoTBUtils.getRotatedAABB((this.getActualState(state, source, pos).getValue(VERTICAL_CONNECTION) == EnumsBlock.EnumVerticalConnection.NONE) ? SMALL_AABB : BIG_AABB, state.getValue(FACING));
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		IBlockState other = world.getBlockState(pos);
		if (other.getBlock() != this) return other.getLightValue(world, pos);
		return (state.getValue(BURNING)) ? 15 : 0;
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies properties not visible in the
	 * metadata, such as fence connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		EnumFacing facing = state.getValue(FACING);
		return state.withProperty(VERTICAL_CONNECTION, this.getVerticalShape(worldIn, pos, facing)).withProperty(HORIZONTAL_CONNECTION, this.getHorizontalShape(worldIn, pos, facing));
	}

	private EnumsBlock.EnumVerticalConnection getVerticalShape(IBlockAccess worldIn, BlockPos pos, EnumFacing facing){

		IBlockState up = worldIn.getBlockState(pos.up());
		IBlockState down = worldIn.getBlockState(pos.down());

		boolean blockUp = up.getBlock() instanceof BlockLimestoneFireplace;
		if(blockUp) blockUp = up.getValue(FACING) == facing;
		boolean blockDown = down.getBlock() instanceof BlockLimestoneFireplace;
		if(blockDown) blockDown = down.getValue(FACING) == facing;

		if(blockUp){
			return (blockDown) ? EnumsBlock.EnumVerticalConnection.BOTH : EnumsBlock.EnumVerticalConnection.ABOVE;
		}else{
			return (blockDown) ? EnumsBlock.EnumVerticalConnection.UNDER : EnumsBlock.EnumVerticalConnection.NONE;
		}
	}

	private EnumsBlock.EnumHorizontalConnection getHorizontalShape(IBlockAccess worldIn, BlockPos pos, EnumFacing facing){

		IBlockState left = worldIn.getBlockState(pos.offset(facing.rotateYCCW()));
		IBlockState right = worldIn.getBlockState(pos.offset(facing.rotateY()));

		boolean blockLeft = left.getBlock() instanceof BlockLimestoneFireplace;
		if(blockLeft) blockLeft = left.getValue(FACING) == facing;
		boolean blockRight = right.getBlock() instanceof BlockLimestoneFireplace;
		if(blockRight) blockRight = right.getValue(FACING) == facing;

		if(blockLeft){
			return (blockRight) ? EnumsBlock.EnumHorizontalConnection.BOTH : EnumsBlock.EnumHorizontalConnection.LEFT;
		}else{
			return (blockRight) ? EnumsBlock.EnumHorizontalConnection.RIGHT : EnumsBlock.EnumHorizontalConnection.NONE;
		}
	}

	/**
	 * Called when the blocks is right clicked by a player.
	 */
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack itemstack = playerIn.getHeldItem(hand);
		state = this.getActualState(state, worldIn, pos);
		if(state.getValue(VERTICAL_CONNECTION) != EnumsBlock.EnumVerticalConnection.BOTH && state.getValue(VERTICAL_CONNECTION) != EnumsBlock.EnumVerticalConnection.UNDER) {
			if (state.getValue(BURNING)) {
				facing = state.getValue(FACING);
				worldIn.setBlockState(pos, state.withProperty(BURNING, false), 10);
				worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
				worldIn.getBlockState(pos.offset(facing.rotateYCCW())).neighborChanged(worldIn, pos.offset(facing.rotateYCCW()), this, pos);
				worldIn.getBlockState(pos.offset(facing.rotateY())).neighborChanged(worldIn, pos.offset(facing.rotateY()), this, pos);
				return true;
			} else if (!itemstack.isEmpty() && (itemstack.getItem() == Items.FLINT_AND_STEEL || itemstack.getItem() == Item.getItemFromBlock(Blocks.TORCH))) {
				facing = state.getValue(FACING);
				worldIn.setBlockState(pos, state.withProperty(BURNING, true), 10);
				worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
				worldIn.getBlockState(pos.offset(facing.rotateYCCW())).neighborChanged(worldIn, pos.offset(facing.rotateYCCW()), this, pos);
				worldIn.getBlockState(pos.offset(facing.rotateY())).neighborChanged(worldIn, pos.offset(facing.rotateY()), this, pos);
				if (itemstack.getItem() == Items.FLINT_AND_STEEL) itemstack.damageItem(1, playerIn);
				else if (!playerIn.capabilities.isCreativeMode) itemstack.shrink(1);
				return true;
			}
		}
		return false;
	}

	/**
	 * Called when a neighboring blocks was changed and marks that this state should perform any checks during a neighbor
	 * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
	 * blocks, etc.
	 */
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		state = state.getActualState(worldIn, pos);
		if(state.getValue(VERTICAL_CONNECTION) != EnumsBlock.EnumVerticalConnection.BOTH && state.getValue(VERTICAL_CONNECTION) != EnumsBlock.EnumVerticalConnection.UNDER){
			IBlockState newState = worldIn.getBlockState(fromPos);
			if(newState.getBlock() instanceof BlockLimestoneFireplace){
				EnumFacing facing = state.getValue(FACING);
				if(newState.getValue(FACING) == facing){
					boolean burning = newState.getValue(BURNING);
					if(burning != state.getValue(BURNING)){
						worldIn.setBlockState(pos, state.withProperty(BURNING, burning), 10);
						BlockPos newPos = (pos.offset(facing.rotateY()).equals(fromPos)) ? pos.offset(facing.rotateYCCW()) : pos.offset(facing.rotateY());
						worldIn.getBlockState(newPos).neighborChanged(worldIn, newPos, this, pos);
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand){
		if(stateIn.getValue(BURNING)) {
			if (rand.nextInt(24) == 0) {
				worldIn.playSound((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
			}
			for (int i = 0; i < 3; ++i) {
				double d0 = (double) pos.getX() + rand.nextDouble() * 0.5D + 0.25D;
				double d1 = (double) pos.getY() + rand.nextDouble() * 0.5D + 0.6D;
				double d2 = (double) pos.getZ() + rand.nextDouble() * 0.5D + 0.25D;

				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState();
		if(meta > 3){
			state = state.withProperty(BURNING, true);
			meta -= 4;
		}else state = state.withProperty(BURNING, false);
		return state.withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex() + ((state.getValue(BURNING)) ? 4 : 0);
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
		return false;
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}
}