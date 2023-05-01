package github.zljtt.legendofthegreatlake.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab CREATIVE_MODE_TAB = new CreativeModeTab("Legend of the Great Lake") {
        @Override
        public ItemStack makeIcon() {
                return new ItemStack(ItemRegistry.BAER_BRAISED_STEW.get());
        }
    };
}
