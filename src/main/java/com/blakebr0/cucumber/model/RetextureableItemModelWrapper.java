package com.blakebr0.cucumber.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class RetextureableItemModelWrapper extends BlockModel {
    private final BlockModel model;

    public RetextureableItemModelWrapper(BlockModel model) {
        super(model.getParentLocation(), model.getElements(), model.textures, model.ambientOcclusion, model.func_230176_c_(), model.getAllTransforms(), model.getOverrides());
        this.model = model;
        this.name = model.name;
        this.parent = model.parent;
    }

    // TODO: 1.16: Fix this to return RetexturableItemModelWrapper
    public RetextureableBlockModelWrapper retexture(ImmutableMap<String, String> textures) {
        BlockModel newModel = new BlockModel(
                this.model.getParentLocation(),
                new ArrayList<>(),
                Maps.newHashMap(this.model.textures),
                this.model.isAmbientOcclusion(),
                this.model.func_230176_c_(),
                this.model.getAllTransforms(),
                Lists.newArrayList(this.model.getOverrides())
        );
        newModel.name = this.model.name;
        newModel.parent = this.model.parent;

        textures.forEach((key, value) -> {
            newModel.textures.put(key, Either.left(new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation(value))));
        });

        return new RetextureableBlockModelWrapper(newModel);
    }
}
