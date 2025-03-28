package com.thesmartnoob880.mbdextcompat.arsnouveau.capability;

import com.google.gson.*;
import com.hollingsworth.arsnouveau.api.source.ISourceTile;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.lowdragmc.lowdraglib.gui.texture.TextTexture;
import com.lowdragmc.lowdraglib.utils.BlockInfo;
import com.lowdragmc.multiblocked.api.capability.IO;
import com.lowdragmc.multiblocked.api.capability.MultiblockCapability;
import com.lowdragmc.multiblocked.api.capability.proxy.CapabilityProxy;
import com.lowdragmc.multiblocked.api.gui.recipe.ContentWidget;
import com.lowdragmc.multiblocked.api.recipe.ContentModifier;
import com.lowdragmc.multiblocked.api.recipe.Recipe;
import com.lowdragmc.multiblocked.api.recipe.serde.content.SerializerInteger;
import com.lowdragmc.multiblocked.common.capability.widget.NumberContentWidget;
import com.thesmartnoob880.mbdextcompat.MultiblockedExtendedCompat;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class SourceMultiblockCapability extends MultiblockCapability<Integer> {
    public static final SourceMultiblockCapability CAP = new SourceMultiblockCapability();

    protected SourceMultiblockCapability() {
        super("arsnouveau.source", 0xFFFF19B4, SerializerInteger.INSTANCE);
    }

    @Override
    public Integer defaultContent() {
        return 200;
    }

    @Override
    public boolean isBlockHasCapability(@NotNull IO io, @NotNull BlockEntity blockEntity) {
        return blockEntity instanceof ISourceTile;
    }

    @Override
    public Integer copyInner(Integer integer) {
        return integer;
    }

    @Override
    public Integer copyWithModifier(Integer content, ContentModifier modifier) {
        return modifier.apply(content).intValue();
    }

    @Override
    public SourceMultiblockCapabilityProxy createProxy(@NotNull IO io, @NotNull BlockEntity blockEntity) {
        return new SourceMultiblockCapabilityProxy(blockEntity);
    }

    @Override
    public BlockInfo[] getCandidates() {
        return new BlockInfo[]{
                BlockInfo.fromBlock(BlockRegistry.SOURCE_JAR),
                BlockInfo.fromBlock(BlockRegistry.CREATIVE_SOURCE_JAR)
        };
    }

    @Override
    public ContentWidget<? super Integer> createContentWidget() {
        return new NumberContentWidget().setContentTexture(new TextTexture("S", color)).setUnit("Source");
    }

    @Override
    public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return json.getAsInt();
    }

    @Override
    public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }

    public static class SourceMultiblockCapabilityProxy extends CapabilityProxy<Integer> {

        public SourceMultiblockCapabilityProxy(BlockEntity tileEntity) {
            super(SourceMultiblockCapability.CAP, tileEntity);
        }

        public ISourceTile getContainer() {
            return (ISourceTile)getTileEntity();
        }

        @Override
        protected List<Integer> handleRecipeInner(IO io, Recipe recipe, List<Integer> list, @Nullable String slot, boolean simulate) {
            ISourceTile sourceTile =getContainer();
            if (sourceTile == null) return list;
            int sum = list.stream().reduce(0, Integer::sum);
            int initialSource = sourceTile.getSource();
            MultiblockedExtendedCompat.LOGGER.info("handle_inner_sum {}", sum);
            if (io == IO.IN) {
                MultiblockedExtendedCompat.LOGGER.info("started with {} source", initialSource);
                int cost = Math.min(initialSource, sum);
                    if (!simulate) {
                        sourceTile.removeSource(cost);
                    MultiblockedExtendedCompat.LOGGER.info("removed {} source", cost);
                    }
                    sum -= cost;
            }
            else if (io == IO.OUT) {
                int producedSource = Math.min(sum, sourceTile.getMaxSource()-initialSource);
                MultiblockedExtendedCompat.LOGGER.info("started with {} source in multiblock", initialSource);
                MultiblockedExtendedCompat.LOGGER.info("Attempting to produce {} Source", sum);
                if (producedSource > 0) {
                    if (!simulate) {
                        sourceTile.addSource(producedSource);
                        MultiblockedExtendedCompat.LOGGER.info("added {} source", producedSource);
                    }
                    sum -= producedSource;
                }
            }
            return sum <= 0  ? null : Collections.singletonList(sum);
        }
        public int currentSource = -1;
        @Override
        protected boolean hasInnerChanged() {
            ISourceTile sourceTile = getContainer();
            if (sourceTile == null) return false;
            if (currentSource == sourceTile.getSource())
                return false;
            currentSource = sourceTile.getSource();
            return true;
        }
    }
}
