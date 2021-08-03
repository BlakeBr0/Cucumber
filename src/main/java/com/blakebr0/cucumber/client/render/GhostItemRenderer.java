package com.blakebr0.cucumber.client.render;

import com.blakebr0.cucumber.client.ModRenderTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.Direction;
import org.lwjgl.opengl.GL14;

import java.util.Random;

// TODO: 1.16: reevaluate, reimplement
public final class GhostItemRenderer {
//    public static void renderItemModel(ItemStack stack, PoseStack matrix, MultiBufferSource buffer, float alpha) {
//        if (!stack.isEmpty()) {
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
//        }
//    }
//
//    public static void renderItemIntoGui(ItemStack stack, int x, int y, ItemRenderer itemRenderer) {
//        renderItemModelIntoGUI(stack, x, y, itemRenderer.getModel(stack, null, null), itemRenderer);
//    }
//
//    private static void renderModel(BakedModel modelIn, ItemStack stack, int combinedLightIn, int combinedOverlayIn, PoseStack matrixStackIn, VertexConsumer bufferIn, ItemRenderer itemRenderer) {
//        Random random = new Random();
//
//        for (Direction direction : Direction.values()) {
//            random.setSeed(42L);
//            itemRenderer.renderQuadList(matrixStackIn, bufferIn, modelIn.getQuads(null, direction, random), stack, combinedLightIn, combinedOverlayIn);
//        }
//
//        random.setSeed(42L);
//        itemRenderer.renderQuadList(matrixStackIn, bufferIn, modelIn.getQuads(null, null, random), stack, combinedLightIn, combinedOverlayIn);
//    }
//
//    private static void renderItem(ItemStack itemStackIn, ItemTransforms.TransformType transformTypeIn, boolean leftHand, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn, BakedModel modelIn, ItemRenderer itemRenderer) {
//        if (!itemStackIn.isEmpty()) {
//            matrixStackIn.pushPose();
//            boolean flag = transformTypeIn == ItemTransforms.TransformType.GUI;
//            boolean flag1 = flag || transformTypeIn == ItemTransforms.TransformType.GROUND || transformTypeIn == ItemTransforms.TransformType.FIXED;
//            if (itemStackIn.getItem() == Items.TRIDENT && flag1) {
//                modelIn = itemRenderer.getItemModelShaper().getModelManager().getModel(new ModelResourceLocation("minecraft:trident#inventory"));
//            }
//
//            modelIn = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(matrixStackIn, modelIn, transformTypeIn, leftHand);
//            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
//            if (!modelIn.isCustomRenderer() && (itemStackIn.getItem() != Items.TRIDENT || flag1)) {
//                VertexConsumer ivertexbuilder = bufferIn.getBuffer(ModRenderTypes.GHOST);
//                renderModel(modelIn, itemStackIn, combinedLightIn, combinedOverlayIn, matrixStackIn, ivertexbuilder, itemRenderer);
//            } else {
//                itemStackIn.getItem().getItemStackTileEntityRenderer().renderByItem(itemStackIn, transformTypeIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
//            }
//
//            matrixStackIn.popPose();
//        }
//    }
//
//    private static void renderItemModelIntoGUI(ItemStack stack, int x, int y, BakedModel bakedmodel, ItemRenderer itemRenderer) {
//        RenderSystem.pushMatrix();
//        Minecraft.getInstance().getTextureManager().bind(TextureAtlas.LOCATION_BLOCKS);
//        Minecraft.getInstance().getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS).setBlurMipmap(false, false);
//        RenderSystem.enableRescaleNormal();
//        RenderSystem.enableAlphaTest();
//        RenderSystem.defaultAlphaFunc();
//        RenderSystem.enableBlend();
//        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//        RenderSystem.translatef((float)x, (float)y, 100.0F + itemRenderer.blitOffset);
//        RenderSystem.translatef(8.0F, 8.0F, 0.0F);
//        RenderSystem.scalef(1.0F, -1.0F, 1.0F);
//        RenderSystem.scalef(16.0F, 16.0F, 16.0F);
//        PoseStack matrixstack = new PoseStack();
//        MultiBufferSource.BufferSource irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
//        boolean flag = !bakedmodel.usesBlockLight();
//        if (flag) {
//            com.mojang.blaze3d.platform.Lighting.setupForFlatItems();
//        }
//
//        renderItem(stack, ItemTransforms.TransformType.GUI, false, matrixstack, irendertypebuffer$impl, 15728880, OverlayTexture.NO_OVERLAY, bakedmodel, itemRenderer);
//        irendertypebuffer$impl.endBatch();
//        RenderSystem.enableDepthTest();
//        if (flag) {
//            com.mojang.blaze3d.platform.Lighting.setupFor3DItems();
//        }
//
//        RenderSystem.disableAlphaTest();
//        RenderSystem.disableRescaleNormal();
//        RenderSystem.popMatrix();
//    }
}
