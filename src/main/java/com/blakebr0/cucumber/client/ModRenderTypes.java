package com.blakebr0.cucumber.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import org.lwjgl.opengl.GL14;

public final class ModRenderTypes extends RenderType {
    public static final RenderType GHOST = RenderType.create(
        "cucumber:ghost",
        DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 2097152, true, false,
        RenderType.CompositeState.builder()
                .setLightmapState(LIGHTMAP)
                .setShaderState(RENDERTYPE_SOLID_SHADER)
                .setTextureState(BLOCK_SHEET)
                .setTransparencyState(new RenderStateShard.TransparencyStateShard("ghost_transparency",
                        () -> {
                            RenderSystem.enableBlend();
                            RenderSystem.blendFunc(GlStateManager.SourceFactor.CONSTANT_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
                            GL14.glBlendColor(1.0F, 1.0F, 1.0F, 0.25F);
                        },
                        () -> {
                            GL14.glBlendColor(1.0F, 1.0F, 1.0F, 1.0F);
                            RenderSystem.disableBlend();
                            RenderSystem.defaultBlendFunc();
                        }
                ))
                .createCompositeState(false)
    );

    // unused, just needed to extend RenderType for protected constants
    private ModRenderTypes(String p_173178_, VertexFormat p_173179_, VertexFormat.Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
        super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
    }
}
