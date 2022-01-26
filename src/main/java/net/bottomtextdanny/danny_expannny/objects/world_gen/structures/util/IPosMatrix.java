package net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util;

import net.bottomtextdanny.dannys_expansion.core.Util.Pair;
import net.minecraft.core.BlockPos;

import java.util.List;

public interface IPosMatrix {

    List<Pair<BlockPos, Byte>> getMatrixList();
}
