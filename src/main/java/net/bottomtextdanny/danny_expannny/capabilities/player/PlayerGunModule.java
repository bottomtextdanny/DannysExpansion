package net.bottomtextdanny.danny_expannny.capabilities.player;

import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.ClientInstance;
import net.bottomtextdanny.danny_expannny.objects.items.gun.GunItem;
import net.bottomtextdanny.danny_expannny.network.servertoclient.MSGUpdateGunCooldown;
import net.bottomtextdanny.braincell.mod.capability_helper.CapabilityModule;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

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

    public PlayerGunModule(PlayerCapability capability) {
        super("gun_handler", capability);
    }

    public void tick() {
        if (!getHolder().level.isClientSide) {
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
                        this.gunUseTicks = Integer.MAX_VALUE;
                        setGunScoping(ItemStack.EMPTY);
                        new MSGUpdateGunCooldown(getHolder().getId(), hand.ordinal()).sendTo(PacketDistributor.PLAYER.with(() -> (ServerPlayer) getHolder()));
                    }
                    break;
                }
            }
            if (needsUpdate) {
                this.gunCooldownTicks = Integer.MAX_VALUE;
                this.previousGun = getHolder().getItemInHand(updatedHand);
                setGunScoping(ItemStack.EMPTY);
                new MSGUpdateGunCooldown(getHolder().getId(), updatedHand.ordinal()).sendTo(PacketDistributor.PLAYER.with(() -> (ServerPlayer) getHolder()));
            }
        }

        if (this.previousGun.getItem() instanceof GunItem<?> gunItem) {
            if (gunItem.usageTime() > 0) {
                if (this.gunUseTicks < gunItem.usageTime()) {
                    this.gunUseTicks++;
                    gunItem.usageTick(getHolder(), this.previousGun, this.gunUseTicks);
                }
            }
            if (this.gunCooldownTicks > 0) {
                this.gunCooldownTicks--;
            }

            Connection.doClientSide(() -> {
                if (getHolder() == ClientInstance.player()) {
                    if (DannysExpansion.clientManager().cGunRetrieveFactor > 0) {
                        DannysExpansion.clientManager().cGunRetrieveFactor--;
                    }
                }
            });
        }

        this.prevRecoil = this.recoil;

        if (this.recoil > 0.0F) {
            this.recoil = Math.max(this.recoil * this.recoilMultiplier - this.recoilSubstract, 0.0F);
        }
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
    public void serializeNBT(CompoundTag nbt) {

    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
