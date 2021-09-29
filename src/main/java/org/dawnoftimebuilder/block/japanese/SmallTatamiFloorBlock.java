package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.templates.NoItemBlock;

import javax.annotation.Nullable;

import static net.minecraft.block.Blocks.SPRUCE_PLANKS;
import static org.dawnoftimebuilder.registry.DoTBBlocksRegistry.SMALL_TATAMI_MAT;

public class SmallTatamiFloorBlock extends NoItemBlock {

	private static final VoxelShape VS = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 17.0D, 16.0D);
	public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;

	public SmallTatamiFloorBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_AXIS);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VS;
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if(facing == Direction.UP){
			BlockState stateAbove = worldIn.getBlockState(facingPos);
			if(isFaceFull(stateAbove.getShape(worldIn, facingPos), Direction.DOWN) && stateAbove.canOcclude()){
				InventoryHelper.dropItemStack((World) worldIn, currentPos.getX(), currentPos.getY(), currentPos.getZ(), new ItemStack(SMALL_TATAMI_MAT.get().asItem(), 1));
				return SPRUCE_PLANKS.defaultBlockState();
			}
		}
		return stateIn;
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(player.isCrouching()){
			if(worldIn.isEmptyBlock(pos.above())){
				worldIn.setBlock(pos.above(), SMALL_TATAMI_MAT.get().defaultBlockState().setValue(SmallTatamiMatBlock.ROLLED, true).setValue(SmallTatamiMatBlock.HORIZONTAL_AXIS, state.getValue(HORIZONTAL_AXIS)), 10);
				worldIn.setBlock(pos, SPRUCE_PLANKS.defaultBlockState(), 10);
				worldIn.playSound(player, pos.above(), this.soundType.getPlaceSound(), SoundCategory.BLOCKS, (this.soundType.getVolume() + 1.0F) / 2.0F, this.soundType.getPitch() * 0.8F);
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.PASS;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(HORIZONTAL_AXIS, state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X);
	}

	@Override
	public void playerDestroy(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
		super.playerDestroy(worldIn, player, pos, state, te, stack);
		worldIn.setBlock(pos, SPRUCE_PLANKS.defaultBlockState(), 10);
	}
}
