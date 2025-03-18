package com.thesmartnoob880.mbdextcompat.elementalcraft.capability;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lowdragmc.multiblocked.api.recipe.serde.content.IContentSerializer;
import sirttas.elementalcraft.api.element.ElementType;

public class SerializerElementStack implements IContentSerializer<ElementStack> {
    @Override
    public ElementStack fromJson(JsonElement json) {
        return ElementStack.fromJson(json);
    }

    @Override
    public JsonElement toJson(ElementStack elementStack) {
        return elementStack.toJson();
    }

    @Override
    public ElementStack of(Object o) {
        return null;
    }

    public static final SerializerElementStack INSTANCE = new SerializerElementStack();
    private SerializerElementStack(){};
}
