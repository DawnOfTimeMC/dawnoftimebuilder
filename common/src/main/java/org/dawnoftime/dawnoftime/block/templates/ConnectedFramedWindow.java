package org.dawnoftime.dawnoftime.block.templates;

import static org.dawnoftime.dawnoftime.util.VoxelShapes.CONNECTED_FRAMED_WINDOW_SHAPES;

public class ConnectedFramedWindow extends ConnectedVerticalSidedPlanBlock {

    public ConnectedFramedWindow(Properties properties) {
        super(properties, CONNECTED_FRAMED_WINDOW_SHAPES);
    }
}
