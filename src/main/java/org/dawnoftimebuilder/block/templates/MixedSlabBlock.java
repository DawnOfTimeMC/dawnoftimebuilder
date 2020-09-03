package org.dawnoftimebuilder.block.templates;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.IBlockCustomItem;

import javax.annotation.Nullable;
import java.util.ArrayList;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class MixedSlabBlock extends SlabBlockDoTB implements IBlockCustomItem {

	private final ArrayList<MixedBlockRecipe> listRecipes = new ArrayList<>();
	private final String name;

	public MixedSlabBlock(String name, Properties properties) {
		super(name, properties);
		this.name = name;
	}

	public MixedSlabBlock(String name, Material materialIn, float hardness, float resistance) {
		this(name, Properties.create(materialIn).hardnessAndResistance(hardness, resistance));
	}

	public MixedSlabBlock(String name, Block block) {
		this(name, Properties.from(block));
	}

	public Block addMixedBlockRecipe(SlabBlock secondSlab, Block mixedBlock, boolean thisSlabIsBottom){
		this.listRecipes.add(new MixedBlockRecipe(secondSlab, mixedBlock, thisSlabIsBottom));
		return this;
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		Direction facing = hit.getFace();
		ItemStack itemStack = player.getHeldItem(handIn);
		if(!player.isSneaking() && player.canPlayerEdit(pos, facing, itemStack) && facing.getAxis().isVertical() && !itemStack.isEmpty()){
			for(MixedBlockRecipe recipe : listRecipes) {
				if(facing == recipe.getFacingForMerging(false)){
					if (recipe.isConnectibleFirstSlab(state) && itemStack.getItem() == recipe.secondSlab.asItem()) {
						BlockState madeState = recipe.mixedBlock.getDefaultState();
						if(worldIn.setBlockState(pos, madeState, 11))  {
							this.onBlockPlacedBy(worldIn, pos, state, player, itemStack);
							if (player instanceof ServerPlayerEntity) {
								CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)player, pos, itemStack);
							}
							SoundType soundtype = recipe.mixedBlock.getSoundType(state, worldIn, pos, player);
							worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
							if(!player.isCreative())
								itemStack.shrink(1);
							return true;
						}
					}
				}
			}
		}
		return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
	}

	@Nullable
	@Override
	public Item getCustomItemBlock() {
		return new BlockItem(this, new Item.Properties().group(DOTB_TAB)){
			@Override
			public ActionResultType tryPlace(BlockItemUseContext context) {
				Direction facing = context.getFace();
				if(context.isPlacerSneaking() || !facing.getAxis().isVertical())
					return super.tryPlace(context);

				PlayerEntity player = context.getPlayer();
				ItemStack itemStack = context.getItem();
				World worldIn = context.getWorld();
				BlockPos pos = context.getPos();
				if(!context.replacingClickedOnBlock()) pos = pos.offset(facing.getOpposite());
				if(player != null){
					if(!player.canPlayerEdit(pos, facing, itemStack))
						return super.tryPlace(context);
				}

				if (!itemStack.isEmpty()) {
					BlockState state = worldIn.getBlockState(pos);
					for(MixedBlockRecipe recipe : listRecipes) {
						if(facing == recipe.getFacingForMerging(true)){
							if (recipe.isConnectibleSecondSlab(state)) {
								BlockState madeState = recipe.mixedBlock.getStateForPlacement(context);
								if(madeState == null)
									continue;
								if(worldIn.setBlockState(pos, madeState, 11))  {
									this.onBlockPlaced(pos, worldIn, player, itemStack, madeState);
									this.getBlock().onBlockPlacedBy(worldIn, pos, state, player, itemStack);
									if (player instanceof ServerPlayerEntity) {
										CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)player, pos, itemStack);
									}
									SoundType soundtype = recipe.mixedBlock.getSoundType(state, worldIn, pos, player);
									worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
									itemStack.shrink(1);
									return ActionResultType.SUCCESS;
								}
							}

						}
					}
				}
				return super.tryPlace(context);
			}
		}.setRegistryName(MOD_ID, name);
	}

	public static class MixedBlockRecipe {

		private final Block secondSlab;
		private final Block mixedBlock;
		private final boolean firstSlabIsBottom;

		private MixedBlockRecipe(SlabBlock secondSlab, Block mixedBlock, boolean firstSlabIsBottom){
			this.secondSlab = secondSlab;
			this.mixedBlock = mixedBlock;
			this.firstSlabIsBottom = firstSlabIsBottom;
		}

		private boolean isConnectibleFirstSlab(BlockState state){
			return firstSlabIsBottom ? state.get(TYPE) == SlabType.BOTTOM : state.get(TYPE) == SlabType.TOP;
		}

		private boolean isConnectibleSecondSlab(BlockState state){
			if(state.getBlock() == this.secondSlab){
				if(secondSlab instanceof SlabBlock){
					return firstSlabIsBottom ? state.get(TYPE) == SlabType.TOP : state.get(TYPE) == SlabType.BOTTOM;
				}
			}
			return false;
		}

		private Direction getFacingForMerging(boolean tryPlacingFirstSlab){
			if (tryPlacingFirstSlab) {
				return this.firstSlabIsBottom ? Direction.DOWN : Direction.UP;
			}else{
				return this.firstSlabIsBottom ? Direction.UP : Direction.DOWN;
			}
		}
	}
}