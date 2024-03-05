package org.dawnoftimebuilder.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.block.templates.BushBlockDoT;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;

public class PausedMapleSaplingBlock extends BushBlockDoT {
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);

    public PausedMapleSaplingBlock(final Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(final BlockState p_220053_1_In, final BlockGetter p_220053_2_In, final BlockPos p_220053_3_In, final CollisionContext p_220053_4_In) {
        return PausedMapleSaplingBlock.SHAPE;
    }

    @Override
    public ItemStack getCloneItemStack(final BlockState stateIn, final HitResult targetIn, final BlockGetter worldIn, final BlockPos posIn, final Player playerIn) {
        return new ItemStack(DoTBBlocksRegistry.ACACIA_BEAM.get().asItem());
    }

    @Override
    public InteractionResult use(final BlockState p_225533_1_In, final Level p_225533_2_In, final BlockPos p_225533_3_In, final Player p_225533_4_In, final InteractionHand p_225533_5_In, final BlockHitResult p_225533_6_In) {
        if(p_225533_4_In.getMainHandItem().getItem() instanceof FlintAndSteelItem) {
            p_225533_2_In.setBlock(p_225533_3_In, DoTBBlocksRegistry.MAPLE_RED_SAPLING.get().defaultBlockState(), 35);
            p_225533_2_In.levelEvent(p_225533_4_In, 2001, p_225533_3_In, Block.getId(p_225533_1_In));

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    /**
     * Lights methods
     */
    @Override
    public boolean useShapeForLightOcclusion(final BlockState p_220074_1_In) {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getShadeBrightness(final BlockState p_220080_1_, final BlockGetter p_220080_2_, final BlockPos p_220080_3_) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(final BlockState p_200123_1_, final BlockGetter p_200123_2_, final BlockPos p_200123_3_) {
        return true;
    }
}
