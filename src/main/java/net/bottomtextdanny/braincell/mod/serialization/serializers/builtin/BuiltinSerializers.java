package net.bottomtextdanny.braincell.mod.serialization.serializers.builtin;

import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.world.EntityReferenceSerializer;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.world.EntitySerializer;
import net.minecraft.resources.ResourceLocation;

public final class BuiltinSerializers {
    //*\\*//*\\*//*\\SIMPLE SERIALIZERS*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
    public static final BooleanSerializer BOOLEAN = new BooleanSerializer();
    public static final ByteSerializer BYTE = new ByteSerializer();
    public static final ShortSerializer SHORT = new ShortSerializer();
    public static final IntegerSerializer INTEGER = new IntegerSerializer();
    public static final FloatSerializer FLOAT = new FloatSerializer();
    public static final LongSerializer LONG = new LongSerializer();
    public static final DoubleSerializer DOUBLE = new DoubleSerializer();
    public static final StringSerializer STRING = new StringSerializer();
    public static final DirectionSerializer DIRECTION = new DirectionSerializer();
    public static final UUIDSerializer UUID = new UUIDSerializer();
    public static final Vec2Serializer VEC2 = new Vec2Serializer();
    public static final Vec3Serializer VEC3 = new Vec3Serializer();
    public static final BlockPosSerializer BLOCK_POS = new BlockPosSerializer();
    public static final ItemStackSerializer ITEM_STACK = new ItemStackSerializer();
    public static final ItemSerializer ITEM = new ItemSerializer();
    public static final ResourceLocationSerializer RESOURCE_LOCATION = new ResourceLocationSerializer();
    public static final ParticleOptionsSerializer PARTICLE_OPTIONS = new ParticleOptionsSerializer();
    //*\\*//*\\*//*\\SIMPLE SERIALIZERS*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//

    //*\\*//*\\*//*\\WORLD DATA SERIALIZERS\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
    public static final EntitySerializer ENTITY = new EntitySerializer();
    public static final EntityReferenceSerializer ENTITY_REFERENCE = new EntityReferenceSerializer();
    //*\\*//*\\*//*\\WORLD DATA SERIALIZERS\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
}
