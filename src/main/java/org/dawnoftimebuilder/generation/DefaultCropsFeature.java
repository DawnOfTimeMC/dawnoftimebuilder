package org.dawnoftimebuilder.generation;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class DefaultCropsFeature  extends Feature<BlockClusterFeatureConfig> {
    public DefaultCropsFeature(Codec<BlockClusterFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(ISeedReader seedReader, ChunkGenerator generator, Random random, BlockPos pos, BlockClusterFeatureConfig config) {
        BlockState blockstate = config.stateProvider.getState(random, pos);
        int i = 0;

        for(int j = 0; j < config.tries; ++j) {
            BlockPos blockpos = this.getPos(random, pos, config);
            if(blockstate.getBlock() instanceof IWaterLoggable){
                if(seedReader.getBlockState(blockpos).getBlock().is(Blocks.WATER)){
                    if(blockpos.getY() < 255 && blockstate.canSurvive(seedReader, blockpos) && this.isValid(seedReader, blockpos, config)) {
                        config.blockPlacer.place(seedReader, blockpos, blockstate, random);
                        ++i;
                    }
                }
            }else{
                if(seedReader.isEmptyBlock(blockpos)){
                    if(blockpos.getY() < 255 && blockstate.canSurvive(seedReader, blockpos) && this.isValid(seedReader, blockpos, config)) {
                        config.blockPlacer.place(seedReader, blockpos, blockstate, random);
                        ++i;
                    }
                }
            }
        }

        return i > 0;
    }

    public boolean isValid(IWorld world, BlockPos pos, BlockClusterFeatureConfig config) {
        return !config.blacklist.contains(world.getBlockState(pos));
    }

    public BlockPos getPos(Random random, BlockPos pos, BlockClusterFeatureConfig config) {
        return pos.offset(random.nextInt(config.xspread) - random.nextInt(config.xspread), random.nextInt(config.yspread) - random.nextInt(config.yspread), random.nextInt(config.zspread) - random.nextInt(config.zspread));
    }
}