package com.example.aimbot;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfig {
    public static final ForgeConfigSpec SPEC;
    public static final Config INSTANCE;

    static {
        final Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }

    public static class Config {
        public final ForgeConfigSpec.DoubleValue maxRange;
        public final ForgeConfigSpec.DoubleValue smoothing;

        Config(ForgeConfigSpec.Builder builder) {
            builder.comment("AimBot Client Settings").push("aimbot");
            maxRange = builder.comment("Maximum range in blocks").defineInRange("maxRange", 50.0, 1.0, 100.0);
            smoothing = builder.comment("Smoothing (0=instant)").defineInRange("smoothing", 0.0, 0.0, 1.0);
            builder.pop();
        }
    }
}
