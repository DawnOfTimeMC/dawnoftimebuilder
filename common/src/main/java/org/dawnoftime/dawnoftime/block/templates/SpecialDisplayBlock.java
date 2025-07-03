package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.block.IBlockSpecialDisplay;

public class SpecialDisplayBlock extends WaterloggedBlock implements IBlockSpecialDisplay {

    public SpecialDisplayBlock(Properties properties, VoxelShape[] shapes) {
        super(properties, shapes);
    }
}