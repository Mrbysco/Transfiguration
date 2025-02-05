package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.recipe.resultinstance.AfterDoneResultInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

public class EntityResult extends Result {
    private final EntityType<?> entityType;

    public EntityResult(EntityType<?> entityType) {
        this.entityType = entityType;
    }

    @Nonnull
    @Override
    public ResultInstance create() {
        return new AfterDoneResultInstance(1, this::handle);
    }

    public void handle(@Nonnull TransfigurationContainer<?> transfigurationContainer, double powerModifier) {
        summon(transfigurationContainer, entityType);
    }

    @Override
    @Nonnull
    public ItemStack getRepresentation() {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ResultSerializer<?> getSerializer() {
        return TransfigurationRecipes.ENTITY_RESULT_SERIALIZER.get();
    }

    public EntityType<?> getEntityType() {
        return entityType;
    }

    public static EntityResult create(EntityType<?> entityType) {
        return new EntityResult(entityType);
    }

    public static ActionResultType summon(TransfigurationContainer<?> transfigurationContainer, EntityType<?> entityType) {
        Entity entity = entityType.create(transfigurationContainer.getWorld());
        if (entity != null) {
            if (transfigurationContainer.getWorld().isRemote()) {
                return ActionResultType.CONSUME;
            } else {
                BlockPos blockPos = transfigurationContainer.getTargetedPos();
                entity.setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                transfigurationContainer.getWorld().addEntity(entity);
                return ActionResultType.SUCCESS;
            }

        } else {
            return ActionResultType.FAIL;
        }
    }
}
