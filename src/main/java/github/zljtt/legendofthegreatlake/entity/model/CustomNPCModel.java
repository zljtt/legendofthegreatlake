package github.zljtt.legendofthegreatlake.entity.model;

import github.zljtt.legendofthegreatlake.entity.CustomNPC;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CustomNPCModel<T extends CustomNPC> extends PlayerModel<CustomNPC> {
    public CustomNPCModel(ModelPart modelPart, boolean slim) {
        super(modelPart, slim);
    }
}
