package net.bottomtextdanny.danny_expannny;

import com.google.gson.JsonObject;
import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.base.annotations.OnlyHandledOnServer;
import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.DeferrorType;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.ModDeferringManager;
import net.bottomtextdanny.braincell.mod.structure.BraincellModules;
import net.bottomtextdanny.braincell.mod.structure.SortedCreativeTab;
import net.bottomtextdanny.braincell.mod.world.helpers.CombatHelper;
import net.bottomtextdanny.braincell.mod.world.helpers.EffectHelper;
import net.bottomtextdanny.danny_expannny.object_tables.*;
import net.bottomtextdanny.danny_expannny.object_tables.items.DEItems;
import net.bottomtextdanny.danny_expannny.structure.DEClientSide;
import net.bottomtextdanny.danny_expannny.structure.DECommonSide;
import net.bottomtextdanny.danny_expannny.structure.DEServerSide;
import net.bottomtextdanny.danny_expannny.object_tables.DETags;
import net.bottomtextdanny.dannys_expansion.common.Evaluations;
import net.bottomtextdanny.danny_expannny.objects.items.MaterialItem;
import net.bottomtextdanny.danny_expannny.objects.items.SpecialKiteItem;
import net.bottomtextdanny.danny_expannny.objects.items.AccessoryItem;
import net.bottomtextdanny.danny_expannny.objects.items.bullet.Bullet;
import net.bottomtextdanny.dannys_expansion.common.crumbs.CrumbRoot;
import net.bottomtextdanny.dannys_expansion.common.hooks.*;
import net.bottomtextdanny.dannys_expansion.core.ClientManager;
import net.bottomtextdanny.dannys_expansion.core.DEDebugger;
import net.bottomtextdanny.dannys_expansion.core.Registries.DannyKeybinds;
import net.bottomtextdanny.dannys_expansion.core.Registries.DannyStructures;
import net.bottomtextdanny.dannys_expansion.core.Util.KiteStitcherManager;
import net.bottomtextdanny.dannys_expansion.core.Util.mixin.IEntityTypeExt;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.AbstractEvaluation;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.bottomtextdanny.danny_expannny.accessory.AccessoryKey;
import net.bottomtextdanny.danny_expannny.object_tables.DEAccessoryKeys;
import net.bottomtextdanny.braincell.mod.world.builtin_items.BCBoatItem;
import net.bottomtextdanny.dannys_expansion.core.base.item.IDescriptionGen;
import net.bottomtextdanny.danny_expannny.capabilities.CapabilityManager;
import net.bottomtextdanny.dannys_expansion.core.config.ClientConfigurationHandler;
import net.bottomtextdanny.dannys_expansion.core.config.common.CommonConfigurationHandler;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe.LazyDeserializers;
import net.bottomtextdanny.de_json_generator.DannyWriter;
import net.bottomtextdanny.de_json_generator.jsonBakers.JsonUtilsMiddleEnd;
import net.bottomtextdanny.de_json_generator.types.base.Generator;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


@Mod(DannysExpansion.ID)
public final class DannysExpansion {
	private static boolean deferSolvingClosed;
	public static final Logger LOGGER = LogManager.getLogger(DannysExpansion.ID);
    public static final DEDebugger DEBUG = new DEDebugger();
    public static final String ID = "dannys_expansion";
    public static CreativeModeTab TAB = new SortedCreativeTab(ID) {
	    @Override
	    public ItemStack makeIcon() {
		    return new ItemStack(DEItems.ICON.get());
	    }
    };
    public static final LinkedList<Runnable> CLIENT_SETUP_CALLS = new LinkedList<>();
    public static final LinkedList<Runnable> COMMON_SETUP_CALLS = new LinkedList<>();
    public static ModDeferringManager solvingState = new ModDeferringManager(DannysExpansion.ID);
    private static final DECommonSide common = DECommonSide.with(ID);
    @OnlyHandledOnServer
    private static final Object server = Connection.makeServerSideUnknown(() -> DEServerSide.with(ID));
    @OnlyIn(Dist.CLIENT)
    private static final DEClientSide client = Connection.makeClientSide(() -> DEClientSide.with(ID));
    @OnlyIn(Dist.CLIENT)
    public static ClientManager clientManager;

    public DannysExpansion() {
        Braincell core = new Braincell();
        if (FMLEnvironment.production) {
            solvingState.addHook(DeferrorType.ITEM, (item, solving) -> {
                if (item instanceof SpecialKiteItem kite) {
                    if (Braincell.common().isModThere(kite.getEntityKeyId().getNamespace())) {
                        if (kite.getCachedEntity() != null) {
                            KiteStitcherManager.getDeferredStitching().add(kite);
                        } else {
                            KiteStitcherManager.getKiteMapByString().put(kite.getEntityKeyId(), kite);
                        }
                    }
                }
            });
        }

        //
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        BraincellModules.PLAYER_MODEL_HOOKS.activate();
        BraincellModules.MOUSE_HOOKS.activate();
        BraincellModules.ITEM_ENTITY_CUSTOM_MODEL.activate();
        BraincellModules.ENTITY_HURT_CALL_OUT.activate();
        modEventBus.addListener(DannysExpansion::commonSetupPhaseHook);
        common.modLoadingCallOut();
        Connection.doClientSide(() -> {
            modEventBus.addListener(DannysExpansion::clientSetupPhaseHook);
            ((DEClientSide)client).modLoadingCallOut();
        });
        Connection.doServerSide(() -> {
            modEventBus.addListener(DannysExpansion::serverSetupPhaseHook);
            ((DEServerSide)server).modLoadingCallOut();
        });

        //
        modEventBus.addListener(this::setupCommon);
        forgeEventBus.addListener(AttackHooks::damageLivingHook);
        forgeEventBus.addListener(AttackHooks::criticalHitHook);
        forgeEventBus.addListener(DeathHooks::livingDeathHook);
        forgeEventBus.addListener(HurtHooks::livingHurtHook);
        forgeEventBus.addListener(ConnectionHooks::trackEntityHook);
        forgeEventBus.addListener(ConnectionHooks::playerLogInHook);
        forgeEventBus.addListener(ConnectionHooks::copyPlayerHook);
        forgeEventBus.addListener(MovementHooks::livingFallHook);
        forgeEventBus.addListener(MovementHooks::livingJumpHook);
        forgeEventBus.addListener(TickHooks::serverLevelTickHook);
        forgeEventBus.addListener(TickHooks::playerTickHook);
        forgeEventBus.addListener(CommandHooks::commandRegistryHook);
        forgeEventBus.addListener(ChunkHooks::chunkLoadingHook);
        forgeEventBus.addListener(ChunkHooks::chunkDataLoadingHook);
        forgeEventBus.addListener(ChunkHooks::chunkUnloadingHook);
        forgeEventBus.addListener(ChunkHooks::chunkDataUnloadingHook);
        forgeEventBus.addListener(this::setupServer);
        Connection.doClientSide(() -> modEventBus.addListener(this::clientSetup));
        Connection.doClientSide(() -> DEAmbiences.loadClass());
	    Evaluations.loadClass();
        CrumbRoot.loadClass();
        DEAccessoryKeys.loadClass();
        DETags.loadClass();
        DannyStructures.loadClass();
        solvingState.addRegistryDeferror(DeferrorType.SOUND_EVENT, DESounds.ENTRIES);
        solvingState.addRegistryDeferror(DeferrorType.ATTRIBUTE, DEAttributes.ENTRIES);
        solvingState.addRegistryDeferror(DeferrorType.FEATURE, DEFeatures.ENTRIES);
        solvingState.addRegistryDeferror(DeferrorType.BIOME, DEBiomes.ENTRIES);
        solvingState.addRegistryDeferror(DeferrorType.BLOCK, DEBlocks.ENTRIES);
        solvingState.addRegistryDeferror(DeferrorType.ITEM, DEItems.ENTRIES);
        solvingState.addRegistryDeferror(DeferrorType.MANU_TYPE, DEMenuTypes.ENTRIES);
        solvingState.addRegistryDeferror(DeferrorType.MOB_EFFECT, DEEffects.ENTRIES);
        solvingState.addRegistryDeferror(DeferrorType.ENTITY_TYPE, DEEntities.ENTRIES);
        solvingState.addRegistryDeferror(DeferrorType.BLOCK_ENTITY_TYPE, DEBlockEntities.ENTRIES);
        solvingState.addRegistryDeferror(DeferrorType.PARTICLE_TYPE, DEParticles.ENTRIES);
        solvingState.addRegistryDeferror(DeferrorType.RECIPE_SERIALIZER, DERecipes.ENTRIES);
	    LazyDeserializers.loadClass();
        solvingState.solveAndLockForeverEver();
	    deferSolvingClosed = true;
        AccessoryKey.build();
        EffectHelper.init();
        CombatHelper.EntityCheckHelper.init();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfigurationHandler.forgeConfigSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfigurationHandler.forgeConfigSpec);
    }

    @OnlyIn(Dist.CLIENT)
    public static ClientManager clientManager() {
        if (clientManager == null) clientManager = new ClientManager(ID);
        return clientManager;
    }
	
	public static boolean isDeferredSolvingClosed() {
		return deferSolvingClosed;
	}

    public static void commonSetupPhaseHook(FMLCommonSetupEvent event) {
        common.postModLoadingPhaseCallOut();
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    public static void serverSetupPhaseHook(FMLDedicatedServerSetupEvent event) {
        ((DEServerSide)server).postModLoadingPhaseCallOut();
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientSetupPhaseHook(FMLClientSetupEvent event) {
        ((DEClientSide)client).postModLoadingPhaseCallOut();
    }

    public static DECommonSide common() {
        return common;
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    public static DEServerSide server() {
        return (DEServerSide)server;
    }

    @OnlyIn(Dist.CLIENT)
    public static DEClientSide client() {
        return (DEClientSide)client;
    }
	
	public void setupCommon(FMLCommonSetupEvent event) {
	    AbstractEvaluation._build();
	    DEUtil.loadData();
	    CapabilityManager.callForSomeReason();
	    COMMON_SETUP_CALLS.forEach(Runnable::run);
	    COMMON_SETUP_CALLS.clear();
    }

    public void setupServer(FMLDedicatedServerSetupEvent event) {
		KiteStitcherManager.stitch();
    }

    @OnlyIn(Dist.CLIENT)
    public void clientSetup(FMLClientSetupEvent event) {
	    try{
		    generateLang();
		    generateItemList();
		   // generateSoundFile();
	    } catch (Exception unused){}
	    
//        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY,
//                () -> (minecraft, screen) -> new MainOptionsScreen(screen));
	

	    DEBlocks.registerBlockRenders();
	    DEBlockEntities.registerTileEntityRenderers();
	    DannyKeybinds.registerKeys();
	    DEMenuTypes.registerScreens();
	    CLIENT_SETUP_CALLS.forEach(Runnable::run);
	    CLIENT_SETUP_CALLS.clear();
    }
	
//	public static void generateSoundFile() throws IOException {
//		File file = Generator.createJsonMainDE("sounds");
//		JsonObject object = new JsonObject();
//		Set<String> names = new HashSet<>(0);
//
//		DannySounds.DE_SOUNDS.getRegistrable().forEach((sound, integer) -> {
//			JsonObject soundObject = new JsonObject();
//			String[] soundArray = new String[integer];
//			String soundName = sound.get().getLocation().getPath();
//
//			soundObject.add("category", soundName.contains("block") ? JsonUtilsMiddleEnd.cString("block") : JsonUtilsMiddleEnd.cString("neutral"));
//
//			for (int i = 0; i < integer; i++)
//				soundArray[i] = MOD_ID + ':' + soundName.replace('.', '/') + '_' + i;
//
//			soundObject.add("sounds", JsonUtilsMiddleEnd.cStringArray(soundArray));
//
//			object.add(soundName, soundObject);
//		});
//
//		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
//
//		writer.write(JsonUtilsMiddleEnd.parse(object));
//		writer.close();
//	}
    
    public static void generateItemList() throws IOException {
	    File file = Generator.createJsonMain("item_list");
	    JsonObject object = new JsonObject();
	    Set<JsonObject> objectSet = new HashSet<>(0);
	
	    DEItems.ENTRIES.getRegistryEntries().forEach(entry -> {
	    	JsonObject iterationObj = new JsonObject();
			Item item = entry.get();
	    	String itemString = item.getRegistryName().getPath();
	    	
	    	if (item instanceof AccessoryItem) {
			    iterationObj.add("type", JsonUtilsMiddleEnd.cString("accessory"));
		    } else if (item instanceof MaterialItem) {
			    iterationObj.add("type", JsonUtilsMiddleEnd.cString("material"));
		    } else if (item instanceof SpawnEggItem) {
			    iterationObj.add("type", JsonUtilsMiddleEnd.cString("spawn_egg"));
		    } else if (item instanceof Bullet) {
			    iterationObj.add("type", JsonUtilsMiddleEnd.cString("bullet"));
	    	} else if (item instanceof BCBoatItem) {
			    iterationObj.add("type", JsonUtilsMiddleEnd.cString("boat"));
			    iterationObj.add("recipe_material_id", JsonUtilsMiddleEnd.cString(((BCBoatItem)item).getType().materialItem.get().getRegistryName().toString()));
	    	} else {
			    iterationObj.add("type", JsonUtilsMiddleEnd.cString("indifferent/unused"));
		    }
		    
	    	iterationObj.add("name", JsonUtilsMiddleEnd.cString(itemString));
	    	objectSet.add(iterationObj);
	    });
	    
	    object.add("objects", JsonUtilsMiddleEnd.cObjectCollectionRaw(objectSet));
	
	    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	    
	    writer.write(JsonUtilsMiddleEnd.parse(object));
	    writer.close();
	
	    try {
		    File drivenFile = Generator.createJsonMain("pleaseworkthistimeparttwo");
		    
		    BufferedWriter drivenWriter = new BufferedWriter(new FileWriter(drivenFile));
		    
		    drivenWriter.write(JsonUtilsMiddleEnd.parse(JsonUtilsMiddleEnd.cString("cock")));
		    drivenWriter.close();
		    
	    } catch (Exception e) {
	    
	    }
    }

    public static void generateLang() throws IOException {
        File json = Generator.createLang("en_us");
        String base = Generator.getTemplateJson("/assets/", "/lang/", "handwritten");
        DannyWriter writer = new DannyWriter(new FileWriter(json));
        writer.write(base);
        writer.openBracket("");

        DESounds.ENTRIES.getRegistryEntries().forEach((soundWrap) -> {
            SoundEvent sound = soundWrap.get();
            String soundLocation = sound.getLocation().getPath();
            try {
                StringBuilder stringBuilder = new StringBuilder(sound.getRegistryName().getPath().replace('_', ' '));
                for (int i = 0; i < stringBuilder.length(); i++) {
                    if (i == 0 || stringBuilder.charAt(i - 1) == ' ') {
                        stringBuilder.setCharAt(i, Character.toUpperCase(stringBuilder.charAt(i)));
                    }
                }
                writer.still("\"dannys_expansion.subtitles." + soundLocation + "\": \"" + stringBuilder + "\",");

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        DEBlocks.ENTRIES.getRegistryEntries().forEach(blockWrap -> {
			Block block = blockWrap.get();
            try {
                StringBuilder stringBuilder = new StringBuilder(block.getRegistryName().getPath().replace('_', ' '));
                for (int i = 0; i < stringBuilder.length(); i++) {
                    if (i == 0 || stringBuilder.charAt(i - 1) == ' ') {
                        stringBuilder.setCharAt(i, Character.toUpperCase(stringBuilder.charAt(i)));
                    }
                }
                writer.still("\"block.dannys_expansion." + block.getRegistryName().getPath() + "\": \"" + stringBuilder + "\",");
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
	    DEAttributes.ENTRIES.getRegistryEntries().forEach(attributeWrap -> {
		    Attribute attribute = attributeWrap.get();
		    try {
			    StringBuilder stringBuilder = new StringBuilder(attribute.getDescriptionId().replace('_', ' '));
			    for (int i = 0; i < stringBuilder.length(); i++) {
				    if (i == 0 || stringBuilder.charAt(i - 1) == ' ') {
					    stringBuilder.setCharAt(i, Character.toUpperCase(stringBuilder.charAt(i)));
				    }
			    }
			    writer.still("\"attribute.name." + attribute.getDescriptionId() + "\": \"" + stringBuilder + "\",");
				
		    } catch (IOException e) {
			    e.printStackTrace();
		    }
	    });
        DEItems.ENTRIES.getRegistryEntries().forEach(objSet -> {
			Item item = objSet.get();
            try {
                StringBuilder stringBuilder = new StringBuilder(item.getRegistryName().getPath().replace('_', ' '));
                for (int i = 0; i < stringBuilder.length(); i++) {
                    if (i == 0 || stringBuilder.charAt(i - 1) == ' ') {
                        stringBuilder.setCharAt(i, Character.toUpperCase(stringBuilder.charAt(i)));
                    }
                }
                writer.still("\"item.dannys_expansion." + item.getRegistryName().getPath() + "\": \"" + stringBuilder + "\",");
                
                if (item instanceof IDescriptionGen descriptionGenerator) {
	                writer.still("\"description.dannys_expansion." + descriptionGenerator.specifyDescriptionPath() + "." + item.getRegistryName().getPath() + "\": \"" + descriptionGenerator.getGenerationDescription() + "\",");
	
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        int[] counter = {0};
        DEEntities.ENTRIES.getRegistryEntries().forEach(entityWrap -> {
			EntityType<?> entity = entityWrap.get();
            try {
                StringBuilder stringBuilder = new StringBuilder(entity.getRegistryName().getPath().replace('_', ' '));
                for (int i = 0; i < stringBuilder.length(); i++) {
                    if (i == 0 || stringBuilder.charAt(i - 1) == ' ') {
                        stringBuilder.setCharAt(i, Character.toUpperCase(stringBuilder.charAt(i)));
                    }
                }
                writer.write("\"entity.dannys_expansion." + entity.getRegistryName().getPath() + "\": \"" + stringBuilder + "\"");
                if (counter[0] < DEEntities.ENTRIES.getRegistryEntries().size() - 1) writer.write(',');
                writer.newLine();
                counter[0]++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.closeBracket("}");
        writer.close();
    }
}