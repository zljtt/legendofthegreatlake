package github.zljtt.legendofthegreatlake.gui;

import github.zljtt.legendofthegreatlake.LegendOfTheGreatLake;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ContainerRegistry {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, LegendOfTheGreatLake.MODID);
    public static final RegistryObject<MenuType<VillagerScheduleContainer>> VILLAGER_SCHEDULE = CONTAINERS
            .register("villager_schedule", () -> new MenuType<>(VillagerScheduleContainer::new));
}
