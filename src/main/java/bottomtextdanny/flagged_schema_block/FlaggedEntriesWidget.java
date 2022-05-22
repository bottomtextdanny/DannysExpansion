package bottomtextdanny.flagged_schema_block;

import bottomtextdanny.braincell.base.BCCharacterUtil;
import bottomtextdanny.braincell.mod.gui.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import bottomtextdanny.braincell.mod.PropertyMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.mutable.MutableInt;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class FlaggedEntriesWidget extends AbstractWidget
        implements BCSelector, BCSelectable, TickableComponent {
    public static int ENTRY_HEIGHT = 32;
    public static int ENTRY_HEIGHT_D2 = ENTRY_HEIGHT / 2;
    public static int ENTRY_SPACING = 2;
    public static int BORDER_WIDTH = 1;
    public static int BORDER_OFFSET = BORDER_WIDTH * 2;
    private static final Component C_TITLE =
            new TranslatableComponent("flagged_schema_maker.entries");
    private static final Component C_DELETE = 
            new TranslatableComponent("flagged_schema_maker.delete");
    private static final Component C_ADD =
            new TranslatableComponent("flagged_schema_maker.add");
    private static final Component C_BLOCK_ID =
            new TranslatableComponent("flagged_schema_maker.block_id");
    private static final Component C_NAME =
            new TranslatableComponent("flagged_schema_maker.name");
    private static final Component C_FLAGS =
            new TranslatableComponent("flagged_schema_maker.flags");
    private static final Component C_PROPS =
            new TranslatableComponent("flagged_schema_maker.properties");
    private final Map<Block, ClientSchemaMakerEntry> usedBlocks;
    private List<ClientSchemaMakerEntry> entries;
    private BCSelector screen;
    private BCSelectable selected;
    private final List<BCSelectable> selectables;
    private final EditableText blockIdBox;
    private final EditableText nameBox;
    private final EditableText flagsBox;
    private final ListingBox propertiesBox;
    private final Button deleteButton;
    private final Button addButton;
    private final int fontHeightD2;
    private int entryPixelOffset;
    private int entryPointer = -1;
    private int newEntryBlockId = 1;

    public FlaggedEntriesWidget(int x, int y,
                                int width, int height,
                                List<ClientSchemaMakerEntry> entries) {
        super(x, y, width, height, C_TITLE);
        this.fontHeightD2 = Minecraft.getInstance().font.lineHeight / 2;
        this.usedBlocks = Maps.newIdentityHashMap();
        setEntries(entries);
        this.blockIdBox = new EditableText(Minecraft.getInstance().font, x + width + 1, 44, width, C_BLOCK_ID);
        this.blockIdBox.setKeyActor((string, key) -> {
            if (isEntrySelected() && key == InputConstants.KEY_RETURN || key == InputConstants.KEY_NUMPADENTER) {
                boolean valid = false;

                try {
                    valid = ResourceLocation.isValidResourceLocation(string);
                } catch (Exception ignored) {}

                if (valid) {
                    ResourceLocation loc = new ResourceLocation(string);
                    Registry.BLOCK.getOptional(loc).ifPresent(block -> {
                        if (!this.usedBlocks.containsKey(block)) {
                            this.usedBlocks.remove(selectedEntry().getBlock());
                            selectedEntry().setBlock(block);
                            this.usedBlocks.put(block, selectedEntry());
                        }
                    });
                }
            }
        });
        this.blockIdBox.setColorProcessor((string) -> {
            boolean valid = false;

            try {
                valid = ResourceLocation.isValidResourceLocation(string);
            } catch (Exception ignored) {}

            if (!valid) return 0xFFFF2000;

            ResourceLocation loc = new ResourceLocation(string);
            if (Registry.BLOCK.containsKey(loc)) {
                Block block = Registry.BLOCK.get(loc);
                return selectedEntry().getBlock() == block ||
                        !this.usedBlocks.containsKey(Registry.BLOCK.get(loc)) ?
                        0xFF20FF00 : 0xFFFF8000;
            }
            return 0xFFFF2000;
        });
        this.blockIdBox.setWordSeparator(BCCharacterUtil.metadataSeparator());
        this.blockIdBox.attachToSelector(this);

        this.nameBox = new EditableText(Minecraft.getInstance().font, x + width + 1, 84, width, C_NAME);
        this.nameBox.setTextProcessor((string) -> {
            StringBuilder builder = new StringBuilder();
            string.chars().forEach(i -> {
                char ch = (char)i;
                if (ch == '_' || ch == '-' || ch >= 'a' && ch <= 'z' || ch >= '0' && ch <= '9') {
                    builder.append(ch);
                }
            });
            return builder.toString();
        });
        this.nameBox.setKeyActor((string, key) -> {
            if (isEntrySelected() && key == InputConstants.KEY_RETURN || key == InputConstants.KEY_NUMPADENTER) {
                selectedEntry().setName(string);
            }
        });
        this.nameBox.attachToSelector(this);

        this.flagsBox = new EditableText(Minecraft.getInstance().font, x + width + 1, 124, width, C_FLAGS);
        this.flagsBox.setTextProcessor((string) -> {
            StringBuilder builder = new StringBuilder();
            string.chars().forEach(i -> {
                char ch = (char)i;
                if (Character.isDigit(ch) || ch == ' ' || ch == ',') {
                    builder.append(ch);
                }
            });
            return builder.toString();
        });
        this.flagsBox.setKeyActor((string, key) -> {
            if (isEntrySelected() && key == InputConstants.KEY_RETURN || key == InputConstants.KEY_NUMPADENTER) {
                IntList flagSet = new IntArrayList();
                StringBuilder builder = new StringBuilder();
                MutableInt counter = new MutableInt(0);
                string.chars().forEach(i -> {
                    char ch = (char)i;
                    boolean build = true;
                    if (Character.isDigit(ch)) {
                        builder.append(ch);
                        build = false;
                    }

                    if (counter.getValue() == string.length() - 1) build = true;
                    counter.increment();

                    if (build) {
                        try {
                            flagSet.add(Integer.parseInt(builder.toString()));
                        } catch (NumberFormatException ignored) {}
                        builder.setLength(0);
                    }
                });
                selectedEntry().setFlags(flagSet);
            }
        });
        this.flagsBox.attachToSelector(this);

        this.propertiesBox = new ListingBox(Minecraft.getInstance().font, x + width + 1, 164, width, C_FLAGS, false);
        this.propertiesBox.setValidator((string) -> isEntrySelected() && PropertyMap.has(string));
        this.propertiesBox.setColorProcessor((string) -> {
            if (isEntrySelected() && PropertyMap.has(string)) {
                return this.propertiesBox.isDuplicate(string) ? 0xFFFF8000 : 0xFF20FF00;
            }
            return 0xFFFF2000;
        });
        this.propertiesBox.attachToSelector(this);

        this.deleteButton = new Button(x + width + 1, 0, 40, 20, C_DELETE, b -> {
            if (isEntrySelected()) deleteEntry(this.entryPointer);
        }) {
            @Override
            public boolean isActive() {
                return super.isActive() && isEntrySelected();
            }
        };

        this.selectables = List.of(this.blockIdBox, this.nameBox, this.flagsBox, this.propertiesBox);

        this.addButton = new Button(x - 40, y + height - 40, 40, 20, C_ADD, b -> {
            Block block = Registry.BLOCK.byId(this.newEntryBlockId);
            while(this.usedBlocks.containsKey(block)) {
                this.newEntryBlockId = (this.newEntryBlockId + 1) % Registry.BLOCK.size();
                block = Registry.BLOCK.byId(this.newEntryBlockId);
            }
            ClientSchemaMakerEntry newEntry = new ClientSchemaMakerEntry(block);
            newEntry.setIndex(entries.size());
            entries.add(newEntry);
            this.usedBlocks.put(block, newEntry);
        });
    }

    @Override
    public void render(PoseStack pose, int x, int y, float tickOffset) {
        super.render(pose, x, y, tickOffset);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        Font font = Minecraft.getInstance().font;

        //@BACKGROUND
        fill(pose, this.x, this.y,
                this.x + this.width, this.y + this.height, 0xFF202020);

        //@ENTRIES
        StringBuilder nameBuilder = new StringBuilder();
        int startX = this.x + BORDER_WIDTH;
        int endX = this.x + width - BORDER_WIDTH;
        int displayIndex = this.entryPixelOffset / ENTRY_HEIGHT;
        int displayOffset = -(this.entryPixelOffset % ENTRY_HEIGHT) + BORDER_WIDTH;
        int entriesDisplayed = Math.min(maxDisplayableEntries(), this.entries.size() - displayIndex);
        int indexDelta = 0;
        int lIndex;
        boolean lSelected;
        int borderColor;
        int entryY;
        int extraSpacing;

        for (int i = 0; i < entriesDisplayed; i++) {
            lIndex = displayIndex + indexDelta;
            lSelected = this.entryPointer == lIndex;
            borderColor = lSelected ? 0xFFFFFFFF : 0xFF000000;
            entryY = this.y + displayOffset + indexDelta * ENTRY_HEIGHT;
            ClientSchemaMakerEntry entry = this.entries.get(lIndex);
            Item asItem = entry.getBlock().asItem();

            fill(pose, startX, entryY,
                    endX, entryY + ENTRY_HEIGHT, borderColor);
            fill(pose, startX + BORDER_WIDTH, entryY + BORDER_WIDTH,
                    endX - BORDER_WIDTH, entryY + ENTRY_HEIGHT - BORDER_WIDTH, 0xFF404040);

            if (asItem != null && asItem != Items.AIR) {
                itemRenderer.renderGuiItem(new ItemStack(asItem.asItem()),
                        startX + ENTRY_SPACING, entryY + ENTRY_HEIGHT_D2 - 8);
                extraSpacing = 16;
            } else {
                extraSpacing = 0;
            }

            nameBuilder.append(entry.getBlock().getName().getString());
            nameBuilder.append(" *");
            nameBuilder.append(entry.getName());
            font.draw(pose, font.plainSubstrByWidth(nameBuilder.toString(), width - (ENTRY_SPACING * 2 + extraSpacing) - BORDER_OFFSET),
                    startX + ENTRY_SPACING * 2 + extraSpacing, entryY + ENTRY_HEIGHT_D2 - this.fontHeightD2,
                    0xFFFFFFFF);
            nameBuilder.setLength(0);
            indexDelta++;
        }

        //@BORDER
        fill(pose, this.x, this.y,
                this.x + this.width, this.y + 1, 0xFFFFFFFF);
        fill(pose, this.x, this.y + this.height - 1,
                this.x + this.width, this.y + this.height, 0xFFFFFFFF);
        fill(pose, this.x, this.y,
                this.x + 1, this.y + this.height, 0xFFFFFFFF);
        fill(pose, this.x + this.width - 1, this.y,
                this.x + this.width, this.y + this.height, 0xFFFFFFFF);

        //@CHILDREN
        executeByChildren(l -> {
            if (l instanceof Widget widget)
                widget.render(pose, x, y, tickOffset);
        });
    }

    public void tick() {
        executeByChildren(l -> {
            if (l instanceof TickableComponent t)
                t.tick();
        });
    }

    public void mouseMoved(double x, double y) {
        super.mouseMoved(x, y);
        executeByChildren(l -> l.mouseMoved(x, y));
    }

    public boolean mouseClicked(double x, double y, int mod) {
        if (isSelected()) {
            if (inbounds(x, y) && y != this.y && y != this.y + height) {
                int displayIndex = this.entryPixelOffset / ENTRY_HEIGHT;
                int displayOffset = -(this.entryPixelOffset % ENTRY_HEIGHT) + BORDER_WIDTH;
                int entriesDisplayed = Math.min(maxDisplayableEntries(), this.entries.size() - displayIndex);
                int indexDelta = 0;
                int lIndex;
                for (int i = 0; i < entriesDisplayed; i++) {
                    lIndex = displayIndex + indexDelta;
                    if (inboundEntry(x, y, displayOffset + indexDelta * ENTRY_HEIGHT)) {
                        if (this.entryPointer != lIndex) {
                            setEntryPointer(lIndex);
                            playDownSound(Minecraft.getInstance().getSoundManager());
                        }
                        break;
                    }
                    indexDelta++;
                }
            }

            handleSelection(x, y, this.selectables);
            executeByChildren(l -> l.mouseClicked(x, y, mod));
        }

        return false;
    }

    public boolean mouseReleased(double x, double y, int mod) {
        executeByChildren(l -> l.mouseReleased(x, y, mod));
        return super.mouseReleased(x, y, mod);
    }

    public boolean mouseDragged(double xStart, double yStart, int mod,
                                double xEnd, double yEnd) {
        executeByChildren(l -> l.mouseDragged(xStart, yStart, mod, xEnd, yEnd));
        return super.mouseDragged(xStart, yStart, mod, xEnd, yEnd);
    }

    public boolean mouseScrolled(double x, double y, double offset) {
        setEntryPixelOffset(this.entryPixelOffset - (int)(offset * 5));
        executeByChildren(l -> l.mouseScrolled(x, y, offset));
        return super.mouseScrolled(x, y, offset);
    }

    public boolean keyPressed(int key, int p_94746_, int p_94747_) {
        executeByChildren(l -> {
            if (l instanceof EditableText)
                l.keyPressed(key, p_94746_, p_94747_);
        });
        return super.keyPressed(key, p_94746_, p_94747_);
    }

    public boolean keyReleased(int key, int p_94751_, int p_94752_) {
        executeByChildren(l -> l.keyReleased(key, p_94751_, p_94752_));
        return super.keyReleased(key, p_94751_, p_94752_);
    }

    public boolean charTyped(char key, int p_94733_) {
        if (this.selected != null) {
            selected.charTyped(key, p_94733_);
        }
        return super.charTyped(key, p_94733_);
    }

    @Override
    public boolean shouldBeSelected(int x, int y) {
        return inbounds(x, y);
    }

    @Override
    public void onSelection() {}

    @Override
    public void onDeselection() {
        this.entryPointer = -1;
    }

    private void forceDataUpdate() {
        this.blockIdBox.runKeyActor(InputConstants.KEY_RETURN);
        this.nameBox.runKeyActor(InputConstants.KEY_RETURN);
        this.flagsBox.runKeyActor(InputConstants.KEY_RETURN);
        this.propertiesBox.runKeyActor(InputConstants.KEY_RETURN);
    }

    public void onSave() {
        if (isEntrySelected()) forceDataUpdate();
    }

    @Override
    public void attachToSelector(BCSelector selector) {
        this.screen = selector;
    }

    public void setEntryPointer(int entryPointer) {
        changeSelected(null);
        if (this.entries.isEmpty()) {
            this.entryPointer = -1;
        } else {
            int old = this.entryPointer;
            int newPointer = Mth.clamp(entryPointer, 0, this.entries.size() - 1);
            boolean changed = old >= 0 && old < this.entries.size() && old != newPointer;
            if (changed) {
                forceDataUpdate();
            }
            this.entryPointer = newPointer;
            ClientSchemaMakerEntry entry = selectedEntry();
            this.blockIdBox.setCurrentText(entry.getBlock().getRegistryName().toString());
            this.nameBox.setCurrentText(entry.getName());
            if (entry.getFlags() != null) {
                this.flagsBox.setCurrentText(String.join(", ", entry.getFlags().intStream().mapToObj(Integer::toString).toList()));
            } else {
                this.flagsBox.setCurrentText("");
            }
            if (entry.getProperties() != null) {
                this.propertiesBox.setNewContents(entry.getProperties());
            } else {
                this.propertiesBox.setNewContents(Lists.newArrayList());
                entry.setProperties(this.propertiesBox.getContents());
            }
            this.propertiesBox.setCurrentText("");
        }
    }

    public void setEntryPixelOffset(int entryPixelOffset) {
        this.entryPixelOffset = Mth.clamp(entryPixelOffset,
                0,
                Math.max(0, this.entries.size() * ENTRY_HEIGHT - (this.height - BORDER_OFFSET)));
    }

    @Override
    public void setSelected(BCSelectable selectable) {
        this.selected = selectable;
    }

    public void setEntries(List<ClientSchemaMakerEntry> entries) {
        this.entries = entries;
        this.entryPointer = -1;
        updateBlockMapToList();
    }

    public void updateBlockMapToList() {
        if (!this.usedBlocks.isEmpty())
            this.usedBlocks.clear();
        this.entries.forEach(en -> {
            this.usedBlocks.put(en.getBlock(), en);
        });
    }
    
    public void deleteEntry(int index) {
        this.entries.remove(index);
        ClientSchemaMakerEntry entry;
        for (int i = index; i < this.entries.size(); i++) {
            entry = this.entries.get(i);
            entry.setIndex(entry.getIndex() - 1);
        }
        if (this.entryPointer >= index)
            setEntryPointer(this.entryPointer - 1);
    }

    @Override
    public void updateNarration(NarrationElementOutput out) {
        out.add(NarratedElementType.TITLE, C_TITLE);
    }

    @Override
    public BCSelector selector() {
        return this.screen;
    }

    @Override
    public BCSelectable selected() {
        return this.selected;
    }

    public List<ClientSchemaMakerEntry> getEntries() {
        return entries;
    }

    @Nullable
    public ClientSchemaMakerEntry getEntryByBlock(Block block) {
        if (this.usedBlocks.containsKey(block)) {
            return this.usedBlocks.get(block);
        }
        return null;
    }

    private int maxDisplayableEntries() {
        return ((this.y + this.height - BORDER_OFFSET) % ENTRY_HEIGHT) + 1;
    }

    private ClientSchemaMakerEntry selectedEntry() {
        return this.entries.get(this.entryPointer);
    }

    private boolean inbounds(double x, double y) {
        return x >= this.x && x < this.x + width && y >= this.y && y < this.y + this.height;
    }

    private boolean inboundEntry(double x, double y, double entryY) {
        return x >= this.x + BORDER_WIDTH && x < this.x + width - BORDER_WIDTH && y >= entryY && y < entryY + ENTRY_HEIGHT;
    }

    private boolean isEntrySelected() {
        return this.entryPointer >= 0;
    }

    public int getEntryPointer() {
        return entryPointer;
    }

    private void executeByChildren(Consumer<GuiEventListener> actor) {
        actor.accept(this.addButton);
        if (isSelected() && isEntrySelected()) {
            actor.accept(this.deleteButton);
            actor.accept(this.blockIdBox);
            actor.accept(this.nameBox);
            actor.accept(this.flagsBox);
            actor.accept(this.propertiesBox);
        }
    }
}
