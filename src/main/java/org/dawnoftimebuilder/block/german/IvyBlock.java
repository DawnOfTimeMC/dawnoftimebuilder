package org.dawnoftimebuilder.block.german;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import org.dawnoftimebuilder.DoTBConfig;
import org.dawnoftimebuilder.block.ICustomBlockItem;
import org.dawnoftimebuilder.block.templates.BlockDoTB;
import org.dawnoftimebuilder.item.templates.PotAndBlockItem;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Random;

import static net.minecraft.tags.BlockTags.SAND;
import static net.minecraftforge.common.Tags.Blocks.DIRT;
import static net.minecraftforge.common.Tags.Blocks.GRAVEL;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;

public class IvyBlock extends BlockDoTB implements ICustomBlockItem {

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    private static final IntegerProperty AGE = BlockStateProperties.AGE_2;
    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
    private static final VoxelShape[] SHAPES = makeShapes();

    public IvyBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AGE, 0).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(PERSISTENT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH, EAST, SOUTH, WEST, AGE, PERSISTENT);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        int index = 0;
        if(state.getValue(SOUTH)) index += 1;
        if(state.getValue(WEST)) index += 2;
        if(state.getValue(NORTH)) index += 4;
        if(state.getValue(EAST)) index += 8;
        if(index > 14) index = 0;
        return SHAPES[index];
    }

    /**
     * @return Stores VoxelShape with index following binary system : <p/>
     * 8+4+2+1 with 1 = SOUTH, 2 = WEST, 4 = NORTH, 8 = EAST
     * 0 : SWNE <p/>
     * 1 : S <p/>
     * 2 : W <p/>
     * 3 : SW <p/>
     * 4 : N <p/>
     * 5 : SN <p/>
     * 6 : WN <p/>
     * 7 : SWN <p/>
     * 8 : E <p/>
     * 9 : SE <p/>
     * 10 : WE <p/>
     * 11 : SWE <p/>
     * 12 : NE <p/>
     * 13 : SNE <p/>
     * 14 : WNE <p/>
     */
    private static VoxelShape[] makeShapes() {
        VoxelShape vs_south = Block.box(0.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vs_west = Block.box(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 16.0D);
        VoxelShape vs_north = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
        VoxelShape vs_east = Block.box(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vs_sw = VoxelShapes.or(vs_south, vs_west);
        VoxelShape vs_wn = VoxelShapes.or(vs_west, vs_north);
        VoxelShape vs_ne = VoxelShapes.or(vs_north, vs_east);
        VoxelShape vs_se = VoxelShapes.or(vs_east, vs_south);
        return new VoxelShape[]{
                VoxelShapes.or(vs_sw, vs_ne),
                vs_south,
                vs_west,
                vs_sw,
                vs_north,
                VoxelShapes.or(vs_south, vs_north),
                vs_wn,
                VoxelShapes.or(vs_sw, vs_north),
                vs_east,
                vs_se,
                VoxelShapes.or(vs_west, vs_east),
                VoxelShapes.or(vs_sw, vs_east),
                vs_ne,
                VoxelShapes.or(vs_south, vs_ne),
                VoxelShapes.or(vs_wn, vs_east),
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        if (state.getBlock() != this){
            state = this.defaultBlockState();
        }
        switch(context.getHorizontalDirection()){
            default:
            case SOUTH:
                return state.setValue(SOUTH, true);
            case WEST:
                return state.setValue(WEST, true);
            case NORTH:
                return state.setValue(NORTH, true);
            case EAST:
                return state.setValue(EAST, true);
        }
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockItemUseContext useContext) {
        ItemStack itemstack = useContext.getItemInHand();
        if(useContext.getPlayer() != null && useContext.getPlayer().isCrouching()) return false;
        if(itemstack.getItem() == this.asItem()) {
            Direction newDirection = useContext.getHorizontalDirection();
            switch(newDirection){
                default:
                case SOUTH:
                    return !state.getValue(SOUTH);
                case WEST:
                    return !state.getValue(WEST);
                case NORTH:
                    return !state.getValue(NORTH);
                case EAST:
                    return !state.getValue(EAST);
            }
        }
        return false;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(PERSISTENT);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!worldIn.isClientSide()) {
            if (!worldIn.isAreaLoaded(pos, 2)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light

            if (worldIn.getRawBrightness(pos, 0) >= 8) {
                int age = state.getValue(AGE);
                if (age < 2) { //Probability "can grow"
                    if(ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt(DoTBConfig.CLIMBING_PLANT_GROWTH_CHANCE.get()) == 0)){
                        worldIn.setBlock(pos, state.setValue(AGE, age + 1), 2);
                        ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                    }
                    return;
                }
                if(random.nextInt(DoTBConfig.CLIMBING_PLANT_SPREAD_CHANCE.get()) == 0) {
                    // The Ivy will spread
                    ArrayList<Direction> list = getCurrentDirections(state);
                    int faceIndex = list.size();
                    if(faceIndex == 0) return;
                    faceIndex = random.nextInt(faceIndex);
                    Direction face = list.get(faceIndex);
                    // Now we want to decide in which direction it will spread.
                    faceIndex = random.nextInt(4);
                    BlockPos studiedPos;
                    if(faceIndex < 2) {
                        // 0 : spread on the left
                        // 1 : spread on the right
                        Direction rotFace = faceIndex == 0 ? face.getCounterClockWise() : face.getClockWise();
                        if (hasFullFace(worldIn, pos, rotFace)) {
                            if (!state.getValue(getProperty(rotFace)))
                                worldIn.setBlock(pos, state.setValue(getProperty(rotFace), true), 2);
                        } else {
                            studiedPos = pos.relative(rotFace);
                            if (worldIn.getBlockState(studiedPos).isAir(worldIn, studiedPos)) {
                                if (hasFullFace(worldIn, studiedPos, face)) {
                                    worldIn.setBlock(studiedPos, this.defaultBlockState().setValue(getProperty(face), true), 2);
                                } else {
                                    studiedPos = studiedPos.relative(face);
                                    if (worldIn.getBlockState(studiedPos).isAir(worldIn, studiedPos)) {
                                        rotFace = faceIndex == 0 ? face.getClockWise() : face.getCounterClockWise();
                                        if (hasFullFace(worldIn, studiedPos, rotFace)) {
                                            worldIn.setBlock(studiedPos, this.defaultBlockState().setValue(getProperty(rotFace), true), 2);
                                        }
                                    }
                                }
                            }
                        }
                    }else{
                        // 2 : spread above
                        // 3 : spread below
                        studiedPos = faceIndex == 2 ? pos.above() : pos.below();
                        if(worldIn.getBlockState(studiedPos).isAir(worldIn, studiedPos)){
                            if(hasFullFace(worldIn, studiedPos, face)){
                                worldIn.setBlock(studiedPos, this.defaultBlockState().setValue(getProperty(face), true), 2);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(facing.getAxis().isHorizontal()){
            if(facing == Direction.NORTH && stateIn.getValue(NORTH) && !hasFullFace(facingState, worldIn, facingPos, facing)){
                stateIn = stateIn.setValue(NORTH, false);
            }
            if(facing == Direction.EAST && stateIn.getValue(EAST) && !hasFullFace(facingState, worldIn, facingPos, facing)){
                stateIn = stateIn.setValue(EAST, false);
            }
            if(facing == Direction.SOUTH && stateIn.getValue(SOUTH) && !hasFullFace(facingState, worldIn, facingPos, facing)){
                stateIn = stateIn.setValue(SOUTH, false);
            }
            if(facing == Direction.WEST && stateIn.getValue(WEST) && !hasFullFace(facingState, worldIn, facingPos, facing)){
                stateIn = stateIn.setValue(WEST, false);
            }
        }
        if(getCurrentDirections(stateIn).isEmpty())
            return Blocks.AIR.defaultBlockState();
        return stateIn;
    }

    private static ArrayList<Direction> getCurrentDirections(BlockState state){
        ArrayList<Direction> list = new ArrayList<>();
        if(state.getValue(NORTH)) list.add(Direction.NORTH);
        if(state.getValue(EAST)) list.add(Direction.EAST);
        if(state.getValue(SOUTH)) list.add(Direction.SOUTH);
        if(state.getValue(WEST)) list.add(Direction.WEST);
        return list;
    }

    private static BooleanProperty getProperty(Direction direction){
        switch (direction){
            default:
            case NORTH:
                return NORTH;
            case SOUTH:
                return SOUTH;
            case WEST:
                return WEST;
            case EAST:
                return EAST;
        }
    }

    private static boolean hasFullFace(IWorldReader world, BlockPos currentPos, Direction face){
        currentPos = currentPos.relative(face);
        return hasFullFace(world.getBlockState(currentPos), world, currentPos, face);
    }

    private static boolean hasFullFace(BlockState state, IWorldReader world, BlockPos pos, Direction face){
        Block block = state.getBlock();
        if(block.is(DIRT) || block.is(SAND) || block.is(GRAVEL)) return false;
        return Block.isFaceFull(state.getCollisionShape(world, pos), face.getOpposite());
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!state.getValue(PERSISTENT)){
            if(DoTBBlockUtils.useLighter(worldIn, pos, player, handIn)){
                Random rand = new Random();
                for(int i = 0; i < 5; i++){
                    worldIn.addParticle(ParticleTypes.SMOKE, (double)pos.getX() + rand.nextDouble(), (double)pos.getY() + 0.5D + rand.nextDouble() / 2, (double)pos.getZ() + rand.nextDouble(), 0.0D, 0.07D, 0.0D);
                }
                worldIn.setBlock(pos, state.setValue(PERSISTENT, true), 10);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Nullable
    @Override
    public Item getCustomBlockItem() {
        return new PotAndBlockItem(this, new Item.Properties().tab(DOTB_TAB));
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }
}
