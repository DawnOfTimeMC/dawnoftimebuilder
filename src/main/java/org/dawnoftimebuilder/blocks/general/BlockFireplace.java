package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
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
import org.dawnoftimebuilder.enums.EnumsBlock;

import java.util.Random;

public class BlockFireplace extends DoTBBlock {

	private static final PropertyBool AXIS_X = PropertyBool.create("axis_x");
	private static final PropertyEnum<EnumsBlock.EnumHorizontalConnection> HORIZONTAL_CONNECTION = PropertyEnum.create("horizontal_connection", EnumsBlock.EnumHorizontalConnection.class);
	private static final PropertyBool BURNING = PropertyBool.create("burning");

	private static final AxisAlignedBB ON_X_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.125F, 1.0F, 0.875F, 0.875F);
	private static final AxisAlignedBB OFF_X_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.125F, 1.0F, 0.3125F, 0.875F);
	private static final AxisAlignedBB ON_Z_AABB = new AxisAlignedBB(0.125F, 0.0F, 0.0F, 0.875F, 0.875F, 1.0F);
	private static final AxisAlignedBB OFF_Z_AABB = new AxisAlignedBB(0.125F, 0.0F, 0.0F, 0.875F, 0.3125F, 1.0F);

	public BlockFireplace() {
		super("fireplace", Material.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS_X, true).withProperty(BURNING, false).withProperty(HORIZONTAL_CONNECTION, EnumsBlock.EnumHorizontalConnection.NONE));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, AXIS_X, BURNING, HORIZONTAL_CONNECTION);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if(this.getActualState(state, source, pos).getValue(AXIS_X)){
			return (this.getActualState(state, source, pos).getValue(BURNING)) ? ON_X_AABB : OFF_X_AABB;
		}else{
			return (this.getActualState(state, source, pos).getValue(BURNING)) ? ON_Z_AABB : OFF_Z_AABB;
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return (this.getActualState(state, source, pos).getValue(AXIS_X)) ? OFF_X_AABB : OFF_Z_AABB;
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		IBlockState other = world.getBlockState(pos);
		if (other.getBlock() != this) return other.getLightValue(world, pos);
		return (state.getValue(BURNING)) ? 15 : 0;
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(AXIS_X, placer.getHorizontalFacing().getAxis() == EnumFacing.Axis.Z).withProperty(HORIZONTAL_CONNECTION, getHorizontalShape(worldIn, pos, placer.getHorizontalFacing().getAxis() == EnumFacing.Axis.Z));
	}

	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)  {
		return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos);
	}

	private boolean canBlockStay(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos.down());
		return state.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID;
	}

	private EnumsBlock.EnumHorizontalConnection getHorizontalShape(IBlockAccess worldIn, BlockPos pos, boolean axisX){

		IBlockState left = worldIn.getBlockState(pos.offset(axisX ? EnumFacing.EAST : EnumFacing.SOUTH, 1));
		IBlockState right = worldIn.getBlockState(pos.offset(axisX ? EnumFacing.EAST : EnumFacing.SOUTH, -1));

		boolean blockLeft = left.getBlock() instanceof BlockFireplace;
		if(blockLeft) blockLeft = left.getValue(AXIS_X) == axisX;
		boolean blockRight = right.getBlock() instanceof BlockFireplace;
		if(blockRight) blockRight = right.getValue(AXIS_X) == axisX;

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

		if (state.getValue(BURNING)) {

			facing = state.getValue(AXIS_X) ? EnumFacing.EAST : EnumFacing.SOUTH;
			worldIn.setBlockState(pos, state.withProperty(BURNING, false), 10);
			worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
			worldIn.getBlockState(pos.offset(facing)).neighborChanged(worldIn, pos.offset(facing), this, pos);
			worldIn.getBlockState(pos.offset(facing.getOpposite())).neighborChanged(worldIn, pos.offset(facing.getOpposite()), this, pos);
			return true;

		} else if (!itemstack.isEmpty() && (itemstack.getItem() == Items.FLINT_AND_STEEL || itemstack.getItem() == Item.getItemFromBlock(Blocks.TORCH))) {

			facing = state.getValue(AXIS_X) ? EnumFacing.EAST : EnumFacing.SOUTH;
			worldIn.setBlockState(pos, state.withProperty(BURNING, true), 10);
			worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
			worldIn.getBlockState(pos.offset(facing)).neighborChanged(worldIn, pos.offset(facing), this, pos);
			worldIn.getBlockState(pos.offset(facing.getOpposite())).neighborChanged(worldIn, pos.offset(facing.getOpposite()), this, pos);

			if (itemstack.getItem() == Items.FLINT_AND_STEEL) itemstack.damageItem(1, playerIn);
			else if (!playerIn.capabilities.isCreativeMode) itemstack.shrink(1);
			return true;
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
		if(!canBlockStay(worldIn, pos)){
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}else if(pos.getY() == fromPos.getY()){
			boolean axisX = state.getValue(AXIS_X);

			if(axisX){
				if(pos.getX() == fromPos.getX()) return;
			}else{
				if(pos.getZ() == fromPos.getZ()) return;
			}

			state = state.withProperty(HORIZONTAL_CONNECTION, getHorizontalShape(worldIn, pos, axisX));

			IBlockState newState = worldIn.getBlockState(fromPos);
			if(newState.getBlock() instanceof BlockFireplace){
				if(newState.getValue(AXIS_X) == axisX){
					if(newState.getValue(BURNING) != state.getValue(BURNING)){
						worldIn.setBlockState(pos, state.withProperty(BURNING, newState.getValue(BURNING)), 10);
						BlockPos newPos = axisX ? pos.offset(EnumFacing.EAST, pos.getX() - fromPos.getX()) : pos.offset(EnumFacing.SOUTH, pos.getZ() - fromPos.getZ());
						worldIn.getBlockState(newPos).neighborChanged(worldIn, newPos, this, pos);
						return;
					}
				}
			}
			worldIn.setBlockState(pos, state, 10);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
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
		if(meta > 7){
			state = state.withProperty(BURNING, true);
			meta -= 8;
		}
		if(meta > 3){
			state = state.withProperty(AXIS_X, false);
			meta -= 4;
		}
		switch(meta){
			case 3 :
				return state.withProperty(HORIZONTAL_CONNECTION, EnumsBlock.EnumHorizontalConnection.BOTH);
			case 2 :
				return state.withProperty(HORIZONTAL_CONNECTION, EnumsBlock.EnumHorizontalConnection.LEFT);
			case 1 :
				return state.withProperty(HORIZONTAL_CONNECTION, EnumsBlock.EnumHorizontalConnection.RIGHT);
			default :
				return state;
		}
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		if(state.getValue(BURNING)) meta += 8;
		if(!state.getValue(AXIS_X)) meta += 4;
		switch(state.getValue(HORIZONTAL_CONNECTION)){
			case BOTH :
				return meta + 3;
			case LEFT :
				return meta + 2;
			case RIGHT:
				return meta + 1;
			default :
				return meta;
		}
	}

	/**
	 * Called When an Entity Collided with the Block
	 */
	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if(state.getValue(BURNING)){
			if (!entityIn.isImmuneToFire() && entityIn instanceof EntityLivingBase && !EnchantmentHelper.hasFrostWalkerEnchantment((EntityLivingBase)entityIn)) {
				entityIn.attackEntityFrom(DamageSource.IN_FIRE, 1.0F);
				entityIn.setFire(3);
			}
		}
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
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return (rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90) ? state.withProperty(AXIS_X, !state.getValue(AXIS_X)) : state;
	}
}