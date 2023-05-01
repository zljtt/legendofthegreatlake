package github.zljtt.legendofthegreatlake.capabilities;

public class WorldCalendar implements IWorldCalendar {

    private VillagerSchedule.TimeSlot timeSlot;

    public WorldCalendar() {
        timeSlot = VillagerSchedule.TimeSlot.SUNRISE;
    }


    @Override
    public VillagerSchedule.TimeSlot getCurrentTimeSlot() {
        return timeSlot;
    }

    @Override
    public void setCurrentTimeSlot(VillagerSchedule.TimeSlot slot) {
        timeSlot = slot;
    }
}
