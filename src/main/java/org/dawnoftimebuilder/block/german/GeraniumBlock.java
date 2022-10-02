package org.dawnoftimebuilder.block.german;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import org.dawnoftimebuilder.block.IBlockGeneration;
import org.dawnoftimebuilder.block.ICustomBlockItem;
import org.dawnoftimebuilder.block.templates.BlockDoTB;
import org.dawnoftimebuilder.block.templates.WildPlantBlock;
import org.dawnoftimebuilder.item.templates.PotAndBlockItem;

import javax.annotation.Nullable;

import java.util.Random;

import static net.minecraftforge.common.Tags.Blocks.DIRT;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;

public class GeraniumBlock extends BlockDoTB implements ICustomBlockItem {

    private static final VoxelShape VS = Block.box(-2.0D, -7.0D, -2.0D, 17.0D, 15.0D, 17.0D);

    public GeraniumBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return canSurvive(this.defaultBlockState(), context.getLevel(), context.getClickedPos()) ? super.getStateForPlacement(context) : null;
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        Block blockDown = worldIn.getBlockState(pos.below()).getBlock();
        return blockDown == Blocks.GRASS_BLOCK || blockDown.is(DIRT) || blockDown == Blocks.FARMLAND;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VS;
    }

    @Nullable
    @Override
    public Item getCustomBlockItem() {
        return new PotAndBlockItem(this, new Item.Properties().tab(DOTB_TAB));
    }
}
