package net.bottomtextdanny.danny_expannny.objects.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.bottomtextdanny.danny_expannny.object_tables.DEParticles;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Locale;

public class DannyDustParticleData implements ParticleOptions {
    public static final Codec<DannyDustParticleData> CODEC = RecordCodecBuilder.create(p_239803_0_ -> p_239803_0_.group(
            Codec.FLOAT.fieldOf("r").forGetter(p_239807_0_ -> p_239807_0_.red),
            Codec.FLOAT.fieldOf("g").forGetter(p_239806_0_ -> p_239806_0_.green),
            Codec.FLOAT.fieldOf("b").forGetter(p_239805_0_ -> p_239805_0_.blue),
            Codec.FLOAT.fieldOf("scale").forGetter(p_239804_0_ -> p_239804_0_.alpha),
            Codec.INT.fieldOf("time").forGetter(p_239804_0_ -> p_239804_0_.time)
            ).apply(p_239803_0_, DannyDustParticleData::new));
    public static final ParticleOptions.Deserializer<DannyDustParticleData> DESERIALIZER = new ParticleOptions.Deserializer<DannyDustParticleData>() {
        public DannyDustParticleData fromCommand(ParticleType<DannyDustParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float f = (float)reader.readDouble();
            reader.expect(' ');
            float f1 = (float)reader.readDouble();
            reader.expect(' ');
            float f2 = (float)reader.readDouble();
            reader.expect(' ');
            float f3 = (float)reader.readDouble();
            reader.expect(' ');
            int i = reader.readInt();
            return new DannyDustParticleData(f, f1, f2, f3, i);
        }

        public DannyDustParticleData fromNetwork(ParticleType<DannyDustParticleData> particleTypeIn, FriendlyByteBuf buffer) {
            return new DannyDustParticleData(buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readInt());
        }
    };

    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;
    private final int time;

    public DannyDustParticleData(float red, float green, float blue, float alpha, int time) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = Mth.clamp(alpha, 0.01F, 4.0F);
        this.time = time;
    }

    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeFloat(this.red);
        buffer.writeFloat(this.green);
        buffer.writeFloat(this.blue);
        buffer.writeFloat(this.alpha);
        buffer.writeFloat(this.time);
    }

    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f %d", Registry.PARTICLE_TYPE.getKey(this.getType()), this.red, this.green, this.blue, this.alpha, this.time);
    }

    public ParticleType<DannyDustParticleData> getType() {
        return DEParticles.DANNY_DUST.get();
    }

    @OnlyIn(Dist.CLIENT)
    public float getRed() {
        return this.red;
    }

    @OnlyIn(Dist.CLIENT)
    public float getGreen() {
        return this.green;
    }

    @OnlyIn(Dist.CLIENT)
    public float getBlue() {
        return this.blue;
    }

    @OnlyIn(Dist.CLIENT)
    public float getAlpha() {
        return this.alpha;
    }

    @OnlyIn(Dist.CLIENT)
    public int getTime() {
        return this.time;
    }
}
