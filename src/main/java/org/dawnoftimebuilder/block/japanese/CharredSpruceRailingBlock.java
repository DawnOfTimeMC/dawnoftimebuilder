package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties.FencePillar;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nullable;
import java.util.List;

public class CharredSpruceRailingBlock extends FenceBlock {

	private static final EnumProperty<DoTBBlockStateProperties.FencePillar> FENCE_PILLAR = DoTBBlockStateProperties.FENCE_PILLAR;

	public CharredSpruceRailingBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(FENCE_PILLAR, FencePillar.PILLAR_SMALL));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FENCE_PILLAR);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		World world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState oldState = world.getBlockState(pos);
		if(oldState.getBlock() == this){
			if(this.isSmallPillar(oldState)){
				return oldState.setValue(FENCE_PILLAR, this.getBigPillar(world, pos));
			}
		}
		return super.getStateForPlacement(context);
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (super.use(state, worldIn, pos, player, handIn, hit) == ActionResultType.SUCCESS) return ActionResultType.SUCCESS;
		if(player.isCrouching()) {
			switch (state.getValue(FENCE_PILLAR)) {
				case PILLAR_BIG:
				case CAP_PILLAR_BIG:
					if (!player.isCreative()) {
						InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this.asItem()));
					}
				case NONE:
					worldIn.setBlock(pos, state.setValue(FENCE_PILLAR, FencePillar.PILLAR_SMALL), 2);
					break;
				case PILLAR_SMALL:
					worldIn.setBlock(pos, state.setValue(FENCE_PILLAR, FencePillar.NONE), 2);
					break;
			}

			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockItemUseContext useContext) {
		if(useContext.getItemInHand().getItem() == this.asItem()) {
			if(useContext.getPlayer() != null && useContext.getPlayer().isCrouching()){
				return useContext.replacingClickedOnBlock();
			}
			return this.isSmallPillar(state);
		}
		return useContext.replacingClickedOnBlock();
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		if(facing == Direction.UP && !isSmallPillar(stateIn)){
			stateIn = stateIn.setValue(FENCE_PILLAR, getBigPillar(worldIn, currentPos));
		}
		return stateIn;
	}

	private boolean isSmallPillar(BlockState state){
		return state.getValue(FENCE_PILLAR) == FencePillar.NONE || state.getValue(FENCE_PILLAR) == FencePillar.PILLAR_SMALL;
	}

	private DoTBBlockStateProperties.FencePillar getBigPillar(IWorld world, BlockPos pos){
		pos = pos.above();
		return (world.getBlockState(pos).getCollisionShape(world, pos).isEmpty()) ? FencePillar.CAP_PILLAR_BIG : FencePillar.PILLAR_BIG;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		DoTBBlockUtils.addTooltip(tooltip, this);
	}
}
