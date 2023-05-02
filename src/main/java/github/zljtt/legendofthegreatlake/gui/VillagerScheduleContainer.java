package github.zljtt.legendofthegreatlake.gui;

import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class VillagerScheduleContainer extends AbstractContainerMenu {
    //private final ContainerLevelAccess containerLevelAccess;

    public VillagerScheduleContainer(int id, Inventory inventory) {
        this(id, inventory, new ItemStackHandler(6), new ItemStackHandler(6), null);
    }

    public VillagerScheduleContainer(int id, Inventory inventory, IItemHandler schedule, IItemHandler equipment, Villager villager) {
        super(ContainerRegistry.VILLAGER_SCHEDULE.get(), id);
        int slotSize = 18;
        int equipmentX = 8, equipmentY = 8;
        int handX = 35, handY = 44;
        int scheduleX = 62, scheduleY = 62;
        int invX = 8, invY = 84;
        int hotbarY = 142;
        for (int column = 0; column < 9; column++) {
            for (int row = 0; row < 3; row++) {
                addSlot(new Slot(inventory, 9 + row * 9 + column, invX + column * slotSize, invY + row * slotSize));
            }
            addSlot(new Slot(inventory, column, invX + column * slotSize, hotbarY));
        }
        addSlot(new SlotItemHandler(equipment, 0, handX, handY));
        addSlot(new SlotItemHandler(equipment, 1, handX, handY + slotSize));

        addSlot(new SlotItemHandler(equipment, 5, equipmentX, equipmentY + 0 * slotSize));
        addSlot(new SlotItemHandler(equipment, 4, equipmentX, equipmentY + 1 * slotSize));
        addSlot(new SlotItemHandler(equipment, 3, equipmentX, equipmentY + 2 * slotSize));
        addSlot(new SlotItemHandler(equipment, 2, equipmentX, equipmentY + 3 * slotSize));

        for (int column = 0; column < schedule.getSlots(); column++) {
            addSlot(new SlotItemHandler(schedule, column, scheduleX + column * slotSize, scheduleY));
        }
        //this.containerLevelAccess = ContainerLevelAccess.create(playerInv.player.level, pos);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        var retStack = ItemStack.EMPTY;
        final Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            final ItemStack stack = slot.getItem();
            retStack = stack.copy();

            final int size = this.slots.size() - player.getInventory().getContainerSize();
            if (index < size) {
                if (!moveItemStackTo(stack, 0, this.slots.size(), false))
                    return ItemStack.EMPTY;
            } else if (!moveItemStackTo(stack, 0, size, false))
                return ItemStack.EMPTY;

            if (stack.isEmpty() || stack.getCount() == 0) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == retStack.getCount())
                return ItemStack.EMPTY;

            slot.onTake(player, stack);
        }

        return retStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
        //return stillValid(this.containerLevelAccess, player, );
    }

    public static MenuConstructor getServerContainer(Villager villager, IItemHandler slots, IItemHandler equipment) {
        return (id, playerInv, player) -> new VillagerScheduleContainer(id, playerInv, slots, equipment, villager);
    }
}
