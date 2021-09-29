package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.dawnoftimebuilder.DoTBConfig;
import org.dawnoftimebuilder.block.IBlockChain;
import org.dawnoftimebuilder.block.templates.BlockDoTB;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.dawnoftimebuilder.registry.DoTBItemsRegistry.SILK_WORMS;

public class StickBundleBlock extends BlockDoTB implements IBlockChain {

    private static final VoxelShape VS_TOP = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    private static final VoxelShape VS_BOTTOM = Block.box(4.0D, 4.0D, 4.0D, 12.0D, 16.0D, 12.0D);
	public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
	private static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public StickBundleBlock(Properties properties) {
		super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AGE, 0).setValue(HALF, Half.TOP));
    }

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return state.getValue(HALF) == Half.BOTTOM ? VS_BOTTOM : VS_TOP;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HALF, AGE);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		World world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		if(!world.getBlockState(pos.below()).canBeReplaced(context) || !canSurvive(this.defaultBlockState(), world, pos))
			return null;
		return super.getStateForPlacement(context);
	}

	@Override
	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		worldIn.setBlock(pos.below(), state.setValue(HALF, Half.BOTTOM), 10);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if(facing.getAxis().isHorizontal()) return stateIn;
		if(facing == Direction.UP && stateIn.getValue(HALF) == Half.BOTTOM) {
			if(facingState.getBlock() == this){
				if(facingState.getValue(HALF) == Half.TOP){
					return stateIn;
				}
			}
			return Blocks.AIR.defaultBlockState();
		}
		if(facing == Direction.DOWN && stateIn.getValue(HALF) == Half.TOP) {
			if(facingState.getBlock() == this){
				if(facingState.getValue(HALF) == Half.BOTTOM){
					return stateIn;
				}
			}
			return Blocks.AIR.defaultBlockState();
		}
    	return stateIn;
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
    	pos = pos.above();
    	BlockState stateUp = worldIn.getBlockState(pos);
		return state.getValue(HALF) == Half.BOTTOM || canSupportCenter(worldIn, pos, Direction.DOWN) || IBlockChain.canBeChained(stateUp, true);
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(!worldIn.isClientSide()){
			//The StickBundle is empty, we try to put worms on it.
			if(state.getValue(AGE) == 0){
				ItemStack itemstack = player.getItemInHand(handIn);
				if(itemstack.getItem() == SILK_WORMS.get() && !itemstack.isEmpty()){
					itemstack.shrink(1);
					worldIn.setBlock(pos, state.setValue(AGE, 1), 10);
					if(state.getValue(HALF) == Half.TOP){
						worldIn.setBlock(pos.below(), this.defaultBlockState().setValue(HALF, Half.BOTTOM).setValue(AGE, 1), 10);
					}else{
						worldIn.setBlock(pos.above(), this.defaultBlockState().setValue(HALF, Half.TOP).setValue(AGE, 1), 10);
					}
					return ActionResultType.SUCCESS;
				}
			}

			//The StickBundle has fully grown worms, it's time to harvest !
			if(state.getValue(AGE) == 3){
				List<ItemStack> drops = DoTBBlockUtils.getLootList((ServerWorld)worldIn, state, player.getItemInHand(handIn), Objects.requireNonNull(this.getRegistryName()).getPath() + "_harvest");
				DoTBBlockUtils.dropLootFromList(worldIn, pos, drops, 1.0F);
				worldIn.setBlock(pos, state.setValue(AGE, 0), 10);
				worldIn.playSound(null, pos, SoundEvents.GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
				if(state.getValue(HALF) == Half.TOP){
					worldIn.setBlock(pos.below(), this.defaultBlockState().setValue(HALF, Half.BOTTOM).setValue(AGE, 0), 10);
				}else{
					worldIn.setBlock(pos.above(), this.defaultBlockState().setValue(HALF, Half.TOP).setValue(AGE, 0), 10);
				}
				return ActionResultType.SUCCESS;
			}
		}
    	return ActionResultType.PASS;
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return state.getValue(AGE) > 0 && state.getValue(AGE) < 3 && state.getValue(HALF) == Half.TOP;
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		int growth = state.getValue(AGE);
		if (growth > 0 && growth < 3) {
			if(random.nextInt(DoTBConfig.STICK_BUNDLE_GROWTH_CHANCE.get()) == 0) {
				worldIn.setBlock(pos, worldIn.getBlockState(pos).setValue(AGE,growth + 1), 10);
				worldIn.setBlock(pos.below(), worldIn.getBlockState(pos.below()).setValue(AGE,growth + 1), 10);
			}
		}

	}

	@Override
	public PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.DESTROY;
	}

	@Override
	public boolean canConnectToChainUnder(BlockState state) {
		return false;
	}
}