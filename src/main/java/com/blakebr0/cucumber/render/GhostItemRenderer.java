package com.blakebr0.cucumber.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL14;

public class GhostItemRenderer {
    public static void renderItemModel(Minecraft mc, ItemStack stack, float alpha) {
        if (!stack.isEmpty()) {
            mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableRescaleNormal();
            GlStateManager.alphaFunc(516, 0.1F);
            GlStateManager.pushMatrix();
            
            IBakedModel bakedmodel = mc.getItemRenderer().getItemModelWithOverrides(stack, null, null);
            bakedmodel = ForgeHooksClient.handleCameraTransforms(bakedmodel, ItemCameraTransforms.TransformType.NONE, false);

            GlStateManager.enableBlend();
            GL14.glBlendColor(1, 1, 1, alpha);
            GlStateManager.blendFunc(SourceFactor.CONSTANT_ALPHA, DestFactor.ONE_MINUS_CONSTANT_ALPHA);

            mc.getItemRenderer().renderItem(stack, bakedmodel);

            GL14.glBlendColor(1, 1, 1, 1);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            
            GlStateManager.cullFace(GlStateManager.CullFace.BACK);
            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
            mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
        }
    }
}
