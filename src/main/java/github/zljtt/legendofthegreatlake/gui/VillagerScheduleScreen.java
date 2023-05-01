package github.zljtt.legendofthegreatlake.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import github.zljtt.legendofthegreatlake.LegendOfTheGreatLake;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class VillagerScheduleScreen extends AbstractContainerScreen<VillagerScheduleContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(LegendOfTheGreatLake.MODID, "textures/gui/villager_schedule.png");
    private ExtendedButton confirmButton;

    public VillagerScheduleScreen(VillagerScheduleContainer container, Inventory inventory, Component component) {
        super(container, inventory, new TextComponent("Villager Scheduler"));
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 133;
    }

    @Override
    protected void renderBg(PoseStack stack, float particleTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        blit(stack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float particleTicks) {
        super.render(stack, mouseX, mouseY, particleTicks);
        RenderSystem.disableBlend();
        this.renderTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack stack, int mouseX, int mouseY) {
        drawString(stack, this.font, this.title, this.leftPos + 8, this.topPos + 3, 0x404040);
        //drawString(stack, this.font, this.playerInventoryTitle, this.leftPos + 8, this.leftPos + 80, 0x404040);
    }


    @Override
    protected void init() {
        super.init();
        /**
         this.confirmButton = this.addRenderableWidget(new ExtendedButton(this.leftPos, this.topPos, 16, 16, new TextComponent("Confirm"), btn -> {
         Minecraft.getInstance().player.displayClientMessage(new TextComponent("confirm"), false);
         }));
         */
    }
}
