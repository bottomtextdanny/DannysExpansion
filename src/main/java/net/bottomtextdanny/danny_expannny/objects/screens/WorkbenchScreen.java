package net.bottomtextdanny.danny_expannny.objects.screens;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.bottomtextdanny.braincell.mod.screen.*;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.LazyCraftScreen;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.WorkbenchContainer;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe.LazyCraftState;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe.LazyIngredient;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe.LazyRecipe;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe.SolvedLazyRecipe;
import net.bottomtextdanny.danny_expannny.rendering.RenderUtils;
import net.bottomtextdanny.danny_expannny.network.clienttoserver.MSGSetLazyCraftResult;
import net.bottomtextdanny.danny_expannny.network.clienttoserver.MSGUpdateLazyCraftInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class WorkbenchScreen extends LazyCraftScreen<WorkbenchContainer> {
    private static final int INGREDIENT_DISPLAY_WIDTH = 100;
    private static final int FILTER_X = 195;
    private static final int FILTER_Y = 14;
    private static final int SLIDER_START_X = 222;
    private static final int SLIDER_START_Y = 67;
    private static final ImageData SCREEN_IMAGE = new ImageData("dannys_expansion", "textures/gui/container/workbench.png", 512, 512);
    private static final Blitty GUI = new Blitty(SCREEN_IMAGE, 0, 0, 240, 238);
    private static final Blitty[] RECIPES = {
            new Blitty(SCREEN_IMAGE, 429, 1, 18, 18),
            new Blitty(SCREEN_IMAGE, 429, 20, 18, 18),
            new Blitty(SCREEN_IMAGE, 429, 39, 18, 18)
    };
    private static final Blitty RECIPE_SELECTED = new Blitty(SCREEN_IMAGE, 429, 58, 18, 18);
    private static final Blitty[] RECIPE_SLIDERS = {
            new Blitty(SCREEN_IMAGE, 473, 1, 7, 6),
            new Blitty(SCREEN_IMAGE, 473, 8, 7, 6)
    };
    private static final Blitty[] FILTERS = {
            new Blitty(SCREEN_IMAGE, 481, 1, 30, 30),
            new Blitty(SCREEN_IMAGE, 481, 32, 30, 30)
    };
    private static final Blitty[] INGREDIENT_BUTTONS = {
            new Blitty(SCREEN_IMAGE, 421, 1, 7, 5),
            new Blitty(SCREEN_IMAGE, 421, 7, 7, 5),
            new Blitty(SCREEN_IMAGE, 421, 13, 7, 5),
            new Blitty(SCREEN_IMAGE, 421, 19, 7, 5),
            new Blitty(SCREEN_IMAGE, 421, 25, 7, 5),
            new Blitty(SCREEN_IMAGE, 421, 31, 7, 5)
    };
    private static final BlittyStepDown CUTE_HAMMER_STREAM = new BlittyStepDown(SCREEN_IMAGE, 448, 1, 24, 16);
    private int startColumnRender;
   // private SolvedLazyRecipe selectedRecipe;
    private DisplayedRecipe selectedRecipe;
    private Item filter;
    public TranslatableComponent name;
    private final PlayableSprite hammerAnimation;

    public WorkbenchScreen(WorkbenchContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.selectedRecipe = new DisplayedRecipe(LazyRecipe.EMPTY, LazyCraftState.IRRELEVANT);
        this.filter = Items.AIR;
        this.leftPos = this.width / 2 - 120;
        this.topPos = this.height / 2 - 119;
        this.imageWidth = 240;
        this.imageHeight = 238;
        this.hammerAnimation = new PlayableSprite(CUTE_HAMMER_STREAM, 14, 4);
        this.name = new TranslatableComponent("container.workbench");
    }

    protected void renderBg(PoseStack poseStack, float partialTicks, int x, int y) {
        this.renderBackground(poseStack);
        SCREEN_IMAGE.use();
        int widthFromCenter = this.width / 2;
        int heightFromCenter = this.height / 2;
        GUI.render(poseStack, widthFromCenter - 120, heightFromCenter - 119, 0.0F);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.25F);
        this.hammerAnimation.render(poseStack, widthFromCenter + 82, heightFromCenter + 96, 0.0F);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        if (this.filter != Items.AIR) {
            FILTERS[0].render(poseStack, widthFromCenter + 75, heightFromCenter + -105, 0.0F);
            RenderUtils.renderItemModelIntoGUI(poseStack, this.filter.getDefaultInstance(), widthFromCenter + 82, heightFromCenter - 98, 255);
        } else {
            FILTERS[1].render(poseStack, widthFromCenter + 75, heightFromCenter + -105, 0.0F);
        }
    }

    @Override
    protected void init() {
        super.init();
        processButtons();
    }

    protected void renderLabels(PoseStack poseStack, int x, int y) {}

    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(poseStack, mouseX, mouseY, partialTicks);
        TooltipFlag tooltipFlag = Minecraft.getInstance().options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL;
        this.renderNames(poseStack);
        if (this.selectedRecipe.getRecipe() != LazyRecipe.EMPTY) {
            int x = this.width / 2 - 66;
            int y = this.height / 2 - 94;
            this.renderIngredientsForRecipe(poseStack, mouseX, mouseY, tooltipFlag, x, y);
            this.renderSelectedRecipe(poseStack, mouseX, mouseY, tooltipFlag);
        }

        if (this.selectedRecipe.shouldRenderButtons()) {
            renderIngredientButtons(poseStack, mouseX, mouseY);
        }

        if (this.getSlotUnderMouse() != null && this.getSlotUnderMouse().getItem() != ItemStack.EMPTY) {
            this.renderComponentTooltip(poseStack, this.getSlotUnderMouse().getItem().getTooltipLines(this.minecraft.player, tooltipFlag), mouseX, mouseY);
        }

        Iterator<Widget> renderablesIterator = this.renderables.iterator();

        while(renderablesIterator.hasNext()) {
            Widget button = renderablesIterator.next();
            if (button instanceof AbstractWidget) {
                AbstractWidget widget = (AbstractWidget)button;
                if (widget.isMouseOver(mouseX, mouseY)) {
                    widget.renderToolTip(poseStack, mouseX, mouseY);
                }
            }
        }

        if (this.isMouseOverFilter(mouseX, mouseY) && this.filter != Items.AIR) {
            this.renderComponentTooltip(poseStack, this.filter.getDefaultInstance().getTooltipLines(this.minecraft.player, tooltipFlag), mouseX, mouseY);
        }
    }

    private void renderIngredientButtons(PoseStack poseStack, int mouseX, int mouseY) {
        final int widthFromCenter = this.width / 2;
        final int heightFromCenter = this.height / 2;

        SCREEN_IMAGE.use();
        int upIndex = 0, downIndex = 3;
        if (this.selectedRecipe.canGoUp()) {
            if (isMouseOver(mouseX, mouseY, widthFromCenter + 70, heightFromCenter - 90, 7, 5)) {
                upIndex++;
            }
        } else {
            upIndex += 2;
        }
        if (this.selectedRecipe.canGoDown()) {
            if (isMouseOver(mouseX, mouseY, widthFromCenter + 70, heightFromCenter - 83, 7, 5)) {
                downIndex++;
            }
        } else {
            downIndex += 2;
        }

        INGREDIENT_BUTTONS[upIndex].render(poseStack, widthFromCenter + 70, heightFromCenter - 90, 0.0F);
        INGREDIENT_BUTTONS[downIndex].render(poseStack, widthFromCenter + 70, heightFromCenter - 83, 0.0F);
    }

    public void onOutputDrawn(ItemStack drawnItemStack) {
        if (drawnItemStack != ItemStack.EMPTY) {
            this.hammerAnimation.reset();
        }
    }

    private void renderSelectedRecipe(PoseStack poseStack, int mouseX, int mouseY, TooltipFlag tooltipFlag) {
        if (this.selectedRecipe.getState() != LazyCraftState.CRAFTABLE) {
            RenderUtils.renderItemModelIntoGUI(poseStack, this.selectedRecipe.getRecipe().getResult(), this.width / 2 - 90, this.height / 2 - 90, 128);
            RenderUtils.renderItemOverlayIntoGUI(this.font, this.selectedRecipe.getRecipe().getResult(), this.width / 2 - 90, this.height / 2 - 90, null, 8421504, 0);
            if (this.isMouseOver(mouseX, mouseY, this.width / 2 - 90, this.height / 2 - 90, 16, 16)) {
                this.renderComponentTooltip(poseStack, this.selectedRecipe.getRecipe().getResult().getTooltipLines(this.minecraft.player, tooltipFlag), mouseX, mouseY);
            }

        }

    }

    private void renderIngredientsForRecipe(PoseStack poseStack, int mouseX, int mouseY, TooltipFlag tooltipFlag, int x, int y) {
        String countText;
        for (XSpacedIngredient displayed : this.selectedRecipe.getLineIngredientsRenderCache()) {
            countText = displayed.ingredient.getCountDisplay();
            ItemStack stack = new ItemStack(displayed.ingredient.screenItem());
            Minecraft.getInstance().getItemRenderer().renderGuiItem(stack, x + displayed.xPos, y);
            MultiBufferSource.BufferSource buffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
            this.font.drawInBatch(countText, x + displayed.xPos + 8 - displayed.textWidthOffset, y + 16, displayed.ingredient.getCountDisplayColor(), true, poseStack.last().pose(), buffer, false, 0, 15728880);
            buffer.endBatch();
            if (this.isMouseOver(mouseX, mouseY, x + displayed.xPos, y, 16, 16)) {
                this.renderComponentTooltip(poseStack, displayed.ingredient.tooltip(tooltipFlag), mouseX, mouseY);
            }
        }
    }

    private void renderNames(PoseStack poseStack) {
        String filterStr = new TranslatableComponent("container.workbench.filter").getString();
        MultiBufferSource.BufferSource renderTypeBuffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        this.font.drawInBatch(this.inventory.getDisplayName(), this.width / 2 - 80, this.height / 2 + 24, 4210752, false, poseStack.last().pose(), renderTypeBuffer, false, 0, 15728880);
        this.font.drawInBatch(this.name.getString(), this.width / 2 - 104, this.height / 2 - 110, 4210752, false, poseStack.last().pose(), renderTypeBuffer, false, 0, 15728880);
        this.font.drawInBatch(filterStr, this.width / 2 + 91 - this.font.width(filterStr) / 2, this.height / 2 - 109, this.filter == Items.AIR ? 4210752 : 16777088, false, poseStack.last().pose(), renderTypeBuffer, false, 0, 15728880);
        renderTypeBuffer.endBatch();
    }

    public void containerTick() {
        super.containerTick();
        if (this.timesInventoryChanged != this.inventory.getTimesChanged()) {
            new MSGUpdateLazyCraftInventory().sendToServer();
            this.menu.onInventoryChange();
            this.processButtons();

        }

        if (this.timesInventoryChanged != this.inventory.getTimesChanged() || this.selectedRecipe.getRecipe() != this.menu.getResultRecipe()) {
            if (this.selectedRecipe.getState() == LazyCraftState.CRAFTABLE) {
                new MSGSetLazyCraftResult(this.selectedRecipe.getRecipe().getIndex()).sendToServer();
                this.menu.setResultRecipe(this.selectedRecipe.getRecipe());
                this.menu.makeResultItemstack();
            } else {
                new MSGSetLazyCraftResult(LazyRecipe.EMPTY.getIndex()).sendToServer();
                this.menu.setResultRecipe(LazyRecipe.EMPTY);
            }
        }

        this.timesInventoryChanged = this.inventory.getTimesChanged();
    }

    public boolean isMouseOver(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

    public boolean isMouseOverFilter(double mouseX, double mouseY) {
        return mouseX >= this.width / 2.0D + 81.0D && mouseY >= this.height / 2.0D - 99.0D && mouseX < this.width / 2 + 81 + 18 && mouseY < this.height / 2 - 99 + 18;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        int widthFromCenter = this.width / 2;
        int heightFromCenter = this.height / 2;
        if (this.isMouseOverFilter(mouseX, mouseY)) {
            Item filterBefore = this.filter;
            ItemStack refStack = this.menu.getCarried();
            if (refStack != null && refStack != ItemStack.EMPTY) {
                if (button == 0 && this.filter != refStack.getItem()) {
                    this.filter = refStack.getItem();
                } else {
                    this.filter = Items.AIR;
                }
            } else {
                this.filter = Items.AIR;
            }

            if (filterBefore != this.filter) {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                new MSGUpdateLazyCraftInventory().sendToServer();
                this.menu.onInventoryChange();
                this.processButtons();
            }
        }
        if (this.selectedRecipe.shouldRenderButtons()) {
            if (isMouseOver(mouseX, mouseY, widthFromCenter + 70, heightFromCenter - 90, 7, 5) && this.selectedRecipe.canGoUp()) {
                this.selectedRecipe.goUp();
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            } else if (isMouseOver(mouseX, mouseY, widthFromCenter + 70, heightFromCenter - 83, 7, 5) && this.selectedRecipe.canGoDown()) {
                this.selectedRecipe.goDown();
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }
        }


        return super.mouseReleased(mouseX, mouseY, button);
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        WorkbenchContainer container = this.menu;
        int size = container.getCraftableRecipes().size() + container.getRelevantRecipes().size() + container.getIrrelevantRecipes().size() - 3;
        if (delta < 0.0D && this.startColumnRender + 3 < size / 11.0F) {
            ++this.startColumnRender;
            this.processButtons();
        } else if (delta > 0.0D && this.startColumnRender > 0) {
            --this.startColumnRender;
            this.processButtons();
        }

        return false;
    }

    public void processButtons() {
        List<SolvedLazyRecipe> allRecipes = Lists.newArrayList(this.menu.getCraftableRecipes());
        allRecipes.addAll(this.menu.getRelevantRecipes());
        allRecipes.addAll(this.menu.getIrrelevantRecipes());
        if (this.filter != Items.AIR) {
            allRecipes = allRecipes.stream().filter(p -> p.recipe().getIngredients().stream().anyMatch(objIngredient -> objIngredient.filter(this.filter))).collect(Collectors.toList());
        }

        List<SolvedLazyRecipe> renderedRecipes = allRecipes.subList(Math.max(0, this.startColumnRender * 11), Math.min(this.startColumnRender * 11 + 44, allRecipes.size()));
        int xOffset = 0;
        int yOffset = 1;
        int counter = 0;
        this.renderables.clear();
        this.children().clear();
        LazyRecipe selectedRecipe0 = this.selectedRecipe.getRecipe();
        this.selectedRecipe = new DisplayedRecipe(LazyRecipe.EMPTY, LazyCraftState.IRRELEVANT);

        Iterator<SolvedLazyRecipe> recipeIterator;
        for(recipeIterator = renderedRecipes.iterator(); recipeIterator.hasNext(); ++counter) {
            SolvedLazyRecipe recipe;
            for(recipe = recipeIterator.next(); yOffset * 10 < xOffset; xOffset -= 11) {
                ++yOffset;
            }

            if (recipe.recipe() == selectedRecipe0) {
                this.selectedRecipe = new DisplayedRecipe(recipe.recipe(), recipe.state());
            }

            WorkbenchScreen.RecipeButton button = new WorkbenchScreen.RecipeButton(this.width / 2 - 99 + counter % 11 * 18, this.height / 2 - 34 + (Mth.floor(counter / 11.0F) - 1) * 18, recipe);
            this.addRenderableWidget(button);
            ++xOffset;
        }
        Iterator<LazyIngredient> ingredientIterator;

        if (this.selectedRecipe.getRecipe().getIngredients() != null) {
            ingredientIterator = this.selectedRecipe.getRecipe().getIngredients().iterator();

            while(ingredientIterator.hasNext()) {
                LazyIngredient ingredient = ingredientIterator.next();
                ingredient.onScreenModification(this.inventory);
            }
        }
    }

    class RecipeButton extends AbstractButton {
        private final SolvedLazyRecipe taggedRecipe;

        protected RecipeButton(int x, int y, SolvedLazyRecipe recipe) {
            super(x, y, 18, 18, TextComponent.EMPTY);
            this.taggedRecipe = recipe;
        }

        public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
            WorkbenchScreen.SCREEN_IMAGE.use();
            WorkbenchScreen.RECIPES[this.taggedRecipe.state().ordinal()].render(poseStack, this.x, this.y, 0.0F);
            if (this.isSelected()) {
                WorkbenchScreen.RECIPE_SELECTED.render(poseStack, this.x, this.y, 999.0F);
            }

            if (this.taggedRecipe.state() == LazyCraftState.CRAFTABLE) {
                RenderUtils.renderItemModelIntoGUI(poseStack, this.taggedRecipe.recipe().getResult(), this.x + 1, this.y + 1, 255);
                RenderUtils.renderItemOverlayIntoGUI(Minecraft.getInstance().font, this.taggedRecipe.recipe().getResult(), this.x + 1, this.y + 1, String.valueOf(this.taggedRecipe.recipe().getResult().getCount()), 16777215, 16777215);
            } else if (this.taggedRecipe.state() == LazyCraftState.RELEVANT) {
                RenderUtils.renderItemModelIntoGUI(poseStack, this.taggedRecipe.recipe().getResult(), this.x + 1, this.y + 1, 195);
                RenderUtils.renderItemOverlayIntoGUI(Minecraft.getInstance().font, this.taggedRecipe.recipe().getResult(), this.x + 1, this.y + 1, String.valueOf(this.taggedRecipe.recipe().getResult().getCount()), 12632256, 16777215);
            } else {
                RenderUtils.renderItemModelIntoGUI(poseStack, this.taggedRecipe.recipe().getResult(), this.x + 1, this.y + 1, 63);
                RenderUtils.renderItemOverlayIntoGUI(Minecraft.getInstance().font, this.taggedRecipe.recipe().getResult(), this.x + 1, this.y + 1, String.valueOf(this.taggedRecipe.recipe().getResult().getCount()), 4210752, 16777215);
            }

        }

        public void renderToolTip(PoseStack poseStack, int mouseX, int mouseY) {
            TooltipFlag.Default tooltipFlag = Minecraft.getInstance().options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL;
            WorkbenchScreen.this.renderComponentTooltip(poseStack, this.taggedRecipe.recipe().getResult().getTooltipLines(WorkbenchScreen.this.minecraft.player, tooltipFlag), this.x + 10, this.y - 11);
            super.renderToolTip(poseStack, mouseX, mouseY);
        }

        public boolean isSelected() {
            return this.taggedRecipe.recipe() == WorkbenchScreen.this.selectedRecipe.getRecipe();
        }

        public void setSelected() {
            WorkbenchScreen.this.selectedRecipe = new DisplayedRecipe(this.taggedRecipe.recipe(), this.taggedRecipe.state());
        }

        public boolean isMouseOver(double mouseX, double mouseY) {
            return super.isMouseOver(mouseX, mouseY);
        }

        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (this.active && this.visible && this.isValidClickButton(button)) {
                boolean flag = this.clicked(mouseX, mouseY);
                if (flag) {
                    if (!this.isSelected()) {
                        this.playDownSound(Minecraft.getInstance().getSoundManager());
                        this.onClick(mouseX, mouseY);
                    }

                    return true;
                }
            }

            return false;
        }

        public void onPress() {
            if (!this.isSelected()) {
                this.setSelected();
                if (WorkbenchScreen.this.selectedRecipe.getRecipe().getIngredients() != null) {
                    for (LazyIngredient ingredient : WorkbenchScreen.this.selectedRecipe.getRecipe().getIngredients()) {
                        ingredient.onScreenModification(WorkbenchScreen.this.inventory);
                    }
                }
            }

        }

        public void updateNarration(NarrationElementOutput p_169152_) {
        }
    }

    private static class DisplayedRecipe {
        private final LazyRecipe recipe;
        private final LazyCraftState state;
        private final int allLines;
        private int ingredientLineIndex;
        private List<XSpacedIngredient> lineIngredientsRenderCache;

        public DisplayedRecipe(LazyRecipe recipe, LazyCraftState state) {
            super();
            final Font font = Minecraft.getInstance().font;
            ImmutableList.Builder<XSpacedIngredient> initialLineCacheBuilder = ImmutableList.builder();
            this.recipe = recipe;
            this.state = state;
            int lines = 0;
            if (recipe != LazyRecipe.EMPTY) {
                String countText;
                int x = 0;
                for(LazyIngredient ingredient : this.recipe.getIngredients()) {
                    countText = ingredient.getCountDisplay();
                    final int countTextWidth = font.width(countText);
                    final int countTextWidthHalf = countTextWidth / 2;
                    if (x > INGREDIENT_DISPLAY_WIDTH) {
                        x = 0;
                        lines++;
                    }
                    x += Mth.clamp(countTextWidthHalf, 9, 64);

                    if (lines == 0) {
                        initialLineCacheBuilder.add(new XSpacedIngredient(ingredient, x, countTextWidthHalf));
                    } else {
                        break;
                    }
                    x += Mth.clamp(countTextWidthHalf + 1, 9, 64);
                }
            }
            this.lineIngredientsRenderCache = initialLineCacheBuilder.build();
            this.allLines = lines;
        }

        private List<XSpacedIngredient> getIngredientsForLine(int lineIndex) {
            final Font font = Minecraft.getInstance().font;
            int lines = 0;
            if (this.recipe != LazyRecipe.EMPTY) {
                ImmutableList.Builder<XSpacedIngredient> builder = ImmutableList.builder();
                String countText;
                int x = 0;
                for(LazyIngredient ingredient : this.recipe.getIngredients()) {
                    countText = ingredient.getCountDisplay();
                    final int countTextWidth = font.width(countText);
                    final int countTextWidthHalf = countTextWidth / 2;
                    if (x > INGREDIENT_DISPLAY_WIDTH) {
                        x = 0;
                        lines++;
                    }
                    x += Mth.clamp(countTextWidthHalf, 9, 64);

                    if (lines == lineIndex) {
                        builder.add(new XSpacedIngredient(ingredient, x, countTextWidthHalf));
                    } else if (lines > lineIndex) {
                        break;
                    }
                    x += Mth.clamp(countTextWidthHalf + 1, 9, 64);
                }
                return builder.build();
            } else {
                return ImmutableList.of();
            }
        }

        public boolean canGoUp() {
            return this.ingredientLineIndex > 0;
        }

        public boolean canGoDown() {
            return this.ingredientLineIndex <= this.allLines;
        }

        public boolean shouldRenderButtons() {
            return this.allLines > 0;
        }

        public void goUp() {
            this.ingredientLineIndex--;
            this.lineIngredientsRenderCache = getIngredientsForLine(this.ingredientLineIndex);
        }

        public void goDown() {
            this.ingredientLineIndex++;
            this.lineIngredientsRenderCache = getIngredientsForLine(this.ingredientLineIndex);
        }

        public LazyCraftState getState() {
            return this.state;
        }

        public LazyRecipe getRecipe() {
            return this.recipe;
        }

        public List<XSpacedIngredient> getLineIngredientsRenderCache() {
            return this.lineIngredientsRenderCache;
        }
    }

    record XSpacedIngredient(LazyIngredient ingredient, int xPos, int textWidthOffset) {}
}
