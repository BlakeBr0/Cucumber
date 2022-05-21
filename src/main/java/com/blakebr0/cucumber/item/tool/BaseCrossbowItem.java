package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.iface.ICustomBow;
import com.blakebr0.cucumber.iface.IEnableable;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.function.Function;

public class BaseCrossbowItem extends CrossbowItem implements ICustomBow {
    public BaseCrossbowItem(Function<Properties, Properties> properties) {
        super(properties.apply(new Properties()));
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this instanceof IEnableable enableable) {
            if (enableable.isEnabled())
                super.fillItemCategory(group, items);
        } else {
            super.fillItemCategory(group, items);
        }
    }

    @Override // copied from CrossbowItem with the initial declaration of 'i' changed
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        int i = (int) ((this.getUseDuration(stack) - timeLeft) * this.getDrawSpeedMulti(stack));
        float f = getPowerForTime(i, stack);
        if (f >= 1.0F && !isCharged(stack) && tryLoadProjectiles(entity, stack)) {
            setCharged(stack, true);
            SoundSource soundsource = entity instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
            level.playSound((Player)null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.CROSSBOW_LOADING_END, soundsource, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
        }

    }

    @Override
    public boolean hasFOVChange() {
        return false;
    }

    // copied from CrossbowItem as-is, may need to be checked in future updates
    private static float getPowerForTime(int power, ItemStack stack) {
        float f = (float) power / (float) getChargeDuration(stack);
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    // copied from CrossbowItem as-is, may need to be checked in future updates
    private static boolean tryLoadProjectiles(LivingEntity entity, ItemStack stack) {
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, stack);
        int j = i == 0 ? 1 : 3;
        boolean flag = entity instanceof Player && ((Player)entity).getAbilities().instabuild;
        ItemStack itemstack = entity.getProjectile(stack);
        ItemStack itemstack1 = itemstack.copy();

        for(int k = 0; k < j; ++k) {
            if (k > 0) {
                itemstack = itemstack1.copy();
            }

            if (itemstack.isEmpty() && flag) {
                itemstack = new ItemStack(Items.ARROW);
                itemstack1 = itemstack.copy();
            }

            if (!loadProjectile(entity, stack, itemstack, k > 0, flag)) {
                return false;
            }
        }

        return true;
    }

    // copied from CrossbowItem as-is, may need to be checked in future updates
    private static boolean loadProjectile(LivingEntity entity, ItemStack bow, ItemStack arrow, boolean p_40866_, boolean p_40867_) {
        if (arrow.isEmpty()) {
            return false;
        } else {
            boolean flag = p_40867_ && arrow.getItem() instanceof ArrowItem;
            ItemStack itemstack;
            if (!flag && !p_40867_ && !p_40866_) {
                itemstack = arrow.split(1);
                if (arrow.isEmpty() && entity instanceof Player) {
                    ((Player)entity).getInventory().removeItem(arrow);
                }
            } else {
                itemstack = arrow.copy();
            }

            addChargedProjectile(bow, itemstack);
            return true;
        }
    }

    // copied from CrossbowItem as-is, may need to be checked in future updates
    private static void addChargedProjectile(ItemStack p_40929_, ItemStack p_40930_) {
        CompoundTag compoundtag = p_40929_.getOrCreateTag();
        ListTag listtag;
        if (compoundtag.contains("ChargedProjectiles", 9)) {
            listtag = compoundtag.getList("ChargedProjectiles", 10);
        } else {
            listtag = new ListTag();
        }

        CompoundTag compoundtag1 = new CompoundTag();
        p_40930_.save(compoundtag1);
        listtag.add(compoundtag1);
        compoundtag.put("ChargedProjectiles", listtag);
    }

    public static ItemPropertyFunction getPullPropertyGetter() {
        return (stack, level, entity, _unused) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return BaseCrossbowItem.isCharged(stack) ? 0.0F : (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) * ((ICustomBow) stack.getItem()).getDrawSpeedMulti(stack) / (float) BaseCrossbowItem.getChargeDuration(stack);
            }
        };
    }

    public static ItemPropertyFunction getPullingPropertyGetter() {
        return (stack, level, entity, _unused) -> {
            return entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F;
        };
    }

    public static ItemPropertyFunction getChargedPropertyGetter() {
        return (stack, level, entity, _unused) -> {
            return entity != null && BaseCrossbowItem.isCharged(stack) ? 1.0F : 0.0F;
        };
    }

    public static ItemPropertyFunction getFireworkPropertyGetter() {
        return (stack, level, entity, _unused) -> {
            return entity != null && BaseCrossbowItem.isCharged(stack) && BaseCrossbowItem.containsChargedProjectile(stack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
        };
    }
}
