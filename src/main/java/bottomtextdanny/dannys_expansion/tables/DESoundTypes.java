package bottomtextdanny.dannys_expansion.tables;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;

public class DESoundTypes {
	public static final SoundType ECTOSHROOM = new SoundType(
            1.0F,
            1.0F,
            DESounds.BS_ECTOSHROOM_BREAK.get(),
            DESounds.BS_ECTOSHROOM_STEP.get(),
            DESounds.BS_ECTOSHROOM_PLACE.get(),
            DESounds.BS_ECTOSHROOM_DIG.get(),
            DESounds.BS_ECTOSHROOM_STEP.get());
    public static final SoundType COMPACT_ICE = new SoundType(
            1.0F,
            1.0F,
            DESounds.BS_COMPACT_ICE_BREAK.get(),
            SoundEvents.STONE_STEP,
            DESounds.BS_COMPACT_ICE_PLACE.get(),
            DESounds.BS_COMPACT_ICE_DIG.get(),
            SoundEvents.STONE_FALL);
    public static final SoundType HOLLOW_METAL = new SoundType(
            1.0F,
            1.0F,
            DESounds.BS_HOLLOW_METAL_BREAK.get(),
            DESounds.BS_HOLLOW_METAL_STEP.get(),
            DESounds.BS_HOLLOW_METAL_PLACE.get(),
            DESounds.BS_HOLLOW_METAL_HIT.get(),
            SoundEvents.METAL_FALL);
}
