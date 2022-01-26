package net.bottomtextdanny.dannys_expansion.core;

import net.bottomtextdanny.braincell.mod.world.block_utilities.AmbientWeightProvider;
import net.bottomtextdanny.danny_expannny.object_tables.DEAmbiences;
import net.bottomtextdanny.danny_expannny.ClientInstance;
import net.bottomtextdanny.danny_expannny.rendering.ambiances.DEAmbiance;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.IntStream;

@OnlyIn(Dist.CLIENT)
public class AmbianceManager {
    private final HashMap<DEAmbiance, Integer> ambianceAccessors = new HashMap<>();
    private DEAmbiance currentAmbiance = DEAmbiences.NONE;

    public void processTick(Level world) {
        if (isAmbianceUpdating(world)) {
            handleAmbiance();
        }
        this.currentAmbiance.tick();
    }

    public void handleAmbiance() {
        LocalPlayer player = ClientInstance.player();
        ClientLevel world = player.clientLevel;

        if (this.ambianceAccessors.isEmpty()) {
            for (DEAmbiance ambiance : DEAmbiance.AMBIANCES) {
                this.ambianceAccessors.put(ambiance, 0);
            }
        }

        //DannysExpansion.DEBUG.start("ambiance test", 1L);
        this.ambianceAccessors.replaceAll((k, v) -> 0);
        if (player.isAlive()) {

//            DannyEx.DEBUG.start("hand made", -1L);
//            for (int x = -20; x < 20; x++) {
//                for (int z = -20; z < 20; z++) {
//                    for (int y = -20; y < 20; y++) {
//                        BlockPos position = player.blockPosition().offset(x, y, z);
//                        BlockState block = world.getBlockState(position);
//                        if (block.getBlock() instanceof IAmbientBlock) {
//                            IAmbientBlock ambientBlock = (IAmbientBlock) block.getBlock();
//                            ambianceAccessors.put(ambientBlock.ambiance(), Math.min(99999999, ambianceAccessors.get(ambientBlock.ambiance()) + ambientBlock.weightOnAmbiance(block, position, player.clientLevel)));
//                        }
//                    }
//                }
//            }
//            DannyEx.DEBUG.end();
//            DannyEx.DEBUG.chat();

//            DannyEx.DEBUG.start("parallel", -1000L);
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
//            DannyEx.DEBUG.end();
//            DannyEx.DEBUG.chat();
        }



        ArrayList<DEAmbiance> sorted = new ArrayList<>(this.ambianceAccessors.keySet());
        sorted.sort(Comparator.comparingInt(this.ambianceAccessors::get).reversed());

        boolean changed = false;
        for (DEAmbiance ambiance : sorted) {
            int weight = this.ambianceAccessors.get(ambiance);

            if (ambiance.meetsConditions(player, weight)) {
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
            this.currentAmbiance = DEAmbiences.NONE;
        }

        //DannysExpansion.DEBUG.end();
        //DannysExpansion.DEBUG.chat();
        //DannysExpansion.DEBUG.clear();
    }

    public DEAmbiance getCurrentAmbiance() {
        return this.currentAmbiance;
    }

    public boolean isAmbianceUpdating(Level world) {
        return world.getGameTime() % 20L == 0L;
    }

    public int getAmbianceValue(DEAmbiance type) {
        return this.ambianceAccessors.getOrDefault(type, 0);
    }

    public HashMap<DEAmbiance, Integer> getAmbientAccessors() {
        return this.ambianceAccessors;
    }
}
