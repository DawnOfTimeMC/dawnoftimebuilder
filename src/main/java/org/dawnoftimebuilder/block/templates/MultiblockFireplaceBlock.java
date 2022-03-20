package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import java.util.Random;

public class MultiblockFireplaceBlock extends SidedPlaneConnectibleBlock {

	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	private static final VoxelShape[] SHAPES = DoTBBlockUtils.GenerateHorizontalShapes(makeShapes());

	public MultiblockFireplaceBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(LIT, false));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LIT);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		int index = (state.getValue(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.NONE) ? 0 : 1;
		return SHAPES[index + state.getValue(FACING).get2DDataValue() * 2];
	}

	/**
	 * @return Stores VoxelShape for "South" with index : <p/>
	 * 0 : S Small fireplace <p/>
	 * 1 : S Big fireplace <p/>
	 */
	private static VoxelShape[] makeShapes() {
		return new VoxelShape[]{
				Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D),
				Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 12.0D)
		};
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
		if(state.getValue(VERTICAL_CONNECTION) != DoTBBlockStateProperties.VerticalConnection.BOTH && state.getValue(VERTICAL_CONNECTION) != DoTBBlockStateProperties.VerticalConnection.UNDER) {
			if (state.getValue(LIT)) {
				Direction direction = state.getValue(FACING);
				worldIn.setBlock(pos, state.setValue(LIT, false), 10);
				worldIn.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
				worldIn.getBlockState(pos.relative(direction.getCounterClockWise())).neighborChanged(worldIn, pos.relative(direction.getCounterClockWise()), this, pos, false);
				worldIn.getBlockState(pos.relative(direction.getClockWise())).neighborChanged(worldIn, pos.relative(direction.getClockWise()), this, pos, false);
				return ActionResultType.SUCCESS;
			} else {
				if (state.getValue(WATERLOGGED)) return ActionResultType.PASS;
				if (DoTBBlockUtils.useLighter(worldIn, pos, player, handIn)) {
					Direction direction = state.getValue(FACING);
					worldIn.setBlock(pos, state.setValue(LIT, true), 10);
					worldIn.getBlockState(pos.relative(direction.getCounterClockWise())).neighborChanged(worldIn, pos.relative(direction.getCounterClockWise()), this, pos, false);
					worldIn.getBlockState(pos.relative(direction.getClockWise())).neighborChanged(worldIn, pos.relative(direction.getClockWise()), this, pos, false);
					return ActionResultType.SUCCESS;
				}
			}
		}
		return ActionResultType.PASS;
	}

	@Override
	public void onProjectileHit(World worldIn, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
		if (!worldIn.isClientSide && projectile instanceof AbstractArrowEntity) {
			AbstractArrowEntity abstractarrowentity = (AbstractArrowEntity)projectile;
			if(state.getValue(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.BOTH || state.getValue(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.UNDER)
				return;
			if (abstractarrowentity.isOnFire() && !state.getValue(LIT) && !state.getValue(WATERLOGGED)) {
				BlockPos pos = hit.getBlockPos();
				worldIn.setBlock(pos, state.setValue(LIT, true), 10);
				worldIn.playSound(null, pos, SoundEvents.FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
				Direction direction = state.getValue(FACING);
				worldIn.getBlockState(pos.relative(direction.getClockWise())).neighborChanged(worldIn, pos.relative(direction.getClockWise()), this, pos, false);
				worldIn.getBlockState(pos.relative(direction.getCounterClockWise())).neighborChanged(worldIn, pos.relative(direction.getCounterClockWise()), this, pos, false);
			}
		}
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if(state.getValue(VERTICAL_CONNECTION) != DoTBBlockStateProperties.VerticalConnection.BOTH && state.getValue(VERTICAL_CONNECTION) != DoTBBlockStateProperties.VerticalConnection.UNDER){
			BlockState newState = worldIn.getBlockState(fromPos);
			if(newState.getBlock() == this){
				Direction facing = state.getValue(FACING);
				if(newState.getValue(FACING) == facing){
					boolean burning = newState.getValue(LIT);
					if(burning != state.getValue(LIT)){
						if(newState.getValue(LIT) && state.getValue(WATERLOGGED)) return;
						worldIn.setBlock(pos, state.setValue(LIT, burning), 10);
						BlockPos newPos = (pos.relative(facing.getClockWise()).equals(fromPos)) ? pos.relative(facing.getCounterClockWise()) : pos.relative(facing.getClockWise());
						worldIn.getBlockState(newPos).neighborChanged(worldIn, newPos, this, pos, false);
					}
				}
			}
		}
	}

	@Override
	public boolean placeLiquid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
		if (!state.getValue(BlockStateProperties.WATERLOGGED) && fluidStateIn.getType() == Fluids.WATER) {
			if (state.getValue(LIT)) {
				worldIn.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
			worldIn.setBlock(pos, state.setValue(WATERLOGGED, true).setValue(LIT, false), 10);
			worldIn.getLiquidTicks().scheduleTick(pos, fluidStateIn.getType(), fluidStateIn.getType().getTickDelay(worldIn));
			return true;
		} else {
			return false;
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if(stateIn.getValue(VERTICAL_CONNECTION) != DoTBBlockStateProperties.VerticalConnection.BOTH && stateIn.getValue(VERTICAL_CONNECTION) != DoTBBlockStateProperties.VerticalConnection.UNDER) {
			if (stateIn.getValue(LIT)) {
				if (rand.nextInt(24) == 0) {
					worldIn.playLocalSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
				}
				for (int i = 0; i < 3; ++i) {
					worldIn.addParticle(ParticleTypes.SMOKE, (float) pos.getX() + rand.nextDouble() * 0.5F + 0.25F, (float) pos.getY() + rand.nextDouble() * 0.5F + 0.6F, (float) pos.getZ() + rand.nextDouble() * 0.5F + 0.25F, 0.0F, 0.0F, 0.0F);
				}
			}
		}
	}
}