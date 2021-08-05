package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.recipe.dust.DustRecipeBuilder;
import com.minerarcana.transfiguration.registrate.TransfigurationTypeBuilder;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.DyeColor;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.FluidTags;

public class TransfigurationTypes {
    private static final NonNullBiFunction<String, BuilderCallback, TransfigurationTypeBuilder<TransfigurationType,
            AbstractRegistrate<?>>> TRANSFIGURATION_TYPE = TransfigurationTypeBuilder.entry(Transfiguration::getRegistrate);

    private static final NonNullFunction<TransfigurationTypeBuilder<TransfigurationType, AbstractRegistrate<?>>,
            TransfigurationTypeBuilder<TransfigurationType, AbstractRegistrate<?>>> ADD_ITEMS =
            builder ->
                    builder.dust()
                            .build()
                            .catalyst()
                            .build()
                            .wand()
                            .build();

    public static final RegistryEntry<TransfigurationType> NETHERI = Transfiguration.getRegistrate()
            .object("netheri")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Netheri")
            .primaryColor(DyeColor.RED.getColorValue())
            .dust()
            .build()
            .catalyst()
            .recipe((context, provider) -> DustRecipeBuilder.create(TransfigurationTypes.NETHERI.get())
                    .withFluid(FluidTags.WATER)
                    .withOutput(context.get().getDefaultInstance())
                    .build(provider)
            )
            .build()
            .wand()
            .build()
            .register();

    public static final RegistryEntry<TransfigurationType> ACCURSED = Transfiguration.getRegistrate()
            .object("accursed")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Accursed")
            .includes(NETHERI)
            .primaryColor(DyeColor.PURPLE.getColorValue())
            .transform(ADD_ITEMS)
            .register();

    public static final RegistryEntry<TransfigurationType> MUTANDI = Transfiguration.getRegistrate()
            .object("mutandi")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Mutandi")
            .primaryColor(DyeColor.GREEN.getColorValue())
            .transform(ADD_ITEMS)
            .register();

    public static final RegistryEntry<TransfigurationType> BLESSED = Transfiguration.getRegistrate()
            .object("blessed")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Blessed")
            .includes(MUTANDI)
            .primaryColor(DyeColor.YELLOW.getColorValue())
            .transform(ADD_ITEMS)
            .register();

    public static final RegistryEntry<TransfigurationType> DESTABILIZING = Transfiguration.getRegistrate()
            .object("destabilizing")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Destabilizing")
            .primaryColor(DyeColor.WHITE.getColorValue())
            .transform(ADD_ITEMS)
            .register();

    public static final RegistryEntry<TransfigurationType> DISSOLUTION = Transfiguration.getRegistrate()
            .object("dissolution")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Dissolution")
            .primaryColor(DyeColor.BLUE.getColorValue())
            .transform(ADD_ITEMS)
            .register();

    public static final RegistryEntry<TransfigurationType> FUNGAL = Transfiguration.getRegistrate()
            .object("fungal")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Fungal")
            .primaryColor(DyeColor.BROWN.getColorValue())
            .transform(ADD_ITEMS)
            .register();

    public static final RegistryEntry<TransfigurationType> OVERNI = Transfiguration.getRegistrate()
            .object("overni")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Overni")
            .primaryColor(DyeColor.LIME.getColorValue())
            .transform(ADD_ITEMS)
            .register();

    public static void setup() {

    }
}
