package bottomtextdanny.de_json_generator.jsonBakers.itemmodel;

import bottomtextdanny.de_json_generator.jsonBakers.JsonBaker;

public class MatrixModifier extends JsonBaker<MatrixModifier> {
	public final String name;
	public float[] rotationVector = new float[3];
	public float[] translationVector = new float[3];
	public float[] scaleVector = new float[3];
	boolean modified;
	
	public MatrixModifier(String name) {
		this.name = name;
	}
	
	public MatrixModifier rotation(float x, float y, float z) {
        this.rotationVector[0] = x;
        this.rotationVector[1] = y;
        this.rotationVector[2] = z;
        this.modified = true;
		return this;
	}
	
	public MatrixModifier translation(float x, float y, float z) {
        this.translationVector[0] = x;
        this.translationVector[1] = y;
        this.translationVector[2] = z;
        this.modified = true;
		return this;
	}
	
	public MatrixModifier scale(float x, float y, float z) {
        this.scaleVector[0] = x;
        this.scaleVector[1] = y;
        this.scaleVector[2] = z;
        this.modified = true;
		return this;
	}
	
	public boolean isModified() {
		return this.modified;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public MatrixModifier bake() {
        this.jsonObj.add("rotation", cFloatArray(this.rotationVector));
        this.jsonObj.add("translation", cFloatArray(this.translationVector));
        this.jsonObj.add("scale", cFloatArray(this.scaleVector));
		return this;
	}
	
	
	
}
