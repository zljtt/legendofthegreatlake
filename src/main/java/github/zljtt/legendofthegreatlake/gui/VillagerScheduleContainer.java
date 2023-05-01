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
        this(id, inventory, new ItemStackHandler(6), null);
    }

    public VillagerScheduleContainer(int id, Inventory inventory, IItemHandler slots, Villager villager) {
        super(ContainerRegistry.VILLAGER_SCHEDULE.get(), id);
        int slotSize = 18, invX = 8, invY = 51, hotbarY = 109, contentX = 35, contentY = 20;
        for (int column = 0; column < 9; column++) {
            for (int row = 0; row < 3; row++) {
                addSlot(new Slot(inventory, 9 + row * 9 + column, invX + column * slotSize, invY + row * slotSize));
            }
            addSlot(new Slot(inventory, column, invX + column * slotSize, hotbarY));
        }
        for (int column = 0; column < slots.getSlots(); column++) {
            addSlot(new SlotItemHandler(slots, column, contentX + column * slotSize, contentY));
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

    public static MenuConstructor getServerContainer(Villager villager, IItemHandler slots) {
        return (id, playerInv, player) -> new VillagerScheduleContainer(id, playerInv, slots, villager);
    }
}
