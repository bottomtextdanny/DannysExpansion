package bottomtextdanny.dannys_expansion._mixin;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._base.capabilities.world.LevelCapability;
import bottomtextdanny.dannys_expansion._base.capabilities.world.LevelPhaseModule;
import bottomtextdanny.dannys_expansion.content.entities._modules.phase_affected_provider.AttributeTransformer;
import bottomtextdanny.dannys_expansion.content.entities._modules.phase_affected_provider.PhaseAffectedProvider;
import bottomtextdanny.braincell.mod.capability.CapabilityHelper;
import bottomtextdanny.braincell.mod.network.Connection;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {

    protected MobMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"finalizeSpawn"},
            remap = true,
            cancellable = true
    )
    public void finalizeSpawnHook(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_, CallbackInfoReturnable<SpawnGroupData> cir) {
        if (!this.level.isClientSide() && p_21436_ != MobSpawnType.COMMAND) {
            Connection.doServerSide(() -> {
                if (this instanceof PhaseAffectedProvider) {
                    PhaseAffectedProvider provider = (PhaseAffectedProvider)this;
                    if (provider.operatingPhaseAffectedModule()) {
                        LevelPhaseModule.Phase levelPhase = ((LevelCapability) CapabilityHelper.get(this.level, LevelCapability.TOKEN)).getPhaseModule().getPhase();
                        LevelPhaseModule.Phase phase = provider.phaseAffectedModule().isUpdated() ? provider.getPhaseWhenSpawned() : levelPhase;
                        provider.phaseAffectedModule().setPhaseSpawned(phase);
                        if (phase != LevelPhaseModule.Phase.NORMAL) {
                            AttributeTransformer<?>[] transformers = this.getTransformers();
                            if (transformers == null) {
                                throw new UnsupportedOperationException("Danny's Expansion: PhaseAffectedProvider type lacks a custom attribute configuration, entity type: " + this.getType().getRegistryName().toString());
                            }

                            AttributeInstance maxHealth = this.getAttribute(Attributes.MAX_HEALTH);
                            float healthRatioO = getHealth() / (float) maxHealth.getValue();

                            for (AttributeTransformer<?> transformer : transformers) {
                                AttributeInstance inst = this.getAttribute(transformer.attribute());
                                if (inst != null) {
                                    inst.setBaseValue(transformer.transformSupplier().calculate(phase, this, inst.getBaseValue()));
                                }
                            }
                            setHealth(healthRatioO * (float) maxHealth.getValue());
                        }

                        provider.phaseAffectedModule().processed = true;
                    }
                }

            });
        }

    }

    @Inject(
            at = {@At("TAIL")},
            method = {"readAdditionalSaveData"},
            remap = true
    )
    public void readAdditionalSaveDataHook(CompoundTag listtag, CallbackInfo ci) {
        if (!this.level.isClientSide()) {
            Connection.doServerSide(() -> {
                if (this instanceof PhaseAffectedProvider) {
                    PhaseAffectedProvider provider = (PhaseAffectedProvider)this;
                    if (provider.operatingPhaseAffectedModule() && !provider.phaseAffectedModule().processed) {
                        LevelPhaseModule.Phase levelPhase = ((LevelCapability)CapabilityHelper.get(this.level, LevelCapability.TOKEN)).getPhaseModule().getPhase();
                        LevelPhaseModule.Phase phase = provider.phaseAffectedModule().isUpdated() ? provider.getPhaseWhenSpawned() : levelPhase;
                        provider.phaseAffectedModule().setPhaseSpawned(phase);
                        if (phase != LevelPhaseModule.Phase.NORMAL) {
                            AttributeTransformer<?>[] transformers = this.getTransformers();
                            if (transformers == null) {
                                throw new UnsupportedOperationException("Danny's Expansion: PhaseAffectedProvider type lacks a custom attribute configuration, entity type: " + this.getType().getRegistryName().toString());
                            }

                            AttributeInstance maxHealth = this.getAttribute(Attributes.MAX_HEALTH);
                            float healthRatioO = getHealth() / (float) maxHealth.getValue();

                            for (AttributeTransformer<?> transformer : transformers) {
                                AttributeInstance inst = this.getAttribute(transformer.attribute());
                                if (inst != null) {
                                    inst.setBaseValue(transformer.transformSupplier().calculate(phase, this, inst.getBaseValue()));
                                }
                            }
                            setHealth(healthRatioO * (float) maxHealth.getValue());
                        }

                        provider.phaseAffectedModule().processed = true;
                    }
                }

            });
        }

    }

    private AttributeTransformer<?>[] getTransformers() {
        return DannysExpansion.server().getPhaseAttributeTransformers().getTransformersForType(this.getType());
    }
}
