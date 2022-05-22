package bottomtextdanny.dannys_expansion.content.entities.mob.slimes.data;

import bottomtextdanny.braincell.base.value_mapper.FloatMapper;
import net.minecraft.sounds.SoundEvent;

public record SlimeHopSoundInput(SoundEvent hop, SoundEvent fall, FloatMapper pitch) {}
