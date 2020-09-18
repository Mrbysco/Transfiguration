package com.minerarcana.transfiguration.recipe.result;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.TagJson;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.TagCollectionManager;

import javax.annotation.Nonnull;
import java.util.Objects;

public class EntityTagResultSerializer extends ResultSerializer<EntityTagResult> {
    @Nonnull
    @Override
    public EntityTagResult parse(@Nonnull PacketBuffer buffer) {
        return new EntityTagResult(TagCollectionManager.func_232928_e_().func_232927_d_()
                .get(buffer.readResourceLocation()));
    }

    @Nonnull
    @Override
    public EntityTagResult parse(@Nonnull JsonObject json) throws JsonParseException {
        return new EntityTagResult(TagJson.getEntityTypeTag(json));
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer, @Nonnull EntityTagResult object) {
        buffer.writeResourceLocation(Objects.requireNonNull(TagCollectionManager.func_232928_e_().func_232927_d_()
                .func_232973_a_(object.getTag())));
    }
}
