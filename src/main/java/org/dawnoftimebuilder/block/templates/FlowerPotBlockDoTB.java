package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.IBlockSpecialDisplay;

import javax.annotation.Nullable;

import static net.minecraft.block.Blocks.FLOWER_POT;

public class FlowerPotBlockDoTB extends BlockDoTB implements IBlockSpecialDisplay {

    private final Item itemInPot;
    private static final VoxelShape VS = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D);

	public FlowerPotBlockDoTB(@Nullable Item itemInPot) {
        super(AbstractBlock.Properties.copy(FLOWER_POT));
        this.itemInPot = itemInPot;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader blockReader, BlockPos pos, ISelectionContext context) {
        return VS;
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult ray) {
        if(this.itemInPot != null && !world.isClientSide()){
            if(entity.getItemInHand(hand).isEmpty()){
                InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this.itemInPot));
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
