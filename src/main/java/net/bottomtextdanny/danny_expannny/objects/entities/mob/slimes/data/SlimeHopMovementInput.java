package net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.data;

import net.bottomtextdanny.dannys_expansion.core.Util.qol.RandomFloatMapper;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.RandomIntegerMapper;

public record SlimeHopMovementInput(float height, float speed, RandomIntegerMapper hopDelay) {}
