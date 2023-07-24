package github.zljtt.legendofthegreatlake.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab ITEM_TAB = new CreativeModeTab("legendofthegreatlake.item") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemRegistry.BAER_BRAISED_STEW.get());
        }
    };

    public static final CreativeModeTab BLOCK_TAB = new CreativeModeTab("legendofthegreatlake.block") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemRegistry.TOBACCO_PIPE.get());
        }
    };
}
