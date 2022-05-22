package bottomtextdanny.de_json_generator.types.item;

import bottomtextdanny.de_json_generator.jsonBakers.itemmodel.ItemModel;

public class MatrixTemplateModel extends ItemModel<MatrixTemplateModel> {
	
	public MatrixTemplateModel(String parent) {
		super(parent);
	}
	
	@Override
	public boolean isTemplate() {
		return true;
	}
}
