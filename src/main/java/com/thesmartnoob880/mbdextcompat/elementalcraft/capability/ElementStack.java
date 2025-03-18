package com.thesmartnoob880.mbdextcompat.elementalcraft.capability;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import sirttas.elementalcraft.api.element.ElementType;

public record ElementStack(ElementType element, int amount) {
    public static ElementStack fromJson(JsonElement json) {
        JsonObject elementData = json.getAsJsonObject();
        ElementType element = ElementType.byName(elementData.get("element").getAsString());
        int amount = elementData.get("amount").getAsInt();
        return new ElementStack(element, amount);
    }

    public JsonElement toJson() {
        JsonObject json = new JsonObject();
        json.add("element", new JsonPrimitive(element.getSerializedName()));
        json.add("amount", new JsonPrimitive(amount));
        return json;
    }
}