package bottomtextdanny.flagged_schema_block;

import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nullable;
import java.util.List;

public class ClientSchemaMakerEntry {
    private Block block;
    private String name;
    @Nullable
    private IntList flags;
    @Nullable
    private List<String> properties;
    private int index = -1;

    public ClientSchemaMakerEntry() {
        this.block = Blocks.STONE;
        this.name = "";
    }

    public ClientSchemaMakerEntry(Block block) {
        this.block = block;
        this.name = "";
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setBlock(Block block) {
        if (block == null) this.block = Blocks.AIR;
        else this.block = block;
    }

    public void setName(String name) {
        if (name == null) this.name = "";
        else this.name = name;
    }

    public void setFlags(IntList flags) {
        this.flags = flags;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties;
    }

    public int getIndex() {
        return index;
    }

    public Block getBlock() {
        return block;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public IntList getFlags() {
        return flags;
    }

    public List<String> getProperties() {
        return properties;
    }
}
