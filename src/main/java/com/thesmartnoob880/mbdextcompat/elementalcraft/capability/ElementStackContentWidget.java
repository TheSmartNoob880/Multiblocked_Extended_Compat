package com.thesmartnoob880.mbdextcompat.elementalcraft.capability;

import com.lowdragmc.lowdraglib.gui.texture.ColorRectTexture;
import com.lowdragmc.lowdraglib.gui.texture.ResourceBorderTexture;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.SelectorWidget;
import com.lowdragmc.lowdraglib.gui.widget.TextFieldWidget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.utils.Position;
import com.lowdragmc.multiblocked.api.gui.recipe.ContentWidget;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import sirttas.elementalcraft.api.element.ElementType;
import sirttas.elementalcraft.gui.GuiHelper;
import sirttas.elementalcraft.interaction.jei.ingredient.element.IngredientElementType;

import java.util.stream.Collectors;

public class ElementStackContentWidget extends ContentWidget<ElementStack> {


    public ElementStackContentWidget() {
    }

    @Override
    protected void onContentUpdate() {
        if (content != null) {
            this.setHoverTooltips(content.element().getDisplayName().getString(), Integer.toString(content.amount()));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void drawHookBackground(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Position position = this.getPosition();
        if (this.content != null){
            GuiHelper.renderElementGauge(matrixStack, position.getX()+2, position.getY()+2, content.amount(), 100000, content.element(), false);
        }
    }

    @Override
    public void openConfigurator(WidgetGroup dialog) {
        super.openConfigurator(dialog);
        int x = 5;
        int y = 25;
        dialog.addWidget(new LabelWidget(5, y+3, "multiblocked.gui.label.amount"));
        dialog.addWidget((new TextFieldWidget(65, y, 60, 15, null, (number) -> {
            this.content = ElementMultiblockCapability.CAP.copyInner(content);
            content.ChangeElementAmount(Integer.parseInt(number));
            onContentUpdate();
        })).setNumbersOnly(1, Integer.MAX_VALUE).setCurrentString(String.valueOf(content.amount())));
        dialog.addWidget(new LabelWidget(5, 53, "Element:"));
        SelectorWidget selectorWidget = new SelectorWidget( x+60, 50, 60, 15, ElementType.ALL_VALID.stream().map(ElementType::getSerializedName).collect(Collectors.toList()), -1);
        boolean isValidElement = ElementType.ALL_VALID.stream().anyMatch(elementType -> elementType.equals(content.element()));
        dialog.addWidget(selectorWidget
                        .setOnChanged(elementName -> {
                            if (elementName != null && !elementName.isEmpty()
                                    && isValidElement){
                                content=new ElementStack(ElementType.byName(elementName), content.amount());
                                selectorWidget.setValue(elementName);
                                onContentUpdate();
                            }
                        }).setValue(isValidElement ? content.element().getSerializedName() : "")
                .setButtonBackground(ResourceBorderTexture.BUTTON_COMMON)
                .setBackground(new ColorRectTexture(0xffaaaaaa)));
    }

    @Override
    public @Nullable Object getJEIIngredient() {
        return new IngredientElementType(content.element());
    }
}
