package net.bottomtextdanny.danny_expannny.capabilities.player;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.bottomtextdanny.braincell.underlying.misc.SparedHashCollection;
import net.bottomtextdanny.danny_expannny.accessory.*;
import net.bottomtextdanny.danny_expannny.accessory.StackAccSoftSave;
import net.bottomtextdanny.danny_expannny.objects.accessories.CoreAccessory;
import net.bottomtextdanny.danny_expannny.objects.accessories.StackAccessory;
import net.bottomtextdanny.dannys_expansion.common.Evaluations;
import net.bottomtextdanny.danny_expannny.objects.containers.AccessoryInventory;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.IAccessoryInventory;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.LazyCleanable;
import net.bottomtextdanny.dannys_expansion.core.base.item.StackAccessoryProvider;
import net.bottomtextdanny.braincell.mod.capability_helper.CapabilityModule;
import net.minecraft.Util;
import net.minecraft.client.Options;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.*;

public class PlayerAccessoryModule extends CapabilityModule<Player, PlayerCapability> {
    public static final int ALL_ACCESSORIES_SIZE = 7;
    public static final int CORE_ACCESSORIES_SIZE = 5;
    public static final int STACK_ACCESSORY_CACHE_ROW = 10;
    public static final String[] ACCESSORY_TAGS = {
            "accessory_0",
            "accessory_1",
            "accessory_2",
            "accessory_3",
            "accessory_4",
            "accessory_5",
            "accessory_6",
            "accessory_7",
            "accessory_8",
            "accessory_9"};
    public static final String[] ACCESSORY_ITEM_TAGS = {
            "accessory_item_0",
            "accessory_item_1",
            "accessory_item_2",
            "accessory_item_3",
            "accessory_item_4",
            "accessory_item_5",
            "accessory_item_6",
            "accessory_item_7",
            "accessory_item_8",
            "accessory_item_9"};
    private final float[] lesserAttributes = Util.make(() -> {
        float[] attributes = new float[MiniAttribute.values().length];
        for (int i = 0; i < attributes.length; i++) {
            attributes[i] = MiniAttribute.values()[i].baseValue;
        }
        return attributes;
    });
    private final LazyCleanable<List<IAccessory>> allAccessoryList;
    private final List<CoreAccessory> coreAccessoryList;
    private final EnumMap<ModifierType, LinkedList<CoreAccessory>> accessoryAttributeModifiers;
    private final Map<Class<? extends IAccessory>, IAccessory> currentAccessoryTypes;
    private final IAccessoryInventory inventory;
    private final TreeSet<IQueuedJump> jumps;
    private final SparedHashCollection<AccessoryKey<?>, Object[]> stackAccessoryCache;
    private final HandAccessories handManager;
    public boolean goodOnGround;


    public PlayerAccessoryModule(PlayerCapability capability) {
        super("accessories", capability);
        this.coreAccessoryList = NonNullList.withSize(CORE_ACCESSORIES_SIZE, CoreAccessory.EMPTY);
        this.handManager = new HandAccessories();
        this.allAccessoryList = LazyCleanable.of(() -> {
            List<IAccessory> list = Lists.newArrayListWithCapacity(ALL_ACCESSORIES_SIZE);
            for (int i = 0; i < CORE_ACCESSORIES_SIZE; i++) {
                list.add(this.coreAccessoryList.get(i));
            }
            list.add(this.handManager.getMain());
            list.add(this.handManager.getOff());

            return list;
        });
        this.accessoryAttributeModifiers = new EnumMap<>(ModifierType.class);
        this.currentAccessoryTypes = Maps.newIdentityHashMap();
        this.inventory = new AccessoryInventory(this, CORE_ACCESSORIES_SIZE);
        this.jumps = new TreeSet<>(Comparator.comparingInt(right -> right.priority().ordinal()));
        this.stackAccessoryCache = new SparedHashCollection<>(STACK_ACCESSORY_CACHE_ROW);

    }

    public void tick() {
        this.allAccessoryList.assertClear();

        for (int i = 0; i < CORE_ACCESSORIES_SIZE; i++) {
            IAccessory accessory = this.coreAccessoryList.get(i);
            accessory.tick();
        }

        if (!getHolder().level.isClientSide) {
            this.handManager.getMain().tick();
            this.handManager.getOff().tick();
            MiniAttribute[] miniValues = MiniAttribute.values();

            for (MiniAttribute miniValue : miniValues) {
                resetLesserAttribute(miniValue);
                modifyLesserAttribute(miniValue, Evaluations.LESSER_MODIFIER_VALUE.test(this, miniValue));
            }

            replaceAttributeModifier(ModifierType.ATTACK_DAMAGE_ADD, "Accessory attack damage addition", Evaluations.MODIFIER_VALUE.test(this, ModifierType.ATTACK_DAMAGE_ADD));
            replaceAttributeModifier(ModifierType.ATTACK_SPEED_ADD, "Accessory attack speed addition", Evaluations.MODIFIER_VALUE.test(this, ModifierType.ATTACK_SPEED_ADD));
            replaceAttributeModifier(ModifierType.ATTACK_KNOCKBACK_ADD, "Accessory attack knockback addition", Evaluations.MODIFIER_VALUE.test(this, ModifierType.ATTACK_KNOCKBACK_ADD));
            replaceAttributeModifier(ModifierType.KNOCKBACK_RESISTANCE_ADD, "Accessory knockback resistance addition", Evaluations.MODIFIER_VALUE.test(this, ModifierType.KNOCKBACK_RESISTANCE_ADD));
            replaceAttributeModifier(ModifierType.ARMOR_ADD, "Accessory armor addition", Evaluations.MODIFIER_VALUE.test(this, ModifierType.ARMOR_ADD));
            replaceAttributeModifier(ModifierType.LUCK_ADD, "Accessory luck addition", Evaluations.MODIFIER_VALUE.test(this, ModifierType.LUCK_ADD));
            replaceAttributeModifier(ModifierType.MOVEMENT_SPEED_MLT, "Accessory speed multiplier", Evaluations.MODIFIER_VALUE.test(this, ModifierType.MOVEMENT_SPEED_MLT));
            replaceAttributeModifier(ModifierType.KNOCKBACK_RESISTANCE_MLT, "Accessory knockback resistance multiplier", Evaluations.MODIFIER_VALUE.test(this, ModifierType.KNOCKBACK_RESISTANCE_MLT));
        }

        handleItemstackAccessories();
    }

    public void handleItemstackAccessories() {
        this.handManager.updatePrevious();
        handleHand(InteractionHand.MAIN_HAND);
        handleHand(InteractionHand.OFF_HAND);
    }

    public void handleHand(InteractionHand handSide) {
        int handId = handSide.ordinal();
        StackAccessory accessory = HandAccessories.ACCESSORY_FOR_HAND.get(handId).apply(this);
        StackAccessory accessoryO = HandAccessories.ACCESSORYO_FOR_HAND.get(handId).apply(this);
        ItemStack itemStack = getHolder().getItemInHand(handSide);

        if (!(itemStack.getItem() instanceof StackAccessoryProvider)) {
            if (accessoryO instanceof StackAccSoftSave softSave) {
                this.stackAccessoryCache.insert(accessoryO.getKey(), softSave.save());
            }
            HandAccessories.SET_BY_HAND.get(handId).accept(this, StackAccessory.EMPTY);
        } else if (accessory.getItem() != itemStack.getItem()) {
            StackAccessoryProvider provider = (StackAccessoryProvider)itemStack.getItem();
            StackAccessory newAccessory = provider.getKey().create(getHolder());

            newAccessory.prepare(itemStack, handSide);
            HandAccessories.SET_BY_HAND.get(handId).accept(this, newAccessory);

            //accessory is ready to change to new one and fetch its saved image, .
            if (accessory instanceof StackAccSoftSave softSave) {
                this.stackAccessoryCache.insert(accessoryO.getKey(), softSave.save());
            }

            if (newAccessory instanceof StackAccSoftSave saveAcc) {
                Object[] fetchedSave = this.stackAccessoryCache.look(newAccessory.getKey());

                if (fetchedSave != null) {
                    saveAcc.retrieve(ObjectFetcher.of(fetchedSave));
                }
            }
        }
    }

    public void handleClientKeybinds(Options settings) {
        this.coreAccessoryList.forEach(accessory -> accessory.keyHandler(settings));
    }

    public final void replaceAttributeModifier(ModifierType modifierType, String name, double newValue) {
        Attribute attribute = modifierType.attribute.get();
        UUID uuid = modifierType.uuid;
        AttributeInstance attributeInst = getHolder().getAttribute(attribute);

        if (attributeInst != null) {
            if (attributeInst.getModifier(uuid) != null) {
                if (attributeInst.getModifier(uuid).getAmount() != newValue) {
                    attributeInst.removeModifier(uuid);
                    attributeInst.addTransientModifier(new AttributeModifier(uuid, name, newValue, modifierType.operation));
                }
            } else {
                attributeInst.addTransientModifier(new AttributeModifier(uuid, name, newValue, modifierType.operation));
            }
        }
    }

    private void modifyLesserAttribute(MiniAttribute attribute, float factor) {
        this.lesserAttributes[attribute.ordinal()] += factor;
        this.lesserAttributes[attribute.ordinal()] = Mth.clamp(this.lesserAttributes[attribute.ordinal()], attribute.clampMin, attribute.clampMax);
    }

    private void resetLesserAttribute(MiniAttribute attribute) {
        this.lesserAttributes[attribute.ordinal()] = attribute.baseValue;
    }

    public float[] getLesserAttributes() {
        return this.lesserAttributes;
    }

    public List<CoreAccessory> getOrCreateAccessoryAttributeModifiers(ModifierType attributeModifierTarget) {
        if (!this.accessoryAttributeModifiers.containsKey(attributeModifierTarget)) {
            this.accessoryAttributeModifiers.put(attributeModifierTarget, Lists.newLinkedList());
        }
        return this.accessoryAttributeModifiers.get(attributeModifierTarget);
    }

    public void addAccessoryAttributeModifier(ModifierType attributeModifierTarget, CoreAccessory accessory) {
        if (!this.accessoryAttributeModifiers.containsKey(attributeModifierTarget)) {
            this.accessoryAttributeModifiers.put(attributeModifierTarget, Lists.newLinkedList());
        }
        this.accessoryAttributeModifiers.get(attributeModifierTarget).add(accessory);
    }

    public void deleteAccessoryAttributeModifier(ModifierType attributeModifierTarget, CoreAccessory accessory) {
        this.accessoryAttributeModifiers.get(attributeModifierTarget).remove(accessory);
    }

    public void addToCurrentAccessorySet(IAccessory accessory) {
        this.currentAccessoryTypes.putIfAbsent(accessory.getClass(), accessory);
    }

    public void deleteFromCurrentAccessorySet(Class<? extends IAccessory> accessory) {
        this.currentAccessoryTypes.remove(accessory);
    }

    public boolean isAccessoryTypeCurrent(Class<? extends CoreAccessory> accessory) {
        return this.currentAccessoryTypes.containsKey(accessory);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public <A extends IAccessory> A getAccessoryByType(Class<? extends IAccessory> accessory) {
        if (this.currentAccessoryTypes.containsKey(accessory)) {
            return (A) this.currentAccessoryTypes.get(accessory);
        }
        return null;
    }

    public void setAccessoryStack(int index, ItemStack itemStack) {
        this.inventory.setItem(index, itemStack);
    }
    
    public IAccessoryInventory getAccessories() {
        return this.inventory;
    }

    public float getLesserModifier(MiniAttribute attribute) {
        return this.lesserAttributes[attribute.ordinal()];
    }

    public TreeSet<IQueuedJump> getJumpSet() {
        return this.jumps;
    }

    public List<CoreAccessory> getCoreAccessoryList() {
        return this.coreAccessoryList;
    }

    public List<IAccessory> getAllAccessoryList() {
        return this.allAccessoryList.get();
    }

    public HandAccessories getHandManager() {
        return this.handManager;
    }

    @Override
    public void serializeNBT(CompoundTag nbt) {
        int containerSize = this.inventory.getContainerSize();
        for (int i = 0; i < containerSize; i++) {
            CompoundTag itemNBT = this.inventory.getStackContents().get(i).serializeNBT();
            CompoundTag accessoryNBT = this.coreAccessoryList.get(i).write();

            nbt.put(ACCESSORY_ITEM_TAGS[i], itemNBT);
            nbt.put(ACCESSORY_TAGS[i + 5], accessoryNBT);
        }
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        int containerSize = this.inventory.getContainerSize();
        for (int i = 0; i < containerSize; i++) {
            ItemStack stack = ItemStack.EMPTY;
            Tag itemNBT = nbt.get(ACCESSORY_ITEM_TAGS[i]);
            if (itemNBT != null) stack = ItemStack.of((CompoundTag) itemNBT);

            this.inventory.setItem(i, stack);
            this.coreAccessoryList.get(i).read(nbt.getCompound(ACCESSORY_TAGS[i]));
        }
    }
}
