package com.blakebr0.cucumber.client.render;

import com.blakebr0.cucumber.client.ModRenderTypes;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;

// TODO: 1.16: reevaluate, reimplement
public final class GhostItemRenderer {
    public static void renderItemModel(ItemStack stack, PoseStack matrix, MultiBufferSource buffer, float alpha) {
        if (!stack.isEmpty()) {
//            Minecraft minecraft = Minecraft.getInstance();
//            minecraft.getTextureManager().bind(TextureAtlas.LOCATION_BLOCKS);
//            minecraft.getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS).setBlurMipmap(false, false);
//            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//            RenderSystem.enableRescaleNormal();
//            RenderSystem.alphaFunc(516, 0.1F);
//            RenderSystem.pushMatrix();
//            RenderSystem.enableBlend();
//            GL14.glBlendColor(1, 1, 1, alpha);
//            RenderSystem.blendFunc(GlStateManager.SourceFactor.CONSTANT_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
//
//            minecraft.getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.NONE, 1, 1, matrix, buffer);
//
//            GL14.glBlendColor(1, 1, 1, 1);
//            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//
////            RenderSystem.cullFace(GlStateManager.CullFace.BACK);
//            RenderSystem.popMatrix();
//            RenderSystem.disableRescaleNormal();
//            RenderSystem.disableBlend();
//            minecraft.getTextureManager().bind(TextureAtlas.LOCATION_BLOCKS);
//            minecraft.getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS).restoreLastBlurMipmap();
        }
    }

    public static void renderItemIntoGui(ItemStack stack, int x, int y, ItemRenderer itemRenderer) {
        renderItemModelIntoGUI(stack, x, y, itemRenderer.getModel(stack, null, null, 0), itemRenderer);
    }

    private static void renderItem(ItemStack itemStackIn, ItemTransforms.TransformType transformTypeIn, boolean leftHand, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn, BakedModel modelIn, ItemRenderer itemRenderer) {
        if (!itemStackIn.isEmpty()) {
            matrixStackIn.pushPose();
            boolean flag = transformTypeIn == ItemTransforms.TransformType.GUI || transformTypeIn == ItemTransforms.TransformType.GROUND || transformTypeIn == ItemTransforms.TransformType.FIXED;
            if (flag) {
                if (itemStackIn.is(Items.TRIDENT)) {
                    modelIn = itemRenderer.getItemModelShaper().getModelManager().getModel(new ModelResourceLocation("minecraft:trident#inventory"));
                } else if (itemStackIn.is(Items.SPYGLASS)) {
                    modelIn = itemRenderer.getItemModelShaper().getModelManager().getModel(new ModelResourceLocation("minecraft:spyglass#inventory"));
                }
            }

            modelIn = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(matrixStackIn, modelIn, transformTypeIn, leftHand);
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            if (!modelIn.isCustomRenderer() && (!itemStackIn.is(Items.TRIDENT) || flag)) {
                boolean flag1;
                if (transformTypeIn != ItemTransforms.TransformType.GUI && !transformTypeIn.firstPerson() && itemStackIn.getItem() instanceof BlockItem) {
                    Block block = ((BlockItem)itemStackIn.getItem()).getBlock();
                    flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
                } else {
                    flag1 = true;
                }
                if (modelIn.isLayered()) { net.minecraftforge.client.ForgeHooksClient.drawItemLayered(itemRenderer, modelIn, itemStackIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, flag1); }
                else {
                    RenderType rendertype = ModRenderTypes.GHOST;
                    VertexConsumer vertexconsumer;
                    if (itemStackIn.is(Items.COMPASS) && itemStackIn.hasFoil()) {
                        matrixStackIn.pushPose();
                        PoseStack.Pose posestack$pose = matrixStackIn.last();
                        if (transformTypeIn == ItemTransforms.TransformType.GUI) {
                            posestack$pose.pose().multiply(0.5F);
                        } else if (transformTypeIn.firstPerson()) {
                            posestack$pose.pose().multiply(0.75F);
                        }

                        if (flag1) {
                            vertexconsumer = ItemRenderer.getCompassFoilBufferDirect(bufferIn, rendertype, posestack$pose);
                        } else {
                            vertexconsumer = ItemRenderer.getCompassFoilBuffer(bufferIn, rendertype, posestack$pose);
                        }

                        matrixStackIn.popPose();
                    } else if (flag1) {
                        vertexconsumer = ItemRenderer.getFoilBufferDirect(bufferIn, rendertype, true, itemStackIn.hasFoil());
                    } else {
                        vertexconsumer = ItemRenderer.getFoilBuffer(bufferIn, rendertype, true, itemStackIn.hasFoil());
                    }

                    itemRenderer.renderModelLists(modelIn, itemStackIn, combinedLightIn, combinedOverlayIn, matrixStackIn, vertexconsumer);
                }
            } else {
                net.minecraftforge.client.RenderProperties.get(itemStackIn).getItemStackRenderer().renderByItem(itemStackIn, transformTypeIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
            }

            matrixStackIn.popPose();
        }
    }

    private static void renderItemModelIntoGUI(ItemStack stack, int x, int y, BakedModel bakedmodel, ItemRenderer itemRenderer) {
        Minecraft.getInstance().getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate((double)x, (double)y, (double)(100.0F + itemRenderer.blitOffset));
        posestack.translate(8.0D, 8.0D, 0.0D);
        posestack.scale(1.0F, -1.0F, 1.0F);
        posestack.scale(16.0F, 16.0F, 16.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        boolean flag = !bakedmodel.usesBlockLight();
        if (flag) {
            Lighting.setupForFlatItems();
        }

        renderItem(stack, ItemTransforms.TransformType.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, bakedmodel, itemRenderer);

        multibuffersource$buffersource.endBatch();
        RenderSystem.enableDepthTest();
        if (flag) {
            Lighting.setupFor3DItems();
        }

        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
    }
}
