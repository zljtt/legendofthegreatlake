package github.zljtt.legendofthegreatlake.entity;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import github.zljtt.legendofthegreatlake.LegendOfTheGreatLake;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.level.Level;

public class CustomNPC extends Villager {
    public CustomNPC(EntityType<? extends Villager> type, Level level) {
        super(type, level);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> p_35445_) {
        Brain<Villager> brain = this.brainProvider().makeBrain(p_35445_);
        this.registerBrainGoals(brain);
        return brain;
    }

    @Override
    public void refreshBrain(ServerLevel p_35484_) {
        Brain<Villager> brain = this.getBrain();
        brain.stopAll(p_35484_, this);
        this.brain = brain.copyWithoutBehaviors();
        this.registerBrainGoals(this.getBrain());
    }

    public void registerBrainGoals(Brain<Villager> brain) {
        brain.removeAllBehaviors();
        VillagerProfession villagerprofession = this.getVillagerData().getProfession();
        if (this.isBaby()) {
            brain.setSchedule(Schedule.VILLAGER_BABY);
            brain.addActivity(Activity.PLAY, VillagerModifiedAI.getPlayPackage(0.5F));
        } else {
            brain.setSchedule(Schedule.VILLAGER_DEFAULT);
            brain.addActivityWithConditions(Activity.WORK, VillagerModifiedAI.getWorkPackage(villagerprofession, 0.5F), ImmutableSet.of(Pair.of(MemoryModuleType.JOB_SITE, MemoryStatus.VALUE_PRESENT)));

        }

        brain.addActivity(Activity.CORE, VillagerModifiedAI.getCorePackage(0.5F));
        brain.addActivityWithConditions(Activity.MEET, VillagerModifiedAI.getMeetPackage(0.5F), ImmutableSet.of(Pair.of(MemoryModuleType.MEETING_POINT, MemoryStatus.VALUE_PRESENT)));
        brain.addActivity(Activity.REST, VillagerModifiedAI.getRestPackage(0.5F));
        brain.addActivity(Activity.IDLE, VillagerModifiedAI.getIdlePackage(0.5F));
        brain.addActivity(Activity.PANIC, VillagerModifiedAI.getPanicPackage(0.5F));
        brain.addActivity(Activity.PRE_RAID, VillagerModifiedAI.getPreRaidPackage(0.5F));
        brain.addActivity(Activity.RAID, VillagerModifiedAI.getRaidPackage(0.5F));
        brain.addActivity(Activity.HIDE, VillagerModifiedAI.getHidePackage(0.5F));
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.setActiveActivityIfPossible(Activity.IDLE);
        brain.updateActivityFromSchedule(this.level.getDayTime(), this.level.getGameTime());
        LegendOfTheGreatLake.LOGGER.debug("Custom npc AI generated");
    }
}
