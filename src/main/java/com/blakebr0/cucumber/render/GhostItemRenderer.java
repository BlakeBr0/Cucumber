package com.blakebr0.cucumber.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL14;

public class GhostItemRenderer {
    // TODO: IMPLEMENT
    public static void renderItemModel(Minecraft mc, ItemStack stack, float alpha) {
        if (!stack.isEmpty()) {
//            mc.getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
//            mc.getTextureManager().func_229267_b_(AtlasTexture.LOCATION_BLOCKS_TEXTURE).setBlurMipmapDirect(false, false);
//            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//            RenderSystem.enableRescaleNormal();
//            RenderSystem.alphaFunc(516, 0.1F);
//            RenderSystem.pushMatrix();
//
//            IBakedModel bakedmodel = mc.getItemRenderer().getItemModelWithOverrides(stack, null, null);
//            bakedmodel = ForgeHooksClient.handleCameraTransforms(bakedmodel, ItemCameraTransforms.TransformType.NONE, false);
//
//            RenderSystem.enableBlend();
//            GL14.glBlendColor(1, 1, 1, alpha);
//            RenderSystem.blendFunc(GlStateManager.SourceFactor.CONSTANT_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
//
//            mc.getItemRenderer().renderItem(stack, bakedmodel);
////            mc.getItemRenderer().func_229110_a_(stack, ItemCameraTransforms.TransformType.NONE, );
//
//            GL14.glBlendColor(1, 1, 1, 1);
//            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//
//            GlStateManager.cullFace(GlStateManager.CullFace.BACK);
//            RenderSystem.popMatrix();
//            RenderSystem.disableRescaleNormal();
//            RenderSystem.disableBlend();
//            mc.getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
//            mc.getTextureManager().func_229267_b_(AtlasTexture.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
        }
    }
}
