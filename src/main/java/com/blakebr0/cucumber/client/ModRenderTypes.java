package com.blakebr0.cucumber.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import org.lwjgl.opengl.GL14;

public final class ModRenderTypes {
    private static final RenderType.TransparencyStateShard GHOST_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("ghost_transparency",
            () -> {
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GlStateManager.SourceFactor.CONSTANT_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
                GL14.glBlendColor(1.0F, 1.0F, 1.0F, 0.25F);
            },
            () -> {
                GL14.glBlendColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.disableBlend();
                RenderSystem.defaultBlendFunc();
            });

    public static final RenderType GHOST = RenderType.create(
        "cucumber:ghost",
        DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 2097152, true, false,
        RenderType.CompositeState.builder()
                .setShaderState(RenderType.POSITION_COLOR_TEX_LIGHTMAP_SHADER)
                .setTextureState(RenderType.BLOCK_SHEET)
                .setTransparencyState(GHOST_TRANSPARENCY)
                .setDepthTestState(RenderType.NO_DEPTH_TEST)
                .createCompositeState(false)
    );
}
