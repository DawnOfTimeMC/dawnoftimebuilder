package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
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
import org.dawnoftimebuilder.utils.DoTBBlockUtils;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;

import java.util.Random;

public class MultiblockFireplaceBlock extends SidedPlaneConnectibleBlock {

	public static final BooleanProperty BURNING = DoTBBlockStateProperties.BURNING;
	private static final VoxelShape[] SHAPES = DoTBBlockUtils.GenerateHorizontalShapes(makeShapes());

	public MultiblockFireplaceBlock(Material materialIn, float hardness, float resistance) {
		super(materialIn, hardness, resistance);
		this.setDefaultState(this.getStateContainer().getBaseState().with(BURNING, false).with(WATERLOGGED, false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(BURNING);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		int index = (state.get(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.NONE) ? 0 : 1;
		return SHAPES[index + state.get(FACING).getHorizontalIndex() * 2];
	}

	/**
	 * @return Stores VoxelShape for "South" with index : <p/>
	 * 0 : S Small fireplace <p/>
	 * 1 : S Big fireplace <p/>
	 */
	private static VoxelShape[] makeShapes() {
		return new VoxelShape[]{
				makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D),
				makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 12.0D)
		};
	}

	@Override
	public int getLightValue(BlockState state) {
		return (state.get(BURNING)) ? 15 : 0;
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
		if(state.get(VERTICAL_CONNECTION) != DoTBBlockStateProperties.VerticalConnection.BOTH && state.get(VERTICAL_CONNECTION) != DoTBBlockStateProperties.VerticalConnection.UNDER) {
			if (state.get(BURNING)) {
				Direction direction = state.get(FACING);
				worldIn.setBlockState(pos, state.with(BURNING, false), 10);
				worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
				worldIn.getBlockState(pos.offset(direction.rotateYCCW())).neighborChanged(worldIn, pos.offset(direction.rotateYCCW()), this, pos, false);
				worldIn.getBlockState(pos.offset(direction.rotateY())).neighborChanged(worldIn, pos.offset(direction.rotateY()), this, pos, false);
				return true;
			} else {
				if (state.get(WATERLOGGED)) return false;
				if (DoTBBlockUtils.lightFireBlock(worldIn, pos, player, handIn)) {
					Direction direction = state.get(FACING);
					worldIn.setBlockState(pos, state.with(BURNING, true), 10);
					worldIn.getBlockState(pos.offset(direction.rotateYCCW())).neighborChanged(worldIn, pos.offset(direction.rotateYCCW()), this, pos, false);
					worldIn.getBlockState(pos.offset(direction.rotateY())).neighborChanged(worldIn, pos.offset(direction.rotateY()), this, pos, false);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onProjectileCollision(World worldIn, BlockState state, BlockRayTraceResult hit, Entity projectile) {
		if (!worldIn.isRemote && projectile instanceof AbstractArrowEntity) {
			AbstractArrowEntity abstractarrowentity = (AbstractArrowEntity)projectile;
			if(state.get(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.BOTH || state.get(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.UNDER)
				return;
			if (abstractarrowentity.isBurning() && !state.get(BURNING) && !state.get(WATERLOGGED)) {
				BlockPos pos = hit.getPos();
				worldIn.setBlockState(pos, state.with(BURNING, true), 10);
				worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
				Direction direction = state.get(FACING);
				worldIn.getBlockState(pos.offset(direction.rotateY())).neighborChanged(worldIn, pos.offset(direction.rotateY()), this, pos, false);
				worldIn.getBlockState(pos.offset(direction.rotateYCCW())).neighborChanged(worldIn, pos.offset(direction.rotateYCCW()), this, pos, false);
			}
		}
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if(state.get(VERTICAL_CONNECTION) != DoTBBlockStateProperties.VerticalConnection.BOTH && state.get(VERTICAL_CONNECTION) != DoTBBlockStateProperties.VerticalConnection.UNDER){
			BlockState newState = worldIn.getBlockState(fromPos);
			if(newState.getBlock() == this){
				Direction facing = state.get(FACING);
				if(newState.get(FACING) == facing){
					boolean burning = newState.get(BURNING);
					if(burning != state.get(BURNING)){
						if(newState.get(BURNING) && state.get(WATERLOGGED)) return;
						worldIn.setBlockState(pos, state.with(BURNING, burning), 10);
						BlockPos newPos = (pos.offset(facing.rotateY()).equals(fromPos)) ? pos.offset(facing.rotateYCCW()) : pos.offset(facing.rotateY());
						worldIn.getBlockState(newPos).neighborChanged(worldIn, newPos, this, pos, false);
					}
				}
			}
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
		if(stateIn.get(VERTICAL_CONNECTION) != DoTBBlockStateProperties.VerticalConnection.BOTH && stateIn.get(VERTICAL_CONNECTION) != DoTBBlockStateProperties.VerticalConnection.UNDER) {
			if (stateIn.get(BURNING)) {
				if (rand.nextInt(24) == 0) {
					worldIn.playSound((float) pos.getX() + 0.5F, (float) pos.getY() + 0.5F, (float) pos.getZ() + 0.5F, SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
				}
				for (int i = 0; i < 3; ++i) {
					worldIn.addParticle(ParticleTypes.SMOKE, (float) pos.getX() + rand.nextDouble() * 0.5F + 0.25F, (float) pos.getY() + rand.nextDouble() * 0.5F + 0.6F, (float) pos.getZ() + rand.nextDouble() * 0.5F + 0.25F, 0.0F, 0.0F, 0.0F);
				}
			}
		}
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
}