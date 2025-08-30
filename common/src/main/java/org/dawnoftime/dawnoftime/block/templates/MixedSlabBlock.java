package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.function.Supplier;

public class MixedSlabBlock extends SlabBlockDoT {
    private final ArrayList<MixedBlockRecipe> listRecipes = new ArrayList<>();

    public MixedSlabBlock(Properties properties) {
        super(properties);
    }

    /**
     * Add a new mixed full Block recipe for this slab
     *
     * @param secondSlab       full block's second slab
     * @param mixedBlock       block that results of the combination of both slabs
     * @param thisSlabIsBottom True if this slab is bottom, and the secondSlab is top. False otherwise.
     *
     * @return this slab
     */
    public MixedSlabBlock addMixedBlockRecipe(Block secondSlab, Supplier<Block> mixedBlock, boolean thisSlabIsBottom) {
        return this.addMixedBlockRecipe(() -> secondSlab, mixedBlock, thisSlabIsBottom);
    }

    /**
     * Add a new mixed full Block recipe for this slab
     *
     * @param secondSlab       full block's second slab
     * @param mixedBlock       block that results of the combination of both slabs
     * @param thisSlabIsBottom True if this slab is bottom, and the secondSlab is top. False otherwise.
     *
     * @return this slab
     */
    private MixedSlabBlock addMixedBlockRecipe(Supplier<Block> secondSlab, Supplier<Block> mixedBlock, boolean thisSlabIsBottom) {
        this.listRecipes.add(new MixedBlockRecipe(secondSlab, mixedBlock, thisSlabIsBottom));
        return this;
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        Direction facing = hit.getDirection();
        ItemStack itemStack = player.getItemInHand(handIn);
        if(!player.isCrouching() && player.mayUseItemAt(pos, facing, itemStack) && facing.getAxis().isVertical() && !itemStack.isEmpty()) {
            for(MixedBlockRecipe recipe : listRecipes) {
                if(facing == recipe.getFacingForMerging(false)) {
                    if(recipe.isConnectibleFirstSlab(state) && itemStack.getItem() == recipe.getSecondSlab().asItem()) {
                        BlockState madeState = recipe.getMixedBlock().defaultBlockState();
                        if(worldIn.setBlock(pos, madeState, 11)) {
                            this.setPlacedBy(worldIn, pos, state, player, itemStack);
                            if(player instanceof ServerPlayer) {
                                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, pos, itemStack);
                            }
                            SoundType soundtype = recipe.getMixedBlock().getSoundType(state);
                            worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                            if(!player.isCreative())
                                itemStack.shrink(1);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    public static Item getBlockItem(MixedSlabBlock block) {
        return new BlockItem(block, new Item.Properties()) {
            @Override
            public InteractionResult place(BlockPlaceContext context) {
                Direction facing = context.getClickedFace();
                if((context.getPlayer() != null && context.getPlayer().isCrouching()) || !facing.getAxis().isVertical())
                    return super.place(context);

                Player player = context.getPlayer();
                ItemStack itemStack = context.getItemInHand();
                Level worldIn = context.getLevel();
                BlockPos pos = context.getClickedPos();
                if(!context.replacingClickedOnBlock())
                    pos = pos.relative(facing.getOpposite());
                if(player != null) {
                    if(!player.mayUseItemAt(pos, facing, itemStack))
                        return super.place(context);
                }

                if(!itemStack.isEmpty()) {
                    BlockState state = worldIn.getBlockState(pos);
                    for(MixedBlockRecipe recipe : block.listRecipes) {
                        if(facing == recipe.getFacingForMerging(true)) {
                            if(recipe.isConnectibleSecondSlab(state)) {
                                BlockState madeState = recipe.getMixedBlock().getStateForPlacement(context);
                                if(madeState == null)
                                    continue;
                                if(worldIn.setBlock(pos, madeState, 11)) {
                                    this.getBlock().setPlacedBy(worldIn, pos, state, player, itemStack);
                                    if(player instanceof ServerPlayer) {
                                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, pos, itemStack);
                                    }
                                    SoundType soundtype = recipe.getMixedBlock().getSoundType(state);
                                    worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                                    itemStack.shrink(1);
                                    return InteractionResult.SUCCESS;
                                }
                            }
                        }
                    }
                }
                return super.place(context);
            }
        };
    }

    public static class MixedBlockRecipe {
        private final Supplier<Block> secondSlab;
        private final Supplier<Block> mixedBlock;
        private final boolean firstSlabIsBottom;

        private MixedBlockRecipe(Supplier<Block> secondSlab, Supplier<Block> mixedBlock, boolean firstSlabIsBottom) {
            this.secondSlab = secondSlab;
            this.mixedBlock = mixedBlock;
            this.firstSlabIsBottom = firstSlabIsBottom;
        }

        private boolean isConnectibleFirstSlab(BlockState state) {
            return this.firstSlabIsBottom ? state.getValue(TYPE) == SlabType.BOTTOM : state.getValue(TYPE) == SlabType.TOP;
        }

        private boolean isConnectibleSecondSlab(BlockState state) {
            if(state.getBlock() == this.secondSlab.get()) {
                if(this.secondSlab.get() instanceof SlabBlock) {
                    return this.firstSlabIsBottom ? state.getValue(TYPE) == SlabType.TOP : state.getValue(TYPE) == SlabType.BOTTOM;
                }
            }
            return false;
        }

        private Direction getFacingForMerging(boolean tryPlacingFirstSlab) {
            if(tryPlacingFirstSlab) {
                return this.firstSlabIsBottom ? Direction.DOWN : Direction.UP;
            } else {
                return this.firstSlabIsBottom ? Direction.UP : Direction.DOWN;
            }
        }

        protected Block getSecondSlab() {
            return this.secondSlab.get();
        }

        protected Block getMixedBlock() {
            return this.mixedBlock.get();
        }
    }
}
