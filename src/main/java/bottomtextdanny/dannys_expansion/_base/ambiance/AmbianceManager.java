package bottomtextdanny.dannys_expansion._base.ambiance;

import bottomtextdanny.dannys_expansion._util._client.CMC;
import bottomtextdanny.dannys_expansion.tables._client.Ambiences;
import bottomtextdanny.dannys_expansion.content._client.ambiances.Ambiance;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.*;
import java.util.stream.IntStream;

@OnlyIn(Dist.CLIENT)
public class AmbianceManager {
    private final HashMap<Ambiance, Integer> ambianceAccessors = new HashMap<>();
    private Ambiance currentAmbiance = Ambiences.NONE;

    public void processTick(Level world) {
        if (isAmbianceUpdating(world)) {
            handleAmbiance();
        }
        this.currentAmbiance.tick();
    }

    public void handleAmbiance() {
        LocalPlayer player = CMC.player();
        ClientLevel world = player.clientLevel;

        if (this.ambianceAccessors.isEmpty()) {
            for (Ambiance ambiance : Ambiance.AMBIANCES) {
                this.ambianceAccessors.put(ambiance, 0);
            }
        }

        this.ambianceAccessors.replaceAll((k, v) -> 0);

        if (player.isAlive()) {
            IntStream.range(-30, 30).parallel().forEach(x -> {
                for (int z = -30; z < 30; z++) {
                    for (int y = -30; y < 10; y++) {
                        BlockPos position = player.blockPosition().offset(x, y, z);
                        BlockState block = world.getBlockState(position);
                        if (block.getBlock() instanceof AmbientWeightProvider ambientBlock) {
                            int old = this.ambianceAccessors.get(ambientBlock.ambiance());
                            int addition = ambientBlock.weightOnAmbiance(block, position, player.clientLevel);
                            this.ambianceAccessors.put(ambientBlock.ambiance(), Math.min(0x0ffff, old + addition));
                        }
                    }
                }
            });
        }

        boolean changed = false;

        List<Map.Entry<Ambiance, Integer>> sorted =
                new ArrayList<>(ambianceAccessors.entrySet());
        sorted.sort(Comparator.comparingInt(Map.Entry::getValue));

        for (Map.Entry<Ambiance, Integer> entry : sorted) {
            int weight = entry.getValue();
            Ambiance ambiance = entry.getKey();

            if (entry.getKey().meetsConditions(player, weight)) {
                changed = true;
                if (this.currentAmbiance != ambiance) {
                    this.currentAmbiance.setAmbianceLoopSound(null);
                    ambiance.setAmbianceLoopSound(null);
                }
                this.currentAmbiance = ambiance;
                break;
            }
        }

        if (!changed) {
            this.currentAmbiance = Ambiences.NONE;
        }
    }

    public Ambiance getCurrentAmbiance() {
        return this.currentAmbiance;
    }

    public boolean isAmbianceUpdating(Level world) {
        return world.getGameTime() % 20L == 0L;
    }

    public int getAmbianceValue(Ambiance type) {
        return this.ambianceAccessors.getOrDefault(type, 0);
    }

    public HashMap<Ambiance, Integer> getAmbientAccessors() {
        return this.ambianceAccessors;
    }
}
