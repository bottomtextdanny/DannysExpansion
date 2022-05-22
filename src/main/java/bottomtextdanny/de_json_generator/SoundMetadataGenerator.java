package bottomtextdanny.de_json_generator;

import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.de_json_generator.jsonBakers.JsonUtilsMiddleEnd;
import bottomtextdanny.de_json_generator.jsonBakers.SoundMetadata;
import bottomtextdanny.de_json_generator.jsonBakers.SoundMetadataBaker;
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

public class SoundMetadataGenerator {

    public static void generate() throws IOException {
        List<SoundMetadata> dataList = collectData();

        try {
            File file = Generator.createSoundMetadata();
            SoundMetadataBaker baker = new SoundMetadataBaker(dataList);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(JsonUtilsMiddleEnd.parse(baker.bake().decode()));
            writer.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<SoundMetadata> collectData() {
        LinkedList<SoundMetadata> list = Lists.newLinkedList();
        String source = GeneratorUtils.getClassSource(DESounds.class);
        String[] lines = source.split("\\n");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (!line.isEmpty()) {
                SoundMetadata bakingData = getDataOffLine(line);

                if (bakingData != null) {
                    list.add(bakingData);
                }
            }
        }

        return list;
    }

    @Nullable
    public static SoundMetadata getDataOffLine(String line) {

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
                if (bool[0]) {
                    bool[0] = false;
                    return true;
                } else {
                    return false;
                }
            }
        });

        String spriteNumber = getFirstMatchingString(line, data -> isDigit(data.current));

        System.out.println(spriteNumber);
        if (key == null || spriteNumber == null) return null;
        else return new SoundMetadata(key.substring(1, key.length() - 1), Integer.parseInt(spriteNumber));
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

    public static boolean isDigit(char character) {
        return character >= '0' && character <= '9';
    }

}
