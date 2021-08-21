package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.templates.DoubleGrowingBushBlock;
import org.dawnoftimebuilder.entity.SilkmothEntity;

import java.util.Random;

import static net.minecraftforge.common.PlantType.Plains;

public class MulberryBlock extends DoubleGrowingBushBlock {

    public MulberryBlock() {
        super("mulberry", Plains, 3, 2);
    }

    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        super.tick(state, worldIn, pos, random);
        if(random.nextInt(20) == 0) SilkmothEntity.createEntity(worldIn);
    }
}
