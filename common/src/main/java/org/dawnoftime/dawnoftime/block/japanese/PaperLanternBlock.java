package org.dawnoftime.dawnoftime.block.japanese;

import org.dawnoftime.dawnoftime.block.templates.SpecialDisplayBlock;

import static org.dawnoftime.dawnoftime.util.VoxelShapes.PAPER_LANTERN_SHAPES;

public class PaperLanternBlock extends SpecialDisplayBlock {

    public PaperLanternBlock(Properties properties) {
        super(properties, PAPER_LANTERN_SHAPES);
    }

    @Override
    public boolean emitsLight() {
        return true;
    }
}
