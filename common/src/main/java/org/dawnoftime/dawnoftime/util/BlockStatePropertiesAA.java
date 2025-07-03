package org.dawnoftime.dawnoftime.util;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;


import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.WallSide;
import org.dawnoftime.dawnoftime.registry.DoTBBlocksRegistry;
import org.dawnoftime.dawnoftime.registry.DoTBItemsRegistry;
import org.jetbrains.annotations.NotNull;

public class BlockStatePropertiesAA {
    public static final BooleanProperty CUT = BooleanProperty.create("cut");
    public static final BooleanProperty ROLLED = BooleanProperty.create("rolled");
    public static final BooleanProperty AXIS_X = BooleanProperty.create("axis_x");
    public static final BooleanProperty AXIS_Y = BooleanProperty.create("axis_y");
    public static final BooleanProperty AXIS_Z = BooleanProperty.create("axis_z");
    public static final BooleanProperty SUBAXIS = BooleanProperty.create("subaxis");
    public static final BooleanProperty HAS_PILLAR = BooleanProperty.create("has_pillar");
    public static final BooleanProperty CENTER = BooleanProperty.create("center");
    public static final BooleanProperty NORTH_TRICKLE = BooleanProperty.create("north_trickle");
    public static final BooleanProperty EAST_TRICKLE = BooleanProperty.create("east_trickle");
    public static final BooleanProperty SOUTH_TRICKLE = BooleanProperty.create("south_trickle");
    public static final BooleanProperty WEST_TRICKLE = BooleanProperty.create("west_trickle");
    public static final BooleanProperty CENTER_TRICKLE = BooleanProperty.create("center_trickle");
    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");
    public static final BooleanProperty SMALL_TOP = BooleanProperty.create("small_top");
    public static final IntegerProperty MULTIBLOCK_0_2 = IntegerProperty.create("multiblock", 0, 2);
    public static final IntegerProperty MULTIBLOCK_3X = IntegerProperty.create("multiblock_3x", 0, 2);
    public static final IntegerProperty MULTIBLOCK_2Y = IntegerProperty.create("multiblock_2y", 0, 1);
    public static final IntegerProperty MULTIBLOCK_3Z = IntegerProperty.create("multiblock_3z", 0, 2);
    public static final IntegerProperty HUMIDITY_0_8 = IntegerProperty.create("humidity", 0, 8);
    public static final IntegerProperty AGE_0_6 = IntegerProperty.create("age", 0, 6);
    public static final IntegerProperty HEAT_0_4 = IntegerProperty.create("heat", 0, 4);
    public static final IntegerProperty SIZE_0_2 = IntegerProperty.create("size", 0, 2);
    public static final IntegerProperty SIZE_0_5 = IntegerProperty.create("size", 0, 5);
    public static final IntegerProperty STACK = IntegerProperty.create("stack", 1, 3);
    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 16);
    public static final EnumProperty<ClimbingPlant> CLIMBING_PLANT = EnumProperty.create("climbing_plant", ClimbingPlant.class);
    public static final EnumProperty<FencePillar> FENCE_PILLAR = EnumProperty.create("fence_pillar", FencePillar.class);
    public static final EnumProperty<HorizontalConnection> HORIZONTAL_CONNECTION = EnumProperty.create("horizontal_connection", HorizontalConnection.class);
    public static final EnumProperty<OpenPosition> OPEN_POSITION = EnumProperty.create("open_position", OpenPosition.class);
    public static final EnumProperty<PillarConnection> PILLAR_CONNECTION = EnumProperty.create("pillar_connection", PillarConnection.class);
    public static final EnumProperty<VerticalConnection> VERTICAL_CONNECTION = EnumProperty.create("vertical_connection", VerticalConnection.class);
    public static final EnumProperty<SquareCorners> CORNER = EnumProperty.create("corner", SquareCorners.class);
    public static final EnumProperty<WallSide> PILLAR_WALL = EnumProperty.create("pillar", WallSide.class);
    public static final EnumProperty<VerticalLimitedConnection> NORTH_STATE = EnumProperty.create("north_state", VerticalLimitedConnection.class);
    public static final EnumProperty<VerticalLimitedConnection> EAST_STATE = EnumProperty.create("east_state", VerticalLimitedConnection.class);
    public static final EnumProperty<VerticalLimitedConnection> SOUTH_STATE = EnumProperty.create("south_state", VerticalLimitedConnection.class);
    public static final EnumProperty<VerticalLimitedConnection> WEST_STATE = EnumProperty.create("west_state", VerticalLimitedConnection.class);
    public static final EnumProperty<WaterTrickleEnd> WATER_TRICKLE_END = EnumProperty.create("water_end", WaterTrickleEnd.class);

    public enum VerticalLimitedConnection implements StringRepresentable {
        NONE("none", 0),
        BOTTOM("bottom", 1),
        TOP("top", 2);
        private final String name;
        private final int index;

        VerticalLimitedConnection(final String name, final int index) {
            this.name = name;
            this.index = index;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }

        /**
         * @return 0 : NONE,
         * 1 : UNDER,
         * 2 : ABOVE,
         * 3 : BOTH
         */
        public int getIndex() {
            return this.index;
        }
    }

    public enum HorizontalConnection implements StringRepresentable {
        NONE("none", 0),
        LEFT("left", 1),
        RIGHT("right", 2),
        BOTH("both", 3);
        private final String name;
        private final int index;

        HorizontalConnection(final String name, final int index) {
            this.name = name;
            this.index = index;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }

        public int getIndex() {
            return this.index;
        }
    }

    public enum VerticalConnection implements StringRepresentable {
        NONE("none", 0),
        UNDER("under", 1),
        ABOVE("above", 2),
        BOTH("both", 3);
        private final String name;
        private final int index;

        VerticalConnection(final String name, final int index) {
            this.name = name;
            this.index = index;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }

        /**
         * @return 0 : NONE,
         * 1 : UNDER,
         * 2 : ABOVE,
         * 3 : BOTH
         */
        public int getIndex() {
            return this.index;
        }
    }

    public enum PillarConnection implements StringRepresentable {
        NOTHING("nothing"),
        FOUR_PX("4_pixels"),
        SIX_PX("6_pixels"),
        EIGHT_PX("8_pixels"),
        TEN_PX("10_pixels");
        private final String name;

        PillarConnection(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }

    public enum FencePillar implements StringRepresentable {
        NONE("none"),
        PILLAR_BIG("pillar_big"),
        PILLAR_SMALL("pillar_small"),
        CAP_PILLAR_BIG("cap_pillar_big");
        private final String name;

        FencePillar(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }

    public enum SquareCorners implements StringRepresentable {
        TOP_LEFT("top_left", -1, 1),
        TOP_RIGHT("top_right", 1, 1),
        BOTTOM_RIGHT("bottom_right", 1, -1),
        BOTTOM_LEFT("bottom_left", -1, -1);
        private final String name;
        private final int horizontal_offset;
        private final int vertical_offset;

        SquareCorners(final String name, final int horizontal_offset, final int vertical_offset) {
            this.name = name;
            this.horizontal_offset = horizontal_offset;
            this.vertical_offset = vertical_offset;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }

        /**
         * @param referenceCorner Corner used as reference
         * @return the offset to apply to the BlockPos horizontally to get the pos of the studied corner.
         */
        public int getHorizontalOffset(final SquareCorners referenceCorner) {
            return referenceCorner.horizontal_offset == this.horizontal_offset ? 0 : this.horizontal_offset;
        }

        /**
         * @param referenceCorner Corner used as reference
         * @return the offset to apply to the BlockPos vertically to get the pos of the studied corner.
         */
        public int getVerticalOffset(final SquareCorners referenceCorner) {
            return referenceCorner.vertical_offset == this.vertical_offset ? 0 : this.vertical_offset;
        }

        public boolean isTopCorner() {
            return this.vertical_offset == 1;
        }

        /**
         * @param vertically must be true if the adjacent corner must be above or under.
         * @return the adjacent SquareCorner vertically or horizontally.
         */
        public SquareCorners getAdjacentCorner(final boolean vertically) {
            return switch (this) {
                case TOP_RIGHT -> vertically ? BOTTOM_RIGHT : TOP_LEFT;
                case BOTTOM_RIGHT -> vertically ? TOP_RIGHT : BOTTOM_LEFT;
                case BOTTOM_LEFT -> vertically ? TOP_LEFT : BOTTOM_RIGHT;
                default -> vertically ? BOTTOM_LEFT : TOP_RIGHT;
            };
        }
    }

    public enum OpenPosition implements StringRepresentable {
        CLOSED("closed"),
        HALF("half"),
        FULL("full");
        private final String name;

        OpenPosition(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }

        public boolean isOpen() {
            return this != CLOSED;
        }
    }

    public enum WaterTrickleEnd implements StringRepresentable {
        STRAIGHT("straight"),
        FADE("fade"),
        SPLASH("splash");
        private final String name;

        WaterTrickleEnd(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }

    public enum ClimbingPlant implements StringRepresentable {
        NONE("none"),
        VINE("vine"),
        IVY("ivy"),
        GRAPE("grape", true, 4, 6, 0, 2, 2);
        //CLEMATIS("clematis", true, 4, 5, 1, 2, 2);
        private final String name;
        private final boolean cycle;
        private final int[] moonPhasePerAge;

        /**
         * These plants grow on blocks, from age 0 to 2 (or 6 if it follows a cycle).
         * 0 and 1 are the childhood of the planks.
         * If the plant follows a cycle, it will grow from 2 to 6, then come back to 2, and repeat.
         * The growth after age 2 is permitted only if the moon phase is between the required phase and 3 moon phases later.
         * Vanilla moon phases are 0, 1, 2, 3, 4, 5, 6, 7 (with 0 being Full Moon, and 4 New Moon).
         *
         * @param name  Plant's name.
         * @param cycle True if the plant can grow above age 2.
         * @param age2  Required moon phase to grow from 2 to 3.
         * @param age3  Required moon phase to grow from 3 to 4.
         * @param age4  Required moon phase to grow from 4 to 5.
         * @param age5  Required moon phase to grow from 5 to 6.
         * @param age6  Required moon phase to go back from 6 to 2.
         */
        ClimbingPlant(final String name, final boolean cycle, final int age2, final int age3, final int age4, final int age5, final int age6) {
            this.name = name;
            this.cycle = cycle;
            this.moonPhasePerAge = new int[]{
                    age2, age3, age4, age5, age6
            };
        }

        ClimbingPlant(final String name) {
            this(name, false, 0, 0, 0, 0, 0);
        }

        public static ClimbingPlant getFromItem(final Item item) {
            if (item == DoTBItemsRegistry.INSTANCE.GRAPE_SEEDS.get()) {
                return GRAPE;
            }
            //if(item == CLEMATIS_SEEDS.get()) return CLEMATIS;
            if (item == Blocks.VINE.asItem()) {
                return VINE;
            }
            if (item == DoTBBlocksRegistry.INSTANCE.IVY.get().asItem()) {
                return IVY;
            }
            return NONE;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }

        public int maxAge() {
            return this.cycle ? 6 : 2;
        }

        public boolean canGrow(final Level worldIn, int currentAge) {
            if (!this.cycle || currentAge < 2 || currentAge > 6) {
                return false;
            }
            currentAge -= 2;
            final int currentPhase = worldIn.dimensionType().moonPhase(worldIn.getDayTime());
            return (currentPhase - this.moonPhasePerAge[currentAge] + 8) % 8 < 4;
        }

        public boolean hasNoPlant() {
            return this == NONE;
        }
    }
}