package bottomtextdanny.dannys_expansion.content.entities.ai;

import bottomtextdanny.braincell.mod.entity.psyche.pos_finder.MobPosPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;

public class HoverProfile {
    private final MobPosPredicate<Object> predicate;
    public final int blockHeapUp;
    public final int blockHeapDown;
    public int blocksUp;
    public int blocksDown;

    public HoverProfile(MobPosPredicate<Object> predicate, int blockHeapUp, int blockHeapDown) {
        this.blockHeapUp = blockHeapUp;
        this.blockHeapDown = blockHeapDown;
        this.predicate = predicate;
    }

    public void update(Mob entity) {
        BlockPos position = entity.blockPosition();
        BlockPos.MutableBlockPos mu = new BlockPos.MutableBlockPos();

        mu.set(position);

        int i = 0;

        while (predicate.test(entity, mu, null) && i < blockHeapUp) {
            i++;
            mu.move(0, 1, 0);
        }

        this.blocksUp = i;

        i = 0;
        mu.set(position);

        while (predicate.test(entity, mu, null) && i < blockHeapDown) {
            i++;
            mu.move(0, -1, 0);
        }

        this.blocksDown = i;
    }

    public float getNormalized() {
        return blocksDown / (float)(blocksDown + blocksUp);
    }

    public boolean isGroundLowerThan(int down) {
        return blocksDown > down;
    }

    public boolean isCeilingHigher(int up) {
        return blocksUp > up;
    }

    public int equilibriumOffset() {
        return this.blocksUp - (this.blocksDown + 1);
    }
}
