package com.blakebr0.cucumber.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.model.BlockModel;

import java.util.ArrayList;

public class RetextureableItemModelWrapper extends BlockModel {
    private final BlockModel model;

    public RetextureableItemModelWrapper(BlockModel model) {
        super(model.getParentLocation(), model.getElements(), model.textures, model.ambientOcclusion, model.isGui3d(), model.getAllTransforms(), model.getOverrides());
        this.model = model;
        this.name = model.name;
        this.parent = model.parent;
    }

    // Yoinked from VanillaModelWrapper
    @Override
    public RetextureableBlockModelWrapper retexture(ImmutableMap<String, String> textures) {
        BlockModel newModel = new BlockModel(this.model.getParentLocation(), new ArrayList<>(),
                Maps.newHashMap(this.model.textures), this.model.isAmbientOcclusion(), this.model.isGui3d(), //New Textures man VERY IMPORTANT
                model.getAllTransforms(), Lists.newArrayList(model.getOverrides()));
        newModel.name = this.model.name;
        newModel.parent = this.model.parent;

        textures.forEach(newModel.textures::put);

        return new RetextureableBlockModelWrapper(newModel);
    }
}
