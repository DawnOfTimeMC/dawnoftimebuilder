package org.dawnoftimebuilder.block.roman;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.block.templates.BlockDoTB;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;

import javax.annotation.Nullable;
import java.util.Random;

import static net.minecraft.block.Blocks.OAK_LEAVES;

public class CypressLeavesBlock extends BlockDoTB {

    private static final IntegerProperty SIZE = DoTBBlockStateProperties.SIZE_0_4;
    private static final VoxelShape VS_0 = makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 8.0D, 10.0D);
    private static final VoxelShape VS_1 = makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    private static final VoxelShape VS_2 = makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public CypressLeavesBlock() {
        super(Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).sound(SoundType.PLANT));
        this.setDefaultState(this.getStateContainer().getBaseState().with(SIZE, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SIZE);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(SIZE)) {
            default:
            case 0:
                return VS_0;
            case 1:
                return VS_1;
            case 2:
            case 3:
                return VS_2;
            case 4:
                return VoxelShapes.fullCube();
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState stateUp = context.getWorld().getBlockState(context.getPos().up());
        return this.getDefaultState().with(SIZE, (stateUp.getBlock() == this) ? Math.min(stateUp.get(SIZE) + 1, 4) : 0);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(facing == Direction.UP){
            if(facingState.getBlock() == this) return stateIn.with(SIZE, Math.min(facingState.get(SIZE) + 1, 4));
            else return stateIn.with(SIZE, 0);
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
        if (worldIn.isRainingAt(pos.up())) {
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
}
