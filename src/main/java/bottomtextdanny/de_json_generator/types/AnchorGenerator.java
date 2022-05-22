package bottomtextdanny.de_json_generator.types;

import bottomtextdanny.de_json_generator.types.base.Generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AnchorGenerator extends Generator<AnchorGenerator> {

    public AnchorGenerator(String name) {
        super(name);
    }

    @Override
    public void generate() throws IOException {
        generateBlockstate();
        generateBlockModel();
        generateItemModel();
    }

    public void generateBlockstate() throws IOException {
        File json = createBlockstate(this.name);
        String template = getTemplate("blockstates", "anchor");

        BufferedWriter writer = new BufferedWriter(new FileWriter(json));
        writer.write(template.replaceAll("_name", this.name));

        writer.close();
    }

    public void generateBlockModel() throws IOException {
        File           json = createBlockModel(this.name);
        String         template = getTemplate("blockmodels", "top_side_down");
        BufferedWriter writer = new BufferedWriter(new FileWriter(json));

        writer.write(template
		    .replaceAll("_IDtop", MOD_ID)
		    .replaceAll("_IDside", MOD_ID)
		    .replaceAll("_IDbottom", MOD_ID)
            .replaceAll("_Btop", this.name + "_top")
            .replaceAll("_Bside", this.name + "_side")
            .replaceAll("_Bbottom", this.name + "_bottom")
        );
      
        writer.close();

        json = createBlockModel(this.name + "_active");
        template = getTemplate("blockmodels", "top_side_down");
        writer = new BufferedWriter(new FileWriter(json));
	
	    writer.write(template
		    .replaceAll("_IDtop", MOD_ID)
		    .replaceAll("_IDside", MOD_ID)
		    .replaceAll("_IDbottom", MOD_ID)
		    .replaceAll("_Btop", this.name + "_top_active")
		    .replaceAll("_Bside", this.name + "_side_active")
		    .replaceAll("_Bbottom", this.name + "_bottom_active")
	    );
	    
	    writer.close();
    }

    public void generateItemModel() throws IOException {
        File           json = createItemModel(this.name);
        String         template = getTemplate("itemmodels", "block_item");
        BufferedWriter writer = new BufferedWriter(new FileWriter(json));

        writer.write(template.replaceAll("_name", this.name + "_active"));

        writer.close();
    }
}
