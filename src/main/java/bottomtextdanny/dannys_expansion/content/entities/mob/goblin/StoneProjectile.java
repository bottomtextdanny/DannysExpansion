package bottomtextdanny.dannys_expansion.content.entities.mob.goblin;

import bottomtextdanny.braincell.base.ObjectFetcher;
import bottomtextdanny.braincell.base.scheduler.IntScheduler;
import bottomtextdanny.braincell.mod._base.serialization.WorldPacketData;
import bottomtextdanny.braincell.mod._base.serialization.builtin.BuiltinSerializers;
import bottomtextdanny.braincell.mod.network.Connection;
import bottomtextdanny.braincell.mod.world.entity_utilities.EntityClientMessenger;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class StoneProjectile extends AbstractArrow implements EntityClientMessenger {
    protected static int HIT_SOMETHING_FLAG = 0;
    public static float HIT_PARTICLE_AMOUNT = 15;
    private IntScheduler stuckRemovalDelay;

    public StoneProjectile(EntityType<? extends StoneProjectile> type, Level worldIn) {
        super(type, worldIn);
        setSoundEvent(SoundEvents.STONE_BREAK);
        this.pickup = Pickup.DISALLOWED;
        this.stuckRemovalDelay = IntScheduler.simple(40);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.isNoGravity() && !this.isNoPhysics()) {
            Vec3 delta = this.getDeltaMovement();
            this.setDeltaMovement(delta.x, delta.y + (double) 0.03F, delta.z);
        }

        if (this.level.isClientSide) {
            //do not use method reference
            Connection.doClientSide(() -> clientTick());
        }

        checkBlockCollisions();
    }

    protected void checkBlockCollisions() {
        if (getX() == xOld && getY() == yOld && getZ() == zOld) {
            this.stuckRemovalDelay.incrementFreely(1);
        } else {
            this.stuckRemovalDelay.reset();
        }

        if (this.stuckRemovalDelay.hasEnded() || this.horizontalCollision || this.verticalCollision) {
            this.remove(RemovalReason.KILLED);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void clientTick() {
        if (random.nextInt(3) == 0) {
            ((ClientLevel) this.level).addParticle(
                    new BlockParticleOption(ParticleTypes.BLOCK, Blocks.STONE.defaultBlockState()),
                    this.getX(),
                    this.getY() + 0.15625F,
                    this.getZ(),
                    0.0D,
                    0.0D,
                    0.0D);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult resultData) {
        super.onHitBlock(resultData);

        callClientHit(resultData);

        this.remove(RemovalReason.KILLED);
    }

    @Override
    protected void onHitEntity(EntityHitResult resultData) {
        //if both owner and hit are goblins whose variant are equal the hit will be ignored.
        if (getOwner() instanceof Goblin goblin
                && resultData.getEntity() instanceof Goblin targetGoblin
                && targetGoblin.variableModule().getForm() == goblin.variableModule().getForm()) {
            return;
        }

        resultData.getEntity().hurt(DamageSource.mobAttack((LivingEntity) getOwner()), 3.0F);

        callClientHit(resultData);

        this.remove(RemovalReason.KILLED);
    }

    private void callClientHit(HitResult result) {
        sendClientMsg(HIT_SOMETHING_FLAG, WorldPacketData.of(BuiltinSerializers.VEC3, result.getLocation()));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        if (flag == HIT_SOMETHING_FLAG) {
            Vec3 hitLocation = fetcher.get(0, Vec3.class);

            for (int i = 0; i < HIT_PARTICLE_AMOUNT; i++) {
                this.level.addParticle(
                        new BlockParticleOption(ParticleTypes.BLOCK, Blocks.STONE.defaultBlockState()),
                        hitLocation.x,
                        hitLocation.y + 0.15625F,
                        hitLocation.z,
                        random.nextGaussian() * 0.2F,
                        0.1D,
                        random.nextGaussian() * 0.2F);
            }
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
