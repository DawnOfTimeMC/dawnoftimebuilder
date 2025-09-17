package org.dawnoftime.dawnoftime.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA.VerticalLimitedConnection;

public class VoxelShapesBuilder {
    /**
     * Generates all orientations based
     * @param shapes Array of Shapes for the South rotation.
     * @param nonRotatedShapes Additional shapes that will be added at the end of the rotated Shapes.
     * @return A list of shapes ordered as following : South, West, North, East.
     */
    public static VoxelShape[] generateHorizontalShapes(final VoxelShape[] shapes, VoxelShape... nonRotatedShapes) {
        final VoxelShape[] newShape = {Shapes.empty()};
        final VoxelShape[] newShapes = new VoxelShape[shapes.length * 4 + nonRotatedShapes.length];
        int i = 0;
        // First we copy the provided array at the start of the new one.
        for (final VoxelShape shape : shapes) {
            newShapes[i] = shape;
            i++;
        }
        // Then rotate the provided array in each direction, and add it the new array.
        for (int rotation = 1; rotation < 4; rotation++) {
            int j = 0;
            for (final VoxelShape shape : shapes) {
                shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> newShape[0] = Shapes.or(newShape[0], Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
                shapes[j] = newShape[0];
                newShapes[i] = newShape[0];
                newShape[0] = Shapes.empty();
                i++;
                j++;
            }
        }
        // Lastly, we add the non-rotated shapes at the end of the array.
        for (final VoxelShape shape : nonRotatedShapes) {
            newShapes[i] = shape;
            i++;
        }
        return newShapes;
    }

    /**
     * Generates all the possible combination of the voxel shape passed in parameter for all horizontal rotations.
     * @param vsSouth : VoxelShape for the SOUTH Direction.
     * @return Array of VoxelShapes, with index : [0 and 15 → S+W+N+E, 1 → S, 2 → W, 3 → S+W, 4 → N, 5 → S+N, 6 → W+N, 7 → S+W+N,
     * 8 → E, 9 → S+E, 10 → W+E, 11 → S+W+E, 12 → N+E, 13 → S+N+E, 14 → W+N+E
     */
    public static VoxelShape[] generateFourSidesShapes(VoxelShape vsSouth) {
        VoxelShape[] horizontalShapes = generateHorizontalShapes(new VoxelShape[]{vsSouth});
        VoxelShape vsWest = horizontalShapes[1];
        VoxelShape vsNorth = horizontalShapes[2];
        VoxelShape vsEast = horizontalShapes[3];
        VoxelShape vsSW = Shapes.or(vsSouth, vsWest);
        VoxelShape vsWN = Shapes.or(vsWest,  vsNorth);
        VoxelShape vsNE = Shapes.or(vsNorth, vsEast);
        VoxelShape vsES = Shapes.or(vsEast,  vsSouth);
        VoxelShape all  = Shapes.or(vsSW, vsNE);

        return new VoxelShape[]{
                all,                          // 0
                vsSouth,                      // 1
                vsWest,                       // 2
                vsSW,                         // 3
                vsNorth,                      // 4
                Shapes.or(vsSouth, vsNorth),  // 5
                vsWN,                         // 6
                Shapes.or(vsSW, vsNorth),     // 7
                vsEast,                       // 8
                vsES,                         // 9
                Shapes.or(vsWest, vsEast),    // 10
                Shapes.or(vsSW, vsEast),      // 11
                vsNE,                         // 12
                Shapes.or(vsSouth, vsNE),     // 13
                Shapes.or(vsWN, vsEast)       // 14
        };
    }

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
            if ((i & 1) == 0) { // Check first bit : 0 → North true
                temp = Shapes.or(temp, vsNorth);
            }
            if ((i >> 1 & 1) == 0) { // Check second bit : 0 → East true
                temp = Shapes.or(temp, vsEast);
            }
            if ((i >> 2 & 1) == 0) { // Check third bit : 0 → South true
                temp = Shapes.or(temp, vsSouth);
            }
            if ((i >> 3 & 1) == 0) { // Check fourth bit : 0 → West true
                temp = Shapes.or(temp, vsWest);
            }
            if ((i >> 4 & 1) == 1) { // Check fifth bit : 1 → Pillar true
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
            if ((i & 1) == 0) { // Check first bit : 0 → North true
                temp = Shapes.or(temp, vsNorth);
            }
            if ((i >> 1 & 1) == 0) { // Check second bit : 0 → East true
                temp = Shapes.or(temp, vsEast);
            }
            if ((i >> 2 & 1) == 0) { // Check third bit : 0 → South true
                temp = Shapes.or(temp, vsSouth);
            }
            if ((i >> 3 & 1) == 0) { // Check fourth bit : 0 → West true
                temp = Shapes.or(temp, vsWest);
            }
            if ((i >> 4 & 1) == 1) { // Check fifth bit : 1 → Pillar true
                temp = Shapes.or(temp, vsPillar);
            }
            if ((i >> 5 & 1) == 1) { // Check fifth bit : 1 → Bottom true
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
            if ((i & 1) == 0) { // Check first bit : 0 → North true
                temp = Shapes.or(temp, vsNorth);
            }
            if ((i >> 1 & 1) == 0) { // Check second bit : 0 → East true
                temp = Shapes.or(temp, vsEast);
            }
            if ((i >> 2 & 1) == 0) { // Check third bit : 0 → South true
                temp = Shapes.or(temp, vsSouth);
            }
            if ((i >> 3 & 1) == 0) { // Check fourth bit : 0 → West true
                temp = Shapes.or(temp, vsWest);
            }
            if ((i >> 4 & 1) == 1) { // Check fifth bit : 1 → Pillar true
                temp = Shapes.or(temp, vsPillar);
            }
            shapes[i] = temp;
        }
        return shapes;
    }

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
                Shapes.or(vsAxisY, vsAxisX),
                Shapes.or(vsAxisY, vsAxisZ),
                Shapes.or(vsAxisY, vsAxisXZ),
                vsAxisY_bottom,
                Shapes.or(vsAxisY_bottom, vsAxisX),
                Shapes.or(vsAxisY_bottom, vsAxisZ),
                Shapes.or(vsAxisY_bottom, vsAxisXZ)
        };
    }

    protected static VoxelShape[] makePergolaShapes() {
        VoxelShape vsAxisX = Block.box(0.0D, 5.0D, 6.0D, 16.0D, 11.0D, 10.0D);
        VoxelShape vsAxisZ = Block.box(6.0D, 5.0D, 0.0D, 10.0D, 11.0D, 16.0D);
        VoxelShape vsAxisXZ = Shapes.or(vsAxisX, vsAxisZ);
        VoxelShape vsAxisY = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
        return new VoxelShape[]{
                vsAxisX,
                vsAxisZ,
                vsAxisXZ,
                vsAxisY,
                Shapes.or(vsAxisY, vsAxisX),
                Shapes.or(vsAxisY, vsAxisZ),
                Shapes.or(vsAxisY, vsAxisXZ),
        };
    }

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

    protected static VoxelShape[] makeWaxedOakTableShapes() {
        // South - West - North - East:
        VoxelShape[] vsSide = generateHorizontalShapes(new VoxelShape[]{Shapes.or(Block.box(1.0D, 1.0D, 13.5D, 15.0D, 3.0D, 14.5D), Block.box(1.0D, 12.0D, 13.5D, 15.0D, 16.0D, 14.5D))});
        // SW - NW - NE - SE:
        VoxelShape[] vsPillar = generateHorizontalShapes(new VoxelShape[]{Block.box(0.5D, 0.0D, 12.5D, 3.5D, 16.0D, 15.5D)});
        // W - N - E - S
        VoxelShape[] vsPillarLeft = generateHorizontalShapes(new VoxelShape[]{Block.box(0.0D, 0.0D, 13.0D, 1.0D, 16.0D, 15.0D)});
        // S - W - N - E
        VoxelShape[] vsPillarRight = generateHorizontalShapes(new VoxelShape[]{Block.box(1.0D, 0.0D, 15.0D, 3.0D, 16.0D, 16.0D)});
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

    protected static VoxelShape[] makeMultiblockFireplaceShapes() {
        return generateHorizontalShapes(new VoxelShape[]{
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D),
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 12.0D)
        });
    }

    protected static VoxelShape[] makeReliefShapes() {
        VoxelShape vsCenter = Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 4.0D);
        VoxelShape vsUnder = Block.box(4.0D, 0.0D, 0.0D, 12.0D, 4.0D, 4.0D);
        VoxelShape vsAbove = Block.box(4.0D, 12.0D, 0.0D, 12.0D, 16.0D, 4.0D);
        VoxelShape vsLeft = Block.box(0.0D, 4.0D, 0.0D, 4.0D, 12.0D, 4.0D);
        VoxelShape vsRight = Block.box(12.0D, 4.0D, 0.0D, 16.0D, 12.0D, 4.0D);
        VoxelShape vsVertical = Shapes.or(vsCenter, vsUnder, vsAbove);
        VoxelShape vsHorizontal = Shapes.or(vsCenter, vsLeft, vsRight);
        return generateHorizontalShapes(new VoxelShape[]{
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

    protected static VoxelShape[] makeSmallShutterShapes() {
        return generateHorizontalShapes(new VoxelShape[]{Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D),
                Block.box(-13.0D, 0.0D, 13.0D, 3.0D, 16.0D, 16.0D),
                Block.box(13.0D, 0.0D, 13.0D, 29.0D, 16.0D, 16.0D)});
    }

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

    protected static VoxelShape[] makeLimestoneSidedColumnShapes() {
        VoxelShape vsColumn = Shapes.or(
                Block.box(1.5D, 0.0D, 0.0D, 14.5D, 16.0D, 3.0D),
                Block.box(5.5D, 0.0D, 3.0D, 10.5D, 16.0D, 6.0D));
        return generateHorizontalShapes(new VoxelShape[]{
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

    protected static VoxelShape[] makeStoneBricksArrowslitShapes() {
        return generateHorizontalShapes(new VoxelShape[]{Shapes.or(
                Block.box(0.0D, 0.0D, 14.0D, 7.0D, 16.0D, 16.0D),
                Block.box(9.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D),
                Block.box(0.0D, 0.0D, 8.0D, 2.0D, 16.0D, 14.0D),
                Block.box(14.0D, 0.0D, 8.0D, 16.0D, 16.0D, 14.0D),
                Block.box(2.0D, 0.0D, 13.0D, 4.5D, 16.0D, 14.0D),
                Block.box(11.5D, 0.0D, 13.0D, 14.0D, 16.0D, 14.0D)
        )
        });
    }

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

    protected static VoxelShape[] makeStoneBricksMachicolationShapes() {
        VoxelShape floorVS = Block.box(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        return generateHorizontalShapes(new VoxelShape[]{
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

    protected static VoxelShape[] makeWaxedOakBalusterShapes() {
        VoxelShape vsNorthFlat = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
        VoxelShape vsEastFlat = Block.box(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vsSouthFlat = Block.box(0.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vsWestFlat = Block.box(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 16.0D);
        VoxelShape vsNorthWestCorner = Block.box(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 4.0D);
        VoxelShape vsNorthEastCorner = Block.box(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
        VoxelShape vsSouthEastCorner = Block.box(12.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);
        VoxelShape vsSouthWestCorner = Block.box(0.0D, 0.0D, 12.0D, 4.0D, 16.0D, 16.0D);
        return generateHorizontalShapes(new VoxelShape[]{
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
        return generateHorizontalShapes(new VoxelShape[]{
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
        return generateHorizontalShapes(new VoxelShape[]{
                Block.box(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D),
                Shapes.or(
                        Block.box(0.0D, 12.0D, 12.0D, 16.0D, 16.0D, 16.0D),
                        Block.box(0.0D, 9.0D, 9.0D, 16.0D, 13.0D, 13.0D),
                        Block.box(0.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D),
                        Block.box(0.0D, 3.0D, 3.0D, 16.0D, 7.0D, 7.0D))});
    }

    protected static VoxelShape[] makeCharredSpruceTallShuttersShapes() {
        return generateHorizontalShapes(new VoxelShape[]{
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
                        Block.box(2.0D, 14.0D, 2.0D, 14.0D, 16.0D, 14.0D),
                        Block.box(2.5D, 0.0D, 2.5D, 13.5D, 14.0D, 13.5D)),
                Shapes.or(
                        Block.box(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D),
                        Block.box(2.5D, 6.0D, 2.5D, 13.5D, 16.0D, 13.5D)),
                Block.box(2.5D, 0.0D, 2.5D, 13.5D, 16.0D, 13.5D)};
    }

    protected static VoxelShape[] makeSandstoneCrenelationShapes() {
        VoxelShape vsCrenelation = Shapes.or(
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 8.0D),
                Block.box(4.0D, 2.0D, 0.0D, 12.0D, 13.0D, 8.0D),
                Block.box(6.0D, 13.0D, 0.0D, 10.0D, 16.0D, 8.0D));
        return generateHorizontalShapes(new VoxelShape[]{
                Block.box(0.0D, 0.0D, 0.0D, 8.0D, 2.0D, 8.0D),
                vsCrenelation,
                Shapes.or(
                        vsCrenelation,
                        Block.box(0.0D, 0.0D, 8.0D, 8.0D, 2.0D, 16.0D),
                        Block.box(0.0D, 2.0D, 4.0D, 8.0D, 13.0D, 12.0D),
                        Block.box(0.0D, 13.0D, 6.0D, 8.0D, 16.0D, 10.0D))
        });
    }

    protected static VoxelShape[] makeGreenSculptedPlasteredStoneFriezeShapes() {
        VoxelShape vsQtrN = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 8.0D);
        VoxelShape vsSpikeN = Block.box(4.5D, 4.0D, 2.0D, 11.5D, 16.0D, 4.0D);
        return generateHorizontalShapes(new VoxelShape[]{
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

    protected static VoxelShape[] makeRedSculptedPlasteredStoneFriezeShapes() {
        VoxelShape vsQtrN = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 8.0D);
        VoxelShape vsSpikeN = Block.box(4.0D, 4.0D, 4.0D, 12.0D, 13.0D, 7.0D);
        return generateHorizontalShapes(new VoxelShape[]{
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

    protected static VoxelShape[] makeSerpentSculptedColumnShapes() {
        VoxelShape vsHead = Block.box(4.0D, 0.0D, 6.0D, 12.0D, 9.0D, 16.0D);
        VoxelShape vsTail = Block.box(5.0D, 0.0D, 0.0D, 11.0D, 16.0D, 6.0D);
        return generateHorizontalShapes(new VoxelShape[]{
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

    protected static VoxelShape[] makeMarbleSidedColumnShapes() {
        VoxelShape vsColumn = Block.box(4.0D, 0.0D, 0.0D, 12.0D, 16.0D, 4.0D);
        return generateHorizontalShapes(new VoxelShape[]{
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

    protected static VoxelShape[] makeSandstoneSidedColumnShapes() {
        VoxelShape vsColumn = Block.box(4.0D, 0.0D, 0.0D, 12.0D, 16.0D, 4.0D);
        return generateHorizontalShapes(new VoxelShape[]{
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
        return generateHorizontalShapes(
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
        return generateHorizontalShapes(
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

    public static VoxelShape[] generateWaterJetShapes(
            VoxelShape centerUp, VoxelShape centerDown,
            VoxelShape southTop, VoxelShape southBottom,
            VoxelShape westTop,  VoxelShape westBottom,
            VoxelShape northTop, VoxelShape northBottom,
            VoxelShape eastTop,  VoxelShape eastBottom
    ) {
        final int TOTAL = 324; // 2 (UP) * 2 (DOWN) * 3^4 (S/W/N/E)
        VoxelShape[] arr = new VoxelShape[TOTAL];

        for (int up = 0; up <= 1; up++) {
            for (int down = 0; down <= 1; down++) {
                for (int s = 0; s < 3; s++) {
                    for (int w = 0; w < 3; w++) {
                        for (int n = 0; n < 3; n++) {
                            for (int e = 0; e < 3; e++) {
                                int ternary = s + 3*w + 9*n + 27*e;
                                int idx = up + (down << 1) + 4 * ternary;

                                VoxelShape shape = Shapes.empty();
                                if (up == 1)   shape = Shapes.or(shape, centerUp);
                                if (down == 1) shape = Shapes.or(shape, centerDown);

                                if (s == 1) shape = Shapes.or(shape, southTop);
                                else if (s == 2) shape = Shapes.or(shape, southBottom);

                                if (w == 1) shape = Shapes.or(shape, westTop);
                                else if (w == 2) shape = Shapes.or(shape, westBottom);

                                if (n == 1) shape = Shapes.or(shape, northTop);
                                else if (n == 2) shape = Shapes.or(shape, northBottom);

                                if (e == 1) shape = Shapes.or(shape, eastTop);
                                else if (e == 2) shape = Shapes.or(shape, eastBottom);

                                // Safety: still return something selectable/collidable if empty
                                if (shape.isEmpty()) shape = Shapes.block();

                                arr[idx] = shape;
                            }
                        }
                    }
                }
            }
        }
        return arr;
    }

    /** Encode BlockState into the precomputed [0..323] index. */
    public static int encodeWaterJetIndex(
            boolean up, boolean down,
            VerticalLimitedConnection south, VerticalLimitedConnection west,
            VerticalLimitedConnection north, VerticalLimitedConnection east
    ) {
        int upDown = (up ? 1 : 0) + (down ? 2 : 0);
        int s = toTrit(south); // 0/1/2
        int w = toTrit(west);
        int n = toTrit(north);
        int e = toTrit(east);
        int ternary = s + 3*w + 9*n + 27*e;
        return upDown + 4 * ternary;
    }

    private static int toTrit(VerticalLimitedConnection v) {
        return switch (v) {
            case NONE -> 0;
            case TOP -> 1;
            case BOTTOM -> 2;
        };
    }
}
