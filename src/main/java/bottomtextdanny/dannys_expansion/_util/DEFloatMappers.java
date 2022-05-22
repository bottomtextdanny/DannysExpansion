package bottomtextdanny.dannys_expansion._util;

import bottomtextdanny.braincell.base.FloatRandomPicker;
import bottomtextdanny.braincell.base.value_mapper.FloatMapper;

public final class DEFloatMappers {

    public static FloatMapper range(float root, float amplitude, FloatRandomPicker picker) {
        return r -> {
            return picker.compute(root - amplitude, root + amplitude, r);
        };
    }

    private DEFloatMappers() {}
}
