package net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator;

public enum LootTableType {
    EMPTY(""),
    ENTITY("entity"),
    BLOCK("block"),
    CHEST("chest"),
    FISHING("fishing"),
	GIFT("gift"),
    BARTER("barter");

    private final String str;

    LootTableType(String type) {
        this.str = type;
    }

    public String str() {
        return this.str;
    }
}
