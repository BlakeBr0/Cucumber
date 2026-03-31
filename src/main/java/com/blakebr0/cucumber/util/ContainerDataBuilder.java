package com.blakebr0.cucumber.util;

import net.minecraft.world.inventory.ContainerData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public class ContainerDataBuilder {
    private final List<DataSlotGetterSetter> data;

    private ContainerDataBuilder() {
        this.data = new ArrayList<>();
    }

    public static ContainerDataBuilder builder() {
        return new ContainerDataBuilder();
    }

    public ContainerDataBuilder sync(IntSupplier getter) {
        return this.sync(getter, _ -> {});
    }

    public ContainerDataBuilder sync(IntSupplier getter, IntConsumer setter) {
        this.data.add(new DataSlotGetterSetter(getter, setter));
        return this;
    }

    public ContainerData build() {
        return new ContainerData() {
            private final List<DataSlotGetterSetter> data = ContainerDataBuilder.this.data;

            @Override
            public int get(int index) {
                return this.data.get(index).getter.getAsInt();
            }

            @Override
            public void set(int index, int value) {
                this.data.get(index).setter.accept(value);
            }

            @Override
            public int getCount() {
                return this.data.size();
            }
        };
    }

    record DataSlotGetterSetter(IntSupplier getter, IntConsumer setter) { }
}
