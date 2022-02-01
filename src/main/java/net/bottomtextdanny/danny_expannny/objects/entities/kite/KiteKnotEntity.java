package net.bottomtextdanny.danny_expannny.objects.entities.kite;

import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManagerProvider;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.danny_expannny.objects.items.BaseKiteItem;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.bottomtextdanny.danny_expannny.capabilities.CapabilityHelper;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelCapability;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelWindModule;
import net.bottomtextdanny.dannys_expansion.core.interfaces.entity.EntityClientMessenger;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class KiteKnotEntity extends Entity implements EntityClientMessenger, BCDataManagerProvider {
    private final BCDataManager deDataManager = new BCDataManager(this);
    public static final EntityDataReference<ItemStack> KITE_ITEMSTACK_REF =
            BCDataManager.attribute(KiteKnotEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.ITEM_STACK,
                            () -> ItemStack.EMPTY,
                            "kite_itemstack")
            );
    public static final EntityDataReference<Vec3> KITE_POSITION_REF =
            BCDataManager.attribute(KiteKnotEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.VEC3,
                            () -> Vec3.ZERO,
                            "kite_position")
            );
    public static final EntityDataReference<Float> KITE_YAW_REF =
            BCDataManager.attribute(KiteKnotEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.FLOAT,
                            () -> 0.0F,
                            "kite_yaw")
            );
    public static final EntityDataReference<Float> KITE_PITCH_REF =
            BCDataManager.attribute(KiteKnotEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.FLOAT,
                            () -> 0.0F,
                            "kite_pitch")
            );
    public static final EntityDataReference<BlockPos> TILE_POSITION_REF =
            BCDataManager.attribute(KiteKnotEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.BLOCK_POS,
                            () -> BlockPos.ZERO,
                            "tile_pos")
            );
    private final EntityData<ItemStack> kite_itemstack;
    public final EntityData<Vec3> kite_position;
    public final EntityData<Float> kite_yaw;
    public final EntityData<Float> kite_pitch;
    private final EntityData<BlockPos> tile_pos;
    public KiteEntity kiteEntity;
    public int updateTick;


    public KiteKnotEntity(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
        this.kite_itemstack = bcDataManager().addSyncedData(EntityData.of(KITE_ITEMSTACK_REF));
        this.kite_position = bcDataManager().addSyncedData(EntityData.of(KITE_POSITION_REF));
        this.kite_yaw = bcDataManager().addSyncedData(EntityData.of(KITE_YAW_REF));
        this.kite_pitch = bcDataManager().addSyncedData(EntityData.of(KITE_PITCH_REF));
        this.tile_pos = bcDataManager().addNonSyncedData(EntityData.of(TILE_POSITION_REF));
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public boolean save(CompoundTag p_20224_) {
        if (this.kiteEntity != null) {
            this.kiteEntity.invalidate();
            this.kiteEntity.remove(RemovalReason.DISCARDED);
        }
        return super.save(p_20224_);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_20052_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_20139_) {

    }

    public void tick() {
        LevelCapability worldCapability = CapabilityHelper.get(this.level, LevelCapability.CAPABILITY);
        LevelWindModule windModule = worldCapability.getWindModule();

        if (!this.level.isClientSide) {
            if (isAlive() && (this.kiteEntity == null || this.kiteEntity.isRemoved())) {
                if (this.kite_itemstack.get() != null && this.kite_itemstack.get().getItem() instanceof  BaseKiteItem) {
                    KiteEntity kiteEntity = ((BaseKiteItem) this.kite_itemstack.get().getItem()).createKite(this.level, this.kite_itemstack.get());
                    kiteEntity.absMoveTo(this.kite_position.get().x, this.kite_position.get().y, this.kite_position.get().z, this.kite_yaw.get(), this.kite_pitch.get());
                    this.kiteEntity = kiteEntity;
                    kiteEntity.validate();

                    this.level.addFreshEntity(kiteEntity);
                    kiteEntity.knowDotDotDotLaugh(this);
                }
            }

            if (this.getY() < -64.0D) {
                this.outOfWorld();
            }

            if (this.updateTick == 20) {
                this.updateTick = 0;
                if (!this.isRemoved() && !onValidSurface(this.level, this.tile_pos.get())) {
                    this.remove(RemovalReason.DISCARDED);
                }
            }
            this.updateTick++;
        }

        refreshDimensions();

        setYRot(windModule.getWindDirection());

    }


    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        return super.interactAt(player, vec, hand);

    }

    @Override
    public boolean skipAttackInteraction(Entity entityIn) {
        return entityIn instanceof Player && this.hurt(DamageSource.playerAttack((Player) entityIn), 0.0F);
    }

    @Override
    public void positionRider(Entity passenger) {
    }

    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            if (!this.isRemoved() && !this.level.isClientSide) {
                this.remove(RemovalReason.DISCARDED);
                this.markHurt();
            }

            return true;
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        this.level.playLocalSound(this.getX(), Mth.floor(this.getY()) + 0.5F, this.getZ(), DESounds.ES_KITE_DETACH.get(), SoundSource.BLOCKS, 1.0F, 1F + this.random.nextFloat() * 0.1F, false);
        super.remove(reason);
    }

    public void setupPositionAndItemstack(BlockPos pos, ItemStack stack) {
        setKnotPosition(pos.getX(), pos.getY(), pos.getZ());
        this.setPos((double) this.tile_pos.get().getX() + 0.5D, (double) this.tile_pos.get().getY() + 0.5D, (double) this.tile_pos.get().getZ() + 0.5D);
        this.kite_position.set(position());
        this.kite_itemstack.set(stack);
    }
    /**
     * Sets the x,y,z of the entity from the given parameters. Also seems to set up a bounding box.
     */
    public void setPos(double x, double y, double z) {
        super.setPos((double) Mth.floor(x) + 0.5D, (double)Mth.floor(y) + 0.34375D, (double)Mth.floor(z) + 0.5D);
    }

    public void setKnotPosition(int x, int y, int z) {
        this.tile_pos.set(new BlockPos(x, y, z));
    }

    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < 1024.0D;
    }

    @OnlyIn(Dist.CLIENT)
    public Vec3 getRopeHoldPosition(float partialTicks) {
        return this.getPosition(partialTicks).add(0.0D, 0.2D, 0.0D);
    }

    @Override
    public void push(double x, double y, double z) {
        super.push(x, y, z);
    }

    public static boolean onValidSurface(Level worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos).is(BlockTags.FENCES);
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public BCDataManager bcDataManager() {
        return this.deDataManager;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {}

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
