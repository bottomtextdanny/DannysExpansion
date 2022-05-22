package bottomtextdanny.dannys_expansion._base.capabilities.player;

import bottomtextdanny.dannys_expansion._base.gun_rendering.GunClientData;
import bottomtextdanny.dannys_expansion._util._client.CMC;
import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._base.network.servertoclient.MSGUpdateGunCooldown;
import bottomtextdanny.dannys_expansion.content.items.gun.GunItem;
import bottomtextdanny.braincell.mod.capability.level.speck.ShootLighSpeck;
import bottomtextdanny.braincell.mod.graphics.point_lighting.SimplePointLight;
import bottomtextdanny.braincell.mod.network.Connection;
import bottomtextdanny.braincell.mod.capability.CapabilityModule;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Objects;

public class PlayerGunModule extends CapabilityModule<Player, PlayerCapability> {
    private float recoil;
    private float prevRecoil;
    private float recoilSubstract;
    private float recoilMultiplier;
    private ItemStack gunScoping = ItemStack.EMPTY;
    private ItemStack previousGun = ItemStack.EMPTY;
    private int gunUseTicks;
    private int gunCooldownTicks;
    @Nullable
    public ResourceLocation fireAnimation;
    public int fireTimer;
    public int fireTicks;

    public PlayerGunModule(PlayerCapability capability) {
        super("gun_handler", capability);
    }

    public void preTick() {
    }

    public void tick() {
        if (!getHolder().level.isClientSide)
            syncToClientIfShould();
        else if (fireTicks > 0) fireTicks--;
        else fireAnimation = null;

        if (this.previousGun.getItem() instanceof GunItem<?> gunItem) {
            int usageTime = gunItem.usageTime();

            if (usageTime > 0) {
                tickShoot(gunItem, usageTime);
            }

            if (this.gunCooldownTicks > 0) {
                this.gunCooldownTicks--;
            }

            tickRetrieveFactor();
        }

        this.prevRecoil = this.recoil;

        if (this.recoil > 0.0F) {
            this.recoil = Math.max(this.recoil * this.recoilMultiplier - this.recoilSubstract, 0.0F);
        }
    }

    private void tickShoot(GunItem<?> gunItem, int usageTime) {
        if (gunUseTicks >= 0 && this.gunUseTicks < usageTime) {
            this.gunUseTicks++;
            gunItem.usageTick(getHolder(), this, this.previousGun, this.gunUseTicks);
        }
    }

    private void syncToClientIfShould() {
        boolean needsUpdate = true;
        InteractionHand updatedHand = InteractionHand.MAIN_HAND;
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack handStack = getHolder().getItemInHand(hand);

            if (handStack.getItem() instanceof GunItem<?> gun) {
                needsUpdate = false;
                updatedHand = hand;
                if (handStack != this.previousGun) {
                    this.previousGun = handStack;
                    this.gunCooldownTicks = gun.cooldown();
                    this.gunUseTicks = -1;
                    setGunScoping(ItemStack.EMPTY);
                    new MSGUpdateGunCooldown(getHolder().getId(), hand.ordinal()).sendTo(PacketDistributor.PLAYER.with(() -> (ServerPlayer) getHolder()));
                }
                break;
            }
        }
        if (needsUpdate) {
            this.gunCooldownTicks = -1;
            this.previousGun = getHolder().getItemInHand(updatedHand);
            setGunScoping(ItemStack.EMPTY);
            new MSGUpdateGunCooldown(getHolder().getId(), updatedHand.ordinal()).sendTo(PacketDistributor.PLAYER.with(() -> (ServerPlayer) getHolder()));
        }
    }

    private void tickRetrieveFactor() {
        Connection.doClientSide(() -> {
            tickRetrieveFactorClient();
        });
    }

    @OnlyIn(Dist.CLIENT)
    private void tickRetrieveFactorClient() {
        GunClientData gunData = DannysExpansion.client().getGunData();
        if (!Minecraft.getInstance().isPaused() && getHolder() == CMC.player()) {
            if (gunData.retrieveFactor > 0) {
                gunData.retrieveFactor--;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void fireAnimation(ResourceLocation texture) {
        fireTimer = 3;
        fireTicks = 2;
        fireAnimation = texture;

        ShootLighSpeck light = new ShootLighSpeck(getHolder().level, 0, 1, 2);

        light.setPosition(getHolder().position().add(0.0, 1.5, 0.0));
        SimplePointLight renderedLight = new SimplePointLight(new Vec3(1.0F, 0.85F, 0.0F), 2.5F, 0.2F, 0.5F);
        light.setLight(renderedLight);

        light.addToLevel();
    }

    public void setPreviousGun(ItemStack previousGun) {
        this.previousGun = previousGun;
    }

    public void setGunUseTicks(int gunUseTicks) {
        this.gunUseTicks = gunUseTicks;
    }

    public void setGunCooldownTicks(int gunCooldownTicks) {
        this.gunCooldownTicks = gunCooldownTicks;
    }

    public void setRecoil(float recoil) {
        this.recoil = recoil;
    }

    public void setPrevRecoil(float prevRecoil) {
        this.prevRecoil = prevRecoil;
    }

    public void setRecoilMultiplier(float recoilMultiplier) {
        this.recoilMultiplier = recoilMultiplier;
    }

    public void setRecoilSubstract(float recoilSubstract) {
        this.recoilSubstract = recoilSubstract;
    }

    public void setGunScoping(ItemStack gunScoping) {
        this.gunScoping = Objects.requireNonNullElse(gunScoping, ItemStack.EMPTY);
    }

    public ItemStack getPreviousGun() {
        return this.previousGun;
    }

    public int getGunCooldownTicks() {
        return this.gunCooldownTicks;
    }

    public int getGunUseTicks() {
        return this.gunUseTicks;
    }

    public float getRecoil() {
        return this.recoil;
    }

    public float getPrevRecoil() {
        return this.prevRecoil;
    }

    public ItemStack getGunScoping() {
        return this.gunScoping;
    }

    @Override
    public void serializeNBT(CompoundTag nbt) {}

    @Override
    public void deserializeNBT(CompoundTag nbt) {}
}
