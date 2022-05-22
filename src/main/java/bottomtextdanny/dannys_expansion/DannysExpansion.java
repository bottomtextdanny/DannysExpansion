package bottomtextdanny.dannys_expansion;

import bottomtextdanny.dannys_expansion._base.gameplay_hooks.*;
import bottomtextdanny.dannys_expansion._base.sensitive_hooks.CommandHooks;
import bottomtextdanny.dannys_expansion.content.items.arrow.DEArrowItem;
import bottomtextdanny.dannys_expansion.tables.*;
import bottomtextdanny.dannys_expansion.tables._client.Ambiences;
import bottomtextdanny.dannys_expansion.tables._client.DEParticles;
import bottomtextdanny.dannys_expansion.content.items.ItemDescriptionAutogen;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.LazyRecipeType;
import bottomtextdanny.dannys_expansion.tables.items.DEItems;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.ItemStackPredicateTypes;
import bottomtextdanny.dannys_expansion.content.items.AccessoryItem;
import bottomtextdanny.dannys_expansion.content.items.MaterialItem;
import bottomtextdanny.dannys_expansion.content.items.bullet.Bullet;
import bottomtextdanny.de_json_generator.DannyWriter;
import bottomtextdanny.de_json_generator.jsonBakers.JsonUtilsMiddleEnd;
import bottomtextdanny.de_json_generator.types.base.Generator;
import com.google.gson.JsonObject;
import bottomtextdanny.braincell.mod.BraincellModules;
import bottomtextdanny.braincell.mod._base.registry.managing.DeferrorType;
import bottomtextdanny.braincell.mod._base.registry.managing.ModDeferringManager;
import bottomtextdanny.braincell.mod._mod.SortedCreativeTab;
import bottomtextdanny.braincell.mod.network.Connection;
import bottomtextdanny.braincell.mod.world.builtin_items.BCBoatItem;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Mod(DannysExpansion.ID)
public final class DannysExpansion {
    public static final String ID = "dannys_expansion";
    public static CreativeModeTab TAB = new SortedCreativeTab(ID) {
	    @Override
	    public ItemStack makeIcon() {
		    return new ItemStack(DEItems.ICON.get());
	    }
    };
    public static final ModDeferringManager DE_REGISTRY_MANAGER = new ModDeferringManager(ID);
    private static final DECommonSide COMMON = new DECommonSide(ID);
    private static final DEServerSide SERVER = new DEServerSide(ID);
    @OnlyIn(Dist.CLIENT)
    private static DEClientSide CLIENT;

    static {
        Connection.doClientSide(() -> CLIENT = Connection.makeClientSide(() -> new DEClientSide(ID)));
    }

    public DannysExpansion() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        BraincellModules.PLAYER_MODEL_HOOKS.activate();
        BraincellModules.MOUSE_HOOKS.activate();
        BraincellModules.ENTITY_HURT_CALL_OUT.activate();

        modEventBus.addListener(DannysExpansion::commonSetupPhaseHook);

        COMMON.modLoadingCallOut();
        Connection.doClientSide(() -> {
            modEventBus.addListener(DannysExpansion::clientSetupPhaseHook);
            forgeEventBus.addListener(MouseHooks::mouseSensibilityHook);
            CLIENT.modLoadingCallOut();
        });
        Connection.doServerSide(() -> {
            modEventBus.addListener(DannysExpansion::serverSetupPhaseHook);
            SERVER.modLoadingCallOut();
        });

        forgeEventBus.addListener(AccessoryHooks::onAllAccessoriesCollection);
        forgeEventBus.addListener(AccessoryHooks::onEmptyKeysCollection);
        forgeEventBus.addListener(DeathHooks::livingDeathHook);
        forgeEventBus.addListener(HurtHooks::livingHurtHook);
        forgeEventBus.addListener(ConnectionHooks::trackEntityHook);
        forgeEventBus.addListener(ConnectionHooks::playerLogInHook);
        forgeEventBus.addListener(ConnectionHooks::copyPlayerHook);
        forgeEventBus.addListener(TickHooks::serverLevelTickHook);
        forgeEventBus.addListener(TickHooks::playerTickHook);
        forgeEventBus.addListener(CommandHooks::commandRegistryHook);

        Connection.doClientSide(() -> {
            modEventBus.addListener(this::clientSetup);
            Ambiences.loadClass();
        });

        ItemStackPredicateTypes.loadClass();
        LazyRecipeType.loadClass();
	    DEEvaluations.loadClass();
        DEAccessoryKeys.loadClass();
        DEStructures.loadClass();
        DEMiniAttributes.loadClass();

        DE_REGISTRY_MANAGER.addRegistryDeferror(DeferrorType.SOUND_EVENT, DESounds.ENTRIES);
        DE_REGISTRY_MANAGER.addRegistryDeferror(DeferrorType.ATTRIBUTE, DEAttributes.ENTRIES);
        DE_REGISTRY_MANAGER.addRegistryDeferror(DeferrorType.STRUCTURE, DEStructures.ENTRIES);
        //SOLVING_STATE.addRegistryDeferror(DeferrorType.FEATURE, DEFeatures.ENTRIES);
        DE_REGISTRY_MANAGER.addRegistryDeferror(DeferrorType.BLOCK, DEBlocks.ENTRIES);
        DE_REGISTRY_MANAGER.addRegistryDeferror(DeferrorType.ITEM, DEItems.ENTRIES);
        DE_REGISTRY_MANAGER.addRegistryDeferror(DeferrorType.MANU_TYPE, DEMenuTypes.ENTRIES);
        DE_REGISTRY_MANAGER.addRegistryDeferror(DeferrorType.MOB_EFFECT, DEEffects.ENTRIES);
        DE_REGISTRY_MANAGER.addRegistryDeferror(DeferrorType.ENTITY_TYPE, DEEntities.ENTRIES);
        DE_REGISTRY_MANAGER.addRegistryDeferror(DeferrorType.BLOCK_ENTITY_TYPE, DEBlockEntities.ENTRIES);
        DE_REGISTRY_MANAGER.addRegistryDeferror(DeferrorType.PARTICLE_TYPE, DEParticles.ENTRIES);
        DE_REGISTRY_MANAGER.addRegistryDeferror(DeferrorType.RECIPE_SERIALIZER, DERecipes.ENTRIES);
        DE_REGISTRY_MANAGER.solveAndLockForeverEver();
    }

    public static ResourceLocation id(String name) {
        return new ResourceLocation(ID, name);
    }

    public static void commonSetupPhaseHook(FMLCommonSetupEvent event) {
        COMMON.postModLoadingPhaseCallOut();
    }

    public static void serverSetupPhaseHook(FMLDedicatedServerSetupEvent event) {
        ((DEServerSide) SERVER).postModLoadingPhaseCallOut();
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientSetupPhaseHook(FMLClientSetupEvent event) {
        ((DEClientSide) CLIENT).postModLoadingPhaseCallOut();
    }

    public static DECommonSide common() {
        return COMMON;
    }

    public static DEServerSide server() {
        return (DEServerSide) SERVER;
    }

    @OnlyIn(Dist.CLIENT)
    public static DEClientSide client() {
        return CLIENT;
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
	    DEBlockEntities.registerBlockEntityRenderers();
	    DEMenuTypes.registerScreens();
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
	    	} else if (item instanceof DEArrowItem) {
                iterationObj.add("type", JsonUtilsMiddleEnd.cString("arrow"));
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
                
                if (item instanceof ItemDescriptionAutogen descriptionGenerator) {
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