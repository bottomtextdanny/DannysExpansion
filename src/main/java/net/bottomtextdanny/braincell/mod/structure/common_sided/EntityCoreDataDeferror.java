package net.bottomtextdanny.braincell.mod.structure.common_sided;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.bottomtextdanny.danny_expannny.objects.items.BCSpawnEggItem;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class EntityCoreDataDeferror implements AutoCloseable {
    private final List<BCSpawnEggItem> eggs = Lists.newLinkedList();
    private final List<EntitySpawnDeferring<?>> deferredSpawnPlacements = Lists.newLinkedList();
    private final List<EntityAttributeDeferror> deferredAttributeAttachments = Lists.newLinkedList();
    private final Map<ResourceLocation, BCSpawnEggItem.Builder> eggBuilders = Maps.newIdentityHashMap();

    public EntityCoreDataDeferror() {
        super();
    }

    public void sendListeners() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener((FMLCommonSetupEvent event) -> {
            this.deferredSpawnPlacements.forEach(EntitySpawnDeferring::put);
            close();
        });
        bus.addListener((EntityAttributeCreationEvent event) -> {
            this.deferredAttributeAttachments.forEach(deferror -> deferror.register(event));
        });
        bus.addGenericListener(EntityType.class, EventPriority.LOWEST, (RegistryEvent<EntityType<?>> event) -> {
            DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior() {
                public ItemStack execute(BlockSource source, ItemStack stack) {
                    Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                    EntityType<?> entitytype = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
                    entitytype.spawn(source.getLevel(), stack, null, source.getPos().relative(direction), MobSpawnType.DISPENSER, direction != Direction.UP, false);
                    stack.shrink(1);
                    return stack;
                }
            };
            Map<EntityType<? extends Mob>, SpawnEggItem> EGGS = SpawnEggItem.BY_ID;
            this.eggs.forEach(egg -> {
                EGGS.put(egg.getType(null), egg);
                DispenserBlock.registerBehavior(egg, defaultDispenseItemBehavior);
            });
        });
    }

    public void saveEggBuilder(ResourceLocation key, BCSpawnEggItem.Builder builder) {
        this.eggBuilders.put(key, builder);
    }

    public void putEgg(BCSpawnEggItem egg) {
        this.eggs.add(egg);
    }

    public Optional<BCSpawnEggItem.Builder> getEggBuilder(ResourceLocation key) {
        return Optional.ofNullable(this.eggBuilders.getOrDefault(key, null));
    }

    public void deferSpawnPlacement(EntitySpawnDeferring<?> spawnDeferring) {
        this.deferredSpawnPlacements.add(spawnDeferring);
    }

    public void deferAttributeAttachment(EntityAttributeDeferror attributeDeferror) {
        this.deferredAttributeAttachments.add(attributeDeferror);
    }

    @Override
    public void close() {
        this.eggs.clear();
        this.deferredAttributeAttachments.clear();
        this.eggBuilders.clear();
        this.deferredSpawnPlacements.clear();
    }
}
