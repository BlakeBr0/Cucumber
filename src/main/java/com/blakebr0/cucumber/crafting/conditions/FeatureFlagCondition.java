package com.blakebr0.cucumber.crafting.conditions;

import com.blakebr0.cucumber.util.FeatureFlag;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.conditions.ICondition;

public record FeatureFlagCondition(Identifier flag) implements ICondition {
    public static final MapCodec<FeatureFlagCondition> CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    Identifier.CODEC.fieldOf("flag").forGetter(FeatureFlagCondition::flag)
            ).apply(builder, FeatureFlagCondition::new)
    );

    @Override
    public boolean test(IContext context) {
        var flag = FeatureFlag.from(this.flag);
        return flag.isEnabled();
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
