package github.zljtt.legendofthegreatlake.items;

import github.zljtt.legendofthegreatlake.LegendOfTheGreatLake;
import github.zljtt.legendofthegreatlake.entity.CustomNPC;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SkinTag extends Item {

    public SkinTag(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity target, InteractionHand hand) {
        LegendOfTheGreatLake.LOGGER.debug("Interact");
        if (target instanceof CustomNPC npc) {


            return InteractionResult.sidedSuccess(player.level.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }
}
