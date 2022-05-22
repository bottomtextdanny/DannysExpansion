package bottomtextdanny.flagged_schema_block;

import java.util.Set;

public class FlaggedPos {
    public final int lpx;
    public final int lpy;
    public final int lpz;
    private final Set<Integer> flags;

    public FlaggedPos(int lpx, int lpy, int lpz, Set<Integer> flags) {
        super();
        this.lpx = lpx;
        this.lpy = lpy;
        this.lpz = lpz;
        this.flags = flags;
    }

    public boolean hasFlag(int flag) {
        return this.flags.contains(flag);
    }
}
