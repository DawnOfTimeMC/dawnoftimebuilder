package org.dawnoftime.dawnoftime.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VoxelShapesBuilder {
    /**
     * @return Stores VoxelShape with index : <p/>
     * {0 : NW Outer, 1 : N Default, 2 : NW Inner, 3 : NE Outer, 4 : N Default, 5 : NE Inner, 6 : SE Outer,
     * 7 : S Default, 8 : SE Inner, 9 : SW Outer, 10 : S Default, 11 : SW Inner}
     */
    protected static VoxelShape[] makePlateShapes() {
        final VoxelShape vsNorthFlat = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        final VoxelShape vsEastFlat = Block.box(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        final VoxelShape vsSouthFlat = Block.box(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        final VoxelShape vsWestFlat = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
        final VoxelShape vsNorthWestCorner = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 8.0D);
        final VoxelShape vsNorthEastCorner = Block.box(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        final VoxelShape vsSouthEastCorner = Block.box(8.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        final VoxelShape vsSouthWestCorner = Block.box(0.0D, 0.0D, 8.0D, 8.0D, 16.0D, 16.0D);
        return new VoxelShape[]{vsNorthWestCorner, vsNorthFlat, Shapes.or(vsNorthFlat, vsSouthWestCorner), vsNorthEastCorner, vsEastFlat, Shapes.or(vsEastFlat, vsNorthWestCorner), vsSouthEastCorner, vsSouthFlat, Shapes.or(vsSouthFlat, vsNorthEastCorner), vsSouthWestCorner, vsWestFlat, Shapes.or(vsWestFlat, vsSouthEastCorner),};
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * {0 : NW Outer, 1 : N Default, 2 : NW Inner, 3 : NE Outer, 4 : N Default, 5 : NE Inner, 6 : SE Outer,
     * 7 : S Default, 8 : SE Inner, 9 : SW Outer, 10 : S Default, 11 : SW Inner}
     */
    protected static VoxelShape[] makeThinPlateShapes() {
        VoxelShape vsNorthFlat = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
        VoxelShape vsEastFlat = Block.box(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vsSouthFlat = Block.box(0.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vsWestFlat = Block.box(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 16.0D);
        VoxelShape vsNorthWestCorner = Block.box(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 4.0D);
        VoxelShape vsNorthEastCorner = Block.box(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
        VoxelShape vsSouthEastCorner = Block.box(12.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vsSouthWestCorner = Block.box(0.0D, 0.0D, 12.0D, 4.0D, 16.0D, 16.0D);
        return new VoxelShape[]{
                vsNorthWestCorner,
                vsNorthFlat,
                Shapes.or(vsNorthFlat, vsWestFlat),
                vsNorthEastCorner,
                vsEastFlat,
                Shapes.or(vsEastFlat, vsNorthFlat),
                vsSouthEastCorner,
                vsSouthFlat,
                Shapes.or(vsSouthFlat, vsEastFlat),
                vsSouthWestCorner,
                vsWestFlat,
                Shapes.or(vsWestFlat, vsSouthFlat),
        };
    }

    protected static VoxelShape[] makePoolShapes() {
        final VoxelShape vsFloor = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
        final VoxelShape vsNorth = Block.box(0.0D, 2.0D, 0.0D, 16.0D, 16.0D, 2.0D);
        final VoxelShape vsEast = Block.box(14.0D, 2.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        final VoxelShape vsSouth = Block.box(0.0D, 2.0D, 14.0D, 16.0D, 16.0D, 16.0D);
        final VoxelShape vsWest = Block.box(0.0D, 2.0D, 0.0D, 2.0D, 16.0D, 16.0D);
        final VoxelShape vsPillar = Block.box(4.0D, 2.0D, 4.0D, 12.0D, 16.0D, 12.0D);
        final VoxelShape[] shapes = new VoxelShape[32];
        for (int i = 0; i < 32; i++) {
            VoxelShape temp = vsFloor;
            if ((i & 1) == 0) { // Check first bit : 0 -> North true
                temp = Shapes.or(temp, vsNorth);
            }
            if ((i >> 1 & 1) == 0) { // Check second bit : 0 -> East true
                temp = Shapes.or(temp, vsEast);
            }
            if ((i >> 2 & 1) == 0) { // Check third bit : 0 -> South true
                temp = Shapes.or(temp, vsSouth);
            }
            if ((i >> 3 & 1) == 0) { // Check fourth bit : 0 -> West true
                temp = Shapes.or(temp, vsWest);
            }
            if ((i >> 4 & 1) == 1) { // Check fifth bit : 1 -> Pillar true
                temp = Shapes.or(temp, vsPillar);
            }
            shapes[i] = temp;
        }
        return shapes;
    }

    protected static VoxelShape[] makeSmallPoolShapes() {
        final VoxelShape vsFloor = Block.box(0.0D, 8.0D, 0.0D, 16.0D, 10.0D, 16.0D);
        final VoxelShape vsNorth = Block.box(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 2.0D);
        final VoxelShape vsEast = Block.box(14.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        final VoxelShape vsSouth = Block.box(0.0D, 8.0D, 14.0D, 16.0D, 16.0D, 16.0D);
        final VoxelShape vsWest = Block.box(0.0D, 8.0D, 0.0D, 2.0D, 16.0D, 16.0D);
        final VoxelShape vsPillar = Block.box(4.0D, 10.0D, 4.0D, 12.0D, 16.0D, 12.0D);
        final VoxelShape vsBottom = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);
        final VoxelShape[] shapes = new VoxelShape[64];
        for (int i = 0; i < 64; i++) {
            VoxelShape temp = vsFloor;
            if ((i & 1) == 0) { // Check first bit : 0 -> North true
                temp = Shapes.or(temp, vsNorth);
            }
            if ((i >> 1 & 1) == 0) { // Check second bit : 0 -> East true
                temp = Shapes.or(temp, vsEast);
            }
            if ((i >> 2 & 1) == 0) { // Check third bit : 0 -> South true
                temp = Shapes.or(temp, vsSouth);
            }
            if ((i >> 3 & 1) == 0) { // Check fourth bit : 0 -> West true
                temp = Shapes.or(temp, vsWest);
            }
            if ((i >> 4 & 1) == 1) { // Check fifth bit : 1 -> Pillar true
                temp = Shapes.or(temp, vsPillar);
            }
            if ((i >> 5 & 1) == 1) { // Check fifth bit : 1 -> Bottom true
                temp = Shapes.or(temp, vsBottom);
            }
            shapes[i] = temp;
        }
        return shapes;
    }

    protected static VoxelShape[] makeSmallPoolCollisionShapes() {
        final VoxelShape vsFloor = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D);
        final VoxelShape vsNorth = Block.box(0.0D, 10.0D, 0.0D, 16.0D, 16.0D, 2.0D);
        final VoxelShape vsEast = Block.box(14.0D, 10.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        final VoxelShape vsSouth = Block.box(0.0D, 10.0D, 14.0D, 16.0D, 16.0D, 16.0D);
        final VoxelShape vsWest = Block.box(0.0D, 10.0D, 0.0D, 2.0D, 16.0D, 16.0D);
        final VoxelShape vsPillar = Block.box(4.0D, 10.0D, 4.0D, 12.0D, 16.0D, 12.0D);
        final VoxelShape[] shapes = new VoxelShape[32];
        for (int i = 0; i < 32; i++) {
            VoxelShape temp = vsFloor;
            if ((i & 1) == 0) { // Check first bit : 0 -> North true
                temp = Shapes.or(temp, vsNorth);
            }
            if ((i >> 1 & 1) == 0) { // Check second bit : 0 -> East true
                temp = Shapes.or(temp, vsEast);
            }
            if ((i >> 2 & 1) == 0) { // Check third bit : 0 -> South true
                temp = Shapes.or(temp, vsSouth);
            }
            if ((i >> 3 & 1) == 0) { // Check fourth bit : 0 -> West true
                temp = Shapes.or(temp, vsWest);
            }
            if ((i >> 4 & 1) == 1) { // Check fifth bit : 1 -> Pillar true
                temp = Shapes.or(temp, vsPillar);
            }
            shapes[i] = temp;
        }
        return shapes;
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * {0 : Axis X, 1 : Axis Z, 2 : Axis X + Z, 3 : Axis Y, 4 : Axis Y + Bottom, 5 : Axis Y + X,
     * 6 : Axis Y + X + Bottom, 7 : Axis Y + Z, 8 : Axis Y + Z + Bottom, 9 : Axis Y + X + Z, 10 : Axis Y + X + Z + Bottom}
     */
    protected static VoxelShape[] makeBeamShapes() {
        VoxelShape vsAxisX = Block.box(0.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);
        VoxelShape vsAxisZ = Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D);
        VoxelShape vsAxisXZ = Shapes.or(vsAxisX, vsAxisZ);
        VoxelShape vsAxisY = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
        VoxelShape vsAxisY_bottom = Shapes.or(vsAxisY, Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D));
        return new VoxelShape[]{
                vsAxisX,
                vsAxisZ,
                vsAxisXZ,
                vsAxisY,
                vsAxisY_bottom,
                Shapes.or(vsAxisY, vsAxisX),
                Shapes.or(vsAxisY_bottom, vsAxisX),
                Shapes.or(vsAxisY, vsAxisZ),
                Shapes.or(vsAxisY_bottom, vsAxisZ),
                Shapes.or(vsAxisY, vsAxisXZ),
                Shapes.or(vsAxisY_bottom, vsAxisXZ)
        };
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * {0 : Axis X, 1 : Axis Z, 2 : Axis X + Z, 3 : Axis Y, 4 : Axis Y + Bottom, 5 : Axis Y + X,
     * 6 : Axis Y + X + Bottom, 7 : Axis Y + Z, 8 : Axis Y + Z + Bottom, 9 : Axis Y + X + Z, 10 : Axis Y + X + Z + Bottom}
     */
    protected static VoxelShape[] makePergolaShapes() {
        VoxelShape vsAxisX = Block.box(0.0D, 5.0D, 6.0D, 16.0D, 11.0D, 10.0D);
        VoxelShape vsAxisZ = Block.box(6.0D, 5.0D, 0.0D, 10.0D, 11.0D, 16.0D);
        VoxelShape vsAxisXZ = Shapes.or(vsAxisX, vsAxisZ);
        VoxelShape vsAxisY = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
        VoxelShape vsAxisY_bottom = Shapes.or(vsAxisY, Block.box(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D));
        return new VoxelShape[]{
                vsAxisX,
                vsAxisZ,
                vsAxisXZ,
                vsAxisY,
                vsAxisY_bottom,
                Shapes.or(vsAxisY, vsAxisX),
                Shapes.or(vsAxisY_bottom, vsAxisX),
                Shapes.or(vsAxisY, vsAxisZ),
                Shapes.or(vsAxisY_bottom, vsAxisZ),
                Shapes.or(vsAxisY, vsAxisXZ),
                Shapes.or(vsAxisY_bottom, vsAxisXZ)
        };
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * {0 : NW Outer, 1 : NW Default, 2 : NW Inner, 3 : NE Outer, 4 : NE Default, 5 : NE Inner, 6 : SE Outer,
     * 7 : SE Default, 8 : SE Inner, 9 : SW Outer, 10 : SW Default, 11 : SW Inner <p/>} * 2 with top.
     */
    protected static VoxelShape[] makeEdgeShapes() {
        final VoxelShape vsNorthFlat = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
        final VoxelShape vsEastFlat = Block.box(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
        final VoxelShape vsSouthFlat = Block.box(0.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
        final VoxelShape vsWestFlat = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 16.0D);
        final VoxelShape vsNorthWestCorner = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
        final VoxelShape vsNorthEastCorner = Block.box(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
        final VoxelShape vsSouthEastCorner = Block.box(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
        final VoxelShape vsSouthWestCorner = Block.box(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
        VoxelShape[] voxelShapes = new VoxelShape[24];
        VoxelShape[] voxelShapesBottom = {
                vsNorthWestCorner,
                vsNorthFlat,
                Shapes.or(vsNorthFlat, vsSouthWestCorner),
                vsNorthEastCorner,
                vsEastFlat,
                Shapes.or(vsEastFlat, vsNorthWestCorner),
                vsSouthEastCorner,
                vsSouthFlat,
                Shapes.or(vsSouthFlat, vsNorthEastCorner),
                vsSouthWestCorner,
                vsWestFlat,
                Shapes.or(vsWestFlat, vsSouthEastCorner)
        };
        System.arraycopy(voxelShapesBottom, 0, voxelShapes, 0, voxelShapesBottom.length);
        for (int i = 0; i < voxelShapesBottom.length; i++) {
            voxelShapes[i + voxelShapesBottom.length] = voxelShapesBottom[i].move(0.0D, 0.5D, 0.0D);
        }
        return voxelShapes;
    }


    /**
     * @return Stores VoxelShape with index following binary system : <p/>
     * {0 : SWNE, 1 : S, 2 : W, 3 : SW, 4 : N, 5 : SN, 6 : WN, 7 : SWN,
     * 8 : E, 9 : SE, 10 : WE, 11 : SWE, 12 : NE, 13 : SNE, 14 : WNE}
     */
    protected static VoxelShape[] makeLatticeShapes() {
        VoxelShape vsSouth = Block.box(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vsWest = Block.box(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 16.0D);
        VoxelShape vsNorth = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 2.0D);
        VoxelShape vsEast = Block.box(14.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vsSouthWest = Shapes.or(vsSouth, vsWest);
        VoxelShape vsNorthWest = Shapes.or(vsWest, vsNorth);
        VoxelShape vsNorthEast = Shapes.or(vsNorth, vsEast);
        VoxelShape vsSouthEast = Shapes.or(vsEast, vsSouth);
        return new VoxelShape[]{
                Shapes.or(vsSouthWest, vsNorthEast),
                vsSouth,
                vsWest,
                vsSouthWest,
                vsNorth,
                Shapes.or(vsSouth, vsNorth),
                vsNorthWest,
                Shapes.or(vsSouthWest, vsNorth),
                vsEast,
                vsSouthEast,
                Shapes.or(vsWest, vsEast),
                Shapes.or(vsSouthWest, vsEast),
                vsNorthEast,
                Shapes.or(vsSouth, vsNorthEast),
                Shapes.or(vsNorthWest, vsEast),
        };
    }

    /**
     * @return Stores VoxelShape with index following binary system : <p/>
     * {0 : Full connection, 1 : S,  2 : W, 3 : S-W + Pillar SW, 4 : N, 5 : S + N, 6 : W-N + Pillar WN,
     * 7 : S-W-N + Pillar SW-WN, 8 : E, 9 : S-E + Pillar ES, 10 : W-E, 11 : S-W-E + Pillar SW-ES,
     * 12 : N-E + Pillar NE, 13 : S-N-E + Pillar NE-ES, 14 : W-N-E + Pillar WN-NE, 15 : S-W-N-E + Pillar SW-WN-NE-ES}
     */
    protected static VoxelShape[] makeWaxedOakTableShapes() {
        // South - West - North - East:
        VoxelShape[] vsSide = Utils.generateHorizontalShapes(new VoxelShape[]{Shapes.or(Block.box(1.0D, 1.0D, 13.5D, 15.0D, 3.0D, 14.5D), Block.box(1.0D, 12.0D, 13.5D, 15.0D, 16.0D, 14.5D))});
        // SW - NW - NE - SE:
        VoxelShape[] vsPillar = Utils.generateHorizontalShapes(new VoxelShape[]{Block.box(0.5D, 0.0D, 12.5D, 3.5D, 16.0D, 15.5D)});
        // W - N - E - S
        VoxelShape[] vsPillarLeft = Utils.generateHorizontalShapes(new VoxelShape[]{Block.box(0.0D, 0.0D, 13.0D, 1.0D, 16.0D, 15.0D)});
        // S - W - N - E
        VoxelShape[] vsPillarRight = Utils.generateHorizontalShapes(new VoxelShape[]{Block.box(1.0D, 0.0D, 15.0D, 3.0D, 16.0D, 16.0D)});
        return new VoxelShape[]{
                Shapes.empty(),
                Shapes.or(vsSide[0], vsPillarLeft[0], vsPillarRight[3]),
                Shapes.or(vsSide[1], vsPillarLeft[1], vsPillarRight[0]),
                Shapes.or(vsSide[0], vsSide[1], vsPillar[0], vsPillarLeft[1], vsPillarRight[3]),
                Shapes.or(vsSide[2], vsPillarLeft[2], vsPillarRight[1]),
                Shapes.or(vsSide[0], vsSide[2], vsPillarLeft[0], vsPillarRight[3], vsPillarLeft[2], vsPillarRight[1]),
                Shapes.or(vsSide[1], vsSide[2], vsPillar[1], vsPillarLeft[2], vsPillarRight[0]),
                Shapes.or(vsSide[0], vsSide[1], vsSide[2], vsPillar[0], vsPillar[1], vsPillarLeft[2], vsPillarRight[3]),
                Shapes.or(vsSide[3], vsPillarLeft[3], vsPillarRight[2]),
                Shapes.or(vsSide[0], vsSide[3], vsPillar[3], vsPillarLeft[0], vsPillarRight[2]),
                Shapes.or(vsSide[1], vsSide[3], vsPillarLeft[1], vsPillarRight[0], vsPillarLeft[3], vsPillarRight[2]),
                Shapes.or(vsSide[0], vsSide[1], vsSide[3], vsPillar[0], vsPillar[3], vsPillarLeft[1], vsPillarRight[2]),
                Shapes.or(vsSide[2], vsSide[3], vsPillar[2], vsPillarRight[1], vsPillarLeft[3]),
                Shapes.or(vsSide[0], vsSide[2], vsSide[3], vsPillar[2], vsPillar[3], vsPillarLeft[0], vsPillarRight[1]),
                Shapes.or(vsSide[1], vsSide[2], vsSide[3], vsPillar[1], vsPillar[2], vsPillarLeft[3], vsPillarRight[0]),
                Shapes.or(vsSide[0], vsSide[1], vsSide[2], vsSide[3], vsPillar[0], vsPillar[1], vsPillar[2], vsPillar[3]),
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D)
        };
    }

    protected static VoxelShape[] makeSupportSlabShapes() {
        VoxelShape vs = Block.box(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vsFourPx = Shapes.or(vs, Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D));
        VoxelShape vsEightPx = Shapes.or(vs, Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D));
        VoxelShape vsTenPx = Shapes.or(vs, Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D));
        return new VoxelShape[]{vs, vsFourPx, vsEightPx, vsTenPx};
    }

    /**
     * @return Stores VoxelShape for "South" with index : <p/>
     * {0 : S Small fireplace, 1 : S Big fireplace}
     */
    protected static VoxelShape[] makeMultiblockFireplaceShapes() {
        return Utils.generateHorizontalShapes(new VoxelShape[]{
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D),
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 12.0D)
        });
    }

    /**
     * @return Stores VoxelShape for South face with index : <p/>
     * {0 : NONE & NONE, 1 : NONE & RIGHT -> horizontal, 2 : NONE & LEFT -> horizontal, 3 : NONE & BOTH -> horizontal,
     * 4 : UNDER & NONE -> vertical, 5 : UNDER & RIGHT, 6 : UNDER & LEFT, 7 : UNDER & BOTH, 8 : ABOVE & NONE -> vertical,
     * 9 : ABOVE & RIGHT, 10 : ABOVE & LEFT, 11 : ABOVE & BOTH, 12 : BOTH & NONE -> vertical, 13 : BOTH & RIGHT,
     * 14 : BOTH & LEFT, 15 : BOTH & BOTH}
     */
    protected static VoxelShape[] makeReliefShapes() {
        VoxelShape vsCenter = Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 4.0D);
        VoxelShape vsUnder = Block.box(4.0D, 0.0D, 0.0D, 12.0D, 4.0D, 4.0D);
        VoxelShape vsAbove = Block.box(4.0D, 12.0D, 0.0D, 12.0D, 16.0D, 4.0D);
        VoxelShape vsLeft = Block.box(0.0D, 4.0D, 0.0D, 4.0D, 12.0D, 4.0D);
        VoxelShape vsRight = Block.box(12.0D, 4.0D, 0.0D, 16.0D, 12.0D, 4.0D);
        VoxelShape vsVertical = Shapes.or(vsCenter, vsUnder, vsAbove);
        VoxelShape vsHorizontal = Shapes.or(vsCenter, vsLeft, vsRight);
        return Utils.generateHorizontalShapes(new VoxelShape[]{
                vsCenter,
                vsHorizontal,
                vsHorizontal,
                vsHorizontal,
                vsVertical,
                Shapes.or(vsCenter, vsUnder, vsRight),
                Shapes.or(vsCenter, vsUnder, vsLeft),
                Shapes.or(vsHorizontal, vsUnder),
                vsVertical,
                Shapes.or(vsCenter, vsAbove, vsRight),
                Shapes.or(vsCenter, vsAbove, vsLeft),
                Shapes.or(vsHorizontal, vsAbove),
                vsVertical,
                Shapes.or(vsVertical, vsRight),
                Shapes.or(vsVertical, vsLeft),
                Shapes.or(vsVertical, vsHorizontal)
        });
    }

    /**
     * @return Stores VoxelShape for "South" with index :<p/>
     * {0 : S Closed, 1 : S Fully opened to the right, 2 : S Fully opened to the left}
     */
    protected static VoxelShape[] makeSmallShutterShapes() {
        return Utils.generateHorizontalShapes(new VoxelShape[]{Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D),
                Block.box(-13.0D, 0.0D, 13.0D, 3.0D, 16.0D, 16.0D),
                Block.box(13.0D, 0.0D, 13.0D, 29.0D, 16.0D, 16.0D)});
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * {0 : Axis X, 1 : Axis Z, 2 : Axis X + Z, 3 : Axis X + 4px, 4 : Axis Z + 4px,
     * 5 : Axis X + Z + 4px, 6 : Axis X + 8px, 7 : Axis Z + 8px, 8 : Axis X + Z + 8px,
     * 9 : Axis X + 10px, 10 : Axis Z + 10px, 11 : Axis X + Z + 10px}
     */
    protected static VoxelShape[] makeSupportBeamShapes() {
        VoxelShape vs = Block.box(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vsAxisX = Shapes.or(vs, Block.box(0.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D));
        VoxelShape vsAxisZ = Shapes.or(vs, Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D));
        VoxelShape vsAxisXZ = Shapes.or(vsAxisX, Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D));
        VoxelShape vsAxis4px = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
        VoxelShape vsAxis8px = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
        VoxelShape vsAxis10px = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
        return new VoxelShape[]{
                vsAxisX,
                vsAxisZ,
                vsAxisXZ,
                Shapes.or(vsAxisX, vsAxis4px),
                Shapes.or(vsAxisZ, vsAxis4px),
                Shapes.or(vsAxisXZ, vsAxis4px),
                Shapes.or(vsAxisX, vsAxis8px),
                Shapes.or(vsAxisZ, vsAxis8px),
                Shapes.or(vsAxisXZ, vsAxis8px),
                Shapes.or(vsAxisX, vsAxis10px),
                Shapes.or(vsAxisZ, vsAxis10px),
                Shapes.or(vsAxisXZ, vsAxis10px)
        };
    }

    /**
     * @return Stores VoxelShape for "South" with index : <p/>
     * {0 : S Lone, 1 : S Under, 2 : S Above, 3 : S Both}
     */
    protected static VoxelShape[] makeLimestoneSidedColumnShapes() {
        VoxelShape vsColumn = Shapes.or(
                Block.box(1.5D, 0.0D, 0.0D, 14.5D, 16.0D, 3.0D),
                Block.box(5.5D, 0.0D, 3.0D, 10.5D, 16.0D, 6.0D));
        return Utils.generateHorizontalShapes(new VoxelShape[]{
                vsColumn,
                Shapes.or(
                        Block.box(1.5D, 0.0D, 0.0D, 14.5D, 9.0D, 3.0D),
                        Block.box(5.5D, 0.0D, 3.0D, 10.5D, 9.0D, 6.0D),
                        Block.box(0.5D, 9.0D, 0.0D, 15.5D, 14.0D, 4.0D),
                        Block.box(4.5D, 9.0D, 4.0D, 11.5D, 14.0D, 7.0D),
                        Block.box(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 5.0D),
                        Block.box(3.5D, 14.0D, 5.0D, 12.5D, 16.0D, 8.0D)),
                Shapes.or(
                        Block.box(1.5D, 4.0D, 0.0D, 14.5D, 16.0D, 3.0D),
                        Block.box(5.5D, 4.0D, 3.0D, 10.5D, 16.0D, 6.0D),
                        Block.box(1.0D, 5.0D, 0.0D, 15.0D, 6.0D, 3.5D),
                        Block.box(5.0D, 5.0D, 3.5D, 11.0D, 6.0D, 6.5D),
                        Block.box(0.5D, 0.0D, 0.0D, 15.5D, 4.0D, 4.0D),
                        Block.box(4.5D, 0.0D, 4.0D, 11.5D, 4.0D, 7.0D)),
                vsColumn});
    }

    /**
     * @return Stores VoxelShape with index. Starting at 12, the shapes are for the TOP half: <p/>
     * {0 : NW Outer, 1 : N Default, 2 : NW Inner, 3 : NE Outer, 4 : N Default, 5 : NE Inner,
     * 6 : SE Outer, 7 : S Default, 8 : SE Inner, 9 : SW Outer, 10 : S Default, 11 : SW Inner}
     */
    protected static VoxelShape[] makeReinforcedIronFenceShapes() {
        VoxelShape vsNorthFlat = Shapes.or(Block.box(0.0D, 8.0D, 4.0D, 16.0D, 16.0D, 6.0D), Block.box(0.0D, 0.0D, 1.0D, 16.0D, 8.0D, 9.0D));
        VoxelShape vsEastFlat = Shapes.or(Block.box(10.0D, 8.0D, 0.0D, 12.0D, 16.0D, 16.0D), Block.box(7.0D, 0.0D, 0.0D, 15.0D, 8.0D, 16.0D));
        VoxelShape vsSouthFlat = Shapes.or(Block.box(0.0D, 8.0D, 10.0D, 16.0D, 16.0D, 12.0D), Block.box(0.0D, 0.0D, 7.0D, 16.0D, 8.0D, 15.0D));
        VoxelShape vsWestFlat = Shapes.or(Block.box(4.0D, 8.0D, 0.0D, 6.0D, 16.0D, 16.0D), Block.box(1.0D, 0.0D, 0.0D, 9.0D, 8.0D, 16.0D));
        VoxelShape vsNorthWestCorner = Block.box(0.0D, 0.0D, 0.0D, 10.0D, 16.0D, 10.0D);
        VoxelShape vsNorthEastCorner = Block.box(6.0D, 0.0D, 0.0D, 16.0D, 16.0D, 10.0D);
        VoxelShape vsSouthEastCorner = Block.box(6.0D, 0.0D, 6.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vsSouthWestCorner = Block.box(0.0D, 0.0D, 6.0D, 10.0D, 16.0D, 16.0D);
        VoxelShape vsNorthFlatTop = Block.box(0.0D, 0.0D, 4.0D, 16.0D, 16.0D, 6.0D);
        VoxelShape vsEastFlatTop = Block.box(10.0D, 0.0D, 0.0D, 12.0D, 16.0D, 16.0D);
        VoxelShape vsSouthFlatTop = Block.box(0.0D, 0.0D, 10.0D, 16.0D, 16.0D, 12.0D);
        VoxelShape vsWestFlatTop = Block.box(4.0D, 0.0D, 0.0D, 6.0D, 16.0D, 16.0D);
        VoxelShape vsNorthWestCornerTop = Block.box(0.0D, 0.0D, 0.0D, 10.0D, 16.0D, 10.0D);
        VoxelShape vsNorthEastCornerTop = Block.box(6.0D, 0.0D, 0.0D, 16.0D, 16.0D, 10.0D);
        VoxelShape vsSouthEastCornerTop = Block.box(6.0D, 0.0D, 6.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vsSouthWestCornerTop = Block.box(0.0D, 0.0D, 6.0D, 10.0D, 16.0D, 16.0D);
        return new VoxelShape[]{
                vsNorthWestCorner,
                vsNorthFlat,
                Shapes.or(vsNorthFlat, vsWestFlat, vsNorthWestCorner),
                vsNorthEastCorner,
                vsEastFlat,
                Shapes.or(vsEastFlat, vsNorthFlat, vsNorthEastCorner),
                vsSouthEastCorner,
                vsSouthFlat,
                Shapes.or(vsSouthFlat, vsEastFlat, vsSouthEastCorner),
                vsSouthWestCorner,
                vsWestFlat,
                Shapes.or(vsWestFlat, vsSouthFlat, vsSouthWestCorner),
                vsNorthWestCornerTop,
                vsNorthFlatTop,
                Shapes.or(vsNorthFlatTop, vsWestFlatTop, vsNorthWestCornerTop),
                vsNorthEastCornerTop,
                vsEastFlatTop,
                Shapes.or(vsEastFlatTop, vsNorthFlatTop, vsNorthEastCornerTop),
                vsSouthEastCornerTop,
                vsSouthFlatTop,
                Shapes.or(vsSouthFlatTop, vsEastFlatTop, vsSouthEastCornerTop),
                vsSouthWestCornerTop,
                vsWestFlatTop,
                Shapes.or(vsWestFlatTop, vsSouthFlatTop, vsSouthWestCornerTop)};
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * {0 : NW Outer, 1 : N Default, 2 : NW Inner, 3 : NE Outer, 4 : N Default, 5 : NE Inner,
     * 6 : SE Outer, 7 : S Default, 8 : SE Inner, 9 : SW Outer, 10 : S Default, 11 : SW Inner}
     */
    protected static VoxelShape[] makeIronFenceShapes() {
        VoxelShape vsNorthFlat = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 2.5D);
        VoxelShape vsEastFlat = Block.box(13.5D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vsSouthFlat = Block.box(0.0D, 0.0D, 13.5D, 16.0D, 16.0D, 16.0D);
        VoxelShape vsWestFlat = Block.box(0.0D, 0.0D, 0.0D, 2.5D, 16.0D, 16.0D);
        VoxelShape vsNorthWestCorner = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 3.0D);
        VoxelShape vsNorthEastCorner = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
        VoxelShape vsSouthEastCorner = Block.box(13.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vsSouthWestCorner = Block.box(0.0D, 0.0D, 13.0D, 3.0D, 16.0D, 16.0D);
        VoxelShape vsNorthFlatUp = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8, 2.5D);
        VoxelShape vsEastFlatUp = Block.box(13.5D, 0.0D, 0.0D, 16.0D, 8, 16.0D);
        VoxelShape vsSouthFlatUp = Block.box(0.0D, 0.0D, 13.5D, 16.0D, 8, 16.0D);
        VoxelShape vsWestFlatUp = Block.box(0.0D, 0.0D, 0.0D, 2.5D, 8, 16.0D);
        VoxelShape vsNorthWestCornerUp = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 10, 3.0D);
        VoxelShape vsNorthEastCornerUp = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 10, 3.0D);
        VoxelShape vsSouthEastCornerUp = Block.box(13.0D, 0.0D, 13.0D, 16.0D, 10, 16.0D);
        VoxelShape vsSouthWestCornerUp = Block.box(0.0D, 0.0D, 13.0D, 3.0D, 10, 16.0D);
        return new VoxelShape[]{
                vsNorthWestCorner,
                vsNorthFlat,
                Shapes.or(vsNorthFlat, vsWestFlat, vsNorthWestCorner),
                vsNorthEastCorner,
                vsEastFlat,
                Shapes.or(vsEastFlat, vsNorthFlat, vsNorthEastCorner),
                vsSouthEastCorner,
                vsSouthFlat,
                Shapes.or(vsSouthFlat, vsEastFlat, vsSouthEastCorner),
                vsSouthWestCorner,
                vsWestFlat,
                Shapes.or(vsWestFlat, vsSouthFlat, vsSouthWestCorner),
                vsNorthWestCornerUp,
                vsNorthFlatUp,
                Shapes.or(vsNorthFlatUp, vsWestFlatUp, vsNorthWestCornerUp),
                vsNorthEastCornerUp,
                vsEastFlatUp,
                Shapes.or(vsEastFlatUp, vsNorthFlatUp, vsNorthEastCornerUp),
                vsSouthEastCornerUp,
                vsSouthFlatUp,
                Shapes.or(vsSouthFlatUp, vsEastFlatUp, vsSouthEastCornerUp),
                vsSouthWestCornerUp,
                vsWestFlatUp,
                Shapes.or(vsWestFlatUp, vsSouthFlatUp, vsSouthWestCornerUp),
        };
    }


    /**
     * @return Stores VoxelShape with index following binary system : <p/>
     * 8+4+2+1 with 1 = SOUTH, 2 = WEST, 4 = NORTH, 8 = EAST
     * {0 : SWNE, 1 : S, 2 : W, 3 : SW, 4 : N, 5 : SN, 6 : WN, 7 : SWN,
     * 8 : E, 9 : SE, 10 : WE, 11 : SWE, 12 : NE, 13 : SNE, 14 : WNE}
     */
    protected static VoxelShape[] makeIvyShapes() {
        VoxelShape vsSouth = Block.box(0.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vsWest = Block.box(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 16.0D);
        VoxelShape vsNorth = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
        VoxelShape vsEast = Block.box(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vsSouthWest = Shapes.or(vsSouth, vsWest);
        VoxelShape vsNorthWest = Shapes.or(vsWest, vsNorth);
        VoxelShape vsNorthEst = Shapes.or(vsNorth, vsEast);
        VoxelShape vsSouthEst = Shapes.or(vsEast, vsSouth);
        return new VoxelShape[]{
                Shapes.or(vsSouthWest, vsNorthEst),
                vsSouth,
                vsWest,
                vsSouthWest,
                vsNorth,
                Shapes.or(vsSouth, vsNorth),
                vsNorthWest,
                Shapes.or(vsSouthWest, vsNorth),
                vsEast,
                vsSouthEst,
                Shapes.or(vsWest, vsEast),
                Shapes.or(vsSouthWest, vsEast),
                vsNorthEst,
                Shapes.or(vsSouth, vsNorthEst),
                Shapes.or(vsNorthWest, vsEast),
        };
    }

    protected static VoxelShape[] makeStoneBricksArrowslitShapes() {
        return Utils.generateHorizontalShapes(new VoxelShape[]{Shapes.or(
                Block.box(0.0D, 0.0D, 14.0D, 7.0D, 16.0D, 16.0D),
                Block.box(9.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D),
                Block.box(0.0D, 0.0D, 8.0D, 2.0D, 16.0D, 14.0D),
                Block.box(14.0D, 0.0D, 8.0D, 16.0D, 16.0D, 14.0D),
                Block.box(2.0D, 0.0D, 13.0D, 4.5D, 16.0D, 14.0D),
                Block.box(11.5D, 0.0D, 13.0D, 14.0D, 16.0D, 14.0D)
        )
        });
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * {0 : Lone, 1 : Under, 2 : Above, 3 : Both}
     */
    protected static VoxelShape[] makeLimestoneChimneyShapes() {
        return new VoxelShape[]{
                Shapes.or(
                        Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D),
                        Block.box(4.0D, 8.0D, 4.0D, 12.0D, 16.0D, 12.0D)),
                Shapes.or(
                        Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D),
                        Block.box(4.0D, 8.0D, 4.0D, 12.0D, 16.0D, 12.0D)),
                Shapes.or(
                        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
                        Block.box(2.0D, 8.0D, 2.0D, 14.0D, 16.0D, 14.0D)),
                Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D)
        };
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * {0 : None, 1 : Under, 2 : Above, 3 : Both}
     */
    protected static VoxelShape[] makeStoneBricksChimneyShapes() {
        return new VoxelShape[]{
                Shapes.or(
                        Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D),
                        Block.box(1.0D, 8.0D, 1.0D, 15.0D, 11.0D, 15.0D)),
                Shapes.or(
                        Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D),
                        Block.box(1.0D, 8.0D, 1.0D, 15.0D, 11.0D, 15.0D)),
                Shapes.or(
                        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
                        Block.box(2.0D, 8.0D, 2.0D, 14.0D, 16.0D, 14.0D)),
                Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D)
        };
    }


    /**
     * @return Stores VoxelShape for "South" with index : <p/>
     * {0 : S None, 1 : S Left, 2 : S Right, 3 : S Both}
     */
    protected static VoxelShape[] makeStoneBricksMachicolationShapes() {
        VoxelShape floorVS = Block.box(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        return Utils.generateHorizontalShapes(new VoxelShape[]{
                Shapes.or(
                        floorVS,
                        Block.box(0.0D, 8.0D, 0.0D, 6.0D, 16.0D, 16.0D),
                        Block.box(10.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D),
                        Block.box(0.0D, 4.0D, 5.0D, 6.0D, 8.0D, 16.0D),
                        Block.box(10.0D, 4.0D, 5.0D, 16.0D, 8.0D, 16.0D),
                        Block.box(0.0D, 0.0D, 11.0D, 6.0D, 4.0D, 16.0D),
                        Block.box(10.0D, 0.0D, 11.0D, 16.0D, 4.0D, 16.0D)
                ),
                Shapes.or(
                        floorVS,
                        Block.box(0.0D, 8.0D, 0.0D, 6.0D, 16.0D, 16.0D),
                        Block.box(13.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D),
                        Block.box(0.0D, 4.0D, 5.0D, 6.0D, 8.0D, 16.0D),
                        Block.box(13.0D, 4.0D, 5.0D, 16.0D, 8.0D, 16.0D),
                        Block.box(0.0D, 0.0D, 11.0D, 6.0D, 4.0D, 16.0D),
                        Block.box(13.0D, 0.0D, 11.0D, 16.0D, 4.0D, 16.0D)
                ),
                Shapes.or(
                        floorVS,
                        Block.box(0.0D, 8.0D, 0.0D, 3.0D, 16.0D, 16.0D),
                        Block.box(10.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D),
                        Block.box(0.0D, 4.0D, 5.0D, 3.0D, 8.0D, 16.0D),
                        Block.box(10.0D, 4.0D, 5.0D, 16.0D, 8.0D, 16.0D),
                        Block.box(0.0D, 0.0D, 11.0D, 3.0D, 4.0D, 16.0D),
                        Block.box(10.0D, 0.0D, 11.0D, 16.0D, 4.0D, 16.0D)
                ),
                Shapes.or(
                        floorVS,
                        Block.box(0.0D, 8.0D, 0.0D, 3.0D, 16.0D, 16.0D),
                        Block.box(13.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D),
                        Block.box(0.0D, 4.0D, 5.0D, 3.0D, 8.0D, 16.0D),
                        Block.box(13.0D, 4.0D, 5.0D, 16.0D, 8.0D, 16.0D),
                        Block.box(0.0D, 0.0D, 11.0D, 3.0D, 4.0D, 16.0D),
                        Block.box(13.0D, 0.0D, 11.0D, 16.0D, 4.0D, 16.0D)
                )
        });
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * {0 : NW Outer, 1 : N Default, 2 : NW Inner, 3 : NE Outer, 4 : N Default, 5 : NE Inner,
     * 6 : SE Outer, 7 : S Default, 8 : SE Inner, 9 : SW Outer, 10 : S Default, 11 : SW Inner}
     */
    protected static VoxelShape[] makeWaxedOakBalusterShapes() {
        VoxelShape vsNorthFlat = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
        VoxelShape vsEastFlat = Block.box(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vsSouthFlat = Block.box(0.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vsWestFlat = Block.box(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 16.0D);
        VoxelShape vsNorthWestCorner = Block.box(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 4.0D);
        VoxelShape vsNorthEastCorner = Block.box(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
        VoxelShape vsSouthEastCorner = Block.box(12.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vsSouthWestCorner = Block.box(0.0D, 0.0D, 12.0D, 4.0D, 16.0D, 16.0D);
        return Utils.generateHorizontalShapes(new VoxelShape[]{
                vsNorthWestCorner,
                vsNorthFlat,
                Shapes.or(vsNorthFlat, vsWestFlat),
                vsNorthEastCorner,
                vsEastFlat,
                Shapes.or(vsEastFlat, vsNorthFlat),
                vsSouthEastCorner,
                vsSouthFlat,
                Shapes.or(vsSouthFlat, vsEastFlat),
                vsSouthWestCorner,
                vsWestFlat,
                Shapes.or(vsWestFlat, vsSouthFlat),
        });
    }

    protected static VoxelShape[] makeWaxedOakChairShapes() {
        return Utils.generateHorizontalShapes(new VoxelShape[]{
                Shapes.or(
                        Block.box(2.0D, 0.0D, 2.0D, 14.0D, 11.0D, 14.0D),
                        Block.box(2.5D, 11.0D, 3.0D, 13.5D, 16.0D, 5.0D)),
                Block.box(2.5D, 0.0D, 3.0D, 13.5D, 10.0D, 5.0D)});
    }

    protected static VoxelShape[] makeCharredSpruceFancyRailingShapes() {
        VoxelShape voxelShape = Block.box(7.0D, 6.0D, 7.0D, 9.0D, 16.0D, 9.0D);
        VoxelShape voxelShape1 = Block.box(7.0D, 6.0D, 0.0D, 9.0D, 16.0D, 9.0D);
        VoxelShape voxelShape2 = Block.box(7.0D, 6.0D, 7.0D, 9.0D, 16.0D, 16.0D);
        VoxelShape voxelShape3 = Block.box(0.0D, 6.0D, 7.0D, 9.0D, 16.0D, 9.0D);
        VoxelShape voxelShape4 = Block.box(7.0D, 6.0D, 7.0D, 16.0D, 16.0D, 9.0D);
        VoxelShape voxelShape5 = Shapes.or(voxelShape1, voxelShape4);
        VoxelShape voxelShape6 = Shapes.or(voxelShape2, voxelShape3);
        VoxelShape[] allVoxelShape = new VoxelShape[]{Shapes.empty(), voxelShape2, voxelShape3, voxelShape6, voxelShape1, Shapes.or(voxelShape2, voxelShape1), Shapes.or(voxelShape3, voxelShape1), Shapes.or(voxelShape6, voxelShape1), voxelShape4, Shapes.or(voxelShape2, voxelShape4), Shapes.or(voxelShape3, voxelShape4), Shapes.or(voxelShape6, voxelShape4), voxelShape5, Shapes.or(voxelShape2, voxelShape5), Shapes.or(voxelShape3, voxelShape5), Shapes.or(voxelShape6, voxelShape5)};
        for (int i = 0; i < 16; ++i) {
            allVoxelShape[i] = Shapes.or(voxelShape, allVoxelShape[i]);
        }
        return allVoxelShape;
    }

    protected static VoxelShape[] makeCharredSpruceShuttersShapes() {
        return Utils.generateHorizontalShapes(new VoxelShape[]{
                Block.box(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D),
                Shapes.or(
                        Block.box(0.0D, 12.0D, 12.0D, 16.0D, 16.0D, 16.0D),
                        Block.box(0.0D, 9.0D, 9.0D, 16.0D, 13.0D, 13.0D),
                        Block.box(0.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D),
                        Block.box(0.0D, 3.0D, 3.0D, 16.0D, 7.0D, 7.0D))});
    }

    protected static VoxelShape[] makeCharredSpruceTallShuttersShapes() {
        return Utils.generateHorizontalShapes(new VoxelShape[]{
                Block.box(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D),
                Shapes.or(
                        Block.box(0.0D, 10.0D, 11.0D, 16.0D, 16.0D, 16.0D),
                        Block.box(0.0D, 5.0D, 9.0D, 16.0D, 10.0D, 14.0D),
                        Block.box(0.0D, 0.0D, 7.0D, 16.0D, 5.0D, 12.0D)),
                Shapes.or(
                        Block.box(0.0D, 11.0D, 5.0D, 16.0D, 16.0D, 10.0D),
                        Block.box(0.0D, 6.0D, 3.0D, 16.0D, 11.0D, 8.0D),
                        Block.box(0.0D, 1.0D, 1.0D, 16.0D, 6.0D, 6.0D))});
    }

    protected static VoxelShape[] makeSmallTatamiMatShapes() {
        return new VoxelShape[]{
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D),
                Block.box(5.5D, 0.0D, 0.0D, 10.5D, 5.0D, 16.0D),
                Block.box(1.5D, 0.0D, 0.0D, 14.5D, 5.0D, 16.0D),
                Block.box(1.5D, 0.0D, 0.0D, 14.5D, 10.0D, 16.0D),
                Block.box(0.0D, 0.0D, 5.5D, 16.0D, 5.0D, 10.5D),
                Block.box(0.0D, 0.0D, 1.5D, 16.0D, 5.0D, 14.5D),
                Block.box(0.0D, 0.0D, 1.5D, 16.0D, 10.0D, 14.5D),
                Block.box(5.5D, 0.0D, 5.5D, 10.5D, 16.0D, 10.5D)};
    }

    /**
     * @return In the order none, under, above, both.
     */
    protected static VoxelShape[] makeMoraqMosaicColumnShapes() {
        return new VoxelShape[]{
                Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D),
                Shapes.or(
                        Block.box(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D),
                        Block.box(2.5D, 6.0D, 2.5D, 13.5D, 16.0D, 13.5D)),
                Shapes.or(
                        Block.box(2.0D, 14.0D, 2.0D, 14.0D, 16.0D, 14.0D),
                        Block.box(2.5D, 0.0D, 2.5D, 13.5D, 14.0D, 13.5D)),
                Block.box(2.5D, 0.0D, 2.5D, 13.5D, 16.0D, 13.5D)};
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * {0 : NW Outer, 1 : N Default, 2 : NW Inner}
     */
    protected static VoxelShape[] makeSandstoneCrenelationShapes() {
        VoxelShape vsCrenelation = Shapes.or(
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 8.0D),
                Block.box(4.0D, 2.0D, 0.0D, 12.0D, 13.0D, 8.0D),
                Block.box(6.0D, 13.0D, 0.0D, 10.0D, 16.0D, 8.0D));
        return Utils.generateHorizontalShapes(new VoxelShape[]{
                Block.box(0.0D, 0.0D, 0.0D, 8.0D, 2.0D, 8.0D),
                vsCrenelation,
                Shapes.or(
                        vsCrenelation,
                        Block.box(0.0D, 0.0D, 8.0D, 8.0D, 2.0D, 16.0D),
                        Block.box(0.0D, 2.0D, 4.0D, 8.0D, 13.0D, 12.0D),
                        Block.box(0.0D, 13.0D, 6.0D, 8.0D, 16.0D, 10.0D))
        });
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * {0 : NW Outer, 1 : N Default, 2 : NW Inner}
     */
    protected static VoxelShape[] makeGreenSculptedPlasteredStoneFriezeShapes() {
        VoxelShape vsQtrN = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 8.0D);
        VoxelShape vsSpikeN = Block.box(4.5D, 4.0D, 2.0D, 11.5D, 16.0D, 4.0D);
        return Utils.generateHorizontalShapes(new VoxelShape[]{
                Shapes.or(
                        Block.box(0.0D, 0.0D, 0.0D, 8.0D, 4.0D, 8.0D),
                        Block.box(0.0D, 4.0D, 0.0D, 6.0D, 16.0D, 6.0D)),
                Shapes.or(vsQtrN, vsSpikeN),
                Shapes.or(
                        vsQtrN,
                        Block.box(0.0D, 0.0D, 8.0D, 8.0D, 4.0D, 16.0D),
                        vsSpikeN,
                        Block.box(2.0D, 4.0D, 4.5D, 4.0D, 16.0D, 11.5D)
                )
        });
    }

    /**
     * @return In the order none, under, above, both.
     */
    protected static VoxelShape[] makePlasteredStoneColumnShapes() {
        return new VoxelShape[]{
                Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D),
                Shapes.or(
                        Block.box(1.0D, 8.0D, 1.0D, 15.0D, 16.0D, 15.0D),
                        Block.box(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D)),
                Shapes.or(
                        Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
                        Block.box(3.0D, 4.0D, 3.0D, 13.0D, 16.0D, 13.0D)),
                Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D)};
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * {0 : NW Outer, 1 : N Default, 2 : NW Inner}
     */
    protected static VoxelShape[] makeRedSculptedPlasteredStoneFriezeShapes() {
        VoxelShape vsQtrN = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 8.0D);
        VoxelShape vsSpikeN = Block.box(4.0D, 4.0D, 4.0D, 12.0D, 13.0D, 7.0D);
        return Utils.generateHorizontalShapes(new VoxelShape[]{
                Shapes.or(
                        Block.box(0.0D, 0.0D, 0.0D, 8.0D, 4.0D, 8.0D),
                        Block.box(4.0D, 2.0D, 4.0D, 12.0D, 13.0D, 12.0D)),
                Shapes.or(vsQtrN, vsSpikeN),
                Shapes.or(
                        vsQtrN,
                        Block.box(0.0D, 0.0D, 8.0D, 8.0D, 4.0D, 16.0D),
                        vsSpikeN,
                        Block.box(4.0D, 4.0D, 4.0D, 7.0D, 13.0D, 12.0D)
                )
        });
    }

    /**
     * @return Stores VoxelShape for "South" with index : <p/>
     * {0 : S Lone, 1 : S Under, 2 : S Above, 3 : S Both}
     */
    protected static VoxelShape[] makeSerpentSculptedColumnShapes() {
        VoxelShape vsHead = Block.box(4.0D, 0.0D, 6.0D, 12.0D, 9.0D, 16.0D);
        VoxelShape vsTail = Block.box(5.0D, 0.0D, 0.0D, 11.0D, 16.0D, 6.0D);
        return Utils.generateHorizontalShapes(new VoxelShape[]{
                Shapes.or(vsHead, Block.box(5.0D, 0.0D, 0.0D, 11.0D, 6.0D, 6.0D)),
                Shapes.or(vsTail, Block.box(5.0D, 10.0D, 6.0D, 11.0D, 16.0D, 15.0D)),
                Shapes.or(vsHead, vsTail),
                vsTail
        });
    }

    protected static VoxelShape[] makeMarbleColumnShapes() {
        return new VoxelShape[]{
                Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D),
                Shapes.or(
                        Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
                        Block.box(3.0D, 4.0D, 3.0D, 13.0D, 6.0D, 13.0D),
                        Block.box(4.0D, 6.0D, 4.0D, 12.0D, 16.0D, 12.0D)),
                Shapes.or(
                        Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D),
                        Block.box(2.0D, 10.0D, 1.0D, 14.0D, 14.0D, 15.0D),
                        Block.box(2.0D, 14.0D, 2.0D, 14.0D, 16.0D, 14.0D)),
                Shapes.or(
                        Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D),
                        Block.box(1.0D, 10.0D, 2.0D, 15.0D, 14.0D, 14.0D),
                        Block.box(2.0D, 14.0D, 2.0D, 14.0D, 16.0D, 14.0D))};
    }

    /**
     * @return Stores VoxelShape for "South" with index : <p/>
     * {0 : S Lone, 1 : S Under, 2 : S Above, 3 : S Both}
     */
    protected static VoxelShape[] makeMarbleSidedColumnShapes() {
        VoxelShape vsColumn = Block.box(4.0D, 0.0D, 0.0D, 12.0D, 16.0D, 4.0D);
        return Utils.generateHorizontalShapes(new VoxelShape[]{
                vsColumn,
                Shapes.or(
                        Block.box(4.0D, 0.0D, 0.0D, 12.0D, 10.0D, 4.0D),
                        Block.box(1.0D, 10.0D, 0.0D, 15.0D, 14.0D, 6.0D),
                        Block.box(2.0D, 14.0D, 0.0D, 14.0D, 16.0D, 6.0D)),
                Shapes.or(
                        Block.box(2.0D, 0.0D, 0.0D, 14.0D, 4.0D, 6.0D),
                        Block.box(3.0D, 4.0D, 0.0D, 13.0D, 6.0D, 5.0D),
                        Block.box(4.0D, 6.0D, 0.0D, 12.0D, 16.0D, 4.0D)),
                vsColumn
        });
    }

    /**
     * @return Stores VoxelShape for "South" with index : <p/>
     * {0 : S Lone, 1 : S Under, 2 : S Above, 3 : S Both}
     */
    protected static VoxelShape[] makeSandstoneSidedColumnShapes() {
        VoxelShape vsColumn = Block.box(4.0D, 0.0D, 0.0D, 12.0D, 16.0D, 4.0D);
        return Utils.generateHorizontalShapes(new VoxelShape[]{
                vsColumn,
                Shapes.or(
                        Block.box(4.0D, 0.0D, 0.0D, 12.0D, 8.0D, 4.0D),
                        Block.box(2.0D, 8.0D, 0.0D, 14.0D, 12.0D, 6.0D),
                        Block.box(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 8.0D)),
                Shapes.or(
                        Block.box(2.0D, 0.0D, 0.0D, 14.0D, 8.0D, 6.0D),
                        Block.box(4.0D, 8.0D, 0.0D, 12.0D, 16.0D, 4.0D)),
                vsColumn
        });
    }

    /**
     * @return Stores VoxelShape with index : <p/>
     * {0: axis_x, 1: axis_z, 2: axis_xz, 3-8: axis_y, 9-14: axis_xy, 15-20: axis_yz, 21-27: axis_xyz}
     */
    protected static VoxelShape[] makeIronColumnShapes() {
        VoxelShape axisY = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
        VoxelShape axisX = Block.box(0.0D, 7.0D, 7.0D, 16.0D, 9.0D, 9.0D);
        VoxelShape axisZ = Block.box(7.0D, 7.0D, 0.0D, 9.0D, 9.0D, 16.0D);
        VoxelShape axisXZ = Shapes.or(axisX, axisZ);
        VoxelShape smallNone = Shapes.or(axisY,
                Block.box(6.0D, 0.0D, 6.0D, 10.0D, 2.0D, 10.0D),
                Block.box(6.5D, 15.0D, 6.5D, 9.5D, 16.0D, 9.5D));
        VoxelShape largeNone = Shapes.or(axisY,
                Block.box(6.0D, 0.0D, 6.0D, 10.0D, 2.0D, 10.0D),
                Block.box(6.0D, 12.0D, 6.0D, 10.0D, 15.0D, 10.0D),
                Block.box(4.0D, 15.0D, 4.0D, 12.0D, 16.0D, 12.0D));
        VoxelShape smallUnder = Shapes.or(axisY, Block.box(6.5D, 15.0D, 6.5D, 9.5D, 16.0D, 9.5D));
        VoxelShape largeUnder = Shapes.or(axisY,
                Block.box(6.0D, 8.0D, 6.0D, 10.0D, 10.0D, 10.0D),
                Block.box(6.0D, 12.0D, 6.0D, 10.0D, 15.0D, 10.0D),
                Block.box(4.0D, 15.0D, 4.0D, 12.0D, 16.0D, 12.0D));
        VoxelShape above = Shapes.or(axisY,
                Block.box(6.0D, 0.0D, 6.0D, 10.0D, 8.0D, 10.0D),
                Block.box(6.0D, 10.0D, 6.0D, 10.0D, 12.0D, 10.0D));
        VoxelShape both = Shapes.or(axisY, Block.box(6.5D, 15.0D, 6.5D, 9.5D, 16.0D, 9.5D));
        return new VoxelShape[]{axisX, axisZ, Shapes.or(axisX, axisZ),
                smallNone, largeNone, smallUnder, largeUnder, above, both,
                Shapes.or(axisX, smallNone), Shapes.or(axisX, largeNone), Shapes.or(axisX, smallUnder), Shapes.or(axisX, largeUnder), Shapes.or(axisX, above), Shapes.or(axisX, both),
                Shapes.or(axisZ, smallNone), Shapes.or(axisZ, largeNone), Shapes.or(axisZ, smallUnder), Shapes.or(axisZ, largeUnder), Shapes.or(axisZ, above), Shapes.or(axisZ, both),
                Shapes.or(axisXZ, smallNone), Shapes.or(axisXZ, largeNone), Shapes.or(axisXZ, smallUnder), Shapes.or(axisXZ, largeUnder), Shapes.or(axisXZ, above), Shapes.or(axisXZ, both)};
    }

    protected static VoxelShape[] makeStoneLanternShapes() {
        return Utils.generateHorizontalShapes(
                new VoxelShape[]{Shapes.or(
                        Block.box(4.0D, 1.0D, 4.0D, 12.0D, 2.0D, 12.0D),
                        Block.box(5.0D, 2.0D, 5.0D, 11.0D, 10.5D, 11.0D),
                        Block.box(7.0D, 6.0D, 0.0D, 9.0D, 16.0D, 9.0D),
                        Block.box(2.0D, 7.5D, 2.0D, 14.0D, 9.0D, 14.0D))
                },
                Shapes.or(
                        Block.box(7.0D, 0.0D, 7.0D, 9.0D, 13.5D, 9.0D),
                        Block.box(4.0D, 2.0D, 4.0D, 12.0D, 3.0D, 12.0D),
                        Block.box(5.0D, 3.0D, 5.0D, 11.0D, 11.5D, 11.0D),
                        Block.box(2.0D, 8.5D, 2.0D, 14.0D, 10.0D, 14.0D)),
                Shapes.or(
                        Block.box(7.0D, 0.0D, 7.0D, 9.0D, 13.5D, 9.0D),
                        Block.box(4.0D, 2.0D, 4.0D, 12.0D, 3.0D, 12.0D),
                        Block.box(5.0D, 3.0D, 5.0D, 11.0D, 11.5D, 11.0D),
                        Block.box(2.0D, 8.5D, 2.0D, 14.0D, 10.0D, 14.0D)));
    }

    protected static VoxelShape[] makeIronFancyLanternShapes() {
        return Utils.generateHorizontalShapes(
                new VoxelShape[]{Shapes.or(
                        Block.box(7.0D, 18.0D, 7.0D, 9.0D, 20.0D, 9.0D),
                        Block.box(6.0D, 17.0D, 6.0D, 10.0D, 18.0D, 10.0D),
                        Block.box(5.0D, 8.0D, 5.0D, 11.0D, 17.0D, 11.0D),
                        Block.box(6.0D, 1.0D, 0.0D, 10.0D, 9.0D, 1.0D),
                        Block.box(7.0D, 7.0D, 1.0D, 9.0D, 8.0D, 10.0D))
                },
                Shapes.or(
                        Block.box(7.0D, 12.0D, 7.0D, 9.0D, 16.0D, 9.0D),
                        Block.box(6.0D, 11.0D, 6.0D, 10.0D, 12.0D, 10.0D),
                        Block.box(5.0D, 2.0D, 5.0D, 11.0D, 11.0D, 11.0D),
                        Block.box(6.5D, 1.0D, 6.5D, 9.5D, 2.0D, 9.5D)),
                Shapes.or(
                        Block.box(7.0D, 12.0D, 7.0D, 9.0D, 14.0D, 9.0D),
                        Block.box(6.0D, 11.0D, 6.0D, 10.0D, 12.0D, 10.0D),
                        Block.box(5.0D, 2.0D, 5.0D, 11.0D, 11.0D, 11.0D),
                        Block.box(6.5D, 0.0D, 6.5D, 9.5D, 2.0D, 9.5D)));
    }
}
