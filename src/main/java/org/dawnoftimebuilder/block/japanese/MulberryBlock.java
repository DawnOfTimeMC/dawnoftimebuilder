package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.BlockState;
import net.minecraft.entity.SpawnReason;
import net.minecraft.state.properties.Half;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dawnoftimebuilder.block.templates.DoubleGrowingBushBlock;
import org.dawnoftimebuilder.utils.DoTBConfig;

import java.util.Random;

import static net.minecraftforge.common.PlantType.Plains;
import static org.dawnoftimebuilder.registries.DoTBEntitiesRegistry.SILKMOTH_ENTITY;

public class MulberryBlock extends DoubleGrowingBushBlock {

    public MulberryBlock() {
        super("mulberry", Plains, 3, 2);
    }

    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        super.tick(state, worldIn, pos, random);
        if(state.get(HALF) == Half.TOP){
            if(random.nextInt(DoTBConfig.SILKMOTH_SPAWN_CHANCE.get()) == 0){
                SILKMOTH_ENTITY.spawn(worldIn, null, null, pos, SpawnReason.SPAWNER, false, true);
            }
        }
    }
}
