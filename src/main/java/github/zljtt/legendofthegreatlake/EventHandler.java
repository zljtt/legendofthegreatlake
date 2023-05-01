package github.zljtt.legendofthegreatlake;

import github.zljtt.legendofthegreatlake.capabilities.*;
import github.zljtt.legendofthegreatlake.entity.VillagerModifiedAI;
import github.zljtt.legendofthegreatlake.gui.VillagerScheduleContainer;
import github.zljtt.legendofthegreatlake.items.ItemRegistry;
import github.zljtt.legendofthegreatlake.items.ScheduledEvent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.NetworkHooks;

public class EventHandler {
    @SubscribeEvent
    public static void openVillagerScheduler(PlayerInteractEvent.EntityInteract event) {
        if (!event.getWorld().isClientSide && event.getTarget() instanceof Villager villager && event.getPlayer().getItemInHand(event.getHand()).getItem() == ItemRegistry.DEBUG_STICK.get()) {
            villager.getCapability(VillagerScheduleProvider.VILLAGER_SCHEDULE_CAPABILITY).ifPresent((slots) -> {
                MenuProvider container = new SimpleMenuProvider(VillagerScheduleContainer.getServerContainer(villager, slots), new TextComponent("Scheduler"));
                NetworkHooks.openGui((ServerPlayer) event.getPlayer(), container, event.getPos());
                event.setResult(Event.Result.DENY);
            });
        }
    }

    @SubscribeEvent
    public void registerCaps(RegisterCapabilitiesEvent event) {
        LegendOfTheGreatLake.LOGGER.debug("Registering capabilities");
        event.register(IScheduledEventData.class);
        event.register(IWorldCalendar.class);
    }

    @SubscribeEvent
    public static void addVillagerSchedule(AttachCapabilitiesEvent<Entity> event) {
        if (!event.getObject().level.isClientSide && event.getObject() instanceof Villager villager) {
            event.addCapability(new ResourceLocation(LegendOfTheGreatLake.MODID, "villager_schedule"), new VillagerScheduleProvider());
        }
    }

    @SubscribeEvent
    public static void addScheduledEventItems(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() instanceof ScheduledEvent) {
            event.addCapability(new ResourceLocation(LegendOfTheGreatLake.MODID, "scheduled_event"), new ScheduledEventDataProvider());
        }
    }

    @SubscribeEvent
    public static void addCalendar(AttachCapabilitiesEvent<Level> event) {
        if (!event.getObject().isClientSide) {
            event.addCapability(new ResourceLocation(LegendOfTheGreatLake.MODID, "world_calendar"), new WorldCalendarProvider());
            LegendOfTheGreatLake.LOGGER.debug("Calendar attached to the level");

        }
    }

    @SubscribeEvent
    public static void transformVillager(EntityJoinWorldEvent event) {
        if (!event.getEntity().level.isClientSide && event.getEntity() instanceof Villager villager) {
            VillagerModifiedAI.transformVillager(villager);

        }
    }

    @SubscribeEvent
    public static void updateVillager(LivingEvent.LivingUpdateEvent event) {
        if (!event.getEntityLiving().level.isClientSide && event.getEntityLiving() instanceof Villager villager) {
            VillagerModifiedAI.updateVillagerSchedule(villager);


        }
    }


    @SubscribeEvent
    public static void updateCalendar(TickEvent.WorldTickEvent event) {
        if (!event.side.isClient()) {
            int time = (int) event.world.getDayTime() % 24000;

            LazyOptional<IWorldCalendar> optional = event.world.getCapability(WorldCalendarProvider.WORLD_CALENDAR_CAPABILITY);
            if (optional.isPresent()) {

                VillagerSchedule.TimeSlot prev = optional.orElse(null).getCurrentTimeSlot();
                VillagerSchedule.TimeSlot slot = VillagerSchedule.TimeSlot.getFromTime(time);

                if (prev != slot) {
                    for (Player player : event.world.players()) {
                        player.sendMessage(new TextComponent("Time is now " + slot.toString()), player.getUUID());
                        LegendOfTheGreatLake.LOGGER.debug("Time is now " + slot.toString());
                    }
                }
                optional.orElse(null).setCurrentTimeSlot(slot);
            }
        }
    }

    @SubscribeEvent
    public static void debugStickTooltip(ItemTooltipEvent event) {
    }

}
