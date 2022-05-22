package bottomtextdanny.de_json_generator;

import bottomtextdanny.de_json_generator._gen.DannyGenerator;
import bottomtextdanny.de_json_generator.types.base.Generator;
import net.minecraft.resources.ResourceLocation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

public class TagBuffer {
    private final Set<String> values = new LinkedHashSet<>();
    private String path;
    private final String id;
    private final String directory;
    private final String name;

    public TagBuffer(String id, String directory, String name) {
        DannyGenerator.TAGS.add(this);
        if (!directory.contains("items&blocks")) {
            this.path = Generator.PATH + "/data/" + id + "/tags/" + directory + "/" + name + ".json";
        }

        this.id = id;
        this.directory = directory;
        this.name = name;
    }

    public boolean add(String str) {
        return this.values.add(str);
    }

    public void bake() {
        try {
            if (this.path != null) {
                createFile();
            } else {
                String extraDir = this.directory.replaceAll("items&blocks", "");
                this.path = Generator.PATH + "/data/" + this.id + "/tags/" + "items" + extraDir + "/" + this.name + ".json";
                createFile();
                this.path = Generator.PATH + "/data/" + this.id + "/tags/" + "blocks" + extraDir + "/" + this.name + ".json";
                createFile();
                this.path = Generator.PATH + "/data/" + this.id + "/tags/" + "items&blocks" + extraDir + "/" + this.name + ".json";
            }

        } catch (IOException unused) {}
    }

    public void createFile() throws IOException{
        File json = new File(this.path);
        if (json.exists()) json.delete();

        json.createNewFile();

        DannyWriter writer = new DannyWriter(new FileWriter(json));

        writer.openBracket("{");
        writer.still("\"replace\": false,");
        writer.openBracket("\"values\": [");

        int[] counter = {0};
        this.values.forEach(str -> {
            try {
                writer.write("\"" + new ResourceLocation(Generator.MOD_ID, str) + "\"");
                if (counter[0] < this.values.size() - 1) writer.write(',');
                writer.newLine();

                counter[0]++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        writer.closeBracket("]");
        writer.closeBracket("}");
        writer.close();
    }
}
