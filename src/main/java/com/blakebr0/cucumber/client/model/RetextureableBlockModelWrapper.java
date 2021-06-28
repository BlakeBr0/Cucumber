package com.blakebr0.cucumber.client.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.client.renderer.model.BlockPart;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class RetextureableBlockModelWrapper extends BlockModel {
    private final BlockModel model;

    public RetextureableBlockModelWrapper(BlockModel model) {
        super(model.getParentLocation(), model.getElements(), model.textureMap, model.hasAmbientOcclusion, model.getGuiLight(), model.getTransforms(), model.getOverrides());
        this.model = model;
        this.name = model.name;
        this.parent = model.parent;
    }

    public RetextureableBlockModelWrapper retexture(ImmutableMap<String, String> textures) {
        if (textures.isEmpty())
            return this;

        List<BlockPart> elements = Lists.newArrayList(); //We have to duplicate this so we can edit it below.
        for (BlockPart part : this.model.getElements()) {
            elements.add(new BlockPart(part.from, part.to, Maps.newHashMap(part.faces), part.rotation, part.shade));
        }

        BlockModel newModel = new BlockModel(this.model.getParentLocation(), elements,
                Maps.newHashMap(this.model.textureMap), this.model.hasAmbientOcclusion(), this.model.getGuiLight(), //New Textures man VERY IMPORTANT
                model.getTransforms(), Lists.newArrayList(model.getOverrides()));
        newModel.name = this.model.name;
        newModel.parent = this.model.parent;

        Set<String> removed = Sets.newHashSet();
        for (Map.Entry<String, String> e : textures.entrySet()) {
            if ("".equals(e.getValue())) {
                removed.add(e.getKey());
                newModel.textureMap.remove(e.getKey());
            } else {
                newModel.textureMap.put(e.getKey(), Either.left(new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation(e.getValue()))));
            }
        }

        // Map the model's texture references as if it was the parent of a model with the retexture map as its textures.
        Map<String, Either<RenderMaterial, String>> remapped = Maps.newHashMap();
        for (Map.Entry<String, Either<RenderMaterial, String>> e : newModel.textureMap.entrySet()) {
            Optional<String> right = e.getValue().right();
            if (right.isPresent() && right.get().startsWith("#")) {
                String key = right.get().substring(1);
                if (newModel.textureMap.containsKey(key))
                    remapped.put(e.getKey(), newModel.textureMap.get(key));
            }
        }

        newModel.textureMap.putAll(remapped);

        //Remove any faces that use a null texture, this is for performance reasons, also allows some cool layering stuff.
        for (BlockPart part : newModel.getElements()) {
            part.faces.entrySet().removeIf(entry -> removed.contains(entry.getValue().texture));
        }

        return new RetextureableBlockModelWrapper(newModel);
    }
}
