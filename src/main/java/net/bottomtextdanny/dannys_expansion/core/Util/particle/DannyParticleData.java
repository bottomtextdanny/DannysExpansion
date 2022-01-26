package net.bottomtextdanny.dannys_expansion.core.Util.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class DannyParticleData implements ParticleOptions {

    public static final ParticleOptions.Deserializer<DannyParticleData> DESERIALIZER = new ParticleOptions.Deserializer<DannyParticleData>() {
        public DannyParticleData fromCommand(ParticleType<DannyParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            return ((DannyParticleType)particleTypeIn).getData();
        }

        public DannyParticleData fromNetwork(ParticleType<DannyParticleData> particleTypeIn, FriendlyByteBuf buffer) {
            return ((DannyParticleType)particleTypeIn).getData();
        }
    };
    private final Codec<DannyParticleData> codec = Codec.unit(this);

    private final List<Object> data;
    private DannyParticleType particleType;

    public DannyParticleData(DannyParticleType type, Object... parameters) {
        this.data = Arrays.asList(parameters);
        this.particleType = type;
    }

    public DannyParticleData(DannyParticleType type) {
        this.data = null;
        this.particleType = type;
    }

    public DannyParticleData(Supplier<DannyParticleType> type, Object... parameters) {
        this.data = Arrays.asList(parameters);
        this.particleType = type.get();
    }

    public DannyParticleData(Supplier<DannyParticleType> type) {
        this.data = null;
        this.particleType = type.get();
    }

    public DannyParticleData(Object... parameters) {
        this.data = Arrays.asList(parameters);
    }

    public void setParticleType(DannyParticleType particleType) {
        this.particleType = particleType;
    }

    @Override
    public ParticleType<?> getType() {
        return this.particleType;
    }

    public List<Object> getData() {
        return this.data;
    }

    public Codec<DannyParticleData> getCodec() {
        return this.codec;
    }

    public Object getParam(int index) {
        return this.data.get(index);
    }

    @SuppressWarnings("unchecked")
    public <T> T fetch(int index, Class<T> cast) {
        return (T) this.data.get(index);
    }

    @SuppressWarnings("unchecked")
    public <T> T fetch(int index) {
        return (T) this.data.get(index);
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
    }

    @Override
    public String writeToString() {
        return null;
    }
}
