package net.bottomtextdanny.dannys_expansion.common.Entities.spell;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.danny_expannny.object_tables.DEParticles;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.danny_expannny.objects.sound_instances.GolemDroneLoopSound;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.ExternalMotion;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.EntityUtil;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class GolemDroneEntity extends SpellEntity {
    public final Animation hurtAnimation = addAnimation(new Animation(8));
    @OnlyIn(Dist.CLIENT)
    GolemDroneLoopSound tchktchk;
    public byte hitCounter;
    public Timer invTimer = new Timer(10);

    public GolemDroneEntity(EntityType<? extends GolemDroneEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
        this.externalMotion = new ExternalMotion(0.8F);

        setLifeTime(200);
    }

    @Override
    public void tick() {
        super.tick();
        this.invTimer.tryUp();
        if (!this.level.isClientSide()) {

            sendClientMsg(0);

            if (this.hitCounter > 4) {
                setDeath();
            }

        }

        if (casterHasAttackTarget()) {
            float yaw = DEMath.getTargetYaw(this, getCasterTarget());
            float pitch = DEMath.getTargetPitch(this, getCasterTarget());
            this.setRotations(yaw, pitch);

            setDeltaMovement(0.35 * this.forward.x, 0.35 * this.forward.y, 0.35 * this.forward.z);

            List<LivingEntity> entities = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox());
            List<Projectile> entities1 = this.level.getEntitiesOfClass(Projectile.class, this.getBoundingBox());

            entities.removeIf(livingEntity -> livingEntity == getCaster());

            if (!entities.isEmpty() || !entities1.isEmpty()) {
                setDeath();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        super.clientCallOutHandler(flag, fetcher);
        if (flag == 0) {
            if (this.tchktchk == null) {
                this.tchktchk = new GolemDroneLoopSound(this);
                Minecraft.getInstance().getSoundManager().play(this.tchktchk);
            }

            if (this.tchktchk.isStopped() && Minecraft.getInstance().player.distanceTo(this) < 20) {

                this.tchktchk = new GolemDroneLoopSound(this);
                Minecraft.getInstance().getSoundManager().play(this.tchktchk);
            }

            if (this.random.nextFloat() < 0.3) {
                Vec3 vec0 = DEMath.fromPitchYaw(getXRot(), getYRot()).multiply(0.1, 0.1, 0.1);

                float xRandom = (float) this.random.nextGaussian() * 0.25F;
                float yRandom = (float) this.random.nextGaussian() * 0.25F;
                float zRandom = (float) this.random.nextGaussian() * 0.25F;

                this.level.addParticle(ParticleTypes.SMOKE, getX() + xRandom, getY() + yRandom, getZ() + zRandom, -vec0.x * 0.6, -vec0.y * 0.6, -vec0.z * 0.6);
            }
        }
    }

    @Override
    public boolean skipAttackInteraction(Entity entityIn) {
        if (this.invTimer.hasEnded()) {

            playSound(DESounds.ES_GOLEM_DRONE_HIT.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.05F);
            this.hitCounter++;
            this.mainHandler.play(this.hurtAnimation);
            this.invTimer.reset();

            if (entityIn instanceof Player) {
                Vec3 vec0 = DEMath.fromPitchYaw(0, DEMath.getTargetYaw(entityIn, this));
                this.externalMotion.setMotion(vec0.x, 0, vec0.z );
            }


            for (int i = 0; i < 5; i++) {
                float xRandom = (float) this.random.nextGaussian() * 0.25F;
                float yRandom = (float) this.random.nextGaussian() * 0.25F;
                float zRandom = (float) this.random.nextGaussian() * 0.25F;

                this.level.addParticle(ParticleTypes.SMOKE, getX() + xRandom, getY() + yRandom, getZ() + zRandom, 0, 0, 0);

            }
        }



        return super.skipAttackInteraction(entityIn);
    }

    @Override
    public boolean isPickable() {
        return true;
    }



    @Override
    public void onLifeEnd() {
        EntityUtil.particleAt(this.level, DEParticles.EXPLOSION.get(), 1, position().x, position().y + 0.25, position().z,0.0D, 0.0D, 0.0D, 0.0F);
        playSound(DESounds.ES_GOLEM_DRONE_EXPLODE.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.2F);
        List<LivingEntity> entities = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2));
        entities.removeIf(livingEntity -> livingEntity == getCaster());
        for(LivingEntity livingEntity : entities) {
            castersDamage(livingEntity, 8.0F);
            ModuledMob.disableShield(livingEntity, 50);
        }
    }
}
