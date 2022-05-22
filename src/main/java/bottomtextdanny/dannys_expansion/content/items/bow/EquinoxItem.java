package bottomtextdanny.dannys_expansion.content.items.bow;

public class EquinoxItem extends DEBowItem implements IBigBowModelLoader {

    public EquinoxItem(Properties properties) {
        super(true, properties);
    }

    @Override
    public boolean automatic() {
        return true;
    }
}
