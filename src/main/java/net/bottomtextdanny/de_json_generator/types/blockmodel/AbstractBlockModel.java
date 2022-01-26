package net.bottomtextdanny.de_json_generator.types.blockmodel;

public abstract class AbstractBlockModel implements IGenBlockModel<AbstractBlockModel> {
    protected String name;

    public AbstractBlockModel() {
    }

    public String getName() {
        return this.name;
    }

    @Override
    public AbstractBlockModel setNameRemote(String newName) {
        this.name = newName;
        return this;
    }
}
