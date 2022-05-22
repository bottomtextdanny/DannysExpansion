package bottomtextdanny.dannys_expansion.content._client.screens.lazy_workstation;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.LazyRecipeManager;
import bottomtextdanny.dannys_expansion._base.network.clienttoserver.MSGSetLazyCraftResult;
import bottomtextdanny.dannys_expansion._base.network.clienttoserver.MSGUpdateLazyCraftInventory;
import bottomtextdanny.dannys_expansion.content.containers.lazy_workstation.base.DefaultLazyCraftMenu;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.LazyCraftState;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.LazyIngredient;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.LazyRecipe;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.SolvedLazyRecipe;
import bottomtextdanny.dannys_expansion._util._client.RenderUtils;
import bottomtextdanny.braincell.mod.gui.EditableText;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import bottomtextdanny.braincell.mod.screen.BlittyStepDown;
import bottomtextdanny.braincell.base.screen.ImageData;
import bottomtextdanny.braincell.mod.screen.PlayableSprite;
import bottomtextdanny.braincell.mod._base.blitty.Blitty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.player.LocalPlayer;
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

public class DefaultLazyCraftScreen<E extends DefaultLazyCraftMenu> extends LazyCraftScreen<E> {
    private static final int INGREDIENT_DISPLAY_WIDTH = 100;
    private static final int FILTER_X = 195;
    private static final int FILTER_Y = 14;
    private static final int SLIDER_START_X = 222;
    private static final int SLIDER_START_Y = 67;
    private static final int SLIDER_END_X = 229;
    private static final int SLIDER_END_Y = 138;
    private static final int SLIDER_Y_LENGTH = SLIDER_END_Y - SLIDER_START_Y;
    private static final ImageData SCREEN_IMAGE = new ImageData(DannysExpansion.ID, "blank.png", 512, 512);
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
    private static final TranslatableComponent SEARCH_BAR_TITLE = new TranslatableComponent("container.lazy_workstation.search_bar");
    public static final TranslatableComponent FILTER_TITLE = new TranslatableComponent("container.lazy_workstation.filter");
    private static final BlittyStepDown CUTE_HAMMER_STREAM = new BlittyStepDown(SCREEN_IMAGE, 448, 1, 24, 16);
    private final PlayableSprite hammerAnimation;
    private final ImageData image;
    public final TranslatableComponent name;
    private EditableText searchBar;
    private int searchBarColor;
    private float sliderAdvance;
    private boolean sliderGrip;
    private DefaultLazyCraftScreen.DisplayedRecipe selectedRecipe;
    private Item filter;

    public DefaultLazyCraftScreen(E screenContainer, TranslatableComponent name, ImageData image, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.image = image;
        hammerAnimation = new PlayableSprite(CUTE_HAMMER_STREAM, 14, 4);
        this.name = name;
        imageWidth = 240;
        imageHeight = 238;
        selectedRecipe = new DefaultLazyCraftScreen.DisplayedRecipe(LazyRecipe.EMPTY, LazyCraftState.IRRELEVANT);
        filter = Items.AIR;
        leftPos = width / 2 - 120;
        topPos = height / 2 - 119;
        searchBarColor = 0xFFFFFFFF;
    }
    
    @Override
    protected void init() {
        super.init();

        searchBar = new EditableText(font, leftPos + 21, topPos + 67, 198, SEARCH_BAR_TITLE) {
            @Override
            public boolean shouldBeDeselected(int x, int y) {
                return !inbounds(x, y);
            }
        };
        searchBar.setColorProcessor(str -> searchBarColor);
        searchBar.onTextModification(str -> {
            searchBarColor = processButtons();
            searchBar.updateColor();
        });
        addRenderableWidget(searchBar);

        processButtons();
    }

    public int processButtons() {
        String searchFilter = searchBar.getCurrentText();
        List<SolvedLazyRecipe> allRecipes = Lists.newArrayList(menu.getCraftableRecipes());

        allRecipes.addAll(menu.getRelevantRecipes());
        allRecipes.addAll(menu.getIrrelevantRecipes());

        LazyRecipe selectedRecipeO = selectedRecipe.getRecipe();

        selectedRecipe = new DefaultLazyCraftScreen.DisplayedRecipe(LazyRecipe.EMPTY, LazyCraftState.IRRELEVANT);

        if (filter != Items.AIR) {
            allRecipes = allRecipes.stream()
                    .filter(p -> {
                        LazyRecipe recipe = p.recipe();
                        boolean filtering = recipe.getIngredients().stream().anyMatch(objIngredient -> objIngredient.filter(filter));
                        return filtering;
                    })
                    .toList();
        }

        boolean emptySearch = false;

        allRecipes.forEach(p -> {
            LazyRecipe recipe = p.recipe();
            if (recipe.getIndex() == selectedRecipeO.getIndex()) {
                selectedRecipe = new DefaultLazyCraftScreen.DisplayedRecipe(recipe, p.state());
            }
        });

        if (!searchFilter.isEmpty()) {
            emptySearch = allRecipes.isEmpty();
            List<SolvedLazyRecipe> o = allRecipes;

            allRecipes = allRecipes.stream()
                    .filter(p -> {
                        ItemStack result = p.recipe().getResult();
                        return result.getItem().getRegistryName().getPath().toString().contains(searchFilter)
                                || result.getItem().getName(result).getString().contains(searchFilter);
                    })
                    .toList();

            if (allRecipes.isEmpty()) {
                emptySearch = true;
                allRecipes = o;
            }
        }

        int startColumnRender = (int) (sliderAdvance / allRecipes.size() / 11);

        List<SolvedLazyRecipe> renderedRecipes = allRecipes.subList(Math.max(0, startColumnRender * 11), Math.min(startColumnRender * 11 + 33, allRecipes.size()));

        int xOffset = 0;
        int yOffset = 1;
        int counter = 0;

        children().clear();
        renderables.clear();

        Iterator<SolvedLazyRecipe> recipeIterator;

        for(recipeIterator = renderedRecipes.iterator(); recipeIterator.hasNext(); ++counter) {
            SolvedLazyRecipe recipe;

            for(recipe = recipeIterator.next(); yOffset * 10 < xOffset; xOffset -= 11) {
                ++yOffset;
            }

            DefaultLazyCraftScreen<?>.RecipeButton button = new DefaultLazyCraftScreen<?>.RecipeButton(leftPos + 21 + counter % 11 * 18, topPos + 83 + (counter / 11) * 18, recipe);

            addRenderableWidget(button);

            ++xOffset;
        }

        Iterator<LazyIngredient> ingredientIterator;

        if (selectedRecipe.getRecipe().getIngredients() != null) {
            ingredientIterator = selectedRecipe.getRecipe().getIngredients().iterator();

            while(ingredientIterator.hasNext()) {
                LazyIngredient ingredient = ingredientIterator.next();

                ingredient.onScreenModification(inventory);
            }
        }

        if (searchFilter.isEmpty()) return 0xFFFFFFFF;
        return allRecipes.isEmpty() || emptySearch ? 0xFF454590 : 0xFF20FF00;
    }
    
    protected void renderBg(PoseStack poseStack, float partialTicks, int x, int y) {
        renderBackground(poseStack);

        int widthFromCenter = width / 2;
        int heightFromCenter = height / 2;

        image.use();
        GUI.render(poseStack, widthFromCenter - 120, heightFromCenter - 119, 0.0F);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.25F);
        hammerAnimation.render(poseStack, widthFromCenter + 82, heightFromCenter + 96, 0.0F);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        if (filter != Items.AIR) {
            FILTERS[0].render(poseStack, widthFromCenter + 75, heightFromCenter + -105, 0.0F);
            RenderUtils.renderItemModelIntoGUI(poseStack, filter.getDefaultInstance(), widthFromCenter + 82, heightFromCenter - 98, 255);
        } else FILTERS[1].render(poseStack, widthFromCenter + 75, heightFromCenter + -105, 0.0F);

        float sliderYOffset = (SLIDER_Y_LENGTH - 6) * sliderAdvance;

        RECIPE_SLIDERS[0].render(poseStack, leftPos + SLIDER_START_X, topPos + SLIDER_START_Y + sliderYOffset, 1.0F);
    }

    protected void renderLabels(PoseStack poseStack, int x, int y) {}

    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(poseStack, mouseX, mouseY, partialTicks);

        TooltipFlag tooltipFlag = Minecraft.getInstance().options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL;
        LocalPlayer player = minecraft.player;

        renderNames(poseStack);

        if (selectedRecipe.getRecipe() != LazyRecipe.EMPTY) {
            int x = width / 2 - 66;
            int y = height / 2 - 94;
            renderIngredientsForRecipe(poseStack, mouseX, mouseY, tooltipFlag, x, y);
            renderSelectedRecipe(poseStack, mouseX, mouseY, tooltipFlag);
        }

        if (selectedRecipe.shouldRenderButtons()) {
            renderIngredientButtons(poseStack, mouseX, mouseY);
        }

        if (getSlotUnderMouse() != null && getSlotUnderMouse().getItem() != ItemStack.EMPTY) {
            renderComponentTooltip(poseStack, getSlotUnderMouse().getItem().getTooltipLines(player, tooltipFlag), mouseX, mouseY);
        }

        for (Widget button : renderables) {
            if (button instanceof AbstractWidget widget) {
                if (widget.isMouseOver(mouseX, mouseY))
                    widget.renderToolTip(poseStack, mouseX, mouseY);
            }
        }

        if (isMouseOverFilter(mouseX, mouseY) && filter != Items.AIR) {
            renderComponentTooltip(poseStack, filter.getDefaultInstance().getTooltipLines(player, tooltipFlag), mouseX, mouseY);
        }
    }

    private void renderIngredientButtons(PoseStack poseStack, int mouseX, int mouseY) {
        final int widthFromCenter = width / 2;
        final int heightFromCenter = height / 2;

        image.use();
        int upIndex = 0, downIndex = 3;

        if (selectedRecipe.canGoUp()) {
            if (isMouseOver(mouseX, mouseY, widthFromCenter + 70, heightFromCenter - 90, 7, 5))
                upIndex++;

        } else upIndex += 2;

        if (selectedRecipe.canGoDown())
            if (isMouseOver(mouseX, mouseY, widthFromCenter + 70, heightFromCenter - 83, 7, 5)) {
                downIndex++;

        } else downIndex += 2;

        INGREDIENT_BUTTONS[upIndex].render(poseStack, widthFromCenter + 70, heightFromCenter - 90, 0.0F);
        INGREDIENT_BUTTONS[downIndex].render(poseStack, widthFromCenter + 70, heightFromCenter - 83, 0.0F);
    }

    public void onOutputDrawn(ItemStack drawnItemStack) {
        if (drawnItemStack != ItemStack.EMPTY)
            hammerAnimation.reset();
    }

    private void renderSelectedRecipe(PoseStack poseStack, int mouseX, int mouseY, TooltipFlag tooltipFlag) {
        if (selectedRecipe.getState() != LazyCraftState.CRAFTABLE) {
            RenderUtils.renderItemModelIntoGUI(poseStack, selectedRecipe.getRecipe().getResult(), width / 2 - 90, height / 2 - 90, 128);
            RenderUtils.renderItemOverlayIntoGUI(font, selectedRecipe.getRecipe().getResult(), width / 2 - 90, height / 2 - 90, null, 8421504, 0);

            if (isMouseOver(mouseX, mouseY, width / 2 - 90, height / 2 - 90, 16, 16))
                renderComponentTooltip(poseStack, selectedRecipe.getRecipe().getResult().getTooltipLines(minecraft.player, tooltipFlag), mouseX, mouseY);
        }
    }

    private void renderIngredientsForRecipe(PoseStack poseStack, int mouseX, int mouseY, TooltipFlag tooltipFlag, int x, int y) {
        String countText;

        for (DefaultLazyCraftScreen.XSpacedIngredient displayed : selectedRecipe.getLineIngredientsRenderCache()) {
            countText = displayed.ingredient.getCountDisplay();
            ItemStack stack = new ItemStack(displayed.ingredient.screenItem());
            MultiBufferSource.BufferSource buffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());

            Minecraft.getInstance().getItemRenderer().renderGuiItem(stack, x + displayed.xPos, y);
            font.drawInBatch(countText, x + displayed.xPos + 8 - displayed.textWidthOffset, y + 16, displayed.ingredient.getCountDisplayColor(), true, poseStack.last().pose(), buffer, false, 0, 15728880);
            buffer.endBatch();

            if (isMouseOver(mouseX, mouseY, x + displayed.xPos, y, 16, 16))
                renderComponentTooltip(poseStack, displayed.ingredient.tooltip(tooltipFlag), mouseX, mouseY);
        }
    }

    private void renderNames(PoseStack poseStack) {
        String filterStr = FILTER_TITLE.getString();
        MultiBufferSource.BufferSource renderTypeBuffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());

        int halfWidth = width / 2;
        int halfHeight = height / 2;

        font.drawInBatch(inventory.getDisplayName(), halfWidth - 80, halfHeight + 24, 4210752, false, poseStack.last().pose(), renderTypeBuffer, false, 0, 15728880);
        font.drawInBatch(name.getString(), halfWidth - 104, halfHeight - 110, 4210752, false, poseStack.last().pose(), renderTypeBuffer, false, 0, 15728880);
        font.drawInBatch(filterStr, halfWidth + 91 - font.width(filterStr) / 2, halfHeight - 109, filter == Items.AIR ? 4210752 : 16777088, false, poseStack.last().pose(), renderTypeBuffer, false, 0, 15728880);
        renderTypeBuffer.endBatch();
    }

    public void containerTick() {
        super.containerTick();

        if (timesInventoryChanged != inventory.getTimesChanged()) {
            new MSGUpdateLazyCraftInventory().sendToServer();
            menu.onInventoryChange();
            processButtons();
        }

        if (timesInventoryChanged != inventory.getTimesChanged() || selectedRecipe.getRecipe() != menu.getResultRecipe()) {
            LazyRecipeManager recipeManager = DannysExpansion.common().getLazyRecipeManager();

            if (selectedRecipe.getState() == LazyCraftState.CRAFTABLE) {
                new MSGSetLazyCraftResult(recipeManager.getTypeId(menu.getRecipeType()), selectedRecipe.getRecipe().getIndex()).sendToServer();
                menu.setResultRecipe(selectedRecipe.getRecipe());
                menu.makeResultItemstack();
            } else if (menu.getResultRecipe() != LazyRecipe.EMPTY) {
                new MSGSetLazyCraftResult(recipeManager.getTypeId(menu.getRecipeType()), LazyRecipe.EMPTY.getIndex()).sendToServer();
                menu.setResultRecipe(LazyRecipe.EMPTY);
                menu.makeResultItemstack();
            }
        }

        timesInventoryChanged = inventory.getTimesChanged();
    }

    public boolean isMouseOver(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

    public boolean isMouseOverFilter(double mouseX, double mouseY) {
        int halfWidth = width / 2;
        int halfHeight = height / 2;

        return mouseX >= halfWidth + 81.0D && mouseY >= halfHeight - 99.0D && mouseX < halfWidth + 81 + 18 && mouseY < halfHeight - 99 + 18;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mod) {
        if (insideSlider(mouseX, mouseY)) sliderGrip = true;
        return super.mouseClicked(mouseX, mouseY, mod);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        int halfWidth = width / 2;
        int halfHeight = height / 2;

        if (sliderGrip) sliderGrip = false;

        if (isMouseOverFilter(mouseX, mouseY)) {
            Item filterBefore = filter;
            ItemStack refStack = menu.getCarried();

            if (refStack != null && refStack != ItemStack.EMPTY) {
                if (button == 0 && filter != refStack.getItem())
                    filter = refStack.getItem();
                else filter = Items.AIR;
            } else filter = Items.AIR;

            if (filterBefore != filter) {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                new MSGUpdateLazyCraftInventory().sendToServer();
                menu.onInventoryChange();
                processButtons();
            }
        }

        if (selectedRecipe.shouldRenderButtons()) {
            if (isMouseOver(mouseX, mouseY, halfWidth + 70, halfHeight - 90, 7, 5) && selectedRecipe.canGoUp()) {
                selectedRecipe.goUp();
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }
            else if (isMouseOver(mouseX, mouseY, halfWidth + 70, halfHeight - 83, 7, 5) && selectedRecipe.canGoDown()) {
                selectedRecipe.goDown();
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }
        }

        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double posX, double posY, int button, double offsetX, double offsetY) {
        if (sliderGrip) {
            posY -= 3;
            int minSliderY = topPos + 67;
            int maxSliderY = topPos + 138 - 6;

            if (posY < minSliderY) sliderAdvance = 0.0F;
            else if (posY >= maxSliderY) sliderAdvance = 1.0F;
            else {
                sliderAdvance = (float) ((posY - minSliderY) / (maxSliderY - minSliderY));
            }
        }
        return super.mouseDragged(posX, posY, button, offsetX, offsetY);
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {

        float old = sliderAdvance;

        sliderAdvance = Mth.clamp(sliderAdvance + (float)delta, 0.0F, 1.0F);

        if (sliderAdvance != old) {
            processButtons();
        }

        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    public boolean insideRecipes(double mouseX, double mouseY) {
        return mouseX >= leftPos + 21 && mouseX < leftPos + 219
                && mouseY >= topPos + 83 && mouseY < topPos + 137;
    }

    public boolean insideSlider(double mouseX, double mouseY) {
        return mouseX >= leftPos + 222 && mouseX < leftPos + 229
                && mouseY >= topPos + 67 && mouseY < topPos + 138;
    }

    class RecipeButton extends AbstractButton {
        private final SolvedLazyRecipe taggedRecipe;

        protected RecipeButton(int x, int y, SolvedLazyRecipe recipe) {
            super(x, y, 18, 18, TextComponent.EMPTY);
            taggedRecipe = recipe;
        }

        public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
            DefaultLazyCraftScreen.this.image.use();
            DefaultLazyCraftScreen.RECIPES[this.taggedRecipe.state().ordinal()].render(poseStack, x, y, 0.0F);
            int brightness, color;

            if (taggedRecipe.state() == LazyCraftState.CRAFTABLE) {
                brightness = 255;
                color = 16777215;
            } else if (taggedRecipe.state() == LazyCraftState.RELEVANT) {
                brightness = 195;
                color = 12632256;
            } else {
                brightness = 63;
                color = 4210752;
            }

            RenderUtils.renderItemModelIntoGUI(poseStack, taggedRecipe.recipe().getResult(), x + 1, y + 1, brightness);
            RenderUtils.renderItemOverlayIntoGUI(Minecraft.getInstance().font, taggedRecipe.recipe().getResult(), x + 1, y + 1, String.valueOf(taggedRecipe.recipe().getResult().getCount()), color, 16777215);

            if (isSelected()) {
                DefaultLazyCraftScreen.this.image.use();
                DefaultLazyCraftScreen.RECIPE_SELECTED.render(poseStack, x, y, -10.0F);
            }
        }

        public void renderToolTip(PoseStack poseStack, int mouseX, int mouseY) {
            TooltipFlag.Default tooltipFlag = Minecraft.getInstance().options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL;
            DefaultLazyCraftScreen.this.renderComponentTooltip(poseStack, taggedRecipe.recipe().getResult().getTooltipLines(DefaultLazyCraftScreen.this.minecraft.player, tooltipFlag), mouseX, mouseY);
            super.renderToolTip(poseStack, mouseX, mouseY);
        }

        public boolean isSelected() {
            return taggedRecipe.recipe().getIndex() == DefaultLazyCraftScreen.this.selectedRecipe.getRecipe().getIndex();
        }

        public void setSelected() {
            DefaultLazyCraftScreen.this.selectedRecipe = new DefaultLazyCraftScreen.DisplayedRecipe(taggedRecipe.recipe(), taggedRecipe.state());
        }

        public boolean isMouseOver(double mouseX, double mouseY) {
            return super.isMouseOver(mouseX, mouseY);
        }

        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (active && visible && isValidClickButton(button)) {
                boolean flag = clicked(mouseX, mouseY);
                if (flag) {
                    if (!this.isSelected()) {
                        playDownSound(Minecraft.getInstance().getSoundManager());
                        onClick(mouseX, mouseY);
                    }

                    return true;
                }
            }

            return false;
        }

        public void onPress() {
            if (!this.isSelected()) {
                setSelected();

                if (DefaultLazyCraftScreen.this.selectedRecipe.getRecipe().getIngredients() != null)
                    for (LazyIngredient ingredient : DefaultLazyCraftScreen.this.selectedRecipe.getRecipe().getIngredients())
                        ingredient.onScreenModification(DefaultLazyCraftScreen.this.inventory);
            }
        }

        public void updateNarration(NarrationElementOutput output) {}
    }

    private static class DisplayedRecipe {
        private final LazyRecipe recipe;
        private final LazyCraftState state;
        private final int allLines;
        private int ingredientLineIndex;
        private List<DefaultLazyCraftScreen.XSpacedIngredient> lineIngredientsRenderCache;

        public DisplayedRecipe(LazyRecipe recipe, LazyCraftState state) {
            super();
            Font font = Minecraft.getInstance().font;
            ImmutableList.Builder<DefaultLazyCraftScreen.XSpacedIngredient> initialLineCacheBuilder = ImmutableList.builder();
            int lines = 0;

            this.recipe = recipe;
            this.state = state;

            if (recipe != LazyRecipe.EMPTY) {
                String countText;
                int x = 0;

                for(LazyIngredient ingredient : recipe.getIngredients()) {
                    countText = ingredient.getCountDisplay();

                    int countTextWidth = font.width(countText);
                    int countTextWidthHalf = countTextWidth / 2;

                    if (x > INGREDIENT_DISPLAY_WIDTH) {
                        x = 0;
                        lines++;
                    }

                    x += Mth.clamp(countTextWidthHalf, 9, 64);

                    if (lines == 0)
                        initialLineCacheBuilder.add(new DefaultLazyCraftScreen.XSpacedIngredient(ingredient, x, countTextWidthHalf));
                    else break;

                    x += Mth.clamp(countTextWidthHalf + 1, 9, 64);
                }
            }

            lineIngredientsRenderCache = initialLineCacheBuilder.build();
            allLines = lines;
        }

        private List<DefaultLazyCraftScreen.XSpacedIngredient> getIngredientsForLine(int lineIndex) {
            Font font = Minecraft.getInstance().font;
            int lines = 0;

            if (recipe != LazyRecipe.EMPTY) {
                ImmutableList.Builder<DefaultLazyCraftScreen.XSpacedIngredient> builder = ImmutableList.builder();
                String countText;
                int x = 0;

                for(LazyIngredient ingredient : recipe.getIngredients()) {
                    countText = ingredient.getCountDisplay();

                    int countTextWidth = font.width(countText);
                    int countTextWidthHalf = countTextWidth / 2;

                    if (x > INGREDIENT_DISPLAY_WIDTH) {
                        x = 0;
                        lines++;
                    }

                    x += Mth.clamp(countTextWidthHalf, 9, 64);

                    if (lines == lineIndex)
                        builder.add(new DefaultLazyCraftScreen.XSpacedIngredient(ingredient, x, countTextWidthHalf));
                    else if (lines > lineIndex) break;

                    x += Mth.clamp(countTextWidthHalf + 1, 9, 64);
                }

                return builder.build();
            }

            return ImmutableList.of();
        }

        public boolean canGoUp() {
            return ingredientLineIndex > 0;
        }

        public boolean canGoDown() {
            return ingredientLineIndex <= allLines;
        }

        public boolean shouldRenderButtons() {
            return allLines > 0;
        }

        public void goUp() {
            ingredientLineIndex--;
            lineIngredientsRenderCache = getIngredientsForLine(ingredientLineIndex);
        }

        public void goDown() {
            ingredientLineIndex++;
            lineIngredientsRenderCache = getIngredientsForLine(ingredientLineIndex);
        }

        public LazyCraftState getState() {
            return state;
        }

        public LazyRecipe getRecipe() {
            return recipe;
        }

        public List<DefaultLazyCraftScreen.XSpacedIngredient> getLineIngredientsRenderCache() {
            return lineIngredientsRenderCache;
        }
    }

    record XSpacedIngredient(LazyIngredient ingredient, int xPos, int textWidthOffset) {}
}
