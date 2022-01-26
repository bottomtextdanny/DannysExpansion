package net.bottomtextdanny.danny_expannny.objects.entities;

import com.google.common.collect.Maps;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimatableModule;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimatableProvider;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationHandler;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.braincell.mod.serialization.WorldPacketData;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.danny_expannny.object_tables.items.DEBuildingItems;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.bottomtextdanny.dannys_expansion.core.interfaces.entity.ClientManager;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TestDummyEntity extends LivingEntity implements AnimatableProvider, ClientManager {
    public static final int DAMAGE_UPDATE_CALL = 0, ANIMATION_CALL = 1;
    public final AnimatableModule animatableModule;
	public final AnimationHandler<TestDummyEntity> mainAnimationModule;
	public final Animation hurtAnimation;
    private final Map<AtomicInteger, Float> damageMap = Maps.newHashMap();
    private final Timer clearDamageMapTimer = new Timer(60);
    private float displayDamage;
    public float hitYaw;

    public TestDummyEntity(EntityType<? extends LivingEntity> type, Level worldIn) {
        super(type, worldIn);
        this.animatableModule = new AnimatableModule(this);
        this.mainAnimationModule = addAnimationHandler(new AnimationHandler<>(this));
        this.hurtAnimation = addAnimation(new Animation(10));
    }

    public static AttributeSupplier.Builder Attributes() {
        return Mob.createMobAttributes()
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D);
    }

    @Override
    public AnimatableModule animatableModule() {
        return this.animatableModule;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.damageMap.isEmpty()) {
            this.clearDamageMapTimer.tryUpOrElse(t -> {
		        t.reset();
                this.damageMap.clear();
		        updateDisplayDamage();
	        });

            this.damageMap.entrySet().removeIf(entry -> {
		        if (entry.getKey().get() > 0) {
			        entry.getKey().set(entry.getKey().get() - 1);
			        return false;
		        }
		        else return true;
	        });
        }
    }
    
    public void updateDisplayDamage() {
	    if (!this.damageMap.isEmpty()) {
		    int highestTimeFactor = 0;
		    int lowestTimeFactor = 60;
		    float damage = 0;
	    	
		    for (Map.Entry<AtomicInteger, Float> entry : this.damageMap.entrySet()) {
			    int realTime =  entry.getKey().get();
			    if (lowestTimeFactor > realTime)
				    lowestTimeFactor = realTime;
			    if (highestTimeFactor < realTime)
				    highestTimeFactor = realTime;
			    damage += entry.getValue();
		    }
		
		    float difference = ((highestTimeFactor - lowestTimeFactor) + (float) (highestTimeFactor - lowestTimeFactor) / 3) * 0.05F;
		
		
		    if (difference < 1) difference = 1;
            this.displayDamage = damage / difference;
	    } else
            this.displayDamage = 0;
    }
    
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.invulnerableTime > 0) return false;

        if (!this.level.isClientSide()) {
            amount = net.minecraftforge.common.ForgeHooks.onLivingDamage(this, source, amount);
            amount = net.minecraftforge.common.ForgeHooks.onLivingHurt(this, source, amount);
        }

        if (source == DamageSource.LAVA) {
            removeAfterChangingDimensions();
            return false;
        }
        if (source == DamageSource.IN_WALL) return false;

        this.playSound(getHurtSound(source), 1.0F, 1.0F + this.random.nextFloat() * 0.1F);

        boolean flag = super.hurt(source, amount);
        
        if (!this.level.isClientSide()) {
            this.damageMap.putIfAbsent(new AtomicInteger(60), amount);
            updateDisplayDamage();
            this.clearDamageMapTimer.reset();
            sendClientMsg(DAMAGE_UPDATE_CALL, WorldPacketData.of(BuiltinSerializers.FLOAT, amount));

            if (source.getDirectEntity() != null) {
                this.hitYaw = DEMath.getTargetYaw(source.getDirectEntity().position().x, source.getDirectEntity().position().z, position().x, position().z);
            } else {
                this.hitYaw = getYRot();
            }
            
            sendClientMsg(ANIMATION_CALL, WorldPacketData.of(BuiltinSerializers.FLOAT, this.hitYaw));
        }

        this.invulnerableTime = 10;
        return flag;

    }

    @Override
    protected void actuallyHurt(DamageSource damageSrc, float damageAmount) {
    }

    public void refreshDimensions() {
        super.refreshDimensions();
    }

    public float getDPS() {
        return this.displayDamage;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        if (flag == DAMAGE_UPDATE_CALL) {
            this.clearDamageMapTimer.reset();
            this.damageMap.putIfAbsent(new AtomicInteger(60), fetcher.get(0, Float.class));
            updateDisplayDamage();
        } else if (flag == ANIMATION_CALL) {
            this.hitYaw = fetcher.get(0, Float.class);
            this.mainAnimationModule.play(this.hurtAnimation);
        }
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {

        if (player.isShiftKeyDown()) {
            ItemEntity itemEntity = new ItemEntity(this.level, getX(), getY(), getZ(), new ItemStack(DEBuildingItems.TEST_DUMMY.get()));
            itemEntity.push(0.0, 0.05, 0.0);
            this.level.addFreshEntity(itemEntity);

            this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), DESounds.ES_TEST_DUMMY_REMOVE.get(), SoundSource.BLOCKS, 0.75F, 1F + this.random.nextFloat() * 0.1F, false);
            remove(RemovalReason.DISCARDED);
            return InteractionResult.CONSUME;
        }

        return super.interactAt(player, vec, hand);
    }

    protected float tickHeadTurn(float p_110146_1_, float p_110146_2_) {
        this.yBodyRotO = this.yRotO;
        this.yBodyRot = this.getYRot();
        return 0.0F;
    }

    public double getMyRidingOffset() {
        return 0.0;
    }

    public void setYBodyRot(float offset) {
        this.yBodyRotO = this.yRotO = offset;
        this.yHeadRotO = this.yHeadRot = offset;
    }

    public void setYHeadRot(float rotation) {
        this.yBodyRotO = this.yRotO = rotation;
        this.yHeadRotO = this.yHeadRot = rotation;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected boolean canRide(Entity entityIn) {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void knockback(double strength, double ratioX, double ratioZ) {
    }

    @Override
    protected void doPush(Entity entityIn) {
        if (!this.isPassengerOfSameVehicle(entityIn)) {
            if (!entityIn.noPhysics && !this.noPhysics) {
                double d0 = entityIn.getX() - this.getX();
                double d1 = entityIn.getZ() - this.getZ();
                double d2 = Mth.absMax(d0, d1);
                if (d2 >= (double)0.01F) {
                    d2 = Mth.sqrt((float) d2);
                    d0 = d0 / d2;
                    d1 = d1 / d2;
                    double d3 = 1.0D / d2;
                    if (d3 > 1.0D) {
                        d3 = 1.0D;
                    }

                    d0 = d0 * d3;
                    d1 = d1 * d3;
                    d0 = d0 * (double)0.05F;
                    d1 = d1 * (double)0.05F;
                    if (!entityIn.isVehicle()) {
                        entityIn.push(d0, 0.0D, d1);
                    }
                }

            }
        }
    }

    @Override
    public void setDeltaMovement(double x, double y, double z) {
        super.setDeltaMovement(0, y, 0);
    }

    @Override
    public void push(double x, double y, double z) {
        super.push(0, y, 0);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return DESounds.ES_TEST_DUMMY_HIT.get();
    }
    
    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return new ArrayList<>(0);
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slotIn) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot slotIn, ItemStack stack) {}

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }
	
	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
