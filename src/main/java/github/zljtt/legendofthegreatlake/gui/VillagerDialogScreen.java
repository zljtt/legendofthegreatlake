package github.zljtt.legendofthegreatlake.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import github.zljtt.legendofthegreatlake.LegendOfTheGreatLake;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class VillagerDialogScreen extends AbstractContainerScreen<VillagerDialogContainer> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(LegendOfTheGreatLake.MODID, "textures/gui/dialog.png");

    public static final int OPTION_MAX = 3;
    protected OptionButton[] options = new OptionButton[OPTION_MAX];
    private String dialogLevel = "";
    private TranslatableComponent npcName;

    public VillagerDialogScreen(VillagerDialogContainer container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.imageWidth = 256;
        this.imageHeight = 50;
        this.resetDialog();
        if (this.title instanceof TranslatableComponent name) {
            this.npcName = new TranslatableComponent(name.getKey() + ".name");
        }
    }

    @Override
    protected void init() {
        super.init();
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = this.height - this.imageHeight - 30;
        this.createOptionButtons();
        this.refreshOptions();
    }

    protected void createOptionButtons() {
        int optionX = this.leftPos + 3;
        int optionHeight = 22 + 3;
        int optionY = this.topPos;
        this.options[0] = this.addRenderableWidget(new OptionButton(optionX, optionY - optionHeight, 126, 22, (button) -> {
            this.selectDialogOption(0);
        }));
        this.options[1] = this.addRenderableWidget(new OptionButton(optionX, optionY - 2 * optionHeight, 166, 22, (button) -> {
            this.selectDialogOption(1);
        }));
        this.options[2] = this.addRenderableWidget(new OptionButton(optionX, optionY - 3 * optionHeight, 166, 22, (button) -> {
            this.selectDialogOption(2);
        }));
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
        String dialog = this.getDialog();
        this.font.draw(stack, npcName.withStyle(npcName.getStyle().withBold(true)), 15, 10, 0x404040);
        if (I18n.exists(dialog)) { // 21 - 39
            List<FormattedCharSequence> paragraph = this.font.split(new TranslatableComponent(dialog), 210);
            int k = Math.min(2, paragraph.size());
            int paragraphHeight = k * 8 + (k - 1) * 2;
            int paragraphY = 21 + (18 - paragraphHeight) / 2;
            for (int l = 0; l < k; ++l) {
                FormattedCharSequence formattedcharsequence = paragraph.get(l);
                this.font.draw(stack, formattedcharsequence, 15, paragraphY + l * 10, 0x404040);
            }
        } else {
            this.font.draw(stack, new TranslatableComponent("npc.default_dialog"), 15, 25, 0x404040);
        }
        //this.cachedPageComponents = this.font.split(formattedtext, 114);

    }

    @Override
    public void onClose() {
        this.resetDialog();
        super.onClose();
    }

    void selectDialogOption(int option) {
        this.progressDialog(option);
        this.refreshOptions();
        LegendOfTheGreatLake.LOGGER.debug("dialog level " + this.dialogLevel);
    }

    void refreshOptions() {
        for (int i = 0; i < OPTION_MAX; i++) {
            String rl = this.getDialogOption(i);
            if (I18n.exists(rl)) {
                this.options[i].visible = true;
                this.options[i].setMessage(new TranslatableComponent(rl).withStyle(npcName.getStyle().withBold(true)));
            } else {
                this.options[i].visible = false;
            }
        }
    }


    public String getDialog() {
        if (dialogLevel == null || dialogLevel.isEmpty()) {
            this.resetDialog();
        }
        return this.dialogLevel + ".dialog";
    }

    public String getDialogOption(int option) {
        if (dialogLevel == null || dialogLevel.isEmpty()) {
            this.resetDialog();
        }
        return this.dialogLevel + ".option" + (option + 1);
    }

    public void progressDialog(int option) {
        if (dialogLevel == null || dialogLevel.isEmpty()) {
            this.resetDialog();
        }
        this.dialogLevel += ".option" + (option + 1);
    }

    public void resetDialog() {
        if (this.title instanceof TranslatableComponent name) {
            this.dialogLevel = name.getKey();
        }
    }

    public static class OptionButton extends Button {

        public OptionButton(int x, int y, int width, int height, OnPress onPress) {
            super(x, y, width, height, TextComponent.EMPTY, onPress);
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
