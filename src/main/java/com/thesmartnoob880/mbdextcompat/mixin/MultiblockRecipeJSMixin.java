package com.thesmartnoob880.mbdextcompat.mixin;

import com.lowdragmc.multiblocked.api.capability.MultiblockCapability;
import com.lowdragmc.multiblocked.api.kubejs.MultiblockRecipeJS;
import com.thesmartnoob880.mbdextcompat.MultiblockedExtendedCompat;
import com.thesmartnoob880.mbdextcompat.arsnouveau.capability.SourceMultiblockCapability;
import com.thesmartnoob880.mbdextcompat.elementalcraft.capability.ElementMultiblockCapability;
import com.thesmartnoob880.mbdextcompat.elementalcraft.capability.ElementStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MultiblockRecipeJS.class)
public abstract class MultiblockRecipeJSMixin {
    @Shadow(remap = false) public abstract <T> MultiblockRecipeJS input(MultiblockCapability<T> capability, T input, String slotName);

    @Shadow(remap = false) public abstract <T> MultiblockRecipeJS output(MultiblockCapability<T> capability, T output, String slotName);

    public MultiblockRecipeJS inputANSource(int source, String slotName){
        if (MultiblockedExtendedCompat.isANLoaded)
            return input(SourceMultiblockCapability.CAP, source, slotName);
        throw MultiblockedExtendedCompat.throwMissingRequiredModException("ars_nouveau");
    }

    public MultiblockRecipeJS inputANSource(int source){
        return inputANSource(source, null);
    }

    public MultiblockRecipeJS outputANSource(int source, String slotName){
        if (MultiblockedExtendedCompat.isANLoaded)
            return output(SourceMultiblockCapability.CAP, source, slotName);
        throw MultiblockedExtendedCompat.throwMissingRequiredModException("ars_nouveau");
    }

    public MultiblockRecipeJS outputANSource(int source){
        return outputANSource(source, null);
    }

    public MultiblockRecipeJS inputECElement(String elementName, int amount, String slotName){
        if (MultiblockedExtendedCompat.isECLoaded)
            return input(ElementMultiblockCapability.CAP, ElementStack.of(elementName, amount), slotName);
        throw MultiblockedExtendedCompat.throwMissingRequiredModException("elementalcraft");
    }

    public MultiblockRecipeJS inputECElement(String elementName, int amount){
        return inputECElement(elementName, amount, null);
    }

    public MultiblockRecipeJS outputECElement(String elementName, int amount, String slotName){
        if (MultiblockedExtendedCompat.isECLoaded)
            return output(ElementMultiblockCapability.CAP, ElementStack.of(elementName, amount), slotName);
        throw MultiblockedExtendedCompat.throwMissingRequiredModException("elementalcraft");
    }

    public MultiblockRecipeJS outputECElement(String elementName, int amount){
        return outputECElement(elementName, amount, null);
    }
    //todo make string convert to lowercase
}