package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.enums.DoTBBlockStateProperties;
import org.dawnoftimebuilder.enums.DoTBBlockStateProperties.HorizontalConnection;
import java.util.Random;

public class FireplaceBlock extends DoTBBlock implements IWaterLoggable {

	public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
	public static final BooleanProperty BURNING = DoTBBlockStateProperties.BURNING;
	public static final EnumProperty<DoTBBlockStateProperties.HorizontalConnection> HORIZONTAL_CONNECTION = DoTBBlockStateProperties.HORIZONTAL_CONNECTION;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	private static final VoxelShape ON_X_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 2.0D, 16.0D, 14.0D, 14.0D);
	private static final VoxelShape OFF_X_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 2.0D, 16.0D, 5.0D, 14.0D);
	private static final VoxelShape ON_Z_SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 0.0D, 14.0D, 14.0D, 16.0D);
	private static final VoxelShape OFF_Z_SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 0.0D, 14.0D, 5.0D, 16.0D);

	public FireplaceBlock() {
		super("fireplace", Material.ROCK,1.5F, 6.0F);
		this.setDefaultState(this.stateContainer.getBaseState().with(BURNING, false).with(HORIZONTAL_AXIS, Direction.Axis.X).with(HORIZONTAL_CONNECTION, HorizontalConnection.NONE).with(WATERLOGGED,false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_AXIS, BURNING, HORIZONTAL_CONNECTION, WATERLOGGED);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return (state.get(HORIZONTAL_AXIS) == Direction.Axis.X) ? OFF_X_SHAPE : OFF_Z_SHAPE;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
		if(state.get(HORIZONTAL_AXIS) == Direction.Axis.X){
			return (state.get(BURNING)) ? ON_X_SHAPE : OFF_X_SHAPE;
		}else{
			return (state.get(BURNING)) ? ON_Z_SHAPE : OFF_Z_SHAPE;
		}
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public IFluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return func_220055_a(worldIn, pos.down(), Direction.UP);
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
		ItemStack itemstack = player.getHeldItem(handIn);
		Direction direction;

		if (state.get(BURNING)) {

			direction = (state.get(HORIZONTAL_AXIS) == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH;
			worldIn.setBlockState(pos, state.with(BURNING, false), 10);
			worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
			worldIn.getBlockState(pos.offset(direction)).neighborChanged(worldIn, pos.offset(direction), this, pos, false);
			worldIn.getBlockState(pos.offset(direction.getOpposite())).neighborChanged(worldIn, pos.offset(direction.getOpposite()), this, pos, false);
			return true;

		} else {
			if(state.get(WATERLOGGED)) return false;

			if (!itemstack.isEmpty() && (itemstack.getItem() == Items.FLINT_AND_STEEL || itemstack.getItem() == Item.getItemFromBlock(Blocks.TORCH))) {
				direction = (state.get(HORIZONTAL_AXIS) == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH;
				worldIn.setBlockState(pos, state.with(BURNING, true), 10);
				worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
				worldIn.getBlockState(pos.offset(direction)).neighborChanged(worldIn, pos.offset(direction), this, pos, false);
				worldIn.getBlockState(pos.offset(direction.getOpposite())).neighborChanged(worldIn, pos.offset(direction.getOpposite()), this, pos, false);

				if (itemstack.getItem() == Items.FLINT_AND_STEEL) itemstack.damageItem(1, player, (p_220287_1_) -> p_220287_1_.sendBreakAnimation(handIn));
				else if (!player.abilities.isCreativeMode) itemstack.shrink(1);
				return true;

			}
		}
		return false;
	}

	@Override
	public void onProjectileCollision(World worldIn, BlockState state, BlockRayTraceResult hit, Entity projectile) {
		if (!worldIn.isRemote && projectile instanceof AbstractArrowEntity) {
			AbstractArrowEntity abstractarrowentity = (AbstractArrowEntity)projectile;
			if (abstractarrowentity.isBurning() && !state.get(BURNING) && !state.get(WATERLOGGED)) {
				BlockPos pos = hit.getPos();
				worldIn.setBlockState(pos, state.with(BlockStateProperties.LIT, true), 10);
				worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
				Direction direction = (state.get(HORIZONTAL_AXIS) == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH;
				worldIn.getBlockState(pos.offset(direction)).neighborChanged(worldIn, pos.offset(direction), this, pos, false);
				worldIn.getBlockState(pos.offset(direction.getOpposite())).neighborChanged(worldIn, pos.offset(direction.getOpposite()), this, pos, false);
			}
		}
	}

	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (!entityIn.isImmuneToFire() && state.get(BURNING) && entityIn instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entityIn)) {
			entityIn.attackEntityFrom(DamageSource.IN_FIRE, 1.0F);
		}
		super.onEntityCollision(state, worldIn, pos, entityIn);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos pos = context.getPos();
		IFluidState ifluidstate = context.getWorld().getFluidState(pos);
		Direction.Axis axis = (context.getPlacementHorizontalFacing().getAxis() == Direction.Axis.X)? Direction.Axis.Z : Direction.Axis.X;
		return this.getDefaultState().with(HORIZONTAL_AXIS, axis).with(HORIZONTAL_CONNECTION, getHorizontalShape(context.getWorld(), pos, axis)).with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if(pos.getY() == fromPos.getY()){
			Direction.Axis axis = state.get(HORIZONTAL_AXIS);
			if(axis == Direction.Axis.X){
				if(pos.getX() == fromPos.getX()) return;
			}else{
				if(pos.getZ() == fromPos.getZ()) return;
			}

			state = state.with(HORIZONTAL_CONNECTION, getHorizontalShape(worldIn, pos, axis));

			BlockState newState = worldIn.getBlockState(fromPos);
			if(newState.getBlock() instanceof FireplaceBlock){
				if(newState.get(HORIZONTAL_AXIS) == axis){
					if(newState.get(BURNING) != state.get(BURNING)){
						if(newState.get(BURNING) && state.get(WATERLOGGED)) return;
						worldIn.setBlockState(pos, state.with(BURNING, newState.get(BURNING)), 10);
						BlockPos newPos = (axis == Direction.Axis.X) ? pos.offset(Direction.EAST, pos.getX() - fromPos.getX()) : pos.offset(Direction.SOUTH, pos.getZ() - fromPos.getZ());
						worldIn.getBlockState(newPos).neighborChanged(worldIn, newPos, this, pos, false);
						return;
					}
				}
			}
			worldIn.setBlockState(pos, state, 10);
		}
	}

	private DoTBBlockStateProperties.HorizontalConnection getHorizontalShape(World worldIn, BlockPos pos, Direction.Axis axis){

		BlockState left = worldIn.getBlockState(pos.offset((axis == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH, 1));
		BlockState right = worldIn.getBlockState(pos.offset((axis == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH, -1));

		boolean blockLeft = left.getBlock() instanceof FireplaceBlock;
		if(blockLeft) blockLeft = left.get(HORIZONTAL_AXIS) == axis;

		boolean blockRight = right.getBlock() instanceof FireplaceBlock;
		if(blockRight) blockRight = right.get(HORIZONTAL_AXIS) == axis;

		if(blockLeft){
			return (blockRight) ? DoTBBlockStateProperties.HorizontalConnection.BOTH : DoTBBlockStateProperties.HorizontalConnection.LEFT;
		}else{
			return (blockRight) ? DoTBBlockStateProperties.HorizontalConnection.RIGHT : DoTBBlockStateProperties.HorizontalConnection.NONE;
		}
	}

	@Override
	public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, IFluidState fluidStateIn) {
		if (!state.get(BlockStateProperties.WATERLOGGED) && fluidStateIn.getFluid() == Fluids.WATER) {
			if (state.get(BURNING)) {
				worldIn.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
			worldIn.setBlockState(pos, state.with(WATERLOGGED, true).with(BURNING, false), 10);
			worldIn.getPendingFluidTicks().scheduleTick(pos, fluidStateIn.getFluid(), fluidStateIn.getFluid().getTickRate(worldIn));
			return true;
		} else {
			return false;
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (stateIn.get(BURNING)) {
			if (rand.nextInt(10) == 0) {
				worldIn.playSound((float)pos.getX() + 0.5F, (float)pos.getY() + 0.5F, (float)pos.getZ() + 0.5F, SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
			}

			if (rand.nextInt(10) == 0) {
				for(int i = 0; i < rand.nextInt(1) + 1; ++i) {
					worldIn.addParticle(ParticleTypes.LAVA, (float)pos.getX() + 0.5F, (float)pos.getY() + 0.5F, (float)pos.getZ() + 0.5F, rand.nextFloat() / 4.0F, 2.5E-5D, rand.nextFloat() / 4.0F);
				}
			}
			worldIn.func_217404_b(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, (double)pos.getX() + 0.5D + rand.nextDouble() / 3.0D * (double)(rand.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.4D, (double)pos.getZ() + 0.5D + rand.nextDouble() / 3.0D * (double)(rand.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
		}
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		Direction.Axis axis = state.get(HORIZONTAL_AXIS);
		return (rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90) ? state.with(HORIZONTAL_AXIS, (axis == Direction.Axis.X) ? Direction.Axis.Z : Direction.Axis.X) : state;
	}
}