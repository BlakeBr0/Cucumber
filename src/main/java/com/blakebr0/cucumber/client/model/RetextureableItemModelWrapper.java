package com.blakebr0.cucumber.client.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class RetextureableItemModelWrapper extends BlockModel {
    private final BlockModel model;

    public RetextureableItemModelWrapper(BlockModel model) {
        super(model.getParentLocation(), model.getElements(), model.textures, model.ambientOcclusion, model.getGuiLight(), model.getAllTransforms(), model.getOverrides());
        this.model = model;
        this.name = model.name;
        this.parent = model.parent;
    }

    public RetextureableItemModelWrapper retexture(ImmutableMap<String, String> textures) {
        BlockModel newModel = new BlockModel(
                this.model.getParentLocation(),
                new ArrayList<>(),
                Maps.newHashMap(this.model.textures),
                this.model.isAmbientOcclusion(),
                this.model.getGuiLight(),
                this.model.getAllTransforms(),
                Lists.newArrayList(this.model.getOverrides())
        );
        newModel.name = this.model.name;
        newModel.parent = this.model.parent;

        textures.forEach((key, value) -> {
            newModel.textures.put(key, Either.left(new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation(value))));
        });

        return new RetextureableItemModelWrapper(newModel);
    }
}
