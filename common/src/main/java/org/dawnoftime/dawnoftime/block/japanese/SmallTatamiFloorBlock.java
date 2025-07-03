package org.dawnoftime.dawnoftime.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.dawnoftime.dawnoftime.block.templates.BlockDoT;
import org.dawnoftime.dawnoftime.registry.DoTBBlocksRegistry;
import org.dawnoftime.dawnoftime.util.VoxelShapes;

import javax.annotation.Nullable;

public class SmallTatamiFloorBlock extends BlockDoT {
    public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;

    public SmallTatamiFloorBlock(Properties properties) {
        super(properties, VoxelShapes.SMALL_TATAMI_FLOOR_SHAPES);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_AXIS);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(facing == Direction.UP) {
            BlockState stateAbove = worldIn.getBlockState(facingPos);
            if(isFaceFull(stateAbove.getShape(worldIn, facingPos), Direction.DOWN) && stateAbove.canOcclude()) {
                Containers.dropItemStack((Level) worldIn, currentPos.getX(), currentPos.getY(), currentPos.getZ(), new ItemStack(DoTBBlocksRegistry.INSTANCE.SMALL_TATAMI_MAT.get().asItem(), 1));
                return Blocks.SPRUCE_PLANKS.defaultBlockState();
            }
        }
        return stateIn;
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if(player.isCrouching()) {
            if(worldIn.isEmptyBlock(pos.above())) {
                worldIn.setBlock(pos.above(), DoTBBlocksRegistry.INSTANCE.SMALL_TATAMI_MAT.get().defaultBlockState().setValue(SmallTatamiMatBlock.ROLLED, true).setValue(SmallTatamiMatBlock.HORIZONTAL_AXIS, state.getValue(HORIZONTAL_AXIS)), 10);
                worldIn.setBlock(pos, Blocks.SPRUCE_PLANKS.defaultBlockState(), 10);
                worldIn.playSound(player, pos.above(), this.soundType.getPlaceSound(), SoundSource.BLOCKS, (this.soundType.getVolume() + 1.0F) / 2.0F, this.soundType.getPitch() * 0.8F);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(HORIZONTAL_AXIS, state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X);
    }

    @Override
    public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
        super.playerDestroy(worldIn, player, pos, state, te, stack);
        worldIn.setBlock(pos, Blocks.SPRUCE_PLANKS.defaultBlockState(), 10);
    }
}
