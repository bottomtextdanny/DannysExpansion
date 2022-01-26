package net.bottomtextdanny.de_json_generator.types.extension;

import net.bottomtextdanny.de_json_generator.TagBuffer;
import net.bottomtextdanny.de_json_generator.types.base.Generator;

import java.io.IOException;

public class TagGenExt implements IGenExtension<Generator> {
    private TagBuffer[] tagBufferArr;

    public static TagGenExt of(TagBuffer tag) {
        TagGenExt extender = new TagGenExt();
        extender.tagBufferArr = new TagBuffer[]{tag};
        return extender;
    }
	
	public static TagGenExt of(TagBuffer... tag) {
		TagGenExt extender = new TagGenExt();
		extender.tagBufferArr = tag;
		return extender;
	}
	
    @Override
    public void generate(Generator base) throws IOException {
    	for(TagBuffer tag : this.tagBufferArr) {
		    tag.add(base.getName());
	    }
    }
}
