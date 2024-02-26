package org.dawnoftimebuilder.block.roman;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.block.templates.BlockDoTB;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nullable;
import java.util.List;

public class CypressBlock extends BlockDoTB {
    public static final IntegerProperty SIZE = DoTBBlockStateProperties.SIZE_0_5;
    private static final VoxelShape VS_0 = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    private static final VoxelShape VS_1 = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 8.0D, 10.0D);
    private static final VoxelShape VS_2 = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    private static final VoxelShape VS_3_4 = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public CypressBlock(final Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(CypressBlock.SIZE, 1));
    }

    @Override
    public boolean canSurvive(final BlockState state, final LevelReader worldIn, final BlockPos pos) {
        return Block.canSupportCenter(worldIn, pos.below(), Direction.UP)
                || worldIn.getBlockState(pos.below()).getBlock() == this;
    }

    @Override
    public InteractionResult use(final BlockState state, final Level worldIn, final BlockPos pos,
                                 final Player player, final InteractionHand handIn, final BlockHitResult hit) {
        final ItemStack heldItemStack = player.getItemInHand(handIn);
        if(player.isCrouching()) {
            //We remove the highest CypressBlock
            final BlockPos topPos = this.getHighestCypressPos(worldIn, pos);
            if(topPos != pos) {
                if(!worldIn.isClientSide()) {
                    worldIn.setBlock(topPos, Blocks.AIR.defaultBlockState(), 35);
                    if(!player.isCreative()) {
                        Block.dropResources(state, worldIn, pos, null, player, heldItemStack);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        } else if(!heldItemStack.isEmpty() && heldItemStack.getItem() == this.asItem()) {
            //We put a CypressBlock on top of the cypress
            final BlockPos topPos = this.getHighestCypressPos(worldIn, pos).above();
            if(topPos.getY() <= DoTBUtils.HIGHEST_Y) {
                if(!worldIn.isClientSide() && worldIn.getBlockState(topPos).isAir()) {
                    worldIn.setBlock(topPos, this.defaultBlockState(), 11);
                    if(!player.isCreative()) {
                        heldItemStack.shrink(1);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    private BlockPos getHighestCypressPos(final Level worldIn, final BlockPos pos) {
        int yOffset;
        for(yOffset = 0; yOffset + pos.getY() <= DoTBUtils.HIGHEST_Y; yOffset++) {
            if(worldIn.getBlockState(pos.above(yOffset)).getBlock() != this) {
                break;
            }
        }
        return pos.above(yOffset - 1);
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CypressBlock.SIZE);
    }

    @Override
    public VoxelShape getShape(final BlockState state, final BlockGetter worldIn, final BlockPos pos,
                               final CollisionContext context) {
        switch(state.getValue(CypressBlock.SIZE)) {
            case 0:
                return CypressBlock.VS_0;
            default:
            case 1:
                return CypressBlock.VS_1;
            case 2:
                return CypressBlock.VS_2;
            case 3:
            case 4:
                return CypressBlock.VS_3_4;
            case 5:
                return Shapes.block();
        }
    }

    @Override
    public VoxelShape getBlockSupportShape(final BlockState p_230335_1_, final BlockGetter p_230335_2_,
                                           final BlockPos p_230335_3_) {
        return Shapes.empty();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        BlockState adjacentState = context.getLevel().getBlockState(context.getClickedPos().above());
        final int size = adjacentState.getBlock() == this
                ? Math.min(adjacentState.getValue(CypressBlock.SIZE) + 1, 5)
                : 1;
        if(size < 3) {
            return this.defaultBlockState().setValue(CypressBlock.SIZE, size);
        }
        adjacentState = context.getLevel().getBlockState(context.getClickedPos().below());
        return this.defaultBlockState().setValue(CypressBlock.SIZE, adjacentState.getBlock() == this ? size : 0);
    }

    @Override
    public BlockState updateShape(final BlockState stateIn, final Direction facing, final BlockState facingState,
                                  final LevelAccessor worldIn, final BlockPos currentPos, final BlockPos facingPos) {
        if(!facing.getAxis().isVertical()) {
            return stateIn;
        }
        if(!this.canSurvive(stateIn, worldIn, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        BlockState adjacentState = worldIn.getBlockState(currentPos.above());
        final int size = adjacentState.getBlock() == this
                ? Math.min(adjacentState.getValue(CypressBlock.SIZE) + 1, 5)
                : 1;
        if(size < 3) {
            return this.defaultBlockState().setValue(CypressBlock.SIZE, size);
        }
        adjacentState = worldIn.getBlockState(currentPos.below());
        return this.defaultBlockState().setValue(CypressBlock.SIZE, adjacentState.getBlock() == this ? size : 0);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(final BlockState stateIn, final Level worldIn, final BlockPos pos, final RandomSource rand) {
        if(worldIn.isRainingAt(pos.above()) && rand.nextInt(15) == 1) {
            final BlockPos posDown = pos.below();
            final BlockState stateDown = worldIn.getBlockState(posDown);
            if(!stateDown.canOcclude() || !stateDown.isFaceSturdy(worldIn, posDown, Direction.UP)) {
                final double x = pos.getX() + rand.nextFloat();
                final double y = pos.getY() - 0.05D;
                final double z = pos.getZ() + rand.nextFloat();
                worldIn.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public void appendHoverText(final ItemStack stack, @Nullable final BlockGetter worldIn,
                                final List<Component> tooltip, final TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        DoTBUtils.addTooltip(tooltip, DoTBUtils.TOOLTIP_COLUMN);
    }
}