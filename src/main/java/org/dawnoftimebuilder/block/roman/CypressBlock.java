package org.dawnoftimebuilder.block.roman;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.block.IBlockGeneration;
import org.dawnoftimebuilder.block.ICustomBlockItem;
import org.dawnoftimebuilder.block.templates.BlockDoTB;
import org.dawnoftimebuilder.item.templates.PotAndBlockItem;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.util.DoTBBlockUtils.HIGHEST_Y;
import static org.dawnoftimebuilder.util.DoTBBlockUtils.TOOLTIP_COLUMN;

public class CypressBlock extends BlockDoTB implements IBlockGeneration, ICustomBlockItem {

    private static final IntegerProperty SIZE = DoTBBlockStateProperties.SIZE_0_5;
    private static final VoxelShape VS_0 = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    private static final VoxelShape VS_1 = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 8.0D, 10.0D);
    private static final VoxelShape VS_2 = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    private static final VoxelShape VS_3_4 = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public CypressBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(SIZE, 1));
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return canSupportCenter(worldIn, pos.below(), Direction.UP) || worldIn.getBlockState(pos.below()).getBlock() == this;
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack heldItemStack = player.getItemInHand(handIn);
        if(player.isCrouching()) {
            //We remove the highest CypressBlock
            BlockPos topPos = this.getHighestCypressPos(worldIn, pos);
            if(topPos != pos){
                if(!worldIn.isClientSide()) {
                    worldIn.setBlock(topPos, Blocks.AIR.defaultBlockState(), 35);
                    if (!player.isCreative()) {
                        Block.dropResources(state, worldIn, pos, null, player, heldItemStack);
                    }
                }
                return ActionResultType.SUCCESS;
            }
        }else{
            if(!heldItemStack.isEmpty() && heldItemStack.getItem() == this.asItem()){
                //We put a CypressBlock on top of the cypress
                BlockPos topPos = this.getHighestCypressPos(worldIn, pos).above();
                if(topPos.getY() <= HIGHEST_Y){
                    if(!worldIn.isClientSide() && worldIn.getBlockState(topPos).isAir(worldIn, topPos)) {
                        worldIn.setBlock(topPos, this.defaultBlockState(), 11);
                        if(!player.isCreative()) {
                            heldItemStack.shrink(1);
                        }
                    }
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    private BlockPos getHighestCypressPos(World worldIn, BlockPos pos){
        int yOffset;
        for(yOffset = 0; yOffset + pos.getY() <= HIGHEST_Y; yOffset++){
            if(worldIn.getBlockState(pos.above(yOffset)).getBlock() != this) break;
        }
        return pos.above(yOffset - 1);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SIZE);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.getValue(SIZE)) {
            case 0:
                return VS_0;
            default:
            case 1:
                return VS_1;
            case 2:
                return VS_2;
            case 3:
            case 4:
                return VS_3_4;
            case 5:
                return VoxelShapes.block();
        }
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState p_230335_1_, IBlockReader p_230335_2_, BlockPos p_230335_3_) {
        return VoxelShapes.empty();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState adjacentState = context.getLevel().getBlockState(context.getClickedPos().above());
        int size = (adjacentState.getBlock() == this) ? Math.min(adjacentState.getValue(SIZE) + 1, 5) : 1;
        if(size < 3) return this.defaultBlockState().setValue(SIZE, size);
        else {
            adjacentState = context.getLevel().getBlockState(context.getClickedPos().below());
            return this.defaultBlockState().setValue(SIZE, (adjacentState.getBlock() == this) ? size : 0);
        }
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(facing.getAxis().isVertical()){
            if(!canSurvive(stateIn, worldIn, currentPos)) return Blocks.AIR.defaultBlockState();
            BlockState adjacentState = worldIn.getBlockState(currentPos.above());
            int size = (adjacentState.getBlock() == this) ? Math.min(adjacentState.getValue(SIZE) + 1, 5) : 1;
            if(size < 3) return this.defaultBlockState().setValue(SIZE, size);
            else {
                adjacentState = worldIn.getBlockState(currentPos.below());
                return this.defaultBlockState().setValue(SIZE, (adjacentState.getBlock() == this) ? size : 0);
            }
        }else return stateIn;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (worldIn.isRainingAt(pos.above())) {
            if (rand.nextInt(15) == 1) {
                BlockPos posDown = pos.below();
                BlockState stateDown = worldIn.getBlockState(posDown);
                if (!stateDown.canOcclude() || !stateDown.isFaceSturdy(worldIn, posDown, Direction.UP)) {
                    double x = pos.getX() + rand.nextFloat();
                    double y = pos.getY() - 0.05D;
                    double z = pos.getZ() + rand.nextFloat();
                    worldIn.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        DoTBBlockUtils.addTooltip(tooltip, TOOLTIP_COLUMN);
    }

    @Override
    public void generateOnPos(IWorld world, BlockPos pos, BlockState state, Random random) {
        int maxSize = 2 + random.nextInt(5);
        for(int i = 0; i < maxSize; i++){
            if(world.getBlockState(pos.above(i)).getMaterial() != Material.AIR){
                return;
            }
        }
        world.setBlock(pos, state.setValue(SIZE, 0), 2);
        int size = 1;
        for (int i = maxSize; i > 0; i--){
            world.setBlock(pos.above(i), state.setValue(SIZE, size), 2);
            if(size < 5) size++;
        }
    }

    @Nullable
    @Override
    public Item getCustomBlockItem() {
        return new PotAndBlockItem(this, new Item.Properties().tab(DOTB_TAB));
    }
}
