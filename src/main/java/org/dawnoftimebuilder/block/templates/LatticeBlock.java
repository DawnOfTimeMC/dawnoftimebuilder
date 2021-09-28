package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.IBlockClimbingPlant;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static org.dawnoftimebuilder.util.DoTBBlockUtils.TOOLTIP_CLIMBING_PLANT;

public class LatticeBlock extends WaterloggedBlock implements IBlockClimbingPlant {

	public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
	public static final BooleanProperty EAST = BlockStateProperties.EAST;
	public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
	public static final BooleanProperty WEST = BlockStateProperties.WEST;
	public static final EnumProperty<DoTBBlockStateProperties.ClimbingPlant> CLIMBING_PLANT = DoTBBlockStateProperties.CLIMBING_PLANT;
	private static final IntegerProperty AGE = DoTBBlockStateProperties.AGE_0_6;
	public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
	private static final VoxelShape[] SHAPES = makeShapes();

	public LatticeBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(CLIMBING_PLANT, DoTBBlockStateProperties.ClimbingPlant.NONE).setValue(AGE, 0).setValue(WATERLOGGED, false).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(PERSISTENT, false));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(NORTH, EAST, SOUTH, WEST, CLIMBING_PLANT, AGE, PERSISTENT);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		int index = 0;
		if(state.get(SOUTH)) index += 1;
		if(state.get(WEST)) index += 2;
		if(state.get(NORTH)) index += 4;
		if(state.get(EAST)) index += 8;
		if(index > 14) index = 0;
		return SHAPES[index];
	}

	/**
	 * @return Stores VoxelShape with index following binary system : <p/>
	 * 8+4+2+1 with 1 = SOUTH, 2 = WEST, 4 = NORTH, 8 = EAST
	 * 0 : SWNE <p/>
	 * 1 : S <p/>
	 * 2 : W <p/>
	 * 3 : SW <p/>
	 * 4 : N <p/>
	 * 5 : SN <p/>
	 * 6 : WN <p/>
	 * 7 : SWN <p/>
	 * 8 : E <p/>
	 * 9 : SE <p/>
	 * 10 : WE <p/>
	 * 11 : SWE <p/>
	 * 12 : NE <p/>
	 * 13 : SNE <p/>
	 * 14 : WNE <p/>
	 */
	private static VoxelShape[] makeShapes() {
		VoxelShape vs_south = Block.box(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D);
		VoxelShape vs_west = Block.box(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 16.0D);
		VoxelShape vs_north = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 2.0D);
		VoxelShape vs_east = Block.box(14.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
		VoxelShape vs_sw = VoxelShapes.or(vs_south, vs_west);
		VoxelShape vs_wn = VoxelShapes.or(vs_west, vs_north);
		VoxelShape vs_ne = VoxelShapes.or(vs_north, vs_east);
		VoxelShape vs_se = VoxelShapes.or(vs_east, vs_south);
		return new VoxelShape[]{
				VoxelShapes.or(vs_sw, vs_ne),
				vs_south,
				vs_west,
				vs_sw,
				vs_north,
				VoxelShapes.or(vs_south, vs_north),
				vs_wn,
				VoxelShapes.or(vs_sw, vs_north),
				vs_east,
				vs_se,
				VoxelShapes.or(vs_west, vs_east),
				VoxelShapes.or(vs_sw, vs_east),
				vs_ne,
				VoxelShapes.or(vs_south, vs_ne),
				VoxelShapes.or(vs_wn, vs_east),
		};
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState state = context.getLevel().getBlockState(context.getClickedPos());
		if (state.getBlock() != this)
			state = super.getStateForPlacement(context);
		switch(context.getHorizontalDirection()){
			default:
			case SOUTH:
				return state.setValue(SOUTH, true);
			case WEST:
				return state.setValue(WEST, true);
			case NORTH:
				return state.setValue(NORTH, true);
			case EAST:
				return state.setValue(EAST, true);
		}
	}

	@Override
	public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
		ItemStack itemstack = useContext.getItem();
		if(useContext.isPlacerSneaking()) return false;
		if(itemstack.getItem() == this.asItem()) {
			Direction newDirection = useContext.getHorizontalDirection();
			switch(newDirection){
				default:
				case SOUTH:
					return !state.get(SOUTH);
				case WEST:
					return !state.get(WEST);
				case NORTH:
					return !state.get(NORTH);
				case EAST:
					return !state.get(EAST);
			}
		}
		return false;
	}

	@Override
	public boolean ticksRandomly(BlockState state) {
		return !state.get(CLIMBING_PLANT).hasNoPlant();
	}

	@Override
	public void spawnAdditionalDrops(BlockState state, World worldIn, BlockPos pos, ItemStack stack) {
		super.spawnAdditionalDrops(state, worldIn, pos, stack);
		//Be careful, climbing plants are not dropping from block's loot_table, but from their own loot_table
		this.dropPlant(state, worldIn, pos, stack);
	}

	@Override
	public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
		this.tickPlant(state, worldIn, pos, random);
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(!state.get(PERSISTENT)){
			if(DoTBBlockUtils.useLighter(worldIn, pos, player, handIn)){
				Random rand = new Random();
				for(int i = 0; i < 5; i++){
					worldIn.addOptionalParticle(ParticleTypes.SMOKE, (double)pos.getX() + rand.nextDouble(), (double)pos.getY() + 0.5D + rand.nextDouble() / 2, (double)pos.getZ() + rand.nextDouble(), 0.0D, 0.07D, 0.0D);
				}
				worldIn.setBlock(pos, state.setValue(PERSISTENT, true), 10);
				return true;
			}
		}
		if(player.isCreative()) {
			if(this.tryPlacingPlant(state, worldIn, pos, player, handIn)) return true;
		}else{
			if(worldIn.getBlockState(pos.below()).isIn(BlockTags.DIRT_LIKE)){
				if(this.tryPlacingPlant(state, worldIn, pos, player, handIn)) return true;
			}
		}
		return this.harvestPlant(state, worldIn, pos, player, handIn);
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity) {
		return true;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		DoTBBlockUtils.addTooltip(tooltip, TOOLTIP_CLIMBING_PLANT);
	}
}
