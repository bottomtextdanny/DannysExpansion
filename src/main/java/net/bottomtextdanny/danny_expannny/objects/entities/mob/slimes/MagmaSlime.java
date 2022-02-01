package net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes;

import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data.SlimeHopAnimationInput;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data.SlimeHopMovementInput;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data.SlimeHopSoundInput;
import net.bottomtextdanny.danny_expannny.objects.entities.modules.critical_effect_provider.LavaCriticalProvider;
import net.bottomtextdanny.danny_expannny.objects.items.GelItem;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.RandomIntegerMapper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

import java.util.Random;

public class MagmaSlime extends AbstractSlime implements LavaCriticalProvider {
    public static final SlimeHopMovementInput HOP_MOVEMENT = new SlimeHopMovementInput(
            0.7F,
            0.11F,
            RandomIntegerMapper.of(30)
    );

    public MagmaSlime(EntityType<? extends MagmaSlime> type, Level worldIn) {
        super(type, worldIn);
    }

    public static AttributeSupplier.Builder Attributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.isFire()) {
            return false;
        }
        return super.hurt(source, amount);
    }

    public boolean isOnFire() {
        return false;
    }

    public boolean isSensitiveToWater() {
        return true;
    }

    public static boolean canSpawn(EntityType<MagmaSlime> entityType, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random rand) {
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && worldIn.getBlockState(pos.below()).getBlock() != Blocks.NETHER_WART_BLOCK;
    }

    @Override
    public float getGelAmount() {
        return 2.3F;
    }

    @Override
    public GelItem.Model getGelVariant() {
        return GelItem.Model.MAGMA;
    }

    @Override
    public SlimeHopSoundInput hopSoundsInput() {
        return DEFAULT_SOUND_INPUT;
    }

    @Override
    public SlimeHopAnimationInput hopAnimationInput() {
        return DEFAULT_HOP_ANIMATION_INPUT;
    }

    @Override
    public SlimeHopMovementInput hopInput() {
        return HOP_MOVEMENT;
    }


    @Override
    public boolean isSurfaceSlime() {
        return false;
    }
}
