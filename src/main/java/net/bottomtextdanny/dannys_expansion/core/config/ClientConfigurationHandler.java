package net.bottomtextdanny.dannys_expansion.core.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfigurationHandler {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static Config CONFIG = new Config(BUILDER);

    public static class Config {

        public final ForgeConfigSpec.BooleanValue wip_arrow_rendering;
        public final ForgeConfigSpec.DoubleValue wip_arrow_rendering_scale;

        Config(ForgeConfigSpec.Builder builder) {

            builder.push("General");
            builder.comment("Changes arrow rendering to a WIP one.");
            this.wip_arrow_rendering = builder.define("wip_custom_arrow_rendering", true);
            this.wip_arrow_rendering_scale = builder.defineInRange("wip_custom_arrow_rendering_scale", 0.8, 0, 2);
            builder.pop();
        }
    }


    public static final ForgeConfigSpec forgeConfigSpec = BUILDER.build();
}
