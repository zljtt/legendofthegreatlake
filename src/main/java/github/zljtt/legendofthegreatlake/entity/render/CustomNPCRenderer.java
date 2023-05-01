package github.zljtt.legendofthegreatlake.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import github.zljtt.legendofthegreatlake.entity.CustomNPC;
import github.zljtt.legendofthegreatlake.entity.model.CustomNPCModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.BeeStingerLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)

public class CustomNPCRenderer extends LivingEntityRenderer<CustomNPC, CustomNPCModel<CustomNPC>> {
    public CustomNPCRenderer(EntityRendererProvider.Context context) {
        this(context, false);
    }

    public CustomNPCRenderer(EntityRendererProvider.Context context, boolean slim) {
        super(context, new CustomNPCModel<>(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER), slim), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this,
                new HumanoidModel(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM_INNER_ARMOR : ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM_OUTER_ARMOR : ModelLayers.PLAYER_OUTER_ARMOR))));

        //this.addLayer(new PlayerItemInHandLayer<>(this));
        //this.addLayer(new ArrowLayer<>(context, this));
        //this.addLayer(new Deadmau5EarsLayer(this));
        //this.addLayer(new CapeLayer(this));
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet()));
        this.addLayer(new ElytraLayer<>(this, context.getModelSet()));
        //this.addLayer(new ParrotOnShoulderLayer<>(this, context.getModelSet()));
        //this.addLayer(new SpinAttackEffectLayer<>(this, context.getModelSet()));
        this.addLayer(new BeeStingerLayer<>(this));
    }

    public void render(CustomNPC npc, float p_117789_, float p_117790_, PoseStack p_117791_, MultiBufferSource p_117792_, int p_117793_) {
        this.setModelProperties(npc);
        super.render(npc, p_117789_, p_117790_, p_117791_, p_117792_, p_117793_);
    }

    public Vec3 getRenderOffset(CustomNPC p_117785_, float p_117786_) {
        return p_117785_.isCrouching() ? new Vec3(0.0D, -0.125D, 0.0D) : super.getRenderOffset(p_117785_, p_117786_);
    }

    private void setModelProperties(CustomNPC npc) {
        CustomNPCModel<CustomNPC> npcModel = this.getModel();
        if (npc.isSpectator()) {
            npcModel.setAllVisible(false);
            npcModel.head.visible = true;
            npcModel.hat.visible = true;
        } else {
            npcModel.setAllVisible(true);
            /**
             npcModel.hat.visible = npc.isModelPartShown(PlayerModelPart.HAT);
             npcModel.jacket.visible = npc.isModelPartShown(PlayerModelPart.JACKET);
             npcModel.leftPants.visible = npc.isModelPartShown(PlayerModelPart.LEFT_PANTS_LEG);
             npcModel.rightPants.visible = npc.isModelPartShown(PlayerModelPart.RIGHT_PANTS_LEG);
             npcModel.leftSleeve.visible = npc.isModelPartShown(PlayerModelPart.LEFT_SLEEVE);
             npcModel.rightSleeve.visible = npc.isModelPartShown(PlayerModelPart.RIGHT_SLEEVE);
             **/
            npcModel.crouching = npc.isCrouching();
            HumanoidModel.ArmPose humanoidmodel$armpose = getArmPose(npc, InteractionHand.MAIN_HAND);
            HumanoidModel.ArmPose humanoidmodel$armpose1 = getArmPose(npc, InteractionHand.OFF_HAND);
            if (humanoidmodel$armpose.isTwoHanded()) {
                humanoidmodel$armpose1 = npc.getOffhandItem().isEmpty() ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;
            }

            if (npc.getMainArm() == HumanoidArm.RIGHT) {
                npcModel.rightArmPose = humanoidmodel$armpose;
                npcModel.leftArmPose = humanoidmodel$armpose1;
            } else {
                npcModel.rightArmPose = humanoidmodel$armpose1;
                npcModel.leftArmPose = humanoidmodel$armpose;
            }
        }

    }

    private static HumanoidModel.ArmPose getArmPose(CustomNPC npc, InteractionHand hand) {
        ItemStack itemstack = npc.getItemInHand(hand);
        if (itemstack.isEmpty()) {
            return HumanoidModel.ArmPose.EMPTY;
        } else {
            if (npc.getUsedItemHand() == hand && npc.getUseItemRemainingTicks() > 0) {
                UseAnim useanim = itemstack.getUseAnimation();
                if (useanim == UseAnim.BLOCK) {
                    return HumanoidModel.ArmPose.BLOCK;
                }

                if (useanim == UseAnim.BOW) {
                    return HumanoidModel.ArmPose.BOW_AND_ARROW;
                }

                if (useanim == UseAnim.SPEAR) {
                    return HumanoidModel.ArmPose.THROW_SPEAR;
                }

                if (useanim == UseAnim.CROSSBOW && hand == npc.getUsedItemHand()) {
                    return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
                }

                if (useanim == UseAnim.SPYGLASS) {
                    return HumanoidModel.ArmPose.SPYGLASS;
                }
            } else if (!npc.swinging && itemstack.getItem() instanceof CrossbowItem && CrossbowItem.isCharged(itemstack)) {
                return HumanoidModel.ArmPose.CROSSBOW_HOLD;
            }

            return HumanoidModel.ArmPose.ITEM;
        }
    }

    public ResourceLocation getTextureLocation(CustomNPC npc) {
        return npc.getSkinTextureLocation();
    }

    protected void scale(CustomNPC npc, PoseStack p_117799_, float p_117800_) {
        float f = 0.9375F;
        p_117799_.scale(0.9375F, 0.9375F, 0.9375F);
    }

    protected void renderNameTag(CustomNPC npc, Component component, PoseStack stack, MultiBufferSource buffer, int p_117812_) {
        double d0 = this.entityRenderDispatcher.distanceToSqr(npc);
        stack.pushPose();
        /**
         if (d0 < 100.0D) {
         Scoreboard scoreboard = npc.getScoreboard();
         Objective objective = scoreboard.getDisplayObjective(2);
         if (objective != null) {
         Score score = scoreboard.getOrCreatePlayerScore(npc.getScoreboardName(), objective);
         super.renderNameTag(npc, (new TextComponent(Integer.toString(score.getScore()))).append(" ").append(objective.getDisplayName()), stack, p_117811_, p_117812_);
         stack.translate(0.0D, (double) (9.0F * 1.15F * 0.025F), 0.0D);
         }
         }
         **/
        float f = npc.getBbHeight() + 0.5F;
        int i = "deadmau5".equals(component.getString()) ? -10 : 0;
        stack.pushPose();
        stack.translate(0.0D, (double) f, 0.0D);
        stack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        stack.scale(-0.025F, -0.025F, 0.025F);
        Matrix4f matrix4f = stack.last().pose();
        float f1 = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
        int j = (int) (f1 * 255.0F) << 24;
        Font font = this.getFont();
        float f2 = (float) (-font.width(component) / 2);
        font.drawInBatch(component, f2, (float) i, 553648127, false, matrix4f, buffer, true, j, p_117812_);
        font.drawInBatch(component, f2, (float) i, -1, false, matrix4f, buffer, false, 0, p_117812_);
        stack.popPose();
    }

    public void renderRightHand(PoseStack stack, MultiBufferSource buffer, int p_117773_, CustomNPC npc) {
        this.renderHand(stack, buffer, p_117773_, npc, (this.model).rightArm, (this.model).rightSleeve);
    }

    public void renderLeftHand(PoseStack stack, MultiBufferSource buffer, int p_117816_, CustomNPC npc) {
        this.renderHand(stack, buffer, p_117816_, npc, (this.model).leftArm, (this.model).leftSleeve);
    }

    private void renderHand(PoseStack stack, MultiBufferSource buffer, int p_117778_, CustomNPC npc, ModelPart p_117780_, ModelPart p_117781_) {
        PlayerModel<CustomNPC> playermodel = this.getModel();
        this.setModelProperties(npc);
        playermodel.attackTime = 0.0F;
        playermodel.crouching = false;
        playermodel.swimAmount = 0.0F;
        playermodel.setupAnim(npc, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        p_117780_.xRot = 0.0F;
        p_117780_.render(stack, buffer.getBuffer(RenderType.entitySolid(npc.getSkinTextureLocation())), p_117778_, OverlayTexture.NO_OVERLAY);
        p_117781_.xRot = 0.0F;
        p_117781_.render(stack, buffer.getBuffer(RenderType.entityTranslucent(npc.getSkinTextureLocation())), p_117778_, OverlayTexture.NO_OVERLAY);
    }

    protected void setupRotations(CustomNPC npc, PoseStack stack, float p_117804_, float p_117805_, float p_117806_) {
        float f = npc.getSwimAmount(p_117806_);
        if (npc.isFallFlying()) {
            super.setupRotations(npc, stack, p_117804_, p_117805_, p_117806_);
            float f1 = (float) npc.getFallFlyingTicks() + p_117806_;
            float f2 = Mth.clamp(f1 * f1 / 100.0F, 0.0F, 1.0F);
            if (!npc.isAutoSpinAttack()) {
                stack.mulPose(Vector3f.XP.rotationDegrees(f2 * (-90.0F - npc.getXRot())));
            }

            Vec3 vec3 = npc.getViewVector(p_117806_);
            Vec3 vec31 = npc.getDeltaMovement();
            double d0 = vec31.horizontalDistanceSqr();
            double d1 = vec3.horizontalDistanceSqr();
            if (d0 > 0.0D && d1 > 0.0D) {
                double d2 = (vec31.x * vec3.x + vec31.z * vec3.z) / Math.sqrt(d0 * d1);
                double d3 = vec31.x * vec3.z - vec31.z * vec3.x;
                stack.mulPose(Vector3f.YP.rotation((float) (Math.signum(d3) * Math.acos(d2))));
            }
        } else if (f > 0.0F) {
            super.setupRotations(npc, stack, p_117804_, p_117805_, p_117806_);
            float f3 = npc.isInWater() ? -90.0F - npc.getXRot() : -90.0F;
            float f4 = Mth.lerp(f, 0.0F, f3);
            stack.mulPose(Vector3f.XP.rotationDegrees(f4));
            if (npc.isVisuallySwimming()) {
                stack.translate(0.0D, -1.0D, (double) 0.3F);
            }
        } else {
            super.setupRotations(npc, stack, p_117804_, p_117805_, p_117806_);
        }

    }
}
