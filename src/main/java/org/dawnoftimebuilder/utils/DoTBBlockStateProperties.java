package org.dawnoftimebuilder.utils;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.IStringSerializable;

public class DoTBBlockStateProperties {

    public static final BooleanProperty BURNING = BooleanProperty.create("burning");
    public static final BooleanProperty CUT = BooleanProperty.create("cut");
    public static final BooleanProperty FULL = BooleanProperty.create("full");
    public static final BooleanProperty SUBAXIS_X = BooleanProperty.create("subaxis_x");
    public static final BooleanProperty SUBAXIS_Z = BooleanProperty.create("subaxis_z");
    public static final BooleanProperty SUBAXIS = BooleanProperty.create("subaxis");
    public static final EnumProperty<HorizontalConnection> HORIZONTAL_CONNECTION = EnumProperty.create("horizontal_connection", HorizontalConnection.class);
    public static final EnumProperty<PillarConnection> PILLAR_CONNECTION = EnumProperty.create("pillar_connection", PillarConnection.class);
    public static final EnumProperty<Slab> SLAB = EnumProperty.create("slab", Slab.class);
    public static final EnumProperty<VerticalConnection> VERTICAL_CONNECTION = EnumProperty.create("vertical_connection", VerticalConnection.class);

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

        public int getIndex(){
            return this.index;
        }
    }

    public enum PillarConnection implements IStringSerializable {
        NOTHING("nothing"),
        FOUR_PX("4_pixels"),
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

    public enum Slab implements IStringSerializable {
        BOTTOM("bottom"),
        TOP("top"),
        DOUBLE("double");

        private final String name;

        Slab(String name){
            this.name = name;
        }

        public String toString(){
            return this.name;
        }

        public String getName(){
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
}
