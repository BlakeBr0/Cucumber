package com.blakebr0.cucumber.client;

import com.blakebr0.cucumber.Cucumber;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;

import java.util.function.Function;

public final class ModRenderTypes {
    private static final RenderPipeline GHOST_BLOCK_PIPELINE = RenderPipeline.builder(RenderPipelines.BLOCK_SNIPPET)
            .withLocation(Cucumber.resource("pipelines/ghost_block"))
            .withShaderDefine("ALPHA_CUTOUT", 0.01F)
            .withVertexShader(Cucumber.resource("ghost_block"))
            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
            .withDepthStencilState(DepthStencilState.DEFAULT)
            .build();
    private static final Function<Identifier, RenderType> GHOST_BLOCK = Util.memoize(
            texture -> {
                RenderSetup state = RenderSetup.builder(GHOST_BLOCK_PIPELINE)
                        .withTexture("Sampler0", texture)
                        .useLightmap()
                        .useOverlay()
                        .affectsCrumbling()
                        .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
                        .createRenderSetup();
                return RenderType.create("cucumber:ghost_block", state);
            }
    );

    public static RenderType ghostBlock() {
        return GHOST_BLOCK.apply(TextureAtlas.LOCATION_BLOCKS);
    }

    @SubscribeEvent
    public void onRegisterRenderPipelines(RegisterRenderPipelinesEvent event) {
        event.registerPipeline(GHOST_BLOCK_PIPELINE);
    }
}
