package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.IBlockCustomItem;
import org.dawnoftimebuilder.block.templates.BlockDoTB;

import static net.minecraft.block.Blocks.SPRUCE_PLANKS;
import static org.dawnoftimebuilder.registries.DoTBBlocksRegistry.SMALL_TATAMI_MAT;

public class SmallTatamiFloorBlock extends BlockDoTB implements IBlockCustomItem {

	private VoxelShape VS = makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 17.0D, 16.0D);
	public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;

	public SmallTatamiFloorBlock() {
		super("small_tatami_floor", Material.WOOD, 2.0F, 2.0F);
		this.setBurnable();
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_AXIS);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VS;
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if(facing == Direction.UP){
			BlockState stateAbove = worldIn.getBlockState(facingPos);
			if(hasSolidSide(stateAbove, worldIn, facingPos, Direction.DOWN) && stateAbove.isSolid()){
				spawnAsEntity(worldIn.getWorld(), currentPos, new ItemStack(SMALL_TATAMI_MAT.asItem(), 1));
				return SPRUCE_PLANKS.getDefaultState();
			}
		}
		return stateIn;
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(player.isSneaking()){
			if(worldIn.isAirBlock(pos.up())){
				worldIn.setBlockState(pos.up(), SMALL_TATAMI_MAT.getDefaultState().with(SmallTatamiMatBlock.ROLLED, true).with(SmallTatamiMatBlock.HORIZONTAL_AXIS, state.get(HORIZONTAL_AXIS)), 10);
				worldIn.setBlockState(pos, SPRUCE_PLANKS.getDefaultState(), 10);
				worldIn.playSound(player, pos.up(), this.soundType.getPlaceSound(), SoundCategory.BLOCKS, (this.soundType.getVolume() + 1.0F) / 2.0F, this.soundType.getPitch() * 0.8F);
				return true;
			}
		}
		return false;
	}

	@Override
	public Item getCustomItemBlock(){
		return null;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(HORIZONTAL_AXIS, state.get(HORIZONTAL_AXIS) == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X);
	}
}
