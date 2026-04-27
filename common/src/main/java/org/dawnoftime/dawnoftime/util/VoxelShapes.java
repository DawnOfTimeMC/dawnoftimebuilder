package org.dawnoftime.dawnoftime.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import static org.dawnoftime.dawnoftime.util.VoxelShapesBuilder.*;
import static org.dawnoftime.dawnoftime.util.VoxelShapesBuilder.makeConnectedFramedWindowShapes;

public class VoxelShapes {

    // Test shape
    // The Y are decimals defined so that it still accept 0.01 or 0.5, values used to avoid overlapping textures or in fancy models.
    public static final VoxelShape SHAPE_DOWN_16x16 = Block.box(0.0F, 0.05F, 0.0F, 16.0F, 0.1F, 16.0F);
    public static final VoxelShape SHAPE_DOWN_4x4 = Block.box(6.0F, 0.05F, 6.0F, 10.0F, 0.1F, 10.0F);

    // Default shapes
    public static final VoxelShape[] FULL_SHAPE = new VoxelShape[]{Shapes.block()};

    // Custom shapes
    public static final VoxelShape[] BEAM_SHAPES = makeBeamShapes();
    public static final VoxelShape[] BIG_FLOWER_POT_SHAPES = new VoxelShape[]{Shapes.or(
            Block.box(-0.5F, 12.0F, -0.5F, 16.5F, 17.0F, 16.5F),
            Block.box(0.5F, 0.0F, 0.5F, 15.5F, 12.0F, 15.5F))};
    public static final VoxelShape[] BIRCH_FOOTSTOOL_SHAPES = new VoxelShape[]{
            Shapes.or(Block.box(4.0F, 0.0F, 2.0F, 12.0F, 3.0F, 14.0F),
                    Block.box(2.0F, 3.0F, 0.0F, 14.0F, 9.0F, 16.0F)),
            Shapes.or(Block.box(2.0F, 0.0F, 4.0F, 14.0F, 3.0F, 12.0F),
                    Block.box(0.0F, 3.0F, 2.0F, 16.0F, 9.0F, 14.0F))};
    public static final VoxelShape[] CANDLESTICK_SHAPES = generateHorizontalShapes(
            new VoxelShape[]{Block.box(4.0D, 1.0D, 0.0D, 12.0D, 15.0D, 14.0D)},
            Block.box(5.0D, 0.0D, 5.0D, 11.0D, 15.0D, 11.0D));
    public static final VoxelShape[] CARPET_SHAPES = new VoxelShape[]{Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D)};
    public static final VoxelShape[] CAST_IRON_TEAPOT_SHAPES = new VoxelShape[]{Block.box(4.8D, 0.0D, 4.8D, 11.2D, 6.4D, 11.2D)};
    public static final VoxelShape[] CAST_IRON_TEACUP_SHAPES = new VoxelShape[]{Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D)};
    public static final VoxelShape[] CHARRED_SPRUCE_FANCY_RAILING_SHAPES = makeCharredSpruceFancyRailingShapes();
    public static final VoxelShape[] CHARRED_SPRUCE_SHUTTERS_SHAPES = makeCharredSpruceShuttersShapes();
    public static final VoxelShape[] CHARRED_SPRUCE_TALL_SHUTTERS_SHAPES = makeCharredSpruceTallShuttersShapes();
    public static final VoxelShape[] EDGE_SHAPES = makeEdgeShapes();
    public static final VoxelShape[] FEATHERED_SERPENT_SCULPTURE_SHAPES = generateHorizontalShapes(new VoxelShape[]{
            Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 14.0D)});
    public static final VoxelShape[] FIREPLACE_SHAPES = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 2.0D, 16.0D, 14.0D, 14.0D),
            Block.box(0.0D, 0.0D, 2.0D, 16.0D, 5.0D, 14.0D),
            Block.box(2.0D, 0.0D, 0.0D, 14.0D, 14.0D, 16.0D),
            Block.box(2.0D, 0.0D, 0.0D, 14.0D, 5.0D, 16.0D)};
    public static final VoxelShape[] FLOWER_POT_SHAPE = new VoxelShape[]{Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D)};
    public static final VoxelShape[] GREEN_SCULPTED_PLASTERED_STONE_FRIEZE_SHAPES = makeGreenSculptedPlasteredStoneFriezeShapes();
    public static final VoxelShape[] IRON_COLUMN_SHAPES = makeIronColumnShapes();
    public static final VoxelShape[] IRON_FANCY_LANTERN_SHAPES = makeIronFancyLanternShapes();
    public static final VoxelShape[] IRON_FENCE_SHAPES = makeIronFenceShapes();
    public static final VoxelShape[] LATTICE_FOUR_SIDES = VoxelShapesBuilder.generateFourSidesShapes(Block.box(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D));
    public static final VoxelShape[] FAUCET_FOUR_SIDES = VoxelShapesBuilder.generateFourSidesShapes(Block.box(6, 10, 10, 10, 14, 16));

    // === WaterJet base boxes (from your old code) ===
    private static final VoxelShape J_UP   = Block.box(6, 10, 6, 10, 16, 10);
    private static final VoxelShape J_DOWN = Block.box(6,  0, 6, 10,  6, 10);

    private static final VoxelShape J_NORTH_TOP    = Block.box(6, 10, 0, 10, 14, 6);
    private static final VoxelShape J_NORTH_BOTTOM = Block.box(6,  2, 0, 10,  6, 6);
    private static final VoxelShape J_SOUTH_TOP    = Block.box(6, 10, 10, 10, 14, 16);
    private static final VoxelShape J_SOUTH_BOTTOM = Block.box(6,  2, 10, 10,  6, 16);
    private static final VoxelShape J_EAST_TOP     = Block.box(10, 10, 6, 16, 14, 10);
    private static final VoxelShape J_EAST_BOTTOM  = Block.box(10,  2, 6, 16,  6, 10);
    private static final VoxelShape J_WEST_TOP     = Block.box(0,  10, 6,  6, 14, 10);
    private static final VoxelShape J_WEST_BOTTOM  = Block.box(0,   2, 6,  6,  6, 10);

    /** All 324 precomposed WaterJet shapes. */
    public static final VoxelShape[] WATERJET_SHAPES =
            VoxelShapesBuilder.generateWaterJetShapes(
                    J_UP, J_DOWN,
                    J_SOUTH_TOP, J_SOUTH_BOTTOM, // SOUTH
                    J_WEST_TOP,  J_WEST_BOTTOM,  // WEST
                    J_NORTH_TOP, J_NORTH_BOTTOM, // NORTH
                    J_EAST_TOP,  J_EAST_BOTTOM   // EAST
            );

    public static final VoxelShape[] LIMESTONE_CHIMNEY_SHAPES = makeLimestoneChimneyShapes();
    public static final VoxelShape[] LIMESTONE_GARGOYLE_SHAPES = generateHorizontalShapes(new VoxelShape[]{
            Block.box(4.0D, 7.0D, 0.0D, 12.0D, 14.0D, 16.0D)
    });
    public static final VoxelShape[] LIMESTONE_SIDED_COLUMN_SHAPES = makeLimestoneSidedColumnShapes();
    public static final VoxelShape[] MARBLE_BIG_FLOWER_POT_SHAPES = new VoxelShape[]{Shapes.or(
            Block.box(0.0F, 15.0F, 0.0F, 16.0F, 17.0F, 16.0F),
            Block.box(2.0F, 8.0F, 2.0F, 14.0F, 15.0F, 14.0F),
            Block.box(1.0F, 5.0F, 1.0F, 15.0F, 8.0F, 15.0F),
            Block.box(5.0F, 2.0F, 5.0F, 11.0F, 5.0F, 11.0F),
            Block.box(2.0F, 0.0F, 2.0F, 14.0F, 2.0F, 14.0F))};
    public static final VoxelShape[] MARBLE_COLUMN_SHAPES = makeMarbleColumnShapes();
    public static final VoxelShape[] MARBLE_SIDED_COLUMN_SHAPES = makeMarbleSidedColumnShapes();
    public static final VoxelShape[] MARBLE_STATUE_SHAPES = new VoxelShape[]{
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D),
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D)};
    public static final VoxelShape[] MORAQ_MOSAIC_COLUMN_SHAPES = makeMoraqMosaicColumnShapes();
    public static final VoxelShape[] MULTIBLOCK_FIREPLACE_SHAPES = makeMultiblockFireplaceShapes();
    public static final VoxelShape[] CONNECTED_FRAMED_WINDOW_SHAPES = makeConnectedFramedWindowShapes();
    public static final VoxelShape[] PAPER_LAMP_SHAPES = new VoxelShape[]{
            Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D),
            Block.box(4.0D, 0.0D, 4.0D, 12.0D, 13.0D, 12.0D)};
    public static final VoxelShape[] PAPER_LANTERN_SHAPES = new VoxelShape[]{Block.box(4.0D, 0.0D, 4.0D, 12.0D, 15.0D, 12.0D)};
    public static final VoxelShape[] PERGOLA_SHAPES = makePergolaShapes();
    public static final VoxelShape[] PLASTERED_STONE_COLUMN_SHAPES = makePlasteredStoneColumnShapes();
    public static final VoxelShape[] PLASTERED_STONE_CRESSET_SHAPES = new VoxelShape[]{Block.box(3.0D, 0.0D, 3.0D, 13.0D, 14.0D, 13.0D)};
    public static final VoxelShape[] PLASTERED_STONE_WINDOW_SHAPES = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D),
            Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D)};
    public static final VoxelShape[] PLATE_SHAPES = makePlateShapes();
    public static final VoxelShape[] POOL_SHAPES = makePoolShapes();
    public static final VoxelShape[] PORTCULLIS_SHAPES = new VoxelShape[]{
            Shapes.empty(),
            Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D),
            Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D)};
    public static final VoxelShape[] RED_SCULPTED_PLASTERED_STONE_FRIEZE_SHAPES = makeRedSculptedPlasteredStoneFriezeShapes();
    public static final VoxelShape[] REINFORCED_IRON_FENCE_SHAPES = makeReinforcedIronFenceShapes();
    public static final VoxelShape[] RELIEF_SHAPES = makeReliefShapes();
    public static final VoxelShape[] ROMAN_COUCH_SHAPES = generateHorizontalShapes(new VoxelShape[]{Shapes.or(
            Block.box(1.0D, 0.0D, 2.0D, 15.0D, 8.0D, 6.0D),
            Block.box(0.0D, 8.0D, 0.0D, 16.0D, 13.0D, 16.0D),
            Block.box(0.0D, 13.0D, 0.0D, 16.0D, 19.0D, 8.0D))});
    public static final VoxelShape[] SAKE_BOTTLE_SHAPES = new VoxelShape[]{Block.box(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D)};
    public static final VoxelShape[] SAKE_CUP_SHAPES = new VoxelShape[]{Block.box(6.0D, 0.0D, 6.0D, 10.0D, 3.0D, 10.0D)};
    public static final VoxelShape[] SANDSTONE_COLUMN_SHAPES = new VoxelShape[]{
            Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D),
            Shapes.or(
                    Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D),
                    Block.box(4.0D, 8.0D, 4.0D, 12.0D, 16.0D, 12.0D)),
            Shapes.or(
                    Block.box(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D),
                    Block.box(2.0D, 8.0D, 2.0D, 14.0D, 12.0D, 14.0D),
                    Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D))};

    public static final VoxelShape[] SANDSTONE_CRENELATION_SHAPES = makeSandstoneCrenelationShapes();
    public static final VoxelShape[] SANDSTONE_SIDED_COLUMN_SHAPES = makeSandstoneSidedColumnShapes();
    public static final VoxelShape[] SERPENT_SCULPTED_COLUMN_SHAPES = makeSerpentSculptedColumnShapes();
    public static final VoxelShape[] SIDED_WINDOW_SHAPES = generateHorizontalShapes(new VoxelShape[]{
            Block.box(0.0D, 0.0D, 10.0D, 16.0D, 16.0D, 16.0D)
    });
    public static final VoxelShape[] SMALL_POOL_COLLISION_SHAPES = makeSmallPoolCollisionShapes();
    public static final VoxelShape[] SMALL_POOL_SHAPES = makeSmallPoolShapes();
    public static final VoxelShape[] SMALL_TATAMI_FLOOR_SHAPES = new VoxelShape[]{Block.box(0.0D, 0.0D, 0.0D, 16.0D, 17.0D, 16.0D)};
    public static final VoxelShape[] SMALL_SHUTTER_SHAPES = makeSmallShutterShapes();
    public static final VoxelShape[] SMALL_TATAMI_MAT_SHAPES = makeSmallTatamiMatShapes();
    public static final VoxelShape[] SPRUCE_LEGLESS_CHAIR_SHAPES = generateHorizontalShapes(new VoxelShape[]{
            Shapes.or(
                    Block.box(2.0D, 0.0D, 0.0D, 14.0D, 3.0D, 16.0D),
                    Block.box(2.0D, 3.0D, 0.0D, 14.0D, 11.0D, 4.0D))});
    public static final VoxelShape[] WHITE_CUSHION_SHAPES = generateHorizontalShapes(new VoxelShape[]{
            Block.box(2.5D, 0.0D, 2.5D, 13.5D, 2.0D, 13.5D)});
    public static final VoxelShape[] SPRUCE_LOW_TABLE_SHAPES = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 2.0D, 16.0D, 8.0D, 14.0D),
            Block.box(2.0D, 0.0D, 0.0D, 14.0D, 8.0D, 16.0D)};

    public static final VoxelShape[] STONE_BRICKS_ARROWSLIT_SHAPES = makeStoneBricksArrowslitShapes();
    public static final VoxelShape[] STONE_BRICKS_CHIMNEY_SHAPES = makeStoneBricksChimneyShapes();
    public static final VoxelShape[] STONE_BRICKS_MACHICOLATION_SHAPES = makeStoneBricksMachicolationShapes();
    public static final VoxelShape[] STONE_LANTERN_SHAPES = makeStoneLanternShapes();
    public static final VoxelShape[] SUPPORT_BEAM_SHAPES = makeSupportBeamShapes();
    public static final VoxelShape[] SUPPORT_SLAB_SHAPES = makeSupportSlabShapes();
    public static final VoxelShape[] TATAMI_FLOOR_SHAPES = new VoxelShape[]{Block.box(0.0D, 0.0D, 0.0D, 16.0D, 17.0D, 16.0D)};
    public static final VoxelShape[] TATAMI_MAT_SHAPES = generateHorizontalShapes(
            new VoxelShape[]{
                    Block.box(0.0D, 0.0D, 8.5D, 16.0D, 7.0D, 15.5D),
                    Block.box(0.0D, 0.0D, 0.5D, 16.0D, 7.0D, 15.5D),
                    Block.box(0.0D, 0.0D, 0.5D, 16.0D, 14.0D, 15.5D)
            },
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D));
    public static final VoxelShape[] THIN_PLATE_SHAPES = makeThinPlateShapes();
    public static final VoxelShape[] WAXED_OAK_BALUSTER_SHAPES = makeWaxedOakBalusterShapes();
    public static final VoxelShape[] WAXED_OAK_CHAIR_SHAPES = makeWaxedOakChairShapes();
    public static final VoxelShape[] WAXED_OAK_CHANDELIER_SHAPES = new VoxelShape[]{Shapes.or(Shapes.or(
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(6.0D, 8.0D, 6.0D, 10.0D, 16.0D, 10.0D)))};
    public static final VoxelShape[] WAXED_OAK_TABLE_SHAPES = makeWaxedOakTableShapes();
}
