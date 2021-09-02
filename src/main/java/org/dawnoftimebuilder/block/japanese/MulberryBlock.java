package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.BlockState;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Food;
import net.minecraft.state.properties.Half;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.PlantType;
import org.dawnoftimebuilder.block.templates.DoubleGrowingBushBlock;
import org.dawnoftimebuilder.util.DoTBConfig;
import org.dawnoftimebuilder.util.DoTBFoods;

import java.util.Random;

import static net.minecraftforge.common.PlantType.Plains;
import static org.dawnoftimebuilder.registry.DoTBEntitiesRegistry.SILKMOTH_ENTITY;

public class MulberryBlock extends DoubleGrowingBushBlock {

    public MulberryBlock(String seedName, PlantType plantType, int cutAge, int growingAge) {
        this(seedName, plantType, growingAge, cutAge, null);
    }

    public MulberryBlock(String seedName, PlantType plantType, int cutAge, int growingAge, Food food) {
        super(seedName, plantType, growingAge, cutAge, food);
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
