package github.zljtt.legendofthegreatlake.gui;

import github.zljtt.legendofthegreatlake.LegendOfTheGreatLake;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuRegistry {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.CONTAINERS, LegendOfTheGreatLake.MODID);
    public static final RegistryObject<MenuType<VillagerScheduleContainer>> VILLAGER_SCHEDULE = MENUS
            .register("villager_schedule", () -> new MenuType<>(VillagerScheduleContainer::new));

    public static final RegistryObject<MenuType<VillagerDialogContainer>> VILLAGER_DIALOG = MENUS
            .register("villager_dialog", () -> new MenuType<>(VillagerDialogContainer::new));
}
