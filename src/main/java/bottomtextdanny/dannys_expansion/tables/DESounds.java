package bottomtextdanny.dannys_expansion.tables;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.braincell.mod._base.registry.managing.BCRegistry;
import bottomtextdanny.braincell.mod._base.registry.managing.RegistryHelper;
import bottomtextdanny.braincell.mod._base.registry.managing.Wrap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public final class DESounds {
    public static final BCRegistry<SoundEvent> ENTRIES = new BCRegistry<>(false);
    public static final RegistryHelper<SoundEvent> HELPER = new RegistryHelper<>(DannysExpansion.DE_REGISTRY_MANAGER, ENTRIES);

    public static final Wrap<SoundEvent> A_END = defer("ambiance.end_ambiance", 1);
	public static final Wrap<SoundEvent> A_EMOSSENCE = defer("ambiance.emossence_ambiance", 1);
	
	//BLOCK
    public static final Wrap<SoundEvent> BS_COMPACT_ICE_BREAK = defer("block.compact_ice_break", 4);
    public static final Wrap<SoundEvent> BS_COMPACT_ICE_DIG = defer("block.compact_ice_dig", 4);
    public static final Wrap<SoundEvent> BS_COMPACT_ICE_PLACE = defer("block.compact_ice_place", 4);
    public static final Wrap<SoundEvent> BS_ECTOSHROOM_BREAK = defer("block.ectoshroom_break", 5);
	public static final Wrap<SoundEvent> BS_ECTOSHROOM_DIG = defer("block.ectoshroom_dig", 4);
	public static final Wrap<SoundEvent> BS_ECTOSHROOM_PLACE = defer("block.ectoshroom_place", 5);
	public static final Wrap<SoundEvent> BS_ECTOSHROOM_STEP = defer("block.ectoshroom_step", 4);
	public static final Wrap<SoundEvent> BS_UPHOLDER_ON = defer("block.upholder_on", 4);
	public static final Wrap<SoundEvent> BS_UPHOLDER_OFF = defer("block.upholder_off", 4);
    public static final Wrap<SoundEvent> BS_HOLLOW_METAL_BREAK = defer("block.hollow_metal_break", 4);
    public static final Wrap<SoundEvent> BS_HOLLOW_METAL_HIT = defer("block.hollow_metal_hit", 6);
    public static final Wrap<SoundEvent> BS_HOLLOW_METAL_PLACE = defer("block.hollow_metal_place", 4);
    public static final Wrap<SoundEvent> BS_HOLLOW_METAL_STEP = defer("block.hollow_metal_step", 4);

    //ENTITY
    public static final Wrap<SoundEvent> ES_MONSTER_COOKIE_HURT = defer("entity.monster_cookie_hurt", 3);
    public static final Wrap<SoundEvent> ES_MONSTER_COOKIE_DEATH = defer("entity.monster_cookie_death", 2);
    public static final Wrap<SoundEvent> ES_MONSTER_COOKIE_GROWL = defer("entity.monster_cookie_growl", 2);
    public static final Wrap<SoundEvent> ES_MONSTER_COOKIE_IDLE = defer("entity.monster_cookie_idle", 3);
    public static final Wrap<SoundEvent> ES_VARADO_HIT = defer("entity.varado_hit", 4);
    public static final Wrap<SoundEvent> ES_ENDER_BEAST_STEP = defer("entity.ender_beast_step", 9);
	public static final Wrap<SoundEvent> ES_ENDER_BEAST_MMH = defer("entity.ender_beast_mmh", 6);
	public static final Wrap<SoundEvent> ES_ENDER_BEAST_GRUNT = defer("entity.ender_beast_grunt", 6);
	public static final Wrap<SoundEvent> ES_ENDER_BEAST_LARGE_GRUNT = defer("entity.ender_beast_large_grunt", 1);
	public static final Wrap<SoundEvent> ES_ENDER_BEAST_HURT = defer("entity.ender_beast_hurt", 4);
	public static final Wrap<SoundEvent> ES_ENDER_BEAST_EFFORT = defer("entity.ender_beast_effort", 2);
	public static final Wrap<SoundEvent> ES_ENDER_BEAST_CONFUSION = defer("entity.ender_beast_confusion", 2);
	public static final Wrap<SoundEvent> ES_ENDER_DRAGON_REWARD_INIT = defer("entity.ender_dragon_reward_init", 1);
	public static final Wrap<SoundEvent> ES_ENDER_DRAGON_REWARD_LOOP = defer("entity.ender_dragon_reward_loop", 1);
	public static final Wrap<SoundEvent> ES_ENDER_DRAGON_REWARD_OPEN = defer("entity.ender_dragon_reward_open", 1);
	public static final Wrap<SoundEvent> ES_ENDER_DRAGON_REWARD_USE = defer("entity.ender_dragon_reward_use", 1);
	public static final Wrap<SoundEvent> ES_GENERIC_CHEST_CLOSE = defer("entity.generic_chest_close", 1);
	public static final Wrap<SoundEvent> ES_GENERIC_CHEST_OPEN = defer("entity.generic_chest_open", 1);
	public static final Wrap<SoundEvent> ES_SQUIG_BUBBLE_POP = defer("entity.squig_bubble_pop", 4);
	public static final Wrap<SoundEvent> ES_SQUIG_BUBBLE_HIT = defer("entity.squig_bubble_hit", 6);
    public static final Wrap<SoundEvent> ES_INSECT_STEP = defer("entity.insect_step", 4);
    public static final Wrap<SoundEvent> ES_MONSTROUS_SCORPION_CLAW_ATTACK = defer("entity.black_scorpion_claw_attack", 2);
    public static final Wrap<SoundEvent> ES_BLACK_SCORPION_DEATH = defer("entity.black_scorpion_death", 2);
    public static final Wrap<SoundEvent> ES_BLACK_SCORPION_HURT = defer("entity.black_scorpion_hurt", 4);
    public static final Wrap<SoundEvent> ES_BLACK_SCORPION_STEP = defer("entity.black_scorpion_step", 4);
    public static final Wrap<SoundEvent> ES_BLACK_SCORPION_STING = defer("entity.black_scorpion_sting", 2);
    public static final Wrap<SoundEvent> ES_BLADE_SWING_LARGE = defer("entity.blade_swing_large", 4);
    public static final Wrap<SoundEvent> ES_BLADE_SWING_MODERATED = defer("entity.blade_swing_moderated", 4);
    public static final Wrap<SoundEvent> ES_BLADE_SWING_SMALL = defer("entity.blade_swing_small", 4);
    public static final Wrap<SoundEvent> ES_BULLET_IMPACT_DIRT = defer("entity.bullet_impact_dirt", 3);
    public static final Wrap<SoundEvent> ES_BULLET_IMPACT_GENERIC = defer("entity.bullet_impact_generic", 4);
    public static final Wrap<SoundEvent> ES_BULLET_IMPACT_GLASS = defer("entity.bullet_impact_glass", 3);
    public static final Wrap<SoundEvent> ES_BULLET_IMPACT_STONE = defer("entity.bullet_impact_stone", 4);
    public static final Wrap<SoundEvent> ES_BULLET_IMPACT_WATER = defer("entity.bullet_impact_water", 6);
    public static final Wrap<SoundEvent> ES_BULLET_IMPACT_WOOD = defer("entity.bullet_impact_wood", 4);
    public static final Wrap<SoundEvent> ES_GHOUL_DEATH = defer("entity.ghoul_death", 3);
    public static final Wrap<SoundEvent> ES_GHOUL_HURT = defer("entity.ghoul_hurt", 4);
    public static final Wrap<SoundEvent> ES_GHOUL_IDLE = defer("entity.ghoul_idle", 4);
    public static final Wrap<SoundEvent> ES_GHOUL_PUNCH = defer("entity.ghoul_punch", 3);
    public static final Wrap<SoundEvent> ES_HIGH_VELOCITY_BULLET = defer("entity.high_velocity_bullet", 11);
    public static final Wrap<SoundEvent> ES_ICE_ELEMENTAL_HURT = defer("entity.ice_elemental_hurt", 4);
    public static final Wrap<SoundEvent> ES_ICE_ELEMENTAL_DEATH = defer("entity.ice_elemental_death", 2);
    public static final Wrap<SoundEvent> ES_ICE_ELEMENTAL_CHARGE_ATTACK = defer("entity.ice_elemental_charge_attack", 3);
    public static final Wrap<SoundEvent> ES_ICE_SPIKE_HIT = defer("entity.ice_spike_hit", 3);
    public static final Wrap<SoundEvent> ES_KITE_ATTACH = defer("entity.kite_attach", 4);
    public static final Wrap<SoundEvent> ES_KITE_DETACH = defer("entity.kite_detach", 4);
    public static final Wrap<SoundEvent> ES_KITE_LOOP = defer("entity.kite_loop", 1);
    public static final Wrap<SoundEvent> ES_KLIFOUR_HIDE = defer("entity.klifour_hide", 1);
    public static final Wrap<SoundEvent> ES_KLIFOUR_HURT = defer("entity.klifour_hurt", 3);
    public static final Wrap<SoundEvent> ES_KLIFOUR_SCRUB = defer("entity.klifour_scrub", 3);
    public static final Wrap<SoundEvent> ES_KLIFOUR_SHOW_UP = defer("entity.klifour_show_up", 1);
    public static final Wrap<SoundEvent> ES_KLIFOUR_SPIT = defer("entity.klifour_spit", 6);
    public static final Wrap<SoundEvent> ES_KLIFOUR_SPIT_IMPACT = defer("entity.klifour_spit_impact", 8);
	public static final Wrap<SoundEvent> ES_POSSESSED_ARMOR_CREAK = defer("entity.armor_creak", 4);
	public static final Wrap<SoundEvent> ES_POSSESSED_ARMOR_DEEP_CREAK = defer("entity.armor_deep_creak", 2);
	public static final Wrap<SoundEvent> ES_POSSESSED_ARMOR_HARD_STEP = defer("entity.possessed_armor_hard_step", 1);
	public static final Wrap<SoundEvent> ES_POSSESSED_ARMOR_HEAVY_CREAK = defer("entity.armor_heavy_creak", 4);
	public static final Wrap<SoundEvent> ES_POSSESSED_ARMOR_HIT = defer("entity.armor_hit", 6);
	public static final Wrap<SoundEvent> ES_POSSESSED_ARMOR_LOW_CREAK = defer("entity.armor_low_creak", 6);
	public static final Wrap<SoundEvent> ES_POSSESSED_ARMOR_THIN_CREAK = defer("entity.armor_thin_creak", 4);
	public static final Wrap<SoundEvent> ES_POSSESSED_ARMOR_DASH = defer("entity.possessed_armor_dash", 2);
    public static final Wrap<SoundEvent> ES_POSSESSED_ARMOR_DEATH = defer("entity.possessed_armor_death", 1);
    public static final Wrap<SoundEvent> ES_POSSESSED_ARMOR_JIGGLES = defer("entity.possessed_armor_jiggles", 5);
    public static final Wrap<SoundEvent> ES_POSSESSED_ARMOR_SLASH = defer("entity.possessed_armor_slash", 3);
    public static final Wrap<SoundEvent> ES_POSSESSED_ARMOR_STEP = defer("entity.possessed_armor_step", 4);
    public static final Wrap<SoundEvent> ES_POSSESSED_ARMOR_SWISH = defer("entity.possessed_armor_swish", 3);
    public static final Wrap<SoundEvent> ES_SQUIG_BUBBLE_GENERATE = defer("entity.squig_bubble_generate", 7);
    public static final Wrap<SoundEvent> ES_SQUIG_HURT = defer("entity.squig_hurt", 3);
    public static final Wrap<SoundEvent> ES_SQUIG_IDLE = defer("entity.squig_idle", 5);
    public static final Wrap<SoundEvent> ES_SQUIG_JUMP = defer("entity.squig_jump", 3);
    public static final Wrap<SoundEvent> ES_SWOOSH = defer("entity.swoosh", 3);
    public static final Wrap<SoundEvent> ES_TEST_DUMMY_HIT = defer("entity.test_dummy_hit", 6);
    public static final Wrap<SoundEvent> ES_TEST_DUMMY_PLACE = defer("entity.test_dummy_place", 4);
    public static final Wrap<SoundEvent> ES_TEST_DUMMY_REMOVE = defer("entity.test_dummy_remove", 4);
    public static final Wrap<SoundEvent> ES_TUMEFEND_HOP = defer("entity.tumefend_hop", 4);
    public static final Wrap<SoundEvent> ES_CURSED_SKULL_HURT = defer("entity.cursed_skull_hurt", 3);
    public static final Wrap<SoundEvent> ES_CURSED_SKULL_DEATH = defer("entity.cursed_skull_death", 1);
    public static final Wrap<SoundEvent> ES_CURSED_SKULL_SPIT = defer("entity.cursed_skull_spit", 2);
    public static final Wrap<SoundEvent> ES_CURSED_SKULL_IDLE = defer("entity.cursed_skull_idle", 3);
    public static final Wrap<SoundEvent> ES_CURSED_FIREBALL_HIT = defer("entity.cursed_fireball_hit", 1);
    public static final Wrap<SoundEvent> ES_GOBLIN_ATTACK = defer("entity.goblin_attack", 7);
    public static final Wrap<SoundEvent> ES_GOBLIN_COMBAT = defer("entity.goblin_combat", 6);
    public static final Wrap<SoundEvent> ES_GOBLIN_DEATH = defer("entity.goblin_death", 4);
    public static final Wrap<SoundEvent> ES_GOBLIN_HURT = defer("entity.goblin_hurt", 5);
    public static final Wrap<SoundEvent> ES_GOBLIN_IDLE = defer("entity.goblin_idle", 7);

    //ITEM
    public static final Wrap<SoundEvent> IS_MUSKET_SHOT = defer("item.musket_shot", 4);
	public static final Wrap<SoundEvent> IS_ECLIPSE_HIT = defer("item.eclipse_hit", 4);
    public static final Wrap<SoundEvent> IS_ECLIPSE_BLACKHOLE = defer("item.eclipse_blackhole", 3);
	public static final Wrap<SoundEvent> IS_GOLEM_HANDGUN_CHARGE = defer("item.golem_handgun_charge", 4);
	public static final Wrap<SoundEvent> IS_OUT_OF_AMMO = defer("item.out_of_ammo", 4);
	public static final Wrap<SoundEvent> IS_PEEK = defer("item.peek", 2);
	public static final Wrap<SoundEvent> IS_PISTOL_SHOT = defer("item.pistol_shot", 3);
	public static final Wrap<SoundEvent> IS_FROZEN_BOW_SHOT = defer("item.frozen_bow_shot", 4);
	public static final Wrap<SoundEvent> IS_SHOOTING_STAR_SHOT = defer("item.shooting_star_shot", 4);
	public static final Wrap<SoundEvent> IS_UNPEEK = defer("item.unpeek", 2);
    public static final Wrap<SoundEvent> IS_EBBEWEL_IMPULSE = defer("item.ebbewel_impulse", 4);
    public static final Wrap<SoundEvent> IS_ECLIPSE_SWEEP = defer("item.eclipse_sweep", 4);
    public static final Wrap<SoundEvent> IS_HEAVY_PISTOL_SHOT = defer("item.heavy_pistol_shot", 2);
    public static final Wrap<SoundEvent> IS_LIGHT_BOW_SHOT = defer("item.light_bow_shot", 1);
    public static final Wrap<SoundEvent> IS_SHOTGUN_SHOT = defer("item.shotgun_shot", 1);

	public static Wrap<SoundEvent> defer(String path, int sounds) {
        return HELPER.defer(path.substring(path.lastIndexOf('.') + 1), () -> new SoundEvent(new ResourceLocation(DannysExpansion.ID, path)));
    }
}
