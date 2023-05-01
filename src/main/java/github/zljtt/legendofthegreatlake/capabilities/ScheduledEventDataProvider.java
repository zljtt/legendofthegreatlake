package github.zljtt.legendofthegreatlake.capabilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ScheduledEventDataProvider implements ICapabilitySerializable<CompoundTag> {


    public static final Capability<IScheduledEventData> SCHEDULED_EVENT_DATA_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    private IScheduledEventData data;

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == SCHEDULED_EVENT_DATA_CAPABILITY ? LazyOptional.of(this::getData).cast() : LazyOptional.empty();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return cap == SCHEDULED_EVENT_DATA_CAPABILITY ? LazyOptional.of(this::getData).cast() : LazyOptional.empty();
    }

    @NotNull
    private IScheduledEventData getData() {
        if (data == null) {
            data = new ScheduledEventData();
        }
        return data;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbtTagList = new CompoundTag();
        nbtTagList.putInt("x", getData().getEventPosition().getX());
        nbtTagList.putInt("y", getData().getEventPosition().getY());
        nbtTagList.putInt("z", getData().getEventPosition().getZ());
        CompoundTag nbt = new CompoundTag();
        nbt.put("ScheduledEventData", nbtTagList);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        CompoundTag tagList = nbt.getCompound("ScheduledEventData");
        int x = tagList.getInt("x");
        int y = tagList.getInt("y");
        int z = tagList.getInt("z");
        getData().setEventPosition(new BlockPos(x, y, z));
    }

}
