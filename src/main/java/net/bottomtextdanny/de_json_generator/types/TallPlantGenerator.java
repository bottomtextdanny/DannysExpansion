package net.bottomtextdanny.de_json_generator.types;

import net.bottomtextdanny.de_json_generator.types.base.Generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;

public class TallPlantGenerator extends Generator<TallPlantGenerator> {
	public TallPlantGenerator(String name) {
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
		String template = getTemplate("blockstates", "tall_plant");
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(json));
		template = P_NAME.matcher(template).replaceAll(Matcher.quoteReplacement(this.name));
		writer.write(template);
		
		writer.close();
	}
	
	public void generateBlockModel() throws IOException {
		File           json = createBlockModel(this.name + "_bottom");
		String         template = getTemplate("blockmodels", "tall_plant_bottom");
		BufferedWriter writer = new BufferedWriter(new FileWriter(json));
		
		writer.write(P_NAME.matcher(template).replaceAll(Matcher.quoteReplacement(this.name)));
		
		writer.close();
		
		json = createBlockModel(this.name + "_top");
		template = getTemplate("blockmodels", "tall_plant_top");
		writer = new BufferedWriter(new FileWriter(json));
		
		writer.write(P_NAME.matcher(template).replaceAll(Matcher.quoteReplacement(this.name)));
		
		writer.close();
	}
	
	public void generateItemModel() throws IOException {
		File           json = createItemModel(this.name);
		String         template = getTemplate("itemmodels", "block_item_baked");
		BufferedWriter writer = new BufferedWriter(new FileWriter(json));
		
		writer.write(template.replaceAll("_name", this.name + "_top"));
		
		writer.close();
	}
}
