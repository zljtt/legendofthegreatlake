package github.zljtt.legendofthegreatlake.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import github.zljtt.legendofthegreatlake.LegendOfTheGreatLake;
import github.zljtt.legendofthegreatlake.capabilities.ScheduledEventDataProvider;
import github.zljtt.legendofthegreatlake.capabilities.VillagerSchedule;
import github.zljtt.legendofthegreatlake.capabilities.VillagerScheduleProvider;
import github.zljtt.legendofthegreatlake.capabilities.WorldCalendarProvider;
import github.zljtt.legendofthegreatlake.items.ScheduledEvent;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.entity.schedule.ScheduleBuilder;
import net.minecraft.world.item.ItemStack;

public class VillagerModifiedAI {
    public static float RUN_MODIFIER = 1.2f;

    public static void updateVillagerSchedule(Villager villager) {
        villager.getCapability(VillagerScheduleProvider.VILLAGER_SCHEDULE_CAPABILITY).ifPresent(slots -> {
            villager.getLevel().getCapability(WorldCalendarProvider.WORLD_CALENDAR_CAPABILITY).ifPresent(calendar -> {
                ItemStack stack = slots.getStackInSlot(calendar.getCurrentTimeSlot().getIndex());
                if (stack.getItem() instanceof ScheduledEvent eventItem) {
                    stack.getCapability(ScheduledEventDataProvider.SCHEDULED_EVENT_DATA_CAPABILITY).ifPresent(data -> {
                        villager.getBrain().eraseMemory(eventItem.getEventType().getMemory());
                        villager.getBrain().setMemory(eventItem.getEventType().getMemory(), GlobalPos.of(villager.getLevel().dimension(), data.getEventPosition()));

                    });
                }
            });
            Schedule schedule = new Schedule();
            ScheduleBuilder builder = new ScheduleBuilder(schedule);
            for (int i = 0; i < VillagerSchedule.TimeSlot.values().length; i++) {
                ItemStack stack = slots.getStackInSlot(i);
                if (stack.getItem() instanceof ScheduledEvent eventItem) {
                    builder.changeActivityAt(VillagerSchedule.TimeSlot.values()[i].getStartTime(), eventItem.getEventType().getActivity());
                } else {
                    builder.changeActivityAt(VillagerSchedule.TimeSlot.values()[i].getStartTime(), Activity.IDLE);
                }
            }
            builder.build();
            villager.getBrain().setSchedule(schedule);
        });
    }


    public static void transformVillager(Villager villager) {
        Brain<Villager> brain = villager.getBrain();
        brain.removeAllBehaviors();
        VillagerProfession villagerprofession = villager.getVillagerData().getProfession();
        if (villager.isBaby()) {
            brain.setSchedule(Schedule.VILLAGER_BABY);
            brain.addActivity(Activity.PLAY, getPlayPackage(0.5F));
        } else {
            brain.setSchedule(Schedule.VILLAGER_DEFAULT);
            brain.addActivityWithConditions(Activity.WORK, getWorkPackage(villagerprofession, 0.5F), ImmutableSet.of(Pair.of(MemoryModuleType.JOB_SITE, MemoryStatus.VALUE_PRESENT)));

        }

        brain.addActivity(Activity.CORE, getCorePackage(0.5F));
        brain.addActivityWithConditions(Activity.MEET, getMeetPackage(0.5F), ImmutableSet.of(Pair.of(MemoryModuleType.MEETING_POINT, MemoryStatus.VALUE_PRESENT)));
        brain.addActivity(Activity.REST, getRestPackage(0.5F));
        brain.addActivity(Activity.IDLE, getIdlePackage(0.5F));
        brain.addActivity(Activity.PANIC, getPanicPackage(0.5F));
        brain.addActivity(Activity.PRE_RAID, getPreRaidPackage(0.5F));
        brain.addActivity(Activity.RAID, getRaidPackage(0.5F));
        brain.addActivity(Activity.HIDE, getHidePackage(0.5F));
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.setActiveActivityIfPossible(Activity.IDLE);
        brain.updateActivityFromSchedule(villager.level.getDayTime(), villager.level.getGameTime());
        LegendOfTheGreatLake.LOGGER.debug("Villager AI updated");
    }


    public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> getCorePackage(float speedModifier) {
        return ImmutableList.of(
                Pair.of(0, new Swim(0.8F)),
                Pair.of(0, new InteractWithModdedDoors()),
                Pair.of(0, new LookAtTargetSink(45, 90)),
                Pair.of(0, new VillagerPanicTrigger()),
                Pair.of(0, new WakeUp()),
                Pair.of(0, new ReactToBell()),
                Pair.of(0, new SetRaidStatus()),
                //Pair.of(0, new UpdateSchedule()),
                //Pair.of(0, new ValidateNearbyPoi(p_24586_.getJobPoiType(), MemoryModuleType.JOB_SITE)),
                //Pair.of(0, new ValidateNearbyPoi(p_24586_.getJobPoiType(), MemoryModuleType.POTENTIAL_JOB_SITE)),
                Pair.of(1, new MoveToTargetSink()),
                //Pair.of(2, new PoiCompetitorScan(p_24586_)),
                Pair.of(3, new LookAndFollowTradingPlayerSink(speedModifier)),
                Pair.of(5, new GoToWantedItem(speedModifier, false, 4)),
                //Pair.of(6, new AcquirePoi(p_24586_.getJobPoiType(), MemoryModuleType.JOB_SITE, MemoryModuleType.POTENTIAL_JOB_SITE, true, Optional.empty())),
                Pair.of(7, new GoToPotentialJobSite(speedModifier))
                //Pair.of(8, new YieldJobSite(speedModifier)),
                //Pair.of(10, new AcquirePoi(PoiType.HOME, MemoryModuleType.HOME, false, Optional.of((byte) 14))),
                //Pair.of(10, new AcquirePoi(PoiType.MEETING, MemoryModuleType.MEETING_POINT, true, Optional.of((byte) 14))),
                //Pair.of(10, new AssignProfessionFromJobSite()),
                //Pair.of(10, new ResetProfession()));
        );
    }

    public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> getWorkPackage(VillagerProfession profession, float speedModifier) {
        WorkAtPoi workatpoi;
        if (profession == VillagerProfession.FARMER) {
            workatpoi = new WorkAtComposter();
        } else {
            workatpoi = new WorkAtPoi();
        }

        return ImmutableList.of(
                getMinimalLookBehavior(),
                Pair.of(5, new RunOne<>(ImmutableList.of(Pair.of(workatpoi, 7),
                        Pair.of(new StrollAroundPoi(MemoryModuleType.JOB_SITE, 0.4F, 4), 2),
                        Pair.of(new StrollToPoi(MemoryModuleType.JOB_SITE, 0.4F, 1, 10), 5),
                        Pair.of(new StrollToPoiList(MemoryModuleType.SECONDARY_JOB_SITE, speedModifier, 1, 6, MemoryModuleType.JOB_SITE), 5),
                        Pair.of(new HarvestFarmland(), profession == VillagerProfession.FARMER ? 2 : 5),
                        Pair.of(new UseBonemeal(), profession == VillagerProfession.FARMER ? 4 : 7)))),
                //Pair.of(10, new ShowTradesToPlayer(400, 1600)),
                Pair.of(10, new SetLookAndInteract(EntityType.PLAYER, 4)),
                Pair.of(2, new SetWalkTargetFromBlockMemory(MemoryModuleType.JOB_SITE, speedModifier * RUN_MODIFIER, 9, 100, 1200)),
                Pair.of(99, new UpdateActivityFromSchedule()));
    }

    public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> getPlayPackage(float speedModifier) {
        return ImmutableList.of(
                Pair.of(0, new MoveToTargetSink(80, 120)),
                getFullLookBehavior(),
                Pair.of(5, new PlayTagWithOtherKids()),
                Pair.of(5, new RunOne<>(ImmutableMap.of(MemoryModuleType.VISIBLE_VILLAGER_BABIES, MemoryStatus.VALUE_ABSENT), ImmutableList.of(
                        Pair.of(InteractWith.of(EntityRegistry.CUSTOM_NPC.get(), 8, MemoryModuleType.INTERACTION_TARGET, speedModifier, 2), 2),
                        Pair.of(InteractWith.of(EntityType.CAT, 8, MemoryModuleType.INTERACTION_TARGET, speedModifier, 2), 1),
                        Pair.of(new VillageBoundRandomStroll(speedModifier), 1),
                        Pair.of(new SetWalkTargetFromLookTarget(speedModifier, 2), 1),
                        Pair.of(new JumpOnBed(speedModifier), 2),
                        Pair.of(new DoNothing(20, 40), 2)))),
                Pair.of(99, new UpdateActivityFromSchedule()));
    }

    public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> getRestPackage(float speedModifier) {
        return ImmutableList.of(
                Pair.of(2, new SetWalkTargetFromBlockMemory(MemoryModuleType.HOME, speedModifier * RUN_MODIFIER, 1, 150, 1200)),
                //Pair.of(3, new ValidateNearbyPoi(PoiType.HOME, MemoryModuleType.HOME)),
                Pair.of(3, new SleepInBed()),
                Pair.of(5, new RunOne<>(ImmutableMap.of(MemoryModuleType.HOME, MemoryStatus.VALUE_ABSENT), ImmutableList.of(
                        //Pair.of(new SetClosestHomeAsWalkTarget(speedModifier), 1),
                        Pair.of(new InsideBrownianWalk(speedModifier), 4),
                        //Pair.of(new GoToClosestVillage(speedModifier, 4), 2),
                        Pair.of(new DoNothing(20, 40), 2)))),
                getMinimalLookBehavior(),
                Pair.of(99, new UpdateActivityFromSchedule()));
    }

    public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> getMeetPackage(float speedModifier) {
        return ImmutableList.of(
                Pair.of(2, new RunOne<>(ImmutableList.of(
                        Pair.of(new StrollAroundPoi(MemoryModuleType.MEETING_POINT, 0.4F, 40), 2),
                        Pair.of(new SocializeAtBell(), 2)))),
                //Pair.of(10, new ShowTradesToPlayer(400, 1600)),
                Pair.of(10, new SetLookAndInteract(EntityType.PLAYER, 4)),
                Pair.of(2, new SetWalkTargetFromBlockMemory(MemoryModuleType.MEETING_POINT, speedModifier * RUN_MODIFIER, 6, 100, 200)),
                //Pair.of(3, new GiveGiftToHero(100)),
                //Pair.of(3, new ValidateNearbyPoi(PoiType.MEETING, MemoryModuleType.MEETING_POINT)),
                Pair.of(3, new GateBehavior<>(ImmutableMap.of(), ImmutableSet.of(MemoryModuleType.INTERACTION_TARGET), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.RUN_ONE, ImmutableList.of(Pair.of(new TradeWithVillager(), 1)))),
                getFullLookBehavior(),
                Pair.of(99, new UpdateActivityFromSchedule()));
    }

    public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> getIdlePackage(float speedModifier) {
        return ImmutableList.of(
                Pair.of(2, new RunOne<>(ImmutableList.of(
                        Pair.of(InteractWith.of(EntityType.VILLAGER, 8, MemoryModuleType.INTERACTION_TARGET, speedModifier, 2), 2),
                        //Pair.of(new InteractWith<>(EntityType.VILLAGER, 8, AgeableMob::canBreed, AgeableMob::canBreed, MemoryModuleType.BREED_TARGET, speedModifier, 2), 1),
                        Pair.of(InteractWith.of(EntityType.CAT, 8, MemoryModuleType.INTERACTION_TARGET, speedModifier, 2), 1),
                        Pair.of(new VillageBoundRandomStroll(speedModifier), 1),
                        Pair.of(new SetWalkTargetFromLookTarget(speedModifier, 2), 1),
                        Pair.of(new JumpOnBed(speedModifier), 1),
                        Pair.of(new DoNothing(30, 60), 1)))),
                //Pair.of(3, new GiveGiftToHero(100)),
                Pair.of(3, new SetLookAndInteract(EntityType.PLAYER, 4)),
                //Pair.of(3, new ShowTradesToPlayer(400, 1600)),
                Pair.of(3, new GateBehavior<>(ImmutableMap.of(), ImmutableSet.of(MemoryModuleType.INTERACTION_TARGET), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.RUN_ONE, ImmutableList.of(Pair.of(new TradeWithVillager(), 1)))),
                Pair.of(3, new GateBehavior<>(ImmutableMap.of(), ImmutableSet.of(MemoryModuleType.BREED_TARGET), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.RUN_ONE, ImmutableList.of(Pair.of(new VillagerMakeLove(), 1)))),
                getFullLookBehavior(),
                Pair.of(99, new UpdateActivityFromSchedule()));
    }

    public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> getPanicPackage(float speedModifier) {
        float f = speedModifier * 1.5F;
        return ImmutableList.of(
                Pair.of(0, new VillagerCalmDown()),
                Pair.of(1, SetWalkTargetAwayFrom.entity(MemoryModuleType.NEAREST_HOSTILE, f, 6, false)),
                Pair.of(1, SetWalkTargetAwayFrom.entity(MemoryModuleType.HURT_BY_ENTITY, f, 6, false)),
                Pair.of(3, new VillageBoundRandomStroll(f, 2, 2)),
                getMinimalLookBehavior());
    }

    public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> getPreRaidPackage(float speedModifier) {
        return ImmutableList.of(
                Pair.of(0, new RingBell()),
                Pair.of(0, new RunOne<>(ImmutableList.of(
                        Pair.of(new SetWalkTargetFromBlockMemory(MemoryModuleType.MEETING_POINT, speedModifier * 1.5F, 2, 150, 200), 6),
                        Pair.of(new VillageBoundRandomStroll(speedModifier * 1.5F), 2)))),
                getMinimalLookBehavior(),
                Pair.of(99, new ResetRaidStatus()));
    }

    public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> getRaidPackage(float speedModifier) {
        return ImmutableList.of(
                Pair.of(0, new RunOne<>(ImmutableList.of(
                        Pair.of(new GoOutsideToCelebrate(speedModifier), 5),
                        Pair.of(new VictoryStroll(speedModifier * 1.1F), 2)))),
                Pair.of(0, new CelebrateVillagersSurvivedRaid(600, 600)),
                Pair.of(2, new LocateHidingPlaceDuringRaid(24, speedModifier * 1.4F)),
                getMinimalLookBehavior(),
                Pair.of(99, new ResetRaidStatus()));
    }

    public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> getHidePackage(float speedModifier) {
        int i = 2;
        return ImmutableList.of(
                Pair.of(0, new SetHiddenState(15, 3)),
                Pair.of(1, new LocateHidingPlace(32, speedModifier * 1.25F, 2)),
                getMinimalLookBehavior());
    }

    private static Pair<Integer, Behavior<LivingEntity>> getFullLookBehavior() {
        return Pair.of(5, new RunOne<>(ImmutableList.of(
                Pair.of(new SetEntityLookTarget(EntityType.CAT, 8.0F), 8),
                Pair.of(new SetEntityLookTarget(EntityType.VILLAGER, 8.0F), 2),
                Pair.of(new SetEntityLookTarget(EntityType.PLAYER, 8.0F), 2),
                Pair.of(new SetEntityLookTarget(MobCategory.CREATURE, 8.0F), 1),
                Pair.of(new SetEntityLookTarget(MobCategory.WATER_CREATURE, 8.0F), 1),
                Pair.of(new SetEntityLookTarget(MobCategory.AXOLOTLS, 8.0F), 1),
                Pair.of(new SetEntityLookTarget(MobCategory.UNDERGROUND_WATER_CREATURE, 8.0F), 1),
                Pair.of(new SetEntityLookTarget(MobCategory.WATER_AMBIENT, 8.0F), 1),
                Pair.of(new SetEntityLookTarget(MobCategory.MONSTER, 8.0F), 1),
                Pair.of(new DoNothing(30, 60), 2))));
    }

    private static Pair<Integer, Behavior<LivingEntity>> getMinimalLookBehavior() {
        return Pair.of(5, new RunOne<>(ImmutableList.of(
                Pair.of(new SetEntityLookTarget(EntityType.VILLAGER, 8.0F), 2),
                Pair.of(new SetEntityLookTarget(EntityType.PLAYER, 8.0F), 2),
                Pair.of(new DoNothing(30, 60), 8))));
    }
}
