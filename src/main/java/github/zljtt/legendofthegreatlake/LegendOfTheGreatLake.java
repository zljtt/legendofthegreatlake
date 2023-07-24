package github.zljtt.legendofthegreatlake;

import com.mojang.logging.LogUtils;
import github.zljtt.legendofthegreatlake.blocks.BlockRegistry;
import github.zljtt.legendofthegreatlake.configs.ModConfig;
import github.zljtt.legendofthegreatlake.entity.CustomNPC;
import github.zljtt.legendofthegreatlake.entity.EntityRegistry;
import github.zljtt.legendofthegreatlake.gui.MenuRegistry;
import github.zljtt.legendofthegreatlake.items.ItemRegistry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.resource.PathResourcePack;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static github.zljtt.legendofthegreatlake.configs.ModConfig.bakeConfig;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("legendofthegreatlake")
public class LegendOfTheGreatLake {

    public static final String MODID = "legendofthegreatlake";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static String PACK_NAME = "resourcepack";
    public static Path PACK_REPO = FMLPaths.GAMEDIR.get().resolve(LegendOfTheGreatLake.MODID);


    public LegendOfTheGreatLake() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(EventHandler.class);
        ItemRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());

        MenuRegistry.MENUS.register(FMLJavaModLoadingContext.get().getModEventBus());
        EntityRegistry.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());

    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("COMMON SETUP starting");
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // Some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        // Some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.messageSupplier().get()).
                collect(Collectors.toList()));
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("Server Starting...");

    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void addEntityAttributes(final EntityAttributeCreationEvent event) {
            LegendOfTheGreatLake.LOGGER.info("Register attributes");
            event.put(EntityRegistry.CUSTOM_NPC.get(), CustomNPC.setAttributes());
        }

        @SubscribeEvent
        public void onModConfigEvent(ModConfigEvent configEvent) {
            LegendOfTheGreatLake.LOGGER.debug("Load config events");
            if (configEvent.getConfig().getSpec() == ModConfig.CLIENT_SPEC) {
                bakeConfig();
            }
        }

        @SubscribeEvent
        public static void addPacks(AddPackFindersEvent event) {
            if (event.getPackType() == PackType.CLIENT_RESOURCES) {
                LegendOfTheGreatLake.LOGGER.info("Adding client resource pack finder");
                try {
                    PathResourcePack resourcePack = new PathResourcePack(MODID + "client", PACK_REPO.resolve(PACK_NAME));
                    var metadataSection = resourcePack.getMetadataSection(PackMetadataSection.SERIALIZER);
                    if (metadataSection != null) {
                        event.addRepositorySource((packConsumer, packConstructor) ->
                                packConsumer.accept(packConstructor.create(
                                        MODID + "client", new TranslatableComponent("pack.legendofthegreatlake.client"), false,
                                        () -> resourcePack, metadataSection, Pack.Position.BOTTOM, PackSource.BUILT_IN, false)));
                    }
                } catch (IOException e) {
                    LegendOfTheGreatLake.LOGGER.error("Error loading resource pack for legendofthegreatlake");
                }
            }
            if (event.getPackType() == PackType.SERVER_DATA) {
                LegendOfTheGreatLake.LOGGER.info("Adding server data pack finder");
                try {
                    PathResourcePack dataPack = new PathResourcePack(MODID + "server", PACK_REPO.resolve(PACK_NAME));
                    var metadataSection = dataPack.getMetadataSection(PackMetadataSection.SERIALIZER);
                    if (metadataSection != null) {
                        event.addRepositorySource((packConsumer, packConstructor) ->
                                packConsumer.accept(packConstructor.create(
                                        MODID + "server", new TranslatableComponent("pack.legendofthegreatlake.server"), false,
                                        () -> dataPack, metadataSection, Pack.Position.TOP, PackSource.SERVER, false)));
                    }
                } catch (IOException e) {
                    LegendOfTheGreatLake.LOGGER.error("Error loading data pack for legendofthegreatlake");
                }
            }

        }
    }
}
