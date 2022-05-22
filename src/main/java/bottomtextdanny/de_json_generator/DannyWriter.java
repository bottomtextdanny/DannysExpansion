package bottomtextdanny.de_json_generator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class DannyWriter extends BufferedWriter {
    private String spacing = "";
    private boolean doSpacing;

    public DannyWriter(Writer out) {
        super(out);
    }

    @Override
    public void newLine() throws IOException {
        super.newLine();
        this.doSpacing = true;
    }

    @Override
    public void write(String str) throws IOException {
        if (this.doSpacing) {
            str = this.spacing + str;
            this.doSpacing = false;
        }

        super.write(str);
    }

    public void still(String str) throws IOException {
        write(str);
        newLine();
    }

    public void openBracket(String str) throws IOException {
        still(str);
        this.spacing += "  ";
    }

    public void closeBracket(String str) throws IOException {
        if (this.spacing.length() < 2) this.spacing = "";
        else this.spacing = this.spacing.substring(2);
        still(str);
    }
}
