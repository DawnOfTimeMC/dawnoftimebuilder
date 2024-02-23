package org.dawnoftimebuilder.block.roman;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.IBlockPillar;
import org.dawnoftimebuilder.block.templates.ColumnConnectibleBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import javax.annotation.Nonnull;

public class SandstoneColumnBlock extends ColumnConnectibleBlock implements IBlockPillar {
    private static final VoxelShape VS_BOT = Shapes.or(
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D),
            Block.box(4.0D, 8.0D, 4.0D, 12.0D, 16.0D, 12.0D)
    );
    private static final VoxelShape VS_MID = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    private static final VoxelShape VS_TOP = Shapes.or(
            Block.box(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(2.0D, 8.0D, 2.0D, 14.0D, 12.0D, 14.0D),
            Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D)
    );

    public SandstoneColumnBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch(state.getValue(VERTICAL_CONNECTION)) {
            case UNDER:
                return VS_TOP;
            default:
            case NONE:
            case BOTH:
                return VS_MID;
            case ABOVE:
                return VS_BOT;
        }
    }

    @Nonnull
    @Override
    public DoTBBlockStateProperties.PillarConnection getBlockPillarConnectionAbove(BlockState state) {
        return DoTBBlockStateProperties.PillarConnection.EIGHT_PX;
    }

    @Override
    public boolean isConnectible(BlockState stateIn, LevelAccessor worldIn, BlockPos pos, Direction faceToConnect) {
        BlockState testedState = worldIn.getBlockState(pos);
        if(faceToConnect == Direction.DOWN && IBlockPillar.getPillarConnectionUnder(worldIn, pos) == DoTBBlockStateProperties.PillarConnection.EIGHT_PX) {
            return true;
        }
        if(faceToConnect == Direction.UP && IBlockPillar.getPillarConnectionAbove(worldIn, pos) == DoTBBlockStateProperties.PillarConnection.EIGHT_PX) {
            return true;
        }
        return testedState.getBlock() == this;
    }
}
