package org.dawnoftimebuilder.block.german;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.block.IBlockChain;
import org.dawnoftimebuilder.block.templates.CandleLampBlock;

public class WaxedOakChandelierBlock extends CandleLampBlock implements IBlockChain {
    private static final VoxelShape VS = Shapes.or(
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(6.0D, 8.0D, 6.0D, 10.0D, 16.0D, 10.0D));

    public WaxedOakChandelierBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return VS;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        this.animateLitCandle(stateIn, worldIn, pos, 0.12D, 0.5D, 0.5D);
        this.animateLitCandle(stateIn, worldIn, pos, 0.5D, 0.5D, 0.12D);
        this.animateLitCandle(stateIn, worldIn, pos, 0.5D, 0.5D, 0.88D);
        this.animateLitCandle(stateIn, worldIn, pos, 0.88D, 0.5D, 0.5D);
    }

    @Override
    public boolean canConnectToChainUnder(BlockState state) {
        return false;
    }
}
