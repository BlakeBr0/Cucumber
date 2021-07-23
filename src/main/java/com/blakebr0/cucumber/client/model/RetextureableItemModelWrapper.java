package com.blakebr0.cucumber.client.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

public class RetextureableItemModelWrapper extends BlockModel {
    private final BlockModel model;

    public RetextureableItemModelWrapper(BlockModel model) {
        super(model.getParentLocation(), model.getElements(), model.textureMap, model.hasAmbientOcclusion, model.getGuiLight(), model.getTransforms(), model.getOverrides());
        this.model = model;
        this.name = model.name;
        this.parent = model.parent;
    }

    public RetextureableItemModelWrapper retexture(ImmutableMap<String, String> textures) {
        BlockModel newModel = new BlockModel(
                this.model.getParentLocation(),
                new ArrayList<>(),
                Maps.newHashMap(this.model.textureMap),
                this.model.hasAmbientOcclusion(),
                this.model.getGuiLight(),
                this.model.getTransforms(),
                Lists.newArrayList(this.model.getOverrides())
        );
        newModel.name = this.model.name;
        newModel.parent = this.model.parent;

        textures.forEach((key, value) -> {
            newModel.textureMap.put(key, Either.left(new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(value))));
        });

        return new RetextureableItemModelWrapper(newModel);
    }
}
