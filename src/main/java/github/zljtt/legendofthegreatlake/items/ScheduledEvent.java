package github.zljtt.legendofthegreatlake.items;

import github.zljtt.legendofthegreatlake.LegendOfTheGreatLake;
import github.zljtt.legendofthegreatlake.capabilities.ScheduledEventDataProvider;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

public class ScheduledEvent extends Item {
    private final EventType eventType;

    public ScheduledEvent(EventType type, Properties properties) {
        super(properties);
        eventType = type;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        LegendOfTheGreatLake.LOGGER.debug("on use");
        context.getItemInHand().getCapability(ScheduledEventDataProvider.SCHEDULED_EVENT_DATA_CAPABILITY).ifPresent(data -> {
            data.setEventPosition(context.getClickedPos());
            context.getItemInHand().setHoverName(new TextComponent(eventType.toString() + " - " +
                    context.getClickedPos().getX() + " " +
                    context.getClickedPos().getY() + " " +
                    context.getClickedPos().getZ()));
        });
        return InteractionResult.FAIL;
    }

    public EventType getEventType() {
        return eventType;
    }

    public enum EventType {
        WORK(Activity.WORK, MemoryModuleType.JOB_SITE),
        IDLE(Activity.IDLE, MemoryModuleType.POTENTIAL_JOB_SITE),
        REST(Activity.REST, MemoryModuleType.HOME),
        MEET(Activity.MEET, MemoryModuleType.MEETING_POINT);


        final Activity activity;
        final MemoryModuleType<GlobalPos> memory;

        EventType(Activity activity, MemoryModuleType<GlobalPos> memory) {
            this.activity = activity;
            this.memory = memory;

        }

        public Activity getActivity() {
            return activity;
        }

        public MemoryModuleType<GlobalPos> getMemory() {
            return memory;
        }
    }
}
