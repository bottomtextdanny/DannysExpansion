package net.bottomtextdanny.braincell.mod.opengl_front.screen_tonemapping;

import com.google.common.collect.Lists;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import net.bottomtextdanny.braincell.mod.opengl_helpers.ShaderWorkflow;
import net.bottomtextdanny.braincell.mod.structure.BCStaticData;
import net.bottomtextdanny.braincell.underlying.util.BCLerp;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.biome.Biome;

import java.util.List;

public class TonemapWorkflow extends ShaderWorkflow {
    public static final float TRANSITION_STEP = 0.018F;
    private final TonemapperPixelProgram tonemapperProgram = new TonemapperPixelProgram(this);
    private final List<TonemapAgent> agents = Lists.newLinkedList();
    private float desaturatorGoal;
    private float desaturationStep;
    private float outDesaturation;
    private Vector3f channelMultiplierGoal = new Vector3f();
    private Vector3f channelMultiplierStep = new Vector3f();
    private Vector3f outChannelMultiplier = new Vector3f();

    @Override
    protected void execute() {
        LocalPlayer player = MC.player;
        fixedMudules(player);
        Vector4f agentOutput = getAgentOutput();
        this.desaturationStep = BCLerp.get(TRANSITION_STEP, this.desaturationStep, this.desaturatorGoal);
        this.channelMultiplierStep = BCLerp.get(TRANSITION_STEP, this.channelMultiplierStep, this.channelMultiplierGoal);
        this.outChannelMultiplier = new Vector3f(this.channelMultiplierStep.x() + agentOutput.x(), this.channelMultiplierStep.y() + agentOutput.y(), this.channelMultiplierStep.z() + agentOutput.z());
        this.outDesaturation = this.desaturationStep + agentOutput.w();
        this.tonemapperProgram.flow();
        this.channelMultiplierGoal = new Vector3f();
        this.desaturatorGoal = 0.0F;
    }

    @Override
    protected void tick() {
        if (!this.agents.isEmpty()) {
            this.agents.removeIf(t -> {
                t.baseTick();
                return t.removeIf();
            });
        }
    }

    private void fixedMudules(LocalPlayer player) {
        if (DannysExpansion.clientManager().ambianceManager.getAmbientAccessors() != null) {
            this.channelMultiplierGoal.add(DannysExpansion.clientManager().ambianceManager.getCurrentAmbiance().tonemapping());
        }

        if (player.level.getBiome(player.blockPosition()).getBiomeCategory() == Biome.BiomeCategory.DESERT) {
            this.channelMultiplierGoal.add(0.3F, 0.02F, -0.2F);
        }

        if (player.level.getBiome(player.blockPosition()).getBiomeCategory() == Biome.BiomeCategory.NETHER) {
            this.channelMultiplierGoal.add(0.1F, -0.4F, -0.6F);
        }

        if (player.level.getBiome(player.blockPosition()).getBiomeCategory() == Biome.BiomeCategory.THEEND) {
            this.channelMultiplierGoal.add(0.6F, 0.02F, -0.0F);
           // float variantFactor = -0.045F * MathUtil.sin((player.level.getGameTime() + DEUtil.PARTIAL_TICK) * 0.5F + Math.PI) - 0.045F;
        }

        if (player.level.getBiome(player.blockPosition()).coldEnoughToSnow(player.blockPosition())) {
            this.channelMultiplierGoal.add(-0.1F, 0.08F, 0.3F);
            this.desaturatorGoal += 0.3F;
        }

        if (player.level.getBiome(player.blockPosition()).getBiomeCategory() == Biome.BiomeCategory.JUNGLE) {
            this.channelMultiplierGoal.add(-0.1F, 0.25F, 0.2F);
        }
    }

    private Vector4f getAgentOutput() {
        Vector4f output = new Vector4f();

        for (TonemapAgent agent : this.agents) {
            agent.render(BCStaticData.partialTick());
            output.add(agent.getChannelModifier().x(), agent.getChannelModifier().y(), agent.getChannelModifier().z(), agent.getSaturationModifier());
        }

        return output;
    }

    public float getOutDesaturation() {
        return this.outDesaturation;
    }

    public Vector3f getOutChannelMultiplier() {
        return this.outChannelMultiplier;
    }

    public boolean addAgent(TonemapAgent agent) {
        return this.agents.add(agent);
    }

    @Override
    protected boolean shouldApply() {
        return MC.player != null;
    }
}
