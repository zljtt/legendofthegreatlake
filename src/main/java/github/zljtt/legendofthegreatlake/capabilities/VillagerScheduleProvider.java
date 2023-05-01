package github.zljtt.legendofthegreatlake.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VillagerScheduleProvider implements ICapabilitySerializable<CompoundTag> {


    public static final Capability<IVillagerSchedule> VILLAGER_SCHEDULE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    private IVillagerSchedule schedule;

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == VILLAGER_SCHEDULE_CAPABILITY ? LazyOptional.of(this::getSchedule).cast() : LazyOptional.empty();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return cap == VILLAGER_SCHEDULE_CAPABILITY ? LazyOptional.of(this::getSchedule).cast() : LazyOptional.empty();
    }

    @NotNull
    private IVillagerSchedule getSchedule() {
        if (schedule == null) {
            schedule = new VillagerSchedule();
        }
        return schedule;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbtTagList = new CompoundTag();
        for (int i = 0; i < getSchedule().getStacks().size(); i++) {
            CompoundTag itemTag = new CompoundTag();
            getSchedule().getStacks().get(i).save(itemTag);
            nbtTagList.put(VillagerSchedule.TimeSlot.values()[i].toString(), itemTag);
        }
        //nbtTagList.putString("Skin", getSchedule().getImageName());
        CompoundTag nbt = new CompoundTag();
        nbt.put("Schedule", nbtTagList);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getSchedule().setStacks(NonNullList.withSize(VillagerSchedule.TimeSlot.values().length, ItemStack.EMPTY));

        CompoundTag tagList = nbt.getCompound("Schedule");
        for (int i = 0; i < VillagerSchedule.TimeSlot.values().length; i++) {
            CompoundTag itemTag = tagList.getCompound(VillagerSchedule.TimeSlot.values()[i].toString());
            getSchedule().getStacks().set(i, ItemStack.of(itemTag));
        }
        //String name = tagList.getString("Skin");
        //getSchedule().setImageName(name.equals("") ? "custom_npc" : name);
    }

}
