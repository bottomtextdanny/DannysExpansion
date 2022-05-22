package bottomtextdanny.flagged_schema_block;

import bottomtextdanny.braincell.mod.gui.EditableText;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.*;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.ints.IntList;
import bottomtextdanny.braincell.mod.PropertyMap;
import bottomtextdanny.braincell.mod.gui.BCScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static bottomtextdanny.braincell.mod._base.plotter.schema.SchemaManager.*;

public class FlaggedSchemaScreen extends BCScreen {
    private static final Gson PARSER = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    private static final Component C_POSITION =
            new TranslatableComponent("flagged_schema_maker.position");
    private static final Component C_SIZE =
            new TranslatableComponent("flagged_schema_maker.size");
    private static final Component C_OFFSET =
            new TranslatableComponent("flagged_schema_maker.offset");
    private static final Component C_TITLE =
            new TranslatableComponent("flagged_schema");
    private static final Component C_SAVE =
            new TranslatableComponent("flagged_schema_maker.save");
    private static final Component C_PARSE =
            new TranslatableComponent("flagged_schema_maker.parse");
    private EditableText posXEdit;
    private EditableText posYEdit;
    private EditableText posZEdit;
    private EditableText sizeXEdit;
    private EditableText sizeYEdit;
    private EditableText sizeZEdit;
    private EditableText offsetXEdit;
    private EditableText offsetYEdit;
    private EditableText offsetZEdit;
    private Button saveButton;
    private Button parseToClipboardButton;
    private FlaggedEntriesWidget entriesWidget;
    private final FlaggedSchemaBlockEntity blockEntity;
    private List<ClientSchemaMakerEntry> fakeEntries;

    public FlaggedSchemaScreen(FlaggedSchemaBlockEntity blockEntity) {
        super(C_TITLE);
        this.blockEntity = blockEntity;
        this.fakeEntries = createClientEntries(blockEntity);
    }

    @Override
    protected void init() {
        super.init();
        Function<String, String> intProcessor = integerInput(511);
        Function<String, String> signedIntProcessor = signedIntegerInput(511);
        int widthBy3 = this.width / 3;
        int buttonPosOffsetY = 24;
        int buttonSizeOffsetY = 104;
        int buttonOffsetOffsetY = 184;
        int buttonsOffsetY = 256;
        this.posXEdit = new EditableText(this.font, 0, buttonPosOffsetY, 80, new TextComponent("X"));
        this.posXEdit.setCurrentText(Integer.toString(blockEntity.getBoxPosX()));
        this.posXEdit.setTextProcessor(signedIntProcessor);
        this.addWidget(this.posXEdit);
        this.posYEdit = new EditableText(this.font, 0, buttonPosOffsetY + 20, 80, new TextComponent("Y"));
        this.posYEdit.setCurrentText(Integer.toString(blockEntity.getBoxPosY()));
        this.posYEdit.setTextProcessor(signedIntProcessor);
        this.addWidget(this.posYEdit);
        this.posZEdit = new EditableText(this.font, 0, buttonPosOffsetY + 40, 80, new TextComponent("Z"));
        this.posZEdit.setCurrentText(Integer.toString(blockEntity.getBoxPosZ()));
        this.posZEdit.setTextProcessor(signedIntProcessor);
        this.addWidget(this.posZEdit);
        this.sizeXEdit = new EditableText(this.font, 0, buttonSizeOffsetY, 80, new TextComponent("X"));
        this.sizeXEdit.setCurrentText(Integer.toString(blockEntity.getBoxSizeX()));
        this.sizeXEdit.setTextProcessor(intProcessor);
        this.addWidget(this.sizeXEdit);
        this.sizeYEdit = new EditableText(this.font, 0, buttonSizeOffsetY + 20, 80, new TextComponent("Y"));
        this.sizeYEdit.setCurrentText(Integer.toString(blockEntity.getBoxSizeY()));
        this.sizeYEdit.setTextProcessor(intProcessor);
        this.addWidget(this.sizeYEdit);
        this.sizeZEdit = new EditableText(this.font, 0, buttonSizeOffsetY + 40, 80, new TextComponent("Z"));
        this.sizeZEdit.setCurrentText(Integer.toString(blockEntity.getBoxSizeZ()));
        this.sizeZEdit.setTextProcessor(intProcessor);
        this.addWidget(this.sizeZEdit);
        this.offsetXEdit = new EditableText(this.font, 0, buttonOffsetOffsetY, 80, new TextComponent("X"));
        this.offsetXEdit.setCurrentText(Integer.toString(blockEntity.getBoxOffsetX()));
        this.offsetXEdit.setTextProcessor(signedIntProcessor);
        this.addWidget(this.offsetXEdit);
        this.offsetYEdit = new EditableText(this.font, 0, buttonOffsetOffsetY + 20, 80, new TextComponent("Y"));
        this.offsetYEdit.setCurrentText(Integer.toString(blockEntity.getBoxOffsetY()));
        this.offsetYEdit.setTextProcessor(signedIntProcessor);
        this.addWidget(this.offsetYEdit);
        this.offsetZEdit = new EditableText(this.font, 0, buttonOffsetOffsetY + 40, 80, new TextComponent("Z"));
        this.offsetZEdit.setCurrentText(Integer.toString(blockEntity.getBoxOffsetZ()));
        this.offsetZEdit.setTextProcessor(signedIntProcessor);
        this.addWidget(this.offsetZEdit);
        this.saveButton = new Button(0, buttonsOffsetY, 40, 20, C_SAVE, b -> {
            save();
        });
        this.addWidget(this.saveButton);
        this.parseToClipboardButton = new Button(0, this.height - 20, Math.min(120, widthBy3), 20, C_PARSE, b -> {
            copyAsJSON();
        });
        this.addWidget(this.parseToClipboardButton);

        this.entriesWidget = new FlaggedEntriesWidget(widthBy3, 0, widthBy3, this.height, this.fakeEntries);
        this.addWidget(this.entriesWidget);
    }

    public void resize(Minecraft minecraft, int width, int height) {
        String posX = this.posXEdit.getCurrentText();
        String posY = this.posYEdit.getCurrentText();
        String posZ = this.posZEdit.getCurrentText();
        String sizeX = this.sizeXEdit.getCurrentText();
        String sizeY = this.sizeYEdit.getCurrentText();
        String sizeZ = this.sizeZEdit.getCurrentText();
        String offsetX = this.offsetXEdit.getCurrentText();
        String offsetY = this.offsetYEdit.getCurrentText();
        String offsetZ = this.offsetZEdit.getCurrentText();
        List<ClientSchemaMakerEntry> entries = this.entriesWidget.getEntries();
        int entryIndex = this.entriesWidget.getEntryPointer();
        this.init(minecraft, width, height);
        this.posXEdit.setCurrentText(posX);
        this.posYEdit.setCurrentText(posY);
        this.posZEdit.setCurrentText(posZ);
        this.sizeXEdit.setCurrentText(sizeX);
        this.sizeYEdit.setCurrentText(sizeY);
        this.sizeZEdit.setCurrentText(sizeZ);
        this.offsetXEdit.setCurrentText(offsetX);
        this.offsetYEdit.setCurrentText(offsetY);
        this.offsetZEdit.setCurrentText(offsetZ);
        this.entriesWidget.setEntries(entries);
        this.entriesWidget.setEntryPointer(entryIndex);
    }

    public void copyAsJSON() {
        save();
        if (this.blockEntity.getLevel() == null) return;
        int posX = getInteger(this.posXEdit);
        int posY = getInteger(this.posYEdit);
        int posZ = getInteger(this.posZEdit);
        int sizeX = getInteger(this.sizeXEdit);
        int sizeY = getInteger(this.sizeYEdit);
        int sizeZ = getInteger(this.sizeZEdit);
        int offsetX = getInteger(this.offsetXEdit);
        int offsetY = getInteger(this.offsetYEdit);
        int offsetZ = getInteger(this.offsetZEdit);

        JsonObject root = new JsonObject();
        JsonArray baseSerial = new JsonArray();
        JsonArray entriesSerial = new JsonArray();
        JsonArray propertyEntriesSerial = new JsonArray();
        JsonArray blockPositionsSerial = new JsonArray();

        root.add(M_BASE, baseSerial);
        root.add(M_ENTRIES, entriesSerial);
        root.add(M_PROPERTY_ENTRIES, propertyEntriesSerial);
        root.add(M_BLOCK_DATA, blockPositionsSerial);

        //@POSITION
        baseSerial.add(posX);
        baseSerial.add(posY);
        baseSerial.add(posZ);
        baseSerial.add(sizeX);
        baseSerial.add(sizeY);
        baseSerial.add(sizeZ);
        baseSerial.add(offsetX);
        baseSerial.add(offsetY);
        baseSerial.add(offsetZ);

        //@ENTRIES
        List<ClientSchemaMakerEntry> entries = this.entriesWidget.getEntries();
        JsonObject entrySerial;
        JsonArray flagArr;
        for (ClientSchemaMakerEntry en : entries) {
            entrySerial = new JsonObject();
            flagArr = new JsonArray();
            entrySerial.add(M_ENTRY_NAME, new JsonPrimitive(en.getName()));
            entrySerial.add(M_ENTRY_FLAGS, flagArr);

            if (en.getFlags() != null) {
                int[] flagIntArray = en.getFlags().toIntArray();
                for (int flag : flagIntArray) {
                    flagArr.add(flag);
                }
            }
            entriesSerial.add(entrySerial);
        }

        //@POSITIONS
        BiMap<Property<?>, Integer> indexedProperties = HashBiMap.create(32);
        Map<Property<?>, PropertyData> propertyEntries = Maps.newIdentityHashMap();
        JsonArray posSerial;
        BlockPos startPos = this.blockEntity.getBlockPos().offset(posX, posY, posZ);
        BlockPos offsetPos = BlockPos.ZERO.offset(
                Math.floor(((double)sizeX - 1.0) / 2) + offsetX,
                offsetY,
                Math.floor(((double)sizeZ - 1.0) / 2) + offsetZ);
        BlockPos.MutableBlockPos worldPos = new BlockPos.MutableBlockPos();
        ClientSchemaMakerEntry lEntry;
        for (int x = 0; x <= sizeX; x++) {
            for (int z = 0; z <= sizeZ; z++) {
                for (int y = 0; y <= sizeY; y++) {
                    worldPos.set(x + startPos.getX(), y + startPos.getY(), z + startPos.getZ());
                    BlockState blockState = this.blockEntity.getLevel().getBlockState(worldPos);
                    lEntry = this.entriesWidget.getEntryByBlock(blockState.getBlock());

                    if (lEntry != null) {
                        List<Integer> propertyIndices = Lists.newLinkedList();
                        if (lEntry.getProperties() != null) {
                            for (String s : lEntry.getProperties()) {
                                Property<?> asProperty = PropertyMap.getProperty(s);
                                if (asProperty != null && blockState.hasProperty(asProperty)) {
                                    PropertyData data;
                                    String propertyValue = forceGetName(asProperty, blockState.getValue(asProperty));

                                    if (!indexedProperties.containsKey(asProperty))
                                        indexedProperties.put(asProperty, indexedProperties.size());

                                    if (!propertyEntries.containsKey(asProperty)) {
                                        data = new PropertyData(s, Maps.newHashMap());
                                        propertyEntries.put(asProperty, data);
                                    } else
                                        data = propertyEntries.get(asProperty);

                                    if (!data.values().containsKey(propertyValue))
                                        data.values().put(propertyValue, data.values().size());

                                    propertyIndices.add(indexedProperties.get(asProperty));
                                    propertyIndices.add(data.values().get(propertyValue));
                                }
                            }
                        }

                        posSerial = new JsonArray();
                        posSerial.add(x - offsetPos.getX());
                        posSerial.add(y - offsetPos.getY());
                        posSerial.add(z - offsetPos.getZ());
                        posSerial.add(lEntry.getIndex());
                        for (Integer i : propertyIndices) {
                            posSerial.add(i);
                        }
                        blockPositionsSerial.add(posSerial);
                    }
                }
            }
        }
        List<Map.Entry<Property<?>, Integer>> sorted = Lists.newArrayList();
        sorted.addAll(indexedProperties.entrySet());
        sorted.sort(Comparator.comparingInt(Map.Entry::getValue));
        sorted.forEach(en -> {
            JsonObject propertyObject = new JsonObject();
            JsonArray valuesArr = new JsonArray();
            PropertyData data = propertyEntries.get(en.getKey());
            data.values().entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getValue)).forEach(value -> {
                valuesArr.add(new JsonPrimitive(value.getKey()));
            });
            propertyObject.add(M_PROPERTY_ENTRIES_NAME, new JsonPrimitive(data.name));
            propertyObject.add(M_PROPERTY_ENTRIES_VALUES, valuesArr);
            propertyEntriesSerial.add(propertyObject);
        });
        String parsed = PARSER.toJson(root);
        Minecraft.getInstance().keyboardHandler.setClipboard(parsed);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> String forceGetName(Property<T> property, Object value) {
        return property.getName((T)value);
    }

    public void save() {
        this.entriesWidget.onSave();
        int posX = getInteger(this.posXEdit);
        int posY = getInteger(this.posYEdit);
        int posZ = getInteger(this.posZEdit);
        int sizeX = getInteger(this.sizeXEdit);
        int sizeY = getInteger(this.sizeYEdit);
        int sizeZ = getInteger(this.sizeZEdit);
        int offsetX = getInteger(this.offsetXEdit);
        int offsetY = getInteger(this.offsetYEdit);
        int offsetZ = getInteger(this.offsetZEdit);
        MSGUpdateServerFlaggedSchemaBlock packet = new MSGUpdateServerFlaggedSchemaBlock(this.blockEntity.getBlockPos().asLong(),
                posX, posY, posZ, sizeX, sizeY, sizeZ, offsetX, offsetY, offsetZ);
        packet.setClientEntries(this.entriesWidget.getEntries());
        packet.sendToServer();
    }

    private static List<ClientSchemaMakerEntry> createClientEntries(FlaggedSchemaBlockEntity blockEntity) {
        Map<Block, SerializableSchemaMakerEntry> original = blockEntity.getEntries();
        List<ClientSchemaMakerEntry> list = Lists.newArrayListWithExpectedSize(original.size());
        MutableInt counter = new MutableInt();
        original.forEach((block, entry) -> {
            ClientSchemaMakerEntry lEntry = new ClientSchemaMakerEntry();
            lEntry.setIndex(counter.getAndIncrement());
            lEntry.setBlock(block);
            lEntry.setName(entry.name());
            lEntry.setFlags(IntList.of(entry.flags().toIntArray()));
            lEntry.setProperties(new ArrayList<>(entry.properties()));
            list.add(lEntry);
        });

        return list;
    }

    private int getInteger(EditableText text) {
        if (text == null || text.getCurrentText().isEmpty()) return 0;
        if (text.getCurrentText().length() == 1 && !Character.isDigit(text.getCurrentText().charAt(0))) return 0;
        return Integer.parseInt(text.getCurrentText());
    }

    public void removed() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    public void tick() {
        super.tick();
        this.entriesWidget.tick();
        this.posXEdit.tick();
        this.posYEdit.tick();
        this.posZEdit.tick();
        this.sizeXEdit.tick();
        this.sizeYEdit.tick();
        this.sizeZEdit.tick();
        this.offsetXEdit.tick();
        this.offsetYEdit.tick();
        this.offsetZEdit.tick();
    }

    public void render(PoseStack pose, int mouseX, int mouseY, float tickOffset) {
        this.renderBackground(pose);
        int widthBy2 = this.width / 3;

        String label = this.font.plainSubstrByWidth(C_POSITION.getString(), widthBy2);

        this.entriesWidget.render(pose, mouseX, mouseY, tickOffset);

        drawString(pose, this.font, label, 0, 10, 0xFFC0C0C0);
        this.posXEdit.render(pose, mouseX, mouseY, tickOffset);
        this.posYEdit.render(pose, mouseX, mouseY, tickOffset);
        this.posZEdit.render(pose, mouseX, mouseY, tickOffset);
        label = this.font.plainSubstrByWidth(C_SIZE.getString(), widthBy2);
        drawString(pose, this.font, label, 0, 90, 0xFFC0C0C0);
        this.sizeXEdit.render(pose, mouseX, mouseY, tickOffset);
        this.sizeYEdit.render(pose, mouseX, mouseY, tickOffset);
        this.sizeZEdit.render(pose, mouseX, mouseY, tickOffset);
        label = this.font.plainSubstrByWidth(C_OFFSET.getString(), widthBy2);
        drawString(pose, this.font, label, 0, 170, 0xFFC0C0C0);
        this.offsetXEdit.render(pose, mouseX, mouseY, tickOffset);
        this.offsetYEdit.render(pose, mouseX, mouseY, tickOffset);
        this.offsetZEdit.render(pose, mouseX, mouseY, tickOffset);

        this.saveButton.render(pose, mouseX, mouseY, tickOffset);
        this.parseToClipboardButton.render(pose, mouseX, mouseY, tickOffset);

        super.render(pose, mouseX, mouseY, tickOffset);
    }

    private Function<String, String> integerInput(int maxValue) {
        return (str) -> {
            String parsed = "";
            try {
                StringBuilder builder = new StringBuilder();
                str.chars().filter(Character::isDigit).forEach(i -> builder.append((char)i));
                parsed = String.valueOf(Math.min(maxValue, Integer.valueOf(builder.toString(), 10)));
            } catch(NumberFormatException ignored) {}
            return parsed;
        };
    }

    private Function<String, String> signedIntegerInput(int maxValue) {
        return (str) -> {
            String parsed = "";
            try {
                StringBuilder builder = new StringBuilder();
                MutableInt counter = new MutableInt();
                str.chars().boxed().filter(i -> {
                    int index = counter.getAndIncrement();
                    return index == 0 && ((char) i.intValue() == '-' || (char) i.intValue() == '+') || Character.isDigit(i);
                }).forEach(i -> builder.append((char)i.intValue()));
                parsed = builder.toString();
                if (parsed.length() > 1 || (!parsed.isEmpty() && Character.isDigit(parsed.charAt(0)))) {
                    parsed = String.valueOf(Math.min(maxValue, Integer.parseInt(parsed)));
                }
            } catch(NumberFormatException ignored) {}
            return parsed;
        };
    }

    public boolean isPauseScreen() {
        return false;
    }

    private record PropertyData(String name, Map<String, Integer> values) {}
}
