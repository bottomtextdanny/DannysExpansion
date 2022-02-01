package net.bottomtextdanny.dannys_expansion.core.Util.mixin;

public interface IItemEntityExt {

    int de_getShowingModel();
	
	void de_setShowingModel(int item);

    void de_setShowingModel(Enum<?> item);
}
