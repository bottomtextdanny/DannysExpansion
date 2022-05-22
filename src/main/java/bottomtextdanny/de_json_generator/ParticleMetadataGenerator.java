package bottomtextdanny.de_json_generator;

import bottomtextdanny.dannys_expansion.tables._client.DEParticles;
import bottomtextdanny.de_json_generator.jsonBakers.JsonUtilsMiddleEnd;
import bottomtextdanny.de_json_generator.jsonBakers.ParticleMetadataBaker;
import bottomtextdanny.de_json_generator.types.base.Generator;
import bottomtextdanny.de_json_generator.types.base.GeneratorUtils;
import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class ParticleMetadataGenerator implements GeneratorUtils {

    public static void generate() throws IOException {
        List<Data> dataList = collectData();

        try {
            for (Data data : dataList) {
                List<String> textureEntryList = Lists.newLinkedList();
                int parsedSpriteNumber = Integer.parseInt(data.spriteNumber) + 1;
                for (int i = 0; i < parsedSpriteNumber; i++) {
                    textureEntryList.add("dannys_expansion:" + data.key + '_' + i);
                }
                File file = Generator.createParticleMetadata(data.key.substring(data.key.indexOf(':') + 1));
                ParticleMetadataBaker baker = new ParticleMetadataBaker(textureEntryList);
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));

                writer.write(JsonUtilsMiddleEnd.parse(baker.bake().decode()));
                writer.close();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<Data> collectData() {
        LinkedList<Data> list = Lists.newLinkedList();
        String source = GeneratorUtils.getClassSource(DEParticles.class);
        String[] lines = source.split("\\n");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (!line.isEmpty()) {
                Data bakingData = getDataOfField(line);

                if (bakingData != null) {
                    list.add(bakingData);
                }
            }
        }

        return list;
    }

    @Nullable
    public static Data getDataOfField(String line) {

        if (!line.contains("public static final Wrap")) return null;

        boolean[] bool = new boolean[1];
        String key = getFirstMatchingString(line, data -> {
            if (!bool[0]) {
                if (data.current == '\"') {
                    bool[0] = true;
                    return true;
                } else {
                    return false;
                }
            } else if (data.current != '\"') {
                return true;
            } else {
                bool[0] = false;
                return true;
            }
        });

        String spriteNumber = getFirstMatchingString(line, data -> isDigit(data.current));

        if (key == null || spriteNumber == null) return null;
        else return new Data(key.substring(1, key.length() - 1), spriteNumber);
    }

    @Nullable
    public static String getFirstMatchingString(String base, Predicate<StringPredicateData> predicate) {
        byte[] bytes = base.getBytes();
        int firstIndex = -1;
        int secondIndex = -1;

        for (int i = 0; i < bytes.length; i++) {
            char ch = (char)bytes[i];

            if (predicate.test(new StringPredicateData(base, i, ch))) {
                if (firstIndex == -1) {
                    firstIndex = i;
                }
                secondIndex = i;
            } else if (firstIndex != -1) {
                break;
            }
        }

        if (firstIndex == -1) return null;
        else return base.substring(firstIndex, secondIndex + 1);
    }

    record StringPredicateData(String base, int index, char current) {}

    record Data(String key, String spriteNumber) {}

    public static boolean isDigit(char character) {
        return character >= '0' && character <= '9';
    }
}
