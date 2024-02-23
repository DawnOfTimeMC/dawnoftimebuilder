package org.dawnoftimebuilder.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraftforge.common.PlantType;
import org.dawnoftimebuilder.DoTBConfig;
import org.dawnoftimebuilder.block.templates.DoubleGrowingBushBlock;

import static net.minecraft.world.entity.MobSpawnType.SPAWNER;
import static org.dawnoftimebuilder.registry.DoTBEntitiesRegistry.SILKMOTH_ENTITY;

public class MulberryBlock extends DoubleGrowingBushBlock {
    public MulberryBlock(PlantType plantType, int cutAge, int growingAge) {
        super(plantType, cutAge, growingAge);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        super.randomTick(state, worldIn, pos, random);
        if(state.getValue(HALF) == Half.TOP) {
            if(random.nextInt(DoTBConfig.SILKMOTH_SPAWN_CHANCE.get()) == 0) {
                SILKMOTH_ENTITY.get().spawn(worldIn, (ItemStack) null, null, pos, SPAWNER, false, true);
            }
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState p_149653_1_) {
        return true;
    }
}
