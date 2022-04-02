package org.dawnoftimebuilder.block.french;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
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
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class LimestoneGargoyleBlock extends WaterloggedBlock {

	private static final IntegerProperty HUMIDITY = DoTBBlockStateProperties.HUMIDITY_0_8;
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
	private static final VoxelShape[] SHAPES = DoTBBlockUtils.GenerateHorizontalShapes(new VoxelShape[]{
			Block.box(4.0D, 7.0D, 0.0D, 12.0D, 14.0D, 16.0D)
	});

	public LimestoneGargoyleBlock(Properties properties) {
		super(properties);
	    this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(HUMIDITY, 0).setValue(PERSISTENT, false));
	}

	private int getMaxHumidity(){
		return 8;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, HUMIDITY, PERSISTENT);
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
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(state.getValue(PERSISTENT)){
			if(player.isCreative()){
				int humidity = state.getValue(HUMIDITY);
				if(player.isCrouching()){
					if(humidity > 0){
						worldIn.setBlock(pos, state.setValue(HUMIDITY, humidity - 1), 10);
						return ActionResultType.SUCCESS;
					}
				}else{
					if(humidity < this.getMaxHumidity()) {
						worldIn.setBlock(pos, state.setValue(HUMIDITY, humidity + 1), 10);
						return ActionResultType.SUCCESS;
					}
				}
			}else{
				if(DoTBBlockUtils.useLighter(worldIn, pos, player, handIn)){
					Random rand = new Random();
					for(int i = 0; i < 5; i++){
						worldIn.addAlwaysVisibleParticle(ParticleTypes.SMOKE, (double)pos.getX() + rand.nextDouble(), (double)pos.getY() + 0.5D + rand.nextDouble() / 2, (double)pos.getZ() + rand.nextDouble(), 0.0D, 0.07D, 0.0D);
					}
					worldIn.setBlock(pos, state.setValue(PERSISTENT, true), 10);
					return ActionResultType.SUCCESS;
				}
			}
		}
		return super.use(state, worldIn, pos, player, handIn, hit);
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

		if(state.getValue(PERSISTENT)){
			super.randomTick(state, world, pos, rand);
			return;
		}

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

	@Override
	public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		DoTBBlockUtils.addTooltip(tooltip, this);
	}
}
