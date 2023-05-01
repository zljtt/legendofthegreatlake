package github.zljtt.legendofthegreatlake;

import github.zljtt.legendofthegreatlake.entity.EntityRegistry;
import github.zljtt.legendofthegreatlake.entity.render.CustomNPCRenderer;
import github.zljtt.legendofthegreatlake.gui.ContainerRegistry;
import github.zljtt.legendofthegreatlake.gui.VillagerScheduleScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = LegendOfTheGreatLake.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(ContainerRegistry.VILLAGER_SCHEDULE.get(), VillagerScheduleScreen::new));
    }

    @SubscribeEvent
    public static void registerEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.CUSTOM_NPC.get(), CustomNPCRenderer::new);
    }
}
