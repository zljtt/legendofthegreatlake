package github.zljtt.legendofthegreatlake.entity;

import com.google.common.collect.ImmutableMap;
import github.zljtt.legendofthegreatlake.capabilities.IScheduledEventData;
import github.zljtt.legendofthegreatlake.capabilities.IVillagerSchedule;
import github.zljtt.legendofthegreatlake.capabilities.ScheduledEventDataProvider;
import github.zljtt.legendofthegreatlake.capabilities.VillagerScheduleProvider;
import github.zljtt.legendofthegreatlake.items.ScheduledEvent;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;

public class UpdateSchedule extends Behavior<Villager> {
    public UpdateSchedule() {
        super(ImmutableMap.of());
    }

    /**
     * day = 1000
     * noon = 6000
     * night = 13000
     * midnight = 18000
     */
    /**
     * sunrise = 23000 - 1000
     * morning = 1000 - 5500
     * noon = 5500 - 7500
     * afternoon = 7500 - 12000
     * evening = 12000 - 15000
     * night = 15000 - 23000
     */
    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, Villager villager) {
        if (villager.getCapability(VillagerScheduleProvider.VILLAGER_SCHEDULE_CAPABILITY).isPresent()) {
            IVillagerSchedule slots = villager.getCapability(VillagerScheduleProvider.VILLAGER_SCHEDULE_CAPABILITY).orElse(null);
            Brain<Villager> brain = villager.getBrain();
            float time = level.getTimeOfDay(0);
            ItemStack itemStack = ItemStack.EMPTY;

            if (time > 23000 || time <= 1000) {
                itemStack = slots.getStackInSlot(0);
            } else if (time > 1000 && time <= 5500) {
                itemStack = slots.getStackInSlot(1);
            } else if (time > 5500 && time <= 7500) {
                itemStack = slots.getStackInSlot(2);
            } else if (time > 7500 && time <= 12000) {
                itemStack = slots.getStackInSlot(3);
            } else if (time > 12000 && time <= 15000) {
                itemStack = slots.getStackInSlot(4);
            } else if (time > 15000) {
                itemStack = slots.getStackInSlot(5);
            }

            if (itemStack.getItem() instanceof ScheduledEvent eventItem && !villager.getBrain().isActive(eventItem.getEventType().getActivity())) {
            }
        }
        return false;
    }

    @Override
    protected void start(ServerLevel level, Villager villager, long p_22542_) {

        if (villager.getCapability(VillagerScheduleProvider.VILLAGER_SCHEDULE_CAPABILITY).isPresent()) {
            IVillagerSchedule slots = villager.getCapability(VillagerScheduleProvider.VILLAGER_SCHEDULE_CAPABILITY).orElse(null);
            Brain<Villager> brain = villager.getBrain();
            float time = level.getTimeOfDay(0);
            ItemStack itemStack = ItemStack.EMPTY;

            if (time > 23000 || time <= 1000) {
                itemStack = slots.getStackInSlot(0);
            } else if (time > 1000 && time <= 5500) {
                itemStack = slots.getStackInSlot(1);
            } else if (time > 5500 && time <= 7500) {
                itemStack = slots.getStackInSlot(2);
            } else if (time > 7500 && time <= 12000) {
                itemStack = slots.getStackInSlot(3);
            } else if (time > 12000 && time <= 15000) {
                itemStack = slots.getStackInSlot(4);
            } else if (time > 15000) {
                itemStack = slots.getStackInSlot(5);
            }

            if (itemStack.getItem() instanceof ScheduledEvent eventItem) {
                IScheduledEventData data = itemStack.getCapability(ScheduledEventDataProvider.SCHEDULED_EVENT_DATA_CAPABILITY).orElse(null);
                brain.eraseMemory(eventItem.getEventType().getMemory());
                villager.getBrain().setMemory(eventItem.getEventType().getMemory(), GlobalPos.of(villager.getLevel().dimension(), data.getEventPosition()));
            }
        }
    }
}
