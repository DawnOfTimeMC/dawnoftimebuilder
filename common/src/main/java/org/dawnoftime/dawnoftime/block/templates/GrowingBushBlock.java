package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.dawnoftime.dawnoftime.util.Utils;

import java.util.List;

public class GrowingBushBlock extends SoilCropsBlock {
    public final VoxelShape[] SHAPES;
    public final int cutAge;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;
    private static final BooleanProperty CUT = BlockStatePropertiesAA.CUT;

    public GrowingBushBlock(PlantType plantType, int cutAge) {
        super(plantType);
        this.cutAge = cutAge;
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(CUT, false).setValue(PERSISTENT, false));
        this.SHAPES = this.makeShapes();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, PERSISTENT, CUT);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        int index = state.getValue(AGE);
        return state.getValue(CUT) ? SHAPES[5] : SHAPES[index];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return (state.getValue(AGE) == 0) ? Shapes.empty() : super.getShape(state, worldIn, pos, context);
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * 0 : Stage 0 <p/>
     * 1 : Stage 1 <p/>
     * 2 : Stage 2 <p/>
     * 3 : Stage 3 <p/>
     * 4 : Stage 4 <p/>
     * 5 : Stage 5 or cut <p/>
     */
    public VoxelShape[] makeShapes() {
        return new VoxelShape[] {
                Block.box(5.0D, 0.0D, 5.0D, 11.0D, 5.5D, 11.0D),
                Block.box(4.0D, 0.0D, 4.0D, 12.0D, 9.0D, 12.0D),
                Block.box(3.0D, 0.0D, 3.0D, 13.0D, 10.5D, 13.0D),
                Block.box(2.0D, 0.0D, 2.0D, 14.0D, 11.0D, 14.0D),
                Block.box(1.5D, 0.0D, 1.5D, 14.5D, 12.0D, 14.5D),
                Block.box(1.0D, 0.0D, 1.0D, 15.0D, 12.0D, 15.0D),
        };
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    public BooleanProperty getCutProperty() {
        return CUT;
    }

    @Override
    public int getMaxAge() {
        return 5;
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player playerIn, InteractionHand hand, BlockHitResult ray) {
        if(super.use(state, worldIn, pos, playerIn, hand, ray) == InteractionResult.SUCCESS)
            return InteractionResult.SUCCESS;
        if(this.isMaxAge(state) && !playerIn.isCreative()) {
            if(!worldIn.isClientSide()) {
                ItemStack itemStackHand = playerIn.getItemInHand(hand);
                boolean holdShears = itemStackHand.is(Items.SHEARS);
                if(holdShears)
                    itemStackHand.hurtAndBreak(1, playerIn, (p) -> p.broadcastBreakEvent(hand));

                ResourceLocation resourceLocation = this.builtInRegistryHolder().key().location();
                this.harvestWithoutBreaking(state, worldIn, pos, itemStackHand, resourceLocation.getPath(), holdShears ? 1.5F : 1.0F);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public void harvestWithoutBreaking(BlockState state, Level worldIn, BlockPos pos, ItemStack itemStackHand, String blockName, float dropMultiplier) {
        List<ItemStack> drops = Utils.getLootList((ServerLevel) worldIn, state, itemStackHand, blockName);
        Utils.dropLootFromList(worldIn, pos, drops, dropMultiplier);

        worldIn.playSound(null, pos, SoundEvents.GRASS_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
        worldIn.setBlock(pos, state.setValue(AGE, this.cutAge).setValue(CUT, true), 2);
    }
}
