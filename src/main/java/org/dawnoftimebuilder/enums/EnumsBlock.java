package org.dawnoftimebuilder.enums;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;

public class EnumsBlock {

    public enum EnumHorizontalAxis implements IStringSerializable {
        AXIS_X("axis_x", 0),
        AXIS_Z("axis_z", 1);

        private final String name;
        private final int meta;

        EnumHorizontalAxis(String name, int meta){
            this.name = name;
            this.meta = meta;
        }

        public String toString()
        {
            return this.name;
        }

        public String getName()
        {
            return this.name;
        }

        public int getMeta()
        {
            return this.meta;
        }

        /**
         * Return the horizontalAxis from the EnumFacing. If the direction is on Y axis, return the default direction X.
         */
        public static EnumHorizontalAxis getHorizontalAxis(EnumFacing direction){
            return (direction.getAxis() == EnumFacing.Axis.Z) ? AXIS_Z : AXIS_X;
        }
    }

    public enum EnumHorizontalConnection implements IStringSerializable {
        LEFT("left", 0),
        RIGHT("right", 1),
        BOTH("both", 2),
        NONE("none", 3);

        private final String name;
        private final int meta;

        EnumHorizontalConnection(String name, int meta){
            this.name = name;
            this.meta = meta;
        }

        public String toString()
        {
            return this.name;
        }

        public String getName()
        {
            return this.name;
        }

        public int getMeta()
        {
            return this.meta;
        }
    }

    public enum EnumVerticalConnection implements IStringSerializable {
        ABOVE("above", 0),
        UNDER("under", 1),
        BOTH("both", 2),
        NONE("none", 3);

        private final String name;
        private final int meta;

        EnumVerticalConnection(String name, int meta){
            this.name = name;
            this.meta = meta;
        }

        public String toString()
        {
            return this.name;
        }

        public String getName()
        {
            return this.name;
        }

        public int getMeta()
        {
            return this.meta;
        }
    }

    public enum EnumUnderConnection implements IStringSerializable {
        NOTHING("nothing", 0),
        FOUR_PX("4_pixels", 1),
        EIGHT_PX("8_pixels", 2),
        TEN_PX("10_pixels", 3);

        private final String name;
        private final int meta;

        EnumUnderConnection(String name, int meta){
            this.name = name;
            this.meta = meta;
        }

        public String toString()
        {
            return this.name;
        }

        public String getName()
        {
            return this.name;
        }

        public int getMeta()
        {
            return this.meta;
        }
    }

    public enum EnumStairsShape implements IStringSerializable {
        STRAIGHT("straight"),
        INNER_LEFT("inner_left"),
        INNER_RIGHT("inner_right"),
        OUTER_LEFT("outer_left"),
        OUTER_RIGHT("outer_right");

        private final String name;

        EnumStairsShape(String name)
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

    public enum EnumCornerShape implements IStringSerializable {
        NONE("none"),
        LEFT("left"),
        RIGHT("right"),
        BOTH("both"),
        FULL("full");

        private final String name;

        EnumCornerShape(String name)
        {
            this.name = name;
        }

        public String toString(){
            return this.name;
        }

        public String getName()
        {
            return this.name;
        }
    }

    public enum EnumHalf implements IStringSerializable {
        TOP("top"),
        BOTTOM("bottom");

        private final String name;

        EnumHalf(String name)
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
}
