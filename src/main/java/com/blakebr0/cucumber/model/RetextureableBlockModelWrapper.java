package com.blakebr0.cucumber.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.client.renderer.model.BlockPart;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RetextureableBlockModelWrapper extends BlockModel {
    private final BlockModel model;

    public RetextureableBlockModelWrapper(BlockModel model) {
        super(model.getParentLocation(), model.getElements(), model.textures, model.ambientOcclusion, model.isGui3d(), model.getAllTransforms(), model.getOverrides());
        this.model = model;
        this.name = model.name;
        this.parent = model.parent;
    }

    // TODO: IMPLEMENT
//    // Yoinked from VanillaModelWrapper
//    @Override
//    public RetextureableBlockModelWrapper retexture(ImmutableMap<String, String> textures) {
//        if (textures.isEmpty())
//            return this;
//
//        List<BlockPart> elements = Lists.newArrayList(); //We have to duplicate this so we can edit it below.
//        for (BlockPart part : this.model.getElements()) {
//            elements.add(new BlockPart(part.positionFrom, part.positionTo, Maps.newHashMap(part.mapFaces), part.partRotation, part.shade));
//        }
//
//        BlockModel newModel = new BlockModel(this.model.getParentLocation(), elements,
//                Maps.newHashMap(this.model.textures), this.model.isAmbientOcclusion(), this.model.isGui3d(), //New Textures man VERY IMPORTANT
//                model.getAllTransforms(), Lists.newArrayList(model.getOverrides()));
//        newModel.name = this.model.name;
//        newModel.parent = this.model.parent;
//
//        Set<String> removed = Sets.newHashSet();
//        for (Map.Entry<String, String> e : textures.entrySet()) {
//            if ("".equals(e.getValue())) {
//                removed.add(e.getKey());
//                newModel.textures.remove(e.getKey());
//            } else {
//                newModel.textures.put(e.getKey(), e.getValue());
//            }
//        }
//
//        // Map the model's texture references as if it was the parent of a model with the retexture map as its textures.
//        Map<String, String> remapped = Maps.newHashMap();
//        for (Map.Entry<String, String> e : newModel.textures.entrySet()) {
//            if (e.getValue().startsWith("#")) {
//                String key = e.getValue().substring(1);
//                if (newModel.textures.containsKey(key))
//                    remapped.put(e.getKey(), newModel.textures.get(key));
//            }
//        }
//
//        newModel.textures.putAll(remapped);
//
//        //Remove any faces that use a null texture, this is for performance reasons, also allows some cool layering stuff.
//        for (BlockPart part : newModel.getElements()) {
//            part.mapFaces.entrySet().removeIf(entry -> removed.contains(entry.getValue().texture));
//        }
//
//        return new RetextureableBlockModelWrapper(newModel);
//    }
}
