package com.blakebr0.cucumber.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

public final class ModRenderTypes {
    public static final RenderType GHOST = RenderType.create(
            "cucumber:ghost",
            DefaultVertexFormat.BLOCK, GL11.GL_QUADS, 256,
            RenderType.CompositeState.builder()
                    .setTextureState(new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_BLOCKS, false, false))
                    .setAlphaState(new RenderStateShard.AlphaStateShard(0.5F) {
                        @Override
                        public void setupRenderState() {
                            RenderSystem.pushMatrix();
                            RenderSystem.color4f(1F, 1F, 1F, 1F);
                            GlStateManager._enableBlend();
                            GL14.glBlendColor(1.0F, 1.0F, 1.0F, 0.25F);
                            GlStateManager._blendFunc(GlStateManager.SourceFactor.CONSTANT_ALPHA.value, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA.value);
                        }

                        @Override
                        public void clearRenderState() {
                            GL14.glBlendColor(1.0F, 1.0F, 1.0F, 1.0F);
                            GlStateManager._blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.value, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.value);
                            RenderSystem.disableBlend();
                            RenderSystem.popMatrix();
                        }
                    })
                    .createCompositeState(false));
}
