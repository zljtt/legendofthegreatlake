package github.zljtt.legendofthegreatlake.entity;

import github.zljtt.legendofthegreatlake.LegendOfTheGreatLake;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, LegendOfTheGreatLake.MODID);

    public static final RegistryObject<EntityType<CustomNPC>> CUSTOM_NPC =
            ENTITIES.register("custom_npc", () -> EntityType.Builder.of(CustomNPC::new, MobCategory.MISC).sized(0.6F, 1.95F).clientTrackingRange(10)
                    .build(new ResourceLocation(LegendOfTheGreatLake.MODID, "custom_npc").toString()));

}
