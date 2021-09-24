package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;

import net.minecraft.block.material.Material;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nullable;
import java.util.List;

import static org.dawnoftimebuilder.util.DoTBBlockUtils.HIGHEST_Y;
import static org.dawnoftimebuilder.util.DoTBBlockUtils.TOOLTIP_COLUMN;

public abstract class ColumnConnectibleBlock extends WaterloggedBlock {

	public static final EnumProperty<DoTBBlockStateProperties.VerticalConnection> VERTICAL_CONNECTION = DoTBBlockStateProperties.VERTICAL_CONNECTION;

	public ColumnConnectibleBlock(Material materialIn, float hardness, float resistance, SoundType soundType) {
		this(Block.Properties.of(materialIn).strength(hardness, resistance).sound(soundType));
	}

	public ColumnConnectibleBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.getStateContainer().getBaseState().with(VERTICAL_CONNECTION, DoTBBlockStateProperties.VerticalConnection.NONE));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(VERTICAL_CONNECTION);
	}

	@Override
	public abstract VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context);

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		return (facing.getAxis().isVertical()) ? stateIn.with(VERTICAL_CONNECTION, this.getColumnState(worldIn, currentPos, stateIn)) : stateIn;
	}

	public DoTBBlockStateProperties.VerticalConnection getColumnState(IWorld worldIn, BlockPos pos, BlockState stateIn){
		if(isConnectible(worldIn, pos.up(), stateIn)){
			return (isConnectible(worldIn, pos.down(), stateIn)) ? DoTBBlockStateProperties.VerticalConnection.BOTH : DoTBBlockStateProperties.VerticalConnection.ABOVE;
		}else{
			return (isConnectible(worldIn, pos.down(), stateIn)) ? DoTBBlockStateProperties.VerticalConnection.UNDER : DoTBBlockStateProperties.VerticalConnection.NONE;
		}
	}

	public boolean isConnectible(IWorld worldIn, BlockPos pos, BlockState stateIn){
		return worldIn.getBlockState(pos).getBlock() == this;
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack heldItemStack = player.getHeldItem(handIn);
		if(player.isSneaking()) {
			//We remove the highest ColumnBlock
			if(state.get(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.NONE)
				return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
			BlockPos topPos = this.getHighestColumnPos(worldIn, pos);
			if(topPos != pos){
				if(!worldIn.isRemote()) {
					worldIn.setBlockState(topPos, Blocks.AIR.defaultBlockState(), 35);
					if (!player.isCreative()) {
						Block.spawnDrops(state, worldIn, pos, null, player, heldItemStack);
					}
				}
				return true;
			}
		}else{
			if(!heldItemStack.isEmpty() && heldItemStack.getItem() == this.asItem()){
				//We put a ColumnBlock on top of the column
				BlockPos topPos = this.getHighestColumnPos(worldIn, pos).up();
				if(topPos.getY() <= HIGHEST_Y){
					if(!worldIn.isRemote()) {
						worldIn.setBlockState(topPos, state, 11);
						if(!player.isCreative()) {
							heldItemStack.shrink(1);
						}
					}
					return true;
				}
			}
		}
		return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
	}

	private BlockPos getHighestColumnPos(World worldIn, BlockPos pos){
		int yOffset;
		for(yOffset = 0; yOffset + pos.getY() <= HIGHEST_Y; yOffset++){
			if(worldIn.getBlockState(pos.up(yOffset)).getBlock() != this) break;
		}
		return pos.up(yOffset - 1);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		DoTBBlockUtils.addTooltip(tooltip, TOOLTIP_COLUMN);
	}
}