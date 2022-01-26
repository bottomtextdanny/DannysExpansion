package net.bottomtextdanny.braincell.mod.serialization.serializers.braincell;

import net.bottomtextdanny.braincell.mod.serialization.serializers.braincell.entity.VariableIntSchedulerSerializer;

public final class BCSerializers {
    //*\\*//*\\*//*\\SIMPLE SERIALIZERS//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
    public static final IntSchedulerSerializer INT_SCHEDULER = new IntSchedulerSerializer();
    //*\\*//*\\*//*\\SIMPLE SERIALIZERS//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//

    //*\\*//*\\*//*\\WORLD DATA SERIALIZERS\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
    //none :p
    //*\\*//*\\*//*\\WORLD DATA SERIALIZERS\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//

    //*\\*//*\\*//*\\ENTITY DATA SERIALIZERS*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
    public static final VariableIntSchedulerSerializer VARIABLE_INT_SCHEDULER = new VariableIntSchedulerSerializer();
    //*\\*//*\\*//*\\ENTITY DATA SERIALIZERS*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
}
