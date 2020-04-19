package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
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
import static org.dawnoftimebuilder.block.japanese.BlockSmallTatamiMat.ROLLED;
import static org.dawnoftimebuilder.registries.DoTBBlocksRegistry.SMALL_TATAMI_MAT;

public class BlockSmallTatamiFloor extends BlockDoTB implements IBlockCustomItem {

	private VoxelShape VS = makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 17.0D, 16.0D);

	public BlockSmallTatamiFloor() {
		super("small_tatami_floor", Material.WOOD, 2.0F, 2.0F);
		this.setBurnable();
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
				worldIn.setBlockState(pos.up(), SMALL_TATAMI_MAT.getDefaultState().with(ROLLED, true), 10);
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
}
