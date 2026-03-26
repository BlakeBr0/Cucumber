package com.blakebr0.cucumber.client;

import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

// TODO figure this out
public final class ModRenderTypes {
//    private static final net.minecraft.client.renderer.rendertype.RenderType.TransparencyStateShard GHOST_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("ghost_transparency",
//            () -> {
//                RenderSystem.enableBlend();
//                RenderSystem.blendFunc(GlStateManager.SourceFactor.CONSTANT_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
//                GL14.glBlendColor(1.0F, 1.0F, 1.0F, 0.25F);
//            },
//            () -> {
//                GL14.glBlendColor(1.0F, 1.0F, 1.0F, 1.0F);
//                RenderSystem.disableBlend();
//                RenderSystem.defaultBlendFunc();
//            });
//
//    public static final RenderType GHOST = RenderType.create(
//            "cucumber:ghost",
//            DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 2097152, true, false,
//            RenderType.CompositeState.builder()
//                    .setShaderState(RenderType.POSITION_COLOR_TEX_LIGHTMAP_SHADER)
//                    .setTextureState(RenderType.BLOCK_SHEET)
//                    .setTransparencyState(GHOST_TRANSPARENCY)
//                    .setDepthTestState(RenderType.NO_DEPTH_TEST)
//                    .createCompositeState(false)
//    );

    private static final Function<Identifier, RenderType> ENTITY_GHOST = Util.memoize(
            texture -> {
                RenderSetup state = RenderSetup.builder(RenderPipelines.ENTITY_TRANSLUCENT)
                        .withTexture("Sampler0", texture)
                        .useLightmap()
                        .useOverlay()
                        .affectsCrumbling()
                        .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
                        .createRenderSetup();
                return RenderType.create("cucumber:entity_ghost", state);
            }
    );

    public RenderType ghost(Identifier texture) {
        return ENTITY_GHOST.apply(texture);
    }
}
