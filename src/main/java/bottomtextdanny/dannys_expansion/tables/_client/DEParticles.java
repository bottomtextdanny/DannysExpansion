package bottomtextdanny.dannys_expansion.tables._client;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.particles.*;
import bottomtextdanny.dannys_expansion._base.particle.DannyParticleData;
import bottomtextdanny.dannys_expansion._base.particle.DannyParticleType;
import bottomtextdanny.braincell.Braincell;
import bottomtextdanny.braincell.mod.network.Connection;
import bottomtextdanny.braincell.mod._base.registry.managing.BCRegistry;
import bottomtextdanny.braincell.mod._base.registry.managing.RegistryHelper;
import bottomtextdanny.braincell.mod._base.registry.managing.Wrap;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.Function;
import java.util.function.Supplier;

public final class DEParticles {
    public static final BCRegistry<ParticleType<?>> ENTRIES = new BCRegistry<>(false);
    public static final RegistryHelper<ParticleType<?>> HELPER = new RegistryHelper<>(DannysExpansion.DE_REGISTRY_MANAGER, ENTRIES);

    public static final Wrap<DannyParticleType> CRITICAL_HIT = defer("critical_hit", 2,
            () -> new DannyParticleType(true, new DannyParticleData(0)),
            () -> o -> new CustomCriticalParticle.Factory((SpriteSet) o));
    public static final Wrap<DannyParticleType> ECLIPSE_SWEEP = defer("eclipse_sweep", 7,
            () -> new DannyParticleType(true, new DannyParticleData(0.0F)),
            () -> o -> new EclipseSweepParticle.Factory((SpriteSet) o));
	public static final Wrap<DannyParticleType> SQUIG_BUBBLE = defer("squig_bubble", 0,
            () -> new DannyParticleType(true, new DannyParticleData()),
            () -> o -> new SquigBubbleParticle.Factory((SpriteSet) o));
	public static final Wrap<DannyParticleType> SQUIG_BUBBLE_POP = defer("squig_bubble_pop", 0,
            () -> new DannyParticleType(true, new DannyParticleData()),
            () -> o -> new SquigBubblePopParticle.Factory((SpriteSet) o));
	public static final Wrap<DannyParticleType> SQUIG_CROSS = defer("squig_cross", 4,
            () -> new DannyParticleType(true, new DannyParticleData(SquigCrossParticle.BLUE_IDX)),
            () -> o -> new SquigCrossParticle.Factory((SpriteSet) o));
	public static final Wrap<DannyParticleType> SQUIG_RING = defer("squig_ring", 4,
            () -> new DannyParticleType(true, new DannyParticleData(SquigRingParticle.BLUE_IDX, 0.0F, 0.0F)),
            () -> o -> new SquigRingParticle.Factory((SpriteSet) o));
	public static final Wrap<SimpleParticleType> EBBEWEL_CLOUD = defer("ebbewel_cloud",
            () -> new SimpleParticleType(true),
            () -> o -> new EbbewelCloudParticle.Factory((SpriteSet) o));
    public static final Wrap<DannyParticleType> EBBEWEL_RING = defer("ebbewel_ring", 1,
            () -> new DannyParticleType(true, new DannyParticleData(0.0F, 0.0F)),
            () -> o -> new EbbewelRingParticle.Factory((SpriteSet) o));
    public static final Wrap<SimpleParticleType> EBBEWEL_SPARK = defer("ebbewel_spark", 2,
            () -> new SimpleParticleType(true),
            () -> o -> new EbbewelSparkParticle.Factory((SpriteSet) o));
    public static final Wrap<SimpleParticleType> ICY_CIRCULAR_SMOKE = defer("icy_circular_smoke", 7,
            () -> new SimpleParticleType(true),
            () -> o -> new IcyCircularSmokeParticle.Factory((SpriteSet) o));
    public static final Wrap<SimpleParticleType> ICY_CLOUD = defer("icy_cloud", 8,
            () -> new SimpleParticleType(true),
            () -> o -> new IcyCloudParticle.Factory((SpriteSet) o));
    public static final Wrap<SimpleParticleType> KLIFOUR_POISON_BIG_DRIP = defer("klifour_poison_big_drip", 0,
            () -> new SimpleParticleType(true),
            () -> o -> new KlifourPoisonBigDripParticle.Factory((SpriteSet) o));
    public static final Wrap<SimpleParticleType> KLIFOUR_POISON_BUBBLE = defer("klifour_poison_bubble", 7,
            () -> new SimpleParticleType(true),
            () -> o -> new KlifourPoisonBubbleParticle.Factory((SpriteSet) o));
    public static final Wrap<SimpleParticleType> KLIFOUR_POISON_DRIP = defer("klifour_poison_drip", 0,
            () -> new SimpleParticleType(true),
            () -> o -> new KlifourPoisonDripParticle.Factory((SpriteSet) o));
    public static final Wrap<SimpleParticleType> KLIFOUR_POISON_SMALL_BUBBLE = defer("klifour_poison_small_bubble", 4,
            () -> new SimpleParticleType(true),
            () -> o -> new KlifourPoisonSmallBubbleParticle.Factory((SpriteSet) o));
    public static final Wrap<SimpleParticleType> SNOWFLAKE = defer("snowflake", 6,
            () -> new SimpleParticleType(true),
            () -> o -> new SnowflakeParticle.Factory((SpriteSet) o));
    public static final Wrap<DannyParticleType> DUST = defer("dust", 0,
            () -> new DannyParticleType(true, new DannyParticleData(0xFFFFFFFF, 0.0F)),
            () -> o -> new DustParticle.Factory((SpriteSet) o));
    public static final Wrap<SimpleParticleType> CURSED_FLAMES = defer("cursed_flame", 8,
            () -> new SimpleParticleType(true),
            () -> o -> new CursedFlameParticle.Factory((SpriteSet) o));

    public static <D extends ParticleOptions, T extends ParticleType<D>> Wrap<T> defer(String key, int spriteNumber, Supplier<T> particleType, Supplier<Function<Object,Object>> factory) {
        return defer(key, particleType, factory);
    }

    public static <D extends ParticleOptions, T extends ParticleType<D>> Wrap<T> defer(String key, Supplier<T> particleType, Supplier<? extends Function<Object, Object>> factory) {
        Wrap<T> wrapped = HELPER.defer(key, particleType);
        Connection.doClientSide(() -> {
            Braincell.client().getParticleFactoryDeferror().addFactoryStitch(wrapped, spriteSet -> (ParticleProvider<D>)factory.get().apply(spriteSet));
        });
        return wrapped;
    }
}
