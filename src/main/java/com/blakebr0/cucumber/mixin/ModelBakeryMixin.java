package com.blakebr0.cucumber.mixin;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.event.RegisterClientItemsEvent;
import com.google.common.base.Stopwatch;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.PlayerSkinRenderCache;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.entity.animation.json.AnimationLoader;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelLoader;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Mixin(ModelBakery.class)
public class ModelBakeryMixin {
    @Inject(
            at = @At("RETURN"),
            method = "<init>(Lnet/minecraft/client/model/geom/EntityModelSet;Lnet/minecraft/client/resources/model/sprite/SpriteGetter;Lnet/minecraft/client/renderer/PlayerSkinRenderCache;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Lnet/minecraft/client/resources/model/ResolvedModel;Lnet/neoforged/neoforge/client/model/standalone/StandaloneModelLoader$LoadedModels;Lnet/neoforged/neoforge/client/entity/animation/json/AnimationLoader$PendingAnimations;)V"
    )
    public void cucumber$constructor(EntityModelSet entityModelSet, SpriteGetter sprites, PlayerSkinRenderCache playerSkinRenderCache, Map unbakedBlockStateModels, Map<Identifier, ClientItem> clientInfos, Map resolvedModels, ResolvedModel missingModel, StandaloneModelLoader.LoadedModels standaloneModels, AnimationLoader.PendingAnimations pendingAnimations, CallbackInfo ci) {
        var stopwatch = Stopwatch.createStarted();
        Map<Identifier, ClientItem> items = new HashMap<>();

        try {
            NeoForge.EVENT_BUS.post(new RegisterClientItemsEvent(clientInfos, items));
        } catch (Exception e) {
            Cucumber.LOGGER.error("An error occurred while firing RegisterClientItemsEvent", e);
        }

        clientInfos.putAll(items);

        Cucumber.LOGGER.info("Registered {} client items in {} ms", items.size(), stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
    }
}
