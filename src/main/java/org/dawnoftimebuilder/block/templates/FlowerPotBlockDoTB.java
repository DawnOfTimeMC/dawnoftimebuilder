package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.IBlockSpecialDisplay;

import javax.annotation.Nullable;

import static net.minecraft.world.level.block.Blocks.FLOWER_POT;

public class FlowerPotBlockDoTB extends BlockDoTB implements IBlockSpecialDisplay {
    private Item itemInPot;
    private static final VoxelShape VS = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 4.0D, 11.0D);

    public FlowerPotBlockDoTB(@Nullable Item itemInPot) {
        super(Block.Properties.copy(FLOWER_POT));
        this.itemInPot = itemInPot;
    }

    public void setItemInPot(@Nullable Item itemInPot) {
        this.itemInPot = itemInPot;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockReader, BlockPos pos, CollisionContext context) {
        return VS;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player entity, InteractionHand hand, BlockHitResult ray) {
        if(this.itemInPot != null && !world.isClientSide()) {
            if(entity.getItemInHand(hand).isEmpty()) {
                Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this.itemInPot));
                world.setBlock(pos, Blocks.FLOWER_POT.defaultBlockState(), 2);
            }
        }
        return super.use(state, world, pos, entity, hand, ray);
    }

    public BlockState getRandomState() {
        return this.defaultBlockState();
    }

    @Override
    public float getDisplayScale() {
        return 0.667F;
    }
}
