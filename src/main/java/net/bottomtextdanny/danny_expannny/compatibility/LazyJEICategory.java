package net.bottomtextdanny.danny_expannny.compatibility;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.bottomtextdanny.braincell.mod.screen.Blitty;
import net.bottomtextdanny.braincell.mod.screen.ImageData;
import net.bottomtextdanny.danny_expannny.ClientInstance;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.object_tables.items.DEBuildingItems;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe.LazyIngredient;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe.LazyRecipe;
import net.bottomtextdanny.danny_expannny.rendering.RenderUtils;
import net.bottomtextdanny.dannys_expansion.core.events.ResourceReloadHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LazyJEICategory implements IRecipeCategory<LazyRecipe> {
    public static final int SLIDER_PLACE_X = 102;
    public static final int SLIDER_PLACE_Y_START = 1;
    public static final int SLIDER_PLACE_Y_END = 48;
    public static final int STEP_OFFSET = 27;
    public static final int MAX_X_WIDTH = 76;
    public static final ResourceLocation ID = new ResourceLocation(DannysExpansion.ID, "workbench");
    public static final ImageData GUI = new ImageData(DannysExpansion.ID, "textures/gui/jei_backgrounds.png", 256, 256);
    private static final Blitty SLIDER = new Blitty(GUI, 166, 0, 5, 6);
    private final RenderData[] data;
    private final RenderData fallbackData;
    private final Component name;
    private final IDrawable background;
    private final IDrawable icon;

    public LazyJEICategory(IGuiHelper guiHelper) {
        super();
        final int recipesSize = ResourceReloadHandler.recipeManager.size();
        this.data = new RenderData[recipesSize];
        this.fallbackData = new RenderData();
        this.background = guiHelper.createDrawable(GUI, 0, 0, 166, 55);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(DEBuildingItems.WORKBENCH.get()));
        this.name = new TranslatableComponent("container.workbench");
    }

    @Override
    public void draw(LazyRecipe recipe, PoseStack stack, double mouseX, double mouseY) {
        final RenderData block = getRenderData(recipe);
        final int xOffset = 1, yOffset = 1;
        final Font font = Minecraft.getInstance().font;
        final List<LazyIngredient> lazyIngredients = recipe.getIngredients();

        block.hoveredIngredient = null;

        if (mouseX >= 0 && mouseX < 166 && mouseY >= -4 && mouseY < 57) {
            handleLeftClicking(block, mouseX, mouseY);
        } else {
            block.sliderGrip = false;
        }

        SLIDER.render(stack, SLIDER_PLACE_X, SLIDER_PLACE_Y_START + (float)block.sliderOffset, 0.0F);

        int x = xOffset, y = yOffset;
        int lineWidth = 0;
        String countString;
        for (final LazyIngredient ingredient : lazyIngredients) {
            countString = ingredient.getCountDisplay();
            final int countWidth = Minecraft.getInstance().font.width(countString);

            if (lineWidth > MAX_X_WIDTH) {
                x = xOffset;
                y += STEP_OFFSET;
                lineWidth = 0;
            }

            lineWidth += countWidth;

            x += Mth.clamp(countWidth, 18, 128);
        }

        final int allSteps = y / STEP_OFFSET;
        final int renderStartStep = (int)(block.sliderOffset / (double) (SLIDER_PLACE_Y_END - SLIDER_PLACE_Y_START) * allSteps);
        boolean shouldRender = renderStartStep == 0;
        y = yOffset;
        x = xOffset;
        lineWidth = 0;

        for (final LazyIngredient ingredient : lazyIngredients) {
            countString = ingredient.getCountDisplay();
            final int countWidth = Minecraft.getInstance().font.width(countString);
            final int countWidthHalf = countWidth / 2;

            if (lineWidth > MAX_X_WIDTH) {
                x = xOffset;
                y += STEP_OFFSET;
                lineWidth = 0;
                int currentStep = y / STEP_OFFSET;
                shouldRender = currentStep == renderStartStep || currentStep == renderStartStep + 1;
            }

            x += Mth.clamp(countWidthHalf, 9, 64);

            if (shouldRender) {
                final MultiBufferSource.BufferSource buffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
                final ItemStack screenItemStack = new ItemStack(ingredient.screenItem());
                final int renderYStart = y - renderStartStep * STEP_OFFSET;
                final int renderXStart = x - 9;

                if (mouseX >= renderXStart && mouseX < renderXStart + 16 && mouseY >= renderYStart && mouseY < renderYStart + 16) {
                    block.hoveredIngredient = ingredient;
                }

                RenderUtils.renderItemModelIntoGUI(stack, screenItemStack, renderXStart, renderYStart, 0x0000FF);

                font.drawInBatch(countString, x - font.width(countString) / 2, renderYStart + 18, 0xFFFFFF, true, stack.last().pose(), buffer, false, 0, 0x0000FF);

                buffer.endBatch();
            }
            lineWidth += countWidth;
            x += Mth.clamp(countWidthHalf, 9, 64);
        }
    }

    @Override
    public List<Component> getTooltipStrings(LazyRecipe recipe, double mouseX, double mouseY) {
        LazyIngredient ingredient = getRenderData(recipe).hoveredIngredient;
        return ingredient == null ? Collections.emptyList() : ingredient.tooltip(TooltipFlag.Default.ADVANCED);
    }

    public void handleLeftClicking(RenderData block, double localCursorX, double localCursorY) {
        final boolean clicking = GLFW.glfwGetMouseButton(Minecraft.getInstance().getWindow().getWindow(), 0) == 1;


        if (clicking) {
            if (block.sliderGrip) {
                final double yDiffToPreviousClick = localCursorY - block.prevClickY;
                block.sliderOffset = Mth.clamp(block.sliderOffset + yDiffToPreviousClick, 0, SLIDER_PLACE_Y_END - SLIDER_PLACE_Y_START);
            } else if (insideSliderBounds(localCursorX, localCursorY)) {
                block.sliderOffset = Mth.clamp(localCursorY - SLIDER_PLACE_Y_START - 3, 0, SLIDER_PLACE_Y_END - SLIDER_PLACE_Y_START);
            }

        }

        if (clicking) {
            block.prevClickY = localCursorY;
            if (!block.sliderGrip && insideSliderBounds(localCursorX, localCursorY)) {
                block.sliderGrip = true;
            }
        } else {
            block.sliderGrip = false;
        }
    }

    public boolean insideSliderBounds(double localCursorX, double localCursorY) {
        return localCursorX >= SLIDER_PLACE_X &&
                localCursorX < SLIDER_PLACE_X + 5 &&
                localCursorY >= SLIDER_PLACE_Y_START &&
                localCursorY < SLIDER_PLACE_Y_END;
    }

    @Override
    public boolean handleInput(LazyRecipe recipe, double mouseX, double mouseY, InputConstants.Key input) {
        return false;
    }

    @Override
    public void setIngredients(LazyRecipe recipe, IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, recipe.getIngredients().stream().map(LazyIngredient::getRelevantIemStacks).collect(Collectors.toList()));
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResult());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, LazyRecipe recipe, IIngredients ingredients) {
        final List<LazyIngredient> lazyIngredients = recipe.getIngredients();
        final int inputSize = lazyIngredients.size();
        recipeLayout.getItemStacks().init(inputSize, false, 144, 18);
        recipeLayout.getItemStacks().set(inputSize, recipe.getResult().copy());
    }

    @Override
    public ResourceLocation getUid() {
        return ID;
    }

    @Override
    public Class<? extends LazyRecipe> getRecipeClass() {
        return LazyRecipe.class;
    }

    @Override
    public Component getTitle() {
        return this.name;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    public RenderData getRenderData(LazyRecipe recipe) {
        int index = recipe.getIndex();

        if (index >= 0) {
            if (this.data[index] == null) {
                this.data[index] = new RenderData();
            }
            return this.data[index];
        } else {
            return this.fallbackData;
        }
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    private static class RenderData {
        private double sliderOffset;
        private double prevClickY;
        private boolean sliderGrip;
        @Nullable
        private LazyIngredient hoveredIngredient;
    }
}
