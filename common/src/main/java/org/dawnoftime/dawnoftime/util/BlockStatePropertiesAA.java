package org.dawnoftime.dawnoftime.util;

import net.minecraft.util.StringRepresentable;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.WallSide;
import org.jetbrains.annotations.NotNull;

public class BlockStatePropertiesAA {
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
    public static final IntegerProperty STACK = IntegerProperty.create("stack", 1, 3);
    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 16);
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
}