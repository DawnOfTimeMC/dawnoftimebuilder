package org.dawnoftimebuilder.block.general;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
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
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties.HorizontalConnection;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import java.util.Random;

public class FireplaceBlock extends WaterloggedBlock {

	public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
	public static final BooleanProperty BURNING = DoTBBlockStateProperties.BURNING;
	public static final EnumProperty<DoTBBlockStateProperties.HorizontalConnection> HORIZONTAL_CONNECTION = DoTBBlockStateProperties.HORIZONTAL_CONNECTION;

	private static final VoxelShape ON_X_SHAPE = net.minecraft.block.Block.box(0.0D, 0.0D, 2.0D, 16.0D, 14.0D, 14.0D);
	private static final VoxelShape OFF_X_SHAPE = net.minecraft.block.Block.box(0.0D, 0.0D, 2.0D, 16.0D, 5.0D, 14.0D);
	private static final VoxelShape ON_Z_SHAPE = net.minecraft.block.Block.box(2.0D, 0.0D, 0.0D, 14.0D, 14.0D, 16.0D);
	private static final VoxelShape OFF_Z_SHAPE = net.minecraft.block.Block.box(2.0D, 0.0D, 0.0D, 14.0D, 5.0D, 16.0D);

	public FireplaceBlock(Material materialIn, float hardness, float resistance, SoundType soundType) {
		super(materialIn, hardness, resistance, soundType);
		this.registerDefaultState(this.defaultBlockState().setValue(BURNING, false).setValue(HORIZONTAL_AXIS, Direction.Axis.X).setValue(HORIZONTAL_CONNECTION, HorizontalConnection.NONE));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HORIZONTAL_AXIS, BURNING, HORIZONTAL_CONNECTION);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return (state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X) ? OFF_X_SHAPE : OFF_Z_SHAPE;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
		if(state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X){
			return (state.getValue(BURNING)) ? ON_X_SHAPE : OFF_X_SHAPE;
		}else{
			return (state.getValue(BURNING)) ? ON_Z_SHAPE : OFF_Z_SHAPE;
		}
	}

	@Override
	public int getLightValue(BlockState state) {
		return (state.getValue(BURNING)) ? 15 : 0;
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return hasEnoughSolidSide(worldIn, pos.below(), Direction.UP);
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
		Direction direction;
		if (state.getValue(BURNING)) {
			direction = (state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH;
			worldIn.setBlock(pos, state.setValue(BURNING, false), 10);
			worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
			worldIn.getBlockState(pos.relative(direction)).neighborChanged(worldIn, pos.relative(direction), this, pos, false);
			worldIn.getBlockState(pos.relative(direction.getOpposite())).neighborChanged(worldIn, pos.relative(direction.getOpposite()), this, pos, false);
			return true;

		} else {
			if(state.getValue(WATERLOGGED)) return false;

			if(DoTBBlockUtils.useLighter(worldIn, pos, player, handIn)){
				direction = (state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH;
				worldIn.setBlock(pos, state.setValue(BURNING, true), 10);
				worldIn.getBlockState(pos.relative(direction)).neighborChanged(worldIn, pos.relative(direction), this, pos, false);
				worldIn.getBlockState(pos.relative(direction.getOpposite())).neighborChanged(worldIn, pos.relative(direction.getOpposite()), this, pos, false);
				return true;
			}
		}
		return false;
	}

	@Override
	public void onProjectileCollision(World worldIn, BlockState state, BlockRayTraceResult hit, Entity projectile) {
		if (!worldIn.isClientSide && projectile instanceof AbstractArrowEntity) {
			AbstractArrowEntity abstractarrowentity = (AbstractArrowEntity)projectile;
			if (abstractarrowentity.isBurning() && !state.getValue(BURNING) && !state.getValue(WATERLOGGED)) {
				BlockPos pos = hit.getPos();
				worldIn.setBlock(pos, state.setValue(BURNING, true), 10);
				worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
				Direction direction = (state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH;
				worldIn.getBlockState(pos.relative(direction)).neighborChanged(worldIn, pos.relative(direction), this, pos, false);
				worldIn.getBlockState(pos.relative(direction.getOpposite())).neighborChanged(worldIn, pos.relative(direction.getOpposite()), this, pos, false);
			}
		}
	}

	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (!entityIn.isImmuneToFire() && state.getValue(BURNING) && entityIn instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entityIn)) {
			entityIn.attackEntityFrom(DamageSource.IN_FIRE, 1.0F);
		}
		super.onEntityCollision(state, worldIn, pos, entityIn);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState state = super.getStateForPlacement(context);
		Direction.Axis axis = (context.getHorizontalDirection().getAxis() == Direction.Axis.X)? Direction.Axis.Z : Direction.Axis.X;
		return state.setValue(HORIZONTAL_AXIS, axis).setValue(HORIZONTAL_CONNECTION, getHorizontalShape(context.getLevel(), context.getClickedPos(), axis));
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, net.minecraft.block.Block blockIn, BlockPos fromPos, boolean isMoving) {
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
		if(pos.getY() == fromPos.getY()){
			Direction.Axis axis = state.getValue(HORIZONTAL_AXIS);
			if(axis == Direction.Axis.X){
				if(pos.getX() == fromPos.getX()) return;
			}else{
				if(pos.getZ() == fromPos.getZ()) return;
			}

			state = state.setValue(HORIZONTAL_CONNECTION, getHorizontalShape(worldIn, pos, axis));

			BlockState newState = worldIn.getBlockState(fromPos);
			if(newState.getBlock() instanceof FireplaceBlock){
				if(newState.getValue(HORIZONTAL_AXIS) == axis){
					if(newState.getValue(BURNING) != state.get(BURNING)){
						if(newState.get(BURNING) && state.get(WATERLOGGED)) return;
						worldIn.setBlock(pos, state.setValue(BURNING, newState.get(BURNING)), 10);
						BlockPos newPos = (axis == Direction.Axis.X) ? pos.relative(Direction.EAST, pos.getX() - fromPos.getX()) : pos.relative(Direction.SOUTH, pos.getZ() - fromPos.getZ());
						worldIn.getBlockState(newPos).neighborChanged(worldIn, newPos, this, pos, false);
						return;
					}
				}
			}
			worldIn.setBlock(pos, state, 10);
		}
	}

	private DoTBBlockStateProperties.HorizontalConnection getHorizontalShape(World worldIn, BlockPos pos, Direction.Axis axis){

		BlockState left = worldIn.getBlockState(pos.relative((axis == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH, 1));
		BlockState right = worldIn.getBlockState(pos.relative((axis == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH, -1));

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
			worldIn.setBlock(pos, state.setValue(WATERLOGGED, true).setValue(BURNING, false), 10);
			worldIn.getLiquidTicks().scheduleTick(pos, fluidStateIn.getFluid(), fluidStateIn.getFluid().getTickRate(worldIn));
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
			worldIn.addOptionalParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, (double)pos.getX() + 0.5D + rand.nextDouble() / 3.0D * (double)(rand.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.4D, (double)pos.getZ() + 0.5D + rand.nextDouble() / 3.0D * (double)(rand.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
		}
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		Direction.Axis axis = state.get(HORIZONTAL_AXIS);
		return (rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90) ? state.setValue(HORIZONTAL_AXIS, (axis == Direction.Axis.X) ? Direction.Axis.Z : Direction.Axis.X) : state;
	}
}