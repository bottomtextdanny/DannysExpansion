package bottomtextdanny.de_json_generator;

public enum SimpleBlockstate {
    NORMAL("s_blockstate"),
    HORIZONTAL_ROTATIVE("s_rotative_horizontal"),
    ;

    private final String dir;


    SimpleBlockstate(String name) {
        this.dir = name;
    }

    public String getDir() {
        return this.dir;
    }
}