package com.blakebr0.cucumber.item;

import com.blakebr0.cucumber.iface.IEnableable;

import java.util.function.Function;
import java.util.function.Supplier;

public class BaseEnableableItem extends BaseItem implements IEnableable {
    private final Supplier<Boolean> condition;

    public BaseEnableableItem(Supplier<Boolean> condition, Function<Properties, Properties> properties) {
        super(properties);
        this.condition = condition;
    }

    @Override
    public boolean isEnabled() {
        return this.condition.get();
    }
}
