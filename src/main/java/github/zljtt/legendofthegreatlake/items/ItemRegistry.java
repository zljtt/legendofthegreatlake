package github.zljtt.legendofthegreatlake.items;

import github.zljtt.legendofthegreatlake.LegendOfTheGreatLake;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LegendOfTheGreatLake.MODID);

    public static final RegistryObject<Item> DEBUG_STICK = ITEMS.register("debug_stick", () -> new DebugStick((new Item.Properties()).stacksTo(1).tab(ModCreativeModeTab.CREATIVE_MODE_TAB)));

    public static final RegistryObject<Item> PURPLE_POTION = ITEMS.register("purple_potion", () -> new Item((new Item.Properties()).stacksTo(1).tab(ModCreativeModeTab.CREATIVE_MODE_TAB).food(Foods.APPLE)));
    public static final RegistryObject<Item> BAER_BRAISED_STEW = ITEMS.register("baer_braised_stew", () -> new Item((new Item.Properties()).tab(ModCreativeModeTab.CREATIVE_MODE_TAB).food(FoodRegistry.BAER_BRAISED_STEW)));
    public static final RegistryObject<Item> BAGUETTE = ITEMS.register("baguette", () -> new Item((new Item.Properties()).tab(ModCreativeModeTab.CREATIVE_MODE_TAB).food(FoodRegistry.BAGUETTE)));
    public static final RegistryObject<Item> BUTTERY_BISCUIT = ITEMS.register("buttery_biscuit", () -> new Item((new Item.Properties()).tab(ModCreativeModeTab.CREATIVE_MODE_TAB).food(FoodRegistry.BUTTERY_BISCUIT)));


    public static final RegistryObject<Item> SCHEDULE_WORK = ITEMS.register("schedule_work", () -> new ScheduledEvent(ScheduledEvent.EventType.WORK, (new Item.Properties()).tab(ModCreativeModeTab.CREATIVE_MODE_TAB).food(FoodRegistry.BUTTERY_BISCUIT)));
    public static final RegistryObject<Item> SCHEDULE_REST = ITEMS.register("schedule_rest", () -> new ScheduledEvent(ScheduledEvent.EventType.REST, (new Item.Properties()).tab(ModCreativeModeTab.CREATIVE_MODE_TAB).food(FoodRegistry.BUTTERY_BISCUIT)));
    public static final RegistryObject<Item> SCHEDULE_MEET = ITEMS.register("schedule_meet", () -> new ScheduledEvent(ScheduledEvent.EventType.MEET, (new Item.Properties()).tab(ModCreativeModeTab.CREATIVE_MODE_TAB).food(FoodRegistry.BUTTERY_BISCUIT)));

}
