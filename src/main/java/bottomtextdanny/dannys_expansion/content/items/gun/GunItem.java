package bottomtextdanny.dannys_expansion.content.items.gun;

import bottomtextdanny.dannys_expansion._base.gun_rendering.GunClientData;
import bottomtextdanny.dannys_expansion._util.tooltip.TooltipWriter;
import bottomtextdanny.dannys_expansion.content.items.bullet.Ammo;
import bottomtextdanny.dannys_expansion._util._client.CMC;
import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._base.network.servertoclient.MSGCShootGun;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerGunModule;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerHelper;
import bottomtextdanny.dannys_expansion.tables.DEMiniAttributes;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.dannys_expansion.tables._client.DERenderProperties;
import bottomtextdanny.dannys_expansion._util.EntityUtil;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import bottomtextdanny.braincell.mod.network.Connection;
import bottomtextdanny.braincell.mod.capability.player.BCAccessoryModule;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public abstract class GunItem<E extends Ammo> extends Item implements GunModelLoader {
    public static final float
	    MAX_ACCURACY = 10.0F,
	    MAX_CADENCE = 10.0F;
    public Random random = new Random();
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;
    private WeakReference<TooltipWriter> tooltipInfo;
	protected final Class<E> bulletClazz;

    public GunItem(Class<E> bulletClazz, Properties properties) {
        super(properties.stacksTo(1));
		this.bulletClazz = bulletClazz;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", 9999999, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
        Connection.doClientSide(() -> renderRegistry());
        tooltipInfo = new WeakReference<>(null);
    }

    @Nonnull
    public TooltipWriter createTooltipInfo() {
        return (spacing, stack, level, tooltip, flag) -> {};
    }

    @OnlyIn(Dist.CLIENT)
    protected abstract void renderRegistry();

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(DERenderProperties.GUN);
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }
    
    public void baseShoot(Player player, ItemStack stack, InteractionHand hand) {
    	if (player.getUseItem() != ItemStack.EMPTY) return;

        if (!player.level.isClientSide) {
            PlayerGunModule gunModule = PlayerHelper.gunModule(player);
            if (gunModule.getGunCooldownTicks() <= 0) {

			    int handIdx = hand.ordinal();
			    if (usageTime() > 0) {
				    shootFirstCallout(player, stack, gunModule);

			    	if (gunModule.getGunUseTicks() < 0 || gunModule.getGunUseTicks() >= usageTime() && usageParameter(player, stack, hand, gunModule)) {
					    gunModule.setGunUseTicks(0);

					    handIdx += 0x100;
					}
				    new MSGCShootGun(player.getId(), handIdx).sendTo(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player));
			    } else {
				    shootFirstCallout(player, stack, gunModule);
				    new MSGCShootGun(player.getId(), handIdx).sendTo(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player));
			    }
		    }
		}
    }
    
    public boolean usageParameter(Player player, ItemStack stack, InteractionHand hand, PlayerGunModule capability) {
    	return false;
    }

    public void shootFirstCallout(Player player, ItemStack stack, PlayerGunModule capability) {
        Connection.doClientSide(() -> {
            GunClientData clientData = DannysExpansion.client().getGunData();

            if (usageTime() <= 0 && player == CMC.player()) {
                clientData.rollDirection = this.random.nextBoolean() ? 1 : -1;
            }
        });

		
        player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
	
	    ItemStack bulletStack = tryToGetBullet(this.bulletClazz, player);
	
	    if (bulletStack != null) {
			shotWithBullet(player, stack, capability, bulletStack, (E)bulletStack.getItem());
	    } else {
		    failShoot(player, stack);
	    }
    }
	
	public void shotWithBullet(Player player, ItemStack stack, PlayerGunModule capability, ItemStack bulletStack, E bulletItem) {
	
	}
	
	public float getMotionMultiplier(Player player) {
		float mult = 1.0F;
		if (player.isShiftKeyDown() && player.isOnGround()) mult = 0.75F;
		if (player.isVisuallySwimming()) mult = 1.333F;
		
		return mult;
	}
	
	public void usageTick(Player player, PlayerGunModule module, ItemStack stack, int progress) {}
    
	public int cooldown() {
        return 5;
    }
	
	public int usageTime() {
		return 0;
	}

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
    	if (this instanceof ScopingGun scopingGun) {
            PlayerGunModule gunModule = PlayerHelper.gunModule(playerIn);
            ItemStack itemstack = playerIn.getItemInHand(handIn);

            return scopingGun.scopingAction(itemstack, playerIn, gunModule) ? InteractionResultHolder.success(itemstack) : InteractionResultHolder.fail(itemstack);
	    }
        return super.use(worldIn, playerIn, handIn);
    }
	
	public ItemStack tryToGetBullet(Class<E> clazz, Player player) {

        for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack itemstack = player.getInventory().getItem(i);

            if (clazz.isAssignableFrom(itemstack.getItem().getClass())) {
                ItemStack itemstack1 = itemstack;
				
                return itemstack1;
            }
        }

        if (player.isCreative()) {
            return new ItemStack(getDefaultBullet());
        }
        return null;
    }
	
	public void failShoot(Player player, ItemStack stack) {
		setVisualRecoil(player, 2.0F, 0.3F, 0.8F);
		EntityUtil.playEyeSound(player, DESounds.IS_OUT_OF_AMMO.get(), 0.8F, 1.0F + this.random.nextFloat() * 0.1F);
    }

    public void setRecoil(Player player, float recoil, float recoilMax, float recoilSub, float recoilMult) {
        PlayerGunModule gunModule = PlayerHelper.gunModule(player);

        gunModule.setRecoilSubstract(recoilSub);
        gunModule.setRecoilMultiplier(recoilMult);
        gunModule.setRecoil(Math.min(gunModule.getRecoil() + recoil, recoilMax));

        Connection.doClientSide(() ->
                setClientRecoil(player, recoil, recoilMax, recoilSub, recoilMult));
    }

    @OnlyIn(Dist.CLIENT)
    public void setClientRecoil(Player player, float recoil, float recoilMax, float recoilSub, float recoilMult) {
        GunClientData clientData = DannysExpansion.client().getGunData();

        if (player == CMC.player()) {
            clientData.recoil = recoil;
            clientData.recoilSubtract = recoilSub;
            clientData.recoilMult = recoilMult;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void updateClientDispersion(Player player, float recoilParam, float movementParam) {
        GunClientData clientData = DannysExpansion.client().getGunData();

        clientData.dispersion = baseDispersionFactor(player, recoilParam, movementParam) + getExtraCrosshairDegrees(player);
    }

    public void setVisualRecoil(Player player, float recoil, float recoilSubstraction, float recoilMult) {
        Connection.doClientSide(() -> setCameraRecoil(player, recoil, recoilSubstraction, recoilMult));
    }

        @OnlyIn(Dist.CLIENT)
    protected void setCameraRecoil(Player player, float recoil, float recoilSubstraction, float recoilMult) {
        GunClientData clientData = DannysExpansion.client().getGunData();

        if (player == CMC.player()) {
            clientData.pitchRecoil = recoil;
            clientData.pitchRecoilSubtract = recoilSubstraction;
            clientData.pitchRecoilMult = recoilMult;
        }
    }
	
	public void applyCooldown(Player player) {
        PlayerGunModule gunModule = PlayerHelper.gunModule(player);
        BCAccessoryModule accessoryModule = PlayerHelper.braincellAccessoryModule(player);
		gunModule.setGunCooldownTicks((int) (cooldown() * ((1.0F - accessoryModule.getLesserModifier(DEMiniAttributes.GUN_CADENCE) / MAX_CADENCE) / 2.0F + 0.5F)));
	}

    @OnlyIn(Dist.CLIENT)
    public double getExtraCrosshairDegrees(Player player) {
        return 0;
    }

    public abstract E getDefaultBullet();

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip,
                                TooltipFlag flag) {
        super.appendHoverText(stack, worldIn, tooltip, flag);
        TooltipWriter writerCache = tooltipInfo.get();

        if (writerCache != null)
            writerCache.write(0, stack, worldIn, tooltip, flag);
        else {
            TooltipWriter writer = createTooltipInfo();
            this.tooltipInfo = new WeakReference<>(writer);
            writer.write(0, stack, worldIn, tooltip, flag);
        }
    }
	
	protected float baseDispersionFactor(Player player, float recoil, float movement) {
        BCAccessoryModule accessoryModule = PlayerHelper.braincellAccessoryModule(player);
        return dispersionFactor(player, recoil, movement) * (1.0F - accessoryModule.getLesserModifier(DEMiniAttributes.GUN_ACCURACY) / MAX_ACCURACY / 2.0F + 0.5F);
	}

    protected abstract float dispersionFactor(Player player, float recoil, float movement);
}
