package github.zljtt.legendofthegreatlake;

import github.zljtt.legendofthegreatlake.entity.EntityRegistry;
import github.zljtt.legendofthegreatlake.entity.render.CustomNPCRenderer;
import github.zljtt.legendofthegreatlake.gui.MenuRegistry;
import github.zljtt.legendofthegreatlake.gui.VillagerDialogScreen;
import github.zljtt.legendofthegreatlake.gui.VillagerScheduleScreen;
import github.zljtt.legendofthegreatlake.npc.ClientNPCManager;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = LegendOfTheGreatLake.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(MenuRegistry.VILLAGER_SCHEDULE.get(), VillagerScheduleScreen::new);
            MenuScreens.register(MenuRegistry.VILLAGER_DIALOG.get(), VillagerDialogScreen::new);
        });
    }

    @SubscribeEvent
    public static void registerEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.CUSTOM_NPC.get(), CustomNPCRenderer::new);
    }

    @SubscribeEvent
    public static void reloadResourcePack(RegisterClientReloadListenersEvent event) {
        LegendOfTheGreatLake.LOGGER.info("Register client pack loader");
        event.registerReloadListener(new ClientNPCManager.DialogLoader());
    }
}
