package bottomtextdanny.dannys_expansion.content._client;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._base.ambiance.AmbianceManager;
import com.mojang.math.Vector3f;
import bottomtextdanny.braincell.base.BCLerp;
import bottomtextdanny.braincell.mod.graphics.screen_tonemapping.TonemapAgent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BiomeTonemapperAgent extends TonemapAgent {
    public static final float TRANSITION_STEP = 0.018F;
    private float desaturatorGoal;
    private final Vector3f channelMultiplierGoal = new Vector3f();

    public BiomeTonemapperAgent() {
        super();
    }

    @Override
    public void tick() {}

    @Override
    public void render(float partialTick) {
        fixedMudules(Minecraft.getInstance().player);
        setSaturationModifier(BCLerp.get(TRANSITION_STEP, getSaturationModifier(), this.desaturatorGoal));
        setChannelModifier(BCLerp.get(TRANSITION_STEP, getChannelModifier(), this.channelMultiplierGoal));
        this.saturationModifier = 0.0F;
        this.channelMultiplierGoal.set(0.0F, 0.0F, 0.0F);
    }

    private void fixedMudules(LocalPlayer player) {
        AmbianceManager manager = DannysExpansion.client().getAmbianceManager();

        if (manager.getAmbientAccessors() != null) {
            this.channelMultiplierGoal.add(manager.getCurrentAmbiance().tonemapping());
        }

        Holder<Biome> biome = player.level.getBiome(player.blockPosition());
        Biome.BiomeCategory cat = Biome.getBiomeCategory(biome);

        if (cat == Biome.BiomeCategory.DESERT) {
            this.channelMultiplierGoal.add(0.03F, 0.02F, -0.1F);
        }

        if (cat == Biome.BiomeCategory.SWAMP) {
            this.desaturatorGoal-=0.05F;
            this.channelMultiplierGoal.add(-0.05F, 0.01F, 0.005F);
        }

        if (cat == Biome.BiomeCategory.NETHER) {
            this.channelMultiplierGoal.add(0.1F, -0.4F, -0.6F);
        }

        if (cat == Biome.BiomeCategory.THEEND) {
            this.channelMultiplierGoal.add(0.6F, 0.02F, -0.0F);
            // float variantFactor = -0.045F * MathUtil.sin((player.level.getGameTime() + DEUtil.PARTIAL_TICK) * 0.5F + Math.PI) - 0.045F;
        }

        if (biome.value().coldEnoughToSnow(player.blockPosition())) {
            this.channelMultiplierGoal.add(-0.1F, 0.08F, 0.3F);
            this.desaturatorGoal += 0.3F;
        }

        if (cat == Biome.BiomeCategory.JUNGLE) {
            this.channelMultiplierGoal.add(-0.1F, 0.25F, 0.2F);
        }
    }

    @Override
    public boolean removeIf() {
        return false;
    }
}
