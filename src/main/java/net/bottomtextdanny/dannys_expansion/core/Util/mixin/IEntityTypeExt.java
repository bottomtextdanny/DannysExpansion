package net.bottomtextdanny.dannys_expansion.core.Util.mixin;

import net.bottomtextdanny.danny_expannny.objects.items.SpecialKiteItem;

public interface IEntityTypeExt {

    int DE_getRegId();

    void DE_setRegId(int newId);

    SpecialKiteItem DE_getFixedKite();

    void DE_setFixedKite(SpecialKiteItem newKite);

}
