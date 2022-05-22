package bottomtextdanny.dannys_expansion._base.particle;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

public class DannyParticleType extends ParticleType<DannyParticleData> {
    protected final DannyParticleData data;

    public DannyParticleType(boolean alwaysShow, DannyParticleData data) {
        super(alwaysShow, DannyParticleData.DESERIALIZER);
        this.data = data;
        data.setParticleType(this);
    }

    public DannyParticleData getData() {
        return this.data;
    }

    public Object getParam(int index) {
        return this.data.getParam(index);
    }

    public void write(FriendlyByteBuf buffer) {
    }

    public String getParameters() {
        return Registry.PARTICLE_TYPE.getKey(this).toString();
    }

    @Override
    public Codec<DannyParticleData> codec() {
        return this.data.getCodec();
    }
}
