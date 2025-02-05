package com.minerarcana.transfiguration.recipe.dust;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationBlocks;
import com.minerarcana.transfiguration.recipe.builder.FinishedObject;
import com.minerarcana.transfiguration.recipe.builder.IngredientBuilder;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredientSerializer;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

public class DustRecipeBuilder {
    private final TransfigurationType transfigurationType;
    private FinishedObject<BasicIngredientSerializer<?>> ingredient;
    private ITag.INamedTag<Fluid> fluidIngredient;
    private ItemStack output;

    public DustRecipeBuilder(TransfigurationType transfigurationType) {
        this.transfigurationType = transfigurationType;
        this.ingredient = IngredientBuilder.matches(TransfigurationBlocks.CATALYST_SUBSTRATE.get());
    }

    public DustRecipeBuilder withBlock(FinishedObject<BasicIngredientSerializer<?>> blockIngredient) {
        this.ingredient = blockIngredient;
        return this;
    }

    public DustRecipeBuilder withFluid(ITag.INamedTag<Fluid> fluid) {
        this.fluidIngredient = fluid;
        return this;
    }

    public DustRecipeBuilder withOutput(ItemStack output) {
        this.output = output;
        return this;
    }

    public void build(Consumer<IFinishedRecipe> recipeConsumer) {
        build(recipeConsumer, null);
    }

    public void build(Consumer<IFinishedRecipe> recipeConsumer, @Nullable ResourceLocation id) {
        this.validate(id);
        if (id == null) {
            ResourceLocation resultId = Objects.requireNonNull(this.output.getItem().getRegistryName());
            id = new ResourceLocation(resultId.getNamespace(), "dust/" + resultId.getPath().replace("/", "_")
                    + "_from_" + Objects.requireNonNull(transfigurationType.getRegistryName()).getPath().replace("/", "_")
                    + "_" + ingredient.getId().toString().replace(":", "_").replace("/", "_"));
        }
        recipeConsumer.accept(new DustFinishedRecipe(id, transfigurationType, ingredient, fluidIngredient, output));
    }

    protected void validate(ResourceLocation id) {
        if (output == null) {
            throw new IllegalStateException("No 'output' defined for Dust Recipe " + id + "!");
        }

        if (transfigurationType == null) {
            throw new IllegalStateException("No 'transfigurationType' defined for Dust Recipe " + id + "!");
        }
    }

    public static DustRecipeBuilder create(TransfigurationType transfigurationType) {
        return new DustRecipeBuilder(transfigurationType);
    }
}
