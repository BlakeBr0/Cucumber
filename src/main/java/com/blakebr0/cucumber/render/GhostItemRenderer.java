package com.blakebr0.cucumber.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL14;

public class GhostItemRenderer {
    public static void renderItemModel(ItemStack stack, MatrixStack matrix, IRenderTypeBuffer buffer, float alpha) {
        if (!stack.isEmpty()) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.getTextureManager().bindTexture(PlayerContainer.BLOCK_ATLAS_TEXTURE);
            minecraft.getTextureManager().getTexture(PlayerContainer.BLOCK_ATLAS_TEXTURE).setBlurMipmapDirect(false, false);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableRescaleNormal();
            RenderSystem.alphaFunc(516, 0.1F);
            RenderSystem.pushMatrix();
            RenderSystem.enableBlend();
            GL14.glBlendColor(1, 1, 1, alpha);
            RenderSystem.blendFunc(GlStateManager.SourceFactor.CONSTANT_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);

            minecraft.getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE, 1, 1, matrix, buffer);

            GL14.glBlendColor(1, 1, 1, 1);
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

//            RenderSystem.cullFace(GlStateManager.CullFace.BACK);
            RenderSystem.popMatrix();
            RenderSystem.disableRescaleNormal();
            RenderSystem.disableBlend();
            minecraft.getTextureManager().bindTexture(PlayerContainer.BLOCK_ATLAS_TEXTURE);
            minecraft.getTextureManager().getTexture(PlayerContainer.BLOCK_ATLAS_TEXTURE).restoreLastBlurMipmap();
        }
    }
}
