package bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator;

public enum EntryType {
    /**
     *  for item entries (DEFAULT).
     */
    ITEM("item"),

    /**
     *  select one sub-entry from a list.
     */
    ALTERNATIVE("alternatives"),

    /**
     *  produce items from another loot table.
     */
    LOOT_TABLE("loot_table"),

    /**
     *  select sub-entries until one entry cannot be granted.
     */
    SEQUENCE("sequence"),

    /**
     *  generate block specific drops.
     */
    DYNAMIC("dynamic"),

    /**
     *  an entry that generates nothing.
     */
    EMPTY("empty");


    private final String str;

    EntryType(String type) {
        this.str = type;
    }

    public String str() {
        return this.str;
    }
}
