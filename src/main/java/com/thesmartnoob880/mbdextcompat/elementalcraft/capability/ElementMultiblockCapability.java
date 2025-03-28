package com.thesmartnoob880.mbdextcompat.elementalcraft.capability;

import com.lowdragmc.lowdraglib.utils.BlockInfo;
import com.lowdragmc.multiblocked.api.capability.IO;
import com.lowdragmc.multiblocked.api.capability.MultiblockCapability;
import com.lowdragmc.multiblocked.api.capability.proxy.CapCapabilityProxy;
import com.lowdragmc.multiblocked.api.capability.proxy.CapabilityProxy;
import com.lowdragmc.multiblocked.api.gui.recipe.ContentWidget;
import com.lowdragmc.multiblocked.api.recipe.Recipe;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sirttas.elementalcraft.api.element.ElementType;
import sirttas.elementalcraft.api.element.storage.CapabilityElementStorage;
import sirttas.elementalcraft.api.element.storage.IElementStorage;
import sirttas.elementalcraft.block.ECBlocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElementMultiblockCapability extends MultiblockCapability<ElementStack> {
    public static final ElementMultiblockCapability CAP = new ElementMultiblockCapability();

    protected ElementMultiblockCapability() {
        super("elementalcraft.element", 0xFFeaf2e6, SerializerElementStack.INSTANCE);
    }

    @Override
    public ElementStack defaultContent() {
        return new ElementStack(ElementType.AIR,1000);
    }

    @Override
    public ContentWidget<? super ElementStack> createContentWidget() {
        return new ElementStackContentWidget();
    }

    @Override
    public boolean isBlockHasCapability(@NotNull IO io, @NotNull BlockEntity blockEntity) {
        return !getCapability(CapabilityElementStorage.ELEMENT_STORAGE_CAPABILITY, blockEntity).isEmpty();
    }

    @Override
    public ElementStack copyInner(ElementStack elementStack) {
        return elementStack;
    }

    @Override
    protected CapabilityProxy<? extends ElementStack> createProxy(@NotNull IO io, @NotNull BlockEntity blockEntity) {
        return new ElementCapabilityProxy(blockEntity);
    }

    @Override
    public BlockInfo[] getCandidates() {
        return new BlockInfo[]{
                BlockInfo.fromBlock(ECBlocks.CONTAINER.get()),
                BlockInfo.fromBlock(ECBlocks.SMALL_CONTAINER.get()),
                BlockInfo.fromBlock(ECBlocks.AIR_RESERVOIR),
                BlockInfo.fromBlock(ECBlocks.EARTH_RESERVOIR),
                BlockInfo.fromBlock(ECBlocks.FIRE_RESERVOIR),
                BlockInfo.fromBlock(ECBlocks.WATER_RESERVOIR),
                BlockInfo.fromBlock(ECBlocks.CREATIVE_CONTAINER)
        };
    }

    public static class ElementCapabilityProxy extends CapCapabilityProxy<IElementStorage, ElementStack> {
        public ElementCapabilityProxy(BlockEntity tileEntity) {
            super(ElementMultiblockCapability.CAP, tileEntity, CapabilityElementStorage.ELEMENT_STORAGE_CAPABILITY);
        }

        @Override
        protected List<ElementStack> handleRecipeInner(IO io, Recipe recipe, List<ElementStack> list, @Nullable String slotName, boolean simulate) {
            IElementStorage capability = getCapability(slotName);
            if (capability == null) return list;
            Map<ElementType, Integer> requiredElements = new HashMap<>();
            ElementType.ALL_VALID.forEach(element -> {
                int elementamount = list.stream()
                        .filter(elementStack -> elementStack.element() == element)
                        .map(ElementStack::amount)
                        .reduce(0, Integer::sum);
                if (elementamount > 0)
                    requiredElements.put(element, elementamount);
            });
            if (io == IO.IN){
                for (ElementType elementType : requiredElements.keySet()) {
                    int remaining = requiredElements.get(elementType);
                    int cost = Math.min(capability.getElementAmount(elementType), remaining);
                    if (!simulate)
                        capability.extractElement(cost, elementType, false);
                    int result = remaining-cost;
                    if (result > 0)
                        requiredElements.put(elementType,result);
                    else requiredElements.remove(elementType);
                }
            } else if (io == IO.OUT){
                for (ElementType elementType : requiredElements.keySet()) {
                    int remaining = requiredElements.get(elementType);
                    int producedElement = Math.min(remaining, capability.getElementCapacity(elementType)-capability.getElementAmount(elementType));
                    if (producedElement > 0){
                        if (!simulate) {
                            capability.insertElement(producedElement, elementType, false);
                        }
                        int result = remaining-producedElement;
                        if (result > 0)
                            requiredElements.put(elementType, result);
                        else requiredElements.remove(elementType);
                    }

                }
            }
            return requiredElements.isEmpty() ? null : elementMaptoList(requiredElements);
        }

        public List<ElementStack> elementMaptoList(Map<ElementType, Integer> elements) {
            List<ElementStack> elementStacks = new ArrayList<>();
            elements.forEach((element, amount) -> elementStacks.add(new ElementStack(element, amount)));
            return elementStacks;
        }
    }
}
