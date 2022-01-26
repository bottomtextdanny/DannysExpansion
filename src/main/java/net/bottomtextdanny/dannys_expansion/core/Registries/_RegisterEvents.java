package net.bottomtextdanny.dannys_expansion.core.Registries;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.dannys_expansion.core.Util.setup.DimensionEnum;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DannysExpansion.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class _RegisterEvents {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void structureRegistry$low(RegistryEvent.Register<StructureFeature<?>> event) {
        DannyStructures.DIMENSION_CONFIG_MAP.put(DimensionEnum.OVERWORLD, NoiseGeneratorSettings.bootstrap());
        DannyStructures.DIMENSION_CONFIG_MAP.put(DimensionEnum.NETHER, BuiltinRegistries.NOISE_GENERATOR_SETTINGS.get(NoiseGeneratorSettings.NETHER));
        DannyStructures.DIMENSION_CONFIG_MAP.put(DimensionEnum.END, BuiltinRegistries.NOISE_GENERATOR_SETTINGS.get(NoiseGeneratorSettings.END));
        DannyStructures.REGISTRY_LIST.forEach(reg -> reg.customRegistry(event));
    }
}
