package com.thesmartnoob880.mbdextcompat.elementalcraft.capability;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import sirttas.elementalcraft.api.element.ElementType;

public class ElementStack {

    private ElementType element;
    private int amount;

    public ElementStack(ElementType element, int amount){
        this.element = element;
        this.amount = amount;
    }
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

    public void ChangeElementAmount(int newamount) {
        amount=newamount;
    }

    public int amount() {
        return amount;
    }

    public ElementType element() {
        return element;
    }

    public static ElementStack of(String element, int amount){
        return new ElementStack(ElementType.byName(element.toLowerCase()), amount);
    }
}