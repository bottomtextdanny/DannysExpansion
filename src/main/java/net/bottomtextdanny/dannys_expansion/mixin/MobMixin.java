package net.bottomtextdanny.dannys_expansion.mixin;

import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.objects.entities.modules.phase_affected_provider.PhaseAffectedProvider;
import net.bottomtextdanny.braincell.mod.entity.modules.variable.VariableModule;
import net.bottomtextdanny.braincell.mod.entity.modules.variable.VariableProvider;
import net.bottomtextdanny.danny_expannny.objects.entities.modules.phase_affected_provider.AttributeTransformer;
import net.bottomtextdanny.danny_expannny.capabilities.CapabilityHelper;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelCapability;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelPhaseModule;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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

    @Inject(at = @At(value = "HEAD"), method = "finalizeSpawn", remap = false, cancellable = true)
    public void finalizeSpawnHook(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_, CallbackInfoReturnable<SpawnGroupData> cir) {
        if (!this.level.isClientSide()) {
            if (p_21436_ != MobSpawnType.COMMAND) {
                if (this instanceof VariableProvider provider && provider.operatingVariableModule()) {
                    VariableModule module = provider.variableModule();

                    if (!module.isUpdated()) {
                        module.setForm(provider.chooseVariant());
                        module.getForm().applyAttributeBonusesRaw((Mob) (Object) this);
                        this.reapplyPosition();
                        this.refreshDimensions();
                    }
                }

                Connection.doServerSide(() -> {
                    if (this instanceof PhaseAffectedProvider provider && provider.operatingPhaseAffectedModule()) {
                        LevelPhaseModule.Phase levelPhase = CapabilityHelper.get(this.level, LevelCapability.CAPABILITY).getPhaseModule().getPhase();
                        LevelPhaseModule.Phase phase = provider.phaseAffectedModule().isUpdated() ? provider.getPhaseWhenSpawned() : levelPhase;
                        provider.phaseAffectedModule().setPhaseSpawned(phase);

                        if (phase != LevelPhaseModule.Phase.NORMAL) {
                            AttributeTransformer<?>[] transformers = getTransformers();

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
                });
            }
        }
    }

    @Inject(at = @At(value = "TAIL"), method = "readAdditionalSaveData", remap = false, cancellable = true)
    public void readAdditionalSaveDataHook(CompoundTag listtag, CallbackInfo ci) {
        if (!this.level.isClientSide()) {
            if (this instanceof VariableProvider provider && provider.operatingVariableModule()) {
                VariableModule module = provider.variableModule();

                if (!module.isUpdated()) {
                    module.setForm(provider.chooseVariant());
                    module.getForm().applyAttributeBonusesRaw((Mob) (Object) this);
                    this.reapplyPosition();
                    this.refreshDimensions();
                }
            }

            Connection.doServerSide(() -> {
                if (this instanceof PhaseAffectedProvider provider && provider.operatingPhaseAffectedModule() && !provider.phaseAffectedModule().processed) {
                    LevelPhaseModule.Phase levelPhase = CapabilityHelper.get(this.level, LevelCapability.CAPABILITY).getPhaseModule().getPhase();
                    LevelPhaseModule.Phase phase = provider.phaseAffectedModule().isUpdated() ? provider.getPhaseWhenSpawned() : levelPhase;
                    provider.phaseAffectedModule().setPhaseSpawned(phase);
                    if (phase != LevelPhaseModule.Phase.NORMAL) {
                        AttributeTransformer<?>[] transformers = getTransformers();

                        if (transformers == null) {
                            throw new UnsupportedOperationException("Danny's Expansion: PhaseAffectedProvider type lacks a custom attribute configuration, entity type: " + this.getType().getRegistryName().toString());
                        }

                        AttributeInstance maxHealth = this.getAttribute(Attributes.MAX_HEALTH);
                        float healthRatioO = getHealth() / (float)maxHealth.getValue();

                        for (AttributeTransformer<?> transformer : transformers) {
                            AttributeInstance inst = this.getAttribute(transformer.attribute());
                            if (inst != null) {
                                inst.setBaseValue(transformer.transformSupplier().calculate(phase, this, inst.getBaseValue()));
                            }
                        }
                        setHealth(healthRatioO * (float)maxHealth.getValue());
                    }
                    provider.phaseAffectedModule().processed = true;
                }
            });
        }
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    private AttributeTransformer<?>[] getTransformers() {
        return DannysExpansion.server().getPhaseAttributeTransformers().getTransformersForType(this.getType());
    }
}
