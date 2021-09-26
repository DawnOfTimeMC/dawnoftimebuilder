package org.dawnoftimebuilder.block.roman;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
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
import org.dawnoftimebuilder.block.templates.BlockDoTB;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static net.minecraft.block.Blocks.OAK_LEAVES;
import static org.dawnoftimebuilder.util.DoTBBlockUtils.HIGHEST_Y;
import static org.dawnoftimebuilder.util.DoTBBlockUtils.TOOLTIP_COLUMN;

public class CypressBlock extends BlockDoTB {

    private static final IntegerProperty SIZE = DoTBBlockStateProperties.SIZE_0_5;
    private static final VoxelShape VS_0 = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    private static final VoxelShape VS_1 = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 8.0D, 10.0D);
    private static final VoxelShape VS_2 = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    private static final VoxelShape VS_3_4 = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public CypressBlock(Material materialIn, float hardness, float resistance, SoundType soundType) {
        super(Properties.of(materialIn).strength(hardness, resistance).sound(soundType));
        this.setDefaultState(this.getStateContainer().getBaseState().with(SIZE, 1));
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return hasEnoughSolidSide(worldIn, pos.down(), Direction.UP) || worldIn.getBlockState(pos.down()).getBlock() == this;
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack heldItemStack = player.getItemInHand(handIn);
        if(player.isCrouching()) {
            //We remove the highest CypressBlock
            BlockPos topPos = this.getHighestCypressPos(worldIn, pos);
            if(topPos != pos){
                if(!worldIn.isClientSide()) {
                    worldIn.setBlockState(topPos, Blocks.AIR.defaultBlockState(), 35);
                    if (!player.isCreative()) {
                        Block.spawnDrops(state, worldIn, pos, null, player, heldItemStack);
                    }
                }
                return true;
            }
        }else{
            if(!heldItemStack.isEmpty() && heldItemStack.getItem() == this.asItem()){
                //We put a CypressBlock on top of the cypress
                BlockPos topPos = this.getHighestCypressPos(worldIn, pos).above();
                if(topPos.getY() <= HIGHEST_Y){
                    if(!worldIn.isClientSide()) {
                        worldIn.setBlockState(topPos, this.defaultBlockState(), 11);
                        if(!player.isCreative()) {
                            heldItemStack.shrink(1);
                        }
                    }
                    return true;
                }
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    private BlockPos getHighestCypressPos(World worldIn, BlockPos pos){
        int yOffset;
        for(yOffset = 0; yOffset + pos.getY() <= HIGHEST_Y; yOffset++){
            if(worldIn.getBlockState(pos.above(yOffset)).getBlock() != this) break;
        }
        return pos.above(yOffset - 1);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SIZE);
    }

    public static BlockState setSize(BlockState state, int size){
        return state.with(SIZE, size);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(SIZE)) {
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
                return VoxelShapes.fullCube();
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState adjacentState = context.getLevel().getBlockState(context.getPos().above());
        int size = (adjacentState.getBlock() == this) ? Math.min(adjacentState.get(SIZE) + 1, 5) : 1;
        if(size < 3) return this.defaultBlockState().with(SIZE, size);
        else {
            adjacentState = context.getLevel().getBlockState(context.getPos().down());
            return this.defaultBlockState().with(SIZE, (adjacentState.getBlock() == this) ? size : 0);
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(facing.getAxis().isVertical()){
            if(!isValidPosition(stateIn, worldIn, currentPos)) return Blocks.AIR.defaultBlockState();
            BlockState adjacentState = worldIn.getBlockState(currentPos.above());
            int size = (adjacentState.getBlock() == this) ? Math.min(adjacentState.get(SIZE) + 1, 5) : 1;
            if(size < 3) return this.defaultBlockState().with(SIZE, size);
            else {
                adjacentState = worldIn.getBlockState(currentPos.down());
                return this.defaultBlockState().with(SIZE, (adjacentState.getBlock() == this) ? size : 0);
            }
        }else return stateIn;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        //OAK_LEAVES RenderLayer uses renderTranslucent from parameters.
        return OAK_LEAVES.getRenderLayer();
    }

    @Override
    public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (worldIn.isRainingAt(pos.above())) {
            if (rand.nextInt(15) == 1) {
                BlockPos posDown = pos.down();
                BlockState stateDown = worldIn.getBlockState(posDown);
                if (!stateDown.isSolid() || !stateDown.func_224755_d(worldIn, posDown, Direction.UP)) {
                    double x = pos.getX() + rand.nextFloat();
                    double y = pos.getY() - 0.05D;
                    double z = pos.getZ() + rand.nextFloat();
                    worldIn.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public boolean isSolid(BlockState state) {
        return false;
    }

    @Override
    public boolean causesSuffocation(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        DoTBBlockUtils.addTooltip(tooltip, TOOLTIP_COLUMN);
    }
}
