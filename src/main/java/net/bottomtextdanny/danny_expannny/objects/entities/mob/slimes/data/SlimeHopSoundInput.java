package net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data;

import net.bottomtextdanny.dannys_expansion.core.Util.qol.RandomFloatMapper;
import net.minecraft.sounds.SoundEvent;

public record SlimeHopSoundInput(SoundEvent hop, SoundEvent fall, RandomFloatMapper pitch) {}
