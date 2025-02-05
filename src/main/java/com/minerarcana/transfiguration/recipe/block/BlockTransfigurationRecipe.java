package com.minerarcana.transfiguration.recipe.block;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.api.event.TransfigurationEvent;
import com.minerarcana.transfiguration.api.recipe.ITransfigurationRecipe;
import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.entity.BlockTransfiguringEntity;
import com.minerarcana.transfiguration.entity.TransfiguringEntity;
import com.minerarcana.transfiguration.recipe.TransfigurationRecipe;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredient;
import com.minerarcana.transfiguration.recipe.result.Result;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Supplier;

public class BlockTransfigurationRecipe extends TransfigurationRecipe<BlockState> {
    public BlockTransfigurationRecipe(ResourceLocation recipeId, TransfigurationType transfigurationType,
                                      BasicIngredient ingredient, Result result, int ticks) {
        super(recipeId, transfigurationType, ingredient, result, ticks);
    }

    @Override
    public TransfiguringEntity<?, BlockState> createTransfiguringEntity(TransfigurationContainer<BlockState> transfigurationContainer, double timeModifier, double powerModifier) {
        return new BlockTransfiguringEntity(
                transfigurationContainer.getWorld(),
                transfigurationContainer.getTargetedPos(),
                this,
                timeModifier,
                powerModifier
        );
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean matches(TransfigurationContainer<BlockState> container, World world) {
        return this.getIngredient().test(container.getTargeted());
    }

    @Override
    @Nonnull
    public IRecipeSerializer<?> getSerializer() {
        return TransfigurationRecipes.BLOCK_TRANSFIGURATION.get();
    }

    @Override
    @Nonnull
    public IRecipeType<?> getType() {
        return this.getTransfigurationType().getBlockRecipeType();
    }

    @SuppressWarnings("UnusedReturnValue")
    public static boolean tryTransfigure(TransfigurationType type, BlockRayTraceResult blockRayTraceResult, World world,
                                         @Nullable Entity entity, double powerModifier, double timeModifier) {
        return tryTransfigure(type, TransfigurationContainer.block(
                world,
                blockRayTraceResult.getPos(),
                entity
        ), powerModifier, timeModifier);
    }

    public static boolean tryTransfigure(TransfigurationType type, TransfigurationContainer<BlockState> container,
                                         double powerModifier, double timeModifier) {
        if (type != null && !containsTransfiguringEntity(container.getWorld(), container.getTargetedPos())) {
            World world = container.getWorld();
            Optional<ITransfigurationRecipe<BlockState>> recipeOptional = world.getRecipeManager()
                    .getRecipe(type.getBlockRecipeType(), container, world);
            TransfigurationEvent transfigurationEvent = new TransfigurationEvent(type, container, timeModifier, powerModifier);
            MinecraftForge.EVENT_BUS.post(transfigurationEvent);
            if (!recipeOptional.isPresent()) {
                Iterator<Supplier<TransfigurationType>> supplierIterator = type.getFallbacks().iterator();
                while (!recipeOptional.isPresent() && supplierIterator.hasNext()) {
                    TransfigurationType nextType = supplierIterator.next().get();
                    transfigurationEvent = new TransfigurationEvent(type, container, timeModifier, powerModifier);
                    MinecraftForge.EVENT_BUS.post(transfigurationEvent);
                    recipeOptional = world.getRecipeManager()
                            .getRecipe(nextType.getBlockRecipeType(), container, world);
                }
            }
            double finalPowerModifier = transfigurationEvent.getCurrentPowerModifier();
            double finalTimeModifier = transfigurationEvent.getCurrentTimeModifier();
            return recipeOptional.map(recipe -> recipe.transfigure(container, finalPowerModifier, finalTimeModifier))
                    .orElse(false);
        }

        return false;
    }

    private static boolean containsTransfiguringEntity(World world, BlockPos blockPos) {
        return world.getEntitiesWithinAABB(TransfiguringEntity.class, new AxisAlignedBB(blockPos))
                .stream()
                .anyMatch(transfiguringEntity -> transfiguringEntity.getPosition().equals(blockPos));
    }
}
