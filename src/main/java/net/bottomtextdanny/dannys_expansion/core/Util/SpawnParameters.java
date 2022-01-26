package net.bottomtextdanny.dannys_expansion.core.Util;

import net.bottomtextdanny.danny_expannny.object_tables.DEBlocks;
import net.bottomtextdanny.dannys_expansion.core.config.common.CommonConfigurationHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class SpawnParameters {
    public static boolean canMonsterSpawnInLight(EntityType<? extends Mob> type, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random randomIn) {
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && isValidLightLevel(worldIn, pos, randomIn) && canSpawnOn(type, worldIn, reason, pos, randomIn);
    }

    public static boolean canSpawnOn(EntityType<? extends Mob> typeIn, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random randomIn) {
        BlockPos blockpos = pos.below();
        return reason == MobSpawnType.SPAWNER || worldIn.getBlockState(blockpos).isValidSpawn(worldIn, blockpos, typeIn);
    }

    public static boolean isValidLightLevel(LevelAccessor worldIn, BlockPos pos, Random randomIn) {
        if (worldIn.getBrightness(LightLayer.SKY, pos) > randomIn.nextInt(32)) {
            return false;
        } else {
            int i = worldIn.getLevelData().isThundering() ? worldIn.getMaxLocalRawBrightness(pos, 10) : worldIn.getMaxLocalRawBrightness(pos);
            return i <= randomIn.nextInt(8);
        }
    }

    public static boolean canAnimalSpawn(EntityType<? extends LivingEntity> animal, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random random) {
        return worldIn.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) && worldIn.getRawBrightness(pos, 0) > 8;
    }

    public static boolean canMonsterSpawnInRain(EntityType<? extends Mob> type, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random randomIn) {
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && (worldIn.getLevelData().isRaining() || !CommonConfigurationHandler.CONFIG.sporerOnlySpawnInRain.get()) && canSpawnOn(type, worldIn, reason, pos, randomIn) && worldIn.getBiome(pos).warmEnoughToRain(pos);
    }

    public static boolean canMonsterSpawnInLavaHeight(EntityType<? extends Mob> type, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random randomIn) {
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && pos.getY() <= 36 && canSpawnOn(type, worldIn, reason, pos, randomIn);
    }

    public static boolean canSpawnInSurface(EntityType<? extends LivingEntity> entityType, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random rand) {
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && worldIn.canSeeSky(pos);
    }

    public static boolean canSpawnInIsland(EntityType<? extends Mob> entityType, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random rand) {
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && pos.getY() > 45;
    }

    public static boolean canSpawnInEmossence(EntityType<? extends Mob> entityType, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random rand) {
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && pos.getY() > 45 && worldIn.getBlockState(pos.below()).is(DEBlocks.EMOSSENCE.get());
    }

    public static boolean canSpawnInJungle(EntityType<? extends LivingEntity> entityType, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random rand) {
        BlockState blockState = worldIn.getBlockState(pos.below());
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && (blockState.is(BlockTags.LEAVES) || blockState == Blocks.GRASS_BLOCK.defaultBlockState());
    }

    public static boolean noRestriction(EntityType<? extends LivingEntity> entityType, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random rand) {
        return true;
    }
}
