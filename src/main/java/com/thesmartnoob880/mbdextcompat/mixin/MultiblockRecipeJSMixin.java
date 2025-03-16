package com.thesmartnoob880.mbdextcompat.mixin;

import com.lowdragmc.multiblocked.api.capability.MultiblockCapability;
import com.lowdragmc.multiblocked.api.kubejs.MultiblockRecipeJS;
import com.thesmartnoob880.mbdextcompat.arsnouveau.capability.SourceMultiblockCapability;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MultiblockRecipeJS.class)
public abstract class MultiblockRecipeJSMixin {
    @Shadow(remap = false) public abstract <T> MultiblockRecipeJS input(MultiblockCapability<T> capability, T input, String slotName);

    @Shadow(remap = false) public abstract <T> MultiblockRecipeJS output(MultiblockCapability<T> capability, T output, String slotName);

    public MultiblockRecipeJS inputANSource(int source, String slotName){
        return input(SourceMultiblockCapability.CAP, source, slotName);
    }

    public MultiblockRecipeJS inputANSource(int source){
        return inputANSource(source, null);
    }

    public MultiblockRecipeJS outputANSource(int source, String slotName){
        return output(SourceMultiblockCapability.CAP, source, slotName);
    }

    public MultiblockRecipeJS outputANSource(int source){
        return outputANSource(source, null);
    }
}
