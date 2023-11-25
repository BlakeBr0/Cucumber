package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.iface.ICustomBow;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;
import java.util.function.Function;

public class BaseCrossbowItem extends CrossbowItem implements ICustomBow {
    public BaseCrossbowItem(Function<Properties, Properties> properties) {
        super(properties.apply(new Properties()));
    }

    @Override // copied from CrossbowItem#releaseUsing
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        // change: account for draw speed multiplier
        int i = (int) ((this.getUseDuration(stack) - timeLeft) * this.getDrawSpeedMulti(stack));
        float f = getPowerForTime(i, stack);
        if (f >= 1.0F && !isCharged(stack) && tryLoadProjectiles(entity, stack)) {
            setCharged(stack, true);
            SoundSource soundsource = entity instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
            level.playSound((Player)null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.CROSSBOW_LOADING_END, soundsource, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
        }
    }

    @Override // copied from CrossbowItem#use as-is to make use of the overridden getArrow method
    public InteractionResultHolder<ItemStack> use(Level p_40920_, Player p_40921_, InteractionHand p_40922_) {
        ItemStack itemstack = p_40921_.getItemInHand(p_40922_);
        if (isCharged(itemstack)) {
            performShooting(p_40920_, p_40921_, p_40922_, itemstack, getShootingPower(itemstack), 1.0F);
            setCharged(itemstack, false);
            return InteractionResultHolder.consume(itemstack);
        } else if (!p_40921_.getProjectile(itemstack).isEmpty()) {
            if (!isCharged(itemstack)) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
                p_40921_.startUsingItem(p_40922_);
            }

            return InteractionResultHolder.consume(itemstack);
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    @Override
    public boolean hasFOVChange() {
        return false;
    }

    // copied from CrossbowItem#performShooting as-is to make use of the overridden getArrow method
    public static void performShooting(Level p_40888_, LivingEntity p_40889_, InteractionHand p_40890_, ItemStack p_40891_, float p_40892_, float p_40893_) {
        if (p_40889_ instanceof Player player) {
            if (ForgeEventFactory.onArrowLoose(p_40891_, p_40889_.level(), player, 1, true) < 0) {
                return;
            }
        }

        List<ItemStack> list = getChargedProjectiles(p_40891_);
        float[] afloat = getShotPitches(p_40889_.getRandom());

        for(int i = 0; i < list.size(); ++i) {
            ItemStack itemstack = (ItemStack)list.get(i);
            boolean flag = p_40889_ instanceof Player && ((Player)p_40889_).getAbilities().instabuild;
            if (!itemstack.isEmpty()) {
                if (i == 0) {
                    shootProjectile(p_40888_, p_40889_, p_40890_, p_40891_, itemstack, afloat[i], flag, p_40892_, p_40893_, 0.0F);
                } else if (i == 1) {
                    shootProjectile(p_40888_, p_40889_, p_40890_, p_40891_, itemstack, afloat[i], flag, p_40892_, p_40893_, -10.0F);
                } else if (i == 2) {
                    shootProjectile(p_40888_, p_40889_, p_40890_, p_40891_, itemstack, afloat[i], flag, p_40892_, p_40893_, 10.0F);
                }
            }
        }

        onCrossbowShot(p_40888_, p_40889_, p_40891_);
    }

    // copied from CrossbowItem#shootProjectile as-is to make use of the overridden getArrow method
    private static void shootProjectile(Level p_40895_, LivingEntity p_40896_, InteractionHand p_40897_, ItemStack p_40898_, ItemStack p_40899_, float p_40900_, boolean p_40901_, float p_40902_, float p_40903_, float p_40904_) {
        if (!p_40895_.isClientSide) {
            boolean flag = p_40899_.is(Items.FIREWORK_ROCKET);
            Object projectile;
            if (flag) {
                projectile = new FireworkRocketEntity(p_40895_, p_40899_, p_40896_, p_40896_.getX(), p_40896_.getEyeY() - 0.15000000596046448, p_40896_.getZ(), true);
            } else {
                projectile = getArrow(p_40895_, p_40896_, p_40898_, p_40899_);
                if (p_40901_ || p_40904_ != 0.0F) {
                    ((AbstractArrow)projectile).pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }
            }

            if (p_40896_ instanceof CrossbowAttackMob) {
                CrossbowAttackMob crossbowattackmob = (CrossbowAttackMob)p_40896_;
                crossbowattackmob.shootCrossbowProjectile(crossbowattackmob.getTarget(), p_40898_, (Projectile)projectile, p_40904_);
            } else {
                Vec3 vec31 = p_40896_.getUpVector(1.0F);
                Quaternionf quaternionf = (new Quaternionf()).setAngleAxis((double)(p_40904_ * 0.017453292F), vec31.x, vec31.y, vec31.z);
                Vec3 vec3 = p_40896_.getViewVector(1.0F);
                Vector3f vector3f = vec3.toVector3f().rotate(quaternionf);
                ((Projectile)projectile).shoot((double)vector3f.x(), (double)vector3f.y(), (double)vector3f.z(), p_40902_, p_40903_);
            }

            p_40898_.hurtAndBreak(flag ? 3 : 1, p_40896_, (p_40858_) -> {
                p_40858_.broadcastBreakEvent(p_40897_);
            });
            p_40895_.addFreshEntity((Entity)projectile);
            p_40895_.playSound((Player)null, p_40896_.getX(), p_40896_.getY(), p_40896_.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, p_40900_);
        }

    }

    // copied from CrossbowItem#getArrow
    private static AbstractArrow getArrow(Level p_40915_, LivingEntity p_40916_, ItemStack p_40917_, ItemStack p_40918_) {
        ArrowItem arrowitem = (ArrowItem)(p_40918_.getItem() instanceof ArrowItem ? p_40918_.getItem() : Items.ARROW);
        AbstractArrow abstractarrow = arrowitem.createArrow(p_40915_, p_40918_, p_40916_);
        if (p_40916_ instanceof Player) {
            abstractarrow.setCritArrow(true);
        }

        // change: account for bonus damage
        abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + ((BaseCrossbowItem) p_40917_.getItem()).getBonusDamage(p_40917_));

        abstractarrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);
        abstractarrow.setShotFromCrossbow(true);
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, p_40917_);
        if (i > 0) {
            abstractarrow.setPierceLevel((byte)i);
        }

        return abstractarrow;
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
