package com.blakebr0.cucumber.container;

import com.blakebr0.cucumber.inventory.slot.BaseItemStackHandlerSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ExtendedContainerMenu extends BaseContainerMenu {
    protected ExtendedContainerMenu(MenuType<?> menu, int id, BlockPos pos) {
        super(menu, id, pos);
    }

    @Override // copied from Container#moveItemStackTo
    protected boolean moveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean reverse) {
        boolean flag = false;
        int i = startIndex;
        if (reverse) {
            i = endIndex - 1;
        }

        // change: remove ItemStack#isStackable check
        while(!stack.isEmpty()) {
            if (reverse) {
                if (i < startIndex) {
                    break;
                }
            } else if (i >= endIndex) {
                break;
            }

            Slot slot = this.slots.get(i);
            ItemStack itemstack = slot.getItem();
            if (!itemstack.isEmpty() && ItemStack.isSameItemSameTags(stack, itemstack)) {
                int j = itemstack.getCount() + stack.getCount();
                int maxSize = Math.min(slot.getMaxStackSize(), stack.getMaxStackSize());

                // change: account for BaseItemStackHandlerSlot potentially having a larger max stack size
                if (slot instanceof BaseItemStackHandlerSlot) {
                    maxSize = slot.getMaxStackSize(stack);
                }

                if (j <= maxSize) {
                    stack.setCount(0);
                    itemstack.setCount(j);
                    slot.setChanged();
                    flag = true;
                } else if (itemstack.getCount() < maxSize) {
                    stack.shrink(maxSize - itemstack.getCount());
                    itemstack.setCount(maxSize);
                    slot.setChanged();
                    flag = true;
                }
            }

            if (reverse) {
                --i;
            } else {
                ++i;
            }
        }

        if (!stack.isEmpty()) {
            if (reverse) {
                i = endIndex - 1;
            } else {
                i = startIndex;
            }

            while(true) {
                if (reverse) {
                    if (i < startIndex) {
                        break;
                    }
                } else if (i >= endIndex) {
                    break;
                }

                Slot slot1 = this.slots.get(i);
                ItemStack itemstack1 = slot1.getItem();
                if (itemstack1.isEmpty() && slot1.mayPlace(stack)) {
                    // change: account for the fact that items that aren't usually stackable are being stacked by
                    // accounting for the max stack size of the item
                    int maxSize = Math.min(slot1.getMaxStackSize(), stack.getMaxStackSize());

                    // change: break right away here so that it doesn't try to fill additional slots in the inventory
                    if (stack.getCount() > maxSize) {
                        slot1.setByPlayer(stack.split(maxSize));

                        slot1.setChanged();
                        break;
                    } else {
                        slot1.setByPlayer(stack.split(stack.getCount()));
                    }

                    slot1.setChanged();
                    flag = true;
                    break;
                }

                if (reverse) {
                    --i;
                } else {
                    ++i;
                }
            }
        }

        return flag;
    }
}
