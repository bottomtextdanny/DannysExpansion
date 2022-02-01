package net.bottomtextdanny.danny_expannny.object_tables;

import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.BCRegistry;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.object_tables.items.DEItemCategory;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.*;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.mundane_slime.MundaneSlime;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.varado.Varado;
import net.bottomtextdanny.danny_expannny.rendering.entity.EmptyRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.EnderDragonRewardRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.TestDummyRenderer;
import net.bottomtextdanny.danny_expannny.rendering.kite.KiteKnotRenderer;
import net.bottomtextdanny.danny_expannny.rendering.kite.KiteRenderer;
import net.bottomtextdanny.danny_expannny.rendering.kite.SpecialKiteRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.living.*;
import net.bottomtextdanny.danny_expannny.rendering.entity.living.slime.*;
import net.bottomtextdanny.danny_expannny.rendering.entity.spell.*;
import net.bottomtextdanny.danny_expannny.rendering.entity.living.enderbeast.EnderBeastArcherRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.living.enderbeast.EnderBeastLancerRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.living.hollow_armor.HollowArmorRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.living.rammer.ChildRammerRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.living.rammer.GrandRammerRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.living.rammer.RammerRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.IceSpikeRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.SporeRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.VomitRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.IceArrowRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.spell.bullet.AquaticBulletRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.spell.bullet.BulletRenderer;
import net.bottomtextdanny.danny_expannny.objects.entities.EnderDragonRewardEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.TestDummyEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.projectile.bullet.AquaticBulletEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.projectile.bullet.BulletEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.kite.KiteEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.kite.KiteKnotEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.kite.SpecialKiteEntity;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.*;
import net.bottomtextdanny.danny_expannny.objects.entities.animal.rammer.ChildRammerEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.animal.rammer.GrandRammerEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.animal.rammer.RammerEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.ender_beast.EnderBeastArcherEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.ender_beast.EnderBeastLancerEntity;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.floating.CursedSkullEntity;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.floating.MagmaGulperEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.ghouls.GhoulEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.ghouls.PetrifiedGhoul;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.hollow_armor.HollowArmor;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.ice_elemental.IceElemental;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.klifour.KlifourEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.monstrous_scorpion.MonstrousScorpion;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.desertic_slime.DeserticSlime;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.squig.SquigEntity;
import net.bottomtextdanny.dannys_expansion.common.Entities.projectile.IceSpike;
import net.bottomtextdanny.dannys_expansion.common.Entities.projectile.SporeEntity;
import net.bottomtextdanny.dannys_expansion.common.Entities.projectile.VomitEntity;
import net.bottomtextdanny.dannys_expansion.common.Entities.projectile.arrow.IceArrowEntity;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.*;
import net.bottomtextdanny.danny_expannny.objects.items.BCSpawnEggItem;
import net.bottomtextdanny.danny_expannny.objects.items.SpecialKiteItem;
import net.bottomtextdanny.dannys_expansion.core.Util.SpawnParameters;
import net.bottomtextdanny.dannys_expansion.core.Util.dannybaseentity.DEEntityBuilder;
import net.bottomtextdanny.dannys_expansion.core.Util.dannybaseentity.DELivingEntityBuilder;
import net.bottomtextdanny.dannys_expansion.core.Util.dannybaseentity.DEMobBuilder;
import net.bottomtextdanny.dannys_expansion.core.Util.setup.EntityWrap;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.levelgen.Heightmap;

public final class DEEntities {
    public static final BCRegistry<EntityType<?>> ENTRIES = new BCRegistry<>();

    //**ENTITIES-START***************************************************************************************************************************//

    //**CREATURES--------------------------------------------------------------------------//

    public static final EntityWrap<EntityType<BlueSlimeEntity>> BLUE_SLIME =
            start("blue_slime", BlueSlimeEntity::new)
                    .classification(MobCategory.CREATURE)
                    .dimensions(1.125F, 0.875F)
                    .attributes(BlueSlimeEntity::Attributes)
                    .renderer(() -> BlueSlimeRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<ChildRammerEntity>> CHILD_RAMMER =
            start("child_rammer", ChildRammerEntity::new)
                    .classification(MobCategory.CREATURE)
                    .dimensions(0.75F * 1.2F, 0.625F * 1.2F)
                    .attributes(ChildRammerEntity::Attributes)
                    .renderer(() -> ChildRammerRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<GrandRammerEntity>> GRAND_RAMMER =
            start("grand_rammer", GrandRammerEntity::new)
                    .classification(MobCategory.CREATURE)
                    .dimensions(1.375F * 1.2F, 1.625F)
                    .attributes(GrandRammerEntity::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND,
                            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                            SpawnParameters::canAnimalSpawn)
                    .renderer(() -> GrandRammerRenderer::new)
                    .egg(eggBuilder(5971731, 16764416))
                    .build();

    public static final EntityWrap<EntityType<RammerEntity>> RAMMER =
            start("rammer", RammerEntity::new)
                    .classification(MobCategory.CREATURE)
                    .dimensions(1.35F, 1.2F)
                    .attributes(RammerEntity::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND,
                            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                            SpawnParameters::canAnimalSpawn)
                    .renderer(() -> RammerRenderer::new)
                    .egg(eggBuilder(7744285, 16764416))
                    .build();

    //**HOSTILE-CREATURES------------------------------------------------------------------//

    public static final EntityWrap<EntityType<AridAbominationEntity>> ARID_ABOMINATION =
            start("arid_abomination", AridAbominationEntity::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.6F, 2.3F)
                    .attributes(AridAbominationEntity::Attributes)
                    .renderer(() -> AridAbominationRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<MonstrousScorpion>> MONSTROUS_SCORPION =
            start("monstrous_scorpion", MonstrousScorpion::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(1.4F, 0.875F)
                    .attributes(MonstrousScorpion::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND,
                            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                            SpawnParameters::canMonsterSpawnInLight)
                    .renderer(() -> MonstrousScorpionRenderer::new)
                    .egg(eggBuilder(3023407, 8937354))
                    .kite(() ->new SpecialKiteItem(50, (byte)1))
                    .build();

    public static final EntityWrap<EntityType<CursedSkullEntity>> CURSED_SKULL =
            start("cursed_skull", CursedSkullEntity::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.75F, 0.75F)
                    .attributes(CursedSkullEntity::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canMonsterSpawnInLight)
                    .renderer(() -> CursedSkullRenderer::new)
                    .egg(eggBuilder(9612685, 3688761).unstable())
                    .kite(() ->new SpecialKiteItem(50, (byte)1))
                    .build();

    public static final EntityWrap<EntityType<DeserticSlime>> DESERTIC_SLIME =
            start("desertic_slime", DeserticSlime::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.75F, 0.5625F)
                    .attributes(DeserticSlime::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canSpawnInSurface)
                    .renderer(() -> DeserticSlimeRenderer::new)
                    .egg(eggBuilder(0xe3da9a, 0x785b47))
                    .kite(() -> new SpecialKiteItem(50, (byte)1))
                    .build();

    public static final EntityWrap<EntityType<DesolatedEntity>> DESOLATED =
            start("desolated", DesolatedEntity::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.5F, 2.0F)
                    .attributes(DesolatedEntity::Attributes)
                    .renderer(() -> DesolatedRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<DecayBroaderEntity>> DECAY_BROADER =
            start("decay_broader", DecayBroaderEntity::new)
                    .classification(DEMobCategory.DANNY_END_PATCH_CREATURE)
                    .dimensions(0.8F, 1.75F)
                    .attributes(DecayBroaderEntity::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canSpawnInEmossence)
                    .renderer(() -> DecayBroaderRenderer::new)
                    .egg(eggBuilder(0x483b68, 0xd181db))
                    .build();

    public static final EntityWrap<EntityType<EnderBeastArcherEntity>> ENDER_BEAST_ARCHER =
            start("ender_beast_archer", EnderBeastArcherEntity::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(1.5F, 3.8F)
                    .attributes(EnderBeastArcherEntity::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::noRestriction)
                    .renderer(() -> EnderBeastArcherRenderer::new)
                    .egg(eggBuilder(4529754, 15131801).unstable())
                    .build();

    public static final EntityWrap<EntityType<EnderBeastLancerEntity>> ENDER_BEAST_LANCER =
            start("ender_beast_lancer", EnderBeastLancerEntity::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(1.4F, 3.5F)
                    .attributes(EnderBeastLancerEntity::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::noRestriction)
                    .renderer(() -> EnderBeastLancerRenderer::new)
                    .egg(eggBuilder(4529754, 15131801).unstable())
                    .build();

    public static final EntityWrap<EntityType<FoamieEntity>> FOAMIE =
            start("foamie", FoamieEntity::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.75F, 0.75F)
                    .attributes(FoamieEntity::Attributes)
                    .renderer(() -> FoamieRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<FrozenGhoulEntity>> FROZEN_GHOUL =
            start("frozen_ghoul", FrozenGhoulEntity::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.625F, 2.125F)
                    .attributes(FrozenGhoulEntity::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canSpawnInSurface)
                    .renderer(() -> FrozenGhoulRenderer::new)
                    .egg(eggBuilder(3027302, 10406105))
                    .kite(() ->new SpecialKiteItem(50, (byte)1))
                    .build();

    public static final EntityWrap<EntityType<FrozenSlime>> FROZEN_SLIME =
            start("frozen_slime", FrozenSlime::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.75F, 0.5625F)
                    .attributes(FrozenSlime::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canSpawnInSurface)
                    .renderer(() -> FrozenSlimeRenderer::new)
                    .egg(eggBuilder(3448291, 3270901))
                    .kite(() ->new SpecialKiteItem(50, (byte)1))
                    .build();

    public static final EntityWrap<EntityType<GhoulEntity>> GHOUL =
            start("ghoul", GhoulEntity::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.625F, 2.125F)
                    .attributes(GhoulEntity::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canMonsterSpawnInLight)
                    .renderer(() -> GhoulRenderer::new)
                    .egg(eggBuilder(6453603, 1187862))
                    .kite(() ->new SpecialKiteItem(50, (byte)1))
                    .build();

    public static final EntityWrap<EntityType<IceElemental>> ICE_ELEMENTAL =
            start("ice_elemental", IceElemental::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.5F, 0.5F)
                    .attributes(IceElemental::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canMonsterSpawnInLight)
                    .renderer(() -> IceElementalRenderer::new)
                    .egg(eggBuilder(2776256, 10933503).unstable())
                    .kite(() ->new SpecialKiteItem(50, (byte)4))
                    .build();

    public static final EntityWrap<EntityType<JemossellyEntity>> JEMOSSELLY =
            start("jemosselly", JemossellyEntity::new)
                    .classification(DEMobCategory.DANNY_END_PATCH_CREATURE)
                    .dimensions(0.625F, 0.625F)
                    .attributes(JemossellyEntity::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canSpawnInEmossence)
                    .renderer(() -> JemossellyRenderer::new)
                    .egg(eggBuilder(0xb570e0, 0xff7dff))
                    .build();

    public static final EntityWrap<EntityType<JungleGolemEntity>> JUNGLE_GOLEM =
            start("jungle_golem", JungleGolemEntity::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.9F, 2.2F)
                    .attributes(JungleGolemEntity::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canSpawnInJungle)
                    .renderer(() -> JungleGolemRenderer::new)
                    .egg(eggBuilder(10042115, 15375872))
                    .kite(() ->new SpecialKiteItem(50, (byte)1).bright())
                    .build();

    public static final EntityWrap<EntityType<JungleSlime>> JUNGLE_SLIME =
            start("jungle_slime", JungleSlime::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.9375F, 0.875F)
                    .attributes(JungleSlime::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canSpawnInJungle)
                    .renderer(() -> JungleSlimeRenderer::new)
                    .egg(eggBuilder(7852355, 1675583))
                    .kite(() ->new SpecialKiteItem(50, (byte)1))
                    .build();

    public static final EntityWrap<EntityType<KlifourEntity>> KLIFOUR =
            start("klifour", KlifourEntity::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.5F, 0.5F)
                    .attributes(KlifourEntity::Attributes)
                    .renderer(() -> KlifourRenderer::new)
                    .egg(eggBuilder(2925892, 16740488).setLogic(BCSpawnEggItem.SL_KLIFOUR))
                    .kite(() ->new SpecialKiteItem(50, (byte)1))
                    .build();

    public static final EntityWrap<EntityType<MagmaGulperEntity>> MAGMA_GULPER =
            start("magma_gulper", MagmaGulperEntity::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.8F, 0.8F)
                    .attributes(MagmaGulperEntity::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::noRestriction)
                    .renderer(() -> MagmaGulperRenderer::new)
                    .egg(eggBuilder(14167059, 14203062))
                    .kite(() ->new SpecialKiteItem(50, (byte)1).bright())
                    .build();

    public static final EntityWrap<EntityType<MagmaSlime>> MAGMA_SLIME =
            start("magma_slime", MagmaSlime::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(1.0F, 0.875F)
                    .attributes(MagmaSlime::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MagmaSlime::canSpawn)
                    .renderer(() -> MagmaSlimeRenderer::new)
                    .egg(eggBuilder(16744448, 6562816))
                    .kite(() ->new SpecialKiteItem(50, (byte)1).bright())
                    .build();

    public static final EntityWrap<EntityType<MummyEntity>> MUMMY =
            start("mummy", MummyEntity::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.6F, 2.2F)
                    .attributes(MummyEntity::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canMonsterSpawnInLight)
                    .renderer(() -> MummyRenderer::new)
                    .egg(eggBuilder(15180596, 15177219))
                    .kite(() ->new SpecialKiteItem(50, (byte)1).bright())
                    .build();

    public static final EntityWrap<EntityType<MundaneSlime>> MUNDANE_SLIME =
            start("mundane_slime", MundaneSlime::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.625F, 0.5F)
                    .attributes(MundaneSlime::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canSpawnInSurface)
                    .renderer(() -> MundaneSlimeRenderer::new)
                    .egg(eggBuilder(3240222, 3246892))
                    .kite(() ->new SpecialKiteItem(50, (byte)1))
                    .build();

    public static final EntityWrap<EntityType<NyctoidEntity>> NYCTOID =
            start("nyctoid", NyctoidEntity::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(1.625F, 1.125F)
                    .attributes(NyctoidEntity::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canMonsterSpawnInLight)
                    .renderer(() -> NyctoidRenderer::new)
                    .egg(eggBuilder(657430, 3485858).unstable())
                    .build();

    public static final EntityWrap<EntityType<PetrifiedGhoul>> PETRIFIED_GHOUL =
            start("petrified_ghoul", PetrifiedGhoul::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.625F, 2.25F)
                    .attributes(PetrifiedGhoul::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canMonsterSpawnInLight)
                    .renderer(() -> PetrifiedGhoulRenderer::new)
                    .egg(eggBuilder(0x7d7d7d, 0x3a3b3d))
                    .kite(() ->new SpecialKiteItem(50, (byte)1))
                    .build();

    public static final EntityWrap<EntityType<HollowArmor>> HOLLOW_ARMOR =
            start("hollow_armor", HollowArmor::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.6F, 2.2F)
                    .attributes(HollowArmor::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canMonsterSpawnInLight)
                    .renderer(() -> HollowArmorRenderer::new)
                    .egg(eggBuilder(8946827, 8596499))
                    .kite(() ->new SpecialKiteItem(50, (byte)1))
                    .build();

    public static final EntityWrap<EntityType<PurpolioEntity>> PURPOLIO =
            start("purpolio", PurpolioEntity::new)
                    .classification(DEMobCategory.DANNY_END_CREATURE)
                    .dimensions(1.3F, 1F)
                    .attributes(PurpolioEntity::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canSpawnInIsland)
                    .renderer(() -> PurpolioRenderer::new)
                    .egg(eggBuilder(0x413757, 0xb488f2))
                    .build();

    public static final EntityWrap<EntityType<SandScarabEntity>> SAND_SCARAB =
            start("sand_scarab", SandScarabEntity::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.8F, 0.5F)
                    .attributes(SandScarabEntity::Attributes)
                    .renderer(() -> SandScarabRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<SnaithEntity>> SNAITH =
            start("snaith", SnaithEntity::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(2.0F, 2.0F)
                    .attributes(SnaithEntity::Attributes)
                    .renderer(() -> SnaithRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<SporeSlime>> SPORE_SLIME =
            start("spore_slime", SporeSlime::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(2.0F, 2.0F)
                    .attributes(SporeSlime::Attributes)
                    .renderer(() -> SporeSlimeRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<SporeWightEntity>> SPORE_WIGHT =
            start("spore_wight", SporeWightEntity::new)
                    .classification(DEMobCategory.DANNY_END_PATCH_CREATURE)
                    .dimensions(0.625F, 1.8F)
                    .attributes(SporeWightEntity::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canSpawnInEmossence)
                    .renderer(() -> SporeWightRenderer::new)
                    .egg(eggBuilder(0xbea7d1, 0xd181db))
                    .build();

    public static final EntityWrap<EntityType<SporerEntity>> SPORER =
            start("sporer", SporerEntity::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(1.25F, 1.25F)
                    .attributes(SporerEntity::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canMonsterSpawnInRain)
                    .renderer(() -> SporerRenderer::new)
                    .egg(eggBuilder(3093057, 13841151))
                    .kite(() ->new SpecialKiteItem(50, (byte)1).bright())
                    .build();

    public static final EntityWrap<EntityType<SquigEntity>> SQUIG =
            start("squig", SquigEntity::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(1.0F, 1.0F)
                    .attributes(SquigEntity::Attributes)
                    .spawn(SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SquigEntity::spawnPlacement)
                    .renderer(() -> SquigRenderer::new)
                    .egg(eggBuilder(0x70f3fc, 0xff64f2))
                    .build();

    public static final EntityWrap<EntityType<TumefendEntity>> TUMEFEND =
            start("tumefend", TumefendEntity::new)
                    .classification(DEMobCategory.DANNY_END_CREATURE)
                    .dimensions(1.25F, 1.25F)
                    .attributes(TumefendEntity::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canSpawnInIsland)
                    .renderer(() -> TumefendRenderer::new)
                    .egg(eggBuilder(0x8d62b1, 0x9c61ed))
                    .build();

    public static final EntityWrap<EntityType<Varado>> VARADO =
            start("varado", Varado::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.8F, 1.8F)
                    .attributes(Varado::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canSpawnInSurface)
                    .renderer(() -> VaradoRenderer::new)
                    .egg(eggBuilder(0x95c4c7, 0xdacfa3))
                    .build();

    //**LIVING-MISC------------------------------------------------------------------------//

    public static final EntityWrap<EntityType<TestDummyEntity>> TEST_DUMMY =
            DEEntities.startLE("test_dummy", TestDummyEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.75F, 1.875F)
                    .attributes(TumefendEntity::Attributes)
                    .renderer(() -> TestDummyRenderer::new)
                    .build();

    //**MISC-------------------------------------------------------------------------------//

    public static final EntityWrap<EntityType<BarrenOrbEntity>> BARREN_ORB =
            startE("barren_orb", BarrenOrbEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.75F, 0.75F)
                    .renderer(() -> BarrenOrbRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<CursedFireEntity>> CURSED_FIRE =
            startE("cursed_fire", CursedFireEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.25F, 0.1875F)
                    .renderer(() -> CursedFireRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<DeserticFangEntity>> DESERTIC_FANG =
            startE("desertic_fang", DeserticFangEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.4F, 0.9F)
                    .renderer(() -> DeserticFangRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<EnderArrowEntity>> ENDER_ARROW =
            startE("ender_arrow", EnderArrowEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.625F, 0.625F)
                    .renderer(() -> EnderArrowRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<FoamshroomProjectileEntity>> FOAMSHROOM_PROJECTILE =
            startE("foamshroom_projectile", FoamshroomProjectileEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.5F, 0.5F)
                    .renderer(() -> FoamshroomProjectileRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<GolemDroneEntity>> GOLEM_DRONE =
            startE("golem_drone", GolemDroneEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.5F, 0.5F)
                    .renderer(() -> GolemDroneRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<IceBulletEntity>> ICE_BULLET =
            startE("ice_bullet", IceBulletEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.25F, 0.25F)
                    .renderer(() -> IceBulletRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<IceSpike>> ICE_SPIKE =
            startE("ice_spike", IceSpike::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.1F, 0.1F)
                    .renderer(() -> IceSpikeRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<KiteEntity>> KITE =
            startE("kite", KiteEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.5F, 0.5F)
                    .renderer(() -> KiteRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<KiteKnotEntity>> KITE_KNOT =
            startE("kite_knot", KiteKnotEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.3125F, 0.3125F)
                    .renderer(() -> KiteKnotRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<KlifourSpitEntity>> KLIFOUR_SPIT =
            startE("klifour_spit", KlifourSpitEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.5F, 0.5F)
                    .renderer(() -> KlifourSpitRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<MummySoulEntity>> MUMMY_SOUL =
            startE("mummy_soul", MummySoulEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(1.0F, 1.0F)
                    .renderer(() -> MummySoulRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<PlasmaProjectileEntity>> PLASMA_PROJECTILE =
            startE("plasma_projectile", PlasmaProjectileEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.5F, 0.5F)
                    .renderer(() -> PlasmaProjectileRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<SandScarabEggEntity>> SAND_SCARAB_EGG =
            startE("sand_scarab_egg", SandScarabEggEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.75F, 0.75F)
                    .renderer(() -> SandScarabEggRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<SpecialKiteEntity>> SPECIAL_KITE =
            startE("special_kite", SpecialKiteEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.5F, 0.5F)
                    .renderer(() -> SpecialKiteRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<SporeEntity>> SPORE =
            startE("spore", SporeEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.3125F, 0.3125F)
                    .renderer(() -> SporeRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<SporeBombEntity>> SPORE_BOMB =
            startE("spore_bomb", SporeBombEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.5F, 0.5F)
                    .renderer(() -> SporeBombRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<SporeCloudEntity>> SPORE_CLOUD =
            startE("spore_cloud", SporeCloudEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.1F, 0.1F)
                    .renderer(() -> EmptyRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<SquigBubbleEntity>> SQUIG_BUBBLE =
            startE("squig_bubble", SquigBubbleEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.5F, 0.5F)
                    .renderer(() -> SquigBubbleRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<VomitEntity>> VOMIT =
            startE("vomit", VomitEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.3125F, 0.3125F)
                    .renderer(() -> VomitRenderer::new)
                    .build();

    //**BULLETS----------------------------------------------------------------------------//

    public static final EntityWrap<EntityType<AquaticBulletEntity>> AQUATIC_BULLET =
            startE("aquatic_bullet", AquaticBulletEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.05F, 0.05F)
                    .renderer(() -> AquaticBulletRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<BulletEntity>> BULLET =
            startE("bullet", BulletEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.05F, 0.05F)
                    .renderer(() -> BulletRenderer::new)
                    .build();

    //**ARROWS-----------------------------------------------------------------------------//

    public static final EntityWrap<EntityType<IceArrowEntity>> ICE_ARROW =
            startE("ice_arrow", IceArrowEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.5F, 0.5F)
                    .renderer(() -> IceArrowRenderer::new)
                    .build();

    //**EXTRA-----------------------------------------------------------------------------//

    public static final EntityWrap<EntityType<EnderDragonRewardEntity>> ENDER_DRAGON_REWARD =
            startE("ender_dragon_reward", EnderDragonRewardEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(1.0F, 1.0F)
                    .renderer(() -> EnderDragonRewardRenderer::new)
                    .build();

    //**ENTITIES-END*****************************************************************************************************************************//

    public static BCSpawnEggItem.Builder eggBuilder(int primaryTint, int secondaryTint) {
        return BCSpawnEggItem.createBuilder(primaryTint, secondaryTint).sorted((short) DEItemCategory.SPAWN_EGGS.ordinal());
    }

    public static <E extends Entity> DEEntityBuilder<E> startE(String entityId, EntityType.EntityFactory<E> factory) {
        DEEntityBuilder<E> builder = new DEEntityBuilder<E>(DannysExpansion.solvingState);
        builder.declare(entityId, factory);
        return builder;
    }

    public static <E extends LivingEntity> DELivingEntityBuilder<E> startLE(String entityId, EntityType.EntityFactory<E> factory) {
        DELivingEntityBuilder<E> builder = new DELivingEntityBuilder<>(DannysExpansion.solvingState);
        builder.declare(entityId, factory);
        return builder;
    }

    public static <E extends Mob> DEMobBuilder<E> start(String entityId, EntityType.EntityFactory<E> factory) {
        DEMobBuilder<E> builder = new DEMobBuilder<E>(DannysExpansion.solvingState);
        builder.declare(entityId, factory);
        return builder;
    }
}
