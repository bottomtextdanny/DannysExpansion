package bottomtextdanny.dannys_expansion.tables;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.*;
import bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.ghoul.GhoulRenderer;
import bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.goblin.GoblinRenderer;
import bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.slime.*;
import bottomtextdanny.dannys_expansion.content._client.rendering.entity.projectile.*;
import bottomtextdanny.dannys_expansion.content.entities.mob.cursed_skull.CursedSkull;
import bottomtextdanny.dannys_expansion.content.entities.mob.ghoul.Ghoul;
import bottomtextdanny.dannys_expansion.content.entities.mob.goblin.Goblin;
import bottomtextdanny.dannys_expansion.content.entities.mob.goblin.StoneProjectile;
import bottomtextdanny.dannys_expansion.content.entities.mob.klifour.Klifour;
import bottomtextdanny.dannys_expansion.content.entities.mob.squig.Squig;
import bottomtextdanny.dannys_expansion.content.entities.projectile.*;
import bottomtextdanny.dannys_expansion.content.entities.projectile.arrow.DEArrow;
import bottomtextdanny.dannys_expansion.tables.items.DEItemCategory;
import bottomtextdanny.dannys_expansion.content.entities.misc.TestDummyEntity;
import bottomtextdanny.dannys_expansion.content.entities.mob.hollow_armor.HollowArmor;
import bottomtextdanny.dannys_expansion.content.entities.mob.ice_elemental.IceElemental;
import bottomtextdanny.dannys_expansion.content.entities.mob.monstrous_scorpion.MonstrousScorpion;
import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.MagmaSlime;
import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.desertic_slime.DeserticSlime;
import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.mundane_slime.MundaneSlime;
import bottomtextdanny.dannys_expansion.content.entities.projectile.bullet.AquaticBulletEntity;
import bottomtextdanny.dannys_expansion.content.entities.projectile.bullet.BulletEntity;
import bottomtextdanny.dannys_expansion.content._client.rendering.entity.misc.TestDummyRenderer;
import bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.hollow_armor.HollowArmorRenderer;
import bottomtextdanny.dannys_expansion.content._client.rendering.entity.projectile.bullet.AquaticBulletRenderer;
import bottomtextdanny.dannys_expansion.content._client.rendering.entity.projectile.bullet.BulletRenderer;
import bottomtextdanny.dannys_expansion._util.SpawnParameters;
import bottomtextdanny.braincell.mod._base.registry.BCEntityBuilder;
import bottomtextdanny.braincell.mod._base.registry.BCLivingEntityBuilder;
import bottomtextdanny.braincell.mod._base.registry.BCMobBuilder;
import bottomtextdanny.braincell.mod._base.registry.EntityWrap;
import bottomtextdanny.braincell.mod._base.registry.managing.BCRegistry;
import bottomtextdanny.braincell.mod.world.builtin_items.BCSpawnEggItem;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;

public final class DEEntities {
    public static final BCRegistry<EntityType<?>> ENTRIES = new BCRegistry<>();

    public static final TagKey<EntityType<?>> GOODIES = bindTag("goodies");
    public static final TagKey<EntityType<?>> POST_DRAGON_GOODIES = bindTag("post_dragon_goodies");

    public static final EntityWrap<EntityType<MonstrousScorpion>> MONSTROUS_SCORPION =
            start("monstrous_scorpion", MonstrousScorpion::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(1.4F, 0.875F)
                    .attributes(MonstrousScorpion::attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND,
                            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                            SpawnParameters::canMonsterSpawnInLight)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MonstrousScorpion::spawnPlacement)
                    .renderer(() -> MonstrousScorpionRenderer::new)
                    .egg(eggBuilder(3023407, 8937354))
                    //.kite(() -> new SpecialKiteItem(50, (byte)1))
                    .build();

    public static final EntityWrap<EntityType<DeserticSlime>> DESERTIC_SLIME =
            start("desertic_slime", DeserticSlime::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.75F, 0.5625F)
                    .attributes(DeserticSlime::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canSpawnInSurface)
                    .renderer(() -> DeserticSlimeRenderer::new)
                    .egg(eggBuilder(0xe3da9a, 0x785b47))
                    //.kite(() -> new SpecialKiteItem(50, (byte)1))
                    .build();

    public static final EntityWrap<EntityType<IceElemental>> ICE_ELEMENTAL =
            start("ice_elemental", IceElemental::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.5F, 0.5F)
                    .attributes(IceElemental::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IceElemental::spawnPlacement)
                    .renderer(() -> IceElementalRenderer::new)
                    .egg(eggBuilder(2776256, 10933503).unstable())
                    //.kite(() -> new SpecialKiteItem(50, (byte)4))
                    .build();

    public static final EntityWrap<EntityType<MagmaSlime>> MAGMA_SLIME =
            start("magma_slime", MagmaSlime::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(1.0F, 0.875F)
                    .attributes(MagmaSlime::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MagmaSlime::canSpawn)
                    .renderer(() -> MagmaSlimeRenderer::new)
                    .egg(eggBuilder(16744448, 6562816))
                    //.kite(() -> new SpecialKiteItem(50, (byte)1).bright())
                    .build();

    public static final EntityWrap<EntityType<MundaneSlime>> MUNDANE_SLIME =
            start("mundane_slime", MundaneSlime::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.625F, 0.5F)
                    .attributes(MundaneSlime::attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canSpawnInSurface)
                    .renderer(() -> MundaneSlimeRenderer::new)
                    .egg(eggBuilder(3240222, 3246892))
                    //.kite(() -> new SpecialKiteItem(50, (byte)1))
                    .build();

    public static final EntityWrap<EntityType<HollowArmor>> HOLLOW_ARMOR =
            start("hollow_armor", HollowArmor::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.6F, 2.2F)
                    .attributes(HollowArmor::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnParameters::canMonsterSpawnInLight)
                    .renderer(() -> HollowArmorRenderer::new)
                    .egg(eggBuilder(8946827, 8596499))
                    //.kite(() -> new SpecialKiteItem(50, (byte)1))
                    .build();

    public static final EntityWrap<EntityType<Squig>> SQUIG =
            start("squig", Squig::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(1.0F, 1.0F)
                    .attributes(Squig::Attributes)
                    .spawn(SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Squig::spawnPlacement)
                    .renderer(() -> SquigRenderer::new)
                    .egg(eggBuilder(0x70f3fc, 0xff64f2))
                    .build();

    public static final EntityWrap<EntityType<Ghoul>> GHOUL =
            start("ghoul", Ghoul::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.6F, 2.0F)
                    .attributes(Ghoul::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Ghoul::spawnPlacement)
                    .renderer(() -> GhoulRenderer::new)
                    .egg(eggBuilder(0x403f33, 0x1f1a19))
                    .build();

    public static final EntityWrap<EntityType<Klifour>> KLIFOUR =
            start("klifour", Klifour::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.5F, 0.5F)
                    .attributes(Klifour::Attributes)
                    .renderer(() -> KlifourRenderer::new)
                    .egg(eggBuilder(0x3cb040, 0xffab9e)
                            .setLogic(Klifour.EGG_LOGIC))
                    .build();

    public static final EntityWrap<EntityType<CursedSkull>> CURSED_SKULL =
            start("cursed_skull", CursedSkull::new)
                    .classification(MobCategory.MONSTER)
                    .dimensions(0.75F, 0.6F)
                    .attributes(CursedSkull::Attributes)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CursedSkull::spawnPlacement)
                    .renderer(() -> CursedSkullRenderer::new)
                    .egg(eggBuilder(0xc3d49d, 0x4c5e4e))
                    .build();

    public static final EntityWrap<EntityType<Goblin>> GOBLIN =
            start("goblin", Goblin::new)
                    .classification(MobCategory.CREATURE)
                    .dimensions(0.6F, 0.7F)
                    .spawn(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Goblin::spawningParameters)
                    .attributes(Goblin::attributes)
                    .renderer(() -> GoblinRenderer::new)
                    .egg(eggBuilder(0x8bc657, 0x573e2c))
                    .build();

    //**LIVING-MISC------------------------------------------------------------------------//

    public static final EntityWrap<EntityType<TestDummyEntity>> TEST_DUMMY =
            DEEntities.startLE("test_dummy", TestDummyEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.75F, 1.875F)
                    .attributes(TestDummyEntity::Attributes)
                    .renderer(() -> TestDummyRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<IceSpike>> ICE_SPIKE =
            startE("ice_spike", IceSpike::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.1F, 0.1F)
                    .renderer(() -> IceSpikeRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<KlifourSpit>> KLIFOUR_SPIT =
            startE("klifour_spit", KlifourSpit::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.5F, 0.5F)
                    .renderer(() -> KlifourSpitRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<SquigBubbleEntity>> SQUIG_BUBBLE =
            startE("squig_bubble", SquigBubbleEntity::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.5F, 0.5F)
                    .renderer(() -> SquigBubbleRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<CursedFireball>> CURSED_FIREBALL =
            startE("cursed_fireball", CursedFireball::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.25F, 0.25F)
                    .renderer(() -> CursedFireballRenderer::new)
                    .build();

    public static final EntityWrap<EntityType<StoneProjectile>> STONE_PROJECTILE =
            startE("stone_projectile", StoneProjectile::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.1875F, 0.1875F)
                    .renderer(() -> StoneProjectileRenderer::new)
                    .build();

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

    public static final EntityWrap<EntityType<DEArrow>> ARROW =
            startE("arrow", DEArrow::new)
                    .classification(MobCategory.MISC)
                    .dimensions(0.5F, 0.5F)
                    .renderer(() -> DEArrowRenderer::new)
                    .build();

    //**EXTRA-----------------------------------------------------------------------------//

//    public static final EntityWrap<EntityType<EnderDragonRewardEntity>> ENDER_DRAGON_REWARD =
//            startE("ender_dragon_reward", EnderDragonRewardEntity::new)
//                    .classification(MobCategory.MISC)
//                    .dimensions(1.0F, 1.0F)
//                    .renderer(() -> EnderDragonRewardRenderer::new)
//                    .build();

    //**ENTITIES-END*****************************************************************************************************************************//

    public static BCSpawnEggItem.Builder eggBuilder(int primaryTint, int secondaryTint) {
        return BCSpawnEggItem.createBuilder(primaryTint, secondaryTint).properties(new Item.Properties().tab(DannysExpansion.TAB)).sorted((short) DEItemCategory.SPAWN_EGGS.ordinal());
    }

    public static <E extends Entity> BCEntityBuilder<E> startE(String entityId, EntityType.EntityFactory<E> factory) {
        BCEntityBuilder<E> builder = new BCEntityBuilder<E>(ENTRIES, DannysExpansion.DE_REGISTRY_MANAGER);
        builder.declare(entityId, factory);
        return builder;
    }

    public static <E extends LivingEntity> BCLivingEntityBuilder<E> startLE(String entityId, EntityType.EntityFactory<E> factory) {
        BCLivingEntityBuilder<E> builder = new BCLivingEntityBuilder<>(ENTRIES, DannysExpansion.DE_REGISTRY_MANAGER);
        builder.declare(entityId, factory);
        return builder;
    }

    public static <E extends Mob> BCMobBuilder<E> start(String entityId, EntityType.EntityFactory<E> factory) {
        BCMobBuilder<E> builder = new BCMobBuilder<E>(ENTRIES, DannysExpansion.DE_REGISTRY_MANAGER);
        builder.declare(entityId, factory);
        return builder;
    }

    private static TagKey<EntityType<?>> bindTag(String location) {
        return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(DannysExpansion.ID, location));
    }
}
