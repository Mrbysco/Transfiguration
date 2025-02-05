package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationAttributes;
import com.minerarcana.transfiguration.recipe.entity.EntityTransfigurationRecipe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.ToDoubleFunction;

public interface ITransfiguring {
    TransfigurationType getType(ItemStack itemStack);

    default double getPowerModifier(ItemStack itemStack) {
        return 1.0F;
    }

    default double getTimeModifier(ItemStack itemStack) {
        return 1.0F;
    }

    default boolean transfigureEntity(Entity target, @Nullable Entity caster, ItemStack itemStack) {
        return EntityTransfigurationRecipe.tryTransfigure(
                this.getType(itemStack),
                target,
                caster,
                getValue(caster, itemStack, TransfigurationAttributes.POWER_MODIFIER.get(), this::getPowerModifier),
                getValue(caster, itemStack, TransfigurationAttributes.TIME_MODIFIER.get(), this::getTimeModifier)
        );
    }

    default double getValue(@Nullable Entity caster, ItemStack itemStack, Attribute attribute, ToDoubleFunction<ItemStack> def) {
        if (caster instanceof LivingEntity) {
            LivingEntity livingCaster = (LivingEntity) caster;
            if (livingCaster.getAttributeManager().hasAttributeInstance(attribute)) {
                return livingCaster.getAttributeValue(attribute);
            }
        }
        return def.applyAsDouble(itemStack);
    }

    void afterTransfiguration(ItemStack itemStack, @Nonnull LivingEntity livingEntity, Hand hand);
}
