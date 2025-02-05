package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.entity.BlockTransfiguringEntity;
import com.minerarcana.transfiguration.entity.EntityTransfiguringEntity;
import com.minerarcana.transfiguration.entity.TransfiguringProjectileEntity;
import com.minerarcana.transfiguration.entity.TransfiguringAreaEffectEntity;
import com.minerarcana.transfiguration.item.TransfiguringProjectileItem;
import com.minerarcana.transfiguration.renderer.TransfiguringEntityRenderer;
import com.minerarcana.transfiguration.renderer.TransfiguringProjectileRenderer;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.client.renderer.entity.AreaEffectCloudRenderer;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

public class TransfigurationEntities {
    public static final RegistryEntry<EntityType<TransfiguringProjectileEntity>> TRANSFIGURING_PROJECTILE =
            Transfiguration.getRegistrate()
                    .object("transfiguring_projectile")
                    .<TransfiguringProjectileEntity>entity(TransfiguringProjectileEntity::new, EntityClassification.MISC)
                    .lang("Transfiguring Projectile")
                    .renderer(() -> TransfiguringProjectileRenderer::new)
                    .register();

    public static final RegistryEntry<TransfiguringProjectileItem> TRANSFIGURING_PROJECTILE_ITEM =
            Transfiguration.getRegistrate()
                    .object("transfiguring_projectile")
                    .item(TransfiguringProjectileItem::new)
                    .model((context, modelProvider) -> modelProvider.generated(context, new ResourceLocation("item/fire_charge")))
                    .color(() -> () -> TransfiguringProjectileItem::getColor)
                    .register();

    public static final RegistryEntry<EntityType<AreaEffectCloudEntity>> TRANSFIGURING_AREA_EFFECT =
            Transfiguration.getRegistrate()
                    .object("transfiguring_area_effect")
                    .<AreaEffectCloudEntity>entity(TransfiguringAreaEffectEntity::new, EntityClassification.MISC)
                    .properties(properties -> properties.immuneToFire()
                            .size(6.0F, 0.5F)
                            .trackingRange(10)
                            .func_233608_b_(Integer.MAX_VALUE)
                    )
                    .lang("Transfiguring")
                    .renderer(() -> AreaEffectCloudRenderer::new)
                    .register();

    public static final RegistryEntry<EntityType<BlockTransfiguringEntity>> BLOCK_TRANSFIGURING =
            Transfiguration.getRegistrate()
                    .object("block_transfiguring")
                    .<BlockTransfiguringEntity>entity(BlockTransfiguringEntity::new, EntityClassification.MISC)
                    .lang("Transfiguration")
                    .renderer(() -> TransfiguringEntityRenderer::new)
                    .properties(properties -> properties.immuneToFire()
                            .size(1.0F, 1.0F)
                    )
                    .register();

    public static final RegistryEntry<EntityType<EntityTransfiguringEntity>> ENTITY_TRANSFIGURING =
            Transfiguration.getRegistrate()
                    .object("block_transfiguring")
                    .<EntityTransfiguringEntity>entity(EntityTransfiguringEntity::new, EntityClassification.MISC)
                    .lang("Transfiguration")
                    .renderer(() -> TransfiguringEntityRenderer::new)
                    .properties(properties -> properties.immuneToFire()
                            .size(1.0F, 1.0F)
                    )
                    .register();


    public static void setup() {

    }
}