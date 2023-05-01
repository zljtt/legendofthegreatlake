package github.zljtt.legendofthegreatlake.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WorldCalendarProvider implements ICapabilitySerializable<CompoundTag> {


    public static final Capability<IWorldCalendar> WORLD_CALENDAR_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    private IWorldCalendar calendar;

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == WORLD_CALENDAR_CAPABILITY ? LazyOptional.of(this::getCalendar).cast() : LazyOptional.empty();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return cap == WORLD_CALENDAR_CAPABILITY ? LazyOptional.of(this::getCalendar).cast() : LazyOptional.empty();
    }

    @NotNull
    private IWorldCalendar getCalendar() {
        if (calendar == null) {
            calendar = new WorldCalendar();
        }
        return calendar;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbtTagList = new CompoundTag();
        nbtTagList.putString("TimeSlot", getCalendar().getCurrentTimeSlot().toString());

        CompoundTag nbt = new CompoundTag();
        nbt.put("Calendar", nbtTagList);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        CompoundTag tagList = nbt.getCompound("Calendar");
        String timeSlot = tagList.getString("TimeSlot");
        getCalendar().setCurrentTimeSlot(VillagerSchedule.TimeSlot.valueOf(timeSlot));
    }

}
