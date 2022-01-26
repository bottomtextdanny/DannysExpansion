package net.bottomtextdanny.danny_expannny.objects.accessories;

import com.mojang.blaze3d.vertex.PoseStack;
import net.bottomtextdanny.braincell.mod.serialization.WorldPacketData;
import net.bottomtextdanny.danny_expannny.accessory.AccessoryKey;
import net.bottomtextdanny.danny_expannny.accessory.IAccessory;
import net.bottomtextdanny.danny_expannny.accessory.ModifierType;
import net.bottomtextdanny.danny_expannny.object_tables.DEAccessoryKeys;
import net.bottomtextdanny.danny_expannny.network.clienttoserver.MSGAccessoryServerManager;
import net.bottomtextdanny.danny_expannny.network.servertoclient.MSGAccessoryClientManager;
import net.bottomtextdanny.dannys_expansion.core.Util.Pair;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.bottomtextdanny.dannys_expansion.core.base.item.IDescriptionUtil;
import net.bottomtextdanny.danny_expannny.capabilities.player.MiniAttribute;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class CoreAccessory implements IAccessory, IDescriptionUtil {
	public static final CoreAccessory EMPTY = new CoreAccessory(DEAccessoryKeys.CORE_EMPTY, null);
	@OnlyIn(Dist.CLIENT)
	public static final String
			ATTACK_DAMAGE_ADD_DESC = "description.dannys_expansion.accessory_base.melee_damage_modifier",
			ATTACK_KNOCKBACK_ADD_DESC = "description.dannys_expansion.accessory_base.melee_knockback_modifier",
	        ATTACK_SPEED_ADD_DESC = "description.dannys_expansion.accessory_base.melee_speed_modifier",
			GUN_ACCURACY_DESC = "description.dannys_expansion.accessory_base.gun_accuracy_modifier",
			GUN_CADENCE_DESC = "description.dannys_expansion.accessory_base.gun_cadence_modifier",
			GUN_DAMAGE_ADD_DESC = "description.dannys_expansion.accessory_base.gun_damage_add_modifier",
			GUN_DAMAGE_MLT_DESC = "description.dannys_expansion.accessory_base.gun_damage_mlt_modifier",
			ARCHERY_SPEED_DESC = "description.dannys_expansion.accessory_base.archery_speed_modifier",
			ARCHERY_DAMAGE_ADD_DESC = "description.dannys_expansion.accessory_base.archery_damage_add_modifier",
			ARCHERY_DAMAGE_MLT_DESC = "description.dannys_expansion.accessory_base.archery_damage_mlt_modifier",
			BULLET_SPEED_MLT_DESC = "description.dannys_expansion.accessory_base.bullet_speed_modifier",
			ARROW_SPEED_MLT_DESC = "description.dannys_expansion.accessory_base.arrow_speed_modifier",
			MOVEMENT_SPEED_MLT_DESC = "description.dannys_expansion.accessory_base.movement_speed_modifier",
			ARMOR_ADD_DESC = "description.dannys_expansion.accessory_base.armor_modifier",
			LUCK_ADD_DESC = "description.dannys_expansion.accessory_base.luck_modifier";
	@OnlyIn(Dist.CLIENT)
	public static final String ATTRIBUTE_MODIFIER_KEY = "@mod<";
	private final AccessoryKey<?> key;
    protected Player player;
    public int index;
    protected Random random = new Random();
	
	public CoreAccessory(AccessoryKey<?> key, Player player) {
		super();
		this.key = key;
		this.player = player;
	}

	public void prepare(int index) {
		this.index = index;
	}
	
	public void populateModifierData(List<Pair<ModifierType, Double>> modifierList, List<Pair<MiniAttribute, Float>> lesserModifierList) {}
	
	@Override
    public void tick() {
    }
	
    protected double getModifierBase(ModifierType modifierType) {
		return AccessoryKey.getAttributeData().get(this.key).getBaseValue(modifierType);
    }
	
	public boolean isModifierActive(ModifierType modifierType) {
		return AccessoryKey.getAttributeData().get(this.key).containsModifier(modifierType);
	}
	
	protected float getLesserModifierBase(MiniAttribute modifierType) {
		return AccessoryKey.getAttributeData().get(this.key).getBaseValue(modifierType);
	}
	
	public boolean isLesserModifierActive(MiniAttribute modifierType) {
		return AccessoryKey.getAttributeData().get(this.key).containsLesserModifier(modifierType);
	}
	
	public double getFinalSpeedMultiplier() {
		return getModifierBase(ModifierType.MOVEMENT_SPEED_MLT) / 100.0 + 1.0;
	}
	
	public double getFinalAttackSpeedAddition() {
		return getModifierBase(ModifierType.ATTACK_SPEED_ADD);
	}
	
	public double getFinalAttackDamageAddition() {
		return getModifierBase(ModifierType.ATTACK_DAMAGE_ADD);
	}
	
	public double getFinalAttackKnockbackAddition() {
		return getModifierBase(ModifierType.ATTACK_KNOCKBACK_ADD);
	}
	
	public double getFinalKnockbackResistanceMultiplier() {
		return getModifierBase(ModifierType.KNOCKBACK_RESISTANCE_MLT);
	}
	
	public double getFinalKnockbackResistanceAddition() {
		return getModifierBase(ModifierType.KNOCKBACK_RESISTANCE_ADD);
	}
	
	public double getFinalArmorAddition() {
		return getModifierBase(ModifierType.ARMOR_ADD);
	}
	
	public double getFinalLuckAddition() {
		return getModifierBase(ModifierType.LUCK_ADD);
	}
	
	
	public float getFinalLesserAttributeValue(MiniAttribute attribute) {
		return getLesserModifierBase(attribute);
	}
	
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		if (FMLLoader.getDist() == Dist.CLIENT) {
			if (isModifierActive(ModifierType.ATTACK_DAMAGE_ADD)) {
				createModifierTooltip(tooltip, ATTACK_DAMAGE_ADD_DESC, getModifierBase(ModifierType.ATTACK_DAMAGE_ADD));
			}
			if (isModifierActive(ModifierType.ATTACK_KNOCKBACK_ADD)) {
				createModifierTooltip(tooltip, ATTACK_KNOCKBACK_ADD_DESC, getModifierBase(ModifierType.ATTACK_KNOCKBACK_ADD));
			}
			if (isModifierActive(ModifierType.ATTACK_SPEED_ADD)) {
				createModifierTooltip(tooltip, ATTACK_SPEED_ADD_DESC, getModifierBase(ModifierType.ATTACK_SPEED_ADD));
			}
			if (isLesserModifierActive(MiniAttribute.GUN_ACCURACY)) {
				createModifierTooltip(tooltip, GUN_ACCURACY_DESC, getLesserModifierBase(MiniAttribute.GUN_ACCURACY));
			}
			if (isLesserModifierActive(MiniAttribute.GUN_CADENCE)) {
				createModifierTooltip(tooltip, GUN_CADENCE_DESC, getLesserModifierBase(MiniAttribute.GUN_CADENCE));
			}
			if (isLesserModifierActive(MiniAttribute.GUN_DAMAGE_ADD)) {
				createModifierTooltip(tooltip, GUN_DAMAGE_ADD_DESC, getLesserModifierBase(MiniAttribute.GUN_DAMAGE_ADD));
			}
			if (isLesserModifierActive(MiniAttribute.GUN_DAMAGE_MLT)) {
				createModifierTooltip(tooltip, GUN_DAMAGE_MLT_DESC, getLesserModifierBase(MiniAttribute.GUN_DAMAGE_MLT));
			}
			if (isLesserModifierActive(MiniAttribute.ARCHERY_SPEED)) {
				createModifierTooltip(tooltip, ARCHERY_SPEED_DESC, getLesserModifierBase(MiniAttribute.ARCHERY_SPEED));
			}
			if (isLesserModifierActive(MiniAttribute.ARCHERY_DAMAGE_ADD)) {
				createModifierTooltip(tooltip, ARCHERY_DAMAGE_ADD_DESC, getLesserModifierBase(MiniAttribute.ARCHERY_DAMAGE_ADD));
			}
			if (isLesserModifierActive(MiniAttribute.ARCHERY_DAMAGE_MLT)) {
				createModifierTooltip(tooltip, ARCHERY_DAMAGE_MLT_DESC, getLesserModifierBase(MiniAttribute.ARCHERY_DAMAGE_MLT));
			}
			if (isLesserModifierActive(MiniAttribute.BULLET_SPEED_MLT)) {
				createModifierTooltip(tooltip, BULLET_SPEED_MLT_DESC, getLesserModifierBase(MiniAttribute.BULLET_SPEED_MLT));
			}
			if (isLesserModifierActive(MiniAttribute.ARROW_SPEED_MLT)) {
				createModifierTooltip(tooltip, ARROW_SPEED_MLT_DESC, getLesserModifierBase(MiniAttribute.ARROW_SPEED_MLT));
			}
			if (isModifierActive(ModifierType.MOVEMENT_SPEED_MLT)) {
				createModifierTooltip(tooltip, MOVEMENT_SPEED_MLT_DESC, getModifierBase(ModifierType.MOVEMENT_SPEED_MLT));
			}
			if (isModifierActive(ModifierType.ARMOR_ADD)) {
				createModifierTooltip(tooltip, ARMOR_ADD_DESC, getModifierBase(ModifierType.ARMOR_ADD));
			}
			if (isModifierActive(ModifierType.LUCK_ADD)) {
				createModifierTooltip(tooltip, LUCK_ADD_DESC, getModifierBase(ModifierType.LUCK_ADD));
			}

			tooltip.add(TextComponent.EMPTY);
			String descriptionRaw = new TranslatableComponent("description.dannys_expansion.accessory." + this.key.getLocation().getPath()).getString();
			String[] description = getDividedDescription(descriptionRaw);

			for (String s : description) {
				tooltip.add(new TextComponent(s).withStyle(ChatFormatting.GREEN));
			}
		}
	}
	
    @OnlyIn(Dist.CLIENT)
	private void createModifierTooltip(List<Component> tooltip, String translationKey, double value) {
		String attributeDesc;
		String attributeValue;
	    boolean positive = false;
		attributeDesc = new TranslatableComponent(translationKey).getString();
		attributeValue = String.valueOf(value);
		
		if (value > 0.0) {
			attributeValue = '+' + attributeValue;
			positive = true;
		}
		
		attributeDesc = attributeDesc.replaceAll(ATTRIBUTE_MODIFIER_KEY, attributeValue);
		
		tooltip.add(new TextComponent(attributeDesc).withStyle(positive ? ChatFormatting.GREEN : ChatFormatting.RED));
	}

	public String getGeneratedDescription() {
		return "";
	}
	
	@OnlyIn(Dist.CLIENT)
    @Override
    public void keyHandler(Options settings) {
    }
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void frameTick(LocalPlayer cPlayer, PoseStack poseStack, float partialTicks) {
	}

	public int getIndex() {
		return this.index;
	}

	@Override
	public AccessoryKey<?> getKey() {
		return this.key;
	}

	@Override
    public void read(CompoundTag nbt) {
    }
	
	@Override
    public CompoundTag write() {
        return new CompoundTag();
    }
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void accessoryClientManager(int flag, ObjectFetcher fetcher) {
	}
	
	@Override
	public void accessoryServerManager(int flag, ObjectFetcher fetcher) {
	}

	@Override
	public void triggerClientAction(int flag, PacketDistributor.PacketTarget target, WorldPacketData<?>... data) {
		if (this.player.level instanceof ServerLevel serverLevel) {
			new MSGAccessoryClientManager(this.player.getId(), this.index, flag, data, serverLevel).sendTo(target);
		}
	}

	@Override
	public void triggerClientActionToTracking(int flag, WorldPacketData<?>... data) {
		if (this.player.level instanceof ServerLevel serverLevel) {
			new MSGAccessoryClientManager(this.player.getId(), this.index, flag, data, serverLevel).sendTo(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.player));
		}
	}
	
	public void triggerClientActionSpecific(int flag, ServerPlayer player, WorldPacketData<?>... data) {
		if (this.player.level instanceof ServerLevel serverLevel) {
			new MSGAccessoryClientManager(player.getId(), this.index, flag, data, serverLevel).sendTo(PacketDistributor.PLAYER.with(() -> player));
		}
	}
	
	@Override
	public void triggerClientActionToTracking(int flag) {
		triggerClientActionToTracking(flag, (WorldPacketData<?>) null);
	}
	
	@Override
	public void triggerServerAction(int flag, WorldPacketData<?>... data) {
		if (this.player.level.isClientSide()) {
			new MSGAccessoryServerManager(this.player.getId(), this.index, flag, data).sendToServer();
		}
	}
	
	@Override
	public void triggerServerAction(int flag) {
		triggerServerAction(flag, (WorldPacketData<?>) null);
	}
}
