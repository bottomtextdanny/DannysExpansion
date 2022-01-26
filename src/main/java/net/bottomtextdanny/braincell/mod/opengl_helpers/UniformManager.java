package net.bottomtextdanny.braincell.mod.opengl_helpers;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.bottomtextdanny.braincell.underlying.util.pair.Pair;

import java.util.Map;

import static org.lwjgl.opengl.GL43.glGetUniformLocation;

public final class UniformManager {
    private final Map<Integer, int[]> uniformArrayDirMap = Maps.newHashMap();
    private final Object2IntOpenHashMap<String> uniformDirMap = new Object2IntOpenHashMap<>();
    private final GLProgram<?> program;

    public UniformManager(GLProgram<?> program, Pair<String, Integer>... arraySizes) {
        super();
        this.program = program;
        if (arraySizes.length > 0) {
            int bracketIndex = arraySizes[0].left().indexOf('[');
            for (int i = 0; i < arraySizes.length; i++) {
                int size = arraySizes[i].right();
                int[] array = new int[size];
                String name = arraySizes[i].left();
                String firstPart = name.substring(0, bracketIndex + 1);
                String secondPart = name.substring(bracketIndex + 1);
                for (int j = 0; j < size; j++) {
                    array[j] = glGetUniformLocation(program.getProgramID(), firstPart + j + secondPart);
                }
                this.uniformArrayDirMap.put(i, array);
            }
        }
    }

    public int[] retrieveLocations(int pointer) {
        return this.uniformArrayDirMap.get(pointer);
    }

    public int retrieveLocation(String str) {
        if (!this.uniformDirMap.containsKey(str)) {
            int newLoc = glGetUniformLocation(this.program.getProgramID(), str);
            this.uniformDirMap.put(str, newLoc);
            return newLoc;
        }
        return this.uniformDirMap.getOrDefault(str, -1);
    }
}
