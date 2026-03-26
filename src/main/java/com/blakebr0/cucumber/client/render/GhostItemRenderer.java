package com.blakebr0.cucumber.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class GhostItemRenderer {
    // TODO reevaluate again!!!!
    public static void renderItemIntoGui(ItemStack stack, int x, int y, Object itemRenderer) {
//        renderItemModelIntoGUI(stack, x, y, itemRenderer.getModel(stack, null, null, 0), itemRenderer);
    }

    // TODO reevaluate AGAIN
    /**
     * Copied from ItemRenderer#render(ItemStack, ItemTransforms.TransformType, boolean, PoseStack, MultiBufferSource, int, int, BakedModel)
     */
    private static void renderItem(ItemStack itemStackIn, ItemDisplayContext transformTypeIn, boolean leftHand, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn, Object modelIn, Object itemRenderer) {
//        if (!itemStackIn.isEmpty()) {
//            matrixStackIn.pushPose();
//            boolean flag = transformTypeIn == ItemDisplayContext.GUI || transformTypeIn == ItemDisplayContext.GROUND || transformTypeIn == ItemDisplayContext.FIXED;
//            if (flag) {
//                if (itemStackIn.is(Items.TRIDENT)) {
//                    // NOTE: constant is private
//                    modelIn = itemRenderer.getItemModelShaper().getModelManager().getModel(ModelIdentifier.vanilla("trident", "#inventory"));
//                } else if (itemStackIn.is(Items.SPYGLASS)) {
//                    // NOTE: constant is private
//                    modelIn = itemRenderer.getItemModelShaper().getModelManager().getModel(ModelIdentifier.vanilla("spyglass", "inventory"));
//                }
//            }
//
//            modelIn = net.neoforged.neoforge.client.ClientHooks.handleCameraTransforms(matrixStackIn, modelIn, transformTypeIn, leftHand);
//            matrixStackIn.translate(-0.5F, -0.5F, -0.5F);
//            if (!modelIn.isCustomRenderer() && (!itemStackIn.is(Items.TRIDENT) || flag)) {
//                boolean flag1;
//                if (transformTypeIn != ItemDisplayContext.GUI && !transformTypeIn.firstPerson() && itemStackIn.getItem() instanceof BlockItem blockitem) {
//                    Block block = blockitem.getBlock();
//                    flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
//                } else {
//                    flag1 = true;
//                }
//
//                for (var model : modelIn.getRenderPasses(itemStackIn, flag1)) {
//                    for (var rendertype : model.getRenderTypes(itemStackIn, flag1)) {
//                        // CHANGE: use ghost render type for all layers
//                        rendertype = ModRenderTypes.GHOST;
//                        VertexConsumer vertexconsumer;
//                        if (hasAnimatedTexture(itemStackIn) && itemStackIn.hasFoil()) {
//                            PoseStack.Pose posestack$pose = matrixStackIn.last().copy();
//                            if (transformTypeIn == ItemDisplayContext.GUI) {
//                                MatrixUtil.mulComponentWise(posestack$pose.pose(), 0.5F);
//                            } else if (transformTypeIn.firstPerson()) {
//                                MatrixUtil.mulComponentWise(posestack$pose.pose(), 0.75F);
//                            }
//
//                            vertexconsumer = ItemRenderer.getCompassFoilBuffer(bufferIn, rendertype, posestack$pose);
//                        } else if (flag1) {
//                            vertexconsumer = ItemRenderer.getFoilBufferDirect(bufferIn, rendertype, true, itemStackIn.hasFoil());
//                        } else {
//                            vertexconsumer = ItemRenderer.getFoilBuffer(bufferIn, rendertype, true, itemStackIn.hasFoil());
//                        }
//
//                        itemRenderer.renderModelLists(model, itemStackIn, combinedLightIn, combinedOverlayIn, matrixStackIn, vertexconsumer);
//                    }
//                }
//            } else {
//                net.neoforged.neoforge.client.extensions.common.IClientItemExtensions.of(itemStackIn).getCustomRenderer().renderByItem(itemStackIn, transformTypeIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
//            }
//
//            matrixStackIn.popPose();
//        }
    }

    /**
     * Copied from ItemRenderer#hasAnimatedTexture(ItemStack)
     */
    private static boolean hasAnimatedTexture(ItemStack p_286353_) {
        return p_286353_.is(ItemTags.COMPASSES) || p_286353_.is(Items.CLOCK);
    }

    // TODO reevaluate AGAIN
    /**
     * Copied from ItemRenderer#renderGuiItem(ItemStack, int, int, BakedModel)
     */
    private static void renderItemModelIntoGUI(ItemStack stack, int x, int y, Object bakedmodel, Object itemRenderer) {
//        Minecraft.getInstance().getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
//        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
//        RenderSystem.defaultBlendFunc();
//        RenderSystem.enableBlend();
//        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        var posestack = RenderSystem.getModelViewStack();
//        posestack.pushMatrix();
//        posestack.translate((float)x, (float)y, 100.0F);
//        posestack.translate(8.0F, 8.0F, 0.0F);
//        posestack.mul((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
//        posestack.scale(16.0F, 16.0F, 16.0F);
//        RenderSystem.applyModelViewMatrix();
//        PoseStack posestack1 = new PoseStack();
//        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
//        boolean flag = !bakedmodel.usesBlockLight();
//        if (flag) {
//            Lighting.setupForFlatItems();
//        }
//
//        // CHANGE: use render function from this class
//        renderItem(stack, ItemDisplayContext.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, bakedmodel, itemRenderer);
//
//        multibuffersource$buffersource.endBatch();
//        RenderSystem.enableDepthTest();
//        if (flag) {
//            Lighting.setupFor3DItems();
//        }
//
//        posestack.popMatrix();
//        RenderSystem.applyModelViewMatrix();
    }
}
