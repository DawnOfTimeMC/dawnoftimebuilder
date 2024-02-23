package org.dawnoftimebuilder.block.general;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.PlateBlock;
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nullable;
import java.util.List;

public class IronFenceBlock extends PlateBlock {
    private static final BooleanProperty UP = BlockStateProperties.UP;
    private static final VoxelShape[] SHAPES_UP = DoTBUtils.GenerateHorizontalShapes(makeShapes(true));
    private static final VoxelShape[] SHAPES_FULL = DoTBUtils.GenerateHorizontalShapes(makeShapes(false));

    public IronFenceBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(PlateBlock.FACING, Direction.NORTH)
                .setValue(PlateBlock.SHAPE, StairsShape.STRAIGHT)
                .setValue(UP, true)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        int index = (state.getValue(FACING).get2DDataValue() + 2) % 4;
        index *= 3;
        switch(state.getValue(SHAPE)) {
            default:
            case OUTER_LEFT:
                break;
            case OUTER_RIGHT:
                index += 3;
                break;
            case STRAIGHT:
                index += 1;
                break;
            case INNER_LEFT:
                index += 2;
                break;
            case INNER_RIGHT:
                index += 5;
                break;
        }
        index %= 12;
        return state.getValue(UP) ? SHAPES_UP[index] : SHAPES_FULL[index];
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * 0 : NW Outer <p/>
     * 1 : N Default <p/>
     * 2 : NW Inner <p/>
     * 3 : NE Outer <p/>
     * 4 : N Default <p/>
     * 5 : NE Inner <p/>
     * 6 : SE Outer <p/>
     * 7 : S Default <p/>
     * 8 : SE Inner <p/>
     * 9 : SW Outer <p/>
     * 10 : S Default <p/>
     * 11 : SW Inner <p/>
     */
    private static VoxelShape[] makeShapes(boolean up) {
        int size_flat = up ? 8 : 16;
        int size_corner = up ? 10 : 16;
        VoxelShape vs_north_flat = Block.box(0.0D, 0.0D, 0.0D, 16.0D, size_flat, 2.5D);
        VoxelShape vs_east_flat = Block.box(13.5D, 0.0D, 0.0D, 16.0D, size_flat, 16.0D);
        VoxelShape vs_south_flat = Block.box(0.0D, 0.0D, 13.5D, 16.0D, size_flat, 16.0D);
        VoxelShape vs_west_flat = Block.box(0.0D, 0.0D, 0.0D, 2.5D, size_flat, 16.0D);
        VoxelShape vs_nw_corner = Block.box(0.0D, 0.0D, 0.0D, 3.0D, size_corner, 3.0D);
        VoxelShape vs_ne_corner = Block.box(13.0D, 0.0D, 0.0D, 16.0D, size_corner, 3.0D);
        VoxelShape vs_se_corner = Block.box(13.0D, 0.0D, 13.0D, 16.0D, size_corner, 16.0D);
        VoxelShape vs_sw_corner = Block.box(0.0D, 0.0D, 13.0D, 3.0D, size_corner, 16.0D);
        return new VoxelShape[] {
                vs_nw_corner,
                vs_north_flat,
                Shapes.or(vs_north_flat, vs_west_flat, vs_nw_corner),
                vs_ne_corner,
                vs_east_flat,
                Shapes.or(vs_east_flat, vs_north_flat, vs_ne_corner),
                vs_se_corner,
                vs_south_flat,
                Shapes.or(vs_south_flat, vs_east_flat, vs_se_corner),
                vs_sw_corner,
                vs_west_flat,
                Shapes.or(vs_west_flat, vs_south_flat, vs_sw_corner),
        };
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(UP);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(UP, !context.getLevel().getBlockState(context.getClickedPos().above()).is(this));
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(facing == Direction.UP) {
            stateIn = stateIn.setValue(UP, !facingState.is(this));
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public InteractionResult use(final BlockState state, final Level worldIn, final BlockPos pos, final Player player, final InteractionHand handIn, final BlockHitResult hit) {
        final ItemStack heldItemStack = player.getItemInHand(handIn);
        if(player.isCrouching()) {
            //We remove the highest Block
            if(state.getValue(UP)) {
                return super.use(state, worldIn, pos, player, handIn, hit);
            }
            final BlockPos topPos = this.getHighestColumnPos(worldIn, pos);
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
            //We put a ColumnBlock on top of the column
            final BlockPos topPos = this.getHighestColumnPos(worldIn, pos).above();
            if(topPos.getY() <= DoTBUtils.HIGHEST_Y) {
                if(!worldIn.isClientSide() && worldIn.getBlockState(topPos).isAir()) {
                    worldIn.setBlock(topPos, state, 11);
                    if(!player.isCreative()) {
                        heldItemStack.shrink(1);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    private BlockPos getHighestColumnPos(final Level worldIn, final BlockPos pos) {
        int yOffset;
        for(yOffset = 0; yOffset + pos.getY() <= DoTBUtils.HIGHEST_Y; yOffset++) {
            if(worldIn.getBlockState(pos.above(yOffset)).getBlock() != this) {
                break;
            }
        }
        return pos.above(yOffset - 1);
    }

    @Override
    public void appendHoverText(final ItemStack stack, @Nullable final BlockGetter worldIn, final List<Component> tooltip, final TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        DoTBUtils.addTooltip(tooltip, DoTBUtils.TOOLTIP_COLUMN);
    }
}
