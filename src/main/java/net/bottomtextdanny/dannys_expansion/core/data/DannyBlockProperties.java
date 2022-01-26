package net.bottomtextdanny.dannys_expansion.core.data;

import net.bottomtextdanny.danny_expannny.objects.block_properties.WorkbenchPart;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class DannyBlockProperties {
    public static final EnumProperty<WorkbenchPart> WORKBENCH_PART = EnumProperty.create("workbench_part", WorkbenchPart.class);
}
