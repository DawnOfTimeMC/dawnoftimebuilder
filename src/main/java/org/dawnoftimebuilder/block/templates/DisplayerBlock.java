package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.dawnoftimebuilder.blockentity.DisplayerTileEntity;

import static net.minecraft.world.Containers.dropItemStack;
import static net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER;
import static org.dawnoftimebuilder.registry.DoTBTileEntitiesRegistry.DISPLAYER_TE;

public abstract class DisplayerBlock extends WaterloggedBlock implements EntityBlock {

	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public DisplayerBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(LIT, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LIT);
	}

	@org.jetbrains.annotations.Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return DISPLAYER_TE.get().create(pPos, pState);
	}

	@Override
	public InteractionResult use(BlockState blockState, Level world, BlockPos pos, Player playerEntity, InteractionHand hand, BlockHitResult rayTraceResult) {
		if(!world.isClientSide()){
			BlockEntity tileEntity = world.getBlockEntity(pos);
			if(tileEntity instanceof MenuProvider){
				NetworkHooks.openScreen((ServerPlayer) playerEntity, (MenuProvider) tileEntity, tileEntity.getBlockPos());
			}
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public void onRemove(BlockState oldState, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (oldState.getBlock() != newState.getBlock()) {
			BlockEntity tileEntity = worldIn.getBlockEntity(pos);
			if (tileEntity instanceof DisplayerTileEntity) {
				tileEntity.getCapability(ITEM_HANDLER).ifPresent(h -> {
					for(int index = 0; index < 9 ; index++){
						dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), h.getStackInSlot(index));
					}
				});
			}
		}
		super.onRemove(oldState, worldIn, pos, newState, isMoving);
	}

	public abstract double getDisplayerX(BlockState state);

	public abstract double getDisplayerY(BlockState state);

	public abstract double getDisplayerZ(BlockState state);
}