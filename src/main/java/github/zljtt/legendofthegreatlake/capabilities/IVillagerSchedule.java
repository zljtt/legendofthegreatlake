package github.zljtt.legendofthegreatlake.capabilities;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public interface IVillagerSchedule extends IItemHandler {

    NonNullList<ItemStack> getStacks();

    void setStacks(NonNullList<ItemStack> list);

}
