package org.dawnoftimebuilder.block.french;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nonnull;
import java.util.Random;

public class LimestoneGargoyleBlock extends WaterloggedBlock {

	private static final IntegerProperty HUMIDITY = DoTBBlockStateProperties.HUMIDITY_0_8;
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	private static final VoxelShape[] SHAPES = DoTBBlockUtils.GenerateHorizontalShapes(new VoxelShape[]{
			Block.box(4.0D, 7.0D, 0.0D, 12.0D, 14.0D, 16.0D)
	});

	public LimestoneGargoyleBlock(Properties properties) {
		super(properties);
	    this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(HUMIDITY, 0));
	}

	private int getMaxHumidity(){
		return 8;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, HUMIDITY);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPES[state.getValue(FACING).get2DDataValue()];
	}

	@Nonnull
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot){
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return this.rotate(state, Rotation.CLOCKWISE_180);
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
		int humidity = state.getValue(HUMIDITY);
		if(humidity > 0){
			if(humidity == this.getMaxHumidity()){
				Direction facing = state.getValue(FACING);
				double x = pos.getX() + (8.0D + facing.getStepX() * 6.0D)/ 16.0D;
				double y = pos.getY() + 9.5D / 16.0D;
				double z = pos.getZ() + (8.0D + facing.getStepZ() * 6.0D) / 16.0D;
				world.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0.0D, 0.0D, 0.0D);
				world.addParticle(ParticleTypes.DRIPPING_WATER, x, y - 0.0625D, z, 0.0D, 0.0D, 0.0D);

			}else if(rand.nextInt(this.getMaxHumidity() - humidity + 1) == 0) {
				Direction facing = state.getValue(FACING);
				double x = pos.getX() + (8.0D + facing.getStepX() * 6.0D)/ 16.0D;
				double y = pos.getY() + 9.5D / 16.0D;
				double z = pos.getZ() + (8.0D + facing.getStepZ() * 6.0D) / 16.0D;
				world.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
		int humidity = state.getValue(HUMIDITY);

		if(world.getBiome(pos).getPrecipitation() == Biome.RainType.RAIN && world.isRaining() && world.canSeeSky(pos)){
			if(state.getValue(HUMIDITY) < this.getMaxHumidity()) {
				world.setBlock(pos, state.setValue(HUMIDITY, this.getMaxHumidity()), 2);
			}
		}else{
			if(rand.nextInt(5) == 0 && humidity > 0) {
				world.setBlock(pos, state.setValue(HUMIDITY, humidity - 1), 2);
			}
		}

		super.randomTick(state, world, pos, rand);
	}

	@Override
	public boolean placeLiquid(IWorld world, BlockPos pos, BlockState state, FluidState fluid) {
		if (!state.getValue(WATERLOGGED) && fluid.getType() == Fluids.WATER) {
			world.setBlock(pos, state.setValue(WATERLOGGED, true).setValue(HUMIDITY, 0), 2);
			world.getLiquidTicks().scheduleTick(pos, fluid.getType(), fluid.getType().getTickDelay(world));
			return true;
		} else {
			return false;
		}
	}
}
