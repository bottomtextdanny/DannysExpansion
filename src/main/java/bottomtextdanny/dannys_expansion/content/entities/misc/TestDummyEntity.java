package bottomtextdanny.dannys_expansion.content.entities.misc;

import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.dannys_expansion.tables.items.DEBuildingItems;
import bottomtextdanny.dannys_expansion._util.DEMath;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import bottomtextdanny.braincell.base.BCLerp;
import bottomtextdanny.braincell.base.ObjectFetcher;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimatableModule;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimatableProvider;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationHandler;
import bottomtextdanny.braincell.mod.entity.modules.animatable.SimpleAnimation;
import bottomtextdanny.braincell.mod._base.serialization.WorldPacketData;
import bottomtextdanny.braincell.mod._base.serialization.builtin.BuiltinSerializers;
import bottomtextdanny.braincell.mod.world.entity_utilities.EntityClientMessenger;
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
import org.apache.commons.lang3.mutable.MutableInt;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Map;

public class TestDummyEntity extends LivingEntity implements AnimatableProvider, EntityClientMessenger {
    public static final int DAMAGE_UPDATE_CALL = 0, ANIMATION_CALL = 1;
    public static final SimpleAnimation HURT = new SimpleAnimation(10);
    public final AnimatableModule animatableModule;
	public final AnimationHandler<TestDummyEntity> mainAnimationModule;
    private final Object2FloatMap<MutableInt> damageMap = new Object2FloatOpenHashMap<>();
    private float displayDamageDirection;
    private float displayDamage;
    public float hitYaw;

    public TestDummyEntity(EntityType<? extends LivingEntity> type, Level worldIn) {
        super(type, worldIn);
        this.animatableModule = new AnimatableModule(this, HURT);
        this.mainAnimationModule = addAnimationHandler(new AnimationHandler<>(this));
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

            this.damageMap.object2FloatEntrySet().removeIf(entry -> {

		        if (entry.getKey().getValue() > 0) {
			        entry.getKey().decrement();
			        return false;
		        }
                updateDisplayDamage();
                return true;
	        });
        }


        if (this.level.isClientSide()) {
            this.displayDamage = Math.max(BCLerp.get(0.5F, this.displayDamage, this.displayDamageDirection), this.displayDamageDirection);
        }
    }
    
    public void updateDisplayDamage() {
	    if (!this.damageMap.isEmpty()) {
		    int highestTimeFactor = 0;
		    int lowestTimeFactor = 60;
		    float damage = 0;

            for (Map.Entry<MutableInt, Float> entry : this.damageMap.entrySet()) {
                int realTime = entry.getKey().getValue();
                if (realTime <= 1) continue;
                if (lowestTimeFactor > realTime) {
                    lowestTimeFactor = realTime;
                }

                if (highestTimeFactor < realTime) {
                    highestTimeFactor = realTime;
                }
                damage += entry.getValue();
            }
		
		    float difference = ((highestTimeFactor - lowestTimeFactor) + (float) (highestTimeFactor - lowestTimeFactor) / 3) * 0.05F;
		
		
		    if (difference < 1) difference = 1;
            this.displayDamageDirection = damage / difference;
	    } else
            this.displayDamageDirection = 0;
    }
    
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!net.minecraftforge.common.ForgeHooks.onLivingAttack(this, source, amount)) return false;
        if (this.invulnerableTime > 0) return false;

        if (source == DamageSource.IN_WALL) return false;

        if (source == DamageSource.LAVA) {
            removeAfterChangingDimensions();
            return false;
        }

        if (!this.level.isClientSide()) {
            amount = net.minecraftforge.common.ForgeHooks.onLivingDamage(this, source, amount);
            amount = net.minecraftforge.common.ForgeHooks.onLivingHurt(this, source, amount);
        }

        this.playSound(getHurtSound(source), 1.0F, 1.0F + this.random.nextFloat() * 0.1F);

        boolean flag = super.hurt(source, amount);
        
        if (!this.level.isClientSide()) {
            this.damageMap.putIfAbsent(new MutableInt(60), amount);
            updateDisplayDamage();
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
            this.damageMap.putIfAbsent(new MutableInt(60), fetcher.get(0, Float.class));
            updateDisplayDamage();
        } else if (flag == ANIMATION_CALL) {
            this.hitYaw = fetcher.get(0, Float.class);
            this.mainAnimationModule.play(HURT);
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
