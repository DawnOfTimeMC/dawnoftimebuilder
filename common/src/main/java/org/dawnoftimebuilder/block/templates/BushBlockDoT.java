package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.IBlockGeneration;
import org.dawnoftimebuilder.block.IFlammable;
import org.dawnoftimebuilder.util.Utils;

import java.util.List;

import static org.dawnoftimebuilder.util.VoxelShapes.FULL_SHAPE;

public class BushBlockDoT extends BushBlock implements IBlockGeneration, IFlammable, BonemealableBlock {
    private int fireSpreadSpeed = 0;
    private int fireDestructionSpeed = 0;
    private final VoxelShape[] shapes;

    public BushBlockDoT(final Properties properties) {
        this(properties, FULL_SHAPE);
    }

    public BushBlockDoT(final Properties properties, VoxelShape[] shapes) {
        super(properties);
        this.shapes = shapes;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return this.shapes[0];
    }

    /**
     * Set Encouragement to 5 and Flammability to 20
     *
     * @return this
     */
    public Block setBurnable() {
        return this.setBurnable(5, 20);
    }

    /**
     * Set burning parameters (default 5 / 20)
     *
     * @param fireSpreadSpeed      Increases the probability to catch fire
     * @param fireDestructionSpeed Decreases burning duration
     *
     * @return this
     */
    public Block setBurnable(final int fireSpreadSpeed, final int fireDestructionSpeed) {
        this.fireSpreadSpeed = fireSpreadSpeed;
        this.fireDestructionSpeed = fireDestructionSpeed;
        return this;
    }

    @Override
    public int getFireSpreadSpeed(final BlockState state, final BlockGetter world, final BlockPos pos, final Direction face) {
        return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) ? 0 : this.fireSpreadSpeed;
    }

    @Override
    public int getFlammability(final BlockState state, final BlockGetter world, final BlockPos pos, final Direction face) {
        return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) ? 0 : this.fireDestructionSpeed;
    }

    @Override
    public boolean generateOnPos(WorldGenLevel world, BlockPos pos, BlockState state, RandomSource random) {
        final BlockState groundState = world.getBlockState(pos.below());

        if (!groundState.is(BlockTags.DIRT)) {
            return false;
        }
        world.setBlock(pos, state, 2);

        return true;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader var1, BlockPos var2, BlockState var3, boolean var4) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level var1, RandomSource var2, BlockPos var3, BlockState var4) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        List<ItemStack> drops = Utils.getLootList(worldIn, state, Items.BONE_MEAL.getDefaultInstance(), this.builtInRegistryHolder().key().location().getPath() + "_bone_meal");
        Utils.dropLootFromList(worldIn, pos, drops, 1);
    }
}
