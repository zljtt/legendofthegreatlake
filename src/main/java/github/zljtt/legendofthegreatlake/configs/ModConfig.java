package github.zljtt.legendofthegreatlake.configs;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;


public class ModConfig {
    public static final ClientConfig CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;

    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
    }


    public static void bakeConfig() {
    }

    public static class ClientConfig {

        public ClientConfig(ForgeConfigSpec.Builder builder) {
        }

    }

}