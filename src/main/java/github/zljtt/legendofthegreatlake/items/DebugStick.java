package github.zljtt.legendofthegreatlake.items;


import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class DebugStick extends Item {

    private static final float STROLL_SPEED_MODIFIER = 0.4F;
    private static GlobalPos workPos;
    private static GlobalPos homePos;
    private static GlobalPos meetPos;
    private static int choose = 0;

    public DebugStick(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull Component getDescription() {
        return new TextComponent("work " + workPos.toString() + " \n home " + homePos.toString() + " \n meet " + meetPos.toString());
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (entity instanceof Villager villager) {
            if (!player.level.isClientSide) {
                int time = (int) (player.level.getDayTime() % 24000);
                Activity scheduledActivity = villager.getBrain().getSchedule().getActivityAt(time);
                Set<Activity> actualActivities = villager.getBrain().getActiveActivities();
                List<Behavior<? super Villager>> runningBehaviors = villager.getBrain().getRunningBehaviors();
                player.sendMessage(new TextComponent("Activity Scheduled: " + scheduledActivity), player.getUUID());
                player.sendMessage(new TextComponent("Current Activities: " + actualActivities), player.getUUID());
                player.sendMessage(new TextComponent("Current Behaviours: " + runningBehaviors), player.getUUID());
            }
            return true;
        }
        return super.onLeftClickEntity(stack, player, entity);
    }
}
