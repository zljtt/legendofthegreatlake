package github.zljtt.legendofthegreatlake.capabilities;

public interface IWorldCalendar {
    VillagerSchedule.TimeSlot getCurrentTimeSlot();

    void setCurrentTimeSlot(VillagerSchedule.TimeSlot slot);
}
