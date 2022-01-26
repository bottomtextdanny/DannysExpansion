package net.bottomtextdanny.danny_expannny.objects.items.gun;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.danny_expannny.ClientInstance;
import net.bottomtextdanny.danny_expannny.rendering.DERenderProperties;
import net.bottomtextdanny.danny_expannny.objects.items.bullet.Ammo;
import net.bottomtextdanny.danny_expannny.network.servertoclient.MSGCShootGun;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.EntityUtil;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.ItemUtil;
import net.bottomtextdanny.danny_expannny.capabilities.item.ProvideCapability;
import net.bottomtextdanny.danny_expannny.capabilities.player.MiniAttribute;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerAccessoryModule;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerGunModule;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerHelper;
import net.bottomtextdanny.dannys_expansion.core.interfaces.item.IGunModelLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
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

import javax.annotation.Nullable;
import java.util.Formatter;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public abstract class GunItem<E extends Ammo> extends Item implements IGunModelLoader, ProvideCapability {
    public static final float
	    MAX_ACCURACY = 10.0F,
	    MAX_CADENCE = 10.0F;
	public LivingEntity livingEntity;
    public float tooltipDamage;
    public Random random = new Random();
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;
	protected final Class<E> bulletClazz;

    public GunItem(Class<E> bulletClazz, Properties properties) {
        super(properties.stacksTo(1));
		this.bulletClazz = bulletClazz;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", 9999999, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
        Connection.doClientSide(() -> renderRegistry());
    }

    @OnlyIn(Dist.CLIENT)
    protected abstract void renderRegistry();

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(DERenderProperties.GUN);
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
	    if (entityIn instanceof Player) {
		    ItemUtil.ifDannyCap(stack, capability -> {
			    capability.setHolder((Player) entityIn);
		    });
	    }

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

			    	if (gunModule.getGunUseTicks() >= usageTime() && usageParameter(player, stack, hand, gunModule)) {
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
        if (usageTime() <= 0 && player.level.isClientSide && player == ClientInstance.player()) {
	        DannysExpansion.clientManager().cRollDirection = this.random.nextBoolean() ? 1 : -1;
        }
		
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
	
	public void usageTick(Player player, ItemStack stack, int progress) {}
    
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
    	if (this instanceof IScopingGun scopingGun) {
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
		setCameraRecoil(player, 2.0F, 0.3F, 0.8F);
		EntityUtil.playEyeSound(player, DESounds.IS_OUT_OF_AMMO.get(), 0.8F, 1.0F + this.random.nextFloat() * 0.1F);
    }

    public void setRecoil(Player player, float recoil, float recoilMax, float recoilSub, float recoilMult) {
        PlayerGunModule gunModule = PlayerHelper.gunModule(player);

        gunModule.setRecoilSubstract(recoilSub);
        gunModule.setRecoilMultiplier(recoilMult);
        gunModule.setRecoil(Math.min(gunModule.getRecoil() + recoil, recoilMax));

        if (player.level.isClientSide) {
            setClientRecoil(player, recoil, recoilMax, recoilSub, recoilMult);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void setClientRecoil(Player player, float recoil, float recoilMax, float recoilSub, float recoilMult) {

        if (player == ClientInstance.player()) {
            DannysExpansion.clientManager().cRecoil = recoil;
            DannysExpansion.clientManager().cRecoilSubtract = recoilSub;
            DannysExpansion.clientManager().cRecoilMult = recoilMult;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void updateClientDispersion(Player player, float recoilParam, float movementParam) {
        DannysExpansion.clientManager().cDispersion = baseDispersionFactor(player, recoilParam, movementParam) + getExtraCrosshairDegrees(player);
    }

    @OnlyIn(Dist.CLIENT)
    public void setCameraRecoil(Player player, float recoil, float recoilSubstraction, float recoilMult) {
        if (player == ClientInstance.player()) {
            DannysExpansion.clientManager().cPitchRecoil = recoil;
            DannysExpansion.clientManager().cPitchRecoilSubtract = recoilSubstraction;
            DannysExpansion.clientManager().cPitchRecoilMult = recoilMult;

        }
    }
	
	public void applyCooldown(Player player) {
        PlayerGunModule gunModule = PlayerHelper.gunModule(player);
        PlayerAccessoryModule accessoryModule = PlayerHelper.accessoryModule(player);
		gunModule.setGunCooldownTicks((int) (cooldown() * ((1.0F - accessoryModule.getLesserModifier(MiniAttribute.GUN_CADENCE) / MAX_CADENCE) / 2.0F + 0.5F)));
	}

    @OnlyIn(Dist.CLIENT)
    public int getExtraCrosshairDegrees(Player player) {
        return 0;
    }

    public abstract E getDefaultBullet();

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent("description.dannys_expansion.gun_damage").append(": ").withStyle(ChatFormatting.GREEN).append(new Formatter().format("%.2f", this.tooltipDamage).toString()));
        tooltip.add(new TranslatableComponent("description.dannys_expansion.gun_cooldown").append(": ").withStyle(ChatFormatting.GREEN).append(new Formatter().format("%.2f", (float) cooldown() / 20).toString()).append("s"));

    }
	
	protected float baseDispersionFactor(Player player, float recoil, float movement) {
        PlayerAccessoryModule accessoryModule = PlayerHelper.accessoryModule(player);
        return dispersionFactor(player, recoil, movement) * (1.0F - accessoryModule.getLesserModifier(MiniAttribute.GUN_ACCURACY) / MAX_ACCURACY / 2.0F + 0.5F);
	}
    protected abstract float dispersionFactor(Player player, float recoil, float movement);
}
