package org.dawnoftime.dawnoftime.block.german;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.dawnoftime.dawnoftime.block.IBlockChain;
import org.dawnoftime.dawnoftime.block.templates.CandleLampBlock;

import static org.dawnoftime.dawnoftime.util.VoxelShapes.WAXED_OAK_CHANDELIER_SHAPES;

public class WaxedOakChandelierBlock extends CandleLampBlock implements IBlockChain {
    public WaxedOakChandelierBlock(Properties properties) {
        super(properties, WAXED_OAK_CHANDELIER_SHAPES);
    }

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
