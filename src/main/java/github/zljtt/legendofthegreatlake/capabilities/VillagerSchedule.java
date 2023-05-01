package github.zljtt.legendofthegreatlake.capabilities;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;


public class VillagerSchedule implements IItemHandlerModifiable, IVillagerSchedule {

    protected NonNullList<ItemStack> stacks;


    public VillagerSchedule() {
        this(TimeSlot.values().length);
    }

    public VillagerSchedule(int size) {
        stacks = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    public NonNullList<ItemStack> getStacks() {
        return stacks;
    }

    public void setStacks(NonNullList<ItemStack> list) {
        stacks = list;
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        validateSlotIndex(slot);
        this.stacks.set(slot, stack);
        onContentsChanged(slot);
    }

    @Override
    public int getSlots() {
        return stacks.size();
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int slot) {
        validateSlotIndex(slot);
        return this.stacks.get(slot);
    }

    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        if (!isItemValid(slot, stack))
            return stack;

        validateSlotIndex(slot);

        ItemStack existing = this.stacks.get(slot);

        int limit = getStackLimit(slot, stack);

        if (!existing.isEmpty()) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                return stack;

            limit -= existing.getCount();
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate) {
            if (existing.isEmpty()) {
                this.stacks.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
            } else {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }
            onContentsChanged(slot);
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0)
            return ItemStack.EMPTY;

        validateSlotIndex(slot);

        ItemStack existing = this.stacks.get(slot);

        if (existing.isEmpty())
            return ItemStack.EMPTY;

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.getCount() <= toExtract) {
            if (!simulate) {
                this.stacks.set(slot, ItemStack.EMPTY);
                onContentsChanged(slot);
                return existing;
            } else {
                return existing.copy();
            }
        } else {
            if (!simulate) {
                this.stacks.set(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
                onContentsChanged(slot);
            }

            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }

    protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
        return Math.min(getSlotLimit(slot), stack.getMaxStackSize());
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return true;
    }


    protected void validateSlotIndex(int slot) {
        if (slot < 0 || slot >= stacks.size())
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + stacks.size() + ")");
    }

    protected void onContentsChanged(int slot) {

    }

    /**
     * day = 1000
     * noon = 6000
     * night = 13000
     * midnight = 18000
     */
    /**
     * sunrise = 23000 - 1000
     * morning = 1000 - 5000
     * noon = 5000 - 7000
     * afternoon = 7000 - 12000
     * evening = 12000 - 15000
     * night = 15000 - 23000
     */

    public enum TimeSlot {
        SUNRISE(0, 23000, 1000),
        MORNING(1, 1000, 5000),
        NOON(2, 5000, 7000),
        AFTERNOON(3, 7000, 12000),
        SUNSET(4, 12000, 15000),
        NIGHT(5, 15000, 23000);

        public int getIndex() {
            return index;
        }

        public int getStartTime() {
            return startTime;
        }

        public int getEndTime() {
            return endTime;
        }

        final int index;
        final int startTime;
        final int endTime;

        TimeSlot(int index, int startTime, int endTime) {
            this.index = index;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public static TimeSlot getFromTime(int time) {
            if (time > SUNRISE.startTime || time <= SUNRISE.endTime) {
                return SUNRISE;
            } else if (time > MORNING.startTime && time <= MORNING.endTime) {
                return MORNING;
            } else if (time > NOON.startTime && time <= NOON.endTime) {
                return NOON;
            } else if (time > AFTERNOON.startTime && time <= AFTERNOON.endTime) {
                return AFTERNOON;
            } else if (time > SUNSET.startTime && time <= SUNSET.endTime) {
                return SUNSET;
            } else if (time > NIGHT.startTime) {
                return NIGHT;
            }
            return null;
        }


    }


}
