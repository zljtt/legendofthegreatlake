package github.zljtt.legendofthegreatlake.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import github.zljtt.legendofthegreatlake.LegendOfTheGreatLake;
import github.zljtt.legendofthegreatlake.npc.ClientNPCManager;
import github.zljtt.legendofthegreatlake.npc.Dialog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class VillagerDialogScreen extends AbstractContainerScreen<VillagerDialogContainer> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(LegendOfTheGreatLake.MODID, "textures/gui/dialog.png");

    public static final int OPTION_MAX = 3;
    protected OptionButton[] options = new OptionButton[OPTION_MAX];
    private Dialog currentDialog;
    private TextComponent npcName;

    public VillagerDialogScreen(VillagerDialogContainer container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.imageWidth = 256;
        this.imageHeight = 50;
        if (this.title instanceof TextComponent name) {
            this.npcName = new TextComponent(ClientNPCManager.getInstance().getName(name.getText()));
            this.currentDialog = ClientNPCManager.getInstance().getDialog(name.getText());
        }
    }

    @Override
    protected void init() {
        super.init();
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = this.height - this.imageHeight - 30;
        this.createOptionButtons();
    }

    protected void createOptionButtons() {
        int optionX = this.leftPos + 3;
        int optionHeight = 22 + 3;
        int optionY = this.topPos;
        for (int i = 0; i < this.options.length; i++) {
            this.removeWidget(this.options[i]);
        }
        List<Dialog.Option> dialogOptions = this.currentDialog.getVisibleOptions();
        this.options = new OptionButton[dialogOptions.size()];
        for (int i = 0; i < this.currentDialog.getVisibleOptions().size(); i++) {
            this.options[i] = this.addRenderableWidget(new OptionButton(optionX, optionY - (i + 1) * optionHeight, 126, 22, this::onButtonSelect, dialogOptions.get(i)));
        }
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
        this.font.draw(stack, npcName.withStyle(npcName.getStyle().withBold(true)), 15, 10, 0x404040);
        List<FormattedCharSequence> paragraph = this.font.split(new TextComponent(this.currentDialog.getDialog()), 210);
        int k = Math.min(2, paragraph.size());
        int paragraphHeight = k * 8 + (k - 1) * 2;
        int paragraphY = 21 + (18 - paragraphHeight) / 2;
        for (int l = 0; l < k; ++l) {
            FormattedCharSequence formattedcharsequence = paragraph.get(l);
            this.font.draw(stack, formattedcharsequence, 15, paragraphY + l * 10, 0x404040);
        }
        //this.cachedPageComponents = this.font.split(formattedtext, 114);

    }

    void onButtonSelect(Button option) {
        if (option instanceof OptionButton optionButton) {
            this.currentDialog = optionButton.getNext();
            this.createOptionButtons();
            LegendOfTheGreatLake.LOGGER.debug("Select to new dialog " + this.currentDialog.toJson());
        }
    }

    public static class OptionButton extends Button {
        private final Dialog.Option option;

        public OptionButton(int x, int y, int width, int height, OnPress onPress, Dialog.Option nextedOption) {
            super(x, y, width, height, TextComponent.EMPTY, onPress);
            this.option = nextedOption;
            TextComponent text = new TextComponent(nextedOption.getText());
            this.setMessage(text.withStyle(text.getStyle().withBold(true)));
        }

        public Dialog getNext() {
            return option.getNext();
        }

        @Override
        public void renderButton(PoseStack stack, int mouseX, int mouseY, float particleTick) {
            Minecraft minecraft = Minecraft.getInstance();
            Font font = minecraft.font;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, TEXTURE);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            int length = font.width(this.getMessage());
            if (length <= 103) {
                this.width = 126;
                this.blit(stack, this.x, this.y, 0, this.isHoveredOrFocused() ? 78 : 53, this.width, this.height);
            } else {
                this.width = 203;
                this.blit(stack, this.x, this.y, 0, this.isHoveredOrFocused() ? 128 : 103, this.width, this.height);
            }
            font.draw(stack, this.getMessage(), this.x + 10, this.y + (float) (this.height - 8) / 2, 0x404040);

        }
    }

}
