package github.zljtt.legendofthegreatlake.items;

import github.zljtt.legendofthegreatlake.LegendOfTheGreatLake;
import github.zljtt.legendofthegreatlake.blocks.BlockRegistry;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LegendOfTheGreatLake.MODID);

    public static final RegistryObject<Item> DEBUG_STICK = ITEMS.register("debug_stick", () -> new DebugStick((new Item.Properties()).stacksTo(1).tab(ModCreativeModeTab.ITEM_TAB)));
    public static final RegistryObject<Item> SKIN_TAG = ITEMS.register("skin_tag", () -> new SkinTag((new Item.Properties()).tab(ModCreativeModeTab.ITEM_TAB)));

    public static final RegistryObject<Item> PURPLE_POTION = ITEMS.register("purple_potion", () -> new Item((new Item.Properties()).stacksTo(1).tab(ModCreativeModeTab.ITEM_TAB).food(Foods.APPLE)));
    public static final RegistryObject<Item> BAER_BRAISED_STEW = ITEMS.register("baer_braised_stew", () -> new Item((new Item.Properties()).tab(ModCreativeModeTab.ITEM_TAB).food(FoodRegistry.BAER_BRAISED_STEW)));
    public static final RegistryObject<Item> BAGUETTE = ITEMS.register("baguette", () -> new Item((new Item.Properties()).tab(ModCreativeModeTab.ITEM_TAB).food(FoodRegistry.BAGUETTE)));
    public static final RegistryObject<Item> BUTTERY_BISCUIT = ITEMS.register("buttery_biscuit", () -> new Item((new Item.Properties()).tab(ModCreativeModeTab.ITEM_TAB).food(FoodRegistry.BUTTERY_BISCUIT)));


    public static final RegistryObject<Item> LONGER_SWORD = ITEMS.register("longer_sword", () -> new SwordItem(Tiers.DIAMOND, 4, -3F, (new Item.Properties()).tab(ModCreativeModeTab.ITEM_TAB)));
    public static final RegistryObject<Item> SCHEDULE_WORK = ITEMS.register("schedule_work", () -> new ScheduledEvent(ScheduledEvent.EventType.WORK, (new Item.Properties()).tab(ModCreativeModeTab.ITEM_TAB)));
    public static final RegistryObject<Item> SCHEDULE_REST = ITEMS.register("schedule_rest", () -> new ScheduledEvent(ScheduledEvent.EventType.REST, (new Item.Properties()).tab(ModCreativeModeTab.ITEM_TAB)));
    public static final RegistryObject<Item> SCHEDULE_MEET = ITEMS.register("schedule_meet", () -> new ScheduledEvent(ScheduledEvent.EventType.MEET, (new Item.Properties()).tab(ModCreativeModeTab.ITEM_TAB)));


    public static final RegistryObject<Item> TOBACCO_PIPE = ITEMS.register("tobacco_pipe", () -> new BlockItem(BlockRegistry.PIPE.get(), (new Item.Properties()).tab(ModCreativeModeTab.BLOCK_TAB)));
    public static final RegistryObject<Item> CRUTCH_1 = ITEMS.register("crutch_1", () -> new BlockItem(BlockRegistry.CRUTCH_1.get(), (new Item.Properties()).tab(ModCreativeModeTab.BLOCK_TAB)));
    public static final RegistryObject<Item> CRUTCH_2 = ITEMS.register("crutch_2", () -> new BlockItem(BlockRegistry.CRUTCH_2.get(), (new Item.Properties()).tab(ModCreativeModeTab.BLOCK_TAB)));
    public static final RegistryObject<Item> FRAME_GROUND = ITEMS.register("frame_ground", () -> new BlockItem(BlockRegistry.FRAME_GROUND.get(), (new Item.Properties()).tab(ModCreativeModeTab.BLOCK_TAB)));
    public static final RegistryObject<Item> FRAME_WALL = ITEMS.register("frame_wall", () -> new BlockItem(BlockRegistry.FRAME_WALL.get(), (new Item.Properties()).tab(ModCreativeModeTab.BLOCK_TAB)));
    public static final RegistryObject<Item> GLASSES = ITEMS.register("glasses", () -> new BlockItem(BlockRegistry.GLASSES.get(), (new Item.Properties()).tab(ModCreativeModeTab.BLOCK_TAB)));
    //public static final RegistryObject<Item> HANGER = ITEMS.register("hanger", () -> new BlockItem(BlockRegistry.HANGER.get(), (new Item.Properties()).tab(ModCreativeModeTab.BLOCK_TAB)));
    public static final RegistryObject<Item> POCKET_WATCH = ITEMS.register("pocket_watch", () -> new BlockItem(BlockRegistry.POCKET_WATCH.get(), (new Item.Properties()).tab(ModCreativeModeTab.BLOCK_TAB)));
    public static final RegistryObject<Item> WINE_BOTTLE_FALL = ITEMS.register("wine_bottle_fall", () -> new BlockItem(BlockRegistry.WINE_BOTTLE_FALL.get(), (new Item.Properties()).tab(ModCreativeModeTab.BLOCK_TAB)));

}
