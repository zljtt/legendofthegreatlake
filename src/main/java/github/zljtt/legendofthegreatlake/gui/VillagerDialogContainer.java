package github.zljtt.legendofthegreatlake.gui;

import github.zljtt.legendofthegreatlake.entity.CustomNPC;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuConstructor;

public class VillagerDialogContainer extends AbstractContainerMenu {

    private CustomNPC npc;

    public VillagerDialogContainer(int id, Inventory inventory) {
        this(id, inventory, null);
    }

    public VillagerDialogContainer(int id, Inventory inventory, CustomNPC npc) {
        super(MenuRegistry.VILLAGER_DIALOG.get(), id);
        this.npc = npc;
    }


    @Override
    public boolean stillValid(Player player) {
        if (npc != null && npc.isAlive()) {
            return player.distanceTo(npc) < 6;
        }
        return false;
    }

    public static MenuConstructor getServerContainer(CustomNPC npc) {
        return (id, playerInv, player) -> new VillagerDialogContainer(id, playerInv, npc);
    }
}
