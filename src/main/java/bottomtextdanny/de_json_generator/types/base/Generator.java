package bottomtextdanny.de_json_generator.types.base;

import bottomtextdanny.de_json_generator._gen.DannyGenerator;
import bottomtextdanny.de_json_generator.GenUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class Generator<B extends Generator<B>> extends GenUtils implements IGenerator<B> {
	protected String materialID;
    protected String name;

    public Generator(String name) {
        DannyGenerator.CURRENT_NAME = name;
        this.name = name;
    }

	public B materialID(String materialID) {
		this.materialID = materialID;
		return (B)this;
	}

	public String getName() {
        return this.name;
    }

	public String getMaterial() {
		return this.materialID;
	}

	public static File createJson(String direction, String subDirection, String fileName) {
    	
        File json = new File(PATH + direction + MOD_ID + subDirection + fileName + ".json");
        if (json.exists()) json.delete();
        try {
            json.createNewFile();


        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
	        System.out.println(PATH + direction + MOD_ID + subDirection + fileName + ".json");
        }

        return json;
    }
	
	public static File createJsonMain(String fileName) {
		
		File json = new File(PATH + "/assets/" + fileName + ".json");
		if (json.exists()) json.delete();
		try {
			json.createNewFile();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return json;
	}

	public static File createParticleMetadata(String fileName) {

		File json = new File(PATH + "/assets/dannys_expansion/particles/" + fileName + ".json");
		if (json.exists()) json.delete();
		try {
			json.createNewFile();


		} catch (IOException e) {
			e.printStackTrace();
		}

		return json;
	}

	public static File createSoundMetadata() {

		File json = new File(PATH + "/assets/dannys_expansion/sounds.json");
		if (json.exists()) json.delete();
		try {
			json.createNewFile();


		} catch (IOException e) {
			e.printStackTrace();
		}

		return json;
	}
	
	public static void sendResources() {
		
		File src = new File(PATH);
		
		File dst = new File("C:\\Users\\pc\\minecraft%20mod\\DannysExpansion1.16.5\\out\\production\\resources");
		
		
		for (int i = 0; i < 30; i++) {
			System.out.println("PROCESSING");
		}
		try {
			FileUtils.copyDirectory(src, dst);
			for (int i = 0; i < 30; i++) {
				System.out.println("DONE");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static File createJsonMainDE(String fileName) {
		
		File json = new File(PATH + "/assets/dannys_expansion/" + fileName + ".json");
		if (json.exists()) json.delete();
		try {
			json.createNewFile();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	public static void trashAgentRun(BiPredicate<String, String> strPredicate) {
		try {
			trashAgent(strPredicate, PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void trashAgent(BiPredicate<String, String> strPredicate, String path) throws IOException {
		File parent = new File(path);
		if (parent.isDirectory()) {
			File[] gottenFiles = parent.listFiles();
			if (gottenFiles != null) {
				
				for (File file : gottenFiles) {
					if (file.isDirectory()) {
						trashAgent(strPredicate, file.getPath());
					} else if (strPredicate.test(parent.getName(), file.getName())) {
						file.delete();
					}
				}
			}
		}
	}

	public static void trashAgentDir(Predicate<String> strPredicate, String path) throws IOException {
		File parent = new File(path);
		if (parent.isDirectory()) {
			File[] gottenFiles = parent.listFiles();
			if (gottenFiles != null) {

				for (File file : gottenFiles) {
					if (file.isDirectory()) {
						trashAgentDir(strPredicate, file.getPath());
					} else if (strPredicate.test(file.getParentFile().getName())) {
						file.delete();
					}
				}
			}
		}
	}

    public static File createBlockstate(String fileName) {
        return createJson("/assets/", "/blockstates/", fileName);
    }

    public static File createBlockModel(String fileName) {
        return createJson("/assets/", "/models/block/", fileName);
    }

    public static File createItemModel(String fileName) {
        return createJson("/assets/", "/models/item/", fileName);
    }

    public static File createLang(String fileName) {
        return createJson("/assets/", "/lang/", fileName);
    }

    public static File createBlockLootTable(String fileName) {
        return createJson("/data/", "/loot_tables/blocks/", fileName);
    }
	
	public static File createWBRecipe(String fileName) {
		return createJson("/data/", "/lazy_recipes/", fileName);
	}

	
	public static File createLootTable(String object, String fileName) {
        return createJson("/data/", "/loot_tables/" + object + "/", fileName);
    }

	public static File createRecipe(String fileName) {
		return createJson("/data/", "/recipes/", fileName);
	}

    public static String getTemplate(String directory, String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(TEMPLATE_PATH + "/" + directory + "/" + file + ".txt")));
    }

    public static String getTemplateJson(String directory, String subDirectory, String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(PATH + directory + Generator.MOD_ID + subDirectory + file + ".json")));
    }
}
