package org.dawnoftimebuilder.utils;

import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

import static org.dawnoftimebuilder.registries.DoTBItemsRegistry.GRAPE_SEEDS;

public class DoTBBlockStateProperties {

    public static final BooleanProperty BURNING = BooleanProperty.create("burning");
    public static final BooleanProperty CUT = BooleanProperty.create("cut");
    public static final BooleanProperty ROLLED = BooleanProperty.create("rolled");
    public static final BooleanProperty AXIS_X = BooleanProperty.create("axis_x");
    public static final BooleanProperty AXIS_Y = BooleanProperty.create("axis_y");
    public static final BooleanProperty AXIS_Z = BooleanProperty.create("axis_z");
    public static final BooleanProperty SUBAXIS = BooleanProperty.create("subaxis");

    public static final IntegerProperty AGE_0_6 = IntegerProperty.create("age", 0, 6);
    public static final IntegerProperty HEAT_0_3 = IntegerProperty.create("heat", 0, 3);
    public static final IntegerProperty SIZE_0_2 = IntegerProperty.create("size", 0, 2);
    public static final IntegerProperty SIZE_0_4 = IntegerProperty.create("size", 0, 4);
    public static final EnumProperty<ClimbingPlant> CLIMBING_PLANT = EnumProperty.create("climbing_plant", ClimbingPlant.class);
    public static final EnumProperty<FencePillar> FENCE_PILLAR = EnumProperty.create("fence_pillar", FencePillar.class);
    public static final EnumProperty<HorizontalConnection> HORIZONTAL_CONNECTION = EnumProperty.create("horizontal_connection", HorizontalConnection.class);
    public static final EnumProperty<OpenPosition> OPEN_POSITION = EnumProperty.create("open_position", OpenPosition.class);
    public static final EnumProperty<PillarConnection> PILLAR_CONNECTION = EnumProperty.create("pillar_connection", PillarConnection.class);
    public static final EnumProperty<SidedWindow> SIDED_WINDOW = EnumProperty.create("sided_window", SidedWindow.class);
    public static final IntegerProperty STACK = IntegerProperty.create("stack", 1, 3);
    public static final EnumProperty<VerticalConnection> VERTICAL_CONNECTION = EnumProperty.create("vertical_connection", VerticalConnection.class);
    public static final EnumProperty<SquareCorners> CORNER = EnumProperty.create("corner", SquareCorners.class);

    public enum HorizontalConnection implements IStringSerializable {
        NONE("none", 0),
        LEFT("left", 1),
        RIGHT("right", 2),
        BOTH("both", 3);

        private final String name;
        private final int index;

        HorizontalConnection(String name, int index){
            this.name = name;
            this.index = index;
        }

        public String toString()
        {
            return this.name;
        }

        public String getName()
        {
            return this.name;
        }

        public int getIndex(){
            return this.index;
        }
    }

    public enum VerticalConnection implements IStringSerializable {
        NONE("none", 0),
        UNDER("under", 1),
        ABOVE("above", 2),
        BOTH("both", 3);

        private final String name;
        private final int index;

        VerticalConnection(String name, int index){
            this.name = name;
            this.index = index;
        }

        public String toString()
        {
            return this.name;
        }

        public String getName()
        {
            return this.name;
        }

        /**
         * @return 0 : NONE,
         * 1 : UNDER,
         * 2 : ABOVE,
         * 3 : BOTH
         */
        public int getIndex(){
            return this.index;
        }
    }

    public enum PillarConnection implements IStringSerializable {
        NOTHING("nothing"),
        FOUR_PX("4_pixels"),
        SIX_PX("6_pixels"),
        EIGHT_PX("8_pixels"),
        TEN_PX("10_pixels");

        private final String name;

        PillarConnection(String name){
            this.name = name;
        }

        public String toString()
        {
            return this.name;
        }

        public String getName()
        {
            return this.name;
        }
    }

    public enum FencePillar implements IStringSerializable {
        NONE("none"),
        PILLAR_BIG("pillar_big"),
        PILLAR_SMALL("pillar_small"),
        CAP_PILLAR_BIG("cap_pillar_big");

        private final String name;

        FencePillar(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return this.name;
        }

        public String getName()
        {
            return this.name;
        }
    }

    public enum SquareCorners implements IStringSerializable {
        TOP_LEFT("top_left", -1, 1),
        TOP_RIGHT("top_right", 1, 1),
        BOTTOM_RIGHT("bottom_right", 1, -1),
        BOTTOM_LEFT("bottom_left", -1, -1);

        private final String name;
        private final int horizontal_offset;
        private final int vertical_offset;

        SquareCorners(String name, int horizontal_offset, int vertical_offset){
            this.name = name;
            this.horizontal_offset = horizontal_offset;
            this.vertical_offset = vertical_offset;
        }

        public String toString()
        {
            return this.name;
        }

        public String getName()
        {
            return this.name;
        }

        /**
         * @param referenceCorner Corner used as reference
         * @return the offset to apply to the BlockPos horizontally to get the pos of the studied corner.
         */
        public int getHorizontalOffset(SquareCorners referenceCorner) {
            return referenceCorner.horizontal_offset == this.horizontal_offset ? 0 : this.horizontal_offset;
        }

        /**
         * @param referenceCorner Corner used as reference
         * @return the offset to apply to the BlockPos vertically to get the pos of the studied corner.
         */
        public int getVerticalOffset(SquareCorners referenceCorner) {
            return referenceCorner.vertical_offset == this.vertical_offset ? 0 : this.vertical_offset;
        }

        public boolean isTopCorner() {
            return this.vertical_offset == 1;
        }

        public SquareCorners getAdjacentCorner(boolean vertically){
            switch (this) {
                default:
                case TOP_LEFT:
                    return vertically ? BOTTOM_LEFT : TOP_RIGHT;
                case TOP_RIGHT:
                    return vertically ? BOTTOM_RIGHT : TOP_LEFT;
                case BOTTOM_RIGHT:
                    return vertically ? TOP_RIGHT : BOTTOM_LEFT;
                case BOTTOM_LEFT:
                    return vertically ? TOP_LEFT : BOTTOM_RIGHT;
            }
        }
    }

    public enum OpenPosition implements IStringSerializable {
        CLOSED("closed"),
        HALF("half"),
        FULL("full");

        private final String name;

        OpenPosition(String name){
            this.name = name;
        }

        public String toString(){
            return this.name;
        }

        public String getName()
        {
            return this.name;
        }

        public boolean isOpen() {
            return this != CLOSED;
        }
	}

    public enum SidedWindow implements IStringSerializable {
        NORTH("north", Direction.NORTH),
        EAST("east", Direction.EAST),
        SOUTH("south", Direction.SOUTH),
        WEST("west", Direction.WEST),
        AXIS_X("axis_x", Direction.EAST),
        AXIS_Z("axis_z", Direction.NORTH);

        private final String name;
        private final Direction direction;

        SidedWindow(String name, Direction offset){
            this.name = name;
            this.direction = offset;
        }

        public String toString(){
            return this.name;
        }

        public String getName()
        {
            return this.name;
        }

        public Direction getDirection(){
            return this.direction;
        }

        public Direction getOffset() {
            return this.direction.rotateYCCW();
        }

        public static SidedWindow getSide(Direction facing, boolean isSneaking) {
            if(isSneaking) return (facing.getAxis() == Direction.Axis.X) ? AXIS_X : AXIS_Z;
            switch (facing){
                default:
                case NORTH:
                    return NORTH;
                case EAST:
                    return EAST;
                case SOUTH:
                    return SOUTH;
                case WEST:
                    return WEST;
            }
        }
    }

    public enum ClimbingPlant implements IStringSerializable {
        NONE("none"),
        VINE("vine"),
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
         * @param name Plant's name.
         * @param cycle True if the plant can grow above age 2.
         * @param age2 Required moon phase to grow from 2 to 3.
         * @param age3 Required moon phase to grow from 3 to 4.
         * @param age4 Required moon phase to grow from 4 to 5.
         * @param age5 Required moon phase to grow from 5 to 6.
         * @param age6 Required moon phase to go back from 6 to 2.
         */
        ClimbingPlant(String name, boolean cycle, int age2, int age3, int age4, int age5, int age6){
            this.name = name;
            this.cycle = cycle;
            this.moonPhasePerAge = new int[]{age2, age3, age4, age5, age6};
        }

        ClimbingPlant(String name){
            this(name, false, 0, 0, 0, 0, 0);
        }

        public String toString(){
            return this.name;
        }

        public String getName()
        {
            return this.name;
        }

        public boolean canGrow(World worldIn, int currentAge){
            if(!this.cycle) return false;
            if(currentAge < 2 || currentAge > 6) return false;
            currentAge -= 2;
            int currentPhase = worldIn.getDimension().getMoonPhase(worldIn.getDayTime());
            return (currentPhase - this.moonPhasePerAge[currentAge] + 8) % 8 < 4;
        }

        public boolean hasNoPlant(){
            return this == NONE;
        }

        public static ClimbingPlant getFromItem(Item item){
            if(item == GRAPE_SEEDS) return GRAPE;
            //if(item == CLEMATIS_SEEDS) return CLEMATIS;
            if(item == Blocks.VINE.asItem()) return VINE;
            return NONE;
        }
    }
}
