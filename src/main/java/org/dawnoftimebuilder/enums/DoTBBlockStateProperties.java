package org.dawnoftimebuilder.enums;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.IStringSerializable;

public class DoTBBlockStateProperties {

    public static final BooleanProperty AXIS_X = BooleanProperty.create("axis_x");
    public static final BooleanProperty BURNING = BooleanProperty.create("burning");
    public static final EnumProperty<HorizontalConnection> HORIZONTAL_CONNECTION = EnumProperty.create("horizontal_connection", HorizontalConnection.class);

    public enum HorizontalConnection implements IStringSerializable {
        LEFT("left"),
        RIGHT("right"),
        BOTH("both"),
        NONE("none");

        private final String name;

        HorizontalConnection(String name){
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

    public enum VerticalConnection implements IStringSerializable {
        ABOVE("above"),
        UNDER("under"),
        BOTH("both"),
        NONE("none");

        private final String name;

        VerticalConnection(String name){
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

    public enum ConnectionWithPillar implements IStringSerializable {
        NOTHING("nothing"),
        FOUR_PX("4_pixels"),
        EIGHT_PX("8_pixels"),
        TEN_PX("10_pixels");

        private final String name;

        ConnectionWithPillar(String name){
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

    public enum CornerShape implements IStringSerializable {
        NONE("none"),
        LEFT("left"),
        RIGHT("right"),
        BOTH("both"),
        FULL("full");

        private final String name;

        CornerShape(String name)
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

    public enum Half implements IStringSerializable {
        TOP("top"),
        BOTTOM("bottom");

        private final String name;

        Half(String name)
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
